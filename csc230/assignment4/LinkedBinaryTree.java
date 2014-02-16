/**
 * The entire quiz tree is comprised of smaller subtrees, which are all of type
 * LinkedBinaryTree. This class is how the tree is constructed. First, 3 plain questions
 * are constructed into a tree, and then that tree and 2 more strings are themselves constructed
 * into their own tree, etc. Eventually the entire tree is constructed.
 *
 * @author Jack Christiansen
 *  adapted from code by
 *  @author Lewis, DePasquale, and Chase
 */
public class LinkedBinaryTree
{
    /**
     * The root is a binary tree node whose children comprise the rest of the tree.
     */
   protected BTNode root;

   /**
    * This constructor creates a new LinkedBinaryTree with an empty root.
    */
   public LinkedBinaryTree()
   {
      root = null;
   }

   /**
    * This constructor creates a new LinkedBinaryTree with an object the user
    * inputs as its root. 
    * @param element 
    */
   public LinkedBinaryTree (String element)
   {
      root = new BTNode(element);
   }

   /**
    * This constructor creates a new LinkedBinaryTree with a root and 2 children.
    * The root is a string (like with the previous constructor) while the children
    * are the left and right subtrees, which are of type LinkedBinaryTree. 
    * This constructor is crucial to the stack that constructs the main tree in
    * the QuirkyExpert class file.
    * 
    * @param element
    * @param left
    * @param right 
    */
   public LinkedBinaryTree (String element, LinkedBinaryTree left, LinkedBinaryTree right)
   {
      root = new BTNode(element);
      root.setLeft(left.root);
      root.setRight(right.root);
   }
   
   /** Returns the root of the tree, but not the object inside the root. */
   public BTNode getRoot()
   {
      if (root == null)
      {
          System.out.println("Error. The tree is empty.");
          return null;
      }
      else
      {
          return root;
      }
   }

   /**
    * Returns the element stored inside the root of the tree.
    * @return 
    */
   public String getRootElement()
   {
      if (root == null)
      {
          System.out.println("Error. The tree is empty.");
          return null;
      }
      else
      {
          return root.getElement();
      }
   }

   /**
    * Returns the root's left subtree.
    * @return 
    */
   public LinkedBinaryTree getLeft()
   {
      if (root == null)
      {
          System.out.println("Error. The tree is empty.");
          return null;
      }
      else
      {
          LinkedBinaryTree result = new LinkedBinaryTree();
          result.root = root.getLeft();
          return result;
      }
   }
   
   /**
    * Returns the root's right subtree.
    * @return 
    */
   public LinkedBinaryTree getRight()
   {
      if (root == null)
      {
          System.out.println("Error. The tree is empty.");
          return null;
      }
      else
      {
          LinkedBinaryTree result = new LinkedBinaryTree();
          result.root = root.getRight();
          return result;
      }
   }

   /**
    * Returns an integer of how many elements are in the tree.
    * @return 
    */
   public int size()
   {
      int result = 0;
      if (root != null)
      {
          result = root.count();
      }
      return result;
   }
   
   /**
    * Performs an in-order traversal of the tree.
    */
   public void inorder()
   {
      if (root != null)
      {
         root.inorder ();
      }
   }

}
