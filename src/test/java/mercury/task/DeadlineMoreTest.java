package mercury.task;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeadlineMoreTest {

    @Test
    public void toString_notDone_correctFormat() {
        Deadline deadline = new Deadline("submit report", LocalDate.of(2026, 3, 1));
        assertEquals("[D][ ] submit report (by: Mar 1 2026)", deadline.toString());
    }

    @Test
    public void toString_done_correctFormat() {
        Deadline deadline = new Deadline("submit report", LocalDate.of(2026, 3, 1));
        deadline.markAsDone();
        assertEquals("[D][X] submit report (by: Mar 1 2026)", deadline.toString());
    }

    @Test
    public void toFileString_notDone_correctFormat() {
        Deadline deadline = new Deadline("submit report", LocalDate.of(2026, 3, 1));
        assertEquals("D| |submit report|2026-03-01", deadline.toFileString());
    }

    @Test
    public void toFileString_done_correctFormat() {
        Deadline deadline = new Deadline("submit report", LocalDate.of(2026, 3, 1));
        deadline.markAsDone();
        assertEquals("D|X|submit report|2026-03-01", deadline.toFileString());
    }

    @Test
    public void markAsUndone_afterMarkDone_statusReverts() {
        Deadline deadline = new Deadline("submit report", LocalDate.of(2026, 3, 1));
        deadline.markAsDone();
        assertTrue(deadline.isDone);
        deadline.markAsUndone();
        assertFalse(deadline.isDone);
    }

    @Test
    public void getStatusIcon_notDone_returnsSpace() {
        Deadline deadline = new Deadline("test", LocalDate.of(2026, 1, 1));
        assertEquals(" ", deadline.getStatusIcon());
    }

    @Test
    public void getStatusIcon_done_returnsX() {
        Deadline deadline = new Deadline("test", LocalDate.of(2026, 1, 1));
        deadline.markAsDone();
        assertEquals("X", deadline.getStatusIcon());
    }
}
