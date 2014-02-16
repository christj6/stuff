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
  
  void update() {
    
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
