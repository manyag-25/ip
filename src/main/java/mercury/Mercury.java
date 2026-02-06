package mercury;

import mercury.ui.Ui;
import mercury.storage.Storage;
import mercury.task.TaskList;
import mercury.parser.Parser;
import mercury.command.Command;

/**
 * The main class of the Mercury application.
 * Initializes the application components and runs the main loop.
 */
public class Mercury {
    
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Constructs a new Mercury application instance.
     *
     * @param filePath The path to the file where tasks are stored.
     */
    public Mercury(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (MercuryException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (MercuryException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    public static void main(String[] args) {
        new Mercury("./data/duke.txt").run();
    }
}