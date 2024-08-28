package barry;
import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(int index) throws BarryException {
        if (index < 0 || index >= tasks.size()) {
            throw new BarryException("Task number is out of range.");
        }
        tasks.remove(index);
    }

    public Task getTask(int index) throws BarryException {
        if (index < 0 || index >= tasks.size()) {
            throw new BarryException("Task number is out of range.");
        }
        return tasks.get(index);
    }

    public void markTask(int index) throws BarryException {
        Task task = getTask(index);
        task.mark();
    }

    public void unmarkTask(int index) throws BarryException {
        Task task = getTask(index);
        task.unmark();
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public int size() {
        return tasks.size();
    }
}
