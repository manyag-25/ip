package mercury.task;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineTest {

    // Test that deadline task is created correctly with proper formatting
    @Test
    public void toString_deadlineWithDate_formattedCorrectly() {
        LocalDate date = LocalDate.of(2024, 12, 25);
        Deadline deadline = new Deadline("submit assignment", date);
        assertEquals("[D][ ] submit assignment (by: Dec 25 2024)", deadline.toString());
    }

    // Test that marking a deadline as done updates the status icon correctly
    @Test
    public void markAsDone_deadlineTask_statusIconUpdated() {
        LocalDate date = LocalDate.of(2024, 12, 25);
        Deadline deadline = new Deadline("submit assignment", date);

        deadline.markAsDone();

        assertEquals("[D][X] submit assignment (by: Dec 25 2024)", deadline.toString());
    }

    // Test that file string format is correct for saving to storage
    @Test
    public void toFileString_unmarkedDeadline_correctFormat() {
        LocalDate date = LocalDate.of(2024, 12, 25);
        Deadline deadline = new Deadline("submit assignment", date);
        assertEquals("D| |submit assignment|2024-12-25", deadline.toFileString());
    }

    // Test that file string format includes done status when marked
    @Test
    public void toFileString_markedDeadline_includesDoneStatus() {
        LocalDate date = LocalDate.of(2024, 12, 25);
        Deadline deadline = new Deadline("submit assignment", date);
        deadline.markAsDone();
        assertEquals("D|X|submit assignment|2024-12-25", deadline.toFileString());
    }
}
