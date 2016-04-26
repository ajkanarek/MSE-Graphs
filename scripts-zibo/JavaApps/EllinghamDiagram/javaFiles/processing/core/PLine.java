// 
// Decompiled by Procyon v0.5.30
// 

package processing.core;

public class PLine implements PConstants
{
    private int[] m_pixels;
    private float[] m_zbuffer;
    private int m_index;
    static final int R_COLOR = 1;
    static final int R_ALPHA = 2;
    static final int R_SPATIAL = 8;
    static final int R_THICK = 4;
    static final int R_SMOOTH = 16;
    private int SCREEN_WIDTH;
    private int SCREEN_HEIGHT;
    private int SCREEN_WIDTH1;
    private int SCREEN_HEIGHT1;
    public boolean INTERPOLATE_RGB;
    public boolean INTERPOLATE_ALPHA;
    public boolean INTERPOLATE_Z;
    public boolean INTERPOLATE_THICK;
    private boolean SMOOTH;
    private int m_stroke;
    public int m_drawFlags;
    private float[] x_array;
    private float[] y_array;
    private float[] z_array;
    private float[] r_array;
    private float[] g_array;
    private float[] b_array;
    private float[] a_array;
    private int o0;
    private int o1;
    private float m_r0;
    private float m_g0;
    private float m_b0;
    private float m_a0;
    private float m_z0;
    private float dz;
    private float dr;
    private float dg;
    private float db;
    private float da;
    private PGraphics parent;
    
    public PLine(final PGraphics g) {
        this.INTERPOLATE_Z = false;
        this.x_array = new float[2];
        this.y_array = new float[2];
        this.z_array = new float[2];
        this.r_array = new float[2];
        this.g_array = new float[2];
        this.b_array = new float[2];
        this.a_array = new float[2];
        this.parent = g;
    }
    
    public void reset() {
        this.SCREEN_WIDTH = this.parent.width;
        this.SCREEN_HEIGHT = this.parent.height;
        this.SCREEN_WIDTH1 = this.SCREEN_WIDTH - 1;
        this.SCREEN_HEIGHT1 = this.SCREEN_HEIGHT - 1;
        this.m_pixels = this.parent.pixels;
        if (this.parent instanceof PGraphics3D) {
            this.m_zbuffer = ((PGraphics3D)this.parent).zbuffer;
        }
        this.INTERPOLATE_RGB = false;
        this.INTERPOLATE_ALPHA = false;
        this.m_drawFlags = 0;
        this.m_index = 0;
    }
    
    public void setVertices(final float x0, final float y0, final float z0, final float x1, final float y1, final float z1) {
        if (z0 != z1 || z0 != 0.0f || z1 != 0.0f || this.INTERPOLATE_Z) {
            this.INTERPOLATE_Z = true;
            this.m_drawFlags |= 0x8;
        }
        else {
            this.INTERPOLATE_Z = false;
            this.m_drawFlags &= 0xFFFFFFF7;
        }
        this.z_array[0] = z0;
        this.z_array[1] = z1;
        this.x_array[0] = x0;
        this.x_array[1] = x1;
        this.y_array[0] = y0;
        this.y_array[1] = y1;
    }
    
    public void setIntensities(final float r0, final float g0, final float b0, final float a0, final float r1, final float g1, final float b1, final float a1) {
        this.a_array[0] = (a0 * 253.0f + 1.0f) * 65536.0f;
        this.a_array[1] = (a1 * 253.0f + 1.0f) * 65536.0f;
        if (a0 != 1.0f || a1 != 1.0f) {
            this.INTERPOLATE_ALPHA = true;
            this.m_drawFlags |= 0x2;
        }
        else {
            this.INTERPOLATE_ALPHA = false;
            this.m_drawFlags &= 0xFFFFFFFD;
        }
        this.r_array[0] = (r0 * 253.0f + 1.0f) * 65536.0f;
        this.r_array[1] = (r1 * 253.0f + 1.0f) * 65536.0f;
        this.g_array[0] = (g0 * 253.0f + 1.0f) * 65536.0f;
        this.g_array[1] = (g1 * 253.0f + 1.0f) * 65536.0f;
        this.b_array[0] = (b0 * 253.0f + 1.0f) * 65536.0f;
        this.b_array[1] = (b1 * 253.0f + 1.0f) * 65536.0f;
        if (r0 != r1) {
            this.INTERPOLATE_RGB = true;
            this.m_drawFlags |= 0x1;
        }
        else if (g0 != g1) {
            this.INTERPOLATE_RGB = true;
            this.m_drawFlags |= 0x1;
        }
        else if (b0 != b1) {
            this.INTERPOLATE_RGB = true;
            this.m_drawFlags |= 0x1;
        }
        else {
            this.m_stroke = (0xFF000000 | (int)(255.0f * r0) << 16 | (int)(255.0f * g0) << 8 | (int)(255.0f * b0));
            this.INTERPOLATE_RGB = false;
            this.m_drawFlags &= 0xFFFFFFFE;
        }
    }
    
    public void setIndex(final int index) {
        this.m_index = index;
        if (this.m_index == -1) {
            this.m_index = 0;
        }
    }
    
    public void draw() {
        boolean visible = true;
        if (this.parent.smooth) {
            this.SMOOTH = true;
            this.m_drawFlags |= 0x10;
        }
        else {
            this.SMOOTH = false;
            this.m_drawFlags &= 0xFFFFFFEF;
        }
        visible = this.lineClipping();
        if (!visible) {
            return;
        }
        boolean yLonger = false;
        if (this.x_array[1] < this.x_array[0]) {
            float t = this.x_array[1];
            this.x_array[1] = this.x_array[0];
            this.x_array[0] = t;
            t = this.y_array[1];
            this.y_array[1] = this.y_array[0];
            this.y_array[0] = t;
            t = this.z_array[1];
            this.z_array[1] = this.z_array[0];
            this.z_array[0] = t;
            t = this.r_array[1];
            this.r_array[1] = this.r_array[0];
            this.r_array[0] = t;
            t = this.g_array[1];
            this.g_array[1] = this.g_array[0];
            this.g_array[0] = t;
            t = this.b_array[1];
            this.b_array[1] = this.b_array[0];
            this.b_array[0] = t;
            t = this.a_array[1];
            this.a_array[1] = this.a_array[0];
            this.a_array[0] = t;
        }
        int longLen = (int)this.x_array[1] - (int)this.x_array[0];
        int shortLen = (int)this.y_array[1] - (int)this.y_array[0];
        if (Math.abs(shortLen) > Math.abs(longLen)) {
            final int swap = shortLen;
            shortLen = longLen;
            longLen = swap;
            yLonger = true;
        }
        int xi;
        int yi;
        int length;
        if (longLen < 0) {
            this.o0 = 1;
            this.o1 = 0;
            xi = (int)this.x_array[1];
            yi = (int)this.y_array[1];
            length = -longLen;
        }
        else {
            this.o0 = 0;
            this.o1 = 1;
            xi = (int)this.x_array[0];
            yi = (int)this.y_array[0];
            length = longLen;
        }
        int dt;
        if (length == 0) {
            dt = 0;
        }
        else {
            dt = (shortLen << 16) / longLen;
        }
        this.m_r0 = this.r_array[this.o0];
        this.m_g0 = this.g_array[this.o0];
        this.m_b0 = this.b_array[this.o0];
        if (this.INTERPOLATE_RGB) {
            this.dr = (this.r_array[this.o1] - this.r_array[this.o0]) / length;
            this.dg = (this.g_array[this.o1] - this.g_array[this.o0]) / length;
            this.db = (this.b_array[this.o1] - this.b_array[this.o0]) / length;
        }
        else {
            this.dr = 0.0f;
            this.dg = 0.0f;
            this.db = 0.0f;
        }
        this.m_a0 = this.a_array[this.o0];
        if (this.INTERPOLATE_ALPHA) {
            this.da = (this.a_array[this.o1] - this.a_array[this.o0]) / length;
        }
        else {
            this.da = 0.0f;
        }
        this.m_z0 = this.z_array[this.o0];
        if (this.INTERPOLATE_Z) {
            this.dz = (this.z_array[this.o1] - this.z_array[this.o0]) / length;
        }
        else {
            this.dz = 0.0f;
        }
        if (length == 0) {
            if (this.INTERPOLATE_ALPHA) {
                this.drawPoint_alpha(xi, yi);
            }
            else {
                this.drawPoint(xi, yi);
            }
            return;
        }
        if (this.SMOOTH) {
            this.drawLine_smooth(xi, yi, dt, length, yLonger);
        }
        else if (this.m_drawFlags == 0) {
            this.drawLine_plain(xi, yi, dt, length, yLonger);
        }
        else if (this.m_drawFlags == 2) {
            this.drawLine_plain_alpha(xi, yi, dt, length, yLonger);
        }
        else if (this.m_drawFlags == 1) {
            this.drawLine_color(xi, yi, dt, length, yLonger);
        }
        else if (this.m_drawFlags == 3) {
            this.drawLine_color_alpha(xi, yi, dt, length, yLonger);
        }
        else if (this.m_drawFlags == 8) {
            this.drawLine_plain_spatial(xi, yi, dt, length, yLonger);
        }
        else if (this.m_drawFlags == 10) {
            this.drawLine_plain_alpha_spatial(xi, yi, dt, length, yLonger);
        }
        else if (this.m_drawFlags == 9) {
            this.drawLine_color_spatial(xi, yi, dt, length, yLonger);
        }
        else if (this.m_drawFlags == 11) {
            this.drawLine_color_alpha_spatial(xi, yi, dt, length, yLonger);
        }
    }
    
    public boolean lineClipping() {
        final int code1 = this.lineClipCode(this.x_array[0], this.y_array[0]);
        final int code2 = this.lineClipCode(this.x_array[1], this.y_array[1]);
        final int dip = code1 | code2;
        if ((code1 & code2) != 0x0) {
            return false;
        }
        if (dip != 0) {
            float a0 = 0.0f;
            float a2 = 1.0f;
            float a3 = 0.0f;
            for (int i = 0; i < 4; ++i) {
                if ((dip >> i) % 2 == 1) {
                    a3 = this.lineSlope(this.x_array[0], this.y_array[0], this.x_array[1], this.y_array[1], i + 1);
                    if ((code1 >> i) % 2 == 1) {
                        a0 = ((a3 > a0) ? a3 : a0);
                    }
                    else {
                        a2 = ((a3 < a2) ? a3 : a2);
                    }
                }
            }
            if (a0 > a2) {
                return false;
            }
            final float xt = this.x_array[0];
            final float yt = this.y_array[0];
            this.x_array[0] = xt + a0 * (this.x_array[1] - xt);
            this.y_array[0] = yt + a0 * (this.y_array[1] - yt);
            this.x_array[1] = xt + a2 * (this.x_array[1] - xt);
            this.y_array[1] = yt + a2 * (this.y_array[1] - yt);
            if (this.INTERPOLATE_RGB) {
                float t = this.r_array[0];
                this.r_array[0] = t + a0 * (this.r_array[1] - t);
                this.r_array[1] = t + a2 * (this.r_array[1] - t);
                t = this.g_array[0];
                this.g_array[0] = t + a0 * (this.g_array[1] - t);
                this.g_array[1] = t + a2 * (this.g_array[1] - t);
                t = this.b_array[0];
                this.b_array[0] = t + a0 * (this.b_array[1] - t);
                this.b_array[1] = t + a2 * (this.b_array[1] - t);
            }
            if (this.INTERPOLATE_ALPHA) {
                final float t = this.a_array[0];
                this.a_array[0] = t + a0 * (this.a_array[1] - t);
                this.a_array[1] = t + a2 * (this.a_array[1] - t);
            }
        }
        return true;
    }
    
    private int lineClipCode(final float xi, final float yi) {
        final int xmin = 0;
        final int ymin = 0;
        final int xmax = this.SCREEN_WIDTH1;
        final int ymax = this.SCREEN_HEIGHT1;
        return ((yi < ymin) ? 8 : 0) | (((int)yi > ymax) ? 4 : 0) | ((xi < xmin) ? 2 : 0) | (((int)xi > xmax) ? 1 : 0);
    }
    
    private float lineSlope(final float x1, final float y1, final float x2, final float y2, final int border) {
        final int xmin = 0;
        final int ymin = 0;
        final int xmax = this.SCREEN_WIDTH1;
        final int ymax = this.SCREEN_HEIGHT1;
        switch (border) {
            case 4: {
                return (ymin - y1) / (y2 - y1);
            }
            case 3: {
                return (ymax - y1) / (y2 - y1);
            }
            case 2: {
                return (xmin - x1) / (x2 - x1);
            }
            case 1: {
                return (xmax - x1) / (x2 - x1);
            }
            default: {
                return -1.0f;
            }
        }
    }
    
    private void drawPoint(final int x0, final int y0) {
        final float iz = this.m_z0;
        final int offset = y0 * this.SCREEN_WIDTH + x0;
        if (this.m_zbuffer == null) {
            this.m_pixels[offset] = this.m_stroke;
        }
        else if (iz <= this.m_zbuffer[offset]) {
            this.m_pixels[offset] = this.m_stroke;
            this.m_zbuffer[offset] = iz;
        }
    }
    
    private void drawPoint_alpha(final int x0, final int y0) {
        final int ia = (int)this.a_array[0];
        final int pr = this.m_stroke & 0xFF0000;
        final int pg = this.m_stroke & 0xFF00;
        final int pb = this.m_stroke & 0xFF;
        final float iz = this.m_z0;
        final int offset = y0 * this.SCREEN_WIDTH + x0;
        if (this.m_zbuffer == null || iz <= this.m_zbuffer[offset]) {
            final int alpha = ia >> 16;
            int r0 = this.m_pixels[offset];
            int g0 = r0 & 0xFF00;
            int b0 = r0 & 0xFF;
            r0 &= 0xFF0000;
            r0 += (pr - r0) * alpha >> 8;
            g0 += (pg - g0) * alpha >> 8;
            b0 += (pb - b0) * alpha >> 8;
            this.m_pixels[offset] = (0xFF000000 | (r0 & 0xFF0000) | (g0 & 0xFF00) | (b0 & 0xFF));
            if (this.m_zbuffer != null) {
                this.m_zbuffer[offset] = iz;
            }
        }
    }
    
    private void drawLine_plain(int x0, int y0, final int dt, int length, final boolean vertical) {
        int offset = 0;
        if (vertical) {
            length += y0;
            int j = 32768 + (x0 << 16);
            while (y0 <= length) {
                offset = y0 * this.SCREEN_WIDTH + (j >> 16);
                this.m_pixels[offset] = this.m_stroke;
                if (this.m_zbuffer != null) {
                    this.m_zbuffer[offset] = this.m_z0;
                }
                j += dt;
                ++y0;
            }
        }
        else {
            length += x0;
            int j = 32768 + (y0 << 16);
            while (x0 <= length) {
                offset = (j >> 16) * this.SCREEN_WIDTH + x0;
                this.m_pixels[offset] = this.m_stroke;
                if (this.m_zbuffer != null) {
                    this.m_zbuffer[offset] = this.m_z0;
                }
                j += dt;
                ++x0;
            }
        }
    }
    
    private void drawLine_plain_alpha(int x0, int y0, final int dt, int length, final boolean vertical) {
        int offset = 0;
        final int pr = this.m_stroke & 0xFF0000;
        final int pg = this.m_stroke & 0xFF00;
        final int pb = this.m_stroke & 0xFF;
        int ia = (int)this.m_a0;
        if (vertical) {
            length += y0;
            int j = 32768 + (x0 << 16);
            while (y0 <= length) {
                offset = y0 * this.SCREEN_WIDTH + (j >> 16);
                final int alpha = ia >> 16;
                int r0 = this.m_pixels[offset];
                int g0 = r0 & 0xFF00;
                int b0 = r0 & 0xFF;
                r0 &= 0xFF0000;
                r0 += (pr - r0) * alpha >> 8;
                g0 += (pg - g0) * alpha >> 8;
                b0 += (pb - b0) * alpha >> 8;
                this.m_pixels[offset] = (0xFF000000 | (r0 & 0xFF0000) | (g0 & 0xFF00) | (b0 & 0xFF));
                ia += (int)this.da;
                j += dt;
                ++y0;
            }
        }
        else {
            length += x0;
            int j = 32768 + (y0 << 16);
            while (x0 <= length) {
                offset = (j >> 16) * this.SCREEN_WIDTH + x0;
                final int alpha = ia >> 16;
                int r0 = this.m_pixels[offset];
                int g0 = r0 & 0xFF00;
                int b0 = r0 & 0xFF;
                r0 &= 0xFF0000;
                r0 += (pr - r0) * alpha >> 8;
                g0 += (pg - g0) * alpha >> 8;
                b0 += (pb - b0) * alpha >> 8;
                this.m_pixels[offset] = (0xFF000000 | (r0 & 0xFF0000) | (g0 & 0xFF00) | (b0 & 0xFF));
                ia += (int)this.da;
                j += dt;
                ++x0;
            }
        }
    }
    
    private void drawLine_color(int x0, int y0, final int dt, int length, final boolean vertical) {
        int offset = 0;
        int ir = (int)this.m_r0;
        int ig = (int)this.m_g0;
        int ib = (int)this.m_b0;
        if (vertical) {
            length += y0;
            int j = 32768 + (x0 << 16);
            while (y0 <= length) {
                offset = y0 * this.SCREEN_WIDTH + (j >> 16);
                this.m_pixels[offset] = (0xFF000000 | ((ir & 0xFF0000) | (ig >> 8 & 0xFF00) | ib >> 16));
                if (this.m_zbuffer != null) {
                    this.m_zbuffer[offset] = this.m_z0;
                }
                ir += (int)this.dr;
                ig += (int)this.dg;
                ib += (int)this.db;
                j += dt;
                ++y0;
            }
        }
        else {
            length += x0;
            int j = 32768 + (y0 << 16);
            while (x0 <= length) {
                offset = (j >> 16) * this.SCREEN_WIDTH + x0;
                this.m_pixels[offset] = (0xFF000000 | ((ir & 0xFF0000) | (ig >> 8 & 0xFF00) | ib >> 16));
                if (this.m_zbuffer != null) {
                    this.m_zbuffer[offset] = this.m_z0;
                }
                ir += (int)this.dr;
                ig += (int)this.dg;
                ib += (int)this.db;
                j += dt;
                ++x0;
            }
        }
    }
    
    private void drawLine_color_alpha(int x0, int y0, final int dt, int length, final boolean vertical) {
        int offset = 0;
        int ir = (int)this.m_r0;
        int ig = (int)this.m_g0;
        int ib = (int)this.m_b0;
        int ia = (int)this.m_a0;
        if (vertical) {
            length += y0;
            int j = 32768 + (x0 << 16);
            while (y0 <= length) {
                offset = y0 * this.SCREEN_WIDTH + (j >> 16);
                final int pr = ir & 0xFF0000;
                final int pg = ig >> 8 & 0xFF00;
                final int pb = ib >> 16;
                int r0 = this.m_pixels[offset];
                int g0 = r0 & 0xFF00;
                int b0 = r0 & 0xFF;
                r0 &= 0xFF0000;
                final int alpha = ia >> 16;
                r0 += (pr - r0) * alpha >> 8;
                g0 += (pg - g0) * alpha >> 8;
                b0 += (pb - b0) * alpha >> 8;
                this.m_pixels[offset] = (0xFF000000 | (r0 & 0xFF0000) | (g0 & 0xFF00) | (b0 & 0xFF));
                if (this.m_zbuffer != null) {
                    this.m_zbuffer[offset] = this.m_z0;
                }
                ir += (int)this.dr;
                ig += (int)this.dg;
                ib += (int)this.db;
                ia += (int)this.da;
                j += dt;
                ++y0;
            }
        }
        else {
            length += x0;
            int j = 32768 + (y0 << 16);
            while (x0 <= length) {
                offset = (j >> 16) * this.SCREEN_WIDTH + x0;
                final int pr = ir & 0xFF0000;
                final int pg = ig >> 8 & 0xFF00;
                final int pb = ib >> 16;
                int r0 = this.m_pixels[offset];
                int g0 = r0 & 0xFF00;
                int b0 = r0 & 0xFF;
                r0 &= 0xFF0000;
                final int alpha = ia >> 16;
                r0 += (pr - r0) * alpha >> 8;
                g0 += (pg - g0) * alpha >> 8;
                b0 += (pb - b0) * alpha >> 8;
                this.m_pixels[offset] = (0xFF000000 | (r0 & 0xFF0000) | (g0 & 0xFF00) | (b0 & 0xFF));
                if (this.m_zbuffer != null) {
                    this.m_zbuffer[offset] = this.m_z0;
                }
                ir += (int)this.dr;
                ig += (int)this.dg;
                ib += (int)this.db;
                ia += (int)this.da;
                j += dt;
                ++x0;
            }
        }
    }
    
    private void drawLine_plain_spatial(int x0, int y0, final int dt, int length, final boolean vertical) {
        int offset = 0;
        float iz = this.m_z0;
        if (vertical) {
            length += y0;
            int j = 32768 + (x0 << 16);
            while (y0 <= length) {
                offset = y0 * this.SCREEN_WIDTH + (j >> 16);
                if (offset < this.m_pixels.length && iz <= this.m_zbuffer[offset]) {
                    this.m_pixels[offset] = this.m_stroke;
                    this.m_zbuffer[offset] = iz;
                }
                iz += this.dz;
                j += dt;
                ++y0;
            }
        }
        else {
            length += x0;
            int j = 32768 + (y0 << 16);
            while (x0 <= length) {
                offset = (j >> 16) * this.SCREEN_WIDTH + x0;
                if (offset < this.m_pixels.length && iz <= this.m_zbuffer[offset]) {
                    this.m_pixels[offset] = this.m_stroke;
                    this.m_zbuffer[offset] = iz;
                }
                iz += this.dz;
                j += dt;
                ++x0;
            }
        }
    }
    
    private void drawLine_plain_alpha_spatial(int x0, int y0, final int dt, int length, final boolean vertical) {
        int offset = 0;
        float iz = this.m_z0;
        final int pr = this.m_stroke & 0xFF0000;
        final int pg = this.m_stroke & 0xFF00;
        final int pb = this.m_stroke & 0xFF;
        int ia = (int)this.m_a0;
        if (vertical) {
            length += y0;
            int j = 32768 + (x0 << 16);
            while (y0 <= length) {
                offset = y0 * this.SCREEN_WIDTH + (j >> 16);
                if (offset < this.m_pixels.length && iz <= this.m_zbuffer[offset]) {
                    final int alpha = ia >> 16;
                    int r0 = this.m_pixels[offset];
                    int g0 = r0 & 0xFF00;
                    int b0 = r0 & 0xFF;
                    r0 &= 0xFF0000;
                    r0 += (pr - r0) * alpha >> 8;
                    g0 += (pg - g0) * alpha >> 8;
                    b0 += (pb - b0) * alpha >> 8;
                    this.m_pixels[offset] = (0xFF000000 | (r0 & 0xFF0000) | (g0 & 0xFF00) | (b0 & 0xFF));
                    this.m_zbuffer[offset] = iz;
                }
                iz += this.dz;
                ia += (int)this.da;
                j += dt;
                ++y0;
            }
        }
        else {
            length += x0;
            int j = 32768 + (y0 << 16);
            while (x0 <= length) {
                offset = (j >> 16) * this.SCREEN_WIDTH + x0;
                if (offset < this.m_pixels.length && iz <= this.m_zbuffer[offset]) {
                    final int alpha = ia >> 16;
                    int r0 = this.m_pixels[offset];
                    int g0 = r0 & 0xFF00;
                    int b0 = r0 & 0xFF;
                    r0 &= 0xFF0000;
                    r0 += (pr - r0) * alpha >> 8;
                    g0 += (pg - g0) * alpha >> 8;
                    b0 += (pb - b0) * alpha >> 8;
                    this.m_pixels[offset] = (0xFF000000 | (r0 & 0xFF0000) | (g0 & 0xFF00) | (b0 & 0xFF));
                    this.m_zbuffer[offset] = iz;
                }
                iz += this.dz;
                ia += (int)this.da;
                j += dt;
                ++x0;
            }
        }
    }
    
    private void drawLine_color_spatial(int x0, int y0, final int dt, int length, final boolean vertical) {
        int offset = 0;
        float iz = this.m_z0;
        int ir = (int)this.m_r0;
        int ig = (int)this.m_g0;
        int ib = (int)this.m_b0;
        if (vertical) {
            length += y0;
            int j = 32768 + (x0 << 16);
            while (y0 <= length) {
                offset = y0 * this.SCREEN_WIDTH + (j >> 16);
                if (iz <= this.m_zbuffer[offset]) {
                    this.m_pixels[offset] = (0xFF000000 | ((ir & 0xFF0000) | (ig >> 8 & 0xFF00) | ib >> 16));
                    this.m_zbuffer[offset] = iz;
                }
                iz += this.dz;
                ir += (int)this.dr;
                ig += (int)this.dg;
                ib += (int)this.db;
                j += dt;
                ++y0;
            }
            return;
        }
        length += x0;
        int j = 32768 + (y0 << 16);
        while (x0 <= length) {
            offset = (j >> 16) * this.SCREEN_WIDTH + x0;
            if (iz <= this.m_zbuffer[offset]) {
                this.m_pixels[offset] = (0xFF000000 | ((ir & 0xFF0000) | (ig >> 8 & 0xFF00) | ib >> 16));
                this.m_zbuffer[offset] = iz;
            }
            iz += this.dz;
            ir += (int)this.dr;
            ig += (int)this.dg;
            ib += (int)this.db;
            j += dt;
            ++x0;
        }
    }
    
    private void drawLine_color_alpha_spatial(int x0, int y0, final int dt, int length, final boolean vertical) {
        int offset = 0;
        float iz = this.m_z0;
        int ir = (int)this.m_r0;
        int ig = (int)this.m_g0;
        int ib = (int)this.m_b0;
        int ia = (int)this.m_a0;
        if (vertical) {
            length += y0;
            int j = 32768 + (x0 << 16);
            while (y0 <= length) {
                offset = y0 * this.SCREEN_WIDTH + (j >> 16);
                if (iz <= this.m_zbuffer[offset]) {
                    final int pr = ir & 0xFF0000;
                    final int pg = ig >> 8 & 0xFF00;
                    final int pb = ib >> 16;
                    int r0 = this.m_pixels[offset];
                    int g0 = r0 & 0xFF00;
                    int b0 = r0 & 0xFF;
                    r0 &= 0xFF0000;
                    final int alpha = ia >> 16;
                    r0 += (pr - r0) * alpha >> 8;
                    g0 += (pg - g0) * alpha >> 8;
                    b0 += (pb - b0) * alpha >> 8;
                    this.m_pixels[offset] = (0xFF000000 | (r0 & 0xFF0000) | (g0 & 0xFF00) | (b0 & 0xFF));
                    this.m_zbuffer[offset] = iz;
                }
                iz += this.dz;
                ir += (int)this.dr;
                ig += (int)this.dg;
                ib += (int)this.db;
                ia += (int)this.da;
                j += dt;
                ++y0;
            }
        }
        else {
            length += x0;
            int j = 32768 + (y0 << 16);
            while (x0 <= length) {
                offset = (j >> 16) * this.SCREEN_WIDTH + x0;
                if (iz <= this.m_zbuffer[offset]) {
                    final int pr = ir & 0xFF0000;
                    final int pg = ig >> 8 & 0xFF00;
                    final int pb = ib >> 16;
                    int r0 = this.m_pixels[offset];
                    int g0 = r0 & 0xFF00;
                    int b0 = r0 & 0xFF;
                    r0 &= 0xFF0000;
                    final int alpha = ia >> 16;
                    r0 += (pr - r0) * alpha >> 8;
                    g0 += (pg - g0) * alpha >> 8;
                    b0 += (pb - b0) * alpha >> 8;
                    this.m_pixels[offset] = (0xFF000000 | (r0 & 0xFF0000) | (g0 & 0xFF00) | (b0 & 0xFF));
                    this.m_zbuffer[offset] = iz;
                }
                iz += this.dz;
                ir += (int)this.dr;
                ig += (int)this.dg;
                ib += (int)this.db;
                ia += (int)this.da;
                j += dt;
                ++x0;
            }
        }
    }
    
    private void drawLine_smooth(final int x0, final int y0, final int dt, final int length, final boolean vertical) {
        int offset = 0;
        float iz = this.m_z0;
        int ir = (int)this.m_r0;
        int ig = (int)this.m_g0;
        int ib = (int)this.m_b0;
        int ia = (int)this.m_a0;
        if (vertical) {
            int xi = x0 << 16;
            int yi = y0 << 16;
            final int end = length + y0;
            while (yi >> 16 < end) {
                offset = (yi >> 16) * this.SCREEN_WIDTH + (xi >> 16);
                final int pr = ir & 0xFF0000;
                final int pg = ig >> 8 & 0xFF00;
                final int pb = ib >> 16;
                if (this.m_zbuffer == null || iz <= this.m_zbuffer[offset]) {
                    final int alpha = (~xi >> 8 & 0xFF) * (ia >> 16) >> 8;
                    int r0 = this.m_pixels[offset];
                    int g0 = r0 & 0xFF00;
                    int b0 = r0 & 0xFF;
                    r0 &= 0xFF0000;
                    r0 += (pr - r0) * alpha >> 8;
                    g0 += (pg - g0) * alpha >> 8;
                    b0 += (pb - b0) * alpha >> 8;
                    this.m_pixels[offset] = (0xFF000000 | (r0 & 0xFF0000) | (g0 & 0xFF00) | (b0 & 0xFF));
                    if (this.m_zbuffer != null) {
                        this.m_zbuffer[offset] = iz;
                    }
                }
                final int temp = (xi >> 16) + 1;
                if (temp >= this.SCREEN_WIDTH) {
                    xi += dt;
                    yi += 65536;
                }
                else {
                    offset = (yi >> 16) * this.SCREEN_WIDTH + temp;
                    if (this.m_zbuffer == null || iz <= this.m_zbuffer[offset]) {
                        final int alpha = (xi >> 8 & 0xFF) * (ia >> 16) >> 8;
                        int r0 = this.m_pixels[offset];
                        int g0 = r0 & 0xFF00;
                        int b0 = r0 & 0xFF;
                        r0 &= 0xFF0000;
                        r0 += (pr - r0) * alpha >> 8;
                        g0 += (pg - g0) * alpha >> 8;
                        b0 += (pb - b0) * alpha >> 8;
                        this.m_pixels[offset] = (0xFF000000 | (r0 & 0xFF0000) | (g0 & 0xFF00) | (b0 & 0xFF));
                        if (this.m_zbuffer != null) {
                            this.m_zbuffer[offset] = iz;
                        }
                    }
                    xi += dt;
                    yi += 65536;
                    iz += this.dz;
                    ir += (int)this.dr;
                    ig += (int)this.dg;
                    ib += (int)this.db;
                    ia += (int)this.da;
                }
            }
        }
        else {
            int xi = x0 << 16;
            int yi = y0 << 16;
            final int end = length + x0;
            while (xi >> 16 < end) {
                offset = (yi >> 16) * this.SCREEN_WIDTH + (xi >> 16);
                final int pr = ir & 0xFF0000;
                final int pg = ig >> 8 & 0xFF00;
                final int pb = ib >> 16;
                if (this.m_zbuffer == null || iz <= this.m_zbuffer[offset]) {
                    final int alpha = (~yi >> 8 & 0xFF) * (ia >> 16) >> 8;
                    int r0 = this.m_pixels[offset];
                    int g0 = r0 & 0xFF00;
                    int b0 = r0 & 0xFF;
                    r0 &= 0xFF0000;
                    r0 += (pr - r0) * alpha >> 8;
                    g0 += (pg - g0) * alpha >> 8;
                    b0 += (pb - b0) * alpha >> 8;
                    this.m_pixels[offset] = (0xFF000000 | (r0 & 0xFF0000) | (g0 & 0xFF00) | (b0 & 0xFF));
                    if (this.m_zbuffer != null) {
                        this.m_zbuffer[offset] = iz;
                    }
                }
                final int temp = (yi >> 16) + 1;
                if (temp >= this.SCREEN_HEIGHT) {
                    xi += 65536;
                    yi += dt;
                }
                else {
                    offset = temp * this.SCREEN_WIDTH + (xi >> 16);
                    if (this.m_zbuffer == null || iz <= this.m_zbuffer[offset]) {
                        final int alpha = (yi >> 8 & 0xFF) * (ia >> 16) >> 8;
                        int r0 = this.m_pixels[offset];
                        int g0 = r0 & 0xFF00;
                        int b0 = r0 & 0xFF;
                        r0 &= 0xFF0000;
                        r0 += (pr - r0) * alpha >> 8;
                        g0 += (pg - g0) * alpha >> 8;
                        b0 += (pb - b0) * alpha >> 8;
                        this.m_pixels[offset] = (0xFF000000 | (r0 & 0xFF0000) | (g0 & 0xFF00) | (b0 & 0xFF));
                        if (this.m_zbuffer != null) {
                            this.m_zbuffer[offset] = iz;
                        }
                    }
                    xi += 65536;
                    yi += dt;
                    iz += this.dz;
                    ir += (int)this.dr;
                    ig += (int)this.dg;
                    ib += (int)this.db;
                    ia += (int)this.da;
                }
            }
        }
    }
}
