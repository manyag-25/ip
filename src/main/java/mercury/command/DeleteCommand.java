package mercury.command;

import mercury.task.Task;
import mercury.task.TaskList;
import mercury.ui.Ui;
import mercury.storage.Storage;
import mercury.MercuryException;

/**
 * Represents a command to delete a task.
 */
public class DeleteCommand extends Command {
    private int index;

    /**
     * Constructs a DeleteCommand with the index of the task to delete.
     *
     * @param index The zero-based index of the task to delete.
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws MercuryException {
        Task deletedTask = tasks.delete(index);
        ui.showTaskDeleted(deletedTask, tasks.size());
        storage.save(tasks);
    }
}
