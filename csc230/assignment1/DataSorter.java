
/** @author Jack Christiansen
 * The driver class DataSorter instantiates the CustomerList class,
 * and calls the scanFile method. The scanFile method, when finished storing the customers in the array,
 * calls the sortCustomers method, which uses the mergeSort algorithm to sort the customers
 * according to certain parameters. After it is sorted, scanFile then calls the writeFile method,
 * which writes every customer to a line in a text file, and exports the text file. */
public class DataSorter
{
    public static void main (String[] args)
    {
        CustomerList list = new CustomerList(); /** This line instantiates an instance of the CustomerList class, which is needed to construct a customer array and scan the URL file. */
        list.scanFile();
    }   
}
    
