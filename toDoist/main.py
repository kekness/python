from datetime import datetime
from TaskManager import TaskManager
from Task import Task
import argparse

manager =TaskManager()
manager.load_from_file()

parser = argparse.ArgumentParser(description="Welcome to ToDoist")

parser.add_argument("--add", nargs="+", metavar="TITLE", help="Add new task")
parser.add_argument("--desc", metavar="DESCRIPTION", help = "Add description")
parser.add_argument("--due", metavar="YYYY-MM-DD_HH:MM", help ="Set due date")
parser.add_argument("--priority",metavar="INT", help= "set priority")
parser.add_argument("--list", action="store_true",help="Show all tasks")
parser.add_argument("--complete", metavar="NUMBER",type=int,help="Finish task")
parser.add_argument("--filter", metavar="BY", choices=["priority", "due", "created"], help="Sort tasks by: priority, due, created")

args = parser.parse_args()

if args.add:
    title = " ".join(args.add)
    description = args.desc or ""
    completed = False
    priority = 1
    if args.priority:
        try:
            priority = int(args.priority)
        except ValueError:
            print("Priority must be an integer.")
            exit()
    due_date = None

    if args.due:
        try:
            due_date = datetime.strptime(args.due, "%Y-%m-%d_%H:%M")
        except ValueError:
            print("Wrong date format. Use YYYY-MM-DD_HH:MM")
            exit()

    new_task = Task(title, description, due_date,completed,priority)
    manager.add_task(new_task)
    manager.save_to_file()
    print(f"Task submitted: {title}")

elif args.complete:
    index = args.complete - 1
    if 0 <= index < len(manager.tasks):
        manager.tasks[index].complete()
        manager.save_to_file()
        print(f"Task {args.complete} marked as finished.")
    else:
        print(f"No such task was found")

elif args.list:
    indexed_tasks = list(enumerate(manager.tasks))  # (index, task)

    if args.filter:
        if args.filter == "priority":
            indexed_tasks.sort(key=lambda x: x[1].priority)
        elif args.filter == "due":
            indexed_tasks.sort(key=lambda x: x[1].due_date or datetime.max)
        elif args.filter == "created":
            indexed_tasks.sort(key=lambda x: x[1].created_at)

    for i, (real_index, task) in enumerate(indexed_tasks, 1):
        print(f"{real_index + 1}. {task}")


else:
    parser.print_help()