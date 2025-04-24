package util;

import model.Task;
import model.TaskStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class JsonUtil {
    public static List<Task> parseJsonToTasks(String jsonString) {
        List<Task> tasks = new ArrayList<>();
        jsonString = jsonString.trim();

        if (jsonString.startsWith("[") && jsonString.endsWith("]")) {
            jsonString = jsonString.substring(1, jsonString.length() - 1).trim();

            String[] taskStrings = jsonString.split("(?<=\\})\\s*,\\s*(?=\\{)");

            for (String taskString : taskStrings) {
                taskString = taskString.trim();
                if (taskString.isEmpty()) continue;

                Task task = new Task();
                taskString = taskString.substring(1, taskString.length() - 1).trim();
                String[] keyValuePairs = taskString.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                for (String pair : keyValuePairs) {
                    pair = pair.trim();
                    String[] keyValue = pair.split(":", 2);
                    if (keyValue.length != 2) continue;

                    String key = keyValue[0].trim().replaceAll("\"", "");
                    String value = keyValue[1].trim().replaceAll("\"", "");

                    try {
                        switch (key) {
                            case "id":
                                task.setId(Integer.parseInt(value));
                                break;
                            case "description":
                                task.setDescription(value);
                                break;
                            case "status":
                                task.setStatus(TaskStatus.valueOf(value));
                                break;
                            case "createdAt":
                                if (!value.equals("null")) {
                                    task.setCreatedAt(LocalDateTime.parse(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                                }
                                break;
                            case "updatedAt":
                                if (!value.equals("null")) {
                                    task.setUpdatedAt(LocalDateTime.parse(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                                }
                                break;
                            default:
                                throw new IllegalArgumentException("Unexpected value: " + key);
                        }
                    } catch (DateTimeParseException e) {
                        System.err.println("Error parsing date-time for key '" + key + "': " + value);
                        continue;
                    }
                }
                tasks.add(task);
            }
        }
        return tasks;
    }

    public static String convertTaskToJson(List<Task> tasks) {
        StringBuilder jsonBuilder = new StringBuilder("[");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            jsonBuilder.append("{")
                    .append("\"id\":").append(task.getId()).append(",")
                    .append("\"description\":\"").append(task.getDescription()).append("\",")
                    .append("\"status\":\"").append(task.getStatus().toString()).append("\",")
                    .append("\"createdAt\":\"")
                    .append(task.getCreatedAt() != null ? task.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) : "null")
                    .append("\",")
                    .append("\"updatedAt\":\"")
                    .append(task.getUpdatedAt() != null ? task.getUpdatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) : "null")
                    .append("\"")
                    .append("}");
            if (i < tasks.size() - 1) {
                jsonBuilder.append(",");
            }
        }
        jsonBuilder.append("]");
        String jsonString = jsonBuilder.toString();
        return jsonString;
    }
}
