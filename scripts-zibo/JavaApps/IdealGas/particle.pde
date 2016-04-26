
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
  float dt = 0.005;
  
  // creates particle
  particle( float x_, float  y_, float m_)
  {
    pos[0] = x_;
    pos[1] = y_;
    m = m_;
    e = 0.5*m*(sq(vel[0])+sq(vel[1])+sq(vel[2]));
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
  void update(){
  pos[0] = pos[0] +dt*vel[0];
  pos[1] = pos[1] +dt*vel[1];
  pos[2] = pos[2] +dt*vel[2];
  //e = 0.5*m*(sq(vel[0])+sq(vel[1])+sq(vel[2]));
  e = energy();
  time[0] = time[0] +dt;
  time[1] = time[1] +dt;
  }  
  
  void update(float dt){
  pos[0] = pos[0] +dt*vel[0];
  pos[1] = pos[1] +dt*vel[1];
  pos[2] = pos[2] +dt*vel[2];
  //e = 0.5*m*(sq(vel[0])+sq(vel[1])+sq(vel[2]));
  e = energy();
  time[0] = time[0] +dt;
  time[1] = time[1] +dt;
  }  
  
  
  // displays particle
  void disp(){
    stroke(0);
    //fill(Geometry.Color(e/1.2e5));
    fill(255*e*0.000005, 0, 255-255*e*0.000005);
    ellipse(pos[0], pos[1], dia, dia);
  }
  
  // calculates particle energy
  float energy(){
    float e = 0.5*m*(sq(vel[0])+sq(vel[1])+sq(vel[2]));
    return e;
  }
   
}
