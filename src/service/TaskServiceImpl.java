package service;

import model.Task;
import model.TaskStatus;
import repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskServiceImpl implements TaskService {

    private List<Task> tasks = new ArrayList<>();
    private int nextId = 1;
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        super();
        this.taskRepository = taskRepository;
        this.tasks = taskRepository.loadTasks();
        this.nextId = tasks.isEmpty() ? 1 : tasks.get(tasks.size() - 1).getId() + 1;
    }

    @Override
    public void addTask(String description) {
        Task task = new Task(nextId, description, TaskStatus.TODO);
        tasks.add(task);
        taskRepository.saveTasks(tasks);
        System.out.println("Task added successfully (ID: " + nextId + ")");
    }

    @Override
    public void updateTask(int id, String descripion) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                task.setDescription(descripion);
                taskRepository.saveTasks(tasks);
                System.out.println("Task added successfully (ID: " + id + ")");
                return;
            }
        }
        System.out.println("Task with ID " + id + " not found.");
    }

    @Override
    public void deleteTask(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                tasks.remove(task);
                taskRepository.saveTasks(tasks);
                System.out.println("Task deleted successfully (ID: " + id + ")");
                return;
            }
        }
        System.out.println("Task with ID " + id + " not found.");

    }

    @Override
    public void markInProgressTask(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                task.setStatus(TaskStatus.IN_PROGRESS);
                taskRepository.saveTasks(tasks);
                System.out.println("Task marked as IN_PROGRESS successfully (ID: " + id + ")");
                return;
            }
        }
        System.out.println("Task with ID " + id + " not found.");

    }

    @Override
    public void markDoneTask(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                task.setStatus(TaskStatus.DONE);
                taskRepository.saveTasks(tasks);
                System.out.println("Task marked as DONE successfully (ID: " + id + ")");
                return;
            }
        }
        System.out.println("Task with ID " + id + " not found.");

    }

    @Override
    public List<Task> listAllTasks() {
        return tasks;
    }

    @Override
    public List<Task> listTaskByStatus(TaskStatus status) {
        return tasks.stream().filter(task -> task.getStatus() == status).collect(Collectors.toList());
    }

    @Override
    public void printAllTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("No Tasks Found");
        } else {
            for (Task task : tasks) {
                System.out.println(task);
            }
        }

    }

}