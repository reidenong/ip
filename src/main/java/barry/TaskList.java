package barry;

import java.util.ArrayList;

/**
 * The TaskList class manages a collection of tasks, providing methods to add, remove, 
 * retrieve, and modify tasks in the list. It also supports operations to mark tasks 
 * as completed or uncompleted.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList with an initial list of tasks.
     *
     * @param tasks The initial list of tasks to be managed by this TaskList.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Removes a task at the specified index from the list.
     *
     * @param index The index of the task to be removed.
     * @throws BarryException If the index is out of the range of the task list.
     */
    public void removeTask(int index) throws BarryException {
        if (index < 0 || index >= tasks.size()) {
            throw new BarryException("Task number is out of range.");
        }
        tasks.remove(index);
    }

    /**
     * Retrieves a task at the specified index from the list.
     *
     * @param index The index of the task to retrieve.
     * @return The task at the specified index.
     * @throws BarryException If the index is out of the range of the task list.
     */
    public Task getTask(int index) throws BarryException {
        if (index < 0 || index >= tasks.size()) {
            throw new BarryException("Task number is out of range.");
        }
        return tasks.get(index);
    }

    /**
     * Marks the task at the specified index as completed.
     *
     * @param index The index of the task to be marked as completed.
     * @throws BarryException If the index is out of the range of the task list.
     */
    public void markTask(int index) throws BarryException {
        Task task = getTask(index);
        task.mark();
    }

    /**
     * Unmarks the task at the specified index as not completed.
     *
     * @param index The index of the task to be marked as not completed.
     * @throws BarryException If the index is out of the range of the task list.
     */
    public void unmarkTask(int index) throws BarryException {
        Task task = getTask(index);
        task.unmark();
    }

    public ArrayList<Task> findTasks(String searchTerm) {
        ArrayList<Task> output = new ArrayList<>();
        for (Task task : this.tasks) {
            if (task.getDescription().contains(searchTerm)) {
                output.add(task);
            }
        }
        return output;
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public int size() {
        return tasks.size();
    }
}
