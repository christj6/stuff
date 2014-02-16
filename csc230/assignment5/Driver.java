
import java.util.Scanner;
/**
 * The Driver class allows the user to search the database for issues,
 * characters, or other data by inputting commands and following various
 * prompts. 
 * 
 * @author Jack Christiansen
 */
public class Driver
{
    /**
     * 
     * @param args 
     */
    public static void main(String[] args)
    {
        QuirkyGraph marvel = new QuirkyGraph();
        marvel.writeFile();
        
        String input = "";
        Scanner scan = new Scanner(System.in);
        
        /**
         * This switch statement allows the user to navigate the program and 
         * search for different types of data.
         */
        while (!input.equals("0"))
        {
            System.out.println("Press 1 to search for issues a given character appeared in.");
            System.out.println("Press 2 to search for characters that appeared in a given issue.");
            System.out.println("Press 3 to find the degree of separation between 2 characters.");
            System.out.println("Press 0 to exit the program.");
            input = scan.nextLine();
            switch (input) {
                case "0":
                    System.out.println("Exiting program.");
                    break;
                case "1":
                    System.out.println("Please type in the character's name. \n");
                    input = scan.nextLine();
                    marvel.findIssues(input.toUpperCase());
                    input = "-1";
                    break;
                case "2":
                    System.out.println("Please type in the name of the issue. \n");
                    input = scan.nextLine();
                    marvel.findCharacters(input.toUpperCase());
                    input = "-1";
                    break;
                case "3":
                    String first = "";
                    String second = "";
                    System.out.println("Please type in the first character's name. \n");
                    input = scan.nextLine();
                    first = input.toUpperCase();
                    System.out.println("Please type in the second character's name. \n");
                    input = scan.nextLine();
                    second = input.toUpperCase();
                    int separation = marvel.shortestPath(first, second);
                    if (separation == 1)
                    {
                        System.out.println(separation + " issue.");
                    }
                    else
                    {
                        System.out.println(separation + " issues.");
                    }
                    input = "-1";
                    break;
                default:
                    System.out.println("Error parsing command.");
                    input = "-1";
                    break;
            }
        }
        
    }
}
