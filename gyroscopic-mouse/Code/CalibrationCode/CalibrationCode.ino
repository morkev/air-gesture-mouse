#include <SD.h>

#include <Keyboard.h>


#include <MPU6050.h>
#include <helper_3dmath.h>

#include <Wire.h>

#include <Mouse.h>

#include <I2Cdev.h>

//Code to calibrate the ThimbleKrox
//To calibrate the device run this code and follow the tutorial found at https://github.com/Magform/ThimbleKrox
//The lines that need to be changed (as the other code) for calibration have "//calibration line" 
//code write by Magform


MPU6050 mpu;
int16_t ax, ay, az, gx, gy, gz;
int vx, vy;
int Nwrong=1;

void setup() {
  Nwrong=1;
  Serial.begin(9600);
  Wire.begin();
  mpu.initialize();
  while(Nwrong!=0){
    if (!mpu.testConnection()) { 
        Serial.print(" –> Wrong connection number: "); 
        Serial.print(Nwrong);
        Nwrong++;
        delay(1000);
    } else{
        Nwrong=0;
    }
  }
}

void up(){
  Serial.print(" up ");
  }
void down(){
  Serial.print(" down ");
  }
void left(){
  Serial.print(" left ");
  }
void right(){
  Serial.print(" right ");
  }
void rightclick(){
    Serial.print(" RightClick ");;
  }
void leftclick(){
    Serial.print(" LeftClick ");;
  }
  
void loop() {
  mpu.getMotion6(&ax, &ay, &az, &gx, &gy, &gz);

   if(ax>=5000){                          //calibration line
    right(); //maybe 15000
  }
  if(ax<=-9000){                         //calibration line
    left();
  }
    
  if(ay<=-8000){                          //calibration line
    up();
  }
  if(ay>=10000){                          //calibration line
    down();
  }

//uncomment the following lines to set the right click with a sprint up and the left click with a sprint down (Work in progress part)
/*
  if(gy>=20000){                         //calibration line        
    rightclick();
    delay(100);
  }
  if(gy<=-20000){                        //calibration line
    leftclick();
    delay(100);
  }
*/


  Serial.print(" | gx= ");
  Serial.print(gx);
  Serial.print(" gy= ");
  Serial.print(gy);
  Serial.print(" gz= ");
  Serial.print(gz);

  
  Serial.print(" | ax= ");
  Serial.print(ax);
  Serial.print(" ay= ");
  Serial.print(ay);
  Serial.print(" az= ");
  Serial.print(az);
  Serial.print("\n");
  
  delay(5000);                     
}
