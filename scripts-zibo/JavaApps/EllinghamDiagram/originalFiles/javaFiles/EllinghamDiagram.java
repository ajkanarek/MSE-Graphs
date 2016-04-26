/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  EllinghamDiagram$EllinghamBoard
 *  EllinghamDiagram$EllinghamPlot
 *  processing.core.PApplet
 */
import processing.core.PApplet;

public class EllinghamDiagram
extends PApplet {
    public EllinghamPlot plot;
    EllinghamBoard board;
    public int W = 800;
    public int H = 600;
    public int D = 541;
    public String statusText;

    public void setup() {
        this.size(540, 600);
        this.smooth();
        this.plot = new EllinghamPlot(this, (PApplet)this, 0, 0, this.D, this.H);
        this.board = new EllinghamBoard(this, this.plot, this.D + 1, 0, this.W - this.D, this.H);
        this.ScribePlot();
    }

    public void ScribePlot() {
        if (this.plot.updated) {
            this.background(200);
            this.plot.ScribePlot();
            this.board.draw();
        }
        this.stroke(100);
    }

    public void draw() {
    }

    public void mousePressed() {
        this.plot.mousePressed();
        this.plot.updated = true;
        this.ScribePlot();
    }

    public void mouseDragged() {
        this.plot.mouseDragged();
        this.ScribePlot();
    }

    public void mouseReleased() {
        this.plot.mouseReleased();
        this.ScribePlot();
    }

    public void test(int n) {
        this.background(n);
    }

    public String getTags() {
        return this.plot.getTags();
    }

    public void setHidden(int n, boolean bl) {
        this.plot.setHidden(n, bl);
        this.ScribePlot();
    }

    public void addData(float f, float f2, String string, float f3, float f4) {
        this.plot.addData(f, f2, string, f3 - 273.16f, f4 - 273.16f);
        this.ScribePlot();
    }

    public boolean getHidden(int n) {
        return this.plot.getHidden(n);
    }

    public String getValueO() {
        return String.format("%.3e", this.plot.getValueO());
    }

    public String getValueH() {
        return String.format("%.3e", this.plot.getValueH());
    }

    public String getValueC() {
        return String.format("%.3e", this.plot.getValueC());
    }

    public static void main(String[] arrstring) {
        PApplet.main((String[])new String[]{"--bgcolor=#FFFFFF", "EllinghamDiagram"});
    }
}
