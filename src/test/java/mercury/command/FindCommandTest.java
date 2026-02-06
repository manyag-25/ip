package mercury.command;

import mercury.MercuryException;
import mercury.storage.Storage;
import mercury.task.TaskList;
import mercury.task.Todo;
import mercury.ui.Ui;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FindCommandTest {
    @Test
    public void execute_findKeyword_success() throws MercuryException {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("read book"));
        tasks.add(new Todo("return book"));
        tasks.add(new Todo("buy grocery"));
        
        Ui ui = new Ui();
        Storage storage = new Storage("./data/test.txt");
        
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        
        Command findCommand = new FindCommand("book");
        findCommand.execute(tasks, ui, storage);
        
        String output = outContent.toString();
        assertTrue(output.contains("read book"));
        assertTrue(output.contains("return book"));
        assertTrue(!output.contains("buy grocery"));
        
        System.setOut(System.out);
    }
}
