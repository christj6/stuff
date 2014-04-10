
import java.text.DecimalFormat;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Used content from: http://stackoverflow.com/questions/1410741/want-to-invoke-a-linux-shell-command-from-java
// And a bit from: http://stackoverflow.com/questions/9993873/java-executing-linux-command

public class Clock
{

	public static void main (String[] args) throws IOException
	{
		long start;
		long end;
		List<String> commands = new ArrayList<String>();
		String directory = "/Users/Jack/Documents/GitHub/stuff/csc345";

		
		commands.add("ls");


		// Run macro on target
		ProcessBuilder pb = new ProcessBuilder(commands);
	        pb.directory(new File(directory));
	        pb.redirectErrorStream(true);
	
	        start = System.nanoTime();
	        Process process = pb.start();
	        end = System.nanoTime(); // measured in nanoseconds
	
	        // read output
	        output(new InputStreamReader(process.getInputStream()));
	
	        
	
			System.out.println("\nTime " + (end - start)/1000000000f + " seconds.");
		}
	
		public void shell()
		{
			Scanner scan = new Scanner(System.in);
	
		}
	
		public static void output(InputStreamReader stream) throws IOException
		{
			StringBuilder out = new StringBuilder();
	        //BufferedReader br = new BufferedReader(stream);
	        BufferedReader br = new BufferedReader(stream);
	        String line = null, previous = null;
	        while ((line = br.readLine()) != null)
	        {
	        	if (!line.equals(previous)) 
	        	{
	                previous = line;
	                out.append(line).append('\n');
	                System.out.println(line);
	            }
	        }
	}
}
