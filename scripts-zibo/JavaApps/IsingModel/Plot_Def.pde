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
void ScribePlot(){
   
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
