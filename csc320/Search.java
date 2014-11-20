

import java.io.*;
import java.util.*;
import java.text.*;

// args[0] is the file containing the collection, with documents separated by #N (where N is the docNo) lines
// args[1] is the document containing the queries
public class Search {

	public static void main (String[] args) throws IOException 
	{
		Map<String, List<Integer>> index = new HashMap<String, List<Integer>>();
		//

		BufferedReader br = new BufferedReader(new FileReader(args[0])); 
		try
		{
			String line = br.readLine();
			int documentNumber = 0;
			//int position = 0;

			while (line != null)
			{
				// first check if line is #number or something else
				if (line.charAt(0) == '#')
				{
					// contains a # and document number...

					//line = line.substring(1); // unneeded, delete this line
					line = line.replaceAll("[^0-9]", ""); // filter out spaces and nonalphanumeric characters
					documentNumber = Integer.parseInt(line); // since the tccorpus.txt file has the documents in order, it's not necessary to parse the number each time, but this gives the ability to parse a text file with out of order documents
					//System.out.println(documentNumber);
					//position = 0;
				}
				else
				{

					String temp[] = line.split(" ");

					for (int i = 0; i < temp.length; i++)
					{
						// for each word in the document...

						// filter out stopwords?

						String word = temp[i].toLowerCase(); // 

						List<Integer> entry = index.get(word); // see if the word has been hashed yet

						if (entry == null) // if not, create a new list for it
						{
							entry = new LinkedList<Integer>();
							index.put(word, entry);
						}

						//Tuple x = new Tuple(documentNumber, position);
						entry.add(documentNumber);

						//position++;
					}
				}

			  line = br.readLine();
			}
		} 
		finally 
		{
			br.close();
		}


		// word -- docId, term frequency
		// referenced this for printing values -- http://stackoverflow.com/questions/5920135/printing-hashmap-in-java
		// this loop prints the term, followed by the appearances the term makes in documents in the collection.
		// for example --
		// women: 2735 2735 2823 2823 2823 2823 2823 3022 3022 3022 3022
		// we want this to instead look like --
		// women: (2735, 2) (2823, 5) (3022, 4) 
		/*
		for (String name: index.keySet())
		{
            String key = name.toString();
            String value = index.get(name).toString();  

            List<Integer> values = index.get(name);

            //System.out.println(key + " " + value);  
            System.out.print(key + ": ");

            for(Integer docNo : values) 
            {
            	System.out.print(docNo + " ");
        	}

        	System.out.println("");
		}
		*/

		List<String> queries = new LinkedList<String>();
		 BufferedReader queryProcessor = new BufferedReader(new FileReader(args[1])); 
	      try
	      {
	        String line = queryProcessor.readLine();

	        while (line != null)
	        {
	        	line = line.substring(2); // remove the digits from the beginning of the line
	        	queries.add(line);

	          line = queryProcessor.readLine();
	        }
	      } finally {
	        queryProcessor.close();
	      }

	      for(String query : queries) 
            {
            	System.out.println(query);
        	}

	}

	
}
