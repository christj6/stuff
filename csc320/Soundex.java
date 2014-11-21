


import java.io.*;
import java.util.*;
import java.text.*;


public class Soundex {
	public static void main (String[] args)
	{
		String input = args[0]; // poiner should be P560

		char[] array = new char[input.length()];

		for (int i = 0; i < input.length(); i++)
		{
			array[i] = input.charAt(i); // we will manipulate this char array over the course of the program
		}

		char firstLetter = array[0];
		array[0] = Character.toUpperCase(firstLetter); // converts first character to upper case

		for (int i = 0; i < input.length(); i++)
		{
			// first, set any a, e, i, o, u, y, h, w to a hyphen
			char x = array[i];

			if (x == 'a' || x == 'e' || x == 'i' || x == 'o' || x == 'u' || x == 'y' || x == 'h' || x == 'w')
			{
				array[i] = '-';
			}
			else if (x == 'b' || x == 'f' || x == 'p' || x == 'v')
			{
				array[i] = '1';
			}
			else if (x == 'c' || x == 'g' || x == 'j' || x == 'k' || x == 'q' || x == 's' || x == 'x' || x == 'z')
			{
				array[i] = '2';
			}
			else if (x == 'd' || x == 't')
			{
				array[i] = '3';
			}
			else if (x == 'l')
			{
				array[i] = '4';
			}
			else if (x == 'm' || x == 'n')
			{
				array[i] = '5';
			}
			else if (x == 'r')
			{
				array[i] = '6';
			}

			// now the hyphens and the numbers are in
		}

		// next, delete adjacent repeats of number

		for (int i = 0; i < input.length(); i++)
		{
			
			char x = array[i];

			int j = i + 1;

			while (j < array.length && array[j] == x)
			{
				if (array[i] == '-')
				{
					j = input.length(); // trip loop
				}
				else
				{
					array[j] = '-'; // replace repeat with hyphen (keep going for subsequent repeats as well)
					j++;
				}

			}
			
		}



		String output = new String(array);
		

		output = output.replaceAll("[\\s\\-()]", "");

		if (output.length() < 4)
		{
			int length = output.length();
			while (length < 4)
			{
				output += '0';
				length++;
			}	
		}
		else if (output.length() > 4)
		{
			output = output.substring(0, 4);
		}

		// output = output.substring(int beginIndex, int endIndex)

		System.out.println(output);


	}
}
