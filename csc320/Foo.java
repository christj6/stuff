

// Jack Christiansen
// CSC 320 - Information Retrieval, 10/3/2014
// Assignment 4 - Page Rank

import java.io.*;
import java.util.*;


public class Foo {

  /*
  * Main method, where the bulk of the text processing occurs
  */
   public static void main(String args[]) throws IOException {
       // String inputTextFile;
       // inputTextFile = readFile("links.srt");

      /*
      BufferedReader br = new BufferedReader(new FileReader("links.srt"));
      int lineCount = 0;
      try
      {
        String line = br.readLine();
        lineCount++;

        while (line != null)
        {
          line = br.readLine();
          lineCount++;
        }
      } finally {
        br.close();
      }
      */

      // System.out.println(lineCount); // 5695807 lines

      int lineCount = 5695806; // 5695806

      String[] corpusLinks = new String[lineCount];

      //String[] pairs = new String[lineCount];

      BufferedReader br = new BufferedReader(new FileReader("links.srt"));
      int index = 0;
      try
      {
        String line = br.readLine();

        while (line != null)
        {
          String temp[] = line.split("\t");
          corpusLinks[index] = temp[0];

          // pairs[index] = temp[1] + "\t" + temp[0];
          // System.out.println(temp[1] + "\t" + temp[0]); // outputs destination, then source

          index++;
          line = br.readLine();
        }
      } finally {
        br.close();
      }

      // now corpusLinks contains each link -- still need to remove duplicates
      // now pairs contains all the pairs, with the destination on the left

      for (int i = 0; i < 10; i++)
      {
        // System.out.println(corpusLinks[i]);
      }

      // remove duplicates from corpusLinks
      
      Arrays.sort(corpusLinks); // missing element in array somewhere?

      for (int i = 0; i < corpusLinks.length; i++)
      {
        String test = corpusLinks[i];

        for (int j = i+1; j < corpusLinks.length; j++)
        {
          if (corpusLinks[j] != null && !corpusLinks[j].isEmpty() && corpusLinks[j].compareTo(test) == 0)
          {
            corpusLinks[j] = "";
          }
          else
          {
            j = corpusLinks.length + 1;
          }
        }
      }
    
      Arrays.sort(corpusLinks);
      

      int validStrings = 0;
      String[] corpusUnique = new String[118981];

      for (int i = 0; i < corpusLinks.length; i++)
      {
        if (corpusLinks[i] != null && !corpusLinks[i].isEmpty())
        {
          corpusUnique[validStrings] = corpusLinks[i];
          validStrings++;
        }
      }

      //System.out.println(validStrings); // 118981 unique links


      // process file -- switch source and destination so that destination goes on the left
      // use funky unix command to sort by destination

      int counter = 0;

      
      BufferedReader compare = new BufferedReader(new FileReader("sorted_destinations.txt"));
      try
      {
        String line = compare.readLine();

        

        while (line != null)
        {
           // System.out.println(counter + " / 5695807"); // remove this line

          String temp[] = line.split("\t"); // search for temp[0] in corpusLinks -- if found, output the link
          int searchIndex = Arrays.binarySearch(corpusUnique, temp[0]); // if searchIndex is -1, the destination wasn't found in the corpus sources
          if (temp[0] != null && searchIndex != -1)
          {

            // it's found, set something to true
            if (searchIndex > -1 && corpusUnique[searchIndex] != null && corpusUnique[searchIndex].compareTo(temp[0]) == 0)
            {
              System.out.println(line); 
            }
          }

          line = compare.readLine();
          counter++;
        }
      } finally {
        compare.close();
      }
      
        
   }

   // adapted from http://stackoverflow.com/questions/16027229/reading-from-a-text-file-and-storing-in-a-string
   /*
   *  This method converts a .txt file into a String object. Very useful for managing the input file and stopwords file.
   */
   public static String readFile(String fileName) throws IOException {
      BufferedReader br = new BufferedReader(new FileReader(fileName));
      try {
          StringBuilder sb = new StringBuilder();
          String line = br.readLine();

          while (line != null) {
              sb.append(line);
              sb.append("\n");
              line = br.readLine();
          }
          return sb.toString();
      } finally {
          br.close();
      }
  }

  //
}
