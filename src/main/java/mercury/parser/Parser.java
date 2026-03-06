package mercury.parser;

import mercury.command.AddCommand;
import mercury.command.CheerCommand;
import mercury.command.Command;
import mercury.command.DeleteCommand;
import mercury.command.ExitCommand;
import mercury.command.FindCommand;
import mercury.command.HelpCommand;
import mercury.command.ListCommand;
import mercury.command.MarkCommand;
import mercury.command.UnmarkCommand;
import mercury.task.Deadline;
import mercury.task.Event;
import mercury.task.Todo;
import mercury.MercuryException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Parses user input into commands.
 */
public class Parser {

    /**
     * Normalizes a command string by trimming whitespace and collapsing multiple spaces.
     *
     * @param input The raw user input.
     * @return The normalized command string.
     */
    private static String normalize(String input) {
        return input.trim().replaceAll("\\s+", " ");
    }

    /**
     * Parses the full command string into a Command object.
     *
     * @param fullCommand The full user input string.
     * @return The parsed Command object.
     * @throws MercuryException If the command is invalid or missing arguments.
     */
    public static Command parse(String fullCommand) throws MercuryException {
        if (fullCommand == null) {
            throw new MercuryException("I received an empty message.");
        }
        String normalized = normalize(fullCommand);
        if (normalized.isEmpty()) {
            throw new MercuryException("Please enter a command. Type 'help' to see what I can do.");
        }
        if (normalized.equals("bye")) {
            return new ExitCommand();
        } else if (normalized.equals("list")) {
            return new ListCommand();
        } else if (normalized.equals("help")) {
            return new HelpCommand();
        } else if (normalized.equals("cheer")) {
            return new CheerCommand();
        } else if (normalized.startsWith("mark ") || normalized.equals("mark")) {
            return new MarkCommand(parseIndex(normalized, "mark"));
        } else if (normalized.startsWith("unmark ") || normalized.equals("unmark")) {
            return new UnmarkCommand(parseIndex(normalized, "unmark"));
        } else if (normalized.startsWith("delete ") || normalized.equals("delete")) {
            return new DeleteCommand(parseIndex(normalized, "delete"));
        } else if (normalized.startsWith("todo ") || normalized.equals("todo")) {
            return parseTodo(normalized);
        } else if (normalized.startsWith("deadline ") || normalized.equals("deadline")) {
            return parseDeadline(normalized);
        } else if (normalized.startsWith("event ") || normalized.equals("event")) {
            return parseEvent(normalized);
        } else if (normalized.startsWith("find ") || normalized.equals("find")) {
            return parseFind(normalized);
        } else {
            throw new MercuryException("I don't understand '" + normalized.split(" ")[0]
                    + "'. Type 'help' to see available commands.");
        }
    }

    private static int parseIndex(String command, String cmdName) throws MercuryException {
        String[] parts = command.split(" ");
        if (parts.length < 2 || parts[1].isBlank()) {
            throw new MercuryException("Please provide a task number after '" + cmdName + "'.");
        }
        if (parts.length > 2) {
            throw new MercuryException("'" + cmdName + "' expects exactly one task number, not multiple.");
        }
        try {
            int n = Integer.parseInt(parts[1]);
            if (n <= 0) {
                throw new MercuryException("Task number must be a positive integer (e.g., " + cmdName + " 1).");
            }
            return n - 1;
        } catch (NumberFormatException e) {
            throw new MercuryException("'" + parts[1] + "' is not a valid task number. Use a positive integer.");
        }
    }

    private static Command parseTodo(String command) throws MercuryException {
        if (!command.startsWith("todo ")) {
            throw new MercuryException("todo requires a description (e.g., todo buy groceries).");
        }
        String description = command.substring(5).trim();
        if (description.isEmpty()) {
            throw new MercuryException("todo requires a description (e.g., todo buy groceries).");
        }
        return new AddCommand(new Todo(description));
    }

    private static Command parseDeadline(String command) throws MercuryException {
        if (!command.startsWith("deadline ")) {
            throw new MercuryException("deadline requires a description and /by date "
                    + "(e.g., deadline report /by 2026-03-01).");
        }
        String rest = command.substring(9).trim();
        if (rest.isEmpty()) {
            throw new MercuryException("deadline requires a description and /by date "
                    + "(e.g., deadline report /by 2026-03-01).");
        }
        int byIndex = rest.indexOf("/by");
        if (byIndex == -1) {
            throw new MercuryException("deadline must include /by followed by the date "
                    + "(e.g., deadline report /by 2026-03-01).");
        }
        String description = rest.substring(0, byIndex).trim();
        if (description.isEmpty()) {
            throw new MercuryException("deadline requires a non-empty description before /by.");
        }
        String dateStr = rest.substring(byIndex + 3).trim();
        if (dateStr.isEmpty()) {
            throw new MercuryException("deadline requires a date after /by (format: yyyy-mm-dd).");
        }
        try {
            LocalDate date = LocalDate.parse(dateStr);
            return new AddCommand(new Deadline(description, date));
        } catch (DateTimeParseException e) {
            throw new MercuryException("Invalid date '" + dateStr
                    + "'. Use yyyy-mm-dd format (e.g., 2026-03-01). Check for non-existent dates.");
        }
    }

    private static Command parseEvent(String command) throws MercuryException {
        if (!command.startsWith("event ")) {
            throw new MercuryException("event requires a description, /from time, and /to time.");
        }
        String rest = command.substring(6).trim();
        if (rest.isEmpty()) {
            throw new MercuryException("event requires a description, /from time, and /to time.");
        }
        int fromIndex = rest.indexOf("/from");
        int toIndex = rest.indexOf("/to");
        if (fromIndex == -1) {
            throw new MercuryException("event must include /from to specify the start time.");
        }
        if (toIndex == -1) {
            throw new MercuryException("event must include /to to specify the end time.");
        }
        if (toIndex < fromIndex) {
            throw new MercuryException("/to must appear after /from in an event command.");
        }
        String description = rest.substring(0, fromIndex).trim();
        if (description.isEmpty()) {
            throw new MercuryException("event requires a non-empty description before /from.");
        }
        String fromTime = rest.substring(fromIndex + 5, toIndex).trim();
        String toTime = rest.substring(toIndex + 3).trim();
        if (fromTime.isEmpty()) {
            throw new MercuryException("event requires a start time after /from.");
        }
        if (toTime.isEmpty()) {
            throw new MercuryException("event requires an end time after /to.");
        }
        if (fromTime.equals(toTime)) {
            throw new MercuryException("Event start time and end time cannot be the same.");
        }
        return new AddCommand(new Event(description, fromTime, toTime));
    }

    private static Command parseFind(String command) throws MercuryException {
        if (!command.startsWith("find ")) {
            throw new MercuryException("find requires a keyword (e.g., find report).");
        }
        String keyword = command.substring(5).trim();
        if (keyword.isEmpty()) {
            throw new MercuryException("find requires a keyword (e.g., find report).");
        }
        return new FindCommand(keyword);
    }
}
