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

public class MarkUnmarkCommandTest {

    @TempDir
    Path tempDir;

    private Storage makeStorage() {
        return new Storage(tempDir.resolve("tasks.txt").toString());
    }

    private TaskList makeTaskList() throws MercuryException {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("task one"));
        tasks.add(new Todo("task two"));
        return tasks;
    }

    @Test
    public void markCommand_marksTaskDone() throws MercuryException {
        TaskList tasks = makeTaskList();
        Ui ui = new Ui();
        Storage storage = makeStorage();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        new MarkCommand(0).execute(tasks, ui, storage);
        System.setOut(System.out);

        assertEquals("[T][X] task one", tasks.get(0).toString());
    }

    @Test
    public void markCommand_invalidIndex_exceptionThrown() {
        TaskList tasks = new TaskList();
        Ui ui = new Ui();
        Storage storage = makeStorage();

        assertThrows(MercuryException.class, () -> {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            System.setOut(new PrintStream(out));
            try {
                new MarkCommand(5).execute(tasks, ui, storage);
            } finally {
                System.setOut(System.out);
            }
        });
    }

    @Test
    public void unmarkCommand_marksTaskNotDone() throws MercuryException {
        TaskList tasks = makeTaskList();
        tasks.get(0).markAsDone();
        Ui ui = new Ui();
        Storage storage = makeStorage();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        new UnmarkCommand(0).execute(tasks, ui, storage);
        System.setOut(System.out);

        assertEquals("[T][ ] task one", tasks.get(0).toString());
    }

    @Test
    public void unmarkCommand_invalidIndex_exceptionThrown() {
        TaskList tasks = new TaskList();
        Ui ui = new Ui();
        Storage storage = makeStorage();

        assertThrows(MercuryException.class, () -> {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            System.setOut(new PrintStream(out));
            try {
                new UnmarkCommand(0).execute(tasks, ui, storage);
            } finally {
                System.setOut(System.out);
            }
        });
    }
}
