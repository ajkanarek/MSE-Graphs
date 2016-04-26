class Lattice {
  private int cols;
  private int rows;
  private int[][] elements;
  
  private float temp;
  private float Energy;
  
  float percentA = 0.5;
  
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
  void disp(){
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
  void disp( float x0,  float y0, float xsize, float ysize){
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
  int getCols(){
    return cols;
  }// end getCols
  
  // get number of rows
  int getRows(){
    return rows;
  }// end getRows  
  
  // set value of a given element
  void setElem( int x, int y, int val){
    elements[x][y] = val;
  }// end setElem
  
  // get value of a given element
  int getElem( int x, int y){
    return elements[x][y];
  }// end getElem
  
  // set system temperature
  void setTemp(float T_){
    temp = T_;
  }// end setTemp
  
  // get system temperature
  float getTemp(){
    return temp;
  }// end getTemp
  
  // get system energy
  float getEnergy(){
    return Energy;
  }// end getEnergy
  
  
  //calculate the energy of the lattice and update Energy
  void CalcEnergy(){
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
  float twoway(int i, int j){
    
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
  void CountBonds(){
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
  void MCStep(){
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
