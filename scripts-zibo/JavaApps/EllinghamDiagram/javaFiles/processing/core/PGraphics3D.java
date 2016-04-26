// 
// Decompiled by Procyon v0.5.30
// 

package processing.core;

import java.awt.image.ColorModel;
import java.util.Arrays;
import java.awt.image.ImageProducer;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;
import java.awt.image.DirectColorModel;

public class PGraphics3D extends PGraphics
{
    public float[] zbuffer;
    public PMatrix3D modelview;
    public PMatrix3D modelviewInv;
    protected boolean sizeChanged;
    public PMatrix3D camera;
    protected PMatrix3D cameraInv;
    public float cameraFOV;
    public float cameraX;
    public float cameraY;
    public float cameraZ;
    public float cameraNear;
    public float cameraFar;
    public float cameraAspect;
    public PMatrix3D projection;
    public static final int MAX_LIGHTS = 8;
    public int lightCount;
    public int[] lightType;
    public PVector[] lightPosition;
    public PVector[] lightNormal;
    public float[] lightFalloffConstant;
    public float[] lightFalloffLinear;
    public float[] lightFalloffQuadratic;
    public float[] lightSpotAngle;
    public float[] lightSpotAngleCos;
    public float[] lightSpotConcentration;
    public float[][] lightDiffuse;
    public float[][] lightSpecular;
    public float[] currentLightSpecular;
    public float currentLightFalloffConstant;
    public float currentLightFalloffLinear;
    public float currentLightFalloffQuadratic;
    public static final int TRI_DIFFUSE_R = 0;
    public static final int TRI_DIFFUSE_G = 1;
    public static final int TRI_DIFFUSE_B = 2;
    public static final int TRI_DIFFUSE_A = 3;
    public static final int TRI_SPECULAR_R = 4;
    public static final int TRI_SPECULAR_G = 5;
    public static final int TRI_SPECULAR_B = 6;
    public static final int TRI_COLOR_COUNT = 7;
    private boolean lightingDependsOnVertexPosition;
    static final int LIGHT_AMBIENT_R = 0;
    static final int LIGHT_AMBIENT_G = 1;
    static final int LIGHT_AMBIENT_B = 2;
    static final int LIGHT_DIFFUSE_R = 3;
    static final int LIGHT_DIFFUSE_G = 4;
    static final int LIGHT_DIFFUSE_B = 5;
    static final int LIGHT_SPECULAR_R = 6;
    static final int LIGHT_SPECULAR_G = 7;
    static final int LIGHT_SPECULAR_B = 8;
    static final int LIGHT_COLOR_COUNT = 9;
    protected float[] tempLightingContribution;
    protected PVector lightTriangleNorm;
    protected boolean manipulatingCamera;
    float[][] matrixStack;
    float[][] matrixInvStack;
    int matrixStackDepth;
    protected PMatrix3D forwardTransform;
    protected PMatrix3D reverseTransform;
    protected float leftScreen;
    protected float rightScreen;
    protected float topScreen;
    protected float bottomScreen;
    protected float nearPlane;
    private boolean frustumMode;
    protected static boolean s_enableAccurateTextures;
    public PSmoothTriangle smoothTriangle;
    protected int shapeFirst;
    protected int shapeLast;
    protected int shapeLastPlusClipped;
    protected int[] vertexOrder;
    protected int pathCount;
    protected int[] pathOffset;
    protected int[] pathLength;
    protected static final int VERTEX1 = 0;
    protected static final int VERTEX2 = 1;
    protected static final int VERTEX3 = 2;
    protected static final int STROKE_COLOR = 1;
    protected static final int TEXTURE_INDEX = 3;
    protected static final int POINT_FIELD_COUNT = 2;
    protected static final int LINE_FIELD_COUNT = 2;
    protected static final int TRIANGLE_FIELD_COUNT = 4;
    static final int DEFAULT_POINTS = 512;
    protected int[][] points;
    protected int pointCount;
    static final int DEFAULT_LINES = 512;
    public PLine line;
    protected int[][] lines;
    protected int lineCount;
    static final int DEFAULT_TRIANGLES = 256;
    public PTriangle triangle;
    protected int[][] triangles;
    protected float[][][] triangleColors;
    protected int triangleCount;
    static final int DEFAULT_TEXTURES = 3;
    protected PImage[] textures;
    int textureIndex;
    DirectColorModel cm;
    MemoryImageSource mis;
    float[] worldNormal;
    PVector lightPositionVec;
    PVector lightDirectionVec;
    
    static {
        PGraphics3D.s_enableAccurateTextures = false;
    }
    
    public PGraphics3D() {
        this.lightCount = 0;
        this.tempLightingContribution = new float[9];
        this.lightTriangleNorm = new PVector();
        this.matrixStack = new float[32][16];
        this.matrixInvStack = new float[32][16];
        this.frustumMode = false;
        this.vertexOrder = new int[512];
        this.pathOffset = new int[64];
        this.pathLength = new int[64];
        this.points = new int[512][2];
        this.lines = new int[512][2];
        this.triangles = new int[256][4];
        this.triangleColors = new float[256][3][7];
        this.textures = new PImage[3];
        this.worldNormal = new float[4];
        this.lightPositionVec = new PVector();
        this.lightDirectionVec = new PVector();
    }
    
    public void setSize(final int iwidth, final int iheight) {
        this.width = iwidth;
        this.height = iheight;
        this.width1 = this.width - 1;
        this.height1 = this.height - 1;
        this.allocate();
        this.reapplySettings();
        this.lightType = new int[8];
        this.lightPosition = new PVector[8];
        this.lightNormal = new PVector[8];
        for (int i = 0; i < 8; ++i) {
            this.lightPosition[i] = new PVector();
            this.lightNormal[i] = new PVector();
        }
        this.lightDiffuse = new float[8][3];
        this.lightSpecular = new float[8][3];
        this.lightFalloffConstant = new float[8];
        this.lightFalloffLinear = new float[8];
        this.lightFalloffQuadratic = new float[8];
        this.lightSpotAngle = new float[8];
        this.lightSpotAngleCos = new float[8];
        this.lightSpotConcentration = new float[8];
        this.currentLightSpecular = new float[3];
        this.projection = new PMatrix3D();
        this.modelview = new PMatrix3D();
        this.modelviewInv = new PMatrix3D();
        this.forwardTransform = this.modelview;
        this.reverseTransform = this.modelviewInv;
        this.cameraFOV = 1.0471976f;
        this.cameraX = this.width / 2.0f;
        this.cameraY = this.height / 2.0f;
        this.cameraZ = this.cameraY / (float)Math.tan(this.cameraFOV / 2.0f);
        this.cameraNear = this.cameraZ / 10.0f;
        this.cameraFar = this.cameraZ * 10.0f;
        this.cameraAspect = this.width / this.height;
        this.camera = new PMatrix3D();
        this.cameraInv = new PMatrix3D();
        this.sizeChanged = true;
    }
    
    protected void allocate() {
        this.pixelCount = this.width * this.height;
        this.pixels = new int[this.pixelCount];
        this.zbuffer = new float[this.pixelCount];
        if (this.primarySurface) {
            this.cm = new DirectColorModel(32, 16711680, 65280, 255);
            (this.mis = new MemoryImageSource(this.width, this.height, this.pixels, 0, this.width)).setFullBufferUpdates(true);
            this.mis.setAnimated(true);
            this.image = Toolkit.getDefaultToolkit().createImage(this.mis);
        }
        else {
            Arrays.fill(this.zbuffer, Float.MAX_VALUE);
        }
        this.line = new PLine(this);
        this.triangle = new PTriangle(this);
        this.smoothTriangle = new PSmoothTriangle(this);
    }
    
    public void beginDraw() {
        if (!this.settingsInited) {
            this.defaultSettings();
        }
        if (this.sizeChanged) {
            this.camera();
            this.perspective();
            this.sizeChanged = false;
        }
        this.resetMatrix();
        this.vertexCount = 0;
        this.modelview.set(this.camera);
        this.modelviewInv.set(this.cameraInv);
        this.lightCount = 0;
        this.lightingDependsOnVertexPosition = false;
        this.lightFalloff(1.0f, 0.0f, 0.0f);
        this.lightSpecular(0.0f, 0.0f, 0.0f);
        this.shapeFirst = 0;
        Arrays.fill(this.textures, null);
        this.textureIndex = 0;
        this.normal(0.0f, 0.0f, 1.0f);
    }
    
    public void endDraw() {
        if (this.hints[5]) {
            this.flush();
        }
        if (this.mis != null) {
            this.mis.newPixels(this.pixels, this.cm, 0, this.width);
        }
        this.updatePixels();
    }
    
    protected void defaultSettings() {
        super.defaultSettings();
        this.manipulatingCamera = false;
        this.forwardTransform = this.modelview;
        this.reverseTransform = this.modelviewInv;
        this.camera();
        this.perspective();
        this.textureMode(2);
        this.emissive(0.0f);
        this.specular(0.5f);
        this.shininess(1.0f);
    }
    
    public void hint(final int which) {
        if (which == -5) {
            this.flush();
        }
        else if (which == 4 && this.zbuffer != null) {
            Arrays.fill(this.zbuffer, Float.MAX_VALUE);
        }
        super.hint(which);
    }
    
    public void beginShape(final int kind) {
        this.shape = kind;
        if (this.hints[5]) {
            this.shapeFirst = this.vertexCount;
            this.shapeLast = 0;
        }
        else {
            this.vertexCount = 0;
            if (this.line != null) {
                this.line.reset();
            }
            this.lineCount = 0;
            if (this.triangle != null) {
                this.triangle.reset();
            }
            this.triangleCount = 0;
        }
        this.textureImage = null;
        this.curveVertexCount = 0;
        this.normalMode = 0;
    }
    
    public void texture(final PImage image) {
        this.textureImage = image;
        if (this.textureIndex == this.textures.length - 1) {
            this.textures = (PImage[])PApplet.expand(this.textures);
        }
        if (this.textures[this.textureIndex] != null) {
            ++this.textureIndex;
        }
        this.textures[this.textureIndex] = image;
    }
    
    public void vertex(final float x, final float y) {
        this.vertex(x, y, 0.0f);
    }
    
    public void vertex(final float x, final float y, final float u, final float v) {
        this.vertex(x, y, 0.0f, u, v);
    }
    
    public void endShape(final int mode) {
        this.shapeLast = this.vertexCount;
        this.shapeLastPlusClipped = this.shapeLast;
        if (this.vertexCount == 0) {
            this.shape = 0;
            return;
        }
        this.endShapeModelToCamera(this.shapeFirst, this.shapeLast);
        if (this.stroke) {
            this.endShapeStroke(mode);
        }
        if (this.fill || this.textureImage != null) {
            this.endShapeFill();
        }
        this.endShapeLighting(this.lightCount > 0 && this.fill);
        this.endShapeCameraToScreen(this.shapeFirst, this.shapeLastPlusClipped);
        if (!this.hints[5]) {
            if ((this.fill || this.textureImage != null) && this.triangleCount > 0) {
                this.renderTriangles(0, this.triangleCount);
                if (this.raw != null) {
                    this.rawTriangles(0, this.triangleCount);
                }
                this.triangleCount = 0;
            }
            if (this.stroke) {
                if (this.pointCount > 0) {
                    this.renderPoints(0, this.pointCount);
                    if (this.raw != null) {
                        this.rawPoints(0, this.pointCount);
                    }
                    this.pointCount = 0;
                }
                if (this.lineCount > 0) {
                    this.renderLines(0, this.lineCount);
                    if (this.raw != null) {
                        this.rawLines(0, this.lineCount);
                    }
                    this.lineCount = 0;
                }
            }
            this.pathCount = 0;
        }
        this.shape = 0;
    }
    
    protected void endShapeModelToCamera(final int start, final int stop) {
        for (int i = start; i < stop; ++i) {
            final float[] vertex = this.vertices[i];
            vertex[21] = this.modelview.m00 * vertex[0] + this.modelview.m01 * vertex[1] + this.modelview.m02 * vertex[2] + this.modelview.m03;
            vertex[22] = this.modelview.m10 * vertex[0] + this.modelview.m11 * vertex[1] + this.modelview.m12 * vertex[2] + this.modelview.m13;
            vertex[23] = this.modelview.m20 * vertex[0] + this.modelview.m21 * vertex[1] + this.modelview.m22 * vertex[2] + this.modelview.m23;
            vertex[24] = this.modelview.m30 * vertex[0] + this.modelview.m31 * vertex[1] + this.modelview.m32 * vertex[2] + this.modelview.m33;
            if (vertex[24] != 0.0f && vertex[24] != 1.0f) {
                final float[] array = vertex;
                final int n = 21;
                array[n] /= vertex[24];
                final float[] array2 = vertex;
                final int n2 = 22;
                array2[n2] /= vertex[24];
                final float[] array3 = vertex;
                final int n3 = 23;
                array3[n3] /= vertex[24];
            }
            vertex[24] = 1.0f;
        }
    }
    
    protected void endShapeStroke(final int mode) {
        switch (this.shape) {
            case 2: {
                for (int stop = this.shapeLast, i = this.shapeFirst; i < stop; ++i) {
                    this.addPoint(i);
                }
                break;
            }
            case 4: {
                final int first = this.lineCount;
                final int stop2 = this.shapeLast - 1;
                if (this.shape != 4) {
                    this.addLineBreak();
                }
                for (int j = this.shapeFirst; j < stop2; j += 2) {
                    if (this.shape == 4) {
                        this.addLineBreak();
                    }
                    this.addLine(j, j + 1);
                }
                if (mode == 2) {
                    this.addLine(stop2, this.lines[first][0]);
                    break;
                }
                break;
            }
            case 9: {
                for (int k = this.shapeFirst; k < this.shapeLast - 2; k += 3) {
                    this.addLineBreak();
                    this.addLine(k + 0, k + 1);
                    this.addLine(k + 1, k + 2);
                    this.addLine(k + 2, k + 0);
                }
                break;
            }
            case 10: {
                int stop = this.shapeLast - 1;
                this.addLineBreak();
                for (int i = this.shapeFirst; i < stop; ++i) {
                    this.addLine(i, i + 1);
                }
                stop = this.shapeLast - 2;
                for (int i = this.shapeFirst; i < stop; ++i) {
                    this.addLineBreak();
                    this.addLine(i, i + 2);
                }
                break;
            }
            case 11: {
                for (int k = this.shapeFirst + 1; k < this.shapeLast; ++k) {
                    this.addLineBreak();
                    this.addLine(this.shapeFirst, k);
                }
                this.addLineBreak();
                for (int k = this.shapeFirst + 1; k < this.shapeLast - 1; ++k) {
                    this.addLine(k, k + 1);
                }
                this.addLine(this.shapeLast - 1, this.shapeFirst + 1);
                break;
            }
            case 16: {
                for (int k = this.shapeFirst; k < this.shapeLast; k += 4) {
                    this.addLineBreak();
                    this.addLine(k + 0, k + 1);
                    this.addLine(k + 1, k + 2);
                    this.addLine(k + 2, k + 3);
                    this.addLine(k + 3, k + 0);
                }
                break;
            }
            case 17: {
                for (int k = this.shapeFirst; k < this.shapeLast - 3; k += 2) {
                    this.addLineBreak();
                    this.addLine(k + 0, k + 2);
                    this.addLine(k + 2, k + 3);
                    this.addLine(k + 3, k + 1);
                    this.addLine(k + 1, k + 0);
                }
                break;
            }
            case 20: {
                final int stop = this.shapeLast - 1;
                this.addLineBreak();
                for (int i = this.shapeFirst; i < stop; ++i) {
                    this.addLine(i, i + 1);
                }
                if (mode == 2) {
                    this.addLine(stop, this.shapeFirst);
                    break;
                }
                break;
            }
        }
    }
    
    protected void endShapeFill() {
        switch (this.shape) {
            case 11: {
                for (int stop = this.shapeLast - 1, i = this.shapeFirst + 1; i < stop; ++i) {
                    this.addTriangle(this.shapeFirst, i, i + 1);
                }
                break;
            }
            case 9: {
                for (int stop = this.shapeLast - 2, i = this.shapeFirst; i < stop; i += 3) {
                    if (i % 2 == 0) {
                        this.addTriangle(i, i + 2, i + 1);
                    }
                    else {
                        this.addTriangle(i, i + 1, i + 2);
                    }
                }
                break;
            }
            case 10: {
                for (int stop = this.shapeLast - 2, i = this.shapeFirst; i < stop; ++i) {
                    if (i % 2 == 0) {
                        this.addTriangle(i, i + 2, i + 1);
                    }
                    else {
                        this.addTriangle(i, i + 1, i + 2);
                    }
                }
                break;
            }
            case 16: {
                for (int stop = this.vertexCount - 3, i = this.shapeFirst; i < stop; i += 4) {
                    this.addTriangle(i, i + 1, i + 2);
                    this.addTriangle(i, i + 2, i + 3);
                }
                break;
            }
            case 17: {
                for (int stop = this.vertexCount - 3, i = this.shapeFirst; i < stop; i += 2) {
                    this.addTriangle(i + 0, i + 2, i + 1);
                    this.addTriangle(i + 2, i + 3, i + 1);
                }
                break;
            }
            case 20: {
                this.addPolygonTriangles();
                break;
            }
        }
    }
    
    protected void endShapeLighting(final boolean lights) {
        if (lights) {
            if (!this.lightingDependsOnVertexPosition && this.normalMode == 1) {
                this.calcLightingContribution(this.shapeFirst, this.tempLightingContribution);
                for (int tri = 0; tri < this.triangleCount; ++tri) {
                    this.lightTriangle(tri, this.tempLightingContribution);
                }
            }
            else {
                for (int tri = 0; tri < this.triangleCount; ++tri) {
                    this.lightTriangle(tri);
                }
            }
        }
        else {
            for (int tri = 0; tri < this.triangleCount; ++tri) {
                int index = this.triangles[tri][0];
                this.copyPrelitVertexColor(tri, index, 0);
                index = this.triangles[tri][1];
                this.copyPrelitVertexColor(tri, index, 1);
                index = this.triangles[tri][2];
                this.copyPrelitVertexColor(tri, index, 2);
            }
        }
    }
    
    protected void endShapeCameraToScreen(final int start, final int stop) {
        for (int i = start; i < stop; ++i) {
            final float[] vx = this.vertices[i];
            float ox = this.projection.m00 * vx[21] + this.projection.m01 * vx[22] + this.projection.m02 * vx[23] + this.projection.m03 * vx[24];
            float oy = this.projection.m10 * vx[21] + this.projection.m11 * vx[22] + this.projection.m12 * vx[23] + this.projection.m13 * vx[24];
            float oz = this.projection.m20 * vx[21] + this.projection.m21 * vx[22] + this.projection.m22 * vx[23] + this.projection.m23 * vx[24];
            final float ow = this.projection.m30 * vx[21] + this.projection.m31 * vx[22] + this.projection.m32 * vx[23] + this.projection.m33 * vx[24];
            if (ow != 0.0f && ow != 1.0f) {
                ox /= ow;
                oy /= ow;
                oz /= ow;
            }
            vx[18] = this.width * (1.0f + ox) / 2.0f;
            vx[19] = this.height * (1.0f + oy) / 2.0f;
            vx[20] = (oz + 1.0f) / 2.0f;
        }
    }
    
    protected void addPoint(final int a) {
        if (this.pointCount == this.points.length) {
            final int[][] temp = new int[this.pointCount << 1][2];
            System.arraycopy(this.points, 0, temp, 0, this.pointCount);
            this.points = temp;
        }
        this.points[this.pointCount][0] = a;
        this.points[this.pointCount][1] = this.strokeColor;
        ++this.pointCount;
    }
    
    protected void renderPoints(final int start, final int stop) {
        if (this.strokeWeight != 1.0f) {
            for (int i = start; i < stop; ++i) {
                final float[] a = this.vertices[this.points[i][0]];
                this.renderLineVertices(a, a);
            }
        }
        else {
            for (int i = start; i < stop; ++i) {
                final float[] a = this.vertices[this.points[i][0]];
                final int sx = (int)(a[18] + 0.4999f);
                final int sy = (int)(a[19] + 0.4999f);
                if (sx >= 0 && sx < this.width && sy >= 0 && sy < this.height) {
                    final int index = sy * this.width + sx;
                    this.pixels[index] = this.points[i][1];
                    this.zbuffer[index] = a[20];
                }
            }
        }
    }
    
    protected void rawPoints(final int start, final int stop) {
        this.raw.colorMode(1, 1.0f);
        this.raw.noFill();
        this.raw.strokeWeight(this.vertices[this.lines[start][0]][17]);
        this.raw.beginShape(2);
        for (int i = start; i < stop; ++i) {
            final float[] a = this.vertices[this.lines[i][0]];
            if (this.raw.is3D()) {
                if (a[24] != 0.0f) {
                    this.raw.stroke(a[13], a[14], a[15], a[16]);
                    this.raw.vertex(a[21] / a[24], a[22] / a[24], a[23] / a[24]);
                }
            }
            else {
                this.raw.stroke(a[13], a[14], a[15], a[16]);
                this.raw.vertex(a[18], a[19]);
            }
        }
        this.raw.endShape();
    }
    
    protected final void addLineBreak() {
        if (this.pathCount == this.pathOffset.length) {
            this.pathOffset = PApplet.expand(this.pathOffset);
            this.pathLength = PApplet.expand(this.pathLength);
        }
        this.pathOffset[this.pathCount] = this.lineCount;
        this.pathLength[this.pathCount] = 0;
        ++this.pathCount;
    }
    
    protected void addLine(final int a, final int b) {
        this.addLineWithClip(a, b);
    }
    
    protected final void addLineWithClip(final int a, final int b) {
        final float az = this.vertices[a][23];
        final float bz = this.vertices[b][23];
        if (az > this.cameraNear) {
            if (bz > this.cameraNear) {
                return;
            }
            final int cb = this.interpolateClipVertex(a, b);
            this.addLineWithoutClip(cb, b);
        }
        else {
            if (bz <= this.cameraNear) {
                this.addLineWithoutClip(a, b);
                return;
            }
            final int cb = this.interpolateClipVertex(a, b);
            this.addLineWithoutClip(a, cb);
        }
    }
    
    protected final void addLineWithoutClip(final int a, final int b) {
        if (this.lineCount == this.lines.length) {
            final int[][] temp = new int[this.lineCount << 1][2];
            System.arraycopy(this.lines, 0, temp, 0, this.lineCount);
            this.lines = temp;
        }
        this.lines[this.lineCount][0] = a;
        this.lines[this.lineCount][1] = b;
        ++this.lineCount;
        final int[] pathLength = this.pathLength;
        final int n = this.pathCount - 1;
        ++pathLength[n];
    }
    
    protected void renderLines(final int start, final int stop) {
        for (int i = start; i < stop; ++i) {
            this.renderLineVertices(this.vertices[this.lines[i][0]], this.vertices[this.lines[i][1]]);
        }
    }
    
    protected void renderLineVertices(final float[] a, final float[] b) {
        if (a[17] > 1.25f || a[17] < 0.75f) {
            final float ox1 = a[18];
            float oy1 = a[19];
            final float ox2 = b[18];
            float oy2 = b[19];
            final float weight = a[17] / 2.0f;
            if (ox1 == ox2 && oy1 == oy2) {
                oy1 -= weight;
                oy2 += weight;
            }
            final float dX = ox2 - ox1 + 1.0E-4f;
            final float dY = oy2 - oy1 + 1.0E-4f;
            final float len = (float)Math.sqrt(dX * dX + dY * dY);
            final float rh = weight / len;
            final float dx0 = rh * dY;
            final float dy0 = rh * dX;
            final float dx2 = rh * dY;
            final float dy2 = rh * dX;
            final float ax1 = ox1 + dx0;
            final float ay1 = oy1 - dy0;
            final float ax2 = ox1 - dx0;
            final float ay2 = oy1 + dy0;
            final float bx1 = ox2 + dx2;
            final float by1 = oy2 - dy2;
            final float bx2 = ox2 - dx2;
            final float by2 = oy2 + dy2;
            if (this.smooth) {
                this.smoothTriangle.reset(3);
                this.smoothTriangle.smooth = true;
                this.smoothTriangle.interpARGB = true;
                this.smoothTriangle.setVertices(ax1, ay1, a[20], bx2, by2, b[20], ax2, ay2, a[20]);
                this.smoothTriangle.setIntensities(a[13], a[14], a[15], a[16], b[13], b[14], b[15], b[16], a[13], a[14], a[15], a[16]);
                this.smoothTriangle.render();
                this.smoothTriangle.setVertices(ax1, ay1, a[20], bx2, by2, b[20], bx1, by1, b[20]);
                this.smoothTriangle.setIntensities(a[13], a[14], a[15], a[16], b[13], b[14], b[15], b[16], b[13], b[14], b[15], b[16]);
                this.smoothTriangle.render();
            }
            else {
                this.triangle.reset();
                this.triangle.setVertices(ax1, ay1, a[20], bx2, by2, b[20], ax2, ay2, a[20]);
                this.triangle.setIntensities(a[13], a[14], a[15], a[16], b[13], b[14], b[15], b[16], a[13], a[14], a[15], a[16]);
                this.triangle.render();
                this.triangle.setVertices(ax1, ay1, a[20], bx2, by2, b[20], bx1, by1, b[20]);
                this.triangle.setIntensities(a[13], a[14], a[15], a[16], b[13], b[14], b[15], b[16], b[13], b[14], b[15], b[16]);
                this.triangle.render();
            }
        }
        else {
            this.line.reset();
            this.line.setIntensities(a[13], a[14], a[15], a[16], b[13], b[14], b[15], b[16]);
            this.line.setVertices(a[18], a[19], a[20], b[18], b[19], b[20]);
            this.line.draw();
        }
    }
    
    protected void rawLines(final int start, final int stop) {
        this.raw.colorMode(1, 1.0f);
        this.raw.noFill();
        this.raw.beginShape(4);
        for (int i = start; i < stop; ++i) {
            final float[] a = this.vertices[this.lines[i][0]];
            final float[] b = this.vertices[this.lines[i][1]];
            this.raw.strokeWeight(this.vertices[this.lines[i][1]][17]);
            if (this.raw.is3D()) {
                if (a[24] != 0.0f && b[24] != 0.0f) {
                    this.raw.stroke(a[13], a[14], a[15], a[16]);
                    this.raw.vertex(a[21] / a[24], a[22] / a[24], a[23] / a[24]);
                    this.raw.stroke(b[13], b[14], b[15], b[16]);
                    this.raw.vertex(b[21] / b[24], b[22] / b[24], b[23] / b[24]);
                }
            }
            else if (this.raw.is2D()) {
                this.raw.stroke(a[13], a[14], a[15], a[16]);
                this.raw.vertex(a[18], a[19]);
                this.raw.stroke(b[13], b[14], b[15], b[16]);
                this.raw.vertex(b[18], b[19]);
            }
        }
        this.raw.endShape();
    }
    
    protected void addTriangle(final int a, final int b, final int c) {
        this.addTriangleWithClip(a, b, c);
    }
    
    protected final void addTriangleWithClip(final int a, final int b, final int c) {
        boolean aClipped = false;
        boolean bClipped = false;
        int clippedCount = 0;
        this.cameraNear = -8.0f;
        if (this.vertices[a][23] > this.cameraNear) {
            aClipped = true;
            ++clippedCount;
        }
        if (this.vertices[b][23] > this.cameraNear) {
            bClipped = true;
            ++clippedCount;
        }
        if (this.vertices[c][23] > this.cameraNear) {
            ++clippedCount;
        }
        if (clippedCount == 0) {
            this.addTriangleWithoutClip(a, b, c);
        }
        else if (clippedCount != 3) {
            if (clippedCount == 2) {
                int ca;
                int cb;
                int cc;
                if (!aClipped) {
                    ca = a;
                    cb = b;
                    cc = c;
                }
                else if (!bClipped) {
                    ca = b;
                    cb = a;
                    cc = c;
                }
                else {
                    ca = c;
                    cb = b;
                    cc = a;
                }
                final int cd = this.interpolateClipVertex(ca, cb);
                final int ce = this.interpolateClipVertex(ca, cc);
                this.addTriangleWithoutClip(ca, cd, ce);
            }
            else {
                int ca;
                int cb;
                int cc;
                if (aClipped) {
                    ca = c;
                    cb = b;
                    cc = a;
                }
                else if (bClipped) {
                    ca = a;
                    cb = c;
                    cc = b;
                }
                else {
                    ca = a;
                    cb = b;
                    cc = c;
                }
                final int cd = this.interpolateClipVertex(ca, cc);
                final int ce = this.interpolateClipVertex(cb, cc);
                this.addTriangleWithoutClip(ca, cd, cb);
                this.addTriangleWithoutClip(cb, cd, ce);
            }
        }
    }
    
    protected final int interpolateClipVertex(final int a, final int b) {
        float[] va;
        float[] vb;
        if (this.vertices[a][23] < this.vertices[b][23]) {
            va = this.vertices[b];
            vb = this.vertices[a];
        }
        else {
            va = this.vertices[a];
            vb = this.vertices[b];
        }
        final float az = va[23];
        final float bz = vb[23];
        final float dz = az - bz;
        if (dz == 0.0f) {
            return a;
        }
        final float pa = (this.cameraNear - bz) / dz;
        final float pb = 1.0f - pa;
        this.vertex(pa * va[0] + pb * vb[0], pa * va[1] + pb * vb[1], pa * va[2] + pb * vb[2]);
        final int irv = this.vertexCount - 1;
        ++this.shapeLastPlusClipped;
        final float[] rv = this.vertices[irv];
        rv[18] = pa * va[18] + pb * vb[18];
        rv[19] = pa * va[19] + pb * vb[19];
        rv[20] = pa * va[20] + pb * vb[20];
        rv[21] = pa * va[21] + pb * vb[21];
        rv[22] = pa * va[22] + pb * vb[22];
        rv[23] = pa * va[23] + pb * vb[23];
        rv[24] = pa * va[24] + pb * vb[24];
        rv[3] = pa * va[3] + pb * vb[3];
        rv[4] = pa * va[4] + pb * vb[4];
        rv[5] = pa * va[5] + pb * vb[5];
        rv[6] = pa * va[6] + pb * vb[6];
        rv[7] = pa * va[7] + pb * vb[7];
        rv[8] = pa * va[8] + pb * vb[8];
        rv[13] = pa * va[13] + pb * vb[13];
        rv[14] = pa * va[14] + pb * vb[14];
        rv[15] = pa * va[15] + pb * vb[15];
        rv[16] = pa * va[16] + pb * vb[16];
        rv[9] = pa * va[9] + pb * vb[9];
        rv[10] = pa * va[10] + pb * vb[10];
        rv[11] = pa * va[11] + pb * vb[11];
        rv[25] = pa * va[25] + pb * vb[25];
        rv[26] = pa * va[26] + pb * vb[26];
        rv[27] = pa * va[27] + pb * vb[27];
        rv[28] = pa * va[28] + pb * vb[28];
        rv[29] = pa * va[29] + pb * vb[29];
        rv[30] = pa * va[30] + pb * vb[30];
        rv[32] = pa * va[32] + pb * vb[32];
        rv[33] = pa * va[33] + pb * vb[33];
        rv[34] = pa * va[34] + pb * vb[34];
        rv[31] = pa * va[31] + pb * vb[31];
        rv[35] = 0.0f;
        return irv;
    }
    
    protected final void addTriangleWithoutClip(final int a, final int b, final int c) {
        if (this.triangleCount == this.triangles.length) {
            final int[][] temp = new int[this.triangleCount << 1][4];
            System.arraycopy(this.triangles, 0, temp, 0, this.triangleCount);
            this.triangles = temp;
            final float[][][] ftemp = new float[this.triangleCount << 1][3][7];
            System.arraycopy(this.triangleColors, 0, ftemp, 0, this.triangleCount);
            this.triangleColors = ftemp;
        }
        this.triangles[this.triangleCount][0] = a;
        this.triangles[this.triangleCount][1] = b;
        this.triangles[this.triangleCount][2] = c;
        if (this.textureImage == null) {
            this.triangles[this.triangleCount][3] = -1;
        }
        else {
            this.triangles[this.triangleCount][3] = this.textureIndex;
        }
        ++this.triangleCount;
    }
    
    protected void addPolygonTriangles() {
        if (this.vertexOrder.length != this.vertices.length) {
            final int[] temp = new int[this.vertices.length];
            PApplet.arrayCopy(this.vertexOrder, temp, this.vertexOrder.length);
            this.vertexOrder = temp;
        }
        int d1 = 0;
        int d2 = 1;
        float area = 0.0f;
        int p = this.shapeLast - 1;
        for (int q = this.shapeFirst; q < this.shapeLast; p = q++) {
            area += this.vertices[q][d1] * this.vertices[p][d2] - this.vertices[p][d1] * this.vertices[q][d2];
        }
        if (area == 0.0f) {
            boolean foundValidX = false;
            boolean foundValidY = false;
            for (int i = this.shapeFirst; i < this.shapeLast; ++i) {
                for (int j = i; j < this.shapeLast; ++j) {
                    if (this.vertices[i][0] != this.vertices[j][0]) {
                        foundValidX = true;
                    }
                    if (this.vertices[i][1] != this.vertices[j][1]) {
                        foundValidY = true;
                    }
                }
            }
            if (foundValidX) {
                d2 = 2;
            }
            else {
                if (!foundValidY) {
                    return;
                }
                d1 = 1;
                d2 = 2;
            }
            int p2 = this.shapeLast - 1;
            for (int q2 = this.shapeFirst; q2 < this.shapeLast; p2 = q2++) {
                area += this.vertices[q2][d1] * this.vertices[p2][d2] - this.vertices[p2][d1] * this.vertices[q2][d2];
            }
        }
        final float[] vfirst = this.vertices[this.shapeFirst];
        final float[] vlast = this.vertices[this.shapeLast - 1];
        if (this.abs(vfirst[0] - vlast[0]) < 1.0E-4f && this.abs(vfirst[1] - vlast[1]) < 1.0E-4f && this.abs(vfirst[2] - vlast[2]) < 1.0E-4f) {
            --this.shapeLast;
        }
        int k = 0;
        if (area > 0.0f) {
            for (int l = this.shapeFirst; l < this.shapeLast; ++l) {
                k = l - this.shapeFirst;
                this.vertexOrder[k] = l;
            }
        }
        else {
            for (int l = this.shapeFirst; l < this.shapeLast; ++l) {
                k = l - this.shapeFirst;
                this.vertexOrder[k] = this.shapeLast - 1 - k;
            }
        }
        int vc = this.shapeLast - this.shapeFirst;
        int count = 2 * vc;
        int m = 0;
        int v = vc - 1;
        while (vc > 2) {
            boolean snip = true;
            if (count-- <= 0) {
                break;
            }
            int u = v;
            if (vc <= u) {
                u = 0;
            }
            v = u + 1;
            if (vc <= v) {
                v = 0;
            }
            int w = v + 1;
            if (vc <= w) {
                w = 0;
            }
            final double Ax = -10.0f * this.vertices[this.vertexOrder[u]][d1];
            final double Ay = 10.0f * this.vertices[this.vertexOrder[u]][d2];
            final double Bx = -10.0f * this.vertices[this.vertexOrder[v]][d1];
            final double By = 10.0f * this.vertices[this.vertexOrder[v]][d2];
            final double Cx = -10.0f * this.vertices[this.vertexOrder[w]][d1];
            final double Cy = 10.0f * this.vertices[this.vertexOrder[w]][d2];
            if (9.999999747378752E-5 > (Bx - Ax) * (Cy - Ay) - (By - Ay) * (Cx - Ax)) {
                continue;
            }
            for (int p3 = 0; p3 < vc; ++p3) {
                if (p3 != u && p3 != v) {
                    if (p3 != w) {
                        final double Px = -10.0f * this.vertices[this.vertexOrder[p3]][d1];
                        final double Py = 10.0f * this.vertices[this.vertexOrder[p3]][d2];
                        final double ax = Cx - Bx;
                        final double ay = Cy - By;
                        final double bx = Ax - Cx;
                        final double by = Ay - Cy;
                        final double cx = Bx - Ax;
                        final double cy = By - Ay;
                        final double apx = Px - Ax;
                        final double apy = Py - Ay;
                        final double bpx = Px - Bx;
                        final double bpy = Py - By;
                        final double cpx = Px - Cx;
                        final double cpy = Py - Cy;
                        final double aCROSSbp = ax * bpy - ay * bpx;
                        final double cCROSSap = cx * apy - cy * apx;
                        final double bCROSScp = bx * cpy - by * cpx;
                        if (aCROSSbp >= 0.0 && bCROSScp >= 0.0 && cCROSSap >= 0.0) {
                            snip = false;
                        }
                    }
                }
            }
            if (!snip) {
                continue;
            }
            this.addTriangle(this.vertexOrder[u], this.vertexOrder[v], this.vertexOrder[w]);
            ++m;
            int s = v;
            for (int t = v + 1; t < vc; ++t) {
                this.vertexOrder[s] = this.vertexOrder[t];
                ++s;
            }
            --vc;
            count = 2 * vc;
        }
    }
    
    private void toWorldNormal(final float nx, final float ny, final float nz, final float[] out) {
        out[0] = this.modelviewInv.m00 * nx + this.modelviewInv.m10 * ny + this.modelviewInv.m20 * nz + this.modelviewInv.m30;
        out[1] = this.modelviewInv.m01 * nx + this.modelviewInv.m11 * ny + this.modelviewInv.m21 * nz + this.modelviewInv.m31;
        out[2] = this.modelviewInv.m02 * nx + this.modelviewInv.m12 * ny + this.modelviewInv.m22 * nz + this.modelviewInv.m32;
        out[3] = this.modelviewInv.m03 * nx + this.modelviewInv.m13 * ny + this.modelviewInv.m23 * nz + this.modelviewInv.m33;
        if (out[3] != 0.0f && out[3] != 1.0f) {
            final int n = 0;
            out[n] /= out[3];
            final int n2 = 1;
            out[n2] /= out[3];
            final int n3 = 2;
            out[n3] /= out[3];
        }
        out[3] = 1.0f;
        final float nlen = this.mag(out[0], out[1], out[2]);
        if (nlen != 0.0f && nlen != 1.0f) {
            final int n4 = 0;
            out[n4] /= nlen;
            final int n5 = 1;
            out[n5] /= nlen;
            final int n6 = 2;
            out[n6] /= nlen;
        }
    }
    
    private void calcLightingContribution(final int vIndex, final float[] contribution) {
        this.calcLightingContribution(vIndex, contribution, false);
    }
    
    private void calcLightingContribution(final int vIndex, final float[] contribution, final boolean normalIsWorld) {
        final float[] v = this.vertices[vIndex];
        final float sr = v[28];
        final float sg = v[29];
        final float sb = v[30];
        float wx = v[21];
        float wy = v[22];
        float wz = v[23];
        final float shine = v[31];
        float nx = v[9];
        float ny = v[10];
        float nz = v[11];
        if (!normalIsWorld) {
            this.toWorldNormal(v[9], v[10], v[11], this.worldNormal);
            nx = this.worldNormal[0];
            ny = this.worldNormal[1];
            nz = this.worldNormal[2];
        }
        else {
            nx = v[9];
            ny = v[10];
            nz = v[11];
        }
        final float dir = this.dot(nx, ny, nz, -wx, -wy, -wz);
        if (dir < 0.0f) {
            nx = -nx;
            ny = -ny;
            nz = -nz;
        }
        contribution[0] = 0.0f;
        contribution[2] = (contribution[1] = 0.0f);
        contribution[4] = (contribution[3] = 0.0f);
        contribution[6] = (contribution[5] = 0.0f);
        contribution[8] = (contribution[7] = 0.0f);
        for (int i = 0; i < this.lightCount; ++i) {
            float denom = this.lightFalloffConstant[i];
            float spotTerm = 1.0f;
            if (this.lightType[i] == 0) {
                if (this.lightFalloffQuadratic[i] != 0.0f || this.lightFalloffLinear[i] != 0.0f) {
                    final float distSq = this.mag(this.lightPosition[i].x - wx, this.lightPosition[i].y - wy, this.lightPosition[i].z - wz);
                    denom += this.lightFalloffQuadratic[i] * distSq + this.lightFalloffLinear[i] * this.sqrt(distSq);
                }
                if (denom == 0.0f) {
                    denom = 1.0f;
                }
                final int n = 0;
                contribution[n] += this.lightDiffuse[i][0] / denom;
                final int n2 = 1;
                contribution[n2] += this.lightDiffuse[i][1] / denom;
                final int n3 = 2;
                contribution[n3] += this.lightDiffuse[i][2] / denom;
            }
            else {
                float lightDir_dot_li = 0.0f;
                float n_dot_li = 0.0f;
                float lix;
                float liy;
                float liz;
                if (this.lightType[i] == 1) {
                    lix = -this.lightNormal[i].x;
                    liy = -this.lightNormal[i].y;
                    liz = -this.lightNormal[i].z;
                    denom = 1.0f;
                    n_dot_li = nx * lix + ny * liy + nz * liz;
                    if (n_dot_li <= 0.0f) {
                        continue;
                    }
                }
                else {
                    lix = this.lightPosition[i].x - wx;
                    liy = this.lightPosition[i].y - wy;
                    liz = this.lightPosition[i].z - wz;
                    final float distSq2 = this.mag(lix, liy, liz);
                    if (distSq2 != 0.0f) {
                        lix /= distSq2;
                        liy /= distSq2;
                        liz /= distSq2;
                    }
                    n_dot_li = nx * lix + ny * liy + nz * liz;
                    if (n_dot_li <= 0.0f) {
                        continue;
                    }
                    if (this.lightType[i] == 3) {
                        lightDir_dot_li = -(this.lightNormal[i].x * lix + this.lightNormal[i].y * liy + this.lightNormal[i].z * liz);
                        if (lightDir_dot_li <= this.lightSpotAngleCos[i]) {
                            continue;
                        }
                        spotTerm = (float)Math.pow(lightDir_dot_li, this.lightSpotConcentration[i]);
                    }
                    if (this.lightFalloffQuadratic[i] != 0.0f || this.lightFalloffLinear[i] != 0.0f) {
                        denom += this.lightFalloffQuadratic[i] * distSq2 + this.lightFalloffLinear[i] * this.sqrt(distSq2);
                    }
                }
                if (denom == 0.0f) {
                    denom = 1.0f;
                }
                float mul = n_dot_li * spotTerm / denom;
                final int n4 = 3;
                contribution[n4] += this.lightDiffuse[i][0] * mul;
                final int n5 = 4;
                contribution[n5] += this.lightDiffuse[i][1] * mul;
                final int n6 = 5;
                contribution[n6] += this.lightDiffuse[i][2] * mul;
                if ((sr > 0.0f || sg > 0.0f || sb > 0.0f) && (this.lightSpecular[i][0] > 0.0f || this.lightSpecular[i][1] > 0.0f || this.lightSpecular[i][2] > 0.0f)) {
                    float vmag = this.mag(wx, wy, wz);
                    if (vmag != 0.0f) {
                        wx /= vmag;
                        wy /= vmag;
                        wz /= vmag;
                    }
                    float sx = lix - wx;
                    float sy = liy - wy;
                    float sz = liz - wz;
                    vmag = this.mag(sx, sy, sz);
                    if (vmag != 0.0f) {
                        sx /= vmag;
                        sy /= vmag;
                        sz /= vmag;
                    }
                    float s_dot_n = sx * nx + sy * ny + sz * nz;
                    if (s_dot_n > 0.0f) {
                        s_dot_n = (float)Math.pow(s_dot_n, shine);
                        mul = s_dot_n * spotTerm / denom;
                        final int n7 = 6;
                        contribution[n7] += this.lightSpecular[i][0] * mul;
                        final int n8 = 7;
                        contribution[n8] += this.lightSpecular[i][1] * mul;
                        final int n9 = 8;
                        contribution[n9] += this.lightSpecular[i][2] * mul;
                    }
                }
            }
        }
    }
    
    private void applyLightingContribution(final int vIndex, final float[] contribution) {
        final float[] v = this.vertices[vIndex];
        v[3] = this.clamp(v[32] + v[25] * contribution[0] + v[3] * contribution[3]);
        v[4] = this.clamp(v[33] + v[26] * contribution[1] + v[4] * contribution[4]);
        v[5] = this.clamp(v[34] + v[27] * contribution[2] + v[5] * contribution[5]);
        v[6] = this.clamp(v[6]);
        v[28] = this.clamp(v[28] * contribution[6]);
        v[29] = this.clamp(v[29] * contribution[7]);
        v[30] = this.clamp(v[30] * contribution[8]);
        v[35] = 1.0f;
    }
    
    private void lightVertex(final int vIndex, final float[] contribution) {
        this.calcLightingContribution(vIndex, contribution);
        this.applyLightingContribution(vIndex, contribution);
    }
    
    private void lightUnlitVertex(final int vIndex, final float[] contribution) {
        if (this.vertices[vIndex][35] == 0.0f) {
            this.lightVertex(vIndex, contribution);
        }
    }
    
    private void copyPrelitVertexColor(final int triIndex, final int index, final int colorIndex) {
        final float[] triColor = this.triangleColors[triIndex][colorIndex];
        final float[] v = this.vertices[index];
        triColor[0] = v[3];
        triColor[1] = v[4];
        triColor[2] = v[5];
        triColor[3] = v[6];
        triColor[4] = v[28];
        triColor[5] = v[29];
        triColor[6] = v[30];
    }
    
    private void copyVertexColor(final int triIndex, final int index, final int colorIndex, final float[] contrib) {
        final float[] triColor = this.triangleColors[triIndex][colorIndex];
        final float[] v = this.vertices[index];
        triColor[0] = this.clamp(v[32] + v[25] * contrib[0] + v[3] * contrib[3]);
        triColor[1] = this.clamp(v[33] + v[26] * contrib[1] + v[4] * contrib[4]);
        triColor[2] = this.clamp(v[34] + v[27] * contrib[2] + v[5] * contrib[5]);
        triColor[3] = this.clamp(v[6]);
        triColor[4] = this.clamp(v[28] * contrib[6]);
        triColor[5] = this.clamp(v[29] * contrib[7]);
        triColor[6] = this.clamp(v[30] * contrib[8]);
    }
    
    private void lightTriangle(final int triIndex, final float[] lightContribution) {
        int vIndex = this.triangles[triIndex][0];
        this.copyVertexColor(triIndex, vIndex, 0, lightContribution);
        vIndex = this.triangles[triIndex][1];
        this.copyVertexColor(triIndex, vIndex, 1, lightContribution);
        vIndex = this.triangles[triIndex][2];
        this.copyVertexColor(triIndex, vIndex, 2, lightContribution);
    }
    
    private void lightTriangle(final int triIndex) {
        if (this.normalMode == 2) {
            int vIndex = this.triangles[triIndex][0];
            this.lightUnlitVertex(vIndex, this.tempLightingContribution);
            this.copyPrelitVertexColor(triIndex, vIndex, 0);
            vIndex = this.triangles[triIndex][1];
            this.lightUnlitVertex(vIndex, this.tempLightingContribution);
            this.copyPrelitVertexColor(triIndex, vIndex, 1);
            vIndex = this.triangles[triIndex][2];
            this.lightUnlitVertex(vIndex, this.tempLightingContribution);
            this.copyPrelitVertexColor(triIndex, vIndex, 2);
        }
        else if (!this.lightingDependsOnVertexPosition) {
            final int vIndex = this.triangles[triIndex][0];
            final int vIndex2 = this.triangles[triIndex][1];
            final int vIndex3 = this.triangles[triIndex][2];
            this.cross(this.vertices[vIndex2][21] - this.vertices[vIndex][21], this.vertices[vIndex2][22] - this.vertices[vIndex][22], this.vertices[vIndex2][23] - this.vertices[vIndex][23], this.vertices[vIndex3][21] - this.vertices[vIndex][21], this.vertices[vIndex3][22] - this.vertices[vIndex][22], this.vertices[vIndex3][23] - this.vertices[vIndex][23], this.lightTriangleNorm);
            this.lightTriangleNorm.normalize();
            this.vertices[vIndex][9] = this.lightTriangleNorm.x;
            this.vertices[vIndex][10] = this.lightTriangleNorm.y;
            this.vertices[vIndex][11] = this.lightTriangleNorm.z;
            this.calcLightingContribution(vIndex, this.tempLightingContribution, true);
            this.copyVertexColor(triIndex, vIndex, 0, this.tempLightingContribution);
            this.copyVertexColor(triIndex, vIndex2, 1, this.tempLightingContribution);
            this.copyVertexColor(triIndex, vIndex3, 2, this.tempLightingContribution);
        }
        else if (this.normalMode == 1) {
            int vIndex = this.triangles[triIndex][0];
            this.vertices[vIndex][9] = this.vertices[this.shapeFirst][9];
            this.vertices[vIndex][10] = this.vertices[this.shapeFirst][10];
            this.vertices[vIndex][11] = this.vertices[this.shapeFirst][11];
            this.calcLightingContribution(vIndex, this.tempLightingContribution);
            this.copyVertexColor(triIndex, vIndex, 0, this.tempLightingContribution);
            vIndex = this.triangles[triIndex][1];
            this.vertices[vIndex][9] = this.vertices[this.shapeFirst][9];
            this.vertices[vIndex][10] = this.vertices[this.shapeFirst][10];
            this.vertices[vIndex][11] = this.vertices[this.shapeFirst][11];
            this.calcLightingContribution(vIndex, this.tempLightingContribution);
            this.copyVertexColor(triIndex, vIndex, 1, this.tempLightingContribution);
            vIndex = this.triangles[triIndex][2];
            this.vertices[vIndex][9] = this.vertices[this.shapeFirst][9];
            this.vertices[vIndex][10] = this.vertices[this.shapeFirst][10];
            this.vertices[vIndex][11] = this.vertices[this.shapeFirst][11];
            this.calcLightingContribution(vIndex, this.tempLightingContribution);
            this.copyVertexColor(triIndex, vIndex, 2, this.tempLightingContribution);
        }
        else {
            final int vIndex = this.triangles[triIndex][0];
            final int vIndex2 = this.triangles[triIndex][1];
            final int vIndex3 = this.triangles[triIndex][2];
            this.cross(this.vertices[vIndex2][21] - this.vertices[vIndex][21], this.vertices[vIndex2][22] - this.vertices[vIndex][22], this.vertices[vIndex2][23] - this.vertices[vIndex][23], this.vertices[vIndex3][21] - this.vertices[vIndex][21], this.vertices[vIndex3][22] - this.vertices[vIndex][22], this.vertices[vIndex3][23] - this.vertices[vIndex][23], this.lightTriangleNorm);
            this.lightTriangleNorm.normalize();
            this.vertices[vIndex][9] = this.lightTriangleNorm.x;
            this.vertices[vIndex][10] = this.lightTriangleNorm.y;
            this.vertices[vIndex][11] = this.lightTriangleNorm.z;
            this.calcLightingContribution(vIndex, this.tempLightingContribution, true);
            this.copyVertexColor(triIndex, vIndex, 0, this.tempLightingContribution);
            this.vertices[vIndex2][9] = this.lightTriangleNorm.x;
            this.vertices[vIndex2][10] = this.lightTriangleNorm.y;
            this.vertices[vIndex2][11] = this.lightTriangleNorm.z;
            this.calcLightingContribution(vIndex2, this.tempLightingContribution, true);
            this.copyVertexColor(triIndex, vIndex2, 1, this.tempLightingContribution);
            this.vertices[vIndex3][9] = this.lightTriangleNorm.x;
            this.vertices[vIndex3][10] = this.lightTriangleNorm.y;
            this.vertices[vIndex3][11] = this.lightTriangleNorm.z;
            this.calcLightingContribution(vIndex3, this.tempLightingContribution, true);
            this.copyVertexColor(triIndex, vIndex3, 2, this.tempLightingContribution);
        }
    }
    
    protected void renderTriangles(final int start, final int stop) {
        for (int i = start; i < stop; ++i) {
            final float[] a = this.vertices[this.triangles[i][0]];
            final float[] b = this.vertices[this.triangles[i][1]];
            final float[] c = this.vertices[this.triangles[i][2]];
            final int tex = this.triangles[i][3];
            this.triangle.reset();
            final float ar = this.clamp(this.triangleColors[i][0][0] + this.triangleColors[i][0][4]);
            final float ag = this.clamp(this.triangleColors[i][0][1] + this.triangleColors[i][0][5]);
            final float ab = this.clamp(this.triangleColors[i][0][2] + this.triangleColors[i][0][6]);
            final float br = this.clamp(this.triangleColors[i][1][0] + this.triangleColors[i][1][4]);
            final float bg = this.clamp(this.triangleColors[i][1][1] + this.triangleColors[i][1][5]);
            final float bb = this.clamp(this.triangleColors[i][1][2] + this.triangleColors[i][1][6]);
            final float cr = this.clamp(this.triangleColors[i][2][0] + this.triangleColors[i][2][4]);
            final float cg = this.clamp(this.triangleColors[i][2][1] + this.triangleColors[i][2][5]);
            final float cb = this.clamp(this.triangleColors[i][2][2] + this.triangleColors[i][2][6]);
            boolean failedToPrecalc = false;
            if (PGraphics3D.s_enableAccurateTextures && this.frustumMode) {
                boolean textured = true;
                this.smoothTriangle.reset(3);
                this.smoothTriangle.smooth = true;
                this.smoothTriangle.interpARGB = true;
                this.smoothTriangle.setIntensities(ar, ag, ab, a[6], br, bg, bb, b[6], cr, cg, cb, c[6]);
                if (tex > -1 && this.textures[tex] != null) {
                    this.smoothTriangle.setCamVertices(a[21], a[22], a[23], b[21], b[22], b[23], c[21], c[22], c[23]);
                    this.smoothTriangle.interpUV = true;
                    this.smoothTriangle.texture(this.textures[tex]);
                    final float umult = this.textures[tex].width;
                    final float vmult = this.textures[tex].height;
                    this.smoothTriangle.vertices[0][7] = a[7] * umult;
                    this.smoothTriangle.vertices[0][8] = a[8] * vmult;
                    this.smoothTriangle.vertices[1][7] = b[7] * umult;
                    this.smoothTriangle.vertices[1][8] = b[8] * vmult;
                    this.smoothTriangle.vertices[2][7] = c[7] * umult;
                    this.smoothTriangle.vertices[2][8] = c[8] * vmult;
                }
                else {
                    this.smoothTriangle.interpUV = false;
                    textured = false;
                }
                this.smoothTriangle.setVertices(a[18], a[19], a[20], b[18], b[19], b[20], c[18], c[19], c[20]);
                if (!textured || this.smoothTriangle.precomputeAccurateTexturing()) {
                    this.smoothTriangle.render();
                }
                else {
                    failedToPrecalc = true;
                }
            }
            if (!PGraphics3D.s_enableAccurateTextures || failedToPrecalc || !this.frustumMode) {
                if (tex > -1 && this.textures[tex] != null) {
                    this.triangle.setTexture(this.textures[tex]);
                    this.triangle.setUV(a[7], a[8], b[7], b[8], c[7], c[8]);
                }
                this.triangle.setIntensities(ar, ag, ab, a[6], br, bg, bb, b[6], cr, cg, cb, c[6]);
                this.triangle.setVertices(a[18], a[19], a[20], b[18], b[19], b[20], c[18], c[19], c[20]);
                this.triangle.render();
            }
        }
    }
    
    protected void rawTriangles(final int start, final int stop) {
        this.raw.colorMode(1, 1.0f);
        this.raw.noStroke();
        this.raw.beginShape(9);
        for (int i = start; i < stop; ++i) {
            final float[] a = this.vertices[this.triangles[i][0]];
            final float[] b = this.vertices[this.triangles[i][1]];
            final float[] c = this.vertices[this.triangles[i][2]];
            final float ar = this.clamp(this.triangleColors[i][0][0] + this.triangleColors[i][0][4]);
            final float ag = this.clamp(this.triangleColors[i][0][1] + this.triangleColors[i][0][5]);
            final float ab = this.clamp(this.triangleColors[i][0][2] + this.triangleColors[i][0][6]);
            final float br = this.clamp(this.triangleColors[i][1][0] + this.triangleColors[i][1][4]);
            final float bg = this.clamp(this.triangleColors[i][1][1] + this.triangleColors[i][1][5]);
            final float bb = this.clamp(this.triangleColors[i][1][2] + this.triangleColors[i][1][6]);
            final float cr = this.clamp(this.triangleColors[i][2][0] + this.triangleColors[i][2][4]);
            final float cg = this.clamp(this.triangleColors[i][2][1] + this.triangleColors[i][2][5]);
            final float cb = this.clamp(this.triangleColors[i][2][2] + this.triangleColors[i][2][6]);
            final int tex = this.triangles[i][3];
            final PImage texImage = (tex > -1) ? this.textures[tex] : null;
            if (texImage != null) {
                if (this.raw.is3D()) {
                    if (a[24] != 0.0f && b[24] != 0.0f && c[24] != 0.0f) {
                        this.raw.fill(ar, ag, ab, a[6]);
                        this.raw.vertex(a[21] / a[24], a[22] / a[24], a[23] / a[24], a[7], a[8]);
                        this.raw.fill(br, bg, bb, b[6]);
                        this.raw.vertex(b[21] / b[24], b[22] / b[24], b[23] / b[24], b[7], b[8]);
                        this.raw.fill(cr, cg, cb, c[6]);
                        this.raw.vertex(c[21] / c[24], c[22] / c[24], c[23] / c[24], c[7], c[8]);
                    }
                }
                else if (this.raw.is2D()) {
                    this.raw.fill(ar, ag, ab, a[6]);
                    this.raw.vertex(a[18], a[19], a[7], a[8]);
                    this.raw.fill(br, bg, bb, b[6]);
                    this.raw.vertex(b[18], b[19], b[7], b[8]);
                    this.raw.fill(cr, cg, cb, c[6]);
                    this.raw.vertex(c[18], c[19], c[7], c[8]);
                }
            }
            else if (this.raw.is3D()) {
                if (a[24] != 0.0f && b[24] != 0.0f && c[24] != 0.0f) {
                    this.raw.fill(ar, ag, ab, a[6]);
                    this.raw.vertex(a[21] / a[24], a[22] / a[24], a[23] / a[24]);
                    this.raw.fill(br, bg, bb, b[6]);
                    this.raw.vertex(b[21] / b[24], b[22] / b[24], b[23] / b[24]);
                    this.raw.fill(cr, cg, cb, c[6]);
                    this.raw.vertex(c[21] / c[24], c[22] / c[24], c[23] / c[24]);
                }
            }
            else if (this.raw.is2D()) {
                this.raw.fill(ar, ag, ab, a[6]);
                this.raw.vertex(a[18], a[19]);
                this.raw.fill(br, bg, bb, b[6]);
                this.raw.vertex(b[18], b[19]);
                this.raw.fill(cr, cg, cb, c[6]);
                this.raw.vertex(c[18], c[19]);
            }
        }
        this.raw.endShape();
    }
    
    public void flush() {
        if (this.hints[5]) {
            this.sort();
        }
        this.render();
    }
    
    protected void render() {
        if (this.pointCount > 0) {
            this.renderPoints(0, this.pointCount);
            if (this.raw != null) {
                this.rawPoints(0, this.pointCount);
            }
            this.pointCount = 0;
        }
        if (this.lineCount > 0) {
            this.renderLines(0, this.lineCount);
            if (this.raw != null) {
                this.rawLines(0, this.lineCount);
            }
            this.lineCount = 0;
            this.pathCount = 0;
        }
        if (this.triangleCount > 0) {
            this.renderTriangles(0, this.triangleCount);
            if (this.raw != null) {
                this.rawTriangles(0, this.triangleCount);
            }
            this.triangleCount = 0;
        }
    }
    
    protected void sort() {
        if (this.triangleCount > 0) {
            this.sortTrianglesInternal(0, this.triangleCount - 1);
        }
    }
    
    private void sortTrianglesInternal(final int i, final int j) {
        final int pivotIndex = (i + j) / 2;
        this.sortTrianglesSwap(pivotIndex, j);
        final int k = this.sortTrianglesPartition(i - 1, j);
        this.sortTrianglesSwap(k, j);
        if (k - i > 1) {
            this.sortTrianglesInternal(i, k - 1);
        }
        if (j - k > 1) {
            this.sortTrianglesInternal(k + 1, j);
        }
    }
    
    private int sortTrianglesPartition(int left, int right) {
        while (true) {
            if (this.sortTrianglesCompare(++left, right) >= 0.0f) {
                while (right != 0 && this.sortTrianglesCompare(--right, right) > 0.0f) {}
                this.sortTrianglesSwap(left, right);
                if (left >= right) {
                    break;
                }
                continue;
            }
        }
        this.sortTrianglesSwap(left, right);
        return left;
    }
    
    private void sortTrianglesSwap(final int a, final int b) {
        final int[] tempi = this.triangles[a];
        this.triangles[a] = this.triangles[b];
        this.triangles[b] = tempi;
        final float[][] tempf = this.triangleColors[a];
        this.triangleColors[a] = this.triangleColors[b];
        this.triangleColors[b] = tempf;
    }
    
    private float sortTrianglesCompare(final int a, final int b) {
        return this.vertices[this.triangles[b][0]][20] + this.vertices[this.triangles[b][1]][20] + this.vertices[this.triangles[b][2]][20] - (this.vertices[this.triangles[a][0]][20] + this.vertices[this.triangles[a][1]][20] + this.vertices[this.triangles[a][2]][20]);
    }
    
    protected void ellipseImpl(final float x, final float y, final float w, final float h) {
        final float radiusH = w / 2.0f;
        final float radiusV = h / 2.0f;
        final float centerX = x + radiusH;
        final float centerY = y + radiusV;
        final int rough = (int)(4.0 + Math.sqrt(w + h) * 3.0);
        final int accuracy = PApplet.constrain(rough, 6, 100);
        if (this.fill) {
            final float inc = 720.0f / accuracy;
            float val = 0.0f;
            final boolean strokeSaved = this.stroke;
            this.stroke = false;
            final boolean smoothSaved = this.smooth;
            if (this.smooth && this.stroke) {
                this.smooth = false;
            }
            this.beginShape(11);
            this.normal(0.0f, 0.0f, 1.0f);
            this.vertex(centerX, centerY);
            for (int i = 0; i < accuracy; ++i) {
                this.vertex(centerX + PGraphics3D.cosLUT[(int)val] * radiusH, centerY + PGraphics3D.sinLUT[(int)val] * radiusV);
                val = (val + inc) % 720.0f;
            }
            this.vertex(centerX + PGraphics3D.cosLUT[0] * radiusH, centerY + PGraphics3D.sinLUT[0] * radiusV);
            this.endShape();
            this.stroke = strokeSaved;
            this.smooth = smoothSaved;
        }
        if (this.stroke) {
            final float inc = 720.0f / accuracy;
            float val = 0.0f;
            final boolean savedFill = this.fill;
            this.fill = false;
            val = 0.0f;
            this.beginShape();
            for (int j = 0; j < accuracy; ++j) {
                this.vertex(centerX + PGraphics3D.cosLUT[(int)val] * radiusH, centerY + PGraphics3D.sinLUT[(int)val] * radiusV);
                val = (val + inc) % 720.0f;
            }
            this.endShape(2);
            this.fill = savedFill;
        }
    }
    
    protected void arcImpl(final float x, final float y, final float w, final float h, final float start, final float stop) {
        final float hr = w / 2.0f;
        final float vr = h / 2.0f;
        final float centerX = x + hr;
        final float centerY = y + vr;
        if (this.fill) {
            final boolean savedStroke = this.stroke;
            this.stroke = false;
            final int startLUT = (int)(0.5f + start / 6.2831855f * 720.0f);
            final int stopLUT = (int)(0.5f + stop / 6.2831855f * 720.0f);
            this.beginShape(11);
            this.vertex(centerX, centerY);
            for (int increment = 1, i = startLUT; i < stopLUT; i += increment) {
                int ii = i % 720;
                if (ii < 0) {
                    ii += 720;
                }
                this.vertex(centerX + PGraphics3D.cosLUT[ii] * hr, centerY + PGraphics3D.sinLUT[ii] * vr);
            }
            this.vertex(centerX + PGraphics3D.cosLUT[stopLUT % 720] * hr, centerY + PGraphics3D.sinLUT[stopLUT % 720] * vr);
            this.endShape();
            this.stroke = savedStroke;
        }
        if (this.stroke) {
            final boolean savedFill = this.fill;
            this.fill = false;
            final int startLUT = (int)(0.5f + start / 6.2831855f * 720.0f);
            final int stopLUT = (int)(0.5f + stop / 6.2831855f * 720.0f);
            this.beginShape();
            for (int increment = 1, i = startLUT; i < stopLUT; i += increment) {
                int ii = i % 720;
                if (ii < 0) {
                    ii += 720;
                }
                this.vertex(centerX + PGraphics3D.cosLUT[ii] * hr, centerY + PGraphics3D.sinLUT[ii] * vr);
            }
            this.vertex(centerX + PGraphics3D.cosLUT[stopLUT % 720] * hr, centerY + PGraphics3D.sinLUT[stopLUT % 720] * vr);
            this.endShape();
            this.fill = savedFill;
        }
    }
    
    public void box(final float w, final float h, final float d) {
        if (this.triangle != null) {
            this.triangle.setCulling(true);
        }
        super.box(w, h, d);
        if (this.triangle != null) {
            this.triangle.setCulling(false);
        }
    }
    
    public void sphere(final float r) {
        if (this.triangle != null) {
            this.triangle.setCulling(true);
        }
        super.sphere(r);
        if (this.triangle != null) {
            this.triangle.setCulling(false);
        }
    }
    
    public void smooth() {
        PGraphics3D.s_enableAccurateTextures = true;
        this.smooth = true;
    }
    
    public void noSmooth() {
        PGraphics3D.s_enableAccurateTextures = false;
        this.smooth = false;
    }
    
    protected boolean textModeCheck(final int mode) {
        return this.textMode == 4 || this.textMode == 256;
    }
    
    public void pushMatrix() {
        if (this.matrixStackDepth == 32) {
            throw new RuntimeException("Too many calls to pushMatrix().");
        }
        this.modelview.get(this.matrixStack[this.matrixStackDepth]);
        this.modelviewInv.get(this.matrixInvStack[this.matrixStackDepth]);
        ++this.matrixStackDepth;
    }
    
    public void popMatrix() {
        if (this.matrixStackDepth == 0) {
            throw new RuntimeException("Too many calls to popMatrix(), and not enough to pushMatrix().");
        }
        --this.matrixStackDepth;
        this.modelview.set(this.matrixStack[this.matrixStackDepth]);
        this.modelviewInv.set(this.matrixInvStack[this.matrixStackDepth]);
    }
    
    public void translate(final float tx, final float ty) {
        this.translate(tx, ty, 0.0f);
    }
    
    public void translate(final float tx, final float ty, final float tz) {
        this.forwardTransform.translate(tx, ty, tz);
        this.reverseTransform.invTranslate(tx, ty, tz);
    }
    
    public void rotate(final float angle) {
        this.rotateZ(angle);
    }
    
    public void rotateX(final float angle) {
        this.forwardTransform.rotateX(angle);
        this.reverseTransform.invRotateX(angle);
    }
    
    public void rotateY(final float angle) {
        this.forwardTransform.rotateY(angle);
        this.reverseTransform.invRotateY(angle);
    }
    
    public void rotateZ(final float angle) {
        this.forwardTransform.rotateZ(angle);
        this.reverseTransform.invRotateZ(angle);
    }
    
    public void rotate(final float angle, final float v0, final float v1, final float v2) {
        this.forwardTransform.rotate(angle, v0, v1, v2);
        this.reverseTransform.invRotate(angle, v0, v1, v2);
    }
    
    public void scale(final float s) {
        this.scale(s, s, s);
    }
    
    public void scale(final float sx, final float sy) {
        this.scale(sx, sy, 1.0f);
    }
    
    public void scale(final float x, final float y, final float z) {
        this.forwardTransform.scale(x, y, z);
        this.reverseTransform.invScale(x, y, z);
    }
    
    public void skewX(final float angle) {
        final float t = (float)Math.tan(angle);
        this.applyMatrix(1.0f, t, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
    }
    
    public void skewY(final float angle) {
        final float t = (float)Math.tan(angle);
        this.applyMatrix(1.0f, 0.0f, 0.0f, 0.0f, t, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
    }
    
    public void resetMatrix() {
        this.forwardTransform.reset();
        this.reverseTransform.reset();
    }
    
    public void applyMatrix(final PMatrix2D source) {
        this.applyMatrix(source.m00, source.m01, source.m02, source.m10, source.m11, source.m12);
    }
    
    public void applyMatrix(final float n00, final float n01, final float n02, final float n10, final float n11, final float n12) {
        this.applyMatrix(n00, n01, n02, 0.0f, n10, n11, n12, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
    }
    
    public void applyMatrix(final PMatrix3D source) {
        this.applyMatrix(source.m00, source.m01, source.m02, source.m03, source.m10, source.m11, source.m12, source.m13, source.m20, source.m21, source.m22, source.m23, source.m30, source.m31, source.m32, source.m33);
    }
    
    public void applyMatrix(final float n00, final float n01, final float n02, final float n03, final float n10, final float n11, final float n12, final float n13, final float n20, final float n21, final float n22, final float n23, final float n30, final float n31, final float n32, final float n33) {
        this.forwardTransform.apply(n00, n01, n02, n03, n10, n11, n12, n13, n20, n21, n22, n23, n30, n31, n32, n33);
        this.reverseTransform.invApply(n00, n01, n02, n03, n10, n11, n12, n13, n20, n21, n22, n23, n30, n31, n32, n33);
    }
    
    public PMatrix getMatrix() {
        return this.modelview.get();
    }
    
    public PMatrix3D getMatrix(PMatrix3D target) {
        if (target == null) {
            target = new PMatrix3D();
        }
        target.set(this.modelview);
        return target;
    }
    
    public void setMatrix(final PMatrix2D source) {
        this.resetMatrix();
        this.applyMatrix(source);
    }
    
    public void setMatrix(final PMatrix3D source) {
        this.resetMatrix();
        this.applyMatrix(source);
    }
    
    public void printMatrix() {
        this.modelview.print();
    }
    
    public void beginCamera() {
        if (this.manipulatingCamera) {
            throw new RuntimeException("beginCamera() cannot be called again before endCamera()");
        }
        this.manipulatingCamera = true;
        this.forwardTransform = this.cameraInv;
        this.reverseTransform = this.camera;
    }
    
    public void endCamera() {
        if (!this.manipulatingCamera) {
            throw new RuntimeException("Cannot call endCamera() without first calling beginCamera()");
        }
        this.modelview.set(this.camera);
        this.modelviewInv.set(this.cameraInv);
        this.forwardTransform = this.modelview;
        this.reverseTransform = this.modelviewInv;
        this.manipulatingCamera = false;
    }
    
    public void camera() {
        this.camera(this.cameraX, this.cameraY, this.cameraZ, this.cameraX, this.cameraY, 0.0f, 0.0f, 1.0f, 0.0f);
    }
    
    public void camera(final float eyeX, final float eyeY, final float eyeZ, final float centerX, final float centerY, final float centerZ, final float upX, final float upY, final float upZ) {
        float z0 = eyeX - centerX;
        float z2 = eyeY - centerY;
        float z3 = eyeZ - centerZ;
        float mag = this.sqrt(z0 * z0 + z2 * z2 + z3 * z3);
        if (mag != 0.0f) {
            z0 /= mag;
            z2 /= mag;
            z3 /= mag;
        }
        float x0 = upY * z3 - upZ * z2;
        float x2 = -upX * z3 + upZ * z0;
        float x3 = upX * z2 - upY * z0;
        float y0 = z2 * x3 - z3 * x2;
        float y2 = -z0 * x3 + z3 * x0;
        float y3 = z0 * x2 - z2 * x0;
        mag = this.sqrt(x0 * x0 + x2 * x2 + x3 * x3);
        if (mag != 0.0f) {
            x0 /= mag;
            x2 /= mag;
            x3 /= mag;
        }
        mag = this.sqrt(y0 * y0 + y2 * y2 + y3 * y3);
        if (mag != 0.0f) {
            y0 /= mag;
            y2 /= mag;
            y3 /= mag;
        }
        this.camera.set(x0, x2, x3, 0.0f, y0, y2, y3, 0.0f, z0, z2, z3, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
        this.camera.translate(-eyeX, -eyeY, -eyeZ);
        this.cameraInv.reset();
        this.cameraInv.invApply(x0, x2, x3, 0.0f, y0, y2, y3, 0.0f, z0, z2, z3, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
        this.cameraInv.translate(eyeX, eyeY, eyeZ);
        this.modelview.set(this.camera);
        this.modelviewInv.set(this.cameraInv);
    }
    
    public void printCamera() {
        this.camera.print();
    }
    
    public void ortho() {
        this.ortho(0.0f, this.width, 0.0f, this.height, -10.0f, 10.0f);
    }
    
    public void ortho(final float left, final float right, final float bottom, final float top, final float near, final float far) {
        final float x = 2.0f / (right - left);
        final float y = 2.0f / (top - bottom);
        final float z = -2.0f / (far - near);
        final float tx = -(right + left) / (right - left);
        final float ty = -(top + bottom) / (top - bottom);
        final float tz = -(far + near) / (far - near);
        this.projection.set(x, 0.0f, 0.0f, tx, 0.0f, y, 0.0f, ty, 0.0f, 0.0f, z, tz, 0.0f, 0.0f, 0.0f, 1.0f);
        this.updateProjection();
        this.frustumMode = false;
    }
    
    public void perspective() {
        this.perspective(this.cameraFOV, this.cameraAspect, this.cameraNear, this.cameraFar);
    }
    
    public void perspective(final float fov, final float aspect, final float zNear, final float zFar) {
        final float ymax = zNear * (float)Math.tan(fov / 2.0f);
        final float ymin = -ymax;
        final float xmin = ymin * aspect;
        final float xmax = ymax * aspect;
        this.frustum(xmin, xmax, ymin, ymax, zNear, zFar);
    }
    
    public void frustum(final float left, final float right, final float bottom, final float top, final float znear, final float zfar) {
        this.leftScreen = left;
        this.rightScreen = right;
        this.bottomScreen = bottom;
        this.topScreen = top;
        this.nearPlane = znear;
        this.frustumMode = true;
        this.projection.set(2.0f * znear / (right - left), 0.0f, (right + left) / (right - left), 0.0f, 0.0f, 2.0f * znear / (top - bottom), (top + bottom) / (top - bottom), 0.0f, 0.0f, 0.0f, -(zfar + znear) / (zfar - znear), -(2.0f * zfar * znear) / (zfar - znear), 0.0f, 0.0f, -1.0f, 0.0f);
        this.updateProjection();
    }
    
    protected void updateProjection() {
    }
    
    public void printProjection() {
        this.projection.print();
    }
    
    public float screenX(final float x, final float y) {
        return this.screenX(x, y, 0.0f);
    }
    
    public float screenY(final float x, final float y) {
        return this.screenY(x, y, 0.0f);
    }
    
    public float screenX(final float x, final float y, final float z) {
        final float ax = this.modelview.m00 * x + this.modelview.m01 * y + this.modelview.m02 * z + this.modelview.m03;
        final float ay = this.modelview.m10 * x + this.modelview.m11 * y + this.modelview.m12 * z + this.modelview.m13;
        final float az = this.modelview.m20 * x + this.modelview.m21 * y + this.modelview.m22 * z + this.modelview.m23;
        final float aw = this.modelview.m30 * x + this.modelview.m31 * y + this.modelview.m32 * z + this.modelview.m33;
        float ox = this.projection.m00 * ax + this.projection.m01 * ay + this.projection.m02 * az + this.projection.m03 * aw;
        final float ow = this.projection.m30 * ax + this.projection.m31 * ay + this.projection.m32 * az + this.projection.m33 * aw;
        if (ow != 0.0f) {
            ox /= ow;
        }
        return this.width * (1.0f + ox) / 2.0f;
    }
    
    public float screenY(final float x, final float y, final float z) {
        final float ax = this.modelview.m00 * x + this.modelview.m01 * y + this.modelview.m02 * z + this.modelview.m03;
        final float ay = this.modelview.m10 * x + this.modelview.m11 * y + this.modelview.m12 * z + this.modelview.m13;
        final float az = this.modelview.m20 * x + this.modelview.m21 * y + this.modelview.m22 * z + this.modelview.m23;
        final float aw = this.modelview.m30 * x + this.modelview.m31 * y + this.modelview.m32 * z + this.modelview.m33;
        float oy = this.projection.m10 * ax + this.projection.m11 * ay + this.projection.m12 * az + this.projection.m13 * aw;
        final float ow = this.projection.m30 * ax + this.projection.m31 * ay + this.projection.m32 * az + this.projection.m33 * aw;
        if (ow != 0.0f) {
            oy /= ow;
        }
        return this.height * (1.0f + oy) / 2.0f;
    }
    
    public float screenZ(final float x, final float y, final float z) {
        final float ax = this.modelview.m00 * x + this.modelview.m01 * y + this.modelview.m02 * z + this.modelview.m03;
        final float ay = this.modelview.m10 * x + this.modelview.m11 * y + this.modelview.m12 * z + this.modelview.m13;
        final float az = this.modelview.m20 * x + this.modelview.m21 * y + this.modelview.m22 * z + this.modelview.m23;
        final float aw = this.modelview.m30 * x + this.modelview.m31 * y + this.modelview.m32 * z + this.modelview.m33;
        float oz = this.projection.m20 * ax + this.projection.m21 * ay + this.projection.m22 * az + this.projection.m23 * aw;
        final float ow = this.projection.m30 * ax + this.projection.m31 * ay + this.projection.m32 * az + this.projection.m33 * aw;
        if (ow != 0.0f) {
            oz /= ow;
        }
        return (oz + 1.0f) / 2.0f;
    }
    
    public float modelX(final float x, final float y, final float z) {
        final float ax = this.modelview.m00 * x + this.modelview.m01 * y + this.modelview.m02 * z + this.modelview.m03;
        final float ay = this.modelview.m10 * x + this.modelview.m11 * y + this.modelview.m12 * z + this.modelview.m13;
        final float az = this.modelview.m20 * x + this.modelview.m21 * y + this.modelview.m22 * z + this.modelview.m23;
        final float aw = this.modelview.m30 * x + this.modelview.m31 * y + this.modelview.m32 * z + this.modelview.m33;
        final float ox = this.cameraInv.m00 * ax + this.cameraInv.m01 * ay + this.cameraInv.m02 * az + this.cameraInv.m03 * aw;
        final float ow = this.cameraInv.m30 * ax + this.cameraInv.m31 * ay + this.cameraInv.m32 * az + this.cameraInv.m33 * aw;
        return (ow != 0.0f) ? (ox / ow) : ox;
    }
    
    public float modelY(final float x, final float y, final float z) {
        final float ax = this.modelview.m00 * x + this.modelview.m01 * y + this.modelview.m02 * z + this.modelview.m03;
        final float ay = this.modelview.m10 * x + this.modelview.m11 * y + this.modelview.m12 * z + this.modelview.m13;
        final float az = this.modelview.m20 * x + this.modelview.m21 * y + this.modelview.m22 * z + this.modelview.m23;
        final float aw = this.modelview.m30 * x + this.modelview.m31 * y + this.modelview.m32 * z + this.modelview.m33;
        final float oy = this.cameraInv.m10 * ax + this.cameraInv.m11 * ay + this.cameraInv.m12 * az + this.cameraInv.m13 * aw;
        final float ow = this.cameraInv.m30 * ax + this.cameraInv.m31 * ay + this.cameraInv.m32 * az + this.cameraInv.m33 * aw;
        return (ow != 0.0f) ? (oy / ow) : oy;
    }
    
    public float modelZ(final float x, final float y, final float z) {
        final float ax = this.modelview.m00 * x + this.modelview.m01 * y + this.modelview.m02 * z + this.modelview.m03;
        final float ay = this.modelview.m10 * x + this.modelview.m11 * y + this.modelview.m12 * z + this.modelview.m13;
        final float az = this.modelview.m20 * x + this.modelview.m21 * y + this.modelview.m22 * z + this.modelview.m23;
        final float aw = this.modelview.m30 * x + this.modelview.m31 * y + this.modelview.m32 * z + this.modelview.m33;
        final float oz = this.cameraInv.m20 * ax + this.cameraInv.m21 * ay + this.cameraInv.m22 * az + this.cameraInv.m23 * aw;
        final float ow = this.cameraInv.m30 * ax + this.cameraInv.m31 * ay + this.cameraInv.m32 * az + this.cameraInv.m33 * aw;
        return (ow != 0.0f) ? (oz / ow) : oz;
    }
    
    public void strokeJoin(final int join) {
        if (join != 8) {
            PGraphics.showMethodWarning("strokeJoin");
        }
    }
    
    public void strokeCap(final int cap) {
        if (cap != 2) {
            PGraphics.showMethodWarning("strokeCap");
        }
    }
    
    protected void fillFromCalc() {
        super.fillFromCalc();
        this.ambientFromCalc();
    }
    
    public void lights() {
        final int colorModeSaved = this.colorMode;
        this.colorMode = 1;
        this.lightFalloff(1.0f, 0.0f, 0.0f);
        this.lightSpecular(0.0f, 0.0f, 0.0f);
        this.ambientLight(this.colorModeX * 0.5f, this.colorModeY * 0.5f, this.colorModeZ * 0.5f);
        this.directionalLight(this.colorModeX * 0.5f, this.colorModeY * 0.5f, this.colorModeZ * 0.5f, 0.0f, 0.0f, -1.0f);
        this.colorMode = colorModeSaved;
        this.lightingDependsOnVertexPosition = false;
    }
    
    public void noLights() {
        this.flush();
        this.lightCount = 0;
    }
    
    public void ambientLight(final float r, final float g, final float b) {
        this.ambientLight(r, g, b, 0.0f, 0.0f, 0.0f);
    }
    
    public void ambientLight(final float r, final float g, final float b, final float x, final float y, final float z) {
        if (this.lightCount == 8) {
            throw new RuntimeException("can only create 8 lights");
        }
        this.colorCalc(r, g, b);
        this.lightDiffuse[this.lightCount][0] = this.calcR;
        this.lightDiffuse[this.lightCount][1] = this.calcG;
        this.lightDiffuse[this.lightCount][2] = this.calcB;
        this.lightType[this.lightCount] = 0;
        this.lightFalloffConstant[this.lightCount] = this.currentLightFalloffConstant;
        this.lightFalloffLinear[this.lightCount] = this.currentLightFalloffLinear;
        this.lightFalloffQuadratic[this.lightCount] = this.currentLightFalloffQuadratic;
        this.lightPosition(this.lightCount, x, y, z);
        ++this.lightCount;
    }
    
    public void directionalLight(final float r, final float g, final float b, final float nx, final float ny, final float nz) {
        if (this.lightCount == 8) {
            throw new RuntimeException("can only create 8 lights");
        }
        this.colorCalc(r, g, b);
        this.lightDiffuse[this.lightCount][0] = this.calcR;
        this.lightDiffuse[this.lightCount][1] = this.calcG;
        this.lightDiffuse[this.lightCount][2] = this.calcB;
        this.lightType[this.lightCount] = 1;
        this.lightFalloffConstant[this.lightCount] = this.currentLightFalloffConstant;
        this.lightFalloffLinear[this.lightCount] = this.currentLightFalloffLinear;
        this.lightFalloffQuadratic[this.lightCount] = this.currentLightFalloffQuadratic;
        this.lightSpecular[this.lightCount][0] = this.currentLightSpecular[0];
        this.lightSpecular[this.lightCount][1] = this.currentLightSpecular[1];
        this.lightSpecular[this.lightCount][2] = this.currentLightSpecular[2];
        this.lightDirection(this.lightCount, nx, ny, nz);
        ++this.lightCount;
    }
    
    public void pointLight(final float r, final float g, final float b, final float x, final float y, final float z) {
        if (this.lightCount == 8) {
            throw new RuntimeException("can only create 8 lights");
        }
        this.colorCalc(r, g, b);
        this.lightDiffuse[this.lightCount][0] = this.calcR;
        this.lightDiffuse[this.lightCount][1] = this.calcG;
        this.lightDiffuse[this.lightCount][2] = this.calcB;
        this.lightType[this.lightCount] = 2;
        this.lightFalloffConstant[this.lightCount] = this.currentLightFalloffConstant;
        this.lightFalloffLinear[this.lightCount] = this.currentLightFalloffLinear;
        this.lightFalloffQuadratic[this.lightCount] = this.currentLightFalloffQuadratic;
        this.lightSpecular[this.lightCount][0] = this.currentLightSpecular[0];
        this.lightSpecular[this.lightCount][1] = this.currentLightSpecular[1];
        this.lightSpecular[this.lightCount][2] = this.currentLightSpecular[2];
        this.lightPosition(this.lightCount, x, y, z);
        ++this.lightCount;
        this.lightingDependsOnVertexPosition = true;
    }
    
    public void spotLight(final float r, final float g, final float b, final float x, final float y, final float z, final float nx, final float ny, final float nz, final float angle, final float concentration) {
        if (this.lightCount == 8) {
            throw new RuntimeException("can only create 8 lights");
        }
        this.colorCalc(r, g, b);
        this.lightDiffuse[this.lightCount][0] = this.calcR;
        this.lightDiffuse[this.lightCount][1] = this.calcG;
        this.lightDiffuse[this.lightCount][2] = this.calcB;
        this.lightType[this.lightCount] = 3;
        this.lightFalloffConstant[this.lightCount] = this.currentLightFalloffConstant;
        this.lightFalloffLinear[this.lightCount] = this.currentLightFalloffLinear;
        this.lightFalloffQuadratic[this.lightCount] = this.currentLightFalloffQuadratic;
        this.lightSpecular[this.lightCount][0] = this.currentLightSpecular[0];
        this.lightSpecular[this.lightCount][1] = this.currentLightSpecular[1];
        this.lightSpecular[this.lightCount][2] = this.currentLightSpecular[2];
        this.lightPosition(this.lightCount, x, y, z);
        this.lightDirection(this.lightCount, nx, ny, nz);
        this.lightSpotAngle[this.lightCount] = angle;
        this.lightSpotAngleCos[this.lightCount] = Math.max(0.0f, (float)Math.cos(angle));
        this.lightSpotConcentration[this.lightCount] = concentration;
        ++this.lightCount;
        this.lightingDependsOnVertexPosition = true;
    }
    
    public void lightFalloff(final float constant, final float linear, final float quadratic) {
        this.currentLightFalloffConstant = constant;
        this.currentLightFalloffLinear = linear;
        this.currentLightFalloffQuadratic = quadratic;
        this.lightingDependsOnVertexPosition = true;
    }
    
    public void lightSpecular(final float x, final float y, final float z) {
        this.colorCalc(x, y, z);
        this.currentLightSpecular[0] = this.calcR;
        this.currentLightSpecular[1] = this.calcG;
        this.currentLightSpecular[2] = this.calcB;
        this.lightingDependsOnVertexPosition = true;
    }
    
    protected void lightPosition(final int num, final float x, final float y, final float z) {
        this.lightPositionVec.set(x, y, z);
        this.modelview.mult(this.lightPositionVec, this.lightPosition[num]);
    }
    
    protected void lightDirection(final int num, final float x, final float y, final float z) {
        this.lightNormal[num].set(this.modelviewInv.m00 * x + this.modelviewInv.m10 * y + this.modelviewInv.m20 * z + this.modelviewInv.m30, this.modelviewInv.m01 * x + this.modelviewInv.m11 * y + this.modelviewInv.m21 * z + this.modelviewInv.m31, this.modelviewInv.m02 * x + this.modelviewInv.m12 * y + this.modelviewInv.m22 * z + this.modelviewInv.m32);
        this.lightNormal[num].normalize();
    }
    
    protected void backgroundImpl(final PImage image) {
        System.arraycopy(image.pixels, 0, this.pixels, 0, this.pixels.length);
        Arrays.fill(this.zbuffer, Float.MAX_VALUE);
    }
    
    protected void backgroundImpl() {
        Arrays.fill(this.pixels, this.backgroundColor);
        Arrays.fill(this.zbuffer, Float.MAX_VALUE);
    }
    
    public boolean is2D() {
        return false;
    }
    
    public boolean is3D() {
        return true;
    }
    
    private final float sqrt(final float a) {
        return (float)Math.sqrt(a);
    }
    
    private final float mag(final float a, final float b, final float c) {
        return (float)Math.sqrt(a * a + b * b + c * c);
    }
    
    private final float clamp(final float a) {
        return (a < 1.0f) ? a : 1.0f;
    }
    
    private final float abs(final float a) {
        return (a < 0.0f) ? (-a) : a;
    }
    
    private float dot(final float ax, final float ay, final float az, final float bx, final float by, final float bz) {
        return ax * bx + ay * by + az * bz;
    }
    
    private final void cross(final float a0, final float a1, final float a2, final float b0, final float b1, final float b2, final PVector out) {
        out.x = a1 * b2 - a2 * b1;
        out.y = a2 * b0 - a0 * b2;
        out.z = a0 * b1 - a1 * b0;
    }
}
