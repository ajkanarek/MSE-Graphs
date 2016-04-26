import processing.core.PApplet;

// 
// Decompiled by Procyon v0.5.30
// 

public class CheckBox
{
    PApplet applet;
    String str;
    int posX;
    int posY;
    int sizeX;
    int sizeY;
    TextLabel label;
    public boolean checked;
    boolean prevStatus;
    
    public CheckBox(final PApplet applet, final int posX, final int posY, final String s) {
        this.applet = applet;
        (this.label = new TextLabel(applet, s, posX + 25, posY)).SetAlign(37, 101);
        this.posX = posX;
        this.posY = posY;
        this.sizeX = 25 + (int)this.label.sizeX;
        this.sizeY = 15;
    }
    
    public boolean over() {
        return this.applet.mouseX >= this.posX && this.applet.mouseX < this.posX + this.sizeX && this.applet.mouseY >= this.posY && this.applet.mouseY < this.posY + this.sizeY;
    }
    
    public void update() {
        final boolean prevStatus = this.over() && this.applet.mousePressed;
        if (!this.prevStatus && prevStatus) {
            this.checked = !this.checked;
        }
        this.prevStatus = prevStatus;
    }
    
    public void display() {
        this.applet.noStroke();
        this.applet.fill(200.0f, 200.0f, 200.0f);
        this.applet.rect((float)this.posX, (float)this.posY, (float)this.sizeX, (float)this.sizeY);
        this.applet.stroke(0);
        if (this.checked) {
            this.applet.fill(200.0f, 0.0f, 0.0f);
        }
        else {
            this.applet.fill(200.0f, 200.0f, 200.0f);
        }
        final PApplet applet = this.applet;
        final PApplet applet2 = this.applet;
        applet.rectMode(0);
        this.applet.rect((float)this.posX, (float)this.posY, (float)this.sizeY, (float)this.sizeY);
        this.applet.fill(0);
        this.label.draw();
    }
}
