package barry;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * The main class for the Barry application, a simple task management
 * application.
 * Barry manages various types of tasks, including todos, deadlines, and events.
 * 
 * Functionality provided includes adding tasks, listing all tasks, marking
 * tasks as done or undone,
 * and deleting tasks. The application is operated via a command-line interface
 * (CLI).
 */
public class Barry {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Constructs a new instance of the Barry application, initializing its
     * components
     * and loading tasks from the specified file.
     * 
     * @param filePath The path to the file where tasks are saved and from which
     *                 they are loaded.
     */
    public Barry(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);

        try {
            // Load existing tasks from storage. If file is not found, initialize an empty
            // task list.
            this.tasks = new TaskList(storage.load());
        } catch (FileNotFoundException e) {
            // Display an error message if the file could not be loaded and initialize an
            // empty task list.
            this.ui.showLoadingError();
            this.tasks = new TaskList();
        }
    }

    /**
     * Processes user input and returns the corresponding response.
     * This method parses the input to determine the appropriate command, executes
     * the command,
     * and returns the result to the user. It handles any exceptions encountered
     * during the process.
     * 
     * @param input The user input command as a string.
     * @return The result of the command execution, or an error message if an
     *         exception occurs.
     */
    public String getResponse(String input) {
        try {
            // Parse the user input to create a command, then execute it.
            Command command = Parser.parse(input);
            return command.execute(tasks, ui, storage);
        } catch (BarryException | IOException e) {
            // Return the exception message if any errors occur during parsing or execution.
            return e.getMessage();
        }
    }
}
