package mercury.command;

import mercury.MercuryException;
import mercury.storage.Storage;
import mercury.task.Task;
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

public class AddCommandTest {

    @TempDir
    Path tempDir;

    private Storage makeStorage() {
        return new Storage(tempDir.resolve("tasks.txt").toString());
    }

    @Test
    public void execute_addsTodoToTaskList() throws MercuryException {
        TaskList tasks = new TaskList();
        Ui ui = new Ui();
        Storage storage = makeStorage();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        new AddCommand(new Todo("read book")).execute(tasks, ui, storage);
        System.setOut(System.out);

        assertEquals(1, tasks.size());
    }

    @Test
    public void execute_taskAddedHasCorrectDescription() throws MercuryException {
        TaskList tasks = new TaskList();
        Ui ui = new Ui();
        Storage storage = makeStorage();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        new AddCommand(new Todo("buy groceries")).execute(tasks, ui, storage);
        System.setOut(System.out);

        Task added = tasks.get(0);
        assertEquals("[T][ ] buy groceries", added.toString());
    }

    @Test
    public void execute_addMultipleTasks_sizeIncrements() throws MercuryException {
        TaskList tasks = new TaskList();
        Ui ui = new Ui();
        Storage storage = makeStorage();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        new AddCommand(new Todo("task 1")).execute(tasks, ui, storage);
        new AddCommand(new Todo("task 2")).execute(tasks, ui, storage);
        System.setOut(System.out);

        assertEquals(2, tasks.size());
    }
}
