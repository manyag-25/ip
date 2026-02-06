package mercury.task;

import mercury.MercuryException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TaskListTest {

    @Test
    public void add_task_success() throws MercuryException {
        TaskList taskList = new TaskList();
        taskList.add(new Todo("test task"));
        assertEquals(1, taskList.size());
        assertEquals("[T][ ] test task", taskList.get(0).toString());
    }

    @Test
    public void delete_task_success() throws MercuryException {
        TaskList taskList = new TaskList();
        taskList.add(new Todo("test task"));
        taskList.delete(0);
        assertEquals(0, taskList.size());
    }

    @Test
    public void delete_invalidIndex_exceptionThrown() {
        TaskList taskList = new TaskList();
        Exception exception = assertThrows(MercuryException.class, () -> {
            taskList.delete(0);
        });
        assertEquals("oops that task number doesn't exist", exception.getMessage());
    }
}
