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

	public static void main (String[] args) throws IOException, InterruptedException
	{
		long start = 0;
		long end = 0;
		int trials = 50;
		double data = 0; // temporarily stores the value so it can be written to a string

		String writes = "";
		String deletes = "";

		String reads = ""; // do not try reads on non-txt files with cygwin -- it will crash.

		String sourceDirectory = "/Users/Jack/Documents/GitHub/stuff/csc345/clock";
		String destinationDirectory = "H:/"; // set this to the appropriate directory if you are using a flash drive
		//String directory = "/cygwin/h";
     
        for (int i = 0; i < 150; i++)
        {
        	//System.out.println(i);
        	//ProcessBuilder folderCopy = new ProcessBuilder("cp","-r","C:/Users/Jack/Documents/GitHub/stuff/csc345/clock/folder","H:/"); 
        	//ProcessBuilder folderDelete = new ProcessBuilder("rm","-r", "H:/folder"); // Performs read of file (cat with single argument)
        	/*
        	if (i < 50)
        	{
        		// do the single file copy
        		ProcessBuilder singleFileCopy = new ProcessBuilder("cp","file" + i + ".txt",destinationDirectory); // copy file to directory
        		singleFileCopy.directory(new File("C:/Users/Jack/Documents/GitHub/stuff/csc345/clock/"));

        		start = System.nanoTime();

	        	Process write = singleFileCopy.start();

	        	if (write.waitFor() == 0)
	        	{
	        		end = System.nanoTime();
	        	}

	        	data = (end - start)/1000000000f;
				writes += data;
				writes += ",";
				System.out.println("Write #" + i + ": " + data + " seconds.");
        	}
        	*/

        	if (i >= 50 && i < 100)
        	{
        		// do the single file read
        		ProcessBuilder singleFileRead = new ProcessBuilder("cat","H:/file" + (i-50) + ".txt"); // Performs read of file (cat with single argument)
        		singleFileRead.directory(new File("C:/Users/Jack/Documents/GitHub/stuff/csc345/clock/"));

        		start = System.nanoTime();

	        	Process read = singleFileRead.start();

	        	end = System.nanoTime();
	        	/*
	        	if (read.waitFor() == 0)
	        	{
	        		end = System.nanoTime();
	        	}
	        	*/

	        	data = (end - start)/1000000000f;
				reads += data;
				reads += ",";
				System.out.println("Read #" + (i-50) + ": " + data + " seconds.");
        	}

        	/*
        	if (i >= 100)
        	{
        		// do the single file delete
        		ProcessBuilder singleFileDelete = new ProcessBuilder("rm", destinationDirectory + "file" + (i-100) + ".txt"); // delete file from flash drive
        		singleFileDelete.directory(new File("C:/Users/Jack/Documents/GitHub/stuff/csc345/clock/"));

        		start = System.nanoTime();
	        	Process delete = singleFileDelete.start();
		        if (delete.waitFor() == 0)
	        	{
	        		end = System.nanoTime();
	        	} 

	        	data = (end - start)/1000000000f;
				deletes += data;
				deletes += ",";
				System.out.println("Delete #" + (i-100) + ": " + data + " seconds.");
        	}
        	*/



        	//singleFileRead.directory(new File("H:/folder"));

        	// Do the write
        	/*
        	start = System.nanoTime();

        	Process write = folderCopy.start();

        	if (write.waitFor() == 0)
        	{
        		end = System.nanoTime();
        	}

        	data = (end - start)/1000000000f;
			writes += data;
			writes += ",";
			System.out.println("Write #" + i + ": " + data + " seconds.");
			*/

			// Do the delete
			/*
        	start = System.nanoTime();
        	Process delete = folderDelete.start();
	        if (delete.waitFor() == 0)
        	{
        		end = System.nanoTime();
        	} 

        	data = (end - start)/1000000000f;
			deletes += data;
			deletes += ",";
			System.out.println("Delete #" + i + ": " + data + " seconds.");
			*/

	        //output(write); // output the text -- mainly for debug purposes (making sure the process executes the way you intended)
	        //output(delete); 

			//System.out.println("\nTime " + (end - start)/1000000000f + " seconds.");
			
        }

    	writeFile("NTFS-writes.csv", writes);
    	writeFile("NTFS-reads.csv", reads);
    	writeFile("NTFS-deletes.csv", deletes);
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
