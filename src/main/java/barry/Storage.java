package barry;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The Storage class is responsible for reading and writing tasks to and from a file.
 * It provides methods to load tasks from a specified file and to save tasks back to the file.
 */
public class Storage {
    private String filePath;

    /**
     * Constructs a Storage object with the specified file path for storing tasks.
     *
     * @param filePath The path to the file where tasks are stored and loaded from.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the file specified by the file path.
     *
     * @return An ArrayList of Task objects loaded from the file.
     * @throws FileNotFoundException If the specified file cannot be found.
     */
    public ArrayList<Task> load() throws FileNotFoundException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            return tasks;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(" \\| ");
                String type = parts[0];
                boolean isCompleted = parts[1].equals("1");
                String description = parts[2];
                Task task;

                if (type.equals("T")) {
                    task = new Task.TodoTask(description);
                } else if (type.equals("D")) {
                    LocalDateTime by = LocalDateTime.parse(parts[3]);
                    task = new Task.DeadlineTask(description, by);
                } else if (type.equals("E")) {
                    LocalDateTime from = LocalDateTime.parse(parts[3]);
                    LocalDateTime to = LocalDateTime.parse(parts[4]);
                    task = new Task.EventTask(description, from, to);
                } else {
                    continue;
                }

                if (isCompleted) {
                    task.mark();
                }

                tasks.add(task);
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File not found.");
        }

        return tasks;
    }

    /**
     * Saves the given list of tasks to the file specified by the file path.
     *
     * @param tasks The list of tasks to be saved to the file.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    public void save(ArrayList<Task> tasks) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Task task : tasks) {
                if (task instanceof Task.TodoTask) {
                    writer.println("T | " + (task.isCompleted() ? "1" : "0") + " | " + task.getDescription());
                } else if (task instanceof Task.DeadlineTask) {
                    Task.DeadlineTask deadlineTask = (Task.DeadlineTask) task;
                    writer.println("D | " + (task.isCompleted() ? "1" : "0") + " | " + task.getDescription() + " | " + deadlineTask.getBy());
                } else if (task instanceof Task.EventTask) {
                    Task.EventTask eventTask = (Task.EventTask) task;
                    writer.println("E | " + (task.isCompleted() ? "1" : "0") + " | " + task.getDescription() + " | " + eventTask.getFrom() + " | " + eventTask.getTo());
                }
            }
        }
    }
}
