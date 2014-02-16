/**
 * The Chemical class allows the program to store instances of
 * chemical objects with various characteristics.
 * The ChemicalData class uses this class to populate an array of chemical
 * objects. This array is sorted in the Metrics class.
 * 
 * The Chemical class implements the Comparable interface in order to override
 * its compareTo method, which is crucial to gathering metrics.
 * 
 * @author Jack Christiansen
 */
public class Chemical implements Comparable<Chemical>
{
    /** 
     * This is the name of the chemical. 
     * It can be an element, mixture or compound.
     * It's stored as a string. */
    private String chemicalName = "";
    
    /** 
     * This stores the chemical's unique ID as a string.
     * */
    private String compoundID = "";
    
    /**
     * This determines whether the chemical is covered
     * by the Clean Air Act.
     */
    private boolean cleanAirAct = false;
    
    /**
     * This string stores whether a given chemical
     * is PBT (persistent, bio-accumulative and toxic)
     * or non-PBT.
     */
    private String classification = "";
    
    /**
     * This determines whether or not a given chemical is a metal.
     */
    private boolean metal = false;
    
    /**
     * This string stores the chemical's particular category of metal.
     * If the chemical is not a metal, metalCategory is stored as a zero.
     */
    private String metalCategory = "";
    
    /**
     * This stores whether or not the chemical is a carcinogen.
     */
    private boolean carcinogen = false;
    
    /**
     * This character stores the chemical's form type.
     */
    private char formType = ' ';
    
    /**
     * This string stores the common measurement of the chemical.
     * They include pounds, grams, etc.
     */
    private String unit = "";
    
    /**
     * This string stores the risk level of the particular chemical.
     * It can be high, medium, low or unknown.
     */
    private String risk = "";
    
    /**
     * This variable refers to the number of times a given object
     * has called its compareTo method. This variable is useful
     * for keeping track of how efficient certain
     * algorithms are.
     */
    private long comparisons = 0;
    
    /** 
     * Creates a chemical object using data provided from the text file.
     * @param chemicalName 
     * @param compoundID 
     * @param cleanAirAct 
     * @param classification 
     * @param metal 
     * @param metalCategory 
     * @param carcinogen 
     * @param formType 
     * @param unit 
     * @param risk 
     * */
    public Chemical(String chemicalName, String compoundID, boolean cleanAirAct, String classification, boolean metal, String metalCategory, boolean carcinogen, char formType, String unit, String risk)
    {
        this.chemicalName = chemicalName;
        this.compoundID = compoundID;
        this.cleanAirAct = cleanAirAct;
        this.classification = classification;
        this.metal = metal;
        this.metalCategory = metalCategory;
        this.carcinogen = carcinogen;
        this.formType = formType;
        this.unit = unit;
        this.risk = risk;
    }
    
    /** 
     * This constructor is designed to make linear and binary searches easier
     * by only requiring the compound ID as an input field,
     * as opposed to requiring the programmer to fill out the entire constructor.
     * @param compoundID 
     */
    public Chemical(String compoundID)
    {
        this.compoundID = compoundID;
    }
    
    /** 
     * Allows the program to input a string for the chemical's name.
     * @param chemicalName 
     */
    public void setChemicalName(String chemicalName)
    {
        this.chemicalName = chemicalName;
    }
    
    /**
     * Returns a string containing the chemical's name.
     * @return chemicalName
     */
    public String getChemicalName()
    {
        return chemicalName;
    }
    
    /** 
     * Allows the program to input a string for the chemical's CAS number
     * or compound ID.
     * @param compoundID 
     */
    public void setCompoundID(String compoundID)
    {
        this.compoundID = compoundID;
    }
    
    /**
     * Returns a string containing the chemical's CAS number/compound ID #.
     * @return compoundID
     */
    public String getCompoundID()
    {
        return compoundID;
    }
    
    /**
     * Allows the program to input a true or false value for whether or not
     * the given chemical is covered by the Clean Air Act.
     * In the text file, these values are provided as yes/no instead of
     * true/false, so an if-then statement using the equals method of
     * the String class is needed to assign the value properly.
     * 
     * @param cleanAirAct 
     */
    public void setCleanAirAct(boolean cleanAirAct)
    {
        this.cleanAirAct = cleanAirAct;
    }
    
    /**
     * Returns whether or not the chemical is covered by the Clean Air Act.
     * @return cleanAirAct, a true or false value
     */
    public boolean getCleanAirAct()
    {
        return cleanAirAct;
    }
    
    /**
     * Allows the program to input a string for the chemical's classification.
     * @param classification 
     */
    public void setClassification(String classification)
    {
        this.classification = classification;
    }
    
    /**
     * Returns a string that contains the chemical's classification.
     * @return classification
     */
    public String getClassification()
    {
        return classification;
    }
    
    /**
     * Allows the user to set a true/false value based on whether or not
     * the chemical is a metal.
     * @param metal 
     */
    public void setMetal(boolean metal)
    {
        this.metal = metal;
    }
    
    /**
     * Returns a true or false value depending on whether the chemical object
     * in question is a metal or not.
     * @return metal
     */
    public boolean getMetal()
    {
        return metal;
    }
    
    /**
     * This method allows the program to input a string for the chemical's
     * metal category. If the chemical is not a metal, the metalCategory
     * should be zero.
     * @param metalCategory 
     */
    public void setMetalCategory(String metalCategory)
    {
        this.metalCategory = metalCategory;
    }
    
    /**
     * Returns a string containing the chemical's metal category.
     * @return 
     */
    public String getMetalCategory()
    {
        return metalCategory;
    }
    
    /**
     * Allows the program to input a boolean value for whether or not the
     * chemical is a carcinogen.
     * @param carcinogen 
     */
    public void setCarcinogen(boolean carcinogen)
    {
        this.carcinogen = carcinogen;
    }
    
    /**
     * Returns a boolean value containing whether or not the chemical
     * is a carcinogen.
     * @return 
     */
    public boolean getCarcinogen()
    {
        return carcinogen;
    }
    
    /**
     * Allows the program to input a character to represent the form type
     * of the chemical.
     * @param formType 
     */
    public void setFormType(char formType)
    {
        this.formType = formType;
    }
    
    /**
     * Returns a character representing the chemical's form type.
     * @return 
     */
    public char getFormType()
    {
        return formType;
    }
    
    /**
     * Allows the program to input a string for the measurement unit best
     * for the chemical.
     * @param unit 
     */
    public void setUnit(String unit)
    {
        this.unit = unit;
    }
    
    /**
     * Returns a string containing the unit of measurement most common for
     * the chemical in question. Could be pounds, grams, etc.
     * @return 
     */
    public String getUnit()
    {
        return unit;
    }
    
    /**
     * Allows the program to input a string for the risk level of a chemical.
     * Generally, it should be high, medium, low, or unknown.
     * @param risk: refers to the risk level.
     */
    public void setRisk(String risk)
    {
        this.risk = risk;
    }
    
    /**
     * Returns a string containing the risk of a chemical.
     * Can be high, medium, low, or unknown.
     * @return risk
     */
    public String getRisk()
    {
        return risk;  
    }
    
    /**
     * This method retrieves the number of comparisons
     * (ie the number of times this object has called its compareTo method)
     * for the particular chemical object. A method in Metrics.java uses
     * this method to compile the number of comparisons made in an entire
     * array.
     * @return comparisons, an integer.
     */
    public long getComparisons()
    {
        return comparisons;
    }
    
    /**
     * Returns a string which contains fields of information
     * for each chemical object.
     * @return string
     */
    public String toString()
    {
        return (this.getChemicalName() + "," + this.getCompoundID() + "," + this.getCleanAirAct() + "," + this.getClassification() + "," + this.getMetal() + "," + this.getMetalCategory() + "," + this.getCarcinogen() + "," + this.getFormType() + "," + this.getUnit() + "," + this.getRisk());
    }
    
    /**
     * This method takes two chemical objects and compares their respective
     * compound IDs to see which should come before the other. Based on the
     * comparison, an integer is returned depending on whether one object is
     * before, after or identical to another object. This method is critical
     * for the search and sort algorithms employed in Metrics.java. Not only
     * for the comparisons themselves, but the metrics provided: each
     * chemical object's comparisons integer is increased by one whenever its
     * respective compareTo method is called. The Metrics class uses its own
     * method which tallies up the comparisons throughout an array of 
     * chemical objects. This allows us to tally up the number of comparisons
     * each algorithm made over the course of its run.
     * 
     * @param x, the chemical object used for comparison.
     * @return an integer less than, greater than or equal to zero.
     */
    @Override
    public int compareTo(Chemical x)
    {
        comparisons++;
        if (this.getCompoundID().compareTo(x.getCompoundID())<0)
        {
            return -1;
        } 
        else if (this.getCompoundID().compareTo(x.getCompoundID())>0)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }
}
