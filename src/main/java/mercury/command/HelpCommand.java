package mercury.command;

import mercury.MercuryException;
import mercury.storage.Storage;
import mercury.task.TaskList;
import mercury.ui.Ui;

/**
 * Represents a command to display the help message.
 */
public class HelpCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws MercuryException {
        ui.showHelp();
    }
}
