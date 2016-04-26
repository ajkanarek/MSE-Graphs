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

public class IsingModel extends PApplet {



Lattice L;
float FractionA = 0.5f;

ArrayList E;
ArrayList naa;
ArrayList nbb;
ArrayList nab;

//Interface components
HScrollBar hsTemp;
HScrollBar hsAA;
HScrollBar hsBB;
HScrollBar hsAB;
HScrollBar hsComp;

CheckBox RunButton;
CheckBox RandConfig;
CheckBox SeperateConfig;

//plots
Plot Eplot;
Plot AAplot;
Plot BBplot;
Plot ABplot;

//Text
TextLabel EnergyX;
TextLabel EnergyY;
TextLabel BondsY;
TextLabel TempSlide;
TextLabel AASlide;
TextLabel BBSlide;
TextLabel ABSlide;
TextLabel CompSlide;
TextLabel InitCondHead;
TextLabel InitCondSub;
TextLabel ActiveCondHead;
TextLabel ActiveCondSub;


int LSize = 20;
int time = 0;


public void setup() {
  
size (800, 601);  
smooth();
background (255);
frameRate(30);

L = new Lattice( LSize, LSize, FractionA, 'S');
E = new ArrayList();
naa = new ArrayList();
nbb = new ArrayList();
nab = new ArrayList();


// create all text components
TempSlide = new TextLabel(this, "Temperature", 424,468);
TempSlide.SetAlign(LEFT, TOP);
TempSlide.draw();

AASlide = new TextLabel(this, "A-A Energy", 424, 498);
AASlide.SetAlign(LEFT, TOP);
AASlide.draw();

BBSlide = new TextLabel(this, "B-B Energy", 424, 528);
BBSlide.SetAlign(LEFT, TOP);
BBSlide.draw();

ABSlide = new TextLabel(this, "A-B Energy", 424, 558);
ABSlide.SetAlign(LEFT, TOP);
ABSlide.draw();

CompSlide = new TextLabel(this, "A:B Ratio", 16, 468);
CompSlide.SetAlign(LEFT, TOP);
CompSlide.draw();

EnergyX = new TextLabel(this, "MC Steps", 580, 382);
EnergyX.SetAlign(LEFT,TOP);
EnergyX.draw();

EnergyY = new TextLabel(this, "Energy", 406, 100);
EnergyY.SetAlign(CENTER,TOP);
EnergyY.SetRotation(PI*3/2);
EnergyY.draw();

BondsY = new TextLabel(this, "Bonds", 406, 300);
BondsY.SetAlign(CENTER,TOP);
BondsY.SetRotation(PI*3/2);
BondsY.draw();

InitCondHead = new TextLabel(this, "Initial Conditions", 200, 416);
InitCondHead.SetAlign(CENTER, TOP);
InitCondHead.draw();

InitCondSub = new TextLabel(this, "( Changing these settings will reset the simulation )", 200, 436);
InitCondSub.SetAlign(CENTER, TOP);
InitCondSub.draw();

ActiveCondHead = new TextLabel(this, "Energy Parameters", 600, 416);
ActiveCondHead.SetAlign(CENTER, TOP);
ActiveCondHead.draw();

ActiveCondSub = new TextLabel(this, "( These settings can be changed during the simulation )", 600, 436);
ActiveCondSub.SetAlign(CENTER, TOP);
ActiveCondSub.draw();


// create slider controls
hsTemp = new HScrollBar(this,496, 466, 250, 20);
hsAA   = new HScrollBar(this,496, 496, 250, 20);
hsBB   = new HScrollBar(this,496, 526, 250, 20);
hsAB   = new HScrollBar(this,496, 556, 250, 20);
hsComp = new HScrollBar(this, 76, 466, 250, 20);

// set the sterting temperature
L.setTemp(LSize*hsTemp.getPos());

// create control buttons
RandConfig = new CheckBox(this, 16, 506, "Random");
RandConfig.checked = false;
RandConfig.display();

SeperateConfig = new CheckBox(this, 16, 536, "Phase Seperated");
SeperateConfig.checked = true;
SeperateConfig.display();

RunButton = new CheckBox(this, 16, 566, "Run");
RunButton.checked = false;
RunButton.display();

// initialize datalogs
E.add( new PlotPoint( time, L.getEnergy()));
naa.add(new PlotPoint( time, L.naa));
nbb.add(new PlotPoint( time, L.nbb));
nab.add(new PlotPoint( time, L.nab));

// draw initial lattice configuration
L.disp(8, 8, 384, 384 );

}


// Draw section
public void draw() {
  //background (255);
  stroke(0);
  
  // update simulation controls/parameters
  hsTemp.update();
  hsAA.update();
  hsBB.update();
  hsAB.update();
  hsComp.update();
  
  L.setTemp(LSize*hsTemp.getPos());
  L.waa = hsAA.getPos();
  L.wbb = hsBB.getPos();
  L.wab = hsAB.getPos();
  
  // update composition
  if(hsComp.getPos() != FractionA )
  {
    // stop simulation and change settings
    RunButton.checked = false;
    FractionA = hsComp.getPos();
    
    // reset lattice
    if (RandConfig.checked)
    { L = new Lattice( LSize, LSize, FractionA, 'R');}
    else { L = new Lattice( LSize, LSize, FractionA, 'S');}
    
    L.setTemp(LSize*hsTemp.getPos());
    
    // reset datalogs
    E = new ArrayList();
    naa = new ArrayList();
    nbb = new ArrayList();
    nab = new ArrayList();
    
    E.add( new PlotPoint( time, L.getEnergy()));
    naa.add(new PlotPoint( time, L.naa));
    nbb.add(new PlotPoint( time, L.nbb));
    nab.add(new PlotPoint( time, L.nab));
    
  }// end of composition update
  
  
  RandConfig.update();
  if(RandConfig.checked && SeperateConfig.checked)
  {
    // adjust simulation settings
    SeperateConfig.checked = false;
    RunButton.checked = false;
    
    // reset lattice
    L = new Lattice( LSize, LSize, FractionA, 'R');
    L.setTemp(LSize*hsTemp.getPos());
    
    // reset datalogs
    E = new ArrayList();
    naa = new ArrayList();
    nbb = new ArrayList();
    nab = new ArrayList();
    
    E.add( new PlotPoint( time, L.getEnergy()));
    naa.add(new PlotPoint( time, L.naa));
    nbb.add(new PlotPoint( time, L.nbb));
    nab.add(new PlotPoint( time, L.nab));
  }// end if RandConfig update
  
  SeperateConfig.update();
  if(RandConfig.checked && SeperateConfig.checked)
  {
    // adjust simulation settings
    RandConfig.checked = false;
    RunButton.checked = false;
    
    // reset lattice
    L = new Lattice( LSize, LSize, FractionA, 'S');
    L.setTemp(LSize*hsTemp.getPos());
    
    //reset datalogs
    E = new ArrayList();
    naa = new ArrayList();
    nbb = new ArrayList();
    nab = new ArrayList();
    
    E.add( new PlotPoint( time, L.getEnergy()));
    naa.add(new PlotPoint( time, L.naa));
    nbb.add(new PlotPoint( time, L.nbb));
    nab.add(new PlotPoint( time, L.nab));
    
  }// end if RandConfig update
  
  
  RunButton.update();
  
  //print(L.getEnergy());
  //println();
  
  
  // update simulation
  if (RunButton.checked)
  {    
    time++;
  //  if (time%100 == 0 && L.getTemp() > 0){
  //    L.setTemp( L.getTemp() - 1.0);
  //    print(L.getTemp());
  //    //print(L.getEnergy());
  //  }
      
      
    L.MCStep();
    E.add( new PlotPoint( time, L.getEnergy()) );
    naa.add(new PlotPoint( time, L.naa));
    nbb.add(new PlotPoint( time, L.nbb));
    nab.add(new PlotPoint( time, L.nab));
  }// end simulation update
  
  
  
  // update display
  
  
  // simulation area  
  fill(150,150,150,255);
  rect(0,0,399,400);
  L.disp(8, 8, 384, 384 ); 
  
   
  //plot Area
  fill(150,150,150,255);
  rect(400, 0, 399, 400);
  
  // Energy plot update
  fill(200,200,200,255);
  rect(424, 16, 360, 172);
  stroke(0,0,200,255);
  Eplot = new Plot(E, 424, 16, 360, 172);
  Eplot.ScribePlot();
  
  // composition plot update
  fill(200,200,200,255);
  rect(424, 208, 360, 172);
  stroke(0,0,200,255);
  AAplot = new Plot(naa, 424, 208, 360, 172);
  AAplot.ymin = 0; 
  AAplot.ymax = L.naa+L.nbb+L.nab;
  AAplot.ScribePlot();
  
  stroke(0,150,0,255);
  BBplot = new Plot(nbb, 424, 208, 360, 172);
  BBplot.ymin = 0; 
  BBplot.ymax = L.naa+L.nbb+L.nab;
  BBplot.ScribePlot();
    
  stroke(200,0,0,255);
  ABplot = new Plot(nab, 424, 208, 360, 172);
  ABplot.ymin = 0; 
  ABplot.ymax = L.naa+L.nbb+L.nab;
  ABplot.ScribePlot();
  
  fill(0);
  EnergyX.draw();
  EnergyY.draw();
  BondsY.draw();
  
  // control Area
  fill(150,150,150,255);
  rect(0,401,799,199);
  
  fill(200, 200, 200, 255);
  rect(8, 409, 384, 184);
  rect(408, 409, 384, 184);
  
  hsTemp.display();
  hsAA.display();
  hsBB.display();
  hsAB.display();
  hsComp.display();
  
  RandConfig.display();
  SeperateConfig.display();
  RunButton.display();
  
  stroke(0);
  fill(0);
  InitCondHead.draw();
  InitCondSub.draw();
  ActiveCondHead.draw();
  ActiveCondSub.draw();
  TempSlide.draw();
  CompSlide.draw();
  
  
  fill(0,0,200);
  AASlide.draw();
  fill(0,150,0);
  BBSlide.draw();
  fill(200,0,0);
  ABSlide.draw();
  
  
  
  
}// end Draw


class Lattice {
  private int cols;
  private int rows;
  private int[][] elements;
  
  private float temp;
  private float Energy;
  
  float percentA = 0.5f;
  
  float waa = 0;
  float wbb = 0;
  float wab = 1;
  
  int Na = 0;
  int Nb = 0;
  
  int naa = 0;
  int nbb = 0;
  int nab = 0;
  
  //Basic Creation Function
  Lattice( int cols_, int rows_ ) {
    cols = cols_;
    rows = rows_;
    temp = 0;
    Energy = 0;
    elements = new int[cols][rows];
    
    for ( int i=0; i < cols; i++)
    {
      for ( int j=0; j < rows; j++)
      {
        if (random(0,1) <= percentA){
         elements[i][j] = 0;
         Na++;
        }
        else{
          elements[i][j] = 1;
          Nb++;
        }// end else
        
      }// end for j
    }// end for i
    
    CalcEnergy();
    CountBonds();
    
  }// end Lattice (int, int)
  
  //Creation Function with perset starting condiions
  Lattice( int cols_, int rows_, float fracA, char StartConfig ) {
    cols = cols_;
    rows = rows_;
    percentA = fracA;
    temp = 0;
    Energy = 0;
    elements = new int[cols][rows];
    
    for ( int i=0; i < cols; i++)
    {
      for ( int j=0; j < rows; j++)
      {
        switch (StartConfig)
        {
          case ('R'): // case for randomly mixed system
            if (random(0,1) <= percentA){
             elements[i][j] = 0;
             Na++;
            }
            else{
              elements[i][j] = 1;
              Nb++;
            }
            break; // end case Rand
            
          case ('S'): //case for initialy phase seperated system
            if( i*rows + (j) <= cols*rows*percentA){
              elements[i][j] = 0;
              Na++;
            }
            else{
              elements[i][j] = 1;
              Nb++;
            }
            break;// end case seperate         
        }// end Switch      
      }// end for j
    }// end for i
    
    CalcEnergy();
    CountBonds();
    
  }// end Lattice (int, int, char)
  
  
  
  
  // function to display lattice
  public void disp(){
    float radius1 = width/(2*cols);
    float radius2 = height/(2*rows);
    
    for (int i=0; i < cols; i++)
   {
     for (int j=0; j < rows; j++)
     {
       fill(0,0,0,255*(elements[i][j]));
       ellipse( (2*(i+1)-1)*radius1, (2*(j+1)-1)*radius2, 2*radius1, 2*radius2 ); 
     }// end for j  
   }// end for i
  }// end disp()
  
  
  
  // function to display lattice in specified area
  public void disp( float x0,  float y0, float xsize, float ysize){
   stroke(0);
   fill(126);
   rect(x0, y0, xsize, ysize);
    
   float radius1 = xsize/(2*cols);
   float radius2 = ysize/(2*rows);

   for (int i=0; i < cols; i++)
   {
     for (int j=0; j < rows; j++)
     {
       fill(255*(elements[i][j]));
       ellipse( x0 + (2*(i+1)-1)*radius1, y0 + (2*(j+1)-1)*radius2, 2*radius1, 2*radius2 ); 
     }// end for j  
   }// end for i
      
  }// end disp (float, float, float, float)  
  
  
  
  
  
  // get number of columns
  public int getCols(){
    return cols;
  }// end getCols
  
  // get number of rows
  public int getRows(){
    return rows;
  }// end getRows  
  
  // set value of a given element
  public void setElem( int x, int y, int val){
    elements[x][y] = val;
  }// end setElem
  
  // get value of a given element
  public int getElem( int x, int y){
    return elements[x][y];
  }// end getElem
  
  // set system temperature
  public void setTemp(float T_){
    temp = T_;
  }// end setTemp
  
  // get system temperature
  public float getTemp(){
    return temp;
  }// end getTemp
  
  // get system energy
  public float getEnergy(){
    return Energy;
  }// end getEnergy
  
  
  //calculate the energy of the lattice and update Energy
  public void CalcEnergy(){
  float e = 0;
  for (int i=0; i < cols; i++)
   {
     for (int j=0; j < rows; j++)
     {
       e += twoway(i,j);
     }  
   } 
  
  Energy = e;
  
  }// end calcenergy
  
  
  
  //energy calculation of a single site to be used with calcEnergy
  public float twoway(int i, int j){
    
    float e = 0;
    int init = elements[i][j];
    int adj;
    
    // look to the right of the element
    if (i == cols - 1){
      adj = elements[0][j];
    }
    else { adj = elements[i+1][j];}
    
    if (init != adj) { e += wab; }
    else if ( init == 0 && adj == 0) { e += waa; }
    else if ( init == 1 && adj == 1) { e += wbb; }
    
    //look below the element  
     if ( j == rows -1){
        adj = elements[i][0];
     }
      else {adj = elements[i][j+1];}
    
    if (init != adj) { e += wab; }
    else if ( init == 0 && adj == 0) { e += waa; }
    else if ( init == 1 && adj == 1) { e += wbb; } 
 
   return e;
  }// end twoway
  
  
  // count the number of a-a, b-b, & a-b bonds
  public void CountBonds(){
    int ab = 0;
      
    for (int i=0; i < cols; i++)
    {
      for (int j=0; j < rows; j++)
      {
        int init = elements[i][j];
        int adj;
        
        // look to the right of the element
        if (i == cols - 1){
          adj = elements[0][j];
        }
        else { adj = elements[i+1][j];}
        
        if (init != adj) { ab++; }
                
        //look below the element  
         if ( j == rows -1){
            adj = elements[i][0];
         }
          else {adj = elements[i][j+1];}
        if (init != adj) { ab++; }
        
      }// end for j
    }// end for i
    nab = ab;
    naa = 2*Na - nab/2;
    nbb = 2*Nb - nab/2;
    
  }// end CountBonds
  
  
  
  //Runs 1 Montecarlo step 
  public void MCStep(){
    float Einit = Energy;
    
    //pick x1,y1 at random
    int x1 = floor(random(0,cols));
    int y1 = floor(random(0,rows));
        
    //pick x2,y2 at random
    int x2 = floor(random(0, cols));
    int y2 = floor(random(0, rows));
        
    //get values for (x1,y1) , (x2,y2)
    int val1 = elements[x1][y1];
    int val2 = elements[x2][y2];
    
    // swap the elements
    elements[x1][y1] = val2;
    elements[x2][y2] = val1;
    
    //recalculate lattice energy
    CalcEnergy();
    
    float DeltaE = Einit - Energy;
  
    if (DeltaE < 0 && random(0,1) >  exp(DeltaE/temp)) {
      elements[x1][y1] = val1;
      elements[x2][y2] = val2;
      CalcEnergy();
    }// end if
    
    CountBonds();
    //print(nab); print(", "); print(naa); print(", "); print(nbb); print(", "); println(nab+naa+nbb);
    //print(Na);print(", ");print(Nb);print(", ");println(Na+Nb); 
  }// end MCSetp 
 
 
 
 
 
  
  
}// end Lattice Class
class Plot{
 int xpos;
 int ypos;
 int xsize;
 int ysize;
 float xmax;
 float xmin;
 float ymax;
 float ymin;
 ArrayList data;
 
//creation function only 1 arraylist as an input
 Plot(ArrayList data_ ){
  xpos = 0;
  ypos = 0;
  xsize = 200;
  ysize = 200;
  data = data_;
   
  // define the max and min xsize values
  xmax = 0;
  ymax = 0;
  for ( int i = 0; i < data.size(); i++ ) {
    PlotPoint pp = (PlotPoint) data.get(i);
    if (pp.x > xmax) { xmax = pp.x;}
    if (pp.y > ymax) { ymax = pp.y;}    
  }
  xmin = xmax;
  ymin = ymax;
  for (int i = 0; i < data.size(); i++ ){
    PlotPoint pp = (PlotPoint) data.get(i);
    if (pp.x < xmin) { xmin = pp.x;}
    if (pp.y < ymin) { ymin = pp.y;}
  }
  
  ScribePlot();
  
} 


//creation function arraylist, wth plot position and dimentions
 Plot( ArrayList data_, int xpos_, int ypos_, int xsize_, int ysize_ ){
  xpos = xpos_;
  ypos = ypos_;
  xsize = xsize_;
  ysize = ysize_;
  data = data_;
   
  // define the max and min xsize values
  xmax = 0;
  ymax = 0;
  for ( int i = 0; i < data.size(); i++ ) {
    PlotPoint pp = (PlotPoint) data.get(i);
    if (pp.x > xmax) { xmax = pp.x;}
    if (pp.y > ymax) { ymax = pp.y;}    
  }
  xmin = xmax;
  ymin = ymax;
  for (int i = 0; i < data.size(); i++ ){
    PlotPoint pp = (PlotPoint) data.get(i);
    if (pp.x < xmin) { xmin = pp.x;}
    if (pp.y < ymin) { ymin = pp.y;}
  }

    //ScribePlot();

}

//function to draw the plot
public void ScribePlot(){
   
  for ( int i = 1; i < data.size(); i++ ) {
    PlotPoint pp1 = (PlotPoint) data.get(i);
    PlotPoint pp2 = (PlotPoint) data.get(i-1);
    
    line( (pp1.x-xmin)*xsize/(xmax-xmin)+xpos, (ymax - pp1.y)*ysize/(ymax-ymin)+ypos, 
          (pp2.x-xmin)*xsize/(xmax-xmin)+xpos, (ymax - pp2.y)*ysize/(ymax-ymin)+ypos); 
  }
  
  stroke(0);
  noFill();  
  rect(xpos, ypos, xsize, ysize);
  
}

  
  
  
  
  
  
}// end of class plot


// a class to define each point in the plot as an object
class PlotPoint{
 
 float x;
 float y; 
  
  PlotPoint(float x_ , float y_){
    x = x_;
    y = y_;
  }
  
  
  
}  

  static public void main(String args[]) {
    PApplet.main(new String[] { "--bgcolor=#FFFFFF", "IsingModel" });
  }
}
