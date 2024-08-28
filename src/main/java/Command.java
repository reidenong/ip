import java.io.IOException;
import java.time.LocalDateTime;

// Command interface
public interface Command {
    void execute(TaskList tasks, Ui ui, Storage storage) throws BarryException, IOException;
    boolean isExit();

    // ExitCommand class
    public static class ExitCommand implements Command {
        public ExitCommand() {
            // Constructor can be called from outside
        }

        @Override
        public void execute(TaskList tasks, Ui ui, Storage storage) {
            ui.showGoodbyeMessage();
        }

        @Override
        public boolean isExit() {
            return true;
        }
    }

    // ListCommand class
    public static class ListCommand implements Command {
        public ListCommand() {
            // Constructor can be called from outside
        }

        @Override
        public void execute(TaskList tasks, Ui ui, Storage storage) {
            if (tasks.isEmpty()) {
                ui.showMessage("There are no tasks in your list.");
            } else {
                int N = tasks.size();
                String message = "";
                for (int i = 0; i < N; i++) {
                    try {
                        message += ((i + 1) + ". " + tasks.getTask(i).toString()) + "\n";
                    } catch (BarryException e) {
                        ui.showMessage("Error retrieving task: " + e.getMessage());
                    }
                }
                ui.showMessage(message);
            }
        }

        @Override
        public boolean isExit() {
            return false;
        }
    }

    // MarkCommand class
    public static class MarkCommand implements Command {
        private int index;

        public MarkCommand(int index) {
            this.index = index - 1;
        }

        @Override
        public void execute(TaskList tasks, Ui ui, Storage storage) throws BarryException, IOException {
            tasks.markTask(index);
            ui.showMessage("I've marked this task as done:\n" + tasks.getTask(index).toString());
            storage.save(tasks.getTasks());
        }

        @Override
        public boolean isExit() {
            return false;
        }
    }

    // UnmarkCommand class
    public static class UnmarkCommand implements Command {
        private int index;

        public UnmarkCommand(int index) {
            this.index = index - 1;
        }

        @Override
        public void execute(TaskList tasks, Ui ui, Storage storage) throws BarryException, IOException {
            tasks.unmarkTask(index);
            ui.showMessage("I've unmarked this task:\n" + tasks.getTask(index).toString());
            storage.save(tasks.getTasks());
        }

        @Override
        public boolean isExit() {
            return false;
        }
    }

    // AddTodoCommand class
    public static class AddTodoCommand implements Command {
        private String description;

        public AddTodoCommand(String description) {
            this.description = description;
        }

        @Override
        public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException {
            Task.TodoTask task = new Task.TodoTask(description);
            tasks.addTask(task);
            ui.showMessage("Got it. I've added this task:\n" + task);
            storage.save(tasks.getTasks());
        }

        @Override
        public boolean isExit() {
            return false;
        }
    }

    // AddDeadlineCommand class
    public static class AddDeadlineCommand implements Command {
        private String description;
        private LocalDateTime by;

        public AddDeadlineCommand(String description, LocalDateTime by) {
            this.description = description;
            this.by = by;
        }

        @Override
        public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException {
            Task.DeadlineTask task = new Task.DeadlineTask(description, by);
            tasks.addTask(task);
            ui.showMessage("Got it. I've added this task:\n" + task);
            storage.save(tasks.getTasks());
        }

        @Override
        public boolean isExit() {
            return false;
        }
    }

    // AddEventCommand class
    public static class AddEventCommand implements Command {
        private String description;
        private LocalDateTime from;
        private LocalDateTime to;

        public AddEventCommand(String description, LocalDateTime from, LocalDateTime to) {
            this.description = description;
            this.from = from;
            this.to = to;
        }

        @Override
        public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException {
            Task.EventTask task = new Task.EventTask(description, from, to);
            tasks.addTask(task);
            ui.showMessage("Got it. I've added this task:\n" + task);
            storage.save(tasks.getTasks());
        }

        @Override
        public boolean isExit() {
            return false;
        }
    }

    // DeleteCommand class
    public static class DeleteCommand implements Command {
        private int index;

        public DeleteCommand(int index) {
            this.index = index - 1;
        }

        @Override
        public void execute(TaskList tasks, Ui ui, Storage storage) throws BarryException, IOException {
            Task task = tasks.getTask(index);
            tasks.removeTask(index);
            ui.showMessage("Noted. I've removed this task:\n" + task);
            storage.save(tasks.getTasks());
        }

        @Override
        public boolean isExit() {
            return false;
        }
    }
}
