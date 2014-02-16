/** Jonathan Christiansen
	CSC 220 - 02
	10/28/2012
	Project 2 - Calculator 
    This program opens a text document named "calculator.txt" which follows the form of: 
    A number (for how many commands the program will execute), followed by
    A series of commands with their parameters. A scanner object reads the first number, 
    and allows that number of commands to occur. If any extra commands are picked up, 
    they are ignored. From there, the commands are read into a string object, whose 
    value is compared with that of the commands (+, -, *, /, %, sin, cos, etc) via an 
    if statement. The next values on the line are read into block statements that 
    perform the mathematic operation and assign the final value to a variable called 
    answer. If the command was valid, the whole line is printed, followed by an equals 
    sign, and then the answer, formatted for 3 decimal places. */
	
import java.util.Scanner;
import java.io.*;
import java.text.DecimalFormat;
	
public class Calculator
{
	public static void main (String[] args) throws IOException
	{
		String line;
		Scanner fileScan, lineScan;
		int commands = 0; /** initialized to avoid "not initialized" error, even though
							  the getCommandYet == false if statement always executes. */
		boolean getCommandYet = false;
		DecimalFormat fmt = new DecimalFormat ("0.000");
		
		fileScan = new Scanner (new File("calculator.txt"));

		double answer = 1;

		while (fileScan.hasNext())
		{
			line = fileScan.nextLine();
			
			lineScan = new Scanner (line);
			lineScan.useDelimiter(" "); /** separate functions and values */
			
			if (getCommandYet == false) {
				/** determines the # of commands the program must execute.
				getCommandYet is initialized as false so this if statement retrieves the first number,
				and then becomes true after the number is retrieved. This is so that
				the # of commands does not change whenever the program picks up another
				value throughout the rest of "calculator.txt" */
                                commands = Integer.parseInt(lineScan.next());
                                getCommandYet = true;
                        }
			
			while (lineScan.hasNextLine())
			{
                            String word;
                            word = lineScan.next();
                
                            if (commands > 0)
                            {
				    if (word.equals("+"))
				    {
					   /** addition function */
					   answer = Double.parseDouble(lineScan.next()) + Double.parseDouble(lineScan.next());
					   System.out.println(line + " = " + fmt.format(answer)+ "\n");
				    }
				    else if (word.equals("-"))
				    {
					   /** subtraction function */
					   answer = Double.parseDouble(lineScan.next()) - Double.parseDouble(lineScan.next());
					   System.out.println(line + " = " + fmt.format(answer)+ "\n");
				    }
				    else if (word.equals("*"))
				    {
					   /** multiplication function */
					   answer = Double.parseDouble(lineScan.next()) * Double.parseDouble(lineScan.next());
					   System.out.println(line + " = " + fmt.format(answer)+ "\n");
				    }
				    else if (word.equals("/"))
				    {
					   /** division function */
					   answer = Double.parseDouble(lineScan.next()) / Double.parseDouble(lineScan.next());
					   System.out.println(line + " = " + fmt.format(answer)+ "\n");
				    }
				    else if (word.equals("%"))
				    {
					   /** modulus function */
					   answer = Double.parseDouble(lineScan.next()) % Double.parseDouble(lineScan.next());
					   System.out.println(line + " = " + fmt.format(answer)+ "\n");
				    }
				    else if (word.equals("n!"))
				    {
					   /** factorial function */
					   answer = Double.parseDouble(lineScan.next());
					   double n = answer - 1;
					   while (n>1) {
                                                answer *= n;
                                                n--;
                                            }
                                            System.out.println(line + " = " + fmt.format(answer)+ "\n");
				    }
				    else if (word.equals("sin"))
				    {
					   /** sine function */
					   answer = Math.sin(Math.toRadians(Double.parseDouble(lineScan.next())));
					   System.out.println(line + " = " + fmt.format(answer)+ "\n");
				    }
				    else if (word.equals("cos"))
				    {
					   /** cosine function */
					   answer = Math.cos(Math.toRadians(Double.parseDouble(lineScan.next())));
					   System.out.println(line + " = " + fmt.format(answer)+ "\n");
				    }
				    else if (word.equals("tan"))
				    {
					   /** tan function */
					   answer = Math.tan(Math.toRadians(Double.parseDouble(lineScan.next())));
					   System.out.println(line + " = " + fmt.format(answer)+ "\n");
				    }
				    else if (word.equals("sqrt"))
                                    {
					   /** square root function */
					   answer = Math.sqrt(Double.parseDouble(lineScan.next()));
					   System.out.println(line + " = " + fmt.format(answer)+ "\n");
				    }
				    else if (word.equals("pow"))
				    {
				    	/** exponential function */
				    	answer = Math.pow(Double.parseDouble(lineScan.next()), Double.parseDouble(lineScan.next()));
				    	System.out.println(line + " = " + fmt.format(answer)+ "\n");
                                    }
				    else if (word.equals("abs"))
                                    {
					   /** absolute value function */
					   answer = Math.abs(Double.parseDouble(lineScan.next()));
					   System.out.println(line + " = " + fmt.format(answer)+ "\n");
                                    }
				    else if (word.equals("inv"))
				    {
					   /** inverse function */
					   answer = 1/Double.parseDouble(lineScan.next());
					   System.out.println(line + " = " + fmt.format(answer)+ "\n");
				    }
				    else if (word.equals("max"))
				    {
                                    /** maximum function */
                                    answer = Double.parseDouble(lineScan.next()); /** set answer to first value */
					    while (lineScan.hasNextDouble()) 
                                            {
                                                double compareValue = Double.parseDouble(lineScan.next());
                                                if (answer < compareValue)
                                                    answer = compareValue; /** compare answer to the remaining values */
                                            }
                                            System.out.println(line + " = " + fmt.format(answer)+ "\n");
                                    }
				    else if (word.equals("min"))
				    {
					   /** minimum function */
                        answer = Double.parseDouble(lineScan.next());
					    while (lineScan.hasNextDouble()) 
                        {
                            double compareValue = Double.parseDouble(lineScan.next());
                            if (answer > compareValue)
                                answer = compareValue;
                        }
                        System.out.println(line + " = " + fmt.format(answer)+ "\n");
				    }
				    else if (word.equals("average"))
				    {
					   /** average function */
                        int count = 0;
					    answer = 0;
					    while (lineScan.hasNextDouble()) 
                        {
                            answer += Double.parseDouble(lineScan.next());
                            count++;
                        }
					    if (count != 0) 
                        {
                            answer = answer / count;
                        }
                        System.out.println(line + " = " + fmt.format(answer)+ "\n");
				    } 
				    else 
				    {
                        /** invalid function */  
                        /** incoming numbers must still be processed, or else the
                        	invalid function will infect the other valid functions'
                        	outputs. */ 
                        while (lineScan.hasNext()) 
                        {
                            answer = Double.parseDouble(lineScan.next());
                        }
                    }
                    commands--; /** Detract from commands # even if the command is invalid. */
                } 
			}
		}
	}
}
