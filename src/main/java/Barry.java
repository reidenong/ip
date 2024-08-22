import java.util.Scanner;

public class Barry {
    private boolean alive;
    
    public Barry() {
        this.alive = false;
    }

    public void startup() {
        this.alive = true;
        System.out.println("Hello! I'm Barry.\nWhat can I do for you?\n");
    }

    private void parseInput(String input) {
        if (input.equals("bye")) {
            this.shutdown();
            return;
        }

        this.speak(input);      // Echo
    }

    private void action(String command, String data) {

    }

    private void speak(String output) {
        System.out.println(output + "\n");

    }

    private void shutdown() {
        this.alive = false;
        System.out.println("Bye. See you soon!");
    }

    private boolean isRunning() {
        return this.alive;
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
