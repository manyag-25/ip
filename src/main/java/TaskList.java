import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task delete(int index) throws MercuryException {
        if (index < 0 || index >= tasks.size()) {
            throw new MercuryException("oops that task number doesn't exist");
        }
        return tasks.remove(index);
    }

    public Task get(int index) throws MercuryException {
        if (index < 0 || index >= tasks.size()) {
            throw new MercuryException("oops that task number doesn't exist");
        }
        return tasks.get(index);
    }

    public int size() {
        return tasks.size();
    }

    public ArrayList<Task> getAllTasks() {
        return tasks;
    }
}
