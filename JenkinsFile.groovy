pipeline {
    agent any

    environment {
        PYTHON_VERSION = '3.8'  // Set the desired Python version
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout the code from the version control system (Git, etc.)
                checkout scm
            }
        }

        stage('Setup Python Environment') {
            steps {
                script {
                    // Set up the Python virtual environment
                    sh 'python3 -m venv venv'  // Create a virtual environment
                    sh 'source venv/bin/activate'  // Activate the virtual environment
                }
            }
        }

        stage('Install Dependencies') {
            steps {
                script {
                    // Install the dependencies from requirements.txt
                    sh 'source venv/bin/activate && pip install -r requirements.txt'
                }
            }
        }

        stage('Run Tests') {
            steps {
                script {
                    // Run the tests using unittest and generate JUnit-compatible XML reports
                    sh 'source venv/bin/activate && python -m unittest discover -s tests -p "*.py" | tee result.log | python -m xmlrunner --output target/surefire-reports'
                }
            }
        }

        stage('Publish Test Results') {
            steps {
                // Publish the test results in JUnit format (ensure the path matches where your results are saved)
                junit 'target/surefire-reports/TEST-*.xml'
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying Python application...'
                // Add deployment steps as needed, e.g., deploying to a server
            }
        }
    }

    post {
        always {
            echo 'Cleaning up...'
        }

        success {
            echo 'Build and tests completed successfully!'
        }

        failure {
            echo 'Build or tests failed.'
        }
    }
}
