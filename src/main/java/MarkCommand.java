public class MarkCommand extends Command {
    private int index;

    public MarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws MercuryException {
        Task task = tasks.get(index);
        task.markAsDone();
        System.out.println("I've marked this task as done:\n  " + task);
        storage.save(tasks);
    }
}
