package barry;
import java.util.Scanner;

/**
 * The Ui class manages the User Command line interface that the user uses to communicate with Barry.
 */
public class Ui {
    private Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showWelcomeMessage() {
        System.out.println("Hello! I'm Barry.\nWhat can I do for you?\n");
    }

    public void showGoodbyeMessage() {
        System.out.println("Bye. See you soon!\n");
    }

    public void showMessage(String message) {
        System.out.println(message + "\n");
    }

    public void showLoadingError() {
        System.out.println("An error occurred while loading tasks from the file.\n");
    }
}
