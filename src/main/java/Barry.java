import java.util.ArrayList;
import java.util.Scanner;

public class Barry {
    private boolean alive;
    private ArrayList<String> tasks;
    
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

        this.action("addtask", input);
    }

    private void action(String command, String data) {
        if (command.equals("addtask")) {
            this.tasks.add(data);
            this.speak("task added: " + data, true);
            return;
        }
        
        if (command.equals("list")) {
            int N = this.tasks.size();
            for (int i = 0; i < N; i++) {
                this.speak((i + 1) + ": " + this.tasks.get(i), i == N - 1);
            }
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
