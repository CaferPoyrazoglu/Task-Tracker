package repository;

import model.Task;

import java.util.List;

public interface TaskRepository {
    List<Task> loadTasks();

    void saveTasks(List<Task> tasks);
}
