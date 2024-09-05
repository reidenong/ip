package barry;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * The main class for the Barry application, a task management application.
 * Barry manages different types of tasks, including todos, deadlines, and
 * events.
 * The application supports adding, listing, marking, unmarking, and deleting
 * tasks.
 * It uses the command-line interface to interact with users.
 */
public class Barry {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Constructs a new Barry instance with the specified file path for storing
     * tasks.
     *
     * @param filePath The path to the file where tasks are stored and loaded from.
     */
    public Barry(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);

        try {
            this.tasks = new TaskList(storage.load());
        } catch (FileNotFoundException e) {
            this.ui.showLoadingError();
            this.tasks = new TaskList();
        }
    }

    /**
     * Runs the Barry application. This method initializes the UI, reads user
     * commands,
     * and executes them in a loop until the user exits the application.
     */
    public String getResponse(String input) {
        try {
            Command command = Parser.parse(input);
            return command.execute(tasks, ui, storage);
        } catch (BarryException | IOException e) {
            return (e.getMessage());
        }
    }
}
