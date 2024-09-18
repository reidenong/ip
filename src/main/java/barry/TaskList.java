package barry;

import java.util.ArrayList;

/**
 * The TaskList class manages a collection of tasks, providing methods to add,
 * remove,
 * retrieve, and modify tasks in the list. It also supports operations to mark
 * tasks
 * as completed or uncompleted.
 */
public class TaskList {
    private ArrayList<Task> taskList;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.taskList = new ArrayList<>();
        assert this.taskList != null : "Task list should not be null after initialization.";
    }

    /**
     * Constructs a TaskList with an initial list of tasks.
     *
     * @param initialTasks The initial list of tasks to be managed by this TaskList.
     */
    public TaskList(ArrayList<Task> initialTasks) {
        this.taskList = initialTasks;
        assert this.taskList != null : "Task list should not be null after initialization with initial tasks.";
    }

    /**
     * Returns the current list of tasks.
     *
     * @return The list of tasks in this TaskList.
     */
    public ArrayList<Task> getTasks() {
        assert taskList != null : "Task list should not be null when retrieving tasks.";
        return this.taskList;
    }

    /**
     * Adds a new task to the task list.
     *
     * @param newTask The task to be added.
     */
    public void addTask(Task newTask) {
        assert newTask != null : "New task should not be null when adding to the list.";
        taskList.add(newTask);
    }

    /**
     * Removes a task at the specified index from the list.
     *
     * @param taskIndex The index of the task to be removed.
     * @throws BarryException If the index is out of the range of the task list.
     */
    public void removeTask(int taskIndex) throws BarryException {
        assert taskIndex >= 0 : "Task index should not be negative.";
        if (taskIndex < 0 || taskIndex >= taskList.size()) {
            throw new BarryException("Task number is out of range.");
        }
        taskList.remove(taskIndex);
    }

    /**
     * Retrieves a task at the specified index from the list.
     *
     * @param taskIndex The index of the task to retrieve.
     * @return The task at the specified index.
     * @throws BarryException If the index is out of the range of the task list.
     */
    public Task getTask(int taskIndex) throws BarryException {
        assert taskIndex >= 0 : "Task index should not be negative.";
        if (taskIndex < 0 || taskIndex >= taskList.size()) {
            throw new BarryException("Task number is out of range.");
        }
        return taskList.get(taskIndex);
    }

    /**
     * Marks the task at the specified index as completed.
     *
     * @param taskIndex The index of the task to be marked as completed.
     * @throws BarryException If the index is out of the range of the task list.
     */
    public void markTask(int taskIndex) throws BarryException {
        assert taskIndex >= 0 : "Task index should not be negative.";
        Task taskToMark = getTask(taskIndex);
        assert taskToMark != null : "Task to be marked should not be null.";
        taskToMark.mark();
    }

    /**
     * Unmarks the task at the specified index as not completed.
     *
     * @param taskIndex The index of the task to be marked as not completed.
     * @throws BarryException If the index is out of the range of the task list.
     */
    public void unmarkTask(int taskIndex) throws BarryException {
        assert taskIndex >= 0 : "Task index should not be negative.";
        Task taskToUnmark = getTask(taskIndex);
        assert taskToUnmark != null : "Task to be unmarked should not be null.";
        taskToUnmark.unmark();
    }

    /**
     * Finds tasks that contain the specified search term.
     *
     * @param searchTerm The term to search for.
     * @return A list of tasks that match the search term.
     */
    public ArrayList<Task> findTasks(String searchTerm) {
        assert searchTerm != null && !searchTerm.isEmpty() : "Search term should not be null or empty.";
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : this.taskList) {
            if (task.getDescription().contains(searchTerm)) {
                matchingTasks.add(task);
            }
        }
        return matchingTasks;
    }

    /**
     * Checks if the task list is empty.
     *
     * @return true if the task list is empty, false otherwise.
     */
    public boolean isEmpty() {
        return taskList.isEmpty();
    }

    /**
     * Returns the number of tasks in the task list.
     *
     * @return The number of tasks in the task list.
     */
    public int size() {
        return taskList.size();
    }
}
