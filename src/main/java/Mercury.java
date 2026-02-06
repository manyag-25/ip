import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Mercury {
    private static final String FILE_PATH = "./data/duke.txt";

    public static void main(String[] args) {
        String name = "Mercury";
        System.out.println("Hello! I'm " + name + "\n" + "What can I do for you?");
        System.out.println("_________________________________________________");
        
        ArrayList<Task> userlist = loadTasks(); // Load tasks on startup

        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        String userinput = myObj.nextLine();  // Read user input
        
        while (!userinput.equals("bye")) {
            System.out.println("_________________________________________________");

            if (userinput.equals("list")) {
                if (userlist.isEmpty()) {
                   System.out.println("No tasks found.");
                } else {
                   System.out.println("Here are the tasks in your list:");
                   for (int i = 0; i < userlist.size(); i++) {
                       System.out.println((i + 1) + ". " + userlist.get(i));
                   }
                }
            }
            else if (userinput.startsWith("mark")) {
                try {
                    int taskIndex = Integer.parseInt(userinput.substring(5)) - 1;
                    if (taskIndex >= 0 && taskIndex < userlist.size()) {
                        Task task = userlist.get(taskIndex);
                        task.markAsDone();
                        saveTasks(userlist); // Save after change
                        System.out.println("I've marked this task as done:\n  " + task);
                    } else {
                        System.out.print("oops plz specify what to mark done");
                    }
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    System.out.println("oops please provide a valid task number");
                }
            }
            else if (userinput.startsWith("unmark")) {
                try {
                    int taskIndex = Integer.parseInt(userinput.substring(7)) - 1;
                    if (taskIndex >= 0 && taskIndex < userlist.size()) {
                        Task task = userlist.get(taskIndex);
                        task.markAsUndone();
                        saveTasks(userlist); // Save after change
                        System.out.println("OK, I've marked this task as not done yet:\n  " + task);
                    } else {
                        System.out.print("oops plz specify what to mark undone");
                    }
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                     System.out.println("oops please provide a valid task number");
                }
            }
            else if (userinput.startsWith("todo ")) {
                String description = userinput.substring(5);
                if (description.isEmpty()){
                    System.out.print("oops todo must be followed by the action item");
                }
                else {
                    Task newTask = new Todo(description);
                    userlist.add(newTask);
                    saveTasks(userlist); // Save after change
                    System.out.println("Got it. I've added this task:");
                    System.out.println(newTask);
                    System.out.println("Now you have " + userlist.size() + " tasks in the list.");
                }
            }
            else if (userinput.startsWith("deadline ")) {
                String rest = userinput.substring(9);
                if (rest.isEmpty()){
                    System.out.print("oops deadline must be followed by the action item");
                }
                else {
                    int byIndex = rest.indexOf("/by");
                    if (byIndex != -1) {
                        String description = rest.substring(0, byIndex).trim();
                        String dateStr = rest.substring(byIndex + 3).trim();
                        try {
                            LocalDate date = LocalDate.parse(dateStr);
                            Task newTask = new Deadline(description, date);
                            userlist.add(newTask);
                            saveTasks(userlist); // Save after change
                            System.out.println("Got it. I've added this task:");
                            System.out.println(newTask);
                            System.out.println("Now you have " + userlist.size() + " tasks in the list.");
                        } catch (DateTimeParseException e) {
                            System.out.println("oops please use yyyy-mm-dd format for deadline (e.g., 2019-12-02)");
                        }
                    } else {
                        System.out.println("oops deadline must include /by");
                    }
                }
            }
            else if (userinput.startsWith("event ")) {
                String rest = userinput.substring(6);
                if (rest.isEmpty()) {
                    System.out.print("oops event must be followed by the action item");
                }
                else {
                    int fromIndex = rest.indexOf("/from");
                    int toIndex = rest.indexOf("/to");
                    if (fromIndex != -1 && toIndex != -1) {
                        String description = rest.substring(0, fromIndex).trim();
                        String fromTime = rest.substring(fromIndex + 5, toIndex).trim();
                        String toTime = rest.substring(toIndex + 3).trim();
                        Task newTask = new Event(description, fromTime, toTime);
                        userlist.add(newTask);
                        saveTasks(userlist); // Save after change
                        System.out.println("Got it. I've added this task:");
                        System.out.println(newTask);
                        System.out.println("Now you have " + userlist.size() + " tasks in the list.");
                    } else {
                        System.out.println("oops event must include /from and /to");
                    }
                }
            }
            else if (userinput.startsWith("delete ")) {
                String indexStr = userinput.substring(7).trim();
                if (indexStr.isEmpty()) {
                    System.out.println("oops delete must be followed by a task number");
                }
                else {
                    try {
                        int taskIndex = Integer.parseInt(indexStr) - 1;
                        if (taskIndex >= 0 && taskIndex < userlist.size()) {
                            Task removedTask = userlist.remove(taskIndex);
                            saveTasks(userlist); // Save after change
                            System.out.println("Noted. I've removed this task:");
                            System.out.println("  " + removedTask);
                            System.out.println("Now you have " + userlist.size() + " tasks in the list.");
                        } else {
                            System.out.println("oops that task number doesn't exist");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("oops please provide a valid task number");
                    }
                }
            }
            else {
                System.out.println("I don't understand that command.");
            }

            System.out.println("_________________________________________________");
            userinput = myObj.nextLine();
        }

        System.out.println("Bye. Hope to see you again soon!");
        myObj.close();
        System.exit(0);
    }

    private static ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return tasks;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\\|");
                // Validate format: at least Type, Status, Description
                if (parts.length < 3) {
                    System.out.println("Warning: Corrupted line ignored: " + line);
                    continue;
                }
                
                String type = parts[0];
                String status = parts[1];
                String description = parts[2];

                Task task = null;
                try {
                    switch (type) {
                        case "T":
                            task = new Todo(description);
                            break;
                        case "D":
                            if (parts.length < 4) {
                                System.out.println("Warning: Corrupted deadline ignored (missing date): " + line);
                                continue;
                            }
                            LocalDate date = LocalDate.parse(parts[3]);
                            task = new Deadline(description, date);
                            break;
                        case "E":
                             if (parts.length < 4) {
                                System.out.println("Warning: Corrupted event ignored (missing time): " + line);
                                continue;
                            }
                            // Parse (from:xxx to:yyy)
                            String timeInfo = parts[3];
                            // Basic parsing assuming format is kept consistent (from: A to: B)
                            // Ideally, save format would be E| |desc|from|to. 
                            // But keeping compatibility with previous style (single time string in parts[3]) for now,
                            // or better: Parse the "(from:xxx to:yyy)" string or just save strict fields?
                            // Let's adjust Event to save strict fields if possible, BUT my previous code saved it as one block.
                            // To be cleaner, let's parse the simple format I used previously: "(from:xxx to:yyy)"
                            // OR simply treat parts[3] as the time string if I was lazy.
                            // Wait, the new Event class I wrote takes `from` and `to`.
                            // I should probably improve the save format for Event to be "E| |desc|from|to" to be cleaner.
                            // BUT, I need to look at how I implemented `toFileString` in Event.java.
                            // Checked Event.java: returns "E|X|desc|(from:x to:y)".
                            // So parts[3] is "(from:x to:y)".
                            // I need to extract x and y.
                            if (timeInfo.startsWith("(from:") && timeInfo.contains(" to:") && timeInfo.endsWith(")")) {
                                int toIndex = timeInfo.indexOf(" to:");
                                String from = timeInfo.substring(6, toIndex); // len("(from:") = 6
                                String to = timeInfo.substring(toIndex + 4, timeInfo.length() - 1); // len(" to:") = 4
                                task = new Event(description, from, to);
                            } else {
                                // Fallback or corrupt
                                System.out.println("Warning: Corrupted event time format ignored: " + line);
                                continue;
                            }
                            break;
                        default:
                            System.out.println("Warning: Unknown task type ignored: " + line);
                            continue;
                    }

                    if (task != null) {
                        if (status.equals("X")) {
                            task.markAsDone();
                        }
                        tasks.add(task);
                    }
                } catch (Exception e) {
                     System.out.println("Warning: Error parsing line ignored: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
        return tasks;
    }

    private static void saveTasks(ArrayList<Task> tasks) {
        try {
            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs(); // Ensure directory exists
            FileWriter writer = new FileWriter(file);
            for (Task task : tasks) {
                writer.write(task.toFileString() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }
}