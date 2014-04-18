import java.text.DecimalFormat;

import java.io.BufferedReader;
import java.io.File;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Used content from: http://stackoverflow.com/questions/1410741/want-to-invoke-a-linux-shell-command-from-java
// And a bit from: http://stackoverflow.com/questions/9993873/java-executing-linux-command

public class Clock
{

	public static void main (String[] args) throws IOException
	{
		long start = 0;
		long end = 0;
		int trials = 50;
		double[] data = new double[trials];
		String outputReadyData = "";
		String sourceDirectory = "/Users/Jack/Documents/GitHub/stuff/csc345/clock";
		String destinationDirectory = "H:/"; // set this to the appropriate directory if you are using a flash drive
		//String directory = "/cygwin/h";
     
	        for (int i = 0; i < trials; i++)
	        {
	        	//ProcessBuilder pb = new ProcessBuilder("cp","file" + i + ".txt",destinationDirectory); // copy file to directory
	        	//ProcessBuilder pb = new ProcessBuilder("cat","file" + i + ".txt"); // Performs read of file (cat with single argument)
	        	ProcessBuilder pb = new ProcessBuilder("rm", destinationDirectory + "file" + i + ".txt"); // delete file from flash drive
	
	        	pb.directory(new File(sourceDirectory));
	        	
	        	// Timed section begins here:
		        if (write == 0)
		        {
		        	start = System.nanoTime();
		        	Process process = pb.start();  
		        	end = System.nanoTime();
		        }
		        // Timed section ends here.
	
		        //output(process); // output the text -- mainly for debug purposes (making sure the process executes the way you intended)
	
			//System.out.println("\nTime " + (end - start)/1000000000f + " seconds.");
			data[i] = (end - start)/1000000000f;
			outputReadyData += data[i];
			outputReadyData += ",";
			//outputReadyData += "\r\n";
			//System.out.println("Time: " + data[i] + " seconds.");
	        }

    		writeFile("values.csv", outputReadyData);
	}

	public static void writeFile(String filename, String data) throws IOException
	{
		FileWriter fileWriter = new FileWriter(filename);
	        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
	        bufferedWriter.write(data);
	        bufferedWriter.close();
	}

	// Takes in process, outputs the result of it to the screen
	public static void output(Process process) throws IOException
	{
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
	}
}
