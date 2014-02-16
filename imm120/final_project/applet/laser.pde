 
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
  
  
  void update() {
    
    //if no collision at all is detected
    px = constrain(px,-6450,6450);
    py = constrain(py,-6450,6450);
    
    
    
    line(originX,originY,px,py);
    
   //below closes update 
  }
  
  //below closes entire class
 }
