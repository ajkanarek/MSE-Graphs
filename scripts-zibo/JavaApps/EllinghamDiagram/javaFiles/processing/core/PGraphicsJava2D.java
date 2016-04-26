// 
// Decompiled by Procyon v0.5.30
// 

package processing.core;

import java.awt.image.WritableRaster;
import java.util.Arrays;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.FontMetrics;
import java.awt.Font;
import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.awt.Paint;
import java.awt.Color;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.Graphics2D;

public class PGraphicsJava2D extends PGraphics
{
    public Graphics2D g2;
    GeneralPath gpath;
    boolean breakShape;
    float[] curveCoordX;
    float[] curveCoordY;
    float[] curveDrawX;
    float[] curveDrawY;
    int transformCount;
    AffineTransform[] transformStack;
    double[] transform;
    Line2D.Float line;
    Ellipse2D.Float ellipse;
    Rectangle2D.Float rect;
    Arc2D.Float arc;
    protected Color tintColorObject;
    protected Color fillColorObject;
    public boolean fillGradient;
    public Paint fillGradientObject;
    protected Color strokeColorObject;
    public boolean strokeGradient;
    public Paint strokeGradientObject;
    int[] clearPixels;
    static int[] getset;
    
    static {
        PGraphicsJava2D.getset = new int[1];
    }
    
    public PGraphicsJava2D() {
        this.transformStack = new AffineTransform[32];
        this.transform = new double[6];
        this.line = new Line2D.Float();
        this.ellipse = new Ellipse2D.Float();
        this.rect = new Rectangle2D.Float();
        this.arc = new Arc2D.Float();
    }
    
    public void setSize(final int iwidth, final int iheight) {
        this.width = iwidth;
        this.height = iheight;
        this.width1 = this.width - 1;
        this.height1 = this.height - 1;
        this.allocate();
        this.reapplySettings();
    }
    
    protected void allocate() {
        this.image = new BufferedImage(this.width, this.height, 2);
        this.g2 = (Graphics2D)this.image.getGraphics();
    }
    
    public boolean canDraw() {
        return true;
    }
    
    public void beginDraw() {
        this.checkSettings();
        this.resetMatrix();
        this.vertexCount = 0;
    }
    
    public void endDraw() {
        if (!this.primarySurface) {
            this.loadPixels();
        }
        this.modified = true;
    }
    
    public void beginShape(final int kind) {
        this.shape = kind;
        this.vertexCount = 0;
        this.curveVertexCount = 0;
        this.gpath = null;
    }
    
    public void texture(final PImage image) {
        PGraphics.showMethodWarning("texture");
    }
    
    public void vertex(final float x, final float y) {
        this.curveVertexCount = 0;
        if (this.vertexCount == this.vertices.length) {
            final float[][] temp = new float[this.vertexCount << 1][36];
            System.arraycopy(this.vertices, 0, temp, 0, this.vertexCount);
            this.vertices = temp;
        }
        this.vertices[this.vertexCount][0] = x;
        this.vertices[this.vertexCount][1] = y;
        ++this.vertexCount;
        switch (this.shape) {
            case 2: {
                this.point(x, y);
                break;
            }
            case 4: {
                if (this.vertexCount % 2 == 0) {
                    this.line(this.vertices[this.vertexCount - 2][0], this.vertices[this.vertexCount - 2][1], x, y);
                    break;
                }
                break;
            }
            case 9: {
                if (this.vertexCount % 3 == 0) {
                    this.triangle(this.vertices[this.vertexCount - 3][0], this.vertices[this.vertexCount - 3][1], this.vertices[this.vertexCount - 2][0], this.vertices[this.vertexCount - 2][1], x, y);
                    break;
                }
                break;
            }
            case 10: {
                if (this.vertexCount >= 3) {
                    this.triangle(this.vertices[this.vertexCount - 2][0], this.vertices[this.vertexCount - 2][1], this.vertices[this.vertexCount - 1][0], this.vertices[this.vertexCount - 1][1], this.vertices[this.vertexCount - 3][0], this.vertices[this.vertexCount - 3][1]);
                    break;
                }
                break;
            }
            case 11: {
                if (this.vertexCount == 3) {
                    this.triangle(this.vertices[0][0], this.vertices[0][1], this.vertices[1][0], this.vertices[1][1], x, y);
                    break;
                }
                if (this.vertexCount > 3) {
                    (this.gpath = new GeneralPath()).moveTo(this.vertices[0][0], this.vertices[0][1]);
                    this.gpath.lineTo(this.vertices[this.vertexCount - 2][0], this.vertices[this.vertexCount - 2][1]);
                    this.gpath.lineTo(x, y);
                    this.drawShape(this.gpath);
                    break;
                }
                break;
            }
            case 16: {
                if (this.vertexCount % 4 == 0) {
                    this.quad(this.vertices[this.vertexCount - 4][0], this.vertices[this.vertexCount - 4][1], this.vertices[this.vertexCount - 3][0], this.vertices[this.vertexCount - 3][1], this.vertices[this.vertexCount - 2][0], this.vertices[this.vertexCount - 2][1], x, y);
                    break;
                }
                break;
            }
            case 17: {
                if (this.vertexCount >= 4 && this.vertexCount % 2 == 0) {
                    this.quad(this.vertices[this.vertexCount - 4][0], this.vertices[this.vertexCount - 4][1], this.vertices[this.vertexCount - 2][0], this.vertices[this.vertexCount - 2][1], x, y, this.vertices[this.vertexCount - 3][0], this.vertices[this.vertexCount - 3][1]);
                    break;
                }
                break;
            }
            case 20: {
                if (this.gpath == null) {
                    (this.gpath = new GeneralPath()).moveTo(x, y);
                    break;
                }
                if (this.breakShape) {
                    this.gpath.moveTo(x, y);
                    this.breakShape = false;
                    break;
                }
                this.gpath.lineTo(x, y);
                break;
            }
        }
    }
    
    public void vertex(final float x, final float y, final float z) {
        PGraphics.showDepthWarningXYZ("vertex");
    }
    
    public void vertex(final float x, final float y, final float u, final float v) {
        PGraphics.showVariationWarning("vertex(x, y, u, v)");
    }
    
    public void vertex(final float x, final float y, final float z, final float u, final float v) {
        PGraphics.showDepthWarningXYZ("vertex");
    }
    
    public void breakShape() {
        this.breakShape = true;
    }
    
    public void endShape(final int mode) {
        if (this.gpath != null && this.shape == 20) {
            if (mode == 2) {
                this.gpath.closePath();
            }
            this.drawShape(this.gpath);
        }
        this.shape = 0;
    }
    
    public void bezierVertex(final float x1, final float y1, final float x2, final float y2, final float x3, final float y3) {
        this.bezierVertexCheck();
        this.gpath.curveTo(x1, y1, x2, y2, x3, y3);
    }
    
    public void bezierVertex(final float x2, final float y2, final float z2, final float x3, final float y3, final float z3, final float x4, final float y4, final float z4) {
        PGraphics.showDepthWarningXYZ("bezierVertex");
    }
    
    protected void curveVertexCheck() {
        super.curveVertexCheck();
        if (this.curveCoordX == null) {
            this.curveCoordX = new float[4];
            this.curveCoordY = new float[4];
            this.curveDrawX = new float[4];
            this.curveDrawY = new float[4];
        }
    }
    
    protected void curveVertexSegment(final float x1, final float y1, final float x2, final float y2, final float x3, final float y3, final float x4, final float y4) {
        this.curveCoordX[0] = x1;
        this.curveCoordY[0] = y1;
        this.curveCoordX[1] = x2;
        this.curveCoordY[1] = y2;
        this.curveCoordX[2] = x3;
        this.curveCoordY[2] = y3;
        this.curveCoordX[3] = x4;
        this.curveCoordY[3] = y4;
        this.curveToBezierMatrix.mult(this.curveCoordX, this.curveDrawX);
        this.curveToBezierMatrix.mult(this.curveCoordY, this.curveDrawY);
        if (this.gpath == null) {
            (this.gpath = new GeneralPath()).moveTo(this.curveDrawX[0], this.curveDrawY[0]);
        }
        this.gpath.curveTo(this.curveDrawX[1], this.curveDrawY[1], this.curveDrawX[2], this.curveDrawY[2], this.curveDrawX[3], this.curveDrawY[3]);
    }
    
    public void curveVertex(final float x, final float y, final float z) {
        PGraphics.showDepthWarningXYZ("curveVertex");
    }
    
    public void point(final float x, final float y) {
        if (this.stroke) {
            this.line(x, y, x + 1.0E-4f, y + 1.0E-4f);
        }
    }
    
    public void line(final float x1, final float y1, final float x2, final float y2) {
        this.line.setLine(x1, y1, x2, y2);
        this.strokeShape(this.line);
    }
    
    public void triangle(final float x1, final float y1, final float x2, final float y2, final float x3, final float y3) {
        (this.gpath = new GeneralPath()).moveTo(x1, y1);
        this.gpath.lineTo(x2, y2);
        this.gpath.lineTo(x3, y3);
        this.gpath.closePath();
        this.drawShape(this.gpath);
    }
    
    public void quad(final float x1, final float y1, final float x2, final float y2, final float x3, final float y3, final float x4, final float y4) {
        final GeneralPath gp = new GeneralPath();
        gp.moveTo(x1, y1);
        gp.lineTo(x2, y2);
        gp.lineTo(x3, y3);
        gp.lineTo(x4, y4);
        gp.closePath();
        this.drawShape(gp);
    }
    
    protected void rectImpl(final float x1, final float y1, final float x2, final float y2) {
        this.rect.setFrame(x1, y1, x2 - x1, y2 - y1);
        this.drawShape(this.rect);
    }
    
    protected void ellipseImpl(final float x, final float y, final float w, final float h) {
        this.ellipse.setFrame(x, y, w, h);
        this.drawShape(this.ellipse);
    }
    
    protected void arcImpl(final float x, final float y, final float w, final float h, float start, float stop) {
        start = -start * 57.295776f;
        stop = -stop * 57.295776f;
        final float sweep = stop - start;
        if (this.fill) {
            this.arc.setArc(x, y, w, h, start, sweep, 2);
            this.fillShape(this.arc);
        }
        if (this.stroke) {
            this.arc.setArc(x, y, w, h, start, sweep, 0);
            this.strokeShape(this.arc);
        }
    }
    
    protected void fillShape(final Shape s) {
        if (this.fillGradient) {
            this.g2.setPaint(this.fillGradientObject);
            this.g2.fill(s);
        }
        else if (this.fill) {
            this.g2.setColor(this.fillColorObject);
            this.g2.fill(s);
        }
    }
    
    protected void strokeShape(final Shape s) {
        if (this.strokeGradient) {
            this.g2.setPaint(this.strokeGradientObject);
            this.g2.draw(s);
        }
        else if (this.stroke) {
            this.g2.setColor(this.strokeColorObject);
            this.g2.draw(s);
        }
    }
    
    protected void drawShape(final Shape s) {
        if (this.fillGradient) {
            this.g2.setPaint(this.fillGradientObject);
            this.g2.fill(s);
        }
        else if (this.fill) {
            this.g2.setColor(this.fillColorObject);
            this.g2.fill(s);
        }
        if (this.strokeGradient) {
            this.g2.setPaint(this.strokeGradientObject);
            this.g2.draw(s);
        }
        else if (this.stroke) {
            this.g2.setColor(this.strokeColorObject);
            this.g2.draw(s);
        }
    }
    
    public void box(final float w, final float h, final float d) {
        PGraphics.showMethodWarning("box");
    }
    
    public void sphere(final float r) {
        PGraphics.showMethodWarning("sphere");
    }
    
    public void bezierDetail(final int detail) {
    }
    
    public void curveDetail(final int detail) {
    }
    
    public void smooth() {
        this.smooth = true;
        this.g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        this.g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
    }
    
    public void noSmooth() {
        this.smooth = false;
        this.g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        this.g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
    }
    
    protected void imageImpl(final PImage who, final float x1, final float y1, final float x2, final float y2, final int u1, final int v1, final int u2, final int v2) {
        if (who.width <= 0 || who.height <= 0) {
            return;
        }
        if (who.getCache(this) == null) {
            who.setCache(this, new ImageCache(who));
            who.updatePixels();
            who.modified = true;
        }
        final ImageCache cash = (ImageCache)who.getCache(this);
        if ((this.tint && !cash.tinted) || (this.tint && cash.tintedColor != this.tintColor) || (!this.tint && cash.tinted)) {
            who.updatePixels();
        }
        if (who.modified) {
            cash.update(this.tint, this.tintColor);
            who.modified = false;
        }
        this.g2.drawImage(((ImageCache)who.getCache(this)).image, (int)x1, (int)y1, (int)x2, (int)y2, u1, v1, u2, v2, null);
    }
    
    public float textAscent() {
        if (this.textFont == null) {
            this.defaultFontOrDeath("textAscent");
        }
        final Font font = this.textFont.getFont();
        if (font == null) {
            return super.textAscent();
        }
        final FontMetrics metrics = this.parent.getFontMetrics(font);
        return metrics.getAscent();
    }
    
    public float textDescent() {
        if (this.textFont == null) {
            this.defaultFontOrDeath("textAscent");
        }
        final Font font = this.textFont.getFont();
        if (font == null) {
            return super.textDescent();
        }
        final FontMetrics metrics = this.parent.getFontMetrics(font);
        return metrics.getDescent();
    }
    
    protected boolean textModeCheck(final int mode) {
        return mode == 4 || mode == 256;
    }
    
    public void textSize(final float size) {
        if (this.textFont == null) {
            this.defaultFontOrDeath("textAscent", size);
        }
        final Font font = this.textFont.getFont();
        if (font != null) {
            final Font dfont = font.deriveFont(size);
            this.g2.setFont(dfont);
            this.textFont.setFont(dfont);
        }
        super.textSize(size);
    }
    
    protected float textWidthImpl(final char[] buffer, final int start, final int stop) {
        final Font font = this.textFont.getFont();
        if (font == null) {
            return super.textWidthImpl(buffer, start, stop);
        }
        final int length = stop - start;
        final FontMetrics metrics = this.g2.getFontMetrics(font);
        return metrics.charsWidth(buffer, start, length);
    }
    
    protected void textLineImpl(final char[] buffer, final int start, final int stop, final float x, final float y) {
        final Font font = this.textFont.getFont();
        if (font == null) {
            super.textLineImpl(buffer, start, stop, x, y);
            return;
        }
        Object antialias = this.g2.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
        if (antialias == null) {
            antialias = RenderingHints.VALUE_ANTIALIAS_DEFAULT;
        }
        this.g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, this.textFont.smooth ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
        this.g2.setColor(this.fillColorObject);
        final int length = stop - start;
        this.g2.drawChars(buffer, start, length, (int)(x + 0.5f), (int)(y + 0.5f));
        this.g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, antialias);
        this.textX = x + this.textWidthImpl(buffer, start, stop);
        this.textY = y;
        this.textZ = 0.0f;
    }
    
    public void pushMatrix() {
        if (this.transformCount == this.transformStack.length) {
            throw new RuntimeException("pushMatrix() cannot use push more than " + this.transformStack.length + " times");
        }
        this.transformStack[this.transformCount] = this.g2.getTransform();
        ++this.transformCount;
    }
    
    public void popMatrix() {
        if (this.transformCount == 0) {
            throw new RuntimeException("missing a popMatrix() to go with that pushMatrix()");
        }
        --this.transformCount;
        this.g2.setTransform(this.transformStack[this.transformCount]);
    }
    
    public void translate(final float tx, final float ty) {
        this.g2.translate(tx, ty);
    }
    
    public void rotate(final float angle) {
        this.g2.rotate(angle);
    }
    
    public void rotateX(final float angle) {
        PGraphics.showDepthWarning("rotateX");
    }
    
    public void rotateY(final float angle) {
        PGraphics.showDepthWarning("rotateY");
    }
    
    public void rotateZ(final float angle) {
        PGraphics.showDepthWarning("rotateZ");
    }
    
    public void rotate(final float angle, final float vx, final float vy, final float vz) {
        PGraphics.showVariationWarning("rotate");
    }
    
    public void scale(final float s) {
        this.g2.scale(s, s);
    }
    
    public void scale(final float sx, final float sy) {
        this.g2.scale(sx, sy);
    }
    
    public void scale(final float sx, final float sy, final float sz) {
        PGraphics.showDepthWarningXYZ("scale");
    }
    
    public void resetMatrix() {
        this.g2.setTransform(new AffineTransform());
    }
    
    public void applyMatrix(final float n00, final float n01, final float n02, final float n10, final float n11, final float n12) {
        this.g2.transform(new AffineTransform(n00, n10, n01, n11, n02, n12));
    }
    
    public void applyMatrix(final float n00, final float n01, final float n02, final float n03, final float n10, final float n11, final float n12, final float n13, final float n20, final float n21, final float n22, final float n23, final float n30, final float n31, final float n32, final float n33) {
        PGraphics.showVariationWarning("applyMatrix");
    }
    
    public PMatrix getMatrix() {
        return this.getMatrix((PMatrix2D)null);
    }
    
    public PMatrix2D getMatrix(PMatrix2D target) {
        if (target == null) {
            target = new PMatrix2D();
        }
        this.g2.getTransform().getMatrix(this.transform);
        target.set((float)this.transform[0], (float)this.transform[2], (float)this.transform[4], (float)this.transform[1], (float)this.transform[3], (float)this.transform[5]);
        return target;
    }
    
    public PMatrix3D getMatrix(final PMatrix3D target) {
        PGraphics.showVariationWarning("getMatrix");
        return target;
    }
    
    public void setMatrix(final PMatrix2D source) {
        this.g2.setTransform(new AffineTransform(source.m00, source.m10, source.m01, source.m11, source.m02, source.m12));
    }
    
    public void setMatrix(final PMatrix3D source) {
        PGraphics.showVariationWarning("setMatrix");
    }
    
    public void printMatrix() {
        this.getMatrix((PMatrix2D)null).print();
    }
    
    public float screenX(final float x, final float y) {
        this.g2.getTransform().getMatrix(this.transform);
        return (float)this.transform[0] * x + (float)this.transform[2] * y + (float)this.transform[4];
    }
    
    public float screenY(final float x, final float y) {
        this.g2.getTransform().getMatrix(this.transform);
        return (float)this.transform[1] * x + (float)this.transform[3] * y + (float)this.transform[5];
    }
    
    public float screenX(final float x, final float y, final float z) {
        PGraphics.showDepthWarningXYZ("screenX");
        return 0.0f;
    }
    
    public float screenY(final float x, final float y, final float z) {
        PGraphics.showDepthWarningXYZ("screenY");
        return 0.0f;
    }
    
    public float screenZ(final float x, final float y, final float z) {
        PGraphics.showDepthWarningXYZ("screenZ");
        return 0.0f;
    }
    
    public void strokeCap(final int cap) {
        super.strokeCap(cap);
        this.strokeImpl();
    }
    
    public void strokeJoin(final int join) {
        super.strokeJoin(join);
        this.strokeImpl();
    }
    
    public void strokeWeight(final float weight) {
        super.strokeWeight(weight);
        this.strokeImpl();
    }
    
    protected void strokeImpl() {
        int cap = 0;
        if (this.strokeCap == 2) {
            cap = 1;
        }
        else if (this.strokeCap == 4) {
            cap = 2;
        }
        int join = 2;
        if (this.strokeJoin == 8) {
            join = 0;
        }
        else if (this.strokeJoin == 2) {
            join = 1;
        }
        this.g2.setStroke(new BasicStroke(this.strokeWeight, cap, join));
    }
    
    protected void strokeFromCalc() {
        super.strokeFromCalc();
        this.strokeColorObject = new Color(this.strokeColor, true);
        this.strokeGradient = false;
    }
    
    protected void tintFromCalc() {
        super.tintFromCalc();
        this.tintColorObject = new Color(this.tintColor, true);
    }
    
    protected void fillFromCalc() {
        super.fillFromCalc();
        this.fillColorObject = new Color(this.fillColor, true);
        this.fillGradient = false;
    }
    
    public void backgroundImpl() {
        if (this.backgroundAlpha) {
            final WritableRaster raster = ((BufferedImage)this.image).getRaster();
            if (this.clearPixels == null || this.clearPixels.length < this.width) {
                this.clearPixels = new int[this.width];
            }
            Arrays.fill(this.clearPixels, this.backgroundColor);
            for (int i = 0; i < this.height; ++i) {
                raster.setDataElements(0, i, this.width, 1, this.clearPixels);
            }
        }
        else {
            this.pushMatrix();
            this.resetMatrix();
            this.g2.setColor(new Color(this.backgroundColor));
            this.g2.fillRect(0, 0, this.width, this.height);
            this.popMatrix();
        }
    }
    
    public void beginRaw(final PGraphics recorderRaw) {
        PGraphics.showMethodWarning("beginRaw");
    }
    
    public void endRaw() {
        PGraphics.showMethodWarning("endRaw");
    }
    
    public void loadPixels() {
        if (this.pixels == null || this.pixels.length != this.width * this.height) {
            this.pixels = new int[this.width * this.height];
        }
        final WritableRaster raster = ((BufferedImage)this.image).getRaster();
        raster.getDataElements(0, 0, this.width, this.height, this.pixels);
    }
    
    public void updatePixels() {
        final WritableRaster raster = ((BufferedImage)this.image).getRaster();
        raster.setDataElements(0, 0, this.width, this.height, this.pixels);
    }
    
    public void updatePixels(final int x, final int y, final int c, final int d) {
        if (x != 0 || y != 0 || c != this.width || d != this.height) {
            PGraphics.showVariationWarning("updatePixels(x, y, w, h)");
        }
        this.updatePixels();
    }
    
    public void resize(final int wide, final int high) {
        PGraphics.showMethodWarning("resize");
    }
    
    public int get(final int x, final int y) {
        if (x < 0 || y < 0 || x >= this.width || y >= this.height) {
            return 0;
        }
        final WritableRaster raster = ((BufferedImage)this.image).getRaster();
        raster.getDataElements(x, y, PGraphicsJava2D.getset);
        return PGraphicsJava2D.getset[0];
    }
    
    public PImage getImpl(final int x, final int y, final int w, final int h) {
        final PImage output = new PImage(w, h);
        output.parent = this.parent;
        final WritableRaster raster = ((BufferedImage)this.image).getRaster();
        raster.getDataElements(x, y, w, h, output.pixels);
        return output;
    }
    
    public PImage get() {
        return this.get(0, 0, this.width, this.height);
    }
    
    public void set(final int x, final int y, final int argb) {
        if (x < 0 || y < 0 || x >= this.width || y >= this.height) {
            return;
        }
        PGraphicsJava2D.getset[0] = argb;
        final WritableRaster raster = ((BufferedImage)this.image).getRaster();
        raster.setDataElements(x, y, PGraphicsJava2D.getset);
    }
    
    protected void setImpl(final int dx, final int dy, final int sx, final int sy, final int sw, final int sh, final PImage src) {
        final WritableRaster raster = ((BufferedImage)this.image).getRaster();
        if (sx == 0 && sy == 0 && sw == src.width && sh == src.height) {
            raster.setDataElements(dx, dy, src.width, src.height, src.pixels);
        }
        else {
            final PImage temp = src.get(sx, sy, sw, sh);
            raster.setDataElements(dx, dy, temp.width, temp.height, temp.pixels);
        }
    }
    
    public void mask(final int[] alpha) {
        PGraphics.showMethodWarning("mask");
    }
    
    public void mask(final PImage alpha) {
        PGraphics.showMethodWarning("mask");
    }
    
    public void copy(final int sx, final int sy, final int sw, final int sh, int dx, int dy, final int dw, final int dh) {
        if (sw != dw || sh != dh) {
            this.copy(this, sx, sy, sw, sh, dx, dy, dw, dh);
        }
        else {
            dx -= sx;
            dy -= sy;
            this.g2.copyArea(sx, sy, sw, sh, dx, dy);
        }
    }
    
    class ImageCache
    {
        PImage source;
        boolean tinted;
        int tintedColor;
        int[] tintedPixels;
        BufferedImage image;
        
        public ImageCache(final PImage source) {
            this.source = source;
        }
        
        public void update(final boolean tint, final int tintColor) {
            int bufferType = 2;
            final boolean opaque = (tintColor & 0xFF000000) == 0xFF000000;
            if (this.source.format == 1 && (!tint || (tint && opaque))) {
                bufferType = 1;
            }
            final boolean wrongType = this.image != null && this.image.getType() != bufferType;
            if (this.image == null || wrongType) {
                this.image = new BufferedImage(this.source.width, this.source.height, bufferType);
            }
            final WritableRaster wr = this.image.getRaster();
            if (tint) {
                if (this.tintedPixels == null || this.tintedPixels.length != this.source.width) {
                    this.tintedPixels = new int[this.source.width];
                }
                final int a2 = tintColor >> 24 & 0xFF;
                final int r2 = tintColor >> 16 & 0xFF;
                final int g2 = tintColor >> 8 & 0xFF;
                final int b2 = tintColor & 0xFF;
                if (bufferType == 1) {
                    int index = 0;
                    for (int y = 0; y < this.source.height; ++y) {
                        for (int x = 0; x < this.source.width; ++x) {
                            final int argb1 = this.source.pixels[index++];
                            final int r3 = argb1 >> 16 & 0xFF;
                            final int g3 = argb1 >> 8 & 0xFF;
                            final int b3 = argb1 & 0xFF;
                            this.tintedPixels[x] = ((r2 * r3 & 0xFF00) << 8 | (g2 * g3 & 0xFF00) | (b2 * b3 & 0xFF00) >> 8);
                        }
                        wr.setDataElements(0, y, this.source.width, 1, this.tintedPixels);
                    }
                }
                else if (bufferType == 2) {
                    int index = 0;
                    for (int y = 0; y < this.source.height; ++y) {
                        if (this.source.format == 1) {
                            final int alpha = tintColor & 0xFF000000;
                            for (int x2 = 0; x2 < this.source.width; ++x2) {
                                final int argb2 = this.source.pixels[index++];
                                final int r4 = argb2 >> 16 & 0xFF;
                                final int g4 = argb2 >> 8 & 0xFF;
                                final int b4 = argb2 & 0xFF;
                                this.tintedPixels[x2] = (alpha | (r2 * r4 & 0xFF00) << 8 | (g2 * g4 & 0xFF00) | (b2 * b4 & 0xFF00) >> 8);
                            }
                        }
                        else if (this.source.format == 2) {
                            for (int x = 0; x < this.source.width; ++x) {
                                final int argb1 = this.source.pixels[index++];
                                final int a3 = argb1 >> 24 & 0xFF;
                                final int r4 = argb1 >> 16 & 0xFF;
                                final int g4 = argb1 >> 8 & 0xFF;
                                final int b4 = argb1 & 0xFF;
                                this.tintedPixels[x] = ((a2 * a3 & 0xFF00) << 16 | (r2 * r4 & 0xFF00) << 8 | (g2 * g4 & 0xFF00) | (b2 * b4 & 0xFF00) >> 8);
                            }
                        }
                        else if (this.source.format == 4) {
                            final int lower = tintColor & 0xFFFFFF;
                            for (int x2 = 0; x2 < this.source.width; ++x2) {
                                final int a3 = this.source.pixels[index++];
                                this.tintedPixels[x2] = ((a2 * a3 & 0xFF00) << 16 | lower);
                            }
                        }
                        wr.setDataElements(0, y, this.source.width, 1, this.tintedPixels);
                    }
                }
            }
            else {
                wr.setDataElements(0, 0, this.source.width, this.source.height, this.source.pixels);
            }
            this.tinted = tint;
            this.tintedColor = tintColor;
        }
    }
}
