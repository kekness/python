from datetime import datetime
from TaskManager import TaskManager
from Task import Task
import argparse
import os

parser = argparse.ArgumentParser(description="Welcome to ToDoist")

parser.add_argument("--add", nargs="+", metavar="TITLE", help="Add new task")
parser.add_argument("--desc", metavar="DESCRIPTION", help="Add description")
parser.add_argument("--due", metavar="YYYY-MM-DD_HH:MM", help="Set due date")
parser.add_argument("--priority", metavar="INT", help="Set priority")
parser.add_argument("--list", action="store_true", help="Show tasks from a specific list")
parser.add_argument("--complete", metavar="NUMBER", type=int, help="Finish task")
parser.add_argument("--filter", metavar="BY", choices=["priority", "due", "created"], help="Sort tasks by: priority, due, created")
parser.add_argument("--listname", metavar="NAME", default="tasks", help="Choose task list (default: 'tasks')")
parser.add_argument("--all", action="store_true", help="Show tasks from all lists but only uncompleted ones")
parser.add_argument("--showall", action="store_true", help="Show all tasks, including completed ones")
parser.add_argument("--delete", metavar="NUMBER", type=int, help="Delete a task by its number")


args = parser.parse_args()

if args.all:
    print("All tasks from all lists:\n")
    for file in glob.glob("*.json"):
        listname = file.replace(".json", "")
        manager = TaskManager()
        manager.load_from_file(file)
        if not manager.tasks:
            continue
        print(f"=== {listname} ===")
        indexed_tasks = list(enumerate(manager.tasks))
        if args.filter:
            if args.filter == "priority":
                indexed_tasks.sort(key=lambda x: x[1].priority)
            elif args.filter == "due":
                indexed_tasks.sort(key=lambda x: x[1].due_date or datetime.max)
            elif args.filter == "created":
                indexed_tasks.sort(key=lambda x: x[1].created_at)
        for i, (real_index, task) in enumerate(indexed_tasks, 1):
            if args.showall or not task.completed:
                 print(f"{real_index + 1}. {task}")
        print()
    exit()

filename = f"{args.listname}.json"
manager = TaskManager()
manager.load_from_file(filename)

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

    new_task = Task(title, description, due_date, completed, priority)
    manager.add_task(new_task)
    manager.save_to_file(filename)
    print(f"Task submitted to '{args.listname}': {title}")

elif args.delete:
    index = args.delete - 1
    if 0 <= index < len(manager.tasks):
        deleted_task = manager.tasks.pop(index)
        manager.save_to_file(filename)
        print(f"Deleted task {args.delete} from '{args.listname}': {deleted_task.title}")
    else:
        print(f"No such task to delete in '{args.listname}'.")

elif args.complete:
    index = args.complete - 1
    if 0 <= index < len(manager.tasks):
        manager.tasks[index].complete()
        manager.save_to_file(filename)
        print(f"Task {args.complete} marked as finished in '{args.listname}'.")
    else:
        print(f"No such task was found in '{args.listname}'.")

elif args.list:
    indexed_tasks = list(enumerate(manager.tasks))
    if args.filter:
        if args.filter == "priority":
            indexed_tasks.sort(key=lambda x: x[1].priority)
        elif args.filter == "due":
            indexed_tasks.sort(key=lambda x: x[1].due_date or datetime.max)
        elif args.filter == "created":
            indexed_tasks.sort(key=lambda x: x[1].created_at)

    print(f" List: {args.listname}")
    for i, (real_index, task) in enumerate(indexed_tasks, 1):
        if args.showall or not task.completed:
             print(f"{real_index + 1}. {task}")

else:
    parser.print_help()
