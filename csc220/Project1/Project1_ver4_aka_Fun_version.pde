/** Jack Christiansen
 CSC 220 - 02
 Project 1      */

/** The objective of this game is to avoid the green creature.
 The player assumes control of the red creature using the AWSD keys to move.
 The player can use the SHIFT key to temporarily double their creature's speed; however,
 if the player holds the shift key too long, their creature slows back down to normal speed.
 Whenever the enemy hits a wall or respawns, it bounces off in a random direction.
 As time goes on, the enemy and player move faster.
 When the player touches the enemy, the player loses a life and resets position.
 When the player runs out of lives, the game ends and the final score is displayed. */

PFont font; /** Only one font is used in the entire project. It displays the HUD. */

float playerCenterX;
float playerCenterY;
float playerSpeedX;
float playerSpeedY;    

/** These let multiple move keys be pressed simultaneously, which allows diagonal movement. */
boolean playerLeft;
boolean playerUp;
boolean playerDown;
boolean playerRight;

int sprint = 100;
boolean sprintOn = false;
boolean collision;
int lives = 3; /** when this number reaches 0, the game is over */
int finalScore; /** the frameCount is stored in this variable when the game ends */
boolean gameOver = false; /** this stops the game and triggers the game over screen */
float maxSpeedValue = 2;

float enemyCenterX;
float enemyCenterY;
float enemySpeedX;
float enemySpeedY;

boolean squee = false; /** creatures shrink and expand thanks to squee */

/** player and enemy are identically-sized squares. */
int squareWidth = 50;

void setup() {
  size(500, 500);
  font = loadFont("Verdana-18.vlw");
  textFont(font, 16);
  resetRound();
}

void draw() {
  if (gameOver == true) {
    background(255);
    text("Game Over", 250, 250);
    text("Final score: " + finalScore, 250, 300); /** display final score */
  } 
  else {
    /** player is playing game */
    background(255); /** clear background before redrawing everything else */
    displayHud();
    collisionTest();
    playerMove();
    playerDisplay();
    enemyMove();
    enemyDisplay();
    

    /** the squee statements make the creatures shrink and expand */
    /*
    if (squee == false) {
       squareWidth += maxSpeedValue;
        if (squareWidth>100) {
           squee = true;
        } 
    }
    
    if (squee == true) {
       squareWidth -= (maxSpeedValue/4);
      if (squareWidth < 20) {
        squee = false;
      }
    }
    */
    
    if (frameCount % 20 == 0) {
      /** this is why this version is more fun: */
      maxSpeedValue += 0.1;
    }
    
    if (collision==true) {
      /** player touched enemy */
      lives -= 1;
      if (lives > 0) {
        resetRound();
      } 
      else {
        finalScore = frameCount;
        gameOver = true;
      }
    }
  }
}

void displayHud() {
  text("score: " + frameCount, 10, 20); /** display current score */
  text("lives: " + lives, 10, 40);
  text("speed level: " + ((int)maxSpeedValue), 10, 60); /** how fast is the game right now? */
  text("Controls: A W S D to move, SHIFT to sprint", 10, 485);
  fill(255, 0, 0);
  rect(120, 6, sprint, 10); /** display sprint bar */
  if (sprintOn == false && sprint < 100) {
    /** replenish sprint bar over time */
    sprint += 1;
  }
  if (sprintOn == true && sprint > 5) {
    /** deplete sprint bar over time */
    sprint -= 5;
  }
  noFill();
  rect(120, 6, 100, 10); /** sprint bar outline (so it can be seen even when it's empty) */
}

void resetRound() {
  /** player spawns in bottom half of screen, enemy spawns in top 4/5ths of screend (randomly) */
  collision = false;
  enemyCenterX = random(squareWidth/2, width-squareWidth/2);
  enemyCenterY = random(squareWidth/2, height/1.4);
  newBounce();
  playerCenterX = width/2;
  playerCenterY = 400;
}

void keyPressed() {
  if (key == 'a' || key == 'A') {
    playerLeft = true;
  }

  if (key == 'w' || key == 'W') {
    playerUp = true;
  }

  if (key == 's' || key == 'S') {
    playerDown = true;
  }

  if (key == 'd' || key == 'D') {
    playerRight = true;
  } 

  if (key == CODED) {
    if (keyCode == SHIFT) {
      sprintOn = true;
    }
  }
}

void keyReleased() {
  if (key == 'a' || key == 'A') {
    playerLeft = false;
  }

  if (key == 'w' || key == 'W') {
    playerUp = false;
  }

  if (key == 's' || key == 'S') {
    playerDown = false;
  }

  if (key == 'd' || key == 'D') {
    playerRight = false;
  } 

  if (key == CODED) {
    if (keyCode == SHIFT) {
      sprintOn = false;
    }
  }
}

void collisionTest() {
  /** player's edges */
  float pTop = playerCenterY - (squareWidth/2);
  float pBottom = playerCenterY + (squareWidth/2);
  float pLeft = playerCenterX - (squareWidth/2);
  float pRight = playerCenterX + (squareWidth/2);
  /** enemy's edges */
  float eTop = enemyCenterY - (squareWidth/2);
  float eBottom = enemyCenterY + (squareWidth/2);
  float eLeft = enemyCenterX - (squareWidth/2);
  float eRight = enemyCenterX + (squareWidth/2);

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
}  

void playerMove() {
  if (playerLeft == true) {
    playerSpeedX = -maxSpeedValue;
  }
  if (playerRight == true) {
    playerSpeedX = maxSpeedValue;
  }
  if (playerLeft == false && playerRight == false) {
    /** stop player from moving along x axis at all */
    playerSpeedX = 0;
  }

  if (playerUp == true) {
    playerSpeedY = -maxSpeedValue;
  } 
  else if (playerDown == true) {
    playerSpeedY = maxSpeedValue;
  } 
  if (playerUp == false && playerDown == false) {
    /** stop player from moving along y axis at all */
    playerSpeedY = 0;
  }

  /** update player's coordinates */
  if (sprint > 5 && sprintOn == true) { /** increase the player's speed via shift key */

    playerCenterX += playerSpeedX * 2;
    playerCenterY += playerSpeedY * 2;
  } 
  else {
    playerCenterX += playerSpeedX;
    playerCenterY += playerSpeedY;
  }

  /** constrain the player within the window */
  playerCenterX=constrain(playerCenterX, squareWidth/2, width-squareWidth/2);
  playerCenterY=constrain(playerCenterY, squareWidth/2, height-squareWidth/2);
}

void playerDisplay() {
  stroke(0);  
  fill(200, 50, 0); /** color of body/feet: red color */
  rect(playerCenterX-squareWidth/2, playerCenterY-squareWidth/2, squareWidth, squareWidth); /** main body, face */
  /** eyes */
  fill(255);
  ellipse(playerCenterX-squareWidth/4, playerCenterY-squareWidth/5, 0.4*squareWidth, 0.4*squareWidth);
  ellipse(playerCenterX+squareWidth/4, playerCenterY-squareWidth/5, 0.4*squareWidth, 0.4*squareWidth);
  fill(0); /** pupils */
  ellipse(playerCenterX-squareWidth/4, playerCenterY-squareWidth/5, squareWidth/5, squareWidth/5);
  ellipse(playerCenterX+squareWidth/4, playerCenterY-squareWidth/5, squareWidth/5, squareWidth/5);
  /** mouth */
  fill(0);
  line(playerCenterX-squareWidth/4, playerCenterY+squareWidth/10, playerCenterX+0.16*squareWidth, playerCenterY+squareWidth/10);
}

void enemyMove() {  
  enemyCenterX += enemySpeedX;
  enemyCenterY += enemySpeedY;

  if ((enemyCenterX-squareWidth/2) <= 0) {
    /** if left side of screen is hit */
    newBounce();
  }
  if ((enemyCenterX+squareWidth/2) >= width) {
    /** if right side of screen is hit */
    newBounce();
  }
  if ((enemyCenterY-squareWidth/2) <= 0) {
    /** if top of screen is hit */
    newBounce();
  }
  if ((enemyCenterY+squareWidth/2) >= width) {
    /** if bottom of screen is hit */
    newBounce();
  }

  /** constrain the enemy within the window */
  enemyCenterX=constrain(enemyCenterX, squareWidth/2, width-squareWidth/2);
  enemyCenterY=constrain(enemyCenterY, squareWidth/2, height-squareWidth/2);
}

void newBounce() {
  /** this function assigns random x and y speeds to the enemy.
   simply pulling random numbers would create either extremely slow
   or fast speeds. To alleviate this, there are two if statements
   to constrain the enemy's general movement to the designated value.
   I couldn't have one if statement check if the absolute values of
   x and y speed were equal (too much recursion - error) so instead
   there are two if statements with a margin of 0.1. */

  enemySpeedX = random(-maxSpeedValue, maxSpeedValue);
  enemySpeedY = random(-maxSpeedValue, maxSpeedValue);
  if (abs(enemySpeedX) + abs(enemySpeedY) < maxSpeedValue-0.1) { 
    newBounce();
  }
  if (abs(enemySpeedX) + abs(enemySpeedY) > maxSpeedValue) {
    newBounce();
  }
}

void enemyDisplay() {
  fill(0, 200, 0); /** color of body/feet: greenish color */
  rect(enemyCenterX-squareWidth/2, enemyCenterY-squareWidth/2, squareWidth, squareWidth); /** main body, face */
  /** eyes */
  fill(255);
  ellipse(enemyCenterX-squareWidth/4, enemyCenterY-squareWidth/5, 0.4*squareWidth, 0.4*squareWidth);
  ellipse(enemyCenterX+squareWidth/4, enemyCenterY-squareWidth/5, 0.4*squareWidth, 0.4*squareWidth);
  fill(0); /** pupils */
  ellipse(enemyCenterX-squareWidth/4, enemyCenterY-squareWidth/5, squareWidth/5, squareWidth/5);
  ellipse(enemyCenterX+squareWidth/4, enemyCenterY-squareWidth/5, squareWidth/5, squareWidth/5);
  /** mouth */
  fill(0);
  line(enemyCenterX-squareWidth/4, enemyCenterY+squareWidth/10, enemyCenterX+0.16*squareWidth, enemyCenterY+squareWidth/10);
}
