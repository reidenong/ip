package barry;

import java.util.ArrayList;
import java.util.List;

/**
 * The TaskList class manages a collection of tasks, providing methods to add,
 * remove, retrieve, and modify tasks in the list. It also supports operations
 * to mark
 * tasks as completed or uncompleted.
 */
public class TaskList {
    private final List<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList with an initial list of tasks.
     *
     * @param initialTasks The initial list of tasks to be managed by this TaskList.
     */
    public TaskList(final List<Task> initialTasks) {
        this.tasks = new ArrayList<>(initialTasks); // Defensive copy
        assert !tasks.isEmpty() : "Initial task list should not be empty.";
    }

    /**
     * Returns the current list of tasks.
     *
     * @return The list of tasks in this TaskList.
     */
    public List<Task> getTasks() {
        return new ArrayList<>(tasks); // Defensive copy to avoid modification
    }

    /**
     * Adds a new task to the task list.
     *
     * @param newTask The task to be added.
     */
    public void addTask(final Task newTask) {
        assert newTask != null : "New task should not be null.";
        tasks.add(newTask);
    }

    /**
     * Removes a task at the specified index from the list.
     *
     * @param taskIndex The index of the task to be removed.
     * @throws BarryException If the index is out of range of the task list.
     */
    public void removeTask(final int taskIndex) throws BarryException {
        validateTaskIndex(taskIndex);
        tasks.remove(taskIndex);
    }

    /**
     * Retrieves a task at the specified index from the list.
     *
     * @param taskIndex The index of the task to retrieve.
     * @return The task at the specified index.
     * @throws BarryException If the index is out of range of the task list.
     */
    public Task getTask(final int taskIndex) throws BarryException {
        validateTaskIndex(taskIndex);
        return tasks.get(taskIndex);
    }

    /**
     * Marks the task at the specified index as completed.
     *
     * @param taskIndex The index of the task to be marked as completed.
     * @throws BarryException If the index is out of range of the task list.
     */
    public void markTask(final int taskIndex) throws BarryException {
        Task task = getTask(taskIndex);
        task.mark();
    }

    /**
     * Unmarks the task at the specified index as not completed.
     *
     * @param taskIndex The index of the task to be marked as not completed.
     * @throws BarryException If the index is out of range of the task list.
     */
    public void unmarkTask(final int taskIndex) throws BarryException {
        Task task = getTask(taskIndex);
        task.unmark();
    }

    /**
     * Finds tasks that contain the specified search term.
     *
     * @param searchTerm The term to search for.
     * @return A list of tasks that match the search term.
     */
    public List<Task> findTasks(final String searchTerm) {
        assert searchTerm != null && !searchTerm.isEmpty() : "Search term should not be null or empty.";
        List<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
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
        return tasks.isEmpty();
    }

    /**
     * Returns the number of tasks in the task list.
     *
     * @return The number of tasks in the task list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Validates that the given task index is within the bounds of the task list.
     *
     * @param taskIndex The index to be validated.
     * @throws BarryException If the index is out of the valid range.
     */
    private void validateTaskIndex(final int taskIndex) throws BarryException {
        if (taskIndex < 0 || taskIndex >= tasks.size()) {
            throw new BarryException("Task index is out of range.");
        }
    }
}
