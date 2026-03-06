package mercury.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventMoreTest {

    @Test
    public void toString_notDone_correctFormat() {
        Event event = new Event("team meeting", "2pm", "4pm");
        assertEquals("[E][ ] team meeting (from: 2pm to: 4pm)", event.toString());
    }

    @Test
    public void toString_done_correctFormat() {
        Event event = new Event("team meeting", "2pm", "4pm");
        event.markAsDone();
        assertEquals("[E][X] team meeting (from: 2pm to: 4pm)", event.toString());
    }

    @Test
    public void toFileString_notDone_correctFormat() {
        Event event = new Event("team meeting", "2pm", "4pm");
        assertEquals("E| |team meeting|(from:2pm to:4pm)", event.toFileString());
    }

    @Test
    public void toFileString_done_correctFormat() {
        Event event = new Event("team meeting", "2pm", "4pm");
        event.markAsDone();
        assertEquals("E|X|team meeting|(from:2pm to:4pm)", event.toFileString());
    }

    @Test
    public void markAsUndone_afterMarkDone_statusReverts() {
        Event event = new Event("team meeting", "2pm", "4pm");
        event.markAsDone();
        assertTrue(event.isDone);
        event.markAsUndone();
        assertFalse(event.isDone);
    }

    @Test
    public void toString_withDateTimes_correctFormat() {
        Event event = new Event("project demo", "2026-03-01 10:00", "2026-03-01 12:00");
        assertEquals("[E][ ] project demo (from: 2026-03-01 10:00 to: 2026-03-01 12:00)", event.toString());
    }
}
