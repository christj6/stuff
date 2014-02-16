
import java.util.InputMismatchException;

/** @author Jack Christiansen
 * The Customer class stores several fields of information for each customer,
 * including ID, gender, name, address, etc. In addition to the setters and getters
 * included to mutate and access these attributes, the class also contains a 
 * compareTo method and a toString method. The compareTo method sorts two customers
 * based on state, zip code and address, while the toString method prints all 
 * the customer's fields consecutively, with each field separated by tabs.
 * This makes it helpful to display each customer's information when the program
 * writes the text file. */  
public class Customer
{
    private int customerID;
    private String gender;
    private String firstName;
    private char middleInitial;
    private String lastName;
    private String streetAddress;
    private String city;
    private String state;
    private int zipCode;
    private String emailAddress;
    private String telephoneNumber;
    private String nationalID;
    private String birthDate;
    
    /** Constructor for the Customer class. Fills all the fields with the data input from the text file.
     * @param id corresponds with the customer's customer ID. 
     * @param gender corresponds with the customer's gender.
     * @param firstName corresponds with the customer's first name.
     * @param middleInitial corresponds with the customer's middle initial.
     * @param lastName corresponds with the customer's last name.
     * @param streetAddress corresponds with the customer's current living address.
     * @param city corresponds with the customer's current city.
     * @param state corresponds with the customer's state - abbreviated with 2 letters.
     * @param zipCode corresponds with the customer's zip code. It may be 4 or 5 digits when first assigned; however, a string formatter will provide a leading zero if needed when the toString method is called.
     * @param emailAddress corresponds with the customer's email address.
     * @param phone corresponds with the customer's home phone number.
     * @param ssid corresponds with the customer's social security number, or national ID.
     * @param birth  corresponds with the customer's birth date, in MM/DD/YYYY format.
     * */
    public Customer(int id, String gender, String firstName, char middleInitial, String lastName, String streetAddress, String city, String state, int zipCode, String email, String phone, String ssid, String birth)
    {
        this.setCustomerID(id);
        this.setGender(gender);
        this.setFirstName(firstName);
        this.setMiddleInitial(middleInitial);
        this.setLastName(lastName);
        this.setStreetAddress(streetAddress);
        this.setCity(city);
        this.setState(state);
        this.setZipCode(zipCode);
        this.setEmailAddress(email);
        this.setTelephoneNumber(phone);
        this.setNationalID(ssid);
        this.setBirthDate(birth);
    }
    
    /** Sets the customer ID equal to an input value.
     * @param id: customer ID
     * @throws InputMismatchException: handles any situation where the customer ID value is not an integer. */
    public void setCustomerID(int id)
    {
        try
        {
            customerID = id;
        }
        catch (InputMismatchException e)
        {
            System.out.println("Invalid input.");
        }
    }
    
    /** Retrieves the integer value for customer ID.
     * @return customerID */
    public int getCustomerID()
    {
        return customerID;
    }
    
    /** Sets the gender to a string scanned from the file.
     * @param gender: a string containing either "male" or "female".
     * @throws InputMismatchException: handles non-string input values. */
    public void setGender(String gender)
    {
        try
        {
            this.gender = gender;
        }
        catch (InputMismatchException e)
        {
            System.out.println("Invalid input.");
        }
    }
    
    /** Retrieves the string for the customer's gender.
     * @return gender */
    public String getGender()
    {
        return gender;
    }
    
    /** Sets the first name to a string scanned from the file.
     * @param firstName: a string containing the customer's first name.
     * @throws InputMismatchException: handles non-string input values. */
    public void setFirstName(String firstName)
    {
        try
        {
            this.firstName = firstName;
        }
        catch (InputMismatchException e)
        {
            System.out.println("Invalid input.");
        }
    }
    
    /** Retrieves the string for the customer's first name.
     * @return firstName */
    public String getFirstName()
    {
        return firstName;
    }
    
    /** Sets the middle initial to a char scanned from the file.
     * @param middleInitial: a character variable containing the customer's middle initial.
     * @throws InputMismatchException: handles non-character input values. */
    public void setMiddleInitial(char middleInitial)
    {
        try
        {
            this.middleInitial = middleInitial;
        }
        catch (InputMismatchException e)
        {
            System.out.println("Invalid input.");
        }
    }
    
    /** Retrieves the char for the customer's middle initial.
     * @return middleInitial */
    public char getMiddleInitial()
    {
        return middleInitial;
    }
    
    /** Sets the last name to a string scanned from the file.
     * @param lastName: a string containing the customer's last name.
     * @throws InputMismatchException: handles non-string input values.  */
    public void setLastName(String lastName)
    {
        try
        {
            this.lastName = lastName;
        }
        catch (InputMismatchException e)
        {
            System.out.println("Invalid input.");
        }
    }
    
    /** Retrieves the string for the customer's last name.
     * @return lastName */
    public String getLastName()
    {
        return lastName;
    }
    
    /** Sets the street address to a string scanned from the file.
     * @param streetAddress: a string containing the customer's last name.
     * @throws InputMismatchException: handles non-string input values.  */
    public void setStreetAddress(String streetAddress)
    {
        try
        {
            this.streetAddress = streetAddress;
        }
        catch (InputMismatchException e)
        {
            System.out.println("Invalid input.");
        }
    }
    
    /** Retrieves the string for the customer's street address.
     * @return streetAddress */
    public String getStreetAddress()
    {
        return streetAddress;
    }
    
    /** Sets the city to a string scanned from the file.
     * @param city: a string containing the name of a city.
     * @throws InputMismatchException: handles non-string input values. */
    public void setCity(String city)
    {
        try
        {
            this.city = city;
        }
        catch (InputMismatchException e)
        {
            System.out.println("Invalid input.");
        }
    }
    
    /** Retrieves the string for the customer's city.
     * @return city */
    public String getCity()
    {
        return city;
    }
    
    /** Sets the state to a string scanned from the file.
     * @param state: a string containing the name of a state in the US.
     * @throws InputMismatchException: handles non-string input values. */
    public void setState(String state)
    {
        try
        {
            this.state = state;
        }
        catch (InputMismatchException e)
        {
            System.out.println("Invalid input.");
        }
    }
    
    /** Retrieves the string for the customer's state abbreviation. It should be a two-letter long string.
     * @return state */
    public String getState()
    {
        return state;
    }
    
    /** Sets the state to an integer scanned from the file. If needed, a leading zero is added to the zip code in the toString method.
     * @param zipCode: a 4 or 5 digit integer containing a customer's zip code.
     * @throws InputMismatchException: handles non-integer input values. */
    public void setZipCode(int zipCode)
    {
        try
        {
            this.zipCode = zipCode;
        }
        catch (InputMismatchException e)
        {
            System.out.println("Invalid input.");
        }
    }
    
    /** Retrieves the integer for the customer's zip code. 
     * @return zipCode */
    public int getZipCode()
    {
        return zipCode;
    }
    
    /** Sets the customer's email address to a string scanned from the file.
     * @param email: a string containing the customer's email address.
     * @throws InputMismatchException: handles non-string input values. */
    public void setEmailAddress(String email)
    {
        try
        {
            emailAddress = email;
        }
        catch (InputMismatchException e)
        {
            System.out.println("Invalid input.");
        }
    }
    
    /** Retrieves the string for the customer's email address.
     * @return emailAddress */
    public String getEmailAddress()
    {
        return emailAddress;
    }
    
    /** Sets the phone number to a string scanned from the file.
     * @param phone: a string containing the customer's home phone number.
     * @throws InputMismatchException: handles non-string input values. */
    public void setTelephoneNumber(String phone)
    {
        try
        {
            telephoneNumber = phone;
        }
        catch (InputMismatchException e)
        {
            System.out.println("Invalid input.");
        }
    }
    
    /** Retrieves the string for the customer's home telephone number.
     * @return telephoneNumber */
    public String getTelephoneNumber()
    {
        return telephoneNumber;
    }
    
    /** Sets the customer's national ID/social security number to a string scanned from the file.
     * @param id: a string containing the customer's SSID number.
     * @throws InputMismatchException: handles non-string input values. */
    public void setNationalID(String id)
    {
        try
        {
            nationalID = id;
        }
        catch (InputMismatchException e)
        {
            System.out.println("Invalid input.");
        }
    }
    
    /** Retrieves the string for the customer's national ID/Social Security number.
     * @return nationalID */
    public String getNationalID()
    {
        return nationalID;
    }
    
    /** Sets the customer's birth date to a string scanned from the file.
     * @param birth: a string containing the customer's birth date in MM/DD/YYYY format.
     * @throws InputMismatchException: handles non-string input values. */
    public void setBirthDate(String birth)
    {
        try
        {
            birthDate = birth;
        }
        catch (InputMismatchException e)
        {
            System.out.println("Invalid input.");
        }
    }
    
    /** Retrieves the string for the customer's birth date, in MM/DD/YYYY format.
     * @return birthDate */
    public String getBirthDate()
    {
        return birthDate;
    }
    
    /** The compareTo method, used to sort customers, compares two Customer objects based on 3 attributes:
     * State abbreviation, in ascending order, zip code, in descending order, and home address,
     * which is the street address with the numbers omitted.
     * @param x: Refers to the Customer object being compared to.
     * @return -1, 1 or 0, depending on how the comparison is evaluated. */
    public int compareTo(Customer x)
    {        
        if (this.getState().compareTo(x.getState())<0)
        {
            return -1;
        } 
        else if (this.getState().compareTo(x.getState())>0)
        {
            return 1;
        } 
        else /** If both objects' states are the same, the zip code is evaluated. */
        {
            if (this.zipCode < x.zipCode)
            {
                return 1; /** Here, the 1 and -1 are switched to sort the zip codes by descending order. */
            } 
            else if (this.zipCode > x.zipCode)
            {
                return -1;
            } 
            else /** If both objects' zip codes are the same, the address is evaluated. */
            {
                String address = this.getStreetAddress().replaceAll("$*[0-9]", ""); /** Removes the numbers from the address without corrupting the original data. */
                String compareAddress = x.getStreetAddress().replaceAll("$*[0-9]", ""); /** compareAddress does the same as address, but for the x comparison object instead. */ 

                if (address.compareTo(compareAddress)<0)
                {
                    return -1;
                } 
                else if (address.compareTo(compareAddress)>0)
                {
                    return 1;
                } 
                else 
                {
                    return 0; /** At this point, the two customers are treated as equal, comparison-wise. */
                }
            }
        }
    }
    
    /** The toString method constructs a string using the customer's information. 
     * This method makes it easy for the file writer to write each line of the SortedData.txt file. 
     * The zip code is formatted so that it has a leading zero if the zipCode integer is only 4 digits long.
     * @return information: a string containing all the fields of the customer object. */
    public String toString()
    {
        String information; /** string that contains every information field. Each field is separated by a tab, to stay in line with the original file's formatting. */
        
        information = customerID + "\t" + gender + "\t" + firstName + "\t" + middleInitial + "\t" + lastName + "\t" + streetAddress + "\t" + city + "\t" + state + "\t" + String.format("%05d", zipCode) + "\t" + emailAddress + "\t" + telephoneNumber + "\t" + nationalID + "\t" + birthDate;
        return information;
    }
}
