import java.util.ArrayList;
import java.util.Scanner;

public class Mercury {
    public static void main(String[] args) {
        String name = "Mercury";
        System.out.println("Hello! I'm " + name + "\n" + "What can I do for you?");
        System.out.println("_________________________________________________");
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        String userinput = myObj.nextLine();  // Read user input
        ArrayList userlist = new ArrayList();
        while (!userinput.equals("bye")) {
            System.out.println("_________________________________________________");
            if (userinput.equals("list")) {
                System.out.println(userinput);
                for (int i = 0; i < userlist.size(); i++) {
                    System.out.println(i + ". " + userlist.get(i));
                }
            }
            else {
                System.out.println("added: " + userinput);
                userlist.add(userinput);}
            System.out.println("_________________________________________________");
            userinput = myObj.nextLine();
        }
        System.out.println("Bye. Hope to see you again soon!");
        System.exit(0);
    }
}