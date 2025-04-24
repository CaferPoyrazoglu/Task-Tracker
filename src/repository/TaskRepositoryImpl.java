package repository;

import model.Task;
import util.JsonUtil;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TaskRepositoryImpl implements TaskRepository {

    private static final String FILE_PATH = "tasks.json";

    @Override
    public List<Task> loadTasks() {
        List<Task> tasks = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            System.out.println("File does not exist. Creating...");
            return tasks;
        }

        try (FileReader reader = new FileReader(file)) {
            StringBuilder jsonString = new StringBuilder();
            int character;
            while ((character = reader.read()) != -1) {
                jsonString.append((char) character);
            }
            tasks = JsonUtil.parseJsonToTasks(jsonString.toString());
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return tasks;
    }

    @Override
    public void saveTasks(List<Task> tasks) {
        String jsonString = JsonUtil.convertTaskToJson(tasks);
        File file = new File(FILE_PATH);

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(jsonString);
        } catch (IOException e) {
            System.err.println("Error saving tasks: " + e.getMessage());
        }
    }
}
