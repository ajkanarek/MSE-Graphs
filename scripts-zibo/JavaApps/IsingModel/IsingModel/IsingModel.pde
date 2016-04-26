import thermodynamics.*;

Lattice L;
float FractionA = 0.5;

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


void setup() {
  
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
void draw() {
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


