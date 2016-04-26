// 
// Decompiled by Procyon v0.5.30
// 

package processing.core;

public class PPolygon implements PConstants
{
    static final int DEFAULT_SIZE = 64;
    float[][] vertices;
    int vertexCount;
    float[] r;
    float[] dr;
    float[] l;
    float[] dl;
    float[] sp;
    float[] sdp;
    protected boolean interpX;
    protected boolean interpUV;
    protected boolean interpARGB;
    private int rgba;
    private int r2;
    private int g2;
    private int b2;
    private int a2;
    private int a2orig;
    PGraphics parent;
    int[] pixels;
    int width;
    int height;
    int width1;
    int height1;
    PImage timage;
    int[] tpixels;
    int theight;
    int twidth;
    int theight1;
    int twidth1;
    int tformat;
    static final int SUBXRES = 8;
    static final int SUBXRES1 = 7;
    static final int SUBYRES = 8;
    static final int SUBYRES1 = 7;
    static final int MAX_COVERAGE = 64;
    boolean smooth;
    int firstModY;
    int lastModY;
    int lastY;
    int[] aaleft;
    int[] aaright;
    int aaleftmin;
    int aarightmin;
    int aaleftmax;
    int aarightmax;
    int aaleftfull;
    int aarightfull;
    
    private final int MODYRES(final int y) {
        return y & 0x7;
    }
    
    public PPolygon(final PGraphics iparent) {
        this.vertices = new float[64][36];
        this.r = new float[64];
        this.dr = new float[64];
        this.l = new float[64];
        this.dl = new float[64];
        this.sp = new float[64];
        this.sdp = new float[64];
        this.aaleft = new int[8];
        this.aaright = new int[8];
        this.parent = iparent;
        this.reset(0);
    }
    
    protected void reset(final int count) {
        this.vertexCount = count;
        this.interpX = true;
        this.interpUV = false;
        this.interpARGB = true;
        this.timage = null;
    }
    
    protected float[] nextVertex() {
        if (this.vertexCount == this.vertices.length) {
            final float[][] temp = new float[this.vertexCount << 1][36];
            System.arraycopy(this.vertices, 0, temp, 0, this.vertexCount);
            this.vertices = temp;
            this.r = new float[this.vertices.length];
            this.dr = new float[this.vertices.length];
            this.l = new float[this.vertices.length];
            this.dl = new float[this.vertices.length];
            this.sp = new float[this.vertices.length];
            this.sdp = new float[this.vertices.length];
        }
        return this.vertices[this.vertexCount++];
    }
    
    protected void texture(final PImage image) {
        this.timage = image;
        if (image != null) {
            this.tpixels = image.pixels;
            this.twidth = image.width;
            this.theight = image.height;
            this.tformat = image.format;
            this.twidth1 = this.twidth - 1;
            this.theight1 = this.theight - 1;
            this.interpUV = true;
        }
        else {
            this.interpUV = false;
        }
    }
    
    protected void renderPolygon(final float[][] v, final int count) {
        this.vertices = v;
        this.vertexCount = count;
        if (this.r.length < this.vertexCount) {
            this.r = new float[this.vertexCount];
            this.dr = new float[this.vertexCount];
            this.l = new float[this.vertexCount];
            this.dl = new float[this.vertexCount];
            this.sp = new float[this.vertexCount];
            this.sdp = new float[this.vertexCount];
        }
        this.render();
        this.checkExpand();
    }
    
    protected void renderTriangle(final float[] v1, final float[] v2, final float[] v3) {
        this.vertices[0] = v1;
        this.vertices[1] = v2;
        this.vertices[2] = v3;
        this.render();
        this.checkExpand();
    }
    
    protected void checkExpand() {
        if (this.smooth) {
            for (int i = 0; i < this.vertexCount; ++i) {
                final float[] array = this.vertices[i];
                final int n = 18;
                array[n] /= 8.0f;
                final float[] array2 = this.vertices[i];
                final int n2 = 19;
                array2[n2] /= 8.0f;
            }
        }
    }
    
    protected void render() {
        if (this.vertexCount < 3) {
            return;
        }
        this.pixels = this.parent.pixels;
        this.smooth = this.parent.smooth;
        this.width = (this.smooth ? (this.parent.width * 8) : this.parent.width);
        this.height = (this.smooth ? (this.parent.height * 8) : this.parent.height);
        this.width1 = this.width - 1;
        this.height1 = this.height - 1;
        if (!this.interpARGB) {
            this.r2 = (int)(this.vertices[0][3] * 255.0f);
            this.g2 = (int)(this.vertices[0][4] * 255.0f);
            this.b2 = (int)(this.vertices[0][5] * 255.0f);
            this.a2 = (int)(this.vertices[0][6] * 255.0f);
            this.a2orig = this.a2;
            this.rgba = (0xFF000000 | this.r2 << 16 | this.g2 << 8 | this.b2);
        }
        for (int i = 0; i < this.vertexCount; ++i) {
            this.r[i] = 0.0f;
            this.dr[i] = 0.0f;
            this.l[i] = 0.0f;
            this.dl[i] = 0.0f;
        }
        if (this.smooth) {
            for (int i = 0; i < this.vertexCount; ++i) {
                final float[] array = this.vertices[i];
                final int n = 18;
                array[n] *= 8.0f;
                final float[] array2 = this.vertices[i];
                final int n2 = 19;
                array2[n2] *= 8.0f;
            }
            this.firstModY = -1;
        }
        int topi = 0;
        float ymin = this.vertices[0][19];
        float ymax = this.vertices[0][19];
        for (int j = 1; j < this.vertexCount; ++j) {
            if (this.vertices[j][19] < ymin) {
                ymin = this.vertices[j][19];
                topi = j;
            }
            if (this.vertices[j][19] > ymax) {
                ymax = this.vertices[j][19];
            }
        }
        this.lastY = (int)(ymax - 0.5f);
        int lefti = topi;
        int righti = topi;
        int y = (int)(ymin + 0.5f);
        int lefty = y - 1;
        int righty = y - 1;
        this.interpX = true;
        int remaining = this.vertexCount;
        while (remaining > 0) {
            while (lefty <= y) {
                if (remaining <= 0) {
                    break;
                }
                --remaining;
                final int k = (lefti != 0) ? (lefti - 1) : (this.vertexCount - 1);
                this.incrementalizeY(this.vertices[lefti], this.vertices[k], this.l, this.dl, y);
                lefty = (int)(this.vertices[k][19] + 0.5f);
                lefti = k;
            }
            while (righty <= y) {
                if (remaining <= 0) {
                    break;
                }
                --remaining;
                final int k = (righti != this.vertexCount - 1) ? (righti + 1) : 0;
                this.incrementalizeY(this.vertices[righti], this.vertices[k], this.r, this.dr, y);
                righty = (int)(this.vertices[k][19] + 0.5f);
                righti = k;
            }
            while (y < lefty && y < righty) {
                if (y >= 0 && y < this.height) {
                    if (this.l[18] <= this.r[18]) {
                        this.scanline(y, this.l, this.r);
                    }
                    else {
                        this.scanline(y, this.r, this.l);
                    }
                }
                ++y;
                this.increment(this.l, this.dl);
                this.increment(this.r, this.dr);
            }
        }
    }
    
    private void scanline(final int y, final float[] l, final float[] r) {
        for (int i = 0; i < this.vertexCount; ++i) {
            this.sp[i] = 0.0f;
            this.sdp[i] = 0.0f;
        }
        int lx = (int)(l[18] + 0.49999f);
        if (lx < 0) {
            lx = 0;
        }
        int rx = (int)(r[18] - 0.5f);
        if (rx > this.width1) {
            rx = this.width1;
        }
        if (lx > rx) {
            return;
        }
        if (this.smooth) {
            final int mody = this.MODYRES(y);
            this.aaleft[mody] = lx;
            this.aaright[mody] = rx;
            if (this.firstModY == -1) {
                this.firstModY = mody;
                this.aaleftmin = lx;
                this.aaleftmax = lx;
                this.aarightmin = rx;
                this.aarightmax = rx;
            }
            else {
                if (this.aaleftmin > this.aaleft[mody]) {
                    this.aaleftmin = this.aaleft[mody];
                }
                if (this.aaleftmax < this.aaleft[mody]) {
                    this.aaleftmax = this.aaleft[mody];
                }
                if (this.aarightmin > this.aaright[mody]) {
                    this.aarightmin = this.aaright[mody];
                }
                if (this.aarightmax < this.aaright[mody]) {
                    this.aarightmax = this.aaright[mody];
                }
            }
            this.lastModY = mody;
            if (mody != 7 && y != this.lastY) {
                return;
            }
            this.aaleftfull = this.aaleftmax / 8 + 1;
            this.aarightfull = this.aarightmin / 8 - 1;
        }
        this.incrementalizeX(l, r, this.sp, this.sdp, lx);
        final int offset = this.smooth ? (this.parent.width * (y / 8)) : (this.parent.width * y);
        int truelx = 0;
        int truerx = 0;
        if (this.smooth) {
            truelx = lx / 8;
            truerx = (rx + 7) / 8;
            lx = this.aaleftmin / 8;
            rx = (this.aarightmax + 7) / 8;
            if (lx < 0) {
                lx = 0;
            }
            if (rx > this.parent.width1) {
                rx = this.parent.width1;
            }
        }
        this.interpX = false;
        for (int x = lx; x <= rx; ++x) {
            if (this.interpUV) {
                int tu = (int)(this.sp[7] * this.twidth);
                int tv = (int)(this.sp[8] * this.theight);
                if (tu > this.twidth1) {
                    tu = this.twidth1;
                }
                if (tv > this.theight1) {
                    tv = this.theight1;
                }
                if (tu < 0) {
                    tu = 0;
                }
                if (tv < 0) {
                    tv = 0;
                }
                final int txy = tv * this.twidth + tu;
                final int tuf1 = (int)(255.0f * (this.sp[7] * this.twidth - tu));
                final int tvf1 = (int)(255.0f * (this.sp[8] * this.theight - tv));
                final int tuf2 = 255 - tuf1;
                final int tvf2 = 255 - tvf1;
                final int pixel00 = this.tpixels[txy];
                final int pixel2 = (tv < this.theight1) ? this.tpixels[txy + this.twidth] : this.tpixels[txy];
                final int pixel3 = (tu < this.twidth1) ? this.tpixels[txy + 1] : this.tpixels[txy];
                final int pixel4 = (tv < this.theight1 && tu < this.twidth1) ? this.tpixels[txy + this.twidth + 1] : this.tpixels[txy];
                int ta;
                if (this.tformat == 4) {
                    final int px0 = pixel00 * tuf2 + pixel3 * tuf1 >> 8;
                    final int px2 = pixel2 * tuf2 + pixel4 * tuf1 >> 8;
                    ta = (px0 * tvf2 + px2 * tvf1 >> 8) * (this.interpARGB ? ((int)(this.sp[6] * 255.0f)) : this.a2orig) >> 8;
                }
                else if (this.tformat == 2) {
                    final int p00 = pixel00 >> 24 & 0xFF;
                    final int p2 = pixel2 >> 24 & 0xFF;
                    final int p3 = pixel3 >> 24 & 0xFF;
                    final int p4 = pixel4 >> 24 & 0xFF;
                    final int px0 = p00 * tuf2 + p3 * tuf1 >> 8;
                    final int px2 = p2 * tuf2 + p4 * tuf1 >> 8;
                    ta = (px0 * tvf2 + px2 * tvf1 >> 8) * (this.interpARGB ? ((int)(this.sp[6] * 255.0f)) : this.a2orig) >> 8;
                }
                else {
                    ta = (this.interpARGB ? ((int)(this.sp[6] * 255.0f)) : this.a2orig);
                }
                int tr;
                int tg;
                int tb;
                if (this.tformat == 1 || this.tformat == 2) {
                    int p00 = pixel00 >> 16 & 0xFF;
                    int p2 = pixel2 >> 16 & 0xFF;
                    int p3 = pixel3 >> 16 & 0xFF;
                    int p4 = pixel4 >> 16 & 0xFF;
                    int px0 = p00 * tuf2 + p3 * tuf1 >> 8;
                    int px2 = p2 * tuf2 + p4 * tuf1 >> 8;
                    tr = (px0 * tvf2 + px2 * tvf1 >> 8) * (this.interpARGB ? ((int)(this.sp[3] * 255.0f)) : this.r2) >> 8;
                    p00 = (pixel00 >> 8 & 0xFF);
                    p2 = (pixel2 >> 8 & 0xFF);
                    p3 = (pixel3 >> 8 & 0xFF);
                    p4 = (pixel4 >> 8 & 0xFF);
                    px0 = p00 * tuf2 + p3 * tuf1 >> 8;
                    px2 = p2 * tuf2 + p4 * tuf1 >> 8;
                    tg = (px0 * tvf2 + px2 * tvf1 >> 8) * (this.interpARGB ? ((int)(this.sp[4] * 255.0f)) : this.g2) >> 8;
                    p00 = (pixel00 & 0xFF);
                    p2 = (pixel2 & 0xFF);
                    p3 = (pixel3 & 0xFF);
                    p4 = (pixel4 & 0xFF);
                    px0 = p00 * tuf2 + p3 * tuf1 >> 8;
                    px2 = p2 * tuf2 + p4 * tuf1 >> 8;
                    tb = (px0 * tvf2 + px2 * tvf1 >> 8) * (this.interpARGB ? ((int)(this.sp[5] * 255.0f)) : this.b2) >> 8;
                }
                else if (this.interpARGB) {
                    tr = (int)(this.sp[3] * 255.0f);
                    tg = (int)(this.sp[4] * 255.0f);
                    tb = (int)(this.sp[5] * 255.0f);
                }
                else {
                    tr = this.r2;
                    tg = this.g2;
                    tb = this.b2;
                }
                final int weight = this.smooth ? this.coverage(x) : 255;
                if (weight != 255) {
                    ta = ta * weight >> 8;
                }
                if (ta == 254 || ta == 255) {
                    this.pixels[offset + x] = (0xFF000000 | tr << 16 | tg << 8 | tb);
                }
                else {
                    final int a1 = 255 - ta;
                    final int r2 = this.pixels[offset + x] >> 16 & 0xFF;
                    final int g1 = this.pixels[offset + x] >> 8 & 0xFF;
                    final int b1 = this.pixels[offset + x] & 0xFF;
                    this.pixels[offset + x] = (0xFF000000 | tr * ta + r2 * a1 >> 8 << 16 | (tg * ta + g1 * a1 & 0xFF00) | tb * ta + b1 * a1 >> 8);
                }
            }
            else {
                int weight2 = this.smooth ? this.coverage(x) : 255;
                if (this.interpARGB) {
                    this.r2 = (int)(this.sp[3] * 255.0f);
                    this.g2 = (int)(this.sp[4] * 255.0f);
                    this.b2 = (int)(this.sp[5] * 255.0f);
                    if (this.sp[6] != 1.0f) {
                        weight2 = weight2 * (int)(this.sp[6] * 255.0f) >> 8;
                    }
                    if (weight2 == 255) {
                        this.rgba = (0xFF000000 | this.r2 << 16 | this.g2 << 8 | this.b2);
                    }
                }
                else if (this.a2orig != 255) {
                    weight2 = weight2 * this.a2orig >> 8;
                }
                if (weight2 == 255) {
                    this.pixels[offset + x] = this.rgba;
                }
                else {
                    final int r3 = this.pixels[offset + x] >> 16 & 0xFF;
                    final int g2 = this.pixels[offset + x] >> 8 & 0xFF;
                    final int b2 = this.pixels[offset + x] & 0xFF;
                    this.a2 = weight2;
                    final int a2 = 255 - this.a2;
                    this.pixels[offset + x] = (0xFF000000 | r3 * a2 + this.r2 * this.a2 >> 8 << 16 | g2 * a2 + this.g2 * this.a2 >> 8 << 8 | b2 * a2 + this.b2 * this.a2 >> 8);
                }
            }
            if (!this.smooth || (x >= truelx && x <= truerx)) {
                this.increment(this.sp, this.sdp);
            }
        }
        this.firstModY = -1;
        this.interpX = true;
    }
    
    private int coverage(final int x) {
        if (x >= this.aaleftfull && x <= this.aarightfull && this.firstModY == 0 && this.lastModY == 7) {
            return 255;
        }
        final int pixelLeft = x * 8;
        final int pixelRight = pixelLeft + 8;
        int amt = 0;
        for (int i = this.firstModY; i <= this.lastModY; ++i) {
            if (this.aaleft[i] <= pixelRight) {
                if (this.aaright[i] >= pixelLeft) {
                    amt += ((this.aaright[i] < pixelRight) ? this.aaright[i] : pixelRight) - ((this.aaleft[i] > pixelLeft) ? this.aaleft[i] : pixelLeft);
                }
            }
        }
        amt <<= 2;
        return (amt == 256) ? 255 : amt;
    }
    
    private void incrementalizeY(final float[] p1, final float[] p2, final float[] p, final float[] dp, final int y) {
        float delta = p2[19] - p1[19];
        if (delta == 0.0f) {
            delta = 1.0f;
        }
        final float fraction = y + 0.5f - p1[19];
        if (this.interpX) {
            dp[18] = (p2[18] - p1[18]) / delta;
            p[18] = p1[18] + dp[18] * fraction;
        }
        if (this.interpARGB) {
            dp[3] = (p2[3] - p1[3]) / delta;
            dp[4] = (p2[4] - p1[4]) / delta;
            dp[5] = (p2[5] - p1[5]) / delta;
            dp[6] = (p2[6] - p1[6]) / delta;
            p[3] = p1[3] + dp[3] * fraction;
            p[4] = p1[4] + dp[4] * fraction;
            p[5] = p1[5] + dp[5] * fraction;
            p[6] = p1[6] + dp[6] * fraction;
        }
        if (this.interpUV) {
            dp[7] = (p2[7] - p1[7]) / delta;
            dp[8] = (p2[8] - p1[8]) / delta;
            p[7] = p1[7] + dp[7] * fraction;
            p[8] = p1[8] + dp[8] * fraction;
        }
    }
    
    private void incrementalizeX(final float[] p1, final float[] p2, final float[] p, final float[] dp, final int x) {
        float delta = p2[18] - p1[18];
        if (delta == 0.0f) {
            delta = 1.0f;
        }
        float fraction = x + 0.5f - p1[18];
        if (this.smooth) {
            delta /= 8.0f;
            fraction /= 8.0f;
        }
        if (this.interpX) {
            dp[18] = (p2[18] - p1[18]) / delta;
            p[18] = p1[18] + dp[18] * fraction;
        }
        if (this.interpARGB) {
            dp[3] = (p2[3] - p1[3]) / delta;
            dp[4] = (p2[4] - p1[4]) / delta;
            dp[5] = (p2[5] - p1[5]) / delta;
            dp[6] = (p2[6] - p1[6]) / delta;
            p[3] = p1[3] + dp[3] * fraction;
            p[4] = p1[4] + dp[4] * fraction;
            p[5] = p1[5] + dp[5] * fraction;
            p[6] = p1[6] + dp[6] * fraction;
        }
        if (this.interpUV) {
            dp[7] = (p2[7] - p1[7]) / delta;
            dp[8] = (p2[8] - p1[8]) / delta;
            p[7] = p1[7] + dp[7] * fraction;
            p[8] = p1[8] + dp[8] * fraction;
        }
    }
    
    private void increment(final float[] p, final float[] dp) {
        if (this.interpX) {
            final int n = 18;
            p[n] += dp[18];
        }
        if (this.interpARGB) {
            final int n2 = 3;
            p[n2] += dp[3];
            final int n3 = 4;
            p[n3] += dp[4];
            final int n4 = 5;
            p[n4] += dp[5];
            final int n5 = 6;
            p[n5] += dp[6];
        }
        if (this.interpUV) {
            final int n6 = 7;
            p[n6] += dp[7];
            final int n7 = 8;
            p[n7] += dp[8];
        }
    }
}
