public class Player extends Character
{
  /** instance variables: */
  private int nlives; /** number of lives remaining */
  private boolean left;
  private boolean right;
  private boolean up;
  private boolean down; /** these boolean variables help make the player control much, much smoother and more enjoyable. */

  /** constructor: */
  public Player (double x, double y, double xSpeed, double ySpeed)
  {
    super(x, y, xSpeed, ySpeed);
    nlives = 3;
    left = false;
    right = false;
    up = false;
    down = false;
  } 
  
  /** retrieves boolean value for player moving left */
  public boolean getLeft ()
  {
    return left;
  }
  
  /** retrieves boolean value for player moving right */
  public boolean getRight ()
  {
    return right;
  }

  /** retrieves boolean value for player moving up */
  public boolean getUp ()
  {
    return up;
  }

  /** retrieves boolean value for player moving down */
  public boolean getDown ()
  {
    return down;
  }

  /** turns on/off left movement */
  public void setLeft (boolean left)
  {
    this.left = left;
  }

  /** turns on/off right movement */
  public void setRight (boolean right)
  {
    this.right = right;
  }

  /** turns on/off up movement */
  public void setUp (boolean up)
  {
    this.up = up;
  }

  /** turns on/off down movement */
  public void setDown (boolean down)
  {
    this.down = down;
  }

  /** retrieves the player's number of lives */
  public int getLives()
  {
    return nlives;
  }

  /** decreases player's number of lives by 1 */
  public void kill()
  {
    nlives--;
  }

  /** moves player around screen, factors in wall boundaries */
  public void move()
  {
    if (left == true) {
      xSpeed = -5;
    }
    if (right == true) {
      xSpeed = 5;
    }
    if (left == false && right == false) {
      /** stop player from moving along x axis at all */
      xSpeed = 0;
    }

    if (up == true) {
      ySpeed = -5;
    } 
    else if (down == true) {
      ySpeed = 5;
    } 
    if (up == false && down == false) {
      /** stop player from moving along y axis at all */
      ySpeed = 0;
    }

    x += xSpeed;
    y += ySpeed;
    x=constrain((float)x, (float)(squareWidth/2), (float)(width-squareWidth/2));
    y=constrain((float)y, (float)(squareWidth/2), (float)(height-squareWidth/2));
  }

  /** Renders player's character on screen */
  public void display()
  {
    fill(200, 0, 0); /** color of body: red */
    rect((float)(x-squareWidth/2), (float)(y-squareWidth/2), (float)squareWidth, (float)squareWidth); /** main body, face */
    /** eyes */
    fill(255);
    ellipse((float)(x-squareWidth/4), (float)(y-squareWidth/5), (float)(0.4*squareWidth), (float)(0.4*squareWidth));
    ellipse((float)(x+squareWidth/4), (float)(y-squareWidth/5), (float)(0.4*squareWidth), (float)(0.4*squareWidth));
    fill(0); /** pupils */
    ellipse((float)(x-squareWidth/4), (float)(y-squareWidth/5), (float)(squareWidth/5), (float)(squareWidth/5));
    ellipse((float)(x+squareWidth/4), (float)(y-squareWidth/5), (float)(squareWidth/5), (float)(squareWidth/5));
    /** mouth */
    fill(0);
    line((float)(x-squareWidth/4), (float)(y+squareWidth/10), (float)(x+0.16*squareWidth), (float)(y+squareWidth/10));
  }
}
