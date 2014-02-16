/**
 * The LinearNode class functions as a node in a linked list. Each node
 * stores a LinkedBinaryTree. The purpose of this class is that while QuirkyExpert
 * processes the input text file, each line/tree can be stored in its own node in the
 * LinkedBinaryTree stack so that when the file is finished, the entire stack can be popped
 * into a tree that is used as the basis of the quiz game.
 *
 * @author Jack Christiansen
 *  adapted from code by
 *  @author Lewis, DePasquale, and Chase
 */

public class LinearNode
{
  protected LinearNode next;
  private LinkedBinaryTree element;
 
  /**
   * Creates an empty node.
   */
  public LinearNode()
  {
    next = null;
    element = null;
  }
 
  /**
   * Creates a node storing the specified LinkedBinaryTree.
   * @param elem  the LinkedBinaryTree to be stored
   */
  public LinearNode (LinkedBinaryTree elem)
  {
    next = null;
    element = elem;
  }
 
  /**
   * Returns the next node.
   * @return next node
   */
  public LinearNode getNext()
  {
    return next;
  }
 
  /**
   * Sets the next node equal to a LinkedBinaryTree.
   * @param node  the node to be set as the next node for this node
   */
  public void setNext (LinearNode node)
  {
    next = node;
  }
 
  /**
   * Returns the LinkedBinaryTree stored in this node.
   * @return  the LinkedBinaryTree that is stored within this node
   */
  public LinkedBinaryTree getElement()
  {
    return element;
  }
 
  /**
   * Sets the LinkedBinaryTree stored in this node.
   * @param elem  the LinkedBinaryTree to be stored within this node
   */
  public void setElement (LinkedBinaryTree elem)
  {
    element = elem;
  }
}
