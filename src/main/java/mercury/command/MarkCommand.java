package mercury.command;

import mercury.task.Task;
import mercury.task.TaskList;
import mercury.ui.Ui;
import mercury.storage.Storage;
import mercury.MercuryException;

/**
 * Represents a command to mark a task as done.
 */
public class MarkCommand extends Command {
    private int index;

    /**
     * Constructs a MarkCommand with the index of the task to mark.
     *
     * @param index The zero-based index of the task to mark as done.
     */
    public MarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws MercuryException {
        Task task = tasks.get(index);
        task.markAsDone();
        ui.showTaskMarked(task);
        storage.save(tasks);
    }
}
