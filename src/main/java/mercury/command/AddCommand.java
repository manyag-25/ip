package mercury.command;

import mercury.task.Task;
import mercury.task.TaskList;
import mercury.ui.Ui;
import mercury.storage.Storage;
import mercury.MercuryException;

/**
 * Represents a command to add a task.
 */
public class AddCommand extends Command {
    private Task task;

    /**
     * Constructs an AddCommand with the task to allow adding.
     *
     * @param task The task to add.
     */
    public AddCommand(Task task) {
        this.task = task;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws MercuryException {
        tasks.add(task);
        ui.showTaskAdded(task, tasks.size());
        storage.save(tasks);
    }
}
