pipeline {
    agent any  // Runs on any available agent

    environment {
        PYTHON_VERSION = '3.11'  // Specify Python version you want to use
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout the code from your version control system (e.g., Git)
                checkout scm
            }
        }

        stage('Setup Python') {
            steps {
                script {
                    // Install the specific version of Python if necessary
                    sh 'python3 -m venv venv'  // Create a virtual environment
                    sh 'source venv/bin/activate'  // Activate the virtual environment
                    sh 'pip install --upgrade pip'  // Upgrade pip to the latest version
                }
            }
        }

        stage('Install Dependencies') {
            steps {
                script {
                    // Install dependencies from requirements.txt
                    sh 'source venv/bin/activate && pip install -r requirements.txt'
                }
            }
        }

        stage('Run Tests') {
            steps {
                script {
                    // Run tests using pytest or your test framework of choice
                    sh 'source venv/bin/activate && pytest --maxfail=1 --disable-warnings -q'
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    // You can add steps to build the application if needed
                    // For example, creating a distribution package or similar
                    echo 'Building the Python application...'
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    // Add deployment steps if necessary
                    echo 'Deploying the application...'
                    // Example: SSH to a server and deploy the app or use cloud services
                }
            }
        }
    }

    post {
        always {
            // Cleanup tasks that run after the pipeline finishes
            echo 'Cleaning up...'
        }

        success {
            // Steps to take if the build is successful
            echo 'Build and tests passed successfully!'
        }

        failure {
            // Steps to take if the build fails
            echo 'Build or tests failed.'
        }
    }
}