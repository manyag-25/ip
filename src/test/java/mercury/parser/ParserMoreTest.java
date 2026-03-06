package mercury.parser;

import mercury.MercuryException;
import mercury.command.AddCommand;
import mercury.command.DeleteCommand;
import mercury.command.ExitCommand;
import mercury.command.HelpCommand;
import mercury.command.ListCommand;
import mercury.command.MarkCommand;
import mercury.command.UnmarkCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParserMoreTest {

    @Test
    public void parse_list_listCommand() throws MercuryException {
        assertTrue(Parser.parse("list") instanceof ListCommand);
    }

    @Test
    public void parse_help_helpCommand() throws MercuryException {
        assertTrue(Parser.parse("help") instanceof HelpCommand);
    }

    @Test
    public void parse_markWithIndex_markCommand() throws MercuryException {
        assertTrue(Parser.parse("mark 2") instanceof MarkCommand);
    }

    @Test
    public void parse_markWithoutIndex_exceptionThrown() {
        assertThrows(MercuryException.class, () -> Parser.parse("mark"));
    }

    @Test
    public void parse_markWithNonInteger_exceptionThrown() {
        assertThrows(MercuryException.class, () -> Parser.parse("mark abc"));
    }

    @Test
    public void parse_unmarkWithIndex_unmarkCommand() throws MercuryException {
        assertTrue(Parser.parse("unmark 1") instanceof UnmarkCommand);
    }

    @Test
    public void parse_unmarkWithoutIndex_exceptionThrown() {
        assertThrows(MercuryException.class, () -> Parser.parse("unmark"));
    }

    @Test
    public void parse_deleteWithIndex_deleteCommand() throws MercuryException {
        assertTrue(Parser.parse("delete 3") instanceof DeleteCommand);
    }

    @Test
    public void parse_deleteWithoutIndex_exceptionThrown() {
        assertThrows(MercuryException.class, () -> Parser.parse("delete"));
    }

    @Test
    public void parse_deadline_addCommand() throws MercuryException {
        assertTrue(Parser.parse("deadline submit report /by 2026-03-01") instanceof AddCommand);
    }

    @Test
    public void parse_deadlineNoBy_exceptionThrown() {
        assertThrows(MercuryException.class, () -> Parser.parse("deadline submit report"));
    }

    @Test
    public void parse_deadlineInvalidDate_exceptionThrown() {
        assertThrows(MercuryException.class, () ->
            Parser.parse("deadline submit report /by 01-03-2026"));
    }

    @Test
    public void parse_deadlineNonExistentDate_exceptionThrown() {
        assertThrows(MercuryException.class, () ->
            Parser.parse("deadline submit report /by 2026-02-30"));
    }

    @Test
    public void parse_event_addCommand() throws MercuryException {
        assertTrue(Parser.parse("event team meeting /from 2pm /to 4pm") instanceof AddCommand);
    }

    @Test
    public void parse_eventNoFrom_exceptionThrown() {
        assertThrows(MercuryException.class, () ->
            Parser.parse("event team meeting /to 4pm"));
    }

    @Test
    public void parse_eventNoTo_exceptionThrown() {
        assertThrows(MercuryException.class, () ->
            Parser.parse("event team meeting /from 2pm"));
    }

    @Test
    public void parse_todoWithMultipleWords_addCommand() throws MercuryException {
        assertTrue(Parser.parse("todo buy groceries and cook dinner") instanceof AddCommand);
    }

    @Test
    public void parse_deadlineWithFullDetails_addCommand() throws MercuryException {
        assertTrue(Parser.parse("deadline finish assignment /by 2026-12-01") instanceof AddCommand);
    }

    @Test
    public void parse_unknownCommand_exceptionThrown() {
        assertThrows(MercuryException.class, () -> Parser.parse("foobar"));
    }

    @Test
    public void parse_emptyString_exceptionThrown() {
        assertThrows(MercuryException.class, () -> Parser.parse(""));
    }
}
