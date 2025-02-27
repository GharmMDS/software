import unittest
from aikido import AikidoTracker
import datetime

class TestAikidoTracker(unittest.TestCase):
    def setUp(self):
        self.tracker = AikidoTracker()

    def test_add_session(self):
        self.tracker.add_session("2024-02-01", 90)
        self.assertEqual(len(self.tracker.sessions), 1)
        self.assertEqual(self.tracker.sessions[0], (datetime.date(2024, 2, 1), 90))

    def test_total_practice_time(self):
        self.tracker.add_session("2024-02-01", 60)
        self.tracker.add_session("2024-02-02", 120)
        self.assertEqual(self.tracker.get_total_practice_time(), 180)

    def test_graduation_eligibility(self):
        for _ in range(60):
            self.tracker.add_session("2024-02-01", 100)
        self.assertTrue(self.tracker.check_graduation_eligibility())

    def test_not_eligible_for_graduation(self):
        self.tracker.add_session("2024-02-01", 90)  # 90 minutes = 1.5 hours
        self.assertFalse(self.tracker.check_graduation_eligibility())

if __name__ == "__main__":
    unittest.main()
