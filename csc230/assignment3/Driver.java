
import java.util.Scanner;
/**
 * The Driver class creates an instance of the BaseCon class, which allows the
 * user to input a value for both input and the base. The input value is
 * converted from base 10 to the input base.
 * @author Jack Christiansen
 */
public class Driver
{
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        int input = 6;
        int base = 2;
        
        try
        {
            System.out.print("Please enter a number in decimal notation: ");
            input = scan.nextInt();
            while (input < 0)
            {
                System.out.print("Input should be a positive integer: ");
                input = scan.nextInt();
            }

            System.out.print("Please enter the base of the number system to convert to: ");
            base = scan.nextInt();
            while (base < 2 || base > 9)
            {
                System.out.print("Base should be between 2 and 9: ");
                base = scan.nextInt();
            }
        }
        catch (Exception e)
        {
            System.out.print("");
            System.out.print("Invalid input");
        }
        
        BaseCon conversion = new BaseCon(input, base);
        
        System.out.println("The number " + input + " in base " + base + " notation is: " + conversion.convert());
        
    }
}
