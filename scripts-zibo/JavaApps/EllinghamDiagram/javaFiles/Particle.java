// 
// Decompiled by Procyon v0.5.30
// 

class Particle
{
    float m;
    float[] pos;
    float[] vel;
    int[] fixed;
    float e;
    public float dt;
    
    Particle(final float n, final float n2, final float m) {
        this.m = 1.0f;
        this.pos = new float[] { 0.0f, 0.0f, 0.0f };
        this.vel = new float[] { 0.0f, 0.0f, 0.0f };
        this.fixed = new int[] { 0, 0, 0 };
        this.e = 0.0f;
        this.dt = 0.005f;
        this.pos[0] = n;
        this.pos[1] = n2;
        this.m = m;
    }
    
    Particle(final float n, final float n2, final float n3, final float m) {
        this.m = 1.0f;
        this.pos = new float[] { 0.0f, 0.0f, 0.0f };
        this.vel = new float[] { 0.0f, 0.0f, 0.0f };
        this.fixed = new int[] { 0, 0, 0 };
        this.e = 0.0f;
        this.dt = 0.005f;
        this.pos[0] = n;
        this.pos[1] = n2;
        this.pos[2] = n3;
        this.m = m;
    }
    
    Particle(final float n, final float n2, final float m, final float n3, final float n4) {
        this.m = 1.0f;
        this.pos = new float[] { 0.0f, 0.0f, 0.0f };
        this.vel = new float[] { 0.0f, 0.0f, 0.0f };
        this.fixed = new int[] { 0, 0, 0 };
        this.e = 0.0f;
        this.dt = 0.005f;
        this.pos[0] = n;
        this.pos[1] = n2;
        this.m = m;
        this.vel[0] = n3;
        this.vel[1] = n4;
    }
    
    Particle(final float n, final float n2, final float n3, final float m, final float n4, final float n5, final float n6) {
        this.m = 1.0f;
        this.pos = new float[] { 0.0f, 0.0f, 0.0f };
        this.vel = new float[] { 0.0f, 0.0f, 0.0f };
        this.fixed = new int[] { 0, 0, 0 };
        this.e = 0.0f;
        this.dt = 0.005f;
        this.pos[0] = n;
        this.pos[1] = n2;
        this.pos[2] = n3;
        this.m = m;
        this.vel[0] = n4;
        this.vel[1] = n5;
        this.vel[2] = n6;
    }
    
    void update() {
        this.pos[0] += this.dt * this.vel[0];
        this.pos[1] += this.dt * this.vel[1];
        this.pos[2] += this.dt * this.vel[2];
        this.e = 0.5f * this.m * (float)Math.sqrt(this.vel[0] * this.vel[0] + this.vel[1] * this.vel[1] + this.vel[2] * this.vel[2]);
    }
}
