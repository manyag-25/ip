package mercury.command;

import mercury.task.TaskList;
import mercury.ui.Ui;
import mercury.storage.Storage;
import mercury.MercuryException;

/**
 * Represents an abstract command that can be executed.
 */
public abstract class Command {
    /**
     * Executes the command.
     *
     * @param tasks   The task list to modify.
     * @param ui      The user interface to interact with.
     * @param storage The storage to save changes.
     * @throws MercuryException If an error occurs during execution.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws MercuryException;

    /**
     * Checks if the command should exit the application.
     *
     * @return True if the command is an exit command, false otherwise.
     */
    public boolean isExit() {
        return false;
    }
}
