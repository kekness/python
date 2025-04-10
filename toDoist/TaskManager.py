import json
from Task import Task

class TaskManager:
    def __init__(self):
        self.tasks = []

    def add_task(self, task):
        self.tasks.append(task)

    def save_to_file(self,filename="tasks.json"):
        with open(filename, 'w', encoding='utf-8') as f:
            json.dump([task.to_dict() for task in self.tasks], f, ensure_ascii=False, indent=4)

    def load_from_file(self,filename="tasks.json"):
        try:
            with open(filename, 'r', encoding='utf-8') as f:
                data=json.load(f)
                self.tasks=[Task.from_dict(item) for item in data]
        except FileNotFoundError:
            print("File not found")
            self.tasks = []

    def read_tasks(self):
        for i, task in enumerate(self.tasks, 1):
            print(f"{i}. {task}")
