public class DeleteCommand extends Command {
    private int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws MercuryException {
        Task deletedTask = tasks.delete(index);
        ui.showTaskDeleted(deletedTask, tasks.size());
        storage.save(tasks);
    }
}
