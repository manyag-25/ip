import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Mercury {
    private static final String FILE_PATH = "./data/duke.txt";

    public static void main(String[] args) {
        String name = "Mercury";
        System.out.println("Hello! I'm " + name + "\n" + "What can I do for you?");
        System.out.println("_________________________________________________");
        
        ArrayList<String[]> userlist = loadTasks(); // Load tasks on startup

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
                       String[] task = userlist.get(i);
                       System.out.print((i + 1) + ". [" + task[0] + "][" + task[1] + "] " + task[2]);
                       if (!task[3].isEmpty()) {
                           System.out.print(" " + task[3]);
                       }
                       System.out.println();
                   }
                }
            }
            else if (userinput.startsWith("mark")) {
                try {
                    int taskIndex = Integer.parseInt(userinput.substring(5)) - 1;
                    if (taskIndex >= 0 && taskIndex < userlist.size()) {
                        userlist.get(taskIndex)[1] = "X";
                        saveTasks(userlist); // Save after change
                        String[] task = userlist.get(taskIndex);
                        System.out.print("I've marked this task as done:\n  [" + task[0] + "][X] " + task[2]);
                        if (!task[3].isEmpty()) {
                            System.out.print(" " + task[3]);
                        }
                        System.out.println();
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
                        userlist.get(taskIndex)[1] = " ";
                        saveTasks(userlist); // Save after change
                        String[] task = userlist.get(taskIndex);
                        System.out.print("OK, I've marked this task as not done yet:\n  [" + task[0] + "][ ] " + task[2]);
                        if (!task[3].isEmpty()) {
                            System.out.print(" " + task[3]);
                        }
                        System.out.println();
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
                    String[] newTask = {"T", " ", description, ""};
                    userlist.add(newTask);
                    saveTasks(userlist); // Save after change
                    System.out.println("Got it. I've added this task:");
                    System.out.println("[T][ ] " + description);
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
                        String datetime = "(by:" + rest.substring(byIndex + 3).trim() + ")";
                        String[] newTask = {"D", " ", description, datetime};
                        userlist.add(newTask);
                        saveTasks(userlist); // Save after change
                        System.out.println("Got it. I've added this task:");
                        System.out.println("[D][ ] " + description + " " + datetime);
                        System.out.println("Now you have " + userlist.size() + " tasks in the list.");
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
                        String datetime = "(from:" + fromTime + " to:" + toTime + ")";
                        String[] newTask = {"E", " ", description, datetime};
                        userlist.add(newTask);
                        saveTasks(userlist); // Save after change
                        System.out.println("Got it. I've added this task:");
                        System.out.println("[E][ ] " + description + " " + datetime);
                        System.out.println("Now you have " + userlist.size() + " tasks in the list.");
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
                            String[] removedTask = userlist.get(taskIndex);
                            userlist.remove(taskIndex);
                            saveTasks(userlist); // Save after change
                            System.out.println("Noted. I've removed this task:");
                            System.out.print("  [" + removedTask[0] + "][" + removedTask[1] + "] " + removedTask[2]);
                            if (!removedTask[3].isEmpty()) {
                                System.out.print(" " + removedTask[3]);
                            }
                            System.out.println();
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

    private static ArrayList<String[]> loadTasks() {
        ArrayList<String[]> tasks = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return tasks;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\\|");
                // Validate format: at least Type, Status, Description
                if (parts.length < 3 || 
                    (!parts[0].equals("T") && !parts[0].equals("D") && !parts[0].equals("E")) ||
                    (!parts[1].equals(" ") && !parts[1].equals("X"))) {
                    System.out.println("Warning: Corrupted line ignored: " + line);
                    continue;
                }

                // Additional validation for Deadline and Event (must have time)
                if ((parts[0].equals("D") || parts[0].equals("E")) && parts.length < 4) {
                     System.out.println("Warning: Corrupted line ignored (missing time): " + line);
                     continue;
                }
                
                String type = parts[0];
                String status = parts[1];
                String description = parts[2];
                String time = (parts.length > 3) ? parts[3] : "";
                tasks.add(new String[]{type, status, description, time});
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
        return tasks;
    }

    private static void saveTasks(ArrayList<String[]> tasks) {
        try {
            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs(); // Ensure directory exists
            FileWriter writer = new FileWriter(file);
            for (String[] task : tasks) {
                // Format: Type|Status|Description|Time
                String line = task[0] + "|" + task[1] + "|" + task[2] + "|" + task[3] + "\n";
                writer.write(line);
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }
}