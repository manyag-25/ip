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
                    System.out.println((i + 1) + ". [" + task[1] + "] " + task[0]);
                }
            }
            else if (userinput.startsWith("mark")) {
                int taskIndex = Integer.parseInt(userinput.substring(5)) - 1;
                if (taskIndex >= 0 && taskIndex < userlist.size()) {
                    userlist.get(taskIndex)[1] = "X";
                    System.out.println("I've marked this task as done:\n" +
                            "[X] " + userlist.get(taskIndex)[0]);
                }
            }
            else if (userinput.startsWith("unmark")) {
                int taskIndex = Integer.parseInt(userinput.substring(7)) - 1;
                if (taskIndex >= 0 && taskIndex < userlist.size()) {
                    userlist.get(taskIndex)[1] = " ";
                    System.out.println("OK, I've marked this task as not done yet:\n" +
                            "[ ] " + userlist.get(taskIndex)[0]);
                }
            }
            else {
                String[] newTask = {userinput, " "};
                userlist.add(newTask);
                System.out.println("added: " + userinput);
            }

            System.out.println("_________________________________________________");
            userinput = myObj.nextLine();
        }

        System.out.println("Bye. Hope to see you again soon!");
        myObj.close();
        System.exit(0);
    }
}