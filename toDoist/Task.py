from datetime import datetime
class Task:
    def __int__(self, title, description="", due_date=None, completed=False, priority=1):
        self.title = title
        self.description = description
        self.due_date = due_date
        self.completed = completed
        self.created_at = datetime.now()

    def complete(self):
        self.completed = True

    def incomplete(self):
        self.completed = False

    def __str__(self):
        status = "✓" if self.completed else "✗"
        due=self.due_date.strftime("%Y-%m-%d %H:%M") if self.due_date else "No due date"
        return f"[{status}] {self.title} (Due: {due})"



