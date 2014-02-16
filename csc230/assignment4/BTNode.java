/**
 * BTNode represents a single node inside a LinkedBinaryTree. These nodes store
 * strings, since the questions and answers in the quiz are all delivered as plain text.
 * Each node represents a certain point in the quiz, at which the user can say yes or no
 * to whatever is prompted of them.
 *
 * @author Jack Christiansen
 *  adapted from code by
 *  @author Lewis, DePasquale, and Chase
 */
public class BTNode
{
   protected String element;
   protected BTNode left;
   protected BTNode right;

   /**
    * This constructor creates a new LinkedBinaryTree node with a string
    * input by the user.
    * @param element 
    */
   public BTNode (String element)
   {
      this.element = element;
      left = right = null;
   }

   /**
    * Retrieves the string inside the node.
    * @return 
    */
   public String getElement()
   {
      return element;
   }

   /**
    * This method allows the user to change the string object inside the node.
    * @param element 
    */
   public void setElement (String element)
   {
      this.element = element;
   }

   /**
    * Returns the left child of the node.
    * @return left
    */
   public BTNode getLeft()
   {
      return left;
   }

   /**
    * Sets the node's left child equal to a node input by the user.
    * @param left 
    */
   public void setLeft (BTNode left)
   {
      this.left = left;
   }

   /**
    * Returns the right child of the node.
    * @return right
    */
   public BTNode getRight()
   {
      return right;
   }

   /**
    * Sets the node's right child equal to a node input by the user.
    * @param right 
    */
   public void setRight (BTNode right)
   {
      this.right = right;
   }

   /**
    * Returns the number of nodes in a subtree, starting at the root.
    * If the node has no children, the result is 1.
    * @return int
    */
   public int count()
   {
      int result = 1;

      if (left != null)
      {
          result += left.count();
      }

      if (right != null)
      {
          result += right.count();
      }

      return result;
   }
   
   /**
    * Performs an in-order traversal.
    */
   public void inorder()
   {
      if (left != null)
      {
          left.inorder ();
      }
      
      System.out.println(element);
      
      if (right != null)
      {
          right.inorder ();
      }
   }
}
