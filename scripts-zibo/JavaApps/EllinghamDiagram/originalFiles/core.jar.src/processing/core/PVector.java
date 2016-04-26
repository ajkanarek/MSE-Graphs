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
/*     */ public class PVector
/*     */ {
/*     */   public float x;
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
/*     */   public float y;
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
/*     */   public float z;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected float[] array;
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
/*     */   public PVector() {}
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
/*     */   public PVector(float x, float y, float z)
/*     */   {
/*  71 */     this.x = x;
/*  72 */     this.y = y;
/*  73 */     this.z = z;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PVector(float x, float y)
/*     */   {
/*  84 */     this.x = x;
/*  85 */     this.y = y;
/*  86 */     this.z = 0.0F;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void set(float x, float y, float z)
/*     */   {
/*  98 */     this.x = x;
/*  99 */     this.y = y;
/* 100 */     this.z = z;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void set(PVector v)
/*     */   {
/* 110 */     this.x = v.x;
/* 111 */     this.y = v.y;
/* 112 */     this.z = v.z;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void set(float[] source)
/*     */   {
/* 121 */     if (source.length >= 2) {
/* 122 */       this.x = source[0];
/* 123 */       this.y = source[1];
/*     */     }
/* 125 */     if (source.length >= 3) {
/* 126 */       this.z = source[2];
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public PVector get()
/*     */   {
/* 135 */     return new PVector(this.x, this.y, this.z);
/*     */   }
/*     */   
/*     */   public float[] get(float[] target)
/*     */   {
/* 140 */     if (target == null) {
/* 141 */       return new float[] { this.x, this.y, this.z };
/*     */     }
/* 143 */     if (target.length >= 2) {
/* 144 */       target[0] = this.x;
/* 145 */       target[1] = this.y;
/*     */     }
/* 147 */     if (target.length >= 3) {
/* 148 */       target[2] = this.z;
/*     */     }
/* 150 */     return target;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float mag()
/*     */   {
/* 159 */     return (float)Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void add(PVector v)
/*     */   {
/* 168 */     this.x += v.x;
/* 169 */     this.y += v.y;
/* 170 */     this.z += v.z;
/*     */   }
/*     */   
/*     */   public void add(float x, float y, float z)
/*     */   {
/* 175 */     this.x += x;
/* 176 */     this.y += y;
/* 177 */     this.z += z;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static PVector add(PVector v1, PVector v2)
/*     */   {
/* 188 */     return add(v1, v2, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static PVector add(PVector v1, PVector v2, PVector target)
/*     */   {
/* 200 */     if (target == null) {
/* 201 */       target = new PVector(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
/*     */     } else {
/* 203 */       target.set(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
/*     */     }
/* 205 */     return target;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void sub(PVector v)
/*     */   {
/* 214 */     this.x -= v.x;
/* 215 */     this.y -= v.y;
/* 216 */     this.z -= v.z;
/*     */   }
/*     */   
/*     */   public void sub(float x, float y, float z)
/*     */   {
/* 221 */     this.x -= x;
/* 222 */     this.y -= y;
/* 223 */     this.z -= z;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static PVector sub(PVector v1, PVector v2)
/*     */   {
/* 234 */     return sub(v1, v2, null);
/*     */   }
/*     */   
/*     */   public static PVector sub(PVector v1, PVector v2, PVector target)
/*     */   {
/* 239 */     if (target == null) {
/* 240 */       target = new PVector(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
/*     */     } else {
/* 242 */       target.set(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
/*     */     }
/* 244 */     return target;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void mult(float n)
/*     */   {
/* 253 */     this.x *= n;
/* 254 */     this.y *= n;
/* 255 */     this.z *= n;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static PVector mult(PVector v, float n)
/*     */   {
/* 266 */     return mult(v, n, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static PVector mult(PVector v, float n, PVector target)
/*     */   {
/* 278 */     if (target == null) {
/* 279 */       target = new PVector(v.x * n, v.y * n, v.z * n);
/*     */     } else {
/* 281 */       target.set(v.x * n, v.y * n, v.z * n);
/*     */     }
/* 283 */     return target;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void mult(PVector v)
/*     */   {
/* 292 */     this.x *= v.x;
/* 293 */     this.y *= v.y;
/* 294 */     this.z *= v.z;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static PVector mult(PVector v1, PVector v2)
/*     */   {
/* 303 */     return mult(v1, v2, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static PVector mult(PVector v1, PVector v2, PVector target)
/*     */   {
/* 315 */     if (target == null) {
/* 316 */       target = new PVector(v1.x * v2.x, v1.y * v2.y, v1.z * v2.z);
/*     */     } else {
/* 318 */       target.set(v1.x * v2.x, v1.y * v2.y, v1.z * v2.z);
/*     */     }
/* 320 */     return target;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void div(float n)
/*     */   {
/* 329 */     this.x /= n;
/* 330 */     this.y /= n;
/* 331 */     this.z /= n;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static PVector div(PVector v, float n)
/*     */   {
/* 342 */     return div(v, n, null);
/*     */   }
/*     */   
/*     */   public static PVector div(PVector v, float n, PVector target)
/*     */   {
/* 347 */     if (target == null) {
/* 348 */       target = new PVector(v.x / n, v.y / n, v.z / n);
/*     */     } else {
/* 350 */       target.set(v.x / n, v.y / n, v.z / n);
/*     */     }
/* 352 */     return target;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void div(PVector v)
/*     */   {
/* 360 */     this.x /= v.x;
/* 361 */     this.y /= v.y;
/* 362 */     this.z /= v.z;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static PVector div(PVector v1, PVector v2)
/*     */   {
/* 371 */     return div(v1, v2, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static PVector div(PVector v1, PVector v2, PVector target)
/*     */   {
/* 383 */     if (target == null) {
/* 384 */       target = new PVector(v1.x / v2.x, v1.y / v2.y, v1.z / v2.z);
/*     */     } else {
/* 386 */       target.set(v1.x / v2.x, v1.y / v2.y, v1.z / v2.z);
/*     */     }
/* 388 */     return target;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float dist(PVector v)
/*     */   {
/* 398 */     float dx = this.x - v.x;
/* 399 */     float dy = this.y - v.y;
/* 400 */     float dz = this.z - v.z;
/* 401 */     return (float)Math.sqrt(dx * dx + dy * dy + dz * dz);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static float dist(PVector v1, PVector v2)
/*     */   {
/* 412 */     float dx = v1.x - v2.x;
/* 413 */     float dy = v1.y - v2.y;
/* 414 */     float dz = v1.z - v2.z;
/* 415 */     return (float)Math.sqrt(dx * dx + dy * dy + dz * dz);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float dot(PVector v)
/*     */   {
/* 424 */     return this.x * v.x + this.y * v.y + this.z * v.z;
/*     */   }
/*     */   
/*     */   public float dot(float x, float y, float z)
/*     */   {
/* 429 */     return this.x * x + this.y * y + this.z * z;
/*     */   }
/*     */   
/*     */   public static float dot(PVector v1, PVector v2)
/*     */   {
/* 434 */     return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public PVector cross(PVector v)
/*     */   {
/* 442 */     return cross(v, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PVector cross(PVector v, PVector target)
/*     */   {
/* 451 */     float crossX = this.y * v.z - v.y * this.z;
/* 452 */     float crossY = this.z * v.x - v.z * this.x;
/* 453 */     float crossZ = this.x * v.y - v.x * this.y;
/*     */     
/* 455 */     if (target == null) {
/* 456 */       target = new PVector(crossX, crossY, crossZ);
/*     */     } else {
/* 458 */       target.set(crossX, crossY, crossZ);
/*     */     }
/* 460 */     return target;
/*     */   }
/*     */   
/*     */   public static PVector cross(PVector v1, PVector v2, PVector target)
/*     */   {
/* 465 */     float crossX = v1.y * v2.z - v2.y * v1.z;
/* 466 */     float crossY = v1.z * v2.x - v2.z * v1.x;
/* 467 */     float crossZ = v1.x * v2.y - v2.x * v1.y;
/*     */     
/* 469 */     if (target == null) {
/* 470 */       target = new PVector(crossX, crossY, crossZ);
/*     */     } else {
/* 472 */       target.set(crossX, crossY, crossZ);
/*     */     }
/* 474 */     return target;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void normalize()
/*     */   {
/* 482 */     float m = mag();
/* 483 */     if ((m != 0.0F) && (m != 1.0F)) {
/* 484 */       div(m);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PVector normalize(PVector target)
/*     */   {
/* 495 */     if (target == null) {
/* 496 */       target = new PVector();
/*     */     }
/* 498 */     float m = mag();
/* 499 */     if (m > 0.0F) {
/* 500 */       target.set(this.x / m, this.y / m, this.z / m);
/*     */     } else {
/* 502 */       target.set(this.x, this.y, this.z);
/*     */     }
/* 504 */     return target;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void limit(float max)
/*     */   {
/* 513 */     if (mag() > max) {
/* 514 */       normalize();
/* 515 */       mult(max);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float heading2D()
/*     */   {
/* 525 */     float angle = (float)Math.atan2(-this.y, this.x);
/* 526 */     return -1.0F * angle;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static float angleBetween(PVector v1, PVector v2)
/*     */   {
/* 537 */     double dot = v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
/* 538 */     double v1mag = Math.sqrt(v1.x * v1.x + v1.y * v1.y + v1.z * v1.z);
/* 539 */     double v2mag = Math.sqrt(v2.x * v2.x + v2.y * v2.y + v2.z * v2.z);
/* 540 */     return (float)Math.acos(dot / (v1mag * v2mag));
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 545 */     return "[ " + this.x + ", " + this.y + ", " + this.z + " ]";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float[] array()
/*     */   {
/* 555 */     if (this.array == null) {
/* 556 */       this.array = new float[3];
/*     */     }
/* 558 */     this.array[0] = this.x;
/* 559 */     this.array[1] = this.y;
/* 560 */     this.array[2] = this.z;
/* 561 */     return this.array;
/*     */   }
/*     */ }


/* Location:              /Users/AndrewKanarek/Desktop/MICHIGAN/KiefferWork/scripts-zibo/JavaApps/EllinghamDiagram/core.jar!/processing/core/PVector.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */