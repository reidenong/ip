package barry;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Represents a command that can be executed in the Barry application.
 * Commands include adding tasks, listing tasks, marking tasks as done,
 * unmarking tasks, and deleting tasks.
 */
public interface Command {

    /**
     * Executes the command using the provided task list, UI, and storage.
     *
     * @param tasks   The TaskList to execute the command on.
     * @param ui      The Ui instance for user interaction.
     * @param storage The Storage instance for saving/loading tasks.
     * @throws BarryException If there is an error in the task operations.
     * @throws IOException    If there is an error in input/output operations.
     */
    String execute(TaskList tasks, Ui ui, Storage storage) throws BarryException, IOException;

    /**
     * Checks if the command is an exit command.
     *
     * @return true if the command is an exit command, false otherwise.
     */
    boolean isExit();

    /**
     * Represents the command to exit the Barry application.
     */
    public static class ExitCommand implements Command {

        /**
         * Constructs an ExitCommand.
         */
        public ExitCommand() {
        }

        @Override
        public String execute(TaskList tasks, Ui ui, Storage storage) {
            return ui.showGoodbyeMessage();
        }

        @Override
        public boolean isExit() {
            return true;
        }
    }

    /**
     * Represents the command to list all tasks in the task list.
     */
    public static class ListCommand implements Command {

        /**
         * Constructs a ListCommand.
         */
        public ListCommand() {
        }

        @Override
        public String execute(TaskList tasks, Ui ui, Storage storage) {
            if (tasks.isEmpty()) {
                return ui.showMessage("There are no tasks in your list.");
            } else {
                int N = tasks.size();
                String message = "";
                for (int i = 0; i < N; i++) {
                    try {
                        message += ((i + 1) + ". " + tasks.getTask(i).toString()) + "\n";
                    } catch (BarryException e) {
                        return ui.showMessage("Error retrieving task: " + e.getMessage());
                    }
                }
                return ui.showMessage(message);
            }
        }

        @Override
        public boolean isExit() {
            return false;
        }
    }

    /**
     * Represents the command to mark a task as done.
     */
    public static class MarkCommand implements Command {
        private int index;

        /**
         * Constructs a MarkCommand with the specified task index.
         *
         * @param index The index of the task to be marked as done.
         */
        public MarkCommand(int index) {
            this.index = index - 1;
        }

        @Override
        public String execute(TaskList tasks, Ui ui, Storage storage) throws BarryException, IOException {
            tasks.markTask(index);
            storage.save(tasks.getTasks());
            return ui.showMessage("I've marked this task as done:\n" + tasks.getTask(index).toString());
        }

        @Override
        public boolean isExit() {
            return false;
        }
    }

    /**
     * Represents the command to unmark a task as not done.
     */
    public static class UnmarkCommand implements Command {
        private int index;

        /**
         * Constructs an UnmarkCommand with the specified task index.
         *
         * @param index The index of the task to be unmarked as not done.
         */
        public UnmarkCommand(int index) {
            this.index = index - 1;
        }

        @Override
        public String execute(TaskList tasks, Ui ui, Storage storage) throws BarryException, IOException {
            tasks.unmarkTask(index);
            storage.save(tasks.getTasks());
            return ui.showMessage("I've unmarked this task:\n" + tasks.getTask(index).toString());

        }

        @Override
        public boolean isExit() {
            return false;
        }
    }

    /**
     * Represents the command to add a new todo task.
     */
    public static class AddTodoCommand implements Command {
        private String description;

        /**
         * Constructs an AddTodoCommand with the specified task description.
         *
         * @param description The description of the todo task.
         */
        public AddTodoCommand(String description) {
            this.description = description;
        }

        @Override
        public String execute(TaskList tasks, Ui ui, Storage storage) throws IOException {
            Task.TodoTask task = new Task.TodoTask(description);
            tasks.addTask(task);
            storage.save(tasks.getTasks());
            return ui.showMessage("Got it. I've added this task:\n" + task);

        }

        @Override
        public boolean isExit() {
            return false;
        }
    }

    /**
     * Represents the command to add a new deadline task.
     */
    public static class AddDeadlineCommand implements Command {
        private String description;
        private LocalDateTime by;

        /**
         * Constructs an AddDeadlineCommand with the specified task description and due
         * date.
         *
         * @param description The description of the deadline task.
         * @param by          The due date of the deadline task.
         */
        public AddDeadlineCommand(String description, LocalDateTime by) {
            this.description = description;
            this.by = by;
        }

        @Override
        public String execute(TaskList tasks, Ui ui, Storage storage) throws IOException {
            Task.DeadlineTask task = new Task.DeadlineTask(description, by);
            tasks.addTask(task);
            storage.save(tasks.getTasks());
            return ui.showMessage("Got it. I've added this task:\n" + task);

        }

        @Override
        public boolean isExit() {
            return false;
        }
    }

    /**
     * Represents the command to add a new event task.
     */
    public static class AddEventCommand implements Command {
        private String description;
        private LocalDateTime from;
        private LocalDateTime to;

        /**
         * Constructs an AddEventCommand with the specified task description, start
         * time, and end time.
         *
         * @param description The description of the event task.
         * @param from        The start time of the event.
         * @param to          The end time of the event.
         */
        public AddEventCommand(String description, LocalDateTime from, LocalDateTime to) {
            this.description = description;
            this.from = from;
            this.to = to;
        }

        @Override
        public String execute(TaskList tasks, Ui ui, Storage storage) throws IOException {
            Task.EventTask task = new Task.EventTask(description, from, to);
            tasks.addTask(task);
            storage.save(tasks.getTasks());
            return ui.showMessage("Got it. I've added this task:\n" + task);
        }

        @Override
        public boolean isExit() {
            return false;
        }
    }

    /**
     * Represents the command to delete a task.
     */
    public static class DeleteCommand implements Command {
        private int index;

        /**
         * Constructs a DeleteCommand with the specified task index.
         *
         * @param index The index of the task to be deleted.
         */
        public DeleteCommand(int index) {
            this.index = index - 1;
        }

        @Override
        public String execute(TaskList tasks, Ui ui, Storage storage) throws BarryException, IOException {
            Task task = tasks.getTask(index);
            tasks.removeTask(index);
            storage.save(tasks.getTasks());
            return ui.showMessage("Noted. I've removed this task:\n" + task);

        }

        @Override
        public boolean isExit() {
            return false;
        }
    }

    /**
     * Represents the command to find a task.
     */
    public static class FindCommand implements Command {
        private String searchTerm;

        /**
         * Constructs a FindCommand with the specified search term
         *
         * @param term The term to be searched
         */
        public FindCommand(String term) {
            this.searchTerm = term;
        }

        @Override
        public String execute(TaskList tasks, Ui ui, Storage storage) throws BarryException, IOException {
            ArrayList<Task> answer = tasks.findTasks(this.searchTerm);
            String message = "I've found the following tasks with your given searchterm:\n";
            for (Task task : answer) {
                message += task.toString() + "\n";
            }
            return ui.showMessage(message);
        }

        @Override
        public boolean isExit() {
            return false;
        }
    }

    /**
 * Represents the command to display a list of all available commands.
 */
public static class HelpCommand implements Command {

    /**
     * Constructs a HelpCommand.
     */
    public HelpCommand() {
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        String helpMessage = "Here are the available commands:\n"
            + "1. list - List all tasks\n"
            + "2. mark [task number] - Mark a task as done\n"
            + "3. unmark [task number] - Unmark a task as not done\n"
            + "4. todo [description] - Add a new todo task\n"
            + "5. deadline [description] /by [d/M/yyyy HHmm] - Add a new deadline task\n"
            + "6. event [description] /from [d/M/yyyy HHmm] /to [d/M/yyyy HHmm] - Add a new event task\n"
            + "7. delete [task number] - Delete a task\n"
            + "8. find [keyword] - Find tasks with the specified keyword\n"
            + "9. bye - Exit the application\n"
            + "10. help - Display this list of commands";
        return ui.showMessage(helpMessage);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
}
