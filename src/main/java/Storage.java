import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

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
