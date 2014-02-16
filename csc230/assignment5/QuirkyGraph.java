
import java.io.*;
import java.util.*;
/**
 * The QuirkyGraph class scans 3 data files and allows a user to interact with
 * the data in various ways. It scans and sorts the information and then
 * creates an adjacency matrix which can be traversed like a graph to gather
 * information and conduct searches.
 * 
 * @author Jack Christiansen
 */
public class QuirkyGraph
{
    /**
     * This array of string stores the characters in the database.
     */
    private String[] characters;
    
    /**
     * This array stores the issues in the database.
     */
    private String[] issues;
    
    /**
     * This two-dimensional array of boolean values stores which issues are
     * connected to which characters and vice versa.
     */
    private boolean[][] adjacencyMatrix;
    
    /**
     * Creates a QuirkyGraph. The constructor creates separate arrays for both the issues
     * and the characters (with rough approximations of the data sizes) and then a 
     * two-dimensional boolean array (adjacency matrix) for storing which 
     * characters show up in which issues. 
     * 
     * The scanFile method is called because the files must be scanned before
     * the QuirkyGraph object can be used. 
     */
    public QuirkyGraph()
    {
        characters = new String[6500];
        issues = new String[12950];
        adjacencyMatrix = new boolean[characters.length][issues.length];
        scanFile();
    }
    
    /**
     * The scanFile() method scans 3 text files. These include a list of characters,
     * a list of issues, and a list of connections (appearances characters make in
     * various issues). This information is scanned into corresponding arrays, which
     * are later resized and sorted when no more characters/issues are found. 
     * As information is scanned in, strings are manipulated to be easier to work with
     * and prettier to display. Quotation marks are removed if they're visible on the outside,
     * extra spaces are removed if they're on the outside, etc.
     */
    public void scanFile()
    {
        try
        {
            /**
             * This block of code dedicates the scanner to scanning the characters
             * and filling the array of characters.
             */
            BufferedReader characterFile = new BufferedReader(new FileReader("marvelCharacterVertices.txt"));
            Scanner fileScan = new Scanner(characterFile);
            fileScan.useDelimiter("Vertex ");
            int i = 0;
            while (fileScan.hasNext())
            {
                characters[i] = fileScan.next();
                int digitLength = String.valueOf(i).length();
                characters[i] = characters[i].substring(digitLength+2); /** cut off number from beginning of string */
                
                if (characters[i].substring(0, 1).equals(" "))
                {
                    characters[i] = characters[i].substring(1); /** Removes the extra space from the beginning if it exists. */
                }
                i++;
            }
            
            /**
             * This block dedicates the scanner to scanning the issues.
             */
            BufferedReader issueFile = new BufferedReader(new FileReader("marvelIssueVertices.txt"));
            fileScan = new Scanner(issueFile);
            fileScan.useDelimiter("Vertex ");
            i = 0;
            while (fileScan.hasNext())
            {
                issues[i] = fileScan.next();
                
                int digitLength = String.valueOf(i).length();
                issues[i] = issues[i].substring(digitLength+2); /** cut off number from beginning of string */
                if (issues[i].substring(0, 1).equals(" "))
                {
                    issues[i] = issues[i].substring(1); /** Removes extra space from beginning if it exists. */
                }
                
                i++;
            }
            
            /** 
             * Finds how many non-null entries are in the characters array and uses
             * that number to determine the size of the new array. 
             */
            int tempIndex = 0;
            for (int j = 0; j < characters.length; j++)
            {
                if (characters[j] != null)
                {
                    tempIndex++;
                }
            }
            String[] temp = new String[tempIndex];
            
            /** Copies characters array into a new array with non-null entries. */
            int k = 0;
            for (int j = 0; j < characters.length; j++)
            {
                if (characters[j] != null)
                {
                    temp[k] = characters[j];
                    k++;
                }
            }
            characters = temp;
            
            /** 
             * Finds how many non-null entries are in the issues array and uses
             * that number to determine the size of the new array. 
             */
            tempIndex = 0;
            for (int j = 0; j < issues.length; j++)
            {
                if (issues[j] != null)
                {
                    tempIndex++;
                }
            }
            temp = new String[tempIndex];
            
            /** Copies issues array into a new array with non-null entries. */
            k = 0;
            for (int j = 0; j < issues.length; j++)
            {
                if (issues[j] != null)
                {
                    temp[k] = issues[j];
                    k++;
                }
            }
            issues = temp;
            
            /**
             * Sorts the arrays. This allows the program to perform binary searches on the arrays.
             */
            Arrays.sort(characters);
            Arrays.sort(issues);
            
            adjacencyMatrix = new boolean[characters.length][issues.length];
            
            /**
             * Scans the adjacency matrix.
             * The arrays were resized and sorted beforehand so the adjacency matrix
             * could be of the right dimensions. Doing the step out of order could
             * disrupt the ordering of the printed adjacency matrix, and 
             * cause a Null Pointer Exception at some point.
             */
            BufferedReader graphFile = new BufferedReader(new FileReader("marvelGraph.tsv"));
            fileScan = new Scanner(graphFile);
            fileScan.useDelimiter("\n");
            while (fileScan.hasNext())
            {
                String line = fileScan.next();
                Scanner scan = new Scanner(line);
                scan.useDelimiter("\t");
                
                String character = scan.next();
                String issue = scan.next();
                
                /** 
                 * Removes quotation marks from strings, if quotation marks are present. 
                 */
                if (character.substring(0, 1).equals("\"") && character.substring(character.length()-1, character.length()).equals("\""))
                {
                    character = (character.substring(1, character.length()-1)).trim();
                }
                if (issue.substring(0, 1).equals("\"") && issue.substring(issue.length()-1, issue.length()).equals("\""))
                {
                    issue = (issue.substring(1, issue.length()-1)).trim();
                }
                
                /**
                 * Determines which entry in the adjacency matrix should be set to true.
                 */
                setAdjacency(character, issue);
            }      
            
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File not found.");
        }
        catch(NullPointerException e)
        {
            System.out.println("Error processing data.");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * This method allows the user to find issues that a particular character appeared in.
     * It performs a breadth-first traversal starting with the character as the first 
     * node, outputting all nodes 1 edge away, which are issues the character appeared in.
     * @param target 
     */
    public void findIssues(String target)
    {
        int index = arraySearch(characters, target);
        boolean found = false;
        
        if (index < 0)
        {
            System.out.println("Character not found in database.");
        }
        else
        {
            System.out.println(characters[index] + "\n" + "appears in");
            for (int i = 0; i < issues.length; i++)
            {
                if (adjacencyMatrix[index][i] == true)
                {
                    System.out.println(issues[i]);
                    found = true;
                }
            }
        }
        if (!found)
        {
            System.out.println("None, character not found in database.");
        }
        
    }
    
    /**
     * This method allows users to find characters that appeared in a particular issue.
     * Same principle as the findIssues method, but in this method the program
     * traverses through any nodes immediately connected to a certain issue.
     * @param target 
     */
    public void findCharacters(String target)
    {
        /**
         * Retrieves the appropriate array index (issue) to search.
         */
        int index = arraySearch(issues, target);
        
        /**
         * Determines if the search actually found anything.
         */
        boolean found = false;
        
        if (index < 0)
        {
            System.out.println("Issue not found in database.");
        }
        else
        {
            System.out.println(issues[index] + "\n" + "features the following characters: ");
            for (int i = 0; i < characters.length; i++)
            {
                /**
                 * Checks each entry in the issue column and prints the appropriate entries in the corresponding row.
                 */
                if (adjacencyMatrix[i][index] == true)
                {
                    System.out.println(characters[i]);
                    found = true;
                }
            }
        }
        if (!found)
        {
            System.out.println("None, issue not found in database.");
        }
        
    }
    
    /**
     * This method allows the user to find the degree of separation between two characters;
     * that is, if the two characters appeared in the same issue, they have one degree of separation.
     * If they appeared in two distinct issues right next to each other, they have two degrees, etc.
     * @param firstCharacter
     * @param secondCharacter
     * @return
     */
    public int shortestPath(String firstCharacter, String secondCharacter)
    {
        int firstIndex = arraySearch(characters, firstCharacter);
        int secondIndex = arraySearch(characters, secondCharacter);
        int degree = 200000;
        
        if (firstIndex >= 0 && secondIndex >= 0)
        {
            for (int i = 0; i < issues.length; i++)
            {
                if (adjacencyMatrix[firstIndex][i] == true)
                {
                    for (int j = 0; j < issues.length; j++)
                    {
                         if (adjacencyMatrix[secondIndex][j] == true && Math.abs(j-i) < degree)
                         {
                             degree = Math.abs(j-i);
                         }
                    }
                }
            }
            System.out.println("Degree of separation between " + characters[firstIndex] + " and " + characters[secondIndex] + " is:");
        }
        else
        {
            System.out.println("Error, one or both of the characters not found in database.");
        }
        
        return degree+1; /** If 2 characters were in the same issue, j-i = 0, but they still have a degree of separation of 1. */
    }
    
    /**
     * This method takes in a string containing the partial name of a character and a string containing
     * the partial name of an issue and finds the appropriate entry in the adjacency matrix to set to true.
     * The right index for each array is found using a binary search and sorted arrays.
     * @param character
     * @param issue 
     */
    public void setAdjacency(String character, String issue)
    {
        adjacencyMatrix[arraySearch(characters, character)][arraySearch(issues, issue)] = true;
    }
    
    /**
     * This method retrieves the array index of a target string.
     * It performs a standard binary search for a target, but instead of
     * returning the target itself, it returns the index number, which refers
     * to where the string can be found in the corresponding array.
     * @param array
     * @param target
     * @return 
     */
    public int arraySearch(String[] array, String target)
    {
        /**
         * The target string is copied into a small substring if it is too large.
         */
        String targetCopy;
        if (target.length() > 20)
        {
            targetCopy = target.substring(0, 20);
        }
        else
        {
            targetCopy = target;
        }
        
        int top = array.length-1;
        int bottom = 0;
        int index = -1;
        
        /**
         * Continue the loop until the bottom index catches up to the top
         * or vice versa.
         */
        while (top > bottom)
        {
            index = (top + bottom)/2;
            
            /** Creates a partial string to compare to the target. */
            String comp;
            if (array[index].length() > 20)
            {
                comp = array[index].substring(0, 20);
            }
            else
            {
                comp = array[index];
            }
            
            
            if (comp.compareTo(targetCopy) == 0)
            {
                /**
                 * Index has been found.
                 */
                return index;
            }
            else
            {
                if (comp.compareTo(targetCopy) > 0)
                {
                    top = index - 1;
                }
                else if (comp.compareTo(targetCopy) < 0)
                {
                    bottom = index + 1;
                }
            }
        }
        return index;
    }
        
    /** 
     * Writes a text file using the information provided from the adjacency matrix.
     * Various techniques are implemented so that the text is indented and tabbed
     * neatly and aligned with corresponding columns.
     * 
     * @throws IOException: handles any input or output errors. 
     */
    public void writeFile()
    {   
        try
        {
            BufferedWriter fileOut = new BufferedWriter(new FileWriter("adjacencyMatrix.txt"));
            
            /** 
             * These variables were implemented so it would be easier to change
             * the size/range of the output table for debugging purposes. 
             * Warning: making issueLength = issues.length and charactersLenght = characters.length
             * will result in a 320 MB text file.
             */
            int issueStart = 0;
            int issueLength = 10;
            int charactersStart = 0;
            int charactersLength = 10;
            
            /** 
             * We're assuming the user doesn't need to read more than 70 
             * characters of a character's name to determine what character it is.  
             */
            int maxStringLength = 70;

            fileOut.write("\t\t\t\t\t\t\t\t\t\t");
            for (int i = issueStart; i < issueLength; i++)
            {
                if(issues[i].length() > 7)
                {
                    fileOut.write(issues[i].substring(0, 7) + "\t\t");
                }
                else
                {
                    fileOut.write(issues[i] + "\t\t");
                }
            }
            fileOut.newLine();
            
            fileOut.write("\t\t\t\t\t\t\t\t\t\t");
            for (int i = issueStart; i < issueLength; i++)
            {
                fileOut.write(i + "\t\t\t");
            }
            fileOut.newLine();
            fileOut.newLine();

            for (int i = charactersStart; i < charactersLength; i++)
            {
                if (characters[i].length() >= maxStringLength)
                {
                    fileOut.write(characters[i].substring(0, maxStringLength) + "\t");
                }
                else
                {
                    fileOut.write(characters[i] + "\t");
                }
                
                /** This loop gives a varying number of tabs to a string based on its length. */
                int tabOffset = characters[i].length();
                while (tabOffset < maxStringLength)
                {
                    fileOut.write(" ");
                    tabOffset += 1;
                    /** For some reason, 7 is the magic number. Anything else slides one of the entries out of place and interferes with the indenting. */
                    if ((maxStringLength - tabOffset) < 7)
                    {
                        fileOut.write("\t");
                        tabOffset += 7;
                    }
                }

                fileOut.write(i + "\t");
                
                for (int j = issueStart; j < issueLength; j++)
                {
                    if (adjacencyMatrix[i][j] == true)
                    {
                        fileOut.write("T\t\t\t");
                    }
                    else
                    {
                        fileOut.write("F\t\t\t");
                    }
                }
                fileOut.newLine();
            }

            fileOut.close(); /** Closes the file. Skipping this step may corrupt the data. */
        }
        catch (IOException e)
        {
            System.out.println("Invalid input.");
        }
    }
    
}
