import ddf.minim.*;
import ddf.minim.signals.*;
import ddf.minim.analysis.*;
import ddf.minim.effects.*;

Minim minim;
AudioSnippet normalMusic;
AudioSnippet bossMusic;
AudioSnippet winMusic;
//this has a soundtrack?
/* import arb.soundcipher.*;


SoundCipher sc = new SoundCipher(this);
SoundCipher mel = new SoundCipher(this);
float pitch = 60; */

//interactive sketch
int xPosition = 350;
int yPosition = 450;
//up left, up right, down left, down right:
float topEdge;
float bottomEdge;
float leftEdge;
float rightEdge;

float arctanPlayer;
float arctanUL;
float arctanUR;
float arctanDL;
float arctanDR;

float playerPX;
float playerPY;

//other player cube stuff
int cubeWidth = 30;
int cubeHeight = 30;
float health = 255;
float damage = 64;
float speed = 5;
float acceleration = 2;

boolean left = false;
boolean right = false;
boolean up = false;
boolean down = false;
boolean attackLeft = false;
boolean attackRight = false;
boolean attackUp = false;
boolean attackDown = false;
boolean isHurt = false;

boolean laserRed;
int fireRate = 510;
int quadrant;

int levelNumber = 1;

//Enemy e1 = new Enemy(200,200,1);
Enemy[] enemies = new Enemy[15];
//Enemy e2 = new Enemy(300,300,1);



//Crate c1 = new Crate(300,300,50,50);
Crate[] crates = new Crate[4];

//final boss image
PImage nicolasCage;


void setup()
{
  size(400,400);
  frameRate(60);
  
  //final boss image
  nicolasCage = loadImage("nc.png");
  
  for (int i = 0; i < enemies.length; i++) {
 enemies[i] = new Enemy(200,200,1); 
        }

                    
                    enemies[13].enemyType = 2;
                    enemies[14].enemyType = 2;
    
        for (int i = 0; i < crates.length; i++) {
 crates[i] = new Crate(300,300,50,50); 
        }
//soundtrack
//sc.instrument = sc.CELLO;
  //mel.instrument = mel.VIBRAPHONE;
  minim = new Minim(this);
  normalMusic = minim.loadSnippet("normalmusic.mp3");
  normalMusic.setLoopPoints(0, 27480);
  
  bossMusic = minim.loadSnippet("bossmusic.mp3");
  bossMusic.setLoopPoints(0, 34320);
  
  winMusic = minim.loadSnippet("youarewinner.mp3");
  winMusic.setLoopPoints(0, 29470);
  
   if (levelNumber < 5) {
   normalMusic.loop(); 
  }
  
  
  
}

void draw()
{
  //soundtrack
  /* if (millis()%1000 < 100) {
  sc.playNote((levelNumber*12), 100, 1);
  pitch = mel.pcRandomWalk(pitch, 2, mel.MAJOR);
  mel.playNote(pitch,100,1);
  } */
  if (levelNumber == 5) {
   imageMode(CENTER);
   image(nicolasCage,enemies[9].xpos,enemies[9].ypos); 
  }

  
  if (health < 0) {  
          
           restartGame();
     }
     else {

                       background(255);
                       
                       //used for firerate
                        float m = millis();
                       
                       
                      
                       //check health and stuff
                       if (isHurt == true) {
                         // drain your life force
                          health -= 3;
                       }
                       
                       if (health < 30) {
                                   //flicker, warn the player
                                      //draw cube
                               stroke(0);
                               fill(m % 255,0,0);
                               rectMode(CENTER);
                               rect(xPosition,yPosition,cubeWidth,cubeHeight);
                          }
                       else if(health ==30 || health > 30) {
                                //all good 
                                  //draw cube
                               stroke(0);
                               fill(health,0,0);
                               rectMode(CENTER);
                               rect(xPosition,yPosition,cubeWidth,cubeHeight);
                       }
                       
                      
                      
                       
                       
                       //use boolean variables to move the thing
                       if (left==true)
                       {
                         xPosition -= speed;
                       }
                       
                       if(right==true)
                       {
                         xPosition += speed;
                       }
                       
                       if(up==true)
                       {
                         yPosition -= speed;
                       }
                       
                       if(down==true)
                       {
                         yPosition += speed;
                       }
                       
                       
                       
                      playerLaser();
                       
                       //position of corners (important for lasers):
                       topEdge = yPosition - cubeHeight/2;;
                       bottomEdge = yPosition + cubeHeight/2;
                       leftEdge = xPosition - cubeWidth/2;
                       rightEdge = xPosition + cubeWidth/2;
                       
                       
                      
                      
                      //update enemy positions
                      //e1.update();
                      //e2.update();
                      
                     /* for (int i = 0; i < enemies.length; i++) {
                       enemies[i].update(); 
                      } */
                      
                      switch(levelNumber) {
                    case 1:
                    //enemies for level 1
                    enemies[0].xpos = 200;
                    enemies[0].ypos = 200;

                    enemies[0].update();
                    if (enemies[0].isDead == true) {
                     levelNumber += 1;
                     restartGame(); 
                    }
                    break;
                    case 2:
                    //enemies for level 2
                      enemies[1].xpos = 100;
                      enemies[1].ypos = 100;
                      enemies[2].xpos = 250;
                      enemies[2].ypos = 100;
                    enemies[1].update();
                    enemies[2].update();
                    if (enemies[1].isDead && enemies[2].isDead == true) {
                     levelNumber += 1;
                     restartGame(); 
                    }
                    break;
                    case 3:
                    //enemies for level 3
                      enemies[3].xpos = 100;
                      enemies[3].ypos = 100;
                      enemies[3].frequency = 0.25;
                      enemies[4].xpos = 250;
                      enemies[4].ypos = 100;
                      enemies[4].frequency = 0.5;
                      enemies[5].xpos = 100;
                      enemies[5].ypos = 250;
                      enemies[5].frequency = 0.75;
                      enemies[6].xpos = 250;
                      enemies[6].ypos = 250;
                      enemies[6].frequency = 1.25;
                      
                    enemies[3].update();
                    enemies[4].update();
                    enemies[5].update();
                    enemies[6].update();
                    if (enemies[3].isDead && enemies[4].isDead && enemies[5].isDead && enemies[6].isDead == true) {
                     levelNumber += 1;
                     restartGame(); 
                    }
                    break;
                    case 4:
                      enemies[7].xpos = 100;
                      enemies[7].ypos = 100;
                      enemies[7].frequency = 1;
                      enemies[8].xpos = 250;
                      enemies[8].ypos = 100;
                      enemies[8].frequency = 2;
                      enemies[9].xpos = 100;
                      enemies[9].ypos = 250;
                      enemies[9].frequency = 3;
                      enemies[10].xpos = 250;
                      enemies[10].ypos = 250;
                      enemies[10].frequency = 4;
                      
                    enemies[7].update();
                    enemies[8].update();
                    enemies[9].update();
                    enemies[10].update();
                    if (enemies[7].isDead && enemies[8].isDead && enemies[9].isDead && enemies[10].isDead == true) {
                     levelNumber += 1;
                     restartGame(); 
                    }
                    break;
                    case 5:
                    //enemies for level 5 - aka, final boss
                    imageMode(CENTER);
                    image(nicolasCage,200,200);
                    enemies[13].magicNumberX = 0.0034;
                    enemies[13].magicNumberY = 0.01;
                    enemies[14].magicNumberX = 0.0016;
                    enemies[14].magicNumberY = 0.98;
                    
                    
                    if (millis()%40000 < 10000) {
                    //enemies[14].frequency *= -2;
                    }
                    else if (millis()%40000 >= 10000) {
                    enemies[14].frequency = -2;  
                    }
                    
                    enemies[13].update();
                    enemies[14].update();
                    if (enemies[13].isDead == true && enemies[14].isDead == true) {
                     //you won the game
                     levelNumber += 1;
                     restartGame();
                    }
                    break;
                    case 6:
                    //6 level just so win music loops properly
                    break;
                      }
                     
                      
                         
                      
                      
                       //constrain x and y variables so cubeboy doesn't go offscreen
                       xPosition = constrain(xPosition,(cubeWidth/2),width-(cubeWidth/2));
                       yPosition = constrain(yPosition,(cubeHeight/2),height-(cubeHeight/2));
 
     }
}



void resetGame() {
   //depending on level # 
   
}


void keyPressed()
{
  //left
  if(key == 'a')
   {
     left = true;
   }
   
   //right
   if(key == 'd')
   {
   right = true;
   }
   
   //up
   if(key == 'w')
   {
     //up
     up = true;
   }
   
    if(key=='s')
   {
     down = true;
   }
   
   if(key==' ') {
     //e1.angle -= e1.frequency; 
   }


 //and attack functions
   if (key == CODED) {
   //left
  if(keyCode == LEFT)
   {
     attackLeft = true;
     attackRight = false;
     attackUp = false;
     attackDown = false;
   }
   
   //right
   if(keyCode == RIGHT)
   {
   attackRight = true;
   attackLeft = false;
     attackUp = false;
     attackDown = false;
   }
   
   //up
   if(keyCode == UP)
   {
     attackUp = true;
     attackRight = false;
     attackLeft = false;
     attackDown = false;
   }
   
    if(keyCode==DOWN)
   {
     attackDown = true;
     attackRight = false;
     attackUp = false;
     attackLeft = false;
   }
   }
}

void keyReleased()
{
  //left
  if(key == 'a')
   {
     left = false;
   }
   
   //right
   if(key == 'd')
   {
   right = false;
   }
   
   //up
   if(key == 'w')
   {
     //up
     up = false;
   }
   
   if(key=='s')
   {
     down = false;
   }


//and attack functions
   if (key==CODED) {
   //left
  if(keyCode == LEFT)
   {
     attackLeft = false;
   }
   
   //right
   if(keyCode == RIGHT)
   {
   attackRight = false;
   }
   
   //up
   if(keyCode == UP)
   {
     attackUp = false;
   }
   
    if(keyCode==DOWN)
   {
     attackDown = false;
   }
   }
}

void playerLaser () {
  //a laser can only collide with one thing. Right?
 //check what thing the laser is colliding with
 //damage?
                       if ((millis() % fireRate) < 12) {
                          //do damage to enemy 
                          laserRed = true;
                       }
                       else if ((millis() % fireRate) > 12) {
                          //nothing 
                          laserRed = false;
                       }
                       
                       stroke(255, millis() % fireRate, millis() % fireRate);
                       
                     if(attackUp==true) {
                        playerPX = xPosition;
                        playerPY = 0;
                        //line(xPosition,yPosition-cubeHeight/2,playerPX,playerPY);
                       }
                       
                     if(attackDown==true) {
                         playerPX = xPosition;
                         playerPY = 400;
                         //line(xPosition,yPosition+cubeHeight/2,playerPX,playerPY);
                       }
                       
                     if(attackLeft==true) {
                         playerPX = 0;
                         playerPY = yPosition;
                         //line(xPosition-cubeWidth/2,yPosition,playerPX,playerPY);
                       }
                       
                     if(attackRight==true) {
                         playerPX = 400;
                         playerPY = yPosition;
                         //line(xPosition+cubeWidth/2,yPosition,playerPX,playerPY);
                       }
  
}


void restartGame() {
  //the player died, so... restart the game
           health = 255;
           xPosition = 350;
           yPosition = 450;
           
           //enemies, too.
           //e1.eHealth = 255;
           //e1.angle = 0;
           for (int i = 0; i < enemies.length; i++) {
             enemies[i].eHealth = 255;
             enemies[i].isDead = false;
             enemies[i].angle = 0; 
            }
            //find a way to make this line run only once
            if (levelNumber == 5) {
                normalMusic.pause();
                bossMusic.loop(); 
              }
             if (levelNumber == 6) {
               health += 50000;
              bossMusic.pause();
             winMusic.loop(); 
             }
}

void stop()
{
  // always close Minim audio classes when you are done with them
  normalMusic.close();
  bossMusic.close();
  winMusic.close();
  minim.stop();
 
  super.stop();
}
