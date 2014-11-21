
// Jack Christiansen
// 11/21/2014
// CSC 320 - Homework 5
// Small search engine

import java.io.*;
import java.util.*;
import java.text.*;

// args[0] is the file containing the collection, with documents separated by #N (where N is the docNo) lines
// args[1] is the document containing the queries
// args[2] is the query #id you'd like to perform
public class Search {

	public static void main (String[] args) throws IOException 
	{
		Map<String, List<Integer>> index = new HashMap<String, List<Integer>>();
		int[] documentLengths = new int[3204]; // if a different corpus file is used, this value needs to be at least (or more than) the # of documents in the corpus
		float averageDocLength = 0;
		int docsInCollection = 0;

		BufferedReader br = new BufferedReader(new FileReader(args[0])); 
		try
		{
			String line = br.readLine();
			int documentNumber = 0;

			while (line != null)
			{
				// first check if line is #number or something else
				if (line.charAt(0) == '#')
				{
					// contains a # and document number...
					line = line.replaceAll("[^0-9]", ""); // filter out spaces and nonalphanumeric characters
					documentNumber = Integer.parseInt(line);
				}
				else
				{

					String temp[] = line.split(" ");

					for (int i = 0; i < temp.length; i++)
					{
						// for each word in the document...

						// filter out stopwords?

						// adapted some code from here http://rosettacode.org/wiki/Inverted_index#Java for this next section

						String word = temp[i].toLowerCase();

						List<Integer> entry = index.get(word); // see if the word has been hashed yet

						if (entry == null) // if not, create a new list for it
						{
							entry = new LinkedList<Integer>();
							index.put(word, entry);
						}

						entry.add(documentNumber);
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
    	//System.out.println("avg doc length: " + averageDocLength + " words"); // 120.61049 words given tccorpus.txt

    	// gathers queries from query document, stores them in string array, takes off number (assumes query id is not part of the query)
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

	      	String query = queries.get(Integer.parseInt(args[2]) - 1); // index 0 refers to query #1 -- subtracting 1 from # gets the right index

			String[] terms = query.split(" "); // terms[0] is first term in query, etc

			// for each document in the collection...
            for (int i = 0; i < docsInCollection; i++)
            {
            	sum = 0; // after the sum is output at the end of the loop, it comes back here and resets it to zero for the next document score

            	// for each term in the query... 
				for (int j = 0; j < terms.length; j++)
				{
					// referenced this for printing/retrieving values -- http://stackoverflow.com/questions/5920135/printing-hashmap-in-java
					String key = terms[j].toString();

		            List<Integer> values = index.get(terms[j]);

		            List<Tuple> tuples = convert(values);

		            // do the computation
		            // For parameters, use k1=1.2, b=0.75, k2=100.
		            float k1 = (float)1.2;
		            float k2 = (float)100;
		            float b = (float)0.75;

		            float k = k1*((1 - b) + b*documentLengths[j]/averageDocLength); // K = k1((1 - b) + b*dl/avgdl)

		            int ni = tuples.size(); // # of documents containing term i. Each tuple refers to a different document, so the # of tuples in this is the # of documents the term appears in
		            int fi = 0;
		            for (Tuple tup : tuples)
		            {
		            	if (tup.getFile() == (i + 1)) // index 0 represents document 1, so adding 1 to the index gets the right document number
		            	{
		            		fi = tup.getFreq(); // # of times tern appears in document
		            	}
		            }
		            int qfi = 1; // frequency of term i in query

		            // no relevance information: R and r are zero
		            float top = (float)((0 + 0.5)/(0 - 0 + 0.5)); // (ri + 0.5)/(R - ri + 0.5)
		            float bottom = (float)((ni - 0 + 0.5)/(docsInCollection - ni - 0 + 0 + 0.5)); // (ni - ri + 0.5)/(N - ni - R + ri + 0.5)
		            float secondPart = (k1 + 1)*fi/(k + fi); // (k1 + 1)fi / (K + fi)
		            float thirdPart = (k2 + 1)*qfi/(k2 + qfi); // (k2 + 1)qfi / (k2 + qfi)

		            sum += Math.log(top/bottom)*secondPart*thirdPart;
				}

				// output final sum
				// query_id Q0 doc_id rank BM25_score system_name
				System.out.println(sum + "\t doc #" + (i + 1));

            }



			// debug sandbox -- to make sure my convert function worked. 
			/*
			List<Integer> listOfNumbers = Arrays.asList(1, 2, 2, 2, 3, 3, 4, 4, 5);

		    List<Tuple> listOfTuples = convert(listOfNumbers);

		    for(Tuple tup : listOfTuples) 
            {
            	System.out.print("(" + tup.getFile() + ", " + tup.getFreq() + ") ");
        	}
			*/


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
