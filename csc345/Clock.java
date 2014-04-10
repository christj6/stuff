
import java.text.DecimalFormat;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Clock
{

	public static void main (String[] args) throws IOException
	{
		long start;
		long end;

		start = System.nanoTime();

		// do something here

		List<String> commands = new ArrayList<String>();
		commands.add("ls");

		// Run macro on target
		ProcessBuilder pb = new ProcessBuilder(commands);
        pb.directory(new File("/Users/Jack/Documents/GitHub/stuff/csc345"));
        pb.redirectErrorStream(true);
        Process process = pb.start();

        // read output
        StringBuilder out = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
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
            

		
		// stop doing that thing here

		end = System.nanoTime(); // measured in nanoseconds

		System.out.println("\nTime " + (end - start)/1000000000f + " seconds.");
	}
}
