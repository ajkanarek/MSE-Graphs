import processing.core.PApplet;

// 
// Decompiled by Procyon v0.5.30
// 

public class VScrollBar
{
    int swidth;
    int sheight;
    int xpos;
    int ypos;
    float spos;
    float newspos;
    int sposMin;
    int sposMax;
    int loose;
    boolean over;
    boolean locked;
    float ratio;
    PApplet applet;
    
    public VScrollBar(final PApplet applet, final int xpos, final int ypos, final int swidth, final int sheight) {
        this.applet = applet;
        this.swidth = swidth;
        this.sheight = sheight;
        this.ratio = 1.0f / (sheight - swidth);
        this.xpos = xpos;
        this.ypos = ypos;
        this.spos = this.ypos + this.sheight / 2 - this.swidth / 2;
        this.newspos = this.spos;
        this.sposMin = this.ypos;
        this.sposMax = this.ypos + this.sheight - this.swidth;
        this.loose = 2;
    }
    
    public VScrollBar(final PApplet applet, final int xpos, final int ypos, final int swidth, final int sheight, final int loose) {
        this.applet = applet;
        this.swidth = swidth;
        this.sheight = sheight;
        this.ratio = 1.0f / (sheight - swidth);
        this.xpos = xpos;
        this.ypos = ypos;
        this.spos = this.ypos + this.sheight / 2 - this.swidth / 2;
        this.newspos = this.spos;
        this.sposMin = this.ypos;
        this.sposMax = this.ypos + this.sheight - this.swidth;
        this.loose = loose;
    }
    
    public void update() {
        if (this.over()) {
            this.over = true;
        }
        else {
            this.over = false;
        }
        if (this.applet.mousePressed && this.over) {
            this.locked = true;
        }
        if (!this.applet.mousePressed) {
            this.locked = false;
        }
        if (this.locked) {
            final PApplet applet = this.applet;
            this.newspos = PApplet.constrain(this.applet.mouseY - this.swidth / 2, this.sposMin, this.sposMax);
        }
        if (PApplet.abs(this.newspos - this.spos) > 1.0f) {
            this.spos += (this.newspos - this.spos) / this.loose;
        }
    }
    
    public int constrain(final int n, final int n2, final int n3) {
        return PApplet.min(PApplet.max(n, n2), n3);
    }
    
    public boolean over() {
        return this.applet.mouseX > this.xpos && this.applet.mouseX < this.xpos + this.swidth && this.applet.mouseY > this.ypos && this.applet.mouseY < this.ypos + this.sheight;
    }
    
    public void display() {
        this.applet.fill(255);
        this.applet.rect((float)this.xpos, (float)this.ypos, (float)this.swidth, (float)this.sheight);
        if (this.over || this.locked) {
            this.applet.fill(150.0f, 0.0f, 0.0f);
        }
        else {
            this.applet.fill(200.0f, 200.0f, 200.0f);
        }
        this.applet.rect((float)this.xpos, this.spos, (float)this.swidth, (float)this.swidth);
    }
    
    public float getPos() {
        return 1.0f - (this.spos - this.ypos) * this.ratio;
    }
}
