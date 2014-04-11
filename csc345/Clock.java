
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
		long start;
		long end;
		int trials = 20;
		double[] data = new double[trials];

		ProcessBuilder pb = new ProcessBuilder("ls","-l"); // put commands there in the form of comma-separated tokens enclosed in quotes
     
        for (int i = 0; i < trials; i++)
        {
        	start = System.nanoTime();

	        Process process = pb.start();  
	        //writeFile("blah.txt","text goes here");

	        end = System.nanoTime(); // measured in nanoseconds

	        //output(process); // output the text -- mainly for debug purposes (making sure the process executes the way you intended)

			//System.out.println("\nTime " + (end - start)/1000000000f + " seconds.");
			data[i] = (end - start)/1000000000f;
			System.out.println("Time: " + data[i] + " seconds.");
        }
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
