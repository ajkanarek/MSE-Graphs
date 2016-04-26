/*     */ package processing.core;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PPolygon
/*     */   implements PConstants
/*     */ {
/*     */   static final int DEFAULT_SIZE = 64;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  34 */   float[][] vertices = new float[64][36];
/*     */   
/*     */   int vertexCount;
/*  37 */   float[] r = new float[64];
/*  38 */   float[] dr = new float[64];
/*  39 */   float[] l = new float[64];
/*  40 */   float[] dl = new float[64];
/*  41 */   float[] sp = new float[64];
/*  42 */   float[] sdp = new float[64];
/*     */   
/*     */   protected boolean interpX;
/*     */   
/*     */   protected boolean interpUV;
/*     */   protected boolean interpARGB;
/*     */   private int rgba;
/*     */   private int r2;
/*     */   private int g2;
/*     */   private int b2;
/*     */   private int a2;
/*     */   private int a2orig;
/*     */   PGraphics parent;
/*     */   int[] pixels;
/*     */   int width;
/*     */   int height;
/*     */   int width1;
/*     */   int height1;
/*     */   PImage timage;
/*     */   int[] tpixels;
/*     */   int theight;
/*     */   int twidth;
/*     */   int theight1;
/*     */   int twidth1;
/*     */   int tformat;
/*     */   static final int SUBXRES = 8;
/*     */   static final int SUBXRES1 = 7;
/*     */   static final int SUBYRES = 8;
/*     */   static final int SUBYRES1 = 7;
/*     */   static final int MAX_COVERAGE = 64;
/*     */   boolean smooth;
/*     */   int firstModY;
/*     */   int lastModY;
/*     */   int lastY;
/*  76 */   int[] aaleft = new int[8];
/*  77 */   int[] aaright = new int[8];
/*     */   int aaleftmin;
/*     */   int aarightmin;
/*     */   int aaleftmax;
/*     */   
/*     */   private final int MODYRES(int y) {
/*  83 */     return y & 0x7;
/*     */   }
/*     */   
/*     */   public PPolygon(PGraphics iparent)
/*     */   {
/*  88 */     this.parent = iparent;
/*  89 */     reset(0);
/*     */   }
/*     */   
/*     */   protected void reset(int count)
/*     */   {
/*  94 */     this.vertexCount = count;
/*  95 */     this.interpX = true;
/*     */     
/*  97 */     this.interpUV = false;
/*  98 */     this.interpARGB = true;
/*  99 */     this.timage = null;
/*     */   }
/*     */   
/*     */   protected float[] nextVertex()
/*     */   {
/* 104 */     if (this.vertexCount == this.vertices.length) {
/* 105 */       float[][] temp = new float[this.vertexCount << 1][36];
/* 106 */       System.arraycopy(this.vertices, 0, temp, 0, this.vertexCount);
/* 107 */       this.vertices = temp;
/*     */       
/* 109 */       this.r = new float[this.vertices.length];
/* 110 */       this.dr = new float[this.vertices.length];
/* 111 */       this.l = new float[this.vertices.length];
/* 112 */       this.dl = new float[this.vertices.length];
/* 113 */       this.sp = new float[this.vertices.length];
/* 114 */       this.sdp = new float[this.vertices.length];
/*     */     }
/* 116 */     return this.vertices[(this.vertexCount++)];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   int aarightmax;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   int aaleftfull;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   int aarightfull;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void texture(PImage image)
/*     */   {
/* 143 */     this.timage = image;
/*     */     
/* 145 */     if (image != null) {
/* 146 */       this.tpixels = image.pixels;
/* 147 */       this.twidth = image.width;
/* 148 */       this.theight = image.height;
/* 149 */       this.tformat = image.format;
/*     */       
/* 151 */       this.twidth1 = (this.twidth - 1);
/* 152 */       this.theight1 = (this.theight - 1);
/* 153 */       this.interpUV = true;
/*     */     }
/*     */     else {
/* 156 */       this.interpUV = false;
/*     */     }
/*     */   }
/*     */   
/*     */   protected void renderPolygon(float[][] v, int count)
/*     */   {
/* 162 */     this.vertices = v;
/* 163 */     this.vertexCount = count;
/*     */     
/* 165 */     if (this.r.length < this.vertexCount) {
/* 166 */       this.r = new float[this.vertexCount];
/* 167 */       this.dr = new float[this.vertexCount];
/* 168 */       this.l = new float[this.vertexCount];
/* 169 */       this.dl = new float[this.vertexCount];
/* 170 */       this.sp = new float[this.vertexCount];
/* 171 */       this.sdp = new float[this.vertexCount];
/*     */     }
/*     */     
/* 174 */     render();
/* 175 */     checkExpand();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void renderTriangle(float[] v1, float[] v2, float[] v3)
/*     */   {
/* 183 */     this.vertices[0] = v1;
/* 184 */     this.vertices[1] = v2;
/* 185 */     this.vertices[2] = v3;
/*     */     
/* 187 */     render();
/* 188 */     checkExpand();
/*     */   }
/*     */   
/*     */   protected void checkExpand()
/*     */   {
/* 193 */     if (this.smooth) {
/* 194 */       for (int i = 0; i < this.vertexCount; i++) {
/* 195 */         this.vertices[i][18] /= 8.0F;
/* 196 */         this.vertices[i][19] /= 8.0F;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void render()
/*     */   {
/* 203 */     if (this.vertexCount < 3) { return;
/*     */     }
/*     */     
/*     */ 
/* 207 */     this.pixels = this.parent.pixels;
/*     */     
/*     */ 
/*     */ 
/* 211 */     this.smooth = this.parent.smooth;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 218 */     this.width = (this.smooth ? this.parent.width * 8 : this.parent.width);
/* 219 */     this.height = (this.smooth ? this.parent.height * 8 : this.parent.height);
/*     */     
/* 221 */     this.width1 = (this.width - 1);
/* 222 */     this.height1 = (this.height - 1);
/*     */     
/* 224 */     if (!this.interpARGB) {
/* 225 */       this.r2 = ((int)(this.vertices[0][3] * 255.0F));
/* 226 */       this.g2 = ((int)(this.vertices[0][4] * 255.0F));
/* 227 */       this.b2 = ((int)(this.vertices[0][5] * 255.0F));
/* 228 */       this.a2 = ((int)(this.vertices[0][6] * 255.0F));
/* 229 */       this.a2orig = this.a2;
/* 230 */       this.rgba = (0xFF000000 | this.r2 << 16 | this.g2 << 8 | this.b2);
/*     */     }
/*     */     
/* 233 */     for (int i = 0; i < this.vertexCount; i++) {
/* 234 */       this.r[i] = 0.0F;this.dr[i] = 0.0F;this.l[i] = 0.0F;this.dl[i] = 0.0F;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 260 */     if (this.smooth) {
/* 261 */       for (int i = 0; i < this.vertexCount; i++) {
/* 262 */         this.vertices[i][18] *= 8.0F;
/* 263 */         this.vertices[i][19] *= 8.0F;
/*     */       }
/* 265 */       this.firstModY = -1;
/*     */     }
/*     */     
/*     */ 
/* 269 */     int topi = 0;
/* 270 */     float ymin = this.vertices[0][19];
/* 271 */     float ymax = this.vertices[0][19];
/* 272 */     for (int i = 1; i < this.vertexCount; i++) {
/* 273 */       if (this.vertices[i][19] < ymin) {
/* 274 */         ymin = this.vertices[i][19];
/* 275 */         topi = i;
/*     */       }
/* 277 */       if (this.vertices[i][19] > ymax) {
/* 278 */         ymax = this.vertices[i][19];
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 289 */     this.lastY = ((int)(ymax - 0.5F));
/*     */     
/* 291 */     int lefti = topi;
/* 292 */     int righti = topi;
/* 293 */     int y = (int)(ymin + 0.5F);
/* 294 */     int lefty = y - 1;
/* 295 */     int righty = y - 1;
/*     */     
/* 297 */     this.interpX = true;
/*     */     
/* 299 */     int remaining = this.vertexCount;
/*     */     
/*     */ 
/*     */ 
/*     */     break label740;
/*     */     
/*     */ 
/* 306 */     remaining--;
/*     */     
/* 308 */     i = lefti != 0 ? lefti - 1 : this.vertexCount - 1;
/* 309 */     incrementalizeY(this.vertices[lefti], this.vertices[i], this.l, this.dl, y);
/* 310 */     lefty = (int)(this.vertices[i][19] + 0.5F);
/*     */     label740:
/* 303 */     for (; remaining > 0; 
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 325 */         (y < lefty) && (y < righty))
/*     */     {
/*     */       int i;
/* 305 */       while (lefty <= y) { if (remaining <= 0) {
/*     */           break;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       do
/*     */       {
/* 316 */         remaining--;
/*     */         
/* 318 */         int i = righti != this.vertexCount - 1 ? righti + 1 : 0;
/* 319 */         incrementalizeY(this.vertices[righti], this.vertices[i], this.r, this.dr, y);
/* 320 */         righty = (int)(this.vertices[i][19] + 0.5F);
/* 321 */         righti = i;
/* 315 */         if (righty > y) break; } while (remaining > 0);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 325 */       continue;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 332 */       if ((y >= 0) && (y < this.height))
/*     */       {
/* 334 */         if (this.l[18] <= this.r[18]) scanline(y, this.l, this.r); else {
/* 335 */           scanline(y, this.r, this.l);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 340 */       y++;
/*     */       
/*     */ 
/* 343 */       increment(this.l, this.dl);
/* 344 */       increment(this.r, this.dr);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void scanline(int y, float[] l, float[] r)
/*     */   {
/* 355 */     for (int i = 0; i < this.vertexCount; i++) {
/* 356 */       this.sp[i] = 0.0F;this.sdp[i] = 0.0F;
/*     */     }
/*     */     
/*     */ 
/* 360 */     int lx = (int)(l[18] + 0.49999F);
/* 361 */     if (lx < 0) lx = 0;
/* 362 */     int rx = (int)(r[18] - 0.5F);
/* 363 */     if (rx > this.width1) { rx = this.width1;
/*     */     }
/* 365 */     if (lx > rx) { return;
/*     */     }
/* 367 */     if (this.smooth) {
/* 368 */       int mody = MODYRES(y);
/*     */       
/* 370 */       this.aaleft[mody] = lx;
/* 371 */       this.aaright[mody] = rx;
/*     */       
/* 373 */       if (this.firstModY == -1) {
/* 374 */         this.firstModY = mody;
/* 375 */         this.aaleftmin = lx;this.aaleftmax = lx;
/* 376 */         this.aarightmin = rx;this.aarightmax = rx;
/*     */       }
/*     */       else {
/* 379 */         if (this.aaleftmin > this.aaleft[mody]) this.aaleftmin = this.aaleft[mody];
/* 380 */         if (this.aaleftmax < this.aaleft[mody]) this.aaleftmax = this.aaleft[mody];
/* 381 */         if (this.aarightmin > this.aaright[mody]) this.aarightmin = this.aaright[mody];
/* 382 */         if (this.aarightmax < this.aaright[mody]) { this.aarightmax = this.aaright[mody];
/*     */         }
/*     */       }
/* 385 */       this.lastModY = mody;
/*     */       
/* 387 */       if ((mody != 7) && (y != this.lastY)) { return;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 395 */       this.aaleftfull = (this.aaleftmax / 8 + 1);
/* 396 */       this.aarightfull = (this.aarightmin / 8 - 1);
/*     */     }
/*     */     
/*     */ 
/* 400 */     incrementalizeX(l, r, this.sp, this.sdp, lx);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 405 */     int offset = this.smooth ? this.parent.width * (y / 8) : this.parent.width * y;
/*     */     
/* 407 */     int truelx = 0;int truerx = 0;
/* 408 */     if (this.smooth) {
/* 409 */       truelx = lx / 8;
/* 410 */       truerx = (rx + 7) / 8;
/*     */       
/* 412 */       lx = this.aaleftmin / 8;
/* 413 */       rx = (this.aarightmax + 7) / 8;
/* 414 */       if (lx < 0) lx = 0;
/* 415 */       if (rx > this.parent.width1) { rx = this.parent.width1;
/*     */       }
/*     */     }
/* 418 */     this.interpX = false;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 423 */     for (int x = lx; x <= rx; x++)
/*     */     {
/* 425 */       if (this.interpUV) {
/* 426 */         int tu = (int)(this.sp[7] * this.twidth);
/* 427 */         int tv = (int)(this.sp[8] * this.theight);
/*     */         
/* 429 */         if (tu > this.twidth1) tu = this.twidth1;
/* 430 */         if (tv > this.theight1) tv = this.theight1;
/* 431 */         if (tu < 0) tu = 0;
/* 432 */         if (tv < 0) { tv = 0;
/*     */         }
/* 434 */         int txy = tv * this.twidth + tu;
/*     */         
/* 436 */         int tuf1 = (int)(255.0F * (this.sp[7] * this.twidth - tu));
/* 437 */         int tvf1 = (int)(255.0F * (this.sp[8] * this.theight - tv));
/*     */         
/*     */ 
/*     */ 
/* 441 */         int tuf = 255 - tuf1;
/* 442 */         int tvf = 255 - tvf1;
/*     */         
/*     */ 
/* 445 */         int pixel00 = this.tpixels[txy];
/* 446 */         int pixel01 = tv < this.theight1 ? 
/* 447 */           this.tpixels[(txy + this.twidth)] : this.tpixels[txy];
/* 448 */         int pixel10 = tu < this.twidth1 ? 
/* 449 */           this.tpixels[(txy + 1)] : this.tpixels[txy];
/* 450 */         int pixel11 = (tv < this.theight1) && (tu < this.twidth1) ? 
/* 451 */           this.tpixels[(txy + this.twidth + 1)] : this.tpixels[txy];
/*     */         
/*     */ 
/*     */         int ta;
/*     */         
/*     */ 
/*     */         int ta;
/*     */         
/* 459 */         if (this.tformat == 4) {
/* 460 */           int px0 = pixel00 * tuf + pixel10 * tuf1 >> 8;
/* 461 */           int px1 = pixel01 * tuf + pixel11 * tuf1 >> 8;
/* 462 */           ta = (px0 * tvf + px1 * tvf1 >> 8) * (
/* 463 */             this.interpARGB ? (int)(this.sp[6] * 255.0F) : this.a2orig) >> 8;
/*     */         } else { int ta;
/* 465 */           if (this.tformat == 2) {
/* 466 */             int p00 = pixel00 >> 24 & 0xFF;
/* 467 */             int p01 = pixel01 >> 24 & 0xFF;
/* 468 */             int p10 = pixel10 >> 24 & 0xFF;
/* 469 */             int p11 = pixel11 >> 24 & 0xFF;
/*     */             
/* 471 */             int px0 = p00 * tuf + p10 * tuf1 >> 8;
/* 472 */             int px1 = p01 * tuf + p11 * tuf1 >> 8;
/* 473 */             ta = (px0 * tvf + px1 * tvf1 >> 8) * (
/* 474 */               this.interpARGB ? (int)(this.sp[6] * 255.0F) : this.a2orig) >> 8;
/*     */           }
/*     */           else {
/* 477 */             ta = this.interpARGB ? (int)(this.sp[6] * 255.0F) : this.a2orig; } }
/*     */         int tb;
/*     */         int tr;
/*     */         int tg;
/*     */         int tb;
/* 482 */         if ((this.tformat == 1) || (this.tformat == 2)) {
/* 483 */           int p00 = pixel00 >> 16 & 0xFF;
/* 484 */           int p01 = pixel01 >> 16 & 0xFF;
/* 485 */           int p10 = pixel10 >> 16 & 0xFF;
/* 486 */           int p11 = pixel11 >> 16 & 0xFF;
/*     */           
/* 488 */           int px0 = p00 * tuf + p10 * tuf1 >> 8;
/* 489 */           int px1 = p01 * tuf + p11 * tuf1 >> 8;
/* 490 */           int tr = (px0 * tvf + px1 * tvf1 >> 8) * (
/* 491 */             this.interpARGB ? (int)(this.sp[3] * 255.0F) : this.r2) >> 8;
/*     */           
/* 493 */           p00 = pixel00 >> 8 & 0xFF;
/* 494 */           p01 = pixel01 >> 8 & 0xFF;
/* 495 */           p10 = pixel10 >> 8 & 0xFF;
/* 496 */           p11 = pixel11 >> 8 & 0xFF;
/*     */           
/* 498 */           px0 = p00 * tuf + p10 * tuf1 >> 8;
/* 499 */           px1 = p01 * tuf + p11 * tuf1 >> 8;
/* 500 */           int tg = (px0 * tvf + px1 * tvf1 >> 8) * (
/* 501 */             this.interpARGB ? (int)(this.sp[4] * 255.0F) : this.g2) >> 8;
/*     */           
/* 503 */           p00 = pixel00 & 0xFF;
/* 504 */           p01 = pixel01 & 0xFF;
/* 505 */           p10 = pixel10 & 0xFF;
/* 506 */           p11 = pixel11 & 0xFF;
/*     */           
/* 508 */           px0 = p00 * tuf + p10 * tuf1 >> 8;
/* 509 */           px1 = p01 * tuf + p11 * tuf1 >> 8;
/* 510 */           tb = (px0 * tvf + px1 * tvf1 >> 8) * (
/* 511 */             this.interpARGB ? (int)(this.sp[5] * 255.0F) : this.b2) >> 8;
/*     */         } else {
/*     */           int tb;
/* 514 */           if (this.interpARGB) {
/* 515 */             int tr = (int)(this.sp[3] * 255.0F);
/* 516 */             int tg = (int)(this.sp[4] * 255.0F);
/* 517 */             tb = (int)(this.sp[5] * 255.0F);
/*     */           }
/*     */           else {
/* 520 */             tr = this.r2;
/* 521 */             tg = this.g2;
/* 522 */             tb = this.b2;
/*     */           }
/*     */         }
/*     */         
/* 526 */         int weight = this.smooth ? coverage(x) : 255;
/* 527 */         if (weight != 255) { ta = ta * weight >> 8;
/*     */         }
/* 529 */         if ((ta == 254) || (ta == 255))
/*     */         {
/* 531 */           this.pixels[(offset + x)] = (0xFF000000 | tr << 16 | tg << 8 | tb);
/*     */         }
/*     */         else
/*     */         {
/* 535 */           int a1 = 255 - ta;
/* 536 */           int r1 = this.pixels[(offset + x)] >> 16 & 0xFF;
/* 537 */           int g1 = this.pixels[(offset + x)] >> 8 & 0xFF;
/* 538 */           int b1 = this.pixels[(offset + x)] & 0xFF;
/*     */           
/* 540 */           this.pixels[(offset + x)] = 
/*     */           
/*     */ 
/* 543 */             (0xFF000000 | tr * ta + r1 * a1 >> 8 << 16 | tg * ta + g1 * a1 & 0xFF00 | tb * ta + b1 * a1 >> 8);
/*     */         }
/*     */       }
/*     */       else {
/* 547 */         int weight = this.smooth ? coverage(x) : 255;
/*     */         
/* 549 */         if (this.interpARGB) {
/* 550 */           this.r2 = ((int)(this.sp[3] * 255.0F));
/* 551 */           this.g2 = ((int)(this.sp[4] * 255.0F));
/* 552 */           this.b2 = ((int)(this.sp[5] * 255.0F));
/* 553 */           if (this.sp[6] != 1.0F) weight = weight * (int)(this.sp[6] * 255.0F) >> 8;
/* 554 */           if (weight == 255) {
/* 555 */             this.rgba = (0xFF000000 | this.r2 << 16 | this.g2 << 8 | this.b2);
/*     */           }
/*     */         }
/* 558 */         else if (this.a2orig != 255) { weight = weight * this.a2orig >> 8;
/*     */         }
/*     */         
/* 561 */         if (weight == 255)
/*     */         {
/* 563 */           this.pixels[(offset + x)] = this.rgba;
/*     */         }
/*     */         else
/*     */         {
/* 567 */           int r1 = this.pixels[(offset + x)] >> 16 & 0xFF;
/* 568 */           int g1 = this.pixels[(offset + x)] >> 8 & 0xFF;
/* 569 */           int b1 = this.pixels[(offset + x)] & 0xFF;
/* 570 */           this.a2 = weight;
/*     */           
/* 572 */           int a1 = 255 - this.a2;
/* 573 */           this.pixels[(offset + x)] = 
/*     */           
/*     */ 
/*     */ 
/* 577 */             (0xFF000000 | r1 * a1 + this.r2 * this.a2 >> 8 << 16 | g1 * a1 + this.g2 * this.a2 >> 8 << 8 | b1 * a1 + this.b2 * this.a2 >> 8);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 584 */       if ((!this.smooth) || ((x >= truelx) && (x <= truerx))) {
/* 585 */         increment(this.sp, this.sdp);
/*     */       }
/*     */     }
/* 588 */     this.firstModY = -1;
/* 589 */     this.interpX = true;
/*     */   }
/*     */   
/*     */ 
/*     */   private int coverage(int x)
/*     */   {
/* 595 */     if ((x >= this.aaleftfull) && (x <= this.aarightfull))
/*     */     {
/* 597 */       if ((this.firstModY == 0) && (this.lastModY == 7)) {
/* 598 */         return 255;
/*     */       }
/*     */     }
/* 601 */     int pixelLeft = x * 8;
/* 602 */     int pixelRight = pixelLeft + 8;
/*     */     
/* 604 */     int amt = 0;
/* 605 */     for (int i = this.firstModY; i <= this.lastModY; i++) {
/* 606 */       if ((this.aaleft[i] <= pixelRight) && (this.aaright[i] >= pixelLeft))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/* 611 */         amt = amt + ((this.aaright[i] < pixelRight ? this.aaright[i] : pixelRight) - (this.aaleft[i] > pixelLeft ? this.aaleft[i] : pixelLeft)); }
/*     */     }
/* 613 */     amt <<= 2;
/* 614 */     return amt == 256 ? 255 : amt;
/*     */   }
/*     */   
/*     */ 
/*     */   private void incrementalizeY(float[] p1, float[] p2, float[] p, float[] dp, int y)
/*     */   {
/* 620 */     float delta = p2[19] - p1[19];
/* 621 */     if (delta == 0.0F) delta = 1.0F;
/* 622 */     float fraction = y + 0.5F - p1[19];
/*     */     
/* 624 */     if (this.interpX) {
/* 625 */       dp[18] = ((p2[18] - p1[18]) / delta);
/* 626 */       p1[18] += dp[18] * fraction;
/*     */     }
/*     */     
/* 629 */     if (this.interpARGB) {
/* 630 */       dp[3] = ((p2[3] - p1[3]) / delta);
/* 631 */       dp[4] = ((p2[4] - p1[4]) / delta);
/* 632 */       dp[5] = ((p2[5] - p1[5]) / delta);
/* 633 */       dp[6] = ((p2[6] - p1[6]) / delta);
/* 634 */       p1[3] += dp[3] * fraction;
/* 635 */       p1[4] += dp[4] * fraction;
/* 636 */       p1[5] += dp[5] * fraction;
/* 637 */       p1[6] += dp[6] * fraction;
/*     */     }
/*     */     
/* 640 */     if (this.interpUV) {
/* 641 */       dp[7] = ((p2[7] - p1[7]) / delta);
/* 642 */       dp[8] = ((p2[8] - p1[8]) / delta);
/*     */       
/* 644 */       p1[7] += dp[7] * fraction;
/* 645 */       p1[8] += dp[8] * fraction;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void incrementalizeX(float[] p1, float[] p2, float[] p, float[] dp, int x)
/*     */   {
/* 652 */     float delta = p2[18] - p1[18];
/* 653 */     if (delta == 0.0F) delta = 1.0F;
/* 654 */     float fraction = x + 0.5F - p1[18];
/* 655 */     if (this.smooth) {
/* 656 */       delta /= 8.0F;
/* 657 */       fraction /= 8.0F;
/*     */     }
/*     */     
/* 660 */     if (this.interpX) {
/* 661 */       dp[18] = ((p2[18] - p1[18]) / delta);
/* 662 */       p1[18] += dp[18] * fraction;
/*     */     }
/*     */     
/* 665 */     if (this.interpARGB) {
/* 666 */       dp[3] = ((p2[3] - p1[3]) / delta);
/* 667 */       dp[4] = ((p2[4] - p1[4]) / delta);
/* 668 */       dp[5] = ((p2[5] - p1[5]) / delta);
/* 669 */       dp[6] = ((p2[6] - p1[6]) / delta);
/* 670 */       p1[3] += dp[3] * fraction;
/* 671 */       p1[4] += dp[4] * fraction;
/* 672 */       p1[5] += dp[5] * fraction;
/* 673 */       p1[6] += dp[6] * fraction;
/*     */     }
/*     */     
/* 676 */     if (this.interpUV) {
/* 677 */       dp[7] = ((p2[7] - p1[7]) / delta);
/* 678 */       dp[8] = ((p2[8] - p1[8]) / delta);
/*     */       
/* 680 */       p1[7] += dp[7] * fraction;
/* 681 */       p1[8] += dp[8] * fraction;
/*     */     }
/*     */   }
/*     */   
/*     */   private void increment(float[] p, float[] dp)
/*     */   {
/* 687 */     if (this.interpX) { p[18] += dp[18];
/*     */     }
/* 689 */     if (this.interpARGB) {
/* 690 */       p[3] += dp[3];
/* 691 */       p[4] += dp[4];
/* 692 */       p[5] += dp[5];
/* 693 */       p[6] += dp[6];
/*     */     }
/*     */     
/* 696 */     if (this.interpUV) {
/* 697 */       p[7] += dp[7];
/* 698 */       p[8] += dp[8];
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/AndrewKanarek/Desktop/MICHIGAN/KiefferWork/scripts-zibo/JavaApps/EllinghamDiagram/core.jar!/processing/core/PPolygon.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */