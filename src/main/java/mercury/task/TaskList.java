package mercury.task;

import mercury.MercuryException;
import java.util.ArrayList;

/**
 * Represents a list of tasks.
 * Provides operations to add, delete, and retrieve tasks.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Constructs a TaskList with an existing list of tasks.
     *
     * @param tasks The initial list of tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        assert tasks != null : "Task list must not be null";
        this.tasks = tasks;
    }

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this(new ArrayList<>());
    }

    /**
     * Adds a task to the list.
     *
     * @param task The task to add.
     */
    public void add(Task task) {
        assert task != null : "Task must not be null";
        tasks.add(task);
    }

    /**
     * Deletes a task from the list at the specified index.
     *
     * @param index The index of the task to delete.
     * @return The deleted task.
     * @throws MercuryException If the index is out of bounds.
     */
    public Task delete(int index) throws MercuryException {
        assert index >= 0 : "Index must not be negative";
        assert tasks != null : "Tasks should be initialized";
        if (index < 0 || index >= tasks.size()) {
            throw new MercuryException("oops that task number doesn't exist");
        }
        return tasks.remove(index);
    }

    /**
     * Retrieves a task from the list at the specified index.
     *
     * @param index The index of the task to retrieve.
     * @return The task at the specified index.
     * @throws MercuryException If the index is out of bounds.
     */
    public Task get(int index) throws MercuryException {
        assert index >= 0 : "Index must not be negative";
        assert tasks != null : "Tasks should be initialized";
        if (index < 0 || index >= tasks.size()) {
            throw new MercuryException("oops that task number doesn't exist");
        }
        return tasks.get(index);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The size of the task list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the underlying ArrayList of tasks.
     *
     * @return The ArrayList of tasks.
     */
    public ArrayList<Task> getAllTasks() {
        assert tasks != null : "Tasks should be initialized";
        return tasks;
    }
}
