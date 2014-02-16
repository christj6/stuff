public abstract class Character
{
  /** instance variables */
  protected double x;
  protected double y; /** x and y denote current location */
  protected double xSpeed;
  protected double ySpeed; /** xSpeed and ySpeed denote current speed/direction */
  protected double squareWidth; /** Determines width and height of character. */

  /** constructor */
  public Character (double x, double y, double xSpeed, double ySpeed)
  {
    this.x = x;
    this.y = y;
    this.xSpeed = xSpeed;
    this.ySpeed = ySpeed;
    squareWidth = 50;
  }
  
  /** Retrieves the value for the x coordinate. */
  public double getX ()
  {
    return x;
  }
  
  /** Retrieves the value for the y coordinate. */
  public double getY ()
  {
    return y;
  }
  
  /** Retrieves the value for the x speed. */
  public double getXspeed ()
  {
    return xSpeed;
  }
  
  /** Retrieves the value for the y speed. */
  public double getYspeed ()
  {
    return ySpeed;
  }
  
  /** Sets a value for the x coordinate. */
  public void setX (double x)
  {
    this.x = x;
  }
  
  /** Sets a value for the y coordinate. */
  public void setY (double y)
  {
    this.y = y;
  }
  
  /** Sets a value for x speed. */
  public void setXspeed (double xSpeed)
  {
    this.xSpeed = xSpeed;
  }
  
  /** Sets a value for y speed. */
  public void setYspeed (double ySpeed)
  {
    this.ySpeed = ySpeed;
  }
  
  /** Uses x and y speeds to move the character around the screen. */
  public void move()
  {
    x += xSpeed;
    y += ySpeed;
  }
  
  /** Determines how the character object appears on the screen */
  abstract void display();
}
