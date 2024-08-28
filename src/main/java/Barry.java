import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Barry {
    private boolean alive;
    private ArrayList<Task> tasks;

    // Base Task class
    private abstract class Task {
        protected boolean completed;
        protected String description;

        public Task(String description) {
            this.description = description;
            this.completed = false;
        }

        public void mark() {
            this.completed = true;
        }

        public void unmark() {
            this.completed = false;
        }

        @Override
        public String toString() {
            String output = (this.completed ? "[X] " : "[ ] ");
            return output + this.description;
        }
    }

    // TodoTask subclass
    private class TodoTask extends Task {
        public TodoTask(String description) {
            super(description);
        }

        @Override
        public String toString() {
            return "[T]" + super.toString();
        }
    }

    // DeadlineTask subclass
    private class DeadlineTask extends Task {
        private String by;

        public DeadlineTask(String description, String by) {
            super(description);
            this.by = by;
        }

        @Override
        public String toString() {
            return "[D]" + super.toString() + " (by: " + this.by + ")";
        }
    }

    // EventTask subclass
    private class EventTask extends Task {
        private String from;
        private String to;

        public EventTask(String description, String from, String to) {
            super(description);
            this.from = from;
            this.to = to;
        }

        @Override
        public String toString() {
            return "[E]" + super.toString() + " (from: " + this.from + " to: " + this.to + ")";
        }
    }

    public Barry() {
        this.alive = false;
        this.tasks = new ArrayList<>();
        loadTasksFromFile(); // Load tasks from file on startup
    }

    public void startup() {
        this.alive = true;
        System.out.println("Hello! I'm Barry.\nWhat can I do for you?\n");
    }

    private void shutdown() {
        this.alive = false;
        System.out.println("Bye. See you soon!");
    }

    private boolean isRunning() {
        return this.alive;
    }

    private void speak(String output, boolean newline) {
        if (newline) output += "\n";
        System.out.println(output);
    }

    private void parseInput(String input) {
        try {
            if (input.equals("bye")) {
                this.shutdown();
                return;
            }

            if (input.equals("list")) {
                this.action("list", "");
                return;
            }

            if (input.startsWith("mark")) {
                if (input.length() < 6) throw new BarryException("OOPS!!! The description of a todo cannot be empty.");
                this.action("mark", input.substring(5));
                return;
            }

            if (input.startsWith("unmark")) {
                if (input.length() < 8) throw new BarryException("OOPS!!! The description of a todo cannot be empty.");
                this.action("unmark", input.substring(7));
                return;
            }

            if (input.startsWith("todo")) {
                if (input.length() < 6) throw new BarryException("OOPS!!! The description of a todo cannot be empty.");
                this.action("addTodo", input.substring(5).trim());
                return;
            }

            if (input.startsWith("deadline")) {
                String[] parts = input.substring(9).split(" /by ");
                if (parts.length < 2) throw new BarryException("OOPS!!! The deadline format is incorrect.");
                this.action("addDeadline", parts[0].trim() + "|" + parts[1].trim());
                return;
            }

            if (input.startsWith("event")) {
                String[] parts = input.substring(6).split(" /from ");
                if (parts.length < 2) throw new BarryException("OOPS!!! The event format is incorrect.");
                String[] timeParts = parts[1].split(" /to ");
                if (timeParts.length < 2) throw new BarryException("OOPS!!! The event time format is incorrect.");
                this.action("addEvent", parts[0].trim() + "|" + timeParts[0].trim() + "|" + timeParts[1].trim());
                return;
            }

            if (input.startsWith("delete")) {
                if (input.length() < 8) throw new BarryException("OOPS!!! The task number to delete cannot be empty.");
                this.action("delete", input.substring(7).trim());
                return;
            }

            throw new BarryException("OOPS!!! I'm sorry, but I don't know what that means :-(");
        } catch (BarryException e) {
            this.speak(e.getMessage(), true);
        }
    }

    private void action(String command, String data) throws BarryException {
        if (command.equals("addTodo")) {
            this.tasks.add(new TodoTask(data));
            this.speak("Got it. I've added this task:\n" + new TodoTask(data), true);
            saveTasksToFile(); // Save after adding a task
            return;
        }

        if (command.equals("addDeadline")) {
            String[] parts = data.split("\\|");
            this.tasks.add(new DeadlineTask(parts[0], parts[1]));
            this.speak("Got it. I've added this task:\n" + new DeadlineTask(parts[0], parts[1]), true);
            saveTasksToFile(); // Save after adding a task
            return;
        }

        if (command.equals("addEvent")) {
            String[] parts = data.split("\\|");
            this.tasks.add(new EventTask(parts[0], parts[1], parts[2]));
            this.speak("Got it. I've added this task:\n" + new EventTask(parts[0], parts[1], parts[2]), true);
            saveTasksToFile(); // Save after adding a task
            return;
        }

        if (command.equals("mark")) {
            int idx = Integer.parseInt(data) - 1;
            if (idx < 0 || idx >= this.tasks.size()) throw new BarryException("OOPS!!! Task number is out of range.");
            this.tasks.get(idx).mark();
            this.speak("I've marked this task as done:", false);
            this.speak(this.tasks.get(idx).toString(), true);
            saveTasksToFile(); // Save after marking a task
            return;
        }

        if (command.equals("unmark")) {
            int idx = Integer.parseInt(data) - 1;
            if (idx < 0 || idx >= this.tasks.size()) throw new BarryException("OOPS!!! Task number is out of range.");
            this.tasks.get(idx).unmark();
            this.speak("I've unmarked this task:", false);
            this.speak(this.tasks.get(idx).toString(), true);
            saveTasksToFile(); // Save after unmarking a task
            return;
        }

        if (command.equals("list")) {
            if (this.tasks.isEmpty()) {
                this.speak("There are no tasks in your list.", true);
            } else {
                int N = this.tasks.size();
                for (int i = 0; i < N; i++) {
                    this.speak((i + 1) + ". " + this.tasks.get(i).toString(), i == N - 1);
                }
            }
            return;
        }

        if (command.equals("delete")) {
            int idx = Integer.parseInt(data) - 1;
            if (idx < 0 || idx >= this.tasks.size()) throw new BarryException("OOPS!!! Task number is out of range.");
            Task removedTask = this.tasks.remove(idx);
            this.speak("Noted. I've removed this task:\n" + removedTask, true);
            this.speak("Now you have " + this.tasks.size() + " tasks in the list.", true);
            saveTasksToFile(); // Save after deleting a task
            return;
        }
    }

    private void saveTasksToFile() {
        ensureDataDirectoryExists(); // Ensure directory exists
        try (PrintWriter writer = new PrintWriter(new FileWriter("./data/barry.txt"))) {
            for (Task task : tasks) {
                if (task instanceof TodoTask) {
                    writer.println("T | " + (task.completed ? "1" : "0") + " | " + task.description);
                } else if (task instanceof DeadlineTask) {
                    DeadlineTask deadlineTask = (DeadlineTask) task;
                    writer.println("D | " + (task.completed ? "1" : "0") + " | " + task.description + " | " + deadlineTask.by);
                } else if (task instanceof EventTask) {
                    EventTask eventTask = (EventTask) task;
                    writer.println("E | " + (task.completed ? "1" : "0") + " | " + task.description + " | " + eventTask.from + " | " + eventTask.to);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while saving tasks to file: " + e.getMessage());
        }
    }

    private void loadTasksFromFile() {
        File file = new File("./data/barry.txt");
        if (!file.exists()) {
            return; // No file to load from
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(" \\| ");
                String type = parts[0];
                boolean isCompleted = parts[1].equals("1");
                String description = parts[2];

                Task task;
                if (type.equals("T")) {
                    task = new TodoTask(description);
                } else if (type.equals("D")) {
                    String by = parts[3];
                    task = new DeadlineTask(description, by);
                } else if (type.equals("E")) {
                    String from = parts[3];
                    String to = parts[4];
                    task = new EventTask(description, from, to);
                } else {
                    continue;
                }

                if (isCompleted) {
                    task.mark();
                }

                tasks.add(task);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while loading tasks from file: " + e.getMessage());
        }
    }

    private void ensureDataDirectoryExists() {
        File directory = new File("./data");
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Barry barry = new Barry();

        barry.startup();

        while (barry.isRunning()) {
            String input = s.nextLine();
            barry.parseInput(input);
        }
        s.close();
    }

    // Exception class for Barry
    private class BarryException extends Exception {
        public BarryException(String message) {
            super(message);
        }
    }
}
