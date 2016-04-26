// 
// Decompiled by Procyon v0.5.30
// 

package processing.core;

public class PTriangle implements PConstants
{
    static final float PIXEL_CENTER = 0.5f;
    static final int R_GOURAUD = 1;
    static final int R_TEXTURE8 = 2;
    static final int R_TEXTURE24 = 4;
    static final int R_TEXTURE32 = 8;
    static final int R_ALPHA = 16;
    private int[] m_pixels;
    private int[] m_texture;
    private float[] m_zbuffer;
    private int SCREEN_WIDTH;
    private int SCREEN_HEIGHT;
    private int TEX_WIDTH;
    private int TEX_HEIGHT;
    private float F_TEX_WIDTH;
    private float F_TEX_HEIGHT;
    public boolean INTERPOLATE_UV;
    public boolean INTERPOLATE_RGB;
    public boolean INTERPOLATE_ALPHA;
    private static final int DEFAULT_INTERP_POWER = 3;
    private static int TEX_INTERP_POWER;
    private float[] x_array;
    private float[] y_array;
    private float[] z_array;
    private float[] camX;
    private float[] camY;
    private float[] camZ;
    private float[] u_array;
    private float[] v_array;
    private float[] r_array;
    private float[] g_array;
    private float[] b_array;
    private float[] a_array;
    private int o0;
    private int o1;
    private int o2;
    private float r0;
    private float r1;
    private float r2;
    private float g0;
    private float g1;
    private float g2;
    private float b0;
    private float b1;
    private float b2;
    private float a0;
    private float a1;
    private float a2;
    private float u0;
    private float u1;
    private float u2;
    private float v0;
    private float v1;
    private float v2;
    private float dx2;
    private float dy0;
    private float dy1;
    private float dy2;
    private float dz0;
    private float dz2;
    private float du0;
    private float du2;
    private float dv0;
    private float dv2;
    private float dr0;
    private float dr2;
    private float dg0;
    private float dg2;
    private float db0;
    private float db2;
    private float da0;
    private float da2;
    private float uleft;
    private float vleft;
    private float uleftadd;
    private float vleftadd;
    private float xleft;
    private float xrght;
    private float xadd1;
    private float xadd2;
    private float zleft;
    private float zleftadd;
    private float rleft;
    private float gleft;
    private float bleft;
    private float aleft;
    private float rleftadd;
    private float gleftadd;
    private float bleftadd;
    private float aleftadd;
    private float dta;
    private float temp;
    private float width;
    private int iuadd;
    private int ivadd;
    private int iradd;
    private int igadd;
    private int ibadd;
    private int iaadd;
    private float izadd;
    private int m_fill;
    public int m_drawFlags;
    private PGraphics3D parent;
    private boolean noDepthTest;
    private boolean m_culling;
    private boolean m_singleRight;
    private boolean m_bilinear;
    private float ax;
    private float ay;
    private float az;
    private float bx;
    private float by;
    private float bz;
    private float cx;
    private float cy;
    private float cz;
    private float nearPlaneWidth;
    private float nearPlaneHeight;
    private float nearPlaneDepth;
    private float xmult;
    private float ymult;
    private float newax;
    private float newbx;
    private float newcx;
    private boolean firstSegment;
    
    static {
        PTriangle.TEX_INTERP_POWER = 3;
    }
    
    public PTriangle(final PGraphics3D g) {
        this.m_bilinear = true;
        this.x_array = new float[3];
        this.y_array = new float[3];
        this.z_array = new float[3];
        this.u_array = new float[3];
        this.v_array = new float[3];
        this.r_array = new float[3];
        this.g_array = new float[3];
        this.b_array = new float[3];
        this.a_array = new float[3];
        this.camX = new float[3];
        this.camY = new float[3];
        this.camZ = new float[3];
        this.parent = g;
        this.reset();
    }
    
    public void reset() {
        this.SCREEN_WIDTH = this.parent.width;
        this.SCREEN_HEIGHT = this.parent.height;
        this.m_pixels = this.parent.pixels;
        this.m_zbuffer = this.parent.zbuffer;
        this.noDepthTest = this.parent.hints[4];
        this.INTERPOLATE_UV = false;
        this.INTERPOLATE_RGB = false;
        this.INTERPOLATE_ALPHA = false;
        this.m_texture = null;
        this.m_drawFlags = 0;
    }
    
    public void setCulling(final boolean tf) {
        this.m_culling = tf;
    }
    
    public void setVertices(final float x0, final float y0, final float z0, final float x1, final float y1, final float z1, final float x2, final float y2, final float z2) {
        this.x_array[0] = x0;
        this.x_array[1] = x1;
        this.x_array[2] = x2;
        this.y_array[0] = y0;
        this.y_array[1] = y1;
        this.y_array[2] = y2;
        this.z_array[0] = z0;
        this.z_array[1] = z1;
        this.z_array[2] = z2;
    }
    
    public void setCamVertices(final float x0, final float y0, final float z0, final float x1, final float y1, final float z1, final float x2, final float y2, final float z2) {
        this.camX[0] = x0;
        this.camX[1] = x1;
        this.camX[2] = x2;
        this.camY[0] = y0;
        this.camY[1] = y1;
        this.camY[2] = y2;
        this.camZ[0] = z0;
        this.camZ[1] = z1;
        this.camZ[2] = z2;
    }
    
    public void setUV(final float u0, final float v0, final float u1, final float v1, final float u2, final float v2) {
        this.u_array[0] = (u0 * this.F_TEX_WIDTH + 0.5f) * 65536.0f;
        this.u_array[1] = (u1 * this.F_TEX_WIDTH + 0.5f) * 65536.0f;
        this.u_array[2] = (u2 * this.F_TEX_WIDTH + 0.5f) * 65536.0f;
        this.v_array[0] = (v0 * this.F_TEX_HEIGHT + 0.5f) * 65536.0f;
        this.v_array[1] = (v1 * this.F_TEX_HEIGHT + 0.5f) * 65536.0f;
        this.v_array[2] = (v2 * this.F_TEX_HEIGHT + 0.5f) * 65536.0f;
    }
    
    public void setIntensities(final float r0, final float g0, final float b0, final float a0, final float r1, final float g1, final float b1, final float a1, final float r2, final float g2, final float b2, final float a2) {
        if (a0 != 1.0f || a1 != 1.0f || a2 != 1.0f) {
            this.INTERPOLATE_ALPHA = true;
            this.a_array[0] = (a0 * 253.0f + 1.0f) * 65536.0f;
            this.a_array[1] = (a1 * 253.0f + 1.0f) * 65536.0f;
            this.a_array[2] = (a2 * 253.0f + 1.0f) * 65536.0f;
            this.m_drawFlags |= 0x10;
        }
        else {
            this.INTERPOLATE_ALPHA = false;
            this.m_drawFlags &= 0xFFFFFFEF;
        }
        if (r0 != r1 || r1 != r2) {
            this.INTERPOLATE_RGB = true;
            this.m_drawFlags |= 0x1;
        }
        else if (g0 != g1 || g1 != g2) {
            this.INTERPOLATE_RGB = true;
            this.m_drawFlags |= 0x1;
        }
        else if (b0 != b1 || b1 != b2) {
            this.INTERPOLATE_RGB = true;
            this.m_drawFlags |= 0x1;
        }
        else {
            this.m_drawFlags &= 0xFFFFFFFE;
        }
        this.r_array[0] = (r0 * 253.0f + 1.0f) * 65536.0f;
        this.r_array[1] = (r1 * 253.0f + 1.0f) * 65536.0f;
        this.r_array[2] = (r2 * 253.0f + 1.0f) * 65536.0f;
        this.g_array[0] = (g0 * 253.0f + 1.0f) * 65536.0f;
        this.g_array[1] = (g1 * 253.0f + 1.0f) * 65536.0f;
        this.g_array[2] = (g2 * 253.0f + 1.0f) * 65536.0f;
        this.b_array[0] = (b0 * 253.0f + 1.0f) * 65536.0f;
        this.b_array[1] = (b1 * 253.0f + 1.0f) * 65536.0f;
        this.b_array[2] = (b2 * 253.0f + 1.0f) * 65536.0f;
        this.m_fill = (0xFF000000 | (int)(255.0f * r0) << 16 | (int)(255.0f * g0) << 8 | (int)(255.0f * b0));
    }
    
    public void setTexture(final PImage image) {
        this.m_texture = image.pixels;
        this.TEX_WIDTH = image.width;
        this.TEX_HEIGHT = image.height;
        this.F_TEX_WIDTH = this.TEX_WIDTH - 1;
        this.F_TEX_HEIGHT = this.TEX_HEIGHT - 1;
        this.INTERPOLATE_UV = true;
        if (image.format == 2) {
            this.m_drawFlags |= 0x8;
        }
        else if (image.format == 1) {
            this.m_drawFlags |= 0x4;
        }
        else if (image.format == 4) {
            this.m_drawFlags |= 0x2;
        }
    }
    
    public void setUV(final float[] u, final float[] v) {
        if (this.m_bilinear) {
            this.u_array[0] = u[0] * this.F_TEX_WIDTH * 65500.0f;
            this.u_array[1] = u[1] * this.F_TEX_WIDTH * 65500.0f;
            this.u_array[2] = u[2] * this.F_TEX_WIDTH * 65500.0f;
            this.v_array[0] = v[0] * this.F_TEX_HEIGHT * 65500.0f;
            this.v_array[1] = v[1] * this.F_TEX_HEIGHT * 65500.0f;
            this.v_array[2] = v[2] * this.F_TEX_HEIGHT * 65500.0f;
        }
        else {
            this.u_array[0] = u[0] * this.TEX_WIDTH * 65500.0f;
            this.u_array[1] = u[1] * this.TEX_WIDTH * 65500.0f;
            this.u_array[2] = u[2] * this.TEX_WIDTH * 65500.0f;
            this.v_array[0] = v[0] * this.TEX_HEIGHT * 65500.0f;
            this.v_array[1] = v[1] * this.TEX_HEIGHT * 65500.0f;
            this.v_array[2] = v[2] * this.TEX_HEIGHT * 65500.0f;
        }
    }
    
    public void render() {
        float y0 = this.y_array[0];
        float y2 = this.y_array[1];
        float y3 = this.y_array[2];
        this.firstSegment = true;
        if (this.m_culling) {
            final float x0 = this.x_array[0];
            if ((this.x_array[2] - x0) * (y2 - y0) < (this.x_array[1] - x0) * (y3 - y0)) {
                return;
            }
        }
        if (y0 < y2) {
            if (y3 < y2) {
                if (y3 < y0) {
                    this.o0 = 2;
                    this.o1 = 0;
                    this.o2 = 1;
                }
                else {
                    this.o0 = 0;
                    this.o1 = 2;
                    this.o2 = 1;
                }
            }
            else {
                this.o0 = 0;
                this.o1 = 1;
                this.o2 = 2;
            }
        }
        else if (y3 > y2) {
            if (y3 < y0) {
                this.o0 = 1;
                this.o1 = 2;
                this.o2 = 0;
            }
            else {
                this.o0 = 1;
                this.o1 = 0;
                this.o2 = 2;
            }
        }
        else {
            this.o0 = 2;
            this.o1 = 1;
            this.o2 = 0;
        }
        y0 = this.y_array[this.o0];
        int yi0 = (int)(y0 + 0.5f);
        if (yi0 > this.SCREEN_HEIGHT) {
            return;
        }
        if (yi0 < 0) {
            yi0 = 0;
        }
        y3 = this.y_array[this.o2];
        int yi2 = (int)(y3 + 0.5f);
        if (yi2 < 0) {
            return;
        }
        if (yi2 > this.SCREEN_HEIGHT) {
            yi2 = this.SCREEN_HEIGHT;
        }
        if (yi2 > yi0) {
            final float x0 = this.x_array[this.o0];
            final float x2 = this.x_array[this.o1];
            final float x3 = this.x_array[this.o2];
            y2 = this.y_array[this.o1];
            int yi3 = (int)(y2 + 0.5f);
            if (yi3 < 0) {
                yi3 = 0;
            }
            if (yi3 > this.SCREEN_HEIGHT) {
                yi3 = this.SCREEN_HEIGHT;
            }
            this.dx2 = x3 - x0;
            this.dy0 = y2 - y0;
            this.dy2 = y3 - y0;
            this.xadd2 = this.dx2 / this.dy2;
            this.temp = this.dy0 / this.dy2;
            this.width = this.temp * this.dx2 + x0 - x2;
            if (this.INTERPOLATE_ALPHA) {
                this.a0 = this.a_array[this.o0];
                this.a1 = this.a_array[this.o1];
                this.a2 = this.a_array[this.o2];
                this.da0 = this.a1 - this.a0;
                this.da2 = this.a2 - this.a0;
                this.iaadd = (int)((this.temp * this.da2 - this.da0) / this.width);
            }
            if (this.INTERPOLATE_RGB) {
                this.r0 = this.r_array[this.o0];
                this.r1 = this.r_array[this.o1];
                this.r2 = this.r_array[this.o2];
                this.g0 = this.g_array[this.o0];
                this.g1 = this.g_array[this.o1];
                this.g2 = this.g_array[this.o2];
                this.b0 = this.b_array[this.o0];
                this.b1 = this.b_array[this.o1];
                this.b2 = this.b_array[this.o2];
                this.dr0 = this.r1 - this.r0;
                this.dg0 = this.g1 - this.g0;
                this.db0 = this.b1 - this.b0;
                this.dr2 = this.r2 - this.r0;
                this.dg2 = this.g2 - this.g0;
                this.db2 = this.b2 - this.b0;
                this.iradd = (int)((this.temp * this.dr2 - this.dr0) / this.width);
                this.igadd = (int)((this.temp * this.dg2 - this.dg0) / this.width);
                this.ibadd = (int)((this.temp * this.db2 - this.db0) / this.width);
            }
            if (this.INTERPOLATE_UV) {
                this.u0 = this.u_array[this.o0];
                this.u1 = this.u_array[this.o1];
                this.u2 = this.u_array[this.o2];
                this.v0 = this.v_array[this.o0];
                this.v1 = this.v_array[this.o1];
                this.v2 = this.v_array[this.o2];
                this.du0 = this.u1 - this.u0;
                this.dv0 = this.v1 - this.v0;
                this.du2 = this.u2 - this.u0;
                this.dv2 = this.v2 - this.v0;
                this.iuadd = (int)((this.temp * this.du2 - this.du0) / this.width);
                this.ivadd = (int)((this.temp * this.dv2 - this.dv0) / this.width);
            }
            final float z0 = this.z_array[this.o0];
            final float z2 = this.z_array[this.o1];
            final float z3 = this.z_array[this.o2];
            this.dz0 = z2 - z0;
            this.dz2 = z3 - z0;
            this.izadd = (this.temp * this.dz2 - this.dz0) / this.width;
            if (yi3 > yi0) {
                this.dta = yi0 + 0.5f - y0;
                this.xadd1 = (x2 - x0) / this.dy0;
                if (this.xadd2 > this.xadd1) {
                    this.xleft = x0 + this.dta * this.xadd1;
                    this.xrght = x0 + this.dta * this.xadd2;
                    this.zleftadd = this.dz0 / this.dy0;
                    this.zleft = this.dta * this.zleftadd + z0;
                    if (this.INTERPOLATE_UV) {
                        this.uleftadd = this.du0 / this.dy0;
                        this.vleftadd = this.dv0 / this.dy0;
                        this.uleft = this.dta * this.uleftadd + this.u0;
                        this.vleft = this.dta * this.vleftadd + this.v0;
                    }
                    if (this.INTERPOLATE_RGB) {
                        this.rleftadd = this.dr0 / this.dy0;
                        this.gleftadd = this.dg0 / this.dy0;
                        this.bleftadd = this.db0 / this.dy0;
                        this.rleft = this.dta * this.rleftadd + this.r0;
                        this.gleft = this.dta * this.gleftadd + this.g0;
                        this.bleft = this.dta * this.bleftadd + this.b0;
                    }
                    if (this.INTERPOLATE_ALPHA) {
                        this.aleftadd = this.da0 / this.dy0;
                        this.aleft = this.dta * this.aleftadd + this.a0;
                        if (this.m_drawFlags == 16) {
                            this.drawsegment_plain_alpha(this.xadd1, this.xadd2, yi0, yi3);
                        }
                        else if (this.m_drawFlags == 17) {
                            this.drawsegment_gouraud_alpha(this.xadd1, this.xadd2, yi0, yi3);
                        }
                        else if (this.m_drawFlags == 18) {
                            this.drawsegment_texture8_alpha(this.xadd1, this.xadd2, yi0, yi3);
                        }
                        else if (this.m_drawFlags == 20) {
                            this.drawsegment_texture24_alpha(this.xadd1, this.xadd2, yi0, yi3);
                        }
                        else if (this.m_drawFlags == 24) {
                            this.drawsegment_texture32_alpha(this.xadd1, this.xadd2, yi0, yi3);
                        }
                        else if (this.m_drawFlags == 19) {
                            this.drawsegment_gouraud_texture8_alpha(this.xadd1, this.xadd2, yi0, yi3);
                        }
                        else if (this.m_drawFlags == 21) {
                            this.drawsegment_gouraud_texture24_alpha(this.xadd1, this.xadd2, yi0, yi3);
                        }
                        else if (this.m_drawFlags == 25) {
                            this.drawsegment_gouraud_texture32_alpha(this.xadd1, this.xadd2, yi0, yi3);
                        }
                    }
                    else if (this.m_drawFlags == 0) {
                        this.drawsegment_plain(this.xadd1, this.xadd2, yi0, yi3);
                    }
                    else if (this.m_drawFlags == 1) {
                        this.drawsegment_gouraud(this.xadd1, this.xadd2, yi0, yi3);
                    }
                    else if (this.m_drawFlags == 2) {
                        this.drawsegment_texture8(this.xadd1, this.xadd2, yi0, yi3);
                    }
                    else if (this.m_drawFlags == 4) {
                        this.drawsegment_texture24(this.xadd1, this.xadd2, yi0, yi3);
                    }
                    else if (this.m_drawFlags == 8) {
                        this.drawsegment_texture32(this.xadd1, this.xadd2, yi0, yi3);
                    }
                    else if (this.m_drawFlags == 3) {
                        this.drawsegment_gouraud_texture8(this.xadd1, this.xadd2, yi0, yi3);
                    }
                    else if (this.m_drawFlags == 5) {
                        this.drawsegment_gouraud_texture24(this.xadd1, this.xadd2, yi0, yi3);
                    }
                    else if (this.m_drawFlags == 9) {
                        this.drawsegment_gouraud_texture32(this.xadd1, this.xadd2, yi0, yi3);
                    }
                    this.m_singleRight = true;
                }
                else {
                    this.xleft = x0 + this.dta * this.xadd2;
                    this.xrght = x0 + this.dta * this.xadd1;
                    this.zleftadd = this.dz2 / this.dy2;
                    this.zleft = this.dta * this.zleftadd + z0;
                    if (this.INTERPOLATE_UV) {
                        this.uleftadd = this.du2 / this.dy2;
                        this.vleftadd = this.dv2 / this.dy2;
                        this.uleft = this.dta * this.uleftadd + this.u0;
                        this.vleft = this.dta * this.vleftadd + this.v0;
                    }
                    if (this.INTERPOLATE_RGB) {
                        this.rleftadd = this.dr2 / this.dy2;
                        this.gleftadd = this.dg2 / this.dy2;
                        this.bleftadd = this.db2 / this.dy2;
                        this.rleft = this.dta * this.rleftadd + this.r0;
                        this.gleft = this.dta * this.gleftadd + this.g0;
                        this.bleft = this.dta * this.bleftadd + this.b0;
                    }
                    if (this.INTERPOLATE_ALPHA) {
                        this.aleftadd = this.da2 / this.dy2;
                        this.aleft = this.dta * this.aleftadd + this.a0;
                        if (this.m_drawFlags == 16) {
                            this.drawsegment_plain_alpha(this.xadd2, this.xadd1, yi0, yi3);
                        }
                        else if (this.m_drawFlags == 17) {
                            this.drawsegment_gouraud_alpha(this.xadd2, this.xadd1, yi0, yi3);
                        }
                        else if (this.m_drawFlags == 18) {
                            this.drawsegment_texture8_alpha(this.xadd2, this.xadd1, yi0, yi3);
                        }
                        else if (this.m_drawFlags == 20) {
                            this.drawsegment_texture24_alpha(this.xadd2, this.xadd1, yi0, yi3);
                        }
                        else if (this.m_drawFlags == 24) {
                            this.drawsegment_texture32_alpha(this.xadd2, this.xadd1, yi0, yi3);
                        }
                        else if (this.m_drawFlags == 19) {
                            this.drawsegment_gouraud_texture8_alpha(this.xadd2, this.xadd1, yi0, yi3);
                        }
                        else if (this.m_drawFlags == 21) {
                            this.drawsegment_gouraud_texture24_alpha(this.xadd2, this.xadd1, yi0, yi3);
                        }
                        else if (this.m_drawFlags == 25) {
                            this.drawsegment_gouraud_texture32_alpha(this.xadd2, this.xadd1, yi0, yi3);
                        }
                    }
                    else if (this.m_drawFlags == 0) {
                        this.drawsegment_plain(this.xadd2, this.xadd1, yi0, yi3);
                    }
                    else if (this.m_drawFlags == 1) {
                        this.drawsegment_gouraud(this.xadd2, this.xadd1, yi0, yi3);
                    }
                    else if (this.m_drawFlags == 2) {
                        this.drawsegment_texture8(this.xadd2, this.xadd1, yi0, yi3);
                    }
                    else if (this.m_drawFlags == 4) {
                        this.drawsegment_texture24(this.xadd2, this.xadd1, yi0, yi3);
                    }
                    else if (this.m_drawFlags == 8) {
                        this.drawsegment_texture32(this.xadd2, this.xadd1, yi0, yi3);
                    }
                    else if (this.m_drawFlags == 3) {
                        this.drawsegment_gouraud_texture8(this.xadd2, this.xadd1, yi0, yi3);
                    }
                    else if (this.m_drawFlags == 5) {
                        this.drawsegment_gouraud_texture24(this.xadd2, this.xadd1, yi0, yi3);
                    }
                    else if (this.m_drawFlags == 9) {
                        this.drawsegment_gouraud_texture32(this.xadd2, this.xadd1, yi0, yi3);
                    }
                    this.m_singleRight = false;
                }
                if (yi2 == yi3) {
                    return;
                }
                this.dy1 = y3 - y2;
                this.xadd1 = (x3 - x2) / this.dy1;
            }
            else {
                this.dy1 = y3 - y2;
                this.xadd1 = (x3 - x2) / this.dy1;
                if (this.xadd2 < this.xadd1) {
                    this.xrght = (yi3 + 0.5f - y0) * this.xadd2 + x0;
                    this.m_singleRight = true;
                }
                else {
                    this.dta = yi3 + 0.5f - y0;
                    this.xleft = this.dta * this.xadd2 + x0;
                    this.zleftadd = this.dz2 / this.dy2;
                    this.zleft = this.dta * this.zleftadd + z0;
                    if (this.INTERPOLATE_UV) {
                        this.uleftadd = this.du2 / this.dy2;
                        this.vleftadd = this.dv2 / this.dy2;
                        this.uleft = this.dta * this.uleftadd + this.u0;
                        this.vleft = this.dta * this.vleftadd + this.v0;
                    }
                    if (this.INTERPOLATE_RGB) {
                        this.rleftadd = this.dr2 / this.dy2;
                        this.gleftadd = this.dg2 / this.dy2;
                        this.bleftadd = this.db2 / this.dy2;
                        this.rleft = this.dta * this.rleftadd + this.r0;
                        this.gleft = this.dta * this.gleftadd + this.g0;
                        this.bleft = this.dta * this.bleftadd + this.b0;
                    }
                    if (this.INTERPOLATE_ALPHA) {
                        this.aleftadd = this.da2 / this.dy2;
                        this.aleft = this.dta * this.aleftadd + this.a0;
                    }
                    this.m_singleRight = false;
                }
            }
            if (this.m_singleRight) {
                this.dta = yi3 + 0.5f - y2;
                this.xleft = this.dta * this.xadd1 + x2;
                this.zleftadd = (z3 - z2) / this.dy1;
                this.zleft = this.dta * this.zleftadd + z2;
                if (this.INTERPOLATE_UV) {
                    this.uleftadd = (this.u2 - this.u1) / this.dy1;
                    this.vleftadd = (this.v2 - this.v1) / this.dy1;
                    this.uleft = this.dta * this.uleftadd + this.u1;
                    this.vleft = this.dta * this.vleftadd + this.v1;
                }
                if (this.INTERPOLATE_RGB) {
                    this.rleftadd = (this.r2 - this.r1) / this.dy1;
                    this.gleftadd = (this.g2 - this.g1) / this.dy1;
                    this.bleftadd = (this.b2 - this.b1) / this.dy1;
                    this.rleft = this.dta * this.rleftadd + this.r1;
                    this.gleft = this.dta * this.gleftadd + this.g1;
                    this.bleft = this.dta * this.bleftadd + this.b1;
                }
                if (this.INTERPOLATE_ALPHA) {
                    this.aleftadd = (this.a2 - this.a1) / this.dy1;
                    this.aleft = this.dta * this.aleftadd + this.a1;
                    if (this.m_drawFlags == 16) {
                        this.drawsegment_plain_alpha(this.xadd1, this.xadd2, yi3, yi2);
                    }
                    else if (this.m_drawFlags == 17) {
                        this.drawsegment_gouraud_alpha(this.xadd1, this.xadd2, yi3, yi2);
                    }
                    else if (this.m_drawFlags == 18) {
                        this.drawsegment_texture8_alpha(this.xadd1, this.xadd2, yi3, yi2);
                    }
                    else if (this.m_drawFlags == 20) {
                        this.drawsegment_texture24_alpha(this.xadd1, this.xadd2, yi3, yi2);
                    }
                    else if (this.m_drawFlags == 24) {
                        this.drawsegment_texture32_alpha(this.xadd1, this.xadd2, yi3, yi2);
                    }
                    else if (this.m_drawFlags == 19) {
                        this.drawsegment_gouraud_texture8_alpha(this.xadd1, this.xadd2, yi3, yi2);
                    }
                    else if (this.m_drawFlags == 21) {
                        this.drawsegment_gouraud_texture24_alpha(this.xadd1, this.xadd2, yi3, yi2);
                    }
                    else if (this.m_drawFlags == 25) {
                        this.drawsegment_gouraud_texture32_alpha(this.xadd1, this.xadd2, yi3, yi2);
                    }
                }
                else if (this.m_drawFlags == 0) {
                    this.drawsegment_plain(this.xadd1, this.xadd2, yi3, yi2);
                }
                else if (this.m_drawFlags == 1) {
                    this.drawsegment_gouraud(this.xadd1, this.xadd2, yi3, yi2);
                }
                else if (this.m_drawFlags == 2) {
                    this.drawsegment_texture8(this.xadd1, this.xadd2, yi3, yi2);
                }
                else if (this.m_drawFlags == 4) {
                    this.drawsegment_texture24(this.xadd1, this.xadd2, yi3, yi2);
                }
                else if (this.m_drawFlags == 8) {
                    this.drawsegment_texture32(this.xadd1, this.xadd2, yi3, yi2);
                }
                else if (this.m_drawFlags == 3) {
                    this.drawsegment_gouraud_texture8(this.xadd1, this.xadd2, yi3, yi2);
                }
                else if (this.m_drawFlags == 5) {
                    this.drawsegment_gouraud_texture24(this.xadd1, this.xadd2, yi3, yi2);
                }
                else if (this.m_drawFlags == 9) {
                    this.drawsegment_gouraud_texture32(this.xadd1, this.xadd2, yi3, yi2);
                }
            }
            else {
                this.xrght = (yi3 + 0.5f - y2) * this.xadd1 + x2;
                if (this.INTERPOLATE_ALPHA) {
                    if (this.m_drawFlags == 16) {
                        this.drawsegment_plain_alpha(this.xadd2, this.xadd1, yi3, yi2);
                    }
                    else if (this.m_drawFlags == 17) {
                        this.drawsegment_gouraud_alpha(this.xadd2, this.xadd1, yi3, yi2);
                    }
                    else if (this.m_drawFlags == 18) {
                        this.drawsegment_texture8_alpha(this.xadd2, this.xadd1, yi3, yi2);
                    }
                    else if (this.m_drawFlags == 20) {
                        this.drawsegment_texture24_alpha(this.xadd2, this.xadd1, yi3, yi2);
                    }
                    else if (this.m_drawFlags == 24) {
                        this.drawsegment_texture32_alpha(this.xadd2, this.xadd1, yi3, yi2);
                    }
                    else if (this.m_drawFlags == 19) {
                        this.drawsegment_gouraud_texture8_alpha(this.xadd2, this.xadd1, yi3, yi2);
                    }
                    else if (this.m_drawFlags == 21) {
                        this.drawsegment_gouraud_texture24_alpha(this.xadd2, this.xadd1, yi3, yi2);
                    }
                    else if (this.m_drawFlags == 25) {
                        this.drawsegment_gouraud_texture32_alpha(this.xadd2, this.xadd1, yi3, yi2);
                    }
                }
                else if (this.m_drawFlags == 0) {
                    this.drawsegment_plain(this.xadd2, this.xadd1, yi3, yi2);
                }
                else if (this.m_drawFlags == 1) {
                    this.drawsegment_gouraud(this.xadd2, this.xadd1, yi3, yi2);
                }
                else if (this.m_drawFlags == 2) {
                    this.drawsegment_texture8(this.xadd2, this.xadd1, yi3, yi2);
                }
                else if (this.m_drawFlags == 4) {
                    this.drawsegment_texture24(this.xadd2, this.xadd1, yi3, yi2);
                }
                else if (this.m_drawFlags == 8) {
                    this.drawsegment_texture32(this.xadd2, this.xadd1, yi3, yi2);
                }
                else if (this.m_drawFlags == 3) {
                    this.drawsegment_gouraud_texture8(this.xadd2, this.xadd1, yi3, yi2);
                }
                else if (this.m_drawFlags == 5) {
                    this.drawsegment_gouraud_texture24(this.xadd2, this.xadd1, yi3, yi2);
                }
                else if (this.m_drawFlags == 9) {
                    this.drawsegment_gouraud_texture32(this.xadd2, this.xadd1, yi3, yi2);
                }
            }
        }
    }
    
    private boolean precomputeAccurateTexturing() {
        final float myFact = 65500.0f;
        final float myFact2 = 65500.0f;
        if (this.firstSegment) {
            final PMatrix3D myMatrix = new PMatrix3D(this.u_array[this.o0] / myFact, this.v_array[this.o0] / myFact2, 1.0f, 0.0f, this.u_array[this.o1] / myFact, this.v_array[this.o1] / myFact2, 1.0f, 0.0f, this.u_array[this.o2] / myFact, this.v_array[this.o2] / myFact2, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
            myMatrix.invert();
            if (myMatrix == null) {
                return false;
            }
            final float m00 = myMatrix.m00 * this.camX[this.o0] + myMatrix.m01 * this.camX[this.o1] + myMatrix.m02 * this.camX[this.o2];
            final float m2 = myMatrix.m10 * this.camX[this.o0] + myMatrix.m11 * this.camX[this.o1] + myMatrix.m12 * this.camX[this.o2];
            final float m3 = myMatrix.m20 * this.camX[this.o0] + myMatrix.m21 * this.camX[this.o1] + myMatrix.m22 * this.camX[this.o2];
            final float m4 = myMatrix.m00 * this.camY[this.o0] + myMatrix.m01 * this.camY[this.o1] + myMatrix.m02 * this.camY[this.o2];
            final float m5 = myMatrix.m10 * this.camY[this.o0] + myMatrix.m11 * this.camY[this.o1] + myMatrix.m12 * this.camY[this.o2];
            final float m6 = myMatrix.m20 * this.camY[this.o0] + myMatrix.m21 * this.camY[this.o1] + myMatrix.m22 * this.camY[this.o2];
            final float m7 = -(myMatrix.m00 * this.camZ[this.o0] + myMatrix.m01 * this.camZ[this.o1] + myMatrix.m02 * this.camZ[this.o2]);
            final float m8 = -(myMatrix.m10 * this.camZ[this.o0] + myMatrix.m11 * this.camZ[this.o1] + myMatrix.m12 * this.camZ[this.o2]);
            final float m9 = -(myMatrix.m20 * this.camZ[this.o0] + myMatrix.m21 * this.camZ[this.o1] + myMatrix.m22 * this.camZ[this.o2]);
            final float px = m3;
            final float py = m6;
            final float pz = m9;
            final float resultT0x = m00 * this.TEX_WIDTH + m3;
            final float resultT0y = m4 * this.TEX_WIDTH + m6;
            final float resultT0z = m7 * this.TEX_WIDTH + m9;
            final float result0Tx = m2 * this.TEX_HEIGHT + m3;
            final float result0Ty = m5 * this.TEX_HEIGHT + m6;
            final float result0Tz = m8 * this.TEX_HEIGHT + m9;
            final float mx = resultT0x - m3;
            final float my = resultT0y - m6;
            final float mz = resultT0z - m9;
            final float nx = result0Tx - m3;
            final float ny = result0Ty - m6;
            final float nz = result0Tz - m9;
            this.ax = (py * nz - pz * ny) * this.TEX_WIDTH;
            this.ay = (pz * nx - px * nz) * this.TEX_WIDTH;
            this.az = (px * ny - py * nx) * this.TEX_WIDTH;
            this.bx = (my * pz - mz * py) * this.TEX_HEIGHT;
            this.by = (mz * px - mx * pz) * this.TEX_HEIGHT;
            this.bz = (mx * py - my * px) * this.TEX_HEIGHT;
            this.cx = ny * mz - nz * my;
            this.cy = nz * mx - nx * mz;
            this.cz = nx * my - ny * mx;
        }
        this.nearPlaneWidth = this.parent.rightScreen - this.parent.leftScreen;
        this.nearPlaneHeight = this.parent.topScreen - this.parent.bottomScreen;
        this.nearPlaneDepth = this.parent.nearPlane;
        this.xmult = this.nearPlaneWidth / this.SCREEN_WIDTH;
        this.ymult = this.nearPlaneHeight / this.SCREEN_HEIGHT;
        this.newax = this.ax * this.xmult;
        this.newbx = this.bx * this.xmult;
        this.newcx = this.cx * this.xmult;
        return true;
    }
    
    public static void setInterpPower(final int pwr) {
        PTriangle.TEX_INTERP_POWER = pwr;
    }
    
    private void drawsegment_plain(final float leftadd, final float rghtadd, int ytop, int ybottom) {
        int xstart;
        int xend;
        float xdiff;
        float iz;
        for (ytop *= this.SCREEN_WIDTH, ybottom *= this.SCREEN_WIDTH; ytop < ybottom; ytop += this.SCREEN_WIDTH, this.xleft += leftadd, this.xrght += rghtadd, this.zleft += this.zleftadd) {
            xstart = (int)(this.xleft + 0.5f);
            if (xstart < 0) {
                xstart = 0;
            }
            xend = (int)(this.xrght + 0.5f);
            if (xend > this.SCREEN_WIDTH) {
                xend = this.SCREEN_WIDTH;
            }
            xdiff = xstart + 0.5f - this.xleft;
            iz = this.izadd * xdiff + this.zleft;
            for (xstart += ytop, xend += ytop; xstart < xend; ++xstart) {
                if (this.noDepthTest || iz <= this.m_zbuffer[xstart]) {
                    this.m_zbuffer[xstart] = iz;
                    this.m_pixels[xstart] = this.m_fill;
                }
                iz += this.izadd;
            }
        }
    }
    
    private void drawsegment_plain_alpha(final float leftadd, final float rghtadd, int ytop, int ybottom) {
        ytop *= this.SCREEN_WIDTH;
        ybottom *= this.SCREEN_WIDTH;
        final int pr = this.m_fill & 0xFF0000;
        final int pg = this.m_fill & 0xFF00;
        final int pb = this.m_fill & 0xFF;
        final float iaf = this.iaadd;
        while (ytop < ybottom) {
            int xstart = (int)(this.xleft + 0.5f);
            if (xstart < 0) {
                xstart = 0;
            }
            int xend = (int)(this.xrght + 0.5f);
            if (xend > this.SCREEN_WIDTH) {
                xend = this.SCREEN_WIDTH;
            }
            final float xdiff = xstart + 0.5f - this.xleft;
            float iz = this.izadd * xdiff + this.zleft;
            int ia = (int)(iaf * xdiff + this.aleft);
            for (xstart += ytop, xend += ytop; xstart < xend; ++xstart) {
                if (this.noDepthTest || iz <= this.m_zbuffer[xstart]) {
                    final int alpha = ia >> 16;
                    int mr0 = this.m_pixels[xstart];
                    int mg0 = mr0 & 0xFF00;
                    int mb0 = mr0 & 0xFF;
                    mr0 &= 0xFF0000;
                    mr0 += (pr - mr0) * alpha >> 8;
                    mg0 += (pg - mg0) * alpha >> 8;
                    mb0 += (pb - mb0) * alpha >> 8;
                    this.m_pixels[xstart] = (0xFF000000 | (mr0 & 0xFF0000) | (mg0 & 0xFF00) | (mb0 & 0xFF));
                }
                iz += this.izadd;
                ia += this.iaadd;
            }
            ytop += this.SCREEN_WIDTH;
            this.xleft += leftadd;
            this.xrght += rghtadd;
            this.zleft += this.zleftadd;
        }
    }
    
    private void drawsegment_gouraud(final float leftadd, final float rghtadd, int ytop, int ybottom) {
        final float irf = this.iradd;
        final float igf = this.igadd;
        final float ibf = this.ibadd;
        int xstart;
        int xend;
        float xdiff;
        int ir;
        int ig;
        int ib;
        float iz;
        for (ytop *= this.SCREEN_WIDTH, ybottom *= this.SCREEN_WIDTH; ytop < ybottom; ytop += this.SCREEN_WIDTH, this.xleft += leftadd, this.xrght += rghtadd, this.rleft += this.rleftadd, this.gleft += this.gleftadd, this.bleft += this.bleftadd, this.zleft += this.zleftadd) {
            xstart = (int)(this.xleft + 0.5f);
            if (xstart < 0) {
                xstart = 0;
            }
            xend = (int)(this.xrght + 0.5f);
            if (xend > this.SCREEN_WIDTH) {
                xend = this.SCREEN_WIDTH;
            }
            xdiff = xstart + 0.5f - this.xleft;
            ir = (int)(irf * xdiff + this.rleft);
            ig = (int)(igf * xdiff + this.gleft);
            ib = (int)(ibf * xdiff + this.bleft);
            iz = this.izadd * xdiff + this.zleft;
            for (xstart += ytop, xend += ytop; xstart < xend; ++xstart) {
                if (this.noDepthTest || iz <= this.m_zbuffer[xstart]) {
                    this.m_zbuffer[xstart] = iz;
                    this.m_pixels[xstart] = (0xFF000000 | ((ir & 0xFF0000) | (ig >> 8 & 0xFF00) | ib >> 16));
                }
                ir += this.iradd;
                ig += this.igadd;
                ib += this.ibadd;
                iz += this.izadd;
            }
        }
    }
    
    private void drawsegment_gouraud_alpha(final float leftadd, final float rghtadd, int ytop, int ybottom) {
        ytop *= this.SCREEN_WIDTH;
        ybottom *= this.SCREEN_WIDTH;
        final float irf = this.iradd;
        final float igf = this.igadd;
        final float ibf = this.ibadd;
        final float iaf = this.iaadd;
        while (ytop < ybottom) {
            int xstart = (int)(this.xleft + 0.5f);
            if (xstart < 0) {
                xstart = 0;
            }
            int xend = (int)(this.xrght + 0.5f);
            if (xend > this.SCREEN_WIDTH) {
                xend = this.SCREEN_WIDTH;
            }
            final float xdiff = xstart + 0.5f - this.xleft;
            int ir = (int)(irf * xdiff + this.rleft);
            int ig = (int)(igf * xdiff + this.gleft);
            int ib = (int)(ibf * xdiff + this.bleft);
            int ia = (int)(iaf * xdiff + this.aleft);
            float iz = this.izadd * xdiff + this.zleft;
            for (xstart += ytop, xend += ytop; xstart < xend; ++xstart) {
                if (this.noDepthTest || iz <= this.m_zbuffer[xstart]) {
                    final int red = ir & 0xFF0000;
                    final int grn = ig >> 8 & 0xFF00;
                    final int blu = ib >> 16;
                    int bb = this.m_pixels[xstart];
                    final int br = bb & 0xFF0000;
                    final int bg = bb & 0xFF00;
                    bb &= 0xFF;
                    final int al = ia >> 16;
                    this.m_pixels[xstart] = (0xFF000000 | (br + ((red - br) * al >> 8) & 0xFF0000) | (bg + ((grn - bg) * al >> 8) & 0xFF00) | (bb + ((blu - bb) * al >> 8) & 0xFF));
                }
                ir += this.iradd;
                ig += this.igadd;
                ib += this.ibadd;
                ia += this.iaadd;
                iz += this.izadd;
            }
            ytop += this.SCREEN_WIDTH;
            this.xleft += leftadd;
            this.xrght += rghtadd;
            this.rleft += this.rleftadd;
            this.gleft += this.gleftadd;
            this.bleft += this.bleftadd;
            this.aleft += this.aleftadd;
            this.zleft += this.zleftadd;
        }
    }
    
    private void drawsegment_texture8(final float leftadd, final float rghtadd, int ytop, int ybottom) {
        int ypixel = ytop;
        final int lastRowStart = this.m_texture.length - this.TEX_WIDTH - 2;
        boolean accurateMode = this.parent.hints[7];
        float screenx = 0.0f;
        float screeny = 0.0f;
        float screenz = 0.0f;
        float a = 0.0f;
        float b = 0.0f;
        float c = 0.0f;
        final int linearInterpPower = PTriangle.TEX_INTERP_POWER;
        final int linearInterpLength = 1 << linearInterpPower;
        if (accurateMode) {
            if (this.precomputeAccurateTexturing()) {
                this.newax *= linearInterpLength;
                this.newbx *= linearInterpLength;
                this.newcx *= linearInterpLength;
                screenz = this.nearPlaneDepth;
                this.firstSegment = false;
            }
            else {
                accurateMode = false;
            }
        }
        ytop *= this.SCREEN_WIDTH;
        ybottom *= this.SCREEN_WIDTH;
        final float iuf = this.iuadd;
        final float ivf = this.ivadd;
        final int red = this.m_fill & 0xFF0000;
        final int grn = this.m_fill & 0xFF00;
        final int blu = this.m_fill & 0xFF;
        while (ytop < ybottom) {
            int xstart = (int)(this.xleft + 0.5f);
            if (xstart < 0) {
                xstart = 0;
            }
            int xpixel = xstart;
            int xend = (int)(this.xrght + 0.5f);
            if (xend > this.SCREEN_WIDTH) {
                xend = this.SCREEN_WIDTH;
            }
            final float xdiff = xstart + 0.5f - this.xleft;
            int iu = (int)(iuf * xdiff + this.uleft);
            int iv = (int)(ivf * xdiff + this.vleft);
            float iz = this.izadd * xdiff + this.zleft;
            xstart += ytop;
            xend += ytop;
            if (accurateMode) {
                screenx = this.xmult * (xpixel + 0.5f - this.SCREEN_WIDTH / 2.0f);
                screeny = this.ymult * (ypixel + 0.5f - this.SCREEN_HEIGHT / 2.0f);
                a = screenx * this.ax + screeny * this.ay + screenz * this.az;
                b = screenx * this.bx + screeny * this.by + screenz * this.bz;
                c = screenx * this.cx + screeny * this.cy + screenz * this.cz;
            }
            final boolean goingIn = this.newcx > 0.0f != c > 0.0f;
            int interpCounter = 0;
            int deltaU = 0;
            int deltaV = 0;
            float fu = 0.0f;
            float fv = 0.0f;
            float oldfu = 0.0f;
            float oldfv = 0.0f;
            if (accurateMode && goingIn) {
                final int rightOffset = (xend - xstart - 1) % linearInterpLength;
                final int leftOffset = linearInterpLength - rightOffset;
                final float rightOffset2 = rightOffset / linearInterpLength;
                final float leftOffset2 = leftOffset / linearInterpLength;
                interpCounter = leftOffset;
                final float ao = a - leftOffset2 * this.newax;
                final float bo = b - leftOffset2 * this.newbx;
                final float co = c - leftOffset2 * this.newcx;
                float oneoverc = 65536.0f / co;
                oldfu = ao * oneoverc;
                oldfv = bo * oneoverc;
                a += rightOffset2 * this.newax;
                b += rightOffset2 * this.newbx;
                c += rightOffset2 * this.newcx;
                oneoverc = 65536.0f / c;
                fu = a * oneoverc;
                fv = b * oneoverc;
                deltaU = (int)(fu - oldfu) >> linearInterpPower;
                deltaV = (int)(fv - oldfv) >> linearInterpPower;
                iu = (int)oldfu + (leftOffset - 1) * deltaU;
                iv = (int)oldfv + (leftOffset - 1) * deltaV;
            }
            else {
                final float preoneoverc = 65536.0f / c;
                fu = a * preoneoverc;
                fv = b * preoneoverc;
            }
            while (xstart < xend) {
                if (accurateMode) {
                    if (interpCounter == linearInterpLength) {
                        interpCounter = 0;
                    }
                    if (interpCounter == 0) {
                        a += this.newax;
                        b += this.newbx;
                        c += this.newcx;
                        final float oneoverc2 = 65536.0f / c;
                        oldfu = fu;
                        oldfv = fv;
                        fu = a * oneoverc2;
                        fv = b * oneoverc2;
                        iu = (int)oldfu;
                        iv = (int)oldfv;
                        deltaU = (int)(fu - oldfu) >> linearInterpPower;
                        deltaV = (int)(fv - oldfv) >> linearInterpPower;
                    }
                    else {
                        iu += deltaU;
                        iv += deltaV;
                    }
                    ++interpCounter;
                }
                try {
                    if (this.noDepthTest || iz <= this.m_zbuffer[xstart]) {
                        int al0;
                        if (this.m_bilinear) {
                            int ofs = (iv >> 16) * this.TEX_WIDTH + (iu >> 16);
                            final int iui = iu & 0xFFFF;
                            al0 = (this.m_texture[ofs] & 0xFF);
                            final int al2 = this.m_texture[ofs + 1] & 0xFF;
                            if (ofs < lastRowStart) {
                                ofs += this.TEX_WIDTH;
                            }
                            int al3 = this.m_texture[ofs] & 0xFF;
                            final int al4 = this.m_texture[ofs + 1] & 0xFF;
                            al0 += (al2 - al0) * iui >> 16;
                            al3 += (al4 - al3) * iui >> 16;
                            al0 += (al3 - al0) * (iv & 0xFFFF) >> 16;
                        }
                        else {
                            al0 = (this.m_texture[(iv >> 16) * this.TEX_WIDTH + (iu >> 16)] & 0xFF);
                        }
                        int br = this.m_pixels[xstart];
                        final int bg = br & 0xFF00;
                        final int bb = br & 0xFF;
                        br &= 0xFF0000;
                        this.m_pixels[xstart] = (0xFF000000 | (br + ((red - br) * al0 >> 8) & 0xFF0000) | (bg + ((grn - bg) * al0 >> 8) & 0xFF00) | (bb + ((blu - bb) * al0 >> 8) & 0xFF));
                    }
                }
                catch (Exception ex) {}
                ++xpixel;
                if (!accurateMode) {
                    iu += this.iuadd;
                    iv += this.ivadd;
                }
                iz += this.izadd;
                ++xstart;
            }
            ++ypixel;
            ytop += this.SCREEN_WIDTH;
            this.xleft += leftadd;
            this.xrght += rghtadd;
            this.uleft += this.uleftadd;
            this.vleft += this.vleftadd;
            this.zleft += this.zleftadd;
        }
    }
    
    private void drawsegment_texture8_alpha(final float leftadd, final float rghtadd, int ytop, int ybottom) {
        int ypixel = ytop;
        final int lastRowStart = this.m_texture.length - this.TEX_WIDTH - 2;
        boolean accurateMode = this.parent.hints[7];
        float screenx = 0.0f;
        float screeny = 0.0f;
        float screenz = 0.0f;
        float a = 0.0f;
        float b = 0.0f;
        float c = 0.0f;
        final int linearInterpPower = PTriangle.TEX_INTERP_POWER;
        final int linearInterpLength = 1 << linearInterpPower;
        if (accurateMode) {
            if (this.precomputeAccurateTexturing()) {
                this.newax *= linearInterpLength;
                this.newbx *= linearInterpLength;
                this.newcx *= linearInterpLength;
                screenz = this.nearPlaneDepth;
                this.firstSegment = false;
            }
            else {
                accurateMode = false;
            }
        }
        ytop *= this.SCREEN_WIDTH;
        ybottom *= this.SCREEN_WIDTH;
        final float iuf = this.iuadd;
        final float ivf = this.ivadd;
        final float iaf = this.iaadd;
        final int red = this.m_fill & 0xFF0000;
        final int grn = this.m_fill & 0xFF00;
        final int blu = this.m_fill & 0xFF;
        while (ytop < ybottom) {
            int xstart = (int)(this.xleft + 0.5f);
            if (xstart < 0) {
                xstart = 0;
            }
            int xpixel = xstart;
            int xend = (int)(this.xrght + 0.5f);
            if (xend > this.SCREEN_WIDTH) {
                xend = this.SCREEN_WIDTH;
            }
            final float xdiff = xstart + 0.5f - this.xleft;
            int iu = (int)(iuf * xdiff + this.uleft);
            int iv = (int)(ivf * xdiff + this.vleft);
            int ia = (int)(iaf * xdiff + this.aleft);
            float iz = this.izadd * xdiff + this.zleft;
            xstart += ytop;
            xend += ytop;
            if (accurateMode) {
                screenx = this.xmult * (xpixel + 0.5f - this.SCREEN_WIDTH / 2.0f);
                screeny = this.ymult * (ypixel + 0.5f - this.SCREEN_HEIGHT / 2.0f);
                a = screenx * this.ax + screeny * this.ay + screenz * this.az;
                b = screenx * this.bx + screeny * this.by + screenz * this.bz;
                c = screenx * this.cx + screeny * this.cy + screenz * this.cz;
            }
            final boolean goingIn = this.newcx > 0.0f != c > 0.0f;
            int interpCounter = 0;
            int deltaU = 0;
            int deltaV = 0;
            float fu = 0.0f;
            float fv = 0.0f;
            float oldfu = 0.0f;
            float oldfv = 0.0f;
            if (accurateMode && goingIn) {
                final int rightOffset = (xend - xstart - 1) % linearInterpLength;
                final int leftOffset = linearInterpLength - rightOffset;
                final float rightOffset2 = rightOffset / linearInterpLength;
                final float leftOffset2 = leftOffset / linearInterpLength;
                interpCounter = leftOffset;
                final float ao = a - leftOffset2 * this.newax;
                final float bo = b - leftOffset2 * this.newbx;
                final float co = c - leftOffset2 * this.newcx;
                float oneoverc = 65536.0f / co;
                oldfu = ao * oneoverc;
                oldfv = bo * oneoverc;
                a += rightOffset2 * this.newax;
                b += rightOffset2 * this.newbx;
                c += rightOffset2 * this.newcx;
                oneoverc = 65536.0f / c;
                fu = a * oneoverc;
                fv = b * oneoverc;
                deltaU = (int)(fu - oldfu) >> linearInterpPower;
                deltaV = (int)(fv - oldfv) >> linearInterpPower;
                iu = (int)oldfu + (leftOffset - 1) * deltaU;
                iv = (int)oldfv + (leftOffset - 1) * deltaV;
            }
            else {
                final float preoneoverc = 65536.0f / c;
                fu = a * preoneoverc;
                fv = b * preoneoverc;
            }
            while (xstart < xend) {
                if (accurateMode) {
                    if (interpCounter == linearInterpLength) {
                        interpCounter = 0;
                    }
                    if (interpCounter == 0) {
                        a += this.newax;
                        b += this.newbx;
                        c += this.newcx;
                        final float oneoverc2 = 65536.0f / c;
                        oldfu = fu;
                        oldfv = fv;
                        fu = a * oneoverc2;
                        fv = b * oneoverc2;
                        iu = (int)oldfu;
                        iv = (int)oldfv;
                        deltaU = (int)(fu - oldfu) >> linearInterpPower;
                        deltaV = (int)(fv - oldfv) >> linearInterpPower;
                    }
                    else {
                        iu += deltaU;
                        iv += deltaV;
                    }
                    ++interpCounter;
                }
                try {
                    if (this.noDepthTest || iz <= this.m_zbuffer[xstart]) {
                        int al0;
                        if (this.m_bilinear) {
                            int ofs = (iv >> 16) * this.TEX_WIDTH + (iu >> 16);
                            final int iui = iu & 0xFFFF;
                            al0 = (this.m_texture[ofs] & 0xFF);
                            final int al2 = this.m_texture[ofs + 1] & 0xFF;
                            if (ofs < lastRowStart) {
                                ofs += this.TEX_WIDTH;
                            }
                            int al3 = this.m_texture[ofs] & 0xFF;
                            final int al4 = this.m_texture[ofs + 1] & 0xFF;
                            al0 += (al2 - al0) * iui >> 16;
                            al3 += (al4 - al3) * iui >> 16;
                            al0 += (al3 - al0) * (iv & 0xFFFF) >> 16;
                        }
                        else {
                            al0 = (this.m_texture[(iv >> 16) * this.TEX_WIDTH + (iu >> 16)] & 0xFF);
                        }
                        al0 = al0 * (ia >> 16) >> 8;
                        int br = this.m_pixels[xstart];
                        final int bg = br & 0xFF00;
                        final int bb = br & 0xFF;
                        br &= 0xFF0000;
                        this.m_pixels[xstart] = (0xFF000000 | (br + ((red - br) * al0 >> 8) & 0xFF0000) | (bg + ((grn - bg) * al0 >> 8) & 0xFF00) | (bb + ((blu - bb) * al0 >> 8) & 0xFF));
                    }
                }
                catch (Exception ex) {}
                ++xpixel;
                if (!accurateMode) {
                    iu += this.iuadd;
                    iv += this.ivadd;
                }
                iz += this.izadd;
                ia += this.iaadd;
                ++xstart;
            }
            ++ypixel;
            ytop += this.SCREEN_WIDTH;
            this.xleft += leftadd;
            this.xrght += rghtadd;
            this.uleft += this.uleftadd;
            this.vleft += this.vleftadd;
            this.zleft += this.zleftadd;
            this.aleft += this.aleftadd;
        }
    }
    
    private void drawsegment_texture24(final float leftadd, final float rghtadd, int ytop, int ybottom) {
        ytop *= this.SCREEN_WIDTH;
        ybottom *= this.SCREEN_WIDTH;
        final float iuf = this.iuadd;
        final float ivf = this.ivadd;
        final boolean tint = (this.m_fill & 0xFFFFFF) != 0xFFFFFF;
        final int rtint = this.m_fill >> 16 & 0xFF;
        final int gtint = this.m_fill >> 8 & 0xFF;
        final int btint = this.m_fill & 0xFF;
        int ypixel = ytop / this.SCREEN_WIDTH;
        final int lastRowStart = this.m_texture.length - this.TEX_WIDTH - 2;
        boolean accurateMode = this.parent.hints[7];
        float screenx = 0.0f;
        float screeny = 0.0f;
        float screenz = 0.0f;
        float a = 0.0f;
        float b = 0.0f;
        float c = 0.0f;
        final int linearInterpPower = PTriangle.TEX_INTERP_POWER;
        final int linearInterpLength = 1 << linearInterpPower;
        if (accurateMode) {
            if (this.precomputeAccurateTexturing()) {
                this.newax *= linearInterpLength;
                this.newbx *= linearInterpLength;
                this.newcx *= linearInterpLength;
                screenz = this.nearPlaneDepth;
                this.firstSegment = false;
            }
            else {
                accurateMode = false;
            }
        }
        while (ytop < ybottom) {
            int xstart = (int)(this.xleft + 0.5f);
            if (xstart < 0) {
                xstart = 0;
            }
            int xpixel = xstart;
            int xend = (int)(this.xrght + 0.5f);
            if (xend > this.SCREEN_WIDTH) {
                xend = this.SCREEN_WIDTH;
            }
            final float xdiff = xstart + 0.5f - this.xleft;
            int iu = (int)(iuf * xdiff + this.uleft);
            int iv = (int)(ivf * xdiff + this.vleft);
            float iz = this.izadd * xdiff + this.zleft;
            xstart += ytop;
            xend += ytop;
            if (accurateMode) {
                screenx = this.xmult * (xpixel + 0.5f - this.SCREEN_WIDTH / 2.0f);
                screeny = this.ymult * (ypixel + 0.5f - this.SCREEN_HEIGHT / 2.0f);
                a = screenx * this.ax + screeny * this.ay + screenz * this.az;
                b = screenx * this.bx + screeny * this.by + screenz * this.bz;
                c = screenx * this.cx + screeny * this.cy + screenz * this.cz;
            }
            final boolean goingIn = this.newcx > 0.0f != c > 0.0f;
            int interpCounter = 0;
            int deltaU = 0;
            int deltaV = 0;
            float fu = 0.0f;
            float fv = 0.0f;
            float oldfu = 0.0f;
            float oldfv = 0.0f;
            if (accurateMode && goingIn) {
                final int rightOffset = (xend - xstart - 1) % linearInterpLength;
                final int leftOffset = linearInterpLength - rightOffset;
                final float rightOffset2 = rightOffset / linearInterpLength;
                final float leftOffset2 = leftOffset / linearInterpLength;
                interpCounter = leftOffset;
                final float ao = a - leftOffset2 * this.newax;
                final float bo = b - leftOffset2 * this.newbx;
                final float co = c - leftOffset2 * this.newcx;
                float oneoverc = 65536.0f / co;
                oldfu = ao * oneoverc;
                oldfv = bo * oneoverc;
                a += rightOffset2 * this.newax;
                b += rightOffset2 * this.newbx;
                c += rightOffset2 * this.newcx;
                oneoverc = 65536.0f / c;
                fu = a * oneoverc;
                fv = b * oneoverc;
                deltaU = (int)(fu - oldfu) >> linearInterpPower;
                deltaV = (int)(fv - oldfv) >> linearInterpPower;
                iu = (int)oldfu + (leftOffset - 1) * deltaU;
                iv = (int)oldfv + (leftOffset - 1) * deltaV;
            }
            else {
                final float preoneoverc = 65536.0f / c;
                fu = a * preoneoverc;
                fv = b * preoneoverc;
            }
            while (xstart < xend) {
                if (accurateMode) {
                    if (interpCounter == linearInterpLength) {
                        interpCounter = 0;
                    }
                    if (interpCounter == 0) {
                        a += this.newax;
                        b += this.newbx;
                        c += this.newcx;
                        final float oneoverc2 = 65536.0f / c;
                        oldfu = fu;
                        oldfv = fv;
                        fu = a * oneoverc2;
                        fv = b * oneoverc2;
                        iu = (int)oldfu;
                        iv = (int)oldfv;
                        deltaU = (int)(fu - oldfu) >> linearInterpPower;
                        deltaV = (int)(fv - oldfv) >> linearInterpPower;
                    }
                    else {
                        iu += deltaU;
                        iv += deltaV;
                    }
                    ++interpCounter;
                }
                try {
                    if (this.noDepthTest || iz <= this.m_zbuffer[xstart]) {
                        this.m_zbuffer[xstart] = iz;
                        if (this.m_bilinear) {
                            int ofs = (iv >> 16) * this.TEX_WIDTH + (iu >> 16);
                            final int iui = (iu & 0xFFFF) >> 9;
                            final int ivi = (iv & 0xFFFF) >> 9;
                            final int pix0 = this.m_texture[ofs];
                            final int pix2 = this.m_texture[ofs + 1];
                            if (ofs < lastRowStart) {
                                ofs += this.TEX_WIDTH;
                            }
                            final int pix3 = this.m_texture[ofs];
                            final int pix4 = this.m_texture[ofs + 1];
                            int red0 = pix0 & 0xFF0000;
                            int red2 = pix3 & 0xFF0000;
                            int up = red0 + (((pix2 & 0xFF0000) - red0) * iui >> 7);
                            int dn = red2 + (((pix4 & 0xFF0000) - red2) * iui >> 7);
                            int red3 = up + ((dn - up) * ivi >> 7);
                            if (tint) {
                                red3 = (red3 * rtint >> 8 & 0xFF0000);
                            }
                            red0 = (pix0 & 0xFF00);
                            red2 = (pix3 & 0xFF00);
                            up = red0 + (((pix2 & 0xFF00) - red0) * iui >> 7);
                            dn = red2 + (((pix4 & 0xFF00) - red2) * iui >> 7);
                            int grn = up + ((dn - up) * ivi >> 7);
                            if (tint) {
                                grn = (grn * gtint >> 8 & 0xFF00);
                            }
                            red0 = (pix0 & 0xFF);
                            red2 = (pix3 & 0xFF);
                            up = red0 + (((pix2 & 0xFF) - red0) * iui >> 7);
                            dn = red2 + (((pix4 & 0xFF) - red2) * iui >> 7);
                            int blu = up + ((dn - up) * ivi >> 7);
                            if (tint) {
                                blu = (blu * btint >> 8 & 0xFF);
                            }
                            this.m_pixels[xstart] = (0xFF000000 | (red3 & 0xFF0000) | (grn & 0xFF00) | (blu & 0xFF));
                        }
                        else {
                            this.m_pixels[xstart] = this.m_texture[(iv >> 16) * this.TEX_WIDTH + (iu >> 16)];
                        }
                    }
                }
                catch (Exception ex) {}
                iz += this.izadd;
                ++xpixel;
                if (!accurateMode) {
                    iu += this.iuadd;
                    iv += this.ivadd;
                }
                ++xstart;
            }
            ++ypixel;
            ytop += this.SCREEN_WIDTH;
            this.xleft += leftadd;
            this.xrght += rghtadd;
            this.zleft += this.zleftadd;
            this.uleft += this.uleftadd;
            this.vleft += this.vleftadd;
        }
    }
    
    private void drawsegment_texture24_alpha(final float leftadd, final float rghtadd, int ytop, int ybottom) {
        int ypixel = ytop;
        final int lastRowStart = this.m_texture.length - this.TEX_WIDTH - 2;
        boolean accurateMode = this.parent.hints[7];
        float screenx = 0.0f;
        float screeny = 0.0f;
        float screenz = 0.0f;
        float a = 0.0f;
        float b = 0.0f;
        float c = 0.0f;
        final int linearInterpPower = PTriangle.TEX_INTERP_POWER;
        final int linearInterpLength = 1 << linearInterpPower;
        if (accurateMode) {
            if (this.precomputeAccurateTexturing()) {
                this.newax *= linearInterpLength;
                this.newbx *= linearInterpLength;
                this.newcx *= linearInterpLength;
                screenz = this.nearPlaneDepth;
                this.firstSegment = false;
            }
            else {
                accurateMode = false;
            }
        }
        final boolean tint = (this.m_fill & 0xFFFFFF) != 0xFFFFFF;
        final int rtint = this.m_fill >> 16 & 0xFF;
        final int gtint = this.m_fill >> 8 & 0xFF;
        final int btint = this.m_fill & 0xFF;
        ytop *= this.SCREEN_WIDTH;
        ybottom *= this.SCREEN_WIDTH;
        final float iuf = this.iuadd;
        final float ivf = this.ivadd;
        final float iaf = this.iaadd;
        while (ytop < ybottom) {
            int xstart = (int)(this.xleft + 0.5f);
            if (xstart < 0) {
                xstart = 0;
            }
            int xpixel = xstart;
            int xend = (int)(this.xrght + 0.5f);
            if (xend > this.SCREEN_WIDTH) {
                xend = this.SCREEN_WIDTH;
            }
            final float xdiff = xstart + 0.5f - this.xleft;
            int iu = (int)(iuf * xdiff + this.uleft);
            int iv = (int)(ivf * xdiff + this.vleft);
            int ia = (int)(iaf * xdiff + this.aleft);
            float iz = this.izadd * xdiff + this.zleft;
            xstart += ytop;
            xend += ytop;
            if (accurateMode) {
                screenx = this.xmult * (xpixel + 0.5f - this.SCREEN_WIDTH / 2.0f);
                screeny = this.ymult * (ypixel + 0.5f - this.SCREEN_HEIGHT / 2.0f);
                a = screenx * this.ax + screeny * this.ay + screenz * this.az;
                b = screenx * this.bx + screeny * this.by + screenz * this.bz;
                c = screenx * this.cx + screeny * this.cy + screenz * this.cz;
            }
            final boolean goingIn = this.newcx > 0.0f != c > 0.0f;
            int interpCounter = 0;
            int deltaU = 0;
            int deltaV = 0;
            float fu = 0.0f;
            float fv = 0.0f;
            float oldfu = 0.0f;
            float oldfv = 0.0f;
            if (accurateMode && goingIn) {
                final int rightOffset = (xend - xstart - 1) % linearInterpLength;
                final int leftOffset = linearInterpLength - rightOffset;
                final float rightOffset2 = rightOffset / linearInterpLength;
                final float leftOffset2 = leftOffset / linearInterpLength;
                interpCounter = leftOffset;
                final float ao = a - leftOffset2 * this.newax;
                final float bo = b - leftOffset2 * this.newbx;
                final float co = c - leftOffset2 * this.newcx;
                float oneoverc = 65536.0f / co;
                oldfu = ao * oneoverc;
                oldfv = bo * oneoverc;
                a += rightOffset2 * this.newax;
                b += rightOffset2 * this.newbx;
                c += rightOffset2 * this.newcx;
                oneoverc = 65536.0f / c;
                fu = a * oneoverc;
                fv = b * oneoverc;
                deltaU = (int)(fu - oldfu) >> linearInterpPower;
                deltaV = (int)(fv - oldfv) >> linearInterpPower;
                iu = (int)oldfu + (leftOffset - 1) * deltaU;
                iv = (int)oldfv + (leftOffset - 1) * deltaV;
            }
            else {
                final float preoneoverc = 65536.0f / c;
                fu = a * preoneoverc;
                fv = b * preoneoverc;
            }
            while (xstart < xend) {
                if (accurateMode) {
                    if (interpCounter == linearInterpLength) {
                        interpCounter = 0;
                    }
                    if (interpCounter == 0) {
                        a += this.newax;
                        b += this.newbx;
                        c += this.newcx;
                        final float oneoverc2 = 65536.0f / c;
                        oldfu = fu;
                        oldfv = fv;
                        fu = a * oneoverc2;
                        fv = b * oneoverc2;
                        iu = (int)oldfu;
                        iv = (int)oldfv;
                        deltaU = (int)(fu - oldfu) >> linearInterpPower;
                        deltaV = (int)(fv - oldfv) >> linearInterpPower;
                    }
                    else {
                        iu += deltaU;
                        iv += deltaV;
                    }
                    ++interpCounter;
                }
                try {
                    if (this.noDepthTest || iz <= this.m_zbuffer[xstart]) {
                        final int al = ia >> 16;
                        if (this.m_bilinear) {
                            int ofs = (iv >> 16) * this.TEX_WIDTH + (iu >> 16);
                            final int iui = (iu & 0xFFFF) >> 9;
                            final int ivi = (iv & 0xFFFF) >> 9;
                            final int pix0 = this.m_texture[ofs];
                            final int pix2 = this.m_texture[ofs + 1];
                            if (ofs < lastRowStart) {
                                ofs += this.TEX_WIDTH;
                            }
                            final int pix3 = this.m_texture[ofs];
                            final int pix4 = this.m_texture[ofs + 1];
                            int red0 = pix0 & 0xFF0000;
                            int red2 = pix3 & 0xFF0000;
                            int up = red0 + (((pix2 & 0xFF0000) - red0) * iui >> 7);
                            int dn = red2 + (((pix4 & 0xFF0000) - red2) * iui >> 7);
                            int red3 = up + ((dn - up) * ivi >> 7);
                            if (tint) {
                                red3 = (red3 * rtint >> 8 & 0xFF0000);
                            }
                            red0 = (pix0 & 0xFF00);
                            red2 = (pix3 & 0xFF00);
                            up = red0 + (((pix2 & 0xFF00) - red0) * iui >> 7);
                            dn = red2 + (((pix4 & 0xFF00) - red2) * iui >> 7);
                            int grn = up + ((dn - up) * ivi >> 7);
                            if (tint) {
                                grn = (grn * gtint >> 8 & 0xFF00);
                            }
                            red0 = (pix0 & 0xFF);
                            red2 = (pix3 & 0xFF);
                            up = red0 + (((pix2 & 0xFF) - red0) * iui >> 7);
                            dn = red2 + (((pix4 & 0xFF) - red2) * iui >> 7);
                            int blu = up + ((dn - up) * ivi >> 7);
                            if (tint) {
                                blu = (blu * btint >> 8 & 0xFF);
                            }
                            int bb = this.m_pixels[xstart];
                            final int br = bb & 0xFF0000;
                            final int bg = bb & 0xFF00;
                            bb &= 0xFF;
                            this.m_pixels[xstart] = (0xFF000000 | (br + ((red3 - br) * al >> 8) & 0xFF0000) | (bg + ((grn - bg) * al >> 8) & 0xFF00) | (bb + ((blu - bb) * al >> 8) & 0xFF));
                        }
                        else {
                            int red4 = this.m_texture[(iv >> 16) * this.TEX_WIDTH + (iu >> 16)];
                            final int grn2 = red4 & 0xFF00;
                            final int blu2 = red4 & 0xFF;
                            red4 &= 0xFF0000;
                            int bb2 = this.m_pixels[xstart];
                            final int br2 = bb2 & 0xFF0000;
                            final int bg2 = bb2 & 0xFF00;
                            bb2 &= 0xFF;
                            this.m_pixels[xstart] = (0xFF000000 | (br2 + ((red4 - br2) * al >> 8) & 0xFF0000) | (bg2 + ((grn2 - bg2) * al >> 8) & 0xFF00) | (bb2 + ((blu2 - bb2) * al >> 8) & 0xFF));
                        }
                    }
                }
                catch (Exception ex) {}
                ++xpixel;
                if (!accurateMode) {
                    iu += this.iuadd;
                    iv += this.ivadd;
                }
                ia += this.iaadd;
                iz += this.izadd;
                ++xstart;
            }
            ++ypixel;
            ytop += this.SCREEN_WIDTH;
            this.xleft += leftadd;
            this.xrght += rghtadd;
            this.uleft += this.uleftadd;
            this.vleft += this.vleftadd;
            this.zleft += this.zleftadd;
            this.aleft += this.aleftadd;
        }
    }
    
    private void drawsegment_texture32(final float leftadd, final float rghtadd, int ytop, int ybottom) {
        int ypixel = ytop;
        final int lastRowStart = this.m_texture.length - this.TEX_WIDTH - 2;
        boolean accurateMode = this.parent.hints[7];
        float screenx = 0.0f;
        float screeny = 0.0f;
        float screenz = 0.0f;
        float a = 0.0f;
        float b = 0.0f;
        float c = 0.0f;
        final int linearInterpPower = PTriangle.TEX_INTERP_POWER;
        final int linearInterpLength = 1 << linearInterpPower;
        if (accurateMode) {
            if (this.precomputeAccurateTexturing()) {
                this.newax *= linearInterpLength;
                this.newbx *= linearInterpLength;
                this.newcx *= linearInterpLength;
                screenz = this.nearPlaneDepth;
                this.firstSegment = false;
            }
            else {
                accurateMode = false;
            }
        }
        ytop *= this.SCREEN_WIDTH;
        ybottom *= this.SCREEN_WIDTH;
        final boolean tint = this.m_fill != -1;
        final int rtint = this.m_fill >> 16 & 0xFF;
        final int gtint = this.m_fill >> 8 & 0xFF;
        final int btint = this.m_fill & 0xFF;
        final float iuf = this.iuadd;
        final float ivf = this.ivadd;
        while (ytop < ybottom) {
            int xstart = (int)(this.xleft + 0.5f);
            if (xstart < 0) {
                xstart = 0;
            }
            int xpixel = xstart;
            int xend = (int)(this.xrght + 0.5f);
            if (xend > this.SCREEN_WIDTH) {
                xend = this.SCREEN_WIDTH;
            }
            final float xdiff = xstart + 0.5f - this.xleft;
            int iu = (int)(iuf * xdiff + this.uleft);
            int iv = (int)(ivf * xdiff + this.vleft);
            float iz = this.izadd * xdiff + this.zleft;
            xstart += ytop;
            xend += ytop;
            if (accurateMode) {
                screenx = this.xmult * (xpixel + 0.5f - this.SCREEN_WIDTH / 2.0f);
                screeny = this.ymult * (ypixel + 0.5f - this.SCREEN_HEIGHT / 2.0f);
                a = screenx * this.ax + screeny * this.ay + screenz * this.az;
                b = screenx * this.bx + screeny * this.by + screenz * this.bz;
                c = screenx * this.cx + screeny * this.cy + screenz * this.cz;
            }
            final boolean goingIn = this.newcx > 0.0f != c > 0.0f;
            int interpCounter = 0;
            int deltaU = 0;
            int deltaV = 0;
            float fu = 0.0f;
            float fv = 0.0f;
            float oldfu = 0.0f;
            float oldfv = 0.0f;
            if (accurateMode && goingIn) {
                final int rightOffset = (xend - xstart - 1) % linearInterpLength;
                final int leftOffset = linearInterpLength - rightOffset;
                final float rightOffset2 = rightOffset / linearInterpLength;
                final float leftOffset2 = leftOffset / linearInterpLength;
                interpCounter = leftOffset;
                final float ao = a - leftOffset2 * this.newax;
                final float bo = b - leftOffset2 * this.newbx;
                final float co = c - leftOffset2 * this.newcx;
                float oneoverc = 65536.0f / co;
                oldfu = ao * oneoverc;
                oldfv = bo * oneoverc;
                a += rightOffset2 * this.newax;
                b += rightOffset2 * this.newbx;
                c += rightOffset2 * this.newcx;
                oneoverc = 65536.0f / c;
                fu = a * oneoverc;
                fv = b * oneoverc;
                deltaU = (int)(fu - oldfu) >> linearInterpPower;
                deltaV = (int)(fv - oldfv) >> linearInterpPower;
                iu = (int)oldfu + (leftOffset - 1) * deltaU;
                iv = (int)oldfv + (leftOffset - 1) * deltaV;
            }
            else {
                final float preoneoverc = 65536.0f / c;
                fu = a * preoneoverc;
                fv = b * preoneoverc;
            }
            while (xstart < xend) {
                if (accurateMode) {
                    if (interpCounter == linearInterpLength) {
                        interpCounter = 0;
                    }
                    if (interpCounter == 0) {
                        a += this.newax;
                        b += this.newbx;
                        c += this.newcx;
                        final float oneoverc2 = 65536.0f / c;
                        oldfu = fu;
                        oldfv = fv;
                        fu = a * oneoverc2;
                        fv = b * oneoverc2;
                        iu = (int)oldfu;
                        iv = (int)oldfv;
                        deltaU = (int)(fu - oldfu) >> linearInterpPower;
                        deltaV = (int)(fv - oldfv) >> linearInterpPower;
                    }
                    else {
                        iu += deltaU;
                        iv += deltaV;
                    }
                    ++interpCounter;
                }
                try {
                    if (this.noDepthTest || iz <= this.m_zbuffer[xstart]) {
                        if (this.m_bilinear) {
                            int ofs = (iv >> 16) * this.TEX_WIDTH + (iu >> 16);
                            final int iui = (iu & 0xFFFF) >> 9;
                            final int ivi = (iv & 0xFFFF) >> 9;
                            int pix0 = this.m_texture[ofs];
                            final int pix2 = this.m_texture[ofs + 1];
                            if (ofs < lastRowStart) {
                                ofs += this.TEX_WIDTH;
                            }
                            int pix3 = this.m_texture[ofs];
                            final int pix4 = this.m_texture[ofs + 1];
                            int red0 = pix0 & 0xFF0000;
                            int red2 = pix3 & 0xFF0000;
                            int up = red0 + (((pix2 & 0xFF0000) - red0) * iui >> 7);
                            int dn = red2 + (((pix4 & 0xFF0000) - red2) * iui >> 7);
                            int red3 = up + ((dn - up) * ivi >> 7);
                            if (tint) {
                                red3 = (red3 * rtint >> 8 & 0xFF0000);
                            }
                            red0 = (pix0 & 0xFF00);
                            red2 = (pix3 & 0xFF00);
                            up = red0 + (((pix2 & 0xFF00) - red0) * iui >> 7);
                            dn = red2 + (((pix4 & 0xFF00) - red2) * iui >> 7);
                            int grn = up + ((dn - up) * ivi >> 7);
                            if (tint) {
                                grn = (grn * gtint >> 8 & 0xFF00);
                            }
                            red0 = (pix0 & 0xFF);
                            red2 = (pix3 & 0xFF);
                            up = red0 + (((pix2 & 0xFF) - red0) * iui >> 7);
                            dn = red2 + (((pix4 & 0xFF) - red2) * iui >> 7);
                            int blu = up + ((dn - up) * ivi >> 7);
                            if (tint) {
                                blu = (blu * btint >> 8 & 0xFF);
                            }
                            pix0 >>>= 24;
                            pix3 >>>= 24;
                            up = pix0 + (((pix2 >>> 24) - pix0) * iui >> 7);
                            dn = pix3 + (((pix4 >>> 24) - pix3) * iui >> 7);
                            final int al = up + ((dn - up) * ivi >> 7);
                            int bb = this.m_pixels[xstart];
                            final int br = bb & 0xFF0000;
                            final int bg = bb & 0xFF00;
                            bb &= 0xFF;
                            this.m_pixels[xstart] = (0xFF000000 | (br + ((red3 - br) * al >> 8) & 0xFF0000) | (bg + ((grn - bg) * al >> 8) & 0xFF00) | (bb + ((blu - bb) * al >> 8) & 0xFF));
                        }
                        else {
                            int red4 = this.m_texture[(iv >> 16) * this.TEX_WIDTH + (iu >> 16)];
                            final int al2 = red4 >>> 24;
                            final int grn2 = red4 & 0xFF00;
                            final int blu2 = red4 & 0xFF;
                            red4 &= 0xFF0000;
                            int bb2 = this.m_pixels[xstart];
                            final int br2 = bb2 & 0xFF0000;
                            final int bg2 = bb2 & 0xFF00;
                            bb2 &= 0xFF;
                            this.m_pixels[xstart] = (0xFF000000 | (br2 + ((red4 - br2) * al2 >> 8) & 0xFF0000) | (bg2 + ((grn2 - bg2) * al2 >> 8) & 0xFF00) | (bb2 + ((blu2 - bb2) * al2 >> 8) & 0xFF));
                        }
                    }
                }
                catch (Exception ex) {}
                ++xpixel;
                if (!accurateMode) {
                    iu += this.iuadd;
                    iv += this.ivadd;
                }
                iz += this.izadd;
                ++xstart;
            }
            ++ypixel;
            ytop += this.SCREEN_WIDTH;
            this.xleft += leftadd;
            this.xrght += rghtadd;
            this.uleft += this.uleftadd;
            this.vleft += this.vleftadd;
            this.zleft += this.zleftadd;
            this.aleft += this.aleftadd;
        }
    }
    
    private void drawsegment_texture32_alpha(final float leftadd, final float rghtadd, int ytop, int ybottom) {
        int ypixel = ytop;
        final int lastRowStart = this.m_texture.length - this.TEX_WIDTH - 2;
        boolean accurateMode = this.parent.hints[7];
        float screenx = 0.0f;
        float screeny = 0.0f;
        float screenz = 0.0f;
        float a = 0.0f;
        float b = 0.0f;
        float c = 0.0f;
        final int linearInterpPower = PTriangle.TEX_INTERP_POWER;
        final int linearInterpLength = 1 << linearInterpPower;
        if (accurateMode) {
            if (this.precomputeAccurateTexturing()) {
                this.newax *= linearInterpLength;
                this.newbx *= linearInterpLength;
                this.newcx *= linearInterpLength;
                screenz = this.nearPlaneDepth;
                this.firstSegment = false;
            }
            else {
                accurateMode = false;
            }
        }
        ytop *= this.SCREEN_WIDTH;
        ybottom *= this.SCREEN_WIDTH;
        final boolean tint = (this.m_fill & 0xFFFFFF) != 0xFFFFFF;
        final int rtint = this.m_fill >> 16 & 0xFF;
        final int gtint = this.m_fill >> 8 & 0xFF;
        final int btint = this.m_fill & 0xFF;
        final float iuf = this.iuadd;
        final float ivf = this.ivadd;
        final float iaf = this.iaadd;
        while (ytop < ybottom) {
            int xstart = (int)(this.xleft + 0.5f);
            if (xstart < 0) {
                xstart = 0;
            }
            int xpixel = xstart;
            int xend = (int)(this.xrght + 0.5f);
            if (xend > this.SCREEN_WIDTH) {
                xend = this.SCREEN_WIDTH;
            }
            final float xdiff = xstart + 0.5f - this.xleft;
            int iu = (int)(iuf * xdiff + this.uleft);
            int iv = (int)(ivf * xdiff + this.vleft);
            int ia = (int)(iaf * xdiff + this.aleft);
            float iz = this.izadd * xdiff + this.zleft;
            xstart += ytop;
            xend += ytop;
            if (accurateMode) {
                screenx = this.xmult * (xpixel + 0.5f - this.SCREEN_WIDTH / 2.0f);
                screeny = this.ymult * (ypixel + 0.5f - this.SCREEN_HEIGHT / 2.0f);
                a = screenx * this.ax + screeny * this.ay + screenz * this.az;
                b = screenx * this.bx + screeny * this.by + screenz * this.bz;
                c = screenx * this.cx + screeny * this.cy + screenz * this.cz;
            }
            final boolean goingIn = this.newcx > 0.0f != c > 0.0f;
            int interpCounter = 0;
            int deltaU = 0;
            int deltaV = 0;
            float fu = 0.0f;
            float fv = 0.0f;
            float oldfu = 0.0f;
            float oldfv = 0.0f;
            if (accurateMode && goingIn) {
                final int rightOffset = (xend - xstart - 1) % linearInterpLength;
                final int leftOffset = linearInterpLength - rightOffset;
                final float rightOffset2 = rightOffset / linearInterpLength;
                final float leftOffset2 = leftOffset / linearInterpLength;
                interpCounter = leftOffset;
                final float ao = a - leftOffset2 * this.newax;
                final float bo = b - leftOffset2 * this.newbx;
                final float co = c - leftOffset2 * this.newcx;
                float oneoverc = 65536.0f / co;
                oldfu = ao * oneoverc;
                oldfv = bo * oneoverc;
                a += rightOffset2 * this.newax;
                b += rightOffset2 * this.newbx;
                c += rightOffset2 * this.newcx;
                oneoverc = 65536.0f / c;
                fu = a * oneoverc;
                fv = b * oneoverc;
                deltaU = (int)(fu - oldfu) >> linearInterpPower;
                deltaV = (int)(fv - oldfv) >> linearInterpPower;
                iu = (int)oldfu + (leftOffset - 1) * deltaU;
                iv = (int)oldfv + (leftOffset - 1) * deltaV;
            }
            else {
                final float preoneoverc = 65536.0f / c;
                fu = a * preoneoverc;
                fv = b * preoneoverc;
            }
            while (xstart < xend) {
                if (accurateMode) {
                    if (interpCounter == linearInterpLength) {
                        interpCounter = 0;
                    }
                    if (interpCounter == 0) {
                        a += this.newax;
                        b += this.newbx;
                        c += this.newcx;
                        final float oneoverc2 = 65536.0f / c;
                        oldfu = fu;
                        oldfv = fv;
                        fu = a * oneoverc2;
                        fv = b * oneoverc2;
                        iu = (int)oldfu;
                        iv = (int)oldfv;
                        deltaU = (int)(fu - oldfu) >> linearInterpPower;
                        deltaV = (int)(fv - oldfv) >> linearInterpPower;
                    }
                    else {
                        iu += deltaU;
                        iv += deltaV;
                    }
                    ++interpCounter;
                }
                try {
                    if (this.noDepthTest || iz <= this.m_zbuffer[xstart]) {
                        int al = ia >> 16;
                        if (this.m_bilinear) {
                            int ofs = (iv >> 16) * this.TEX_WIDTH + (iu >> 16);
                            final int iui = (iu & 0xFFFF) >> 9;
                            final int ivi = (iv & 0xFFFF) >> 9;
                            int pix0 = this.m_texture[ofs];
                            final int pix2 = this.m_texture[ofs + 1];
                            if (ofs < lastRowStart) {
                                ofs += this.TEX_WIDTH;
                            }
                            int pix3 = this.m_texture[ofs];
                            final int pix4 = this.m_texture[ofs + 1];
                            int red0 = pix0 & 0xFF0000;
                            int red2 = pix3 & 0xFF0000;
                            int up = red0 + (((pix2 & 0xFF0000) - red0) * iui >> 7);
                            int dn = red2 + (((pix4 & 0xFF0000) - red2) * iui >> 7);
                            int red3 = up + ((dn - up) * ivi >> 7);
                            if (tint) {
                                red3 = (red3 * rtint >> 8 & 0xFF0000);
                            }
                            red0 = (pix0 & 0xFF00);
                            red2 = (pix3 & 0xFF00);
                            up = red0 + (((pix2 & 0xFF00) - red0) * iui >> 7);
                            dn = red2 + (((pix4 & 0xFF00) - red2) * iui >> 7);
                            int grn = up + ((dn - up) * ivi >> 7);
                            if (tint) {
                                grn = (grn * gtint >> 8 & 0xFF00);
                            }
                            red0 = (pix0 & 0xFF);
                            red2 = (pix3 & 0xFF);
                            up = red0 + (((pix2 & 0xFF) - red0) * iui >> 7);
                            dn = red2 + (((pix4 & 0xFF) - red2) * iui >> 7);
                            int blu = up + ((dn - up) * ivi >> 7);
                            if (tint) {
                                blu = (blu * btint >> 8 & 0xFF);
                            }
                            pix0 >>>= 24;
                            pix3 >>>= 24;
                            up = pix0 + (((pix2 >>> 24) - pix0) * iui >> 7);
                            dn = pix3 + (((pix4 >>> 24) - pix3) * iui >> 7);
                            al = al * (up + ((dn - up) * ivi >> 7)) >> 8;
                            int bb = this.m_pixels[xstart];
                            final int br = bb & 0xFF0000;
                            final int bg = bb & 0xFF00;
                            bb &= 0xFF;
                            this.m_pixels[xstart] = (0xFF000000 | (br + ((red3 - br) * al >> 8) & 0xFF0000) | (bg + ((grn - bg) * al >> 8) & 0xFF00) | (bb + ((blu - bb) * al >> 8) & 0xFF));
                        }
                        else {
                            int red4 = this.m_texture[(iv >> 16) * this.TEX_WIDTH + (iu >> 16)];
                            al = al * (red4 >>> 24) >> 8;
                            final int grn2 = red4 & 0xFF00;
                            final int blu2 = red4 & 0xFF;
                            red4 &= 0xFF0000;
                            int bb2 = this.m_pixels[xstart];
                            final int br2 = bb2 & 0xFF0000;
                            final int bg2 = bb2 & 0xFF00;
                            bb2 &= 0xFF;
                            this.m_pixels[xstart] = (0xFF000000 | (br2 + ((red4 - br2) * al >> 8) & 0xFF0000) | (bg2 + ((grn2 - bg2) * al >> 8) & 0xFF00) | (bb2 + ((blu2 - bb2) * al >> 8) & 0xFF));
                        }
                    }
                }
                catch (Exception ex) {}
                ++xpixel;
                if (!accurateMode) {
                    iu += this.iuadd;
                    iv += this.ivadd;
                }
                ia += this.iaadd;
                iz += this.izadd;
                ++xstart;
            }
            ++ypixel;
            ytop += this.SCREEN_WIDTH;
            this.xleft += leftadd;
            this.xrght += rghtadd;
            this.uleft += this.uleftadd;
            this.vleft += this.vleftadd;
            this.zleft += this.zleftadd;
            this.aleft += this.aleftadd;
        }
    }
    
    private void drawsegment_gouraud_texture8(final float leftadd, final float rghtadd, int ytop, int ybottom) {
        int ypixel = ytop;
        final int lastRowStart = this.m_texture.length - this.TEX_WIDTH - 2;
        boolean accurateMode = this.parent.hints[7];
        float screenx = 0.0f;
        float screeny = 0.0f;
        float screenz = 0.0f;
        float a = 0.0f;
        float b = 0.0f;
        float c = 0.0f;
        final int linearInterpPower = PTriangle.TEX_INTERP_POWER;
        final int linearInterpLength = 1 << linearInterpPower;
        if (accurateMode) {
            if (this.precomputeAccurateTexturing()) {
                this.newax *= linearInterpLength;
                this.newbx *= linearInterpLength;
                this.newcx *= linearInterpLength;
                screenz = this.nearPlaneDepth;
                this.firstSegment = false;
            }
            else {
                accurateMode = false;
            }
        }
        ytop *= this.SCREEN_WIDTH;
        ybottom *= this.SCREEN_WIDTH;
        final float iuf = this.iuadd;
        final float ivf = this.ivadd;
        final float irf = this.iradd;
        final float igf = this.igadd;
        final float ibf = this.ibadd;
        while (ytop < ybottom) {
            int xstart = (int)(this.xleft + 0.5f);
            if (xstart < 0) {
                xstart = 0;
            }
            int xpixel = xstart;
            int xend = (int)(this.xrght + 0.5f);
            if (xend > this.SCREEN_WIDTH) {
                xend = this.SCREEN_WIDTH;
            }
            final float xdiff = xstart + 0.5f - this.xleft;
            int iu = (int)(iuf * xdiff + this.uleft);
            int iv = (int)(ivf * xdiff + this.vleft);
            int ir = (int)(irf * xdiff + this.rleft);
            int ig = (int)(igf * xdiff + this.gleft);
            int ib = (int)(ibf * xdiff + this.bleft);
            float iz = this.izadd * xdiff + this.zleft;
            xstart += ytop;
            xend += ytop;
            if (accurateMode) {
                screenx = this.xmult * (xpixel + 0.5f - this.SCREEN_WIDTH / 2.0f);
                screeny = this.ymult * (ypixel + 0.5f - this.SCREEN_HEIGHT / 2.0f);
                a = screenx * this.ax + screeny * this.ay + screenz * this.az;
                b = screenx * this.bx + screeny * this.by + screenz * this.bz;
                c = screenx * this.cx + screeny * this.cy + screenz * this.cz;
            }
            final boolean goingIn = this.newcx > 0.0f != c > 0.0f;
            int interpCounter = 0;
            int deltaU = 0;
            int deltaV = 0;
            float fu = 0.0f;
            float fv = 0.0f;
            float oldfu = 0.0f;
            float oldfv = 0.0f;
            if (accurateMode && goingIn) {
                final int rightOffset = (xend - xstart - 1) % linearInterpLength;
                final int leftOffset = linearInterpLength - rightOffset;
                final float rightOffset2 = rightOffset / linearInterpLength;
                final float leftOffset2 = leftOffset / linearInterpLength;
                interpCounter = leftOffset;
                final float ao = a - leftOffset2 * this.newax;
                final float bo = b - leftOffset2 * this.newbx;
                final float co = c - leftOffset2 * this.newcx;
                float oneoverc = 65536.0f / co;
                oldfu = ao * oneoverc;
                oldfv = bo * oneoverc;
                a += rightOffset2 * this.newax;
                b += rightOffset2 * this.newbx;
                c += rightOffset2 * this.newcx;
                oneoverc = 65536.0f / c;
                fu = a * oneoverc;
                fv = b * oneoverc;
                deltaU = (int)(fu - oldfu) >> linearInterpPower;
                deltaV = (int)(fv - oldfv) >> linearInterpPower;
                iu = (int)oldfu + (leftOffset - 1) * deltaU;
                iv = (int)oldfv + (leftOffset - 1) * deltaV;
            }
            else {
                final float preoneoverc = 65536.0f / c;
                fu = a * preoneoverc;
                fv = b * preoneoverc;
            }
            while (xstart < xend) {
                if (accurateMode) {
                    if (interpCounter == linearInterpLength) {
                        interpCounter = 0;
                    }
                    if (interpCounter == 0) {
                        a += this.newax;
                        b += this.newbx;
                        c += this.newcx;
                        final float oneoverc2 = 65536.0f / c;
                        oldfu = fu;
                        oldfv = fv;
                        fu = a * oneoverc2;
                        fv = b * oneoverc2;
                        iu = (int)oldfu;
                        iv = (int)oldfv;
                        deltaU = (int)(fu - oldfu) >> linearInterpPower;
                        deltaV = (int)(fv - oldfv) >> linearInterpPower;
                    }
                    else {
                        iu += deltaU;
                        iv += deltaV;
                    }
                    ++interpCounter;
                }
                try {
                    if (this.noDepthTest || iz <= this.m_zbuffer[xstart]) {
                        int al0;
                        if (this.m_bilinear) {
                            int ofs = (iv >> 16) * this.TEX_WIDTH + (iu >> 16);
                            final int iui = iu & 0xFFFF;
                            al0 = (this.m_texture[ofs] & 0xFF);
                            final int al2 = this.m_texture[ofs + 1] & 0xFF;
                            if (ofs < lastRowStart) {
                                ofs += this.TEX_WIDTH;
                            }
                            int al3 = this.m_texture[ofs] & 0xFF;
                            final int al4 = this.m_texture[ofs + 1] & 0xFF;
                            al0 += (al2 - al0) * iui >> 16;
                            al3 += (al4 - al3) * iui >> 16;
                            al0 += (al3 - al0) * (iv & 0xFFFF) >> 16;
                        }
                        else {
                            al0 = (this.m_texture[(iv >> 16) * this.TEX_WIDTH + (iu >> 16)] & 0xFF);
                        }
                        final int red = ir & 0xFF0000;
                        final int grn = ig >> 8 & 0xFF00;
                        final int blu = ib >> 16;
                        int bb = this.m_pixels[xstart];
                        final int br = bb & 0xFF0000;
                        final int bg = bb & 0xFF00;
                        bb &= 0xFF;
                        this.m_pixels[xstart] = (0xFF000000 | (br + ((red - br) * al0 >> 8) & 0xFF0000) | (bg + ((grn - bg) * al0 >> 8) & 0xFF00) | (bb + ((blu - bb) * al0 >> 8) & 0xFF));
                    }
                }
                catch (Exception ex) {}
                ++xpixel;
                if (!accurateMode) {
                    iu += this.iuadd;
                    iv += this.ivadd;
                }
                ir += this.iradd;
                ig += this.igadd;
                ib += this.ibadd;
                iz += this.izadd;
                ++xstart;
            }
            ++ypixel;
            ytop += this.SCREEN_WIDTH;
            this.xleft += leftadd;
            this.xrght += rghtadd;
            this.uleft += this.uleftadd;
            this.vleft += this.vleftadd;
            this.rleft += this.rleftadd;
            this.gleft += this.gleftadd;
            this.bleft += this.bleftadd;
            this.zleft += this.zleftadd;
        }
    }
    
    private void drawsegment_gouraud_texture8_alpha(final float leftadd, final float rghtadd, int ytop, int ybottom) {
        int ypixel = ytop;
        final int lastRowStart = this.m_texture.length - this.TEX_WIDTH - 2;
        boolean accurateMode = this.parent.hints[7];
        float screenx = 0.0f;
        float screeny = 0.0f;
        float screenz = 0.0f;
        float a = 0.0f;
        float b = 0.0f;
        float c = 0.0f;
        final int linearInterpPower = PTriangle.TEX_INTERP_POWER;
        final int linearInterpLength = 1 << linearInterpPower;
        if (accurateMode) {
            if (this.precomputeAccurateTexturing()) {
                this.newax *= linearInterpLength;
                this.newbx *= linearInterpLength;
                this.newcx *= linearInterpLength;
                screenz = this.nearPlaneDepth;
                this.firstSegment = false;
            }
            else {
                accurateMode = false;
            }
        }
        ytop *= this.SCREEN_WIDTH;
        ybottom *= this.SCREEN_WIDTH;
        final float iuf = this.iuadd;
        final float ivf = this.ivadd;
        final float irf = this.iradd;
        final float igf = this.igadd;
        final float ibf = this.ibadd;
        final float iaf = this.iaadd;
        while (ytop < ybottom) {
            int xstart = (int)(this.xleft + 0.5f);
            if (xstart < 0) {
                xstart = 0;
            }
            int xpixel = xstart;
            int xend = (int)(this.xrght + 0.5f);
            if (xend > this.SCREEN_WIDTH) {
                xend = this.SCREEN_WIDTH;
            }
            final float xdiff = xstart + 0.5f - this.xleft;
            int iu = (int)(iuf * xdiff + this.uleft);
            int iv = (int)(ivf * xdiff + this.vleft);
            int ir = (int)(irf * xdiff + this.rleft);
            int ig = (int)(igf * xdiff + this.gleft);
            int ib = (int)(ibf * xdiff + this.bleft);
            int ia = (int)(iaf * xdiff + this.aleft);
            float iz = this.izadd * xdiff + this.zleft;
            xstart += ytop;
            xend += ytop;
            if (accurateMode) {
                screenx = this.xmult * (xpixel + 0.5f - this.SCREEN_WIDTH / 2.0f);
                screeny = this.ymult * (ypixel + 0.5f - this.SCREEN_HEIGHT / 2.0f);
                a = screenx * this.ax + screeny * this.ay + screenz * this.az;
                b = screenx * this.bx + screeny * this.by + screenz * this.bz;
                c = screenx * this.cx + screeny * this.cy + screenz * this.cz;
            }
            final boolean goingIn = this.newcx > 0.0f != c > 0.0f;
            int interpCounter = 0;
            int deltaU = 0;
            int deltaV = 0;
            float fu = 0.0f;
            float fv = 0.0f;
            float oldfu = 0.0f;
            float oldfv = 0.0f;
            if (accurateMode && goingIn) {
                final int rightOffset = (xend - xstart - 1) % linearInterpLength;
                final int leftOffset = linearInterpLength - rightOffset;
                final float rightOffset2 = rightOffset / linearInterpLength;
                final float leftOffset2 = leftOffset / linearInterpLength;
                interpCounter = leftOffset;
                final float ao = a - leftOffset2 * this.newax;
                final float bo = b - leftOffset2 * this.newbx;
                final float co = c - leftOffset2 * this.newcx;
                float oneoverc = 65536.0f / co;
                oldfu = ao * oneoverc;
                oldfv = bo * oneoverc;
                a += rightOffset2 * this.newax;
                b += rightOffset2 * this.newbx;
                c += rightOffset2 * this.newcx;
                oneoverc = 65536.0f / c;
                fu = a * oneoverc;
                fv = b * oneoverc;
                deltaU = (int)(fu - oldfu) >> linearInterpPower;
                deltaV = (int)(fv - oldfv) >> linearInterpPower;
                iu = (int)oldfu + (leftOffset - 1) * deltaU;
                iv = (int)oldfv + (leftOffset - 1) * deltaV;
            }
            else {
                final float preoneoverc = 65536.0f / c;
                fu = a * preoneoverc;
                fv = b * preoneoverc;
            }
            while (xstart < xend) {
                if (accurateMode) {
                    if (interpCounter == linearInterpLength) {
                        interpCounter = 0;
                    }
                    if (interpCounter == 0) {
                        a += this.newax;
                        b += this.newbx;
                        c += this.newcx;
                        final float oneoverc2 = 65536.0f / c;
                        oldfu = fu;
                        oldfv = fv;
                        fu = a * oneoverc2;
                        fv = b * oneoverc2;
                        iu = (int)oldfu;
                        iv = (int)oldfv;
                        deltaU = (int)(fu - oldfu) >> linearInterpPower;
                        deltaV = (int)(fv - oldfv) >> linearInterpPower;
                    }
                    else {
                        iu += deltaU;
                        iv += deltaV;
                    }
                    ++interpCounter;
                }
                try {
                    if (this.noDepthTest || iz <= this.m_zbuffer[xstart]) {
                        int al0;
                        if (this.m_bilinear) {
                            int ofs = (iv >> 16) * this.TEX_WIDTH + (iu >> 16);
                            final int iui = iu & 0xFFFF;
                            al0 = (this.m_texture[ofs] & 0xFF);
                            final int al2 = this.m_texture[ofs + 1] & 0xFF;
                            if (ofs < lastRowStart) {
                                ofs += this.TEX_WIDTH;
                            }
                            int al3 = this.m_texture[ofs] & 0xFF;
                            final int al4 = this.m_texture[ofs + 1] & 0xFF;
                            al0 += (al2 - al0) * iui >> 16;
                            al3 += (al4 - al3) * iui >> 16;
                            al0 += (al3 - al0) * (iv & 0xFFFF) >> 16;
                        }
                        else {
                            al0 = (this.m_texture[(iv >> 16) * this.TEX_WIDTH + (iu >> 16)] & 0xFF);
                        }
                        al0 = al0 * (ia >> 16) >> 8;
                        final int red = ir & 0xFF0000;
                        final int grn = ig >> 8 & 0xFF00;
                        final int blu = ib >> 16;
                        int bb = this.m_pixels[xstart];
                        final int br = bb & 0xFF0000;
                        final int bg = bb & 0xFF00;
                        bb &= 0xFF;
                        this.m_pixels[xstart] = (0xFF000000 | (br + ((red - br) * al0 >> 8) & 0xFF0000) | (bg + ((grn - bg) * al0 >> 8) & 0xFF00) | (bb + ((blu - bb) * al0 >> 8) & 0xFF));
                    }
                }
                catch (Exception ex) {}
                ++xpixel;
                if (!accurateMode) {
                    iu += this.iuadd;
                    iv += this.ivadd;
                }
                ir += this.iradd;
                ig += this.igadd;
                ib += this.ibadd;
                ia += this.iaadd;
                iz += this.izadd;
                ++xstart;
            }
            ++ypixel;
            ytop += this.SCREEN_WIDTH;
            this.xleft += leftadd;
            this.xrght += rghtadd;
            this.uleft += this.uleftadd;
            this.vleft += this.vleftadd;
            this.rleft += this.rleftadd;
            this.gleft += this.gleftadd;
            this.bleft += this.bleftadd;
            this.aleft += this.aleftadd;
            this.zleft += this.zleftadd;
        }
    }
    
    private void drawsegment_gouraud_texture24(final float leftadd, final float rghtadd, int ytop, int ybottom) {
        int ypixel = ytop;
        final int lastRowStart = this.m_texture.length - this.TEX_WIDTH - 2;
        boolean accurateMode = this.parent.hints[7];
        float screenx = 0.0f;
        float screeny = 0.0f;
        float screenz = 0.0f;
        float a = 0.0f;
        float b = 0.0f;
        float c = 0.0f;
        final int linearInterpPower = PTriangle.TEX_INTERP_POWER;
        final int linearInterpLength = 1 << linearInterpPower;
        if (accurateMode) {
            if (this.precomputeAccurateTexturing()) {
                this.newax *= linearInterpLength;
                this.newbx *= linearInterpLength;
                this.newcx *= linearInterpLength;
                screenz = this.nearPlaneDepth;
                this.firstSegment = false;
            }
            else {
                accurateMode = false;
            }
        }
        ytop *= this.SCREEN_WIDTH;
        ybottom *= this.SCREEN_WIDTH;
        final float iuf = this.iuadd;
        final float ivf = this.ivadd;
        final float irf = this.iradd;
        final float igf = this.igadd;
        final float ibf = this.ibadd;
        while (ytop < ybottom) {
            int xstart = (int)(this.xleft + 0.5f);
            if (xstart < 0) {
                xstart = 0;
            }
            int xpixel = xstart;
            int xend = (int)(this.xrght + 0.5f);
            if (xend > this.SCREEN_WIDTH) {
                xend = this.SCREEN_WIDTH;
            }
            final float xdiff = xstart + 0.5f - this.xleft;
            int iu = (int)(iuf * xdiff + this.uleft);
            int iv = (int)(ivf * xdiff + this.vleft);
            int ir = (int)(irf * xdiff + this.rleft);
            int ig = (int)(igf * xdiff + this.gleft);
            int ib = (int)(ibf * xdiff + this.bleft);
            float iz = this.izadd * xdiff + this.zleft;
            xstart += ytop;
            xend += ytop;
            if (accurateMode) {
                screenx = this.xmult * (xpixel + 0.5f - this.SCREEN_WIDTH / 2.0f);
                screeny = this.ymult * (ypixel + 0.5f - this.SCREEN_HEIGHT / 2.0f);
                a = screenx * this.ax + screeny * this.ay + screenz * this.az;
                b = screenx * this.bx + screeny * this.by + screenz * this.bz;
                c = screenx * this.cx + screeny * this.cy + screenz * this.cz;
            }
            final boolean goingIn = this.newcx > 0.0f != c > 0.0f;
            int interpCounter = 0;
            int deltaU = 0;
            int deltaV = 0;
            float fu = 0.0f;
            float fv = 0.0f;
            float oldfu = 0.0f;
            float oldfv = 0.0f;
            if (accurateMode && goingIn) {
                final int rightOffset = (xend - xstart - 1) % linearInterpLength;
                final int leftOffset = linearInterpLength - rightOffset;
                final float rightOffset2 = rightOffset / linearInterpLength;
                final float leftOffset2 = leftOffset / linearInterpLength;
                interpCounter = leftOffset;
                final float ao = a - leftOffset2 * this.newax;
                final float bo = b - leftOffset2 * this.newbx;
                final float co = c - leftOffset2 * this.newcx;
                float oneoverc = 65536.0f / co;
                oldfu = ao * oneoverc;
                oldfv = bo * oneoverc;
                a += rightOffset2 * this.newax;
                b += rightOffset2 * this.newbx;
                c += rightOffset2 * this.newcx;
                oneoverc = 65536.0f / c;
                fu = a * oneoverc;
                fv = b * oneoverc;
                deltaU = (int)(fu - oldfu) >> linearInterpPower;
                deltaV = (int)(fv - oldfv) >> linearInterpPower;
                iu = (int)oldfu + (leftOffset - 1) * deltaU;
                iv = (int)oldfv + (leftOffset - 1) * deltaV;
            }
            else {
                final float preoneoverc = 65536.0f / c;
                fu = a * preoneoverc;
                fv = b * preoneoverc;
            }
            while (xstart < xend) {
                if (accurateMode) {
                    if (interpCounter == linearInterpLength) {
                        interpCounter = 0;
                    }
                    if (interpCounter == 0) {
                        a += this.newax;
                        b += this.newbx;
                        c += this.newcx;
                        final float oneoverc2 = 65536.0f / c;
                        oldfu = fu;
                        oldfv = fv;
                        fu = a * oneoverc2;
                        fv = b * oneoverc2;
                        iu = (int)oldfu;
                        iv = (int)oldfv;
                        deltaU = (int)(fu - oldfu) >> linearInterpPower;
                        deltaV = (int)(fv - oldfv) >> linearInterpPower;
                    }
                    else {
                        iu += deltaU;
                        iv += deltaV;
                    }
                    ++interpCounter;
                }
                try {
                    if (this.noDepthTest || iz <= this.m_zbuffer[xstart]) {
                        this.m_zbuffer[xstart] = iz;
                        int red3;
                        int grn;
                        int blu;
                        if (this.m_bilinear) {
                            int ofs = (iv >> 16) * this.TEX_WIDTH + (iu >> 16);
                            final int iui = (iu & 0xFFFF) >> 9;
                            final int ivi = (iv & 0xFFFF) >> 9;
                            final int pix0 = this.m_texture[ofs];
                            final int pix2 = this.m_texture[ofs + 1];
                            if (ofs < lastRowStart) {
                                ofs += this.TEX_WIDTH;
                            }
                            final int pix3 = this.m_texture[ofs];
                            final int pix4 = this.m_texture[ofs + 1];
                            int red0 = pix0 & 0xFF0000;
                            int red2 = pix3 & 0xFF0000;
                            int up = red0 + (((pix2 & 0xFF0000) - red0) * iui >> 7);
                            int dn = red2 + (((pix4 & 0xFF0000) - red2) * iui >> 7);
                            red3 = up + ((dn - up) * ivi >> 7);
                            red0 = (pix0 & 0xFF00);
                            red2 = (pix3 & 0xFF00);
                            up = red0 + (((pix2 & 0xFF00) - red0) * iui >> 7);
                            dn = red2 + (((pix4 & 0xFF00) - red2) * iui >> 7);
                            grn = up + ((dn - up) * ivi >> 7);
                            red0 = (pix0 & 0xFF);
                            red2 = (pix3 & 0xFF);
                            up = red0 + (((pix2 & 0xFF) - red0) * iui >> 7);
                            dn = red2 + (((pix4 & 0xFF) - red2) * iui >> 7);
                            blu = up + ((dn - up) * ivi >> 7);
                        }
                        else {
                            blu = this.m_texture[(iv >> 16) * this.TEX_WIDTH + (iu >> 16)];
                            red3 = (blu & 0xFF0000);
                            grn = (blu & 0xFF00);
                            blu &= 0xFF;
                        }
                        final int r = ir >> 16;
                        final int g = ig >> 16;
                        final int bb2 = ib >> 16;
                        this.m_pixels[xstart] = (0xFF000000 | ((red3 * r & 0xFF000000) | (grn * g & 0xFF0000) | blu * bb2) >> 8);
                    }
                }
                catch (Exception ex) {}
                ++xpixel;
                if (!accurateMode) {
                    iu += this.iuadd;
                    iv += this.ivadd;
                }
                ir += this.iradd;
                ig += this.igadd;
                ib += this.ibadd;
                iz += this.izadd;
                ++xstart;
            }
            ++ypixel;
            ytop += this.SCREEN_WIDTH;
            this.xleft += leftadd;
            this.xrght += rghtadd;
            this.uleft += this.uleftadd;
            this.vleft += this.vleftadd;
            this.rleft += this.rleftadd;
            this.gleft += this.gleftadd;
            this.bleft += this.bleftadd;
            this.zleft += this.zleftadd;
        }
    }
    
    private void drawsegment_gouraud_texture24_alpha(final float leftadd, final float rghtadd, int ytop, int ybottom) {
        int ypixel = ytop;
        final int lastRowStart = this.m_texture.length - this.TEX_WIDTH - 2;
        boolean accurateMode = this.parent.hints[7];
        float screenx = 0.0f;
        float screeny = 0.0f;
        float screenz = 0.0f;
        float a = 0.0f;
        float b = 0.0f;
        float c = 0.0f;
        final int linearInterpPower = PTriangle.TEX_INTERP_POWER;
        final int linearInterpLength = 1 << linearInterpPower;
        if (accurateMode) {
            if (this.precomputeAccurateTexturing()) {
                this.newax *= linearInterpLength;
                this.newbx *= linearInterpLength;
                this.newcx *= linearInterpLength;
                screenz = this.nearPlaneDepth;
                this.firstSegment = false;
            }
            else {
                accurateMode = false;
            }
        }
        ytop *= this.SCREEN_WIDTH;
        ybottom *= this.SCREEN_WIDTH;
        final float iuf = this.iuadd;
        final float ivf = this.ivadd;
        final float irf = this.iradd;
        final float igf = this.igadd;
        final float ibf = this.ibadd;
        final float iaf = this.iaadd;
        while (ytop < ybottom) {
            int xstart = (int)(this.xleft + 0.5f);
            if (xstart < 0) {
                xstart = 0;
            }
            int xpixel = xstart;
            int xend = (int)(this.xrght + 0.5f);
            if (xend > this.SCREEN_WIDTH) {
                xend = this.SCREEN_WIDTH;
            }
            final float xdiff = xstart + 0.5f - this.xleft;
            int iu = (int)(iuf * xdiff + this.uleft);
            int iv = (int)(ivf * xdiff + this.vleft);
            int ir = (int)(irf * xdiff + this.rleft);
            int ig = (int)(igf * xdiff + this.gleft);
            int ib = (int)(ibf * xdiff + this.bleft);
            int ia = (int)(iaf * xdiff + this.aleft);
            float iz = this.izadd * xdiff + this.zleft;
            xstart += ytop;
            xend += ytop;
            if (accurateMode) {
                screenx = this.xmult * (xpixel + 0.5f - this.SCREEN_WIDTH / 2.0f);
                screeny = this.ymult * (ypixel + 0.5f - this.SCREEN_HEIGHT / 2.0f);
                a = screenx * this.ax + screeny * this.ay + screenz * this.az;
                b = screenx * this.bx + screeny * this.by + screenz * this.bz;
                c = screenx * this.cx + screeny * this.cy + screenz * this.cz;
            }
            final boolean goingIn = this.newcx > 0.0f != c > 0.0f;
            int interpCounter = 0;
            int deltaU = 0;
            int deltaV = 0;
            float fu = 0.0f;
            float fv = 0.0f;
            float oldfu = 0.0f;
            float oldfv = 0.0f;
            if (accurateMode && goingIn) {
                final int rightOffset = (xend - xstart - 1) % linearInterpLength;
                final int leftOffset = linearInterpLength - rightOffset;
                final float rightOffset2 = rightOffset / linearInterpLength;
                final float leftOffset2 = leftOffset / linearInterpLength;
                interpCounter = leftOffset;
                final float ao = a - leftOffset2 * this.newax;
                final float bo = b - leftOffset2 * this.newbx;
                final float co = c - leftOffset2 * this.newcx;
                float oneoverc = 65536.0f / co;
                oldfu = ao * oneoverc;
                oldfv = bo * oneoverc;
                a += rightOffset2 * this.newax;
                b += rightOffset2 * this.newbx;
                c += rightOffset2 * this.newcx;
                oneoverc = 65536.0f / c;
                fu = a * oneoverc;
                fv = b * oneoverc;
                deltaU = (int)(fu - oldfu) >> linearInterpPower;
                deltaV = (int)(fv - oldfv) >> linearInterpPower;
                iu = (int)oldfu + (leftOffset - 1) * deltaU;
                iv = (int)oldfv + (leftOffset - 1) * deltaV;
            }
            else {
                final float preoneoverc = 65536.0f / c;
                fu = a * preoneoverc;
                fv = b * preoneoverc;
            }
            while (xstart < xend) {
                if (accurateMode) {
                    if (interpCounter == linearInterpLength) {
                        interpCounter = 0;
                    }
                    if (interpCounter == 0) {
                        a += this.newax;
                        b += this.newbx;
                        c += this.newcx;
                        final float oneoverc2 = 65536.0f / c;
                        oldfu = fu;
                        oldfv = fv;
                        fu = a * oneoverc2;
                        fv = b * oneoverc2;
                        iu = (int)oldfu;
                        iv = (int)oldfv;
                        deltaU = (int)(fu - oldfu) >> linearInterpPower;
                        deltaV = (int)(fv - oldfv) >> linearInterpPower;
                    }
                    else {
                        iu += deltaU;
                        iv += deltaV;
                    }
                    ++interpCounter;
                }
                try {
                    if (this.noDepthTest || iz <= this.m_zbuffer[xstart]) {
                        final int al = ia >> 16;
                        int red3;
                        int grn;
                        int blu;
                        if (this.m_bilinear) {
                            int ofs = (iv >> 16) * this.TEX_WIDTH + (iu >> 16);
                            final int iui = (iu & 0xFFFF) >> 9;
                            final int ivi = (iv & 0xFFFF) >> 9;
                            final int pix0 = this.m_texture[ofs];
                            final int pix2 = this.m_texture[ofs + 1];
                            if (ofs < lastRowStart) {
                                ofs += this.TEX_WIDTH;
                            }
                            final int pix3 = this.m_texture[ofs];
                            final int pix4 = this.m_texture[ofs + 1];
                            int red0 = pix0 & 0xFF0000;
                            int red2 = pix3 & 0xFF0000;
                            int up = red0 + (((pix2 & 0xFF0000) - red0) * iui >> 7);
                            int dn = red2 + (((pix4 & 0xFF0000) - red2) * iui >> 7);
                            red3 = up + ((dn - up) * ivi >> 7) >> 16;
                            red0 = (pix0 & 0xFF00);
                            red2 = (pix3 & 0xFF00);
                            up = red0 + (((pix2 & 0xFF00) - red0) * iui >> 7);
                            dn = red2 + (((pix4 & 0xFF00) - red2) * iui >> 7);
                            grn = up + ((dn - up) * ivi >> 7) >> 8;
                            red0 = (pix0 & 0xFF);
                            red2 = (pix3 & 0xFF);
                            up = red0 + (((pix2 & 0xFF) - red0) * iui >> 7);
                            dn = red2 + (((pix4 & 0xFF) - red2) * iui >> 7);
                            blu = up + ((dn - up) * ivi >> 7);
                        }
                        else {
                            blu = this.m_texture[(iv >> 16) * this.TEX_WIDTH + (iu >> 16)];
                            red3 = (blu & 0xFF0000) >> 16;
                            grn = (blu & 0xFF00) >> 8;
                            blu &= 0xFF;
                        }
                        red3 = red3 * ir >>> 8;
                        grn = grn * ig >>> 16;
                        blu = blu * ib >>> 24;
                        int bb = this.m_pixels[xstart];
                        final int br = bb & 0xFF0000;
                        final int bg = bb & 0xFF00;
                        bb &= 0xFF;
                        this.m_pixels[xstart] = (0xFF000000 | (br + ((red3 - br) * al >> 8) & 0xFF0000) | (bg + ((grn - bg) * al >> 8) & 0xFF00) | (bb + ((blu - bb) * al >> 8) & 0xFF));
                    }
                }
                catch (Exception ex) {}
                ++xpixel;
                if (!accurateMode) {
                    iu += this.iuadd;
                    iv += this.ivadd;
                }
                ir += this.iradd;
                ig += this.igadd;
                ib += this.ibadd;
                ia += this.iaadd;
                iz += this.izadd;
                ++xstart;
            }
            ++ypixel;
            ytop += this.SCREEN_WIDTH;
            this.xleft += leftadd;
            this.xrght += rghtadd;
            this.uleft += this.uleftadd;
            this.vleft += this.vleftadd;
            this.rleft += this.rleftadd;
            this.gleft += this.gleftadd;
            this.bleft += this.bleftadd;
            this.aleft += this.aleftadd;
            this.zleft += this.zleftadd;
        }
    }
    
    private void drawsegment_gouraud_texture32(final float leftadd, final float rghtadd, int ytop, int ybottom) {
        int ypixel = ytop;
        final int lastRowStart = this.m_texture.length - this.TEX_WIDTH - 2;
        boolean accurateMode = this.parent.hints[7];
        float screenx = 0.0f;
        float screeny = 0.0f;
        float screenz = 0.0f;
        float a = 0.0f;
        float b = 0.0f;
        float c = 0.0f;
        final int linearInterpPower = PTriangle.TEX_INTERP_POWER;
        final int linearInterpLength = 1 << linearInterpPower;
        if (accurateMode) {
            if (this.precomputeAccurateTexturing()) {
                this.newax *= linearInterpLength;
                this.newbx *= linearInterpLength;
                this.newcx *= linearInterpLength;
                screenz = this.nearPlaneDepth;
                this.firstSegment = false;
            }
            else {
                accurateMode = false;
            }
        }
        ytop *= this.SCREEN_WIDTH;
        ybottom *= this.SCREEN_WIDTH;
        final float iuf = this.iuadd;
        final float ivf = this.ivadd;
        final float irf = this.iradd;
        final float igf = this.igadd;
        final float ibf = this.ibadd;
        while (ytop < ybottom) {
            int xstart = (int)(this.xleft + 0.5f);
            if (xstart < 0) {
                xstart = 0;
            }
            int xpixel = xstart;
            int xend = (int)(this.xrght + 0.5f);
            if (xend > this.SCREEN_WIDTH) {
                xend = this.SCREEN_WIDTH;
            }
            final float xdiff = xstart + 0.5f - this.xleft;
            int iu = (int)(iuf * xdiff + this.uleft);
            int iv = (int)(ivf * xdiff + this.vleft);
            int ir = (int)(irf * xdiff + this.rleft);
            int ig = (int)(igf * xdiff + this.gleft);
            int ib = (int)(ibf * xdiff + this.bleft);
            float iz = this.izadd * xdiff + this.zleft;
            xstart += ytop;
            xend += ytop;
            if (accurateMode) {
                screenx = this.xmult * (xpixel + 0.5f - this.SCREEN_WIDTH / 2.0f);
                screeny = this.ymult * (ypixel + 0.5f - this.SCREEN_HEIGHT / 2.0f);
                a = screenx * this.ax + screeny * this.ay + screenz * this.az;
                b = screenx * this.bx + screeny * this.by + screenz * this.bz;
                c = screenx * this.cx + screeny * this.cy + screenz * this.cz;
            }
            final boolean goingIn = this.newcx > 0.0f != c > 0.0f;
            int interpCounter = 0;
            int deltaU = 0;
            int deltaV = 0;
            float fu = 0.0f;
            float fv = 0.0f;
            float oldfu = 0.0f;
            float oldfv = 0.0f;
            if (accurateMode && goingIn) {
                final int rightOffset = (xend - xstart - 1) % linearInterpLength;
                final int leftOffset = linearInterpLength - rightOffset;
                final float rightOffset2 = rightOffset / linearInterpLength;
                final float leftOffset2 = leftOffset / linearInterpLength;
                interpCounter = leftOffset;
                final float ao = a - leftOffset2 * this.newax;
                final float bo = b - leftOffset2 * this.newbx;
                final float co = c - leftOffset2 * this.newcx;
                float oneoverc = 65536.0f / co;
                oldfu = ao * oneoverc;
                oldfv = bo * oneoverc;
                a += rightOffset2 * this.newax;
                b += rightOffset2 * this.newbx;
                c += rightOffset2 * this.newcx;
                oneoverc = 65536.0f / c;
                fu = a * oneoverc;
                fv = b * oneoverc;
                deltaU = (int)(fu - oldfu) >> linearInterpPower;
                deltaV = (int)(fv - oldfv) >> linearInterpPower;
                iu = (int)oldfu + (leftOffset - 1) * deltaU;
                iv = (int)oldfv + (leftOffset - 1) * deltaV;
            }
            else {
                final float preoneoverc = 65536.0f / c;
                fu = a * preoneoverc;
                fv = b * preoneoverc;
            }
            while (xstart < xend) {
                if (accurateMode) {
                    if (interpCounter == linearInterpLength) {
                        interpCounter = 0;
                    }
                    if (interpCounter == 0) {
                        a += this.newax;
                        b += this.newbx;
                        c += this.newcx;
                        final float oneoverc2 = 65536.0f / c;
                        oldfu = fu;
                        oldfv = fv;
                        fu = a * oneoverc2;
                        fv = b * oneoverc2;
                        iu = (int)oldfu;
                        iv = (int)oldfv;
                        deltaU = (int)(fu - oldfu) >> linearInterpPower;
                        deltaV = (int)(fv - oldfv) >> linearInterpPower;
                    }
                    else {
                        iu += deltaU;
                        iv += deltaV;
                    }
                    ++interpCounter;
                }
                try {
                    if (this.noDepthTest || iz <= this.m_zbuffer[xstart]) {
                        int red3;
                        int grn;
                        int blu;
                        int al;
                        if (this.m_bilinear) {
                            int ofs = (iv >> 16) * this.TEX_WIDTH + (iu >> 16);
                            final int iui = (iu & 0xFFFF) >> 9;
                            final int ivi = (iv & 0xFFFF) >> 9;
                            int pix0 = this.m_texture[ofs];
                            final int pix2 = this.m_texture[ofs + 1];
                            if (ofs < lastRowStart) {
                                ofs += this.TEX_WIDTH;
                            }
                            int pix3 = this.m_texture[ofs];
                            final int pix4 = this.m_texture[ofs + 1];
                            int red0 = pix0 & 0xFF0000;
                            int red2 = pix3 & 0xFF0000;
                            int up = red0 + (((pix2 & 0xFF0000) - red0) * iui >> 7);
                            int dn = red2 + (((pix4 & 0xFF0000) - red2) * iui >> 7);
                            red3 = up + ((dn - up) * ivi >> 7) >> 16;
                            red0 = (pix0 & 0xFF00);
                            red2 = (pix3 & 0xFF00);
                            up = red0 + (((pix2 & 0xFF00) - red0) * iui >> 7);
                            dn = red2 + (((pix4 & 0xFF00) - red2) * iui >> 7);
                            grn = up + ((dn - up) * ivi >> 7) >> 8;
                            red0 = (pix0 & 0xFF);
                            red2 = (pix3 & 0xFF);
                            up = red0 + (((pix2 & 0xFF) - red0) * iui >> 7);
                            dn = red2 + (((pix4 & 0xFF) - red2) * iui >> 7);
                            blu = up + ((dn - up) * ivi >> 7);
                            pix0 >>>= 24;
                            pix3 >>>= 24;
                            up = pix0 + (((pix2 >>> 24) - pix0) * iui >> 7);
                            dn = pix3 + (((pix4 >>> 24) - pix3) * iui >> 7);
                            al = up + ((dn - up) * ivi >> 7);
                        }
                        else {
                            blu = this.m_texture[(iv >> 16) * this.TEX_WIDTH + (iu >> 16)];
                            al = blu >>> 24;
                            red3 = (blu & 0xFF0000) >> 16;
                            grn = (blu & 0xFF00) >> 8;
                            blu &= 0xFF;
                        }
                        red3 = red3 * ir >>> 8;
                        grn = grn * ig >>> 16;
                        blu = blu * ib >>> 24;
                        int bb = this.m_pixels[xstart];
                        final int br = bb & 0xFF0000;
                        final int bg = bb & 0xFF00;
                        bb &= 0xFF;
                        this.m_pixels[xstart] = (0xFF000000 | (br + ((red3 - br) * al >> 8) & 0xFF0000) | (bg + ((grn - bg) * al >> 8) & 0xFF00) | (bb + ((blu - bb) * al >> 8) & 0xFF));
                    }
                }
                catch (Exception ex) {}
                ++xpixel;
                if (!accurateMode) {
                    iu += this.iuadd;
                    iv += this.ivadd;
                }
                ir += this.iradd;
                ig += this.igadd;
                ib += this.ibadd;
                iz += this.izadd;
                ++xstart;
            }
            ++ypixel;
            ytop += this.SCREEN_WIDTH;
            this.xleft += leftadd;
            this.xrght += rghtadd;
            this.uleft += this.uleftadd;
            this.vleft += this.vleftadd;
            this.rleft += this.rleftadd;
            this.gleft += this.gleftadd;
            this.bleft += this.bleftadd;
            this.zleft += this.zleftadd;
        }
    }
    
    private void drawsegment_gouraud_texture32_alpha(final float leftadd, final float rghtadd, int ytop, int ybottom) {
        int ypixel = ytop;
        final int lastRowStart = this.m_texture.length - this.TEX_WIDTH - 2;
        boolean accurateMode = this.parent.hints[7];
        float screenx = 0.0f;
        float screeny = 0.0f;
        float screenz = 0.0f;
        float a = 0.0f;
        float b = 0.0f;
        float c = 0.0f;
        final int linearInterpPower = PTriangle.TEX_INTERP_POWER;
        final int linearInterpLength = 1 << linearInterpPower;
        if (accurateMode) {
            if (this.precomputeAccurateTexturing()) {
                this.newax *= linearInterpLength;
                this.newbx *= linearInterpLength;
                this.newcx *= linearInterpLength;
                screenz = this.nearPlaneDepth;
                this.firstSegment = false;
            }
            else {
                accurateMode = false;
            }
        }
        ytop *= this.SCREEN_WIDTH;
        ybottom *= this.SCREEN_WIDTH;
        final float iuf = this.iuadd;
        final float ivf = this.ivadd;
        final float irf = this.iradd;
        final float igf = this.igadd;
        final float ibf = this.ibadd;
        final float iaf = this.iaadd;
        while (ytop < ybottom) {
            int xstart = (int)(this.xleft + 0.5f);
            if (xstart < 0) {
                xstart = 0;
            }
            int xpixel = xstart;
            int xend = (int)(this.xrght + 0.5f);
            if (xend > this.SCREEN_WIDTH) {
                xend = this.SCREEN_WIDTH;
            }
            final float xdiff = xstart + 0.5f - this.xleft;
            int iu = (int)(iuf * xdiff + this.uleft);
            int iv = (int)(ivf * xdiff + this.vleft);
            int ir = (int)(irf * xdiff + this.rleft);
            int ig = (int)(igf * xdiff + this.gleft);
            int ib = (int)(ibf * xdiff + this.bleft);
            int ia = (int)(iaf * xdiff + this.aleft);
            float iz = this.izadd * xdiff + this.zleft;
            xstart += ytop;
            xend += ytop;
            if (accurateMode) {
                screenx = this.xmult * (xpixel + 0.5f - this.SCREEN_WIDTH / 2.0f);
                screeny = this.ymult * (ypixel + 0.5f - this.SCREEN_HEIGHT / 2.0f);
                a = screenx * this.ax + screeny * this.ay + screenz * this.az;
                b = screenx * this.bx + screeny * this.by + screenz * this.bz;
                c = screenx * this.cx + screeny * this.cy + screenz * this.cz;
            }
            final boolean goingIn = this.newcx > 0.0f != c > 0.0f;
            int interpCounter = 0;
            int deltaU = 0;
            int deltaV = 0;
            float fu = 0.0f;
            float fv = 0.0f;
            float oldfu = 0.0f;
            float oldfv = 0.0f;
            if (accurateMode && goingIn) {
                final int rightOffset = (xend - xstart - 1) % linearInterpLength;
                final int leftOffset = linearInterpLength - rightOffset;
                final float rightOffset2 = rightOffset / linearInterpLength;
                final float leftOffset2 = leftOffset / linearInterpLength;
                interpCounter = leftOffset;
                final float ao = a - leftOffset2 * this.newax;
                final float bo = b - leftOffset2 * this.newbx;
                final float co = c - leftOffset2 * this.newcx;
                float oneoverc = 65536.0f / co;
                oldfu = ao * oneoverc;
                oldfv = bo * oneoverc;
                a += rightOffset2 * this.newax;
                b += rightOffset2 * this.newbx;
                c += rightOffset2 * this.newcx;
                oneoverc = 65536.0f / c;
                fu = a * oneoverc;
                fv = b * oneoverc;
                deltaU = (int)(fu - oldfu) >> linearInterpPower;
                deltaV = (int)(fv - oldfv) >> linearInterpPower;
                iu = (int)oldfu + (leftOffset - 1) * deltaU;
                iv = (int)oldfv + (leftOffset - 1) * deltaV;
            }
            else {
                final float preoneoverc = 65536.0f / c;
                fu = a * preoneoverc;
                fv = b * preoneoverc;
            }
            while (xstart < xend) {
                if (accurateMode) {
                    if (interpCounter == linearInterpLength) {
                        interpCounter = 0;
                    }
                    if (interpCounter == 0) {
                        a += this.newax;
                        b += this.newbx;
                        c += this.newcx;
                        final float oneoverc2 = 65536.0f / c;
                        oldfu = fu;
                        oldfv = fv;
                        fu = a * oneoverc2;
                        fv = b * oneoverc2;
                        iu = (int)oldfu;
                        iv = (int)oldfv;
                        deltaU = (int)(fu - oldfu) >> linearInterpPower;
                        deltaV = (int)(fv - oldfv) >> linearInterpPower;
                    }
                    else {
                        iu += deltaU;
                        iv += deltaV;
                    }
                    ++interpCounter;
                }
                try {
                    if (this.noDepthTest || iz <= this.m_zbuffer[xstart]) {
                        int al = ia >> 16;
                        int red3;
                        int grn;
                        int blu;
                        if (this.m_bilinear) {
                            int ofs = (iv >> 16) * this.TEX_WIDTH + (iu >> 16);
                            final int iui = (iu & 0xFFFF) >> 9;
                            final int ivi = (iv & 0xFFFF) >> 9;
                            int pix0 = this.m_texture[ofs];
                            final int pix2 = this.m_texture[ofs + 1];
                            if (ofs < lastRowStart) {
                                ofs += this.TEX_WIDTH;
                            }
                            int pix3 = this.m_texture[ofs];
                            final int pix4 = this.m_texture[ofs + 1];
                            int red0 = pix0 & 0xFF0000;
                            int red2 = pix3 & 0xFF0000;
                            int up = red0 + (((pix2 & 0xFF0000) - red0) * iui >> 7);
                            int dn = red2 + (((pix4 & 0xFF0000) - red2) * iui >> 7);
                            red3 = up + ((dn - up) * ivi >> 7) >> 16;
                            red0 = (pix0 & 0xFF00);
                            red2 = (pix3 & 0xFF00);
                            up = red0 + (((pix2 & 0xFF00) - red0) * iui >> 7);
                            dn = red2 + (((pix4 & 0xFF00) - red2) * iui >> 7);
                            grn = up + ((dn - up) * ivi >> 7) >> 8;
                            red0 = (pix0 & 0xFF);
                            red2 = (pix3 & 0xFF);
                            up = red0 + (((pix2 & 0xFF) - red0) * iui >> 7);
                            dn = red2 + (((pix4 & 0xFF) - red2) * iui >> 7);
                            blu = up + ((dn - up) * ivi >> 7);
                            pix0 >>>= 24;
                            pix3 >>>= 24;
                            up = pix0 + (((pix2 >>> 24) - pix0) * iui >> 7);
                            dn = pix3 + (((pix4 >>> 24) - pix3) * iui >> 7);
                            al = al * (up + ((dn - up) * ivi >> 7)) >> 8;
                        }
                        else {
                            blu = this.m_texture[(iv >> 16) * this.TEX_WIDTH + (iu >> 16)];
                            al = al * (blu >>> 24) >> 8;
                            red3 = (blu & 0xFF0000) >> 16;
                            grn = (blu & 0xFF00) >> 8;
                            blu &= 0xFF;
                        }
                        red3 = red3 * ir >>> 8;
                        grn = grn * ig >>> 16;
                        blu = blu * ib >>> 24;
                        int bb = this.m_pixels[xstart];
                        final int br = bb & 0xFF0000;
                        final int bg = bb & 0xFF00;
                        bb &= 0xFF;
                        this.m_pixels[xstart] = (0xFF000000 | (br + ((red3 - br) * al >> 8) & 0xFF0000) | (bg + ((grn - bg) * al >> 8) & 0xFF00) | (bb + ((blu - bb) * al >> 8) & 0xFF));
                    }
                }
                catch (Exception ex) {}
                ++xpixel;
                if (!accurateMode) {
                    iu += this.iuadd;
                    iv += this.ivadd;
                }
                ir += this.iradd;
                ig += this.igadd;
                ib += this.ibadd;
                ia += this.iaadd;
                iz += this.izadd;
                ++xstart;
            }
            ++ypixel;
            ytop += this.SCREEN_WIDTH;
            this.xleft += leftadd;
            this.xrght += rghtadd;
            this.uleft += this.uleftadd;
            this.vleft += this.vleftadd;
            this.rleft += this.rleftadd;
            this.gleft += this.gleftadd;
            this.bleft += this.bleftadd;
            this.aleft += this.aleftadd;
            this.zleft += this.zleftadd;
        }
    }
}
