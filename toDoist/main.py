from datetime import datetime
from TaskManager import TaskManager
from Task import Task

manager =TaskManager()
manager.load_from_file()

new_task = Task("Kup Mleko,","jestem laktozowo tolerancyjny",datetime(2025,4,11,17,0))
manager.add_task(new_task)

manager.save_to_file()
manager.read_tasks()