

import java.io.*;
import java.util.*;
import java.text.*;

// args[0] is the file containing the collection, with documents separated by #N (where N is the docNo) lines
// args[1] is the document containing the queries
public class Search {

	public static void main (String[] args) throws IOException 
	{
		Map<String, List<Integer>> index = new HashMap<String, List<Integer>>();
		int[] documentLengths = new int[3204]; 
		float averageDocLength = 0;
		int docsInCollection = 0;

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

					// add temp.length to the word count for the #documentNumber doc
					documentLengths[documentNumber - 1] += temp.length;
				}

			  line = br.readLine();
			}

			docsInCollection = documentNumber;
		} 
		finally 
		{
			br.close();
		}

		// find average document length
		float sum = 0;
		for (int i = 0; i < docsInCollection; i++)
    	{
    		sum += (float)documentLengths[i];
    	}
    	averageDocLength = (float)sum/docsInCollection;
    	System.out.println("avg doc length: " + averageDocLength + " words"); // 120.61049 words given tccorpus.txt


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

				String[] terms = query.split(" "); // terms[0] is first term in query

				// System.out.println("ding");

				// for each document in the collection...
	            for (int i = 0; i < docsInCollection; i++)
	            {
	            	sum = 0;

	            	// for each term in the query... 
					for (int j = 0; j < terms.length; j++) // remember to change i < 1 back to i < terms.length
					{
						String key = terms[j].toString();

			            List<Integer> values = index.get(terms[j]);

			            List<Tuple> tuples = convert(values);

			            // do the computation
			            float k = 0; // K = k1((1 - b) + b*dl/avgdl)

			            float top = 1; // (ri + 0.5)/(R - ri + 0.5)
			            float bottom = 0; // (ni - ri + 0.5)/(N - ni - R + ri + 0.5)
			            float secondPart = 0; // (k1 + 1)fi / (K + fi)
			            float thirdPart = 0; // (k2 + 1)qfi / (k2 + qfi)

			            // sum += Math.log(top/bottom)*secondPart*thirdPart;
					}

					// output final sum
					System.out.println(sum + "\t doc #" + (i + 1));

	            }

				



			}



			// debug sandbox
			List<Integer> listOfNumbers = Arrays.asList(1, 2, 2, 2, 3, 3, 4, 4, 5);

		    List<Tuple> listOfTuples = convert(listOfNumbers);

		    for(Tuple tup : listOfTuples) 
            {
            	System.out.print("(" + tup.getFile() + ", " + tup.getFreq() + ") ");
        	}



        	// System.out.println(docsInCollection + " documents in collection"); // 3204 documents in collection given tccorpus.txt


	}

	// takes in list of integers (doc numbers) and converts into list of tuples (doc no, tf)
	// women: 2735 2735 2823 2823 2823 2823 2823 3022 3022 3022 3022
	// women: (2735, 2) (2823, 5) (3022, 4) 
	public static List<Tuple> convert (List<Integer> list)
	{
		List<Tuple> x = new LinkedList<Tuple>();
		int currentDoc = list.get(0);
		int occurrences = 0;

    	for (int i = 0; i < list.size(); i++)
    	{
    		int docNo = list.get(i);

    		if (currentDoc == docNo)
        	{
        		occurrences++;
        	}
        	else if (currentDoc != docNo)
        	{
        		Tuple tup = new Tuple(currentDoc, occurrences);

        		x.add(tup);

        		currentDoc = docNo;
        		i--;
        		occurrences = 0;
        	}
    	}

    	Tuple tup = new Tuple(currentDoc, occurrences);
        x.add(tup);

		return x; // the length of the list returned is the number of documents a given term appears in
	}

	
}
