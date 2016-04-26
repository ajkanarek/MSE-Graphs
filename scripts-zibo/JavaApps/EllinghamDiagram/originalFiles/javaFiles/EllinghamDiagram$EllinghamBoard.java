/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  EllinghamDiagram$EllinghamPlot
 *  processing.core.PFont
 */
import processing.core.PFont;

class EllinghamDiagram.EllinghamBoard {
    EllinghamDiagram.EllinghamPlot plot;
    int posX;
    int posY;
    int sizeX;
    int sizeY;

    EllinghamDiagram.EllinghamBoard(EllinghamDiagram.EllinghamPlot ellinghamPlot, int n, int n2, int n3, int n4) {
        this.plot = ellinghamPlot;
        this.posX = n;
        this.posY = n2;
        this.sizeX = n3;
        this.sizeY = n4;
    }

    public void draw() {
        EllinghamDiagram.this.pushMatrix();
        EllinghamDiagram.this.translate((float)this.posX, (float)this.posY);
        EllinghamDiagram.this.noStroke();
        EllinghamDiagram.this.fill(300);
        EllinghamDiagram.this.rect(0.0f, 0.0f, (float)this.sizeX, (float)this.sizeY);
        EllinghamDiagram.this.fill(0);
        EllinghamDiagram.this.textFont(this.plot.font, 12.0f);
        String string = null;
        switch (this.plot.control) {
            case 'O': {
                string = "pO2";
                break;
            }
            case 'H': {
                string = "H2/H2O ratio";
                break;
            }
            case 'C': {
                string = "CO/CO2 ratio";
            }
        }
        if (string != null) {
            EllinghamDiagram.this.text(String.valueOf(string) + " : " + String.format("%.3e", this.plot.controlValue), 20.0f, 20.0f);
        }
        EllinghamDiagram.this.text("... ", 20.0f, 40.0f);
        EllinghamDiagram.this.popMatrix();
    }

    public String status() {
        String string = null;
        switch (this.plot.control) {
            case 'O': {
                string = "pO2";
                break;
            }
            case 'H': {
                string = "H2/H2O ratio";
                break;
            }
            case 'C': {
                string = "CO/CO2 ratio";
            }
        }
        if (string != null) {
            return String.valueOf(string) + " : " + String.format("%.3e", this.plot.controlValue);
        }
        return "";
    }
}
