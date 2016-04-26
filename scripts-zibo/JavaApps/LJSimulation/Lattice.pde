class Lattice{

//Particle properties
ArrayList P;
int   N  = 1;
float r0 = 1;
float m  = 1;
float K  = 50;

//Termostat variables
float initVel = 1;

float Q  = 1000; //coupling between Ensemble and Bath
float nu = 0; // drag term
float T  = 40; // system temperature
float E  = 0; // System Energy 


//Container description
float BoxWidth  = 400;
float BoxHeight = 400;
float BoxPosX   = 0;
float BoxPosY   = 0;

//Radial Distrobution Data
int      Nbins    = 100;
float [] count;   // = new float[Nbins];
float [] Distance;// = new float[Nbins];

//L-J Potential plot
float [] potential; 
float pmin;
float pmax;
float [] TempLine;
float [] WidthIndex;

float pow(float base, int exp) {
    float result = 1;
    while (exp!=0) {
        if ((exp & 1)!=0) result *= base;
        exp >>= 1;
        base *= base;
    }
    return result;
}

  
  //Creation Function for the lattice
  Lattice(float PosX_, float PosY_, float BoxWidth_, float BoxHeight_, int N_, float r0_, float m_, float K_, float initVel_, int Nbins_)
  {
    r0 = r0_;
    
    BoxPosX   = PosX_ + r0/2;
    BoxPosY   = PosY_ + r0/2;
    BoxWidth  = BoxWidth_ - r0;
    BoxHeight = BoxHeight_ - r0;
        
    N  = N_;
    m  = m_;
    K  = K_;
    initVel = initVel_;
    
    Nbins = Nbins_;
    count    = new float[Nbins];
    Distance = new float[Nbins];
    
    //potential = new float [int(BoxWidth/2)];
    //rvec = new float [int(BoxWidth/2)];
    potential = new float [Nbins];
    TempLine = new float [int(BoxWidth*4)];
    WidthIndex = new float [int(BoxWidth*4)];
    
    
    P = new ArrayList();
         
    // create Particles in arbitrary locations 
    int n = 0;
    int k = 0;
    int l = 0;
    while ( n < N){ 
      
        float px = (k + (l%2)*0.5 + 3.5 )*( r0*1.01 );
        //if ( px % BoxWidth*0.7 <= r0*1.01 && n>0 ){
          if ( k >= 10 ){
           k = 0;
           l++;
           //px = (k + (l%2)*0.5 + 5 )*( r0*1.01 ) % BoxWidth*0.7;
           px = (k + (l%2)*0.5 +3.5 )*( r0*1.01 ); 
          }     
        float py = (l*0.5*sqrt(3) + 3.5)*( r0*1.01 ) ;
        
  
        P.add( new Particle( px, py, m ) );
  
        //Particle Ptemp = (Particle) P.get(n);
        //Ptemp.vel[0] = random( -initVel, initVel);
        //Ptemp.vel[1] = random( -initVel,initVel);
       k++;
       //if (px%BoxWidth <= r0 && n>0 ){ l++;}
       n++;
    }// end while
  
    
    
//    for (int i = 0; i < N ; i++){
//      
//      P.add( new Particle( BoxWidth/sqrt(N)*(1/2+floor(i%sqrt(N))) , BoxHeight/sqrt(N)*floor(i/sqrt(N)), m) );
//     
//      Particle Ptemp = (Particle) P.get(i);
//      Ptemp.vel[0] = random( -initVel, initVel);
//      Ptemp.vel[1] = random( -initVel,initVel);
//     }// end For




     
     //Create Distance bins for distrobution function;
     for( int i = 0; i< Nbins ; i++){
      Distance[i] = i*BoxWidth/(2*Nbins); 
      //Distance[i] = i*BoxWidth/(Nbins);
     }
     
     
     //Create LJ potential function for plotting
     for( int i = 0; i< Nbins-6 ; i++){
     // rvec[i] = i;
     potential[i] = 4*K*(pow(r0,12)/pow(Distance[i+6],12) - pow(r0,6)/pow(Distance[i+6]+1,6) ) ;
     }
     for( int i = 0; Distance[i+6]< r0 ; i++){
      potential[i] = 100;
     }
     
     // find min and max values of potential
     pmin = potential[0];
     pmax = potential[0];
     for (int i=1; i<potential.length; i++){
     if( potential[i] > pmax ) { pmax = potential[i];}
     else if( potential[i] < pmin) { pmin = potential[i];} 
     }
     //print( pmin); print("  "); print(pmax); println();
    
    
    for(int i = 0 ; i < BoxWidth*4 ; i++){
     WidthIndex[i] = i; 
     TempLine[i] = T;
    } 
     
     
     
  }// End of creation Function
  
  
  
  void update(){
    frameRate(30);
    
    //clear distrobution Function;
    count    = new float[Nbins];
    
    //calculate forces on each Particle (includes periodic boundary condition)
    for (int i = 0; i<N ; i++){
      
      Particle P1 = (Particle) P.get(i);
      
      for(int j = 0 ; j<N ; j++){
        Particle P2 = (Particle) P.get(j);
      
        float DistX2 = P1.pos[0] - P2.pos[0]; 
        float DistX1 = DistX2 - BoxWidth;
        float DistX3 = DistX2 + BoxWidth;
        //print(DistX1); print(", "); print(DistX2); print(", "); print(DistX3); println();
        
        float DistY2 = P1.pos[1] - P2.pos[1];
        float DistY1 = DistY2 - BoxWidth;
        float DistY3 = DistY2 + BoxWidth;
        
        float DistX12=DistX1*DistX1;
        float DistX22=DistX2*DistX2;
        float DistX32=DistX3*DistX3;
        float DistY12=DistY1*DistY1;
        float DistY22=DistY2*DistY2;
        float DistY32=DistY3*DistY3;
        //print(DistY1); print(", "); print(DistY2); print(", "); print(DistY3); println();
    
        float Dist1 = sqrt( DistX12 + DistY12); 
        //print(Dist1); print(", ");
        
        float Dist2 = sqrt( DistX22 + DistY12);
        //print(Dist2); print(", ");
        
        float Dist3 = sqrt( DistX32 + DistY12);
        //print(Dist3); print(", ");
        
        float Dist4 = sqrt( DistX12 + DistY22);
        //print(Dist4); print(", ");
        
        float Dist5 = sqrt( DistX22 + DistY22);
        //print(Dist5); print(", ");
        
        float Dist6 = sqrt( DistX32 + DistY22);
        //print(Dist6); print(", ");
        
        float Dist7 = sqrt( DistX12 + DistY32);
        //print(Dist7); print(", ");
        
        float Dist8 = sqrt( DistX22 + DistY32);
        //print(Dist8); print(", ");
        
        float Dist9 = sqrt( DistX32 + DistY32);
        //print(Dist9); print(", "); println();
        
        float r012=pow(r0,12), r06=pow(r0,6);
        
        float F1 = 12*K*( r012/pow(Dist1,13) - r06/pow(Dist1,7));
        //print(F1); print(", ");
        
        float F2 = 12*K*( r012/pow(Dist2,13) - r06/pow(Dist2,7));
        //print(F2); print(", ");
        
        float F3 = 12*K*( r012/pow(Dist3,13) - r06/pow(Dist3,7));
        //print(F3); print(", ");
        
        float F4 = 12*K*( r012/pow(Dist4,13) - r06/pow(Dist4,7));
        //print(F4); print(", ");
        
        float F5 = 0;
        if ( Dist5 != 0){ F5 = 12*K*( r012/pow(Dist5,13) - r06/pow(Dist5,7)); }// end if
        //print(F5); print(", ");
        
        float F6 = 12*K*( r012/pow(Dist6,13) - r06/pow(Dist6,7));
        //print(F6); print(", ");
        
        float F7 = 12*K*( r012/pow(Dist7,13) - r06/pow(Dist7,7));
        //print(F7); print(", ");
        
        float F8 = 12*K*( r012/pow(Dist8,13) - r06/pow(Dist8,7));
        //print(F8); print(", ");
        
        float F9 = 12*K*( r012/pow(Dist9,13) - r06/pow(Dist9,7));
        //print(F9); print(", "); println();
        
        float Fx = 0;
        float Fy = 0;
        if (F5 != 0 ) {
          Fx = F1*DistX1/Dist1 + F2*DistX2/Dist2 + F3*DistX3/Dist3 + F4*DistX1/Dist4 + F5*DistX2/Dist5 + F6*DistX3/Dist6 + F7*DistX1/Dist7 + F8*DistX2/Dist8 + F9*DistX3/Dist9;
          Fy = F1*DistY1/Dist1 + F2*DistY1/Dist2 + F3*DistY1/Dist3 + F4*DistY2/Dist4 + F5*DistY2/Dist5 + F6*DistY2/Dist6 + F7*DistY3/Dist7 + F8*DistY3/Dist8 + F9*DistY3/Dist9;
        }
        else {
          Fx = F1*DistX1/Dist1 + F2*DistX2/Dist2 + F3*DistX3/Dist3 + F4*DistX1/Dist4 + F6*DistX3/Dist6 + F7*DistX1/Dist7 + F8*DistX2/Dist8 + F9*DistX3/Dist9;
          Fy = F1*DistY1/Dist1 + F2*DistY1/Dist2 + F3*DistY1/Dist3 + F4*DistY2/Dist4 + F6*DistY2/Dist6 + F7*DistY3/Dist7 + F8*DistY3/Dist8 + F9*DistY3/Dist9;  
        }
        
        
        //print(Fx); print(", "); print(Fy); println();
        
        P1.vel[0] += Fx/P1.m;
        P1.vel[1] += Fy/P1.m; 
        
       //print(P1.vel[0]); print(", "); print(P1.vel[1]); println();
       
       //add distances to radial distrobution function
       for ( int k = 1; k<Nbins; k++){
         if( (Dist1) < Distance[k] && (Dist1) > Distance[k-1]){count[k]++;}
         if( (Dist2) < Distance[k] && (Dist2) > Distance[k-1]){count[k]++;}
         if( (Dist3) < Distance[k] && (Dist3) > Distance[k-1]){count[k]++;}
         if( (Dist4) < Distance[k] && (Dist4) > Distance[k-1]){count[k]++;}
         if( (Dist5) < Distance[k] && (Dist5) > Distance[k-1]){count[k]++;}
         if( (Dist6) < Distance[k] && (Dist6) > Distance[k-1]){count[k]++;}
         if( (Dist7) < Distance[k] && (Dist7) > Distance[k-1]){count[k]++;}
         if( (Dist8) < Distance[k] && (Dist8) > Distance[k-1]){count[k]++;}
         if( (Dist9) < Distance[k] && (Dist9) > Distance[k-1]){count[k]++;}
         
       }// end for (radial distrobution function
       
        
     
      }// end for
      
      for (int j = 1; j < Nbins; j++){
        count[j] = count[j]*(BoxWidth*BoxWidth)/(4*PI*(Distance[j]*Distance[j])*(N*N));
      } 
    
    }// end for (Force Calculation)
    
    
    //Thermostat
    E = 0;
    for (int i = 0 ; i<N ; i++){
     Particle Ptemp = (Particle) P.get(i);
     E += Ptemp.e;
    }// end for 
    
    E = E/N; // Average Kinetic Energy of the System
    nu += (E-T)/Q; // Adjust drag based on average energy
    
    for (int i = 0; i<N ; i++){
      Particle Ptemp = (Particle) P.get(i);
      Ptemp.vel[0] -= Ptemp.vel[0]*nu;
      Ptemp.vel[1] -= Ptemp.vel[1]*nu;
    }// end for
    // end of thermostat  
    
       
    for (int i = 0 ; i<N ; i++){ // update Particle positions
      Particle Ptemp = (Particle) P.get(i);
      Ptemp.update();
      
      if (Ptemp.pos[0] < BoxPosX)          {Ptemp.pos[0] += BoxWidth;  }
      if (Ptemp.pos[0] > BoxPosX+BoxWidth) {Ptemp.pos[0] -= BoxWidth;  }
      if (Ptemp.pos[1] < BoxPosY)          {Ptemp.pos[1] += BoxHeight; }
      if (Ptemp.pos[1] > BoxPosY+BoxWidth) {Ptemp.pos[1] -= BoxHeight; }
      
    }// end for
  
  
   for(int i = 0 ; i < BoxWidth*4 ; i++){
     WidthIndex[i] = i;
     TempLine[i] = E;
    } 
    TempLine[0] = pmax-pmin;
    TempLine[int(BoxWidth*4)-1] = 0;
  
  }// End Update
  
  
  void Disp()
  {
    stroke(0);
    fill(50,50,50,255);
    rect(BoxPosX - r0/2-1 , BoxPosY - r0/2-1, BoxWidth + r0+1, BoxHeight + r0+1);
    
    for (int i = 0 ; i<N ; i++){
      Particle Ptemp = (Particle) P.get(i);
      noStroke();
      fill(2*Ptemp.e,0,255-2*Ptemp.e );
      ellipse(Ptemp.pos[0], Ptemp.pos[1], r0*0.9, r0*0.9);
    }
    noStroke();
    fill(150);
    rect(BoxPosX - r0/2, BoxPosY - r0/2, BoxWidth + r0, r0/2);
    rect(BoxPosX - r0/2, BoxPosY - r0/2, r0/2, BoxHeight + r0);
    rect(BoxPosX - r0/2, BoxPosY + BoxHeight, BoxWidth + r0, r0/2);
    rect(BoxPosX + BoxWidth, BoxPosY - r0/2, r0/2, BoxHeight + r0);
    stroke(0);
    noFill();
    rect(BoxPosX-1 , BoxPosY-1 , BoxWidth+1 , BoxHeight+1);    
  }// End Disp
  
  
//  void SetBins(int Nbins_)
//  {
//    Nbins = Nbins_;
//    float [] count    = new float[Nbins];
//    float [] Distance = new float[Nbins];    
//  }
  
  
  
  
  
  
  
  
  
}// End Class
