// 
// Decompiled by Procyon v0.5.30
// 

package processing.core;

public class PVector
{
    public float x;
    public float y;
    public float z;
    protected float[] array;
    
    public PVector() {
    }
    
    public PVector(final float x, final float y, final float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public PVector(final float x, final float y) {
        this.x = x;
        this.y = y;
        this.z = 0.0f;
    }
    
    public void set(final float x, final float y, final float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public void set(final PVector v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }
    
    public void set(final float[] source) {
        if (source.length >= 2) {
            this.x = source[0];
            this.y = source[1];
        }
        if (source.length >= 3) {
            this.z = source[2];
        }
    }
    
    public PVector get() {
        return new PVector(this.x, this.y, this.z);
    }
    
    public float[] get(final float[] target) {
        if (target == null) {
            return new float[] { this.x, this.y, this.z };
        }
        if (target.length >= 2) {
            target[0] = this.x;
            target[1] = this.y;
        }
        if (target.length >= 3) {
            target[2] = this.z;
        }
        return target;
    }
    
    public float mag() {
        return (float)Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }
    
    public void add(final PVector v) {
        this.x += v.x;
        this.y += v.y;
        this.z += v.z;
    }
    
    public void add(final float x, final float y, final float z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }
    
    public static PVector add(final PVector v1, final PVector v2) {
        return add(v1, v2, null);
    }
    
    public static PVector add(final PVector v1, final PVector v2, PVector target) {
        if (target == null) {
            target = new PVector(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
        }
        else {
            target.set(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
        }
        return target;
    }
    
    public void sub(final PVector v) {
        this.x -= v.x;
        this.y -= v.y;
        this.z -= v.z;
    }
    
    public void sub(final float x, final float y, final float z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
    }
    
    public static PVector sub(final PVector v1, final PVector v2) {
        return sub(v1, v2, null);
    }
    
    public static PVector sub(final PVector v1, final PVector v2, PVector target) {
        if (target == null) {
            target = new PVector(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
        }
        else {
            target.set(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
        }
        return target;
    }
    
    public void mult(final float n) {
        this.x *= n;
        this.y *= n;
        this.z *= n;
    }
    
    public static PVector mult(final PVector v, final float n) {
        return mult(v, n, null);
    }
    
    public static PVector mult(final PVector v, final float n, PVector target) {
        if (target == null) {
            target = new PVector(v.x * n, v.y * n, v.z * n);
        }
        else {
            target.set(v.x * n, v.y * n, v.z * n);
        }
        return target;
    }
    
    public void mult(final PVector v) {
        this.x *= v.x;
        this.y *= v.y;
        this.z *= v.z;
    }
    
    public static PVector mult(final PVector v1, final PVector v2) {
        return mult(v1, v2, null);
    }
    
    public static PVector mult(final PVector v1, final PVector v2, PVector target) {
        if (target == null) {
            target = new PVector(v1.x * v2.x, v1.y * v2.y, v1.z * v2.z);
        }
        else {
            target.set(v1.x * v2.x, v1.y * v2.y, v1.z * v2.z);
        }
        return target;
    }
    
    public void div(final float n) {
        this.x /= n;
        this.y /= n;
        this.z /= n;
    }
    
    public static PVector div(final PVector v, final float n) {
        return div(v, n, null);
    }
    
    public static PVector div(final PVector v, final float n, PVector target) {
        if (target == null) {
            target = new PVector(v.x / n, v.y / n, v.z / n);
        }
        else {
            target.set(v.x / n, v.y / n, v.z / n);
        }
        return target;
    }
    
    public void div(final PVector v) {
        this.x /= v.x;
        this.y /= v.y;
        this.z /= v.z;
    }
    
    public static PVector div(final PVector v1, final PVector v2) {
        return div(v1, v2, null);
    }
    
    public static PVector div(final PVector v1, final PVector v2, PVector target) {
        if (target == null) {
            target = new PVector(v1.x / v2.x, v1.y / v2.y, v1.z / v2.z);
        }
        else {
            target.set(v1.x / v2.x, v1.y / v2.y, v1.z / v2.z);
        }
        return target;
    }
    
    public float dist(final PVector v) {
        final float dx = this.x - v.x;
        final float dy = this.y - v.y;
        final float dz = this.z - v.z;
        return (float)Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
    
    public static float dist(final PVector v1, final PVector v2) {
        final float dx = v1.x - v2.x;
        final float dy = v1.y - v2.y;
        final float dz = v1.z - v2.z;
        return (float)Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
    
    public float dot(final PVector v) {
        return this.x * v.x + this.y * v.y + this.z * v.z;
    }
    
    public float dot(final float x, final float y, final float z) {
        return this.x * x + this.y * y + this.z * z;
    }
    
    public static float dot(final PVector v1, final PVector v2) {
        return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
    }
    
    public PVector cross(final PVector v) {
        return this.cross(v, null);
    }
    
    public PVector cross(final PVector v, PVector target) {
        final float crossX = this.y * v.z - v.y * this.z;
        final float crossY = this.z * v.x - v.z * this.x;
        final float crossZ = this.x * v.y - v.x * this.y;
        if (target == null) {
            target = new PVector(crossX, crossY, crossZ);
        }
        else {
            target.set(crossX, crossY, crossZ);
        }
        return target;
    }
    
    public static PVector cross(final PVector v1, final PVector v2, PVector target) {
        final float crossX = v1.y * v2.z - v2.y * v1.z;
        final float crossY = v1.z * v2.x - v2.z * v1.x;
        final float crossZ = v1.x * v2.y - v2.x * v1.y;
        if (target == null) {
            target = new PVector(crossX, crossY, crossZ);
        }
        else {
            target.set(crossX, crossY, crossZ);
        }
        return target;
    }
    
    public void normalize() {
        final float m = this.mag();
        if (m != 0.0f && m != 1.0f) {
            this.div(m);
        }
    }
    
    public PVector normalize(PVector target) {
        if (target == null) {
            target = new PVector();
        }
        final float m = this.mag();
        if (m > 0.0f) {
            target.set(this.x / m, this.y / m, this.z / m);
        }
        else {
            target.set(this.x, this.y, this.z);
        }
        return target;
    }
    
    public void limit(final float max) {
        if (this.mag() > max) {
            this.normalize();
            this.mult(max);
        }
    }
    
    public float heading2D() {
        final float angle = (float)Math.atan2(-this.y, this.x);
        return -1.0f * angle;
    }
    
    public static float angleBetween(final PVector v1, final PVector v2) {
        final double dot = v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
        final double v1mag = Math.sqrt(v1.x * v1.x + v1.y * v1.y + v1.z * v1.z);
        final double v2mag = Math.sqrt(v2.x * v2.x + v2.y * v2.y + v2.z * v2.z);
        return (float)Math.acos(dot / (v1mag * v2mag));
    }
    
    public String toString() {
        return "[ " + this.x + ", " + this.y + ", " + this.z + " ]";
    }
    
    public float[] array() {
        if (this.array == null) {
            this.array = new float[3];
        }
        this.array[0] = this.x;
        this.array[1] = this.y;
        this.array[2] = this.z;
        return this.array;
    }
}
