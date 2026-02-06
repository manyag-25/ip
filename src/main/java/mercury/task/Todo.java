package mercury.task;

/**
 * Represents a todo task without any date/time constraint.
 */
public class Todo extends Task {

    /**
     * Constructs a Todo task with the specified description.
     *
     * @param description The description of the todo task.
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toFileString() {
        return "T|" + getStatusIcon() + "|" + description;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
