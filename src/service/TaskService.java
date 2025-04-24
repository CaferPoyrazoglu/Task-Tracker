package service;

import model.Task;
import model.TaskStatus;

import java.util.List;

public interface TaskService {
    void addTask(String description);

    void updateTask(int id, String description);

    void deleteTask(int id);

    void markInProgressTask(int id);

    void markDoneTask(int id);

    List<Task> listAllTasks();

    List<Task> listTaskByStatus(TaskStatus status);

    void printAllTasks(List<Task> tasks);
}
