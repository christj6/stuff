
import java.io.*;
import java.net.URL;
import java.util.Scanner;
    
/** @author Jack Christiansen 
 * The CustomerList class scans a text file off the Internet, line by line,
 * and sends each token into a data field for each customer. All the customers
 * are stored in an array. This array is sorted, via merge sort, according to
 * state abbreviation, zip code and address. The resulting data is then 
 * rewritten to a new text file called "sortedData.txt" and the program ends. */
public class CustomerList
{
    private Customer[] customers; /** Polymorphic array of customers and purchasing customers. */
    
    /** Constructor for the CustomerList. Sets the initial size of the customer array. */
    public CustomerList()
    {
        customers = new Customer[0]; /** This initializes the array of customer objects at 0. It will get resized with every additional line of input data. */
    }
    
    /** This method scans the url file line by line, sending each line to another scanner
     * which scans each token for information fields to be stored in a customer object.
     * After the array is finished, it is sent to the sortCustomers method, which
     * recursively sorts the array. Then scanFile calls writeFile, which writes the 
     * newly sorted information to a text file called "SortedData.txt".
     * @throws IOException: handles any input or output errors. */
    public void scanFile()
    {
        try
        {
            URL url = new URL("http://s3.amazonaws.com/depasquale/datasets/personalData.txt"); /** Retrieves the url of the text file from where the customer information will be scanned. */
            Scanner fileScan = new Scanner(url.openStream()); /** fileScan is responsible for scanning the entire online file, one line at a time. */
            int n = 1; /** Used to determine both the size of the array and the current array index. Incremented after each line in the input file. */
            String line = fileScan.nextLine(); /** Each line fileScan scans is sent to line. First the header line is sent, to prevent any invalid data from being input. */
            System.out.println("Scanning customers...");
            /** This loop iterates through every line in the input file, scanning information for every customer. */
            while (fileScan.hasNext())
            {
                line = fileScan.nextLine();	
                Scanner lineScan = new Scanner (line); /** lineScan scans the entire line and breaks it into tokens, which correspond with certain customer input fields. */
                lineScan.useDelimiter("\t"); /** Each customer field is separated by a tab. */
                
                /** Following loop resizes customer array to make room for next line of input.
                 * the integer variable "n" is used to increment the array. */
                Customer[] temp = new Customer[n];
                for (int i = 0; i < customers.length; i++)
                {
                    temp[i] = customers[i];
                }
                customers = temp;
                
                /** This loop iterates through every token (string and integer) inside each line. Each token represents a field of information for the customer. */
                while (lineScan.hasNext())
		{
                    customers[n-1] = new Customer(0, "", "", 'c', "", "", "", "", 0, "", "", "", "");
                    customers[n-1].setCustomerID(lineScan.nextInt());
                    customers[n-1].setGender(lineScan.next());
                    customers[n-1].setFirstName(lineScan.next());
                    String middle = lineScan.next(); /** This string makes it easier for the first character (index 0) to be retrieved and input as the middle initial for the customer. */
                    customers[n-1].setMiddleInitial(middle.charAt(0));
                    customers[n-1].setLastName(lineScan.next());
                    customers[n-1].setStreetAddress(lineScan.next());
                    customers[n-1].setCity(lineScan.next());
                    customers[n-1].setState(lineScan.next());
                    customers[n-1].setZipCode(lineScan.nextInt());
                    customers[n-1].setEmailAddress(lineScan.next());
                    customers[n-1].setTelephoneNumber(lineScan.next());
                    customers[n-1].setNationalID(lineScan.next());
                    customers[n-1].setBirthDate(lineScan.next());
                    
                    String purchaseCheck = lineScan.next(); /** Moves the next string token (if it exists) out of the way, leaving the next token (a long value) to be checked. If the next token's a number, the customer becomes a purchasing customer, and additional fields are filled out. */                                              
                    
                    /** If the condition is true, the customer is a purchasing customer. */
                    if (lineScan.hasNextLong())
                    {
                        customers[n-1] = new PurchasingCustomer(customers[n-1].getCustomerID(), customers[n-1].getGender(), customers[n-1].getFirstName(), customers[n-1].getMiddleInitial(), customers[n-1].getLastName(), customers[n-1].getStreetAddress(), customers[n-1].getCity(), customers[n-1].getState(), customers[n-1].getZipCode(), customers[n-1].getEmailAddress(), customers[n-1].getTelephoneNumber(), customers[n-1].getNationalID(), customers[n-1].getBirthDate(), purchaseCheck, lineScan.nextLong(), lineScan.nextInt(), lineScan.next(), lineScan.next());
                    }
                    
                    /** Following loop cycles through remaining line tokens (if any) to prevent invalid input errors. */
                    while (lineScan.hasNext())
                    {
                        middle = lineScan.next();
                    }
                }
                n++; /** increments customer array index and size. */
                
            }
            
        }
        catch (IOException e)
        {
            System.out.println("Invalid input.");
        }
        
        /** These lines call the methods needed to sort and write the customers to a file.
         * They also use print messages to inform the user of the current status
         * of the program. */
        System.out.println("Sorting customers...");
        sortCustomers(customers, 0, (customers.length)-1);
        System.out.println("Writing file...");
        writeFile();
    }
    
    /** Writes a text file using the information provided from the customer array.
     * @throws IOException: handles any input or output errors. */
    public void writeFile()
    {
        try
        {
            String heading = "Number\tGender\tGivenName\tMiddleInitial\tSurname\tStreetAddress\tCity\tState\tZipCode\tEmailAddress\tTelephoneNumber\tNationalID\tBirthday\tCCType\tCCNumber\tCVV2\tCCExpires\tUPS";
            BufferedWriter fileOut = new BufferedWriter(new FileWriter("sortedData.txt"));
            fileOut.write(heading + "\n"); /** The first line of the text file is the heading. */
            
            for (int i = 0; i<customers.length; i++)
            {
                fileOut.write(customers[i].toString() + "\n"); /** Each line of the file is a customer's toString method. */
            }
            fileOut.close(); /** Closes the file. Skipping this step may corrupt the data. */
            customers = null; /** Clears the array from memory: it is no longer needed. */
        }
        catch (IOException e)
        {
            System.out.println("Invalid input.");
        }
    }
    
    /** sortCustomers is based on mergeSort, in that it sorts an array of objects by
     * splitting the array in half and recursively splitting the sub-arrays in half
     * until each sub-array contains one object. At that point, the sub-arrays are
     * sorted together in order until the array is reconstructed, this time in order.
     * The sorting comparisons are made using the Customer class compareTo method.
     * @param customers: the array whose data will be sorted.
     * @param min: the start value for the lower half of the array.
     * @param max: the end value for the upper half of the array.
     * */
    public static void sortCustomers(Customer[] customers, int min, int max)
    {
        if (min < max)
        {
            int mid = (min + max)/2; /** The mid variable determines where the array should be split. */
            sortCustomers (customers, min, mid);
            sortCustomers (customers, mid+1, max);
            merge (customers, min, mid, max);
        }
    }
    
    /** Merge is a support method (hence the private visibility) for sortCustomers.
     * It merges the sub-arrays from sortCustomers in order using compareTo.
     * @param customers: same array that was passed to sortCustomers.
     * @param first: first index of the lower sub-array.
     * @param mid: Used to split the two sub-arrays.
     * @param last: last index of the upper sub-array.
     * */
    private static void merge(Customer[] customers, int first, int mid, int last)
    {
        Customer[] temp = new Customer[(customers.length)];
        int first1 = first; /** first and mid are the endpoints of the first sub-array. */
        int last1 = mid;
        int first2 = mid+1; /** mid+1 and last are the endpoints of the second sub-array. */
        int last2 = last;
        int index = first1; /** This contains the next index open in the temporary array. */
        
        /** This loop copies the smaller item from each sub-array into the temporary array
         * until one of the sub-arrays becomes empty. */
        while (first1 <= last1 && first2 <= last2)
        {
            if(customers[first1].compareTo(customers[first2]) < 0)
            {
                temp[index] = customers[first1];
                first1++;
            } else {
                temp[index] = customers[first2];
                first2++;
            }
            index++;
        }
        
        /** If there are any elements from the first sub-array leftover, they are copied over. */
        while (first1 <= last1)
        {
            temp[index] = customers[first1];
            first1++;
            index++;
        }
        
        /** Same goes for the second sub-array. */
        while (first2 <= last2)
        {
            temp[index] = customers[first2];
            first2++;
            index++;
        }
        
        /** This loop copies the merged data into the original array. */
        for (index = first; index <= last; index++)
        {
            customers[index] = temp[index];
        }
    }
}
    
