/**
 *  @author Jack Christiansen
 *  adapted from code by
 *  @author Lewis, DePasquale, and Chase
 *
 *  The LinkedStack class stores nodes of type LinkedBinaryTree. It's designed to
 * store subtrees as a text file is scanned and processed in a certain syntax to
 * produce a playable 20 questions quiz game by constructing a large binary tree.
 */

public class LinkedStack
{
  /** Integer for the amount of objects in the stack. */
  private int count;  
  /** Whatever object is at the top of the stack. */
  private LinearNode top; 

  /**
   * Creates a new, empty stack. 0 objects, no top object.
   */
  public LinkedStack()
  {
    count = 0;
    top = null;
  }

  /**
   * Adds the specified LinkedBinaryTree to the top of this stack.
   * @param element LinkedBinaryTree to be pushed on stack
   */
  public void push (LinkedBinaryTree element)
  {
    LinearNode temp = new LinearNode(element);

    temp.setNext(top);
    top = temp;
    count++;
  }

  /**
   * Sets the LinkedBinaryTree at the top of the stack to null, lowers the
   * count index number and returns the former top item in the stack.
   * @return LinkedBinaryTree from top of stack
   */
  public LinkedBinaryTree pop()
  {
    if (isEmpty()) 
    {
        System.out.println("Error.");
        return null;
    }
    else
    {
        LinkedBinaryTree result = top.getElement();
        top = top.getNext();
        count--;
        return result;
    }
  }
   
  /**
   * Like pop, peek returns the LinkedBinaryTree from the top of the stack.
   * Unlike pop, it doesn't set the top item to null.
   * @return LinkedBinaryTree on top of stack
   */
  public LinkedBinaryTree peek()
  {
    if (isEmpty()) 
    {
        System.out.println("Empty.");
        return null;
    }
    else
    {
        return top.getElement();
    }
  }

  /**
   * Returns true if this stack is empty and false otherwise. 
   * @return boolean true if stack is empty
   */
  public boolean isEmpty()
  {
    return (count == 0);
  }
 
  /**
   * Returns the number of elements in this stack.
   * @return integer
   */
  public int size()
  {
    return count;
  }

  /**
   * Returns a string of all the items in the stack.
   * @return String
   */
  public String toString()
  {
    String result = "";
    LinearNode current = top;

    while (current != null)
    {
      result = result + (current.getElement()).toString() + "\n";
      current = current.getNext();
    }

    return result;
  }
}
