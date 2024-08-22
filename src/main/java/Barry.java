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
        }

        public void mark() {
            this.completed = true;
        }

        public void unmark() {
            this.completed = false;
        }

        public boolean isDone() {
            return this.completed;
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
        if (input.equals("bye")) {
            this.shutdown();
            return;
        }

        if (input.equals("list")) {
            this.action("list", "");
            return;
        }

        if (input.startsWith("mark")) {
            this.action("mark", input.substring(5));
            return;
        }

        if (input.startsWith("unmark")) {
            this.action("unmark", input.substring(7));
            return;
        }

        if (input.startsWith("todo")) {
            this.action("addTodo", input.substring(5));
            return;
        }

        if (input.startsWith("deadline")) {
            String[] parts = input.substring(9).split(" /by ");
            this.action("addDeadline", parts[0] + "|" + parts[1]);
            return;
        }

        if (input.startsWith("event")) {
            String[] parts = input.substring(6).split(" /from ");
            String[] timeParts = parts[1].split(" /to ");
            this.action("addEvent", parts[0] + "|" + timeParts[0] + "|" + timeParts[1]);
            return;
        }

        this.action("addtask", input);
    }

    private void action(String command, String data) {
        if (command.equals("addTodo")) {
            this.tasks.add(new TodoTask(data));
            this.speak("Got it. I've added this task:\n" + new TodoTask(data), true);
            return;
        }

        if (command.equals("addDeadline")) {
            String[] parts = data.split("\\|");
            this.tasks.add(new DeadlineTask(parts[0], parts[1]));
            this.speak("Got it. I've added this task:\n" + new DeadlineTask(parts[0], parts[1]), true);
            return;
        }

        if (command.equals("addEvent")) {
            String[] parts = data.split("\\|");
            this.tasks.add(new EventTask(parts[0], parts[1], parts[2]));
            this.speak("Got it. I've added this task:\n" + new EventTask(parts[0], parts[1], parts[2]), true);
            return;
        }

        if (command.equals("mark")) {
            int idx = Integer.parseInt(data) - 1;
            this.tasks.get(idx).mark();
            this.speak("I've marked this task as done:", false);
            this.speak(this.tasks.get(idx).toString(), true);
            return;
        }
        
        if (command.equals("unmark")) {
            int idx = Integer.parseInt(data) - 1;
            this.tasks.get(idx).unmark();
            this.speak("I've unmarked this task:", false);
            this.speak(this.tasks.get(idx).toString(), true);
            return;
        }

        if (command.equals("list")) {
            int N = this.tasks.size();
            for (int i = 0; i < N; i++) {
                this.speak((i + 1) + ". " + this.tasks.get(i).toString(), i == N - 1);
            }
            return;
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
    }
}
