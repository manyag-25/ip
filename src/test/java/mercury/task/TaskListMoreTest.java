package mercury.task;

import mercury.MercuryException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TaskListMoreTest {

    @Test
    public void get_validIndex_returnsTask() throws MercuryException {
        TaskList taskList = new TaskList();
        Task t = new Todo("read book");
        taskList.add(t);
        assertEquals(t, taskList.get(0));
    }

    @Test
    public void get_invalidIndex_exceptionThrown() {
        TaskList taskList = new TaskList();
        assertThrows(MercuryException.class, () -> taskList.get(0));
    }

    @Test
    public void get_indexBeyondSize_exceptionThrown() {
        TaskList taskList = new TaskList();
        taskList.add(new Todo("only task"));
        assertThrows(MercuryException.class, () -> taskList.get(99));
    }

    @Test
    public void delete_returnsRemovedTask() throws MercuryException {
        TaskList taskList = new TaskList();
        Task t = new Todo("buy milk");
        taskList.add(t);
        Task deleted = taskList.delete(0);
        assertEquals(t, deleted);
        assertEquals(0, taskList.size());
    }

    @Test
    public void delete_outOfBoundsIndex_exceptionThrown() {
        TaskList taskList = new TaskList();
        taskList.add(new Todo("task A"));
        assertThrows(MercuryException.class, () -> taskList.delete(5));
    }

    @Test
    public void add_multipleTaskTypes_sizeCorrect() throws MercuryException {
        TaskList taskList = new TaskList();
        taskList.add(new Todo("todo"));
        taskList.add(new Deadline("report", LocalDate.of(2026, 3, 1)));
        taskList.add(new Event("meeting", "9am", "10am"));
        assertEquals(3, taskList.size());
    }

    @Test
    public void getAllTasks_returnsAllTasks() throws MercuryException {
        TaskList taskList = new TaskList();
        taskList.add(new Todo("a"));
        taskList.add(new Todo("b"));
        assertEquals(2, taskList.getAllTasks().size());
    }

    @Test
    public void size_emptyList_returnsZero() {
        TaskList taskList = new TaskList();
        assertEquals(0, taskList.size());
    }
}
