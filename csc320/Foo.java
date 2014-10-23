

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

      int lineCount = 5695807;

      String[] corpusLinks = new String[lineCount];

      BufferedReader br = new BufferedReader(new FileReader("links.srt"));
      int index = 0;
      try
      {
        String line = br.readLine();

        while (line != null)
        {
          String temp[] = line.split("\t");
          corpusLinks[index] = temp[0];
          index++;
          line = br.readLine();
        }
      } finally {
        br.close();
      }

      // now corpusLinks contains each link -- still need to remove duplicates

      for (int i = 0; i < 10; i++)
      {
        System.out.println(corpusLinks[i]);
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
