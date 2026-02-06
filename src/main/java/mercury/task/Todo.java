package mercury.task;

public class Todo extends Task {

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
