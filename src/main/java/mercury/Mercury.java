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
     * Constructs a new Mercury application instance with default file path.
     */
    public Mercury() {
        this("./data/duke.txt");
    }

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

    /**
     * Gets the welcome message.
     *
     * @return The welcome message string.
     */
    public String getWelcomeMessage() {
        return "Hello! I'm Mercury\nWhat can I do for you?";
    }

    /**
     * Gets the response for a user command.
     *
     * @param input The user's command.
     * @return The response message.
     */
    public String getResponse(String input) {
        try {
            Command c = Parser.parse(input);
            return executeCommand(c);
        } catch (MercuryException e) {
            return e.getMessage();
        }
    }

    /**
     * Executes a command and returns the response as a string.
     *
     * @param command The command to execute.
     * @return The response message.
     * @throws MercuryException If an error occurs during execution.
     */
    private String executeCommand(Command command) throws MercuryException {
        // Capture output using a custom Ui that returns strings
        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        java.io.PrintStream ps = new java.io.PrintStream(baos);
        java.io.PrintStream old = System.out;
        System.setOut(ps);

        try {
            command.execute(tasks, ui, storage);
            System.out.flush();
            return baos.toString().trim();
        } finally {
            System.setOut(old);
        }
    }

    public static void main(String[] args) {
        new Mercury("./data/duke.txt").run();
    }
}