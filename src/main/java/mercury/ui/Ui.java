package mercury.ui;

import mercury.task.Task;
import mercury.task.TaskList;
import mercury.MercuryException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * Handles interactions with the user.
 * Responsible for reading commands and displaying messages.
 */
public class Ui {
    private Scanner scanner;

    /**
     * Constructs a new Ui instance.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the welcome message to the user.
     */
    public void showWelcome() {
        System.out.println("Greetings! I'm Mercury — swift messenger of the gods.");
        System.out.println("Your tasks are my missions. What shall I carry for you today?");
        showLine();
    }

    /**
     * Displays a horizontal line separator.
     */
    public void showLine() {
        System.out.println("_________________________________________________");
    }

    /**
     * Reads the next command from the user.
     *
     * @return The command string entered by the user.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays an error message when tasks fail to load.
     */
    public void showLoadingError() {
        System.out.println("Error loading tasks. Starting with an empty list.");
    }

    /**
     * Displays a specific error message.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        System.out.println(message);
    }

    /**
     * Displays a message confirming a task has been added.
     *
     * @param task The task that was added.
     * @param size The new total number of tasks.
     */
    public void showTaskAdded(Task task, int size) {
        System.out.println("Mission logged! Swift delivery guaranteed:");
        System.out.println("  " + task);
        System.out.println("You now have " + size + " mission(s) in your scroll.");
    }

    /**
     * Displays a message confirming a task has been deleted.
     *
     * @param task The task that was deleted.
     * @param size The new total number of tasks.
     */
    public void showTaskDeleted(Task task, int size) {
        System.out.println("Message recalled. This mission has been erased:");
        System.out.println("  " + task);
        System.out.println("You now have " + size + " mission(s) remaining.");
    }

    /**
     * Displays a message confirming a task has been marked as done.
     *
     * @param task The task that was marked.
     */
    public void showTaskMarked(Task task) {
        System.out.println("Delivered! Mission accomplished:");
        System.out.println("  " + task);
    }

    /**
     * Displays a message confirming a task has been marked as not done.
     *
     * @param task The task that was unmarked.
     */
    public void showTaskUnmarked(Task task) {
        System.out.println("Understood — mission back in transit:");
        System.out.println("  " + task);
    }

    /**
     * Displays the list of all tasks.
     *
     * @param tasks The list of tasks to display.
     */
    public void showTaskList(TaskList tasks) {
        if (tasks.size() == 0) {
            System.out.println("No tasks found.");
        } else {
            System.out.println("Here are the tasks in your list:");
            IntStream.range(0, tasks.size()).forEach(i -> {
                try {
                    System.out.println((i + 1) + ". " + tasks.get(i));
                } catch (MercuryException e) {
                    // This should not happen while iterating size()
                }
            });
        }
    }

    /**
     * Displays a list of tasks that matched a search query.
     *
     * @param matchingTasks The list of tasks to display.
     */
    public void showMatchingTasks(List<Task> matchingTasks) {
        if (matchingTasks.isEmpty()) {
            System.out.println("No matching tasks found.");
        } else {
            System.out.println("Here are the matching tasks in your list:");
            for (int i = 0; i < matchingTasks.size(); i++) {
                System.out.println((i + 1) + ". " + matchingTasks.get(i));
            }
        }
    }
    
    /**
     * Displays the goodbye message to the user.
     */
    public void showGoodbye() {
        System.out.println("Until next time! May your path be swift and your tasks few.");
    }

    /**
     * Displays a motivational quote to the user.
     *
     * @param quote The motivational quote to display.
     */
    public void showCheerMessage(String quote) {
        System.out.println(quote);
    }

    /**
     * Displays the list of supported commands.
     */
    public void showHelp() {
        System.out.println("Mercury's Message Board — Available Commands:");
        System.out.println("  help                                   — view this scroll");
        System.out.println("  todo <description>                     — log a simple mission");
        System.out.println("  deadline <description> /by <yyyy-mm-dd> — mission with a deadline");
        System.out.println("  event <description> /from <time> /to <time> — timed mission");
        System.out.println("  list                                   — read all missions");
        System.out.println("  mark <task number>                     — mark mission complete");
        System.out.println("  unmark <task number>                   — mark mission incomplete");
        System.out.println("  delete <task number>                   — recall a mission");
        System.out.println("  find <keyword>                         — search the scroll");
        System.out.println("  stats                                  — view mission statistics");
        System.out.println("  cheer                                  — receive divine inspiration");
        System.out.println("  bye                                    — dismiss Mercury");
    }
}
