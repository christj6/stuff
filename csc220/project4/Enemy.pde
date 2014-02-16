public class Enemy extends Character
{
  /** instance variables: */


  /** constructor: */
  public Enemy (double x, double y, double xSpeed, double ySpeed)
  {
    super(x, y, xSpeed, ySpeed);
  } 
  
  /** Moves enemy around screen, factors in boundaries and bouncing off walls. */
  public void move()
  {
    x += xSpeed;
    y += ySpeed;


    if (((x-squareWidth/2) <= 0)||((x+squareWidth/2) >= width)||((y-squareWidth/2) <= 0)||((y+squareWidth/2) >= height)) 
    {
      /** if any edge of screen is hit */
      xSpeed = random(-5, 5);
      ySpeed = random(-5, 5);
      while ( (abs ( (float)xSpeed) + abs((float)ySpeed) < 4.9) || (abs((float)xSpeed) + abs((float)ySpeed) > 5))
      {
        /** if initial random x and y speeds produce too slow/fast a vector, new values are created */
        xSpeed = random(-5, 5);
        ySpeed = random(-5, 5);
      }
    }

    /** constrain the enemy within the window */
    x=constrain((float)x, (float)(squareWidth/2), (float)(width-squareWidth/2));
    y=constrain((float)y, (float)(squareWidth/2), (float)(height-squareWidth/2));
  }
  
  /** determines if the player object came in contact with the enemy object */
  public boolean collision()
  {
    boolean collision = false;
    /** player's edges */
    double pTop = playerOne.getY() - (squareWidth/2);
    double pBottom = playerOne.getY() + (squareWidth/2);
    double pLeft = playerOne.getX() - (squareWidth/2);
    double pRight = playerOne.getX() + (squareWidth/2);
    /** enemy's edges */
    double eTop = y - (squareWidth/2);
    double eBottom = y + (squareWidth/2);
    double eLeft = x - (squareWidth/2);
    double eRight = x + (squareWidth/2);

    if (pTop <= eBottom && pBottom > eBottom) {
      if (pLeft < eRight && pRight > eLeft) {
        /** player top touch enemy bottom */
        collision = true;
      }
    }
    if (pBottom >= eTop && pTop < eTop) {
      if (pLeft < eRight && pRight > eLeft) {
        /** player bottom touch enemy top */
        collision = true;
      }
    }
    if (pRight >= eLeft && pLeft < eLeft) {
      if (pBottom > eTop && pTop < eBottom) {
        /** player right touch enemy left */
        collision = true;
      }
    }
    if (pLeft <= eRight && pRight > eRight) {
      if (pBottom > eTop && pTop < eBottom) {
        /** player left touch enemy right */
        collision = true;
      }
    }
    return collision;
  }
  
  /** Renders enemy on screen */
  public void display()
  {
    fill(0, 200, 0); /** color of body: greenish color */
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
