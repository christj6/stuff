
import java.util.*;

/** @author Jack Christiansen
 * The PurchasingCustomer class inherits from the Customer class.
 * The main difference between the two classes is that PurchasingCustomer objects
 * contain credit card and delivery information. This information distinguishes them
 * from customers that merely browse. The toString method is overriden to accommodate
 * the new information fields. However, compareTo did not need to be rewritten, since
 * it still needs only the state, zipCode and streetAddress variables. */
    
public class PurchasingCustomer extends Customer
{
    private String creditCardType;
    private long creditCardNumber;
    private int cardVerificationValue;
    private String expirationDate;
    private String upsTrackingCode;
    
    /** Constructor for the PurchasingCustomer class. Fills all the fields with the data input from the text file. It calls the super constructor to input the information needed for normal Customers before inputting the rest of the information.
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
     * @param birth corresponds with the customer's birth date, in MM/DD/YYYY format.
     * The following parameters are exclusive to PurchasingCustomer objects:
     * @param creditCardType corresponds with the customer's credit card type. 
     * @param creditCardNumber corresponds with the customer's credit card number. 
     * @param cardVerificationValue corresponds with the customer's credit card verification value. 
     * @param expirationDate corresponds with the customer's credit card expiration date. 
     * @param upsTrackingCode corresponds with the customer's UPS tracking code. 
     * */
    public PurchasingCustomer(int id, String gender, String firstName, char middleInitial, String lastName, String streetAddress, String city, String state, int zipCode, String email, String phone, String ssid, String birth, String creditCardType, long creditCardNumber, int cardVerificationValue, String expirationDate, String upsTrackingCode)
    {
        super(id, gender, firstName, middleInitial, lastName, streetAddress, city, state, zipCode, email, phone, ssid, birth);
        this.setCreditCardType(creditCardType);
        this.setCreditCardNumber(creditCardNumber);
        this.setCardVerificationValue(cardVerificationValue);
        this.setExpirationDate(expirationDate);
        this.setUpsTrackingCode(upsTrackingCode);
    }
    
    /** Sets the credit card type to a string scanned from the file.
     * @param creditCardType: a string containing the credit card type (MasterCard, Visa, etc).
     * @throws InputMismatchException: handles non-string input values. */
    public void setCreditCardType(String creditCardType)
    {
        try
        {
            this.creditCardType = creditCardType;
        }
        catch (InputMismatchException e)
        {
            System.out.println("Invalid input.");
        }
    }
    
    /** Retrieves the string for the customer's credit card type.
     * @return creditCardType */
    public String getCreditCardType()
    {
        return creditCardType;
    }
    
    /** Sets the credit card number to a long integer scanned from the file.
     * @param creditCardNumber: a long integer containing the credit card number.
     * @throws InputMismatchException: handles non-long integer input values. */
    public void setCreditCardNumber(long creditCardNumber)
    {
        try
        {
            this.creditCardNumber = creditCardNumber;
        }
        catch (InputMismatchException e)
        {
            System.out.println("Invalid input.");
        }
    }
    
    /** Retrieves the long integer for the customer's credit card number.
     * @return creditCardNumber */
    public long getCreditCardNumber()
    {
        return creditCardNumber;
    }
    
    /** Sets the credit card verification value to an integer scanned from the file.
     * @param cardVerificationValue: an integer containing the credit card verification value.
     * @throws InputMismatchException: handles non-integer input values. */
    public void setCardVerificationValue(int cardVerificationValue)
    {
        try
        {
            this.cardVerificationValue = cardVerificationValue;
        }
        catch (InputMismatchException e)
        {
            System.out.println("Invalid input.");
        }
    }
    
    /** Retrieves the integer for the customer's credit card verification number.
     * @return cardVerificationValue */
    public int getCardVerificationValue()
    {
        return cardVerificationValue;
    }
    
    /** Sets the credit card expiration date to a string scanned from the file.
     * @param expirationDate: a string containing the credit card expiration date.
     * @throws InputMismatchException: handles non-string input values. */
    public void setExpirationDate(String expirationDate)
    {
        try
        {
            this.expirationDate = expirationDate;
        }
        catch (InputMismatchException e)
        {
            System.out.println("Invalid input.");
        }
    }
    
    /** Retrieves the string for the customer's credit card expiration date.
     * @return expirationDate */
    public String getExpirationDate()
    {
        return expirationDate;
    }
    
    /** Sets the UPS tracking code to a string scanned from the file.
     * @param upsTrackingCode: a string containing the UPS tracking code.
     * @throws InputMismatchException: handles non-string input values. */
    public void setUpsTrackingCode(String upsTrackingCode)
    {
        try
        {
            this.upsTrackingCode = upsTrackingCode;
        }
        catch (InputMismatchException e)
        {
            System.out.println("Invalid input.");
        }
    }
    
    /** Retrieves the string for the customer's UPS tracking code.
     * @return upsTrackingCode */
    public String getUpsTrackingCode()
    {
        return upsTrackingCode;
    }
    
    /** The toString method constructs a string using the purchasing customer's information. 
     * This method makes it easy for the file writer to write each line of the SortedData.txt file. 
     * The toString method is overriden here to call the toString method for the parent class, and
     * then append the additional credit card information to the original string.
     * @return information: a string containing all the fields of the PurchasingCustomer object. */
    public String toString()
    {
        String information = super.toString();
        information += "\t" + creditCardType + "\t" + creditCardNumber + "\t" + cardVerificationValue + "\t" + expirationDate + "\t" + upsTrackingCode;
        return information;
    }
}
