import datetime

class AikidoTracker:
    def __init__(self):
        self.sessions = []

    def add_session(self, date: str, duration: int):
        try:
            session_date = datetime.datetime.strptime(date, "%Y-%m-%d").date()
            self.sessions.append((session_date, duration))
        except ValueError:
            print("Invalid date format. Use YYYY-MM-DD.")

    def get_total_practice_time(self) -> int:
        return sum(duration for _, duration in self.sessions)

    def check_graduation_eligibility(self, required_hours: int = 100) -> bool:
        total_hours = self.get_total_practice_time() / 60
        return total_hours >= required_hours

def main():
    tracker = AikidoTracker()

    while True:
        print("===== Aikido Practice Tracker =====")
        print("1. Add Practice Session")
        print("2. View Total Practice Time")
        print("3. Check Graduation Eligibility")
        print("4. Exit")
        choice = input("Choose an option: ")

        if choice == "1":
            date = input("Enter the date (YYYY-MM-DD): ")
            duration = int(input("Enter the duration in minutes: "))
            tracker.add_session(date, duration)
            print("Session added!\n")
        elif choice == "2":
            print(f"Total Practice Time: {tracker.get_total_practice_time()} minutes\n")
        elif choice == "3":
            eligible = tracker.check_graduation_eligibility()
            print("Eligible for Kyu graduation!\n" if eligible else "Not yet eligible for Kyu graduation.\n")
        elif choice == "4":
            break
        else:
            print("Invalid option. Please choose again.\n")

if __name__ == "__main__":
    main()
    