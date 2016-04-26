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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PMatrix2D
/*     */   implements PMatrix
/*     */ {
/*     */   public float m00;
/*     */   public float m01;
/*     */   public float m02;
/*     */   public float m10;
/*     */   public float m11;
/*     */   public float m12;
/*     */   
/*     */   public PMatrix2D()
/*     */   {
/*  37 */     reset();
/*     */   }
/*     */   
/*     */ 
/*     */   public PMatrix2D(float m00, float m01, float m02, float m10, float m11, float m12)
/*     */   {
/*  43 */     set(m00, m01, m02, 
/*  44 */       m10, m11, m12);
/*     */   }
/*     */   
/*     */   public PMatrix2D(PMatrix matrix)
/*     */   {
/*  49 */     set(matrix);
/*     */   }
/*     */   
/*     */   public void reset()
/*     */   {
/*  54 */     set(1.0F, 0.0F, 0.0F, 
/*  55 */       0.0F, 1.0F, 0.0F);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public PMatrix2D get()
/*     */   {
/*  63 */     PMatrix2D outgoing = new PMatrix2D();
/*  64 */     outgoing.set(this);
/*  65 */     return outgoing;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float[] get(float[] target)
/*     */   {
/*  74 */     if ((target == null) || (target.length != 6)) {
/*  75 */       target = new float[6];
/*     */     }
/*  77 */     target[0] = this.m00;
/*  78 */     target[1] = this.m01;
/*  79 */     target[2] = this.m02;
/*     */     
/*  81 */     target[3] = this.m10;
/*  82 */     target[4] = this.m11;
/*  83 */     target[5] = this.m12;
/*     */     
/*  85 */     return target;
/*     */   }
/*     */   
/*     */   public void set(PMatrix matrix)
/*     */   {
/*  90 */     if ((matrix instanceof PMatrix2D)) {
/*  91 */       PMatrix2D src = (PMatrix2D)matrix;
/*  92 */       set(src.m00, src.m01, src.m02, 
/*  93 */         src.m10, src.m11, src.m12);
/*     */     } else {
/*  95 */       throw new IllegalArgumentException("PMatrix2D.set() only accepts PMatrix2D objects.");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void set(PMatrix3D src) {}
/*     */   
/*     */ 
/*     */   public void set(float[] source)
/*     */   {
/* 105 */     this.m00 = source[0];
/* 106 */     this.m01 = source[1];
/* 107 */     this.m02 = source[2];
/*     */     
/* 109 */     this.m10 = source[3];
/* 110 */     this.m11 = source[4];
/* 111 */     this.m12 = source[5];
/*     */   }
/*     */   
/*     */ 
/*     */   public void set(float m00, float m01, float m02, float m10, float m11, float m12)
/*     */   {
/* 117 */     this.m00 = m00;this.m01 = m01;this.m02 = m02;
/* 118 */     this.m10 = m10;this.m11 = m11;this.m12 = m12;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void set(float m00, float m01, float m02, float m03, float m10, float m11, float m12, float m13, float m20, float m21, float m22, float m23, float m30, float m31, float m32, float m33) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void translate(float tx, float ty)
/*     */   {
/* 131 */     this.m02 = (tx * this.m00 + ty * this.m01 + this.m02);
/* 132 */     this.m12 = (tx * this.m10 + ty * this.m11 + this.m12);
/*     */   }
/*     */   
/*     */   public void translate(float x, float y, float z)
/*     */   {
/* 137 */     throw new IllegalArgumentException("Cannot use translate(x, y, z) on a PMatrix2D.");
/*     */   }
/*     */   
/*     */ 
/*     */   public void rotate(float angle)
/*     */   {
/* 143 */     float s = sin(angle);
/* 144 */     float c = cos(angle);
/*     */     
/* 146 */     float temp1 = this.m00;
/* 147 */     float temp2 = this.m01;
/* 148 */     this.m00 = (c * temp1 + s * temp2);
/* 149 */     this.m01 = (-s * temp1 + c * temp2);
/* 150 */     temp1 = this.m10;
/* 151 */     temp2 = this.m11;
/* 152 */     this.m10 = (c * temp1 + s * temp2);
/* 153 */     this.m11 = (-s * temp1 + c * temp2);
/*     */   }
/*     */   
/*     */   public void rotateX(float angle)
/*     */   {
/* 158 */     throw new IllegalArgumentException("Cannot use rotateX() on a PMatrix2D.");
/*     */   }
/*     */   
/*     */   public void rotateY(float angle)
/*     */   {
/* 163 */     throw new IllegalArgumentException("Cannot use rotateY() on a PMatrix2D.");
/*     */   }
/*     */   
/*     */   public void rotateZ(float angle)
/*     */   {
/* 168 */     rotate(angle);
/*     */   }
/*     */   
/*     */   public void rotate(float angle, float v0, float v1, float v2)
/*     */   {
/* 173 */     throw new IllegalArgumentException("Cannot use this version of rotate() on a PMatrix2D.");
/*     */   }
/*     */   
/*     */   public void scale(float s)
/*     */   {
/* 178 */     scale(s, s);
/*     */   }
/*     */   
/*     */   public void scale(float sx, float sy)
/*     */   {
/* 183 */     this.m00 *= sx;this.m01 *= sy;
/* 184 */     this.m10 *= sx;this.m11 *= sy;
/*     */   }
/*     */   
/*     */   public void scale(float x, float y, float z)
/*     */   {
/* 189 */     throw new IllegalArgumentException("Cannot use this version of scale() on a PMatrix2D.");
/*     */   }
/*     */   
/*     */   public void skewX(float angle)
/*     */   {
/* 194 */     apply(1.0F, 0.0F, 1.0F, tan(angle), 0.0F, 0.0F);
/*     */   }
/*     */   
/*     */   public void skewY(float angle)
/*     */   {
/* 199 */     apply(1.0F, 0.0F, 1.0F, 0.0F, tan(angle), 0.0F);
/*     */   }
/*     */   
/*     */   public void apply(PMatrix source)
/*     */   {
/* 204 */     if ((source instanceof PMatrix2D)) {
/* 205 */       apply((PMatrix2D)source);
/* 206 */     } else if ((source instanceof PMatrix3D)) {
/* 207 */       apply((PMatrix3D)source);
/*     */     }
/*     */   }
/*     */   
/*     */   public void apply(PMatrix2D source)
/*     */   {
/* 213 */     apply(source.m00, source.m01, source.m02, 
/* 214 */       source.m10, source.m11, source.m12);
/*     */   }
/*     */   
/*     */   public void apply(PMatrix3D source)
/*     */   {
/* 219 */     throw new IllegalArgumentException("Cannot use apply(PMatrix3D) on a PMatrix2D.");
/*     */   }
/*     */   
/*     */ 
/*     */   public void apply(float n00, float n01, float n02, float n10, float n11, float n12)
/*     */   {
/* 225 */     float t0 = this.m00;
/* 226 */     float t1 = this.m01;
/* 227 */     this.m00 = (n00 * t0 + n10 * t1);
/* 228 */     this.m01 = (n01 * t0 + n11 * t1);
/* 229 */     this.m02 += n02 * t0 + n12 * t1;
/*     */     
/* 231 */     t0 = this.m10;
/* 232 */     t1 = this.m11;
/* 233 */     this.m10 = (n00 * t0 + n10 * t1);
/* 234 */     this.m11 = (n01 * t0 + n11 * t1);
/* 235 */     this.m12 += n02 * t0 + n12 * t1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void apply(float n00, float n01, float n02, float n03, float n10, float n11, float n12, float n13, float n20, float n21, float n22, float n23, float n30, float n31, float n32, float n33)
/*     */   {
/* 243 */     throw new IllegalArgumentException("Cannot use this version of apply() on a PMatrix2D.");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void preApply(PMatrix2D left)
/*     */   {
/* 251 */     preApply(left.m00, left.m01, left.m02, 
/* 252 */       left.m10, left.m11, left.m12);
/*     */   }
/*     */   
/*     */   public void preApply(PMatrix3D left)
/*     */   {
/* 257 */     throw new IllegalArgumentException("Cannot use preApply(PMatrix3D) on a PMatrix2D.");
/*     */   }
/*     */   
/*     */ 
/*     */   public void preApply(float n00, float n01, float n02, float n10, float n11, float n12)
/*     */   {
/* 263 */     float t0 = this.m02;
/* 264 */     float t1 = this.m12;
/* 265 */     n02 += t0 * n00 + t1 * n01;
/* 266 */     n12 += t0 * n10 + t1 * n11;
/*     */     
/* 268 */     this.m02 = n02;
/* 269 */     this.m12 = n12;
/*     */     
/* 271 */     t0 = this.m00;
/* 272 */     t1 = this.m10;
/* 273 */     this.m00 = (t0 * n00 + t1 * n01);
/* 274 */     this.m10 = (t0 * n10 + t1 * n11);
/*     */     
/* 276 */     t0 = this.m01;
/* 277 */     t1 = this.m11;
/* 278 */     this.m01 = (t0 * n00 + t1 * n01);
/* 279 */     this.m11 = (t0 * n10 + t1 * n11);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void preApply(float n00, float n01, float n02, float n03, float n10, float n11, float n12, float n13, float n20, float n21, float n22, float n23, float n30, float n31, float n32, float n33)
/*     */   {
/* 287 */     throw new IllegalArgumentException("Cannot use this version of preApply() on a PMatrix2D.");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PVector mult(PVector source, PVector target)
/*     */   {
/* 298 */     if (target == null) {
/* 299 */       target = new PVector();
/*     */     }
/* 301 */     target.x = (this.m00 * source.x + this.m01 * source.y + this.m02);
/* 302 */     target.y = (this.m10 * source.x + this.m11 * source.y + this.m12);
/* 303 */     return target;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float[] mult(float[] vec, float[] out)
/*     */   {
/* 313 */     if ((out == null) || (out.length != 2)) {
/* 314 */       out = new float[2];
/*     */     }
/*     */     
/* 317 */     if (vec == out) {
/* 318 */       float tx = this.m00 * vec[0] + this.m01 * vec[1] + this.m02;
/* 319 */       float ty = this.m10 * vec[0] + this.m11 * vec[1] + this.m12;
/*     */       
/* 321 */       out[0] = tx;
/* 322 */       out[1] = ty;
/*     */     }
/*     */     else {
/* 325 */       out[0] = (this.m00 * vec[0] + this.m01 * vec[1] + this.m02);
/* 326 */       out[1] = (this.m10 * vec[0] + this.m11 * vec[1] + this.m12);
/*     */     }
/*     */     
/* 329 */     return out;
/*     */   }
/*     */   
/*     */   public float multX(float x, float y)
/*     */   {
/* 334 */     return this.m00 * x + this.m01 * y + this.m02;
/*     */   }
/*     */   
/*     */   public float multY(float x, float y)
/*     */   {
/* 339 */     return this.m10 * x + this.m11 * y + this.m12;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void transpose() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean invert()
/*     */   {
/* 355 */     float determinant = determinant();
/* 356 */     if (Math.abs(determinant) <= Float.MIN_VALUE) {
/* 357 */       return false;
/*     */     }
/*     */     
/* 360 */     float t00 = this.m00;
/* 361 */     float t01 = this.m01;
/* 362 */     float t02 = this.m02;
/* 363 */     float t10 = this.m10;
/* 364 */     float t11 = this.m11;
/* 365 */     float t12 = this.m12;
/*     */     
/* 367 */     this.m00 = (t11 / determinant);
/* 368 */     this.m10 = (-t10 / determinant);
/* 369 */     this.m01 = (-t01 / determinant);
/* 370 */     this.m11 = (t00 / determinant);
/* 371 */     this.m02 = ((t01 * t12 - t11 * t02) / determinant);
/* 372 */     this.m12 = ((t10 * t02 - t00 * t12) / determinant);
/*     */     
/* 374 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public float determinant()
/*     */   {
/* 382 */     return this.m00 * this.m11 - this.m01 * this.m10;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void print()
/*     */   {
/* 390 */     int big = (int)abs(max(PApplet.max(abs(this.m00), abs(this.m01), abs(this.m02)), 
/* 391 */       PApplet.max(abs(this.m10), abs(this.m11), abs(this.m12))));
/*     */     
/* 393 */     int digits = 1;
/* 394 */     if ((Float.isNaN(big)) || (Float.isInfinite(big))) {
/* 395 */       digits = 5;
/*     */     } else {
/* 397 */       while (big /= 10 != 0) { digits++;
/*     */       }
/*     */     }
/* 400 */     System.out.println(PApplet.nfs(this.m00, digits, 4) + " " + 
/* 401 */       PApplet.nfs(this.m01, digits, 4) + " " + 
/* 402 */       PApplet.nfs(this.m02, digits, 4));
/*     */     
/* 404 */     System.out.println(PApplet.nfs(this.m10, digits, 4) + " " + 
/* 405 */       PApplet.nfs(this.m11, digits, 4) + " " + 
/* 406 */       PApplet.nfs(this.m12, digits, 4));
/*     */     
/* 408 */     System.out.println();
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
/*     */   protected boolean isIdentity()
/*     */   {
/* 421 */     return (this.m00 == 1.0F) && (this.m01 == 0.0F) && (this.m02 == 0.0F) && (this.m10 == 0.0F) && (this.m11 == 1.0F) && (this.m12 == 0.0F);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected boolean isWarped()
/*     */   {
/* 428 */     return (this.m00 != 1.0F) || ((this.m01 != 0.0F) && (this.m10 != 0.0F)) || (this.m11 != 1.0F);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private final float max(float a, float b)
/*     */   {
/* 436 */     return a > b ? a : b;
/*     */   }
/*     */   
/*     */   private final float abs(float a) {
/* 440 */     return a < 0.0F ? -a : a;
/*     */   }
/*     */   
/*     */   private final float sin(float angle) {
/* 444 */     return (float)Math.sin(angle);
/*     */   }
/*     */   
/*     */   private final float cos(float angle) {
/* 448 */     return (float)Math.cos(angle);
/*     */   }
/*     */   
/*     */   private final float tan(float angle) {
/* 452 */     return (float)Math.tan(angle);
/*     */   }
/*     */ }


/* Location:              /Users/AndrewKanarek/Desktop/MICHIGAN/KiefferWork/scripts-zibo/JavaApps/EllinghamDiagram/core.jar!/processing/core/PMatrix2D.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */