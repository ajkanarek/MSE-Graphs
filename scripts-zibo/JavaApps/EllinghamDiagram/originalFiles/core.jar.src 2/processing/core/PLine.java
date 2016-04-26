/*      */ package processing.core;
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PLine
/*      */   implements PConstants
/*      */ {
/*      */   private int[] m_pixels;
/*      */   
/*      */ 
/*      */   private float[] m_zbuffer;
/*      */   
/*      */ 
/*      */   private int m_index;
/*      */   
/*      */ 
/*      */   static final int R_COLOR = 1;
/*      */   
/*      */ 
/*      */   static final int R_ALPHA = 2;
/*      */   
/*      */ 
/*      */   static final int R_SPATIAL = 8;
/*      */   
/*      */ 
/*      */   static final int R_THICK = 4;
/*      */   
/*      */ 
/*      */   static final int R_SMOOTH = 16;
/*      */   
/*      */ 
/*      */   private int SCREEN_WIDTH;
/*      */   
/*      */ 
/*      */   private int SCREEN_HEIGHT;
/*      */   
/*      */ 
/*      */   private int SCREEN_WIDTH1;
/*      */   
/*      */ 
/*      */   private int SCREEN_HEIGHT1;
/*      */   
/*      */ 
/*      */   public boolean INTERPOLATE_RGB;
/*      */   
/*      */ 
/*      */   public boolean INTERPOLATE_ALPHA;
/*      */   
/*      */ 
/*      */   public boolean INTERPOLATE_Z;
/*      */   
/*      */ 
/*      */   public boolean INTERPOLATE_THICK;
/*      */   
/*      */   private boolean SMOOTH;
/*      */   
/*      */   private int m_stroke;
/*      */   
/*      */   public int m_drawFlags;
/*      */   
/*      */   private float[] x_array;
/*      */   
/*      */   private float[] y_array;
/*      */   
/*      */   private float[] z_array;
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
/*      */   private float m_r0;
/*      */   
/*      */   private float m_g0;
/*      */   
/*      */   private float m_b0;
/*      */   
/*      */   private float m_a0;
/*      */   
/*      */   private float m_z0;
/*      */   
/*      */   private float dz;
/*      */   
/*      */   private float dr;
/*      */   
/*      */   private float dg;
/*      */   
/*      */   private float db;
/*      */   
/*      */   private float da;
/*      */   
/*      */   private PGraphics parent;
/*      */   
/*      */ 
/*      */   public PLine(PGraphics g)
/*      */   {
/*  104 */     this.INTERPOLATE_Z = false;
/*      */     
/*  106 */     this.x_array = new float[2];
/*  107 */     this.y_array = new float[2];
/*  108 */     this.z_array = new float[2];
/*  109 */     this.r_array = new float[2];
/*  110 */     this.g_array = new float[2];
/*  111 */     this.b_array = new float[2];
/*  112 */     this.a_array = new float[2];
/*      */     
/*  114 */     this.parent = g;
/*      */   }
/*      */   
/*      */ 
/*      */   public void reset()
/*      */   {
/*  120 */     this.SCREEN_WIDTH = this.parent.width;
/*  121 */     this.SCREEN_HEIGHT = this.parent.height;
/*  122 */     this.SCREEN_WIDTH1 = (this.SCREEN_WIDTH - 1);
/*  123 */     this.SCREEN_HEIGHT1 = (this.SCREEN_HEIGHT - 1);
/*      */     
/*  125 */     this.m_pixels = this.parent.pixels;
/*      */     
/*  127 */     if ((this.parent instanceof PGraphics3D)) {
/*  128 */       this.m_zbuffer = ((PGraphics3D)this.parent).zbuffer;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  133 */     this.INTERPOLATE_RGB = false;
/*  134 */     this.INTERPOLATE_ALPHA = false;
/*      */     
/*  136 */     this.m_drawFlags = 0;
/*  137 */     this.m_index = 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setVertices(float x0, float y0, float z0, float x1, float y1, float z1)
/*      */   {
/*  146 */     if ((z0 != z1) || (z0 != 0.0F) || (z1 != 0.0F) || (this.INTERPOLATE_Z)) {
/*  147 */       this.INTERPOLATE_Z = true;
/*  148 */       this.m_drawFlags |= 0x8;
/*      */     } else {
/*  150 */       this.INTERPOLATE_Z = false;
/*  151 */       this.m_drawFlags &= 0xFFFFFFF7;
/*      */     }
/*      */     
/*  154 */     this.z_array[0] = z0;
/*  155 */     this.z_array[1] = z1;
/*      */     
/*  157 */     this.x_array[0] = x0;
/*  158 */     this.x_array[1] = x1;
/*      */     
/*  160 */     this.y_array[0] = y0;
/*  161 */     this.y_array[1] = y1;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setIntensities(float r0, float g0, float b0, float a0, float r1, float g1, float b1, float a1)
/*      */   {
/*  167 */     this.a_array[0] = ((a0 * 253.0F + 1.0F) * 65536.0F);
/*  168 */     this.a_array[1] = ((a1 * 253.0F + 1.0F) * 65536.0F);
/*      */     
/*      */ 
/*  171 */     if ((a0 != 1.0F) || (a1 != 1.0F)) {
/*  172 */       this.INTERPOLATE_ALPHA = true;
/*  173 */       this.m_drawFlags |= 0x2;
/*      */     } else {
/*  175 */       this.INTERPOLATE_ALPHA = false;
/*  176 */       this.m_drawFlags &= 0xFFFFFFFD;
/*      */     }
/*      */     
/*      */ 
/*  180 */     this.r_array[0] = ((r0 * 253.0F + 1.0F) * 65536.0F);
/*  181 */     this.r_array[1] = ((r1 * 253.0F + 1.0F) * 65536.0F);
/*      */     
/*  183 */     this.g_array[0] = ((g0 * 253.0F + 1.0F) * 65536.0F);
/*  184 */     this.g_array[1] = ((g1 * 253.0F + 1.0F) * 65536.0F);
/*      */     
/*  186 */     this.b_array[0] = ((b0 * 253.0F + 1.0F) * 65536.0F);
/*  187 */     this.b_array[1] = ((b1 * 253.0F + 1.0F) * 65536.0F);
/*      */     
/*      */ 
/*  190 */     if (r0 != r1) {
/*  191 */       this.INTERPOLATE_RGB = true;
/*  192 */       this.m_drawFlags |= 0x1;
/*      */     }
/*  194 */     else if (g0 != g1) {
/*  195 */       this.INTERPOLATE_RGB = true;
/*  196 */       this.m_drawFlags |= 0x1;
/*      */     }
/*  198 */     else if (b0 != b1) {
/*  199 */       this.INTERPOLATE_RGB = true;
/*  200 */       this.m_drawFlags |= 0x1;
/*      */     }
/*      */     else
/*      */     {
/*  204 */       this.m_stroke = 
/*  205 */         (0xFF000000 | (int)(255.0F * r0) << 16 | (int)(255.0F * g0) << 8 | (int)(255.0F * b0));
/*  206 */       this.INTERPOLATE_RGB = false;
/*  207 */       this.m_drawFlags &= 0xFFFFFFFE;
/*      */     }
/*      */   }
/*      */   
/*      */   public void setIndex(int index)
/*      */   {
/*  213 */     this.m_index = index;
/*      */     
/*  215 */     if (this.m_index == -1)
/*      */     {
/*      */ 
/*  218 */       this.m_index = 0;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void draw()
/*      */   {
/*  227 */     boolean visible = true;
/*      */     
/*  229 */     if (this.parent.smooth) {
/*  230 */       this.SMOOTH = true;
/*  231 */       this.m_drawFlags |= 0x10;
/*      */     }
/*      */     else {
/*  234 */       this.SMOOTH = false;
/*  235 */       this.m_drawFlags &= 0xFFFFFFEF;
/*      */     }
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
/*  260 */     visible = lineClipping();
/*  261 */     if (!visible) {
/*  262 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  272 */     boolean yLonger = false;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  282 */     if (this.x_array[1] < this.x_array[0])
/*      */     {
/*      */ 
/*  285 */       float t = this.x_array[1];this.x_array[1] = this.x_array[0];this.x_array[0] = t;
/*  286 */       t = this.y_array[1];this.y_array[1] = this.y_array[0];this.y_array[0] = t;
/*  287 */       t = this.z_array[1];this.z_array[1] = this.z_array[0];this.z_array[0] = t;
/*      */       
/*  289 */       t = this.r_array[1];this.r_array[1] = this.r_array[0];this.r_array[0] = t;
/*  290 */       t = this.g_array[1];this.g_array[1] = this.g_array[0];this.g_array[0] = t;
/*  291 */       t = this.b_array[1];this.b_array[1] = this.b_array[0];this.b_array[0] = t;
/*  292 */       t = this.a_array[1];this.a_array[1] = this.a_array[0];this.a_array[0] = t;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  297 */     int longLen = (int)this.x_array[1] - (int)this.x_array[0];
/*  298 */     int shortLen = (int)this.y_array[1] - (int)this.y_array[0];
/*      */     
/*  300 */     if (Math.abs(shortLen) > Math.abs(longLen)) {
/*  301 */       int swap = shortLen;
/*  302 */       shortLen = longLen;
/*  303 */       longLen = swap;
/*  304 */       yLonger = true; }
/*      */     int length;
/*      */     int xi;
/*      */     int yi;
/*      */     int length;
/*  309 */     if (longLen < 0)
/*      */     {
/*  311 */       this.o0 = 1;
/*  312 */       this.o1 = 0;
/*      */       
/*  314 */       int xi = (int)this.x_array[1];
/*  315 */       int yi = (int)this.y_array[1];
/*      */       
/*  317 */       length = -longLen;
/*      */     }
/*      */     else {
/*  320 */       this.o0 = 0;
/*  321 */       this.o1 = 1;
/*      */       
/*  323 */       xi = (int)this.x_array[0];
/*  324 */       yi = (int)this.y_array[0];
/*      */       
/*  326 */       length = longLen;
/*      */     }
/*      */     int dt;
/*      */     int dt;
/*  330 */     if (length == 0) {
/*  331 */       dt = 0;
/*      */     } else {
/*  333 */       dt = (shortLen << 16) / longLen;
/*      */     }
/*      */     
/*  336 */     this.m_r0 = this.r_array[this.o0];
/*  337 */     this.m_g0 = this.g_array[this.o0];
/*  338 */     this.m_b0 = this.b_array[this.o0];
/*      */     
/*  340 */     if (this.INTERPOLATE_RGB) {
/*  341 */       this.dr = ((this.r_array[this.o1] - this.r_array[this.o0]) / length);
/*  342 */       this.dg = ((this.g_array[this.o1] - this.g_array[this.o0]) / length);
/*  343 */       this.db = ((this.b_array[this.o1] - this.b_array[this.o0]) / length);
/*      */     } else {
/*  345 */       this.dr = 0.0F;
/*  346 */       this.dg = 0.0F;
/*  347 */       this.db = 0.0F;
/*      */     }
/*      */     
/*  350 */     this.m_a0 = this.a_array[this.o0];
/*      */     
/*  352 */     if (this.INTERPOLATE_ALPHA) {
/*  353 */       this.da = ((this.a_array[this.o1] - this.a_array[this.o0]) / length);
/*      */     } else {
/*  355 */       this.da = 0.0F;
/*      */     }
/*      */     
/*  358 */     this.m_z0 = this.z_array[this.o0];
/*      */     
/*      */ 
/*  361 */     if (this.INTERPOLATE_Z) {
/*  362 */       this.dz = ((this.z_array[this.o1] - this.z_array[this.o0]) / length);
/*      */     } else {
/*  364 */       this.dz = 0.0F;
/*      */     }
/*      */     
/*      */ 
/*  368 */     if (length == 0) {
/*  369 */       if (this.INTERPOLATE_ALPHA) {
/*  370 */         drawPoint_alpha(xi, yi);
/*      */       } else {
/*  372 */         drawPoint(xi, yi);
/*      */       }
/*  374 */       return;
/*      */     }
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
/*  391 */     if (this.SMOOTH)
/*      */     {
/*      */ 
/*      */ 
/*  395 */       drawLine_smooth(xi, yi, dt, length, yLonger);
/*      */ 
/*      */ 
/*      */     }
/*  399 */     else if (this.m_drawFlags == 0) {
/*  400 */       drawLine_plain(xi, yi, dt, length, yLonger);
/*      */     }
/*  402 */     else if (this.m_drawFlags == 2) {
/*  403 */       drawLine_plain_alpha(xi, yi, dt, length, yLonger);
/*      */     }
/*  405 */     else if (this.m_drawFlags == 1) {
/*  406 */       drawLine_color(xi, yi, dt, length, yLonger);
/*      */     }
/*  408 */     else if (this.m_drawFlags == 3) {
/*  409 */       drawLine_color_alpha(xi, yi, dt, length, yLonger);
/*      */     }
/*  411 */     else if (this.m_drawFlags == 8) {
/*  412 */       drawLine_plain_spatial(xi, yi, dt, length, yLonger);
/*      */     }
/*  414 */     else if (this.m_drawFlags == 10) {
/*  415 */       drawLine_plain_alpha_spatial(xi, yi, dt, length, yLonger);
/*      */     }
/*  417 */     else if (this.m_drawFlags == 9) {
/*  418 */       drawLine_color_spatial(xi, yi, dt, length, yLonger);
/*      */     }
/*  420 */     else if (this.m_drawFlags == 11) {
/*  421 */       drawLine_color_alpha_spatial(xi, yi, dt, length, yLonger);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean lineClipping()
/*      */   {
/*  430 */     int code1 = lineClipCode(this.x_array[0], this.y_array[0]);
/*  431 */     int code2 = lineClipCode(this.x_array[1], this.y_array[1]);
/*  432 */     int dip = code1 | code2;
/*      */     
/*  434 */     if ((code1 & code2) != 0)
/*      */     {
/*  436 */       return false;
/*      */     }
/*  438 */     if (dip != 0)
/*      */     {
/*      */ 
/*  441 */       float a0 = 0.0F;float a1 = 1.0F;float a = 0.0F;
/*      */       
/*  443 */       for (int i = 0; i < 4; i++) {
/*  444 */         if ((dip >> i) % 2 == 1) {
/*  445 */           a = lineSlope(this.x_array[0], this.y_array[0], this.x_array[1], this.y_array[1], i + 1);
/*  446 */           if ((code1 >> i) % 2 == 1) {
/*  447 */             a0 = a > a0 ? a : a0;
/*      */           } else {
/*  449 */             a1 = a < a1 ? a : a1;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*  454 */       if (a0 > a1) {
/*  455 */         return false;
/*      */       }
/*  457 */       float xt = this.x_array[0];
/*  458 */       float yt = this.y_array[0];
/*      */       
/*  460 */       this.x_array[0] = (xt + a0 * (this.x_array[1] - xt));
/*  461 */       this.y_array[0] = (yt + a0 * (this.y_array[1] - yt));
/*  462 */       this.x_array[1] = (xt + a1 * (this.x_array[1] - xt));
/*  463 */       this.y_array[1] = (yt + a1 * (this.y_array[1] - yt));
/*      */       
/*      */ 
/*  466 */       if (this.INTERPOLATE_RGB) {
/*  467 */         float t = this.r_array[0];
/*  468 */         this.r_array[0] = (t + a0 * (this.r_array[1] - t));
/*  469 */         this.r_array[1] = (t + a1 * (this.r_array[1] - t));
/*  470 */         t = this.g_array[0];
/*  471 */         this.g_array[0] = (t + a0 * (this.g_array[1] - t));
/*  472 */         this.g_array[1] = (t + a1 * (this.g_array[1] - t));
/*  473 */         t = this.b_array[0];
/*  474 */         this.b_array[0] = (t + a0 * (this.b_array[1] - t));
/*  475 */         this.b_array[1] = (t + a1 * (this.b_array[1] - t));
/*      */       }
/*      */       
/*  478 */       if (this.INTERPOLATE_ALPHA) {
/*  479 */         float t = this.a_array[0];
/*  480 */         this.a_array[0] = (t + a0 * (this.a_array[1] - t));
/*  481 */         this.a_array[1] = (t + a1 * (this.a_array[1] - t));
/*      */       }
/*      */     }
/*      */     
/*  485 */     return true;
/*      */   }
/*      */   
/*      */   private int lineClipCode(float xi, float yi)
/*      */   {
/*  490 */     int xmin = 0;
/*  491 */     int ymin = 0;
/*  492 */     int xmax = this.SCREEN_WIDTH1;
/*  493 */     int ymax = this.SCREEN_HEIGHT1;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  500 */     return (yi < ymin ? 8 : 0) | ((int)yi > ymax ? 4 : 0) | 
/*  501 */       (xi < xmin ? 2 : 0) | ((int)xi > xmax ? 1 : 0);
/*      */   }
/*      */   
/*      */   private float lineSlope(float x1, float y1, float x2, float y2, int border)
/*      */   {
/*  506 */     int xmin = 0;
/*  507 */     int ymin = 0;
/*  508 */     int xmax = this.SCREEN_WIDTH1;
/*  509 */     int ymax = this.SCREEN_HEIGHT1;
/*      */     
/*  511 */     switch (border) {
/*  512 */     case 4:  return (ymin - y1) / (y2 - y1);
/*  513 */     case 3:  return (ymax - y1) / (y2 - y1);
/*  514 */     case 2:  return (xmin - x1) / (x2 - x1);
/*  515 */     case 1:  return (xmax - x1) / (x2 - x1);
/*      */     }
/*  517 */     return -1.0F;
/*      */   }
/*      */   
/*      */   private void drawPoint(int x0, int y0)
/*      */   {
/*  522 */     float iz = this.m_z0;
/*  523 */     int offset = y0 * this.SCREEN_WIDTH + x0;
/*      */     
/*  525 */     if (this.m_zbuffer == null) {
/*  526 */       this.m_pixels[offset] = this.m_stroke;
/*      */ 
/*      */     }
/*  529 */     else if (iz <= this.m_zbuffer[offset]) {
/*  530 */       this.m_pixels[offset] = this.m_stroke;
/*  531 */       this.m_zbuffer[offset] = iz;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void drawPoint_alpha(int x0, int y0)
/*      */   {
/*  538 */     int ia = (int)this.a_array[0];
/*  539 */     int pr = this.m_stroke & 0xFF0000;
/*  540 */     int pg = this.m_stroke & 0xFF00;
/*  541 */     int pb = this.m_stroke & 0xFF;
/*  542 */     float iz = this.m_z0;
/*  543 */     int offset = y0 * this.SCREEN_WIDTH + x0;
/*      */     
/*  545 */     if ((this.m_zbuffer == null) || (iz <= this.m_zbuffer[offset])) {
/*  546 */       int alpha = ia >> 16;
/*  547 */       int r0 = this.m_pixels[offset];
/*  548 */       int g0 = r0 & 0xFF00;
/*  549 */       int b0 = r0 & 0xFF;
/*  550 */       r0 &= 0xFF0000;
/*      */       
/*  552 */       r0 += ((pr - r0) * alpha >> 8);
/*  553 */       g0 += ((pg - g0) * alpha >> 8);
/*  554 */       b0 += ((pb - b0) * alpha >> 8);
/*      */       
/*  556 */       this.m_pixels[offset] = 
/*  557 */         (0xFF000000 | r0 & 0xFF0000 | g0 & 0xFF00 | b0 & 0xFF);
/*  558 */       if (this.m_zbuffer != null) { this.m_zbuffer[offset] = iz;
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
/*      */   private void drawLine_plain(int x0, int y0, int dt, int length, boolean vertical)
/*      */   {
/*  573 */     int offset = 0;
/*      */     
/*  575 */     if (vertical)
/*      */     {
/*  577 */       length += y0;
/*  578 */       for (int j = 32768 + (x0 << 16); y0 <= length; y0++) {
/*  579 */         offset = y0 * this.SCREEN_WIDTH + (j >> 16);
/*  580 */         this.m_pixels[offset] = this.m_stroke;
/*  581 */         if (this.m_zbuffer != null) this.m_zbuffer[offset] = this.m_z0;
/*  582 */         j += dt;
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  587 */       length += x0;
/*  588 */       for (int j = 32768 + (y0 << 16); x0 <= length; x0++) {
/*  589 */         offset = (j >> 16) * this.SCREEN_WIDTH + x0;
/*  590 */         this.m_pixels[offset] = this.m_stroke;
/*  591 */         if (this.m_zbuffer != null) this.m_zbuffer[offset] = this.m_z0;
/*  592 */         j += dt;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void drawLine_plain_alpha(int x0, int y0, int dt, int length, boolean vertical)
/*      */   {
/*  600 */     int offset = 0;
/*      */     
/*  602 */     int pr = this.m_stroke & 0xFF0000;
/*  603 */     int pg = this.m_stroke & 0xFF00;
/*  604 */     int pb = this.m_stroke & 0xFF;
/*      */     
/*  606 */     int ia = (int)this.m_a0;
/*      */     
/*  608 */     if (vertical) {
/*  609 */       length += y0;
/*  610 */       for (int j = 32768 + (x0 << 16); y0 <= length; y0++) {
/*  611 */         offset = y0 * this.SCREEN_WIDTH + (j >> 16);
/*      */         
/*  613 */         int alpha = ia >> 16;
/*  614 */         int r0 = this.m_pixels[offset];
/*  615 */         int g0 = r0 & 0xFF00;
/*  616 */         int b0 = r0 & 0xFF;
/*  617 */         r0 &= 0xFF0000;
/*  618 */         r0 += ((pr - r0) * alpha >> 8);
/*  619 */         g0 += ((pg - g0) * alpha >> 8);
/*  620 */         b0 += ((pb - b0) * alpha >> 8);
/*      */         
/*  622 */         this.m_pixels[offset] = 
/*  623 */           (0xFF000000 | r0 & 0xFF0000 | g0 & 0xFF00 | b0 & 0xFF);
/*      */         
/*      */ 
/*  626 */         ia = (int)(ia + this.da);
/*  627 */         j += dt;
/*      */       }
/*      */     }
/*      */     else {
/*  631 */       length += x0;
/*  632 */       for (int j = 32768 + (y0 << 16); x0 <= length; x0++) {
/*  633 */         offset = (j >> 16) * this.SCREEN_WIDTH + x0;
/*      */         
/*  635 */         int alpha = ia >> 16;
/*  636 */         int r0 = this.m_pixels[offset];
/*  637 */         int g0 = r0 & 0xFF00;
/*  638 */         int b0 = r0 & 0xFF;
/*  639 */         r0 &= 0xFF0000;
/*  640 */         r0 += ((pr - r0) * alpha >> 8);
/*  641 */         g0 += ((pg - g0) * alpha >> 8);
/*  642 */         b0 += ((pb - b0) * alpha >> 8);
/*      */         
/*  644 */         this.m_pixels[offset] = 
/*  645 */           (0xFF000000 | r0 & 0xFF0000 | g0 & 0xFF00 | b0 & 0xFF);
/*      */         
/*      */ 
/*  648 */         ia = (int)(ia + this.da);
/*  649 */         j += dt;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void drawLine_color(int x0, int y0, int dt, int length, boolean vertical)
/*      */   {
/*  657 */     int offset = 0;
/*      */     
/*  659 */     int ir = (int)this.m_r0;
/*  660 */     int ig = (int)this.m_g0;
/*  661 */     int ib = (int)this.m_b0;
/*      */     
/*  663 */     if (vertical) {
/*  664 */       length += y0;
/*  665 */       for (int j = 32768 + (x0 << 16); y0 <= length; y0++) {
/*  666 */         offset = y0 * this.SCREEN_WIDTH + (j >> 16);
/*  667 */         this.m_pixels[offset] = 
/*  668 */           (0xFF000000 | ir & 0xFF0000 | ig >> 8 & 0xFF00 | ib >> 16);
/*  669 */         if (this.m_zbuffer != null) this.m_zbuffer[offset] = this.m_z0;
/*  670 */         ir = (int)(ir + this.dr);
/*  671 */         ig = (int)(ig + this.dg);
/*  672 */         ib = (int)(ib + this.db);
/*  673 */         j += dt;
/*      */       }
/*      */     }
/*      */     else {
/*  677 */       length += x0;
/*  678 */       for (int j = 32768 + (y0 << 16); x0 <= length; x0++) {
/*  679 */         offset = (j >> 16) * this.SCREEN_WIDTH + x0;
/*  680 */         this.m_pixels[offset] = 
/*  681 */           (0xFF000000 | ir & 0xFF0000 | ig >> 8 & 0xFF00 | ib >> 16);
/*  682 */         if (this.m_zbuffer != null) this.m_zbuffer[offset] = this.m_z0;
/*  683 */         ir = (int)(ir + this.dr);
/*  684 */         ig = (int)(ig + this.dg);
/*  685 */         ib = (int)(ib + this.db);
/*  686 */         j += dt;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void drawLine_color_alpha(int x0, int y0, int dt, int length, boolean vertical)
/*      */   {
/*  694 */     int offset = 0;
/*      */     
/*  696 */     int ir = (int)this.m_r0;
/*  697 */     int ig = (int)this.m_g0;
/*  698 */     int ib = (int)this.m_b0;
/*  699 */     int ia = (int)this.m_a0;
/*      */     
/*  701 */     if (vertical) {
/*  702 */       length += y0;
/*  703 */       for (int j = 32768 + (x0 << 16); y0 <= length; y0++) {
/*  704 */         offset = y0 * this.SCREEN_WIDTH + (j >> 16);
/*      */         
/*  706 */         int pr = ir & 0xFF0000;
/*  707 */         int pg = ig >> 8 & 0xFF00;
/*  708 */         int pb = ib >> 16;
/*      */         
/*  710 */         int r0 = this.m_pixels[offset];
/*  711 */         int g0 = r0 & 0xFF00;
/*  712 */         int b0 = r0 & 0xFF;
/*  713 */         r0 &= 0xFF0000;
/*      */         
/*  715 */         int alpha = ia >> 16;
/*      */         
/*  717 */         r0 += ((pr - r0) * alpha >> 8);
/*  718 */         g0 += ((pg - g0) * alpha >> 8);
/*  719 */         b0 += ((pb - b0) * alpha >> 8);
/*      */         
/*  721 */         this.m_pixels[offset] = 
/*  722 */           (0xFF000000 | r0 & 0xFF0000 | g0 & 0xFF00 | b0 & 0xFF);
/*  723 */         if (this.m_zbuffer != null) { this.m_zbuffer[offset] = this.m_z0;
/*      */         }
/*  725 */         ir = (int)(ir + this.dr);
/*  726 */         ig = (int)(ig + this.dg);
/*  727 */         ib = (int)(ib + this.db);
/*  728 */         ia = (int)(ia + this.da);
/*  729 */         j += dt;
/*      */       }
/*      */     }
/*      */     else {
/*  733 */       length += x0;
/*  734 */       for (int j = 32768 + (y0 << 16); x0 <= length; x0++) {
/*  735 */         offset = (j >> 16) * this.SCREEN_WIDTH + x0;
/*      */         
/*  737 */         int pr = ir & 0xFF0000;
/*  738 */         int pg = ig >> 8 & 0xFF00;
/*  739 */         int pb = ib >> 16;
/*      */         
/*  741 */         int r0 = this.m_pixels[offset];
/*  742 */         int g0 = r0 & 0xFF00;
/*  743 */         int b0 = r0 & 0xFF;
/*  744 */         r0 &= 0xFF0000;
/*      */         
/*  746 */         int alpha = ia >> 16;
/*      */         
/*  748 */         r0 += ((pr - r0) * alpha >> 8);
/*  749 */         g0 += ((pg - g0) * alpha >> 8);
/*  750 */         b0 += ((pb - b0) * alpha >> 8);
/*      */         
/*  752 */         this.m_pixels[offset] = 
/*  753 */           (0xFF000000 | r0 & 0xFF0000 | g0 & 0xFF00 | b0 & 0xFF);
/*  754 */         if (this.m_zbuffer != null) { this.m_zbuffer[offset] = this.m_z0;
/*      */         }
/*  756 */         ir = (int)(ir + this.dr);
/*  757 */         ig = (int)(ig + this.dg);
/*  758 */         ib = (int)(ib + this.db);
/*  759 */         ia = (int)(ia + this.da);
/*  760 */         j += dt;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void drawLine_plain_spatial(int x0, int y0, int dt, int length, boolean vertical)
/*      */   {
/*  768 */     int offset = 0;
/*  769 */     float iz = this.m_z0;
/*      */     
/*  771 */     if (vertical) {
/*  772 */       length += y0;
/*  773 */       for (int j = 32768 + (x0 << 16); y0 <= length; y0++) {
/*  774 */         offset = y0 * this.SCREEN_WIDTH + (j >> 16);
/*  775 */         if ((offset < this.m_pixels.length) && 
/*  776 */           (iz <= this.m_zbuffer[offset])) {
/*  777 */           this.m_pixels[offset] = this.m_stroke;
/*  778 */           this.m_zbuffer[offset] = iz;
/*      */         }
/*      */         
/*  781 */         iz += this.dz;
/*  782 */         j += dt;
/*      */       }
/*      */     }
/*      */     else {
/*  786 */       length += x0;
/*  787 */       for (int j = 32768 + (y0 << 16); x0 <= length; x0++) {
/*  788 */         offset = (j >> 16) * this.SCREEN_WIDTH + x0;
/*  789 */         if ((offset < this.m_pixels.length) && 
/*  790 */           (iz <= this.m_zbuffer[offset])) {
/*  791 */           this.m_pixels[offset] = this.m_stroke;
/*  792 */           this.m_zbuffer[offset] = iz;
/*      */         }
/*      */         
/*  795 */         iz += this.dz;
/*  796 */         j += dt;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void drawLine_plain_alpha_spatial(int x0, int y0, int dt, int length, boolean vertical)
/*      */   {
/*  804 */     int offset = 0;
/*  805 */     float iz = this.m_z0;
/*      */     
/*  807 */     int pr = this.m_stroke & 0xFF0000;
/*  808 */     int pg = this.m_stroke & 0xFF00;
/*  809 */     int pb = this.m_stroke & 0xFF;
/*      */     
/*  811 */     int ia = (int)this.m_a0;
/*      */     
/*  813 */     if (vertical) {
/*  814 */       length += y0;
/*  815 */       for (int j = 32768 + (x0 << 16); y0 <= length; y0++) {
/*  816 */         offset = y0 * this.SCREEN_WIDTH + (j >> 16);
/*  817 */         if ((offset < this.m_pixels.length) && 
/*  818 */           (iz <= this.m_zbuffer[offset])) {
/*  819 */           int alpha = ia >> 16;
/*  820 */           int r0 = this.m_pixels[offset];
/*  821 */           int g0 = r0 & 0xFF00;
/*  822 */           int b0 = r0 & 0xFF;
/*  823 */           r0 &= 0xFF0000;
/*  824 */           r0 += ((pr - r0) * alpha >> 8);
/*  825 */           g0 += ((pg - g0) * alpha >> 8);
/*  826 */           b0 += ((pb - b0) * alpha >> 8);
/*      */           
/*  828 */           this.m_pixels[offset] = 
/*  829 */             (0xFF000000 | r0 & 0xFF0000 | g0 & 0xFF00 | b0 & 0xFF);
/*  830 */           this.m_zbuffer[offset] = iz;
/*      */         }
/*      */         
/*  833 */         iz += this.dz;
/*  834 */         ia = (int)(ia + this.da);
/*  835 */         j += dt;
/*      */       }
/*      */     }
/*      */     else {
/*  839 */       length += x0;
/*  840 */       for (int j = 32768 + (y0 << 16); x0 <= length; x0++) {
/*  841 */         offset = (j >> 16) * this.SCREEN_WIDTH + x0;
/*      */         
/*  843 */         if ((offset < this.m_pixels.length) && 
/*  844 */           (iz <= this.m_zbuffer[offset])) {
/*  845 */           int alpha = ia >> 16;
/*  846 */           int r0 = this.m_pixels[offset];
/*  847 */           int g0 = r0 & 0xFF00;
/*  848 */           int b0 = r0 & 0xFF;
/*  849 */           r0 &= 0xFF0000;
/*  850 */           r0 += ((pr - r0) * alpha >> 8);
/*  851 */           g0 += ((pg - g0) * alpha >> 8);
/*  852 */           b0 += ((pb - b0) * alpha >> 8);
/*      */           
/*  854 */           this.m_pixels[offset] = 
/*  855 */             (0xFF000000 | r0 & 0xFF0000 | g0 & 0xFF00 | b0 & 0xFF);
/*  856 */           this.m_zbuffer[offset] = iz;
/*      */         }
/*      */         
/*  859 */         iz += this.dz;
/*  860 */         ia = (int)(ia + this.da);
/*  861 */         j += dt;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void drawLine_color_spatial(int x0, int y0, int dt, int length, boolean vertical)
/*      */   {
/*  869 */     int offset = 0;
/*  870 */     float iz = this.m_z0;
/*      */     
/*  872 */     int ir = (int)this.m_r0;
/*  873 */     int ig = (int)this.m_g0;
/*  874 */     int ib = (int)this.m_b0;
/*      */     
/*  876 */     if (vertical) {
/*  877 */       length += y0;
/*  878 */       for (int j = 32768 + (x0 << 16); y0 <= length; y0++) {
/*  879 */         offset = y0 * this.SCREEN_WIDTH + (j >> 16);
/*      */         
/*  881 */         if (iz <= this.m_zbuffer[offset]) {
/*  882 */           this.m_pixels[offset] = 
/*  883 */             (0xFF000000 | ir & 0xFF0000 | ig >> 8 & 0xFF00 | ib >> 16);
/*  884 */           this.m_zbuffer[offset] = iz;
/*      */         }
/*  886 */         iz += this.dz;
/*  887 */         ir = (int)(ir + this.dr);
/*  888 */         ig = (int)(ig + this.dg);
/*  889 */         ib = (int)(ib + this.db);
/*  890 */         j += dt;
/*      */       }
/*      */     } else {
/*  893 */       length += x0;
/*  894 */       for (int j = 32768 + (y0 << 16); x0 <= length; x0++) {
/*  895 */         offset = (j >> 16) * this.SCREEN_WIDTH + x0;
/*  896 */         if (iz <= this.m_zbuffer[offset]) {
/*  897 */           this.m_pixels[offset] = 
/*  898 */             (0xFF000000 | ir & 0xFF0000 | ig >> 8 & 0xFF00 | ib >> 16);
/*  899 */           this.m_zbuffer[offset] = iz;
/*      */         }
/*  901 */         iz += this.dz;
/*  902 */         ir = (int)(ir + this.dr);
/*  903 */         ig = (int)(ig + this.dg);
/*  904 */         ib = (int)(ib + this.db);
/*  905 */         j += dt;
/*      */       }
/*  907 */       return;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void drawLine_color_alpha_spatial(int x0, int y0, int dt, int length, boolean vertical)
/*      */   {
/*  914 */     int offset = 0;
/*  915 */     float iz = this.m_z0;
/*      */     
/*  917 */     int ir = (int)this.m_r0;
/*  918 */     int ig = (int)this.m_g0;
/*  919 */     int ib = (int)this.m_b0;
/*  920 */     int ia = (int)this.m_a0;
/*      */     
/*  922 */     if (vertical) {
/*  923 */       length += y0;
/*  924 */       for (int j = 32768 + (x0 << 16); y0 <= length; y0++) {
/*  925 */         offset = y0 * this.SCREEN_WIDTH + (j >> 16);
/*      */         
/*  927 */         if (iz <= this.m_zbuffer[offset]) {
/*  928 */           int pr = ir & 0xFF0000;
/*  929 */           int pg = ig >> 8 & 0xFF00;
/*  930 */           int pb = ib >> 16;
/*      */           
/*  932 */           int r0 = this.m_pixels[offset];
/*  933 */           int g0 = r0 & 0xFF00;
/*  934 */           int b0 = r0 & 0xFF;
/*  935 */           r0 &= 0xFF0000;
/*      */           
/*  937 */           int alpha = ia >> 16;
/*      */           
/*  939 */           r0 += ((pr - r0) * alpha >> 8);
/*  940 */           g0 += ((pg - g0) * alpha >> 8);
/*  941 */           b0 += ((pb - b0) * alpha >> 8);
/*      */           
/*  943 */           this.m_pixels[offset] = 
/*  944 */             (0xFF000000 | r0 & 0xFF0000 | g0 & 0xFF00 | b0 & 0xFF);
/*  945 */           this.m_zbuffer[offset] = iz;
/*      */         }
/*  947 */         iz += this.dz;
/*  948 */         ir = (int)(ir + this.dr);
/*  949 */         ig = (int)(ig + this.dg);
/*  950 */         ib = (int)(ib + this.db);
/*  951 */         ia = (int)(ia + this.da);
/*  952 */         j += dt;
/*      */       }
/*      */     }
/*      */     else {
/*  956 */       length += x0;
/*  957 */       for (int j = 32768 + (y0 << 16); x0 <= length; x0++) {
/*  958 */         offset = (j >> 16) * this.SCREEN_WIDTH + x0;
/*      */         
/*  960 */         if (iz <= this.m_zbuffer[offset]) {
/*  961 */           int pr = ir & 0xFF0000;
/*  962 */           int pg = ig >> 8 & 0xFF00;
/*  963 */           int pb = ib >> 16;
/*      */           
/*  965 */           int r0 = this.m_pixels[offset];
/*  966 */           int g0 = r0 & 0xFF00;
/*  967 */           int b0 = r0 & 0xFF;
/*  968 */           r0 &= 0xFF0000;
/*      */           
/*  970 */           int alpha = ia >> 16;
/*      */           
/*  972 */           r0 += ((pr - r0) * alpha >> 8);
/*  973 */           g0 += ((pg - g0) * alpha >> 8);
/*  974 */           b0 += ((pb - b0) * alpha >> 8);
/*      */           
/*  976 */           this.m_pixels[offset] = 
/*  977 */             (0xFF000000 | r0 & 0xFF0000 | g0 & 0xFF00 | b0 & 0xFF);
/*  978 */           this.m_zbuffer[offset] = iz;
/*      */         }
/*  980 */         iz += this.dz;
/*  981 */         ir = (int)(ir + this.dr);
/*  982 */         ig = (int)(ig + this.dg);
/*  983 */         ib = (int)(ib + this.db);
/*  984 */         ia = (int)(ia + this.da);
/*  985 */         j += dt;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void drawLine_smooth(int x0, int y0, int dt, int length, boolean vertical)
/*      */   {
/*  994 */     int offset = 0;
/*      */     
/*      */ 
/*      */ 
/*  998 */     float iz = this.m_z0;
/*      */     
/* 1000 */     int ir = (int)this.m_r0;
/* 1001 */     int ig = (int)this.m_g0;
/* 1002 */     int ib = (int)this.m_b0;
/* 1003 */     int ia = (int)this.m_a0;
/*      */     
/* 1005 */     if (vertical) {
/* 1006 */       int xi = x0 << 16;
/* 1007 */       int yi = y0 << 16;
/*      */       
/* 1009 */       int end = length + y0;
/*      */       
/* 1011 */       while (yi >> 16 < end)
/*      */       {
/* 1013 */         offset = (yi >> 16) * this.SCREEN_WIDTH + (xi >> 16);
/*      */         
/* 1015 */         int pr = ir & 0xFF0000;
/* 1016 */         int pg = ig >> 8 & 0xFF00;
/* 1017 */         int pb = ib >> 16;
/*      */         
/* 1019 */         if ((this.m_zbuffer == null) || (iz <= this.m_zbuffer[offset])) {
/* 1020 */           int alpha = ((xi ^ 0xFFFFFFFF) >> 8 & 0xFF) * (ia >> 16) >> 8;
/*      */           
/* 1022 */           int r0 = this.m_pixels[offset];
/* 1023 */           int g0 = r0 & 0xFF00;
/* 1024 */           int b0 = r0 & 0xFF;
/* 1025 */           r0 &= 0xFF0000;
/*      */           
/* 1027 */           r0 += ((pr - r0) * alpha >> 8);
/* 1028 */           g0 += ((pg - g0) * alpha >> 8);
/* 1029 */           b0 += ((pb - b0) * alpha >> 8);
/*      */           
/* 1031 */           this.m_pixels[offset] = 
/* 1032 */             (0xFF000000 | r0 & 0xFF0000 | g0 & 0xFF00 | b0 & 0xFF);
/* 1033 */           if (this.m_zbuffer != null) { this.m_zbuffer[offset] = iz;
/*      */           }
/*      */         }
/*      */         
/*      */ 
/* 1038 */         int temp = (xi >> 16) + 1;
/* 1039 */         if (temp >= this.SCREEN_WIDTH) {
/* 1040 */           xi += dt;
/* 1041 */           yi += 65536;
/*      */         }
/*      */         else
/*      */         {
/* 1045 */           offset = (yi >> 16) * this.SCREEN_WIDTH + temp;
/*      */           
/* 1047 */           if ((this.m_zbuffer == null) || (iz <= this.m_zbuffer[offset])) {
/* 1048 */             int alpha = (xi >> 8 & 0xFF) * (ia >> 16) >> 8;
/*      */             
/* 1050 */             int r0 = this.m_pixels[offset];
/* 1051 */             int g0 = r0 & 0xFF00;
/* 1052 */             int b0 = r0 & 0xFF;
/* 1053 */             r0 &= 0xFF0000;
/*      */             
/* 1055 */             r0 += ((pr - r0) * alpha >> 8);
/* 1056 */             g0 += ((pg - g0) * alpha >> 8);
/* 1057 */             b0 += ((pb - b0) * alpha >> 8);
/*      */             
/* 1059 */             this.m_pixels[offset] = 
/* 1060 */               (0xFF000000 | r0 & 0xFF0000 | g0 & 0xFF00 | b0 & 0xFF);
/* 1061 */             if (this.m_zbuffer != null) { this.m_zbuffer[offset] = iz;
/*      */             }
/*      */           }
/* 1064 */           xi += dt;
/* 1065 */           yi += 65536;
/*      */           
/* 1067 */           iz += this.dz;
/* 1068 */           ir = (int)(ir + this.dr);
/* 1069 */           ig = (int)(ig + this.dg);
/* 1070 */           ib = (int)(ib + this.db);
/* 1071 */           ia = (int)(ia + this.da);
/*      */         }
/*      */       }
/*      */     } else {
/* 1075 */       int xi = x0 << 16;
/* 1076 */       int yi = y0 << 16;
/* 1077 */       int end = length + x0;
/*      */       
/* 1079 */       while (xi >> 16 < end) {
/* 1080 */         offset = (yi >> 16) * this.SCREEN_WIDTH + (xi >> 16);
/*      */         
/* 1082 */         int pr = ir & 0xFF0000;
/* 1083 */         int pg = ig >> 8 & 0xFF00;
/* 1084 */         int pb = ib >> 16;
/*      */         
/* 1086 */         if ((this.m_zbuffer == null) || (iz <= this.m_zbuffer[offset])) {
/* 1087 */           int alpha = ((yi ^ 0xFFFFFFFF) >> 8 & 0xFF) * (ia >> 16) >> 8;
/*      */           
/* 1089 */           int r0 = this.m_pixels[offset];
/* 1090 */           int g0 = r0 & 0xFF00;
/* 1091 */           int b0 = r0 & 0xFF;
/* 1092 */           r0 &= 0xFF0000;
/*      */           
/* 1094 */           r0 += ((pr - r0) * alpha >> 8);
/* 1095 */           g0 += ((pg - g0) * alpha >> 8);
/* 1096 */           b0 += ((pb - b0) * alpha >> 8);
/*      */           
/* 1098 */           this.m_pixels[offset] = 
/* 1099 */             (0xFF000000 | r0 & 0xFF0000 | g0 & 0xFF00 | b0 & 0xFF);
/* 1100 */           if (this.m_zbuffer != null) { this.m_zbuffer[offset] = iz;
/*      */           }
/*      */         }
/*      */         
/* 1104 */         int temp = (yi >> 16) + 1;
/* 1105 */         if (temp >= this.SCREEN_HEIGHT) {
/* 1106 */           xi += 65536;
/* 1107 */           yi += dt;
/*      */         }
/*      */         else
/*      */         {
/* 1111 */           offset = temp * this.SCREEN_WIDTH + (xi >> 16);
/*      */           
/* 1113 */           if ((this.m_zbuffer == null) || (iz <= this.m_zbuffer[offset])) {
/* 1114 */             int alpha = (yi >> 8 & 0xFF) * (ia >> 16) >> 8;
/*      */             
/* 1116 */             int r0 = this.m_pixels[offset];
/* 1117 */             int g0 = r0 & 0xFF00;
/* 1118 */             int b0 = r0 & 0xFF;
/* 1119 */             r0 &= 0xFF0000;
/*      */             
/* 1121 */             r0 += ((pr - r0) * alpha >> 8);
/* 1122 */             g0 += ((pg - g0) * alpha >> 8);
/* 1123 */             b0 += ((pb - b0) * alpha >> 8);
/*      */             
/* 1125 */             this.m_pixels[offset] = 
/* 1126 */               (0xFF000000 | r0 & 0xFF0000 | g0 & 0xFF00 | b0 & 0xFF);
/* 1127 */             if (this.m_zbuffer != null) { this.m_zbuffer[offset] = iz;
/*      */             }
/*      */           }
/* 1130 */           xi += 65536;
/* 1131 */           yi += dt;
/*      */           
/* 1133 */           iz += this.dz;
/* 1134 */           ir = (int)(ir + this.dr);
/* 1135 */           ig = (int)(ig + this.dg);
/* 1136 */           ib = (int)(ib + this.db);
/* 1137 */           ia = (int)(ia + this.da);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/AndrewKanarek/Desktop/MICHIGAN/KiefferWork/scripts-zibo/JavaApps/EllinghamDiagram/core.jar!/processing/core/PLine.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */