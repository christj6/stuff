
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

		// do something
		/*
		for (int i = 0; i < 10000; i++)
		{

		}
		*/

		// Process p = Runtime.getRuntime().exec(new String[]{"csh","-c","cat /home/narek/pk.txt"});
		Process p = Runtime.getRuntime().exec(new String[]{"ls","-l"});

		end = System.nanoTime();

		System.out.println("Time " + (end - start));
	}
}
