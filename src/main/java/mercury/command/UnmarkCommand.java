package mercury.command;

import mercury.task.Task;
import mercury.task.TaskList;
import mercury.ui.Ui;
import mercury.storage.Storage;
import mercury.MercuryException;

/**
 * Represents a command to mark a task as not done.
 */
public class UnmarkCommand extends Command {
    private int index;

    /**
     * Constructs an UnmarkCommand with the index of the task to unmark.
     *
     * @param index The zero-based index of the task to mark as not done.
     */
    public UnmarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws MercuryException {
        Task task = tasks.get(index);
        task.markAsUndone();
        ui.showTaskUnmarked(task);
        storage.save(tasks);
    }
}
