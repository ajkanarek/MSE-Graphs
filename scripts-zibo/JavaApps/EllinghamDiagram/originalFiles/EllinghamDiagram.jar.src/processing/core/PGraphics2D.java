/*      */ package processing.core;
/*      */ 
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.image.DirectColorModel;
/*      */ import java.awt.image.MemoryImageSource;
/*      */ import java.util.Arrays;
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
/*      */ public class PGraphics2D
/*      */   extends PGraphics
/*      */ {
/*   40 */   PMatrix2D ctm = new PMatrix2D();
/*      */   
/*      */   PPolygon fpolygon;
/*      */   
/*      */   PPolygon spolygon;
/*      */   
/*      */   float[][] svertices;
/*      */   
/*      */   PPolygon tpolygon;
/*      */   
/*      */   int[] vertexOrder;
/*      */   PLine line;
/*   52 */   float[][] matrixStack = new float[32][6];
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   int matrixStackDepth;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   DirectColorModel cm;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   MemoryImageSource mis;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void allocate()
/*      */   {
/*   78 */     this.pixelCount = (this.width * this.height);
/*   79 */     this.pixels = new int[this.pixelCount];
/*      */     
/*   81 */     if (this.primarySurface) {
/*   82 */       this.cm = new DirectColorModel(32, 16711680, 65280, 255);
/*   83 */       this.mis = new MemoryImageSource(this.width, this.height, this.pixels, 0, this.width);
/*   84 */       this.mis.setFullBufferUpdates(true);
/*   85 */       this.mis.setAnimated(true);
/*   86 */       this.image = Toolkit.getDefaultToolkit().createImage(this.mis);
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
/*      */   public boolean canDraw()
/*      */   {
/*   99 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void beginDraw()
/*      */   {
/*  106 */     if (!this.settingsInited) {
/*  107 */       defaultSettings();
/*      */       
/*      */ 
/*  110 */       this.fpolygon = new PPolygon(this);
/*  111 */       this.spolygon = new PPolygon(this);
/*  112 */       this.spolygon.vertexCount = 4;
/*  113 */       this.svertices = new float[2][];
/*      */     }
/*      */     
/*  116 */     resetMatrix();
/*      */     
/*      */ 
/*  119 */     this.vertexCount = 0;
/*      */   }
/*      */   
/*      */   public void endDraw()
/*      */   {
/*  124 */     if (this.mis != null) {
/*  125 */       this.mis.newPixels(this.pixels, this.cm, 0, this.width);
/*      */     }
/*      */     
/*      */ 
/*  129 */     updatePixels();
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
/*      */   public void beginShape(int kind)
/*      */   {
/*  164 */     this.shape = kind;
/*  165 */     this.vertexCount = 0;
/*  166 */     this.curveVertexCount = 0;
/*      */     
/*      */ 
/*  169 */     this.fpolygon.reset(4);
/*  170 */     this.spolygon.reset(4);
/*      */     
/*  172 */     this.textureImage = null;
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
/*      */   public void vertex(float x, float y, float z)
/*      */   {
/*  201 */     showDepthWarningXYZ("vertex");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void vertex(float x, float y, float z, float u, float v)
/*      */   {
/*  209 */     showDepthWarningXYZ("vertex");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void breakShape()
/*      */   {
/*  217 */     showWarning("This renderer cannot handle concave shapes or shapes with holes.");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void endShape(int mode)
/*      */   {
/*  226 */     if (this.ctm.isIdentity()) {
/*  227 */       for (int i = 0; i < this.vertexCount; i++) {
/*  228 */         this.vertices[i][18] = this.vertices[i][0];
/*  229 */         this.vertices[i][19] = this.vertices[i][1];
/*      */       }
/*      */     } else {
/*  232 */       for (int i = 0; i < this.vertexCount; i++) {
/*  233 */         this.vertices[i][18] = this.ctm.multX(this.vertices[i][0], this.vertices[i][1]);
/*  234 */         this.vertices[i][19] = this.ctm.multY(this.vertices[i][0], this.vertices[i][1]);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  241 */     this.fpolygon.texture(this.textureImage);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  247 */     this.spolygon.interpARGB = true;
/*  248 */     this.fpolygon.interpARGB = true;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  258 */     switch (this.shape)
/*      */     {
/*      */     case 2: 
/*  261 */       if (this.stroke)
/*  262 */         if ((this.ctm.m00 == this.ctm.m11) && (this.strokeWeight == 1.0F)) {
/*  263 */           for (int i = 0; i < this.vertexCount; i++) {
/*  264 */             thin_point(this.vertices[i][18], this.vertices[i][19], this.strokeColor);
/*      */           }
/*      */         } else
/*  267 */           for (int i = 0; i < this.vertexCount; i++) {
/*  268 */             float[] v = this.vertices[i];
/*  269 */             thick_point(v[18], v[19], v[20], v[13], v[14], v[15], v[16]);
/*      */           }
/*      */       break;
/*      */     case 4: 
/*  273 */       if ((goto 2270) && 
/*      */       
/*      */ 
/*  276 */         (this.stroke))
/*      */       {
/*  278 */         int increment = this.shape == 4 ? 2 : 1;
/*  279 */         draw_lines(this.vertices, this.vertexCount - 1, 1, increment, 0);
/*      */       }
/*  281 */       break;
/*      */     
/*      */ 
/*      */ 
/*      */     case 11: 
/*  286 */       if ((this.fill) || (this.textureImage != null)) {
/*  287 */         this.fpolygon.vertexCount = 3;
/*      */         
/*  289 */         for (int i = 1; i < this.vertexCount - 1; i++)
/*      */         {
/*      */ 
/*  292 */           this.fpolygon.vertices[2][3] = this.vertices[0][3];
/*  293 */           this.fpolygon.vertices[2][4] = this.vertices[0][4];
/*  294 */           this.fpolygon.vertices[2][5] = this.vertices[0][5];
/*  295 */           this.fpolygon.vertices[2][6] = this.vertices[0][6];
/*      */           
/*  297 */           this.fpolygon.vertices[2][18] = this.vertices[0][18];
/*  298 */           this.fpolygon.vertices[2][19] = this.vertices[0][19];
/*      */           
/*  300 */           if (this.textureImage != null) {
/*  301 */             this.fpolygon.vertices[2][7] = this.vertices[0][7];
/*  302 */             this.fpolygon.vertices[2][8] = this.vertices[0][8];
/*      */           }
/*      */           
/*      */ 
/*  306 */           for (int j = 0; j < 2; j++) {
/*  307 */             this.fpolygon.vertices[j][3] = this.vertices[(i + j)][3];
/*  308 */             this.fpolygon.vertices[j][4] = this.vertices[(i + j)][4];
/*  309 */             this.fpolygon.vertices[j][5] = this.vertices[(i + j)][5];
/*  310 */             this.fpolygon.vertices[j][6] = this.vertices[(i + j)][6];
/*      */             
/*  312 */             this.fpolygon.vertices[j][18] = this.vertices[(i + j)][18];
/*  313 */             this.fpolygon.vertices[j][19] = this.vertices[(i + j)][19];
/*      */             
/*      */ 
/*      */ 
/*  317 */             if (this.textureImage != null) {
/*  318 */               this.fpolygon.vertices[j][7] = this.vertices[(i + j)][7];
/*  319 */               this.fpolygon.vertices[j][8] = this.vertices[(i + j)][8];
/*      */             }
/*      */           }
/*      */           
/*  323 */           this.fpolygon.render();
/*      */         }
/*      */       }
/*  326 */       if (this.stroke)
/*      */       {
/*  328 */         for (int i = 1; i < this.vertexCount; i++) {
/*  329 */           draw_line(this.vertices[0], this.vertices[i]);
/*      */         }
/*      */         
/*  332 */         for (int i = 1; i < this.vertexCount - 1; i++) {
/*  333 */           draw_line(this.vertices[i], this.vertices[(i + 1)]);
/*      */         }
/*      */         
/*  336 */         draw_line(this.vertices[(this.vertexCount - 1)], this.vertices[1]);
/*      */       }
/*  338 */       break;
/*      */     
/*      */     case 9: 
/*      */     case 10: 
/*  342 */       int increment = this.shape == 9 ? 3 : 1;
/*      */       
/*      */ 
/*  345 */       if ((this.fill) || (this.textureImage != null)) {
/*  346 */         this.fpolygon.vertexCount = 3;
/*  347 */         for (int i = 0; i < this.vertexCount - 2; i += increment) {
/*  348 */           for (int j = 0; j < 3; j++) {
/*  349 */             this.fpolygon.vertices[j][3] = this.vertices[(i + j)][3];
/*  350 */             this.fpolygon.vertices[j][4] = this.vertices[(i + j)][4];
/*  351 */             this.fpolygon.vertices[j][5] = this.vertices[(i + j)][5];
/*  352 */             this.fpolygon.vertices[j][6] = this.vertices[(i + j)][6];
/*      */             
/*  354 */             this.fpolygon.vertices[j][18] = this.vertices[(i + j)][18];
/*  355 */             this.fpolygon.vertices[j][19] = this.vertices[(i + j)][19];
/*  356 */             this.fpolygon.vertices[j][20] = this.vertices[(i + j)][20];
/*      */             
/*  358 */             if (this.textureImage != null) {
/*  359 */               this.fpolygon.vertices[j][7] = this.vertices[(i + j)][7];
/*  360 */               this.fpolygon.vertices[j][8] = this.vertices[(i + j)][8];
/*      */             }
/*      */           }
/*  363 */           this.fpolygon.render();
/*      */         }
/*      */       }
/*  366 */       if (this.stroke)
/*      */       {
/*  368 */         if (this.shape == 10) {
/*  369 */           draw_lines(this.vertices, this.vertexCount - 1, 1, 1, 0);
/*      */         } else {
/*  371 */           draw_lines(this.vertices, this.vertexCount - 1, 1, 1, 3);
/*      */         }
/*      */         
/*      */ 
/*  375 */         draw_lines(this.vertices, this.vertexCount - 2, 2, increment, 0);
/*      */       }
/*      */       
/*      */ 
/*  379 */       break;
/*      */     
/*      */     case 16: 
/*  382 */       if ((this.fill) || (this.textureImage != null)) {
/*  383 */         this.fpolygon.vertexCount = 4;
/*  384 */         for (int i = 0; i < this.vertexCount - 3; i += 4) {
/*  385 */           for (int j = 0; j < 4; j++) {
/*  386 */             int jj = i + j;
/*  387 */             this.fpolygon.vertices[j][3] = this.vertices[jj][3];
/*  388 */             this.fpolygon.vertices[j][4] = this.vertices[jj][4];
/*  389 */             this.fpolygon.vertices[j][5] = this.vertices[jj][5];
/*  390 */             this.fpolygon.vertices[j][6] = this.vertices[jj][6];
/*      */             
/*  392 */             this.fpolygon.vertices[j][18] = this.vertices[jj][18];
/*  393 */             this.fpolygon.vertices[j][19] = this.vertices[jj][19];
/*  394 */             this.fpolygon.vertices[j][20] = this.vertices[jj][20];
/*      */             
/*  396 */             if (this.textureImage != null) {
/*  397 */               this.fpolygon.vertices[j][7] = this.vertices[jj][7];
/*  398 */               this.fpolygon.vertices[j][8] = this.vertices[jj][8];
/*      */             }
/*      */           }
/*  401 */           this.fpolygon.render();
/*      */         }
/*      */       }
/*  404 */       if (this.stroke) {
/*  405 */         for (int i = 0; i < this.vertexCount - 3; i += 4) {
/*  406 */           draw_line(this.vertices[(i + 0)], this.vertices[(i + 1)]);
/*  407 */           draw_line(this.vertices[(i + 1)], this.vertices[(i + 2)]);
/*  408 */           draw_line(this.vertices[(i + 2)], this.vertices[(i + 3)]);
/*  409 */           draw_line(this.vertices[(i + 3)], this.vertices[(i + 0)]);
/*      */         }
/*      */       }
/*  412 */       break;
/*      */     
/*      */     case 17: 
/*  415 */       if ((this.fill) || (this.textureImage != null)) {
/*  416 */         this.fpolygon.vertexCount = 4;
/*  417 */         for (int i = 0; i < this.vertexCount - 3; i += 2) {
/*  418 */           for (int j = 0; j < 4; j++) {
/*  419 */             int jj = i + j;
/*  420 */             if (j == 2) jj = i + 3;
/*  421 */             if (j == 3) { jj = i + 2;
/*      */             }
/*  423 */             this.fpolygon.vertices[j][3] = this.vertices[jj][3];
/*  424 */             this.fpolygon.vertices[j][4] = this.vertices[jj][4];
/*  425 */             this.fpolygon.vertices[j][5] = this.vertices[jj][5];
/*  426 */             this.fpolygon.vertices[j][6] = this.vertices[jj][6];
/*      */             
/*  428 */             this.fpolygon.vertices[j][18] = this.vertices[jj][18];
/*  429 */             this.fpolygon.vertices[j][19] = this.vertices[jj][19];
/*  430 */             this.fpolygon.vertices[j][20] = this.vertices[jj][20];
/*      */             
/*  432 */             if (this.textureImage != null) {
/*  433 */               this.fpolygon.vertices[j][7] = this.vertices[jj][7];
/*  434 */               this.fpolygon.vertices[j][8] = this.vertices[jj][8];
/*      */             }
/*      */           }
/*  437 */           this.fpolygon.render();
/*      */         }
/*      */       }
/*  440 */       if (this.stroke) {
/*  441 */         draw_lines(this.vertices, this.vertexCount - 1, 1, 2, 0);
/*  442 */         draw_lines(this.vertices, this.vertexCount - 2, 2, 1, 0);
/*      */       }
/*  444 */       break;
/*      */     
/*      */     case 20: 
/*  447 */       if (isConvex()) {
/*  448 */         if ((this.fill) || (this.textureImage != null))
/*      */         {
/*  450 */           this.fpolygon.renderPolygon(this.vertices, this.vertexCount);
/*      */         }
/*      */         
/*      */ 
/*  454 */         if (this.stroke) {
/*  455 */           draw_lines(this.vertices, this.vertexCount - 1, 1, 1, 0);
/*  456 */           if (mode == 2)
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*  461 */             draw_line(this.vertices[(this.vertexCount - 1)], this.vertices[0]);
/*      */           }
/*      */         }
/*      */       }
/*      */       else {
/*  466 */         if ((this.fill) || (this.textureImage != null))
/*      */         {
/*      */ 
/*      */ 
/*  470 */           boolean smoov = this.smooth;
/*      */           
/*  472 */           if (this.stroke) this.smooth = false;
/*  473 */           concaveRender();
/*      */           
/*  475 */           if (this.stroke) { this.smooth = smoov;
/*      */           }
/*      */         }
/*  478 */         if (this.stroke) {
/*  479 */           draw_lines(this.vertices, this.vertexCount - 1, 1, 1, 0);
/*  480 */           if (mode == 2)
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  486 */             draw_line(this.vertices[(this.vertexCount - 1)], this.vertices[0]);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */       break;
/*      */     }
/*      */     
/*  494 */     this.shape = 0;
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
/*      */   private boolean isConvex()
/*      */   {
/*  510 */     if (this.vertexCount < 3)
/*      */     {
/*  512 */       return true;
/*      */     }
/*      */     
/*  515 */     int flag = 0;
/*      */     
/*      */ 
/*  518 */     for (int i = 0; i < this.vertexCount; i++) {
/*  519 */       float[] vi = this.vertices[i];
/*  520 */       float[] vj = this.vertices[((i + 1) % this.vertexCount)];
/*  521 */       float[] vk = this.vertices[((i + 2) % this.vertexCount)];
/*  522 */       float calc = (vj[18] - vi[18]) * (vk[19] - vj[19]) - 
/*  523 */         (vj[19] - vi[19]) * (vk[18] - vj[18]);
/*  524 */       if (calc < 0.0F) {
/*  525 */         flag |= 0x1;
/*  526 */       } else if (calc > 0.0F) {
/*  527 */         flag |= 0x2;
/*      */       }
/*  529 */       if (flag == 3) {
/*  530 */         return false;
/*      */       }
/*      */     }
/*  533 */     if (flag != 0) {
/*  534 */       return true;
/*      */     }
/*      */     
/*      */ 
/*  538 */     return true;
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
/*      */   protected void concaveRender()
/*      */   {
/*  552 */     if ((this.vertexOrder == null) || (this.vertexOrder.length != this.vertices.length)) {
/*  553 */       this.vertexOrder = new int[this.vertices.length];
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  560 */     if (this.tpolygon == null) {
/*  561 */       this.tpolygon = new PPolygon(this);
/*      */     }
/*  563 */     this.tpolygon.reset(3);
/*      */     
/*      */ 
/*  566 */     float area = 0.0F;
/*  567 */     int p = this.vertexCount - 1; for (int q = 0; q < this.vertexCount; p = q++)
/*      */     {
/*  569 */       area = area + (this.vertices[q][0] * this.vertices[p][1] - this.vertices[p][0] * this.vertices[q][1]);
/*      */     }
/*      */     
/*  572 */     if (area == 0.0F) { return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  577 */     float[] vfirst = this.vertices[0];
/*  578 */     float[] vlast = this.vertices[(this.vertexCount - 1)];
/*  579 */     if ((Math.abs(vfirst[0] - vlast[0]) < 1.0E-4F) && 
/*  580 */       (Math.abs(vfirst[1] - vlast[1]) < 1.0E-4F) && 
/*  581 */       (Math.abs(vfirst[2] - vlast[2]) < 1.0E-4F)) {
/*  582 */       this.vertexCount -= 1;
/*      */     }
/*      */     
/*      */ 
/*  586 */     for (int i = 0; i < this.vertexCount; i++) {
/*  587 */       this.vertexOrder[i] = (area > 0.0F ? i : this.vertexCount - 1 - i);
/*      */     }
/*      */     
/*      */ 
/*  591 */     int vc = this.vertexCount;
/*  592 */     int count = 2 * vc;
/*      */     
/*  594 */     int m = 0; for (int v = vc - 1; vc > 2;) {
/*  595 */       boolean snip = true;
/*      */       
/*      */ 
/*  598 */       if (count-- <= 0) {
/*      */         break;
/*      */       }
/*      */       
/*      */ 
/*  603 */       int u = v; if (vc <= u) u = 0;
/*  604 */       v = u + 1; if (vc <= v) v = 0;
/*  605 */       int w = v + 1; if (vc <= w) { w = 0;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  615 */       double Ax = -10.0F * this.vertices[this.vertexOrder[u]][0];
/*  616 */       double Ay = 10.0F * this.vertices[this.vertexOrder[u]][1];
/*  617 */       double Bx = -10.0F * this.vertices[this.vertexOrder[v]][0];
/*  618 */       double By = 10.0F * this.vertices[this.vertexOrder[v]][1];
/*  619 */       double Cx = -10.0F * this.vertices[this.vertexOrder[w]][0];
/*  620 */       double Cy = 10.0F * this.vertices[this.vertexOrder[w]][1];
/*      */       
/*      */ 
/*  623 */       if (9.999999747378752E-5D <= (Bx - Ax) * (Cy - Ay) - (By - Ay) * (Cx - Ax))
/*      */       {
/*      */ 
/*      */ 
/*  627 */         for (int p = 0; p < vc; p++) {
/*  628 */           if ((p != u) && (p != v) && (p != w))
/*      */           {
/*      */ 
/*      */ 
/*  632 */             double Px = -10.0F * this.vertices[this.vertexOrder[p]][0];
/*  633 */             double Py = 10.0F * this.vertices[this.vertexOrder[p]][1];
/*      */             
/*  635 */             double ax = Cx - Bx;double ay = Cy - By;
/*  636 */             double bx = Ax - Cx;double by = Ay - Cy;
/*  637 */             double cx = Bx - Ax;double cy = By - Ay;
/*  638 */             double apx = Px - Ax;double apy = Py - Ay;
/*  639 */             double bpx = Px - Bx;double bpy = Py - By;
/*  640 */             double cpx = Px - Cx;double cpy = Py - Cy;
/*      */             
/*  642 */             double aCROSSbp = ax * bpy - ay * bpx;
/*  643 */             double cCROSSap = cx * apy - cy * apx;
/*  644 */             double bCROSScp = bx * cpy - by * cpx;
/*      */             
/*  646 */             if ((aCROSSbp >= 0.0D) && (bCROSScp >= 0.0D) && (cCROSSap >= 0.0D)) {
/*  647 */               snip = false;
/*      */             }
/*      */           }
/*      */         }
/*  651 */         if (snip) {
/*  652 */           this.tpolygon.renderTriangle(this.vertices[this.vertexOrder[u]], 
/*  653 */             this.vertices[this.vertexOrder[v]], 
/*  654 */             this.vertices[this.vertexOrder[w]]);
/*  655 */           m++;
/*      */           
/*      */ 
/*  658 */           int s = v; for (int t = v + 1; t < vc; t++) {
/*  659 */             this.vertexOrder[s] = this.vertexOrder[t];s++;
/*      */           }
/*  661 */           vc--;
/*      */           
/*      */ 
/*  664 */           count = 2 * vc;
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
/*      */ 
/*      */ 
/*      */   public void point(float x, float y, float z)
/*      */   {
/*  917 */     showDepthWarningXYZ("point");
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
/*      */   protected void rectImpl(float x1f, float y1f, float x2f, float y2f)
/*      */   {
/*  944 */     if ((this.smooth) || (this.strokeAlpha) || (this.ctm.isWarped()))
/*      */     {
/*  946 */       super.rectImpl(x1f, y1f, x2f, y2f);
/*      */     }
/*      */     else {
/*  949 */       int x1 = (int)(x1f + this.ctm.m02);
/*  950 */       int y1 = (int)(y1f + this.ctm.m12);
/*  951 */       int x2 = (int)(x2f + this.ctm.m02);
/*  952 */       int y2 = (int)(y2f + this.ctm.m12);
/*      */       
/*  954 */       if (this.fill) {
/*  955 */         simple_rect_fill(x1, y1, x2, y2);
/*      */       }
/*      */       
/*  958 */       if (this.stroke) {
/*  959 */         if (this.strokeWeight == 1.0F) {
/*  960 */           thin_flat_line(x1, y1, x2, y1);
/*  961 */           thin_flat_line(x2, y1, x2, y2);
/*  962 */           thin_flat_line(x2, y2, x1, y2);
/*  963 */           thin_flat_line(x1, y2, x1, y1);
/*      */         }
/*      */         else {
/*  966 */           thick_flat_line(x1, y1, this.strokeR, this.strokeG, this.strokeB, this.strokeA, 
/*  967 */             x2, y1, this.strokeR, this.strokeG, this.strokeB, this.strokeA);
/*  968 */           thick_flat_line(x2, y1, this.strokeR, this.strokeG, this.strokeB, this.strokeA, 
/*  969 */             x2, y2, this.strokeR, this.strokeG, this.strokeB, this.strokeA);
/*  970 */           thick_flat_line(x2, y2, this.strokeR, this.strokeG, this.strokeB, this.strokeA, 
/*  971 */             x1, y2, this.strokeR, this.strokeG, this.strokeB, this.strokeA);
/*  972 */           thick_flat_line(x1, y2, this.strokeR, this.strokeG, this.strokeB, this.strokeA, 
/*  973 */             x1, y1, this.strokeR, this.strokeG, this.strokeB, this.strokeA);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void simple_rect_fill(int x1, int y1, int x2, int y2)
/*      */   {
/*  985 */     if (y2 < y1) {
/*  986 */       int temp = y1;y1 = y2;y2 = temp;
/*      */     }
/*  988 */     if (x2 < x1) {
/*  989 */       int temp = x1;x1 = x2;x2 = temp;
/*      */     }
/*      */     
/*      */ 
/*  993 */     if ((x1 > this.width1) || (x2 < 0) || 
/*  994 */       (y1 > this.height1) || (y2 < 0)) { return;
/*      */     }
/*      */     
/*      */ 
/*  998 */     if (x1 < 0) x1 = 0;
/*  999 */     if (x2 > this.width) x2 = this.width;
/* 1000 */     if (y1 < 0) y1 = 0;
/* 1001 */     if (y2 > this.height) { y2 = this.height;
/*      */     }
/* 1003 */     int ww = x2 - x1;
/*      */     
/* 1005 */     if (this.fillAlpha) {
/* 1006 */       for (int y = y1; y < y2; y++) {
/* 1007 */         int index = y * this.width + x1;
/* 1008 */         for (int x = 0; x < ww; x++) {
/* 1009 */           this.pixels[index] = blend_fill(this.pixels[index]);
/* 1010 */           index++;
/*      */         }
/*      */         
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 1017 */       int hh = y2 - y1;
/*      */       
/*      */ 
/* 1020 */       int index = y1 * this.width + x1;
/* 1021 */       int rowIndex = index;
/* 1022 */       for (int i = 0; i < ww; i++) {
/* 1023 */         this.pixels[(index + i)] = this.fillColor;
/*      */       }
/* 1025 */       for (int y = 0; y < hh; y++)
/*      */       {
/* 1027 */         System.arraycopy(this.pixels, rowIndex, this.pixels, index, ww);
/* 1028 */         index += this.width;
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
/*      */   protected void ellipseImpl(float x, float y, float w, float h)
/*      */   {
/* 1042 */     if ((this.smooth) || (this.strokeWeight != 1.0F) || 
/* 1043 */       (this.fillAlpha) || (this.strokeAlpha) || (this.ctm.isWarped()))
/*      */     {
/*      */ 
/* 1046 */       float radiusH = w / 2.0F;
/* 1047 */       float radiusV = h / 2.0F;
/*      */       
/* 1049 */       float centerX = x + radiusH;
/* 1050 */       float centerY = y + radiusV;
/*      */       
/* 1052 */       float sx1 = screenX(x, y);
/* 1053 */       float sy1 = screenY(x, y);
/* 1054 */       float sx2 = screenX(x + w, y + h);
/* 1055 */       float sy2 = screenY(x + w, y + h);
/* 1056 */       int accuracy = (int)(6.2831855F * PApplet.dist(sx1, sy1, sx2, sy2) / 8.0F);
/* 1057 */       if (accuracy < 4) { return;
/*      */       }
/*      */       
/* 1060 */       float inc = 720.0F / accuracy;
/*      */       
/* 1062 */       float val = 0.0F;
/*      */       
/* 1064 */       if (this.fill) {
/* 1065 */         boolean savedStroke = this.stroke;
/* 1066 */         this.stroke = false;
/*      */         
/* 1068 */         beginShape();
/* 1069 */         for (int i = 0; i < accuracy; i++) {
/* 1070 */           vertex(centerX + cosLUT[((int)val)] * radiusH, 
/* 1071 */             centerY + sinLUT[((int)val)] * radiusV);
/* 1072 */           val += inc;
/*      */         }
/* 1074 */         endShape(2);
/*      */         
/* 1076 */         this.stroke = savedStroke;
/*      */       }
/*      */       
/* 1079 */       if (this.stroke) {
/* 1080 */         boolean savedFill = this.fill;
/* 1081 */         this.fill = false;
/*      */         
/* 1083 */         val = 0.0F;
/* 1084 */         beginShape();
/* 1085 */         for (int i = 0; i < accuracy; i++) {
/* 1086 */           vertex(centerX + cosLUT[((int)val)] * radiusH, 
/* 1087 */             centerY + sinLUT[((int)val)] * radiusV);
/* 1088 */           val += inc;
/*      */         }
/* 1090 */         endShape(2);
/*      */         
/* 1092 */         this.fill = savedFill;
/*      */       }
/*      */     } else {
/* 1095 */       float hradius = w / 2.0F;
/* 1096 */       float vradius = h / 2.0F;
/*      */       
/* 1098 */       int centerX = (int)(x + hradius + this.ctm.m02);
/* 1099 */       int centerY = (int)(y + vradius + this.ctm.m12);
/*      */       
/* 1101 */       int hradiusi = (int)hradius;
/* 1102 */       int vradiusi = (int)vradius;
/*      */       
/* 1104 */       if (hradiusi == vradiusi) {
/* 1105 */         if (this.fill) flat_circle_fill(centerX, centerY, hradiusi);
/* 1106 */         if (this.stroke) flat_circle_stroke(centerX, centerY, hradiusi);
/*      */       }
/*      */       else {
/* 1109 */         if (this.fill) flat_ellipse_internal(centerX, centerY, hradiusi, vradiusi, true);
/* 1110 */         if (this.stroke) { flat_ellipse_internal(centerX, centerY, hradiusi, vradiusi, false);
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
/*      */   private void flat_circle_stroke(int xC, int yC, int r)
/*      */   {
/* 1142 */     int x = 0;int y = r;int u = 1;int v = 2 * r - 1;int E = 0;
/* 1143 */     while (x < y) {
/* 1144 */       thin_point(xC + x, yC + y, this.strokeColor);
/* 1145 */       thin_point(xC + y, yC - x, this.strokeColor);
/* 1146 */       thin_point(xC - x, yC - y, this.strokeColor);
/* 1147 */       thin_point(xC - y, yC + x, this.strokeColor);
/*      */       
/* 1149 */       x++;E += u;u += 2;
/* 1150 */       if (v < 2 * E) {
/* 1151 */         y--;E -= v;v -= 2;
/*      */       }
/* 1153 */       if (x > y)
/*      */         break;
/* 1155 */       thin_point(xC + y, yC + x, this.strokeColor);
/* 1156 */       thin_point(xC + x, yC - y, this.strokeColor);
/* 1157 */       thin_point(xC - y, yC - x, this.strokeColor);
/* 1158 */       thin_point(xC - x, yC + y, this.strokeColor);
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
/*      */   private void flat_circle_fill(int xc, int yc, int r)
/*      */   {
/* 1176 */     int x = 0;int y = r;int u = 1;int v = 2 * r - 1;int E = 0;
/* 1177 */     int xx; for (; x < y; 
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
/* 1206 */         xx < xc)
/*      */     {
/* 1178 */       for (int xx = xc; xx < xc + x; xx++) {
/* 1179 */         thin_point(xx, yc + y, this.fillColor);
/*      */       }
/* 1181 */       for (int xx = xc; xx < xc + y; xx++) {
/* 1182 */         thin_point(xx, yc - x, this.fillColor);
/*      */       }
/* 1184 */       for (int xx = xc - x; xx < xc; xx++) {
/* 1185 */         thin_point(xx, yc - y, this.fillColor);
/*      */       }
/* 1187 */       for (int xx = xc - y; xx < xc; xx++) {
/* 1188 */         thin_point(xx, yc + x, this.fillColor);
/*      */       }
/*      */       
/* 1191 */       x++;E += u;u += 2;
/* 1192 */       if (v < 2 * E) {
/* 1193 */         y--;E -= v;v -= 2;
/*      */       }
/* 1195 */       if (x > y)
/*      */         break;
/* 1197 */       for (int xx = xc; xx < xc + y; xx++) {
/* 1198 */         thin_point(xx, yc + x, this.fillColor);
/*      */       }
/* 1200 */       for (int xx = xc; xx < xc + x; xx++) {
/* 1201 */         thin_point(xx, yc - y, this.fillColor);
/*      */       }
/* 1203 */       for (int xx = xc - y; xx < xc; xx++) {
/* 1204 */         thin_point(xx, yc - x, this.fillColor);
/*      */       }
/* 1206 */       xx = xc - x; continue;
/* 1207 */       thin_point(xx, yc + y, this.fillColor);xx++;
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
/*      */   private final void flat_ellipse_symmetry(int centerX, int centerY, int ellipseX, int ellipseY, boolean filling)
/*      */   {
/* 1219 */     if (filling) {
/* 1220 */       for (int i = centerX - ellipseX + 1; i < centerX + ellipseX; i++) {
/* 1221 */         thin_point(i, centerY - ellipseY, this.fillColor);
/* 1222 */         thin_point(i, centerY + ellipseY, this.fillColor);
/*      */       }
/*      */     } else {
/* 1225 */       thin_point(centerX - ellipseX, centerY + ellipseY, this.strokeColor);
/* 1226 */       thin_point(centerX + ellipseX, centerY + ellipseY, this.strokeColor);
/* 1227 */       thin_point(centerX - ellipseX, centerY - ellipseY, this.strokeColor);
/* 1228 */       thin_point(centerX + ellipseX, centerY - ellipseY, this.strokeColor);
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
/*      */   private void flat_ellipse_internal(int centerX, int centerY, int a, int b, boolean filling)
/*      */   {
/* 1250 */     int a2 = a * a;
/* 1251 */     int b2 = b * b;
/* 1252 */     int x = 0;
/* 1253 */     int y = b;
/* 1254 */     int s = a2 * (1 - 2 * b) + 2 * b2;
/* 1255 */     int t = b2 - 2 * a2 * (2 * b - 1);
/* 1256 */     flat_ellipse_symmetry(centerX, centerY, x, y, filling);
/*      */     do
/*      */     {
/* 1259 */       if (s < 0) {
/* 1260 */         s += 2 * b2 * (2 * x + 3);
/* 1261 */         t += 4 * b2 * (x + 1);
/* 1262 */         x++;
/* 1263 */       } else if (t < 0) {
/* 1264 */         s += 2 * b2 * (2 * x + 3) - 4 * a2 * (y - 1);
/* 1265 */         t += 4 * b2 * (x + 1) - 2 * a2 * (2 * y - 3);
/* 1266 */         x++;
/* 1267 */         y--;
/*      */       } else {
/* 1269 */         s -= 4 * a2 * (y - 1);
/* 1270 */         t -= 2 * a2 * (2 * y - 3);
/* 1271 */         y--;
/*      */       }
/* 1273 */       flat_ellipse_symmetry(centerX, centerY, x, y, filling);
/*      */     }
/* 1275 */     while (y > 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void arcImpl(float x, float y, float w, float h, float start, float stop)
/*      */   {
/* 1283 */     float hr = w / 2.0F;
/* 1284 */     float vr = h / 2.0F;
/*      */     
/* 1286 */     float centerX = x + hr;
/* 1287 */     float centerY = y + vr;
/*      */     
/* 1289 */     if (this.fill)
/*      */     {
/* 1291 */       boolean savedStroke = this.stroke;
/* 1292 */       this.stroke = false;
/*      */       
/* 1294 */       int startLUT = (int)(-0.5F + start / 6.2831855F * 720.0F);
/* 1295 */       int stopLUT = (int)(0.5F + stop / 6.2831855F * 720.0F);
/*      */       
/* 1297 */       beginShape();
/* 1298 */       vertex(centerX, centerY);
/* 1299 */       for (int i = startLUT; i < stopLUT; i++) {
/* 1300 */         int ii = i % 720;
/*      */         
/* 1302 */         if (ii < 0) ii += 720;
/* 1303 */         vertex(centerX + cosLUT[ii] * hr, 
/* 1304 */           centerY + sinLUT[ii] * vr);
/*      */       }
/* 1306 */       endShape(2);
/*      */       
/* 1308 */       this.stroke = savedStroke;
/*      */     }
/*      */     
/* 1311 */     if (this.stroke)
/*      */     {
/*      */ 
/*      */ 
/* 1315 */       boolean savedFill = this.fill;
/* 1316 */       this.fill = false;
/*      */       
/* 1318 */       int startLUT = (int)(0.5F + start / 6.2831855F * 720.0F);
/* 1319 */       int stopLUT = (int)(0.5F + stop / 6.2831855F * 720.0F);
/*      */       
/* 1321 */       beginShape();
/* 1322 */       int increment = 1;
/* 1323 */       for (int i = startLUT; i < stopLUT; i += increment) {
/* 1324 */         int ii = i % 720;
/* 1325 */         if (ii < 0) ii += 720;
/* 1326 */         vertex(centerX + cosLUT[ii] * hr, 
/* 1327 */           centerY + sinLUT[ii] * vr);
/*      */       }
/*      */       
/* 1330 */       vertex(centerX + cosLUT[(stopLUT % 720)] * hr, 
/* 1331 */         centerY + sinLUT[(stopLUT % 720)] * vr);
/* 1332 */       endShape();
/*      */       
/* 1334 */       this.fill = savedFill;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void box(float size)
/*      */   {
/* 1346 */     showDepthWarning("box");
/*      */   }
/*      */   
/*      */   public void box(float w, float h, float d) {
/* 1350 */     showDepthWarning("box");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void sphereDetail(int res)
/*      */   {
/* 1361 */     showDepthWarning("sphereDetail");
/*      */   }
/*      */   
/*      */   public void sphereDetail(int ures, int vres) {
/* 1365 */     showDepthWarning("sphereDetail");
/*      */   }
/*      */   
/*      */   public void sphere(float r) {
/* 1369 */     showDepthWarning("sphere");
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
/*      */   public void bezier(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4)
/*      */   {
/* 1383 */     showDepthWarningXYZ("bezier");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void curve(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4)
/*      */   {
/* 1391 */     showDepthWarningXYZ("curve");
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
/*      */   protected void imageImpl(PImage image, float x1, float y1, float x2, float y2, int u1, int v1, int u2, int v2)
/*      */   {
/* 1404 */     if ((x2 - x1 == image.width) && 
/* 1405 */       (y2 - y1 == image.height) && 
/* 1406 */       (!this.tint) && (!this.ctm.isWarped())) {
/* 1407 */       simple_image(image, (int)(x1 + this.ctm.m02), (int)(y1 + this.ctm.m12), u1, v1, u2, v2);
/*      */     }
/*      */     else {
/* 1410 */       super.imageImpl(image, x1, y1, x2, y2, u1, v1, u2, v2);
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
/*      */   private void simple_image(PImage image, int sx1, int sy1, int ix1, int iy1, int ix2, int iy2)
/*      */   {
/* 1426 */     int sx2 = sx1 + image.width;
/* 1427 */     int sy2 = sy1 + image.height;
/*      */     
/*      */ 
/*      */ 
/* 1431 */     if ((sx1 > this.width1) || (sx2 < 0) || 
/* 1432 */       (sy1 > this.height1) || (sy2 < 0)) { return;
/*      */     }
/* 1434 */     if (sx1 < 0) {
/* 1435 */       ix1 -= sx1;
/* 1436 */       sx1 = 0;
/*      */     }
/* 1438 */     if (sy1 < 0) {
/* 1439 */       iy1 -= sy1;
/* 1440 */       sy1 = 0;
/*      */     }
/* 1442 */     if (sx2 > this.width) {
/* 1443 */       ix2 -= sx2 - this.width;
/* 1444 */       sx2 = this.width;
/*      */     }
/* 1446 */     if (sy2 > this.height) {
/* 1447 */       iy2 -= sy2 - this.height;
/* 1448 */       sy2 = this.height;
/*      */     }
/*      */     
/* 1451 */     int source = iy1 * image.width + ix1;
/* 1452 */     int target = sy1 * this.width;
/*      */     
/* 1454 */     if (image.format == 2) {
/* 1455 */       for (int y = sy1; y < sy2; y++) {
/* 1456 */         int tx = 0;
/*      */         
/* 1458 */         for (int x = sx1; x < sx2; x++) {
/* 1459 */           this.pixels[(target + x)] = 
/*      */           
/*      */ 
/*      */ 
/* 1463 */             blend_color(this.pixels[(target + x)], 
/* 1464 */             image.pixels[(source + tx++)]);
/*      */         }
/* 1466 */         source += image.width;
/* 1467 */         target += this.width;
/*      */       }
/* 1469 */     } else if (image.format == 4) {
/* 1470 */       for (int y = sy1; y < sy2; y++) {
/* 1471 */         int tx = 0;
/*      */         
/* 1473 */         for (int x = sx1; x < sx2; x++) {
/* 1474 */           this.pixels[(target + x)] = 
/* 1475 */             blend_color_alpha(this.pixels[(target + x)], 
/* 1476 */             this.fillColor, 
/* 1477 */             image.pixels[(source + tx++)]);
/*      */         }
/* 1479 */         source += image.width;
/* 1480 */         target += this.width;
/*      */       }
/*      */     }
/* 1483 */     else if (image.format == 1) {
/* 1484 */       target += sx1;
/* 1485 */       int tw = sx2 - sx1;
/* 1486 */       for (int y = sy1; y < sy2; y++) {
/* 1487 */         System.arraycopy(image.pixels, source, this.pixels, target, tw);
/*      */         
/*      */ 
/* 1490 */         source += image.width;
/* 1491 */         target += this.width;
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
/*      */   private void thin_point_at(int x, int y, float z, int color)
/*      */   {
/* 1514 */     int index = y * this.width + x;
/* 1515 */     this.pixels[index] = color;
/*      */   }
/*      */   
/*      */ 
/*      */   private void thin_point_at_index(int offset, float z, int color)
/*      */   {
/* 1521 */     this.pixels[offset] = color;
/*      */   }
/*      */   
/*      */ 
/*      */   private void thick_point(float x, float y, float z, float r, float g, float b, float a)
/*      */   {
/* 1527 */     this.spolygon.reset(4);
/* 1528 */     this.spolygon.interpARGB = false;
/*      */     
/* 1530 */     float strokeWidth2 = this.strokeWeight / 2.0F;
/*      */     
/* 1532 */     float[] svertex = this.spolygon.vertices[0];
/* 1533 */     svertex[18] = (x - strokeWidth2);
/* 1534 */     svertex[19] = (y - strokeWidth2);
/* 1535 */     svertex[20] = z;
/*      */     
/* 1537 */     svertex[3] = r;
/* 1538 */     svertex[4] = g;
/* 1539 */     svertex[5] = b;
/* 1540 */     svertex[6] = a;
/*      */     
/* 1542 */     svertex = this.spolygon.vertices[1];
/* 1543 */     svertex[18] = (x + strokeWidth2);
/* 1544 */     svertex[19] = (y - strokeWidth2);
/* 1545 */     svertex[20] = z;
/*      */     
/* 1547 */     svertex = this.spolygon.vertices[2];
/* 1548 */     svertex[18] = (x + strokeWidth2);
/* 1549 */     svertex[19] = (y + strokeWidth2);
/* 1550 */     svertex[20] = z;
/*      */     
/* 1552 */     svertex = this.spolygon.vertices[3];
/* 1553 */     svertex[18] = (x - strokeWidth2);
/* 1554 */     svertex[19] = (y + strokeWidth2);
/* 1555 */     svertex[20] = z;
/*      */     
/* 1557 */     this.spolygon.render();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void thin_flat_line(int x1, int y1, int x2, int y2)
/*      */   {
/* 1566 */     int code1 = thin_flat_line_clip_code(x1, y1);
/* 1567 */     int code2 = thin_flat_line_clip_code(x2, y2);
/*      */     
/* 1569 */     if ((code1 & code2) != 0) {
/* 1570 */       return;
/*      */     }
/* 1572 */     int dip = code1 | code2;
/* 1573 */     int ny2; int nx1; int nx2; int ny1; int ny2; if (dip != 0)
/*      */     {
/* 1575 */       float a1 = 0.0F;float a2 = 1.0F;float a = 0.0F;
/* 1576 */       for (int i = 0; i < 4; i++) {
/* 1577 */         if ((dip >> i) % 2 == 1) {
/* 1578 */           a = thin_flat_line_slope(x1, y1, 
/* 1579 */             x2, y2, i + 1);
/* 1580 */           if ((code1 >> i) % 2 == 1) {
/* 1581 */             a1 = Math.max(a, a1);
/*      */           } else {
/* 1583 */             a2 = Math.min(a, a2);
/*      */           }
/*      */         }
/*      */       }
/* 1587 */       if (a1 > a2) { return;
/*      */       }
/* 1589 */       int nx1 = (int)(x1 + a1 * (x2 - x1));
/* 1590 */       int ny1 = (int)(y1 + a1 * (y2 - y1));
/* 1591 */       int nx2 = (int)(x1 + a2 * (x2 - x1));
/* 1592 */       ny2 = (int)(y1 + a2 * (y2 - y1));
/*      */     }
/*      */     else
/*      */     {
/* 1596 */       nx1 = x1;nx2 = x2;
/* 1597 */       ny1 = y1;ny2 = y2;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1604 */     boolean yLonger = false;
/* 1605 */     int shortLen = ny2 - ny1;
/* 1606 */     int longLen = nx2 - nx1;
/* 1607 */     if (Math.abs(shortLen) > Math.abs(longLen)) {
/* 1608 */       int swap = shortLen;
/* 1609 */       shortLen = longLen;
/* 1610 */       longLen = swap;
/* 1611 */       yLonger = true; }
/*      */     int decInc;
/*      */     int decInc;
/* 1614 */     if (longLen == 0) decInc = 0; else {
/* 1615 */       decInc = (shortLen << 16) / longLen;
/*      */     }
/* 1617 */     if (nx1 == nx2)
/*      */     {
/* 1619 */       if (ny1 > ny2) { int ty = ny1;ny1 = ny2;ny2 = ty; }
/* 1620 */       int offset = ny1 * this.width + nx1;
/* 1621 */       for (int j = ny1; j <= ny2; j++) {
/* 1622 */         thin_point_at_index(offset, 0.0F, this.strokeColor);
/* 1623 */         offset += this.width;
/*      */       }
/* 1625 */       return; }
/* 1626 */     if (ny1 == ny2)
/*      */     {
/* 1628 */       if (nx1 > nx2) { int tx = nx1;nx1 = nx2;nx2 = tx; }
/* 1629 */       int offset = ny1 * this.width + nx1;
/* 1630 */       for (int j = nx1; j <= nx2; j++) thin_point_at_index(offset++, 0.0F, this.strokeColor);
/* 1631 */       return; }
/* 1632 */     if (yLonger) {
/* 1633 */       if (longLen > 0) {
/* 1634 */         longLen += ny1;
/* 1635 */         for (int j = 32768 + (nx1 << 16); ny1 <= longLen; ny1++) {
/* 1636 */           thin_point_at(j >> 16, ny1, 0.0F, this.strokeColor);
/* 1637 */           j += decInc;
/*      */         }
/* 1639 */         return;
/*      */       }
/* 1641 */       longLen += ny1;
/* 1642 */       for (int j = 32768 + (nx1 << 16); ny1 >= longLen; ny1--) {
/* 1643 */         thin_point_at(j >> 16, ny1, 0.0F, this.strokeColor);
/* 1644 */         j -= decInc;
/*      */       }
/* 1646 */       return; }
/* 1647 */     if (longLen > 0) {
/* 1648 */       longLen += nx1;
/* 1649 */       for (int j = 32768 + (ny1 << 16); nx1 <= longLen; nx1++) {
/* 1650 */         thin_point_at(nx1, j >> 16, 0.0F, this.strokeColor);
/* 1651 */         j += decInc;
/*      */       }
/* 1653 */       return;
/*      */     }
/* 1655 */     longLen += nx1;
/* 1656 */     for (int j = 32768 + (ny1 << 16); nx1 >= longLen; nx1--) {
/* 1657 */       thin_point_at(nx1, j >> 16, 0.0F, this.strokeColor);
/* 1658 */       j -= decInc;
/*      */     }
/*      */   }
/*      */   
/*      */   private int thin_flat_line_clip_code(float x, float y)
/*      */   {
/* 1664 */     return (y < 0.0F ? 8 : 0) | (y > this.height1 ? 4 : 0) | 
/* 1665 */       (x < 0.0F ? 2 : 0) | (x > this.width1 ? 1 : 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private float thin_flat_line_slope(float x1, float y1, float x2, float y2, int border)
/*      */   {
/* 1671 */     switch (border) {
/*      */     case 4: 
/* 1673 */       return -y1 / (y2 - y1);
/*      */     
/*      */     case 3: 
/* 1676 */       return (this.height1 - y1) / (y2 - y1);
/*      */     
/*      */     case 2: 
/* 1679 */       return -x1 / (x2 - x1);
/*      */     
/*      */     case 1: 
/* 1682 */       return (this.width1 - x1) / (x2 - x1);
/*      */     }
/*      */     
/* 1685 */     return -1.0F;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void thick_flat_line(float ox1, float oy1, float r1, float g1, float b1, float a1, float ox2, float oy2, float r2, float g2, float b2, float a2)
/*      */   {
/* 1693 */     this.spolygon.interpARGB = ((r1 != r2) || (g1 != g2) || (b1 != b2) || (a1 != a2));
/*      */     
/*      */ 
/* 1696 */     float dX = ox2 - ox1 + 1.0E-4F;
/* 1697 */     float dY = oy2 - oy1 + 1.0E-4F;
/* 1698 */     float len = (float)Math.sqrt(dX * dX + dY * dY);
/*      */     
/*      */ 
/* 1701 */     float rh = this.strokeWeight / len / 2.0F;
/*      */     
/* 1703 */     float dx0 = rh * dY;
/* 1704 */     float dy0 = rh * dX;
/* 1705 */     float dx1 = rh * dY;
/* 1706 */     float dy1 = rh * dX;
/*      */     
/* 1708 */     this.spolygon.reset(4);
/*      */     
/* 1710 */     float[] svertex = this.spolygon.vertices[0];
/* 1711 */     svertex[18] = (ox1 + dx0);
/* 1712 */     svertex[19] = (oy1 - dy0);
/* 1713 */     svertex[3] = r1;
/* 1714 */     svertex[4] = g1;
/* 1715 */     svertex[5] = b1;
/* 1716 */     svertex[6] = a1;
/*      */     
/* 1718 */     svertex = this.spolygon.vertices[1];
/* 1719 */     svertex[18] = (ox1 - dx0);
/* 1720 */     svertex[19] = (oy1 + dy0);
/* 1721 */     svertex[3] = r1;
/* 1722 */     svertex[4] = g1;
/* 1723 */     svertex[5] = b1;
/* 1724 */     svertex[6] = a1;
/*      */     
/* 1726 */     svertex = this.spolygon.vertices[2];
/* 1727 */     svertex[18] = (ox2 - dx1);
/* 1728 */     svertex[19] = (oy2 + dy1);
/* 1729 */     svertex[3] = r2;
/* 1730 */     svertex[4] = g2;
/* 1731 */     svertex[5] = b2;
/* 1732 */     svertex[6] = a2;
/*      */     
/* 1734 */     svertex = this.spolygon.vertices[3];
/* 1735 */     svertex[18] = (ox2 + dx1);
/* 1736 */     svertex[19] = (oy2 - dy1);
/* 1737 */     svertex[3] = r2;
/* 1738 */     svertex[4] = g2;
/* 1739 */     svertex[5] = b2;
/* 1740 */     svertex[6] = a2;
/*      */     
/* 1742 */     this.spolygon.render();
/*      */   }
/*      */   
/*      */   private void draw_line(float[] v1, float[] v2)
/*      */   {
/* 1747 */     if (this.strokeWeight == 1.0F) {
/* 1748 */       if (this.line == null) { this.line = new PLine(this);
/*      */       }
/* 1750 */       this.line.reset();
/* 1751 */       this.line.setIntensities(v1[13], v1[14], v1[15], v1[16], 
/* 1752 */         v2[13], v2[14], v2[15], v2[16]);
/* 1753 */       this.line.setVertices(v1[18], v1[19], v1[20], 
/* 1754 */         v2[18], v2[19], v2[20]);
/* 1755 */       this.line.draw();
/*      */     }
/*      */     else {
/* 1758 */       thick_flat_line(v1[18], v1[19], v1[13], v1[14], v1[15], v1[16], 
/* 1759 */         v2[18], v2[19], v2[13], v2[14], v2[15], v2[16]);
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
/*      */   private void draw_lines(float[][] vertices, int max, int offset, int increment, int skip)
/*      */   {
/* 1772 */     if (this.strokeWeight == 1.0F) {
/* 1773 */       for (int i = 0; i < max; i += increment) {
/* 1774 */         if ((skip == 0) || ((i + offset) % skip != 0))
/*      */         {
/* 1776 */           float[] a = vertices[i];
/* 1777 */           float[] b = vertices[(i + offset)];
/*      */           
/* 1779 */           if (this.line == null) { this.line = new PLine(this);
/*      */           }
/* 1781 */           this.line.reset();
/* 1782 */           this.line.setIntensities(a[13], a[14], a[15], a[16], 
/* 1783 */             b[13], b[14], b[15], b[16]);
/* 1784 */           this.line.setVertices(a[18], a[19], a[20], 
/* 1785 */             b[18], b[19], b[20]);
/* 1786 */           this.line.draw();
/*      */         }
/*      */       }
/*      */     } else {
/* 1790 */       for (int i = 0; i < max; i += increment) {
/* 1791 */         if ((skip == 0) || ((i + offset) % skip != 0))
/*      */         {
/* 1793 */           float[] v1 = vertices[i];
/* 1794 */           float[] v2 = vertices[(i + offset)];
/* 1795 */           thick_flat_line(v1[18], v1[19], v1[13], v1[14], v1[15], v1[16], 
/* 1796 */             v2[18], v2[19], v2[13], v2[14], v2[15], v2[16]);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void thin_point(float fx, float fy, int color) {
/* 1803 */     int x = (int)(fx + 0.4999F);
/* 1804 */     int y = (int)(fy + 0.4999F);
/* 1805 */     if ((x < 0) || (x > this.width1) || (y < 0) || (y > this.height1)) { return;
/*      */     }
/* 1807 */     int index = y * this.width + x;
/* 1808 */     if ((color & 0xFF000000) == -16777216) {
/* 1809 */       this.pixels[index] = color;
/*      */     }
/*      */     else
/*      */     {
/* 1813 */       int a2 = color >> 24 & 0xFF;
/* 1814 */       int a1 = a2 ^ 0xFF;
/*      */       
/* 1816 */       int p2 = this.strokeColor;
/* 1817 */       int p1 = this.pixels[index];
/*      */       
/* 1819 */       int r = a1 * (p1 >> 16 & 0xFF) + a2 * (p2 >> 16 & 0xFF) & 0xFF00;
/* 1820 */       int g = a1 * (p1 >> 8 & 0xFF) + a2 * (p2 >> 8 & 0xFF) & 0xFF00;
/* 1821 */       int b = a1 * (p1 & 0xFF) + a2 * (p2 & 0xFF) >> 8;
/*      */       
/* 1823 */       this.pixels[index] = (0xFF000000 | r << 8 | g | b);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void translate(float tx, float ty)
/*      */   {
/* 1835 */     this.ctm.translate(tx, ty);
/*      */   }
/*      */   
/*      */   public void translate(float tx, float ty, float tz)
/*      */   {
/* 1840 */     showDepthWarningXYZ("translate");
/*      */   }
/*      */   
/*      */   public void rotate(float angle)
/*      */   {
/* 1845 */     this.ctm.rotate(angle);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void rotateX(float angle)
/*      */   {
/* 1853 */     showDepthWarning("rotateX");
/*      */   }
/*      */   
/*      */   public void rotateY(float angle) {
/* 1857 */     showDepthWarning("rotateY");
/*      */   }
/*      */   
/*      */   public void rotateZ(float angle)
/*      */   {
/* 1862 */     showDepthWarning("rotateZ");
/*      */   }
/*      */   
/*      */   public void rotate(float angle, float vx, float vy, float vz)
/*      */   {
/* 1867 */     showVariationWarning("rotate(angle, x, y, z)");
/*      */   }
/*      */   
/*      */   public void scale(float s)
/*      */   {
/* 1872 */     this.ctm.scale(s);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void scale(float sx, float sy)
/*      */   {
/* 1879 */     this.ctm.scale(sx, sy);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void scale(float x, float y, float z)
/*      */   {
/* 1886 */     showDepthWarningXYZ("scale");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void pushMatrix()
/*      */   {
/* 1897 */     if (this.matrixStackDepth == 32) {
/* 1898 */       throw new RuntimeException("Too many calls to pushMatrix().");
/*      */     }
/* 1900 */     this.ctm.get(this.matrixStack[this.matrixStackDepth]);
/* 1901 */     this.matrixStackDepth += 1;
/*      */   }
/*      */   
/*      */   public void popMatrix()
/*      */   {
/* 1906 */     if (this.matrixStackDepth == 0) {
/* 1907 */       throw new RuntimeException("Too many calls to popMatrix(), and not enough to pushMatrix().");
/*      */     }
/* 1909 */     this.matrixStackDepth -= 1;
/* 1910 */     this.ctm.set(this.matrixStack[this.matrixStackDepth]);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void resetMatrix()
/*      */   {
/* 1919 */     this.ctm.reset();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void applyMatrix(float n00, float n01, float n02, float n10, float n11, float n12)
/*      */   {
/* 1930 */     this.ctm.apply(n00, n01, n02, 
/* 1931 */       n10, n11, n12);
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
/*      */   public void applyMatrix(float n00, float n01, float n02, float n03, float n10, float n11, float n12, float n13, float n20, float n21, float n22, float n23, float n30, float n31, float n32, float n33)
/*      */   {
/* 1950 */     showDepthWarningXYZ("applyMatrix");
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
/*      */   public void printMatrix()
/*      */   {
/* 1970 */     this.ctm.print();
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
/*      */   public float screenX(float x, float y)
/*      */   {
/* 2009 */     return this.ctm.m00 * x + this.ctm.m01 * y + this.ctm.m02;
/*      */   }
/*      */   
/*      */   public float screenY(float x, float y)
/*      */   {
/* 2014 */     return this.ctm.m10 * x + this.ctm.m11 * y + this.ctm.m12;
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
/*      */   protected void backgroundImpl()
/*      */   {
/* 2028 */     Arrays.fill(this.pixels, this.backgroundColor);
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
/*      */   private final int blend_fill(int p1)
/*      */   {
/* 2104 */     int a2 = this.fillAi;
/* 2105 */     int a1 = a2 ^ 0xFF;
/*      */     
/* 2107 */     int r = a1 * (p1 >> 16 & 0xFF) + a2 * this.fillRi & 0xFF00;
/* 2108 */     int g = a1 * (p1 >> 8 & 0xFF) + a2 * this.fillGi & 0xFF00;
/* 2109 */     int b = a1 * (p1 & 0xFF) + a2 * this.fillBi & 0xFF00;
/*      */     
/* 2111 */     return 0xFF000000 | r << 8 | g | b >> 8;
/*      */   }
/*      */   
/*      */   private final int blend_color(int p1, int p2)
/*      */   {
/* 2116 */     int a2 = p2 >>> 24;
/*      */     
/* 2118 */     if (a2 == 255)
/*      */     {
/* 2120 */       return p2;
/*      */     }
/*      */     
/* 2123 */     int a1 = a2 ^ 0xFF;
/* 2124 */     int r = a1 * (p1 >> 16 & 0xFF) + a2 * (p2 >> 16 & 0xFF) & 0xFF00;
/* 2125 */     int g = a1 * (p1 >> 8 & 0xFF) + a2 * (p2 >> 8 & 0xFF) & 0xFF00;
/* 2126 */     int b = a1 * (p1 & 0xFF) + a2 * (p2 & 0xFF) >> 8;
/*      */     
/* 2128 */     return 0xFF000000 | r << 8 | g | b;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private final int blend_color_alpha(int p1, int p2, int a2)
/*      */   {
/* 2135 */     a2 = a2 * (p2 >>> 24) >> 8;
/*      */     
/* 2137 */     int a1 = a2 ^ 0xFF;
/* 2138 */     int r = a1 * (p1 >> 16 & 0xFF) + a2 * (p2 >> 16 & 0xFF) & 0xFF00;
/* 2139 */     int g = a1 * (p1 >> 8 & 0xFF) + a2 * (p2 >> 8 & 0xFF) & 0xFF00;
/* 2140 */     int b = a1 * (p1 & 0xFF) + a2 * (p2 & 0xFF) >> 8;
/*      */     
/* 2142 */     return 0xFF000000 | r << 8 | g | b;
/*      */   }
/*      */ }


/* Location:              /Users/AndrewKanarek/Desktop/MICHIGAN/KiefferWork/scripts-zibo/JavaApps/EllinghamDiagram/core.jar!/processing/core/PGraphics2D.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */