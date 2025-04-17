# ToDoist – Console Task Manager

ToDoist is a simple yet powerful command-line task manager built with Python. It allows you to create, list, complete, delete, and sort tasks. Tasks are stored in JSON files, organized into separate lists.

## Features

- Add and manage tasks with due dates and priorities
- Support for multiple task lists (stored as `.json` files)
- Sort tasks by priority, due date, or creation time
- Mark tasks as completed
- Delete tasks by number
- View only incomplete tasks by default, or show all with `--showall`

## Usage

### Add a new task

```bash
python main.py --add "Buy milk" --desc "From the supermarket" --due 2025-04-20_18:00 --priority 2 --listname shopping
```

### List tasks

```bash
python main.py --list --listname shopping
```

### Mark a task as completed

```bash
python main.py --complete 1 --listname shopping
```

### Delete a task

```bash
python main.py --delete 2 --listname shopping
```

### Sort tasks

You can sort tasks using `--filter`:

```bash
python main.py --list --filter=priority --listname shopping
```

Available filters:
- priority
- due
- created

### Show tasks from all lists

```bash
python main.py --all
```

### Show completed tasks too

By default, only incomplete tasks are shown. Use `--showall` to include completed ones:

```bash
python main.py --list --showall --listname shopping
```

## Data Storage

Tasks are saved in JSON files, one per list. For example:
- shopping.json
- work.json
- personal.json


Built in Python.