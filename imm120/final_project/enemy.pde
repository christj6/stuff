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
  void update() { 
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
                xoff += cos(cos(radians(.01)))%magicNumberX/5;
                yoff += cos(radians(.02))%magicNumberY/5;
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

