/** Jonathan Christiansen
    CSC 220 - 02
    11/20/2012
    Project 3 - MyDate class
    This class allows the user to create a MyDate object, which 
    has instance variables for day, month and year. The class has 
    various methods which allow the user to get and set these values, 
    compare the dates with other MyDate objects, check if a date is valid, 
    convert a date to a string, and add/subtract days from a date. */
    
public class MyDate
{
    private int day; /** Contains the particular day of the month. */
    private int month; /** Contains the particular month (1-12 inclusive). */
    private int year; /** Contains the current year (must be 1900 or above). */
    
    /** Constructor for MyDate class: takes 3 integers (day, month, year) as parameters. */
    public MyDate(int d, int m, int y)
    {
        if (isValid(d, m, y)==true)
        {
            day = d;
            month = m;
            year = y;
        } else {
            day = 1;
            month = 1;
            year = 1900; /** if initial date is invalid, it is set to January 1st, 1900. */
        }
    }
    
    /** Retrieves the day value: the integer ranges between 1 and 31 inclusively. */
    public int getDay()
    {
        return day;
    }
    
    /** Retrieves the month value: ranges between 1 and 12 inclusively. */
    public int getMonth()
    {
        return month;
    }
    
    /** Retrieves the year value: it can be 1900 or greater. */
    public int getYear()
    {
        return year;
    }
    
    /** User inputs an integer, which, if valid, is set as the day. */
    public void setDay(int d)
    {
        setDate(d, month, year);
    }
    
    /** User inputs an integer which is set as the month, if it's valid. */
    public void setMonth(int m)
    {
        setDate(day, m, year);
    }
    
    /** Same as setDate and setMonth, except with the year. User sets year to integer value. */
    public void setYear(int y)
    {
        setDate(day, month, y);
    } 
    
    /** setDate, setMonth and setYear all use setDate, changing only the parameter the user inputs.
    	With setDate, the user sets all 3 variables to desired values. If the set of values form
    	a valid date, that date becomes the new date. */
    public void setDate(int d, int m, int y)
    {
        if (isValid(d, m, y)==true)
        {
            day = d;
            month = m;
            year = y;
        }
    }
    
    /** This method maps the month int to a corresponding string. It then concatenates the month, day
    	and year together in a string separated by spaces and commas. */
    public String toString()
    {
        String dateString;
        String monthString = "";
        switch (month)
        {
            case 1:
                monthString = "January";
                break;    
            case 2:
                monthString = "February";
                break; 
            case 3:
                monthString = "March";
                break; 
            case 4:
                monthString = "April";
                break; 
            case 5:
                monthString = "May";
                break; 
            case 6:
                monthString = "June";
                break; 
            case 7:
                monthString = "July";
                break; 
            case 8:
                monthString = "August";
                break; 
            case 9:
                monthString = "September";
                break; 
            case 10:
                monthString = "October";
                break; 
            case 11:
                monthString = "November";
                break; 
            case 12:
                monthString = "December";
                break;                      
        }
        dateString = (monthString + " " + day + ", " + year);
        return dateString;
    }
    
    /** This method checks if one date is the same date as another. Compares 2 objects. */
    public boolean equals(MyDate date)
    {
        if ((this.day == date.day && this.month == date.month) && this.year == date.year)
        {
            return true;   
        } else {
            return false;   
        }
    }
    
    /** This method adds one day to the current date. Doing so may change the month and even year. 
    	The add(int n) method simply loops this function, performing it n times. */
    public void addOne()
    {
        day++;
        if (!isValid(day, month, year))
        {
            month++;
            day=1;
            if (month > 12) /** Converts December 31st to January 1st of the next year. */
            {
                month = 1;
                year++;   
            }
        }
    }
    
    /** Calls the addOne method n times. */
    public void add(int n)
    {
        while (n>0)
        {
            addOne();
            n--;
        }
    }
    
    /** This method subtracts one from the current date. */
    public void subOne()
    {
        day--;
        if (!isValid(day, month, year))
        {
            month--;
            day=31; 
            if (month < 1) /** Converts January 1st to December 31st of the previous year. */
            {
                month = 12;
                year--;   
            }
            while (!isValid(day, month, year))
            {
                day--; 
            }
        }
    }
    
    /** Calls the subOne method n times. */
    public void sub(int n)
    {
        while (n>0)
        {
            subOne();
            n--;
        }
    }
    
    /** Checks if month, year and day values are all valid. */
    public boolean isValid (int d, int m, int y)
    {
        int localDay = d;
        int localMonth = m;
        int localYear = y;
        boolean leapYear = false;
        
        if (localDay < 1 || localYear < 1900)
                return false;
                
        if (localYear % 4 == 0)
        {
            leapYear = true;
            if ((localYear % 100 == 0) && (localYear % 400 != 0)) 
            {
                leapYear = false;
            }
        }
                   
        switch (localMonth)
        {
            case 1:
                if (localDay > 31)
                    return false;
                else
                    return true;   
            case 2:
                if ((localDay > 28 && leapYear==false)||(localDay > 29 && leapYear==true))
                    return false;
                else
                    return true;  
            case 3:
                if (localDay > 31)
                    return false;
                else
                    return true;  
            case 4:
                if (localDay > 30)
                    return false;
                else
                    return true;  
            case 5:
                if (localDay > 31)
                    return false;
                else
                    return true;  
            case 6:
                if (localDay > 30)
                    return false;
                else
                    return true;  
            case 7:
                if (localDay > 31)
                    return false;
                else
                    return true;  
            case 8:
                if (localDay > 31)
                    return false;
                else
                    return true;  
            case 9:
                if (localDay > 30)
                    return false;
                else
                    return true;  
            case 10:
                if (localDay > 31)
                    return false;
                else
                    return true;  
            case 11:
                if (localDay > 30)
                    return false;
                else
                    return true;  
            case 12:
                if (localDay > 31)
                    return false;
                else
                    return true;  
            default:
                return false;
        }
    }
}
