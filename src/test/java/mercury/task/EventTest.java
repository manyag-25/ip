package mercury.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {

    // Test that event task displays time range correctly
    @Test
    public void toString_eventWithTimeRange_formattedCorrectly() {
        Event event = new Event("team meeting", "2pm", "4pm");
        assertEquals("[E][ ] team meeting (from: 2pm to: 4pm)", event.toString());
    }

    // Test that marking an event as done changes the status appropriately
    @Test
    public void markAsDone_eventTask_statusChanges() {
        Event event = new Event("team meeting", "2pm", "4pm");

        event.markAsDone();

        assertEquals("[E][X] team meeting (from: 2pm to: 4pm)", event.toString());
    }

    // Test that file string format preserves event time information
    @Test
    public void toFileString_unmarkedEvent_preservesTimeInfo() {
        Event event = new Event("team meeting", "2pm", "4pm");
        assertEquals("E| |team meeting|(from:2pm to:4pm)", event.toFileString());
    }

    // Test that unmarking a previously marked event reverts the status
    @Test
    public void markAsUndone_markedEvent_statusReverted() {
        Event event = new Event("team meeting", "2pm", "4pm");
        event.markAsDone();

        event.markAsUndone();

        assertEquals("[E][ ] team meeting (from: 2pm to: 4pm)", event.toString());
    }
}
