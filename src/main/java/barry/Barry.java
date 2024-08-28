package barry;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * The main class for the Barry application, a task management application.
 * Barry manages different types of tasks, including todos, deadlines, and events.
 * The application supports adding, listing, marking, unmarking, and deleting tasks.
 * It uses the command-line interface to interact with users.
 */
public class Barry {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Constructs a new Barry instance with the specified file path for storing tasks.
     *
     * @param filePath The path to the file where tasks are stored and loaded from.
     */
    public Barry(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);

        try {
            tasks = new TaskList(storage.load());
        } catch (FileNotFoundException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Runs the Barry application. This method initializes the UI, reads user commands,
     * and executes them in a loop until the user exits the application.
     */
    public void run() {
        ui.showWelcomeMessage();
        boolean isRunning = true;

        while (isRunning) {
            String fullCommand = ui.readCommand();
            try {
                Command command = Parser.parse(fullCommand);
                command.execute(tasks, ui, storage);
                isRunning = !command.isExit();
            } catch (BarryException | IOException e) {
                ui.showMessage(e.getMessage());
            }
        }
    }

    /**
     * The main entry point of the Barry application. Creates an instance of Barry with the
     * specified file path and runs the application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        new Barry("data/tasks.txt").run();
    }
}
