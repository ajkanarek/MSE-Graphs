import processing.core.*; 
import processing.xml.*; 

import thermodynamics.*; 

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

public class IdealGas extends PApplet {



// Create Simulation components
piston pist;

// Create interface components
HScrollBar hsTemp;
HScrollBar hsPres;

CheckBox FixPadjT;
CheckBox FixTadjP;
CheckBox FixEadjP;

//Create plots
Plot TempPlot;
Plot PresPlot;
Plot VolPlot;
Plot PVPlot;

// Text
TextLabel Ensemble;
TextLabel EnvVars;
TextLabel TempLabel;
TextLabel PresLabel;


// set simulation parameters
float MaxTemp = 350000; // Max temperature
float MaxP = 50;
int N = 1000;

// create logs for data
int DataBuffer = 3000;
LinkedList Time, Temp, Pres, Vol;

// true if it's showing one big graph
boolean drawBigPlot=false;
Plot bigPlot;
// slot to save the original size of magnified graph
int originalSize[];

// number of sample points to draw in the graphs
int numPoints=100;

public void setup()
{
 //size(900, 550);
 size (900, 698);
 smooth();
 frameRate(30);
 
 //Initialize Data logs
 Time = new LinkedList();
 Temp = new LinkedList();
 Pres = new LinkedList();
 Vol  = new LinkedList();

 // Initialize Scrollbar for Temperature Control
 hsTemp = new HScrollBar(this, 400, 600, 476, 20);
 hsPres = new HScrollBar(this, 400, 640, 476, 20);
 
 // initialize ensamble checkboxes
 FixPadjT = new CheckBox(this, 16, 600, "Constant pressure adjustable temperature");
 FixPadjT.checked = true;

 FixTadjP = new CheckBox(this, 16, 620, "Constant temperature adjustable pressure");
 FixTadjP.checked = false;

 FixEadjP = new CheckBox(this, 16, 640, "Constant energy adjustable pressure");
 FixEadjP.checked = false; 
 
 
 
 // Initialize the piston(xpos, ypos, width, height, N, T, StartPt)
 pist = new piston(8, 8, 150, 542, N, MaxTemp*hsTemp.getPos(), 550/2); 
 pist.g = MaxP*hsPres.getPos();
 
 
 // Initialize plots
 TempPlot = new Plot(this, 174, 8, 359, 271);
 TempPlot.AddData(null,null); //cerate an empty dataset
 TempPlot.Color( 200, 0, 0);
 TempPlot.SetTitle("System Temperature");
 TempPlot.SetXTitle("Elapsed Time" );
 TempPlot.SetYTitle("Temperature");
 
 PresPlot = new Plot(this, 174, 279, 359, 271);
 PresPlot.AddData(null,null);
 PresPlot.Color( 0, 200, 0);
 PresPlot.SetTitle("System Pressure");
 PresPlot.SetXTitle("Elapsed Time" );
 PresPlot.SetYTitle("Pressure");
 
 
 VolPlot = new Plot(this, 533, 8, 359, 271);
 VolPlot.AddData(null, null);
 VolPlot.Color( 0, 0, 200);
 VolPlot.SetTitle("System Volume");
 VolPlot.SetXTitle("Elapsed Time" );
 VolPlot.SetYTitle("Volume");
 
 PVPlot = new Plot(this, 533, 279, 359, 271);
 PVPlot.AddData(null, null);
 PVPlot.SetTitle("PV Diagram");
 PVPlot.Gradation(200,200,200);
 PVPlot.SetXTitle("Volume");
 PVPlot.SetYTitle("Pressure");
 
 //Initialize Text
 Ensemble = new TextLabel(this, "Select ensemble", 150, 574);
 Ensemble.SetAlign(CENTER, TOP);
 
 EnvVars = new TextLabel(this, "Set environment variables", 600, 574);
 EnvVars.SetAlign(CENTER, TOP);
 
 TempLabel = new TextLabel(this, "Temperature", 324, 602);
 TempLabel.SetAlign(LEFT, TOP);
 
 PresLabel = new TextLabel(this, "Pressure", 324, 642);
 PresLabel.SetAlign(LEFT, TOP);
 
 
//  //Draw background areas
// fill(150,150,150,255);
// rect(0,0,900, 550);
// noFill();
// stroke(0);
// rect(0, 0, 166, 558);
// rect(167, 0, width-167-1, 558);
// rect(174, 8, 718, 542);
// rect(173, 7, 719, 543);
// 
// fill(200);
// rect(8, 566, 300, 142);
 
  
 // update the piston once in order to draw it
 pist.Update();
 
 // update datalogs
 Time.offer(0.0f);
 Temp.offer(pist.AveE);
 Pres.offer(pist.pressure);
 Vol.offer(pist.volume);
 
 //update plots
 TempPlot.UpdateData(0,Time,Temp);
 TempPlot.SetAxis((Float)Time.getFirst(),(Float)Time.getLast(),0,200000);
 TempPlot.SetNumPoints(numPoints);
 
 PresPlot.UpdateData(0,Time,Pres);
 PresPlot.SetAxis((Float)Time.getFirst(),(Float)Time.getLast(),0,6000);
 PresPlot.SetNumPoints(numPoints);
 
 VolPlot.UpdateData(0,Time,Vol);
 VolPlot.SetAxis((Float)Time.getFirst(),(Float)Time.getLast(),0, pist.boxsize[1]);
 VolPlot.SetNumPoints(numPoints);
 
 PVPlot.UpdateData(0,Vol,Pres);
 PVPlot.SetAxis(0,pist.boxsize[1],0,6000);
 PVPlot.SetNumPoints(numPoints);
  
 
 //Display UI elements
 hsTemp.display();
 hsPres.display();
 FixPadjT.display();
 FixTadjP.display();
 FixEadjP.display();
 
 TempPlot.ScribePlot();
 PresPlot.ScribePlot();
 VolPlot.ScribePlot();
 PVPlot.ScribePlot();
 
 Ensemble.draw();
 EnvVars.draw();
 TempLabel.draw();
 PresLabel.draw();
 
 
     
}// end of setup

public void draw ()
{
 if(frame!=null) frame.setTitle(String.format("IdealGas - FPS : %.1f",frameRate)); 
 background(150);
 noFill();
 stroke(0);
 rect(0, 0, 166, 558);
 rect(167, 0, width-167-1, 558);
 rect(173, 7, 719, 543);
 rect(0, 559, width -1, 138);
 
 fill(200);
 rect(8, 567, 292, 122);
 rect(308, 567, 584, 122);
 
 //Draw background areas
 
 
 
 // update user settings
 FixPadjT.update();
 if(FixPadjT.checked && ( FixTadjP.checked || FixEadjP.checked ) ) 
 { 
    FixTadjP.checked = false;
    FixEadjP.checked = false;
    
    pist.TempFixed = true;
    
 }// end FixPadjT update
 
 FixTadjP.update();
 if(FixTadjP.checked && ( FixPadjT.checked || FixEadjP.checked ) ) 
 { 
    FixPadjT.checked = false;
    FixEadjP.checked = false;
    
    pist.TempFixed = true;
    
 }// end FixTadjT update
 
 FixEadjP.update();
 if(FixEadjP.checked && ( FixTadjP.checked || FixPadjT.checked ) ) 
 { 
    FixPadjT.checked = false;
    FixTadjP.checked = false;
    
    pist.TempFixed = false;
    
 }// end FixTadjT update
 
 
 
 if(FixPadjT.checked)
   {
     hsTemp.update();
     pist.fixedT = MaxTemp*hsTemp.getPos();
   }
 
 
 if(FixTadjP.checked)
   {
     hsPres.update();
     pist.g = MaxP*hsPres.getPos(); 
   }
   
   
 if(FixEadjP.checked)
   {
     hsPres.update();
     pist.g = MaxP*hsPres.getPos();
   }
 
 // update the piston 
 pist.Update();
 
 // Add new data
 Time.offer((Float)Time.getLast()+1);
 Temp.offer(pist.temperature);
 Pres.offer(pist.pressure);
 Vol.offer(pist.volume);
   
 // Remove old data
 if( Time.size() > DataBuffer) { 
   Time.remove(); 
   PVPlot.GetData(0).offset++;
 }
 if( Temp.size() > DataBuffer) { 
   Temp.remove(); 
   TempPlot.GetData(0).offset++;
 }
 if( Pres.size() > DataBuffer) {
   Pres.remove(); 
   PresPlot.GetData(0).offset++;
 }
 if( Vol.size()  > DataBuffer) { 
   Vol.remove();  
   VolPlot.GetData(0).offset++;
 }

  
 // Update plots
 TempPlot.UpdateData(0,Time,Temp);
 TempPlot.SetAxis((Float)Time.getFirst(),(Float)Time.getLast(),0,200000);
  
 PresPlot.UpdateData(0,Time,Pres);
 PresPlot.SetAxis((Float)Time.getFirst(),(Float)Time.getLast(),0,6000);
 
 VolPlot.UpdateData(0,Time,Vol);
 VolPlot.SetAxis((Float)Time.getFirst(),(Float)Time.getLast(),0, pist.boxsize[1]);
 
 PVPlot.UpdateData(0,Vol,Pres);
 PVPlot.SetAxis(0,pist.boxsize[1],0,6000);

 
 
 //Draw UI elements
 hsTemp.display();
 hsPres.display();
 
 // greyout disabled slider
 fill(100,100,100,100);
 if(FixPadjT.checked != true){
   rect(400,600,476,20);
 }
 else{
   rect(400,640,476,20);
 }
 
 
 FixPadjT.display();
 FixTadjP.display();
 FixEadjP.display();
 
 Ensemble.draw();
 EnvVars.draw();
 TempLabel.draw();
 PresLabel.draw();
 
 
 if(TempPlot!=bigPlot)
   TempPlot.ScribePlot();
 if(PresPlot!=bigPlot)
   PresPlot.ScribePlot();
 if(VolPlot!=bigPlot)
   VolPlot.ScribePlot();
 if(PVPlot!=bigPlot)
   PVPlot.ScribePlot();
 if(bigPlot!=null) 
   bigPlot.ScribePlot();
 // end of draw
}

// controlls to add/remove bals from piston
//void keyPressed() {
//  if (key == CODED) {
//    if (keyCode == UP) {
//      pist.AddBalls(1,1,pist.fixedT);
//    } else if (keyCode == DOWN) {
//      pist.RmBalls(1);
//    } 
//  }
//}// end keyPressed()


public void mousePressed() {
  Plot plot=null;
  if(mouseX>=TempPlot.xpos && mouseX<TempPlot.xpos+TempPlot.xsize && mouseY>=TempPlot.ypos && mouseY<TempPlot.ypos+TempPlot.ysize)
    plot=TempPlot;
  if(mouseX>=PresPlot.xpos && mouseX<PresPlot.xpos+PresPlot.xsize && mouseY>=PresPlot.ypos && mouseY<PresPlot.ypos+PresPlot.ysize)
    plot=PresPlot;
  if(mouseX>=VolPlot.xpos && mouseX<VolPlot.xpos+VolPlot.xsize && mouseY>=VolPlot.ypos && mouseY<VolPlot.ypos+VolPlot.ysize)
    plot=VolPlot;
  if(mouseX>=PVPlot.xpos && mouseX<PVPlot.xpos+PVPlot.xsize && mouseY>=PVPlot.ypos && mouseY<PVPlot.ypos+PVPlot.ysize)
    plot=PVPlot;
  if(plot==null) return;
  if(drawBigPlot) {
    drawBigPlot=false;
    bigPlot.Animate(originalSize,15);
    bigPlot.SetNumPoints(numPoints);
    return;
  }
  originalSize=new int[] {plot.xpos,plot.ypos,plot.xsize,plot.ysize};
  int dest[]={174, 8, 718, 542};
  plot.Animate(dest,15);
  plot.SetNumPoints(1000);
  bigPlot=plot;
  drawBigPlot=true;
  /*
  if (pist.PistFree){
    pist.PistFree = false;
  } else{
    pist.PistFree = true;
    pist.Plunger.vel[1] = 0;
  }
*/
}

class piston {

float[] boxpos = {0,0};  
float[] boxsize = {150, 400};

// ball parameters
ArrayList B;
int N = 500;
float Bmass = 1;
int dia = 6;
float initVel = 1000;

// kinetic energy of all balls
float BallKE = 0;


// Plunger parameters
particle Plunger;
float PlungerMass = 20000;
float PlungerStartPt = boxsize[1]/2;
float PistMin = PlungerStartPt;
float PistMax = PlungerStartPt;
int PlungerWidth = 10;
float g = 25;
float PistPE = 0;
boolean PistFree = true;

// Global parameters
//Global time
float StartTime = 0;
float CurrentTime = 0;

//Global energy
float InitE = 0;
float SystE = 0;

// Volume control
boolean FixedVol = false;
float setHeight = PlungerStartPt;
float Gtemp = g;
float GDot = 0.01f; // Rate of gravity change;
float GDDot = 0.01f; // acceleration of gravity change
float volume = boxsize[1] - PlungerStartPt;

// Pressure parameters
float pressure = 0;
int   srate = 256;
int   pcount = 0;
float ptemp = 0;
float[] pbuffer = new float[srate];

// Temperature parameter
int trate = 10;
int tcount = 0;
float temperature = 0;
float[] tbuffer = new float[trate];

//Thermostat parameters
boolean TempFixed = true; // toggel thermostat
float   fixedT    = 200000; // The bath temperature
float   Q         = 1; // coupling constant
float   AveE      = 0;



// create Piston 
  piston(float x_, float y_, float xsize_, float ysize_, int N_, float T_, float StartPt_)
 {

  boxpos[0] = x_;
  boxpos[1] = y_;
  boxsize[0] = xsize_;
  boxsize[1] = ysize_;
  N = N_;
  fixedT = T_;
  initVel = T_;
  
  PlungerStartPt = StartPt_;
  setHeight = StartPt_;
  

  //create Plunger
  Plunger = new particle(0, PlungerStartPt, PlungerMass);
  rect (boxpos[0], boxpos[1]+Plunger.pos[1] - PlungerWidth, boxsize[0]-1, PlungerWidth);
  PistPE = Plunger.m*g*(boxpos[1]+boxsize[1]-Plunger.pos[1]);
  

  // Create particle array
  B = new ArrayList();
  //Fill array with balls
  AddBalls(N, Bmass, initVel);


  // calculate total system energy
  SystE = BallKE + PistPE + Plunger.e;
  //SystE = BallKE;
  InitE = SystE;
  AveE = BallKE/N; 
  //Set Start Time
  StartTime = second() + 60*(minute() +60*(hour() +24*( day() ) ) ) ;
}// end of creation function



// Update the piston system
public void Update(){
  // draw a white box to display a piston on
  stroke(0);
  fill(200,200,200);
  rect(boxpos[0],boxpos[1], boxsize[0], boxsize[1]);

  // determine timestep 
  float dt=0.005f;
  
  //initialize pressure
  ptemp = 0;
  
  // initialize counters
  float passed=0;
  int count=0;
  
  //Handle ball and piston updates
  while(passed<dt) {
    
    //initialize collision parameters
    float minTime=Float.MAX_VALUE;
    int minDirection=-1;
    int minIndex=-1;
    
    // find the next ball by calculating collision times
    for (int i = 0; i < B.size() ; i++) {
      // get each ball from arraylist
      particle ball = (particle)B.get(i);
      
      //initialize bounceTime
      float bounceTime[]={Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE};
      
      // collision with right wall
      if(ball.vel[0]>0) bounceTime[0]=(boxpos[0]+boxsize[0]-ball.dia/2-ball.pos[0])/ball.vel[0];
      //collision with left wall
      if(ball.vel[0]<0) bounceTime[1]=(boxpos[0]-(ball.pos[0]-ball.dia/2))/ball.vel[0]; 
      // collision with floor
      if(ball.vel[1]>0) bounceTime[2]=(boxpos[1]+boxsize[1]-ball.dia/2-ball.pos[1])/ball.vel[1];
      //collision with plunger
      if(ball.vel[1]<Plunger.vel[1]) bounceTime[3]=(boxpos[1]-(ball.pos[1]-Plunger.pos[1]-ball.dia/2))/(ball.vel[1]-Plunger.vel[1]);

      //determine which ball will bounce next, when and in what direction
      for(int j=0 ; j<4 ; j++) {
        if(bounceTime[j]<-1e-4f || bounceTime[j]>dt) continue;
        if(bounceTime[j]<minTime) {
          minDirection=j;
          minTime=bounceTime[j];
          minIndex=i;
        }// end if
      }// end for j
    }// end calculating collision times
    
    // Handle case where next collision is greater than time remaining in update
    if(passed+minTime > dt) minTime=dt-passed;
    if(minIndex==-1) minTime=dt;
    
    // update all particles by time = mintime
    BallKE = 0;
    for (int i=0 ; i<B.size() ; i++) {
      particle ball=(particle)B.get(i);
      ball.update(minTime);
      BallKE += ball.e;
    }// end updates
    
    //calculate momentum exchanges
    if(passed + minTime < dt) {
      particle ball=(particle)B.get(minIndex);
      
     
      Random r = new Random();
      float newVel = sqrt(sq((float)r.nextGaussian()*sqrt(ball.m*fixedT))+sq((float)r.nextGaussian()*sqrt(ball.m*fixedT)));
      float oldVel = sqrt(sq(ball.vel[0])+sq(ball.vel[1]));
      
        
      switch(minDirection) {
        case 0: // right wall collision
          ball.vel[0]*=-1;
          if(TempFixed){
            ball.vel[0] = ball.vel[0]*newVel/oldVel;
            ball.vel[1] = ball.vel[1]*newVel/oldVel;
          }
          break;
        case 1: // left wall collision
          ball.vel[0]*=-1;
          if(TempFixed){
            ball.vel[0] = ball.vel[0]*newVel/oldVel;
            ball.vel[1] = ball.vel[1]*newVel/oldVel;
          }
          break;
        case 2: // floor collision
          ball.vel[1]*=-1;
          if(TempFixed){
            ball.vel[0] = ball.vel[0]*newVel/oldVel;
            ball.vel[1] = ball.vel[1]*newVel/oldVel;
          }
          break;
        case 3: // plunger collision
          // case where plunger moves
          if (PistFree){
            float pv = Plunger.vel[1];
            float pm = Plunger.m;
            float bv = ball.vel[1];
            float bm = ball.m;
      
            Plunger.vel[1] = (pv*(pm-bm)+2*bv*bm)/(pm+bm);     
            ball.vel[1] = (bv*(bm-pm)+2*pv*pm)/(pm+bm);
            
            //find dp for pressure calc
            ptemp += bm*(ball.vel[1]-bv);
            //ptemp ++;
          } 
          // case where plunger is fixed
          else {
            float pv = -ball.vel[1];
            float pm = ball.m;
            float bv = ball.vel[1];
            float bm = ball.m;
      
            //Plunger.vel[1] = (pv*(pm-bm)+2*bv*bm)/(pm+bm);     
            ball.vel[1] = (bv*(bm-pm)+2*pv*pm)/(pm+bm);
            
            //find dp for pressure calc
            ptemp += bm*(ball.vel[1]-bv);
            //ptemp ++;
            
          }
          break;
      }// end switch cases
    }// end momentum exchanges
    
  
    // Adjust force on plunger to arrive at set volume
    
    if (FixedVol && (abs(Plunger.pos[1]-setHeight) >= 0.5f) ){
      //Case where plunger is not within tolerance of fixed point
      PistFree = true;
      //println("Piston Free: Finding setHeight");
      if (Plunger.pos[1] <= setHeight){ 
        // case where plunger is above set point
        if(Plunger.vel[1] <= 5){
          // acceleration control to avoid too high an acceleration
          GDot  += GDDot;
          Gtemp += GDot;
        }// end if (plunger.vel[1])
      }// end if (Plunger.pos[1])
      else{
        // plunger below set point
        if (Plunger.vel[1] >= -5){
          // acceleration control
          GDot -= GDDot;
          Gtemp += GDot;
        }// end if (plunger.vel[1])
      }// end else
   }// end if (fixedVol and not at setheight)
   
   else if (FixedVol && (abs(Plunger.pos[1]-setHeight) < 0.5f) ){
     // case where plunger is within tolerance of set point
       PistFree = false; //fix piston
       Plunger.vel[1] = 0; // stop piston
       GDot = 0; // stop increasing force
       //println("Piston Fixed: at set Point");
   }// end else if (at fixed point) 
   
   else{
    PistFree = true;
    Gtemp = g;
    //println("Piston Free: no set point");
   } // end else (no fixed point) 
    
    
    //Update Plunger by minTime if it is free to move 
    if (PistFree){
      Plunger.update(minTime);
      Plunger.vel[1] += Gtemp*minTime;
      PistPE = Plunger.m*g*(boxpos[1]+boxsize[1]-Plunger.pos[1]);
    }
    
    //check min and max plunger positions
    //if (Plunger.pos[1]< PistMin){PistMin = Plunger.pos[1];}
    //if (Plunger.pos[1]> PistMax){PistMax = Plunger.pos[1];}

    // increment time and particle number
    passed+=minTime;
    count++;
    if(minTime<0) println("mintime : "+minTime);
  }// End of while loop for Ball updates
  
  // update volume
  volume = boxsize[1] - Plunger.pos[1];
 // println(volume);
  
  //Update pressure
  pressure = 0;
  for( int i=0; i < srate-1; i++){
   pbuffer[i] = pbuffer[i+1]; // shift entries in pbufer 
  }
  pbuffer[srate-1] = ptemp; // add newest pressure to buffer
  for(int i=0; i < srate; i++){
    pressure += pbuffer[i];
  }
  pcount++;
  pressure /= (pcount<srate)?pcount:srate;
  //println(pressure);
    
  
  // update total system energy
  CurrentTime = second() + 60*(minute() +60*(hour() +24*( day() ) ) ) ;
  SystE = BallKE + PistPE + Plunger.e;
  AveE = BallKE/N; // analagous to Temperature
  
  temperature=0;
  for(int i=0; i < trate-1; i++) {
    tbuffer[i] = tbuffer[i+1];
  }
  tbuffer[trate-1]=AveE;
  for(int i=0; i<trate; i++) {
    temperature += tbuffer[i];
  }
  tcount++;
  temperature /= (tcount<trate)?tcount:trate;
  
  
  // Display graphical elements of Piston
  
  // display balls
  for(int i=0 ; i<B.size() ; i++) {
    particle ball=(particle)B.get(i);
    if (ball.pos[1] >= boxpos[1]+ball.dia/2 && ball.pos[1] <= boxpos[1]+boxsize[1]+ball.dia/2){
      ball.disp();
    }// end if
  }// end loop for ball dis
  
  //println("count = "+count);
  
  //Draw Plunger
  // color acording to average ball energy 
  fill(.001f*AveE,0,255-0.001f*AveE);
  //draw based on plunger position within Piston
  if( Plunger.pos[1] >= PlungerWidth && Plunger.pos[1] <= boxsize[1]){ 
    // case where plunger is within visible piston area (most common case)
    rect (boxpos[0], boxpos[1]+Plunger.pos[1] - PlungerWidth, boxsize[0]-1, PlungerWidth);
     //Draw mass on top of plunger
     if( Plunger.pos[1] >= PlungerWidth+sqrt(g)*10){
        rect( boxpos[0] + boxsize[0]/2 - sqrt(g)*5, boxpos[1]+Plunger.pos[1] - PlungerWidth - sqrt(g)*10, sqrt(g)*10, sqrt(g)*10);
      }else if(Plunger.pos[1] < PlungerWidth+sqrt(g)*10){
        rect( boxpos[0] + boxsize[0]/2 - sqrt(g)*5, boxpos[1], sqrt(g)*10, Plunger.pos[1]-PlungerWidth);
        
      }
  }else if(Plunger.pos[1] < PlungerWidth && Plunger.pos[1] >= 0){
    // case where plunger exits top of piston
    rect (boxpos[0], boxpos[1], boxsize[0]-1, Plunger.pos[1]);
    
  }else if(Plunger.pos[1] > boxsize[1]){
    // case where plunger hits bottom pf piston
    rect (boxpos[0], boxpos[1] + boxsize[1] - PlungerWidth, boxsize[0]-1, PlungerWidth);
    //Draw mass on top of plunger
    rect(boxpos[0] + boxsize[0]/2 - sqrt(g)*5, boxpos[1]+Plunger.pos[1] - PlungerWidth - sqrt(g)*10, sqrt(g)*10, sqrt(g)*10);
    // stop plunger from leaving bottom of piston
    Plunger.vel[1] = 0;
    Plunger.pos[1] = boxsize[1]; 
  } 
  // End of Plunger updates

  
  //print("Percent Energy Change = "); print(100*(SystE-InitE)/InitE); print(" %"); println();
  //print("Rate of Energy Change = "); print( (100*(SystE-InitE)/InitE)/(CurrentTime-StartTime) ); print(" %/sec"); println();

  // Draw Plunger min, max, and starting lines
  //line(boxpos[0],boxpos[1]+PistMin-PlungerWidth, boxpos[0]+boxsize[0], boxpos[1]+PistMin-PlungerWidth);
  //line(boxpos[0],boxpos[1]+PlungerStartPt, boxpos[0]+boxsize[0], boxpos[1]+PlungerStartPt);
  //line(boxpos[0],boxpos[1]+PistMax, boxpos[0]+boxsize[0], boxpos[1]+PistMax); 
  
 }// end of Piston update function




// Function to add balls to the piston
public void AddBalls( int Num, float mass, float Temp){
  Random r = new Random();
  for (int i = 0; i < Num ; i++)
  {
    // Randomly distribute particles in piston
    B.add( new  particle(random(boxpos[0]+dia/2, boxpos[0]+boxsize[0]-dia/2), random(boxpos[1]+Plunger.pos[1]+dia/2,boxpos[1]+boxsize[1]-dia/2), mass) );
    // generate all aparticles at a pointsource in the center of the piston
    //B.add( new  particle( ((boxpos[0]+boxsize[0]-dia/2) + (boxpos[0]+dia/2))/2, ((boxpos[1]+Plunger.pos[1]+dia/2) + (boxpos[1]+boxsize[1]-dia/2))/2, mass) );
    
    particle Btemp = (particle) B.get(B.size()-1);
    float dir = random(0, 2*PI);
    //float vel = random(0, vel_);
    //float vel = (float)r.nextGaussian()*vel_;
    //Btemp.vel[0] = vel*cos(dir);
    //Btemp.vel[1] = vel*sin(dir);
      Btemp.vel[0] = (float)r.nextGaussian()*sqrt(mass*Temp);
      Btemp.vel[1] = (float)r.nextGaussian()*sqrt(mass*Temp);
    Btemp.disp();
    BallKE += Btemp.energy();
  }// end for
  N = B.size(); // have N reflect new number of balls in piston
 
}// end AddBalls



// Function to remove balls from the piston
public void RmBalls( int Num_){
  int Num = Num_;
  
  // dont allow all particles to be deleted (min number of particles is 1)
  if (Num >= B.size()){ Num = B.size()-1;}
  
  for (int i = 1; i <= Num ; i++)
  {
    B.remove(B.size()-1);
  } 
  N = B.size(); // have N reflect new number of balls in piston
  
  
}// end RmBalls





}// end of class piston

class particle{
  
  float m = 1;
  float[] pos = {0,0,0};
  float[] vel = {0,0,0};
  int[] fixed = {0,0,0};
  float dia = 6;
  float e = 0;
  float[] time = {0,0};
  float[] BounceTime = {0,0};
  float FloorTime, PistTime;
  float dt = 0.005f;
  
  // creates particle
  particle( float x_, float  y_, float m_)
  {
    pos[0] = x_;
    pos[1] = y_;
    m = m_;
    e = 0.5f*m*(sq(vel[0])+sq(vel[1])+sq(vel[2]));
    e = energy();
  }
  
  // creates particle with given velocity
  particle( float x_, float  y_, float m_, float vx, float vy)
  {
    pos[0] = x_;
    pos[1] = y_;
    m = m_;
    vel[0] = vx;
    vel[1] = vy;
    //e = 0.5*m*(sq(vel[0])+sq(vel[1])+sq(vel[2]));
    e = energy();
  }
  
  
  // updates particle position energy and time
  public void update(){
  pos[0] = pos[0] +dt*vel[0];
  pos[1] = pos[1] +dt*vel[1];
  pos[2] = pos[2] +dt*vel[2];
  //e = 0.5*m*(sq(vel[0])+sq(vel[1])+sq(vel[2]));
  e = energy();
  time[0] = time[0] +dt;
  time[1] = time[1] +dt;
  }  
  
  public void update(float dt){
  pos[0] = pos[0] +dt*vel[0];
  pos[1] = pos[1] +dt*vel[1];
  pos[2] = pos[2] +dt*vel[2];
  //e = 0.5*m*(sq(vel[0])+sq(vel[1])+sq(vel[2]));
  e = energy();
  time[0] = time[0] +dt;
  time[1] = time[1] +dt;
  }  
  
  
  // displays particle
  public void disp(){
    stroke(0);
    //fill(Geometry.Color(e/1.2e5));
    fill(255*e*0.000005f, 0, 255-255*e*0.000005f);
    ellipse(pos[0], pos[1], dia, dia);
  }
  
  // calculates particle energy
  public float energy(){
    float e = 0.5f*m*(sq(vel[0])+sq(vel[1])+sq(vel[2]));
    return e;
  }
   
}

  static public void main(String args[]) {
    PApplet.main(new String[] { "--bgcolor=#FFFFFF", "IdealGas" });
  }
}
