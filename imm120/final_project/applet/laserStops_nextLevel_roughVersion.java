import processing.core.*; 
import processing.xml.*; 

import ddf.minim.*; 
import ddf.minim.signals.*; 
import ddf.minim.analysis.*; 
import ddf.minim.effects.*; 

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class laserStops_nextLevel_roughVersion extends PApplet {






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


public void setup()
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

public void draw()
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
                      enemies[3].frequency = 0.25f;
                      enemies[4].xpos = 250;
                      enemies[4].ypos = 100;
                      enemies[4].frequency = 0.5f;
                      enemies[5].xpos = 100;
                      enemies[5].ypos = 250;
                      enemies[5].frequency = 0.75f;
                      enemies[6].xpos = 250;
                      enemies[6].ypos = 250;
                      enemies[6].frequency = 1.25f;
                      
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
                    enemies[13].magicNumberX = 0.0034f;
                    enemies[13].magicNumberY = 0.01f;
                    enemies[14].magicNumberX = 0.0016f;
                    enemies[14].magicNumberY = 0.98f;
                    
                    
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



public void resetGame() {
   //depending on level # 
   
}


public void keyPressed()
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

public void keyReleased()
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

public void playerLaser () {
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


public void restartGame() {
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

public void stop()
{
  // always close Minim audio classes when you are done with them
  normalMusic.close();
  bossMusic.close();
  winMusic.close();
  minim.stop();
 
  super.stop();
}
//box (crate) class
class Crate {
  
  //put variables here
  //where is it? dimensions?
  int crateX;
  int crateY;
  int crateWidth;
  int crateHeight;
  
  //enemies[i].angle things
  float crateUR;
  float crateUL;
  float crateDR;
  float crateDL;
  
  //quad
  int crateQuad;
  
  //the HAIL MARY variable
  boolean collisionDetected;
  
  Crate (int number1, int number2, int number3, int number4) {
    
    //constructor here
    crateX = number1;
    crateY = number2;
    crateWidth = number3;
    crateHeight = number4;
    
  }
  
  public void update() {
    
    fill(30,30,30);
    stroke(0);
    rectMode(CORNER);
    rect(crateX,crateY,crateWidth,crateHeight);
    
    
    for (int i = 0; i < enemies.length; i++) {
              crateUL = ((degrees(atan2((crateY)-enemies[i].ypos,(crateX)-enemies[i].xpos)))%360)+180;
              crateUR = ((degrees(atan2((crateY)-enemies[i].ypos,(crateX+crateWidth)-enemies[i].xpos)))%360)+180;
              crateDL = ((degrees(atan2((crateY+crateHeight)-enemies[i].ypos,(crateX)-enemies[i].xpos)))%360)+180;
              crateDR = ((degrees(atan2((crateY+crateHeight)-enemies[i].ypos,(crateX+crateWidth)-enemies[i].xpos)))%360)+180;
              
              //collisionDetected = false; //false until proven otherwise
              println(collisionDetected);
              
              //following code is COPY PASTED from enemy class
                       if ((abs(enemies[i].angle%360) > 0 && abs(enemies[i].angle%360) <= 90)) { 
                          if ((crateX+crateWidth/2)>enemies[i].xpos && (crateY+crateHeight/2)<enemies[i].ypos) { 
                                 crateQuad = 1; 
                            }
                       }

                        if (abs(enemies[i].angle%360) > 90 && abs(enemies[i].angle%360) <= 180) {
                           if ((crateX+crateWidth/2)<enemies[i].xpos && (crateY+crateHeight/2)<enemies[i].ypos) {
                            //quadrant 1 = quadrant 1, that is, top right corner
                           crateQuad = 2; 
                          } 
                        }
                         
                          if (abs(enemies[i].angle%360) > 180 && abs(enemies[i].angle%360) <= 270) { 
                             if ((crateX+crateWidth/2)<enemies[i].xpos && (crateY+crateHeight/2)>enemies[i].ypos) {
                            //quadrant 1 = quadrant 1, that is, top right corner
                           crateQuad = 3; 
                             }
                          }
                             
                         if (abs(enemies[i].angle%360) > 270 && abs(enemies[i].angle%360) <= 360) {
                           if ((crateX+crateWidth/2)>enemies[i].xpos && (crateY+crateHeight/2)>enemies[i].ypos) {
                           crateQuad = 4; 
                           }
                         }
              
              
              // "Oh, switch." - Will Smith
              switch(crateQuad) {
              case 1:
                
                       if ( enemies[i].arctanLaser > crateUL && enemies[i].arctanLaser < crateDR  ) {
                                 //px = constrain(px,-6450,(crateX+crateWidth)-(crateWidth*(crateDR-enemies[i].arctanLaser)/(crateDR-crateDL)));
                                 //py = constrain(py,(crateY+crateHeight)-(crateHeight),-6450);
                                 if(enemies[i].arctanLaser>crateDL) {
                                   //laser goes along the bottom side of the cube
                                               enemies[i].px = constrain(enemies[i].px,enemies[i].xpos,(crateX+crateWidth)-crateWidth*norm(enemies[i].arctanLaser, crateDR, crateDL));
                                               enemies[i].py = (crateY+crateHeight);
                                               
                                 }
                                 if(enemies[i].arctanLaser<crateDL) {
                                   //laser goes along left side of cube
                                               enemies[i].px = crateX;
                                               enemies[i].py = constrain(enemies[i].py,(crateY+crateHeight)-crateHeight*norm(enemies[i].arctanLaser, crateDL, crateUL),-6450);
                                 }
                                 collisionDetected = true;
                      }
                      else {
                       collisionDetected = false; 
                      }
                 
                break;
              case 2: 
              
                if ( enemies[i].arctanLaser < crateUR && enemies[i].arctanLaser > crateDL  ) {
                              if(enemies[i].arctanLaser>crateDR) {
                             //laser goes along right side of cube
                                         enemies[i].px = (crateX+crateWidth);
                                         enemies[i].py = constrain(enemies[i].py,(crateY+crateHeight)-crateHeight*norm(enemies[i].arctanLaser, crateDR, crateUR),-6450);
                                       }
                           if(enemies[i].arctanLaser<crateDR) {
                             //laser on bottom side
                                         enemies[i].px = constrain(enemies[i].px,crateX+crateWidth*norm(enemies[i].arctanLaser, crateDL, crateDR),6450);
                                         enemies[i].py = (crateY+crateHeight);
                                       }
                                       collisionDetected = true;
                }
              
                break;
               
              case 3:
             
                if ( enemies[i].arctanLaser < crateUL && enemies[i].arctanLaser > crateDR  ) {
                    if(enemies[i].arctanLaser>crateUR) {
                               //laser on top side
                                           enemies[i].px = constrain(enemies[i].px,crateX+crateWidth*norm(enemies[i].arctanLaser, crateUL, crateUR),6450);
                                           enemies[i].py = crateY;
                                         }
                    if(enemies[i].arctanLaser<crateUR) {
                               //laser goes along right side of cube
                                           enemies[i].px = (crateX+crateWidth);
                                           enemies[i].py = constrain(enemies[i].py,-6450,crateY+crateHeight*norm(enemies[i].arctanLaser, crateUR, crateDR));
                                         }    
                                    collisionDetected = true;     
                }
              
                break;
              case 4:
              
                if ( enemies[i].arctanLaser > crateUR && enemies[i].arctanLaser < crateDL  ) {
                  //NOT DONE YET!
                  if(enemies[i].arctanLaser>crateUL) {
                               //laser goes along left side of cube
                                         enemies[i].px = crateX;
                                         enemies[i].py = constrain(enemies[i].py,-6450,crateY+crateHeight*norm(enemies[i].arctanLaser, crateUL, crateDL));
                                         }
                    if(enemies[i].arctanLaser<crateUL) {
                               //laser on top side
                                           enemies[i].px = constrain(enemies[i].px,-6450,(crateX+crateWidth)-crateWidth*norm(enemies[i].arctanLaser, crateUR, crateUL));
                                           enemies[i].py = crateY;
                                         }     
                                        collisionDetected = true; 
                }
              
                break;
              }
   
              
              //test for collision with player's laser
                    stroke(255, millis() % fireRate, millis() % fireRate);
                    //if (enemies[i].isHit == false) {
                            if (attackUp == true) {
                              //fix this line
                                if((xPosition > crateX) && (xPosition < crateX+crateWidth) && (yPosition-cubeHeight/2 > crateY)) {
        
                                 playerPY = crateY+crateHeight;
                                 playerPX = constrain(playerPX,crateX,crateX+crateWidth);
                                                    
                            }
                                 line(xPosition,yPosition-cubeHeight/2,playerPX,playerPY);
                            }   
                            
                            
                            
                            if (attackDown == true) {
                              //fix this line
                                if((xPosition > crateX) && (xPosition < crateX+crateWidth) && (yPosition+cubeHeight/2 < crateY)) {
        
                                 playerPY = crateY;
                                 playerPX = constrain(playerPX,crateX,crateX+crateWidth); 
                                }  
                                line(xPosition,yPosition+cubeHeight/2,playerPX,playerPY);
                            }
                            
                        
                            if (attackRight == true) {
                                if((xPosition+cubeWidth/2 < crateX) && (yPosition > crateY) && (yPosition < crateY+crateHeight)) {
        
                                 playerPX = crateX;
                                 playerPY = constrain(playerPY,crateY,crateY+crateHeight);
                                }
                                line(xPosition+cubeWidth/2,yPosition,playerPX,playerPY);
                            }
                            
                            if (attackLeft == true) {
                                if((xPosition-cubeWidth/2 > crateX+crateWidth) && (yPosition > crateY) && (yPosition < crateY+crateHeight)) {
        
                                 playerPX = crateX+crateWidth;
                                 playerPY = constrain(playerPY,crateY,crateY+crateHeight);
                                }
                                line(xPosition-cubeWidth/2,yPosition,playerPX,playerPY);
                            }
                            
     //paranth below closes enemies array if then               //}
    }
  }
 
 //below is the paranth that closes the whole class 
}

//enemy class
class Enemy { 
  float xpos;
  float ypos;
  float xoff;
  float yoff;
  
  int enemyType;
  
  //extra stuff
  boolean isHit = false;
  boolean isDead = false;
  float eHealth = 255;
  float speed;
  float magicNumberX;
  float magicNumberY;
  
  //the enemies have freaking laser beams attached to their freaking heads
  //what I meant to say is: enemy laser variables
  float px, py;
  float angle;
  float radius = 25;
  float frequency = 2;
  
  float arctanPlayer;
  float arctanLaser;
  boolean collisionDetected;

  
      Enemy (float number, float number2, int number3) {  

        xpos = number;
        ypos = number2;
        enemyType = number3;
  } 
  public void update() { 
            if (eHealth < 0) {
                //well, he should drop dead, right?
                  isDead = true;
                  //vanish
                  /* fill(255);
                  stroke(255);
                  rectMode(CENTER);
                  rect(xpos,ypos,50,50); */
                                
            }
            if (isDead == false) {
              /* xoff += cos(cos(radians(.01)))%magicNumberX/5;
              yoff += cos(radians(.02))%magicNumberY/5;
              xpos = noise(tan(xoff)) * (width-xoff);
              ypos = noise(tan(yoff)) * (height-yoff); */
              
              //xpos = 200;
             // ypos = 200;
             
              
              fill(0,eHealth,0);
              stroke(0);
              
              //these lines have a problem: yes, they constrain the enemy.
              //BUT when they do the laser attack, the player has no room to dodge.
              //This code is okay to use IF the enemy in question has no laser attack.
              /* xpos = constrain(xpos,(25),width-(25));
              ypos = constrain(ypos,(25),height-(25)); */
              
              /* if (millis()%4000 > 0 && millis()%4000 < 2000) {
               xpos += 1; 
              }
              
              if (millis()%4000 > 2000 && millis()%4000 < 4000) {
               xpos -= 1; 
              } */
              
              
              
              
              //fixed? - but use this ONLY if the enemy has a laser attack
              xpos = constrain(xpos,cubeWidth*2,width-cubeWidth*2);
              ypos = constrain(ypos,cubeHeight*2,height-cubeHeight*2);
              //xpos = 200;
              //ypos = 200;
              
              rectMode(CENTER);
              rect(xpos,ypos,50,50); 
              
              //test player for collision with enemies
              if(((xPosition+cubeWidth/2) > (xpos-25)  &&  (xPosition-cubeWidth/2) < (xpos+25)) && (yPosition+cubeHeight/2) > (ypos-25)  &&  (yPosition-cubeHeight/2) < (ypos+25)) { 
               isHurt = true;
              }
              else {
               isHurt = false; 
              }
              
              
              
              //the enemy shoots a laser. right?
              
                //line rotates around this circle
                //ellipse(xpos,ypos,25,25);
              
              // rotates rectangle around circle
              px = xpos + cos(radians(angle))*(radius/2)*500;
              py = ypos + sin(radians(angle))*(radius/2)*500;
              ellipse(xpos,ypos,25,25);
              
              
              //that looks cool, do that
              fill(255,0,0);
              rectMode(CENTER);
              rect(xpos,ypos,px/500,py/500); 
              
              
              
              //GIANT LASER
              /* fill(255,0,0);
              rectMode(CORNERS);
              rect(xpos,ypos,px,py); */
              
              //if you want the line spinning clockwise, make this line
              //angle += frequency; - that will spin clockwise
              //otherwise, MINUS goes counterclockwise
              
               angle -= frequency;
               
               if (enemyType == 2) {
                 //final boss
                xoff += cos(cos(radians(.01f)))%magicNumberX/5;
                yoff += cos(radians(.02f))%magicNumberY/5;
                xpos = noise(tan(xoff)) * (width-xoff);
                ypos = noise(tan(yoff)) * (height-yoff); 
               }
              
              
              //playerAngle = degrees(atan2(yPosition-ypos,xPosition-xpos));



              //println("laser: " + arctanLaser + ", UL: " + arctanUL + ", DR: " + arctanDR + ", UR: " + arctanUR + ", DL: " +  + arctanDL);
              //println(quadrant);
              
              
              arctanPlayer = ((degrees(atan2(yPosition-ypos,xPosition-xpos)))%360)+180;
              
              arctanLaser = (degrees(atan2(py-ypos,px-xpos))%360)+180;
              
              arctanUL = ((degrees(atan2(topEdge-ypos,leftEdge-xpos)))%360)+180;
              arctanUR = ((degrees(atan2(topEdge-ypos,rightEdge-xpos)))%360)+180;
              arctanDL = ((degrees(atan2(bottomEdge-ypos,leftEdge-xpos)))%360)+180;
              arctanDR = ((degrees(atan2(bottomEdge-ypos,rightEdge-xpos)))%360)+180;

              
              
              
                                         //println("arcDR - arcLaser/arcDR-arcDL: " + ((arctanDR-arctanLaser)/(arctanDR-arctanDL)));
                                         //man, I need to set a scale to "DR-DL" so that the min value is 0 and the max is 1.
                                         //println("arctanLaser: " + (arctanLaser) + ", px: " + px + ", py: " + py);
                                         
                            //bring back the quadrant/switch statement thing, it was lovely.
              //if (arctanLaser < 1 || arctanLaser > 359) {
                //if (bottomEdge < ypos-25) {
                  //if (abs(angle%360) > 0 && abs(angle%360) <= 90) {
                      if (xPosition>xpos && yPosition<ypos) {
                            //quadrant 1 = quadrant 1, that is, top right corner
                            
                           quadrant = 1; 
                      }
                  //}

                  //if (abs(angle%360) > 90 && abs(angle%360) <= 180) {
                           if (xPosition<xpos && yPosition<ypos) {
                            //quadrant 1 = quadrant 1, that is, top right corner
                           quadrant = 2; 
                      } 
                  //}
                //}
                
                //if (topEdge > ypos) {
                  //if (abs(angle%360) > 180 && abs(angle%360) <= 270) {
                             if (xPosition<xpos && yPosition>ypos) {
                            //quadrant 1 = quadrant 1, that is, top right corner
                           quadrant = 3; 
                             }
                  //}
                 // if (abs(angle%360) > 270 && abs(angle%360) <= 360) {
                           if (xPosition>xpos && yPosition>ypos) {
                            //quadrant 1 = quadrant 1, that is, top right corner
                           quadrant = 4; 
                           }
                  //}
                //}
              //}
              println("quadrant: " + quadrant);
              
              //and boxes
              //crates[i].update();
               for (int i = 0; i < crates.length; i++) {
                       crates[i].update(); 
                      }
              //this line of code only works if the box is in the first quadrant
        for (int i = 0; i < crates.length; i++) {
              if ((crates[i].collisionDetected == false) || (crates[i].collisionDetected = true && (crates[i].crateQuad == 1 && xPosition<crates[i].crateX && yPosition>crates[i].crateY) || (crates[i].crateQuad == 2 && xPosition>crates[i].crateX && yPosition>crates[i].crateY) || (crates[i].crateQuad == 3 && xPosition>crates[i].crateX && yPosition<crates[i].crateY) || (crates[i].crateQuad == 4 && xPosition<crates[i].crateX && yPosition<crates[i].crateY))) {
              //switch statement for top half of screen
               switch(quadrant) {
              case 1:
                 if ((abs(angle%360) > 0 && abs(angle%360) <= 90)) { 
                       if ( arctanLaser > arctanUL && arctanLaser < arctanDR  ) {
                                 //px = constrain(px,-6450,rightEdge-(cubeWidth*(arctanDR-arctanLaser)/(arctanDR-arctanDL)));
                                 //py = constrain(py,bottomEdge-(cubeHeight),-6450);
                                 if(arctanLaser>arctanDL) {
                                   //laser goes along the bottom side of the cube
                                               px = constrain(px,xpos,rightEdge-cubeWidth*norm(arctanLaser, arctanDR, arctanDL));
                                               py = bottomEdge;
                                 }
                                 if(arctanLaser<arctanDL) {
                                   //laser goes along left side of cube
                                               px = leftEdge;
                                               py = constrain(py,bottomEdge-cubeHeight*norm(arctanLaser, arctanDL, arctanUL),-6450);
                                 }
                                health -= damage/8;
                      }
                 }
                break;
              case 2: 
              if (abs(angle%360) > 90 && abs(angle%360) <= 180) {
                if ( arctanLaser < arctanUR && arctanLaser > arctanDL  ) {
                              if(arctanLaser>arctanDR) {
                             //laser goes along right side of cube
                                         px = rightEdge;
                                         py = constrain(py,bottomEdge-cubeHeight*norm(arctanLaser, arctanDR, arctanUR),-6450);
                                       }
                           if(arctanLaser<arctanDR) {
                             //laser on bottom side
                                         px = constrain(px,leftEdge+cubeWidth*norm(arctanLaser, arctanDL, arctanDR),6450);
                                         py = bottomEdge;
                                       }
                health -= damage/8;
                }
              }
                break;
               
              case 3:
              if (abs(angle%360) > 180 && abs(angle%360) <= 270) {
                if ( arctanLaser < arctanUL && arctanLaser > arctanDR  ) {
                    if(arctanLaser>arctanUR) {
                               //laser on top side
                                           px = constrain(px,leftEdge+cubeWidth*norm(arctanLaser, arctanUL, arctanUR),6450);
                                           py = topEdge;
                                         }
                    if(arctanLaser<arctanUR) {
                               //laser goes along right side of cube
                                           px = rightEdge;
                                           py = constrain(py,-6450,topEdge+cubeHeight*norm(arctanLaser, arctanUR, arctanDR));
                                         }         
                health -= damage/8;
                }
              }
                break;
              case 4:
              if (abs(angle%360) > 270 && abs(angle%360) <= 360) {
                if ( arctanLaser > arctanUR && arctanLaser < arctanDL  ) {
                  //NOT DONE YET!
                  if(arctanLaser>arctanUL) {
                               //laser goes along left side of cube
                                         px = leftEdge;
                                         py = constrain(py,-6450,topEdge+cubeHeight*norm(arctanLaser, arctanUL, arctanDL));
                                         }
                    if(arctanLaser<arctanUL) {
                               //laser on top side
                                           px = constrain(px,-6450,rightEdge-cubeWidth*norm(arctanLaser, arctanUR, arctanUL));
                                           py = topEdge;
                                         }      
                health -= damage/8;
                }
              }
                break;
              }
              //coldet paranth
              }
              
              //for array crate paranth
        }
        
              
              println("arctanLaser: " + arctanLaser + ", arctanUL: " + arctanUL + ", arctanUR: " + arctanUR);
              /* if (abs(angle%360) >  170 && abs(angle%360) < 190) {
                //something happens here
                   if (abs(arctanLaser-180)>abs(arctanUL-180) && abs(arctanLaser-180)>abs(arctanUR-180)) {
                    //blah 
                    //background(0);
                    px = rightEdge;
                    py = constrain(py,topEdge,bottomEdge);
                   }
              }
              */
             
             
              
              //test for the player colliding with enemy's LASER
                  //I'll get to that.
              //px = xpos + cos(radians(angle))*(radius/2)*500;
             // py = ypos + sin(radians(angle))*(radius/2)*500;
            //println("px: " + px + " py: " + py);

             //println("cos rad ang: " + (cos(radians(angle))) + ", sin rad ang: " + (sin(radians(angle))) + " ");
              //println("angle: " + (angle % 360) + " and the other number: " + (degrees(atan2(ypos-yPosition,xpos-xPosition))));
              //println("angle: " + (angle % 360) + ", playerAngle: " + (degrees(playerAngle % 360)));
              //println("xPosition-xpos: " + (xPosition-xpos) + ", yPosition-ypos: " + (yPosition-ypos));
              
              if (laserRed == false) {
               isHit = false; 
              }
              
              //normal laser for enemy
              stroke(255,0,0);
              line(xpos, ypos, px, py);
              
              //test for collision with player's laser
                    stroke(255, millis() % fireRate, millis() % fireRate);
                    if (attackUp == true) {
                      //fix this line
                        if((xPosition > xpos-25) && (xPosition < xpos+25) && (yPosition-cubeHeight/2 > ypos-25)) {
                         //do damage on enemy
                               if(laserRed==true) {
                         isHit = true; 
                         collisionDetected = true;
                               } 
                         playerPY = ypos+25;
                         playerPX = constrain(playerPX,xpos-25,xpos+25);
                                            
                    }
                         line(xPosition,yPosition-cubeHeight/2,playerPX,playerPY);
                    }   
                    
                    
                    
                    if (attackDown == true) {
                      //fix this line
                        if((xPosition > xpos-25) && (xPosition < xpos+25) && (yPosition+cubeHeight/2 < ypos-25)) {
                         //do damage on enemy
                         if(laserRed==true) {
                         isHit = true; 
                         collisionDetected = true;
                               } 
                         playerPY = ypos-25;
                         playerPX = constrain(playerPX,xpos-25,xpos+25); 
                        }  
                        line(xPosition,yPosition+cubeHeight/2,playerPX,playerPY);
                    }
                    
                
                    if (attackRight == true) {
                        if((xPosition+cubeWidth/2 < xpos-25) && (yPosition > ypos-25) && (yPosition < ypos+25)) {
                         //do damage on enemy
                         if(laserRed==true) {
                         isHit = true; 
                         collisionDetected = true;
                               }  
                         playerPX = xpos-25;
                         playerPY = constrain(playerPY,ypos-25,ypos+25);
                        }
                        line(xPosition+cubeWidth/2,yPosition,playerPX,playerPY);
                    }
                    
                    if (attackLeft == true) {
                        if((xPosition-cubeWidth/2 > xpos+25) && (yPosition > ypos-25) && (yPosition < ypos+25)) {
                         //do damage on enemy
                         if(laserRed==true) {
                         isHit = true;
                        collisionDetected = true; 
                               } 
                         playerPX = xpos+25;
                         playerPY = constrain(playerPY,ypos-25,ypos+25);
                        }
                        line(xPosition-cubeWidth/2,yPosition,playerPX,playerPY);
                    }
                    
                    //and if you just want to go kamikaze, fine.
                    if ( (attackUp == true) || (attackDown == true) || (attackLeft == true) || (attackRight == true)) {
                      //now if the player is INSIDE the enemy
                             if (isHurt == true) {
                               //sorry, gotta remove that line - makes the game too easy
                                //isHit = true;
                             } 
                    }
                    
                    //hurt?
                    if (isHit == true) {
                      eHealth -= damage;
                    }
                    
              }
              
             
            
            
            }
            
  } 


 
 class Laser {
  
  //variables
  float originX;
  float originY;
  float px;
  float py;
  
  //constructor:
  Laser (float firstX, float firstY, float secondX, float secondY) {
    originX = firstX;
    originY = firstY;
    px = secondX;
    py = secondY; 
  }
  
  
  public void update() {
    
    //if no collision at all is detected
    px = constrain(px,-6450,6450);
    py = constrain(py,-6450,6450);
    
    
    
    line(originX,originY,px,py);
    
   //below closes update 
  }
  
  //below closes entire class
 }
  static public void main(String args[]) {
    PApplet.main(new String[] { "--bgcolor=#D4D0C8", "laserStops_nextLevel_roughVersion" });
  }
}
