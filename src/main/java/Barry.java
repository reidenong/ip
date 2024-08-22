import java.util.ArrayList;
import java.util.Scanner;

public class Barry {
    private boolean alive;
    private ArrayList<Task> tasks;

    private class Task {
        private boolean completed;
        private String description;

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

        this.action("addtask", input);
    }

    private void action(String command, String data) {
        if (command.equals("addtask")) {
            this.tasks.add(new Task(data));
            this.speak("task added: " + data, true);
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
