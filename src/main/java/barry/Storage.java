package barry;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The Storage class manages reading and writing of tasks to a file.
 * It provides functionality to load tasks from the file and save them back.
 * Tasks are stored as text in the format that includes task type, completion
 * status,
 * and relevant task details.
 */
public class Storage {
    private String filePath;

    /**
     * Constructs a Storage object with the specified file path for reading and
     * writing tasks.
     *
     * @param filePath The path to the file where tasks will be saved and loaded
     *                 from.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the file. If the file or directory doesn't exist, it will
     * create them.
     * If the file is corrupted, it will attempt to delete the corrupted file and
     * create a new one.
     *
     * @return An ArrayList of Task objects loaded from the file. If the file is
     *         empty or not found,
     *         an empty list will be returned.
     * @throws FileNotFoundException If the file specified by the filePath cannot be
     *                               found.
     */
    public ArrayList<Task> load() throws FileNotFoundException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);
        File directory = file.getParentFile(); // Parent directory of the file

        // Create parent directory if it does not exist
        if (directory != null && !directory.exists()) {
            if (directory.mkdirs()) {
                System.err.println("Directory created: " + directory.getAbsolutePath());
            } else {
                System.err.println("Failed to create directory: " + directory.getAbsolutePath());
            }
        }

        // Create a new file if it doesn't exist and return an empty task list
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.err.println("New file created: " + filePath);
                }
            } catch (IOException e) {
                System.err.println("Failed to create new file: " + e.getMessage());
                return tasks;
            }
            return tasks; // Return an empty list as no tasks exist in a new file
        }

        // File exists, load tasks from it
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(" \\| ");
                String type = parts[0];
                boolean isCompleted = parts[1].equals("1");
                String description = parts[2];
                Task task;

                // Load a ToDo task
                if (type.equals("T")) {
                    task = new Task.TodoTask(description);

                    // Load a Deadline task
                } else if (type.equals("D")) {
                    LocalDateTime by = LocalDateTime.parse(parts[3]);
                    task = new Task.DeadlineTask(description, by);

                    // Load an Event task
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
        } catch (ArrayIndexOutOfBoundsException | IOException e) {
            // Handle file corruption or invalid content by deleting and recreating the file
            System.err.println("Error loading tasks from file: " + e.getMessage());
            if (file.delete()) {
                System.err.println("Corrupted file deleted: " + filePath);
            } else {
                System.err.println("Failed to delete the corrupted file: " + filePath);
            }

            try {
                if (file.createNewFile()) {
                    System.err.println("New empty file created: " + filePath);
                }
            } catch (IOException ioException) {
                System.err.println("Failed to create new file: " + ioException.getMessage());
            }
        }

        return tasks;
    }

    /**
     * Saves the current list of tasks to the file. It overwrites the file's
     * contents
     * with the serialized format of each task.
     *
     * @param tasks The list of tasks to be saved to the file.
     * @throws IOException If an I/O error occurs during writing.
     */
    public void save(ArrayList<Task> tasks) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Task task : tasks) {
                if (task instanceof Task.TodoTask) {
                    writer.println("T | " + (task.isCompleted() ? "1" : "0") + " | " + task.getDescription());
                } else if (task instanceof Task.DeadlineTask) {
                    Task.DeadlineTask deadlineTask = (Task.DeadlineTask) task;
                    writer.println("D | " + (task.isCompleted() ? "1" : "0") + " | " + task.getDescription() + " | "
                            + deadlineTask.getBy());
                } else if (task instanceof Task.EventTask) {
                    Task.EventTask eventTask = (Task.EventTask) task;
                    writer.println("E | " + (task.isCompleted() ? "1" : "0") + " | " + task.getDescription() + " | "
                            + eventTask.getFrom() + " | " + eventTask.getTo());
                }
            }
        }
    }
}
