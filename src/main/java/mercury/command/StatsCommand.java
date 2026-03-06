package mercury.command;

import mercury.task.Deadline;
import mercury.task.Event;
import mercury.task.Task;
import mercury.task.TaskList;
import mercury.task.Todo;
import mercury.ui.Ui;
import mercury.storage.Storage;
import mercury.MercuryException;

/**
 * Represents a command to display task statistics.
 */
public class StatsCommand extends Command {

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws MercuryException {
        int total = tasks.size();
        int done = 0;
        int todos = 0;
        int deadlines = 0;
        int events = 0;

        for (Task task : tasks.getAllTasks()) {
            if (task.getStatusIcon().equals("X")) {
                done++;
            }
            if (task instanceof Todo) {
                todos++;
            } else if (task instanceof Deadline) {
                deadlines++;
            } else if (task instanceof Event) {
                events++;
            }
        }

        ui.showStats(total, done, todos, deadlines, events);
    }
}
