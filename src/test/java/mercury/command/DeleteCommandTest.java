package mercury.command;

import mercury.MercuryException;
import mercury.storage.Storage;
import mercury.task.TaskList;
import mercury.task.Todo;
import mercury.ui.Ui;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeleteCommandTest {

    @TempDir
    Path tempDir;

    private Storage makeStorage() {
        return new Storage(tempDir.resolve("tasks.txt").toString());
    }

    @Test
    public void execute_deletesCorrectTask() throws MercuryException {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("task A"));
        tasks.add(new Todo("task B"));
        Ui ui = new Ui();
        Storage storage = makeStorage();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        new DeleteCommand(0).execute(tasks, ui, storage);
        System.setOut(System.out);

        assertEquals(1, tasks.size());
        assertEquals("[T][ ] task B", tasks.get(0).toString());
    }

    @Test
    public void execute_invalidIndex_exceptionThrown() {
        TaskList tasks = new TaskList();
        Ui ui = new Ui();
        Storage storage = makeStorage();

        assertThrows(MercuryException.class, () -> {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            System.setOut(new PrintStream(out));
            try {
                new DeleteCommand(0).execute(tasks, ui, storage);
            } finally {
                System.setOut(System.out);
            }
        });
    }

    @Test
    public void execute_deleteAll_listBecomesEmpty() throws MercuryException {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("task X"));
        Ui ui = new Ui();
        Storage storage = makeStorage();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        new DeleteCommand(0).execute(tasks, ui, storage);
        System.setOut(System.out);

        assertEquals(0, tasks.size());
    }
}
