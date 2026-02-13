package mercury.ui;

import mercury.task.Task;
import mercury.task.TaskList;
import mercury.MercuryException;
import java.util.Scanner;
import java.util.List;

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
        String name = "Mercury";
        System.out.println("Hello! I'm " + name + "\n" + "What can I do for you?");
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
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + size + " tasks in the list.");
    }

    /**
     * Displays a message confirming a task has been deleted.
     *
     * @param task The task that was deleted.
     * @param size The new total number of tasks.
     */
    public void showTaskDeleted(Task task, int size) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + size + " tasks in the list.");
    }

    /**
     * Displays a message confirming a task has been marked as done.
     *
     * @param task The task that was marked.
     */
    public void showTaskMarked(Task task) {
        System.out.println("I've marked this task as done:");
        System.out.println("  " + task);
    }

    /**
     * Displays a message confirming a task has been marked as not done.
     *
     * @param task The task that was unmarked.
     */
    public void showTaskUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
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
            for (int i = 0; i < tasks.size(); i++) {
                try {
                    System.out.println((i + 1) + ". " + tasks.get(i));
                } catch (MercuryException e) {
                    // This shouldn't happen while iterating size()
                }
            }
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
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * Displays a motivational quote to the user.
     *
     * @param quote The motivational quote to display.
     */
    public void showCheerMessage(String quote) {
        System.out.println(quote);
    }
}
