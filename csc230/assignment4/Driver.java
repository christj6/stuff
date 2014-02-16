

/**
 * The Driver class creates an instance of the QuirkyExpert class, which is
 * used to conduct a game of 20 questions. In this particular instance, the
 * user is asked about a particular Nicolas Cage film. The tree is constructed
 * from a text file. Then, an in-order traversal of the tree is printed out.
 * After that, the user gets to play the game, after which the inorder traversal
 * is printed again to verify the tree was not modified during the program's run.
 * 
 * @author Jack Christiansen
 */
public class Driver
{  
    public static void main(String[] args)
    {
        QuirkyExpert game = new QuirkyExpert();
        game.constructTree();
        game.traverse();
        game.playGame();
        game.traverse();
    } 
}
