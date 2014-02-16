/** Jonathan Christiansen
    CSC 220 - 02
    11/20/2012
    Project 3 - MyDateTester 
    This program is a driver for the MyDate class. 
    It instantiates two MyDate objects and performs 
    several operations on them using the methods from 
    the MyDate class. */
    
public class MyDateTester
{
    public static void main (String[] args)
    {
        MyDate date1 = new MyDate(31, 12, 1999);
        MyDate date2 = new MyDate(27, 2, 2003);
        
        date1.setDay(1);
        date1.setMonth(2);
        date1.setYear(1998);
        
        date2.setDay(4);
        date2.setMonth(1);
        date2.setYear(2009);
        
        System.out.println(date1.getDay() + "/" + date1.getDay() + "/" + date1.getYear());
        System.out.println(date2.getDay() + "/" + date2.getMonth() + "/" + date2.getYear());
        
        date1.setDate(1, 1, 2001);
        date2.setDate(1, 1, 2003);
        
        date1.add(365);
        
        date2.sub(365);
        
        System.out.println(date1);
        System.out.println(date2);
        
        if (date1.equals(date2))
        {
            System.out.println("The dates are equal.");
        }
    }   
}
    
