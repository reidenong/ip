import java.io.FileNotFoundException;
import java.io.IOException;

public class Barry {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

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

    public static void main(String[] args) {
        new Barry("data/tasks.txt").run();
    }

}
