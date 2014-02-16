

import java.io.*;
import java.util.Scanner;

/**
 * The QuirkyExpert class scans a text file, converts it into a massive binary tree
 * using a stack and various other data structures, and then allows the user to play the game
 * using this main tree. The class keeps track of how many questions are asked, how the user plays
 * the game, and the winner at the end of 3 rounds.
 * 
 * @author Jack Christiansen
 */
public class QuirkyExpert
{
    /**
     * This tree houses all the questions and branches in the game.
     */
    private LinkedBinaryTree mainTree;
    
    /**
     * This integer stores the player's score.
     */
    private int playerScore;
    
    /**
     * This integer stores the computer's score.
     */
    private int computerScore;
    
    /**
     * This constructor sets the main tree to null, and both scores to 0.
     */
    public QuirkyExpert()
    {
        mainTree = null;
        playerScore = 0;
        computerScore = 0;
    }
    
    /**
     * This method scans a text file. The text file has a syntax similar to postfix notation that computers use
     * to analyze assignment statements. There is a list of questions/answers with asterisks interjected in
     * between occasionally. This method pushes each line onto a stack, and when it encounters an asterisk,
     * it constructs a tree from the 3 things that are currently on top of the stack. Everything in the stack
     * is a LinkedBinaryTree, whether it was created using the (string) constructor or the (string, tree, tree)
     * constructor. When the file ends, the stack contains one large tree, which is pushed into the class'
     * mainTree, where it is used to conduct the 20 questions game.
     */
    public void constructTree()
    {
        /**
         * This is the stack that stores all the nodes and subtrees which are
         * ultimately constructed into the one mainTree.
         */
        LinkedStack stack = new LinkedStack();
        
        /**
         * This block of code scans the text file. It's mainly concerned with whether a line
         * contains "*" or some kind of string. It doesn't need to specify whether a line is a question
         * or an answer - the program later checks the size of the current subtree based on which node the
         * program is at in order to see if the tree has terminated yet or not.
         */
        try
        {
            BufferedReader file = new BufferedReader(new FileReader("data_postfix.txt"));
            String line = file.readLine();
            
            while (line != null)
            {
                /**
                 * If the asterisk character isn't used, the line is pushed onto the stack.
                 * Single-line questions or entire trees can be pushed onto the stack, since
                 * one of the LinkedBinaryTree constructors allows an entire tree to be
                 * constructed from one string.
                 */
                if (!line.equals("*"))
                {
                    LinkedBinaryTree tree = new LinkedBinaryTree(line);
                    stack.push(tree);
                }
                /**
                 * If the asterisk is used, a tree is constructed out of the 3
                 * preceding elements popped from the stack. This tree is pushed back into the stack,
                 * so that the tree replaces whatever the tree is comprised of. 
                 */
                if (line.equals("*"))
                {
                    String question = stack.pop().getRootElement();
                    LinkedBinaryTree right = stack.pop();
                    LinkedBinaryTree left = stack.pop();
                    
                    mainTree = new LinkedBinaryTree(question, left, right);
                    stack.push(mainTree);
                }
                
                /**
                 * This line retrieves the next line of information from the file.
                 */
                line = file.readLine();
            }
            
            /**
             * After the program finishes processing the file, there is one item left in the stack:
             * the entire tree itself. This is popped off to mainTree.
             */
            mainTree = stack.pop();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Error, file not found.");
        }
        catch(Exception e)
        {
            System.out.println("Error.");
        }
    }
    
    /**
     * This method prints an in-order traversal of the contents of the tree.
     */
    public void traverse()
    {
        System.out.println("Inorder Traversal:");
        System.out.println("");
        mainTree.inorder();
        System.out.println("");
    }
    
    /**
     * This method plays 3 rounds of 20 questions.
     * 
     * It runs a loop that terminates when round 3 concludes. In each round,
     * a value is determined for the integer temp by calling the playRound() function.
     * This value helps allocate the score between the player and the computer.
     */
    public void playGame()
    {
        int roundNumber = 1;
        System.out.println("We will play 3 rounds of 20 questions.");
        while (roundNumber <= 3)
        {
            System.out.println("");
            System.out.println("Round " + roundNumber);
            
            /**
             * This line is where the game itself is played. playRound() returns an integer value
             * which temp uses to determine whether the player or the computer won that round.
             */
            int temp = playRound();
            
            if (temp >= 10)
            {
                System.out.println("Answer took more than 10 questions. You win that round.");
                playerScore++;
            }
            else
            {
                System.out.println("Answer took less than 10 questions. I win that round.");
                computerScore++;
            }
            roundNumber++;
        }
        System.out.println("");
        System.out.println("Final scores: ");
        System.out.println("Player: " + playerScore);
        System.out.println("Computer: " + computerScore);
        System.out.println("");
        if (playerScore > computerScore)
        {
            System.out.println("You win.");
        }
        else if (playerScore < computerScore)
        {
            System.out.println("I win!");
        }
        else
        {
            System.out.println("Tie!"); /** In a 3 round game, a tie isn't possible, but it's best to account for it. */
        }
        System.out.println(""); /** Several of these blank print statements are provided for readability. */
    }
    
    /**
     * This method plays 1 round of 20 questions. It returns the number of
     * questions the computer had to ask before it arrived at the answer. This
     * number is used to determine who "won" that round.
     * Answers are recorded in the form of strings. I initially used an integer type,
     * but it was more difficult to remediate invalid input. With a string, it's easier
     * to determine whether or not a user's input is invalid. 
     * 
     * The method starts at the root of the mainTree and moves to the left or right subtree,
     * depending on the user's response. Every time it moves, it checks the size of the remaining
     * subtree to determine if there are more questions left to ask. Omitting this step causes
     * the program to call a child node that doesn't exist, leading to a null pointer exception.
     * Once the round ends, the loop terminates, and the method returns the integer of the number
     * of questions asked.
     * 
     * @return int 
     */
    public int playRound()
    {
        int questionsAnswered = -1;
        Scanner scan = new Scanner(System.in);
        String response = "";
        LinkedBinaryTree guide = mainTree;
        boolean gameOver = false;
        while (gameOver != true)
        {
            System.out.println(guide.getRootElement());
            System.out.println("0 for no, 1 for yes");
            response = scan.next();
            
            if (response.equals("0"))
            {
                if (guide.getLeft().size() > 0)
                {
                    guide = guide.getLeft();
                    questionsAnswered++;
                }
                else
                {
                    System.out.println("Round over.");
                    gameOver = true;
                }
            }
            else if (response.equals("1"))
            {
                if (guide.getRight().size() > 0)
                {
                    guide = guide.getRight();
                    questionsAnswered++;
                }
                else
                {
                    System.out.println("Round over.");
                    gameOver = true;
                }
            }
            else
            {
                /**
                 * Treats invalid user input.
                 */
                System.out.println("Please input 0 or 1.");
            }
        }
        return questionsAnswered;
    }
}
