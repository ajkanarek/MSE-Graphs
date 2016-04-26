/*      */ package processing.core;
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PTriangle
/*      */   implements PConstants
/*      */ {
/*      */   static final float PIXEL_CENTER = 0.5F;
/*      */   
/*      */ 
/*      */   static final int R_GOURAUD = 1;
/*      */   
/*      */ 
/*      */   static final int R_TEXTURE8 = 2;
/*      */   
/*      */ 
/*      */   static final int R_TEXTURE24 = 4;
/*      */   
/*      */ 
/*      */   static final int R_TEXTURE32 = 8;
/*      */   
/*      */ 
/*      */   static final int R_ALPHA = 16;
/*      */   
/*      */ 
/*      */   private int[] m_pixels;
/*      */   
/*      */ 
/*      */   private int[] m_texture;
/*      */   
/*      */ 
/*      */   private float[] m_zbuffer;
/*      */   
/*      */ 
/*      */   private int SCREEN_WIDTH;
/*      */   
/*      */ 
/*      */   private int SCREEN_HEIGHT;
/*      */   
/*      */ 
/*      */   private int TEX_WIDTH;
/*      */   
/*      */ 
/*      */   private int TEX_HEIGHT;
/*      */   
/*      */ 
/*      */   private float F_TEX_WIDTH;
/*      */   
/*      */ 
/*      */   private float F_TEX_HEIGHT;
/*      */   
/*      */ 
/*      */   public boolean INTERPOLATE_UV;
/*      */   
/*      */ 
/*      */   public boolean INTERPOLATE_RGB;
/*      */   
/*      */ 
/*      */   public boolean INTERPOLATE_ALPHA;
/*      */   
/*      */ 
/*      */   private static final int DEFAULT_INTERP_POWER = 3;
/*      */   
/*   64 */   private static int TEX_INTERP_POWER = 3;
/*      */   
/*      */   private float[] x_array;
/*      */   
/*      */   private float[] y_array;
/*      */   
/*      */   private float[] z_array;
/*      */   
/*      */   private float[] camX;
/*      */   
/*      */   private float[] camY;
/*      */   
/*      */   private float[] camZ;
/*      */   
/*      */   private float[] u_array;
/*      */   
/*      */   private float[] v_array;
/*      */   
/*      */   private float[] r_array;
/*      */   
/*      */   private float[] g_array;
/*      */   
/*      */   private float[] b_array;
/*      */   
/*      */   private float[] a_array;
/*      */   
/*      */   private int o0;
/*      */   
/*      */   private int o1;
/*      */   
/*      */   private int o2;
/*      */   
/*      */   private float r0;
/*      */   
/*      */   private float r1;
/*      */   
/*      */   private float r2;
/*      */   
/*      */   private float g0;
/*      */   
/*      */   private float g1;
/*      */   
/*      */   private float g2;
/*      */   
/*      */   private float b0;
/*      */   
/*      */   private float b1;
/*      */   
/*      */   private float b2;
/*      */   
/*      */   private float a0;
/*      */   
/*      */   private float a1;
/*      */   
/*      */   private float a2;
/*      */   
/*      */   private float u0;
/*      */   
/*      */   private float u1;
/*      */   
/*      */   private float u2;
/*      */   
/*      */   private float v0;
/*      */   
/*      */   private float v1;
/*      */   
/*      */   private float v2;
/*      */   
/*      */   private float dx2;
/*      */   
/*      */   private float dy0;
/*      */   
/*      */   private float dy1;
/*      */   
/*      */   private float dy2;
/*      */   
/*      */   private float dz0;
/*      */   
/*      */   private float dz2;
/*      */   
/*      */   private float du0;
/*      */   
/*      */   private float du2;
/*      */   
/*      */   private float dv0;
/*      */   
/*      */   private float dv2;
/*      */   
/*      */   private float dr0;
/*      */   
/*      */   private float dr2;
/*      */   
/*      */   private float dg0;
/*      */   
/*      */   private float dg2;
/*      */   
/*      */   private float db0;
/*      */   
/*      */   private float db2;
/*      */   
/*      */   private float da0;
/*      */   
/*      */   private float da2;
/*      */   
/*      */   private float uleft;
/*      */   
/*      */   private float vleft;
/*      */   
/*      */   private float uleftadd;
/*      */   
/*      */   private float vleftadd;
/*      */   
/*      */   private float xleft;
/*      */   
/*      */   private float xrght;
/*      */   
/*      */   private float xadd1;
/*      */   
/*      */   private float xadd2;
/*      */   
/*      */   private float zleft;
/*      */   
/*      */   private float zleftadd;
/*      */   private float rleft;
/*      */   private float gleft;
/*      */   private float bleft;
/*      */   private float aleft;
/*      */   private float rleftadd;
/*      */   private float gleftadd;
/*      */   private float bleftadd;
/*      */   private float aleftadd;
/*      */   private float dta;
/*      */   private float temp;
/*      */   private float width;
/*      */   private int iuadd;
/*      */   private int ivadd;
/*      */   private int iradd;
/*      */   private int igadd;
/*      */   private int ibadd;
/*      */   private int iaadd;
/*      */   private float izadd;
/*      */   private int m_fill;
/*      */   public int m_drawFlags;
/*      */   private PGraphics3D parent;
/*      */   private boolean noDepthTest;
/*      */   private boolean m_culling;
/*      */   private boolean m_singleRight;
/*  211 */   private boolean m_bilinear = true;
/*      */   private float ax;
/*      */   private float ay;
/*      */   private float az;
/*      */   private float bx;
/*      */   private float by;
/*      */   private float bz;
/*      */   private float cx;
/*      */   private float cy;
/*      */   private float cz;
/*      */   private float nearPlaneWidth;
/*      */   private float nearPlaneHeight;
/*      */   private float nearPlaneDepth;
/*      */   private float xmult;
/*      */   private float ymult;
/*      */   private float newax;
/*      */   private float newbx;
/*      */   private float newcx;
/*      */   private boolean firstSegment;
/*      */   
/*      */   public PTriangle(PGraphics3D g) {
/*  232 */     this.x_array = new float[3];
/*  233 */     this.y_array = new float[3];
/*  234 */     this.z_array = new float[3];
/*  235 */     this.u_array = new float[3];
/*  236 */     this.v_array = new float[3];
/*  237 */     this.r_array = new float[3];
/*  238 */     this.g_array = new float[3];
/*  239 */     this.b_array = new float[3];
/*  240 */     this.a_array = new float[3];
/*      */     
/*  242 */     this.camX = new float[3];
/*  243 */     this.camY = new float[3];
/*  244 */     this.camZ = new float[3];
/*      */     
/*  246 */     this.parent = g;
/*  247 */     reset();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void reset()
/*      */   {
/*  257 */     this.SCREEN_WIDTH = this.parent.width;
/*  258 */     this.SCREEN_HEIGHT = this.parent.height;
/*      */     
/*      */ 
/*      */ 
/*  262 */     this.m_pixels = this.parent.pixels;
/*      */     
/*  264 */     this.m_zbuffer = this.parent.zbuffer;
/*      */     
/*  266 */     this.noDepthTest = this.parent.hints[4];
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  271 */     this.INTERPOLATE_UV = false;
/*  272 */     this.INTERPOLATE_RGB = false;
/*  273 */     this.INTERPOLATE_ALPHA = false;
/*      */     
/*  275 */     this.m_texture = null;
/*  276 */     this.m_drawFlags = 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setCulling(boolean tf)
/*      */   {
/*  284 */     this.m_culling = tf;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setVertices(float x0, float y0, float z0, float x1, float y1, float z1, float x2, float y2, float z2)
/*      */   {
/*  294 */     this.x_array[0] = x0;
/*  295 */     this.x_array[1] = x1;
/*  296 */     this.x_array[2] = x2;
/*      */     
/*  298 */     this.y_array[0] = y0;
/*  299 */     this.y_array[1] = y1;
/*  300 */     this.y_array[2] = y2;
/*      */     
/*  302 */     this.z_array[0] = z0;
/*  303 */     this.z_array[1] = z1;
/*  304 */     this.z_array[2] = z2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setCamVertices(float x0, float y0, float z0, float x1, float y1, float z1, float x2, float y2, float z2)
/*      */   {
/*  317 */     this.camX[0] = x0;
/*  318 */     this.camX[1] = x1;
/*  319 */     this.camX[2] = x2;
/*      */     
/*  321 */     this.camY[0] = y0;
/*  322 */     this.camY[1] = y1;
/*  323 */     this.camY[2] = y2;
/*      */     
/*  325 */     this.camZ[0] = z0;
/*  326 */     this.camZ[1] = z1;
/*  327 */     this.camZ[2] = z2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setUV(float u0, float v0, float u1, float v1, float u2, float v2)
/*      */   {
/*  338 */     this.u_array[0] = ((u0 * this.F_TEX_WIDTH + 0.5F) * 65536.0F);
/*  339 */     this.u_array[1] = ((u1 * this.F_TEX_WIDTH + 0.5F) * 65536.0F);
/*  340 */     this.u_array[2] = ((u2 * this.F_TEX_WIDTH + 0.5F) * 65536.0F);
/*  341 */     this.v_array[0] = ((v0 * this.F_TEX_HEIGHT + 0.5F) * 65536.0F);
/*  342 */     this.v_array[1] = ((v1 * this.F_TEX_HEIGHT + 0.5F) * 65536.0F);
/*  343 */     this.v_array[2] = ((v2 * this.F_TEX_HEIGHT + 0.5F) * 65536.0F);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setIntensities(float r0, float g0, float b0, float a0, float r1, float g1, float b1, float a1, float r2, float g2, float b2, float a2)
/*      */   {
/*  354 */     if ((a0 != 1.0F) || (a1 != 1.0F) || (a2 != 1.0F)) {
/*  355 */       this.INTERPOLATE_ALPHA = true;
/*  356 */       this.a_array[0] = ((a0 * 253.0F + 1.0F) * 65536.0F);
/*  357 */       this.a_array[1] = ((a1 * 253.0F + 1.0F) * 65536.0F);
/*  358 */       this.a_array[2] = ((a2 * 253.0F + 1.0F) * 65536.0F);
/*  359 */       this.m_drawFlags |= 0x10;
/*      */     } else {
/*  361 */       this.INTERPOLATE_ALPHA = false;
/*  362 */       this.m_drawFlags &= 0xFFFFFFEF;
/*      */     }
/*      */     
/*      */ 
/*  366 */     if ((r0 != r1) || (r1 != r2)) {
/*  367 */       this.INTERPOLATE_RGB = true;
/*  368 */       this.m_drawFlags |= 0x1;
/*  369 */     } else if ((g0 != g1) || (g1 != g2)) {
/*  370 */       this.INTERPOLATE_RGB = true;
/*  371 */       this.m_drawFlags |= 0x1;
/*  372 */     } else if ((b0 != b1) || (b1 != b2)) {
/*  373 */       this.INTERPOLATE_RGB = true;
/*  374 */       this.m_drawFlags |= 0x1;
/*      */     }
/*      */     else {
/*  377 */       this.m_drawFlags &= 0xFFFFFFFE;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  382 */     this.r_array[0] = ((r0 * 253.0F + 1.0F) * 65536.0F);
/*  383 */     this.r_array[1] = ((r1 * 253.0F + 1.0F) * 65536.0F);
/*  384 */     this.r_array[2] = ((r2 * 253.0F + 1.0F) * 65536.0F);
/*      */     
/*  386 */     this.g_array[0] = ((g0 * 253.0F + 1.0F) * 65536.0F);
/*  387 */     this.g_array[1] = ((g1 * 253.0F + 1.0F) * 65536.0F);
/*  388 */     this.g_array[2] = ((g2 * 253.0F + 1.0F) * 65536.0F);
/*      */     
/*  390 */     this.b_array[0] = ((b0 * 253.0F + 1.0F) * 65536.0F);
/*  391 */     this.b_array[1] = ((b1 * 253.0F + 1.0F) * 65536.0F);
/*  392 */     this.b_array[2] = ((b2 * 253.0F + 1.0F) * 65536.0F);
/*      */     
/*      */ 
/*  395 */     this.m_fill = 
/*  396 */       (0xFF000000 | (int)(255.0F * r0) << 16 | (int)(255.0F * g0) << 8 | (int)(255.0F * b0));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setTexture(PImage image)
/*      */   {
/*  405 */     this.m_texture = image.pixels;
/*  406 */     this.TEX_WIDTH = image.width;
/*  407 */     this.TEX_HEIGHT = image.height;
/*  408 */     this.F_TEX_WIDTH = (this.TEX_WIDTH - 1);
/*  409 */     this.F_TEX_HEIGHT = (this.TEX_HEIGHT - 1);
/*  410 */     this.INTERPOLATE_UV = true;
/*      */     
/*  412 */     if (image.format == 2) {
/*  413 */       this.m_drawFlags |= 0x8;
/*  414 */     } else if (image.format == 1) {
/*  415 */       this.m_drawFlags |= 0x4;
/*  416 */     } else if (image.format == 4) {
/*  417 */       this.m_drawFlags |= 0x2;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setUV(float[] u, float[] v)
/*      */   {
/*  426 */     if (this.m_bilinear)
/*      */     {
/*  428 */       this.u_array[0] = (u[0] * this.F_TEX_WIDTH * 65500.0F);
/*  429 */       this.u_array[1] = (u[1] * this.F_TEX_WIDTH * 65500.0F);
/*  430 */       this.u_array[2] = (u[2] * this.F_TEX_WIDTH * 65500.0F);
/*  431 */       this.v_array[0] = (v[0] * this.F_TEX_HEIGHT * 65500.0F);
/*  432 */       this.v_array[1] = (v[1] * this.F_TEX_HEIGHT * 65500.0F);
/*  433 */       this.v_array[2] = (v[2] * this.F_TEX_HEIGHT * 65500.0F);
/*      */     }
/*      */     else {
/*  436 */       this.u_array[0] = (u[0] * this.TEX_WIDTH * 65500.0F);
/*  437 */       this.u_array[1] = (u[1] * this.TEX_WIDTH * 65500.0F);
/*  438 */       this.u_array[2] = (u[2] * this.TEX_WIDTH * 65500.0F);
/*  439 */       this.v_array[0] = (v[0] * this.TEX_HEIGHT * 65500.0F);
/*  440 */       this.v_array[1] = (v[1] * this.TEX_HEIGHT * 65500.0F);
/*  441 */       this.v_array[2] = (v[2] * this.TEX_HEIGHT * 65500.0F);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void render()
/*      */   {
/*  458 */     float y0 = this.y_array[0];
/*  459 */     float y1 = this.y_array[1];
/*  460 */     float y2 = this.y_array[2];
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  466 */     this.firstSegment = true;
/*      */     
/*      */ 
/*  469 */     if (this.m_culling) {
/*  470 */       float x0 = this.x_array[0];
/*  471 */       if ((this.x_array[2] - x0) * (y1 - y0) < (this.x_array[1] - x0) * (y2 - y0)) {
/*  472 */         return;
/*      */       }
/*      */     }
/*      */     
/*  476 */     if (y0 < y1) {
/*  477 */       if (y2 < y1) {
/*  478 */         if (y2 < y0) {
/*  479 */           this.o0 = 2;
/*  480 */           this.o1 = 0;
/*  481 */           this.o2 = 1;
/*      */         } else {
/*  483 */           this.o0 = 0;
/*  484 */           this.o1 = 2;
/*  485 */           this.o2 = 1;
/*      */         }
/*      */       } else {
/*  488 */         this.o0 = 0;
/*  489 */         this.o1 = 1;
/*  490 */         this.o2 = 2;
/*      */       }
/*      */     }
/*  493 */     else if (y2 > y1) {
/*  494 */       if (y2 < y0) {
/*  495 */         this.o0 = 1;
/*  496 */         this.o1 = 2;
/*  497 */         this.o2 = 0;
/*      */       } else {
/*  499 */         this.o0 = 1;
/*  500 */         this.o1 = 0;
/*  501 */         this.o2 = 2;
/*      */       }
/*      */     } else {
/*  504 */       this.o0 = 2;
/*  505 */       this.o1 = 1;
/*  506 */       this.o2 = 0;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  516 */     y0 = this.y_array[this.o0];
/*  517 */     int yi0 = (int)(y0 + 0.5F);
/*  518 */     if (yi0 > this.SCREEN_HEIGHT)
/*  519 */       return;
/*  520 */     if (yi0 < 0) {
/*  521 */       yi0 = 0;
/*      */     }
/*      */     
/*  524 */     y2 = this.y_array[this.o2];
/*  525 */     int yi2 = (int)(y2 + 0.5F);
/*  526 */     if (yi2 < 0)
/*  527 */       return;
/*  528 */     if (yi2 > this.SCREEN_HEIGHT) {
/*  529 */       yi2 = this.SCREEN_HEIGHT;
/*      */     }
/*      */     
/*      */ 
/*  533 */     if (yi2 > yi0) {
/*  534 */       float x0 = this.x_array[this.o0];
/*  535 */       float x1 = this.x_array[this.o1];
/*  536 */       float x2 = this.x_array[this.o2];
/*      */       
/*      */ 
/*  539 */       y1 = this.y_array[this.o1];
/*  540 */       int yi1 = (int)(y1 + 0.5F);
/*  541 */       if (yi1 < 0)
/*  542 */         yi1 = 0;
/*  543 */       if (yi1 > this.SCREEN_HEIGHT) {
/*  544 */         yi1 = this.SCREEN_HEIGHT;
/*      */       }
/*      */       
/*  547 */       this.dx2 = (x2 - x0);
/*  548 */       this.dy0 = (y1 - y0);
/*  549 */       this.dy2 = (y2 - y0);
/*  550 */       this.xadd2 = (this.dx2 / this.dy2);
/*  551 */       this.temp = (this.dy0 / this.dy2);
/*  552 */       this.width = (this.temp * this.dx2 + x0 - x1);
/*      */       
/*      */ 
/*  555 */       if (this.INTERPOLATE_ALPHA) {
/*  556 */         this.a0 = this.a_array[this.o0];
/*  557 */         this.a1 = this.a_array[this.o1];
/*  558 */         this.a2 = this.a_array[this.o2];
/*  559 */         this.da0 = (this.a1 - this.a0);
/*  560 */         this.da2 = (this.a2 - this.a0);
/*  561 */         this.iaadd = ((int)((this.temp * this.da2 - this.da0) / this.width));
/*      */       }
/*      */       
/*      */ 
/*  565 */       if (this.INTERPOLATE_RGB) {
/*  566 */         this.r0 = this.r_array[this.o0];
/*  567 */         this.r1 = this.r_array[this.o1];
/*  568 */         this.r2 = this.r_array[this.o2];
/*      */         
/*  570 */         this.g0 = this.g_array[this.o0];
/*  571 */         this.g1 = this.g_array[this.o1];
/*  572 */         this.g2 = this.g_array[this.o2];
/*      */         
/*  574 */         this.b0 = this.b_array[this.o0];
/*  575 */         this.b1 = this.b_array[this.o1];
/*  576 */         this.b2 = this.b_array[this.o2];
/*      */         
/*  578 */         this.dr0 = (this.r1 - this.r0);
/*  579 */         this.dg0 = (this.g1 - this.g0);
/*  580 */         this.db0 = (this.b1 - this.b0);
/*      */         
/*  582 */         this.dr2 = (this.r2 - this.r0);
/*  583 */         this.dg2 = (this.g2 - this.g0);
/*  584 */         this.db2 = (this.b2 - this.b0);
/*      */         
/*  586 */         this.iradd = ((int)((this.temp * this.dr2 - this.dr0) / this.width));
/*  587 */         this.igadd = ((int)((this.temp * this.dg2 - this.dg0) / this.width));
/*  588 */         this.ibadd = ((int)((this.temp * this.db2 - this.db0) / this.width));
/*      */       }
/*      */       
/*      */ 
/*  592 */       if (this.INTERPOLATE_UV) {
/*  593 */         this.u0 = this.u_array[this.o0];
/*  594 */         this.u1 = this.u_array[this.o1];
/*  595 */         this.u2 = this.u_array[this.o2];
/*  596 */         this.v0 = this.v_array[this.o0];
/*  597 */         this.v1 = this.v_array[this.o1];
/*  598 */         this.v2 = this.v_array[this.o2];
/*  599 */         this.du0 = (this.u1 - this.u0);
/*  600 */         this.dv0 = (this.v1 - this.v0);
/*  601 */         this.du2 = (this.u2 - this.u0);
/*  602 */         this.dv2 = (this.v2 - this.v0);
/*  603 */         this.iuadd = ((int)((this.temp * this.du2 - this.du0) / this.width));
/*  604 */         this.ivadd = ((int)((this.temp * this.dv2 - this.dv0) / this.width));
/*      */       }
/*      */       
/*  607 */       float z0 = this.z_array[this.o0];
/*  608 */       float z1 = this.z_array[this.o1];
/*  609 */       float z2 = this.z_array[this.o2];
/*  610 */       this.dz0 = (z1 - z0);
/*  611 */       this.dz2 = (z2 - z0);
/*  612 */       this.izadd = ((this.temp * this.dz2 - this.dz0) / this.width);
/*      */       
/*      */ 
/*  615 */       if (yi1 > yi0) {
/*  616 */         this.dta = (yi0 + 0.5F - y0);
/*  617 */         this.xadd1 = ((x1 - x0) / this.dy0);
/*      */         
/*      */ 
/*      */ 
/*  621 */         if (this.xadd2 > this.xadd1) {
/*  622 */           this.xleft = (x0 + this.dta * this.xadd1);
/*  623 */           this.xrght = (x0 + this.dta * this.xadd2);
/*  624 */           this.zleftadd = (this.dz0 / this.dy0);
/*  625 */           this.zleft = (this.dta * this.zleftadd + z0);
/*      */           
/*  627 */           if (this.INTERPOLATE_UV) {
/*  628 */             this.uleftadd = (this.du0 / this.dy0);
/*  629 */             this.vleftadd = (this.dv0 / this.dy0);
/*  630 */             this.uleft = (this.dta * this.uleftadd + this.u0);
/*  631 */             this.vleft = (this.dta * this.vleftadd + this.v0);
/*      */           }
/*      */           
/*  634 */           if (this.INTERPOLATE_RGB) {
/*  635 */             this.rleftadd = (this.dr0 / this.dy0);
/*  636 */             this.gleftadd = (this.dg0 / this.dy0);
/*  637 */             this.bleftadd = (this.db0 / this.dy0);
/*  638 */             this.rleft = (this.dta * this.rleftadd + this.r0);
/*  639 */             this.gleft = (this.dta * this.gleftadd + this.g0);
/*  640 */             this.bleft = (this.dta * this.bleftadd + this.b0);
/*      */           }
/*      */           
/*  643 */           if (this.INTERPOLATE_ALPHA) {
/*  644 */             this.aleftadd = (this.da0 / this.dy0);
/*  645 */             this.aleft = (this.dta * this.aleftadd + this.a0);
/*      */             
/*  647 */             if (this.m_drawFlags == 16) {
/*  648 */               drawsegment_plain_alpha(this.xadd1, this.xadd2, yi0, yi1);
/*  649 */             } else if (this.m_drawFlags == 17) {
/*  650 */               drawsegment_gouraud_alpha(this.xadd1, this.xadd2, yi0, yi1);
/*  651 */             } else if (this.m_drawFlags == 18) {
/*  652 */               drawsegment_texture8_alpha(this.xadd1, this.xadd2, yi0, yi1);
/*  653 */             } else if (this.m_drawFlags == 20) {
/*  654 */               drawsegment_texture24_alpha(this.xadd1, this.xadd2, yi0, yi1);
/*  655 */             } else if (this.m_drawFlags == 24) {
/*  656 */               drawsegment_texture32_alpha(this.xadd1, this.xadd2, yi0, yi1);
/*  657 */             } else if (this.m_drawFlags == 19) {
/*  658 */               drawsegment_gouraud_texture8_alpha(this.xadd1, this.xadd2, yi0, yi1);
/*  659 */             } else if (this.m_drawFlags == 21) {
/*  660 */               drawsegment_gouraud_texture24_alpha(this.xadd1, this.xadd2, yi0, yi1);
/*  661 */             } else if (this.m_drawFlags == 25) {
/*  662 */               drawsegment_gouraud_texture32_alpha(this.xadd1, this.xadd2, yi0, yi1);
/*      */             }
/*      */           }
/*  665 */           else if (this.m_drawFlags == 0) {
/*  666 */             drawsegment_plain(this.xadd1, this.xadd2, yi0, yi1);
/*  667 */           } else if (this.m_drawFlags == 1) {
/*  668 */             drawsegment_gouraud(this.xadd1, this.xadd2, yi0, yi1);
/*  669 */           } else if (this.m_drawFlags == 2) {
/*  670 */             drawsegment_texture8(this.xadd1, this.xadd2, yi0, yi1);
/*  671 */           } else if (this.m_drawFlags == 4) {
/*  672 */             drawsegment_texture24(this.xadd1, this.xadd2, yi0, yi1);
/*  673 */           } else if (this.m_drawFlags == 8) {
/*  674 */             drawsegment_texture32(this.xadd1, this.xadd2, yi0, yi1);
/*  675 */           } else if (this.m_drawFlags == 3) {
/*  676 */             drawsegment_gouraud_texture8(this.xadd1, this.xadd2, yi0, yi1);
/*  677 */           } else if (this.m_drawFlags == 5) {
/*  678 */             drawsegment_gouraud_texture24(this.xadd1, this.xadd2, yi0, yi1);
/*  679 */           } else if (this.m_drawFlags == 9) {
/*  680 */             drawsegment_gouraud_texture32(this.xadd1, this.xadd2, yi0, yi1);
/*      */           }
/*      */           
/*  683 */           this.m_singleRight = true;
/*      */         } else {
/*  685 */           this.xleft = (x0 + this.dta * this.xadd2);
/*  686 */           this.xrght = (x0 + this.dta * this.xadd1);
/*  687 */           this.zleftadd = (this.dz2 / this.dy2);
/*  688 */           this.zleft = (this.dta * this.zleftadd + z0);
/*      */           
/*  690 */           if (this.INTERPOLATE_UV) {
/*  691 */             this.uleftadd = (this.du2 / this.dy2);
/*  692 */             this.vleftadd = (this.dv2 / this.dy2);
/*  693 */             this.uleft = (this.dta * this.uleftadd + this.u0);
/*  694 */             this.vleft = (this.dta * this.vleftadd + this.v0);
/*      */           }
/*      */           
/*      */ 
/*  698 */           if (this.INTERPOLATE_RGB) {
/*  699 */             this.rleftadd = (this.dr2 / this.dy2);
/*  700 */             this.gleftadd = (this.dg2 / this.dy2);
/*  701 */             this.bleftadd = (this.db2 / this.dy2);
/*  702 */             this.rleft = (this.dta * this.rleftadd + this.r0);
/*  703 */             this.gleft = (this.dta * this.gleftadd + this.g0);
/*  704 */             this.bleft = (this.dta * this.bleftadd + this.b0);
/*      */           }
/*      */           
/*      */ 
/*  708 */           if (this.INTERPOLATE_ALPHA) {
/*  709 */             this.aleftadd = (this.da2 / this.dy2);
/*  710 */             this.aleft = (this.dta * this.aleftadd + this.a0);
/*      */             
/*  712 */             if (this.m_drawFlags == 16) {
/*  713 */               drawsegment_plain_alpha(this.xadd2, this.xadd1, yi0, yi1);
/*  714 */             } else if (this.m_drawFlags == 17) {
/*  715 */               drawsegment_gouraud_alpha(this.xadd2, this.xadd1, yi0, yi1);
/*  716 */             } else if (this.m_drawFlags == 18) {
/*  717 */               drawsegment_texture8_alpha(this.xadd2, this.xadd1, yi0, yi1);
/*  718 */             } else if (this.m_drawFlags == 20) {
/*  719 */               drawsegment_texture24_alpha(this.xadd2, this.xadd1, yi0, yi1);
/*  720 */             } else if (this.m_drawFlags == 24) {
/*  721 */               drawsegment_texture32_alpha(this.xadd2, this.xadd1, yi0, yi1);
/*  722 */             } else if (this.m_drawFlags == 19) {
/*  723 */               drawsegment_gouraud_texture8_alpha(this.xadd2, this.xadd1, yi0, yi1);
/*  724 */             } else if (this.m_drawFlags == 21) {
/*  725 */               drawsegment_gouraud_texture24_alpha(this.xadd2, this.xadd1, yi0, yi1);
/*  726 */             } else if (this.m_drawFlags == 25) {
/*  727 */               drawsegment_gouraud_texture32_alpha(this.xadd2, this.xadd1, yi0, yi1);
/*      */             }
/*      */           }
/*  730 */           else if (this.m_drawFlags == 0) {
/*  731 */             drawsegment_plain(this.xadd2, this.xadd1, yi0, yi1);
/*  732 */           } else if (this.m_drawFlags == 1) {
/*  733 */             drawsegment_gouraud(this.xadd2, this.xadd1, yi0, yi1);
/*  734 */           } else if (this.m_drawFlags == 2) {
/*  735 */             drawsegment_texture8(this.xadd2, this.xadd1, yi0, yi1);
/*  736 */           } else if (this.m_drawFlags == 4) {
/*  737 */             drawsegment_texture24(this.xadd2, this.xadd1, yi0, yi1);
/*  738 */           } else if (this.m_drawFlags == 8) {
/*  739 */             drawsegment_texture32(this.xadd2, this.xadd1, yi0, yi1);
/*  740 */           } else if (this.m_drawFlags == 3) {
/*  741 */             drawsegment_gouraud_texture8(this.xadd2, this.xadd1, yi0, yi1);
/*  742 */           } else if (this.m_drawFlags == 5) {
/*  743 */             drawsegment_gouraud_texture24(this.xadd2, this.xadd1, yi0, yi1);
/*  744 */           } else if (this.m_drawFlags == 9) {
/*  745 */             drawsegment_gouraud_texture32(this.xadd2, this.xadd1, yi0, yi1);
/*      */           }
/*      */           
/*  748 */           this.m_singleRight = false;
/*      */         }
/*      */         
/*      */ 
/*  752 */         if (yi2 == yi1) { return;
/*      */         }
/*      */         
/*  755 */         this.dy1 = (y2 - y1);
/*  756 */         this.xadd1 = ((x2 - x1) / this.dy1);
/*      */       }
/*      */       else
/*      */       {
/*  760 */         this.dy1 = (y2 - y1);
/*  761 */         this.xadd1 = ((x2 - x1) / this.dy1);
/*      */         
/*      */ 
/*  764 */         if (this.xadd2 < this.xadd1) {
/*  765 */           this.xrght = ((yi1 + 0.5F - y0) * this.xadd2 + x0);
/*  766 */           this.m_singleRight = true;
/*      */         } else {
/*  768 */           this.dta = (yi1 + 0.5F - y0);
/*  769 */           this.xleft = (this.dta * this.xadd2 + x0);
/*  770 */           this.zleftadd = (this.dz2 / this.dy2);
/*  771 */           this.zleft = (this.dta * this.zleftadd + z0);
/*      */           
/*  773 */           if (this.INTERPOLATE_UV) {
/*  774 */             this.uleftadd = (this.du2 / this.dy2);
/*  775 */             this.vleftadd = (this.dv2 / this.dy2);
/*  776 */             this.uleft = (this.dta * this.uleftadd + this.u0);
/*  777 */             this.vleft = (this.dta * this.vleftadd + this.v0);
/*      */           }
/*      */           
/*  780 */           if (this.INTERPOLATE_RGB) {
/*  781 */             this.rleftadd = (this.dr2 / this.dy2);
/*  782 */             this.gleftadd = (this.dg2 / this.dy2);
/*  783 */             this.bleftadd = (this.db2 / this.dy2);
/*  784 */             this.rleft = (this.dta * this.rleftadd + this.r0);
/*  785 */             this.gleft = (this.dta * this.gleftadd + this.g0);
/*  786 */             this.bleft = (this.dta * this.bleftadd + this.b0);
/*      */           }
/*      */           
/*      */ 
/*  790 */           if (this.INTERPOLATE_ALPHA) {
/*  791 */             this.aleftadd = (this.da2 / this.dy2);
/*  792 */             this.aleft = (this.dta * this.aleftadd + this.a0);
/*      */           }
/*  794 */           this.m_singleRight = false;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  799 */       if (this.m_singleRight) {
/*  800 */         this.dta = (yi1 + 0.5F - y1);
/*  801 */         this.xleft = (this.dta * this.xadd1 + x1);
/*  802 */         this.zleftadd = ((z2 - z1) / this.dy1);
/*  803 */         this.zleft = (this.dta * this.zleftadd + z1);
/*      */         
/*  805 */         if (this.INTERPOLATE_UV) {
/*  806 */           this.uleftadd = ((this.u2 - this.u1) / this.dy1);
/*  807 */           this.vleftadd = ((this.v2 - this.v1) / this.dy1);
/*  808 */           this.uleft = (this.dta * this.uleftadd + this.u1);
/*  809 */           this.vleft = (this.dta * this.vleftadd + this.v1);
/*      */         }
/*      */         
/*  812 */         if (this.INTERPOLATE_RGB) {
/*  813 */           this.rleftadd = ((this.r2 - this.r1) / this.dy1);
/*  814 */           this.gleftadd = ((this.g2 - this.g1) / this.dy1);
/*  815 */           this.bleftadd = ((this.b2 - this.b1) / this.dy1);
/*  816 */           this.rleft = (this.dta * this.rleftadd + this.r1);
/*  817 */           this.gleft = (this.dta * this.gleftadd + this.g1);
/*  818 */           this.bleft = (this.dta * this.bleftadd + this.b1);
/*      */         }
/*      */         
/*  821 */         if (this.INTERPOLATE_ALPHA) {
/*  822 */           this.aleftadd = ((this.a2 - this.a1) / this.dy1);
/*  823 */           this.aleft = (this.dta * this.aleftadd + this.a1);
/*      */           
/*  825 */           if (this.m_drawFlags == 16) {
/*  826 */             drawsegment_plain_alpha(this.xadd1, this.xadd2, yi1, yi2);
/*  827 */           } else if (this.m_drawFlags == 17) {
/*  828 */             drawsegment_gouraud_alpha(this.xadd1, this.xadd2, yi1, yi2);
/*  829 */           } else if (this.m_drawFlags == 18) {
/*  830 */             drawsegment_texture8_alpha(this.xadd1, this.xadd2, yi1, yi2);
/*  831 */           } else if (this.m_drawFlags == 20) {
/*  832 */             drawsegment_texture24_alpha(this.xadd1, this.xadd2, yi1, yi2);
/*  833 */           } else if (this.m_drawFlags == 24) {
/*  834 */             drawsegment_texture32_alpha(this.xadd1, this.xadd2, yi1, yi2);
/*  835 */           } else if (this.m_drawFlags == 19) {
/*  836 */             drawsegment_gouraud_texture8_alpha(this.xadd1, this.xadd2, yi1, yi2);
/*  837 */           } else if (this.m_drawFlags == 21) {
/*  838 */             drawsegment_gouraud_texture24_alpha(this.xadd1, this.xadd2, yi1, yi2);
/*  839 */           } else if (this.m_drawFlags == 25) {
/*  840 */             drawsegment_gouraud_texture32_alpha(this.xadd1, this.xadd2, yi1, yi2);
/*      */           }
/*      */         }
/*  843 */         else if (this.m_drawFlags == 0) {
/*  844 */           drawsegment_plain(this.xadd1, this.xadd2, yi1, yi2);
/*  845 */         } else if (this.m_drawFlags == 1) {
/*  846 */           drawsegment_gouraud(this.xadd1, this.xadd2, yi1, yi2);
/*  847 */         } else if (this.m_drawFlags == 2) {
/*  848 */           drawsegment_texture8(this.xadd1, this.xadd2, yi1, yi2);
/*  849 */         } else if (this.m_drawFlags == 4) {
/*  850 */           drawsegment_texture24(this.xadd1, this.xadd2, yi1, yi2);
/*  851 */         } else if (this.m_drawFlags == 8) {
/*  852 */           drawsegment_texture32(this.xadd1, this.xadd2, yi1, yi2);
/*  853 */         } else if (this.m_drawFlags == 3) {
/*  854 */           drawsegment_gouraud_texture8(this.xadd1, this.xadd2, yi1, yi2);
/*  855 */         } else if (this.m_drawFlags == 5) {
/*  856 */           drawsegment_gouraud_texture24(this.xadd1, this.xadd2, yi1, yi2);
/*  857 */         } else if (this.m_drawFlags == 9) {
/*  858 */           drawsegment_gouraud_texture32(this.xadd1, this.xadd2, yi1, yi2);
/*      */         }
/*      */       }
/*      */       else {
/*  862 */         this.xrght = ((yi1 + 0.5F - y1) * this.xadd1 + x1);
/*      */         
/*  864 */         if (this.INTERPOLATE_ALPHA) {
/*  865 */           if (this.m_drawFlags == 16) {
/*  866 */             drawsegment_plain_alpha(this.xadd2, this.xadd1, yi1, yi2);
/*  867 */           } else if (this.m_drawFlags == 17) {
/*  868 */             drawsegment_gouraud_alpha(this.xadd2, this.xadd1, yi1, yi2);
/*  869 */           } else if (this.m_drawFlags == 18) {
/*  870 */             drawsegment_texture8_alpha(this.xadd2, this.xadd1, yi1, yi2);
/*  871 */           } else if (this.m_drawFlags == 20) {
/*  872 */             drawsegment_texture24_alpha(this.xadd2, this.xadd1, yi1, yi2);
/*  873 */           } else if (this.m_drawFlags == 24) {
/*  874 */             drawsegment_texture32_alpha(this.xadd2, this.xadd1, yi1, yi2);
/*  875 */           } else if (this.m_drawFlags == 19) {
/*  876 */             drawsegment_gouraud_texture8_alpha(this.xadd2, this.xadd1, yi1, yi2);
/*  877 */           } else if (this.m_drawFlags == 21) {
/*  878 */             drawsegment_gouraud_texture24_alpha(this.xadd2, this.xadd1, yi1, yi2);
/*  879 */           } else if (this.m_drawFlags == 25) {
/*  880 */             drawsegment_gouraud_texture32_alpha(this.xadd2, this.xadd1, yi1, yi2);
/*      */           }
/*      */         }
/*  883 */         else if (this.m_drawFlags == 0) {
/*  884 */           drawsegment_plain(this.xadd2, this.xadd1, yi1, yi2);
/*  885 */         } else if (this.m_drawFlags == 1) {
/*  886 */           drawsegment_gouraud(this.xadd2, this.xadd1, yi1, yi2);
/*  887 */         } else if (this.m_drawFlags == 2) {
/*  888 */           drawsegment_texture8(this.xadd2, this.xadd1, yi1, yi2);
/*  889 */         } else if (this.m_drawFlags == 4) {
/*  890 */           drawsegment_texture24(this.xadd2, this.xadd1, yi1, yi2);
/*  891 */         } else if (this.m_drawFlags == 8) {
/*  892 */           drawsegment_texture32(this.xadd2, this.xadd1, yi1, yi2);
/*  893 */         } else if (this.m_drawFlags == 3) {
/*  894 */           drawsegment_gouraud_texture8(this.xadd2, this.xadd1, yi1, yi2);
/*  895 */         } else if (this.m_drawFlags == 5) {
/*  896 */           drawsegment_gouraud_texture24(this.xadd2, this.xadd1, yi1, yi2);
/*  897 */         } else if (this.m_drawFlags == 9) {
/*  898 */           drawsegment_gouraud_texture32(this.xadd2, this.xadd1, yi1, yi2);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean precomputeAccurateTexturing()
/*      */   {
/*  966 */     float myFact = 65500.0F;
/*  967 */     float myFact2 = 65500.0F;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  999 */     if (this.firstSegment) {
/* 1000 */       PMatrix3D myMatrix = 
/* 1001 */         new PMatrix3D(this.u_array[this.o0] / myFact, this.v_array[this.o0] / myFact2, 1.0F, 0.0F, 
/* 1002 */         this.u_array[this.o1] / myFact, this.v_array[this.o1] / myFact2, 1.0F, 0.0F, 
/* 1003 */         this.u_array[this.o2] / myFact, this.v_array[this.o2] / myFact2, 1.0F, 0.0F, 
/* 1004 */         0.0F, 0.0F, 0.0F, 1.0F);
/*      */       
/*      */ 
/* 1007 */       myMatrix.invert();
/*      */       
/* 1009 */       if (myMatrix == null) { return false;
/*      */       }
/*      */       
/* 1012 */       float m00 = myMatrix.m00 * this.camX[this.o0] + myMatrix.m01 * this.camX[this.o1] + myMatrix.m02 * this.camX[this.o2];
/* 1013 */       float m01 = myMatrix.m10 * this.camX[this.o0] + myMatrix.m11 * this.camX[this.o1] + myMatrix.m12 * this.camX[this.o2];
/* 1014 */       float m02 = myMatrix.m20 * this.camX[this.o0] + myMatrix.m21 * this.camX[this.o1] + myMatrix.m22 * this.camX[this.o2];
/* 1015 */       float m10 = myMatrix.m00 * this.camY[this.o0] + myMatrix.m01 * this.camY[this.o1] + myMatrix.m02 * this.camY[this.o2];
/* 1016 */       float m11 = myMatrix.m10 * this.camY[this.o0] + myMatrix.m11 * this.camY[this.o1] + myMatrix.m12 * this.camY[this.o2];
/* 1017 */       float m12 = myMatrix.m20 * this.camY[this.o0] + myMatrix.m21 * this.camY[this.o1] + myMatrix.m22 * this.camY[this.o2];
/* 1018 */       float m20 = -(myMatrix.m00 * this.camZ[this.o0] + myMatrix.m01 * this.camZ[this.o1] + myMatrix.m02 * this.camZ[this.o2]);
/* 1019 */       float m21 = -(myMatrix.m10 * this.camZ[this.o0] + myMatrix.m11 * this.camZ[this.o1] + myMatrix.m12 * this.camZ[this.o2]);
/* 1020 */       float m22 = -(myMatrix.m20 * this.camZ[this.o0] + myMatrix.m21 * this.camZ[this.o1] + myMatrix.m22 * this.camZ[this.o2]);
/*      */       
/* 1022 */       float px = m02;
/* 1023 */       float py = m12;
/* 1024 */       float pz = m22;
/*      */       
/*      */ 
/* 1027 */       float resultT0x = m00 * this.TEX_WIDTH + m02;
/* 1028 */       float resultT0y = m10 * this.TEX_WIDTH + m12;
/* 1029 */       float resultT0z = m20 * this.TEX_WIDTH + m22;
/* 1030 */       float result0Tx = m01 * this.TEX_HEIGHT + m02;
/* 1031 */       float result0Ty = m11 * this.TEX_HEIGHT + m12;
/* 1032 */       float result0Tz = m21 * this.TEX_HEIGHT + m22;
/* 1033 */       float mx = resultT0x - m02;
/* 1034 */       float my = resultT0y - m12;
/* 1035 */       float mz = resultT0z - m22;
/* 1036 */       float nx = result0Tx - m02;
/* 1037 */       float ny = result0Ty - m12;
/* 1038 */       float nz = result0Tz - m22;
/*      */       
/*      */ 
/* 1041 */       this.ax = ((py * nz - pz * ny) * this.TEX_WIDTH);
/* 1042 */       this.ay = ((pz * nx - px * nz) * this.TEX_WIDTH);
/* 1043 */       this.az = ((px * ny - py * nx) * this.TEX_WIDTH);
/*      */       
/* 1045 */       this.bx = ((my * pz - mz * py) * this.TEX_HEIGHT);
/* 1046 */       this.by = ((mz * px - mx * pz) * this.TEX_HEIGHT);
/* 1047 */       this.bz = ((mx * py - my * px) * this.TEX_HEIGHT);
/*      */       
/* 1049 */       this.cx = (ny * mz - nz * my);
/* 1050 */       this.cy = (nz * mx - nx * mz);
/* 1051 */       this.cz = (nx * my - ny * mx);
/*      */     }
/*      */     
/* 1054 */     this.nearPlaneWidth = (this.parent.rightScreen - this.parent.leftScreen);
/* 1055 */     this.nearPlaneHeight = (this.parent.topScreen - this.parent.bottomScreen);
/* 1056 */     this.nearPlaneDepth = this.parent.nearPlane;
/*      */     
/* 1058 */     this.xmult = (this.nearPlaneWidth / this.SCREEN_WIDTH);
/* 1059 */     this.ymult = (this.nearPlaneHeight / this.SCREEN_HEIGHT);
/*      */     
/* 1061 */     this.newax = (this.ax * this.xmult);
/* 1062 */     this.newbx = (this.bx * this.xmult);
/* 1063 */     this.newcx = (this.cx * this.xmult);
/* 1064 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void setInterpPower(int pwr)
/*      */   {
/* 1074 */     TEX_INTERP_POWER = pwr;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void drawsegment_plain(float leftadd, float rghtadd, int ytop, int ybottom)
/*      */   {
/* 1085 */     ytop *= this.SCREEN_WIDTH;
/* 1086 */     ybottom *= this.SCREEN_WIDTH;
/*      */     
/*      */ 
/*      */ 
/* 1090 */     while (ytop < ybottom) {
/* 1091 */       int xstart = (int)(this.xleft + 0.5F);
/* 1092 */       if (xstart < 0) {
/* 1093 */         xstart = 0;
/*      */       }
/* 1095 */       int xend = (int)(this.xrght + 0.5F);
/* 1096 */       if (xend > this.SCREEN_WIDTH) {
/* 1097 */         xend = this.SCREEN_WIDTH;
/*      */       }
/* 1099 */       float xdiff = xstart + 0.5F - this.xleft;
/* 1100 */       float iz = this.izadd * xdiff + this.zleft;
/* 1101 */       xstart += ytop;
/* 1102 */       xend += ytop;
/* 1104 */       for (; 
/* 1104 */           xstart < xend; xstart++) {
/* 1105 */         if ((this.noDepthTest) || (iz <= this.m_zbuffer[xstart])) {
/* 1106 */           this.m_zbuffer[xstart] = iz;
/* 1107 */           this.m_pixels[xstart] = this.m_fill;
/*      */         }
/*      */         
/* 1110 */         iz += this.izadd;
/*      */       }
/*      */       
/* 1113 */       ytop += this.SCREEN_WIDTH;
/* 1114 */       this.xleft += leftadd;
/* 1115 */       this.xrght += rghtadd;
/* 1116 */       this.zleft += this.zleftadd;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void drawsegment_plain_alpha(float leftadd, float rghtadd, int ytop, int ybottom)
/*      */   {
/* 1128 */     ytop *= this.SCREEN_WIDTH;
/* 1129 */     ybottom *= this.SCREEN_WIDTH;
/*      */     
/* 1131 */     int pr = this.m_fill & 0xFF0000;
/* 1132 */     int pg = this.m_fill & 0xFF00;
/* 1133 */     int pb = this.m_fill & 0xFF;
/*      */     
/*      */ 
/* 1136 */     float iaf = this.iaadd;
/*      */     
/* 1138 */     while (ytop < ybottom) {
/* 1139 */       int xstart = (int)(this.xleft + 0.5F);
/* 1140 */       if (xstart < 0) {
/* 1141 */         xstart = 0;
/*      */       }
/* 1143 */       int xend = (int)(this.xrght + 0.5F);
/* 1144 */       if (xend > this.SCREEN_WIDTH) {
/* 1145 */         xend = this.SCREEN_WIDTH;
/*      */       }
/* 1147 */       float xdiff = xstart + 0.5F - this.xleft;
/* 1148 */       float iz = this.izadd * xdiff + this.zleft;
/* 1149 */       int ia = (int)(iaf * xdiff + this.aleft);
/* 1150 */       xstart += ytop;
/* 1151 */       xend += ytop;
/* 1155 */       for (; 
/*      */           
/*      */ 
/* 1155 */           xstart < xend; xstart++) {
/* 1156 */         if ((this.noDepthTest) || (iz <= this.m_zbuffer[xstart]))
/*      */         {
/*      */ 
/*      */ 
/* 1160 */           int alpha = ia >> 16;
/* 1161 */           int mr0 = this.m_pixels[xstart];
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1168 */           int mg0 = mr0 & 0xFF00;
/* 1169 */           int mb0 = mr0 & 0xFF;
/* 1170 */           mr0 &= 0xFF0000;
/*      */           
/* 1172 */           mr0 += ((pr - mr0) * alpha >> 8);
/* 1173 */           mg0 += ((pg - mg0) * alpha >> 8);
/* 1174 */           mb0 += ((pb - mb0) * alpha >> 8);
/*      */           
/* 1176 */           this.m_pixels[xstart] = 
/* 1177 */             (0xFF000000 | mr0 & 0xFF0000 | mg0 & 0xFF00 | mb0 & 0xFF);
/*      */         }
/*      */         
/*      */ 
/* 1181 */         iz += this.izadd;
/* 1182 */         ia += this.iaadd;
/*      */       }
/* 1184 */       ytop += this.SCREEN_WIDTH;
/* 1185 */       this.xleft += leftadd;
/* 1186 */       this.xrght += rghtadd;
/* 1187 */       this.zleft += this.zleftadd;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void drawsegment_gouraud(float leftadd, float rghtadd, int ytop, int ybottom)
/*      */   {
/* 1199 */     float irf = this.iradd;
/* 1200 */     float igf = this.igadd;
/* 1201 */     float ibf = this.ibadd;
/*      */     
/* 1203 */     ytop *= this.SCREEN_WIDTH;
/* 1204 */     ybottom *= this.SCREEN_WIDTH;
/*      */     
/*      */ 
/* 1207 */     while (ytop < ybottom) {
/* 1208 */       int xstart = (int)(this.xleft + 0.5F);
/* 1209 */       if (xstart < 0) {
/* 1210 */         xstart = 0;
/*      */       }
/* 1212 */       int xend = (int)(this.xrght + 0.5F);
/* 1213 */       if (xend > this.SCREEN_WIDTH) {
/* 1214 */         xend = this.SCREEN_WIDTH;
/*      */       }
/* 1216 */       float xdiff = xstart + 0.5F - this.xleft;
/* 1217 */       int ir = (int)(irf * xdiff + this.rleft);
/* 1218 */       int ig = (int)(igf * xdiff + this.gleft);
/* 1219 */       int ib = (int)(ibf * xdiff + this.bleft);
/* 1220 */       float iz = this.izadd * xdiff + this.zleft;
/*      */       
/* 1222 */       xstart += ytop;
/* 1223 */       xend += ytop;
/* 1225 */       for (; 
/* 1225 */           xstart < xend; xstart++) {
/* 1226 */         if ((this.noDepthTest) || (iz <= this.m_zbuffer[xstart])) {
/* 1227 */           this.m_zbuffer[xstart] = iz;
/* 1228 */           this.m_pixels[xstart] = 
/* 1229 */             (0xFF000000 | ir & 0xFF0000 | ig >> 8 & 0xFF00 | ib >> 16);
/*      */         }
/*      */         
/*      */ 
/* 1233 */         ir += this.iradd;
/* 1234 */         ig += this.igadd;
/* 1235 */         ib += this.ibadd;
/* 1236 */         iz += this.izadd;
/*      */       }
/*      */       
/* 1239 */       ytop += this.SCREEN_WIDTH;
/* 1240 */       this.xleft += leftadd;
/* 1241 */       this.xrght += rghtadd;
/* 1242 */       this.rleft += this.rleftadd;
/* 1243 */       this.gleft += this.gleftadd;
/* 1244 */       this.bleft += this.bleftadd;
/* 1245 */       this.zleft += this.zleftadd;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void drawsegment_gouraud_alpha(float leftadd, float rghtadd, int ytop, int ybottom)
/*      */   {
/* 1257 */     ytop *= this.SCREEN_WIDTH;
/* 1258 */     ybottom *= this.SCREEN_WIDTH;
/*      */     
/*      */ 
/* 1261 */     float irf = this.iradd;
/* 1262 */     float igf = this.igadd;
/* 1263 */     float ibf = this.ibadd;
/* 1264 */     float iaf = this.iaadd;
/*      */     
/* 1266 */     while (ytop < ybottom) {
/* 1267 */       int xstart = (int)(this.xleft + 0.5F);
/* 1268 */       if (xstart < 0)
/* 1269 */         xstart = 0;
/* 1270 */       int xend = (int)(this.xrght + 0.5F);
/* 1271 */       if (xend > this.SCREEN_WIDTH)
/* 1272 */         xend = this.SCREEN_WIDTH;
/* 1273 */       float xdiff = xstart + 0.5F - this.xleft;
/*      */       
/* 1275 */       int ir = (int)(irf * xdiff + this.rleft);
/* 1276 */       int ig = (int)(igf * xdiff + this.gleft);
/* 1277 */       int ib = (int)(ibf * xdiff + this.bleft);
/* 1278 */       int ia = (int)(iaf * xdiff + this.aleft);
/* 1279 */       float iz = this.izadd * xdiff + this.zleft;
/*      */       
/* 1281 */       xstart += ytop;
/* 1282 */       xend += ytop;
/* 1284 */       for (; 
/* 1284 */           xstart < xend; xstart++) {
/* 1285 */         if ((this.noDepthTest) || (iz <= this.m_zbuffer[xstart]))
/*      */         {
/*      */ 
/*      */ 
/* 1289 */           int red = ir & 0xFF0000;
/* 1290 */           int grn = ig >> 8 & 0xFF00;
/* 1291 */           int blu = ib >> 16;
/*      */           
/*      */ 
/* 1294 */           int bb = this.m_pixels[xstart];
/* 1295 */           int br = bb & 0xFF0000;
/* 1296 */           int bg = bb & 0xFF00;
/* 1297 */           bb &= 0xFF;
/*      */           
/*      */ 
/* 1300 */           int al = ia >> 16;
/*      */           
/*      */ 
/* 1303 */           this.m_pixels[xstart] = 
/*      */           
/*      */ 
/* 1306 */             (0xFF000000 | br + ((red - br) * al >> 8) & 0xFF0000 | bg + ((grn - bg) * al >> 8) & 0xFF00 | bb + ((blu - bb) * al >> 8) & 0xFF);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 1311 */         ir += this.iradd;
/* 1312 */         ig += this.igadd;
/* 1313 */         ib += this.ibadd;
/* 1314 */         ia += this.iaadd;
/* 1315 */         iz += this.izadd;
/*      */       }
/*      */       
/* 1318 */       ytop += this.SCREEN_WIDTH;
/* 1319 */       this.xleft += leftadd;
/* 1320 */       this.xrght += rghtadd;
/* 1321 */       this.rleft += this.rleftadd;
/* 1322 */       this.gleft += this.gleftadd;
/* 1323 */       this.bleft += this.bleftadd;
/* 1324 */       this.aleft += this.aleftadd;
/* 1325 */       this.zleft += this.zleftadd;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void drawsegment_texture8(float leftadd, float rghtadd, int ytop, int ybottom)
/*      */   {
/* 1341 */     int ypixel = ytop;
/* 1342 */     int lastRowStart = this.m_texture.length - this.TEX_WIDTH - 2;
/* 1343 */     boolean accurateMode = this.parent.hints[7];
/* 1344 */     float screenx = 0.0F;float screeny = 0.0F;float screenz = 0.0F;
/* 1345 */     float a = 0.0F;float b = 0.0F;float c = 0.0F;
/* 1346 */     int linearInterpPower = TEX_INTERP_POWER;
/* 1347 */     int linearInterpLength = 1 << linearInterpPower;
/* 1348 */     if (accurateMode)
/*      */     {
/* 1350 */       if (precomputeAccurateTexturing()) {
/* 1351 */         this.newax *= linearInterpLength;
/* 1352 */         this.newbx *= linearInterpLength;
/* 1353 */         this.newcx *= linearInterpLength;
/* 1354 */         screenz = this.nearPlaneDepth;
/* 1355 */         this.firstSegment = false;
/*      */       }
/*      */       else
/*      */       {
/* 1359 */         accurateMode = false;
/*      */       }
/*      */     }
/* 1362 */     ytop *= this.SCREEN_WIDTH;
/* 1363 */     ybottom *= this.SCREEN_WIDTH;
/*      */     
/*      */ 
/* 1366 */     float iuf = this.iuadd;
/* 1367 */     float ivf = this.ivadd;
/*      */     
/* 1369 */     int red = this.m_fill & 0xFF0000;
/* 1370 */     int grn = this.m_fill & 0xFF00;
/* 1371 */     int blu = this.m_fill & 0xFF;
/*      */     
/* 1373 */     while (ytop < ybottom) {
/* 1374 */       int xstart = (int)(this.xleft + 0.5F);
/* 1375 */       if (xstart < 0) {
/* 1376 */         xstart = 0;
/*      */       }
/* 1378 */       int xpixel = xstart;
/*      */       
/* 1380 */       int xend = (int)(this.xrght + 0.5F);
/* 1381 */       if (xend > this.SCREEN_WIDTH) {
/* 1382 */         xend = this.SCREEN_WIDTH;
/*      */       }
/* 1384 */       float xdiff = xstart + 0.5F - this.xleft;
/* 1385 */       int iu = (int)(iuf * xdiff + this.uleft);
/* 1386 */       int iv = (int)(ivf * xdiff + this.vleft);
/* 1387 */       float iz = this.izadd * xdiff + this.zleft;
/*      */       
/* 1389 */       xstart += ytop;
/* 1390 */       xend += ytop;
/*      */       
/* 1392 */       if (accurateMode) {
/* 1393 */         screenx = this.xmult * (xpixel + 0.5F - this.SCREEN_WIDTH / 2.0F);
/* 1394 */         screeny = this.ymult * (ypixel + 0.5F - this.SCREEN_HEIGHT / 2.0F);
/* 1395 */         a = screenx * this.ax + screeny * this.ay + screenz * this.az;
/* 1396 */         b = screenx * this.bx + screeny * this.by + screenz * this.bz;
/* 1397 */         c = screenx * this.cx + screeny * this.cy + screenz * this.cz;
/*      */       }
/* 1399 */       boolean goingIn = (this.newcx > 0.0F ? 1 : 0) != (c > 0.0F ? 1 : 0);
/* 1400 */       int interpCounter = 0;
/* 1401 */       int deltaU = 0;int deltaV = 0;
/* 1402 */       float fu = 0.0F;float fv = 0.0F;
/* 1403 */       float oldfu = 0.0F;float oldfv = 0.0F;
/*      */       
/* 1405 */       if ((accurateMode) && (goingIn)) {
/* 1406 */         int rightOffset = (xend - xstart - 1) % linearInterpLength;
/* 1407 */         int leftOffset = linearInterpLength - rightOffset;
/* 1408 */         float rightOffset2 = rightOffset / linearInterpLength;
/* 1409 */         float leftOffset2 = leftOffset / linearInterpLength;
/* 1410 */         interpCounter = leftOffset;
/* 1411 */         float ao = a - leftOffset2 * this.newax;
/* 1412 */         float bo = b - leftOffset2 * this.newbx;
/* 1413 */         float co = c - leftOffset2 * this.newcx;
/* 1414 */         float oneoverc = 65536.0F / co;
/* 1415 */         oldfu = ao * oneoverc;oldfv = bo * oneoverc;
/* 1416 */         a += rightOffset2 * this.newax;
/* 1417 */         b += rightOffset2 * this.newbx;
/* 1418 */         c += rightOffset2 * this.newcx;
/* 1419 */         oneoverc = 65536.0F / c;
/* 1420 */         fu = a * oneoverc;fv = b * oneoverc;
/* 1421 */         deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 1422 */         deltaV = (int)(fv - oldfv) >> linearInterpPower;
/* 1423 */         iu = (int)oldfu + (leftOffset - 1) * deltaU;
/* 1424 */         iv = (int)oldfv + (leftOffset - 1) * deltaV;
/*      */       } else {
/* 1426 */         float preoneoverc = 65536.0F / c;
/* 1427 */         fu = a * preoneoverc;
/* 1428 */         fv = b * preoneoverc;
/*      */       }
/* 1431 */       for (; 
/* 1431 */           xstart < xend; xstart++) {
/* 1432 */         if (accurateMode) {
/* 1433 */           if (interpCounter == linearInterpLength) interpCounter = 0;
/* 1434 */           if (interpCounter == 0) {
/* 1435 */             a += this.newax;
/* 1436 */             b += this.newbx;
/* 1437 */             c += this.newcx;
/* 1438 */             float oneoverc = 65536.0F / c;
/* 1439 */             oldfu = fu;oldfv = fv;
/* 1440 */             fu = a * oneoverc;fv = b * oneoverc;
/* 1441 */             iu = (int)oldfu;iv = (int)oldfv;
/* 1442 */             deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 1443 */             deltaV = (int)(fv - oldfv) >> linearInterpPower;
/*      */           } else {
/* 1445 */             iu += deltaU;
/* 1446 */             iv += deltaV;
/*      */           }
/* 1448 */           interpCounter++;
/*      */         }
/*      */         try
/*      */         {
/* 1452 */           if ((this.noDepthTest) || (iz <= this.m_zbuffer[xstart]))
/*      */           {
/*      */             int al0;
/*      */             
/* 1456 */             if (this.m_bilinear) {
/* 1457 */               int ofs = (iv >> 16) * this.TEX_WIDTH + (iu >> 16);
/* 1458 */               int iui = iu & 0xFFFF;
/* 1459 */               int al0 = this.m_texture[ofs] & 0xFF;
/* 1460 */               int al1 = this.m_texture[(ofs + 1)] & 0xFF;
/* 1461 */               if (ofs < lastRowStart) ofs += this.TEX_WIDTH;
/* 1462 */               int al2 = this.m_texture[ofs] & 0xFF;
/* 1463 */               int al3 = this.m_texture[(ofs + 1)] & 0xFF;
/* 1464 */               al0 += ((al1 - al0) * iui >> 16);
/* 1465 */               al2 += ((al3 - al2) * iui >> 16);
/* 1466 */               al0 += ((al2 - al0) * (iv & 0xFFFF) >> 16);
/*      */             } else {
/* 1468 */               al0 = this.m_texture[((iv >> 16) * this.TEX_WIDTH + (iu >> 16))] & 0xFF;
/*      */             }
/*      */             
/* 1471 */             int br = this.m_pixels[xstart];
/* 1472 */             int bg = br & 0xFF00;
/* 1473 */             int bb = br & 0xFF;
/* 1474 */             br &= 0xFF0000;
/* 1475 */             this.m_pixels[xstart] = 
/*      */             
/*      */ 
/* 1478 */               (0xFF000000 | br + ((red - br) * al0 >> 8) & 0xFF0000 | bg + ((grn - bg) * al0 >> 8) & 0xFF00 | bb + ((blu - bb) * al0 >> 8) & 0xFF);
/*      */           }
/*      */         }
/*      */         catch (Exception localException) {}
/*      */         
/*      */ 
/* 1484 */         xpixel++;
/* 1485 */         if (!accurateMode) {
/* 1486 */           iu += this.iuadd;
/* 1487 */           iv += this.ivadd;
/*      */         }
/* 1489 */         iz += this.izadd;
/*      */       }
/* 1491 */       ypixel++;
/* 1492 */       ytop += this.SCREEN_WIDTH;
/* 1493 */       this.xleft += leftadd;
/* 1494 */       this.xrght += rghtadd;
/* 1495 */       this.uleft += this.uleftadd;
/* 1496 */       this.vleft += this.vleftadd;
/* 1497 */       this.zleft += this.zleftadd;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void drawsegment_texture8_alpha(float leftadd, float rghtadd, int ytop, int ybottom)
/*      */   {
/* 1513 */     int ypixel = ytop;
/* 1514 */     int lastRowStart = this.m_texture.length - this.TEX_WIDTH - 2;
/* 1515 */     boolean accurateMode = this.parent.hints[7];
/* 1516 */     float screenx = 0.0F;float screeny = 0.0F;float screenz = 0.0F;
/* 1517 */     float a = 0.0F;float b = 0.0F;float c = 0.0F;
/* 1518 */     int linearInterpPower = TEX_INTERP_POWER;
/* 1519 */     int linearInterpLength = 1 << linearInterpPower;
/* 1520 */     if (accurateMode)
/*      */     {
/* 1522 */       if (precomputeAccurateTexturing()) {
/* 1523 */         this.newax *= linearInterpLength;
/* 1524 */         this.newbx *= linearInterpLength;
/* 1525 */         this.newcx *= linearInterpLength;
/* 1526 */         screenz = this.nearPlaneDepth;
/* 1527 */         this.firstSegment = false;
/*      */       }
/*      */       else
/*      */       {
/* 1531 */         accurateMode = false;
/*      */       }
/*      */     }
/* 1534 */     ytop *= this.SCREEN_WIDTH;
/* 1535 */     ybottom *= this.SCREEN_WIDTH;
/*      */     
/*      */ 
/* 1538 */     float iuf = this.iuadd;
/* 1539 */     float ivf = this.ivadd;
/* 1540 */     float iaf = this.iaadd;
/*      */     
/* 1542 */     int red = this.m_fill & 0xFF0000;
/* 1543 */     int grn = this.m_fill & 0xFF00;
/* 1544 */     int blu = this.m_fill & 0xFF;
/*      */     
/* 1546 */     while (ytop < ybottom) {
/* 1547 */       int xstart = (int)(this.xleft + 0.5F);
/* 1548 */       if (xstart < 0) {
/* 1549 */         xstart = 0;
/*      */       }
/* 1551 */       int xpixel = xstart;
/*      */       
/* 1553 */       int xend = (int)(this.xrght + 0.5F);
/* 1554 */       if (xend > this.SCREEN_WIDTH) {
/* 1555 */         xend = this.SCREEN_WIDTH;
/*      */       }
/* 1557 */       float xdiff = xstart + 0.5F - this.xleft;
/* 1558 */       int iu = (int)(iuf * xdiff + this.uleft);
/* 1559 */       int iv = (int)(ivf * xdiff + this.vleft);
/* 1560 */       int ia = (int)(iaf * xdiff + this.aleft);
/* 1561 */       float iz = this.izadd * xdiff + this.zleft;
/*      */       
/* 1563 */       xstart += ytop;
/* 1564 */       xend += ytop;
/*      */       
/* 1566 */       if (accurateMode) {
/* 1567 */         screenx = this.xmult * (xpixel + 0.5F - this.SCREEN_WIDTH / 2.0F);
/* 1568 */         screeny = this.ymult * (ypixel + 0.5F - this.SCREEN_HEIGHT / 2.0F);
/* 1569 */         a = screenx * this.ax + screeny * this.ay + screenz * this.az;
/* 1570 */         b = screenx * this.bx + screeny * this.by + screenz * this.bz;
/* 1571 */         c = screenx * this.cx + screeny * this.cy + screenz * this.cz;
/*      */       }
/* 1573 */       boolean goingIn = (this.newcx > 0.0F ? 1 : 0) != (c > 0.0F ? 1 : 0);
/* 1574 */       int interpCounter = 0;
/* 1575 */       int deltaU = 0;int deltaV = 0;
/* 1576 */       float fu = 0.0F;float fv = 0.0F;
/* 1577 */       float oldfu = 0.0F;float oldfv = 0.0F;
/*      */       
/* 1579 */       if ((accurateMode) && (goingIn)) {
/* 1580 */         int rightOffset = (xend - xstart - 1) % linearInterpLength;
/* 1581 */         int leftOffset = linearInterpLength - rightOffset;
/* 1582 */         float rightOffset2 = rightOffset / linearInterpLength;
/* 1583 */         float leftOffset2 = leftOffset / linearInterpLength;
/* 1584 */         interpCounter = leftOffset;
/* 1585 */         float ao = a - leftOffset2 * this.newax;
/* 1586 */         float bo = b - leftOffset2 * this.newbx;
/* 1587 */         float co = c - leftOffset2 * this.newcx;
/* 1588 */         float oneoverc = 65536.0F / co;
/* 1589 */         oldfu = ao * oneoverc;oldfv = bo * oneoverc;
/* 1590 */         a += rightOffset2 * this.newax;
/* 1591 */         b += rightOffset2 * this.newbx;
/* 1592 */         c += rightOffset2 * this.newcx;
/* 1593 */         oneoverc = 65536.0F / c;
/* 1594 */         fu = a * oneoverc;fv = b * oneoverc;
/* 1595 */         deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 1596 */         deltaV = (int)(fv - oldfv) >> linearInterpPower;
/* 1597 */         iu = (int)oldfu + (leftOffset - 1) * deltaU;iv = (int)oldfv + (leftOffset - 1) * deltaV;
/*      */       } else {
/* 1599 */         float preoneoverc = 65536.0F / c;
/* 1600 */         fu = a * preoneoverc;
/* 1601 */         fv = b * preoneoverc;
/*      */       }
/* 1605 */       for (; 
/*      */           
/* 1605 */           xstart < xend; xstart++) {
/* 1606 */         if (accurateMode) {
/* 1607 */           if (interpCounter == linearInterpLength) interpCounter = 0;
/* 1608 */           if (interpCounter == 0) {
/* 1609 */             a += this.newax;
/* 1610 */             b += this.newbx;
/* 1611 */             c += this.newcx;
/* 1612 */             float oneoverc = 65536.0F / c;
/* 1613 */             oldfu = fu;oldfv = fv;
/* 1614 */             fu = a * oneoverc;fv = b * oneoverc;
/* 1615 */             iu = (int)oldfu;iv = (int)oldfv;
/* 1616 */             deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 1617 */             deltaV = (int)(fv - oldfv) >> linearInterpPower;
/*      */           } else {
/* 1619 */             iu += deltaU;
/* 1620 */             iv += deltaV;
/*      */           }
/* 1622 */           interpCounter++;
/*      */         }
/*      */         
/*      */         try
/*      */         {
/* 1627 */           if ((this.noDepthTest) || (iz <= this.m_zbuffer[xstart]))
/*      */           {
/*      */ 
/*      */ 
/* 1631 */             if (this.m_bilinear) {
/* 1632 */               int ofs = (iv >> 16) * this.TEX_WIDTH + (iu >> 16);
/* 1633 */               int iui = iu & 0xFFFF;
/* 1634 */               int al0 = this.m_texture[ofs] & 0xFF;
/* 1635 */               int al1 = this.m_texture[(ofs + 1)] & 0xFF;
/* 1636 */               if (ofs < lastRowStart) ofs += this.TEX_WIDTH;
/* 1637 */               int al2 = this.m_texture[ofs] & 0xFF;
/* 1638 */               int al3 = this.m_texture[(ofs + 1)] & 0xFF;
/* 1639 */               al0 += ((al1 - al0) * iui >> 16);
/* 1640 */               al2 += ((al3 - al2) * iui >> 16);
/* 1641 */               al0 += ((al2 - al0) * (iv & 0xFFFF) >> 16);
/*      */             } else {
/* 1643 */               al0 = this.m_texture[((iv >> 16) * this.TEX_WIDTH + (iu >> 16))] & 0xFF;
/*      */             }
/* 1645 */             int al0 = al0 * (ia >> 16) >> 8;
/*      */             
/* 1647 */             int br = this.m_pixels[xstart];
/* 1648 */             int bg = br & 0xFF00;
/* 1649 */             int bb = br & 0xFF;
/* 1650 */             br &= 0xFF0000;
/* 1651 */             this.m_pixels[xstart] = 
/*      */             
/*      */ 
/* 1654 */               (0xFF000000 | br + ((red - br) * al0 >> 8) & 0xFF0000 | bg + ((grn - bg) * al0 >> 8) & 0xFF00 | bb + ((blu - bb) * al0 >> 8) & 0xFF);
/*      */           }
/*      */         }
/*      */         catch (Exception localException) {}
/*      */         
/*      */ 
/* 1660 */         xpixel++;
/* 1661 */         if (!accurateMode) {
/* 1662 */           iu += this.iuadd;
/* 1663 */           iv += this.ivadd;
/*      */         }
/* 1665 */         iz += this.izadd;
/* 1666 */         ia += this.iaadd;
/*      */       }
/* 1668 */       ypixel++;
/* 1669 */       ytop += this.SCREEN_WIDTH;
/* 1670 */       this.xleft += leftadd;
/* 1671 */       this.xrght += rghtadd;
/* 1672 */       this.uleft += this.uleftadd;
/* 1673 */       this.vleft += this.vleftadd;
/* 1674 */       this.zleft += this.zleftadd;
/* 1675 */       this.aleft += this.aleftadd;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void drawsegment_texture24(float leftadd, float rghtadd, int ytop, int ybottom)
/*      */   {
/* 1686 */     ytop *= this.SCREEN_WIDTH;
/* 1687 */     ybottom *= this.SCREEN_WIDTH;
/*      */     
/* 1689 */     float iuf = this.iuadd;
/* 1690 */     float ivf = this.ivadd;
/*      */     
/* 1692 */     boolean tint = (this.m_fill & 0xFFFFFF) != 16777215;
/* 1693 */     int rtint = this.m_fill >> 16 & 0xFF;
/* 1694 */     int gtint = this.m_fill >> 8 & 0xFF;
/* 1695 */     int btint = this.m_fill & 0xFF;
/*      */     
/* 1697 */     int ypixel = ytop / this.SCREEN_WIDTH;
/* 1698 */     int lastRowStart = this.m_texture.length - this.TEX_WIDTH - 2;
/*      */     
/* 1700 */     boolean accurateMode = this.parent.hints[7];
/* 1701 */     float screenx = 0.0F;float screeny = 0.0F;float screenz = 0.0F;
/* 1702 */     float a = 0.0F;float b = 0.0F;float c = 0.0F;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1709 */     int linearInterpPower = TEX_INTERP_POWER;
/* 1710 */     int linearInterpLength = 1 << linearInterpPower;
/* 1711 */     if (accurateMode) {
/* 1712 */       if (precomputeAccurateTexturing()) {
/* 1713 */         this.newax *= linearInterpLength;
/* 1714 */         this.newbx *= linearInterpLength;
/* 1715 */         this.newcx *= linearInterpLength;
/* 1716 */         screenz = this.nearPlaneDepth;
/* 1717 */         this.firstSegment = false;
/*      */       } else {
/* 1719 */         accurateMode = false;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1724 */     while (ytop < ybottom) {
/* 1725 */       int xstart = (int)(this.xleft + 0.5F);
/* 1726 */       if (xstart < 0) { xstart = 0;
/*      */       }
/* 1728 */       int xpixel = xstart;
/*      */       
/* 1730 */       int xend = (int)(this.xrght + 0.5F);
/* 1731 */       if (xend > this.SCREEN_WIDTH) xend = this.SCREEN_WIDTH;
/* 1732 */       float xdiff = xstart + 0.5F - this.xleft;
/* 1733 */       int iu = (int)(iuf * xdiff + this.uleft);
/* 1734 */       int iv = (int)(ivf * xdiff + this.vleft);
/* 1735 */       float iz = this.izadd * xdiff + this.zleft;
/* 1736 */       xstart += ytop;
/* 1737 */       xend += ytop;
/*      */       
/* 1739 */       if (accurateMode)
/*      */       {
/* 1741 */         screenx = this.xmult * (xpixel + 0.5F - this.SCREEN_WIDTH / 2.0F);
/* 1742 */         screeny = this.ymult * (ypixel + 0.5F - this.SCREEN_HEIGHT / 2.0F);
/* 1743 */         a = screenx * this.ax + screeny * this.ay + screenz * this.az;
/* 1744 */         b = screenx * this.bx + screeny * this.by + screenz * this.bz;
/* 1745 */         c = screenx * this.cx + screeny * this.cy + screenz * this.cz;
/*      */       }
/*      */       
/*      */ 
/* 1749 */       boolean goingIn = (this.newcx > 0.0F ? 1 : 0) != (c > 0.0F ? 1 : 0);
/*      */       
/*      */ 
/* 1752 */       int interpCounter = 0;
/* 1753 */       int deltaU = 0;int deltaV = 0;
/*      */       
/*      */ 
/* 1756 */       float fu = 0.0F;float fv = 0.0F;
/* 1757 */       float oldfu = 0.0F;float oldfv = 0.0F;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1766 */       if ((accurateMode) && (goingIn))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1772 */         int rightOffset = (xend - xstart - 1) % linearInterpLength;
/* 1773 */         int leftOffset = linearInterpLength - rightOffset;
/* 1774 */         float rightOffset2 = rightOffset / linearInterpLength;
/* 1775 */         float leftOffset2 = leftOffset / linearInterpLength;
/* 1776 */         interpCounter = leftOffset;
/*      */         
/*      */ 
/* 1779 */         float ao = a - leftOffset2 * this.newax;
/* 1780 */         float bo = b - leftOffset2 * this.newbx;
/* 1781 */         float co = c - leftOffset2 * this.newcx;
/* 1782 */         float oneoverc = 65536.0F / co;
/* 1783 */         oldfu = ao * oneoverc;oldfv = bo * oneoverc;
/*      */         
/*      */ 
/* 1786 */         a += rightOffset2 * this.newax;
/* 1787 */         b += rightOffset2 * this.newbx;
/* 1788 */         c += rightOffset2 * this.newcx;
/* 1789 */         oneoverc = 65536.0F / c;
/* 1790 */         fu = a * oneoverc;fv = b * oneoverc;
/*      */         
/*      */ 
/* 1793 */         deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 1794 */         deltaV = (int)(fv - oldfv) >> linearInterpPower;
/* 1795 */         iu = (int)oldfu + (leftOffset - 1) * deltaU;iv = (int)oldfv + (leftOffset - 1) * deltaV;
/*      */       }
/*      */       else {
/* 1798 */         float preoneoverc = 65536.0F / c;
/* 1799 */         fu = a * preoneoverc;
/* 1800 */         fv = b * preoneoverc;
/*      */       }
/* 1803 */       for (; 
/* 1803 */           xstart < xend; xstart++)
/*      */       {
/* 1805 */         if (accurateMode)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1817 */           if (interpCounter == linearInterpLength) interpCounter = 0;
/* 1818 */           if (interpCounter == 0)
/*      */           {
/* 1820 */             a += this.newax;
/* 1821 */             b += this.newbx;
/* 1822 */             c += this.newcx;
/* 1823 */             float oneoverc = 65536.0F / c;
/* 1824 */             oldfu = fu;oldfv = fv;
/* 1825 */             fu = a * oneoverc;fv = b * oneoverc;
/* 1826 */             iu = (int)oldfu;iv = (int)oldfv;
/* 1827 */             deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 1828 */             deltaV = (int)(fv - oldfv) >> linearInterpPower;
/*      */           } else {
/* 1830 */             iu += deltaU;
/* 1831 */             iv += deltaV;
/*      */           }
/* 1833 */           interpCounter++;
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         try
/*      */         {
/* 1863 */           if ((this.noDepthTest) || (iz <= this.m_zbuffer[xstart])) {
/* 1864 */             this.m_zbuffer[xstart] = iz;
/* 1865 */             if (this.m_bilinear)
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1871 */               int ofs = (iv >> 16) * this.TEX_WIDTH + (iu >> 16);
/* 1872 */               int iui = (iu & 0xFFFF) >> 9;
/* 1873 */               int ivi = (iv & 0xFFFF) >> 9;
/*      */               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1879 */               int pix0 = this.m_texture[ofs];
/* 1880 */               int pix1 = this.m_texture[(ofs + 1)];
/* 1881 */               if (ofs < lastRowStart) ofs += this.TEX_WIDTH;
/* 1882 */               int pix2 = this.m_texture[ofs];
/* 1883 */               int pix3 = this.m_texture[(ofs + 1)];
/*      */               
/*      */ 
/* 1886 */               int red0 = pix0 & 0xFF0000;
/* 1887 */               int red2 = pix2 & 0xFF0000;
/* 1888 */               int up = red0 + (((pix1 & 0xFF0000) - red0) * iui >> 7);
/* 1889 */               int dn = red2 + (((pix3 & 0xFF0000) - red2) * iui >> 7);
/* 1890 */               int red = up + ((dn - up) * ivi >> 7);
/* 1891 */               if (tint) { red = red * rtint >> 8 & 0xFF0000;
/*      */               }
/*      */               
/* 1894 */               red0 = pix0 & 0xFF00;
/* 1895 */               red2 = pix2 & 0xFF00;
/* 1896 */               up = red0 + (((pix1 & 0xFF00) - red0) * iui >> 7);
/* 1897 */               dn = red2 + (((pix3 & 0xFF00) - red2) * iui >> 7);
/* 1898 */               int grn = up + ((dn - up) * ivi >> 7);
/* 1899 */               if (tint) { grn = grn * gtint >> 8 & 0xFF00;
/*      */               }
/*      */               
/* 1902 */               red0 = pix0 & 0xFF;
/* 1903 */               red2 = pix2 & 0xFF;
/* 1904 */               up = red0 + (((pix1 & 0xFF) - red0) * iui >> 7);
/* 1905 */               dn = red2 + (((pix3 & 0xFF) - red2) * iui >> 7);
/* 1906 */               int blu = up + ((dn - up) * ivi >> 7);
/* 1907 */               if (tint) { blu = blu * btint >> 8 & 0xFF;
/*      */               }
/*      */               
/* 1910 */               this.m_pixels[xstart] = 
/* 1911 */                 (0xFF000000 | red & 0xFF0000 | grn & 0xFF00 | blu & 0xFF);
/*      */             }
/*      */             else {
/* 1914 */               this.m_pixels[xstart] = this.m_texture[((iv >> 16) * this.TEX_WIDTH + (iu >> 16))];
/*      */             }
/*      */           }
/*      */         }
/*      */         catch (Exception localException) {}
/* 1919 */         iz += this.izadd;
/* 1920 */         xpixel++;
/* 1921 */         if (!accurateMode) {
/* 1922 */           iu += this.iuadd;
/* 1923 */           iv += this.ivadd;
/*      */         }
/*      */       }
/* 1926 */       ypixel++;
/* 1927 */       ytop += this.SCREEN_WIDTH;
/* 1928 */       this.xleft += leftadd;
/* 1929 */       this.xrght += rghtadd;
/* 1930 */       this.zleft += this.zleftadd;
/* 1931 */       this.uleft += this.uleftadd;
/* 1932 */       this.vleft += this.vleftadd;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void drawsegment_texture24_alpha(float leftadd, float rghtadd, int ytop, int ybottom)
/*      */   {
/* 1946 */     int ypixel = ytop;
/* 1947 */     int lastRowStart = this.m_texture.length - this.TEX_WIDTH - 2;
/* 1948 */     boolean accurateMode = this.parent.hints[7];
/* 1949 */     float screenx = 0.0F;float screeny = 0.0F;float screenz = 0.0F;
/* 1950 */     float a = 0.0F;float b = 0.0F;float c = 0.0F;
/* 1951 */     int linearInterpPower = TEX_INTERP_POWER;
/* 1952 */     int linearInterpLength = 1 << linearInterpPower;
/* 1953 */     if (accurateMode) {
/* 1954 */       if (precomputeAccurateTexturing()) {
/* 1955 */         this.newax *= linearInterpLength;
/* 1956 */         this.newbx *= linearInterpLength;
/* 1957 */         this.newcx *= linearInterpLength;
/* 1958 */         screenz = this.nearPlaneDepth;
/* 1959 */         this.firstSegment = false;
/*      */       } else {
/* 1961 */         accurateMode = false;
/*      */       }
/*      */     }
/*      */     
/* 1965 */     boolean tint = (this.m_fill & 0xFFFFFF) != 16777215;
/* 1966 */     int rtint = this.m_fill >> 16 & 0xFF;
/* 1967 */     int gtint = this.m_fill >> 8 & 0xFF;
/* 1968 */     int btint = this.m_fill & 0xFF;
/*      */     
/* 1970 */     ytop *= this.SCREEN_WIDTH;
/* 1971 */     ybottom *= this.SCREEN_WIDTH;
/*      */     
/*      */ 
/* 1974 */     float iuf = this.iuadd;
/* 1975 */     float ivf = this.ivadd;
/* 1976 */     float iaf = this.iaadd;
/*      */     
/* 1978 */     while (ytop < ybottom) {
/* 1979 */       int xstart = (int)(this.xleft + 0.5F);
/* 1980 */       if (xstart < 0) {
/* 1981 */         xstart = 0;
/*      */       }
/* 1983 */       int xpixel = xstart;
/*      */       
/* 1985 */       int xend = (int)(this.xrght + 0.5F);
/* 1986 */       if (xend > this.SCREEN_WIDTH) {
/* 1987 */         xend = this.SCREEN_WIDTH;
/*      */       }
/* 1989 */       float xdiff = xstart + 0.5F - this.xleft;
/* 1990 */       int iu = (int)(iuf * xdiff + this.uleft);
/* 1991 */       int iv = (int)(ivf * xdiff + this.vleft);
/* 1992 */       int ia = (int)(iaf * xdiff + this.aleft);
/* 1993 */       float iz = this.izadd * xdiff + this.zleft;
/*      */       
/* 1995 */       xstart += ytop;
/* 1996 */       xend += ytop;
/*      */       
/* 1998 */       if (accurateMode) {
/* 1999 */         screenx = this.xmult * (xpixel + 0.5F - this.SCREEN_WIDTH / 2.0F);
/* 2000 */         screeny = this.ymult * (ypixel + 0.5F - this.SCREEN_HEIGHT / 2.0F);
/* 2001 */         a = screenx * this.ax + screeny * this.ay + screenz * this.az;
/* 2002 */         b = screenx * this.bx + screeny * this.by + screenz * this.bz;
/* 2003 */         c = screenx * this.cx + screeny * this.cy + screenz * this.cz;
/*      */       }
/* 2005 */       boolean goingIn = (this.newcx > 0.0F ? 1 : 0) != (c > 0.0F ? 1 : 0);
/* 2006 */       int interpCounter = 0;
/* 2007 */       int deltaU = 0;int deltaV = 0;
/* 2008 */       float fu = 0.0F;float fv = 0.0F;
/* 2009 */       float oldfu = 0.0F;float oldfv = 0.0F;
/*      */       
/* 2011 */       if ((accurateMode) && (goingIn)) {
/* 2012 */         int rightOffset = (xend - xstart - 1) % linearInterpLength;
/* 2013 */         int leftOffset = linearInterpLength - rightOffset;
/* 2014 */         float rightOffset2 = rightOffset / linearInterpLength;
/* 2015 */         float leftOffset2 = leftOffset / linearInterpLength;
/* 2016 */         interpCounter = leftOffset;
/* 2017 */         float ao = a - leftOffset2 * this.newax;
/* 2018 */         float bo = b - leftOffset2 * this.newbx;
/* 2019 */         float co = c - leftOffset2 * this.newcx;
/* 2020 */         float oneoverc = 65536.0F / co;
/* 2021 */         oldfu = ao * oneoverc;oldfv = bo * oneoverc;
/* 2022 */         a += rightOffset2 * this.newax;
/* 2023 */         b += rightOffset2 * this.newbx;
/* 2024 */         c += rightOffset2 * this.newcx;
/* 2025 */         oneoverc = 65536.0F / c;
/* 2026 */         fu = a * oneoverc;fv = b * oneoverc;
/* 2027 */         deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 2028 */         deltaV = (int)(fv - oldfv) >> linearInterpPower;
/* 2029 */         iu = (int)oldfu + (leftOffset - 1) * deltaU;iv = (int)oldfv + (leftOffset - 1) * deltaV;
/*      */       } else {
/* 2031 */         float preoneoverc = 65536.0F / c;
/* 2032 */         fu = a * preoneoverc;
/* 2033 */         fv = b * preoneoverc;
/*      */       }
/* 2037 */       for (; 
/*      */           
/* 2037 */           xstart < xend; xstart++) {
/* 2038 */         if (accurateMode) {
/* 2039 */           if (interpCounter == linearInterpLength) interpCounter = 0;
/* 2040 */           if (interpCounter == 0) {
/* 2041 */             a += this.newax;
/* 2042 */             b += this.newbx;
/* 2043 */             c += this.newcx;
/* 2044 */             float oneoverc = 65536.0F / c;
/* 2045 */             oldfu = fu;oldfv = fv;
/* 2046 */             fu = a * oneoverc;fv = b * oneoverc;
/* 2047 */             iu = (int)oldfu;iv = (int)oldfv;
/* 2048 */             deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 2049 */             deltaV = (int)(fv - oldfv) >> linearInterpPower;
/*      */           } else {
/* 2051 */             iu += deltaU;
/* 2052 */             iv += deltaV;
/*      */           }
/* 2054 */           interpCounter++;
/*      */         }
/*      */         try
/*      */         {
/* 2058 */           if ((this.noDepthTest) || (iz <= this.m_zbuffer[xstart]))
/*      */           {
/*      */ 
/*      */ 
/* 2062 */             int al = ia >> 16;
/*      */             
/* 2064 */             if (this.m_bilinear) {
/* 2065 */               int ofs = (iv >> 16) * this.TEX_WIDTH + (iu >> 16);
/* 2066 */               int iui = (iu & 0xFFFF) >> 9;
/* 2067 */               int ivi = (iv & 0xFFFF) >> 9;
/*      */               
/*      */ 
/* 2070 */               int pix0 = this.m_texture[ofs];
/* 2071 */               int pix1 = this.m_texture[(ofs + 1)];
/* 2072 */               if (ofs < lastRowStart) ofs += this.TEX_WIDTH;
/* 2073 */               int pix2 = this.m_texture[ofs];
/* 2074 */               int pix3 = this.m_texture[(ofs + 1)];
/*      */               
/*      */ 
/* 2077 */               int red0 = pix0 & 0xFF0000;
/* 2078 */               int red2 = pix2 & 0xFF0000;
/* 2079 */               int up = red0 + (((pix1 & 0xFF0000) - red0) * iui >> 7);
/* 2080 */               int dn = red2 + (((pix3 & 0xFF0000) - red2) * iui >> 7);
/* 2081 */               int red = up + ((dn - up) * ivi >> 7);
/* 2082 */               if (tint) { red = red * rtint >> 8 & 0xFF0000;
/*      */               }
/*      */               
/* 2085 */               red0 = pix0 & 0xFF00;
/* 2086 */               red2 = pix2 & 0xFF00;
/* 2087 */               up = red0 + (((pix1 & 0xFF00) - red0) * iui >> 7);
/* 2088 */               dn = red2 + (((pix3 & 0xFF00) - red2) * iui >> 7);
/* 2089 */               int grn = up + ((dn - up) * ivi >> 7);
/* 2090 */               if (tint) { grn = grn * gtint >> 8 & 0xFF00;
/*      */               }
/*      */               
/* 2093 */               red0 = pix0 & 0xFF;
/* 2094 */               red2 = pix2 & 0xFF;
/* 2095 */               up = red0 + (((pix1 & 0xFF) - red0) * iui >> 7);
/* 2096 */               dn = red2 + (((pix3 & 0xFF) - red2) * iui >> 7);
/* 2097 */               int blu = up + ((dn - up) * ivi >> 7);
/* 2098 */               if (tint) { blu = blu * btint >> 8 & 0xFF;
/*      */               }
/*      */               
/* 2101 */               int bb = this.m_pixels[xstart];
/* 2102 */               int br = bb & 0xFF0000;
/* 2103 */               int bg = bb & 0xFF00;
/* 2104 */               bb &= 0xFF;
/* 2105 */               this.m_pixels[xstart] = 
/*      */               
/*      */ 
/* 2108 */                 (0xFF000000 | br + ((red - br) * al >> 8) & 0xFF0000 | bg + ((grn - bg) * al >> 8) & 0xFF00 | bb + ((blu - bb) * al >> 8) & 0xFF);
/*      */             }
/*      */             else {
/* 2111 */               int red = this.m_texture[((iv >> 16) * this.TEX_WIDTH + (iu >> 16))];
/* 2112 */               int grn = red & 0xFF00;
/* 2113 */               int blu = red & 0xFF;
/* 2114 */               red &= 0xFF0000;
/*      */               
/*      */ 
/* 2117 */               int bb = this.m_pixels[xstart];
/* 2118 */               int br = bb & 0xFF0000;
/* 2119 */               int bg = bb & 0xFF00;
/* 2120 */               bb &= 0xFF;
/* 2121 */               this.m_pixels[xstart] = 
/*      */               
/*      */ 
/* 2124 */                 (0xFF000000 | br + ((red - br) * al >> 8) & 0xFF0000 | bg + ((grn - bg) * al >> 8) & 0xFF00 | bb + ((blu - bb) * al >> 8) & 0xFF);
/*      */             }
/*      */           }
/*      */         }
/*      */         catch (Exception localException) {}
/*      */         
/* 2130 */         xpixel++;
/* 2131 */         if (!accurateMode) {
/* 2132 */           iu += this.iuadd;
/* 2133 */           iv += this.ivadd;
/*      */         }
/* 2135 */         ia += this.iaadd;
/* 2136 */         iz += this.izadd;
/*      */       }
/* 2138 */       ypixel++;
/*      */       
/* 2140 */       ytop += this.SCREEN_WIDTH;
/*      */       
/* 2142 */       this.xleft += leftadd;
/* 2143 */       this.xrght += rghtadd;
/* 2144 */       this.uleft += this.uleftadd;
/* 2145 */       this.vleft += this.vleftadd;
/* 2146 */       this.zleft += this.zleftadd;
/* 2147 */       this.aleft += this.aleftadd;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void drawsegment_texture32(float leftadd, float rghtadd, int ytop, int ybottom)
/*      */   {
/* 2159 */     int ypixel = ytop;
/* 2160 */     int lastRowStart = this.m_texture.length - this.TEX_WIDTH - 2;
/* 2161 */     boolean accurateMode = this.parent.hints[7];
/* 2162 */     float screenx = 0.0F;float screeny = 0.0F;float screenz = 0.0F;
/* 2163 */     float a = 0.0F;float b = 0.0F;float c = 0.0F;
/* 2164 */     int linearInterpPower = TEX_INTERP_POWER;
/* 2165 */     int linearInterpLength = 1 << linearInterpPower;
/* 2166 */     if (accurateMode) {
/* 2167 */       if (precomputeAccurateTexturing()) {
/* 2168 */         this.newax *= linearInterpLength;
/* 2169 */         this.newbx *= linearInterpLength;
/* 2170 */         this.newcx *= linearInterpLength;
/* 2171 */         screenz = this.nearPlaneDepth;
/* 2172 */         this.firstSegment = false;
/*      */       } else {
/* 2174 */         accurateMode = false;
/*      */       }
/*      */     }
/*      */     
/* 2178 */     ytop *= this.SCREEN_WIDTH;
/* 2179 */     ybottom *= this.SCREEN_WIDTH;
/*      */     
/*      */ 
/* 2182 */     boolean tint = this.m_fill != -1;
/* 2183 */     int rtint = this.m_fill >> 16 & 0xFF;
/* 2184 */     int gtint = this.m_fill >> 8 & 0xFF;
/* 2185 */     int btint = this.m_fill & 0xFF;
/*      */     
/* 2187 */     float iuf = this.iuadd;
/* 2188 */     float ivf = this.ivadd;
/*      */     
/* 2190 */     while (ytop < ybottom) {
/* 2191 */       int xstart = (int)(this.xleft + 0.5F);
/* 2192 */       if (xstart < 0) {
/* 2193 */         xstart = 0;
/*      */       }
/* 2195 */       int xpixel = xstart;
/*      */       
/* 2197 */       int xend = (int)(this.xrght + 0.5F);
/* 2198 */       if (xend > this.SCREEN_WIDTH) {
/* 2199 */         xend = this.SCREEN_WIDTH;
/*      */       }
/* 2201 */       float xdiff = xstart + 0.5F - this.xleft;
/* 2202 */       int iu = (int)(iuf * xdiff + this.uleft);
/* 2203 */       int iv = (int)(ivf * xdiff + this.vleft);
/* 2204 */       float iz = this.izadd * xdiff + this.zleft;
/*      */       
/* 2206 */       xstart += ytop;
/* 2207 */       xend += ytop;
/*      */       
/* 2209 */       if (accurateMode) {
/* 2210 */         screenx = this.xmult * (xpixel + 0.5F - this.SCREEN_WIDTH / 2.0F);
/* 2211 */         screeny = this.ymult * (ypixel + 0.5F - this.SCREEN_HEIGHT / 2.0F);
/* 2212 */         a = screenx * this.ax + screeny * this.ay + screenz * this.az;
/* 2213 */         b = screenx * this.bx + screeny * this.by + screenz * this.bz;
/* 2214 */         c = screenx * this.cx + screeny * this.cy + screenz * this.cz;
/*      */       }
/* 2216 */       boolean goingIn = (this.newcx > 0.0F ? 1 : 0) != (c > 0.0F ? 1 : 0);
/* 2217 */       int interpCounter = 0;
/* 2218 */       int deltaU = 0;int deltaV = 0;
/* 2219 */       float fu = 0.0F;float fv = 0.0F;
/* 2220 */       float oldfu = 0.0F;float oldfv = 0.0F;
/*      */       
/* 2222 */       if ((accurateMode) && (goingIn)) {
/* 2223 */         int rightOffset = (xend - xstart - 1) % linearInterpLength;
/* 2224 */         int leftOffset = linearInterpLength - rightOffset;
/* 2225 */         float rightOffset2 = rightOffset / linearInterpLength;
/* 2226 */         float leftOffset2 = leftOffset / linearInterpLength;
/* 2227 */         interpCounter = leftOffset;
/* 2228 */         float ao = a - leftOffset2 * this.newax;
/* 2229 */         float bo = b - leftOffset2 * this.newbx;
/* 2230 */         float co = c - leftOffset2 * this.newcx;
/* 2231 */         float oneoverc = 65536.0F / co;
/* 2232 */         oldfu = ao * oneoverc;oldfv = bo * oneoverc;
/* 2233 */         a += rightOffset2 * this.newax;
/* 2234 */         b += rightOffset2 * this.newbx;
/* 2235 */         c += rightOffset2 * this.newcx;
/* 2236 */         oneoverc = 65536.0F / c;
/* 2237 */         fu = a * oneoverc;fv = b * oneoverc;
/* 2238 */         deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 2239 */         deltaV = (int)(fv - oldfv) >> linearInterpPower;
/* 2240 */         iu = (int)oldfu + (leftOffset - 1) * deltaU;iv = (int)oldfv + (leftOffset - 1) * deltaV;
/*      */       } else {
/* 2242 */         float preoneoverc = 65536.0F / c;
/* 2243 */         fu = a * preoneoverc;
/* 2244 */         fv = b * preoneoverc;
/*      */       }
/* 2248 */       for (; 
/*      */           
/* 2248 */           xstart < xend; xstart++) {
/* 2249 */         if (accurateMode) {
/* 2250 */           if (interpCounter == linearInterpLength) interpCounter = 0;
/* 2251 */           if (interpCounter == 0) {
/* 2252 */             a += this.newax;
/* 2253 */             b += this.newbx;
/* 2254 */             c += this.newcx;
/* 2255 */             float oneoverc = 65536.0F / c;
/* 2256 */             oldfu = fu;oldfv = fv;
/* 2257 */             fu = a * oneoverc;fv = b * oneoverc;
/* 2258 */             iu = (int)oldfu;iv = (int)oldfv;
/* 2259 */             deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 2260 */             deltaV = (int)(fv - oldfv) >> linearInterpPower;
/*      */           } else {
/* 2262 */             iu += deltaU;
/* 2263 */             iv += deltaV;
/*      */           }
/* 2265 */           interpCounter++;
/*      */         }
/*      */         
/*      */         try
/*      */         {
/* 2270 */           if ((this.noDepthTest) || (iz <= this.m_zbuffer[xstart]))
/*      */           {
/*      */ 
/* 2273 */             if (this.m_bilinear) {
/* 2274 */               int ofs = (iv >> 16) * this.TEX_WIDTH + (iu >> 16);
/* 2275 */               int iui = (iu & 0xFFFF) >> 9;
/* 2276 */               int ivi = (iv & 0xFFFF) >> 9;
/*      */               
/*      */ 
/* 2279 */               int pix0 = this.m_texture[ofs];
/* 2280 */               int pix1 = this.m_texture[(ofs + 1)];
/* 2281 */               if (ofs < lastRowStart) ofs += this.TEX_WIDTH;
/* 2282 */               int pix2 = this.m_texture[ofs];
/* 2283 */               int pix3 = this.m_texture[(ofs + 1)];
/*      */               
/*      */ 
/* 2286 */               int red0 = pix0 & 0xFF0000;
/* 2287 */               int red2 = pix2 & 0xFF0000;
/* 2288 */               int up = red0 + (((pix1 & 0xFF0000) - red0) * iui >> 7);
/* 2289 */               int dn = red2 + (((pix3 & 0xFF0000) - red2) * iui >> 7);
/* 2290 */               int red = up + ((dn - up) * ivi >> 7);
/* 2291 */               if (tint) { red = red * rtint >> 8 & 0xFF0000;
/*      */               }
/*      */               
/* 2294 */               red0 = pix0 & 0xFF00;
/* 2295 */               red2 = pix2 & 0xFF00;
/* 2296 */               up = red0 + (((pix1 & 0xFF00) - red0) * iui >> 7);
/* 2297 */               dn = red2 + (((pix3 & 0xFF00) - red2) * iui >> 7);
/* 2298 */               int grn = up + ((dn - up) * ivi >> 7);
/* 2299 */               if (tint) { grn = grn * gtint >> 8 & 0xFF00;
/*      */               }
/*      */               
/* 2302 */               red0 = pix0 & 0xFF;
/* 2303 */               red2 = pix2 & 0xFF;
/* 2304 */               up = red0 + (((pix1 & 0xFF) - red0) * iui >> 7);
/* 2305 */               dn = red2 + (((pix3 & 0xFF) - red2) * iui >> 7);
/* 2306 */               int blu = up + ((dn - up) * ivi >> 7);
/* 2307 */               if (tint) { blu = blu * btint >> 8 & 0xFF;
/*      */               }
/*      */               
/* 2310 */               pix0 >>>= 24;
/* 2311 */               pix2 >>>= 24;
/* 2312 */               up = pix0 + (((pix1 >>> 24) - pix0) * iui >> 7);
/* 2313 */               dn = pix2 + (((pix3 >>> 24) - pix2) * iui >> 7);
/* 2314 */               int al = up + ((dn - up) * ivi >> 7);
/*      */               
/*      */ 
/* 2317 */               int bb = this.m_pixels[xstart];
/* 2318 */               int br = bb & 0xFF0000;
/* 2319 */               int bg = bb & 0xFF00;
/* 2320 */               bb &= 0xFF;
/* 2321 */               this.m_pixels[xstart] = 
/*      */               
/*      */ 
/* 2324 */                 (0xFF000000 | br + ((red - br) * al >> 8) & 0xFF0000 | bg + ((grn - bg) * al >> 8) & 0xFF00 | bb + ((blu - bb) * al >> 8) & 0xFF);
/*      */             } else {
/* 2326 */               int red = this.m_texture[((iv >> 16) * this.TEX_WIDTH + (iu >> 16))];
/* 2327 */               int al = red >>> 24;
/* 2328 */               int grn = red & 0xFF00;
/* 2329 */               int blu = red & 0xFF;
/* 2330 */               red &= 0xFF0000;
/*      */               
/*      */ 
/* 2333 */               int bb = this.m_pixels[xstart];
/* 2334 */               int br = bb & 0xFF0000;
/* 2335 */               int bg = bb & 0xFF00;
/* 2336 */               bb &= 0xFF;
/* 2337 */               this.m_pixels[xstart] = 
/*      */               
/*      */ 
/* 2340 */                 (0xFF000000 | br + ((red - br) * al >> 8) & 0xFF0000 | bg + ((grn - bg) * al >> 8) & 0xFF00 | bb + ((blu - bb) * al >> 8) & 0xFF);
/*      */             }
/*      */           }
/*      */         }
/*      */         catch (Exception localException) {}
/* 2345 */         xpixel++;
/* 2346 */         if (!accurateMode) {
/* 2347 */           iu += this.iuadd;
/* 2348 */           iv += this.ivadd;
/*      */         }
/* 2350 */         iz += this.izadd;
/*      */       }
/* 2352 */       ypixel++;
/*      */       
/* 2354 */       ytop += this.SCREEN_WIDTH;
/*      */       
/* 2356 */       this.xleft += leftadd;
/* 2357 */       this.xrght += rghtadd;
/* 2358 */       this.uleft += this.uleftadd;
/* 2359 */       this.vleft += this.vleftadd;
/* 2360 */       this.zleft += this.zleftadd;
/* 2361 */       this.aleft += this.aleftadd;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void drawsegment_texture32_alpha(float leftadd, float rghtadd, int ytop, int ybottom)
/*      */   {
/* 2375 */     int ypixel = ytop;
/* 2376 */     int lastRowStart = this.m_texture.length - this.TEX_WIDTH - 2;
/* 2377 */     boolean accurateMode = this.parent.hints[7];
/* 2378 */     float screenx = 0.0F;float screeny = 0.0F;float screenz = 0.0F;
/* 2379 */     float a = 0.0F;float b = 0.0F;float c = 0.0F;
/* 2380 */     int linearInterpPower = TEX_INTERP_POWER;
/* 2381 */     int linearInterpLength = 1 << linearInterpPower;
/* 2382 */     if (accurateMode) {
/* 2383 */       if (precomputeAccurateTexturing()) {
/* 2384 */         this.newax *= linearInterpLength;
/* 2385 */         this.newbx *= linearInterpLength;
/* 2386 */         this.newcx *= linearInterpLength;
/* 2387 */         screenz = this.nearPlaneDepth;
/* 2388 */         this.firstSegment = false;
/*      */       } else {
/* 2390 */         accurateMode = false;
/*      */       }
/*      */     }
/*      */     
/* 2394 */     ytop *= this.SCREEN_WIDTH;
/* 2395 */     ybottom *= this.SCREEN_WIDTH;
/*      */     
/*      */ 
/* 2398 */     boolean tint = (this.m_fill & 0xFFFFFF) != 16777215;
/* 2399 */     int rtint = this.m_fill >> 16 & 0xFF;
/* 2400 */     int gtint = this.m_fill >> 8 & 0xFF;
/* 2401 */     int btint = this.m_fill & 0xFF;
/*      */     
/* 2403 */     float iuf = this.iuadd;
/* 2404 */     float ivf = this.ivadd;
/* 2405 */     float iaf = this.iaadd;
/*      */     
/* 2407 */     while (ytop < ybottom) {
/* 2408 */       int xstart = (int)(this.xleft + 0.5F);
/* 2409 */       if (xstart < 0) {
/* 2410 */         xstart = 0;
/*      */       }
/* 2412 */       int xpixel = xstart;
/*      */       
/* 2414 */       int xend = (int)(this.xrght + 0.5F);
/* 2415 */       if (xend > this.SCREEN_WIDTH) {
/* 2416 */         xend = this.SCREEN_WIDTH;
/*      */       }
/* 2418 */       float xdiff = xstart + 0.5F - this.xleft;
/* 2419 */       int iu = (int)(iuf * xdiff + this.uleft);
/* 2420 */       int iv = (int)(ivf * xdiff + this.vleft);
/* 2421 */       int ia = (int)(iaf * xdiff + this.aleft);
/* 2422 */       float iz = this.izadd * xdiff + this.zleft;
/*      */       
/* 2424 */       xstart += ytop;
/* 2425 */       xend += ytop;
/*      */       
/* 2427 */       if (accurateMode) {
/* 2428 */         screenx = this.xmult * (xpixel + 0.5F - this.SCREEN_WIDTH / 2.0F);
/* 2429 */         screeny = this.ymult * (ypixel + 0.5F - this.SCREEN_HEIGHT / 2.0F);
/* 2430 */         a = screenx * this.ax + screeny * this.ay + screenz * this.az;
/* 2431 */         b = screenx * this.bx + screeny * this.by + screenz * this.bz;
/* 2432 */         c = screenx * this.cx + screeny * this.cy + screenz * this.cz;
/*      */       }
/* 2434 */       boolean goingIn = (this.newcx > 0.0F ? 1 : 0) != (c > 0.0F ? 1 : 0);
/* 2435 */       int interpCounter = 0;
/* 2436 */       int deltaU = 0;int deltaV = 0;
/* 2437 */       float fu = 0.0F;float fv = 0.0F;
/* 2438 */       float oldfu = 0.0F;float oldfv = 0.0F;
/*      */       
/* 2440 */       if ((accurateMode) && (goingIn)) {
/* 2441 */         int rightOffset = (xend - xstart - 1) % linearInterpLength;
/* 2442 */         int leftOffset = linearInterpLength - rightOffset;
/* 2443 */         float rightOffset2 = rightOffset / linearInterpLength;
/* 2444 */         float leftOffset2 = leftOffset / linearInterpLength;
/* 2445 */         interpCounter = leftOffset;
/* 2446 */         float ao = a - leftOffset2 * this.newax;
/* 2447 */         float bo = b - leftOffset2 * this.newbx;
/* 2448 */         float co = c - leftOffset2 * this.newcx;
/* 2449 */         float oneoverc = 65536.0F / co;
/* 2450 */         oldfu = ao * oneoverc;oldfv = bo * oneoverc;
/* 2451 */         a += rightOffset2 * this.newax;
/* 2452 */         b += rightOffset2 * this.newbx;
/* 2453 */         c += rightOffset2 * this.newcx;
/* 2454 */         oneoverc = 65536.0F / c;
/* 2455 */         fu = a * oneoverc;fv = b * oneoverc;
/* 2456 */         deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 2457 */         deltaV = (int)(fv - oldfv) >> linearInterpPower;
/* 2458 */         iu = (int)oldfu + (leftOffset - 1) * deltaU;iv = (int)oldfv + (leftOffset - 1) * deltaV;
/*      */       } else {
/* 2460 */         float preoneoverc = 65536.0F / c;
/* 2461 */         fu = a * preoneoverc;
/* 2462 */         fv = b * preoneoverc;
/*      */       }
/* 2465 */       for (; 
/* 2465 */           xstart < xend; xstart++) {
/* 2466 */         if (accurateMode) {
/* 2467 */           if (interpCounter == linearInterpLength) interpCounter = 0;
/* 2468 */           if (interpCounter == 0) {
/* 2469 */             a += this.newax;
/* 2470 */             b += this.newbx;
/* 2471 */             c += this.newcx;
/* 2472 */             float oneoverc = 65536.0F / c;
/* 2473 */             oldfu = fu;oldfv = fv;
/* 2474 */             fu = a * oneoverc;fv = b * oneoverc;
/* 2475 */             iu = (int)oldfu;iv = (int)oldfv;
/* 2476 */             deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 2477 */             deltaV = (int)(fv - oldfv) >> linearInterpPower;
/*      */           } else {
/* 2479 */             iu += deltaU;
/* 2480 */             iv += deltaV;
/*      */           }
/* 2482 */           interpCounter++;
/*      */         }
/*      */         
/*      */         try
/*      */         {
/* 2487 */           if ((this.noDepthTest) || (iz <= this.m_zbuffer[xstart]))
/*      */           {
/*      */ 
/*      */ 
/* 2491 */             int al = ia >> 16;
/*      */             
/* 2493 */             if (this.m_bilinear) {
/* 2494 */               int ofs = (iv >> 16) * this.TEX_WIDTH + (iu >> 16);
/* 2495 */               int iui = (iu & 0xFFFF) >> 9;
/* 2496 */               int ivi = (iv & 0xFFFF) >> 9;
/*      */               
/*      */ 
/* 2499 */               int pix0 = this.m_texture[ofs];
/* 2500 */               int pix1 = this.m_texture[(ofs + 1)];
/* 2501 */               if (ofs < lastRowStart) ofs += this.TEX_WIDTH;
/* 2502 */               int pix2 = this.m_texture[ofs];
/* 2503 */               int pix3 = this.m_texture[(ofs + 1)];
/*      */               
/*      */ 
/* 2506 */               int red0 = pix0 & 0xFF0000;
/* 2507 */               int red2 = pix2 & 0xFF0000;
/* 2508 */               int up = red0 + (((pix1 & 0xFF0000) - red0) * iui >> 7);
/* 2509 */               int dn = red2 + (((pix3 & 0xFF0000) - red2) * iui >> 7);
/* 2510 */               int red = up + ((dn - up) * ivi >> 7);
/* 2511 */               if (tint) { red = red * rtint >> 8 & 0xFF0000;
/*      */               }
/*      */               
/* 2514 */               red0 = pix0 & 0xFF00;
/* 2515 */               red2 = pix2 & 0xFF00;
/* 2516 */               up = red0 + (((pix1 & 0xFF00) - red0) * iui >> 7);
/* 2517 */               dn = red2 + (((pix3 & 0xFF00) - red2) * iui >> 7);
/* 2518 */               int grn = up + ((dn - up) * ivi >> 7);
/* 2519 */               if (tint) { grn = grn * gtint >> 8 & 0xFF00;
/*      */               }
/*      */               
/* 2522 */               red0 = pix0 & 0xFF;
/* 2523 */               red2 = pix2 & 0xFF;
/* 2524 */               up = red0 + (((pix1 & 0xFF) - red0) * iui >> 7);
/* 2525 */               dn = red2 + (((pix3 & 0xFF) - red2) * iui >> 7);
/* 2526 */               int blu = up + ((dn - up) * ivi >> 7);
/* 2527 */               if (tint) { blu = blu * btint >> 8 & 0xFF;
/*      */               }
/*      */               
/* 2530 */               pix0 >>>= 24;
/* 2531 */               pix2 >>>= 24;
/* 2532 */               up = pix0 + (((pix1 >>> 24) - pix0) * iui >> 7);
/* 2533 */               dn = pix2 + (((pix3 >>> 24) - pix2) * iui >> 7);
/* 2534 */               al = al * (up + ((dn - up) * ivi >> 7)) >> 8;
/*      */               
/*      */ 
/* 2537 */               int bb = this.m_pixels[xstart];
/* 2538 */               int br = bb & 0xFF0000;
/* 2539 */               int bg = bb & 0xFF00;
/* 2540 */               bb &= 0xFF;
/* 2541 */               this.m_pixels[xstart] = 
/*      */               
/*      */ 
/* 2544 */                 (0xFF000000 | br + ((red - br) * al >> 8) & 0xFF0000 | bg + ((grn - bg) * al >> 8) & 0xFF00 | bb + ((blu - bb) * al >> 8) & 0xFF);
/*      */             } else {
/* 2546 */               int red = this.m_texture[((iv >> 16) * this.TEX_WIDTH + (iu >> 16))];
/* 2547 */               al = al * (red >>> 24) >> 8;
/* 2548 */               int grn = red & 0xFF00;
/* 2549 */               int blu = red & 0xFF;
/* 2550 */               red &= 0xFF0000;
/*      */               
/*      */ 
/* 2553 */               int bb = this.m_pixels[xstart];
/* 2554 */               int br = bb & 0xFF0000;
/* 2555 */               int bg = bb & 0xFF00;
/* 2556 */               bb &= 0xFF;
/* 2557 */               this.m_pixels[xstart] = 
/*      */               
/*      */ 
/* 2560 */                 (0xFF000000 | br + ((red - br) * al >> 8) & 0xFF0000 | bg + ((grn - bg) * al >> 8) & 0xFF00 | bb + ((blu - bb) * al >> 8) & 0xFF);
/*      */             }
/*      */           }
/*      */         }
/*      */         catch (Exception localException) {}
/*      */         
/* 2566 */         xpixel++;
/* 2567 */         if (!accurateMode) {
/* 2568 */           iu += this.iuadd;
/* 2569 */           iv += this.ivadd;
/*      */         }
/* 2571 */         ia += this.iaadd;
/* 2572 */         iz += this.izadd;
/*      */       }
/* 2574 */       ypixel++;
/*      */       
/* 2576 */       ytop += this.SCREEN_WIDTH;
/*      */       
/* 2578 */       this.xleft += leftadd;
/* 2579 */       this.xrght += rghtadd;
/* 2580 */       this.uleft += this.uleftadd;
/* 2581 */       this.vleft += this.vleftadd;
/* 2582 */       this.zleft += this.zleftadd;
/* 2583 */       this.aleft += this.aleftadd;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void drawsegment_gouraud_texture8(float leftadd, float rghtadd, int ytop, int ybottom)
/*      */   {
/* 2598 */     int ypixel = ytop;
/* 2599 */     int lastRowStart = this.m_texture.length - this.TEX_WIDTH - 2;
/* 2600 */     boolean accurateMode = this.parent.hints[7];
/* 2601 */     float screenx = 0.0F;float screeny = 0.0F;float screenz = 0.0F;
/* 2602 */     float a = 0.0F;float b = 0.0F;float c = 0.0F;
/* 2603 */     int linearInterpPower = TEX_INTERP_POWER;
/* 2604 */     int linearInterpLength = 1 << linearInterpPower;
/* 2605 */     if (accurateMode) {
/* 2606 */       if (precomputeAccurateTexturing()) {
/* 2607 */         this.newax *= linearInterpLength;
/* 2608 */         this.newbx *= linearInterpLength;
/* 2609 */         this.newcx *= linearInterpLength;
/* 2610 */         screenz = this.nearPlaneDepth;
/* 2611 */         this.firstSegment = false;
/*      */       } else {
/* 2613 */         accurateMode = false;
/*      */       }
/*      */     }
/*      */     
/* 2617 */     ytop *= this.SCREEN_WIDTH;
/* 2618 */     ybottom *= this.SCREEN_WIDTH;
/*      */     
/*      */ 
/* 2621 */     float iuf = this.iuadd;
/* 2622 */     float ivf = this.ivadd;
/* 2623 */     float irf = this.iradd;
/* 2624 */     float igf = this.igadd;
/* 2625 */     float ibf = this.ibadd;
/*      */     
/* 2627 */     while (ytop < ybottom) {
/* 2628 */       int xstart = (int)(this.xleft + 0.5F);
/* 2629 */       if (xstart < 0) {
/* 2630 */         xstart = 0;
/*      */       }
/* 2632 */       int xpixel = xstart;
/*      */       
/* 2634 */       int xend = (int)(this.xrght + 0.5F);
/* 2635 */       if (xend > this.SCREEN_WIDTH)
/* 2636 */         xend = this.SCREEN_WIDTH;
/* 2637 */       float xdiff = xstart + 0.5F - this.xleft;
/*      */       
/* 2639 */       int iu = (int)(iuf * xdiff + this.uleft);
/* 2640 */       int iv = (int)(ivf * xdiff + this.vleft);
/* 2641 */       int ir = (int)(irf * xdiff + this.rleft);
/* 2642 */       int ig = (int)(igf * xdiff + this.gleft);
/* 2643 */       int ib = (int)(ibf * xdiff + this.bleft);
/* 2644 */       float iz = this.izadd * xdiff + this.zleft;
/*      */       
/* 2646 */       xstart += ytop;
/* 2647 */       xend += ytop;
/*      */       
/* 2649 */       if (accurateMode) {
/* 2650 */         screenx = this.xmult * (xpixel + 0.5F - this.SCREEN_WIDTH / 2.0F);
/* 2651 */         screeny = this.ymult * (ypixel + 0.5F - this.SCREEN_HEIGHT / 2.0F);
/* 2652 */         a = screenx * this.ax + screeny * this.ay + screenz * this.az;
/* 2653 */         b = screenx * this.bx + screeny * this.by + screenz * this.bz;
/* 2654 */         c = screenx * this.cx + screeny * this.cy + screenz * this.cz;
/*      */       }
/* 2656 */       boolean goingIn = (this.newcx > 0.0F ? 1 : 0) != (c > 0.0F ? 1 : 0);
/* 2657 */       int interpCounter = 0;
/* 2658 */       int deltaU = 0;int deltaV = 0;
/* 2659 */       float fu = 0.0F;float fv = 0.0F;
/* 2660 */       float oldfu = 0.0F;float oldfv = 0.0F;
/*      */       
/* 2662 */       if ((accurateMode) && (goingIn)) {
/* 2663 */         int rightOffset = (xend - xstart - 1) % linearInterpLength;
/* 2664 */         int leftOffset = linearInterpLength - rightOffset;
/* 2665 */         float rightOffset2 = rightOffset / linearInterpLength;
/* 2666 */         float leftOffset2 = leftOffset / linearInterpLength;
/* 2667 */         interpCounter = leftOffset;
/* 2668 */         float ao = a - leftOffset2 * this.newax;
/* 2669 */         float bo = b - leftOffset2 * this.newbx;
/* 2670 */         float co = c - leftOffset2 * this.newcx;
/* 2671 */         float oneoverc = 65536.0F / co;
/* 2672 */         oldfu = ao * oneoverc;oldfv = bo * oneoverc;
/* 2673 */         a += rightOffset2 * this.newax;
/* 2674 */         b += rightOffset2 * this.newbx;
/* 2675 */         c += rightOffset2 * this.newcx;
/* 2676 */         oneoverc = 65536.0F / c;
/* 2677 */         fu = a * oneoverc;fv = b * oneoverc;
/* 2678 */         deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 2679 */         deltaV = (int)(fv - oldfv) >> linearInterpPower;
/* 2680 */         iu = (int)oldfu + (leftOffset - 1) * deltaU;iv = (int)oldfv + (leftOffset - 1) * deltaV;
/*      */       } else {
/* 2682 */         float preoneoverc = 65536.0F / c;
/* 2683 */         fu = a * preoneoverc;
/* 2684 */         fv = b * preoneoverc;
/*      */       }
/* 2688 */       for (; 
/*      */           
/* 2688 */           xstart < xend; xstart++) {
/* 2689 */         if (accurateMode) {
/* 2690 */           if (interpCounter == linearInterpLength) interpCounter = 0;
/* 2691 */           if (interpCounter == 0) {
/* 2692 */             a += this.newax;
/* 2693 */             b += this.newbx;
/* 2694 */             c += this.newcx;
/* 2695 */             float oneoverc = 65536.0F / c;
/* 2696 */             oldfu = fu;oldfv = fv;
/* 2697 */             fu = a * oneoverc;fv = b * oneoverc;
/* 2698 */             iu = (int)oldfu;iv = (int)oldfv;
/* 2699 */             deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 2700 */             deltaV = (int)(fv - oldfv) >> linearInterpPower;
/*      */           } else {
/* 2702 */             iu += deltaU;
/* 2703 */             iv += deltaV;
/*      */           }
/* 2705 */           interpCounter++;
/*      */         }
/*      */         
/*      */         try
/*      */         {
/* 2710 */           if ((this.noDepthTest) || (iz <= this.m_zbuffer[xstart]))
/*      */           {
/*      */             int al0;
/*      */             
/* 2714 */             if (this.m_bilinear) {
/* 2715 */               int ofs = (iv >> 16) * this.TEX_WIDTH + (iu >> 16);
/* 2716 */               int iui = iu & 0xFFFF;
/* 2717 */               int al0 = this.m_texture[ofs] & 0xFF;
/* 2718 */               int al1 = this.m_texture[(ofs + 1)] & 0xFF;
/* 2719 */               if (ofs < lastRowStart) ofs += this.TEX_WIDTH;
/* 2720 */               int al2 = this.m_texture[ofs] & 0xFF;
/* 2721 */               int al3 = this.m_texture[(ofs + 1)] & 0xFF;
/* 2722 */               al0 += ((al1 - al0) * iui >> 16);
/* 2723 */               al2 += ((al3 - al2) * iui >> 16);
/* 2724 */               al0 += ((al2 - al0) * (iv & 0xFFFF) >> 16);
/*      */             } else {
/* 2726 */               al0 = this.m_texture[((iv >> 16) * this.TEX_WIDTH + (iu >> 16))] & 0xFF;
/*      */             }
/*      */             
/*      */ 
/* 2730 */             int red = ir & 0xFF0000;
/* 2731 */             int grn = ig >> 8 & 0xFF00;
/* 2732 */             int blu = ib >> 16;
/*      */             
/*      */ 
/* 2735 */             int bb = this.m_pixels[xstart];
/* 2736 */             int br = bb & 0xFF0000;
/* 2737 */             int bg = bb & 0xFF00;
/* 2738 */             bb &= 0xFF;
/* 2739 */             this.m_pixels[xstart] = 
/*      */             
/*      */ 
/* 2742 */               (0xFF000000 | br + ((red - br) * al0 >> 8) & 0xFF0000 | bg + ((grn - bg) * al0 >> 8) & 0xFF00 | bb + ((blu - bb) * al0 >> 8) & 0xFF);
/*      */           }
/*      */         }
/*      */         catch (Exception localException) {}
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2753 */         xpixel++;
/* 2754 */         if (!accurateMode) {
/* 2755 */           iu += this.iuadd;
/* 2756 */           iv += this.ivadd;
/*      */         }
/* 2758 */         ir += this.iradd;
/* 2759 */         ig += this.igadd;
/* 2760 */         ib += this.ibadd;
/* 2761 */         iz += this.izadd;
/*      */       }
/* 2763 */       ypixel++;
/* 2764 */       ytop += this.SCREEN_WIDTH;
/* 2765 */       this.xleft += leftadd;
/* 2766 */       this.xrght += rghtadd;
/*      */       
/* 2768 */       this.uleft += this.uleftadd;
/* 2769 */       this.vleft += this.vleftadd;
/* 2770 */       this.rleft += this.rleftadd;
/* 2771 */       this.gleft += this.gleftadd;
/* 2772 */       this.bleft += this.bleftadd;
/* 2773 */       this.zleft += this.zleftadd;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void drawsegment_gouraud_texture8_alpha(float leftadd, float rghtadd, int ytop, int ybottom)
/*      */   {
/* 2787 */     int ypixel = ytop;
/* 2788 */     int lastRowStart = this.m_texture.length - this.TEX_WIDTH - 2;
/* 2789 */     boolean accurateMode = this.parent.hints[7];
/* 2790 */     float screenx = 0.0F;float screeny = 0.0F;float screenz = 0.0F;
/* 2791 */     float a = 0.0F;float b = 0.0F;float c = 0.0F;
/* 2792 */     int linearInterpPower = TEX_INTERP_POWER;
/* 2793 */     int linearInterpLength = 1 << linearInterpPower;
/* 2794 */     if (accurateMode)
/*      */     {
/* 2796 */       if (precomputeAccurateTexturing()) {
/* 2797 */         this.newax *= linearInterpLength;
/* 2798 */         this.newbx *= linearInterpLength;
/* 2799 */         this.newcx *= linearInterpLength;
/* 2800 */         screenz = this.nearPlaneDepth;
/* 2801 */         this.firstSegment = false;
/*      */       }
/*      */       else
/*      */       {
/* 2805 */         accurateMode = false;
/*      */       }
/*      */     }
/*      */     
/* 2809 */     ytop *= this.SCREEN_WIDTH;
/* 2810 */     ybottom *= this.SCREEN_WIDTH;
/*      */     
/*      */ 
/* 2813 */     float iuf = this.iuadd;
/* 2814 */     float ivf = this.ivadd;
/* 2815 */     float irf = this.iradd;
/* 2816 */     float igf = this.igadd;
/* 2817 */     float ibf = this.ibadd;
/* 2818 */     float iaf = this.iaadd;
/*      */     
/* 2820 */     while (ytop < ybottom) {
/* 2821 */       int xstart = (int)(this.xleft + 0.5F);
/* 2822 */       if (xstart < 0) {
/* 2823 */         xstart = 0;
/*      */       }
/* 2825 */       int xpixel = xstart;
/*      */       
/* 2827 */       int xend = (int)(this.xrght + 0.5F);
/* 2828 */       if (xend > this.SCREEN_WIDTH)
/* 2829 */         xend = this.SCREEN_WIDTH;
/* 2830 */       float xdiff = xstart + 0.5F - this.xleft;
/*      */       
/* 2832 */       int iu = (int)(iuf * xdiff + this.uleft);
/* 2833 */       int iv = (int)(ivf * xdiff + this.vleft);
/* 2834 */       int ir = (int)(irf * xdiff + this.rleft);
/* 2835 */       int ig = (int)(igf * xdiff + this.gleft);
/* 2836 */       int ib = (int)(ibf * xdiff + this.bleft);
/* 2837 */       int ia = (int)(iaf * xdiff + this.aleft);
/* 2838 */       float iz = this.izadd * xdiff + this.zleft;
/*      */       
/* 2840 */       xstart += ytop;
/* 2841 */       xend += ytop;
/*      */       
/* 2843 */       if (accurateMode) {
/* 2844 */         screenx = this.xmult * (xpixel + 0.5F - this.SCREEN_WIDTH / 2.0F);
/* 2845 */         screeny = this.ymult * (ypixel + 0.5F - this.SCREEN_HEIGHT / 2.0F);
/* 2846 */         a = screenx * this.ax + screeny * this.ay + screenz * this.az;
/* 2847 */         b = screenx * this.bx + screeny * this.by + screenz * this.bz;
/* 2848 */         c = screenx * this.cx + screeny * this.cy + screenz * this.cz;
/*      */       }
/* 2850 */       boolean goingIn = (this.newcx > 0.0F ? 1 : 0) != (c > 0.0F ? 1 : 0);
/* 2851 */       int interpCounter = 0;
/* 2852 */       int deltaU = 0;int deltaV = 0;
/* 2853 */       float fu = 0.0F;float fv = 0.0F;
/* 2854 */       float oldfu = 0.0F;float oldfv = 0.0F;
/*      */       
/* 2856 */       if ((accurateMode) && (goingIn)) {
/* 2857 */         int rightOffset = (xend - xstart - 1) % linearInterpLength;
/* 2858 */         int leftOffset = linearInterpLength - rightOffset;
/* 2859 */         float rightOffset2 = rightOffset / linearInterpLength;
/* 2860 */         float leftOffset2 = leftOffset / linearInterpLength;
/* 2861 */         interpCounter = leftOffset;
/* 2862 */         float ao = a - leftOffset2 * this.newax;
/* 2863 */         float bo = b - leftOffset2 * this.newbx;
/* 2864 */         float co = c - leftOffset2 * this.newcx;
/* 2865 */         float oneoverc = 65536.0F / co;
/* 2866 */         oldfu = ao * oneoverc;oldfv = bo * oneoverc;
/* 2867 */         a += rightOffset2 * this.newax;
/* 2868 */         b += rightOffset2 * this.newbx;
/* 2869 */         c += rightOffset2 * this.newcx;
/* 2870 */         oneoverc = 65536.0F / c;
/* 2871 */         fu = a * oneoverc;fv = b * oneoverc;
/* 2872 */         deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 2873 */         deltaV = (int)(fv - oldfv) >> linearInterpPower;
/* 2874 */         iu = (int)oldfu + (leftOffset - 1) * deltaU;iv = (int)oldfv + (leftOffset - 1) * deltaV;
/*      */       } else {
/* 2876 */         float preoneoverc = 65536.0F / c;
/* 2877 */         fu = a * preoneoverc;
/* 2878 */         fv = b * preoneoverc;
/*      */       }
/* 2882 */       for (; 
/*      */           
/* 2882 */           xstart < xend; xstart++) {
/* 2883 */         if (accurateMode) {
/* 2884 */           if (interpCounter == linearInterpLength) interpCounter = 0;
/* 2885 */           if (interpCounter == 0) {
/* 2886 */             a += this.newax;
/* 2887 */             b += this.newbx;
/* 2888 */             c += this.newcx;
/* 2889 */             float oneoverc = 65536.0F / c;
/* 2890 */             oldfu = fu;oldfv = fv;
/* 2891 */             fu = a * oneoverc;fv = b * oneoverc;
/* 2892 */             iu = (int)oldfu;iv = (int)oldfv;
/* 2893 */             deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 2894 */             deltaV = (int)(fv - oldfv) >> linearInterpPower;
/*      */           } else {
/* 2896 */             iu += deltaU;
/* 2897 */             iv += deltaV;
/*      */           }
/* 2899 */           interpCounter++;
/*      */         }
/*      */         try
/*      */         {
/* 2903 */           if ((this.noDepthTest) || (iz <= this.m_zbuffer[xstart]))
/*      */           {
/*      */ 
/*      */ 
/* 2907 */             if (this.m_bilinear) {
/* 2908 */               int ofs = (iv >> 16) * this.TEX_WIDTH + (iu >> 16);
/* 2909 */               int iui = iu & 0xFFFF;
/* 2910 */               int al0 = this.m_texture[ofs] & 0xFF;
/* 2911 */               int al1 = this.m_texture[(ofs + 1)] & 0xFF;
/* 2912 */               if (ofs < lastRowStart) ofs += this.TEX_WIDTH;
/* 2913 */               int al2 = this.m_texture[ofs] & 0xFF;
/* 2914 */               int al3 = this.m_texture[(ofs + 1)] & 0xFF;
/* 2915 */               al0 += ((al1 - al0) * iui >> 16);
/* 2916 */               al2 += ((al3 - al2) * iui >> 16);
/* 2917 */               al0 += ((al2 - al0) * (iv & 0xFFFF) >> 16);
/*      */             } else {
/* 2919 */               al0 = this.m_texture[((iv >> 16) * this.TEX_WIDTH + (iu >> 16))] & 0xFF;
/*      */             }
/* 2921 */             int al0 = al0 * (ia >> 16) >> 8;
/*      */             
/*      */ 
/* 2924 */             int red = ir & 0xFF0000;
/* 2925 */             int grn = ig >> 8 & 0xFF00;
/* 2926 */             int blu = ib >> 16;
/*      */             
/*      */ 
/* 2929 */             int bb = this.m_pixels[xstart];
/* 2930 */             int br = bb & 0xFF0000;
/* 2931 */             int bg = bb & 0xFF00;
/* 2932 */             bb &= 0xFF;
/*      */             
/* 2934 */             this.m_pixels[xstart] = 
/*      */             
/*      */ 
/* 2937 */               (0xFF000000 | br + ((red - br) * al0 >> 8) & 0xFF0000 | bg + ((grn - bg) * al0 >> 8) & 0xFF00 | bb + ((blu - bb) * al0 >> 8) & 0xFF);
/*      */           }
/*      */         }
/*      */         catch (Exception localException) {}
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 2945 */         xpixel++;
/* 2946 */         if (!accurateMode) {
/* 2947 */           iu += this.iuadd;
/* 2948 */           iv += this.ivadd;
/*      */         }
/* 2950 */         ir += this.iradd;
/* 2951 */         ig += this.igadd;
/* 2952 */         ib += this.ibadd;
/* 2953 */         ia += this.iaadd;
/* 2954 */         iz += this.izadd;
/*      */       }
/* 2956 */       ypixel++;
/* 2957 */       ytop += this.SCREEN_WIDTH;
/* 2958 */       this.xleft += leftadd;
/* 2959 */       this.xrght += rghtadd;
/* 2960 */       this.uleft += this.uleftadd;
/* 2961 */       this.vleft += this.vleftadd;
/* 2962 */       this.rleft += this.rleftadd;
/* 2963 */       this.gleft += this.gleftadd;
/* 2964 */       this.bleft += this.bleftadd;
/* 2965 */       this.aleft += this.aleftadd;
/* 2966 */       this.zleft += this.zleftadd;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void drawsegment_gouraud_texture24(float leftadd, float rghtadd, int ytop, int ybottom)
/*      */   {
/* 2979 */     int ypixel = ytop;
/* 2980 */     int lastRowStart = this.m_texture.length - this.TEX_WIDTH - 2;
/* 2981 */     boolean accurateMode = this.parent.hints[7];
/* 2982 */     float screenx = 0.0F;float screeny = 0.0F;float screenz = 0.0F;
/* 2983 */     float a = 0.0F;float b = 0.0F;float c = 0.0F;
/* 2984 */     int linearInterpPower = TEX_INTERP_POWER;
/* 2985 */     int linearInterpLength = 1 << linearInterpPower;
/* 2986 */     if (accurateMode) {
/* 2987 */       if (precomputeAccurateTexturing()) {
/* 2988 */         this.newax *= linearInterpLength;
/* 2989 */         this.newbx *= linearInterpLength;
/* 2990 */         this.newcx *= linearInterpLength;
/* 2991 */         screenz = this.nearPlaneDepth;
/* 2992 */         this.firstSegment = false;
/*      */       } else {
/* 2994 */         accurateMode = false;
/*      */       }
/*      */     }
/*      */     
/* 2998 */     ytop *= this.SCREEN_WIDTH;
/* 2999 */     ybottom *= this.SCREEN_WIDTH;
/*      */     
/*      */ 
/* 3002 */     float iuf = this.iuadd;
/* 3003 */     float ivf = this.ivadd;
/* 3004 */     float irf = this.iradd;
/* 3005 */     float igf = this.igadd;
/* 3006 */     float ibf = this.ibadd;
/*      */     
/* 3008 */     while (ytop < ybottom) {
/* 3009 */       int xstart = (int)(this.xleft + 0.5F);
/* 3010 */       if (xstart < 0) {
/* 3011 */         xstart = 0;
/*      */       }
/* 3013 */       int xpixel = xstart;
/*      */       
/* 3015 */       int xend = (int)(this.xrght + 0.5F);
/* 3016 */       if (xend > this.SCREEN_WIDTH)
/* 3017 */         xend = this.SCREEN_WIDTH;
/* 3018 */       float xdiff = xstart + 0.5F - this.xleft;
/*      */       
/* 3020 */       int iu = (int)(iuf * xdiff + this.uleft);
/* 3021 */       int iv = (int)(ivf * xdiff + this.vleft);
/* 3022 */       int ir = (int)(irf * xdiff + this.rleft);
/* 3023 */       int ig = (int)(igf * xdiff + this.gleft);
/* 3024 */       int ib = (int)(ibf * xdiff + this.bleft);
/* 3025 */       float iz = this.izadd * xdiff + this.zleft;
/*      */       
/* 3027 */       xstart += ytop;
/* 3028 */       xend += ytop;
/*      */       
/* 3030 */       if (accurateMode) {
/* 3031 */         screenx = this.xmult * (xpixel + 0.5F - this.SCREEN_WIDTH / 2.0F);
/* 3032 */         screeny = this.ymult * (ypixel + 0.5F - this.SCREEN_HEIGHT / 2.0F);
/* 3033 */         a = screenx * this.ax + screeny * this.ay + screenz * this.az;
/* 3034 */         b = screenx * this.bx + screeny * this.by + screenz * this.bz;
/* 3035 */         c = screenx * this.cx + screeny * this.cy + screenz * this.cz;
/*      */       }
/* 3037 */       boolean goingIn = (this.newcx > 0.0F ? 1 : 0) != (c > 0.0F ? 1 : 0);
/* 3038 */       int interpCounter = 0;
/* 3039 */       int deltaU = 0;int deltaV = 0;
/* 3040 */       float fu = 0.0F;float fv = 0.0F;
/* 3041 */       float oldfu = 0.0F;float oldfv = 0.0F;
/*      */       
/* 3043 */       if ((accurateMode) && (goingIn)) {
/* 3044 */         int rightOffset = (xend - xstart - 1) % linearInterpLength;
/* 3045 */         int leftOffset = linearInterpLength - rightOffset;
/* 3046 */         float rightOffset2 = rightOffset / linearInterpLength;
/* 3047 */         float leftOffset2 = leftOffset / linearInterpLength;
/* 3048 */         interpCounter = leftOffset;
/* 3049 */         float ao = a - leftOffset2 * this.newax;
/* 3050 */         float bo = b - leftOffset2 * this.newbx;
/* 3051 */         float co = c - leftOffset2 * this.newcx;
/* 3052 */         float oneoverc = 65536.0F / co;
/* 3053 */         oldfu = ao * oneoverc;oldfv = bo * oneoverc;
/* 3054 */         a += rightOffset2 * this.newax;
/* 3055 */         b += rightOffset2 * this.newbx;
/* 3056 */         c += rightOffset2 * this.newcx;
/* 3057 */         oneoverc = 65536.0F / c;
/* 3058 */         fu = a * oneoverc;fv = b * oneoverc;
/* 3059 */         deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 3060 */         deltaV = (int)(fv - oldfv) >> linearInterpPower;
/* 3061 */         iu = (int)oldfu + (leftOffset - 1) * deltaU;iv = (int)oldfv + (leftOffset - 1) * deltaV;
/*      */       } else {
/* 3063 */         float preoneoverc = 65536.0F / c;
/* 3064 */         fu = a * preoneoverc;
/* 3065 */         fv = b * preoneoverc;
/*      */       }
/* 3068 */       for (; 
/* 3068 */           xstart < xend; xstart++) {
/* 3069 */         if (accurateMode) {
/* 3070 */           if (interpCounter == linearInterpLength) interpCounter = 0;
/* 3071 */           if (interpCounter == 0) {
/* 3072 */             a += this.newax;
/* 3073 */             b += this.newbx;
/* 3074 */             c += this.newcx;
/* 3075 */             float oneoverc = 65536.0F / c;
/* 3076 */             oldfu = fu;oldfv = fv;
/* 3077 */             fu = a * oneoverc;fv = b * oneoverc;
/* 3078 */             iu = (int)oldfu;iv = (int)oldfv;
/* 3079 */             deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 3080 */             deltaV = (int)(fv - oldfv) >> linearInterpPower;
/*      */           } else {
/* 3082 */             iu += deltaU;
/* 3083 */             iv += deltaV;
/*      */           }
/* 3085 */           interpCounter++;
/*      */         }
/*      */         try
/*      */         {
/* 3089 */           if ((this.noDepthTest) || (iz <= this.m_zbuffer[xstart])) {
/* 3090 */             this.m_zbuffer[xstart] = iz;
/*      */             
/*      */             int blu;
/*      */             int blu;
/*      */             int red;
/*      */             int grn;
/* 3096 */             if (this.m_bilinear) {
/* 3097 */               int ofs = (iv >> 16) * this.TEX_WIDTH + (iu >> 16);
/* 3098 */               int iui = (iu & 0xFFFF) >> 9;
/* 3099 */               int ivi = (iv & 0xFFFF) >> 9;
/*      */               
/*      */ 
/* 3102 */               int pix0 = this.m_texture[ofs];
/* 3103 */               int pix1 = this.m_texture[(ofs + 1)];
/* 3104 */               if (ofs < lastRowStart) ofs += this.TEX_WIDTH;
/* 3105 */               int pix2 = this.m_texture[ofs];
/* 3106 */               int pix3 = this.m_texture[(ofs + 1)];
/*      */               
/*      */ 
/* 3109 */               int red0 = pix0 & 0xFF0000;
/* 3110 */               int red2 = pix2 & 0xFF0000;
/* 3111 */               int up = red0 + (((pix1 & 0xFF0000) - red0) * iui >> 7);
/* 3112 */               int dn = red2 + (((pix3 & 0xFF0000) - red2) * iui >> 7);
/* 3113 */               int red = up + ((dn - up) * ivi >> 7);
/*      */               
/*      */ 
/* 3116 */               red0 = pix0 & 0xFF00;
/* 3117 */               red2 = pix2 & 0xFF00;
/* 3118 */               up = red0 + (((pix1 & 0xFF00) - red0) * iui >> 7);
/* 3119 */               dn = red2 + (((pix3 & 0xFF00) - red2) * iui >> 7);
/* 3120 */               int grn = up + ((dn - up) * ivi >> 7);
/*      */               
/*      */ 
/* 3123 */               red0 = pix0 & 0xFF;
/* 3124 */               red2 = pix2 & 0xFF;
/* 3125 */               up = red0 + (((pix1 & 0xFF) - red0) * iui >> 7);
/* 3126 */               dn = red2 + (((pix3 & 0xFF) - red2) * iui >> 7);
/* 3127 */               blu = up + ((dn - up) * ivi >> 7);
/*      */             }
/*      */             else {
/* 3130 */               blu = this.m_texture[((iv >> 16) * this.TEX_WIDTH + (iu >> 16))];
/* 3131 */               red = blu & 0xFF0000;
/* 3132 */               grn = blu & 0xFF00;
/* 3133 */               blu &= 0xFF;
/*      */             }
/*      */             
/*      */ 
/* 3137 */             int r = ir >> 16;
/* 3138 */             int g = ig >> 16;
/*      */             
/*      */ 
/* 3141 */             int bb2 = ib >> 16;
/*      */             
/* 3143 */             this.m_pixels[xstart] = 
/* 3144 */               (0xFF000000 | (red * r & 0xFF000000 | grn * g & 0xFF0000 | blu * bb2) >> 8);
/*      */           }
/*      */         }
/*      */         catch (Exception localException) {}
/*      */         
/*      */ 
/* 3150 */         xpixel++;
/* 3151 */         if (!accurateMode) {
/* 3152 */           iu += this.iuadd;
/* 3153 */           iv += this.ivadd;
/*      */         }
/* 3155 */         ir += this.iradd;
/* 3156 */         ig += this.igadd;
/* 3157 */         ib += this.ibadd;
/* 3158 */         iz += this.izadd;
/*      */       }
/* 3160 */       ypixel++;
/*      */       
/* 3162 */       ytop += this.SCREEN_WIDTH;
/* 3163 */       this.xleft += leftadd;
/* 3164 */       this.xrght += rghtadd;
/* 3165 */       this.uleft += this.uleftadd;
/* 3166 */       this.vleft += this.vleftadd;
/* 3167 */       this.rleft += this.rleftadd;
/* 3168 */       this.gleft += this.gleftadd;
/* 3169 */       this.bleft += this.bleftadd;
/* 3170 */       this.zleft += this.zleftadd;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void drawsegment_gouraud_texture24_alpha(float leftadd, float rghtadd, int ytop, int ybottom)
/*      */   {
/* 3187 */     int ypixel = ytop;
/* 3188 */     int lastRowStart = this.m_texture.length - this.TEX_WIDTH - 2;
/* 3189 */     boolean accurateMode = this.parent.hints[7];
/* 3190 */     float screenx = 0.0F;float screeny = 0.0F;float screenz = 0.0F;
/* 3191 */     float a = 0.0F;float b = 0.0F;float c = 0.0F;
/* 3192 */     int linearInterpPower = TEX_INTERP_POWER;
/* 3193 */     int linearInterpLength = 1 << linearInterpPower;
/* 3194 */     if (accurateMode) {
/* 3195 */       if (precomputeAccurateTexturing()) {
/* 3196 */         this.newax *= linearInterpLength;
/* 3197 */         this.newbx *= linearInterpLength;
/* 3198 */         this.newcx *= linearInterpLength;
/* 3199 */         screenz = this.nearPlaneDepth;
/* 3200 */         this.firstSegment = false;
/*      */       } else {
/* 3202 */         accurateMode = false;
/*      */       }
/*      */     }
/*      */     
/* 3206 */     ytop *= this.SCREEN_WIDTH;
/* 3207 */     ybottom *= this.SCREEN_WIDTH;
/*      */     
/*      */ 
/* 3210 */     float iuf = this.iuadd;
/* 3211 */     float ivf = this.ivadd;
/* 3212 */     float irf = this.iradd;
/* 3213 */     float igf = this.igadd;
/* 3214 */     float ibf = this.ibadd;
/* 3215 */     float iaf = this.iaadd;
/*      */     
/* 3217 */     while (ytop < ybottom) {
/* 3218 */       int xstart = (int)(this.xleft + 0.5F);
/* 3219 */       if (xstart < 0) {
/* 3220 */         xstart = 0;
/*      */       }
/* 3222 */       int xpixel = xstart;
/*      */       
/* 3224 */       int xend = (int)(this.xrght + 0.5F);
/* 3225 */       if (xend > this.SCREEN_WIDTH)
/* 3226 */         xend = this.SCREEN_WIDTH;
/* 3227 */       float xdiff = xstart + 0.5F - this.xleft;
/*      */       
/* 3229 */       int iu = (int)(iuf * xdiff + this.uleft);
/* 3230 */       int iv = (int)(ivf * xdiff + this.vleft);
/* 3231 */       int ir = (int)(irf * xdiff + this.rleft);
/* 3232 */       int ig = (int)(igf * xdiff + this.gleft);
/* 3233 */       int ib = (int)(ibf * xdiff + this.bleft);
/* 3234 */       int ia = (int)(iaf * xdiff + this.aleft);
/* 3235 */       float iz = this.izadd * xdiff + this.zleft;
/*      */       
/* 3237 */       xstart += ytop;
/* 3238 */       xend += ytop;
/*      */       
/* 3240 */       if (accurateMode) {
/* 3241 */         screenx = this.xmult * (xpixel + 0.5F - this.SCREEN_WIDTH / 2.0F);
/* 3242 */         screeny = this.ymult * (ypixel + 0.5F - this.SCREEN_HEIGHT / 2.0F);
/* 3243 */         a = screenx * this.ax + screeny * this.ay + screenz * this.az;
/* 3244 */         b = screenx * this.bx + screeny * this.by + screenz * this.bz;
/* 3245 */         c = screenx * this.cx + screeny * this.cy + screenz * this.cz;
/*      */       }
/* 3247 */       boolean goingIn = (this.newcx > 0.0F ? 1 : 0) != (c > 0.0F ? 1 : 0);
/* 3248 */       int interpCounter = 0;
/* 3249 */       int deltaU = 0;int deltaV = 0;
/* 3250 */       float fu = 0.0F;float fv = 0.0F;
/* 3251 */       float oldfu = 0.0F;float oldfv = 0.0F;
/*      */       
/* 3253 */       if ((accurateMode) && (goingIn)) {
/* 3254 */         int rightOffset = (xend - xstart - 1) % linearInterpLength;
/* 3255 */         int leftOffset = linearInterpLength - rightOffset;
/* 3256 */         float rightOffset2 = rightOffset / linearInterpLength;
/* 3257 */         float leftOffset2 = leftOffset / linearInterpLength;
/* 3258 */         interpCounter = leftOffset;
/* 3259 */         float ao = a - leftOffset2 * this.newax;
/* 3260 */         float bo = b - leftOffset2 * this.newbx;
/* 3261 */         float co = c - leftOffset2 * this.newcx;
/* 3262 */         float oneoverc = 65536.0F / co;
/* 3263 */         oldfu = ao * oneoverc;oldfv = bo * oneoverc;
/* 3264 */         a += rightOffset2 * this.newax;
/* 3265 */         b += rightOffset2 * this.newbx;
/* 3266 */         c += rightOffset2 * this.newcx;
/* 3267 */         oneoverc = 65536.0F / c;
/* 3268 */         fu = a * oneoverc;fv = b * oneoverc;
/* 3269 */         deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 3270 */         deltaV = (int)(fv - oldfv) >> linearInterpPower;
/* 3271 */         iu = (int)oldfu + (leftOffset - 1) * deltaU;iv = (int)oldfv + (leftOffset - 1) * deltaV;
/*      */       } else {
/* 3273 */         float preoneoverc = 65536.0F / c;
/* 3274 */         fu = a * preoneoverc;
/* 3275 */         fv = b * preoneoverc;
/*      */       }
/* 3278 */       for (; 
/* 3278 */           xstart < xend; xstart++) {
/* 3279 */         if (accurateMode) {
/* 3280 */           if (interpCounter == linearInterpLength) interpCounter = 0;
/* 3281 */           if (interpCounter == 0) {
/* 3282 */             a += this.newax;
/* 3283 */             b += this.newbx;
/* 3284 */             c += this.newcx;
/* 3285 */             float oneoverc = 65536.0F / c;
/* 3286 */             oldfu = fu;oldfv = fv;
/* 3287 */             fu = a * oneoverc;fv = b * oneoverc;
/* 3288 */             iu = (int)oldfu;iv = (int)oldfv;
/* 3289 */             deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 3290 */             deltaV = (int)(fv - oldfv) >> linearInterpPower;
/*      */           } else {
/* 3292 */             iu += deltaU;
/* 3293 */             iv += deltaV;
/*      */           }
/* 3295 */           interpCounter++;
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */         try
/*      */         {
/* 3302 */           if ((this.noDepthTest) || (iz <= this.m_zbuffer[xstart]))
/*      */           {
/*      */ 
/*      */ 
/* 3306 */             int al = ia >> 16;
/*      */             
/*      */ 
/*      */             int blu;
/*      */             
/*      */ 
/* 3312 */             if (this.m_bilinear) {
/* 3313 */               int ofs = (iv >> 16) * this.TEX_WIDTH + (iu >> 16);
/* 3314 */               int iui = (iu & 0xFFFF) >> 9;
/* 3315 */               int ivi = (iv & 0xFFFF) >> 9;
/*      */               
/*      */ 
/* 3318 */               int pix0 = this.m_texture[ofs];
/* 3319 */               int pix1 = this.m_texture[(ofs + 1)];
/* 3320 */               if (ofs < lastRowStart) ofs += this.TEX_WIDTH;
/* 3321 */               int pix2 = this.m_texture[ofs];
/* 3322 */               int pix3 = this.m_texture[(ofs + 1)];
/*      */               
/*      */ 
/* 3325 */               int red0 = pix0 & 0xFF0000;
/* 3326 */               int red2 = pix2 & 0xFF0000;
/* 3327 */               int up = red0 + (((pix1 & 0xFF0000) - red0) * iui >> 7);
/* 3328 */               int dn = red2 + (((pix3 & 0xFF0000) - red2) * iui >> 7);
/* 3329 */               int red = up + ((dn - up) * ivi >> 7) >> 16;
/*      */               
/*      */ 
/* 3332 */               red0 = pix0 & 0xFF00;
/* 3333 */               red2 = pix2 & 0xFF00;
/* 3334 */               up = red0 + (((pix1 & 0xFF00) - red0) * iui >> 7);
/* 3335 */               dn = red2 + (((pix3 & 0xFF00) - red2) * iui >> 7);
/* 3336 */               int grn = up + ((dn - up) * ivi >> 7) >> 8;
/*      */               
/*      */ 
/* 3339 */               red0 = pix0 & 0xFF;
/* 3340 */               red2 = pix2 & 0xFF;
/* 3341 */               up = red0 + (((pix1 & 0xFF) - red0) * iui >> 7);
/* 3342 */               dn = red2 + (((pix3 & 0xFF) - red2) * iui >> 7);
/* 3343 */               blu = up + ((dn - up) * ivi >> 7);
/*      */             } else {
/* 3345 */               blu = this.m_texture[((iv >> 16) * this.TEX_WIDTH + (iu >> 16))];
/* 3346 */               red = (blu & 0xFF0000) >> 16;
/* 3347 */               grn = (blu & 0xFF00) >> 8;
/* 3348 */               blu &= 0xFF;
/*      */             }
/*      */             
/*      */ 
/* 3352 */             int red = red * ir >>> 8;
/* 3353 */             int grn = grn * ig >>> 16;
/* 3354 */             int blu = blu * ib >>> 24;
/*      */             
/*      */ 
/* 3357 */             int bb = this.m_pixels[xstart];
/* 3358 */             int br = bb & 0xFF0000;
/* 3359 */             int bg = bb & 0xFF00;
/* 3360 */             bb &= 0xFF;
/*      */             
/*      */ 
/* 3363 */             this.m_pixels[xstart] = 
/*      */             
/*      */ 
/* 3366 */               (0xFF000000 | br + ((red - br) * al >> 8) & 0xFF0000 | bg + ((grn - bg) * al >> 8) & 0xFF00 | bb + ((blu - bb) * al >> 8) & 0xFF);
/*      */           }
/*      */         }
/*      */         catch (Exception localException) {}
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 3374 */         xpixel++;
/* 3375 */         if (!accurateMode) {
/* 3376 */           iu += this.iuadd;
/* 3377 */           iv += this.ivadd;
/*      */         }
/* 3379 */         ir += this.iradd;
/* 3380 */         ig += this.igadd;
/* 3381 */         ib += this.ibadd;
/* 3382 */         ia += this.iaadd;
/* 3383 */         iz += this.izadd;
/*      */       }
/*      */       
/* 3386 */       ypixel++;
/*      */       
/* 3388 */       ytop += this.SCREEN_WIDTH;
/* 3389 */       this.xleft += leftadd;
/* 3390 */       this.xrght += rghtadd;
/* 3391 */       this.uleft += this.uleftadd;
/* 3392 */       this.vleft += this.vleftadd;
/* 3393 */       this.rleft += this.rleftadd;
/* 3394 */       this.gleft += this.gleftadd;
/* 3395 */       this.bleft += this.bleftadd;
/* 3396 */       this.aleft += this.aleftadd;
/* 3397 */       this.zleft += this.zleftadd;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void drawsegment_gouraud_texture32(float leftadd, float rghtadd, int ytop, int ybottom)
/*      */   {
/* 3414 */     int ypixel = ytop;
/* 3415 */     int lastRowStart = this.m_texture.length - this.TEX_WIDTH - 2;
/* 3416 */     boolean accurateMode = this.parent.hints[7];
/* 3417 */     float screenx = 0.0F;float screeny = 0.0F;float screenz = 0.0F;
/* 3418 */     float a = 0.0F;float b = 0.0F;float c = 0.0F;
/* 3419 */     int linearInterpPower = TEX_INTERP_POWER;
/* 3420 */     int linearInterpLength = 1 << linearInterpPower;
/* 3421 */     if (accurateMode) {
/* 3422 */       if (precomputeAccurateTexturing()) {
/* 3423 */         this.newax *= linearInterpLength;
/* 3424 */         this.newbx *= linearInterpLength;
/* 3425 */         this.newcx *= linearInterpLength;
/* 3426 */         screenz = this.nearPlaneDepth;
/* 3427 */         this.firstSegment = false;
/*      */       } else {
/* 3429 */         accurateMode = false;
/*      */       }
/*      */     }
/*      */     
/* 3433 */     ytop *= this.SCREEN_WIDTH;
/* 3434 */     ybottom *= this.SCREEN_WIDTH;
/*      */     
/*      */ 
/* 3437 */     float iuf = this.iuadd;
/* 3438 */     float ivf = this.ivadd;
/* 3439 */     float irf = this.iradd;
/* 3440 */     float igf = this.igadd;
/* 3441 */     float ibf = this.ibadd;
/*      */     
/* 3443 */     while (ytop < ybottom) {
/* 3444 */       int xstart = (int)(this.xleft + 0.5F);
/* 3445 */       if (xstart < 0) {
/* 3446 */         xstart = 0;
/*      */       }
/* 3448 */       int xpixel = xstart;
/*      */       
/* 3450 */       int xend = (int)(this.xrght + 0.5F);
/* 3451 */       if (xend > this.SCREEN_WIDTH)
/* 3452 */         xend = this.SCREEN_WIDTH;
/* 3453 */       float xdiff = xstart + 0.5F - this.xleft;
/*      */       
/* 3455 */       int iu = (int)(iuf * xdiff + this.uleft);
/* 3456 */       int iv = (int)(ivf * xdiff + this.vleft);
/* 3457 */       int ir = (int)(irf * xdiff + this.rleft);
/* 3458 */       int ig = (int)(igf * xdiff + this.gleft);
/* 3459 */       int ib = (int)(ibf * xdiff + this.bleft);
/* 3460 */       float iz = this.izadd * xdiff + this.zleft;
/*      */       
/* 3462 */       xstart += ytop;
/* 3463 */       xend += ytop;
/* 3464 */       if (accurateMode) {
/* 3465 */         screenx = this.xmult * (xpixel + 0.5F - this.SCREEN_WIDTH / 2.0F);
/* 3466 */         screeny = this.ymult * (ypixel + 0.5F - this.SCREEN_HEIGHT / 2.0F);
/* 3467 */         a = screenx * this.ax + screeny * this.ay + screenz * this.az;
/* 3468 */         b = screenx * this.bx + screeny * this.by + screenz * this.bz;
/* 3469 */         c = screenx * this.cx + screeny * this.cy + screenz * this.cz;
/*      */       }
/* 3471 */       boolean goingIn = (this.newcx > 0.0F ? 1 : 0) != (c > 0.0F ? 1 : 0);
/* 3472 */       int interpCounter = 0;
/* 3473 */       int deltaU = 0;int deltaV = 0;
/* 3474 */       float fu = 0.0F;float fv = 0.0F;
/* 3475 */       float oldfu = 0.0F;float oldfv = 0.0F;
/*      */       
/* 3477 */       if ((accurateMode) && (goingIn)) {
/* 3478 */         int rightOffset = (xend - xstart - 1) % linearInterpLength;
/* 3479 */         int leftOffset = linearInterpLength - rightOffset;
/* 3480 */         float rightOffset2 = rightOffset / linearInterpLength;
/* 3481 */         float leftOffset2 = leftOffset / linearInterpLength;
/* 3482 */         interpCounter = leftOffset;
/* 3483 */         float ao = a - leftOffset2 * this.newax;
/* 3484 */         float bo = b - leftOffset2 * this.newbx;
/* 3485 */         float co = c - leftOffset2 * this.newcx;
/* 3486 */         float oneoverc = 65536.0F / co;
/* 3487 */         oldfu = ao * oneoverc;oldfv = bo * oneoverc;
/* 3488 */         a += rightOffset2 * this.newax;
/* 3489 */         b += rightOffset2 * this.newbx;
/* 3490 */         c += rightOffset2 * this.newcx;
/* 3491 */         oneoverc = 65536.0F / c;
/* 3492 */         fu = a * oneoverc;fv = b * oneoverc;
/* 3493 */         deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 3494 */         deltaV = (int)(fv - oldfv) >> linearInterpPower;
/* 3495 */         iu = (int)oldfu + (leftOffset - 1) * deltaU;iv = (int)oldfv + (leftOffset - 1) * deltaV;
/*      */       } else {
/* 3497 */         float preoneoverc = 65536.0F / c;
/* 3498 */         fu = a * preoneoverc;
/* 3499 */         fv = b * preoneoverc;
/*      */       }
/* 3503 */       for (; 
/*      */           
/* 3503 */           xstart < xend; xstart++) {
/* 3504 */         if (accurateMode) {
/* 3505 */           if (interpCounter == linearInterpLength) interpCounter = 0;
/* 3506 */           if (interpCounter == 0) {
/* 3507 */             a += this.newax;
/* 3508 */             b += this.newbx;
/* 3509 */             c += this.newcx;
/* 3510 */             float oneoverc = 65536.0F / c;
/* 3511 */             oldfu = fu;oldfv = fv;
/* 3512 */             fu = a * oneoverc;fv = b * oneoverc;
/* 3513 */             iu = (int)oldfu;iv = (int)oldfv;
/* 3514 */             deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 3515 */             deltaV = (int)(fv - oldfv) >> linearInterpPower;
/*      */           } else {
/* 3517 */             iu += deltaU;
/* 3518 */             iv += deltaV;
/*      */           }
/* 3520 */           interpCounter++;
/*      */         }
/*      */         try
/*      */         {
/* 3524 */           if ((this.noDepthTest) || (iz <= this.m_zbuffer[xstart]))
/*      */           {
/*      */             int al;
/*      */             
/*      */ 
/*      */             int al;
/*      */             
/*      */ 
/* 3532 */             if (this.m_bilinear) {
/* 3533 */               int ofs = (iv >> 16) * this.TEX_WIDTH + (iu >> 16);
/* 3534 */               int iui = (iu & 0xFFFF) >> 9;
/* 3535 */               int ivi = (iv & 0xFFFF) >> 9;
/*      */               
/*      */ 
/* 3538 */               int pix0 = this.m_texture[ofs];
/* 3539 */               int pix1 = this.m_texture[(ofs + 1)];
/* 3540 */               if (ofs < lastRowStart) ofs += this.TEX_WIDTH;
/* 3541 */               int pix2 = this.m_texture[ofs];
/* 3542 */               int pix3 = this.m_texture[(ofs + 1)];
/*      */               
/*      */ 
/* 3545 */               int red0 = pix0 & 0xFF0000;
/* 3546 */               int red2 = pix2 & 0xFF0000;
/* 3547 */               int up = red0 + (((pix1 & 0xFF0000) - red0) * iui >> 7);
/* 3548 */               int dn = red2 + (((pix3 & 0xFF0000) - red2) * iui >> 7);
/* 3549 */               int red = up + ((dn - up) * ivi >> 7) >> 16;
/*      */               
/*      */ 
/* 3552 */               red0 = pix0 & 0xFF00;
/* 3553 */               red2 = pix2 & 0xFF00;
/* 3554 */               up = red0 + (((pix1 & 0xFF00) - red0) * iui >> 7);
/* 3555 */               dn = red2 + (((pix3 & 0xFF00) - red2) * iui >> 7);
/* 3556 */               int grn = up + ((dn - up) * ivi >> 7) >> 8;
/*      */               
/*      */ 
/* 3559 */               red0 = pix0 & 0xFF;
/* 3560 */               red2 = pix2 & 0xFF;
/* 3561 */               up = red0 + (((pix1 & 0xFF) - red0) * iui >> 7);
/* 3562 */               dn = red2 + (((pix3 & 0xFF) - red2) * iui >> 7);
/* 3563 */               int blu = up + ((dn - up) * ivi >> 7);
/*      */               
/*      */ 
/* 3566 */               pix0 >>>= 24;
/* 3567 */               pix2 >>>= 24;
/* 3568 */               up = pix0 + (((pix1 >>> 24) - pix0) * iui >> 7);
/* 3569 */               dn = pix2 + (((pix3 >>> 24) - pix2) * iui >> 7);
/* 3570 */               al = up + ((dn - up) * ivi >> 7);
/*      */             }
/*      */             else {
/* 3573 */               blu = this.m_texture[((iv >> 16) * this.TEX_WIDTH + (iu >> 16))];
/* 3574 */               al = blu >>> 24;
/* 3575 */               red = (blu & 0xFF0000) >> 16;
/* 3576 */               grn = (blu & 0xFF00) >> 8;
/* 3577 */               blu &= 0xFF;
/*      */             }
/*      */             
/*      */ 
/* 3581 */             int red = red * ir >>> 8;
/* 3582 */             int grn = grn * ig >>> 16;
/* 3583 */             int blu = blu * ib >>> 24;
/*      */             
/*      */ 
/* 3586 */             int bb = this.m_pixels[xstart];
/* 3587 */             int br = bb & 0xFF0000;
/* 3588 */             int bg = bb & 0xFF00;
/* 3589 */             bb &= 0xFF;
/*      */             
/*      */ 
/* 3592 */             this.m_pixels[xstart] = 
/*      */             
/*      */ 
/* 3595 */               (0xFF000000 | br + ((red - br) * al >> 8) & 0xFF0000 | bg + ((grn - bg) * al >> 8) & 0xFF00 | bb + ((blu - bb) * al >> 8) & 0xFF);
/*      */           }
/*      */         }
/*      */         catch (Exception localException) {}
/*      */         
/* 3600 */         xpixel++;
/* 3601 */         if (!accurateMode) {
/* 3602 */           iu += this.iuadd;
/* 3603 */           iv += this.ivadd;
/*      */         }
/* 3605 */         ir += this.iradd;
/* 3606 */         ig += this.igadd;
/* 3607 */         ib += this.ibadd;
/* 3608 */         iz += this.izadd;
/*      */       }
/* 3610 */       ypixel++;
/* 3611 */       ytop += this.SCREEN_WIDTH;
/* 3612 */       this.xleft += leftadd;
/* 3613 */       this.xrght += rghtadd;
/* 3614 */       this.uleft += this.uleftadd;
/* 3615 */       this.vleft += this.vleftadd;
/* 3616 */       this.rleft += this.rleftadd;
/* 3617 */       this.gleft += this.gleftadd;
/* 3618 */       this.bleft += this.bleftadd;
/* 3619 */       this.zleft += this.zleftadd;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void drawsegment_gouraud_texture32_alpha(float leftadd, float rghtadd, int ytop, int ybottom)
/*      */   {
/* 3635 */     int ypixel = ytop;
/* 3636 */     int lastRowStart = this.m_texture.length - this.TEX_WIDTH - 2;
/* 3637 */     boolean accurateMode = this.parent.hints[7];
/* 3638 */     float screenx = 0.0F;float screeny = 0.0F;float screenz = 0.0F;
/* 3639 */     float a = 0.0F;float b = 0.0F;float c = 0.0F;
/* 3640 */     int linearInterpPower = TEX_INTERP_POWER;
/* 3641 */     int linearInterpLength = 1 << linearInterpPower;
/* 3642 */     if (accurateMode) {
/* 3643 */       if (precomputeAccurateTexturing()) {
/* 3644 */         this.newax *= linearInterpLength;
/* 3645 */         this.newbx *= linearInterpLength;
/* 3646 */         this.newcx *= linearInterpLength;
/* 3647 */         screenz = this.nearPlaneDepth;
/* 3648 */         this.firstSegment = false;
/*      */       } else {
/* 3650 */         accurateMode = false;
/*      */       }
/*      */     }
/*      */     
/* 3654 */     ytop *= this.SCREEN_WIDTH;
/* 3655 */     ybottom *= this.SCREEN_WIDTH;
/*      */     
/*      */ 
/* 3658 */     float iuf = this.iuadd;
/* 3659 */     float ivf = this.ivadd;
/* 3660 */     float irf = this.iradd;
/* 3661 */     float igf = this.igadd;
/* 3662 */     float ibf = this.ibadd;
/* 3663 */     float iaf = this.iaadd;
/*      */     
/* 3665 */     while (ytop < ybottom) {
/* 3666 */       int xstart = (int)(this.xleft + 0.5F);
/* 3667 */       if (xstart < 0) {
/* 3668 */         xstart = 0;
/*      */       }
/* 3670 */       int xpixel = xstart;
/*      */       
/* 3672 */       int xend = (int)(this.xrght + 0.5F);
/* 3673 */       if (xend > this.SCREEN_WIDTH)
/* 3674 */         xend = this.SCREEN_WIDTH;
/* 3675 */       float xdiff = xstart + 0.5F - this.xleft;
/*      */       
/* 3677 */       int iu = (int)(iuf * xdiff + this.uleft);
/* 3678 */       int iv = (int)(ivf * xdiff + this.vleft);
/* 3679 */       int ir = (int)(irf * xdiff + this.rleft);
/* 3680 */       int ig = (int)(igf * xdiff + this.gleft);
/* 3681 */       int ib = (int)(ibf * xdiff + this.bleft);
/* 3682 */       int ia = (int)(iaf * xdiff + this.aleft);
/* 3683 */       float iz = this.izadd * xdiff + this.zleft;
/*      */       
/* 3685 */       xstart += ytop;
/* 3686 */       xend += ytop;
/* 3687 */       if (accurateMode) {
/* 3688 */         screenx = this.xmult * (xpixel + 0.5F - this.SCREEN_WIDTH / 2.0F);
/* 3689 */         screeny = this.ymult * (ypixel + 0.5F - this.SCREEN_HEIGHT / 2.0F);
/* 3690 */         a = screenx * this.ax + screeny * this.ay + screenz * this.az;
/* 3691 */         b = screenx * this.bx + screeny * this.by + screenz * this.bz;
/* 3692 */         c = screenx * this.cx + screeny * this.cy + screenz * this.cz;
/*      */       }
/* 3694 */       boolean goingIn = (this.newcx > 0.0F ? 1 : 0) != (c > 0.0F ? 1 : 0);
/* 3695 */       int interpCounter = 0;
/* 3696 */       int deltaU = 0;int deltaV = 0;
/* 3697 */       float fu = 0.0F;float fv = 0.0F;
/* 3698 */       float oldfu = 0.0F;float oldfv = 0.0F;
/*      */       
/* 3700 */       if ((accurateMode) && (goingIn)) {
/* 3701 */         int rightOffset = (xend - xstart - 1) % linearInterpLength;
/* 3702 */         int leftOffset = linearInterpLength - rightOffset;
/* 3703 */         float rightOffset2 = rightOffset / linearInterpLength;
/* 3704 */         float leftOffset2 = leftOffset / linearInterpLength;
/* 3705 */         interpCounter = leftOffset;
/* 3706 */         float ao = a - leftOffset2 * this.newax;
/* 3707 */         float bo = b - leftOffset2 * this.newbx;
/* 3708 */         float co = c - leftOffset2 * this.newcx;
/* 3709 */         float oneoverc = 65536.0F / co;
/* 3710 */         oldfu = ao * oneoverc;oldfv = bo * oneoverc;
/* 3711 */         a += rightOffset2 * this.newax;
/* 3712 */         b += rightOffset2 * this.newbx;
/* 3713 */         c += rightOffset2 * this.newcx;
/* 3714 */         oneoverc = 65536.0F / c;
/* 3715 */         fu = a * oneoverc;fv = b * oneoverc;
/* 3716 */         deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 3717 */         deltaV = (int)(fv - oldfv) >> linearInterpPower;
/* 3718 */         iu = (int)oldfu + (leftOffset - 1) * deltaU;iv = (int)oldfv + (leftOffset - 1) * deltaV;
/*      */       } else {
/* 3720 */         float preoneoverc = 65536.0F / c;
/* 3721 */         fu = a * preoneoverc;
/* 3722 */         fv = b * preoneoverc;
/*      */       }
/* 3725 */       for (; 
/* 3725 */           xstart < xend; xstart++) {
/* 3726 */         if (accurateMode) {
/* 3727 */           if (interpCounter == linearInterpLength) interpCounter = 0;
/* 3728 */           if (interpCounter == 0) {
/* 3729 */             a += this.newax;
/* 3730 */             b += this.newbx;
/* 3731 */             c += this.newcx;
/* 3732 */             float oneoverc = 65536.0F / c;
/* 3733 */             oldfu = fu;oldfv = fv;
/* 3734 */             fu = a * oneoverc;fv = b * oneoverc;
/* 3735 */             iu = (int)oldfu;iv = (int)oldfv;
/* 3736 */             deltaU = (int)(fu - oldfu) >> linearInterpPower;
/* 3737 */             deltaV = (int)(fv - oldfv) >> linearInterpPower;
/*      */           } else {
/* 3739 */             iu += deltaU;
/* 3740 */             iv += deltaV;
/*      */           }
/* 3742 */           interpCounter++;
/*      */         }
/*      */         
/*      */ 
/*      */         try
/*      */         {
/* 3748 */           if ((this.noDepthTest) || (iz <= this.m_zbuffer[xstart]))
/*      */           {
/*      */ 
/*      */ 
/* 3752 */             int al = ia >> 16;
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3758 */             if (this.m_bilinear) {
/* 3759 */               int ofs = (iv >> 16) * this.TEX_WIDTH + (iu >> 16);
/* 3760 */               int iui = (iu & 0xFFFF) >> 9;
/* 3761 */               int ivi = (iv & 0xFFFF) >> 9;
/*      */               
/*      */ 
/* 3764 */               int pix0 = this.m_texture[ofs];
/* 3765 */               int pix1 = this.m_texture[(ofs + 1)];
/* 3766 */               if (ofs < lastRowStart) ofs += this.TEX_WIDTH;
/* 3767 */               int pix2 = this.m_texture[ofs];
/* 3768 */               int pix3 = this.m_texture[(ofs + 1)];
/*      */               
/*      */ 
/* 3771 */               int red0 = pix0 & 0xFF0000;
/* 3772 */               int red2 = pix2 & 0xFF0000;
/* 3773 */               int up = red0 + (((pix1 & 0xFF0000) - red0) * iui >> 7);
/* 3774 */               int dn = red2 + (((pix3 & 0xFF0000) - red2) * iui >> 7);
/* 3775 */               int red = up + ((dn - up) * ivi >> 7) >> 16;
/*      */               
/*      */ 
/* 3778 */               red0 = pix0 & 0xFF00;
/* 3779 */               red2 = pix2 & 0xFF00;
/* 3780 */               up = red0 + (((pix1 & 0xFF00) - red0) * iui >> 7);
/* 3781 */               dn = red2 + (((pix3 & 0xFF00) - red2) * iui >> 7);
/* 3782 */               int grn = up + ((dn - up) * ivi >> 7) >> 8;
/*      */               
/*      */ 
/* 3785 */               red0 = pix0 & 0xFF;
/* 3786 */               red2 = pix2 & 0xFF;
/* 3787 */               up = red0 + (((pix1 & 0xFF) - red0) * iui >> 7);
/* 3788 */               dn = red2 + (((pix3 & 0xFF) - red2) * iui >> 7);
/* 3789 */               int blu = up + ((dn - up) * ivi >> 7);
/*      */               
/*      */ 
/* 3792 */               pix0 >>>= 24;
/* 3793 */               pix2 >>>= 24;
/* 3794 */               up = pix0 + (((pix1 >>> 24) - pix0) * iui >> 7);
/* 3795 */               dn = pix2 + (((pix3 >>> 24) - pix2) * iui >> 7);
/* 3796 */               al = al * (up + ((dn - up) * ivi >> 7)) >> 8;
/*      */             } else {
/* 3798 */               blu = this.m_texture[((iv >> 16) * this.TEX_WIDTH + (iu >> 16))];
/* 3799 */               al = al * (blu >>> 24) >> 8;
/* 3800 */               red = (blu & 0xFF0000) >> 16;
/* 3801 */               grn = (blu & 0xFF00) >> 8;
/* 3802 */               blu &= 0xFF;
/*      */             }
/*      */             
/*      */ 
/* 3806 */             int red = red * ir >>> 8;
/* 3807 */             int grn = grn * ig >>> 16;
/* 3808 */             int blu = blu * ib >>> 24;
/*      */             
/*      */ 
/* 3811 */             int bb = this.m_pixels[xstart];
/* 3812 */             int br = bb & 0xFF0000;
/* 3813 */             int bg = bb & 0xFF00;
/* 3814 */             bb &= 0xFF;
/*      */             
/*      */ 
/* 3817 */             this.m_pixels[xstart] = 
/*      */             
/*      */ 
/* 3820 */               (0xFF000000 | br + ((red - br) * al >> 8) & 0xFF0000 | bg + ((grn - bg) * al >> 8) & 0xFF00 | bb + ((blu - bb) * al >> 8) & 0xFF);
/*      */           }
/*      */         }
/*      */         catch (Exception localException) {}
/*      */         
/*      */ 
/* 3826 */         xpixel++;
/* 3827 */         if (!accurateMode) {
/* 3828 */           iu += this.iuadd;
/* 3829 */           iv += this.ivadd;
/*      */         }
/* 3831 */         ir += this.iradd;
/* 3832 */         ig += this.igadd;
/* 3833 */         ib += this.ibadd;
/* 3834 */         ia += this.iaadd;
/* 3835 */         iz += this.izadd;
/*      */       }
/* 3837 */       ypixel++;
/* 3838 */       ytop += this.SCREEN_WIDTH;
/* 3839 */       this.xleft += leftadd;
/* 3840 */       this.xrght += rghtadd;
/* 3841 */       this.uleft += this.uleftadd;
/* 3842 */       this.vleft += this.vleftadd;
/* 3843 */       this.rleft += this.rleftadd;
/* 3844 */       this.gleft += this.gleftadd;
/* 3845 */       this.bleft += this.bleftadd;
/* 3846 */       this.aleft += this.aleftadd;
/* 3847 */       this.zleft += this.zleftadd;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/AndrewKanarek/Desktop/MICHIGAN/KiefferWork/scripts-zibo/JavaApps/EllinghamDiagram/core.jar!/processing/core/PTriangle.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */