package mercury.parser;

import mercury.command.AddCommand;
import mercury.command.Command;
import mercury.command.DeleteCommand;
import mercury.command.ExitCommand;
import mercury.command.ListCommand;
import mercury.command.MarkCommand;
import mercury.command.UnmarkCommand;
import mercury.command.FindCommand;
import mercury.command.CheerCommand;
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
     * Parses the full command string into a Command object.
     *
     * @param fullCommand The full user input string.
     * @return The parsed Command object.
     * @throws MercuryException If the command is invalid or missing arguments.
     */
    public static Command parse(String fullCommand) throws MercuryException {
        if (fullCommand.equals("bye")) {
            return new ExitCommand();
        } else if (fullCommand.equals("list")) {
            return new ListCommand();
        } else if (fullCommand.startsWith("mark")) {
            return new MarkCommand(parseIndex(fullCommand));
        } else if (fullCommand.startsWith("unmark")) {
            return new UnmarkCommand(parseIndex(fullCommand));
        } else if (fullCommand.startsWith("delete")) {
            return new DeleteCommand(parseIndex(fullCommand));
        } else if (fullCommand.startsWith("todo")) {
            return parseTodo(fullCommand);
        } else if (fullCommand.startsWith("deadline")) {
            return parseDeadline(fullCommand);
        } else if (fullCommand.startsWith("event")) {
            return parseEvent(fullCommand);
        } else if (fullCommand.startsWith("find")) {
            return parseFind(fullCommand);
        } else if (fullCommand.equals("cheer")) {
            return new CheerCommand();
        } else {
            throw new MercuryException("I don't understand that command.");
        }
    }

    private static int parseIndex(String command) throws MercuryException {
        try {
            String[] parts = command.split(" ");
            if (parts.length < 2) {
                // Determine cmd type for error msg or just generic
                throw new MercuryException("oops please provide a task number");
            }
            return Integer.parseInt(parts[1]) - 1;
        } catch (NumberFormatException e) {
            throw new MercuryException("oops please provide a valid task number");
        }
    }

    private static Command parseTodo(String command) throws MercuryException {
        String description = command.substring(4).trim();
        if (description.isEmpty()) {
            throw new MercuryException("oops todo must be followed by the action item");
        }
        return new AddCommand(new Todo(description));
    }

    private static Command parseDeadline(String command) throws MercuryException {
        String rest = command.substring(8).trim();
        if (rest.isEmpty()) {
             throw new MercuryException("oops deadline must be followed by the action item");
        }
        int byIndex = rest.indexOf("/by");
        if (byIndex == -1) {
            throw new MercuryException("oops deadline must include /by");
        }
        String description = rest.substring(0, byIndex).trim();
        String dateStr = rest.substring(byIndex + 3).trim();
        try {
            LocalDate date = LocalDate.parse(dateStr);
            return new AddCommand(new Deadline(description, date));
        } catch (DateTimeParseException e) {
            throw new MercuryException("oops please use yyyy-mm-dd format for deadline (e.g., 2019-12-02)");
        }
    }

    private static Command parseEvent(String command) throws MercuryException {
        String rest = command.substring(5).trim();
        if (rest.isEmpty()) {
             throw new MercuryException("oops event must be followed by the action item");
        }
        int fromIndex = rest.indexOf("/from");
        int toIndex = rest.indexOf("/to");
        if (fromIndex == -1 || toIndex == -1) {
             throw new MercuryException("oops event must include /from and /to");
        }
        String description = rest.substring(0, fromIndex).trim();
        String fromTime = rest.substring(fromIndex + 5, toIndex).trim();
        String toTime = rest.substring(toIndex + 3).trim();
        return new AddCommand(new Event(description, fromTime, toTime));
    }

    private static Command parseFind(String command) throws MercuryException {
        String keyword = command.substring(4).trim();
        if (keyword.isEmpty()) {
            throw new MercuryException("oops find must be followed by a keyword");
        }
        return new FindCommand(keyword);
    }
}
