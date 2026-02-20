package mercury.command;

import mercury.MercuryException;
import mercury.storage.Storage;
import mercury.task.Task;
import mercury.task.TaskList;
import mercury.ui.Ui;

import java.util.List;

/**
 * Represents a command to find tasks by keyword.
 */
public class FindCommand extends Command {
    private String keyword;

    /**
     * Constructs a FindCommand with the specified keyword.
     *
     * @param keyword The keyword to search for.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws MercuryException {
        List<Task> matchingTasks = tasks.getAllTasks().stream()
            .filter(task -> task.toString().contains(keyword))
            .toList();
        ui.showMatchingTasks(matchingTasks);
    }
}
