/*     */ package processing.core;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PSmoothTriangle
/*     */   implements PConstants
/*     */ {
/*     */   private static final boolean EWJORDAN = false;
/*     */   
/*     */ 
/*     */ 
/*     */   private static final boolean FRY = false;
/*     */   
/*     */ 
/*     */ 
/*     */   static final int X = 0;
/*     */   
/*     */ 
/*     */ 
/*     */   static final int Y = 1;
/*     */   
/*     */ 
/*     */ 
/*     */   static final int Z = 2;
/*     */   
/*     */ 
/*     */ 
/*     */   static final int R = 3;
/*     */   
/*     */ 
/*     */ 
/*     */   static final int G = 4;
/*     */   
/*     */ 
/*     */ 
/*     */   static final int B = 5;
/*     */   
/*     */ 
/*     */ 
/*     */   static final int A = 6;
/*     */   
/*     */ 
/*     */ 
/*     */   static final int U = 7;
/*     */   
/*     */ 
/*     */ 
/*     */   static final int V = 8;
/*     */   
/*     */ 
/*     */   static final int DEFAULT_SIZE = 64;
/*     */   
/*     */ 
/*  55 */   float[][] vertices = new float[64][36];
/*     */   
/*     */ 
/*     */   int vertexCount;
/*     */   
/*     */   static final int ZBUFFER_MIN_COVERAGE = 204;
/*     */   
/*  62 */   float[] r = new float[64];
/*  63 */   float[] dr = new float[64];
/*  64 */   float[] l = new float[64];
/*  65 */   float[] dl = new float[64];
/*  66 */   float[] sp = new float[64];
/*  67 */   float[] sdp = new float[64];
/*     */   
/*     */   boolean interpX;
/*     */   
/*     */   boolean interpZ;
/*     */   
/*     */   boolean interpUV;
/*     */   
/*     */   boolean interpARGB;
/*     */   
/*     */   int rgba;
/*     */   
/*     */   int r2;
/*     */   
/*     */   int g2;
/*     */   
/*     */   int b2;
/*     */   int a2;
/*     */   int a2orig;
/*     */   boolean noDepthTest;
/*     */   PGraphics3D parent;
/*     */   int[] pixels;
/*     */   float[] zbuffer;
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
/*     */   boolean texture_smooth;
/*     */   static final int SUBXRES = 8;
/*     */   static final int SUBXRES1 = 7;
/*     */   static final int SUBYRES = 8;
/*     */   static final int SUBYRES1 = 7;
/*     */   static final int MAX_COVERAGE = 64;
/*     */   boolean smooth;
/*     */   int firstModY;
/*     */   int lastModY;
/*     */   int lastY;
/* 111 */   int[] aaleft = new int[8];
/* 112 */   int[] aaright = new int[8];
/*     */   int aaleftmin;
/*     */   int aarightmin;
/*     */   int aaleftmax;
/*     */   int aarightmax;
/*     */   int aaleftfull;
/*     */   int aarightfull;
/* 119 */   private float[] camX = new float[3];
/* 120 */   private float[] camY = new float[3];
/* 121 */   private float[] camZ = new float[3];
/*     */   private float ax;
/*     */   private float ay;
/*     */   private float az;
/*     */   private float bx;
/*     */   private float by;
/*     */   private float bz;
/*     */   private float cx;
/*     */   
/*     */   private final int MODYRES(int y) {
/* 131 */     return y & 0x7;
/*     */   }
/*     */   
/*     */   public PSmoothTriangle(PGraphics3D iparent)
/*     */   {
/* 136 */     this.parent = iparent;
/* 137 */     reset(0);
/*     */   }
/*     */   
/*     */   public void reset(int count)
/*     */   {
/* 142 */     this.vertexCount = count;
/* 143 */     this.interpX = true;
/* 144 */     this.interpZ = true;
/* 145 */     this.interpUV = false;
/* 146 */     this.interpARGB = true;
/* 147 */     this.timage = null;
/*     */   }
/*     */   
/*     */   public float[] nextVertex()
/*     */   {
/* 152 */     if (this.vertexCount == this.vertices.length)
/*     */     {
/*     */ 
/* 155 */       float[][] temp = new float[this.vertexCount << 1][36];
/* 156 */       System.arraycopy(this.vertices, 0, temp, 0, this.vertexCount);
/* 157 */       this.vertices = temp;
/*     */       
/* 159 */       this.r = new float[this.vertices.length];
/* 160 */       this.dr = new float[this.vertices.length];
/* 161 */       this.l = new float[this.vertices.length];
/* 162 */       this.dl = new float[this.vertices.length];
/* 163 */       this.sp = new float[this.vertices.length];
/* 164 */       this.sdp = new float[this.vertices.length];
/*     */     }
/* 166 */     return this.vertices[(this.vertexCount++)];
/*     */   }
/*     */   
/*     */   public void texture(PImage image)
/*     */   {
/* 171 */     this.timage = image;
/* 172 */     this.tpixels = image.pixels;
/* 173 */     this.twidth = image.width;
/* 174 */     this.theight = image.height;
/* 175 */     this.tformat = image.format;
/*     */     
/* 177 */     this.twidth1 = (this.twidth - 1);
/* 178 */     this.theight1 = (this.theight - 1);
/* 179 */     this.interpUV = true;
/*     */   }
/*     */   
/*     */   public void render() {
/* 183 */     if (this.vertexCount < 3) { return;
/*     */     }
/* 185 */     this.smooth = true;
/*     */     
/*     */ 
/* 188 */     this.pixels = this.parent.pixels;
/* 189 */     this.zbuffer = this.parent.zbuffer;
/*     */     
/* 191 */     this.noDepthTest = false;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 199 */     this.texture_smooth = true;
/*     */     
/* 201 */     this.width = (this.smooth ? this.parent.width * 8 : this.parent.width);
/* 202 */     this.height = (this.smooth ? this.parent.height * 8 : this.parent.height);
/*     */     
/* 204 */     this.width1 = (this.width - 1);
/* 205 */     this.height1 = (this.height - 1);
/*     */     
/* 207 */     if (!this.interpARGB) {
/* 208 */       this.r2 = ((int)(this.vertices[0][3] * 255.0F));
/* 209 */       this.g2 = ((int)(this.vertices[0][4] * 255.0F));
/* 210 */       this.b2 = ((int)(this.vertices[0][5] * 255.0F));
/* 211 */       this.a2 = ((int)(this.vertices[0][6] * 255.0F));
/* 212 */       this.a2orig = this.a2;
/* 213 */       this.rgba = (0xFF000000 | this.r2 << 16 | this.g2 << 8 | this.b2);
/*     */     }
/*     */     
/* 216 */     for (int i = 0; i < this.vertexCount; i++) {
/* 217 */       this.r[i] = 0.0F;this.dr[i] = 0.0F;this.l[i] = 0.0F;this.dl[i] = 0.0F;
/*     */     }
/*     */     
/* 220 */     if (this.smooth) {
/* 221 */       for (int i = 0; i < this.vertexCount; i++) {
/* 222 */         this.vertices[i][0] *= 8.0F;
/* 223 */         this.vertices[i][1] *= 8.0F;
/*     */       }
/* 225 */       this.firstModY = -1;
/*     */     }
/*     */     
/*     */ 
/* 229 */     int topi = 0;
/* 230 */     float ymin = this.vertices[0][1];
/* 231 */     float ymax = this.vertices[0][1];
/* 232 */     for (int i = 1; i < this.vertexCount; i++) {
/* 233 */       if (this.vertices[i][1] < ymin) {
/* 234 */         ymin = this.vertices[i][1];
/* 235 */         topi = i;
/*     */       }
/* 237 */       if (this.vertices[i][1] > ymax) { ymax = this.vertices[i][1];
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 247 */     this.lastY = ((int)(ymax - 0.5F));
/*     */     
/* 249 */     int lefti = topi;
/* 250 */     int righti = topi;
/* 251 */     int y = (int)(ymin + 0.5F);
/* 252 */     int lefty = y - 1;
/* 253 */     int righty = y - 1;
/*     */     
/* 255 */     this.interpX = true;
/*     */     
/* 257 */     int remaining = this.vertexCount;
/*     */     
/*     */ 
/*     */ 
/*     */     break label743;
/*     */     
/*     */ 
/* 264 */     remaining--;
/*     */     
/* 266 */     i = lefti != 0 ? lefti - 1 : this.vertexCount - 1;
/* 267 */     incrementalize_y(this.vertices[lefti], this.vertices[i], this.l, this.dl, y);
/* 268 */     lefty = (int)(this.vertices[i][1] + 0.5F);
/*     */     label743:
/* 261 */     for (; remaining > 0; 
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
/* 283 */         (y < lefty) && (y < righty))
/*     */     {
/*     */       int i;
/* 263 */       while (lefty <= y) { if (remaining <= 0) {
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
/* 274 */         remaining--;
/*     */         
/* 276 */         int i = righti != this.vertexCount - 1 ? righti + 1 : 0;
/* 277 */         incrementalize_y(this.vertices[righti], this.vertices[i], this.r, this.dr, y);
/* 278 */         righty = (int)(this.vertices[i][1] + 0.5F);
/* 279 */         righti = i;
/* 273 */         if (righty > y) break; } while (remaining > 0);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 283 */       continue;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 290 */       if ((y >= 0) && (y < this.height))
/*     */       {
/* 292 */         if (this.l[0] <= this.r[0]) scanline(y, this.l, this.r); else {
/* 293 */           scanline(y, this.r, this.l);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 298 */       y++;
/*     */       
/*     */ 
/* 301 */       increment(this.l, this.dl);
/* 302 */       increment(this.r, this.dr);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void unexpand()
/*     */   {
/* 312 */     if (this.smooth) {
/* 313 */       for (int i = 0; i < this.vertexCount; i++) {
/* 314 */         this.vertices[i][0] /= 8.0F;
/* 315 */         this.vertices[i][1] /= 8.0F;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void scanline(int y, float[] l, float[] r)
/*     */   {
/* 323 */     for (int i = 0; i < this.vertexCount; i++) {
/* 324 */       this.sp[i] = 0.0F;this.sdp[i] = 0.0F;
/*     */     }
/*     */     
/*     */ 
/* 328 */     int lx = (int)(l[0] + 0.49999F);
/* 329 */     if (lx < 0) lx = 0;
/* 330 */     int rx = (int)(r[0] - 0.5F);
/* 331 */     if (rx > this.width1) { rx = this.width1;
/*     */     }
/* 333 */     if (lx > rx) { return;
/*     */     }
/* 335 */     if (this.smooth) {
/* 336 */       int mody = MODYRES(y);
/*     */       
/* 338 */       this.aaleft[mody] = lx;
/* 339 */       this.aaright[mody] = rx;
/*     */       
/* 341 */       if (this.firstModY == -1) {
/* 342 */         this.firstModY = mody;
/* 343 */         this.aaleftmin = lx;this.aaleftmax = lx;
/* 344 */         this.aarightmin = rx;this.aarightmax = rx;
/*     */       }
/*     */       else {
/* 347 */         if (this.aaleftmin > this.aaleft[mody]) this.aaleftmin = this.aaleft[mody];
/* 348 */         if (this.aaleftmax < this.aaleft[mody]) this.aaleftmax = this.aaleft[mody];
/* 349 */         if (this.aarightmin > this.aaright[mody]) this.aarightmin = this.aaright[mody];
/* 350 */         if (this.aarightmax < this.aaright[mody]) { this.aarightmax = this.aaright[mody];
/*     */         }
/*     */       }
/* 353 */       this.lastModY = mody;
/*     */       
/* 355 */       if ((mody != 7) && (y != this.lastY)) { return;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 363 */       this.aaleftfull = (this.aaleftmax / 8 + 1);
/* 364 */       this.aarightfull = (this.aarightmin / 8 - 1);
/*     */     }
/*     */     
/*     */ 
/* 368 */     incrementalize_x(l, r, this.sp, this.sdp, lx);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 374 */     int offset = this.smooth ? this.parent.width * (y / 8) : this.parent.width * y;
/*     */     
/* 376 */     int truelx = 0;int truerx = 0;
/* 377 */     if (this.smooth) {
/* 378 */       truelx = lx / 8;
/* 379 */       truerx = (rx + 7) / 8;
/*     */       
/* 381 */       lx = this.aaleftmin / 8;
/* 382 */       rx = (this.aarightmax + 7) / 8;
/* 383 */       if (lx < 0) lx = 0;
/* 384 */       if (rx > this.parent.width1) { rx = this.parent.width1;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 390 */     this.interpX = false;
/*     */     
/*     */ 
/* 393 */     for (int x = lx; x <= rx; x++)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 398 */       if ((this.noDepthTest) || (this.sp[2] <= this.zbuffer[(offset + x)]))
/*     */       {
/*     */ 
/*     */ 
/* 402 */         if (this.interpUV) {
/* 403 */           int tu = (int)this.sp[7];
/* 404 */           int tv = (int)this.sp[8];
/*     */           
/* 406 */           if (tu > this.twidth1) tu = this.twidth1;
/* 407 */           if (tv > this.theight1) tv = this.theight1;
/* 408 */           if (tu < 0) tu = 0;
/* 409 */           if (tv < 0) { tv = 0;
/*     */           }
/* 411 */           int txy = tv * this.twidth + tu;
/*     */           
/* 413 */           float[] uv = new float[2];
/* 414 */           txy = getTextureIndex(x, y * 1.0F / 8.0F, uv);
/*     */           
/*     */ 
/* 417 */           tu = (int)uv[0];tv = (int)uv[1];
/*     */           
/*     */ 
/*     */ 
/*     */ 
/* 422 */           txy = this.twidth * tv + tu;
/*     */           int ta;
/*     */           int tr;
/*     */           int tg;
/*     */           int tb;
/* 427 */           if ((this.smooth) || (this.texture_smooth))
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 435 */             int tuf1 = (int)(255.0F * (uv[0] - tu));
/* 436 */             int tvf1 = (int)(255.0F * (uv[1] - tv));
/*     */             
/*     */ 
/*     */ 
/* 440 */             int tuf = 255 - tuf1;
/* 441 */             int tvf = 255 - tvf1;
/*     */             
/*     */ 
/* 444 */             int pixel00 = this.tpixels[txy];
/* 445 */             int pixel01 = tv < this.theight1 ? this.tpixels[(txy + this.twidth)] : this.tpixels[txy];
/* 446 */             int pixel10 = tu < this.twidth1 ? this.tpixels[(txy + 1)] : this.tpixels[txy];
/* 447 */             int pixel11 = (tv < this.theight1) && (tu < this.twidth1) ? this.tpixels[(txy + this.twidth + 1)] : this.tpixels[txy];
/*     */             
/*     */             int ta;
/*     */             
/*     */             int ta;
/*     */             
/* 453 */             if (this.tformat == 4) {
/* 454 */               int px0 = pixel00 * tuf + pixel10 * tuf1 >> 8;
/* 455 */               int px1 = pixel01 * tuf + pixel11 * tuf1 >> 8;
/* 456 */               ta = (px0 * tvf + px1 * tvf1 >> 8) * (
/* 457 */                 this.interpARGB ? (int)(this.sp[6] * 255.0F) : this.a2orig) >> 8; } else { int ta;
/* 458 */               if (this.tformat == 2) {
/* 459 */                 int p00 = pixel00 >> 24 & 0xFF;
/* 460 */                 int p01 = pixel01 >> 24 & 0xFF;
/* 461 */                 int p10 = pixel10 >> 24 & 0xFF;
/* 462 */                 int p11 = pixel11 >> 24 & 0xFF;
/*     */                 
/* 464 */                 int px0 = p00 * tuf + p10 * tuf1 >> 8;
/* 465 */                 int px1 = p01 * tuf + p11 * tuf1 >> 8;
/* 466 */                 ta = (px0 * tvf + px1 * tvf1 >> 8) * (
/* 467 */                   this.interpARGB ? (int)(this.sp[6] * 255.0F) : this.a2orig) >> 8;
/*     */               }
/*     */               else {
/* 470 */                 ta = this.interpARGB ? (int)(this.sp[6] * 255.0F) : this.a2orig;
/*     */               }
/*     */             }
/*     */             int tb;
/*     */             int tb;
/* 475 */             if ((this.tformat == 1) || (this.tformat == 2)) {
/* 476 */               int p00 = pixel00 >> 16 & 0xFF;
/* 477 */               int p01 = pixel01 >> 16 & 0xFF;
/* 478 */               int p10 = pixel10 >> 16 & 0xFF;
/* 479 */               int p11 = pixel11 >> 16 & 0xFF;
/*     */               
/* 481 */               int px0 = p00 * tuf + p10 * tuf1 >> 8;
/* 482 */               int px1 = p01 * tuf + p11 * tuf1 >> 8;
/* 483 */               int tr = (px0 * tvf + px1 * tvf1 >> 8) * (this.interpARGB ? (int)(this.sp[3] * 255.0F) : this.r2) >> 8;
/*     */               
/* 485 */               p00 = pixel00 >> 8 & 0xFF;
/* 486 */               p01 = pixel01 >> 8 & 0xFF;
/* 487 */               p10 = pixel10 >> 8 & 0xFF;
/* 488 */               p11 = pixel11 >> 8 & 0xFF;
/*     */               
/* 490 */               px0 = p00 * tuf + p10 * tuf1 >> 8;
/* 491 */               px1 = p01 * tuf + p11 * tuf1 >> 8;
/* 492 */               int tg = (px0 * tvf + px1 * tvf1 >> 8) * (this.interpARGB ? (int)(this.sp[4] * 255.0F) : this.g2) >> 8;
/*     */               
/*     */ 
/* 495 */               p00 = pixel00 & 0xFF;
/* 496 */               p01 = pixel01 & 0xFF;
/* 497 */               p10 = pixel10 & 0xFF;
/* 498 */               p11 = pixel11 & 0xFF;
/*     */               
/* 500 */               px0 = p00 * tuf + p10 * tuf1 >> 8;
/* 501 */               px1 = p01 * tuf + p11 * tuf1 >> 8;
/* 502 */               tb = (px0 * tvf + px1 * tvf1 >> 8) * (this.interpARGB ? (int)(this.sp[5] * 255.0F) : this.b2) >> 8;
/*     */             }
/*     */             else {
/*     */               int tb;
/* 506 */               if (this.interpARGB) {
/* 507 */                 int tr = (int)(this.sp[3] * 255.0F);
/* 508 */                 int tg = (int)(this.sp[4] * 255.0F);
/* 509 */                 tb = (int)(this.sp[5] * 255.0F);
/*     */               } else {
/* 511 */                 int tr = this.r2;
/* 512 */                 int tg = this.g2;
/* 513 */                 tb = this.b2;
/*     */               }
/*     */             }
/*     */             
/*     */ 
/*     */ 
/*     */ 
/* 520 */             int weight = this.smooth ? coverage(x) : 255;
/* 521 */             if (weight != 255) { ta = ta * weight >> 8;
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 526 */             int tpixel = this.tpixels[txy];
/*     */             
/*     */ 
/* 529 */             if (this.tformat == 4) {
/* 530 */               int ta = tpixel;
/* 531 */               if (this.interpARGB) {
/* 532 */                 int tr = (int)(this.sp[3] * 255.0F);
/* 533 */                 int tg = (int)(this.sp[4] * 255.0F);
/* 534 */                 int tb = (int)(this.sp[5] * 255.0F);
/* 535 */                 if (this.sp[6] != 1.0F) {
/* 536 */                   ta = (int)(this.sp[6] * 255.0F) * ta >> 8;
/*     */                 }
/*     */               } else {
/* 539 */                 int tr = this.r2;
/* 540 */                 int tg = this.g2;
/* 541 */                 int tb = this.b2;
/* 542 */                 ta = this.a2orig * ta >> 8;
/*     */               }
/*     */             }
/*     */             else {
/* 546 */               ta = this.tformat == 1 ? 255 : tpixel >> 24 & 0xFF;
/* 547 */               if (this.interpARGB) {
/* 548 */                 int tr = (int)(this.sp[3] * 255.0F) * (tpixel >> 16 & 0xFF) >> 8;
/* 549 */                 int tg = (int)(this.sp[4] * 255.0F) * (tpixel >> 8 & 0xFF) >> 8;
/* 550 */                 int tb = (int)(this.sp[5] * 255.0F) * (tpixel & 0xFF) >> 8;
/* 551 */                 ta = (int)(this.sp[6] * 255.0F) * ta >> 8;
/*     */               } else {
/* 553 */                 tr = this.r2 * (tpixel >> 16 & 0xFF) >> 8;
/* 554 */                 tg = this.g2 * (tpixel >> 8 & 0xFF) >> 8;
/* 555 */                 tb = this.b2 * (tpixel & 0xFF) >> 8;
/* 556 */                 ta = this.a2orig * ta >> 8;
/*     */               }
/*     */             }
/*     */           }
/*     */           
/* 561 */           if ((ta == 254) || (ta == 255))
/*     */           {
/* 563 */             this.pixels[(offset + x)] = (0xFF000000 | tr << 16 | tg << 8 | tb);
/* 564 */             this.zbuffer[(offset + x)] = this.sp[2];
/*     */           }
/*     */           else {
/* 567 */             int a1 = 255 - ta;
/* 568 */             int r1 = this.pixels[(offset + x)] >> 16 & 0xFF;
/* 569 */             int g1 = this.pixels[(offset + x)] >> 8 & 0xFF;
/* 570 */             int b1 = this.pixels[(offset + x)] & 0xFF;
/*     */             
/*     */ 
/* 573 */             this.pixels[(offset + x)] = 
/* 574 */               (0xFF000000 | 
/* 575 */               tr * ta + r1 * a1 >> 8 << 16 | 
/* 576 */               tg * ta + g1 * a1 & 0xFF00 | 
/* 577 */               tb * ta + b1 * a1 >> 8);
/*     */             
/*     */ 
/*     */ 
/* 581 */             if (ta > 204) { this.zbuffer[(offset + x)] = this.sp[2];
/*     */             }
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 587 */           int weight = this.smooth ? coverage(x) : 255;
/*     */           
/* 589 */           if (this.interpARGB) {
/* 590 */             this.r2 = ((int)(this.sp[3] * 255.0F));
/* 591 */             this.g2 = ((int)(this.sp[4] * 255.0F));
/* 592 */             this.b2 = ((int)(this.sp[5] * 255.0F));
/* 593 */             if (this.sp[6] != 1.0F) weight = weight * (int)(this.sp[6] * 255.0F) >> 8;
/* 594 */             if (weight == 255) {
/* 595 */               this.rgba = (0xFF000000 | this.r2 << 16 | this.g2 << 8 | this.b2);
/*     */             }
/*     */           }
/* 598 */           else if (this.a2orig != 255) { weight = weight * this.a2orig >> 8;
/*     */           }
/*     */           
/* 601 */           if (weight == 255)
/*     */           {
/* 603 */             this.pixels[(offset + x)] = this.rgba;
/* 604 */             this.zbuffer[(offset + x)] = this.sp[2];
/*     */           }
/*     */           else {
/* 607 */             int r1 = this.pixels[(offset + x)] >> 16 & 0xFF;
/* 608 */             int g1 = this.pixels[(offset + x)] >> 8 & 0xFF;
/* 609 */             int b1 = this.pixels[(offset + x)] & 0xFF;
/* 610 */             this.a2 = weight;
/*     */             
/* 612 */             int a1 = 255 - this.a2;
/* 613 */             this.pixels[(offset + x)] = 
/*     */             
/*     */ 
/*     */ 
/* 617 */               (0xFF000000 | r1 * a1 + this.r2 * this.a2 >> 8 << 16 | g1 * a1 + this.g2 * this.a2 >> 8 << 8 | b1 * a1 + this.b2 * this.a2 >> 8);
/*     */             
/* 619 */             if (this.a2 > 204) { this.zbuffer[(offset + x)] = this.sp[2];
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 626 */       if ((!this.smooth) || ((x >= truelx) && (x <= truerx)))
/*     */       {
/* 628 */         increment(this.sp, this.sdp);
/*     */       }
/*     */     }
/* 631 */     this.firstModY = -1;
/* 632 */     this.interpX = true;
/*     */   }
/*     */   
/*     */ 
/*     */   private int coverage(int x)
/*     */   {
/* 638 */     if ((x >= this.aaleftfull) && (x <= this.aarightfull))
/*     */     {
/* 640 */       if ((this.firstModY == 0) && (this.lastModY == 7)) {
/* 641 */         return 255;
/*     */       }
/*     */     }
/* 644 */     int pixelLeft = x * 8;
/* 645 */     int pixelRight = pixelLeft + 8;
/*     */     
/* 647 */     int amt = 0;
/* 648 */     for (int i = this.firstModY; i <= this.lastModY; i++) {
/* 649 */       if ((this.aaleft[i] <= pixelRight) && (this.aaright[i] >= pixelLeft))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/* 654 */         amt = amt + ((this.aaright[i] < pixelRight ? this.aaright[i] : pixelRight) - (this.aaleft[i] > pixelLeft ? this.aaleft[i] : pixelLeft)); }
/*     */     }
/* 656 */     amt <<= 2;
/* 657 */     return amt == 256 ? 255 : amt;
/*     */   }
/*     */   
/*     */ 
/*     */   private void incrementalize_y(float[] p1, float[] p2, float[] p, float[] dp, int y)
/*     */   {
/* 663 */     float delta = p2[1] - p1[1];
/* 664 */     if (delta == 0.0F) delta = 1.0F;
/* 665 */     float fraction = y + 0.5F - p1[1];
/*     */     
/* 667 */     if (this.interpX) {
/* 668 */       dp[0] = ((p2[0] - p1[0]) / delta);
/* 669 */       p1[0] += dp[0] * fraction;
/*     */     }
/* 671 */     if (this.interpZ) {
/* 672 */       dp[2] = ((p2[2] - p1[2]) / delta);
/* 673 */       p1[2] += dp[2] * fraction;
/*     */     }
/*     */     
/* 676 */     if (this.interpARGB) {
/* 677 */       dp[3] = ((p2[3] - p1[3]) / delta);
/* 678 */       dp[4] = ((p2[4] - p1[4]) / delta);
/* 679 */       dp[5] = ((p2[5] - p1[5]) / delta);
/* 680 */       dp[6] = ((p2[6] - p1[6]) / delta);
/* 681 */       p1[3] += dp[3] * fraction;
/* 682 */       p1[4] += dp[4] * fraction;
/* 683 */       p1[5] += dp[5] * fraction;
/* 684 */       p1[6] += dp[6] * fraction;
/*     */     }
/*     */     
/* 687 */     if (this.interpUV) {
/* 688 */       dp[7] = ((p2[7] - p1[7]) / delta);
/* 689 */       dp[8] = ((p2[8] - p1[8]) / delta);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 696 */       p1[7] += dp[7] * fraction;
/* 697 */       p1[8] += dp[8] * fraction;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void incrementalize_x(float[] p1, float[] p2, float[] p, float[] dp, int x)
/*     */   {
/* 706 */     float delta = p2[0] - p1[0];
/* 707 */     if (delta == 0.0F) delta = 1.0F;
/* 708 */     float fraction = x + 0.5F - p1[0];
/* 709 */     if (this.smooth) {
/* 710 */       delta /= 8.0F;
/* 711 */       fraction /= 8.0F;
/*     */     }
/*     */     
/* 714 */     if (this.interpX) {
/* 715 */       dp[0] = ((p2[0] - p1[0]) / delta);
/* 716 */       p1[0] += dp[0] * fraction;
/*     */     }
/* 718 */     if (this.interpZ) {
/* 719 */       dp[2] = ((p2[2] - p1[2]) / delta);
/* 720 */       p1[2] += dp[2] * fraction;
/*     */     }
/*     */     
/*     */ 
/* 724 */     if (this.interpARGB) {
/* 725 */       dp[3] = ((p2[3] - p1[3]) / delta);
/* 726 */       dp[4] = ((p2[4] - p1[4]) / delta);
/* 727 */       dp[5] = ((p2[5] - p1[5]) / delta);
/* 728 */       dp[6] = ((p2[6] - p1[6]) / delta);
/* 729 */       p1[3] += dp[3] * fraction;
/* 730 */       p1[4] += dp[4] * fraction;
/* 731 */       p1[5] += dp[5] * fraction;
/* 732 */       p1[6] += dp[6] * fraction;
/*     */     }
/*     */     
/* 735 */     if (this.interpUV)
/*     */     {
/* 737 */       dp[7] = ((p2[7] - p1[7]) / delta);
/* 738 */       dp[8] = ((p2[8] - p1[8]) / delta);
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
/* 750 */       p1[7] += dp[7] * fraction;
/* 751 */       p1[8] += dp[8] * fraction;
/*     */     }
/*     */   }
/*     */   
/*     */   private void increment(float[] p, float[] dp)
/*     */   {
/* 757 */     if (this.interpX) p[0] += dp[0];
/* 758 */     if (this.interpZ) { p[2] += dp[2];
/*     */     }
/* 760 */     if (this.interpARGB) {
/* 761 */       p[3] += dp[3];
/* 762 */       p[4] += dp[4];
/* 763 */       p[5] += dp[5];
/* 764 */       p[6] += dp[6];
/*     */     }
/*     */     
/* 767 */     if (this.interpUV)
/*     */     {
/* 769 */       p[7] += dp[7];
/* 770 */       p[8] += dp[8];
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setCamVertices(float x0, float y0, float z0, float x1, float y1, float z1, float x2, float y2, float z2)
/*     */   {
/* 784 */     this.camX[0] = x0;
/* 785 */     this.camX[1] = x1;
/* 786 */     this.camX[2] = x2;
/*     */     
/* 788 */     this.camY[0] = y0;
/* 789 */     this.camY[1] = y1;
/* 790 */     this.camY[2] = y2;
/*     */     
/* 792 */     this.camZ[0] = z0;
/* 793 */     this.camZ[1] = z1;
/* 794 */     this.camZ[2] = z2;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setVertices(float x0, float y0, float z0, float x1, float y1, float z1, float x2, float y2, float z2)
/*     */   {
/* 800 */     this.vertices[0][0] = x0;
/* 801 */     this.vertices[1][0] = x1;
/* 802 */     this.vertices[2][0] = x2;
/*     */     
/* 804 */     this.vertices[0][1] = y0;
/* 805 */     this.vertices[1][1] = y1;
/* 806 */     this.vertices[2][1] = y2;
/*     */     
/* 808 */     this.vertices[0][2] = z0;
/* 809 */     this.vertices[1][2] = z1;
/* 810 */     this.vertices[2][2] = z2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   boolean precomputeAccurateTexturing()
/*     */   {
/* 821 */     int o0 = 0;
/* 822 */     int o1 = 1;
/* 823 */     int o2 = 2;
/*     */     
/* 825 */     PMatrix3D myMatrix = new PMatrix3D(this.vertices[o0][7], this.vertices[o0][8], 1.0F, 0.0F, 
/* 826 */       this.vertices[o1][7], this.vertices[o1][8], 1.0F, 0.0F, 
/* 827 */       this.vertices[o2][7], this.vertices[o2][8], 1.0F, 0.0F, 
/* 828 */       0.0F, 0.0F, 0.0F, 1.0F);
/*     */     
/*     */ 
/*     */ 
/* 832 */     boolean invertSuccess = myMatrix.invert();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 838 */     if (!invertSuccess) { return false;
/*     */     }
/*     */     
/* 841 */     float m00 = myMatrix.m00 * this.camX[o0] + myMatrix.m01 * this.camX[o1] + myMatrix.m02 * this.camX[o2];
/* 842 */     float m01 = myMatrix.m10 * this.camX[o0] + myMatrix.m11 * this.camX[o1] + myMatrix.m12 * this.camX[o2];
/* 843 */     float m02 = myMatrix.m20 * this.camX[o0] + myMatrix.m21 * this.camX[o1] + myMatrix.m22 * this.camX[o2];
/* 844 */     float m10 = myMatrix.m00 * this.camY[o0] + myMatrix.m01 * this.camY[o1] + myMatrix.m02 * this.camY[o2];
/* 845 */     float m11 = myMatrix.m10 * this.camY[o0] + myMatrix.m11 * this.camY[o1] + myMatrix.m12 * this.camY[o2];
/* 846 */     float m12 = myMatrix.m20 * this.camY[o0] + myMatrix.m21 * this.camY[o1] + myMatrix.m22 * this.camY[o2];
/* 847 */     float m20 = -(myMatrix.m00 * this.camZ[o0] + myMatrix.m01 * this.camZ[o1] + myMatrix.m02 * this.camZ[o2]);
/* 848 */     float m21 = -(myMatrix.m10 * this.camZ[o0] + myMatrix.m11 * this.camZ[o1] + myMatrix.m12 * this.camZ[o2]);
/* 849 */     float m22 = -(myMatrix.m20 * this.camZ[o0] + myMatrix.m21 * this.camZ[o1] + myMatrix.m22 * this.camZ[o2]);
/*     */     
/* 851 */     float px = m02;
/* 852 */     float py = m12;
/* 853 */     float pz = m22;
/*     */     
/* 855 */     float TEX_WIDTH = this.twidth;
/* 856 */     float TEX_HEIGHT = this.theight;
/*     */     
/* 858 */     float resultT0x = m00 * TEX_WIDTH + m02;
/* 859 */     float resultT0y = m10 * TEX_WIDTH + m12;
/* 860 */     float resultT0z = m20 * TEX_WIDTH + m22;
/* 861 */     float result0Tx = m01 * TEX_HEIGHT + m02;
/* 862 */     float result0Ty = m11 * TEX_HEIGHT + m12;
/* 863 */     float result0Tz = m21 * TEX_HEIGHT + m22;
/* 864 */     float mx = resultT0x - m02;
/* 865 */     float my = resultT0y - m12;
/* 866 */     float mz = resultT0z - m22;
/* 867 */     float nx = result0Tx - m02;
/* 868 */     float ny = result0Ty - m12;
/* 869 */     float nz = result0Tz - m22;
/*     */     
/*     */ 
/* 872 */     this.ax = ((py * nz - pz * ny) * TEX_WIDTH);
/* 873 */     this.ay = ((pz * nx - px * nz) * TEX_WIDTH);
/* 874 */     this.az = ((px * ny - py * nx) * TEX_WIDTH);
/*     */     
/* 876 */     this.bx = ((my * pz - mz * py) * TEX_HEIGHT);
/* 877 */     this.by = ((mz * px - mx * pz) * TEX_HEIGHT);
/* 878 */     this.bz = ((mx * py - my * px) * TEX_HEIGHT);
/*     */     
/* 880 */     this.cx = (ny * mz - nz * my);
/* 881 */     this.cy = (nz * mx - nx * mz);
/* 882 */     this.cz = (nx * my - ny * mx);
/*     */     
/*     */ 
/*     */ 
/* 886 */     this.nearPlaneWidth = (this.parent.rightScreen - this.parent.leftScreen);
/* 887 */     this.nearPlaneHeight = (this.parent.topScreen - this.parent.bottomScreen);
/* 888 */     this.nearPlaneDepth = this.parent.nearPlane;
/*     */     
/*     */ 
/* 891 */     this.xmult = (this.nearPlaneWidth / this.parent.width);
/* 892 */     this.ymult = (this.nearPlaneHeight / this.parent.height);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 902 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   private float cy;
/*     */   
/*     */   private float cz;
/*     */   private float nearPlaneWidth;
/*     */   private float nearPlaneHeight;
/*     */   private float nearPlaneDepth;
/*     */   private float xmult;
/*     */   private float ymult;
/*     */   private int getTextureIndex(float sx, float sy, float[] uv)
/*     */   {
/* 916 */     sx = this.xmult * (sx - this.parent.width / 2.0F + 0.5F);
/* 917 */     sy = this.ymult * (sy - this.parent.height / 2.0F + 0.5F);
/*     */     
/*     */ 
/* 920 */     float sz = this.nearPlaneDepth;
/* 921 */     float a = sx * this.ax + sy * this.ay + sz * this.az;
/* 922 */     float b = sx * this.bx + sy * this.by + sz * this.bz;
/* 923 */     float c = sx * this.cx + sy * this.cy + sz * this.cz;
/* 924 */     int u = (int)(a / c);
/* 925 */     int v = (int)(b / c);
/* 926 */     uv[0] = (a / c);
/* 927 */     uv[1] = (b / c);
/* 928 */     if (uv[0] < 0.0F) {
/* 929 */       uv[0] = (u = 0);
/*     */     }
/* 931 */     if (uv[1] < 0.0F) {
/* 932 */       uv[1] = (v = 0);
/*     */     }
/* 934 */     if (uv[0] >= this.twidth) {
/* 935 */       uv[0] = (this.twidth - 1);
/* 936 */       u = this.twidth - 1;
/*     */     }
/* 938 */     if (uv[1] >= this.theight) {
/* 939 */       uv[1] = (this.theight - 1);
/* 940 */       v = this.theight - 1;
/*     */     }
/* 942 */     int result = v * this.twidth + u;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 948 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setIntensities(float ar, float ag, float ab, float aa, float br, float bg, float bb, float ba, float cr, float cg, float cb, float ca)
/*     */   {
/* 955 */     this.vertices[0][3] = ar;
/* 956 */     this.vertices[0][4] = ag;
/* 957 */     this.vertices[0][5] = ab;
/* 958 */     this.vertices[0][6] = aa;
/* 959 */     this.vertices[1][3] = br;
/* 960 */     this.vertices[1][4] = bg;
/* 961 */     this.vertices[1][5] = bb;
/* 962 */     this.vertices[1][6] = ba;
/* 963 */     this.vertices[2][3] = cr;
/* 964 */     this.vertices[2][4] = cg;
/* 965 */     this.vertices[2][5] = cb;
/* 966 */     this.vertices[2][6] = ca;
/*     */   }
/*     */ }


/* Location:              /Users/AndrewKanarek/Desktop/MICHIGAN/KiefferWork/scripts-zibo/JavaApps/EllinghamDiagram/core.jar!/processing/core/PSmoothTriangle.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */