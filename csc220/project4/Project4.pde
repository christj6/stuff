/** Jack Christiansen
 CSC 220 - 02
 Project 4      */

PFont font;
int finalScore;

Player playerOne = new Player(100, 100, 0, 0);
Enemy[] enemies = new Enemy[3];

void setup()
{
  size(500, 500);
  font = loadFont("Verdana-18.vlw");
  textFont(font, 16);
  enemies[0] = new Enemy(300, 120, 3, 3);
  enemies[1] = new Enemy(200, 120, 3, 3);
  enemies[2] = new Enemy(250, 120, 3, 3);
}

void draw()
{
  background(255);
  if (playerOne.getLives() < 1)
  {
    /** Game over */
    text("Game Over", 250, 250);
    text("Final score: " + finalScore, 250, 300); /** display final score */
  } 
  else 
  {
    text("score: " + frameCount, 10, 20); /** display current score */
    text("lives: " + playerOne.getLives(), 10, 40);
    text("Controls: A W S D to move", 10, 485);
    playerOne.move();
    playerOne.display();

    enemies[0].move(); 
    enemies[0].display();
    enemies[1].move(); 
    enemies[1].display();
    enemies[2].move(); 
    enemies[2].display();

    if ((enemies[0].collision() == true) || (enemies[1].collision() == true) || (enemies[2].collision() == true))
    {
      playerOne.kill();
      finalScore = frameCount;
      /** respawn player and enemies in initial positions */
      playerOne.setX(100);
      playerOne.setY(100);
      enemies[0] = new Enemy(300, 120, 3, 3);
      enemies[1] = new Enemy(200, 120, 3, 3);
      enemies[2] = new Enemy(250, 120, 3, 3);
    }
  }
}

void keyPressed() {
  if (key == 'a' || key == 'A') {
    playerOne.setLeft(true);
  }

  if (key == 'w' || key == 'W') {
    playerOne.setUp(true);
  }

  if (key == 's' || key == 'S') {
    playerOne.setDown(true);
  }

  if (key == 'd' || key == 'D') {
    playerOne.setRight(true);
  }
}

void keyReleased() {
  if (key == 'a' || key == 'A') {
    playerOne.setLeft(false);
  }

  if (key == 'w' || key == 'W') {
    playerOne.setUp(false);
  }

  if (key == 's' || key == 'S') {
    playerOne.setDown(false);
  }

  if (key == 'd' || key == 'D') {
    playerOne.setRight(false);
  }
}
