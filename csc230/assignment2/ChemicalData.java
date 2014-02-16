
import java.io.*;

/**
 * The ChemicalData class creates an array of chemical objects (using the 
 * Chemical.java class) and populates them using information provided from
 * a text file (toxins2.csv). The array is populated until either the text file
 * has no more information to provide, or until the array is filled.
 * The default maximum size of the array is 2000 elements.
 * After the array is completed, and duplicate elements are removed,
 * the array is passed along to the Metrics class, where several sorting and
 * searching algorithms are applied to it, and various metrics are recorded
 * to provide information on the efficiency of various methods.
 * 
 * @author Jack Christiansen
 */
public class ChemicalData
{
    private Chemical[] chemicals; /** Array of chemical objects. */
    
    /** 
     * Constructor for the ChemicalData list. 
     * Sets the initial size of the chemical array. 
     */
    public ChemicalData()
    {
        chemicals = new Chemical[2000]; /** This initializes the array of chemicals at 2000. */
    }
    
    /**
     * The scanFile method takes in a text file and scans it line by line,
     * using the information in each line to create a corresponding chemical
     * object. Before the next object is created, the program checks if the
     * array is full and if the next line has information in it.
     * During every iteration of this loop, the program takes the new object
     * and sweeps through the array to check for any duplicates. If it is
     * determined that the new object is a duplicate of an earlier object,
     * the new object is set to null.
     * 
     * After the scanning loop is over, the program copies the array elements
     * into another array of the same size, such that the null entries are
     * placed to the right and the non-null entries are placed to the left.
     * They are not sorted: the order of the entries is not affected. After 
     * this loop, another loop is used to copy the entries of this array back to
     * the original array, except now the old array is reinitialized to have
     * the exact amount of space needed to hold all the unique array elements.
     * 
     * Then a new Metrics object is created based on the new unique element-only 
     * chemical array. A copy of this array is made to perform different sort
     * algorithms on.
     * 
     * @throws FileNotFoundException: in the event the text file is not found.
     * @throws IOException: in the event that invalid or incorrect input data
     *                      is entered into a field.
     */
    public void scanFile()
    {
        /**
         * This integer determines which element of the array will be filled.
         * It increments by one with every line of the file read.
         * Once it matches the array's length, the parsing loop will terminate.
         */
        int arrayIndex = 0;

        try
        {
            BufferedReader file = new BufferedReader(new FileReader("toxins2.csv"));
            
            /**
             * The first line of the file is scanned but never used. This line
             * contains the heading, which would cause an error if some of the
             * strings were passed in as parameters for a chemical object.
             */
            String line = file.readLine();
            
            /**
             * This string scans the first line of parameters. This line
             * contains information for the first chemical object.
             */
            line = file.readLine();    
            
            /**
             * In order for the next line of text to be parsed, not only must
             * the next line contain information, but there must also be enough
             * room in the array for more chemical objects. If there is no more
             * room in the array, the parsing process ends and the sorting and
             * searching metrics are gathered on the partial array with the maximum
             * number of elements in there, all other chemicals in the text
             * file ignored.
             */
            while (arrayIndex < chemicals.length && line != null)
            {
                /**
                 * The items are separated by commas.
                 */
                String[] dataArray = line.split(",");

                chemicals[arrayIndex] = new Chemical(dataArray[0], dataArray[1], true, dataArray[3], true, dataArray[5], true, 'c', dataArray[8], dataArray[9]);
                if (dataArray[2].equalsIgnoreCase("NO"))
                {
                    chemicals[arrayIndex].setCleanAirAct(false);
                }   
                if (dataArray[4].equalsIgnoreCase("NO"))
                {
                    chemicals[arrayIndex].setMetal(false);
                }
                if (dataArray[6].equalsIgnoreCase("NO"))
                {
                    chemicals[arrayIndex].setCarcinogen(false);
                }
                
                /** 
                 * This string makes it easier for the first character (index 0) 
                 * to be retrieved and input as the form type for the chemical. 
                 */
                String form = dataArray[7]; 
                chemicals[arrayIndex].setFormType(form.charAt(0));
                
                /** This loop eliminates duplicates. The non-null entries are not contiguous. This problem is addressed after all the chemical entries have been processed. */
                for (int i = 0; i<arrayIndex; i++)
                {
                    if(chemicals[i] != null)
                    {
                        if(chemicals[arrayIndex].compareTo(chemicals[i]) == 0)
                        {
                            chemicals[i] = null;
                        }
                    }
                }

                /**
                 * Moves focus to next element in array.
                 * If the array is full, this value will be set equal
                 * to the size of the array, and no more elements will be
                 * placed in the array.
                 */
                arrayIndex++;
                
                /**
                 * Prepares the next line of the text file to be parsed
                 * and converted into a chemical object.
                 */
                line = file.readLine();	
            }
            
            /**
             * Closes the file to prevent any possible corruption of data.
             */
            file.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Error: file not found.");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        
        /** This loop copies the entries of chemicals into a new array such that the non-null entries are contiguous. */
        Chemical[] arrayCopy;
        arrayCopy = new Chemical[chemicals.length];
        int copyIndex = 0;
        for (int i = 0; i < chemicals.length; i++)
        {
            if(chemicals[i] != null)
            {
                arrayCopy[copyIndex] = chemicals[i];
                copyIndex++;
            }
        }
        
        /** This loop copies the contiguous non-null entries back into the chemicals array with the exact size needed to store them all. */
        chemicals = new Chemical[copyIndex];
        for (int i = 0; i < chemicals.length; i++)
        {
            chemicals[i] = arrayCopy[i];
        }
        
        Metrics metric = new Metrics(chemicals.length);
        
        for (int i = 0; i < 4; i++)
        {
            /** 
             * Recopy the sorting array from the master copy. 
             */
            arrayCopy = new Chemical[chemicals.length];
            for (int k = 0; k < arrayCopy.length; k++)
            {
                arrayCopy[k] = chemicals[k];
            }
            
            /** 
             * Use integer i to call the corresponding sort method. 
             */
            metric.sortMetrics(i, arrayCopy);
        }
        
        /**
        * Gathers metrics on various search methods using the base array.
        */
       metric.searchMetrics(chemicals);
        
        /**
         * Prints the toString method of the Metrics class, which is
         * a table of metrics and other information on the efficiency of
         * various sorting and searching algorithms.
         */
        System.out.println(metric);
        
        
    }
    
    
}
