package mercury.command;

import mercury.task.TaskList;
import mercury.ui.Ui;
import mercury.storage.Storage;

public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showTaskList(tasks);
    }
}
