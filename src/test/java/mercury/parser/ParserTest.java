package mercury.parser;

import mercury.MercuryException;
import mercury.command.ExitCommand;
import mercury.command.AddCommand;
import mercury.command.FindCommand;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTest {

    @Test
    public void parse_bye_exitCommand() throws MercuryException {
        assertTrue(Parser.parse("bye") instanceof ExitCommand);
    }

    @Test
    public void parse_todo_addCommand() throws MercuryException {
        assertTrue(Parser.parse("todo read book") instanceof AddCommand);
    }

    @Test
    public void parse_invalidCommand_exceptionThrown() {
        Exception exception = assertThrows(MercuryException.class, () -> {
            Parser.parse("blah");
        });
        assertEquals("I don't understand that command.", exception.getMessage());
    }
    
    @Test
    public void parse_todoNoDescription_exceptionThrown() {
         Exception exception = assertThrows(MercuryException.class, () -> {
            Parser.parse("todo");
        });
        assertEquals("oops todo must be followed by the action item", exception.getMessage());
    }

    @Test
    public void parse_find_findCommand() throws MercuryException {
        assertTrue(Parser.parse("find book") instanceof FindCommand);
    }
}
