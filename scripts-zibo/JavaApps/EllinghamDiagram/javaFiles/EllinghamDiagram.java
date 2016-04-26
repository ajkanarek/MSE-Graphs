/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  processing.core.PApplet
 *  processing.core.PFont
 */
import java.awt.event.MouseEvent;
import java.io.PrintStream;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PFont;

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
        this.plot = new EllinghamPlot(this, 0, 0, this.D, this.H);
        this.board = new EllinghamBoard(this.plot, this.D + 1, 0, this.W - this.D, this.H);
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

    class EllinghamBoard {
        EllinghamPlot plot;
        int posX;
        int posY;
        int sizeX;
        int sizeY;

        EllinghamBoard(EllinghamPlot ellinghamPlot, int n, int n2, int n3, int n4) {
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

    class EllinghamPlot {
        int xpos;
        int ypos;
        int xsize;
        int ysize;
        Plot plot;
        PApplet applet;
        PFont font;
        int[] padding;
        int HX1;
        int HY1;
        int HX2;
        int HY2;
        int HX3;
        int HY3;
        int HX4;
        int HY4;
        int CX1;
        int CY1;
        int CX2;
        int CY2;
        int CX3;
        int CY3;
        int CX4;
        int CY4;
        int OX1;
        int OY1;
        int OX2;
        int OY2;
        int OX3;
        int OY3;
        int startx;
        int starty;
        int O;
        int H;
        int C;
        int endx;
        int endy;
        int hHX;
        int hHY;
        int hCX;
        int hCY;
        int hOX;
        int hOY;
        boolean controlH;
        boolean controlC;
        boolean controlO;
        char control;
        double controlValue;
        public double controlValueH;
        public double controlValueC;
        public double controlValueO;
        boolean updated;
        boolean dragging;
        static final float R = 8.314472f;
        TextLabel labelH;
        TextLabel labelC;
        TextLabel labelO;
        TextLabel labelY;
        ArrayList<Float> intersectX;
        ArrayList<Float> intersectY;
        int labelIndex;

        EllinghamPlot(PApplet pApplet, int n, int n2, int n3, int n4) {
            this.padding = new int[]{50, 60, 65, 50};
            this.hHX = -1;
            this.hHY = -1;
            this.hCX = -1;
            this.hCY = -1;
            this.hOX = -1;
            this.hOY = -1;
            this.intersectX = new ArrayList();
            this.intersectY = new ArrayList();
            this.labelIndex = -1;
            this.xpos = n;
            this.ypos = n2;
            this.xsize = n3;
            this.ysize = n4;
            this.applet = pApplet;
            this.plot = new Plot(pApplet, n + this.padding[3], n2 + this.padding[0], n3 - this.padding[1] - this.padding[3], n4 - this.padding[0] - this.padding[2]);
            this.font = this.plot.font;
            float[] arrf = new float[]{0.0f, 961.78f, 2160.0f};
            float[] arrf2 = new float[]{-24.98394f, 102.203766f, 260.63242f};
            this.plot.AddData(arrf, arrf2);
            this.plot.AddTag("4Ag+O<sub>2</sub>=2Ag<sub>2</sub>O");
            arrf = new float[]{0.0f, 1084.62f, 1230.0f};
            arrf2 = new float[]{-286.59f, -136.37463f, -110.60081f};
            this.plot.AddData(arrf, arrf2);
            this.plot.AddTag("4Cu+O<sub>2</sub>=2Cu<sub>2</sub>O");
            arrf = new float[]{0.0f, 1454.85f, 2400.0f};
            arrf2 = new float[]{-424.244f, -173.98228f, 7.601352f};
            this.plot.AddData(arrf, arrf2);
            this.plot.AddTag("2Ni+O<sub>2</sub>=2NiO");
            arrf = new float[]{0.0f, 1495.0f, 1933.0f};
            arrf2 = new float[]{-428.55078f, -213.59164f, -150.62038f};
            this.plot.AddData(arrf, arrf2);
            this.plot.AddTag("2Co+O<sub>2</sub>=2CoO");
            arrf = new float[]{0.0f, 2400.0f};
            arrf2 = new float[]{-394.3293f, -396.34546f};
            this.plot.AddData(arrf, arrf2);
            this.plot.AddTag("C+O<sub>2</sub>=CO<sub>2</sub>");
            arrf = new float[]{0.0f, 2400.0f};
            arrf2 = new float[]{-271.2569f, -692.00494f};
            this.plot.AddData(arrf, arrf2);
            this.plot.AddTag("2C+O<sub>2</sub>=2CO");
            arrf = new float[]{0.0f, 2400.0f};
            arrf2 = new float[]{-516.6072f, -99.93942f};
            this.plot.AddData(arrf, arrf2);
            this.plot.AddTag("2CO+O<sub>2</sub>=2CO<sub>2</sub>");
            arrf = new float[]{100.0f, 2400.0f};
            arrf2 = new float[]{-464.5059f, -196.40804f};
            this.plot.AddData(arrf, arrf2);
            this.plot.AddTag("2H<sub>2</sub>+O<sub>2</sub>=2H<sub>2</sub>O");
            arrf = new float[]{0.0f, 1370.0f, 1537.85f, 2400.0f};
            arrf2 = new float[]{-492.2649f, -315.92532f, -294.323f, -225.00954f};
            this.plot.AddData(arrf, arrf2);
            this.plot.AddTag("2Fe+O<sub>2</sub>=2FeO");
            arrf = new float[]{0.0f, 1537.85f};
            arrf2 = new float[]{-509.1399f, -272.74777f};
            this.plot.AddData(arrf, arrf2);
            this.plot.AddTag(String.valueOf(this.fraction(3, 2)) + "Fe+O<sub>2</sub>=Fe<sub>3</sub>O<sub>4</sub>");
            arrf = new float[]{0.0f, 1906.85f};
            arrf2 = new float[]{-695.0572f, -380.6484f};
            this.plot.AddData(arrf, arrf2);
            this.plot.AddTag(String.valueOf(this.fraction(4, 3)) + "Cr+O<sub>2</sub>=" + this.fraction(2, 3) + "Cr<sub>2</sub>O<sub>3</sub>");
            arrf = new float[]{0.0f, 1245.85f, 1785.0f};
            arrf2 = new float[]{-736.1293f, -545.9383f, -450.58936f};
            this.plot.AddData(arrf, arrf2);
            this.plot.AddTag("2Mn+O<sub>2</sub>=2MnO");
            arrf = new float[]{0.0f, 842.0f, 1484.0f, 2400.0f};
            arrf2 = new float[]{-1226.578f, -1046.3558f, -908.9678f, -547.676f};
            this.plot.AddData(arrf, arrf2);
            this.plot.AddTag("2Ca+O<sub>2</sub>=2CaO");
            arrf = new float[]{0.0f, 649.85f, 1089.85f, 2400.0f};
            arrf2 = new float[]{-1144.2928f, -1003.5591f, -908.2947f, -368.55072f};
            this.plot.AddData(arrf, arrf2);
            this.plot.AddTag("2Mg+O<sub>2</sub>=2MgO");
            arrf = new float[]{0.0f, 1413.85f};
            arrf2 = new float[]{-859.325f, -611.8732f};
            this.plot.AddData(arrf, arrf2);
            this.plot.AddTag("Si+O<sub>2</sub>=SiO<sub>2</sub>");
            arrf = new float[]{0.0f, 1668.0f, 1800.0f};
            arrf2 = new float[]{-862.771f, -574.1793f, -551.3433f};
            this.plot.AddData(arrf, arrf2);
            this.plot.AddTag("Ti+O<sub>2</sub>=TiO<sub>2</sub>");
            arrf = new float[]{0.0f, 660.32f, 2054.0f};
            arrf2 = new float[]{-1065.3215f, -921.4227f, -617.7817f};
            this.plot.AddData(arrf, arrf2);
            this.plot.AddTag(String.valueOf(this.fraction(4, 3)) + "Al+O<sub>2</sub>=" + this.fraction(2, 3) + "Al<sub>2</sub>O<sub>3</sub>");
            this.plot.HideAll();
            this.plot.ShowAxis();
            this.plot.SetLineType(Plot.Circled);
            this.plot.SetAxis(0.0f, 2400.0f, -1200.0f, 0.0f);
            this.plot.SetXTitle("Temperature (\u00b0C)");
            this.HX1 = n + this.padding[3];
            this.HY1 = n2 + 20;
            this.HX2 = n + n3 - 40;
            this.HY2 = n2 + 20;
            this.HX3 = n + n3 - 40;
            this.HY3 = n2 + n4 - 40;
            this.HX4 = n + this.padding[3];
            this.HY4 = n2 + n4 - 40;
            this.CX1 = n + this.padding[3];
            this.CY1 = n2 + 40;
            this.CX2 = n + n3 - 60;
            this.CY2 = n2 + 40;
            this.CX3 = n + n3 - 60;
            this.CY3 = n2 + n4 - 60;
            this.CX4 = n + this.padding[3];
            this.CY4 = n2 + n4 - 60;
            this.OX1 = n + n3 - 20;
            this.OY1 = n2 + 20;
            this.OX2 = n + n3 - 20;
            this.OY2 = n2 + n4 - 20;
            this.OX3 = n + this.padding[3];
            this.OY3 = n2 + n4 - 20;
            this.labelH = new TextLabel(pApplet, "H_2 /H_2 O ratio");
            this.labelH.SetAlign(37, 102);
            this.labelC = new TextLabel(pApplet, "CO/CO_2  ratio");
            this.labelC.SetAlign(37, 102);
            this.labelO = new TextLabel(pApplet, "pO_2");
            this.labelO.SetAlign(3, 102);
            this.labelY = new TextLabel(pApplet, "Standard Free Energies of formulation of oxides (\u0394G=RT ln pO_2 )");
            this.labelY.SetRotation(-1.5707964f);
            this.startx = n + this.padding[3];
            this.starty = n2 + this.padding[0] + this.plot.padding[0];
            this.endx = n + this.padding[3];
            this.endy = n2 + n4 - this.padding[2] - this.plot.padding[2];
            this.O = this.starty;
            this.H = this.starty + (this.endy - this.starty) * 495 / 1200;
            this.C = this.starty + (this.endy - this.starty) * 566 / 1200;
            this.updated = true;
        }

        public String fraction(int n, int n2) {
            return "<span style='font-size:80%;letter-spacing:-1px;'><sup>" + n + "</sup>&frasl;<sub>" + n2 + "</sub> </span>";
        }

        public void ForcePlot() {
            this.updated = true;
            this.ScribePlot();
        }

        public int addData(float f, float f2, String string, float f3, float f4) {
            float[] arrf = new float[]{f3, f4};
            float[] arrf2 = new float[]{f + f3 * f2, f + f4 * f2};
            this.plot.AddData(arrf, arrf2);
            this.plot.AddTag(string);
            int n = this.plot.data.size() - 1;
            this.plot.SetHidden(n, false);
            this.findIntersects();
            this.updated = true;
            return n;
        }

        public String formatFloat(float f) {
            if (EllinghamDiagram.abs((float)f) < 10.0f) {
                return String.format("%.3f", Float.valueOf(f));
            }
            if (EllinghamDiagram.abs((float)f) < 100.0f) {
                return String.format("%.2f", Float.valueOf(f));
            }
            if (EllinghamDiagram.abs((float)f) < 1000.0f) {
                return String.format("%.1f", Float.valueOf(f));
            }
            return String.format("%d", (int)f);
        }

        public void ScribePlot() {
            float f;
            String string;
            float f2;
            float f3;
            float f4;
            if (!this.updated) {
                return;
            }
            this.plot.ForcePlot();
            EllinghamDiagram.this.pushMatrix();
            EllinghamDiagram.this.rotate(-1.5707964f);
            EllinghamDiagram.this.translate((float)((- this.ypos) * 2 - this.ysize), 0.0f);
            EllinghamDiagram.this.text("Standard Free Energies of formation of oxides (\u0394G)", (float)this.ypos, (float)(this.xpos + 10), (float)this.ysize, 25.0f);
            EllinghamDiagram.this.popMatrix();
            EllinghamDiagram.this.textFont(this.font, 12.0f);
            EllinghamDiagram.this.textAlign(3, 3);
            EllinghamDiagram.this.line((float)this.startx, (float)this.starty, (float)this.endx, (float)this.endy);
            EllinghamDiagram.this.stroke(0.0f, 200.0f, 0.0f);
            EllinghamDiagram.this.fill(0.0f, 200.0f, 0.0f);
            EllinghamDiagram.this.ellipse((float)this.startx, (float)this.starty, 4.0f, 4.0f);
            EllinghamDiagram.this.line((float)(this.startx - 3), (float)this.starty, (float)this.startx, (float)this.starty);
            EllinghamDiagram.this.stroke(0.0f, 0.0f, 200.0f);
            EllinghamDiagram.this.fill(0.0f, 0.0f, 200.0f);
            EllinghamDiagram.this.ellipse((float)this.startx, (float)this.H, 4.0f, 4.0f);
            EllinghamDiagram.this.stroke(200.0f, 0.0f, 0.0f);
            EllinghamDiagram.this.fill(200.0f, 0.0f, 0.0f);
            EllinghamDiagram.this.ellipse((float)this.startx, (float)this.C, 4.0f, 4.0f);
            EllinghamDiagram.this.stroke(0);
            EllinghamDiagram.this.fill(0);
            EllinghamDiagram.this.line((float)(this.endx - 3), (float)this.endy, (float)this.endx, (float)this.endy);
            EllinghamDiagram.this.text("O", (float)(this.startx - 10), (float)this.starty);
            EllinghamDiagram.this.text("H", (float)(this.startx - 10), (float)this.H);
            EllinghamDiagram.this.text("C", (float)(this.startx - 10), (float)this.C);
            EllinghamDiagram.this.text("0K", (float)(this.endx - 10), (float)this.endy);
            EllinghamDiagram.this.textAlign(37);
            this.labelH.draw(this.HX1, this.HY1 - 3);
            EllinghamDiagram.this.line((float)this.HX1, (float)this.HY1, (float)this.HX2, (float)this.HY2);
            EllinghamDiagram.this.line((float)this.HX2, (float)this.HY2, (float)this.HX3, (float)this.HY3);
            EllinghamDiagram.this.line((float)this.HX3, (float)this.HY3, (float)this.HX4, (float)this.HY4);
            this.labelH.draw(this.HX4, this.HY4 - 3);
            this.labelC.draw(this.CX1, this.CY1 - 3);
            EllinghamDiagram.this.line((float)this.CX1, (float)this.CY1, (float)this.CX2, (float)this.CY2);
            EllinghamDiagram.this.line((float)this.CX2, (float)this.CY2, (float)this.CX3, (float)this.CY3);
            EllinghamDiagram.this.line((float)this.CX3, (float)this.CY3, (float)this.CX4, (float)this.CY4);
            this.labelC.draw(this.CX4, this.CY4 - 3);
            this.labelO.draw(this.OX1, this.OY1 - 3);
            EllinghamDiagram.this.line((float)this.OX1, (float)this.OY1, (float)this.OX2, (float)this.OY2);
            EllinghamDiagram.this.line((float)this.OX2, (float)this.OY2, (float)this.OX3, (float)this.OY3);
            if (this.controlO) {
                EllinghamDiagram.this.stroke(0.0f, 200.0f, 0.0f);
                EllinghamDiagram.this.fill(0.0f, 200.0f, 0.0f);
                EllinghamDiagram.this.line((float)this.startx, (float)this.starty, (float)this.hOX, (float)this.hOY);
                EllinghamDiagram.this.ellipse((float)this.hOX, (float)this.hOY, 10.0f, 10.0f);
            }
            if (this.controlC) {
                EllinghamDiagram.this.stroke(200.0f, 0.0f, 0.0f);
                EllinghamDiagram.this.fill(200.0f, 0.0f, 0.0f);
                EllinghamDiagram.this.line((float)this.startx, (float)this.C, (float)this.hCX, (float)this.hCY);
                EllinghamDiagram.this.ellipse((float)this.hCX, (float)this.hCY, 10.0f, 10.0f);
            }
            if (this.controlH) {
                EllinghamDiagram.this.stroke(0.0f, 0.0f, 200.0f);
                EllinghamDiagram.this.fill(0.0f, 0.0f, 200.0f);
                EllinghamDiagram.this.line((float)this.startx, (float)this.H, (float)this.hHX, (float)this.hHY);
                EllinghamDiagram.this.ellipse((float)this.hHX, (float)this.hHY, 10.0f, 10.0f);
            }
            int n = 0;
            while (n < this.intersectX.size()) {
                f3 = this.intersectX.get(n).floatValue();
                f2 = this.intersectY.get(n).floatValue();
                f = f3 / 6.469f + (float)this.padding[3] + (float)this.plot.padding[3];
                f4 = (float)(this.padding[0] + this.plot.padding[0]) - f2 / 2.857f;
                EllinghamDiagram.this.fill(255);
                EllinghamDiagram.this.stroke(0);
                if (f + 200.0f > (float)this.applet.width) {
                    f -= 80.0f;
                }
                EllinghamDiagram.this.rect(f + 5.0f, f4 + 5.0f, 70.0f, 35.0f);
                string = this.formatFloat(f3);
                String string2 = this.formatFloat(f2);
                EllinghamDiagram.this.fill(0);
                EllinghamDiagram.this.textAlign(37, 101);
                EllinghamDiagram.this.text("T = " + string, f + 10.0f, f4 + 10.0f);
                EllinghamDiagram.this.text("\u0394G = " + string2, f + 10.0f, f4 + 25.0f);
                ++n;
            }
            n = 0;
            while (n < this.intersectX.size()) {
                f3 = this.intersectX.get(n).floatValue();
                f2 = this.intersectY.get(n).floatValue();
                f = f3 / 6.469f + (float)this.padding[3] + (float)this.plot.padding[3];
                f4 = (float)(this.padding[0] + this.plot.padding[0]) - f2 / 2.857f;
                EllinghamDiagram.this.stroke(255.0f, 0.0f, 0.0f);
                EllinghamDiagram.this.fill(255.0f, 0.0f, 0.0f);
                EllinghamDiagram.this.ellipse(f, f4, 8.0f, 8.0f);
                ++n;
            }
            if (this.labelIndex != -1) {
                float f5 = this.intersectX.get(this.labelIndex).floatValue();
                f3 = this.intersectY.get(this.labelIndex).floatValue();
                f2 = f5 / 6.469f + (float)this.padding[3] + (float)this.plot.padding[3];
                f = (float)(this.padding[0] + this.plot.padding[0]) - f3 / 2.857f;
                EllinghamDiagram.this.fill(255);
                EllinghamDiagram.this.stroke(0);
                if (f2 + 200.0f > (float)this.applet.width) {
                    f2 -= 80.0f;
                }
                EllinghamDiagram.this.rect(f2 + 5.0f, f + 5.0f, 70.0f, 35.0f);
                String string3 = this.formatFloat(f5);
                string = this.formatFloat(f3);
                EllinghamDiagram.this.fill(0);
                EllinghamDiagram.this.textAlign(37, 101);
                EllinghamDiagram.this.text("T = " + string3, f2 + 10.0f, f + 10.0f);
                EllinghamDiagram.this.text("\u0394G = " + string, f2 + 10.0f, f + 25.0f);
            }
            this.updated = false;
        }

        public boolean OnLine(int n, int n2, int n3, int n4) {
            if (n == n3) {
                if (EllinghamDiagram.abs((int)(EllinghamDiagram.this.mouseX - n)) < 8 && EllinghamDiagram.min((int)n2, (int)n4) <= EllinghamDiagram.this.mouseY && EllinghamDiagram.this.mouseY <= EllinghamDiagram.max((int)n2, (int)n4)) {
                    return true;
                }
                return false;
            }
            if (n2 == n4) {
                if (EllinghamDiagram.abs((int)(EllinghamDiagram.this.mouseY - n2)) < 8 && EllinghamDiagram.min((int)n, (int)n3) <= EllinghamDiagram.this.mouseX && EllinghamDiagram.this.mouseX <= EllinghamDiagram.max((int)n, (int)n3)) {
                    return true;
                }
                return false;
            }
            int[] arrn = this.ClosestOnLine(n, n2, n3, n4);
            if (arrn[2] != -1 && arrn[2] <= 49) {
                return true;
            }
            return false;
        }

        public int[] ClosestOnLine(int n, int n2, int n3, int n4) {
            int n5 = n3 - n;
            int n6 = n4 - n2;
            int n7 = EllinghamDiagram.this.mouseX - n;
            int n8 = EllinghamDiagram.this.mouseY - n2;
            int n9 = EllinghamDiagram.this.mouseX - n3;
            int n10 = EllinghamDiagram.this.mouseY - n4;
            int[] arrn = new int[3];
            if (n5 == 0 && n6 == 0) {
                System.err.println("invalid line");
                return arrn;
            }
            if (n7 == 0 && n8 == 0) {
                arrn[0] = n;
                arrn[1] = n2;
                arrn[2] = 0;
                return arrn;
            }
            if (n9 == 0 && n10 == 0) {
                arrn[0] = n3;
                arrn[1] = n4;
                arrn[2] = 0;
                return arrn;
            }
            float f = (float)(n5 * n7 + n6 * n8) / (float)(n5 * n5 + n6 * n6);
            float f2 = f * (float)n5;
            float f3 = f * (float)n6;
            if (f <= 0.0f) {
                arrn[0] = n;
                arrn[1] = n2;
                arrn[2] = n7 * n7 + n8 * n8;
                return arrn;
            }
            if (f2 * f2 + f3 * f3 > (float)(n5 * n5 + n6 * n6)) {
                arrn[0] = n3;
                arrn[1] = n4;
                arrn[2] = n9 * n9 + n10 * n10;
                return arrn;
            }
            arrn[0] = n + (int)f2;
            arrn[1] = n2 + (int)f3;
            int n11 = EllinghamDiagram.this.mouseX - arrn[0];
            int n12 = EllinghamDiagram.this.mouseY - arrn[1];
            arrn[2] = n11 * n11 + n12 * n12;
            return arrn;
        }

        public void mousePressed() {
            boolean bl = false;
            char c = this.control;
            if (this.OnLine(this.OX1, this.OY1, this.OX2, this.OY2) || this.OnLine(this.OX2, this.OY2, this.OX3, this.OY3)) {
                this.control = 79;
                bl = true;
            }
            if (this.OnLine(this.HX1, this.HY1, this.HX2, this.HY2) || this.OnLine(this.HX2, this.HY2, this.HX3, this.HY3) || this.OnLine(this.HX3, this.HY3, this.HX4, this.HY4)) {
                this.control = 72;
                bl = true;
            }
            if (this.OnLine(this.CX1, this.CY1, this.CX2, this.CY2) || this.OnLine(this.CX2, this.CY2, this.CX3, this.CY3) || this.OnLine(this.CX3, this.CY3, this.CX4, this.CY4)) {
                this.control = 67;
                bl = true;
            }
            this.dragging = true;
            if (c != '\u0000' && !bl) {
                this.dragging = false;
            }
            if (EllinghamDiagram.this.mouseEvent.getClickCount() == 2) {
                this.control = '\u0000';
                this.controlC = false;
                this.controlH = false;
                this.controlO = false;
                this.controlValueC = 0.0;
                this.controlValueH = 0.0;
                this.controlValueO = 0.0;
                this.updated = true;
                this.dragging = false;
                this.findIntersects();
                return;
            }
            this.mouseDragged();
        }

        public void mouseDragged() {
            int[] arrn;
            int[] arrn2;
            float f;
            int[] arrn3;
            if (!this.dragging) {
                return;
            }
            int n = this.ypos + this.padding[0] + this.plot.padding[0];
            int n2 = this.xpos + this.padding[3] + this.plot.padding[3];
            if (this.control == 'O') {
                arrn3 = this.ClosestOnLine(this.OX1, n, this.OX2, this.OY2);
                arrn = arrn3[2] < (arrn2 = this.ClosestOnLine(this.OX2, this.OY2, n2, this.OY3))[2] ? arrn3 : arrn2;
                double d = 0.0;
                this.hOX = arrn[0];
                this.hOY = arrn[1];
                this.controlO = true;
                if (arrn == arrn3) {
                    float f2 = 3216.46f;
                    int n3 = arrn3[1] - (this.ypos + this.padding[0] + this.plot.padding[0]);
                    int n4 = this.OY2 - (this.ypos + this.padding[0] + this.plot.padding[0]);
                    if (n3 < 0) {
                        n3 = 0;
                    }
                    d = EllinghamDiagram.exp((float)(-1485710.0f * (float)n3 / (float)n4 / 8.314472f / f2));
                } else {
                    float f3 = -1485.71f;
                    int n5 = arrn2[0] - (this.xpos + this.padding[3]);
                    float f4 = (float)n5 * 6.829f;
                    d = Math.exp(1000.0f * f3 / 8.314472f / f4);
                }
                this.controlValueO = this.controlValue = d;
            }
            if (this.control == 'H') {
                arrn3 = this.ClosestOnLine(n2, this.HY1, this.HX2, this.HY2);
                arrn2 = this.ClosestOnLine(this.HX2, this.HY2, this.HX3, this.HY3);
                arrn = this.ClosestOnLine(this.HX3, this.HY3, n2, this.HY4);
                int[] arrn4 = arrn3[2] < arrn2[2] ? arrn3 : arrn2;
                arrn4 = arrn4[2] < arrn[2] ? arrn4 : arrn;
                this.hHX = arrn4[0];
                this.hHY = arrn4[1];
                this.controlH = true;
                f = (float)(this.H - this.hHY) / (float)(this.hHX - this.startx) * 371.0f / 840.0f;
                this.controlValueH = this.controlValue = (double)EllinghamDiagram.exp((float)(1000.0f * (f - 0.1117f) / -16.628944f));
            }
            if (this.control == 'C') {
                arrn3 = this.ClosestOnLine(n2, this.CY1, this.CX2, this.CY2);
                arrn2 = this.ClosestOnLine(this.CX2, this.CY2, this.CX3, this.CY3);
                arrn = this.ClosestOnLine(this.CX3, this.CY3, n2, this.CY4);
                int[] arrn5 = arrn3[2] < arrn2[2] ? arrn3 : arrn2;
                arrn5 = arrn5[2] < arrn[2] ? arrn5 : arrn;
                this.hCX = arrn5[0];
                this.hCY = arrn5[1];
                this.controlC = true;
                f = (float)(this.C - this.hCY) / (float)(this.hCX - this.startx) * 371.0f / 840.0f;
                this.controlValueC = this.controlValue = (double)EllinghamDiagram.exp((float)(1000.0f * (f - 0.1736f) / -16.628944f));
            }
            if (this.controlO || this.controlH || this.controlC) {
                this.updated = true;
                this.findIntersects();
            }
        }

        public void findIntersects() {
            float[] arrf = new float[]{this.hOX, this.hHX, this.hCX};
            float[] arrf2 = new float[]{this.hOY, this.hHY, this.hCY};
            char[] arrc = new char[]{'O', 'H', 'C'};
            boolean[] arrbl = new boolean[]{this.controlO, this.controlH, this.controlC};
            this.intersectX.clear();
            this.intersectY.clear();
            int n = 0;
            while (n < 3) {
                char c = arrc[n];
                if (arrbl[n]) {
                    float f = -273.16f;
                    float f2 = c == 'O' ? 0.0f : (float)(c == 'H' ? -495 : -566);
                    float f3 = 6.469f * (arrf[n] - (float)this.padding[3] - (float)this.plot.padding[3]);
                    float f4 = 2.857f * ((float)(this.padding[0] + this.plot.padding[0]) - arrf2[n]);
                    int n2 = this.plot.data.size();
                    int n3 = 0;
                    while (n3 < n2) {
                        Plot.Data data = this.plot.data.get(n3);
                        if (!data.hidden) {
                            int n4 = 0;
                            while (n4 < data.Xdata.length - 1) {
                                float[] arrf3 = Geometry.intersection(data.Xdata[n4], data.Ydata[n4], data.Xdata[n4 + 1], data.Ydata[n4 + 1], f, f2, f3, f4);
                                if (arrf3 != null) {
                                    this.intersectX.add(Float.valueOf(arrf3[0]));
                                    this.intersectY.add(Float.valueOf(arrf3[1]));
                                }
                                ++n4;
                            }
                        }
                        ++n3;
                    }
                }
                ++n;
            }
        }

        public void mouseReleased() {
            this.dragging = false;
        }

        public String getTags() {
            return this.plot.GetTags();
        }

        public void setHidden(int n, boolean bl) {
            this.plot.SetHidden(n, bl);
            this.findIntersects();
            this.updated = true;
        }

        public boolean getHidden(int n) {
            if (n < 0 || n >= this.plot.data.size()) {
                return false;
            }
            return this.plot.data.get((int)n).hidden;
        }

        public void mouseMoved() {
            int n = this.labelIndex;
            int n2 = 0;
            while (n2 < this.intersectX.size()) {
                float f = this.intersectX.get(n2).floatValue();
                float f2 = this.intersectY.get(n2).floatValue();
                float f3 = f / 6.469f + (float)this.padding[3] + (float)this.plot.padding[3];
                float f4 = (float)(this.padding[0] + this.plot.padding[0]) - f2 / 2.857f;
                if (EllinghamDiagram.abs((float)((float)EllinghamDiagram.this.mouseX - f3)) <= 5.0f && EllinghamDiagram.abs((float)((float)EllinghamDiagram.this.mouseY - f4)) <= 5.0f) {
                    this.labelIndex = n2;
                    if (n != this.labelIndex) {
                        this.updated = true;
                    }
                    return;
                }
                ++n2;
            }
            if (n != this.labelIndex) {
                this.updated = true;
            }
        }

        public double getValueO() {
            return this.controlValueO;
        }

        public double getValueH() {
            return this.controlValueH;
        }

        public double getValueC() {
            return this.controlValueC;
        }
    }

}

