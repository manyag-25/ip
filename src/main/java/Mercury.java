import java.util.ArrayList;
import java.util.Scanner;

public class Mercury {
    public static void main(String[] args) {
        String name = "Mercury";
        System.out.println("Hello! I'm " + name + "\n" + "What can I do for you?");
        System.out.println("_________________________________________________");
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        String userinput = myObj.nextLine();  // Read user input
        ArrayList<String[]> userlist = new ArrayList<>();

        while (!userinput.equals("bye")) {
            System.out.println("_________________________________________________");

            if (userinput.equals("list")) {
                System.out.println(userinput);
                for (int i = 0; i < userlist.size(); i++) {
                    String[] task = userlist.get(i);
                    System.out.print((i + 1) + ". [" + task[0] + "][" + task[1] + "] " + task[2]);
                    if (!task[3].isEmpty()) {
                        System.out.print(" " + task[3]);
                    }
                    System.out.println();
                }
            }
            else if (userinput.startsWith("mark")) {
                int taskIndex = Integer.parseInt(userinput.substring(5)) - 1;
                if (taskIndex >= 0 && taskIndex < userlist.size()) {
                    userlist.get(taskIndex)[1] = "X";
                    String[] task = userlist.get(taskIndex);
                    System.out.print("I've marked this task as done:\n  [" + task[0] + "][X] " + task[2]);
                    if (!task[3].isEmpty()) {
                        System.out.print(" " + task[3]);
                    }
                    System.out.println();
                }
                else {System.out.print("oops plz specify what to mark done");}
            }
            else if (userinput.startsWith("unmark")) {
                int taskIndex = Integer.parseInt(userinput.substring(7)) - 1;
                if (taskIndex >= 0 && taskIndex < userlist.size()) {
                    userlist.get(taskIndex)[1] = " ";
                    String[] task = userlist.get(taskIndex);
                    System.out.print("OK, I've marked this task as not done yet:\n  [" + task[0] + "][ ] " + task[2]);
                    if (!task[3].isEmpty()) {
                        System.out.print(" " + task[3]);
                    }
                    System.out.println();
                }
                else {System.out.print("oops plz specify what to mark undone");}
            }
            else if (userinput.startsWith("todo ")) {
                String description = userinput.substring(5);
                if (description.isEmpty()){
                    System.out.print("oops todo must be followed by the action item");
                }
                String[] newTask = {"T", " ", description, ""};
                userlist.add(newTask);
                System.out.println("Got it. I've added this task:");
                System.out.println("[T][ ] " + description);
                System.out.println("Now you have " + userlist.size() + " tasks in the list.");
            }
            else if (userinput.startsWith("deadline ")) {
                String rest = userinput.substring(9);
                if (rest.isEmpty()){
                    System.out.print("oops deadline must be followed by the action item");
                }
                int byIndex = rest.indexOf("/by");
                if (byIndex != -1) {
                    String description = rest.substring(0, byIndex).trim();
                    String datetime = "(by:" + rest.substring(byIndex + 3).trim() + ")";
                    String[] newTask = {"D", " ", description, datetime};
                    userlist.add(newTask);
                    System.out.println("Got it. I've added this task:");
                    System.out.println("[D][ ] " + description + " " + datetime);
                    System.out.println("Now you have " + userlist.size() + " tasks in the list.");
                }
            }
            else if (userinput.startsWith("event ")) {
                String rest = userinput.substring(6);
                if (rest.isEmpty()){
                    System.out.print("oops event must be followed by the action item");
                }
                int fromIndex = rest.indexOf("/from");
                int toIndex = rest.indexOf("/to");
                if (fromIndex != -1 && toIndex != -1) {
                    String description = rest.substring(0, fromIndex).trim();
                    String fromTime = rest.substring(fromIndex + 5, toIndex).trim();
                    String toTime = rest.substring(toIndex + 3).trim();
                    String datetime = "(from:" + fromTime + " to:" + toTime + ")";
                    String[] newTask = {"E", " ", description, datetime};
                    userlist.add(newTask);
                    System.out.println("Got it. I've added this task:");
                    System.out.println("[E][ ] " + description + " " + datetime);
                    System.out.println("Now you have " + userlist.size() + " tasks in the list.");
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
}