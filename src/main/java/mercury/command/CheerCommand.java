package mercury.command;

import mercury.task.TaskList;
import mercury.ui.Ui;
import mercury.storage.Storage;
import mercury.MercuryException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a command to display a random motivational quote.
 */
public class CheerCommand extends Command {
    private static final String QUOTES_FILE_PATH = "./data/cheer.txt";
    private Random random;

    /**
     * Constructs a new CheerCommand.
     */
    public CheerCommand() {
        this.random = new Random();
    }

    /**
     * Executes the cheer command by displaying a random motivational quote.
     *
     * @param tasks   The task list (not used by this command).
     * @param ui      The user interface to display the quote.
     * @param storage The storage (not used by this command).
     * @throws MercuryException If an error occurs while reading quotes.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws MercuryException {
        List<String> quotes = loadQuotes();
        if (quotes.isEmpty()) {
            throw new MercuryException("No motivational quotes available.");
        }
        String randomQuote = quotes.get(random.nextInt(quotes.size()));
        ui.showCheerMessage(randomQuote);
    }

    /**
     * Loads all quotes from the quotes file.
     *
     * @return A list of all available quotes.
     * @throws MercuryException If the file cannot be read.
     */
    private List<String> loadQuotes() throws MercuryException {
        List<String> quotes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(QUOTES_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    quotes.add(line.trim());
                }
            }
        } catch (IOException e) {
            throw new MercuryException("Error reading motivational quotes file: " + e.getMessage());
        }
        return quotes;
    }
}
