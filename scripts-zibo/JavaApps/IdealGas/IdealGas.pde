import thermodynamics.*;

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

void setup()
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
 Time.offer(0.0);
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

void draw ()
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


void mousePressed() {
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

