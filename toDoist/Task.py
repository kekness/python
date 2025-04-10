from datetime import datetime
class Task:
    def __init__(self, title, description="", due_date=None, completed=False, priority=1):
        self.title = title
        self.description = description
        self.due_date = due_date
        self.completed = completed
        self.created_at = datetime.now()
        self.priority = priority

    def complete(self):
        self.completed = True

    def incomplete(self):
        self.completed = False

    def to_dict(self):
        return {
            "title": self.title,
            "description": self.description,
            "due_date": self.due_date.isoformat() if self.due_date else None,
            "completed": self.completed,
            "priority": self.priority,
            "created_at": self.created_at.isoformat()
        }

    @staticmethod
    def from_dict(data):
        return Task(
            title=data.get("title", ""),
            description=data.get("description", ""),
            due_date=datetime.fromisoformat(data["due_date"]) if data.get("due_date") else None,
            completed=data.get("completed", False),
            priority=data.get("priority", 1)
        )

    def __str__(self):
        status = "✓" if self.completed else "✗"
        due=self.due_date.strftime("%Y-%m-%d %H:%M") if self.due_date else "No due date"
        prior = self.priority
        return f"[{status}] {self.title} (Due: {due}) P: {prior}"





