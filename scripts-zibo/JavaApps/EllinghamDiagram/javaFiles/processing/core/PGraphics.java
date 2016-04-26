// 
// Decompiled by Procyon v0.5.30
// 

package processing.core;

import java.awt.Color;
import java.util.HashMap;
import java.awt.Image;

public class PGraphics extends PImage implements PConstants
{
    protected int width1;
    protected int height1;
    public int pixelCount;
    public boolean smooth;
    protected boolean settingsInited;
    protected PGraphics raw;
    protected String path;
    protected boolean primarySurface;
    protected boolean[] hints;
    public int colorMode;
    public float colorModeX;
    public float colorModeY;
    public float colorModeZ;
    public float colorModeA;
    boolean colorModeScale;
    boolean colorModeDefault;
    public boolean tint;
    public int tintColor;
    protected boolean tintAlpha;
    protected float tintR;
    protected float tintG;
    protected float tintB;
    protected float tintA;
    protected int tintRi;
    protected int tintGi;
    protected int tintBi;
    protected int tintAi;
    public boolean fill;
    public int fillColor;
    protected boolean fillAlpha;
    protected float fillR;
    protected float fillG;
    protected float fillB;
    protected float fillA;
    protected int fillRi;
    protected int fillGi;
    protected int fillBi;
    protected int fillAi;
    public boolean stroke;
    public int strokeColor;
    protected boolean strokeAlpha;
    protected float strokeR;
    protected float strokeG;
    protected float strokeB;
    protected float strokeA;
    protected int strokeRi;
    protected int strokeGi;
    protected int strokeBi;
    protected int strokeAi;
    protected static final float DEFAULT_STROKE_WEIGHT = 1.0f;
    protected static final int DEFAULT_STROKE_JOIN = 8;
    protected static final int DEFAULT_STROKE_CAP = 2;
    public float strokeWeight;
    public int strokeJoin;
    public int strokeCap;
    public int rectMode;
    public int ellipseMode;
    public int shapeMode;
    public int imageMode;
    public PFont textFont;
    public int textAlign;
    public int textAlignY;
    public int textMode;
    public float textSize;
    public float textLeading;
    public float ambientR;
    public float ambientG;
    public float ambientB;
    public float specularR;
    public float specularG;
    public float specularB;
    public float emissiveR;
    public float emissiveG;
    public float emissiveB;
    public float shininess;
    static final int STYLE_STACK_DEPTH = 64;
    PStyle[] styleStack;
    int styleStackDepth;
    public int backgroundColor;
    protected boolean backgroundAlpha;
    protected float backgroundR;
    protected float backgroundG;
    protected float backgroundB;
    protected float backgroundA;
    protected int backgroundRi;
    protected int backgroundGi;
    protected int backgroundBi;
    protected int backgroundAi;
    static final int MATRIX_STACK_DEPTH = 32;
    public Image image;
    protected float calcR;
    protected float calcG;
    protected float calcB;
    protected float calcA;
    protected int calcRi;
    protected int calcGi;
    protected int calcBi;
    protected int calcAi;
    protected int calcColor;
    protected boolean calcAlpha;
    int cacheHsbKey;
    float[] cacheHsbValue;
    protected int shape;
    static final int DEFAULT_VERTICES = 512;
    protected float[][] vertices;
    protected int vertexCount;
    protected boolean bezierInited;
    public int bezierDetail;
    protected PMatrix3D bezierBasisMatrix;
    protected PMatrix3D bezierDrawMatrix;
    protected boolean curveInited;
    protected int curveDetail;
    public float curveTightness;
    protected PMatrix3D curveBasisMatrix;
    protected PMatrix3D curveDrawMatrix;
    protected PMatrix3D bezierBasisInverse;
    protected PMatrix3D curveToBezierMatrix;
    protected float[][] curveVertices;
    protected int curveVertexCount;
    protected static final float[] sinLUT;
    protected static final float[] cosLUT;
    protected static final float SINCOS_PRECISION = 0.5f;
    protected static final int SINCOS_LENGTH = 720;
    protected float textX;
    protected float textY;
    protected float textZ;
    protected char[] textBuffer;
    protected char[] textWidthBuffer;
    protected int textBreakCount;
    protected int[] textBreakStart;
    protected int[] textBreakStop;
    public boolean edge;
    protected static final int NORMAL_MODE_AUTO = 0;
    protected static final int NORMAL_MODE_SHAPE = 1;
    protected static final int NORMAL_MODE_VERTEX = 2;
    protected int normalMode;
    public float normalX;
    public float normalY;
    public float normalZ;
    public int textureMode;
    public float textureU;
    public float textureV;
    public PImage textureImage;
    float[] sphereX;
    float[] sphereY;
    float[] sphereZ;
    public int sphereDetailU;
    public int sphereDetailV;
    static float[] lerpColorHSB1;
    static float[] lerpColorHSB2;
    protected static HashMap<String, Object> warnings;
    
    static {
        sinLUT = new float[720];
        cosLUT = new float[720];
        for (int i = 0; i < 720; ++i) {
            PGraphics.sinLUT[i] = (float)Math.sin(i * 0.017453292f * 0.5f);
            PGraphics.cosLUT[i] = (float)Math.cos(i * 0.017453292f * 0.5f);
        }
    }
    
    public PGraphics() {
        this.smooth = false;
        this.hints = new boolean[10];
        this.fillColor = -1;
        this.strokeColor = -16777216;
        this.strokeWeight = 1.0f;
        this.strokeJoin = 8;
        this.strokeCap = 2;
        this.imageMode = 0;
        this.textAlign = 37;
        this.textAlignY = 0;
        this.textMode = 4;
        this.styleStack = new PStyle[64];
        this.backgroundColor = -3355444;
        this.cacheHsbValue = new float[3];
        this.vertices = new float[512][36];
        this.bezierInited = false;
        this.bezierDetail = 20;
        this.bezierBasisMatrix = new PMatrix3D(-1.0f, 3.0f, -3.0f, 1.0f, 3.0f, -6.0f, 3.0f, 0.0f, -3.0f, 3.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f);
        this.curveInited = false;
        this.curveDetail = 20;
        this.curveTightness = 0.0f;
        this.textBuffer = new char[8192];
        this.textWidthBuffer = new char[8192];
        this.edge = true;
        this.sphereDetailU = 0;
        this.sphereDetailV = 0;
    }
    
    public void setParent(final PApplet parent) {
        this.parent = parent;
    }
    
    public void setPrimary(final boolean primary) {
        this.primarySurface = primary;
        if (this.primarySurface) {
            this.format = 1;
        }
    }
    
    public void setPath(final String path) {
        this.path = path;
    }
    
    public void setSize(final int w, final int h) {
        this.width = w;
        this.height = h;
        this.width1 = this.width - 1;
        this.height1 = this.height - 1;
        this.allocate();
        this.reapplySettings();
    }
    
    protected void allocate() {
    }
    
    public void dispose() {
    }
    
    public boolean canDraw() {
        return true;
    }
    
    public void beginDraw() {
    }
    
    public void endDraw() {
    }
    
    public void flush() {
    }
    
    protected void checkSettings() {
        if (!this.settingsInited) {
            this.defaultSettings();
        }
    }
    
    protected void defaultSettings() {
        this.noSmooth();
        this.colorMode(1, 255.0f);
        this.fill(255);
        this.stroke(0);
        this.strokeWeight(1.0f);
        this.strokeJoin(8);
        this.strokeCap(2);
        this.rectMode(this.shape = 0);
        this.ellipseMode(3);
        this.textFont = null;
        this.textSize = 12.0f;
        this.textLeading = 14.0f;
        this.textAlign = 37;
        this.textMode = 4;
        if (this.primarySurface) {
            this.background(this.backgroundColor);
        }
        this.settingsInited = true;
    }
    
    protected void reapplySettings() {
        if (!this.settingsInited) {
            return;
        }
        this.colorMode(this.colorMode, this.colorModeX, this.colorModeY, this.colorModeZ);
        if (this.fill) {
            this.fill(this.fillColor);
        }
        else {
            this.noFill();
        }
        if (this.stroke) {
            this.stroke(this.strokeColor);
            this.strokeWeight(this.strokeWeight);
            this.strokeCap(this.strokeCap);
            this.strokeJoin(this.strokeJoin);
        }
        else {
            this.noStroke();
        }
        if (this.tint) {
            this.tint(this.tintColor);
        }
        else {
            this.noTint();
        }
        if (this.smooth) {
            this.smooth();
        }
        else {
            this.noSmooth();
        }
        if (this.textFont != null) {
            final float saveLeading = this.textLeading;
            this.textFont(this.textFont, this.textSize);
            this.textLeading(saveLeading);
        }
        this.textMode(this.textMode);
        this.textAlign(this.textAlign, this.textAlignY);
        this.background(this.backgroundColor);
    }
    
    public void hint(final int which) {
        if (which > 0) {
            this.hints[which] = true;
        }
        else {
            this.hints[-which] = false;
        }
    }
    
    public void beginShape() {
        this.beginShape(20);
    }
    
    public void beginShape(final int kind) {
        this.shape = kind;
    }
    
    public void edge(final boolean edge) {
        this.edge = edge;
    }
    
    public void normal(final float nx, final float ny, final float nz) {
        this.normalX = nx;
        this.normalY = ny;
        this.normalZ = nz;
        if (this.shape != 0) {
            if (this.normalMode == 0) {
                this.normalMode = 1;
            }
            else if (this.normalMode == 1) {
                this.normalMode = 2;
            }
        }
    }
    
    public void textureMode(final int mode) {
        this.textureMode = mode;
    }
    
    public void texture(final PImage image) {
        this.textureImage = image;
    }
    
    protected void vertexCheck() {
        if (this.vertexCount == this.vertices.length) {
            final float[][] temp = new float[this.vertexCount << 1][36];
            System.arraycopy(this.vertices, 0, temp, 0, this.vertexCount);
            this.vertices = temp;
        }
    }
    
    public void vertex(final float x, final float y) {
        this.vertexCheck();
        final float[] vertex = this.vertices[this.vertexCount];
        vertex[this.curveVertexCount = 0] = x;
        vertex[1] = y;
        vertex[12] = (this.edge ? 1 : 0);
        if (this.fill || this.textureImage != null) {
            if (this.textureImage == null) {
                vertex[3] = this.fillR;
                vertex[4] = this.fillG;
                vertex[5] = this.fillB;
                vertex[6] = this.fillA;
            }
            else if (this.tint) {
                vertex[3] = this.tintR;
                vertex[4] = this.tintG;
                vertex[5] = this.tintB;
                vertex[6] = this.tintA;
            }
            else {
                vertex[4] = (vertex[3] = 1.0f);
                vertex[6] = (vertex[5] = 1.0f);
            }
        }
        if (this.stroke) {
            vertex[13] = this.strokeR;
            vertex[14] = this.strokeG;
            vertex[15] = this.strokeB;
            vertex[16] = this.strokeA;
            vertex[17] = this.strokeWeight;
        }
        if (this.textureImage != null) {
            vertex[7] = this.textureU;
            vertex[8] = this.textureV;
        }
        ++this.vertexCount;
    }
    
    public void vertex(final float x, final float y, final float z) {
        this.vertexCheck();
        final float[] vertex = this.vertices[this.vertexCount];
        if (this.shape == 20 && this.vertexCount > 0) {
            final float[] pvertex = this.vertices[this.vertexCount - 1];
            if (Math.abs(pvertex[0] - x) < 1.0E-4f && Math.abs(pvertex[1] - y) < 1.0E-4f && Math.abs(pvertex[2] - z) < 1.0E-4f) {
                return;
            }
        }
        vertex[this.curveVertexCount = 0] = x;
        vertex[1] = y;
        vertex[2] = z;
        vertex[12] = (this.edge ? 1 : 0);
        if (this.fill || this.textureImage != null) {
            if (this.textureImage == null) {
                vertex[3] = this.fillR;
                vertex[4] = this.fillG;
                vertex[5] = this.fillB;
                vertex[6] = this.fillA;
            }
            else if (this.tint) {
                vertex[3] = this.tintR;
                vertex[4] = this.tintG;
                vertex[5] = this.tintB;
                vertex[6] = this.tintA;
            }
            else {
                vertex[4] = (vertex[3] = 1.0f);
                vertex[6] = (vertex[5] = 1.0f);
            }
            vertex[25] = this.ambientR;
            vertex[26] = this.ambientG;
            vertex[27] = this.ambientB;
            vertex[28] = this.specularR;
            vertex[29] = this.specularG;
            vertex[30] = this.specularB;
            vertex[31] = this.shininess;
            vertex[32] = this.emissiveR;
            vertex[33] = this.emissiveG;
            vertex[34] = this.emissiveB;
        }
        if (this.stroke) {
            vertex[13] = this.strokeR;
            vertex[14] = this.strokeG;
            vertex[15] = this.strokeB;
            vertex[16] = this.strokeA;
            vertex[17] = this.strokeWeight;
        }
        if (this.textureImage != null) {
            vertex[7] = this.textureU;
            vertex[8] = this.textureV;
        }
        vertex[9] = this.normalX;
        vertex[10] = this.normalY;
        vertex[11] = this.normalZ;
        vertex[35] = 0.0f;
        ++this.vertexCount;
    }
    
    public void vertex(final float[] v) {
        this.vertexCheck();
        this.curveVertexCount = 0;
        final float[] vertex = this.vertices[this.vertexCount];
        System.arraycopy(v, 0, vertex, 0, 36);
        ++this.vertexCount;
    }
    
    public void vertex(final float x, final float y, final float u, final float v) {
        this.vertexTexture(u, v);
        this.vertex(x, y);
    }
    
    public void vertex(final float x, final float y, final float z, final float u, final float v) {
        this.vertexTexture(u, v);
        this.vertex(x, y, z);
    }
    
    protected void vertexTexture(float u, float v) {
        if (this.textureImage == null) {
            throw new RuntimeException("You must first call texture() before using u and v coordinates with vertex()");
        }
        if (this.textureMode == 2) {
            u /= this.textureImage.width;
            v /= this.textureImage.height;
        }
        this.textureU = u;
        this.textureV = v;
        if (this.textureU < 0.0f) {
            this.textureU = 0.0f;
        }
        else if (this.textureU > 1.0f) {
            this.textureU = 1.0f;
        }
        if (this.textureV < 0.0f) {
            this.textureV = 0.0f;
        }
        else if (this.textureV > 1.0f) {
            this.textureV = 1.0f;
        }
    }
    
    public void breakShape() {
        showWarning("This renderer cannot currently handle concave shapes, or shapes with holes.");
    }
    
    public void endShape() {
        this.endShape(1);
    }
    
    public void endShape(final int mode) {
    }
    
    protected void bezierVertexCheck() {
        if (this.shape == 0 || this.shape != 20) {
            throw new RuntimeException("beginShape() or beginShape(POLYGON) must be used before bezierVertex()");
        }
        if (this.vertexCount == 0) {
            throw new RuntimeException("vertex() must be used at least oncebefore bezierVertex()");
        }
    }
    
    public void bezierVertex(final float x2, final float y2, final float x3, final float y3, final float x4, final float y4) {
        this.bezierInitCheck();
        this.bezierVertexCheck();
        final PMatrix3D draw = this.bezierDrawMatrix;
        final float[] prev = this.vertices[this.vertexCount - 1];
        float x5 = prev[0];
        float y5 = prev[1];
        float xplot1 = draw.m10 * x5 + draw.m11 * x2 + draw.m12 * x3 + draw.m13 * x4;
        float xplot2 = draw.m20 * x5 + draw.m21 * x2 + draw.m22 * x3 + draw.m23 * x4;
        final float xplot3 = draw.m30 * x5 + draw.m31 * x2 + draw.m32 * x3 + draw.m33 * x4;
        float yplot1 = draw.m10 * y5 + draw.m11 * y2 + draw.m12 * y3 + draw.m13 * y4;
        float yplot2 = draw.m20 * y5 + draw.m21 * y2 + draw.m22 * y3 + draw.m23 * y4;
        final float yplot3 = draw.m30 * y5 + draw.m31 * y2 + draw.m32 * y3 + draw.m33 * y4;
        for (int j = 0; j < this.bezierDetail; ++j) {
            x5 += xplot1;
            xplot1 += xplot2;
            xplot2 += xplot3;
            y5 += yplot1;
            yplot1 += yplot2;
            yplot2 += yplot3;
            this.vertex(x5, y5);
        }
    }
    
    public void bezierVertex(final float x2, final float y2, final float z2, final float x3, final float y3, final float z3, final float x4, final float y4, final float z4) {
        this.bezierInitCheck();
        this.bezierVertexCheck();
        final PMatrix3D draw = this.bezierDrawMatrix;
        final float[] prev = this.vertices[this.vertexCount - 1];
        float x5 = prev[0];
        float y5 = prev[1];
        float z5 = prev[2];
        float xplot1 = draw.m10 * x5 + draw.m11 * x2 + draw.m12 * x3 + draw.m13 * x4;
        float xplot2 = draw.m20 * x5 + draw.m21 * x2 + draw.m22 * x3 + draw.m23 * x4;
        final float xplot3 = draw.m30 * x5 + draw.m31 * x2 + draw.m32 * x3 + draw.m33 * x4;
        float yplot1 = draw.m10 * y5 + draw.m11 * y2 + draw.m12 * y3 + draw.m13 * y4;
        float yplot2 = draw.m20 * y5 + draw.m21 * y2 + draw.m22 * y3 + draw.m23 * y4;
        final float yplot3 = draw.m30 * y5 + draw.m31 * y2 + draw.m32 * y3 + draw.m33 * y4;
        float zplot1 = draw.m10 * z5 + draw.m11 * z2 + draw.m12 * z3 + draw.m13 * z4;
        float zplot2 = draw.m20 * z5 + draw.m21 * z2 + draw.m22 * z3 + draw.m23 * z4;
        final float zplot3 = draw.m30 * z5 + draw.m31 * z2 + draw.m32 * z3 + draw.m33 * z4;
        for (int j = 0; j < this.bezierDetail; ++j) {
            x5 += xplot1;
            xplot1 += xplot2;
            xplot2 += xplot3;
            y5 += yplot1;
            yplot1 += yplot2;
            yplot2 += yplot3;
            z5 += zplot1;
            zplot1 += zplot2;
            zplot2 += zplot3;
            this.vertex(x5, y5, z5);
        }
    }
    
    protected void curveVertexCheck() {
        if (this.shape != 20) {
            throw new RuntimeException("You must use beginShape() or beginShape(POLYGON) before curveVertex()");
        }
        if (this.curveVertices == null) {
            this.curveVertices = new float[128][3];
        }
        if (this.curveVertexCount == this.curveVertices.length) {
            final float[][] temp = new float[this.curveVertexCount << 1][3];
            System.arraycopy(this.curveVertices, 0, temp, 0, this.curveVertexCount);
            this.curveVertices = temp;
        }
        this.curveInitCheck();
    }
    
    public void curveVertex(final float x, final float y) {
        this.curveVertexCheck();
        final float[] vertex = this.curveVertices[this.curveVertexCount];
        vertex[0] = x;
        vertex[1] = y;
        ++this.curveVertexCount;
        if (this.curveVertexCount > 3) {
            this.curveVertexSegment(this.curveVertices[this.curveVertexCount - 4][0], this.curveVertices[this.curveVertexCount - 4][1], this.curveVertices[this.curveVertexCount - 3][0], this.curveVertices[this.curveVertexCount - 3][1], this.curveVertices[this.curveVertexCount - 2][0], this.curveVertices[this.curveVertexCount - 2][1], this.curveVertices[this.curveVertexCount - 1][0], this.curveVertices[this.curveVertexCount - 1][1]);
        }
    }
    
    public void curveVertex(final float x, final float y, final float z) {
        this.curveVertexCheck();
        final float[] vertex = this.curveVertices[this.curveVertexCount];
        vertex[0] = x;
        vertex[1] = y;
        vertex[2] = z;
        ++this.curveVertexCount;
        if (this.curveVertexCount > 3) {
            this.curveVertexSegment(this.curveVertices[this.curveVertexCount - 4][0], this.curveVertices[this.curveVertexCount - 4][1], this.curveVertices[this.curveVertexCount - 4][2], this.curveVertices[this.curveVertexCount - 3][0], this.curveVertices[this.curveVertexCount - 3][1], this.curveVertices[this.curveVertexCount - 3][2], this.curveVertices[this.curveVertexCount - 2][0], this.curveVertices[this.curveVertexCount - 2][1], this.curveVertices[this.curveVertexCount - 2][2], this.curveVertices[this.curveVertexCount - 1][0], this.curveVertices[this.curveVertexCount - 1][1], this.curveVertices[this.curveVertexCount - 1][2]);
        }
    }
    
    protected void curveVertexSegment(final float x1, final float y1, final float x2, final float y2, final float x3, final float y3, final float x4, final float y4) {
        float x5 = x2;
        float y5 = y2;
        final PMatrix3D draw = this.curveDrawMatrix;
        float xplot1 = draw.m10 * x1 + draw.m11 * x2 + draw.m12 * x3 + draw.m13 * x4;
        float xplot2 = draw.m20 * x1 + draw.m21 * x2 + draw.m22 * x3 + draw.m23 * x4;
        final float xplot3 = draw.m30 * x1 + draw.m31 * x2 + draw.m32 * x3 + draw.m33 * x4;
        float yplot1 = draw.m10 * y1 + draw.m11 * y2 + draw.m12 * y3 + draw.m13 * y4;
        float yplot2 = draw.m20 * y1 + draw.m21 * y2 + draw.m22 * y3 + draw.m23 * y4;
        final float yplot3 = draw.m30 * y1 + draw.m31 * y2 + draw.m32 * y3 + draw.m33 * y4;
        final int savedCount = this.curveVertexCount;
        this.vertex(x5, y5);
        for (int j = 0; j < this.curveDetail; ++j) {
            x5 += xplot1;
            xplot1 += xplot2;
            xplot2 += xplot3;
            y5 += yplot1;
            yplot1 += yplot2;
            yplot2 += yplot3;
            this.vertex(x5, y5);
        }
        this.curveVertexCount = savedCount;
    }
    
    protected void curveVertexSegment(final float x1, final float y1, final float z1, final float x2, final float y2, final float z2, final float x3, final float y3, final float z3, final float x4, final float y4, final float z4) {
        float x5 = x2;
        float y5 = y2;
        float z5 = z2;
        final PMatrix3D draw = this.curveDrawMatrix;
        float xplot1 = draw.m10 * x1 + draw.m11 * x2 + draw.m12 * x3 + draw.m13 * x4;
        float xplot2 = draw.m20 * x1 + draw.m21 * x2 + draw.m22 * x3 + draw.m23 * x4;
        final float xplot3 = draw.m30 * x1 + draw.m31 * x2 + draw.m32 * x3 + draw.m33 * x4;
        float yplot1 = draw.m10 * y1 + draw.m11 * y2 + draw.m12 * y3 + draw.m13 * y4;
        float yplot2 = draw.m20 * y1 + draw.m21 * y2 + draw.m22 * y3 + draw.m23 * y4;
        final float yplot3 = draw.m30 * y1 + draw.m31 * y2 + draw.m32 * y3 + draw.m33 * y4;
        final int savedCount = this.curveVertexCount;
        float zplot1 = draw.m10 * z1 + draw.m11 * z2 + draw.m12 * z3 + draw.m13 * z4;
        float zplot2 = draw.m20 * z1 + draw.m21 * z2 + draw.m22 * z3 + draw.m23 * z4;
        final float zplot3 = draw.m30 * z1 + draw.m31 * z2 + draw.m32 * z3 + draw.m33 * z4;
        this.vertex(x5, y5, z5);
        for (int j = 0; j < this.curveDetail; ++j) {
            x5 += xplot1;
            xplot1 += xplot2;
            xplot2 += xplot3;
            y5 += yplot1;
            yplot1 += yplot2;
            yplot2 += yplot3;
            z5 += zplot1;
            zplot1 += zplot2;
            zplot2 += zplot3;
            this.vertex(x5, y5, z5);
        }
        this.curveVertexCount = savedCount;
    }
    
    public void point(final float x, final float y) {
        this.beginShape(2);
        this.vertex(x, y);
        this.endShape();
    }
    
    public void point(final float x, final float y, final float z) {
        this.beginShape(2);
        this.vertex(x, y, z);
        this.endShape();
    }
    
    public void line(final float x1, final float y1, final float x2, final float y2) {
        this.beginShape(4);
        this.vertex(x1, y1);
        this.vertex(x2, y2);
        this.endShape();
    }
    
    public void line(final float x1, final float y1, final float z1, final float x2, final float y2, final float z2) {
        this.beginShape(4);
        this.vertex(x1, y1, z1);
        this.vertex(x2, y2, z2);
        this.endShape();
    }
    
    public void triangle(final float x1, final float y1, final float x2, final float y2, final float x3, final float y3) {
        this.beginShape(9);
        this.vertex(x1, y1);
        this.vertex(x2, y2);
        this.vertex(x3, y3);
        this.endShape();
    }
    
    public void quad(final float x1, final float y1, final float x2, final float y2, final float x3, final float y3, final float x4, final float y4) {
        this.beginShape(16);
        this.vertex(x1, y1);
        this.vertex(x2, y2);
        this.vertex(x3, y3);
        this.vertex(x4, y4);
        this.endShape();
    }
    
    public void rectMode(final int mode) {
        this.rectMode = mode;
    }
    
    public void rect(float a, float b, float c, float d) {
        switch (this.rectMode) {
            case 0: {
                c += a;
                d += b;
                break;
            }
            case 2: {
                final float hradius = c;
                final float vradius = d;
                c = a + hradius;
                d = b + vradius;
                a -= hradius;
                b -= vradius;
                break;
            }
            case 3: {
                final float hradius = c / 2.0f;
                final float vradius = d / 2.0f;
                c = a + hradius;
                d = b + vradius;
                a -= hradius;
                b -= vradius;
                break;
            }
        }
        if (a > c) {
            final float temp = a;
            a = c;
            c = temp;
        }
        if (b > d) {
            final float temp = b;
            b = d;
            d = temp;
        }
        this.rectImpl(a, b, c, d);
    }
    
    protected void rectImpl(final float x1, final float y1, final float x2, final float y2) {
        this.quad(x1, y1, x2, y1, x2, y2, x1, y2);
    }
    
    private void quadraticVertex(final float cpx, final float cpy, final float x, final float y) {
        final float[] prev = this.vertices[this.vertexCount - 1];
        final float prevX = prev[0];
        final float prevY = prev[1];
        final float cp1x = prevX + 0.6666667f * (cpx - prevX);
        final float cp1y = prevY + 0.6666667f * (cpy - prevY);
        final float cp2x = cp1x + (x - prevX) / 3.0f;
        final float cp2y = cp1y + (y - prevY) / 3.0f;
        this.bezierVertex(cp1x, cp1y, cp2x, cp2y, x, y);
    }
    
    public void rect(float a, float b, float c, float d, final float hr, final float vr) {
        switch (this.rectMode) {
            case 0: {
                c += a;
                d += b;
                break;
            }
            case 2: {
                final float hradius = c;
                final float vradius = d;
                c = a + hradius;
                d = b + vradius;
                a -= hradius;
                b -= vradius;
                break;
            }
            case 3: {
                final float hradius = c / 2.0f;
                final float vradius = d / 2.0f;
                c = a + hradius;
                d = b + vradius;
                a -= hradius;
                b -= vradius;
                break;
            }
        }
        if (a > c) {
            final float temp = a;
            a = c;
            c = temp;
        }
        if (b > d) {
            final float temp = b;
            b = d;
            d = temp;
        }
        this.rectImpl(a, b, c, d, hr, vr);
    }
    
    protected void rectImpl(final float x1, final float y1, final float x2, final float y2, final float hr, final float vr) {
        this.beginShape();
        this.vertex(x1 + hr, y1);
        this.vertex(x2 - hr, y1);
        this.quadraticVertex(x2, y1, x2, y1 + vr);
        this.vertex(x2, y2 - vr);
        this.quadraticVertex(x2, y2, x2 - hr, y2);
        this.vertex(x1 + hr, y2);
        this.quadraticVertex(x1, y2, x1, y2 - vr);
        this.vertex(x1, y1 + vr);
        this.quadraticVertex(x1, y1, x1 + hr, y1);
        this.endShape();
    }
    
    public void rect(float a, float b, float c, float d, final float tl, final float tr, final float bl, final float br) {
        switch (this.rectMode) {
            case 0: {
                c += a;
                d += b;
                break;
            }
            case 2: {
                final float hradius = c;
                final float vradius = d;
                c = a + hradius;
                d = b + vradius;
                a -= hradius;
                b -= vradius;
                break;
            }
            case 3: {
                final float hradius = c / 2.0f;
                final float vradius = d / 2.0f;
                c = a + hradius;
                d = b + vradius;
                a -= hradius;
                b -= vradius;
                break;
            }
        }
        if (a > c) {
            final float temp = a;
            a = c;
            c = temp;
        }
        if (b > d) {
            final float temp = b;
            b = d;
            d = temp;
        }
        this.rectImpl(a, b, c, d, tl, tr, bl, br);
    }
    
    protected void rectImpl(final float x1, final float y1, final float x2, final float y2, final float tl, final float tr, final float bl, final float br) {
        this.beginShape();
        this.vertex(x1 + tl, y1);
        this.vertex(x2 - tr, y1);
        this.quadraticVertex(x2, y1, x2, y1 + tr);
        this.vertex(x2, y2 - br);
        this.quadraticVertex(x2, y2, x2 - br, y2);
        this.vertex(x1 + bl, y2);
        this.quadraticVertex(x1, y2, x1, y2 - bl);
        this.vertex(x1, y1 + tl);
        this.quadraticVertex(x1, y1, x1 + tl, y1);
        this.endShape();
    }
    
    public void ellipseMode(final int mode) {
        this.ellipseMode = mode;
    }
    
    public void ellipse(final float a, final float b, final float c, final float d) {
        float x = a;
        float y = b;
        float w = c;
        float h = d;
        if (this.ellipseMode == 1) {
            w = c - a;
            h = d - b;
        }
        else if (this.ellipseMode == 2) {
            x = a - c;
            y = b - d;
            w = c * 2.0f;
            h = d * 2.0f;
        }
        else if (this.ellipseMode == 3) {
            x = a - c / 2.0f;
            y = b - d / 2.0f;
        }
        if (w < 0.0f) {
            x += w;
            w = -w;
        }
        if (h < 0.0f) {
            y += h;
            h = -h;
        }
        this.ellipseImpl(x, y, w, h);
    }
    
    protected void ellipseImpl(final float x, final float y, final float w, final float h) {
    }
    
    public void arc(final float a, final float b, final float c, final float d, float start, float stop) {
        float x = a;
        float y = b;
        float w = c;
        float h = d;
        if (this.ellipseMode == 1) {
            w = c - a;
            h = d - b;
        }
        else if (this.ellipseMode == 2) {
            x = a - c;
            y = b - d;
            w = c * 2.0f;
            h = d * 2.0f;
        }
        else if (this.ellipseMode == 3) {
            x = a - c / 2.0f;
            y = b - d / 2.0f;
        }
        if (Float.isInfinite(start) || Float.isInfinite(stop)) {
            return;
        }
        if (stop < start) {
            return;
        }
        while (start < 0.0f) {
            start += 6.2831855f;
            stop += 6.2831855f;
        }
        if (stop - start > 6.2831855f) {
            start = 0.0f;
            stop = 6.2831855f;
        }
        this.arcImpl(x, y, w, h, start, stop);
    }
    
    protected void arcImpl(final float x, final float y, final float w, final float h, final float start, final float stop) {
    }
    
    public void box(final float size) {
        this.box(size, size, size);
    }
    
    public void box(final float w, final float h, final float d) {
        final float x1 = -w / 2.0f;
        final float x2 = w / 2.0f;
        final float y1 = -h / 2.0f;
        final float y2 = h / 2.0f;
        final float z1 = -d / 2.0f;
        final float z2 = d / 2.0f;
        this.beginShape(16);
        this.normal(0.0f, 0.0f, 1.0f);
        this.vertex(x1, y1, z1);
        this.vertex(x2, y1, z1);
        this.vertex(x2, y2, z1);
        this.vertex(x1, y2, z1);
        this.normal(1.0f, 0.0f, 0.0f);
        this.vertex(x2, y1, z1);
        this.vertex(x2, y1, z2);
        this.vertex(x2, y2, z2);
        this.vertex(x2, y2, z1);
        this.normal(0.0f, 0.0f, -1.0f);
        this.vertex(x2, y1, z2);
        this.vertex(x1, y1, z2);
        this.vertex(x1, y2, z2);
        this.vertex(x2, y2, z2);
        this.normal(-1.0f, 0.0f, 0.0f);
        this.vertex(x1, y1, z2);
        this.vertex(x1, y1, z1);
        this.vertex(x1, y2, z1);
        this.vertex(x1, y2, z2);
        this.normal(0.0f, 1.0f, 0.0f);
        this.vertex(x1, y1, z2);
        this.vertex(x2, y1, z2);
        this.vertex(x2, y1, z1);
        this.vertex(x1, y1, z1);
        this.normal(0.0f, -1.0f, 0.0f);
        this.vertex(x1, y2, z1);
        this.vertex(x2, y2, z1);
        this.vertex(x2, y2, z2);
        this.vertex(x1, y2, z2);
        this.endShape();
    }
    
    public void sphereDetail(final int res) {
        this.sphereDetail(res, res);
    }
    
    public void sphereDetail(int ures, int vres) {
        if (ures < 3) {
            ures = 3;
        }
        if (vres < 2) {
            vres = 2;
        }
        if (ures == this.sphereDetailU && vres == this.sphereDetailV) {
            return;
        }
        final float delta = 720.0f / ures;
        final float[] cx = new float[ures];
        final float[] cz = new float[ures];
        for (int i = 0; i < ures; ++i) {
            cx[i] = PGraphics.cosLUT[(int)(i * delta) % 720];
            cz[i] = PGraphics.sinLUT[(int)(i * delta) % 720];
        }
        final int vertCount = ures * (vres - 1) + 2;
        int currVert = 0;
        this.sphereX = new float[vertCount];
        this.sphereY = new float[vertCount];
        this.sphereZ = new float[vertCount];
        float angle;
        final float angle_step = angle = 360.0f / vres;
        for (int j = 1; j < vres; ++j) {
            final float curradius = PGraphics.sinLUT[(int)angle % 720];
            final float currY = -PGraphics.cosLUT[(int)angle % 720];
            for (int k = 0; k < ures; ++k) {
                this.sphereX[currVert] = cx[k] * curradius;
                this.sphereY[currVert] = currY;
                this.sphereZ[currVert++] = cz[k] * curradius;
            }
            angle += angle_step;
        }
        this.sphereDetailU = ures;
        this.sphereDetailV = vres;
    }
    
    public void sphere(final float r) {
        if (this.sphereDetailU < 3 || this.sphereDetailV < 2) {
            this.sphereDetail(30);
        }
        this.pushMatrix();
        this.scale(r);
        this.edge(false);
        this.beginShape(10);
        for (int i = 0; i < this.sphereDetailU; ++i) {
            this.normal(0.0f, -1.0f, 0.0f);
            this.vertex(0.0f, -1.0f, 0.0f);
            this.normal(this.sphereX[i], this.sphereY[i], this.sphereZ[i]);
            this.vertex(this.sphereX[i], this.sphereY[i], this.sphereZ[i]);
        }
        this.vertex(0.0f, -1.0f, 0.0f);
        this.normal(this.sphereX[0], this.sphereY[0], this.sphereZ[0]);
        this.vertex(this.sphereX[0], this.sphereY[0], this.sphereZ[0]);
        this.endShape();
        int voff = 0;
        for (int j = 2; j < this.sphereDetailV; ++j) {
            int v12;
            final int v11 = v12 = voff;
            int v13;
            voff = (v13 = voff + this.sphereDetailU);
            this.beginShape(10);
            for (int k = 0; k < this.sphereDetailU; ++k) {
                this.normal(this.sphereX[v12], this.sphereY[v12], this.sphereZ[v12]);
                this.vertex(this.sphereX[v12], this.sphereY[v12], this.sphereZ[v12++]);
                this.normal(this.sphereX[v13], this.sphereY[v13], this.sphereZ[v13]);
                this.vertex(this.sphereX[v13], this.sphereY[v13], this.sphereZ[v13++]);
            }
            v12 = v11;
            v13 = voff;
            this.normal(this.sphereX[v12], this.sphereY[v12], this.sphereZ[v12]);
            this.vertex(this.sphereX[v12], this.sphereY[v12], this.sphereZ[v12]);
            this.normal(this.sphereX[v13], this.sphereY[v13], this.sphereZ[v13]);
            this.vertex(this.sphereX[v13], this.sphereY[v13], this.sphereZ[v13]);
            this.endShape();
        }
        this.beginShape(10);
        for (int j = 0; j < this.sphereDetailU; ++j) {
            final int v13 = voff + j;
            this.normal(this.sphereX[v13], this.sphereY[v13], this.sphereZ[v13]);
            this.vertex(this.sphereX[v13], this.sphereY[v13], this.sphereZ[v13]);
            this.normal(0.0f, 1.0f, 0.0f);
            this.vertex(0.0f, 1.0f, 0.0f);
        }
        this.normal(this.sphereX[voff], this.sphereY[voff], this.sphereZ[voff]);
        this.vertex(this.sphereX[voff], this.sphereY[voff], this.sphereZ[voff]);
        this.normal(0.0f, 1.0f, 0.0f);
        this.vertex(0.0f, 1.0f, 0.0f);
        this.endShape();
        this.edge(true);
        this.popMatrix();
    }
    
    public float bezierPoint(final float a, final float b, final float c, final float d, final float t) {
        final float t2 = 1.0f - t;
        return a * t2 * t2 * t2 + 3.0f * b * t * t2 * t2 + 3.0f * c * t * t * t2 + d * t * t * t;
    }
    
    public float bezierTangent(final float a, final float b, final float c, final float d, final float t) {
        return 3.0f * t * t * (-a + 3.0f * b - 3.0f * c + d) + 6.0f * t * (a - 2.0f * b + c) + 3.0f * (-a + b);
    }
    
    protected void bezierInitCheck() {
        if (!this.bezierInited) {
            this.bezierInit();
        }
    }
    
    protected void bezierInit() {
        this.bezierDetail(this.bezierDetail);
        this.bezierInited = true;
    }
    
    public void bezierDetail(final int detail) {
        this.bezierDetail = detail;
        if (this.bezierDrawMatrix == null) {
            this.bezierDrawMatrix = new PMatrix3D();
        }
        this.splineForward(detail, this.bezierDrawMatrix);
        this.bezierDrawMatrix.apply(this.bezierBasisMatrix);
    }
    
    public void bezier(final float x1, final float y1, final float x2, final float y2, final float x3, final float y3, final float x4, final float y4) {
        this.beginShape();
        this.vertex(x1, y1);
        this.bezierVertex(x2, y2, x3, y3, x4, y4);
        this.endShape();
    }
    
    public void bezier(final float x1, final float y1, final float z1, final float x2, final float y2, final float z2, final float x3, final float y3, final float z3, final float x4, final float y4, final float z4) {
        this.beginShape();
        this.vertex(x1, y1, z1);
        this.bezierVertex(x2, y2, z2, x3, y3, z3, x4, y4, z4);
        this.endShape();
    }
    
    public float curvePoint(final float a, final float b, final float c, final float d, final float t) {
        this.curveInitCheck();
        final float tt = t * t;
        final float ttt = t * tt;
        final PMatrix3D cb = this.curveBasisMatrix;
        return a * (ttt * cb.m00 + tt * cb.m10 + t * cb.m20 + cb.m30) + b * (ttt * cb.m01 + tt * cb.m11 + t * cb.m21 + cb.m31) + c * (ttt * cb.m02 + tt * cb.m12 + t * cb.m22 + cb.m32) + d * (ttt * cb.m03 + tt * cb.m13 + t * cb.m23 + cb.m33);
    }
    
    public float curveTangent(final float a, final float b, final float c, final float d, final float t) {
        this.curveInitCheck();
        final float tt3 = t * t * 3.0f;
        final float t2 = t * 2.0f;
        final PMatrix3D cb = this.curveBasisMatrix;
        return a * (tt3 * cb.m00 + t2 * cb.m10 + cb.m20) + b * (tt3 * cb.m01 + t2 * cb.m11 + cb.m21) + c * (tt3 * cb.m02 + t2 * cb.m12 + cb.m22) + d * (tt3 * cb.m03 + t2 * cb.m13 + cb.m23);
    }
    
    public void curveDetail(final int detail) {
        this.curveDetail = detail;
        this.curveInit();
    }
    
    public void curveTightness(final float tightness) {
        this.curveTightness = tightness;
        this.curveInit();
    }
    
    protected void curveInitCheck() {
        if (!this.curveInited) {
            this.curveInit();
        }
    }
    
    protected void curveInit() {
        if (this.curveDrawMatrix == null) {
            this.curveBasisMatrix = new PMatrix3D();
            this.curveDrawMatrix = new PMatrix3D();
            this.curveInited = true;
        }
        final float s = this.curveTightness;
        this.curveBasisMatrix.set((s - 1.0f) / 2.0f, (s + 3.0f) / 2.0f, (-3.0f - s) / 2.0f, (1.0f - s) / 2.0f, 1.0f - s, (-5.0f - s) / 2.0f, s + 2.0f, (s - 1.0f) / 2.0f, (s - 1.0f) / 2.0f, 0.0f, (1.0f - s) / 2.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f);
        this.splineForward(this.curveDetail, this.curveDrawMatrix);
        if (this.bezierBasisInverse == null) {
            (this.bezierBasisInverse = this.bezierBasisMatrix.get()).invert();
            this.curveToBezierMatrix = new PMatrix3D();
        }
        this.curveToBezierMatrix.set(this.curveBasisMatrix);
        this.curveToBezierMatrix.preApply(this.bezierBasisInverse);
        this.curveDrawMatrix.apply(this.curveBasisMatrix);
    }
    
    public void curve(final float x1, final float y1, final float x2, final float y2, final float x3, final float y3, final float x4, final float y4) {
        this.beginShape();
        this.curveVertex(x1, y1);
        this.curveVertex(x2, y2);
        this.curveVertex(x3, y3);
        this.curveVertex(x4, y4);
        this.endShape();
    }
    
    public void curve(final float x1, final float y1, final float z1, final float x2, final float y2, final float z2, final float x3, final float y3, final float z3, final float x4, final float y4, final float z4) {
        this.beginShape();
        this.curveVertex(x1, y1, z1);
        this.curveVertex(x2, y2, z2);
        this.curveVertex(x3, y3, z3);
        this.curveVertex(x4, y4, z4);
        this.endShape();
    }
    
    protected void splineForward(final int segments, final PMatrix3D matrix) {
        final float f = 1.0f / segments;
        final float ff = f * f;
        final float fff = ff * f;
        matrix.set(0.0f, 0.0f, 0.0f, 1.0f, fff, ff, f, 0.0f, 6.0f * fff, 2.0f * ff, 0.0f, 0.0f, 6.0f * fff, 0.0f, 0.0f, 0.0f);
    }
    
    public void smooth() {
        this.smooth = true;
    }
    
    public void noSmooth() {
        this.smooth = false;
    }
    
    public void imageMode(final int mode) {
        if (mode == 0 || mode == 1 || mode == 3) {
            this.imageMode = mode;
            return;
        }
        final String msg = "imageMode() only works with CORNER, CORNERS, or CENTER";
        throw new RuntimeException(msg);
    }
    
    public void image(final PImage image, final float x, final float y) {
        if (image.width == -1 || image.height == -1) {
            return;
        }
        if (this.imageMode == 0 || this.imageMode == 1) {
            this.imageImpl(image, x, y, x + image.width, y + image.height, 0, 0, image.width, image.height);
        }
        else if (this.imageMode == 3) {
            final float x2 = x - image.width / 2;
            final float y2 = y - image.height / 2;
            this.imageImpl(image, x2, y2, x2 + image.width, y2 + image.height, 0, 0, image.width, image.height);
        }
    }
    
    public void image(final PImage image, final float x, final float y, final float c, final float d) {
        this.image(image, x, y, c, d, 0, 0, image.width, image.height);
    }
    
    public void image(final PImage image, float a, float b, float c, float d, final int u1, final int v1, final int u2, final int v2) {
        if (image.width == -1 || image.height == -1) {
            return;
        }
        if (this.imageMode == 0) {
            if (c < 0.0f) {
                a += c;
                c = -c;
            }
            if (d < 0.0f) {
                b += d;
                d = -d;
            }
            this.imageImpl(image, a, b, a + c, b + d, u1, v1, u2, v2);
        }
        else if (this.imageMode == 1) {
            if (c < a) {
                final float temp = a;
                a = c;
                c = temp;
            }
            if (d < b) {
                final float temp = b;
                b = d;
                d = temp;
            }
            this.imageImpl(image, a, b, c, d, u1, v1, u2, v2);
        }
        else if (this.imageMode == 3) {
            if (c < 0.0f) {
                c = -c;
            }
            if (d < 0.0f) {
                d = -d;
            }
            final float x1 = a - c / 2.0f;
            final float y1 = b - d / 2.0f;
            this.imageImpl(image, x1, y1, x1 + c, y1 + d, u1, v1, u2, v2);
        }
    }
    
    protected void imageImpl(final PImage image, final float x1, final float y1, final float x2, final float y2, final int u1, final int v1, final int u2, final int v2) {
        final boolean savedStroke = this.stroke;
        final int savedTextureMode = this.textureMode;
        this.stroke = false;
        this.textureMode = 2;
        this.beginShape(16);
        this.texture(image);
        this.vertex(x1, y1, u1, v1);
        this.vertex(x1, y2, u1, v2);
        this.vertex(x2, y2, u2, v2);
        this.vertex(x2, y1, u2, v1);
        this.endShape();
        this.stroke = savedStroke;
        this.textureMode = savedTextureMode;
    }
    
    public void shapeMode(final int mode) {
        this.shapeMode = mode;
    }
    
    public void shape(final PShape shape) {
        if (shape.isVisible()) {
            if (this.shapeMode == 3) {
                this.pushMatrix();
                this.translate(-shape.getWidth() / 2.0f, -shape.getHeight() / 2.0f);
            }
            shape.draw(this);
            if (this.shapeMode == 3) {
                this.popMatrix();
            }
        }
    }
    
    public void shape(final PShape shape, final float x, final float y) {
        if (shape.isVisible()) {
            this.pushMatrix();
            if (this.shapeMode == 3) {
                this.translate(x - shape.getWidth() / 2.0f, y - shape.getHeight() / 2.0f);
            }
            else if (this.shapeMode == 0 || this.shapeMode == 1) {
                this.translate(x, y);
            }
            shape.draw(this);
            this.popMatrix();
        }
    }
    
    public void shape(final PShape shape, final float x, final float y, float c, float d) {
        if (shape.isVisible()) {
            this.pushMatrix();
            if (this.shapeMode == 3) {
                this.translate(x - c / 2.0f, y - d / 2.0f);
                this.scale(c / shape.getWidth(), d / shape.getHeight());
            }
            else if (this.shapeMode == 0) {
                this.translate(x, y);
                this.scale(c / shape.getWidth(), d / shape.getHeight());
            }
            else if (this.shapeMode == 1) {
                c -= x;
                d -= y;
                this.translate(x, y);
                this.scale(c / shape.getWidth(), d / shape.getHeight());
            }
            shape.draw(this);
            this.popMatrix();
        }
    }
    
    public void textAlign(final int align) {
        this.textAlign(align, 0);
    }
    
    public void textAlign(final int alignX, final int alignY) {
        this.textAlign = alignX;
        this.textAlignY = alignY;
    }
    
    public float textAscent() {
        if (this.textFont == null) {
            this.defaultFontOrDeath("textAscent");
        }
        return this.textFont.ascent() * ((this.textMode == 256) ? this.textFont.size : this.textSize);
    }
    
    public float textDescent() {
        if (this.textFont == null) {
            this.defaultFontOrDeath("textDescent");
        }
        return this.textFont.descent() * ((this.textMode == 256) ? this.textFont.size : this.textSize);
    }
    
    public void textFont(final PFont which) {
        if (which != null) {
            this.textFont = which;
            if (this.hints[3]) {
                which.findFont();
            }
            this.textSize(which.size);
            return;
        }
        throw new RuntimeException("A null PFont was passed to textFont()");
    }
    
    public void textFont(final PFont which, final float size) {
        this.textFont(which);
        this.textSize(size);
    }
    
    public void textLeading(final float leading) {
        this.textLeading = leading;
    }
    
    public void textMode(final int mode) {
        if (mode == 37 || mode == 39) {
            showWarning("Since Processing beta, textMode() is now textAlign().");
            return;
        }
        if (this.textModeCheck(mode)) {
            this.textMode = mode;
        }
        else {
            String modeStr = String.valueOf(mode);
            switch (mode) {
                case 256: {
                    modeStr = "SCREEN";
                    break;
                }
                case 4: {
                    modeStr = "MODEL";
                    break;
                }
                case 5: {
                    modeStr = "SHAPE";
                    break;
                }
            }
            showWarning("textMode(" + modeStr + ") is not supported by this renderer.");
        }
    }
    
    protected boolean textModeCheck(final int mode) {
        return true;
    }
    
    public void textSize(final float size) {
        if (this.textFont == null) {
            this.defaultFontOrDeath("textSize", size);
        }
        this.textSize = size;
        this.textLeading = (this.textAscent() + this.textDescent()) * 1.275f;
    }
    
    public float textWidth(final char c) {
        this.textWidthBuffer[0] = c;
        return this.textWidthImpl(this.textWidthBuffer, 0, 1);
    }
    
    public float textWidth(final String str) {
        if (this.textFont == null) {
            this.defaultFontOrDeath("textWidth");
        }
        final int length = str.length();
        if (length > this.textWidthBuffer.length) {
            this.textWidthBuffer = new char[length + 10];
        }
        str.getChars(0, length, this.textWidthBuffer, 0);
        float wide = 0.0f;
        int index = 0;
        int start = 0;
        while (index < length) {
            if (this.textWidthBuffer[index] == '\n') {
                wide = Math.max(wide, this.textWidthImpl(this.textWidthBuffer, start, index));
                start = index + 1;
            }
            ++index;
        }
        if (start < length) {
            wide = Math.max(wide, this.textWidthImpl(this.textWidthBuffer, start, index));
        }
        return wide;
    }
    
    public float textWidth(final char[] chars, final int start, final int length) {
        return this.textWidthImpl(chars, start, start + length);
    }
    
    protected float textWidthImpl(final char[] buffer, final int start, final int stop) {
        float wide = 0.0f;
        for (int i = start; i < stop; ++i) {
            wide += this.textFont.width(buffer[i]) * this.textSize;
        }
        return wide;
    }
    
    public void text(final char c) {
        this.text(c, this.textX, this.textY, this.textZ);
    }
    
    public void text(final char c, final float x, float y) {
        if (this.textFont == null) {
            this.defaultFontOrDeath("text");
        }
        if (this.textMode == 256) {
            this.loadPixels();
        }
        if (this.textAlignY == 3) {
            y += this.textAscent() / 2.0f;
        }
        else if (this.textAlignY == 101) {
            y += this.textAscent();
        }
        else if (this.textAlignY == 102) {
            y -= this.textDescent();
        }
        this.textBuffer[0] = c;
        this.textLineAlignImpl(this.textBuffer, 0, 1, x, y);
        if (this.textMode == 256) {
            this.updatePixels();
        }
    }
    
    public void text(final char c, final float x, final float y, final float z) {
        if (z != 0.0f) {
            this.translate(0.0f, 0.0f, z);
        }
        this.text(c, x, y);
        this.textZ = z;
        if (z != 0.0f) {
            this.translate(0.0f, 0.0f, -z);
        }
    }
    
    public void text(final String str) {
        this.text(str, this.textX, this.textY, this.textZ);
    }
    
    public void text(final String str, final float x, final float y) {
        if (this.textFont == null) {
            this.defaultFontOrDeath("text");
        }
        if (this.textMode == 256) {
            this.loadPixels();
        }
        final int length = str.length();
        if (length > this.textBuffer.length) {
            this.textBuffer = new char[length + 10];
        }
        str.getChars(0, length, this.textBuffer, 0);
        this.text(this.textBuffer, 0, length, x, y);
    }
    
    public void text(final char[] chars, int start, final int stop, final float x, float y) {
        float high = 0.0f;
        for (int i = start; i < stop; ++i) {
            if (chars[i] == '\n') {
                high += this.textLeading;
            }
        }
        if (this.textAlignY == 3) {
            y += (this.textAscent() - high) / 2.0f;
        }
        else if (this.textAlignY == 101) {
            y += this.textAscent();
        }
        else if (this.textAlignY == 102) {
            y -= this.textDescent() + high;
        }
        int index;
        for (index = 0; index < stop; ++index) {
            if (chars[index] == '\n') {
                this.textLineAlignImpl(chars, start, index, x, y);
                start = index + 1;
                y += this.textLeading;
            }
        }
        if (start < stop) {
            this.textLineAlignImpl(chars, start, index, x, y);
        }
        if (this.textMode == 256) {
            this.updatePixels();
        }
    }
    
    public void text(final String str, final float x, final float y, final float z) {
        if (z != 0.0f) {
            this.translate(0.0f, 0.0f, z);
        }
        this.text(str, x, y);
        this.textZ = z;
        if (z != 0.0f) {
            this.translate(0.0f, 0.0f, -z);
        }
    }
    
    public void text(final char[] chars, final int start, final int stop, final float x, final float y, final float z) {
        if (z != 0.0f) {
            this.translate(0.0f, 0.0f, z);
        }
        this.text(chars, start, stop, x, y);
        this.textZ = z;
        if (z != 0.0f) {
            this.translate(0.0f, 0.0f, -z);
        }
    }
    
    public void text(final String str, float x1, float y1, float x2, float y2) {
        if (this.textFont == null) {
            this.defaultFontOrDeath("text");
        }
        if (this.textMode == 256) {
            this.loadPixels();
        }
        switch (this.rectMode) {
            case 0: {
                x2 += x1;
                y2 += y1;
                break;
            }
            case 2: {
                final float hradius = x2;
                final float vradius = y2;
                x2 = x1 + hradius;
                y2 = y1 + vradius;
                x1 -= hradius;
                y1 -= vradius;
                break;
            }
            case 3: {
                final float hradius = x2 / 2.0f;
                final float vradius = y2 / 2.0f;
                x2 = x1 + hradius;
                y2 = y1 + vradius;
                x1 -= hradius;
                y1 -= vradius;
                break;
            }
        }
        if (x2 < x1) {
            final float temp = x1;
            x1 = x2;
            x2 = temp;
        }
        if (y2 < y1) {
            final float temp = y1;
            y1 = y2;
            y2 = temp;
        }
        final float boxWidth = x2 - x1;
        final float spaceWidth = this.textWidth(' ');
        if (this.textBreakStart == null) {
            this.textBreakStart = new int[20];
            this.textBreakStop = new int[20];
        }
        this.textBreakCount = 0;
        int length = str.length();
        if (length + 1 > this.textBuffer.length) {
            this.textBuffer = new char[length + 1];
        }
        str.getChars(0, length, this.textBuffer, 0);
        this.textBuffer[length++] = '\n';
        int sentenceStart = 0;
        for (int i = 0; i < length; ++i) {
            if (this.textBuffer[i] == '\n') {
                final boolean legit = this.textSentence(this.textBuffer, sentenceStart, i, boxWidth, spaceWidth);
                if (!legit) {
                    break;
                }
                sentenceStart = i + 1;
            }
        }
        float lineX = x1;
        if (this.textAlign == 3) {
            lineX += boxWidth / 2.0f;
        }
        else if (this.textAlign == 39) {
            lineX = x2;
        }
        final float boxHeight = y2 - y1;
        final float topAndBottom = this.textAscent() + this.textDescent();
        final int lineFitCount = 1 + PApplet.floor((boxHeight - topAndBottom) / this.textLeading);
        final int lineCount = Math.min(this.textBreakCount, lineFitCount);
        if (this.textAlignY == 3) {
            final float lineHigh = this.textAscent() + this.textLeading * (lineCount - 1);
            float y3 = y1 + this.textAscent() + (boxHeight - lineHigh) / 2.0f;
            for (int j = 0; j < lineCount; ++j) {
                this.textLineAlignImpl(this.textBuffer, this.textBreakStart[j], this.textBreakStop[j], lineX, y3);
                y3 += this.textLeading;
            }
        }
        else if (this.textAlignY == 102) {
            float y4 = y2 - this.textDescent() - this.textLeading * (lineCount - 1);
            for (int k = 0; k < lineCount; ++k) {
                this.textLineAlignImpl(this.textBuffer, this.textBreakStart[k], this.textBreakStop[k], lineX, y4);
                y4 += this.textLeading;
            }
        }
        else {
            float y4 = y1 + this.textAscent();
            for (int k = 0; k < lineCount; ++k) {
                this.textLineAlignImpl(this.textBuffer, this.textBreakStart[k], this.textBreakStop[k], lineX, y4);
                y4 += this.textLeading;
            }
        }
        if (this.textMode == 256) {
            this.updatePixels();
        }
    }
    
    protected boolean textSentence(final char[] buffer, final int start, final int stop, final float boxWidth, final float spaceWidth) {
        float runningX = 0.0f;
        int lineStart = start;
        int wordStart = start;
        int index = start;
        while (index <= stop) {
            if (buffer[index] == ' ' || index == stop) {
                float wordWidth = this.textWidthImpl(buffer, wordStart, index);
                if (runningX + wordWidth > boxWidth) {
                    Label_0134: {
                        if (runningX == 0.0f) {
                            while (--index != wordStart) {
                                wordWidth = this.textWidthImpl(buffer, wordStart, index);
                                if (wordWidth <= boxWidth) {
                                    this.textSentenceBreak(lineStart, index);
                                    break Label_0134;
                                }
                            }
                            return false;
                        }
                        index = wordStart;
                        this.textSentenceBreak(lineStart, index);
                        while (index < stop) {
                            if (buffer[index] != ' ') {
                                break;
                            }
                            ++index;
                        }
                    }
                    lineStart = index;
                    wordStart = index;
                    runningX = 0.0f;
                }
                else if (index == stop) {
                    this.textSentenceBreak(lineStart, index);
                    ++index;
                }
                else {
                    runningX += wordWidth + spaceWidth;
                    wordStart = index + 1;
                    ++index;
                }
            }
            else {
                ++index;
            }
        }
        return true;
    }
    
    protected void textSentenceBreak(final int start, final int stop) {
        if (this.textBreakCount == this.textBreakStart.length) {
            this.textBreakStart = PApplet.expand(this.textBreakStart);
            this.textBreakStop = PApplet.expand(this.textBreakStop);
        }
        this.textBreakStart[this.textBreakCount] = start;
        this.textBreakStop[this.textBreakCount] = stop;
        ++this.textBreakCount;
    }
    
    public void text(final String s, final float x1, final float y1, final float x2, final float y2, final float z) {
        if (z != 0.0f) {
            this.translate(0.0f, 0.0f, z);
        }
        this.text(s, x1, y1, x2, y2);
        this.textZ = z;
        if (z != 0.0f) {
            this.translate(0.0f, 0.0f, -z);
        }
    }
    
    public void text(final int num, final float x, final float y) {
        this.text(String.valueOf(num), x, y);
    }
    
    public void text(final int num, final float x, final float y, final float z) {
        this.text(String.valueOf(num), x, y, z);
    }
    
    public void text(final float num, final float x, final float y) {
        this.text(PApplet.nfs(num, 0, 3), x, y);
    }
    
    public void text(final float num, final float x, final float y, final float z) {
        this.text(PApplet.nfs(num, 0, 3), x, y, z);
    }
    
    protected void textLineAlignImpl(final char[] buffer, final int start, final int stop, float x, final float y) {
        if (this.textAlign == 3) {
            x -= this.textWidthImpl(buffer, start, stop) / 2.0f;
        }
        else if (this.textAlign == 39) {
            x -= this.textWidthImpl(buffer, start, stop);
        }
        this.textLineImpl(buffer, start, stop, x, y);
    }
    
    protected void textLineImpl(final char[] buffer, final int start, final int stop, float x, final float y) {
        for (int index = start; index < stop; ++index) {
            this.textCharImpl(buffer[index], x, y);
            x += this.textWidth(buffer[index]);
        }
        this.textX = x;
        this.textY = y;
        this.textZ = 0.0f;
    }
    
    protected void textCharImpl(final char ch, final float x, final float y) {
        final PFont.Glyph glyph = this.textFont.getGlyph(ch);
        if (glyph != null) {
            if (this.textMode == 4) {
                final float high = glyph.height / this.textFont.size;
                final float bwidth = glyph.width / this.textFont.size;
                final float lextent = glyph.leftExtent / this.textFont.size;
                final float textent = glyph.topExtent / this.textFont.size;
                final float x2 = x + lextent * this.textSize;
                final float y2 = y - textent * this.textSize;
                final float x3 = x2 + bwidth * this.textSize;
                final float y3 = y2 + high * this.textSize;
                this.textCharModelImpl(glyph.image, x2, y2, x3, y3, glyph.width, glyph.height);
            }
            else if (this.textMode == 256) {
                final int xx = (int)x + glyph.leftExtent;
                final int yy = (int)y - glyph.topExtent;
                final int w0 = glyph.width;
                final int h0 = glyph.height;
                this.textCharScreenImpl(glyph.image, xx, yy, w0, h0);
            }
        }
    }
    
    protected void textCharModelImpl(final PImage glyph, final float x1, final float y1, final float x2, final float y2, final int u2, final int v2) {
        final boolean savedTint = this.tint;
        final int savedTintColor = this.tintColor;
        final float savedTintR = this.tintR;
        final float savedTintG = this.tintG;
        final float savedTintB = this.tintB;
        final float savedTintA = this.tintA;
        final boolean savedTintAlpha = this.tintAlpha;
        this.tint = true;
        this.tintColor = this.fillColor;
        this.tintR = this.fillR;
        this.tintG = this.fillG;
        this.tintB = this.fillB;
        this.tintA = this.fillA;
        this.tintAlpha = this.fillAlpha;
        this.imageImpl(glyph, x1, y1, x2, y2, 0, 0, u2, v2);
        this.tint = savedTint;
        this.tintColor = savedTintColor;
        this.tintR = savedTintR;
        this.tintG = savedTintG;
        this.tintB = savedTintB;
        this.tintA = savedTintA;
        this.tintAlpha = savedTintAlpha;
    }
    
    protected void textCharScreenImpl(final PImage glyph, int xx, int yy, int w0, int h0) {
        int x0 = 0;
        int y0 = 0;
        if (xx >= this.width || yy >= this.height || xx + w0 < 0 || yy + h0 < 0) {
            return;
        }
        if (xx < 0) {
            x0 -= xx;
            w0 += xx;
            xx = 0;
        }
        if (yy < 0) {
            y0 -= yy;
            h0 += yy;
            yy = 0;
        }
        if (xx + w0 > this.width) {
            w0 -= xx + w0 - this.width;
        }
        if (yy + h0 > this.height) {
            h0 -= yy + h0 - this.height;
        }
        final int fr = this.fillRi;
        final int fg = this.fillGi;
        final int fb = this.fillBi;
        final int fa = this.fillAi;
        final int[] pixels1 = glyph.pixels;
        for (int row = y0; row < y0 + h0; ++row) {
            for (int col = x0; col < x0 + w0; ++col) {
                final int a1 = fa * pixels1[row * glyph.width + col] >> 8;
                final int a2 = a1 ^ 0xFF;
                final int p2 = this.pixels[(yy + row - y0) * this.width + (xx + col - x0)];
                this.pixels[(yy + row - y0) * this.width + xx + col - x0] = (0xFF000000 | (a1 * fr + a2 * (p2 >> 16 & 0xFF) & 0xFF00) << 8 | (a1 * fg + a2 * (p2 >> 8 & 0xFF) & 0xFF00) | a1 * fb + a2 * (p2 & 0xFF) >> 8);
            }
        }
    }
    
    public void pushMatrix() {
        showMethodWarning("pushMatrix");
    }
    
    public void popMatrix() {
        showMethodWarning("popMatrix");
    }
    
    public void translate(final float tx, final float ty) {
        showMissingWarning("translate");
    }
    
    public void translate(final float tx, final float ty, final float tz) {
        showMissingWarning("translate");
    }
    
    public void rotate(final float angle) {
        showMissingWarning("rotate");
    }
    
    public void rotateX(final float angle) {
        showMethodWarning("rotateX");
    }
    
    public void rotateY(final float angle) {
        showMethodWarning("rotateY");
    }
    
    public void rotateZ(final float angle) {
        showMethodWarning("rotateZ");
    }
    
    public void rotate(final float angle, final float vx, final float vy, final float vz) {
        showMissingWarning("rotate");
    }
    
    public void scale(final float s) {
        showMissingWarning("scale");
    }
    
    public void scale(final float sx, final float sy) {
        showMissingWarning("scale");
    }
    
    public void scale(final float x, final float y, final float z) {
        showMissingWarning("scale");
    }
    
    public void skewX(final float angle) {
        showMissingWarning("skewX");
    }
    
    public void skewY(final float angle) {
        showMissingWarning("skewY");
    }
    
    public void resetMatrix() {
        showMethodWarning("resetMatrix");
    }
    
    public void applyMatrix(final PMatrix source) {
        if (source instanceof PMatrix2D) {
            this.applyMatrix((PMatrix2D)source);
        }
        else if (source instanceof PMatrix3D) {
            this.applyMatrix((PMatrix3D)source);
        }
    }
    
    public void applyMatrix(final PMatrix2D source) {
        this.applyMatrix(source.m00, source.m01, source.m02, source.m10, source.m11, source.m12);
    }
    
    public void applyMatrix(final float n00, final float n01, final float n02, final float n10, final float n11, final float n12) {
        showMissingWarning("applyMatrix");
    }
    
    public void applyMatrix(final PMatrix3D source) {
        this.applyMatrix(source.m00, source.m01, source.m02, source.m03, source.m10, source.m11, source.m12, source.m13, source.m20, source.m21, source.m22, source.m23, source.m30, source.m31, source.m32, source.m33);
    }
    
    public void applyMatrix(final float n00, final float n01, final float n02, final float n03, final float n10, final float n11, final float n12, final float n13, final float n20, final float n21, final float n22, final float n23, final float n30, final float n31, final float n32, final float n33) {
        showMissingWarning("applyMatrix");
    }
    
    public PMatrix getMatrix() {
        showMissingWarning("getMatrix");
        return null;
    }
    
    public PMatrix2D getMatrix(final PMatrix2D target) {
        showMissingWarning("getMatrix");
        return null;
    }
    
    public PMatrix3D getMatrix(final PMatrix3D target) {
        showMissingWarning("getMatrix");
        return null;
    }
    
    public void setMatrix(final PMatrix source) {
        if (source instanceof PMatrix2D) {
            this.setMatrix((PMatrix2D)source);
        }
        else if (source instanceof PMatrix3D) {
            this.setMatrix((PMatrix3D)source);
        }
    }
    
    public void setMatrix(final PMatrix2D source) {
        showMissingWarning("setMatrix");
    }
    
    public void setMatrix(final PMatrix3D source) {
        showMissingWarning("setMatrix");
    }
    
    public void printMatrix() {
        showMethodWarning("printMatrix");
    }
    
    public void beginCamera() {
        showMethodWarning("beginCamera");
    }
    
    public void endCamera() {
        showMethodWarning("endCamera");
    }
    
    public void camera() {
        showMissingWarning("camera");
    }
    
    public void camera(final float eyeX, final float eyeY, final float eyeZ, final float centerX, final float centerY, final float centerZ, final float upX, final float upY, final float upZ) {
        showMissingWarning("camera");
    }
    
    public void printCamera() {
        showMethodWarning("printCamera");
    }
    
    public void ortho() {
        showMissingWarning("ortho");
    }
    
    public void ortho(final float left, final float right, final float bottom, final float top, final float near, final float far) {
        showMissingWarning("ortho");
    }
    
    public void perspective() {
        showMissingWarning("perspective");
    }
    
    public void perspective(final float fovy, final float aspect, final float zNear, final float zFar) {
        showMissingWarning("perspective");
    }
    
    public void frustum(final float left, final float right, final float bottom, final float top, final float near, final float far) {
        showMethodWarning("frustum");
    }
    
    public void printProjection() {
        showMethodWarning("printCamera");
    }
    
    public float screenX(final float x, final float y) {
        showMissingWarning("screenX");
        return 0.0f;
    }
    
    public float screenY(final float x, final float y) {
        showMissingWarning("screenY");
        return 0.0f;
    }
    
    public float screenX(final float x, final float y, final float z) {
        showMissingWarning("screenX");
        return 0.0f;
    }
    
    public float screenY(final float x, final float y, final float z) {
        showMissingWarning("screenY");
        return 0.0f;
    }
    
    public float screenZ(final float x, final float y, final float z) {
        showMissingWarning("screenZ");
        return 0.0f;
    }
    
    public float modelX(final float x, final float y, final float z) {
        showMissingWarning("modelX");
        return 0.0f;
    }
    
    public float modelY(final float x, final float y, final float z) {
        showMissingWarning("modelY");
        return 0.0f;
    }
    
    public float modelZ(final float x, final float y, final float z) {
        showMissingWarning("modelZ");
        return 0.0f;
    }
    
    public void pushStyle() {
        if (this.styleStackDepth == this.styleStack.length) {
            this.styleStack = (PStyle[])PApplet.expand(this.styleStack);
        }
        if (this.styleStack[this.styleStackDepth] == null) {
            this.styleStack[this.styleStackDepth] = new PStyle();
        }
        final PStyle s = this.styleStack[this.styleStackDepth++];
        this.getStyle(s);
    }
    
    public void popStyle() {
        if (this.styleStackDepth == 0) {
            throw new RuntimeException("Too many popStyle() without enough pushStyle()");
        }
        --this.styleStackDepth;
        this.style(this.styleStack[this.styleStackDepth]);
    }
    
    public void style(final PStyle s) {
        this.imageMode(s.imageMode);
        this.rectMode(s.rectMode);
        this.ellipseMode(s.ellipseMode);
        this.shapeMode(s.shapeMode);
        if (s.tint) {
            this.tint(s.tintColor);
        }
        else {
            this.noTint();
        }
        if (s.fill) {
            this.fill(s.fillColor);
        }
        else {
            this.noFill();
        }
        if (s.stroke) {
            this.stroke(s.strokeColor);
        }
        else {
            this.noStroke();
        }
        this.strokeWeight(s.strokeWeight);
        this.strokeCap(s.strokeCap);
        this.strokeJoin(s.strokeJoin);
        this.colorMode(1, 1.0f);
        this.ambient(s.ambientR, s.ambientG, s.ambientB);
        this.emissive(s.emissiveR, s.emissiveG, s.emissiveB);
        this.specular(s.specularR, s.specularG, s.specularB);
        this.shininess(s.shininess);
        this.colorMode(s.colorMode, s.colorModeX, s.colorModeY, s.colorModeZ, s.colorModeA);
        if (s.textFont != null) {
            this.textFont(s.textFont, s.textSize);
            this.textLeading(s.textLeading);
        }
        this.textAlign(s.textAlign, s.textAlignY);
        this.textMode(s.textMode);
    }
    
    public PStyle getStyle() {
        return this.getStyle(null);
    }
    
    public PStyle getStyle(PStyle s) {
        if (s == null) {
            s = new PStyle();
        }
        s.imageMode = this.imageMode;
        s.rectMode = this.rectMode;
        s.ellipseMode = this.ellipseMode;
        s.shapeMode = this.shapeMode;
        s.colorMode = this.colorMode;
        s.colorModeX = this.colorModeX;
        s.colorModeY = this.colorModeY;
        s.colorModeZ = this.colorModeZ;
        s.colorModeA = this.colorModeA;
        s.tint = this.tint;
        s.tintColor = this.tintColor;
        s.fill = this.fill;
        s.fillColor = this.fillColor;
        s.stroke = this.stroke;
        s.strokeColor = this.strokeColor;
        s.strokeWeight = this.strokeWeight;
        s.strokeCap = this.strokeCap;
        s.strokeJoin = this.strokeJoin;
        s.ambientR = this.ambientR;
        s.ambientG = this.ambientG;
        s.ambientB = this.ambientB;
        s.specularR = this.specularR;
        s.specularG = this.specularG;
        s.specularB = this.specularB;
        s.emissiveR = this.emissiveR;
        s.emissiveG = this.emissiveG;
        s.emissiveB = this.emissiveB;
        s.shininess = this.shininess;
        s.textFont = this.textFont;
        s.textAlign = this.textAlign;
        s.textAlignY = this.textAlignY;
        s.textMode = this.textMode;
        s.textSize = this.textSize;
        s.textLeading = this.textLeading;
        return s;
    }
    
    public void strokeWeight(final float weight) {
        this.strokeWeight = weight;
    }
    
    public void strokeJoin(final int join) {
        this.strokeJoin = join;
    }
    
    public void strokeCap(final int cap) {
        this.strokeCap = cap;
    }
    
    public void noStroke() {
        this.stroke = false;
    }
    
    public void stroke(final int rgb) {
        this.colorCalc(rgb);
        this.strokeFromCalc();
    }
    
    public void stroke(final int rgb, final float alpha) {
        this.colorCalc(rgb, alpha);
        this.strokeFromCalc();
    }
    
    public void stroke(final float gray) {
        this.colorCalc(gray);
        this.strokeFromCalc();
    }
    
    public void stroke(final float gray, final float alpha) {
        this.colorCalc(gray, alpha);
        this.strokeFromCalc();
    }
    
    public void stroke(final float x, final float y, final float z) {
        this.colorCalc(x, y, z);
        this.strokeFromCalc();
    }
    
    public void stroke(final float x, final float y, final float z, final float a) {
        this.colorCalc(x, y, z, a);
        this.strokeFromCalc();
    }
    
    protected void strokeFromCalc() {
        this.stroke = true;
        this.strokeR = this.calcR;
        this.strokeG = this.calcG;
        this.strokeB = this.calcB;
        this.strokeA = this.calcA;
        this.strokeRi = this.calcRi;
        this.strokeGi = this.calcGi;
        this.strokeBi = this.calcBi;
        this.strokeAi = this.calcAi;
        this.strokeColor = this.calcColor;
        this.strokeAlpha = this.calcAlpha;
    }
    
    public void noTint() {
        this.tint = false;
    }
    
    public void tint(final int rgb) {
        this.colorCalc(rgb);
        this.tintFromCalc();
    }
    
    public void tint(final int rgb, final float alpha) {
        this.colorCalc(rgb, alpha);
        this.tintFromCalc();
    }
    
    public void tint(final float gray) {
        this.colorCalc(gray);
        this.tintFromCalc();
    }
    
    public void tint(final float gray, final float alpha) {
        this.colorCalc(gray, alpha);
        this.tintFromCalc();
    }
    
    public void tint(final float x, final float y, final float z) {
        this.colorCalc(x, y, z);
        this.tintFromCalc();
    }
    
    public void tint(final float x, final float y, final float z, final float a) {
        this.colorCalc(x, y, z, a);
        this.tintFromCalc();
    }
    
    protected void tintFromCalc() {
        this.tint = true;
        this.tintR = this.calcR;
        this.tintG = this.calcG;
        this.tintB = this.calcB;
        this.tintA = this.calcA;
        this.tintRi = this.calcRi;
        this.tintGi = this.calcGi;
        this.tintBi = this.calcBi;
        this.tintAi = this.calcAi;
        this.tintColor = this.calcColor;
        this.tintAlpha = this.calcAlpha;
    }
    
    public void noFill() {
        this.fill = false;
    }
    
    public void fill(final int rgb) {
        this.colorCalc(rgb);
        this.fillFromCalc();
    }
    
    public void fill(final int rgb, final float alpha) {
        this.colorCalc(rgb, alpha);
        this.fillFromCalc();
    }
    
    public void fill(final float gray) {
        this.colorCalc(gray);
        this.fillFromCalc();
    }
    
    public void fill(final float gray, final float alpha) {
        this.colorCalc(gray, alpha);
        this.fillFromCalc();
    }
    
    public void fill(final float x, final float y, final float z) {
        this.colorCalc(x, y, z);
        this.fillFromCalc();
    }
    
    public void fill(final float x, final float y, final float z, final float a) {
        this.colorCalc(x, y, z, a);
        this.fillFromCalc();
    }
    
    protected void fillFromCalc() {
        this.fill = true;
        this.fillR = this.calcR;
        this.fillG = this.calcG;
        this.fillB = this.calcB;
        this.fillA = this.calcA;
        this.fillRi = this.calcRi;
        this.fillGi = this.calcGi;
        this.fillBi = this.calcBi;
        this.fillAi = this.calcAi;
        this.fillColor = this.calcColor;
        this.fillAlpha = this.calcAlpha;
    }
    
    public void ambient(final int rgb) {
        this.colorCalc(rgb);
        this.ambientFromCalc();
    }
    
    public void ambient(final float gray) {
        this.colorCalc(gray);
        this.ambientFromCalc();
    }
    
    public void ambient(final float x, final float y, final float z) {
        this.colorCalc(x, y, z);
        this.ambientFromCalc();
    }
    
    protected void ambientFromCalc() {
        this.ambientR = this.calcR;
        this.ambientG = this.calcG;
        this.ambientB = this.calcB;
    }
    
    public void specular(final int rgb) {
        this.colorCalc(rgb);
        this.specularFromCalc();
    }
    
    public void specular(final float gray) {
        this.colorCalc(gray);
        this.specularFromCalc();
    }
    
    public void specular(final float x, final float y, final float z) {
        this.colorCalc(x, y, z);
        this.specularFromCalc();
    }
    
    protected void specularFromCalc() {
        this.specularR = this.calcR;
        this.specularG = this.calcG;
        this.specularB = this.calcB;
    }
    
    public void shininess(final float shine) {
        this.shininess = shine;
    }
    
    public void emissive(final int rgb) {
        this.colorCalc(rgb);
        this.emissiveFromCalc();
    }
    
    public void emissive(final float gray) {
        this.colorCalc(gray);
        this.emissiveFromCalc();
    }
    
    public void emissive(final float x, final float y, final float z) {
        this.colorCalc(x, y, z);
        this.emissiveFromCalc();
    }
    
    protected void emissiveFromCalc() {
        this.emissiveR = this.calcR;
        this.emissiveG = this.calcG;
        this.emissiveB = this.calcB;
    }
    
    public void lights() {
        showMethodWarning("lights");
    }
    
    public void noLights() {
        showMethodWarning("noLights");
    }
    
    public void ambientLight(final float red, final float green, final float blue) {
        showMethodWarning("ambientLight");
    }
    
    public void ambientLight(final float red, final float green, final float blue, final float x, final float y, final float z) {
        showMethodWarning("ambientLight");
    }
    
    public void directionalLight(final float red, final float green, final float blue, final float nx, final float ny, final float nz) {
        showMethodWarning("directionalLight");
    }
    
    public void pointLight(final float red, final float green, final float blue, final float x, final float y, final float z) {
        showMethodWarning("pointLight");
    }
    
    public void spotLight(final float red, final float green, final float blue, final float x, final float y, final float z, final float nx, final float ny, final float nz, final float angle, final float concentration) {
        showMethodWarning("spotLight");
    }
    
    public void lightFalloff(final float constant, final float linear, final float quadratic) {
        showMethodWarning("lightFalloff");
    }
    
    public void lightSpecular(final float x, final float y, final float z) {
        showMethodWarning("lightSpecular");
    }
    
    public void background(final int rgb) {
        this.colorCalc(rgb);
        this.backgroundFromCalc();
    }
    
    public void background(final int rgb, final float alpha) {
        this.colorCalc(rgb, alpha);
        this.backgroundFromCalc();
    }
    
    public void background(final float gray) {
        this.colorCalc(gray);
        this.backgroundFromCalc();
    }
    
    public void background(final float gray, final float alpha) {
        if (this.format == 1) {
            this.background(gray);
        }
        else {
            this.colorCalc(gray, alpha);
            this.backgroundFromCalc();
        }
    }
    
    public void background(final float x, final float y, final float z) {
        this.colorCalc(x, y, z);
        this.backgroundFromCalc();
    }
    
    public void background(final float x, final float y, final float z, final float a) {
        this.colorCalc(x, y, z, a);
        this.backgroundFromCalc();
    }
    
    protected void backgroundFromCalc() {
        this.backgroundR = this.calcR;
        this.backgroundG = this.calcG;
        this.backgroundB = this.calcB;
        this.backgroundA = ((this.format == 1) ? this.colorModeA : this.calcA);
        this.backgroundRi = this.calcRi;
        this.backgroundGi = this.calcGi;
        this.backgroundBi = this.calcBi;
        this.backgroundAi = ((this.format == 1) ? 255 : this.calcAi);
        this.backgroundAlpha = (this.format != 1 && this.calcAlpha);
        this.backgroundColor = this.calcColor;
        this.backgroundImpl();
    }
    
    public void background(final PImage image) {
        if (image.width != this.width || image.height != this.height) {
            throw new RuntimeException("background image must be the same size as your application");
        }
        if (image.format != 1 && image.format != 2) {
            throw new RuntimeException("background images should be RGB or ARGB");
        }
        this.backgroundColor = 0;
        this.backgroundImpl(image);
    }
    
    protected void backgroundImpl(final PImage image) {
        this.set(0, 0, image);
    }
    
    protected void backgroundImpl() {
        this.pushStyle();
        this.pushMatrix();
        this.resetMatrix();
        this.fill(this.backgroundColor);
        this.rect(0.0f, 0.0f, this.width, this.height);
        this.popMatrix();
        this.popStyle();
    }
    
    public void colorMode(final int mode) {
        this.colorMode(mode, this.colorModeX, this.colorModeY, this.colorModeZ, this.colorModeA);
    }
    
    public void colorMode(final int mode, final float max) {
        this.colorMode(mode, max, max, max, max);
    }
    
    public void colorMode(final int mode, final float maxX, final float maxY, final float maxZ) {
        this.colorMode(mode, maxX, maxY, maxZ, this.colorModeA);
    }
    
    public void colorMode(final int mode, final float maxX, final float maxY, final float maxZ, final float maxA) {
        this.colorMode = mode;
        this.colorModeX = maxX;
        this.colorModeY = maxY;
        this.colorModeZ = maxZ;
        this.colorModeA = maxA;
        this.colorModeScale = (maxA != 1.0f || maxX != maxY || maxY != maxZ || maxZ != maxA);
        this.colorModeDefault = (this.colorMode == 1 && this.colorModeA == 255.0f && this.colorModeX == 255.0f && this.colorModeY == 255.0f && this.colorModeZ == 255.0f);
    }
    
    protected void colorCalc(final int rgb) {
        if ((rgb & 0xFF000000) == 0x0 && rgb <= this.colorModeX) {
            this.colorCalc((float)rgb);
        }
        else {
            this.colorCalcARGB(rgb, this.colorModeA);
        }
    }
    
    protected void colorCalc(final int rgb, final float alpha) {
        if ((rgb & 0xFF000000) == 0x0 && rgb <= this.colorModeX) {
            this.colorCalc((float)rgb, alpha);
        }
        else {
            this.colorCalcARGB(rgb, alpha);
        }
    }
    
    protected void colorCalc(final float gray) {
        this.colorCalc(gray, this.colorModeA);
    }
    
    protected void colorCalc(float gray, float alpha) {
        if (gray > this.colorModeX) {
            gray = this.colorModeX;
        }
        if (alpha > this.colorModeA) {
            alpha = this.colorModeA;
        }
        if (gray < 0.0f) {
            gray = 0.0f;
        }
        if (alpha < 0.0f) {
            alpha = 0.0f;
        }
        this.calcR = (this.colorModeScale ? (gray / this.colorModeX) : gray);
        this.calcG = this.calcR;
        this.calcB = this.calcR;
        this.calcA = (this.colorModeScale ? (alpha / this.colorModeA) : alpha);
        this.calcRi = (int)(this.calcR * 255.0f);
        this.calcGi = (int)(this.calcG * 255.0f);
        this.calcBi = (int)(this.calcB * 255.0f);
        this.calcAi = (int)(this.calcA * 255.0f);
        this.calcColor = (this.calcAi << 24 | this.calcRi << 16 | this.calcGi << 8 | this.calcBi);
        this.calcAlpha = (this.calcAi != 255);
    }
    
    protected void colorCalc(final float x, final float y, final float z) {
        this.colorCalc(x, y, z, this.colorModeA);
    }
    
    protected void colorCalc(float x, float y, float z, float a) {
        if (x > this.colorModeX) {
            x = this.colorModeX;
        }
        if (y > this.colorModeY) {
            y = this.colorModeY;
        }
        if (z > this.colorModeZ) {
            z = this.colorModeZ;
        }
        if (a > this.colorModeA) {
            a = this.colorModeA;
        }
        if (x < 0.0f) {
            x = 0.0f;
        }
        if (y < 0.0f) {
            y = 0.0f;
        }
        if (z < 0.0f) {
            z = 0.0f;
        }
        if (a < 0.0f) {
            a = 0.0f;
        }
        Label_0473: {
            switch (this.colorMode) {
                case 1: {
                    if (this.colorModeScale) {
                        this.calcR = x / this.colorModeX;
                        this.calcG = y / this.colorModeY;
                        this.calcB = z / this.colorModeZ;
                        this.calcA = a / this.colorModeA;
                        break;
                    }
                    this.calcR = x;
                    this.calcG = y;
                    this.calcB = z;
                    this.calcA = a;
                    break;
                }
                case 3: {
                    x /= this.colorModeX;
                    y /= this.colorModeY;
                    z /= this.colorModeZ;
                    this.calcA = (this.colorModeScale ? (a / this.colorModeA) : a);
                    if (y == 0.0f) {
                        final float calcR = z;
                        this.calcB = calcR;
                        this.calcG = calcR;
                        this.calcR = calcR;
                        break;
                    }
                    final float which = (x - (int)x) * 6.0f;
                    final float f = which - (int)which;
                    final float p = z * (1.0f - y);
                    final float q = z * (1.0f - y * f);
                    final float t = z * (1.0f - y * (1.0f - f));
                    switch ((int)which) {
                        case 0: {
                            this.calcR = z;
                            this.calcG = t;
                            this.calcB = p;
                            break Label_0473;
                        }
                        case 1: {
                            this.calcR = q;
                            this.calcG = z;
                            this.calcB = p;
                            break Label_0473;
                        }
                        case 2: {
                            this.calcR = p;
                            this.calcG = z;
                            this.calcB = t;
                            break Label_0473;
                        }
                        case 3: {
                            this.calcR = p;
                            this.calcG = q;
                            this.calcB = z;
                            break Label_0473;
                        }
                        case 4: {
                            this.calcR = t;
                            this.calcG = p;
                            this.calcB = z;
                            break Label_0473;
                        }
                        case 5: {
                            this.calcR = z;
                            this.calcG = p;
                            this.calcB = q;
                            break Label_0473;
                        }
                    }
                    break;
                }
            }
        }
        this.calcRi = (int)(255.0f * this.calcR);
        this.calcGi = (int)(255.0f * this.calcG);
        this.calcBi = (int)(255.0f * this.calcB);
        this.calcAi = (int)(255.0f * this.calcA);
        this.calcColor = (this.calcAi << 24 | this.calcRi << 16 | this.calcGi << 8 | this.calcBi);
        this.calcAlpha = (this.calcAi != 255);
    }
    
    protected void colorCalcARGB(final int argb, final float alpha) {
        if (alpha == this.colorModeA) {
            this.calcAi = (argb >> 24 & 0xFF);
            this.calcColor = argb;
        }
        else {
            this.calcAi = (int)((argb >> 24 & 0xFF) * (alpha / this.colorModeA));
            this.calcColor = (this.calcAi << 24 | (argb & 0xFFFFFF));
        }
        this.calcRi = (argb >> 16 & 0xFF);
        this.calcGi = (argb >> 8 & 0xFF);
        this.calcBi = (argb & 0xFF);
        this.calcA = this.calcAi / 255.0f;
        this.calcR = this.calcRi / 255.0f;
        this.calcG = this.calcGi / 255.0f;
        this.calcB = this.calcBi / 255.0f;
        this.calcAlpha = (this.calcAi != 255);
    }
    
    public final int color(int gray) {
        if ((gray & 0xFF000000) == 0x0 && gray <= this.colorModeX) {
            if (this.colorModeDefault) {
                if (gray > 255) {
                    gray = 255;
                }
                else if (gray < 0) {
                    gray = 0;
                }
                return 0xFF000000 | gray << 16 | gray << 8 | gray;
            }
            this.colorCalc(gray);
        }
        else {
            this.colorCalcARGB(gray, this.colorModeA);
        }
        return this.calcColor;
    }
    
    public final int color(final float gray) {
        this.colorCalc(gray);
        return this.calcColor;
    }
    
    public final int color(int gray, int alpha) {
        if (this.colorModeDefault) {
            if (gray > 255) {
                gray = 255;
            }
            else if (gray < 0) {
                gray = 0;
            }
            if (alpha > 255) {
                alpha = 255;
            }
            else if (alpha < 0) {
                alpha = 0;
            }
            return (alpha & 0xFF) << 24 | gray << 16 | gray << 8 | gray;
        }
        this.colorCalc(gray, alpha);
        return this.calcColor;
    }
    
    public final int color(final int rgb, final float alpha) {
        if ((rgb & 0xFF000000) == 0x0 && rgb <= this.colorModeX) {
            this.colorCalc(rgb, alpha);
        }
        else {
            this.colorCalcARGB(rgb, alpha);
        }
        return this.calcColor;
    }
    
    public final int color(final float gray, final float alpha) {
        this.colorCalc(gray, alpha);
        return this.calcColor;
    }
    
    public final int color(int x, int y, int z) {
        if (this.colorModeDefault) {
            if (x > 255) {
                x = 255;
            }
            else if (x < 0) {
                x = 0;
            }
            if (y > 255) {
                y = 255;
            }
            else if (y < 0) {
                y = 0;
            }
            if (z > 255) {
                z = 255;
            }
            else if (z < 0) {
                z = 0;
            }
            return 0xFF000000 | x << 16 | y << 8 | z;
        }
        this.colorCalc(x, y, z);
        return this.calcColor;
    }
    
    public final int color(final float x, final float y, final float z) {
        this.colorCalc(x, y, z);
        return this.calcColor;
    }
    
    public final int color(int x, int y, int z, int a) {
        if (this.colorModeDefault) {
            if (a > 255) {
                a = 255;
            }
            else if (a < 0) {
                a = 0;
            }
            if (x > 255) {
                x = 255;
            }
            else if (x < 0) {
                x = 0;
            }
            if (y > 255) {
                y = 255;
            }
            else if (y < 0) {
                y = 0;
            }
            if (z > 255) {
                z = 255;
            }
            else if (z < 0) {
                z = 0;
            }
            return a << 24 | x << 16 | y << 8 | z;
        }
        this.colorCalc(x, y, z, a);
        return this.calcColor;
    }
    
    public final int color(final float x, final float y, final float z, final float a) {
        this.colorCalc(x, y, z, a);
        return this.calcColor;
    }
    
    public final float alpha(final int what) {
        final float c = what >> 24 & 0xFF;
        if (this.colorModeA == 255.0f) {
            return c;
        }
        return c / 255.0f * this.colorModeA;
    }
    
    public final float red(final int what) {
        final float c = what >> 16 & 0xFF;
        if (this.colorModeDefault) {
            return c;
        }
        return c / 255.0f * this.colorModeX;
    }
    
    public final float green(final int what) {
        final float c = what >> 8 & 0xFF;
        if (this.colorModeDefault) {
            return c;
        }
        return c / 255.0f * this.colorModeY;
    }
    
    public final float blue(final int what) {
        final float c = what & 0xFF;
        if (this.colorModeDefault) {
            return c;
        }
        return c / 255.0f * this.colorModeZ;
    }
    
    public final float hue(final int what) {
        if (what != this.cacheHsbKey) {
            Color.RGBtoHSB(what >> 16 & 0xFF, what >> 8 & 0xFF, what & 0xFF, this.cacheHsbValue);
            this.cacheHsbKey = what;
        }
        return this.cacheHsbValue[0] * this.colorModeX;
    }
    
    public final float saturation(final int what) {
        if (what != this.cacheHsbKey) {
            Color.RGBtoHSB(what >> 16 & 0xFF, what >> 8 & 0xFF, what & 0xFF, this.cacheHsbValue);
            this.cacheHsbKey = what;
        }
        return this.cacheHsbValue[1] * this.colorModeY;
    }
    
    public final float brightness(final int what) {
        if (what != this.cacheHsbKey) {
            Color.RGBtoHSB(what >> 16 & 0xFF, what >> 8 & 0xFF, what & 0xFF, this.cacheHsbValue);
            this.cacheHsbKey = what;
        }
        return this.cacheHsbValue[2] * this.colorModeZ;
    }
    
    public int lerpColor(final int c1, final int c2, final float amt) {
        return lerpColor(c1, c2, amt, this.colorMode);
    }
    
    public static int lerpColor(final int c1, final int c2, final float amt, final int mode) {
        if (mode == 1) {
            final float a1 = c1 >> 24 & 0xFF;
            final float r1 = c1 >> 16 & 0xFF;
            final float g1 = c1 >> 8 & 0xFF;
            final float b1 = c1 & 0xFF;
            final float a2 = c2 >> 24 & 0xFF;
            final float r2 = c2 >> 16 & 0xFF;
            final float g2 = c2 >> 8 & 0xFF;
            final float b2 = c2 & 0xFF;
            return (int)(a1 + (a2 - a1) * amt) << 24 | (int)(r1 + (r2 - r1) * amt) << 16 | (int)(g1 + (g2 - g1) * amt) << 8 | (int)(b1 + (b2 - b1) * amt);
        }
        if (mode == 3) {
            if (PGraphics.lerpColorHSB1 == null) {
                PGraphics.lerpColorHSB1 = new float[3];
                PGraphics.lerpColorHSB2 = new float[3];
            }
            final float a1 = c1 >> 24 & 0xFF;
            final float a3 = c2 >> 24 & 0xFF;
            final int alfa = (int)(a1 + (a3 - a1) * amt) << 24;
            Color.RGBtoHSB(c1 >> 16 & 0xFF, c1 >> 8 & 0xFF, c1 & 0xFF, PGraphics.lerpColorHSB1);
            Color.RGBtoHSB(c2 >> 16 & 0xFF, c2 >> 8 & 0xFF, c2 & 0xFF, PGraphics.lerpColorHSB2);
            final float ho = PApplet.lerp(PGraphics.lerpColorHSB1[0], PGraphics.lerpColorHSB2[0], amt);
            final float so = PApplet.lerp(PGraphics.lerpColorHSB1[1], PGraphics.lerpColorHSB2[1], amt);
            final float bo = PApplet.lerp(PGraphics.lerpColorHSB1[2], PGraphics.lerpColorHSB2[2], amt);
            return alfa | (Color.HSBtoRGB(ho, so, bo) & 0xFFFFFF);
        }
        return 0;
    }
    
    public void beginRaw(final PGraphics rawGraphics) {
        (this.raw = rawGraphics).beginDraw();
    }
    
    public void endRaw() {
        if (this.raw != null) {
            this.flush();
            this.raw.endDraw();
            this.raw.dispose();
            this.raw = null;
        }
    }
    
    public static void showWarning(final String msg) {
        if (PGraphics.warnings == null) {
            PGraphics.warnings = new HashMap<String, Object>();
        }
        if (!PGraphics.warnings.containsKey(msg)) {
            System.err.println(msg);
            PGraphics.warnings.put(msg, new Object());
        }
    }
    
    protected static void showDepthWarning(final String method) {
        showWarning(String.valueOf(method) + "() can only be used with a renderer that " + "supports 3D, such as P3D or OPENGL.");
    }
    
    protected static void showDepthWarningXYZ(final String method) {
        showWarning(String.valueOf(method) + "() with x, y, and z coordinates " + "can only be used with a renderer that " + "supports 3D, such as P3D or OPENGL. " + "Use a version without a z-coordinate instead.");
    }
    
    protected static void showMethodWarning(final String method) {
        showWarning(String.valueOf(method) + "() is not available with this renderer.");
    }
    
    protected static void showVariationWarning(final String str) {
        showWarning(String.valueOf(str) + " is not available with this renderer.");
    }
    
    protected static void showMissingWarning(final String method) {
        showWarning(String.valueOf(method) + "(), or this particular variation of it, " + "is not available with this renderer.");
    }
    
    public static void showException(final String msg) {
        throw new RuntimeException(msg);
    }
    
    protected void defaultFontOrDeath(final String method) {
        this.defaultFontOrDeath(method, 12.0f);
    }
    
    protected void defaultFontOrDeath(final String method, final float size) {
        if (this.parent != null) {
            this.textFont = this.parent.createDefaultFont(size);
            return;
        }
        throw new RuntimeException("Use textFont() before " + method + "()");
    }
    
    public boolean displayable() {
        return true;
    }
    
    public boolean is2D() {
        return true;
    }
    
    public boolean is3D() {
        return false;
    }
}
