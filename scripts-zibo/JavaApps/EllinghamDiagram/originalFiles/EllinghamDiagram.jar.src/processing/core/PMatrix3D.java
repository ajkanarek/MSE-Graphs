/*     */ package processing.core;
/*     */ 
/*     */ import java.io.PrintStream;
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
/*     */ public final class PMatrix3D
/*     */   implements PMatrix
/*     */ {
/*     */   public float m00;
/*     */   public float m01;
/*     */   public float m02;
/*     */   public float m03;
/*     */   public float m10;
/*     */   public float m11;
/*     */   public float m12;
/*     */   public float m13;
/*     */   public float m20;
/*     */   public float m21;
/*     */   public float m22;
/*     */   public float m23;
/*     */   public float m30;
/*     */   public float m31;
/*     */   public float m32;
/*     */   public float m33;
/*     */   protected PMatrix3D inverseCopy;
/*     */   
/*     */   public PMatrix3D()
/*     */   {
/*  43 */     reset();
/*     */   }
/*     */   
/*     */ 
/*     */   public PMatrix3D(float m00, float m01, float m02, float m10, float m11, float m12)
/*     */   {
/*  49 */     set(m00, m01, m02, 0.0F, 
/*  50 */       m10, m11, m12, 0.0F, 
/*  51 */       0.0F, 0.0F, 1.0F, 0.0F, 
/*  52 */       0.0F, 0.0F, 0.0F, 1.0F);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public PMatrix3D(float m00, float m01, float m02, float m03, float m10, float m11, float m12, float m13, float m20, float m21, float m22, float m23, float m30, float m31, float m32, float m33)
/*     */   {
/*  60 */     set(m00, m01, m02, m03, 
/*  61 */       m10, m11, m12, m13, 
/*  62 */       m20, m21, m22, m23, 
/*  63 */       m30, m31, m32, m33);
/*     */   }
/*     */   
/*     */   public PMatrix3D(PMatrix matrix)
/*     */   {
/*  68 */     set(matrix);
/*     */   }
/*     */   
/*     */   public void reset()
/*     */   {
/*  73 */     set(1.0F, 0.0F, 0.0F, 0.0F, 
/*  74 */       0.0F, 1.0F, 0.0F, 0.0F, 
/*  75 */       0.0F, 0.0F, 1.0F, 0.0F, 
/*  76 */       0.0F, 0.0F, 0.0F, 1.0F);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public PMatrix3D get()
/*     */   {
/*  84 */     PMatrix3D outgoing = new PMatrix3D();
/*  85 */     outgoing.set(this);
/*  86 */     return outgoing;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float[] get(float[] target)
/*     */   {
/*  95 */     if ((target == null) || (target.length != 16)) {
/*  96 */       target = new float[16];
/*     */     }
/*  98 */     target[0] = this.m00;
/*  99 */     target[1] = this.m01;
/* 100 */     target[2] = this.m02;
/* 101 */     target[3] = this.m03;
/*     */     
/* 103 */     target[4] = this.m10;
/* 104 */     target[5] = this.m11;
/* 105 */     target[6] = this.m12;
/* 106 */     target[7] = this.m13;
/*     */     
/* 108 */     target[8] = this.m20;
/* 109 */     target[9] = this.m21;
/* 110 */     target[10] = this.m22;
/* 111 */     target[11] = this.m23;
/*     */     
/* 113 */     target[12] = this.m30;
/* 114 */     target[13] = this.m31;
/* 115 */     target[14] = this.m32;
/* 116 */     target[15] = this.m33;
/*     */     
/* 118 */     return target;
/*     */   }
/*     */   
/*     */   public void set(PMatrix matrix)
/*     */   {
/* 123 */     if ((matrix instanceof PMatrix3D)) {
/* 124 */       PMatrix3D src = (PMatrix3D)matrix;
/* 125 */       set(src.m00, src.m01, src.m02, src.m03, 
/* 126 */         src.m10, src.m11, src.m12, src.m13, 
/* 127 */         src.m20, src.m21, src.m22, src.m23, 
/* 128 */         src.m30, src.m31, src.m32, src.m33);
/*     */     } else {
/* 130 */       PMatrix2D src = (PMatrix2D)matrix;
/* 131 */       set(src.m00, src.m01, 0.0F, src.m02, 
/* 132 */         src.m10, src.m11, 0.0F, src.m12, 
/* 133 */         0.0F, 0.0F, 1.0F, 0.0F, 
/* 134 */         0.0F, 0.0F, 0.0F, 1.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   public void set(float[] source)
/*     */   {
/* 140 */     if (source.length == 6) {
/* 141 */       set(source[0], source[1], source[2], 
/* 142 */         source[3], source[4], source[5]);
/*     */     }
/* 144 */     else if (source.length == 16) {
/* 145 */       this.m00 = source[0];
/* 146 */       this.m01 = source[1];
/* 147 */       this.m02 = source[2];
/* 148 */       this.m03 = source[3];
/*     */       
/* 150 */       this.m10 = source[4];
/* 151 */       this.m11 = source[5];
/* 152 */       this.m12 = source[6];
/* 153 */       this.m13 = source[7];
/*     */       
/* 155 */       this.m20 = source[8];
/* 156 */       this.m21 = source[9];
/* 157 */       this.m22 = source[10];
/* 158 */       this.m23 = source[11];
/*     */       
/* 160 */       this.m30 = source[12];
/* 161 */       this.m31 = source[13];
/* 162 */       this.m32 = source[14];
/* 163 */       this.m33 = source[15];
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void set(float m00, float m01, float m02, float m10, float m11, float m12)
/*     */   {
/* 170 */     set(m00, m01, 0.0F, m02, 
/* 171 */       m10, m11, 0.0F, m12, 
/* 172 */       0.0F, 0.0F, 1.0F, 0.0F, 
/* 173 */       0.0F, 0.0F, 0.0F, 1.0F);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void set(float m00, float m01, float m02, float m03, float m10, float m11, float m12, float m13, float m20, float m21, float m22, float m23, float m30, float m31, float m32, float m33)
/*     */   {
/* 181 */     this.m00 = m00;this.m01 = m01;this.m02 = m02;this.m03 = m03;
/* 182 */     this.m10 = m10;this.m11 = m11;this.m12 = m12;this.m13 = m13;
/* 183 */     this.m20 = m20;this.m21 = m21;this.m22 = m22;this.m23 = m23;
/* 184 */     this.m30 = m30;this.m31 = m31;this.m32 = m32;this.m33 = m33;
/*     */   }
/*     */   
/*     */   public void translate(float tx, float ty)
/*     */   {
/* 189 */     translate(tx, ty, 0.0F);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void translate(float tx, float ty, float tz)
/*     */   {
/* 198 */     this.m03 += tx * this.m00 + ty * this.m01 + tz * this.m02;
/* 199 */     this.m13 += tx * this.m10 + ty * this.m11 + tz * this.m12;
/* 200 */     this.m23 += tx * this.m20 + ty * this.m21 + tz * this.m22;
/* 201 */     this.m33 += tx * this.m30 + ty * this.m31 + tz * this.m32;
/*     */   }
/*     */   
/*     */   public void rotate(float angle)
/*     */   {
/* 206 */     rotateZ(angle);
/*     */   }
/*     */   
/*     */   public void rotateX(float angle)
/*     */   {
/* 211 */     float c = cos(angle);
/* 212 */     float s = sin(angle);
/* 213 */     apply(1.0F, 0.0F, 0.0F, 0.0F, 0.0F, c, -s, 0.0F, 0.0F, s, c, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public void rotateY(float angle)
/*     */   {
/* 218 */     float c = cos(angle);
/* 219 */     float s = sin(angle);
/* 220 */     apply(c, 0.0F, s, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, -s, 0.0F, c, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public void rotateZ(float angle)
/*     */   {
/* 225 */     float c = cos(angle);
/* 226 */     float s = sin(angle);
/* 227 */     apply(c, -s, 0.0F, 0.0F, s, c, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void rotate(float angle, float v0, float v1, float v2)
/*     */   {
/* 234 */     float c = cos(angle);
/* 235 */     float s = sin(angle);
/* 236 */     float t = 1.0F - c;
/*     */     
/* 238 */     apply(t * v0 * v0 + c, t * v0 * v1 - s * v2, t * v0 * v2 + s * v1, 0.0F, 
/* 239 */       t * v0 * v1 + s * v2, t * v1 * v1 + c, t * v1 * v2 - s * v0, 0.0F, 
/* 240 */       t * v0 * v2 - s * v1, t * v1 * v2 + s * v0, t * v2 * v2 + c, 0.0F, 
/* 241 */       0.0F, 0.0F, 0.0F, 1.0F);
/*     */   }
/*     */   
/*     */ 
/*     */   public void scale(float s)
/*     */   {
/* 247 */     scale(s, s, s);
/*     */   }
/*     */   
/*     */ 
/*     */   public void scale(float sx, float sy)
/*     */   {
/* 253 */     scale(sx, sy, 1.0F);
/*     */   }
/*     */   
/*     */ 
/*     */   public void scale(float x, float y, float z)
/*     */   {
/* 259 */     this.m00 *= x;this.m01 *= y;this.m02 *= z;
/* 260 */     this.m10 *= x;this.m11 *= y;this.m12 *= z;
/* 261 */     this.m20 *= x;this.m21 *= y;this.m22 *= z;
/* 262 */     this.m30 *= x;this.m31 *= y;this.m32 *= z;
/*     */   }
/*     */   
/*     */   public void skewX(float angle)
/*     */   {
/* 267 */     float t = (float)Math.tan(angle);
/* 268 */     apply(1.0F, t, 0.0F, 0.0F, 
/* 269 */       0.0F, 1.0F, 0.0F, 0.0F, 
/* 270 */       0.0F, 0.0F, 1.0F, 0.0F, 
/* 271 */       0.0F, 0.0F, 0.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public void skewY(float angle)
/*     */   {
/* 276 */     float t = (float)Math.tan(angle);
/* 277 */     apply(1.0F, 0.0F, 0.0F, 0.0F, 
/* 278 */       t, 1.0F, 0.0F, 0.0F, 
/* 279 */       0.0F, 0.0F, 1.0F, 0.0F, 
/* 280 */       0.0F, 0.0F, 0.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public void apply(PMatrix source)
/*     */   {
/* 285 */     if ((source instanceof PMatrix2D)) {
/* 286 */       apply((PMatrix2D)source);
/* 287 */     } else if ((source instanceof PMatrix3D)) {
/* 288 */       apply((PMatrix3D)source);
/*     */     }
/*     */   }
/*     */   
/*     */   public void apply(PMatrix2D source)
/*     */   {
/* 294 */     apply(source.m00, source.m01, 0.0F, source.m02, 
/* 295 */       source.m10, source.m11, 0.0F, source.m12, 
/* 296 */       0.0F, 0.0F, 1.0F, 0.0F, 
/* 297 */       0.0F, 0.0F, 0.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public void apply(PMatrix3D source)
/*     */   {
/* 302 */     apply(source.m00, source.m01, source.m02, source.m03, 
/* 303 */       source.m10, source.m11, source.m12, source.m13, 
/* 304 */       source.m20, source.m21, source.m22, source.m23, 
/* 305 */       source.m30, source.m31, source.m32, source.m33);
/*     */   }
/*     */   
/*     */ 
/*     */   public void apply(float n00, float n01, float n02, float n10, float n11, float n12)
/*     */   {
/* 311 */     apply(n00, n01, 0.0F, n02, 
/* 312 */       n10, n11, 0.0F, n12, 
/* 313 */       0.0F, 0.0F, 1.0F, 0.0F, 
/* 314 */       0.0F, 0.0F, 0.0F, 1.0F);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void apply(float n00, float n01, float n02, float n03, float n10, float n11, float n12, float n13, float n20, float n21, float n22, float n23, float n30, float n31, float n32, float n33)
/*     */   {
/* 323 */     float r00 = this.m00 * n00 + this.m01 * n10 + this.m02 * n20 + this.m03 * n30;
/* 324 */     float r01 = this.m00 * n01 + this.m01 * n11 + this.m02 * n21 + this.m03 * n31;
/* 325 */     float r02 = this.m00 * n02 + this.m01 * n12 + this.m02 * n22 + this.m03 * n32;
/* 326 */     float r03 = this.m00 * n03 + this.m01 * n13 + this.m02 * n23 + this.m03 * n33;
/*     */     
/* 328 */     float r10 = this.m10 * n00 + this.m11 * n10 + this.m12 * n20 + this.m13 * n30;
/* 329 */     float r11 = this.m10 * n01 + this.m11 * n11 + this.m12 * n21 + this.m13 * n31;
/* 330 */     float r12 = this.m10 * n02 + this.m11 * n12 + this.m12 * n22 + this.m13 * n32;
/* 331 */     float r13 = this.m10 * n03 + this.m11 * n13 + this.m12 * n23 + this.m13 * n33;
/*     */     
/* 333 */     float r20 = this.m20 * n00 + this.m21 * n10 + this.m22 * n20 + this.m23 * n30;
/* 334 */     float r21 = this.m20 * n01 + this.m21 * n11 + this.m22 * n21 + this.m23 * n31;
/* 335 */     float r22 = this.m20 * n02 + this.m21 * n12 + this.m22 * n22 + this.m23 * n32;
/* 336 */     float r23 = this.m20 * n03 + this.m21 * n13 + this.m22 * n23 + this.m23 * n33;
/*     */     
/* 338 */     float r30 = this.m30 * n00 + this.m31 * n10 + this.m32 * n20 + this.m33 * n30;
/* 339 */     float r31 = this.m30 * n01 + this.m31 * n11 + this.m32 * n21 + this.m33 * n31;
/* 340 */     float r32 = this.m30 * n02 + this.m31 * n12 + this.m32 * n22 + this.m33 * n32;
/* 341 */     float r33 = this.m30 * n03 + this.m31 * n13 + this.m32 * n23 + this.m33 * n33;
/*     */     
/* 343 */     this.m00 = r00;this.m01 = r01;this.m02 = r02;this.m03 = r03;
/* 344 */     this.m10 = r10;this.m11 = r11;this.m12 = r12;this.m13 = r13;
/* 345 */     this.m20 = r20;this.m21 = r21;this.m22 = r22;this.m23 = r23;
/* 346 */     this.m30 = r30;this.m31 = r31;this.m32 = r32;this.m33 = r33;
/*     */   }
/*     */   
/*     */   public void preApply(PMatrix2D left)
/*     */   {
/* 351 */     preApply(left.m00, left.m01, 0.0F, left.m02, 
/* 352 */       left.m10, left.m11, 0.0F, left.m12, 
/* 353 */       0.0F, 0.0F, 1.0F, 0.0F, 
/* 354 */       0.0F, 0.0F, 0.0F, 1.0F);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void preApply(PMatrix3D left)
/*     */   {
/* 362 */     preApply(left.m00, left.m01, left.m02, left.m03, 
/* 363 */       left.m10, left.m11, left.m12, left.m13, 
/* 364 */       left.m20, left.m21, left.m22, left.m23, 
/* 365 */       left.m30, left.m31, left.m32, left.m33);
/*     */   }
/*     */   
/*     */ 
/*     */   public void preApply(float n00, float n01, float n02, float n10, float n11, float n12)
/*     */   {
/* 371 */     preApply(n00, n01, 0.0F, n02, 
/* 372 */       n10, n11, 0.0F, n12, 
/* 373 */       0.0F, 0.0F, 1.0F, 0.0F, 
/* 374 */       0.0F, 0.0F, 0.0F, 1.0F);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void preApply(float n00, float n01, float n02, float n03, float n10, float n11, float n12, float n13, float n20, float n21, float n22, float n23, float n30, float n31, float n32, float n33)
/*     */   {
/* 383 */     float r00 = n00 * this.m00 + n01 * this.m10 + n02 * this.m20 + n03 * this.m30;
/* 384 */     float r01 = n00 * this.m01 + n01 * this.m11 + n02 * this.m21 + n03 * this.m31;
/* 385 */     float r02 = n00 * this.m02 + n01 * this.m12 + n02 * this.m22 + n03 * this.m32;
/* 386 */     float r03 = n00 * this.m03 + n01 * this.m13 + n02 * this.m23 + n03 * this.m33;
/*     */     
/* 388 */     float r10 = n10 * this.m00 + n11 * this.m10 + n12 * this.m20 + n13 * this.m30;
/* 389 */     float r11 = n10 * this.m01 + n11 * this.m11 + n12 * this.m21 + n13 * this.m31;
/* 390 */     float r12 = n10 * this.m02 + n11 * this.m12 + n12 * this.m22 + n13 * this.m32;
/* 391 */     float r13 = n10 * this.m03 + n11 * this.m13 + n12 * this.m23 + n13 * this.m33;
/*     */     
/* 393 */     float r20 = n20 * this.m00 + n21 * this.m10 + n22 * this.m20 + n23 * this.m30;
/* 394 */     float r21 = n20 * this.m01 + n21 * this.m11 + n22 * this.m21 + n23 * this.m31;
/* 395 */     float r22 = n20 * this.m02 + n21 * this.m12 + n22 * this.m22 + n23 * this.m32;
/* 396 */     float r23 = n20 * this.m03 + n21 * this.m13 + n22 * this.m23 + n23 * this.m33;
/*     */     
/* 398 */     float r30 = n30 * this.m00 + n31 * this.m10 + n32 * this.m20 + n33 * this.m30;
/* 399 */     float r31 = n30 * this.m01 + n31 * this.m11 + n32 * this.m21 + n33 * this.m31;
/* 400 */     float r32 = n30 * this.m02 + n31 * this.m12 + n32 * this.m22 + n33 * this.m32;
/* 401 */     float r33 = n30 * this.m03 + n31 * this.m13 + n32 * this.m23 + n33 * this.m33;
/*     */     
/* 403 */     this.m00 = r00;this.m01 = r01;this.m02 = r02;this.m03 = r03;
/* 404 */     this.m10 = r10;this.m11 = r11;this.m12 = r12;this.m13 = r13;
/* 405 */     this.m20 = r20;this.m21 = r21;this.m22 = r22;this.m23 = r23;
/* 406 */     this.m30 = r30;this.m31 = r31;this.m32 = r32;this.m33 = r33;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public PVector mult(PVector source, PVector target)
/*     */   {
/* 414 */     if (target == null) {
/* 415 */       target = new PVector();
/*     */     }
/* 417 */     target.x = (this.m00 * source.x + this.m01 * source.y + this.m02 * source.z + this.m03);
/* 418 */     target.y = (this.m10 * source.x + this.m11 * source.y + this.m12 * source.z + this.m13);
/* 419 */     target.z = (this.m20 * source.x + this.m21 * source.y + this.m22 * source.z + this.m23);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 424 */     return target;
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
/*     */   public float[] mult(float[] source, float[] target)
/*     */   {
/* 450 */     if ((target == null) || (target.length < 3)) {
/* 451 */       target = new float[3];
/*     */     }
/* 453 */     if (source == target) {
/* 454 */       throw new RuntimeException("The source and target vectors used in PMatrix3D.mult() cannot be identical.");
/*     */     }
/*     */     
/* 457 */     if (target.length == 3) {
/* 458 */       target[0] = (this.m00 * source[0] + this.m01 * source[1] + this.m02 * source[2] + this.m03);
/* 459 */       target[1] = (this.m10 * source[0] + this.m11 * source[1] + this.m12 * source[2] + this.m13);
/* 460 */       target[2] = (this.m20 * source[0] + this.m21 * source[1] + this.m22 * source[2] + this.m23);
/*     */ 
/*     */ 
/*     */ 
/*     */     }
/* 465 */     else if (target.length > 3) {
/* 466 */       target[0] = (this.m00 * source[0] + this.m01 * source[1] + this.m02 * source[2] + this.m03 * source[3]);
/* 467 */       target[1] = (this.m10 * source[0] + this.m11 * source[1] + this.m12 * source[2] + this.m13 * source[3]);
/* 468 */       target[2] = (this.m20 * source[0] + this.m21 * source[1] + this.m22 * source[2] + this.m23 * source[3]);
/* 469 */       target[3] = (this.m30 * source[0] + this.m31 * source[1] + this.m32 * source[2] + this.m33 * source[3]);
/*     */     }
/* 471 */     return target;
/*     */   }
/*     */   
/*     */   public float multX(float x, float y)
/*     */   {
/* 476 */     return this.m00 * x + this.m01 * y + this.m03;
/*     */   }
/*     */   
/*     */   public float multY(float x, float y)
/*     */   {
/* 481 */     return this.m10 * x + this.m11 * y + this.m13;
/*     */   }
/*     */   
/*     */   public float multX(float x, float y, float z)
/*     */   {
/* 486 */     return this.m00 * x + this.m01 * y + this.m02 * z + this.m03;
/*     */   }
/*     */   
/*     */   public float multY(float x, float y, float z)
/*     */   {
/* 491 */     return this.m10 * x + this.m11 * y + this.m12 * z + this.m13;
/*     */   }
/*     */   
/*     */   public float multZ(float x, float y, float z)
/*     */   {
/* 496 */     return this.m20 * x + this.m21 * y + this.m22 * z + this.m23;
/*     */   }
/*     */   
/*     */   public float multW(float x, float y, float z)
/*     */   {
/* 501 */     return this.m30 * x + this.m31 * y + this.m32 * z + this.m33;
/*     */   }
/*     */   
/*     */   public float multX(float x, float y, float z, float w)
/*     */   {
/* 506 */     return this.m00 * x + this.m01 * y + this.m02 * z + this.m03 * w;
/*     */   }
/*     */   
/*     */   public float multY(float x, float y, float z, float w)
/*     */   {
/* 511 */     return this.m10 * x + this.m11 * y + this.m12 * z + this.m13 * w;
/*     */   }
/*     */   
/*     */   public float multZ(float x, float y, float z, float w)
/*     */   {
/* 516 */     return this.m20 * x + this.m21 * y + this.m22 * z + this.m23 * w;
/*     */   }
/*     */   
/*     */   public float multW(float x, float y, float z, float w)
/*     */   {
/* 521 */     return this.m30 * x + this.m31 * y + this.m32 * z + this.m33 * w;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void transpose()
/*     */   {
/* 530 */     float temp = this.m01;this.m01 = this.m10;this.m10 = temp;
/* 531 */     temp = this.m02;this.m02 = this.m20;this.m20 = temp;
/* 532 */     temp = this.m03;this.m03 = this.m30;this.m30 = temp;
/* 533 */     temp = this.m12;this.m12 = this.m21;this.m21 = temp;
/* 534 */     temp = this.m13;this.m13 = this.m31;this.m31 = temp;
/* 535 */     temp = this.m23;this.m23 = this.m32;this.m32 = temp;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean invert()
/*     */   {
/* 544 */     float determinant = determinant();
/* 545 */     if (determinant == 0.0F) {
/* 546 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 550 */     float t00 = determinant3x3(this.m11, this.m12, this.m13, this.m21, this.m22, this.m23, this.m31, this.m32, this.m33);
/* 551 */     float t01 = -determinant3x3(this.m10, this.m12, this.m13, this.m20, this.m22, this.m23, this.m30, this.m32, this.m33);
/* 552 */     float t02 = determinant3x3(this.m10, this.m11, this.m13, this.m20, this.m21, this.m23, this.m30, this.m31, this.m33);
/* 553 */     float t03 = -determinant3x3(this.m10, this.m11, this.m12, this.m20, this.m21, this.m22, this.m30, this.m31, this.m32);
/*     */     
/*     */ 
/* 556 */     float t10 = -determinant3x3(this.m01, this.m02, this.m03, this.m21, this.m22, this.m23, this.m31, this.m32, this.m33);
/* 557 */     float t11 = determinant3x3(this.m00, this.m02, this.m03, this.m20, this.m22, this.m23, this.m30, this.m32, this.m33);
/* 558 */     float t12 = -determinant3x3(this.m00, this.m01, this.m03, this.m20, this.m21, this.m23, this.m30, this.m31, this.m33);
/* 559 */     float t13 = determinant3x3(this.m00, this.m01, this.m02, this.m20, this.m21, this.m22, this.m30, this.m31, this.m32);
/*     */     
/*     */ 
/* 562 */     float t20 = determinant3x3(this.m01, this.m02, this.m03, this.m11, this.m12, this.m13, this.m31, this.m32, this.m33);
/* 563 */     float t21 = -determinant3x3(this.m00, this.m02, this.m03, this.m10, this.m12, this.m13, this.m30, this.m32, this.m33);
/* 564 */     float t22 = determinant3x3(this.m00, this.m01, this.m03, this.m10, this.m11, this.m13, this.m30, this.m31, this.m33);
/* 565 */     float t23 = -determinant3x3(this.m00, this.m01, this.m02, this.m10, this.m11, this.m12, this.m30, this.m31, this.m32);
/*     */     
/*     */ 
/* 568 */     float t30 = -determinant3x3(this.m01, this.m02, this.m03, this.m11, this.m12, this.m13, this.m21, this.m22, this.m23);
/* 569 */     float t31 = determinant3x3(this.m00, this.m02, this.m03, this.m10, this.m12, this.m13, this.m20, this.m22, this.m23);
/* 570 */     float t32 = -determinant3x3(this.m00, this.m01, this.m03, this.m10, this.m11, this.m13, this.m20, this.m21, this.m23);
/* 571 */     float t33 = determinant3x3(this.m00, this.m01, this.m02, this.m10, this.m11, this.m12, this.m20, this.m21, this.m22);
/*     */     
/*     */ 
/* 574 */     this.m00 = (t00 / determinant);
/* 575 */     this.m01 = (t10 / determinant);
/* 576 */     this.m02 = (t20 / determinant);
/* 577 */     this.m03 = (t30 / determinant);
/*     */     
/* 579 */     this.m10 = (t01 / determinant);
/* 580 */     this.m11 = (t11 / determinant);
/* 581 */     this.m12 = (t21 / determinant);
/* 582 */     this.m13 = (t31 / determinant);
/*     */     
/* 584 */     this.m20 = (t02 / determinant);
/* 585 */     this.m21 = (t12 / determinant);
/* 586 */     this.m22 = (t22 / determinant);
/* 587 */     this.m23 = (t32 / determinant);
/*     */     
/* 589 */     this.m30 = (t03 / determinant);
/* 590 */     this.m31 = (t13 / determinant);
/* 591 */     this.m32 = (t23 / determinant);
/* 592 */     this.m33 = (t33 / determinant);
/*     */     
/* 594 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private float determinant3x3(float t00, float t01, float t02, float t10, float t11, float t12, float t20, float t21, float t22)
/*     */   {
/* 605 */     return t00 * (t11 * t22 - t12 * t21) + 
/* 606 */       t01 * (t12 * t20 - t10 * t22) + 
/* 607 */       t02 * (t10 * t21 - t11 * t20);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public float determinant()
/*     */   {
/* 615 */     float f = 
/* 616 */       this.m00 * (
/* 617 */       this.m11 * this.m22 * this.m33 + this.m12 * this.m23 * this.m31 + this.m13 * this.m21 * this.m32 - 
/* 618 */       this.m13 * this.m22 * this.m31 - 
/* 619 */       this.m11 * this.m23 * this.m32 - 
/* 620 */       this.m12 * this.m21 * this.m33);
/*     */     
/* 622 */     f = f - this.m01 * (this.m10 * this.m22 * this.m33 + this.m12 * this.m23 * this.m30 + this.m13 * this.m20 * this.m32 - 
/* 623 */       this.m13 * this.m22 * this.m30 - 
/* 624 */       this.m10 * this.m23 * this.m32 - 
/* 625 */       this.m12 * this.m20 * this.m33);
/*     */     
/* 627 */     f = f + this.m02 * (this.m10 * this.m21 * this.m33 + this.m11 * this.m23 * this.m30 + this.m13 * this.m20 * this.m31 - 
/* 628 */       this.m13 * this.m21 * this.m30 - 
/* 629 */       this.m10 * this.m23 * this.m31 - 
/* 630 */       this.m11 * this.m20 * this.m33);
/*     */     
/* 632 */     f = f - this.m03 * (this.m10 * this.m21 * this.m32 + this.m11 * this.m22 * this.m30 + this.m12 * this.m20 * this.m31 - 
/* 633 */       this.m12 * this.m21 * this.m30 - 
/* 634 */       this.m10 * this.m22 * this.m31 - 
/* 635 */       this.m11 * this.m20 * this.m32);
/* 636 */     return f;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void invTranslate(float tx, float ty, float tz)
/*     */   {
/* 648 */     preApply(1.0F, 0.0F, 0.0F, -tx, 
/* 649 */       0.0F, 1.0F, 0.0F, -ty, 
/* 650 */       0.0F, 0.0F, 1.0F, -tz, 
/* 651 */       0.0F, 0.0F, 0.0F, 1.0F);
/*     */   }
/*     */   
/*     */   protected void invRotateX(float angle)
/*     */   {
/* 656 */     float c = cos(-angle);
/* 657 */     float s = sin(-angle);
/* 658 */     preApply(1.0F, 0.0F, 0.0F, 0.0F, 0.0F, c, -s, 0.0F, 0.0F, s, c, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
/*     */   }
/*     */   
/*     */   protected void invRotateY(float angle)
/*     */   {
/* 663 */     float c = cos(-angle);
/* 664 */     float s = sin(-angle);
/* 665 */     preApply(c, 0.0F, s, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, -s, 0.0F, c, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
/*     */   }
/*     */   
/*     */   protected void invRotateZ(float angle)
/*     */   {
/* 670 */     float c = cos(-angle);
/* 671 */     float s = sin(-angle);
/* 672 */     preApply(c, -s, 0.0F, 0.0F, s, c, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void invRotate(float angle, float v0, float v1, float v2)
/*     */   {
/* 679 */     float c = cos(-angle);
/* 680 */     float s = sin(-angle);
/* 681 */     float t = 1.0F - c;
/*     */     
/* 683 */     preApply(t * v0 * v0 + c, t * v0 * v1 - s * v2, t * v0 * v2 + s * v1, 0.0F, 
/* 684 */       t * v0 * v1 + s * v2, t * v1 * v1 + c, t * v1 * v2 - s * v0, 0.0F, 
/* 685 */       t * v0 * v2 - s * v1, t * v1 * v2 + s * v0, t * v2 * v2 + c, 0.0F, 
/* 686 */       0.0F, 0.0F, 0.0F, 1.0F);
/*     */   }
/*     */   
/*     */   protected void invScale(float x, float y, float z)
/*     */   {
/* 691 */     preApply(1.0F / x, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F / y, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F / z, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean invApply(float n00, float n01, float n02, float n03, float n10, float n11, float n12, float n13, float n20, float n21, float n22, float n23, float n30, float n31, float n32, float n33)
/*     */   {
/* 699 */     if (this.inverseCopy == null) {
/* 700 */       this.inverseCopy = new PMatrix3D();
/*     */     }
/* 702 */     this.inverseCopy.set(n00, n01, n02, n03, 
/* 703 */       n10, n11, n12, n13, 
/* 704 */       n20, n21, n22, n23, 
/* 705 */       n30, n31, n32, n33);
/* 706 */     if (!this.inverseCopy.invert()) {
/* 707 */       return false;
/*     */     }
/* 709 */     preApply(this.inverseCopy);
/* 710 */     return true;
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
/*     */ 
/*     */   public void print()
/*     */   {
/* 724 */     int big = (int)Math.abs(max(max(max(max(abs(this.m00), abs(this.m01)), 
/* 725 */       max(abs(this.m02), abs(this.m03))), 
/* 726 */       max(max(abs(this.m10), abs(this.m11)), 
/* 727 */       max(abs(this.m12), abs(this.m13)))), 
/* 728 */       max(max(max(abs(this.m20), abs(this.m21)), 
/* 729 */       max(abs(this.m22), abs(this.m23))), 
/* 730 */       max(max(abs(this.m30), abs(this.m31)), 
/* 731 */       max(abs(this.m32), abs(this.m33))))));
/*     */     
/* 733 */     int digits = 1;
/* 734 */     if ((Float.isNaN(big)) || (Float.isInfinite(big))) {
/* 735 */       digits = 5;
/*     */     } else {
/* 737 */       while (big /= 10 != 0) { digits++;
/*     */       }
/*     */     }
/* 740 */     System.out.println(PApplet.nfs(this.m00, digits, 4) + " " + 
/* 741 */       PApplet.nfs(this.m01, digits, 4) + " " + 
/* 742 */       PApplet.nfs(this.m02, digits, 4) + " " + 
/* 743 */       PApplet.nfs(this.m03, digits, 4));
/*     */     
/* 745 */     System.out.println(PApplet.nfs(this.m10, digits, 4) + " " + 
/* 746 */       PApplet.nfs(this.m11, digits, 4) + " " + 
/* 747 */       PApplet.nfs(this.m12, digits, 4) + " " + 
/* 748 */       PApplet.nfs(this.m13, digits, 4));
/*     */     
/* 750 */     System.out.println(PApplet.nfs(this.m20, digits, 4) + " " + 
/* 751 */       PApplet.nfs(this.m21, digits, 4) + " " + 
/* 752 */       PApplet.nfs(this.m22, digits, 4) + " " + 
/* 753 */       PApplet.nfs(this.m23, digits, 4));
/*     */     
/* 755 */     System.out.println(PApplet.nfs(this.m30, digits, 4) + " " + 
/* 756 */       PApplet.nfs(this.m31, digits, 4) + " " + 
/* 757 */       PApplet.nfs(this.m32, digits, 4) + " " + 
/* 758 */       PApplet.nfs(this.m33, digits, 4));
/*     */     
/* 760 */     System.out.println();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private final float max(float a, float b)
/*     */   {
/* 768 */     return a > b ? a : b;
/*     */   }
/*     */   
/*     */   private final float abs(float a) {
/* 772 */     return a < 0.0F ? -a : a;
/*     */   }
/*     */   
/*     */   private final float sin(float angle) {
/* 776 */     return (float)Math.sin(angle);
/*     */   }
/*     */   
/*     */   private final float cos(float angle) {
/* 780 */     return (float)Math.cos(angle);
/*     */   }
/*     */ }


/* Location:              /Users/AndrewKanarek/Desktop/MICHIGAN/KiefferWork/scripts-zibo/JavaApps/EllinghamDiagram/core.jar!/processing/core/PMatrix3D.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */