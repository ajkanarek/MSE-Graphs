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
float GDot = 0.01; // Rate of gravity change;
float GDDot = 0.01; // acceleration of gravity change
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
void Update(){
  // draw a white box to display a piston on
  stroke(0);
  fill(200,200,200);
  rect(boxpos[0],boxpos[1], boxsize[0], boxsize[1]);

  // determine timestep 
  float dt=0.005;
  
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
        if(bounceTime[j]<-1e-4 || bounceTime[j]>dt) continue;
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
    
    if (FixedVol && (abs(Plunger.pos[1]-setHeight) >= 0.5) ){
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
   
   else if (FixedVol && (abs(Plunger.pos[1]-setHeight) < 0.5) ){
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
  fill(.001*AveE,0,255-0.001*AveE);
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
void AddBalls( int Num, float mass, float Temp){
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
void RmBalls( int Num_){
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
