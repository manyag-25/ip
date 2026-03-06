package mercury.storage;

import mercury.MercuryException;
import mercury.task.Deadline;
import mercury.task.Event;
import mercury.task.Task;
import mercury.task.TaskList;
import mercury.task.Todo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StorageTest {

    @TempDir
    Path tempDir;

    private String tempFile() {
        return tempDir.resolve("tasks.txt").toString();
    }

    @Test
    public void load_emptyFile_returnsEmptyList() throws MercuryException {
        Storage storage = new Storage(tempDir.resolve("nonexistent.txt").toString());
        ArrayList<Task> tasks = storage.load();
        assertTrue(tasks.isEmpty());
    }

    @Test
    public void saveAndLoad_todo_preservesData() throws MercuryException {
        Storage storage = new Storage(tempFile());
        TaskList tasks = new TaskList();
        tasks.add(new Todo("read book"));
        storage.save(tasks);

        ArrayList<Task> loaded = storage.load();
        assertEquals(1, loaded.size());
        assertEquals("[T][ ] read book", loaded.get(0).toString());
    }

    @Test
    public void saveAndLoad_deadlineDone_preservesDoneStatus() throws MercuryException {
        Storage storage = new Storage(tempFile());
        TaskList tasks = new TaskList();
        Deadline deadline = new Deadline("submit report", LocalDate.of(2026, 3, 1));
        deadline.markAsDone();
        tasks.add(deadline);
        storage.save(tasks);

        ArrayList<Task> loaded = storage.load();
        assertEquals(1, loaded.size());
        assertEquals("[D][X] submit report (by: Mar 1 2026)", loaded.get(0).toString());
    }

    @Test
    public void saveAndLoad_event_preservesData() throws MercuryException {
        Storage storage = new Storage(tempFile());
        TaskList tasks = new TaskList();
        tasks.add(new Event("team meeting", "2pm", "4pm"));
        storage.save(tasks);

        ArrayList<Task> loaded = storage.load();
        assertEquals(1, loaded.size());
        assertEquals("[E][ ] team meeting (from: 2pm to: 4pm)", loaded.get(0).toString());
    }

    @Test
    public void saveAndLoad_multipleTasks_allPreserved() throws MercuryException {
        Storage storage = new Storage(tempFile());
        TaskList tasks = new TaskList();
        tasks.add(new Todo("task A"));
        tasks.add(new Deadline("deadline B", LocalDate.of(2026, 6, 15)));
        tasks.add(new Event("event C", "9am", "10am"));
        storage.save(tasks);

        ArrayList<Task> loaded = storage.load();
        assertEquals(3, loaded.size());
        assertEquals("[T][ ] task A", loaded.get(0).toString());
        assertEquals("[D][ ] deadline B (by: Jun 15 2026)", loaded.get(1).toString());
        assertEquals("[E][ ] event C (from: 9am to: 10am)", loaded.get(2).toString());
    }

    @Test
    public void save_overwritesPreviousData() throws MercuryException {
        Storage storage = new Storage(tempFile());
        TaskList tasks1 = new TaskList();
        tasks1.add(new Todo("old task"));
        storage.save(tasks1);

        TaskList tasks2 = new TaskList();
        tasks2.add(new Todo("new task"));
        storage.save(tasks2);

        ArrayList<Task> loaded = storage.load();
        assertEquals(1, loaded.size());
        assertEquals("[T][ ] new task", loaded.get(0).toString());
    }
}
