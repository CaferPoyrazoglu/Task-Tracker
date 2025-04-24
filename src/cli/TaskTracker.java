package cli;

import model.Task;
import model.TaskStatus;
import repository.TaskRepository;
import repository.TaskRepositoryImpl;
import service.TaskService;
import service.TaskServiceImpl;

import java.util.List;

public class TaskTracker {
    public static void main(String[] args) {
        TaskRepository taskRepository = new TaskRepositoryImpl();
        TaskService taskService = new TaskServiceImpl(taskRepository);

        if (args.length == 0) {
            System.out.println("Usage: task-cli <command> [arguments]");
            return;
        }
        String command = args[0];

        switch (command) {
            case "add":
                if (args.length < 2) {
                    System.out.println("Usage: task-cli add <description>");
                }
                String description = args[1];
                taskService.addTask(description);
                break;

            case "update":
                if (args.length < 3) {
                    System.out.println("Usage: task-cli update <id> <description>");
                }
                int updateId = Integer.parseInt(args[1]);
                String newDescription = args[2];
                taskService.updateTask(updateId, newDescription);
                break;
            case "delete":
                if (args.length < 2) {
                    System.out.println("Usage: task-cli delete <id>");
                }
                int deleteId = Integer.parseInt(args[1]);
                taskService.deleteTask(deleteId);
                break;
            case "mark-in-progress":
                if (args.length < 2) {
                    System.out.println("Usage task-cli mark-in-progress <id>");
                }
                int inProgressId = Integer.parseInt(args[1]);
                taskService.markInProgressTask(inProgressId);
                break;
            case "mark-done":
                if (args.length < 2) {
                    System.out.println("Usage task-cli mark-done <id>");
                }
                int doneId = Integer.parseInt(args[1]);
                taskService.markDoneTask(doneId);
                break;
            case "list":
                if (args.length == 1) {
                    List<Task> allTasks = taskService.listAllTasks();
                    taskService.printAllTasks(allTasks);
                } else if (args.length == 2) {
                    String statusArg = args[1].toUpperCase().replace("-", "_");
                    try {
                        TaskStatus status = TaskStatus.valueOf(statusArg);
                        List<Task> filteredTasks = taskService.listTaskByStatus(status);
                        taskService.printAllTasks(filteredTasks);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid status. Available statuses: TODO, IN_PROGRESS, DONE");
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Usage: task-cli list [status]");
                }
                break;
            default:
                System.out.println("Invalid command. Available commands: add, update, delete, mark-in-progress, mark-done, list");
                break;
        }
    }
}