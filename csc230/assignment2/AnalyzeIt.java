/**
 * AnalyzeIt is the driver class for the program which scans a file of chemical
 * objects, populates an array and applies various algorithms to it.
 * The driver class instantiates an instance of the ChemicalData class
 * and executes the sortFile method. This eventually leads to a table printed
 * on the screen containing various metrics, sorted by algorithm name.
 * 
 * @author Jack Christiansen
 */
public class AnalyzeIt 
{
    /**
     * The main method creates an instance of the ChemicalData class, called
     * "data," and executes its sortFile method.
     * @param args 
     */
    public static void main (String[] args)
    {
        ChemicalData data = new ChemicalData();
        data.scanFile();
    }
}
