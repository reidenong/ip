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

    public String showWelcomeMessage() {
        return("Hello! I'm Barry.\nWhat can I do for you?\n");
    }

    public String showGoodbyeMessage() {
        return("Bye. See you soon!\n");
    }

    public String showMessage(String message) {
        return(message + "\n");
    }

    public String showLoadingError() {
        return("An error occurred while loading tasks from the file.\n");
    }
}
