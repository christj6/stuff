

/**
 * The BaseCon class converts an integer of base 10 into a value of a target
 * base, chosen by the user. It uses a stack (implemented by an array) to store
 * each remainder of the division operations needed to convert the value.
 * @author Jack Christiansen
 */
public class BaseCon
{
    /**
     * This is the initial base 10 integer to be converted.
     */
    private int input;
    
    /**
     * This is the target base that the input will be converted to.
     */
    private int base;
    
    /**
     * This array stores the remainders from the division operations
     */
    private int[] stack;
    
    /**
     * This number denotes the current top of the stack. It's initialized at
     * zero because the stack is initially empty.
     */
    private int top = 0;
    
    /**
     * The constructor creates an instance of the BaseCon class using an
     * input value and a base value. 
     * @param input
     * @param base 
     */
    public BaseCon (int input, int base)
    {
        this.input = input;
        this.base = base;
        stack = new int[input*2];
    }
    
    /**
     * This method converts the base 10 input value to a value represented by
     * the target base. It does so using two loops. One pushes each remainder
     * of each division operation into the top of the stack. The next loop
     * pops each value off, in reverse order, into a string that contains the
     * entire value.
     * @return output: the string that contains the final value.
     */
    public String convert()
    {
        /** This loop populates the stack. */
        while (input > 0)
        {
            push(input%base);
            input = input/base;
        }
        
        String output = "";
        pop();
        
        /** This loop populates the string. */
        while (top > -1)
        {
            output += pop();
        }
        
        return output;
    }
    
    /**
     * This method takes an input value and designates it the new top of stack.
     * @param value: the new value for the top of stack.
     */
    public void push(int value)
    {
        stack[top] = value;
        top++;
    }
    
    /**
     * This method returns the current top of stack value before erasing it
     * and designating the penultimate item the new top of stack.
     * @return value: value at top of stack before the top is decreased by one.
     */
    public int pop()
    {
        int value = stack[top];
        stack[top] = -1;
        top--;
        return value;
    }
}
