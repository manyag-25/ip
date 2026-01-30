import java.util.Scanner;

public class Mercury {
    public static void main(String[] args) {
        String name = "Mercury";
        System.out.println("Hello! I'm " + name + "\n" + "What can I do for you?");
        System.out.println("_________________________________________________");
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        String userinput = myObj.nextLine();  // Read user input
        while (!userinput.equals("bye")) {
            System.out.println("_________________________________________________");
            System.out.println(userinput);
            System.out.println("_________________________________________________");
            userinput = myObj.nextLine();
        }
        System.out.println("Bye. Hope to see you again soon!");
        System.exit(0);
    }
}
