// 
// Decompiled by Procyon v0.5.30
// 

package processing.core;

import java.util.Arrays;
import java.awt.image.ColorModel;
import java.awt.image.ImageProducer;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;
import java.awt.image.DirectColorModel;

public class PGraphics2D extends PGraphics
{
    PMatrix2D ctm;
    PPolygon fpolygon;
    PPolygon spolygon;
    float[][] svertices;
    PPolygon tpolygon;
    int[] vertexOrder;
    PLine line;
    float[][] matrixStack;
    int matrixStackDepth;
    DirectColorModel cm;
    MemoryImageSource mis;
    
    public PGraphics2D() {
        this.ctm = new PMatrix2D();
        this.matrixStack = new float[32][6];
    }
    
    protected void allocate() {
        this.pixelCount = this.width * this.height;
        this.pixels = new int[this.pixelCount];
        if (this.primarySurface) {
            this.cm = new DirectColorModel(32, 16711680, 65280, 255);
            (this.mis = new MemoryImageSource(this.width, this.height, this.pixels, 0, this.width)).setFullBufferUpdates(true);
            this.mis.setAnimated(true);
            this.image = Toolkit.getDefaultToolkit().createImage(this.mis);
        }
    }
    
    public boolean canDraw() {
        return true;
    }
    
    public void beginDraw() {
        if (!this.settingsInited) {
            this.defaultSettings();
            this.fpolygon = new PPolygon(this);
            this.spolygon = new PPolygon(this);
            this.spolygon.vertexCount = 4;
            this.svertices = new float[2][];
        }
        this.resetMatrix();
        this.vertexCount = 0;
    }
    
    public void endDraw() {
        if (this.mis != null) {
            this.mis.newPixels(this.pixels, this.cm, 0, this.width);
        }
        this.updatePixels();
    }
    
    public void beginShape(final int kind) {
        this.shape = kind;
        this.vertexCount = 0;
        this.curveVertexCount = 0;
        this.fpolygon.reset(4);
        this.spolygon.reset(4);
        this.textureImage = null;
    }
    
    public void vertex(final float x, final float y, final float z) {
        PGraphics.showDepthWarningXYZ("vertex");
    }
    
    public void vertex(final float x, final float y, final float z, final float u, final float v) {
        PGraphics.showDepthWarningXYZ("vertex");
    }
    
    public void breakShape() {
        PGraphics.showWarning("This renderer cannot handle concave shapes or shapes with holes.");
    }
    
    public void endShape(final int mode) {
        if (this.ctm.isIdentity()) {
            for (int i = 0; i < this.vertexCount; ++i) {
                this.vertices[i][18] = this.vertices[i][0];
                this.vertices[i][19] = this.vertices[i][1];
            }
        }
        else {
            for (int i = 0; i < this.vertexCount; ++i) {
                this.vertices[i][18] = this.ctm.multX(this.vertices[i][0], this.vertices[i][1]);
                this.vertices[i][19] = this.ctm.multY(this.vertices[i][0], this.vertices[i][1]);
            }
        }
        this.fpolygon.texture(this.textureImage);
        this.spolygon.interpARGB = true;
        this.fpolygon.interpARGB = true;
        switch (this.shape) {
            case 2: {
                if (!this.stroke) {
                    break;
                }
                if (this.ctm.m00 == this.ctm.m11 && this.strokeWeight == 1.0f) {
                    for (int j = 0; j < this.vertexCount; ++j) {
                        this.thin_point(this.vertices[j][18], this.vertices[j][19], this.strokeColor);
                    }
                    break;
                }
                for (int j = 0; j < this.vertexCount; ++j) {
                    final float[] v = this.vertices[j];
                    this.thick_point(v[18], v[19], v[20], v[13], v[14], v[15], v[16]);
                }
                break;
            }
            case 4: {
                if (this.stroke) {
                    final int increment = (this.shape == 4) ? 2 : 1;
                    this.draw_lines(this.vertices, this.vertexCount - 1, 1, increment, 0);
                    break;
                }
                break;
            }
            case 11: {
                if (this.fill || this.textureImage != null) {
                    this.fpolygon.vertexCount = 3;
                    for (int j = 1; j < this.vertexCount - 1; ++j) {
                        this.fpolygon.vertices[2][3] = this.vertices[0][3];
                        this.fpolygon.vertices[2][4] = this.vertices[0][4];
                        this.fpolygon.vertices[2][5] = this.vertices[0][5];
                        this.fpolygon.vertices[2][6] = this.vertices[0][6];
                        this.fpolygon.vertices[2][18] = this.vertices[0][18];
                        this.fpolygon.vertices[2][19] = this.vertices[0][19];
                        if (this.textureImage != null) {
                            this.fpolygon.vertices[2][7] = this.vertices[0][7];
                            this.fpolygon.vertices[2][8] = this.vertices[0][8];
                        }
                        for (int k = 0; k < 2; ++k) {
                            this.fpolygon.vertices[k][3] = this.vertices[j + k][3];
                            this.fpolygon.vertices[k][4] = this.vertices[j + k][4];
                            this.fpolygon.vertices[k][5] = this.vertices[j + k][5];
                            this.fpolygon.vertices[k][6] = this.vertices[j + k][6];
                            this.fpolygon.vertices[k][18] = this.vertices[j + k][18];
                            this.fpolygon.vertices[k][19] = this.vertices[j + k][19];
                            if (this.textureImage != null) {
                                this.fpolygon.vertices[k][7] = this.vertices[j + k][7];
                                this.fpolygon.vertices[k][8] = this.vertices[j + k][8];
                            }
                        }
                        this.fpolygon.render();
                    }
                }
                if (this.stroke) {
                    for (int j = 1; j < this.vertexCount; ++j) {
                        this.draw_line(this.vertices[0], this.vertices[j]);
                    }
                    for (int j = 1; j < this.vertexCount - 1; ++j) {
                        this.draw_line(this.vertices[j], this.vertices[j + 1]);
                    }
                    this.draw_line(this.vertices[this.vertexCount - 1], this.vertices[1]);
                    break;
                }
                break;
            }
            case 9:
            case 10: {
                final int increment = (this.shape == 9) ? 3 : 1;
                if (this.fill || this.textureImage != null) {
                    this.fpolygon.vertexCount = 3;
                    for (int j = 0; j < this.vertexCount - 2; j += increment) {
                        for (int k = 0; k < 3; ++k) {
                            this.fpolygon.vertices[k][3] = this.vertices[j + k][3];
                            this.fpolygon.vertices[k][4] = this.vertices[j + k][4];
                            this.fpolygon.vertices[k][5] = this.vertices[j + k][5];
                            this.fpolygon.vertices[k][6] = this.vertices[j + k][6];
                            this.fpolygon.vertices[k][18] = this.vertices[j + k][18];
                            this.fpolygon.vertices[k][19] = this.vertices[j + k][19];
                            this.fpolygon.vertices[k][20] = this.vertices[j + k][20];
                            if (this.textureImage != null) {
                                this.fpolygon.vertices[k][7] = this.vertices[j + k][7];
                                this.fpolygon.vertices[k][8] = this.vertices[j + k][8];
                            }
                        }
                        this.fpolygon.render();
                    }
                }
                if (this.stroke) {
                    if (this.shape == 10) {
                        this.draw_lines(this.vertices, this.vertexCount - 1, 1, 1, 0);
                    }
                    else {
                        this.draw_lines(this.vertices, this.vertexCount - 1, 1, 1, 3);
                    }
                    this.draw_lines(this.vertices, this.vertexCount - 2, 2, increment, 0);
                    break;
                }
                break;
            }
            case 16: {
                if (this.fill || this.textureImage != null) {
                    this.fpolygon.vertexCount = 4;
                    for (int j = 0; j < this.vertexCount - 3; j += 4) {
                        for (int k = 0; k < 4; ++k) {
                            final int jj = j + k;
                            this.fpolygon.vertices[k][3] = this.vertices[jj][3];
                            this.fpolygon.vertices[k][4] = this.vertices[jj][4];
                            this.fpolygon.vertices[k][5] = this.vertices[jj][5];
                            this.fpolygon.vertices[k][6] = this.vertices[jj][6];
                            this.fpolygon.vertices[k][18] = this.vertices[jj][18];
                            this.fpolygon.vertices[k][19] = this.vertices[jj][19];
                            this.fpolygon.vertices[k][20] = this.vertices[jj][20];
                            if (this.textureImage != null) {
                                this.fpolygon.vertices[k][7] = this.vertices[jj][7];
                                this.fpolygon.vertices[k][8] = this.vertices[jj][8];
                            }
                        }
                        this.fpolygon.render();
                    }
                }
                if (this.stroke) {
                    for (int j = 0; j < this.vertexCount - 3; j += 4) {
                        this.draw_line(this.vertices[j + 0], this.vertices[j + 1]);
                        this.draw_line(this.vertices[j + 1], this.vertices[j + 2]);
                        this.draw_line(this.vertices[j + 2], this.vertices[j + 3]);
                        this.draw_line(this.vertices[j + 3], this.vertices[j + 0]);
                    }
                    break;
                }
                break;
            }
            case 17: {
                if (this.fill || this.textureImage != null) {
                    this.fpolygon.vertexCount = 4;
                    for (int j = 0; j < this.vertexCount - 3; j += 2) {
                        for (int k = 0; k < 4; ++k) {
                            int jj = j + k;
                            if (k == 2) {
                                jj = j + 3;
                            }
                            if (k == 3) {
                                jj = j + 2;
                            }
                            this.fpolygon.vertices[k][3] = this.vertices[jj][3];
                            this.fpolygon.vertices[k][4] = this.vertices[jj][4];
                            this.fpolygon.vertices[k][5] = this.vertices[jj][5];
                            this.fpolygon.vertices[k][6] = this.vertices[jj][6];
                            this.fpolygon.vertices[k][18] = this.vertices[jj][18];
                            this.fpolygon.vertices[k][19] = this.vertices[jj][19];
                            this.fpolygon.vertices[k][20] = this.vertices[jj][20];
                            if (this.textureImage != null) {
                                this.fpolygon.vertices[k][7] = this.vertices[jj][7];
                                this.fpolygon.vertices[k][8] = this.vertices[jj][8];
                            }
                        }
                        this.fpolygon.render();
                    }
                }
                if (this.stroke) {
                    this.draw_lines(this.vertices, this.vertexCount - 1, 1, 2, 0);
                    this.draw_lines(this.vertices, this.vertexCount - 2, 2, 1, 0);
                    break;
                }
                break;
            }
            case 20: {
                if (this.isConvex()) {
                    if (this.fill || this.textureImage != null) {
                        this.fpolygon.renderPolygon(this.vertices, this.vertexCount);
                    }
                    if (!this.stroke) {
                        break;
                    }
                    this.draw_lines(this.vertices, this.vertexCount - 1, 1, 1, 0);
                    if (mode == 2) {
                        this.draw_line(this.vertices[this.vertexCount - 1], this.vertices[0]);
                        break;
                    }
                    break;
                }
                else {
                    if (this.fill || this.textureImage != null) {
                        final boolean smoov = this.smooth;
                        if (this.stroke) {
                            this.smooth = false;
                        }
                        this.concaveRender();
                        if (this.stroke) {
                            this.smooth = smoov;
                        }
                    }
                    if (!this.stroke) {
                        break;
                    }
                    this.draw_lines(this.vertices, this.vertexCount - 1, 1, 1, 0);
                    if (mode == 2) {
                        this.draw_line(this.vertices[this.vertexCount - 1], this.vertices[0]);
                        break;
                    }
                    break;
                }
                break;
            }
        }
        this.shape = 0;
    }
    
    private boolean isConvex() {
        if (this.vertexCount < 3) {
            return true;
        }
        int flag = 0;
        for (int i = 0; i < this.vertexCount; ++i) {
            final float[] vi = this.vertices[i];
            final float[] vj = this.vertices[(i + 1) % this.vertexCount];
            final float[] vk = this.vertices[(i + 2) % this.vertexCount];
            final float calc = (vj[18] - vi[18]) * (vk[19] - vj[19]) - (vj[19] - vi[19]) * (vk[18] - vj[18]);
            if (calc < 0.0f) {
                flag |= 0x1;
            }
            else if (calc > 0.0f) {
                flag |= 0x2;
            }
            if (flag == 3) {
                return false;
            }
        }
        return flag == 0 || true;
    }
    
    protected void concaveRender() {
        if (this.vertexOrder == null || this.vertexOrder.length != this.vertices.length) {
            this.vertexOrder = new int[this.vertices.length];
        }
        if (this.tpolygon == null) {
            this.tpolygon = new PPolygon(this);
        }
        this.tpolygon.reset(3);
        float area = 0.0f;
        int p = this.vertexCount - 1;
        for (int q = 0; q < this.vertexCount; p = q++) {
            area += this.vertices[q][0] * this.vertices[p][1] - this.vertices[p][0] * this.vertices[q][1];
        }
        if (area == 0.0f) {
            return;
        }
        final float[] vfirst = this.vertices[0];
        final float[] vlast = this.vertices[this.vertexCount - 1];
        if (Math.abs(vfirst[0] - vlast[0]) < 1.0E-4f && Math.abs(vfirst[1] - vlast[1]) < 1.0E-4f && Math.abs(vfirst[2] - vlast[2]) < 1.0E-4f) {
            --this.vertexCount;
        }
        for (int i = 0; i < this.vertexCount; ++i) {
            this.vertexOrder[i] = ((area > 0.0f) ? i : (this.vertexCount - 1 - i));
        }
        int vc = this.vertexCount;
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
            final double Ax = -10.0f * this.vertices[this.vertexOrder[u]][0];
            final double Ay = 10.0f * this.vertices[this.vertexOrder[u]][1];
            final double Bx = -10.0f * this.vertices[this.vertexOrder[v]][0];
            final double By = 10.0f * this.vertices[this.vertexOrder[v]][1];
            final double Cx = -10.0f * this.vertices[this.vertexOrder[w]][0];
            final double Cy = 10.0f * this.vertices[this.vertexOrder[w]][1];
            if (9.999999747378752E-5 > (Bx - Ax) * (Cy - Ay) - (By - Ay) * (Cx - Ax)) {
                continue;
            }
            for (int p2 = 0; p2 < vc; ++p2) {
                if (p2 != u && p2 != v) {
                    if (p2 != w) {
                        final double Px = -10.0f * this.vertices[this.vertexOrder[p2]][0];
                        final double Py = 10.0f * this.vertices[this.vertexOrder[p2]][1];
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
            this.tpolygon.renderTriangle(this.vertices[this.vertexOrder[u]], this.vertices[this.vertexOrder[v]], this.vertices[this.vertexOrder[w]]);
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
    
    public void point(final float x, final float y, final float z) {
        PGraphics.showDepthWarningXYZ("point");
    }
    
    protected void rectImpl(final float x1f, final float y1f, final float x2f, final float y2f) {
        if (this.smooth || this.strokeAlpha || this.ctm.isWarped()) {
            super.rectImpl(x1f, y1f, x2f, y2f);
        }
        else {
            final int x1 = (int)(x1f + this.ctm.m02);
            final int y1 = (int)(y1f + this.ctm.m12);
            final int x2 = (int)(x2f + this.ctm.m02);
            final int y2 = (int)(y2f + this.ctm.m12);
            if (this.fill) {
                this.simple_rect_fill(x1, y1, x2, y2);
            }
            if (this.stroke) {
                if (this.strokeWeight == 1.0f) {
                    this.thin_flat_line(x1, y1, x2, y1);
                    this.thin_flat_line(x2, y1, x2, y2);
                    this.thin_flat_line(x2, y2, x1, y2);
                    this.thin_flat_line(x1, y2, x1, y1);
                }
                else {
                    this.thick_flat_line(x1, y1, this.strokeR, this.strokeG, this.strokeB, this.strokeA, x2, y1, this.strokeR, this.strokeG, this.strokeB, this.strokeA);
                    this.thick_flat_line(x2, y1, this.strokeR, this.strokeG, this.strokeB, this.strokeA, x2, y2, this.strokeR, this.strokeG, this.strokeB, this.strokeA);
                    this.thick_flat_line(x2, y2, this.strokeR, this.strokeG, this.strokeB, this.strokeA, x1, y2, this.strokeR, this.strokeG, this.strokeB, this.strokeA);
                    this.thick_flat_line(x1, y2, this.strokeR, this.strokeG, this.strokeB, this.strokeA, x1, y1, this.strokeR, this.strokeG, this.strokeB, this.strokeA);
                }
            }
        }
    }
    
    private void simple_rect_fill(int x1, int y1, int x2, int y2) {
        if (y2 < y1) {
            final int temp = y1;
            y1 = y2;
            y2 = temp;
        }
        if (x2 < x1) {
            final int temp = x1;
            x1 = x2;
            x2 = temp;
        }
        if (x1 > this.width1 || x2 < 0 || y1 > this.height1 || y2 < 0) {
            return;
        }
        if (x1 < 0) {
            x1 = 0;
        }
        if (x2 > this.width) {
            x2 = this.width;
        }
        if (y1 < 0) {
            y1 = 0;
        }
        if (y2 > this.height) {
            y2 = this.height;
        }
        final int ww = x2 - x1;
        if (this.fillAlpha) {
            for (int y3 = y1; y3 < y2; ++y3) {
                int index = y3 * this.width + x1;
                for (int x3 = 0; x3 < ww; ++x3) {
                    this.pixels[index] = this.blend_fill(this.pixels[index]);
                    ++index;
                }
            }
        }
        else {
            final int hh = y2 - y1;
            final int rowIndex;
            int index = rowIndex = y1 * this.width + x1;
            for (int i = 0; i < ww; ++i) {
                this.pixels[index + i] = this.fillColor;
            }
            for (int y4 = 0; y4 < hh; ++y4) {
                System.arraycopy(this.pixels, rowIndex, this.pixels, index, ww);
                index += this.width;
            }
        }
    }
    
    protected void ellipseImpl(final float x, final float y, final float w, final float h) {
        if (this.smooth || this.strokeWeight != 1.0f || this.fillAlpha || this.strokeAlpha || this.ctm.isWarped()) {
            final float radiusH = w / 2.0f;
            final float radiusV = h / 2.0f;
            final float centerX = x + radiusH;
            final float centerY = y + radiusV;
            final float sx1 = this.screenX(x, y);
            final float sy1 = this.screenY(x, y);
            final float sx2 = this.screenX(x + w, y + h);
            final float sy2 = this.screenY(x + w, y + h);
            final int accuracy = (int)(6.2831855f * PApplet.dist(sx1, sy1, sx2, sy2) / 8.0f);
            if (accuracy < 4) {
                return;
            }
            final float inc = 720.0f / accuracy;
            float val = 0.0f;
            if (this.fill) {
                final boolean savedStroke = this.stroke;
                this.stroke = false;
                this.beginShape();
                for (int i = 0; i < accuracy; ++i) {
                    this.vertex(centerX + PGraphics2D.cosLUT[(int)val] * radiusH, centerY + PGraphics2D.sinLUT[(int)val] * radiusV);
                    val += inc;
                }
                this.endShape(2);
                this.stroke = savedStroke;
            }
            if (this.stroke) {
                final boolean savedFill = this.fill;
                this.fill = false;
                val = 0.0f;
                this.beginShape();
                for (int i = 0; i < accuracy; ++i) {
                    this.vertex(centerX + PGraphics2D.cosLUT[(int)val] * radiusH, centerY + PGraphics2D.sinLUT[(int)val] * radiusV);
                    val += inc;
                }
                this.endShape(2);
                this.fill = savedFill;
            }
        }
        else {
            final float hradius = w / 2.0f;
            final float vradius = h / 2.0f;
            final int centerX2 = (int)(x + hradius + this.ctm.m02);
            final int centerY2 = (int)(y + vradius + this.ctm.m12);
            final int hradiusi = (int)hradius;
            final int vradiusi = (int)vradius;
            if (hradiusi == vradiusi) {
                if (this.fill) {
                    this.flat_circle_fill(centerX2, centerY2, hradiusi);
                }
                if (this.stroke) {
                    this.flat_circle_stroke(centerX2, centerY2, hradiusi);
                }
            }
            else {
                if (this.fill) {
                    this.flat_ellipse_internal(centerX2, centerY2, hradiusi, vradiusi, true);
                }
                if (this.stroke) {
                    this.flat_ellipse_internal(centerX2, centerY2, hradiusi, vradiusi, false);
                }
            }
        }
    }
    
    private void flat_circle_stroke(final int xC, final int yC, final int r) {
        int x = 0;
        int y = r;
        int u = 1;
        int v = 2 * r - 1;
        int E = 0;
        while (x < y) {
            this.thin_point(xC + x, yC + y, this.strokeColor);
            this.thin_point(xC + y, yC - x, this.strokeColor);
            this.thin_point(xC - x, yC - y, this.strokeColor);
            this.thin_point(xC - y, yC + x, this.strokeColor);
            ++x;
            E += u;
            u += 2;
            if (v < 2 * E) {
                --y;
                E -= v;
                v -= 2;
            }
            if (x > y) {
                break;
            }
            this.thin_point(xC + y, yC + x, this.strokeColor);
            this.thin_point(xC + x, yC - y, this.strokeColor);
            this.thin_point(xC - y, yC - x, this.strokeColor);
            this.thin_point(xC - x, yC + y, this.strokeColor);
        }
    }
    
    private void flat_circle_fill(final int xc, final int yc, final int r) {
        int x = 0;
        int y = r;
        int u = 1;
        int v = 2 * r - 1;
        int E = 0;
        while (x < y) {
            for (int xx = xc; xx < xc + x; ++xx) {
                this.thin_point(xx, yc + y, this.fillColor);
            }
            for (int xx = xc; xx < xc + y; ++xx) {
                this.thin_point(xx, yc - x, this.fillColor);
            }
            for (int xx = xc - x; xx < xc; ++xx) {
                this.thin_point(xx, yc - y, this.fillColor);
            }
            for (int xx = xc - y; xx < xc; ++xx) {
                this.thin_point(xx, yc + x, this.fillColor);
            }
            ++x;
            E += u;
            u += 2;
            if (v < 2 * E) {
                --y;
                E -= v;
                v -= 2;
            }
            if (x > y) {
                break;
            }
            for (int xx = xc; xx < xc + y; ++xx) {
                this.thin_point(xx, yc + x, this.fillColor);
            }
            for (int xx = xc; xx < xc + x; ++xx) {
                this.thin_point(xx, yc - y, this.fillColor);
            }
            for (int xx = xc - y; xx < xc; ++xx) {
                this.thin_point(xx, yc - x, this.fillColor);
            }
            for (int xx = xc - x; xx < xc; ++xx) {
                this.thin_point(xx, yc + y, this.fillColor);
            }
        }
    }
    
    private final void flat_ellipse_symmetry(final int centerX, final int centerY, final int ellipseX, final int ellipseY, final boolean filling) {
        if (filling) {
            for (int i = centerX - ellipseX + 1; i < centerX + ellipseX; ++i) {
                this.thin_point(i, centerY - ellipseY, this.fillColor);
                this.thin_point(i, centerY + ellipseY, this.fillColor);
            }
        }
        else {
            this.thin_point(centerX - ellipseX, centerY + ellipseY, this.strokeColor);
            this.thin_point(centerX + ellipseX, centerY + ellipseY, this.strokeColor);
            this.thin_point(centerX - ellipseX, centerY - ellipseY, this.strokeColor);
            this.thin_point(centerX + ellipseX, centerY - ellipseY, this.strokeColor);
        }
    }
    
    private void flat_ellipse_internal(final int centerX, final int centerY, final int a, final int b, final boolean filling) {
        final int a2 = a * a;
        final int b2 = b * b;
        int x = 0;
        int y = b;
        int s = a2 * (1 - 2 * b) + 2 * b2;
        int t = b2 - 2 * a2 * (2 * b - 1);
        this.flat_ellipse_symmetry(centerX, centerY, x, y, filling);
        do {
            if (s < 0) {
                s += 2 * b2 * (2 * x + 3);
                t += 4 * b2 * (x + 1);
                ++x;
            }
            else if (t < 0) {
                s += 2 * b2 * (2 * x + 3) - 4 * a2 * (y - 1);
                t += 4 * b2 * (x + 1) - 2 * a2 * (2 * y - 3);
                ++x;
                --y;
            }
            else {
                s -= 4 * a2 * (y - 1);
                t -= 2 * a2 * (2 * y - 3);
                --y;
            }
            this.flat_ellipse_symmetry(centerX, centerY, x, y, filling);
        } while (y > 0);
    }
    
    protected void arcImpl(final float x, final float y, final float w, final float h, final float start, final float stop) {
        final float hr = w / 2.0f;
        final float vr = h / 2.0f;
        final float centerX = x + hr;
        final float centerY = y + vr;
        if (this.fill) {
            final boolean savedStroke = this.stroke;
            this.stroke = false;
            final int startLUT = (int)(-0.5f + start / 6.2831855f * 720.0f);
            final int stopLUT = (int)(0.5f + stop / 6.2831855f * 720.0f);
            this.beginShape();
            this.vertex(centerX, centerY);
            for (int i = startLUT; i < stopLUT; ++i) {
                int ii = i % 720;
                if (ii < 0) {
                    ii += 720;
                }
                this.vertex(centerX + PGraphics2D.cosLUT[ii] * hr, centerY + PGraphics2D.sinLUT[ii] * vr);
            }
            this.endShape(2);
            this.stroke = savedStroke;
        }
        if (this.stroke) {
            final boolean savedFill = this.fill;
            this.fill = false;
            final int startLUT = (int)(0.5f + start / 6.2831855f * 720.0f);
            final int stopLUT = (int)(0.5f + stop / 6.2831855f * 720.0f);
            this.beginShape();
            for (int increment = 1, j = startLUT; j < stopLUT; j += increment) {
                int ii2 = j % 720;
                if (ii2 < 0) {
                    ii2 += 720;
                }
                this.vertex(centerX + PGraphics2D.cosLUT[ii2] * hr, centerY + PGraphics2D.sinLUT[ii2] * vr);
            }
            this.vertex(centerX + PGraphics2D.cosLUT[stopLUT % 720] * hr, centerY + PGraphics2D.sinLUT[stopLUT % 720] * vr);
            this.endShape();
            this.fill = savedFill;
        }
    }
    
    public void box(final float size) {
        PGraphics.showDepthWarning("box");
    }
    
    public void box(final float w, final float h, final float d) {
        PGraphics.showDepthWarning("box");
    }
    
    public void sphereDetail(final int res) {
        PGraphics.showDepthWarning("sphereDetail");
    }
    
    public void sphereDetail(final int ures, final int vres) {
        PGraphics.showDepthWarning("sphereDetail");
    }
    
    public void sphere(final float r) {
        PGraphics.showDepthWarning("sphere");
    }
    
    public void bezier(final float x1, final float y1, final float z1, final float x2, final float y2, final float z2, final float x3, final float y3, final float z3, final float x4, final float y4, final float z4) {
        PGraphics.showDepthWarningXYZ("bezier");
    }
    
    public void curve(final float x1, final float y1, final float z1, final float x2, final float y2, final float z2, final float x3, final float y3, final float z3, final float x4, final float y4, final float z4) {
        PGraphics.showDepthWarningXYZ("curve");
    }
    
    protected void imageImpl(final PImage image, final float x1, final float y1, final float x2, final float y2, final int u1, final int v1, final int u2, final int v2) {
        if (x2 - x1 == image.width && y2 - y1 == image.height && !this.tint && !this.ctm.isWarped()) {
            this.simple_image(image, (int)(x1 + this.ctm.m02), (int)(y1 + this.ctm.m12), u1, v1, u2, v2);
        }
        else {
            super.imageImpl(image, x1, y1, x2, y2, u1, v1, u2, v2);
        }
    }
    
    private void simple_image(final PImage image, int sx1, int sy1, int ix1, int iy1, int ix2, int iy2) {
        int sx2 = sx1 + image.width;
        int sy2 = sy1 + image.height;
        if (sx1 > this.width1 || sx2 < 0 || sy1 > this.height1 || sy2 < 0) {
            return;
        }
        if (sx1 < 0) {
            ix1 -= sx1;
            sx1 = 0;
        }
        if (sy1 < 0) {
            iy1 -= sy1;
            sy1 = 0;
        }
        if (sx2 > this.width) {
            ix2 -= sx2 - this.width;
            sx2 = this.width;
        }
        if (sy2 > this.height) {
            iy2 -= sy2 - this.height;
            sy2 = this.height;
        }
        int source = iy1 * image.width + ix1;
        int target = sy1 * this.width;
        if (image.format == 2) {
            for (int y = sy1; y < sy2; ++y) {
                int tx = 0;
                for (int x = sx1; x < sx2; ++x) {
                    this.pixels[target + x] = this.blend_color(this.pixels[target + x], image.pixels[source + tx++]);
                }
                source += image.width;
                target += this.width;
            }
        }
        else if (image.format == 4) {
            for (int y = sy1; y < sy2; ++y) {
                int tx = 0;
                for (int x = sx1; x < sx2; ++x) {
                    this.pixels[target + x] = this.blend_color_alpha(this.pixels[target + x], this.fillColor, image.pixels[source + tx++]);
                }
                source += image.width;
                target += this.width;
            }
        }
        else if (image.format == 1) {
            target += sx1;
            final int tw = sx2 - sx1;
            for (int y2 = sy1; y2 < sy2; ++y2) {
                System.arraycopy(image.pixels, source, this.pixels, target, tw);
                source += image.width;
                target += this.width;
            }
        }
    }
    
    private void thin_point_at(final int x, final int y, final float z, final int color) {
        final int index = y * this.width + x;
        this.pixels[index] = color;
    }
    
    private void thin_point_at_index(final int offset, final float z, final int color) {
        this.pixels[offset] = color;
    }
    
    private void thick_point(final float x, final float y, final float z, final float r, final float g, final float b, final float a) {
        this.spolygon.reset(4);
        this.spolygon.interpARGB = false;
        final float strokeWidth2 = this.strokeWeight / 2.0f;
        float[] svertex = this.spolygon.vertices[0];
        svertex[18] = x - strokeWidth2;
        svertex[19] = y - strokeWidth2;
        svertex[20] = z;
        svertex[3] = r;
        svertex[4] = g;
        svertex[5] = b;
        svertex[6] = a;
        svertex = this.spolygon.vertices[1];
        svertex[18] = x + strokeWidth2;
        svertex[19] = y - strokeWidth2;
        svertex[20] = z;
        svertex = this.spolygon.vertices[2];
        svertex[18] = x + strokeWidth2;
        svertex[19] = y + strokeWidth2;
        svertex[20] = z;
        svertex = this.spolygon.vertices[3];
        svertex[18] = x - strokeWidth2;
        svertex[19] = y + strokeWidth2;
        svertex[20] = z;
        this.spolygon.render();
    }
    
    private void thin_flat_line(final int x1, final int y1, final int x2, final int y2) {
        final int code1 = this.thin_flat_line_clip_code(x1, y1);
        final int code2 = this.thin_flat_line_clip_code(x2, y2);
        if ((code1 & code2) != 0x0) {
            return;
        }
        final int dip = code1 | code2;
        int nx1;
        int ny1;
        int nx2;
        int ny2;
        if (dip != 0) {
            float a1 = 0.0f;
            float a2 = 1.0f;
            float a3 = 0.0f;
            for (int i = 0; i < 4; ++i) {
                if ((dip >> i) % 2 == 1) {
                    a3 = this.thin_flat_line_slope(x1, y1, x2, y2, i + 1);
                    if ((code1 >> i) % 2 == 1) {
                        a1 = Math.max(a3, a1);
                    }
                    else {
                        a2 = Math.min(a3, a2);
                    }
                }
            }
            if (a1 > a2) {
                return;
            }
            nx1 = (int)(x1 + a1 * (x2 - x1));
            ny1 = (int)(y1 + a1 * (y2 - y1));
            nx2 = (int)(x1 + a2 * (x2 - x1));
            ny2 = (int)(y1 + a2 * (y2 - y1));
        }
        else {
            nx1 = x1;
            nx2 = x2;
            ny1 = y1;
            ny2 = y2;
        }
        boolean yLonger = false;
        int shortLen = ny2 - ny1;
        int longLen = nx2 - nx1;
        if (Math.abs(shortLen) > Math.abs(longLen)) {
            final int swap = shortLen;
            shortLen = longLen;
            longLen = swap;
            yLonger = true;
        }
        int decInc;
        if (longLen == 0) {
            decInc = 0;
        }
        else {
            decInc = (shortLen << 16) / longLen;
        }
        if (nx1 == nx2) {
            if (ny1 > ny2) {
                final int ty = ny1;
                ny1 = ny2;
                ny2 = ty;
            }
            int offset = ny1 * this.width + nx1;
            for (int j = ny1; j <= ny2; ++j) {
                this.thin_point_at_index(offset, 0.0f, this.strokeColor);
                offset += this.width;
            }
            return;
        }
        if (ny1 == ny2) {
            if (nx1 > nx2) {
                final int tx = nx1;
                nx1 = nx2;
                nx2 = tx;
            }
            int offset = ny1 * this.width + nx1;
            for (int j = nx1; j <= nx2; ++j) {
                this.thin_point_at_index(offset++, 0.0f, this.strokeColor);
            }
            return;
        }
        if (yLonger) {
            if (longLen > 0) {
                longLen += ny1;
                int k = 32768 + (nx1 << 16);
                while (ny1 <= longLen) {
                    this.thin_point_at(k >> 16, ny1, 0.0f, this.strokeColor);
                    k += decInc;
                    ++ny1;
                }
                return;
            }
            longLen += ny1;
            int k = 32768 + (nx1 << 16);
            while (ny1 >= longLen) {
                this.thin_point_at(k >> 16, ny1, 0.0f, this.strokeColor);
                k -= decInc;
                --ny1;
            }
        }
        else {
            if (longLen > 0) {
                longLen += nx1;
                int k = 32768 + (ny1 << 16);
                while (nx1 <= longLen) {
                    this.thin_point_at(nx1, k >> 16, 0.0f, this.strokeColor);
                    k += decInc;
                    ++nx1;
                }
                return;
            }
            longLen += nx1;
            int k = 32768 + (ny1 << 16);
            while (nx1 >= longLen) {
                this.thin_point_at(nx1, k >> 16, 0.0f, this.strokeColor);
                k -= decInc;
                --nx1;
            }
        }
    }
    
    private int thin_flat_line_clip_code(final float x, final float y) {
        return ((y < 0.0f) ? 8 : 0) | ((y > this.height1) ? 4 : 0) | ((x < 0.0f) ? 2 : 0) | ((x > this.width1) ? 1 : 0);
    }
    
    private float thin_flat_line_slope(final float x1, final float y1, final float x2, final float y2, final int border) {
        switch (border) {
            case 4: {
                return -y1 / (y2 - y1);
            }
            case 3: {
                return (this.height1 - y1) / (y2 - y1);
            }
            case 2: {
                return -x1 / (x2 - x1);
            }
            case 1: {
                return (this.width1 - x1) / (x2 - x1);
            }
            default: {
                return -1.0f;
            }
        }
    }
    
    private void thick_flat_line(final float ox1, final float oy1, final float r1, final float g1, final float b1, final float a1, final float ox2, final float oy2, final float r2, final float g2, final float b2, final float a2) {
        this.spolygon.interpARGB = (r1 != r2 || g1 != g2 || b1 != b2 || a1 != a2);
        final float dX = ox2 - ox1 + 1.0E-4f;
        final float dY = oy2 - oy1 + 1.0E-4f;
        final float len = (float)Math.sqrt(dX * dX + dY * dY);
        final float rh = this.strokeWeight / len / 2.0f;
        final float dx0 = rh * dY;
        final float dy0 = rh * dX;
        final float dx2 = rh * dY;
        final float dy2 = rh * dX;
        this.spolygon.reset(4);
        float[] svertex = this.spolygon.vertices[0];
        svertex[18] = ox1 + dx0;
        svertex[19] = oy1 - dy0;
        svertex[3] = r1;
        svertex[4] = g1;
        svertex[5] = b1;
        svertex[6] = a1;
        svertex = this.spolygon.vertices[1];
        svertex[18] = ox1 - dx0;
        svertex[19] = oy1 + dy0;
        svertex[3] = r1;
        svertex[4] = g1;
        svertex[5] = b1;
        svertex[6] = a1;
        svertex = this.spolygon.vertices[2];
        svertex[18] = ox2 - dx2;
        svertex[19] = oy2 + dy2;
        svertex[3] = r2;
        svertex[4] = g2;
        svertex[5] = b2;
        svertex[6] = a2;
        svertex = this.spolygon.vertices[3];
        svertex[18] = ox2 + dx2;
        svertex[19] = oy2 - dy2;
        svertex[3] = r2;
        svertex[4] = g2;
        svertex[5] = b2;
        svertex[6] = a2;
        this.spolygon.render();
    }
    
    private void draw_line(final float[] v1, final float[] v2) {
        if (this.strokeWeight == 1.0f) {
            if (this.line == null) {
                this.line = new PLine(this);
            }
            this.line.reset();
            this.line.setIntensities(v1[13], v1[14], v1[15], v1[16], v2[13], v2[14], v2[15], v2[16]);
            this.line.setVertices(v1[18], v1[19], v1[20], v2[18], v2[19], v2[20]);
            this.line.draw();
        }
        else {
            this.thick_flat_line(v1[18], v1[19], v1[13], v1[14], v1[15], v1[16], v2[18], v2[19], v2[13], v2[14], v2[15], v2[16]);
        }
    }
    
    private void draw_lines(final float[][] vertices, final int max, final int offset, final int increment, final int skip) {
        if (this.strokeWeight == 1.0f) {
            for (int i = 0; i < max; i += increment) {
                if (skip == 0 || (i + offset) % skip != 0) {
                    final float[] a = vertices[i];
                    final float[] b = vertices[i + offset];
                    if (this.line == null) {
                        this.line = new PLine(this);
                    }
                    this.line.reset();
                    this.line.setIntensities(a[13], a[14], a[15], a[16], b[13], b[14], b[15], b[16]);
                    this.line.setVertices(a[18], a[19], a[20], b[18], b[19], b[20]);
                    this.line.draw();
                }
            }
        }
        else {
            for (int i = 0; i < max; i += increment) {
                if (skip == 0 || (i + offset) % skip != 0) {
                    final float[] v1 = vertices[i];
                    final float[] v2 = vertices[i + offset];
                    this.thick_flat_line(v1[18], v1[19], v1[13], v1[14], v1[15], v1[16], v2[18], v2[19], v2[13], v2[14], v2[15], v2[16]);
                }
            }
        }
    }
    
    private void thin_point(final float fx, final float fy, final int color) {
        final int x = (int)(fx + 0.4999f);
        final int y = (int)(fy + 0.4999f);
        if (x < 0 || x > this.width1 || y < 0 || y > this.height1) {
            return;
        }
        final int index = y * this.width + x;
        if ((color & 0xFF000000) == 0xFF000000) {
            this.pixels[index] = color;
        }
        else {
            final int a2 = color >> 24 & 0xFF;
            final int a3 = a2 ^ 0xFF;
            final int p2 = this.strokeColor;
            final int p3 = this.pixels[index];
            final int r = a3 * (p3 >> 16 & 0xFF) + a2 * (p2 >> 16 & 0xFF) & 0xFF00;
            final int g = a3 * (p3 >> 8 & 0xFF) + a2 * (p2 >> 8 & 0xFF) & 0xFF00;
            final int b = a3 * (p3 & 0xFF) + a2 * (p2 & 0xFF) >> 8;
            this.pixels[index] = (0xFF000000 | r << 8 | g | b);
        }
    }
    
    public void translate(final float tx, final float ty) {
        this.ctm.translate(tx, ty);
    }
    
    public void translate(final float tx, final float ty, final float tz) {
        PGraphics.showDepthWarningXYZ("translate");
    }
    
    public void rotate(final float angle) {
        this.ctm.rotate(angle);
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
        PGraphics.showVariationWarning("rotate(angle, x, y, z)");
    }
    
    public void scale(final float s) {
        this.ctm.scale(s);
    }
    
    public void scale(final float sx, final float sy) {
        this.ctm.scale(sx, sy);
    }
    
    public void scale(final float x, final float y, final float z) {
        PGraphics.showDepthWarningXYZ("scale");
    }
    
    public void pushMatrix() {
        if (this.matrixStackDepth == 32) {
            throw new RuntimeException("Too many calls to pushMatrix().");
        }
        this.ctm.get(this.matrixStack[this.matrixStackDepth]);
        ++this.matrixStackDepth;
    }
    
    public void popMatrix() {
        if (this.matrixStackDepth == 0) {
            throw new RuntimeException("Too many calls to popMatrix(), and not enough to pushMatrix().");
        }
        --this.matrixStackDepth;
        this.ctm.set(this.matrixStack[this.matrixStackDepth]);
    }
    
    public void resetMatrix() {
        this.ctm.reset();
    }
    
    public void applyMatrix(final float n00, final float n01, final float n02, final float n10, final float n11, final float n12) {
        this.ctm.apply(n00, n01, n02, n10, n11, n12);
    }
    
    public void applyMatrix(final float n00, final float n01, final float n02, final float n03, final float n10, final float n11, final float n12, final float n13, final float n20, final float n21, final float n22, final float n23, final float n30, final float n31, final float n32, final float n33) {
        PGraphics.showDepthWarningXYZ("applyMatrix");
    }
    
    public void printMatrix() {
        this.ctm.print();
    }
    
    public float screenX(final float x, final float y) {
        return this.ctm.m00 * x + this.ctm.m01 * y + this.ctm.m02;
    }
    
    public float screenY(final float x, final float y) {
        return this.ctm.m10 * x + this.ctm.m11 * y + this.ctm.m12;
    }
    
    protected void backgroundImpl() {
        Arrays.fill(this.pixels, this.backgroundColor);
    }
    
    private final int blend_fill(final int p1) {
        final int a2 = this.fillAi;
        final int a3 = a2 ^ 0xFF;
        final int r = a3 * (p1 >> 16 & 0xFF) + a2 * this.fillRi & 0xFF00;
        final int g = a3 * (p1 >> 8 & 0xFF) + a2 * this.fillGi & 0xFF00;
        final int b = a3 * (p1 & 0xFF) + a2 * this.fillBi & 0xFF00;
        return 0xFF000000 | r << 8 | g | b >> 8;
    }
    
    private final int blend_color(final int p1, final int p2) {
        final int a2 = p2 >>> 24;
        if (a2 == 255) {
            return p2;
        }
        final int a3 = a2 ^ 0xFF;
        final int r = a3 * (p1 >> 16 & 0xFF) + a2 * (p2 >> 16 & 0xFF) & 0xFF00;
        final int g = a3 * (p1 >> 8 & 0xFF) + a2 * (p2 >> 8 & 0xFF) & 0xFF00;
        final int b = a3 * (p1 & 0xFF) + a2 * (p2 & 0xFF) >> 8;
        return 0xFF000000 | r << 8 | g | b;
    }
    
    private final int blend_color_alpha(final int p1, final int p2, int a2) {
        a2 = a2 * (p2 >>> 24) >> 8;
        final int a3 = a2 ^ 0xFF;
        final int r = a3 * (p1 >> 16 & 0xFF) + a2 * (p2 >> 16 & 0xFF) & 0xFF00;
        final int g = a3 * (p1 >> 8 & 0xFF) + a2 * (p2 >> 8 & 0xFF) & 0xFF00;
        final int b = a3 * (p1 & 0xFF) + a2 * (p2 & 0xFF) >> 8;
        return 0xFF000000 | r << 8 | g | b;
    }
}
