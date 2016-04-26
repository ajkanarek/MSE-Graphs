import thermodynamics.*;

Lattice L;
VScrollBar vsTemp;  
Plot plot;

int N = 100;
//float r0 = 35;
float r0 = 25;
float m = 1;

//Thermostat Settings
float K = 100;       // Potential Well Depth
float T = 70;         // Thermostat Temp
float Q = 100; // Thermostat coupling coeficient (small values cause temp oscilations, large ones, lead to slow equilibration)

float initVel = 10;


int BoxWidth = 398;
int BoxHeight = 398;
int BoxPosX =1;
int BoxPosY =1;
float prevPos=0.5;
int Nbins = 200;

//Text
TextLabel TS_Text;
TextLabel T_Set;

TextLabel TC_Text;
TextLabel T_Current;


void setup(){
 size(421,500);
 smooth();
 frameRate(30);
 background (255); 
 
 vsTemp = new VScrollBar(this,width -21, 0, 20, height-1, 2);
 vsTemp.spos = vsTemp.sposMax*0.95;
 vsTemp.newspos = vsTemp.sposMax*0.95;
 
 L = new Lattice( BoxPosX, BoxPosY, BoxWidth, BoxHeight, N, r0, m, K, initVel, Nbins);
 //L.T = T; 
 L.T = T * vsTemp.getPos(); 
 L.Q = Q; 
 
 
 
 stroke(0);
 fill(200,200,200,200);
 rect(0, BoxHeight, width-22, height - BoxHeight  );
 plot = new Plot(this, 8, BoxHeight+8, width-22-16, height -BoxHeight-16);
 plot.AddData(L.Distance, L.count);
 plot.AddData(L.Distance, L.potential);
 plot.AddData(L.WidthIndex, L.TempLine);
 plot.Color(0, 0, 0, 250);
 plot.Color(1, 0, 200, 0);
 plot.Color(2, 200, 0, 0);
 
 TS_Text = new TextLabel(this, "Set Temp (K) = ", 275,407);
 TS_Text.SetAlign(LEFT,TOP);
 TS_Text.draw();
 
 //TC_Text = new TextLabel(this, "Temp (K) = ", 296,420);
 //TC_Text.SetAlign(LEFT,TOP);
 //TC_Text.draw();
 
 T_Set = new TextLabel(this, str(int(L.T)), 360,407);
 T_Set.SetAlign(LEFT,TOP);
 T_Set.draw();
 
 //T_Current = new TextLabel(this, str(int(L.E*21.8)), 360,420);
 //T_Current.SetAlign(LEFT,TOP);
 //T_Current.draw();
 
}// end Setup


void draw(){
 vsTemp.update();
 vsTemp.display();
 float pos=vsTemp.getPos();
 float th=0.05;
 if(pos-prevPos>th) {
   pos=prevPos+th;
 } else if(pos-prevPos<-th) {
   pos=prevPos-th;
 } 
 L.T = T * pos;
 prevPos=pos;
 
 L.update();
 L.Disp();
 
 stroke(0);
 fill(150,150,150,150);
 rect(0, BoxHeight, width-22, height - BoxHeight-1  );
 fill(50, 50, 50, 200);
 rect(8, BoxHeight+8, width-22-16, height -BoxHeight-16);
 
 plot.UpdateData(0,L.Distance, L.count);
 plot.ScribePlot();
 noFill();
 stroke(0);
 rect(8, BoxHeight+8, width-22-16, height -BoxHeight-16);
 
 TS_Text.draw();
 
 //TC_Text.draw();
 
 T_Set = new TextLabel(this, str(int(L.T*21.8)) , 360,407);
 T_Set.SetAlign(LEFT,TOP);
 T_Set.draw();
 
 //T_Current = new TextLabel(this, str(int(L.E*21.8)), 360,420);
 //T_Current.SetAlign(LEFT,TOP);
 //T_Current.draw();
 
}// End Draw







