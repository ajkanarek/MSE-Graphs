import java.util.HashMap;
import java.awt.Font;
import java.util.Map;
import processing.core.PFont;
import processing.core.PApplet;

// 
// Decompiled by Procyon v0.5.30
// 

public class TextLabel
{
    PApplet applet;
    String str;
    int posX;
    int posY;
    int alignX;
    int alignY;
    PFont normal;
    PFont small;
    float height;
    Map<Character, Float> width;
    float sizeX;
    float sizeY;
    float fontSize;
    float smallSize;
    float offsetX;
    float offsetY;
    float rotation;
    
    public TextLabel(final PApplet applet, final String str) {
        this.applet = applet;
        this.str = str;
        this.posX = -1;
        this.posY = -1;
        this.SetFont("Helvetica", 12);
        this.SetAlign(3, 3);
    }
    
    public TextLabel(final PApplet applet, final String str, final int posX, final int posY) {
        this.applet = applet;
        this.str = str;
        this.posX = posX;
        this.posY = posY;
        this.SetFont("Helvetica", 12);
        this.SetAlign(3, 3);
    }
    
    public void SetFont(final String s, final int n) {
        this.fontSize = n;
        this.normal = new PFont(new Font(s, 0, n), true);
        this.smallSize = n * 11 / 12;
        this.small = new PFont(new Font(s, 0, Math.round(this.smallSize)), true);
        this.height = this.normal.ascent() + this.normal.descent();
        this.sizeY = this.height * this.fontSize;
        this.sizeX = 0.0f;
        this.width = new HashMap<Character, Float>();
        for (int i = 0; i < this.str.length(); ++i) {
            final char char1 = this.str.charAt(i);
            if (!this.width.containsKey(char1)) {
                this.width.put(char1, this.normal.width(char1));
            }
            if (char1 == '^' || char1 == '_') {
                char char2;
                while (++i < this.str.length() && (char2 = this.str.charAt(i)) != ' ') {
                    if (!this.width.containsKey(char2)) {
                        this.width.put(char2, this.normal.width(char2));
                    }
                    this.sizeX += this.width.get(char2) * this.smallSize;
                }
            }
            else {
                this.sizeX += this.width.get(char1) * n;
            }
        }
    }
    
    public void SetAlign(final int alignX, final int alignY) {
        this.alignX = alignX;
        this.alignY = alignY;
        switch (alignX) {
            case 37: {
                this.offsetX = 0.0f;
                break;
            }
            case 3: {
                this.offsetX = -this.sizeX / 2.0f;
                break;
            }
            case 39: {
                this.offsetX = -this.sizeX;
                break;
            }
        }
        switch (alignY) {
            case 102: {
                this.offsetY = 0.0f;
                break;
            }
            case 3: {
                this.offsetY = this.sizeY / 2.0f;
                break;
            }
            case 101: {
                this.offsetY = this.sizeY;
                break;
            }
            case 0: {
                this.offsetY = this.normal.descent() * this.fontSize;
                break;
            }
        }
    }
    
    public void SetRotation(final float rotation) {
        this.rotation = rotation;
    }
    
    public void draw() {
        if (this.posX == -1 || this.posY == -1) {
            return;
        }
        this.applet.pushMatrix();
        this.applet.translate((float)this.posX, (float)this.posY);
        this.applet.rotate(this.rotation);
        this.applet.textAlign(37);
        float n = 0.0f;
        for (int i = 0; i < this.str.length(); ++i) {
            final char char1 = this.str.charAt(i);
            if (char1 == '^' || char1 == '_') {
                final char c = char1;
                this.applet.textFont(this.small, this.smallSize);
                char char2;
                while (++i < this.str.length() && (char2 = this.str.charAt(i)) != ' ') {
                    this.applet.text(char2, this.offsetX + n, this.offsetY + ((c == '^') ? (-this.smallSize / 2.0f) : (this.smallSize / 4.0f)));
                    n += this.width.get(char2) * this.smallSize;
                }
            }
            else {
                this.applet.textFont(this.normal, this.fontSize);
                this.applet.text(char1, this.offsetX + n, this.offsetY);
                n += this.width.get(char1) * this.fontSize;
            }
        }
        this.applet.popMatrix();
    }
    
    public void draw(final int n, final int n2) {
        this.applet.pushMatrix();
        this.applet.translate((float)n, (float)n2);
        this.applet.rotate(this.rotation);
        this.applet.textAlign(37);
        float n3 = 0.0f;
        for (int i = 0; i < this.str.length(); ++i) {
            final char char1 = this.str.charAt(i);
            if (char1 == '^' || char1 == '_') {
                final char c = char1;
                this.applet.textFont(this.small, this.smallSize);
                char char2;
                while (++i < this.str.length() && (char2 = this.str.charAt(i)) != ' ') {
                    this.applet.text(char2, this.offsetX + n3, this.offsetY + ((c == '^') ? (-this.smallSize / 2.0f) : (this.smallSize / 4.0f)));
                    n3 += this.width.get(char2) * this.smallSize;
                }
            }
            else {
                this.applet.textFont(this.normal, this.fontSize);
                this.applet.text(char1, this.offsetX + n3, this.offsetY);
                n3 += this.width.get(char1) * this.fontSize;
            }
        }
        this.applet.popMatrix();
    }
}
