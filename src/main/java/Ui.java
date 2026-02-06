import java.util.Scanner;

public class Ui {
    private Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        String name = "Mercury";
        System.out.println("Hello! I'm " + name + "\n" + "What can I do for you?");
        showLine();
    }

    public void showLine() {
        System.out.println("_________________________________________________");
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showLoadingError() {
        System.out.println("Error loading tasks. Starting with an empty list.");
    }

    public void showError(String message) {
        System.out.println(message);
    }

    public void showTaskAdded(Task task, int size) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + size + " tasks in the list.");
    }

    public void showTaskDeleted(Task task, int size) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + size + " tasks in the list.");
    }

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
    
    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }
}
