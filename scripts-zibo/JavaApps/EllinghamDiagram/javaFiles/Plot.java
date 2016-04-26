import java.util.List;
import java.util.Iterator;
import java.awt.Font;
import processing.core.PFont;
import java.util.ArrayList;
import processing.core.PApplet;

// 
// Decompiled by Procyon v0.5.30
// 

public class Plot
{
    BoundMode boundMode;
    PApplet applet;
    LineType lineType;
    public static final LineType Circled;
    public static final LineType Normal;
    public int xpos;
    public int ypos;
    public int xsize;
    public int ysize;
    boolean animating;
    int duration;
    int[] destination;
    int frame;
    float xmax;
    float xmin;
    float ymax;
    float ymin;
    public int[] padding;
    public int bgColor;
    public int bgColorPlot;
    public ArrayList<Data> data;
    float[] Xgrid;
    float[] Ygrid;
    public PFont font;
    public PFont smallFont;
    protected int xexp;
    protected int yexp;
    protected int exp;
    String xTitle;
    String yTitle;
    String title;
    boolean updated;
    boolean displayAxis;
    
    public Plot(final PApplet applet, final int xpos, final int ypos, final int xsize, final int ysize) {
        this.boundMode = BoundMode.Independent;
        this.xpos = 0;
        this.ypos = 0;
        this.xsize = 200;
        this.ysize = 200;
        this.duration = 0;
        this.padding = new int[] { 0, 0, 0, 0 };
        this.bgColor = -3618616;
        this.bgColorPlot = -1;
        this.data = new ArrayList<Data>();
        this.applet = applet;
        this.font = new PFont(new Font("Helvetica", 0, 12), true);
        this.smallFont = new PFont(new Font("Helvetica", 0, 11), true);
        this.xpos = xpos;
        this.ypos = ypos;
        this.xsize = xsize;
        this.ysize = ysize;
        this.updated = true;
    }
    
    public Plot(final PApplet applet, final float[] array, final float[] array2, final int xpos, final int ypos, final int xsize, final int ysize) {
        this.boundMode = BoundMode.Independent;
        this.xpos = 0;
        this.ypos = 0;
        this.xsize = 200;
        this.ysize = 200;
        this.duration = 0;
        this.padding = new int[] { 0, 0, 0, 0 };
        this.bgColor = -3618616;
        this.bgColorPlot = -1;
        this.data = new ArrayList<Data>();
        this.applet = applet;
        this.font = applet.loadFont("Helvetica-12.vlw");
        this.smallFont = applet.loadFont("Helvetica-11.vlw");
        this.xpos = xpos;
        this.ypos = ypos;
        this.xsize = xsize;
        this.ysize = ysize;
        this.AddData(array, array2);
        this.updated = true;
    }
    
    public void AddData(float[] array, float[] array2) {
        if (array == null) {
            array = new float[0];
        }
        if (array2 == null) {
            array2 = new float[0];
        }
        assert array.length == array2.length : "data length does not match";
        this.data.add(new Data(array, array2));
        this.UpdateBounds();
    }
    
    public void AddTag(final String tag) {
        final int n = this.data.size() - 1;
        assert n >= 0 : "No data in the plot";
        this.data.get(n).tag = tag;
    }
    
    public void SetTag(final int n, final String tag) {
        assert n >= 0 && n < this.data.size() : "invalid index";
        this.data.get(n).tag = tag;
    }
    
    public void SetHidden(final int n, final boolean hidden) {
        assert n >= 0 && n < this.data.size() : "invalid index";
        this.data.get(n).hidden = hidden;
        this.updated = true;
    }
    
    public void HideAll() {
        final Iterator<Data> iterator = this.data.iterator();
        while (iterator.hasNext()) {
            iterator.next().hidden = true;
        }
    }
    
    public void ShowAll() {
        final Iterator<Data> iterator = this.data.iterator();
        while (iterator.hasNext()) {
            iterator.next().hidden = false;
        }
    }
    
    public String GetTags() {
        final StringBuffer sb = new StringBuffer();
        final Iterator<Data> iterator = this.data.iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next().tag);
            sb.append("\n");
        }
        return sb.toString();
    }
    
    public int size() {
        return this.data.size();
    }
    
    protected void UpdateBounds() {
        float n = Float.MIN_VALUE;
        float n2 = Float.MAX_VALUE;
        float n3 = Float.MIN_VALUE;
        float n4 = Float.MAX_VALUE;
        if (this.boundMode != BoundMode.FixedAxis) {
            for (final Data data : this.data) {
                if (this.boundMode == BoundMode.Independent) {
                    n = Float.MIN_VALUE;
                    n3 = Float.MIN_VALUE;
                    n2 = Float.MAX_VALUE;
                    n4 = Float.MAX_VALUE;
                }
                if (data.X == null || data.Y == null) {
                    for (final float n5 : data.Xdata) {
                        if (n5 > n) {
                            n = n5;
                        }
                        if (n5 < n2) {
                            n2 = n5;
                        }
                    }
                    for (final float n6 : data.Ydata) {
                        if (n6 > n3) {
                            n3 = n6;
                        }
                        if (n6 < n4) {
                            n4 = n6;
                        }
                    }
                }
                else {
                    for (final float floatValue : data.X) {
                        if (floatValue > n) {
                            n = floatValue;
                        }
                        if (floatValue < n2) {
                            n2 = floatValue;
                        }
                    }
                    for (final float floatValue2 : data.Y) {
                        if (floatValue2 > n3) {
                            n3 = floatValue2;
                        }
                        if (floatValue2 < n4) {
                            n4 = floatValue2;
                        }
                    }
                }
                if (this.boundMode == BoundMode.Independent) {
                    data.Xmax = n;
                    data.Xmin = n2;
                    data.Ymax = n3;
                    data.Ymin = n4;
                }
            }
            this.xmin = n2;
            this.xmax = n;
            this.ymin = n4;
            this.ymax = n3;
        }
        if (this.Xgrid != null || this.Ygrid != null) {
            this.UpdateGrid();
        }
        this.updated = true;
    }
    
    public static float[] toFloat(final Object[] array) {
        final float[] array2 = new float[array.length];
        for (int i = 0; i < array.length; ++i) {
            assert array[i] instanceof Float : "invalid data";
            array2[i] = (float)array[i];
        }
        return array2;
    }
    
    public void UpdateData(final int n, float[] xdata, float[] ydata) {
        assert n >= 0 && n < this.data.size() : "invalid plot index - index should be less than " + this.data.size();
        if (xdata == null) {
            xdata = new float[0];
        }
        if (ydata == null) {
            ydata = new float[0];
        }
        assert xdata.length == ydata.length : "data length does not match";
        final Data data = this.data.get(n);
        data.Xdata = xdata;
        data.Ydata = ydata;
        this.UpdateBounds();
        this.updated = true;
    }
    
    public void UpdateData(final int n, final List<Float> x, final List<Float> y) {
        assert n >= 0 && n < this.data.size() : "invalid plot index - index should be less than " + this.data.size();
        assert x != null && y != null : "invalid data";
        assert x.size() == y.size() : "data length does not match";
        final Data data = this.data.get(n);
        data.X = x;
        data.Y = y;
        this.UpdateBounds();
        this.updated = true;
    }
    
    protected boolean inside(final float n, final float n2) {
        return n >= this.xpos + this.padding[3] && n <= this.xpos + this.xsize - this.padding[1] && n2 >= this.ypos + this.padding[0] && n2 <= this.ypos + this.ysize - this.padding[2];
    }
    
    protected float[] ClampLine(final float[] array, final int xpos, final int ypos, final int xsize, final int ysize) {
        final int[] array2 = { this.xpos, this.ypos, this.xsize, this.ysize };
        this.xpos = xpos;
        this.ypos = ypos;
        this.xsize = xsize;
        this.ysize = ysize;
        final float[] clampLine = this.ClampLine(array);
        this.xpos = array2[0];
        this.ypos = array2[1];
        this.xsize = array2[2];
        this.ysize = array2[3];
        return clampLine;
    }
    
    protected float[] ClampLine(final float[] array) {
        assert array != null : "invalid line";
        assert array.length == 4 : "invalide line";
        final boolean inside = this.inside(array[0], array[1]);
        final boolean inside2 = this.inside(array[2], array[3]);
        if (!inside && !inside2) {
            return null;
        }
        if (inside && inside2) {
            return array;
        }
        if (inside && !inside2) {
            final float n = array[0];
            array[0] = array[2];
            array[2] = n;
            final float n2 = array[1];
            array[1] = array[3];
            array[3] = n2;
        }
        final float n3 = this.xpos + this.padding[3];
        final float n4 = this.xpos + this.xsize - this.padding[1];
        final float n5 = this.ypos + this.padding[0];
        final float n6 = this.ypos + this.ysize - this.padding[2];
        final float n7 = array[2] - array[0];
        final float n8 = array[3] - array[1];
        float n9 = array[0] * array[3] - array[1] * array[2];
        if (n9 >= 0.0f && n9 < 1.0E-10f) {
            n9 = 1.0E-10f;
        }
        if (n9 < 0.0f && n9 > -1.0E-10f) {
            n9 = -1.0E-10f;
        }
        final float n10 = (array[3] - array[1]) / n9;
        final float n11 = (array[0] - array[2]) / n9;
        if (array[0] < n3) {
            final float n12 = (n3 - array[0]) / n7;
            array[0] = n3;
            array[1] = array[1] * (1.0f - n12) + array[3] * n12;
        }
        if (array[0] > n4) {
            final float n13 = (n4 - array[0]) / n7;
            array[0] = this.xpos + this.xsize - this.padding[1];
            array[1] = array[1] * (1.0f - n13) + array[3] * n13;
        }
        final float n14 = array[2] - array[0];
        final float n15 = array[3] - array[1];
        float n16 = array[0] * array[3] - array[1] * array[2];
        if (n16 < 1.0E-10f) {
            n16 = 1.0E-10f;
        }
        final float n17 = (array[3] - array[1]) / n16;
        final float n18 = (array[0] - array[2]) / n16;
        if (array[1] < n5) {
            final float n19 = (n5 - array[1]) / n15;
            array[0] = array[0] * (1.0f - n19) + array[2] * n19;
            array[1] = n5;
        }
        if (array[1] > n6) {
            final float n20 = (n6 - array[1]) / n15;
            array[0] = array[0] * (1.0f - n20) + array[2] * n20;
            array[1] = n6;
        }
        return array;
    }
    
    public void ForcePlot() {
        this.updated = true;
        this.ScribePlot();
    }
    
    public boolean ScribePlot() {
        if (!this.updated) {
            return false;
        }
        this.UpdateBounds();
        int xpos = this.xpos;
        int ypos = this.ypos;
        int xsize = this.xsize;
        int ysize = this.ysize;
        if (this.animating) {
            if (this.frame >= this.duration) {
                this.animating = false;
                final int xpos2 = this.destination[0];
                this.xpos = xpos2;
                xpos = xpos2;
                final int ypos2 = this.destination[1];
                this.ypos = ypos2;
                ypos = ypos2;
                final int xsize2 = this.destination[2];
                this.xsize = xsize2;
                xsize = xsize2;
                final int ysize2 = this.destination[3];
                this.ysize = ysize2;
                ysize = ysize2;
            }
            else {
                final float n = this.frame / this.duration;
                xpos = (int)(xpos * (1.0f - n) + this.destination[0] * n);
                ypos = (int)(ypos * (1.0f - n) + this.destination[1] * n);
                xsize = (int)(xsize * (1.0f - n) + this.destination[2] * n);
                ysize = (int)(ysize * (1.0f - n) + this.destination[3] * n);
                ++this.frame;
            }
        }
        this.applet.noStroke();
        this.applet.fill(this.bgColor);
        this.applet.rect((float)xpos, (float)ypos, (float)xsize, (float)ysize);
        this.applet.stroke(0);
        this.applet.fill(this.bgColorPlot);
        this.applet.rect((float)(xpos + this.padding[3]), (float)(ypos + this.padding[0]), (float)(xsize - this.padding[1] - this.padding[3]), (float)(ysize - this.padding[0] - this.padding[2]));
        float n2 = this.xmax;
        float n3 = this.xmin;
        float n4 = this.ymax;
        float n5 = this.ymin;
        this.applet.fill(0);
        this.applet.ellipseMode(3);
        for (final Data data : this.data) {
            if (data.hidden) {
                continue;
            }
            this.applet.stroke((float)data.PlotR, (float)data.PlotG, (float)data.PlotB);
            this.applet.fill((float)data.PlotR, (float)data.PlotG, (float)data.PlotB);
            final float[] xdata = data.Xdata;
            final float[] ydata = data.Ydata;
            if ((data.X == null || data.Y == null) && xdata.length <= 1) {
                continue;
            }
            if (data.X != null && data.X.size() <= 1) {
                continue;
            }
            if (this.boundMode == BoundMode.Independent) {
                n2 = data.Xmax;
                n3 = data.Xmin;
                n4 = data.Ymax;
                n5 = data.Ymin;
            }
            final float n6 = (xsize - this.padding[1] - this.padding[3]) / (n2 - n3);
            final float n7 = (ysize - this.padding[0] - this.padding[2]) / (n4 - n5);
            final float n8 = xpos + this.padding[3];
            final float n9 = ypos + this.padding[0];
            if (data.X == null || data.Y == null) {
                float n10 = (xdata[0] - n3) * n6 + n8;
                float n11 = (n4 - ydata[0]) * n7 + n9;
                final int n12 = (xdata.length - 1) / (((xdata.length > data.numpoints) ? data.numpoints : xdata.length) - 1);
                int n13 = n12 - data.offset % n12;
                while (true) {
                    if (n13 >= xdata.length) {
                        n13 = xdata.length - 1;
                    }
                    if (data.gradation) {
                        final int color = data.getColor(n13);
                        this.applet.stroke(color);
                        this.applet.fill(color);
                    }
                    final float n14 = (xdata[n13] - n3) * n6 + n8;
                    final float n15 = (n4 - ydata[n13]) * n7 + n9;
                    final float[] clampLine = this.ClampLine(new float[] { n10, n11, n14, n15 }, xpos, ypos, xsize, ysize);
                    if (clampLine != null) {
                        this.applet.line(clampLine[0], clampLine[1], clampLine[2], clampLine[3]);
                    }
                    n10 = n14;
                    n11 = n15;
                    if (n13 < xdata.length - 1 && this.lineType == LineType.Circled && this.inside(n14, n15)) {
                        this.applet.ellipse(n14, n15, 4.0f, 4.0f);
                    }
                    if (n13 == xdata.length - 1) {
                        break;
                    }
                    n13 += n12;
                }
            }
            else {
                float n16 = (data.X.get(0) - n3) * n6 + n8;
                float n17 = (n4 - data.Y.get(0)) * n7 + n9;
                final int n18 = (data.X.size() - 1) / (((data.X.size() > data.numpoints) ? data.numpoints : data.X.size()) - 1);
                int n19 = n18 - data.offset % n18;
                while (true) {
                    if (n19 >= data.X.size()) {
                        n19 = data.X.size() - 1;
                    }
                    final float floatValue = data.X.get(n19);
                    final float floatValue2 = data.Y.get(n19);
                    if (data.gradation) {
                        final int color2 = data.getColor(n19);
                        this.applet.stroke(color2);
                        this.applet.fill(color2);
                    }
                    final float n20 = (floatValue - n3) * n6 + n8;
                    final float n21 = (n4 - floatValue2) * n7 + n9;
                    final float[] clampLine2 = this.ClampLine(new float[] { n16, n17, n20, n21 }, xpos, ypos, xsize, ysize);
                    if (clampLine2 != null) {
                        this.applet.line(clampLine2[0], clampLine2[1], clampLine2[2], clampLine2[3]);
                    }
                    n16 = n20;
                    n17 = n21;
                    if (this.lineType == LineType.Circled && n19 < data.X.size() - 1 && this.inside(n20, n21)) {
                        this.applet.ellipse(n20, n21, 4.0f, 4.0f);
                    }
                    if (n19 == data.X.size() - 1) {
                        break;
                    }
                    n19 += n18;
                }
            }
        }
        this.applet.stroke(0);
        this.applet.fill(0);
        if (this.font != null) {
            this.applet.textFont(this.font, 12.0f);
        }
        if ((this.Xgrid != null || this.Ygrid != null) && (this.data.size() > 0 || this.boundMode == BoundMode.FixedAxis)) {
            this.applet.textFont(this.font, 12.0f);
            if (this.boundMode == BoundMode.Independent) {
                final Data data2 = this.data.get(0);
                n2 = data2.Xmax;
                n3 = data2.Xmin;
                n4 = data2.Ymax;
                n5 = data2.Ymin;
            }
            if (this.Xgrid != null) {
                final float n22 = xpos + this.padding[3];
                final float n23 = (xsize - this.padding[1] - this.padding[3]) / (n2 - n3);
                final float n24 = ypos + ysize - this.padding[2];
                for (int i = 0; i < this.Xgrid.length; ++i) {
                    final float n25 = (this.Xgrid[i] - n3) * n23 + n22;
                    this.applet.line(n25, n24, n25, n24 - 5.0f);
                    String s;
                    if (this.xexp > 0 && this.xexp <= 3) {
                        s = String.format("%d", Math.round(this.Xgrid[i]));
                    }
                    else {
                        s = String.format("%.1f", this.Xgrid[i] / pow(10.0f, this.xexp));
                    }
                    this.applet.textAlign(3, 101);
                    this.applet.text(s, n25 - 18.0f, n24 + 5.0f, 36.0f, 15.0f);
                }
                if (this.xexp < 0 || this.xexp > 3) {
                    this.applet.textAlign(39, 101);
                    this.applet.text("*10", (float)(xpos + xsize - this.padding[1] - 20), (float)(ypos + ysize - this.padding[2] + 25), 20.0f, 25.0f);
                    this.applet.textAlign(37, 101);
                    this.applet.text(String.format("%d", this.xexp), (float)(xpos + xsize - this.padding[1]), (float)(ypos + ysize - this.padding[2] + 20), 25.0f, 25.0f);
                }
            }
            if (this.Ygrid != null) {
                final float n26 = ypos + this.padding[0];
                final float n27 = (ysize - this.padding[0] - this.padding[2]) / (n4 - n5);
                final float n28 = xpos + this.padding[3];
                for (int j = 0; j < this.Ygrid.length; ++j) {
                    final float n29 = (n4 - this.Ygrid[j]) * n27 + n26;
                    this.applet.line(n28, n29, n28 + 5.0f, n29);
                    String s2;
                    if (this.yexp > 0 && this.yexp <= 4) {
                        s2 = String.format("%d", Math.round(this.Ygrid[j]));
                    }
                    else {
                        s2 = String.format("%.1f", this.Ygrid[j] / pow(10.0f, this.yexp));
                    }
                    this.applet.textAlign(39, 3);
                    this.applet.text(s2, n28 - 40.0f, n29 - 10.0f, 36.0f, 20.0f);
                }
                if (this.yexp < 0 || this.yexp > 4) {
                    this.applet.textAlign(39, 102);
                    this.applet.text("*10", (float)(xpos + this.padding[3] - 60), (float)(ypos + 5), 25.0f, 25.0f);
                    this.applet.textAlign(37, 102);
                    this.applet.text(String.format("%d", this.yexp), (float)(xpos + this.padding[3] - 35), (float)ypos, 25.0f, 25.0f);
                }
            }
        }
        this.applet.textAlign(3);
        if (this.title != null) {
            this.applet.text(this.title, (float)(xpos + this.padding[3]), (float)(ypos + 5), (float)(xsize - this.padding[1] - this.padding[3]), (float)(this.padding[0] - 5));
        }
        if (this.xTitle != null) {
            this.applet.text(this.xTitle, (float)(xpos + this.padding[3]), (float)(ypos + ysize - this.padding[2] + 25), (float)(xsize - this.padding[1] - this.padding[3]), (float)(this.padding[2] - 25));
        }
        if (this.yTitle != null) {
            this.applet.pushMatrix();
            this.applet.rotate(-1.5707964f);
            this.applet.translate((float)(-ypos * 2 - ysize), 0.0f);
            this.applet.text(this.yTitle, (float)(ypos + this.padding[2]), (float)(xpos + 3), (float)(ysize - this.padding[0] - this.padding[2]), 25.0f);
            this.applet.popMatrix();
        }
        this.updated = false;
        return true;
    }
    
    public void Color(final int plotR, final int plotG, final int plotB) {
        final int n = this.data.size() - 1;
        assert n >= 0 : "No data in the plot";
        final Data data = this.data.get(n);
        data.PlotR = plotR;
        data.PlotG = plotG;
        data.PlotB = plotB;
        this.updated = true;
    }
    
    public void Color(final int n, final int plotR, final int plotG, final int plotB) {
        assert n >= 0 && n < this.data.size() : "Invalid index - should be less than " + this.data.size();
        final Data data = this.data.get(n);
        data.PlotR = plotR;
        data.PlotG = plotG;
        data.PlotB = plotB;
        this.updated = true;
    }
    
    public void Gradation(final int gradR, final int gradG, final int gradB) {
        final int n = this.data.size() - 1;
        assert n >= 0 : "No data in the plot";
        final Data data = this.data.get(n);
        data.gradation = true;
        data.GradR = gradR;
        data.GradG = gradG;
        data.GradB = gradB;
        this.updated = true;
    }
    
    public void Gradation(final int n, final int gradR, final int gradG, final int gradB) {
        assert n >= 0 && n < this.data.size() : "Invalid index - should be less than " + this.data.size();
        final Data data = this.data.get(n);
        data.gradation = true;
        data.GradR = gradR;
        data.GradG = gradG;
        data.GradB = gradB;
        this.updated = true;
    }
    
    public static float pow(float n, int i) {
        float n2 = 1.0f;
        if (i < 0) {
            return 1.0f / pow(n, -i);
        }
        while (i != 0) {
            if ((i & 0x1) != 0x0) {
                n2 *= n;
            }
            i >>= 1;
            n *= n;
        }
        return n2;
    }
    
    protected float[] MakeGrid(final float n, final float n2) {
        final float n3 = n2 - n;
        if (n3 <= 1.0E-20) {
            return null;
        }
        final float n4 = (float)(Math.log(n3) / Math.log(10.0));
        this.exp = (int)Math.floor(n4);
        final float n5 = n4 - this.exp;
        float pow = pow(10.0f, this.exp);
        if (n5 < 0.2f) {
            pow *= 0.2f;
        }
        else if (n5 < 0.5f) {
            pow *= 0.5f;
        }
        float n6 = (float)Math.floor(n / pow) * pow;
        final float n7 = (float)Math.ceil(n2 / pow) * pow;
        int n8 = Math.round((n7 - n6) / pow) - 1;
        if (n6 >= n - n3 * 1.0E-4) {
            ++n8;
        }
        if (n7 <= n2 + n3 * 1.0E-4) {
            ++n8;
        }
        final float[] array = new float[n8];
        int i = 0;
        if (n6 >= n - n3 * 1.0E-4) {
            array[i++] = n;
        }
        else {
            n6 += pow;
        }
        while (i < n8) {
            array[i] = n6 + i * pow;
            ++i;
        }
        if (n7 <= n2 + n3 * 1.0E-4) {
            array[n8 - 1] = n2;
        }
        return array;
    }
    
    public void ShowAxis() {
        this.displayAxis = true;
        this.UpdatePaddings();
        this.UpdateGrid();
    }
    
    protected void UpdatePaddings() {
        final int[] padding = { 0, 0, 0, 0 };
        if (this.displayAxis) {
            final int[] array = padding;
            final int n = 0;
            array[n] += 10;
            final int[] array2 = padding;
            final int n2 = 1;
            array2[n2] += 20;
            final int[] array3 = padding;
            final int n3 = 2;
            array3[n3] += 40;
            final int[] array4 = padding;
            final int n4 = 3;
            array4[n4] += 40;
        }
        if (this.title != null) {
            final int[] array5 = padding;
            final int n5 = 0;
            array5[n5] += 15;
        }
        if (this.xTitle != null) {
            final int[] array6 = padding;
            final int n6 = 2;
            array6[n6] += 15;
        }
        if (this.yTitle != null) {
            final int[] array7 = padding;
            final int n7 = 3;
            array7[n7] += 15;
        }
        this.padding = padding;
    }
    
    public void UpdateGrid() {
        float n = this.xmax;
        float n2 = this.xmin;
        float n3 = this.ymax;
        float n4 = this.ymin;
        if (this.data.size() == 0 && this.boundMode != BoundMode.FixedAxis) {
            return;
        }
        if (this.boundMode == BoundMode.Independent) {
            final Data data = this.data.get(0);
            n = data.Xmax;
            n2 = data.Xmin;
            n3 = data.Ymax;
            n4 = data.Ymin;
        }
        this.Xgrid = this.MakeGrid(n2, n);
        this.xexp = this.exp;
        this.Ygrid = this.MakeGrid(n4, n3);
        this.yexp = this.exp;
        this.updated = true;
    }
    
    public void SetFont(final PFont font) {
        this.font = font;
        this.updated = true;
    }
    
    public void SetAxis(final float xmin, final float xmax, final float ymin, final float ymax) {
        assert xmin <= xmax && ymin <= ymax : "invalid values";
        this.boundMode = BoundMode.FixedAxis;
        this.ShowAxis();
        this.xmin = xmin;
        this.xmax = xmax;
        this.ymin = ymin;
        this.ymax = ymax;
        this.UpdateGrid();
    }
    
    public void SetLineType(final LineType lineType) {
        this.lineType = lineType;
        this.updated = true;
    }
    
    public void SetXTitle(final String xTitle) {
        this.xTitle = xTitle;
        this.UpdatePaddings();
        this.updated = true;
    }
    
    public void SetYTitle(final String yTitle) {
        this.yTitle = yTitle;
        this.UpdatePaddings();
        this.updated = true;
    }
    
    public void SetTitle(final String title) {
        this.title = title;
        this.UpdatePaddings();
        this.updated = true;
    }
    
    public Data GetData(final int n) {
        return this.data.get(n);
    }
    
    public void Animate(final int[] destination, final int duration) {
        this.destination = destination;
        this.duration = duration;
        this.frame = 0;
        this.animating = true;
    }
    
    public void SetNumPoints(final int numpoints) {
        final Iterator<Data> iterator = this.data.iterator();
        while (iterator.hasNext()) {
            iterator.next().numpoints = numpoints;
        }
    }
    
    static {
        Circled = LineType.Circled;
        Normal = LineType.Normal;
    }
    
    public class Data
    {
        float Xmax;
        float Xmin;
        float Ymax;
        float Ymin;
        float[] Xdata;
        float[] Ydata;
        List<Float> X;
        List<Float> Y;
        int PlotR;
        int PlotG;
        int PlotB;
        int GradR;
        int GradG;
        int GradB;
        String tag;
        boolean hidden;
        boolean gradation;
        int numpoints;
        int offset;
        
        public Data(final float[] xdata, final float[] ydata) {
            this.numpoints = 1000;
            this.offset = 0;
            this.Xdata = xdata;
            this.Ydata = ydata;
            final boolean plotR = false;
            this.PlotB = (plotR ? 1 : 0);
            this.PlotG = (plotR ? 1 : 0);
            this.PlotR = (plotR ? 1 : 0);
            final boolean gradR = false;
            this.GradB = (gradR ? 1 : 0);
            this.GradG = (gradR ? 1 : 0);
            this.GradR = (gradR ? 1 : 0);
            final List<Float> list = null;
            this.Y = list;
            this.X = list;
            final float n = Float.MIN_VALUE;
            this.Ymax = n;
            this.Xmax = n;
            final float n2 = Float.MAX_VALUE;
            this.Ymin = n2;
            this.Xmin = n2;
            this.tag = null;
            this.hidden = false;
            this.gradation = false;
        }
        
        public int getColor(final int n) {
            int n2 = (this.X == null) ? this.Xdata.length : this.X.size();
            if (n2 == 1) {
                n2 = 2;
            }
            final float n3 = n / (n2 - 1);
            return 0xFF000000 | (int)(this.GradR * (1.0f - n3) + this.PlotR * n3) << 16 | (int)(this.GradG * (1.0f - n3) + this.PlotG * n3) << 8 | (int)(this.GradB * (1.0f - n3) + this.PlotB * n3);
        }
    }
    
    public enum LineType
    {
        Normal, 
        Circled;
    }
    
    public enum BoundMode
    {
        Independent, 
        SameScale, 
        FixedAxis;
    }
}
