package barry;

import java.util.Scanner;

/**
 * The Ui class handles the User Interface for Barry, facilitating communication
 * between the user and the application through the command-line interface
 * (CLI).
 * It provides methods to display messages to the user and read user input.
 */
public class Ui {
    private Scanner scanner;

    /**
     * Constructs a new Ui instance and initializes the scanner
     * to read user input from the command-line.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads the next command entered by the user via the command-line.
     * 
     * @return The user's input as a string.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays a welcome message to the user when they start the application.
     * 
     * @return A welcome message as a string.
     */
    public String showWelcomeMessage() {
        return ("Hello! I'm Barry.\nWhat can I do for you?\n");
    }

    /**
     * Displays a farewell message to the user when they exit the application.
     * 
     * @return A goodbye message as a string.
     */
    public String showGoodbyeMessage() {
        return ("Bye. See you soon!\n");
    }

    /**
     * Displays any message passed to it, typically used for general notifications.
     * 
     * @param message The message to be displayed.
     * @return The message followed by a newline character.
     */
    public String showMessage(String message) {
        return (message + "\n");
    }

    /**
     * Displays an error message if tasks cannot be loaded from the storage file.
     * 
     * @return A loading error message as a string.
     */
    public String showLoadingError() {
        return ("An error occurred while loading tasks from the file.\n");
    }
}
