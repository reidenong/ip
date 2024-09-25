package barry;

import java.util.Scanner;

/**
 * The Ui class manages the User Command line interface that the user uses to
 * communicate with Barry.
 */
public class Ui {
    private Scanner scanner;

    // UI Constructor
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    // Takes in the next command from shell
    public String readCommand() {
        return scanner.nextLine();
    }

    // Defines a welcome message
    public String showWelcomeMessage() {
        return ("Hello! I'm Barry.\nWhat can I do for you?\n");
    }

    // Defines a goodbye message
    public String showGoodbyeMessage() {
        return ("Bye. See you soon!\n");
    }

    // Shows the error message
    public String showMessage(String message) {
        return (message + "\n");
    }

    // Shows the error message
    public String showLoadingError() {
        return ("An error occurred while loading tasks from the file.\n");
    }
}
