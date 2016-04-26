// 
// Decompiled by Procyon v0.5.30
// 

package processing.core;

public class PMatrix2D implements PMatrix
{
    public float m00;
    public float m01;
    public float m02;
    public float m10;
    public float m11;
    public float m12;
    
    public PMatrix2D() {
        this.reset();
    }
    
    public PMatrix2D(final float m00, final float m01, final float m02, final float m10, final float m11, final float m12) {
        this.set(m00, m01, m02, m10, m11, m12);
    }
    
    public PMatrix2D(final PMatrix matrix) {
        this.set(matrix);
    }
    
    public void reset() {
        this.set(1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
    }
    
    public PMatrix2D get() {
        final PMatrix2D outgoing = new PMatrix2D();
        outgoing.set(this);
        return outgoing;
    }
    
    public float[] get(float[] target) {
        if (target == null || target.length != 6) {
            target = new float[6];
        }
        target[0] = this.m00;
        target[1] = this.m01;
        target[2] = this.m02;
        target[3] = this.m10;
        target[4] = this.m11;
        target[5] = this.m12;
        return target;
    }
    
    public void set(final PMatrix matrix) {
        if (matrix instanceof PMatrix2D) {
            final PMatrix2D src = (PMatrix2D)matrix;
            this.set(src.m00, src.m01, src.m02, src.m10, src.m11, src.m12);
            return;
        }
        throw new IllegalArgumentException("PMatrix2D.set() only accepts PMatrix2D objects.");
    }
    
    public void set(final PMatrix3D src) {
    }
    
    public void set(final float[] source) {
        this.m00 = source[0];
        this.m01 = source[1];
        this.m02 = source[2];
        this.m10 = source[3];
        this.m11 = source[4];
        this.m12 = source[5];
    }
    
    public void set(final float m00, final float m01, final float m02, final float m10, final float m11, final float m12) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
    }
    
    public void set(final float m00, final float m01, final float m02, final float m03, final float m10, final float m11, final float m12, final float m13, final float m20, final float m21, final float m22, final float m23, final float m30, final float m31, final float m32, final float m33) {
    }
    
    public void translate(final float tx, final float ty) {
        this.m02 += tx * this.m00 + ty * this.m01;
        this.m12 += tx * this.m10 + ty * this.m11;
    }
    
    public void translate(final float x, final float y, final float z) {
        throw new IllegalArgumentException("Cannot use translate(x, y, z) on a PMatrix2D.");
    }
    
    public void rotate(final float angle) {
        final float s = this.sin(angle);
        final float c = this.cos(angle);
        float temp1 = this.m00;
        float temp2 = this.m01;
        this.m00 = c * temp1 + s * temp2;
        this.m01 = -s * temp1 + c * temp2;
        temp1 = this.m10;
        temp2 = this.m11;
        this.m10 = c * temp1 + s * temp2;
        this.m11 = -s * temp1 + c * temp2;
    }
    
    public void rotateX(final float angle) {
        throw new IllegalArgumentException("Cannot use rotateX() on a PMatrix2D.");
    }
    
    public void rotateY(final float angle) {
        throw new IllegalArgumentException("Cannot use rotateY() on a PMatrix2D.");
    }
    
    public void rotateZ(final float angle) {
        this.rotate(angle);
    }
    
    public void rotate(final float angle, final float v0, final float v1, final float v2) {
        throw new IllegalArgumentException("Cannot use this version of rotate() on a PMatrix2D.");
    }
    
    public void scale(final float s) {
        this.scale(s, s);
    }
    
    public void scale(final float sx, final float sy) {
        this.m00 *= sx;
        this.m01 *= sy;
        this.m10 *= sx;
        this.m11 *= sy;
    }
    
    public void scale(final float x, final float y, final float z) {
        throw new IllegalArgumentException("Cannot use this version of scale() on a PMatrix2D.");
    }
    
    public void skewX(final float angle) {
        this.apply(1.0f, 0.0f, 1.0f, this.tan(angle), 0.0f, 0.0f);
    }
    
    public void skewY(final float angle) {
        this.apply(1.0f, 0.0f, 1.0f, 0.0f, this.tan(angle), 0.0f);
    }
    
    public void apply(final PMatrix source) {
        if (source instanceof PMatrix2D) {
            this.apply((PMatrix2D)source);
        }
        else if (source instanceof PMatrix3D) {
            this.apply((PMatrix3D)source);
        }
    }
    
    public void apply(final PMatrix2D source) {
        this.apply(source.m00, source.m01, source.m02, source.m10, source.m11, source.m12);
    }
    
    public void apply(final PMatrix3D source) {
        throw new IllegalArgumentException("Cannot use apply(PMatrix3D) on a PMatrix2D.");
    }
    
    public void apply(final float n00, final float n01, final float n02, final float n10, final float n11, final float n12) {
        float t0 = this.m00;
        float t2 = this.m01;
        this.m00 = n00 * t0 + n10 * t2;
        this.m01 = n01 * t0 + n11 * t2;
        this.m02 += n02 * t0 + n12 * t2;
        t0 = this.m10;
        t2 = this.m11;
        this.m10 = n00 * t0 + n10 * t2;
        this.m11 = n01 * t0 + n11 * t2;
        this.m12 += n02 * t0 + n12 * t2;
    }
    
    public void apply(final float n00, final float n01, final float n02, final float n03, final float n10, final float n11, final float n12, final float n13, final float n20, final float n21, final float n22, final float n23, final float n30, final float n31, final float n32, final float n33) {
        throw new IllegalArgumentException("Cannot use this version of apply() on a PMatrix2D.");
    }
    
    public void preApply(final PMatrix2D left) {
        this.preApply(left.m00, left.m01, left.m02, left.m10, left.m11, left.m12);
    }
    
    public void preApply(final PMatrix3D left) {
        throw new IllegalArgumentException("Cannot use preApply(PMatrix3D) on a PMatrix2D.");
    }
    
    public void preApply(final float n00, final float n01, float n02, final float n10, final float n11, float n12) {
        float t0 = this.m02;
        float t2 = this.m12;
        n02 += t0 * n00 + t2 * n01;
        n12 += t0 * n10 + t2 * n11;
        this.m02 = n02;
        this.m12 = n12;
        t0 = this.m00;
        t2 = this.m10;
        this.m00 = t0 * n00 + t2 * n01;
        this.m10 = t0 * n10 + t2 * n11;
        t0 = this.m01;
        t2 = this.m11;
        this.m01 = t0 * n00 + t2 * n01;
        this.m11 = t0 * n10 + t2 * n11;
    }
    
    public void preApply(final float n00, final float n01, final float n02, final float n03, final float n10, final float n11, final float n12, final float n13, final float n20, final float n21, final float n22, final float n23, final float n30, final float n31, final float n32, final float n33) {
        throw new IllegalArgumentException("Cannot use this version of preApply() on a PMatrix2D.");
    }
    
    public PVector mult(final PVector source, PVector target) {
        if (target == null) {
            target = new PVector();
        }
        target.x = this.m00 * source.x + this.m01 * source.y + this.m02;
        target.y = this.m10 * source.x + this.m11 * source.y + this.m12;
        return target;
    }
    
    public float[] mult(final float[] vec, float[] out) {
        if (out == null || out.length != 2) {
            out = new float[2];
        }
        if (vec == out) {
            final float tx = this.m00 * vec[0] + this.m01 * vec[1] + this.m02;
            final float ty = this.m10 * vec[0] + this.m11 * vec[1] + this.m12;
            out[0] = tx;
            out[1] = ty;
        }
        else {
            out[0] = this.m00 * vec[0] + this.m01 * vec[1] + this.m02;
            out[1] = this.m10 * vec[0] + this.m11 * vec[1] + this.m12;
        }
        return out;
    }
    
    public float multX(final float x, final float y) {
        return this.m00 * x + this.m01 * y + this.m02;
    }
    
    public float multY(final float x, final float y) {
        return this.m10 * x + this.m11 * y + this.m12;
    }
    
    public void transpose() {
    }
    
    public boolean invert() {
        final float determinant = this.determinant();
        if (Math.abs(determinant) <= Float.MIN_VALUE) {
            return false;
        }
        final float t00 = this.m00;
        final float t2 = this.m01;
        final float t3 = this.m02;
        final float t4 = this.m10;
        final float t5 = this.m11;
        final float t6 = this.m12;
        this.m00 = t5 / determinant;
        this.m10 = -t4 / determinant;
        this.m01 = -t2 / determinant;
        this.m11 = t00 / determinant;
        this.m02 = (t2 * t6 - t5 * t3) / determinant;
        this.m12 = (t4 * t3 - t00 * t6) / determinant;
        return true;
    }
    
    public float determinant() {
        return this.m00 * this.m11 - this.m01 * this.m10;
    }
    
    public void print() {
        int big = (int)this.abs(this.max(PApplet.max(this.abs(this.m00), this.abs(this.m01), this.abs(this.m02)), PApplet.max(this.abs(this.m10), this.abs(this.m11), this.abs(this.m12))));
        int digits = 1;
        if (Float.isNaN(big) || Float.isInfinite(big)) {
            digits = 5;
        }
        else {
            while ((big /= 10) != 0) {
                ++digits;
            }
        }
        System.out.println(String.valueOf(PApplet.nfs(this.m00, digits, 4)) + " " + PApplet.nfs(this.m01, digits, 4) + " " + PApplet.nfs(this.m02, digits, 4));
        System.out.println(String.valueOf(PApplet.nfs(this.m10, digits, 4)) + " " + PApplet.nfs(this.m11, digits, 4) + " " + PApplet.nfs(this.m12, digits, 4));
        System.out.println();
    }
    
    protected boolean isIdentity() {
        return this.m00 == 1.0f && this.m01 == 0.0f && this.m02 == 0.0f && this.m10 == 0.0f && this.m11 == 1.0f && this.m12 == 0.0f;
    }
    
    protected boolean isWarped() {
        return this.m00 != 1.0f || (this.m01 != 0.0f && this.m10 != 0.0f) || this.m11 != 1.0f;
    }
    
    private final float max(final float a, final float b) {
        return (a > b) ? a : b;
    }
    
    private final float abs(final float a) {
        return (a < 0.0f) ? (-a) : a;
    }
    
    private final float sin(final float angle) {
        return (float)Math.sin(angle);
    }
    
    private final float cos(final float angle) {
        return (float)Math.cos(angle);
    }
    
    private final float tan(final float angle) {
        return (float)Math.tan(angle);
    }
}
