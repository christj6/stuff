import java.text.DecimalFormat;

/**
 * The Metrics class records data on how long certain algorithms take,
 * and how many comparisons algorithms require in order to sort and search
 * arrays. The ChemicalData class sends a fresh, unsorted array for every
 * sort method so the data is fair for all algorithms. Time and comparisons
 * are recorded before the algorithm, and then after the algorithm has finished
 * executing, the smaller initial values are subtracted from the later, larger
 * time and comparison values to get the accurate amount of time and comparisons.
 * 
 * The DecimalFormat package is imported so that the metrics can be displayed
 * with commas, for added readability.
 * 
 * @author Jack Christiansen
 */
public class Metrics
{
    /**
     * Keeps track of the number of unique chemicals in the array.
     */
    private int chemicalCount = 0;
    
    /**
     * This is the number of comparisons the quick sort algorithm made
     * over the course of its run.
     */
    private long quickComparisons = 0;
    
    /**
     * This records the amount of time (in nanoseconds) the quick sort method took
     * sorting the array.
     */
    private long quickTime = 0;
    
    /**
     * This is the number of comparisons the merge sort algorithm made
     * over the course of its run.
     */
    private long mergeComparisons = 0;
    
    /**
     * This records the amount of time (in nanoseconds) the merge sort method took
     * sorting the array.
     */
    private long mergeTime = 0;
    
    /**
     * This is the number of comparisons the insertion sort algorithm made
     * over the course of its run.
     */
    private long insertionComparisons = 0;
    
    /**
     * This records the amount of time (in nanoseconds) the insertion sort method took
     * sorting the array.
     */
    private long insertionTime = 0;
    
    /**
     * This is the number of comparisons the bucket sort algorithm made
     * over the course of its run.
     */
    private long bucketComparisons = 0;
    
    /**
     * This records the amount of time (in nanoseconds) the bucket sort method took
     * sorting the array.
     */
    private long bucketTime = 0;
    
    /**
     * This array of variables of type long keeps track of both the
     * number of comparisons and amount of time both search methods 
     * (linear and binary) take over their runs.
     * Different searches are conducted using different targets in the
     * front, middle and end of the array.
     * One search is conducted where the target does not exist in the
     * array at all.
     * The data is allocated in the following manner:
     * index 0: Linear Search, target in the front of the array: Comparisons made over course of run
     * index 1: Linear Search front: Time
     * index 2: Linear Search middle: Comparisons
     * index 3: Linear Search middle: Time
     * index 4: Linear Search end: Comparisons
     * index 5: Linear Search end: Time
     * index 6: Linear Search, target not present: Comparisons
     * index 7: Linear Search not present: Time
     * index 8: Binary Search front: Comparisons
     * index 9: Binary Search front: Time
     * index 10: Binary Search middle: Comparisons
     * index 11: Binary Search middle: Time
     * index 12: Binary Search end: Comparisons
     * index 13: Binary Search end: Time
     * index 14: Binary Search not present: Comparisons
     * index 15: Binary Search not present: Time
     */
    private long[] searchMetrics = new long[16];
    
    /**
     * This constructor inputs the number of unique chemicals into the
     * metrics object. It's assumed the program is finished with storing
     * unique chemical objects into the array by the time the metrics
     * object is instantiated, so retrieving the value at that point
     * should produce an accurate number.
     * 
     * @param chemicalCount refers to the # of unique chemicals in the array.
     */
    public Metrics(int chemicalCount)
    {
        this.chemicalCount = chemicalCount;
    }
    
    /**
     * This method returns a string which is essentially a table.
     * The table contains execution times and comparisons for all the
     * algorithms used throughout the program. 
     * @return a string
     */
    public String toString()
    {
        String[] lines = new String[14];
        DecimalFormat fmt = new DecimalFormat("#,###,###");
        
        lines[0] = "Metrics using " + fmt.format(chemicalCount) + " chemicals";
        lines[1] = "Algorithm Name\t\t\tExecution Time\t# Comparisons";
        lines[2] = "Insertion Sort\t\t\t" + fmt.format(insertionTime) + "\t\t" + fmt.format(insertionComparisons);
        lines[3] = "Quick Sort\t\t\t" + fmt.format(quickTime) + "\t\t" + fmt.format(quickComparisons);
        lines[4] = "Merge Sort\t\t\t" + fmt.format(mergeTime) + "\t\t" + fmt.format(mergeComparisons);
        lines[5] = "Bucket Sort\t\t\t" + fmt.format(bucketTime) + "\t\t" + fmt.format(bucketComparisons);
        lines[6] = "Linear Search (front)\t\t" + fmt.format(searchMetrics[1]) + "\t\t" + fmt.format(searchMetrics[0]);
        lines[7] = "Linear Search (middle)\t\t" + fmt.format(searchMetrics[3]) + "\t\t" + fmt.format(searchMetrics[2]);
        lines[8] = "Linear Search (end)\t\t" + fmt.format(searchMetrics[5]) + "\t\t" + fmt.format(searchMetrics[4]);
        lines[9] = "Linear Search (not present)\t" + fmt.format(searchMetrics[7]) + "\t\t" + fmt.format(searchMetrics[6]);
        lines[10] = "Binary Search (front)\t\t" + fmt.format(searchMetrics[9]) + "\t\t" + fmt.format(searchMetrics[8]);
        lines[11] = "Binary Search (middle)\t\t" + fmt.format(searchMetrics[11]) + "\t\t" + fmt.format(searchMetrics[10]);
        lines[12] = "Binary Search (end)\t\t" + fmt.format(searchMetrics[13]) + "\t\t" + fmt.format(searchMetrics[12]);
        lines[13] = "Binary Search (not present)\t" + fmt.format(searchMetrics[15]) + "\t\t" + fmt.format(searchMetrics[14]);
        
        String large = "";
        for (int i = 0; i < lines.length; i++)
        {
            large += (lines[i] + "\n");
        }
        return large;
    }
    
    /**
     * The getComp method uses the Chemical class method getComparisons to
     * add each chemical object's number of compareTo calls to a running sum
     * of total comparisons.
     * 
     * @param array
     * @return a long integer, which is the number of compareTo calls made
     * by an entire array.
     */
    public long getComp(Chemical[] array)
    {
        long comparisons = 0;
        for (int i = 0; i < array.length; i++)
        {
            comparisons += array[i].getComparisons();
        }
        return comparisons;
    }
    
    /**
     * This method inputs an array of chemical objects to be sorted in a certain
     * way, based on the integer x passed in. The sort methods are:
     * x==0: Insertion Sort
     * x==1: Quick Sort
     * x==2: Merge Sort
     * x==3: Bucket Sort
     * Then, again based on the value of integer x, the sort metrics are
     * passed along to the appropriate variables. It is very important that
     * the integer x is not modified during this method call. Doing so would
     * completely disrupt the data.
     * @param x: the integer x which determines which sorting algorithm is used.
     * @param array 
     */
    public void sortMetrics(int x, Chemical[] array)
    {
        long comp = getComp(array);
        long result = System.nanoTime();

        switch(x)
        {
            case 0: 
                insertionSort(array); 
                break;
            case 1:
                quickSort(array, 0, (array.length)-1); 
                break;
            case 2: 
                mergeSort(array, 0, (array.length)-1); 
                break;
            case 3:
                bucketSort(array); 
                break;
        }
        
        result = System.nanoTime() - result;
        comp = getComp(array) - comp;

        switch(x)
        {
            case 0: 
                insertionComparisons = comp;
                insertionTime = result;
                break;
            case 1:
                mergeComparisons = comp;
                mergeTime = result;
                break;
            case 2:
                quickComparisons = comp;
                quickTime = result;
                break;
            case 3:
                bucketComparisons = comp;
                bucketTime = result;
                break;
        }
        array = null;
    }
    
    /**
     * This method inputs an array of chemical objects, and gathers metrics
     * from various search algorithms applied to it. These metrics are saved
     * to an array of long integers, such that:
     * indices 0-7 correspond with linear search,
     * indices 8-15 correspond with binary search,
     * even-numbered indices correspond with comparisons, and
     * odd-numbered indices correspond with time.
     * Two for-loops are designed accordingly to fill the right array elements.
     * 
     * The 4 linear searches and 4 binary searches are not compressed into
     * one for-loop and one switch statement because the binary searches
     * require a quick sort beforehand. Putting everything in one switch
     * statement would require the quick sort to be applied every single time
     * a binary search method was executed.
     * 
     * @param array 
     */
    public void searchMetrics(Chemical[] array)
    {
        /** Perform 4 linear searches */
        for (int i = 0; i < 8; i+=2)
        {
            long comp = getComp(array); /** Initial comparisons. */
            long result = System.nanoTime(); /** Initial time. */
            switch(i)
            {
                case 0:
                    linearSearch(array, new Chemical("924425")); /** Element near front of array. */
                    break;
                case 2:
                    linearSearch(array, new Chemical("140885")); /** Element in middle of array. */
                    break;
                case 4:
                    linearSearch(array, new Chemical("7697372")); /** Element near end of array. */
                    break;
                case 6:
                    linearSearch(array, new Chemical("ZN00")); /** Element not present in array. */
                    break;  
            }
            result = System.nanoTime() - result; /** Retrieves time duration. */
            comp = getComp(array) - comp; /** Retrieves number of comparisons. */
            searchMetrics[i] = comp;
            searchMetrics[i+1] = result;
        }
        
        /** Perform 4 binary searches */
        
        /**
         * The array must be sorted first, in order for binary search to be
         * effective.
         */
        quickSort(array, 0, (array.length)-1);
        
        
        for (int i = 8; i < 16; i+=2)
        {
            long comp = getComp(array);
            long result = System.nanoTime();
            switch(i)
            {
                case 8:
                    binarySearch(array, new Chemical("100016"));
                    break;
                case 10:
                    binarySearch(array, new Chemical("7440473"));
                    break;
                case 12:
                    binarySearch(array, new Chemical("N982"));
                    break;
                case 14:
                    binarySearch(array, new Chemical("ZN00"));
                    break;  
            }
            result = System.nanoTime() - result;
            comp = getComp(array) - comp;
            searchMetrics[i] = comp;
            searchMetrics[i+1] = result;
        }
    }
    
    /** Sorting methods */
    
    /**
     * The quick sort method chooses a partition element and moves the elements
     * in the array so that everything less than the partition element is on
     * the left and everything greater than the partition element is on the
     * right. Once this is done, the partition element is sorted and doesn't 
     * need to be sorted again. This process is repeated recursively until
     * all the elements on both sides of the partition element are sorted, and
     * therefore the array is sorted.
     * 
     * @param data: the array to be sorted.
     * @param min: the lowest index element in the array (initially equal to 0).
     * @param max: the highest index element in the array (initially equal to length-1)
     */
    public static void quickSort(Chemical[] data, int min, int max)
    {
        int pivot;
        if (min < max)
        {
            pivot = partition (data, min, max);
            quickSort(data, min, pivot-1);
            quickSort(data, pivot+1, max);
        }
    }
    
    /**
     * Partition is a support method for quick sort. 
     * It returns a partition element from the chemical object array.
     * @param data
     * @param min
     * @param max
     * @return chemical object that divides sub-array.
     */
    private static int partition(Chemical[] data, int min, int max)
    {
        Chemical partitionValue = data[min];
        int left = min;
        int right = max;
        while (left < right)
        {
            while (data[left].compareTo(partitionValue) <= 0 && left < right)
            {
                left++;
            }
            
            while (data[right].compareTo(partitionValue) > 0)
            {
                right--;
            }
            
            if (left < right)
            {
                Chemical temp = data[left];
                data[left] = data[right];
                data[right] = temp;
            }
        }
        Chemical temp = data[min];
        data[min] = data[right];
        data[right] = temp;
        
        return right;
    }
    
    /**
     * Insertion sort starts with the initial element and scans through the
     * array searching for the smallest element. When the smallest is found,
     * the two elements switch places. Essentially, the insertion sort
     * repeatedly inserts an element into its appropriate position in the
     * sorted sublist until the entire array is sorted.
     * 
     * @param data 
     */
    public static void insertionSort(Chemical[] data)
    {
        for (int index = 1; index < data.length; index++)
        {
            Chemical current = data[index];
            int position = index;
            while (position > 0 && data[position-1].compareTo(current) > 0)
            {
                data[position] = data[position-1];
                position--;
            }
            data[position] = current;
        }
    }
    
    
    /**
     * This method sorts an array of Chemical objects in the following manner:
     * First, it creates an array of strings and sets each string equal to
     * a Chemical object's compoundID, so that each string represents a
     * chemical object. Then, starting at character index 6, it places each
     * string in a "bucket" according to the ASCII value of the character at
     * index 6 (This bucket is a two-dimensional array with 128 rows and
     * array.length number of columns. There is also a value for each row that
     * determines which slot in a row should next be occupied). 
     *  
     * After all the strings have been placed in their respective buckets, the  
     * original array is modified so that each string object is brought back in 
     * order based on the order of the buckets and what element went into the 
     * bucket first. The entire process is then repeated, starting at character 
     * index 5. This continues until the strings are sorted based on character 
     * index 0, at which point they are all sorted.
     * 
     * Next, the strings need to be matched up with their corresponding chemical
     * objects so that the array of chemicals can be sorted. In a for loop,
     * the nth chemical object is found by performing a binary search where the
     * nth string is input as the compoundID parameter.
     * 
     * @param data 
     */
    public void bucketSort(Chemical[] data)
    {
        /** Array of string objects used to represent each chemical. */
        String[] array = new String[data.length];
        
        /**
         * A copy of the array of chemical objects is needed because
         * an unmodified array is needed to perform the binary search to find
         * each chemical object. Using the same array for both the sort and
         * the search would cause the array to modify itself while searching
         * itself, which would lead to an error.
         */
        Chemical[] temp = new Chemical[data.length];
        
        /**
         * This loop copies the original array data over to the array copy,
         * and sets each string object equal to a compoundID from the array.
         */
        for (int i = 0; i < data.length; i++)
        {
            temp[i] = data[i];
            array[i] = data[i].getCompoundID();
        }
        
        /** 
         * There are 256 ASCII values, but I'm assuming 
         * the user only goes up to 128. That excludes 
         * most of the unusual symbols. 
         */
        int numberOfRows = 128; 
        
        /*
         * This two-dimensional array stores the string objects.
         * The number of rows corresponds with the number of buckets
         * the algorithm will use to gather objects.
         */
        String[][] sort = new String[numberOfRows][array.length];
        
        /**
         * Each row in the two-dimensional array has its own number which
         * keeps track of which spot in the row is next to get filled
         * when an element is going to enter the bucket.
         * This is the column number, where the maximum number for the column
         * is reserved for the case where all the elements in the array
         * happen to need to go in the same bucket.
         */
        int[] column = new int[numberOfRows];
        
        /** 
         * I assumed a compoundID wouldn't be longer than 7 characters. 
         */
        int expectedMaxIndex = 6; 
        
        /**
         * This loop sorts each element in the string array by the nth character.
         * n starts at 6 and decreases until it's 0.
         */
        for (int index = expectedMaxIndex; index >= 0; index--)
        {
            /** Place each value in its corresponding bucket. */
            for (int i = 0; i < array.length; i++)
            {
                int row = 0;
                
                /** Checks if the string is long enough to be looked at in the current gathering pass. */
                if (index < array[i].length())
                {
                    row = (int)(array[i].charAt(index));
                }
                sort[row][column[row]] = array[i];
                column[row]++;
            }

            /** Sorts the elements back into the original array. */
            int element = 0;
            for (int i = 0; i < numberOfRows; i++)
            {
                for (int k = 0; k < column[i]; k++)
                {
                    array[element] = sort[i][k];
                    element++;
                }
            }
            
            /** Resets column row values back to zero. */
            for (int i = 0; i < column.length; i++)
            {
                column[i] = 0;
            }
        }
        
        /** Match the newly sorted String objects with their corresponding chemical objects. */
        for (int i = 0; i < temp.length; i++)
        {
            temp[i] = binarySearch(data, new Chemical(array[i]));
        }
        
        /** 
         * This sets the original array equal to the temporary array,
         * and then discards the temporary array. */
        data = temp;
        temp = null;
    }

    /**
     * The merge sort method chooses an element in the middle of an array and 
     * uses it to split an array in half, into 2 smaller arrays. It then 
     * recursively divides those two smaller arrays in half until each
     * individual element is by itself, and therefore inherently sorted.
     * These individual elements are then merged with each other, until the
     * entire array is sorted. 
     * 
     * @param data
     * @param min
     * @param max 
     */
    public static void mergeSort(Chemical[] data, int min, int max)
    {
        if (min < max)
        {
            int mid = (min + max)/2; /** The mid variable determines where the array should be split. */
            mergeSort (data, min, mid);
            mergeSort (data, mid+1, max);
            merge (data, min, mid, max);
        }
    }

    /**
     * Merge is a support method for merge sort.
     * @param data
     * @param first
     * @param mid
     * @param last 
     */
    private static void merge(Chemical[] data, int first, int mid, int last)
    {
        Chemical[] temp = new Chemical[(data.length)];
        int first1 = first; /** first and mid are the endpoints of the first sub-array. */
        int last1 = mid;
        int first2 = mid+1; /** mid+1 and last are the endpoints of the second sub-array. */
        int last2 = last;
        int index = first1; /** This contains the next index open in the temporary array. */
        
        /** This loop copies the smaller item from each sub-array into the temporary array
         * until one of the sub-arrays becomes empty. */
        while (first1 <= last1 && first2 <= last2)
        {
            if(data[first1].compareTo(data[first2]) < 0)
            {
                temp[index] = data[first1];
                first1++;
            } else {
                temp[index] = data[first2];
                first2++;
            }
            index++;
        }
        
        /** If there are any elements from the first sub-array leftover, they are copied over. */
        while (first1 <= last1)
        {
            temp[index] = data[first1];
            first1++;
            index++;
        }
        
        /** Same goes for the second sub-array. */
        while (first2 <= last2)
        {
            temp[index] = data[first2];
            first2++;
            index++;
        }
        
        /** This loop copies the merged data into the original array. */
        for (index = first; index <= last; index++)
        {
            data[index] = temp[index];
        }
    }
    
    /** Searching methods */
    
    /**
     * Linear search compares the target with each element of the array.
     * If the current object is not equal to the target, the search moves on
     * to the next element of the array. If the target is not present within
     * the array, the search still goes through every single element of the
     * array.
     * 
     * @param data: the array of chemical objects to be searched.
     * @param target: the particular chemical object to be found.
     * @return a chemical object.
     */
    public static Chemical linearSearch(Chemical[] data, Chemical target)
    {
        Chemical result = null;
        int index = 0;
        
        while (result == null && index < data.length)
        {
            if (data[index].compareTo(target) == 0)
            {
                result = data[index];
            }
            index++;
        }
        return result;
    }
    
    /**
     * Binary search compares the target with the middle element of a sorted
     * array. If the target is equal to this object, it is found. If the target
     * is before or after the middle, the search readjusts its first, last and
     * middle elements and performs the search again, on a smaller section of
     * the array. If the target is not present in the array, the search doesn't
     * need to check every element of the array to terminate the search.
     * Binary search is significantly more efficient than linear search.
     * 
     * @param data: the array of chemical objects to be searched.
     * @param target: the particular chemical object to be found.
     * @return a chemical object.
     */
    public static Chemical binarySearch(Chemical[] data, Chemical target)
    {
        Chemical result = null;
        int first = 0;
        int last = data.length-1;
        int mid;
        while (result == null && first <= last)
        {
            mid = (first + last)/2;
            if (data[mid].compareTo(target) == 0)
            {
                result = data[mid];
            }
            else
            {
                if (data[mid].compareTo(target) > 0)
                {
                    last = mid - 1;
                }
                else
                {
                    first = mid + 1;
                }
            }
        }
        return result;
    }
    
}
