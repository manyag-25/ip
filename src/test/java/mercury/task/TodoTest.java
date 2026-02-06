package mercury.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoTest {

    @Test
    public void toString_formatCorrect() {
        Todo todo = new Todo("read book");
        assertEquals("[T][ ] read book", todo.toString());
    }

    @Test
    public void toFileString_formatCorrect() {
        Todo todo = new Todo("read book");
        assertEquals("T| |read book", todo.toFileString());
    }
    
    @Test
    public void markAsDone_toStringUpdated() {
        Todo todo = new Todo("read book");
        todo.markAsDone();
        assertEquals("[T][X] read book", todo.toString());
    }
}
