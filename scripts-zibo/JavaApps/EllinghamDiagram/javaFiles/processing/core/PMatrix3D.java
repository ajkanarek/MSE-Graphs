// 
// Decompiled by Procyon v0.5.30
// 

package processing.core;

public final class PMatrix3D implements PMatrix
{
    public float m00;
    public float m01;
    public float m02;
    public float m03;
    public float m10;
    public float m11;
    public float m12;
    public float m13;
    public float m20;
    public float m21;
    public float m22;
    public float m23;
    public float m30;
    public float m31;
    public float m32;
    public float m33;
    protected PMatrix3D inverseCopy;
    
    public PMatrix3D() {
        this.reset();
    }
    
    public PMatrix3D(final float m00, final float m01, final float m02, final float m10, final float m11, final float m12) {
        this.set(m00, m01, m02, 0.0f, m10, m11, m12, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
    }
    
    public PMatrix3D(final float m00, final float m01, final float m02, final float m03, final float m10, final float m11, final float m12, final float m13, final float m20, final float m21, final float m22, final float m23, final float m30, final float m31, final float m32, final float m33) {
        this.set(m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33);
    }
    
    public PMatrix3D(final PMatrix matrix) {
        this.set(matrix);
    }
    
    public void reset() {
        this.set(1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
    }
    
    public PMatrix3D get() {
        final PMatrix3D outgoing = new PMatrix3D();
        outgoing.set(this);
        return outgoing;
    }
    
    public float[] get(float[] target) {
        if (target == null || target.length != 16) {
            target = new float[16];
        }
        target[0] = this.m00;
        target[1] = this.m01;
        target[2] = this.m02;
        target[3] = this.m03;
        target[4] = this.m10;
        target[5] = this.m11;
        target[6] = this.m12;
        target[7] = this.m13;
        target[8] = this.m20;
        target[9] = this.m21;
        target[10] = this.m22;
        target[11] = this.m23;
        target[12] = this.m30;
        target[13] = this.m31;
        target[14] = this.m32;
        target[15] = this.m33;
        return target;
    }
    
    public void set(final PMatrix matrix) {
        if (matrix instanceof PMatrix3D) {
            final PMatrix3D src = (PMatrix3D)matrix;
            this.set(src.m00, src.m01, src.m02, src.m03, src.m10, src.m11, src.m12, src.m13, src.m20, src.m21, src.m22, src.m23, src.m30, src.m31, src.m32, src.m33);
        }
        else {
            final PMatrix2D src2 = (PMatrix2D)matrix;
            this.set(src2.m00, src2.m01, 0.0f, src2.m02, src2.m10, src2.m11, 0.0f, src2.m12, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
        }
    }
    
    public void set(final float[] source) {
        if (source.length == 6) {
            this.set(source[0], source[1], source[2], source[3], source[4], source[5]);
        }
        else if (source.length == 16) {
            this.m00 = source[0];
            this.m01 = source[1];
            this.m02 = source[2];
            this.m03 = source[3];
            this.m10 = source[4];
            this.m11 = source[5];
            this.m12 = source[6];
            this.m13 = source[7];
            this.m20 = source[8];
            this.m21 = source[9];
            this.m22 = source[10];
            this.m23 = source[11];
            this.m30 = source[12];
            this.m31 = source[13];
            this.m32 = source[14];
            this.m33 = source[15];
        }
    }
    
    public void set(final float m00, final float m01, final float m02, final float m10, final float m11, final float m12) {
        this.set(m00, m01, 0.0f, m02, m10, m11, 0.0f, m12, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
    }
    
    public void set(final float m00, final float m01, final float m02, final float m03, final float m10, final float m11, final float m12, final float m13, final float m20, final float m21, final float m22, final float m23, final float m30, final float m31, final float m32, final float m33) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m03 = m03;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m13 = m13;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
        this.m23 = m23;
        this.m30 = m30;
        this.m31 = m31;
        this.m32 = m32;
        this.m33 = m33;
    }
    
    public void translate(final float tx, final float ty) {
        this.translate(tx, ty, 0.0f);
    }
    
    public void translate(final float tx, final float ty, final float tz) {
        this.m03 += tx * this.m00 + ty * this.m01 + tz * this.m02;
        this.m13 += tx * this.m10 + ty * this.m11 + tz * this.m12;
        this.m23 += tx * this.m20 + ty * this.m21 + tz * this.m22;
        this.m33 += tx * this.m30 + ty * this.m31 + tz * this.m32;
    }
    
    public void rotate(final float angle) {
        this.rotateZ(angle);
    }
    
    public void rotateX(final float angle) {
        final float c = this.cos(angle);
        final float s = this.sin(angle);
        this.apply(1.0f, 0.0f, 0.0f, 0.0f, 0.0f, c, -s, 0.0f, 0.0f, s, c, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
    }
    
    public void rotateY(final float angle) {
        final float c = this.cos(angle);
        final float s = this.sin(angle);
        this.apply(c, 0.0f, s, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, -s, 0.0f, c, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
    }
    
    public void rotateZ(final float angle) {
        final float c = this.cos(angle);
        final float s = this.sin(angle);
        this.apply(c, -s, 0.0f, 0.0f, s, c, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
    }
    
    public void rotate(final float angle, final float v0, final float v1, final float v2) {
        final float c = this.cos(angle);
        final float s = this.sin(angle);
        final float t = 1.0f - c;
        this.apply(t * v0 * v0 + c, t * v0 * v1 - s * v2, t * v0 * v2 + s * v1, 0.0f, t * v0 * v1 + s * v2, t * v1 * v1 + c, t * v1 * v2 - s * v0, 0.0f, t * v0 * v2 - s * v1, t * v1 * v2 + s * v0, t * v2 * v2 + c, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
    }
    
    public void scale(final float s) {
        this.scale(s, s, s);
    }
    
    public void scale(final float sx, final float sy) {
        this.scale(sx, sy, 1.0f);
    }
    
    public void scale(final float x, final float y, final float z) {
        this.m00 *= x;
        this.m01 *= y;
        this.m02 *= z;
        this.m10 *= x;
        this.m11 *= y;
        this.m12 *= z;
        this.m20 *= x;
        this.m21 *= y;
        this.m22 *= z;
        this.m30 *= x;
        this.m31 *= y;
        this.m32 *= z;
    }
    
    public void skewX(final float angle) {
        final float t = (float)Math.tan(angle);
        this.apply(1.0f, t, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
    }
    
    public void skewY(final float angle) {
        final float t = (float)Math.tan(angle);
        this.apply(1.0f, 0.0f, 0.0f, 0.0f, t, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
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
        this.apply(source.m00, source.m01, 0.0f, source.m02, source.m10, source.m11, 0.0f, source.m12, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
    }
    
    public void apply(final PMatrix3D source) {
        this.apply(source.m00, source.m01, source.m02, source.m03, source.m10, source.m11, source.m12, source.m13, source.m20, source.m21, source.m22, source.m23, source.m30, source.m31, source.m32, source.m33);
    }
    
    public void apply(final float n00, final float n01, final float n02, final float n10, final float n11, final float n12) {
        this.apply(n00, n01, 0.0f, n02, n10, n11, 0.0f, n12, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
    }
    
    public void apply(final float n00, final float n01, final float n02, final float n03, final float n10, final float n11, final float n12, final float n13, final float n20, final float n21, final float n22, final float n23, final float n30, final float n31, final float n32, final float n33) {
        final float r00 = this.m00 * n00 + this.m01 * n10 + this.m02 * n20 + this.m03 * n30;
        final float r2 = this.m00 * n01 + this.m01 * n11 + this.m02 * n21 + this.m03 * n31;
        final float r3 = this.m00 * n02 + this.m01 * n12 + this.m02 * n22 + this.m03 * n32;
        final float r4 = this.m00 * n03 + this.m01 * n13 + this.m02 * n23 + this.m03 * n33;
        final float r5 = this.m10 * n00 + this.m11 * n10 + this.m12 * n20 + this.m13 * n30;
        final float r6 = this.m10 * n01 + this.m11 * n11 + this.m12 * n21 + this.m13 * n31;
        final float r7 = this.m10 * n02 + this.m11 * n12 + this.m12 * n22 + this.m13 * n32;
        final float r8 = this.m10 * n03 + this.m11 * n13 + this.m12 * n23 + this.m13 * n33;
        final float r9 = this.m20 * n00 + this.m21 * n10 + this.m22 * n20 + this.m23 * n30;
        final float r10 = this.m20 * n01 + this.m21 * n11 + this.m22 * n21 + this.m23 * n31;
        final float r11 = this.m20 * n02 + this.m21 * n12 + this.m22 * n22 + this.m23 * n32;
        final float r12 = this.m20 * n03 + this.m21 * n13 + this.m22 * n23 + this.m23 * n33;
        final float r13 = this.m30 * n00 + this.m31 * n10 + this.m32 * n20 + this.m33 * n30;
        final float r14 = this.m30 * n01 + this.m31 * n11 + this.m32 * n21 + this.m33 * n31;
        final float r15 = this.m30 * n02 + this.m31 * n12 + this.m32 * n22 + this.m33 * n32;
        final float r16 = this.m30 * n03 + this.m31 * n13 + this.m32 * n23 + this.m33 * n33;
        this.m00 = r00;
        this.m01 = r2;
        this.m02 = r3;
        this.m03 = r4;
        this.m10 = r5;
        this.m11 = r6;
        this.m12 = r7;
        this.m13 = r8;
        this.m20 = r9;
        this.m21 = r10;
        this.m22 = r11;
        this.m23 = r12;
        this.m30 = r13;
        this.m31 = r14;
        this.m32 = r15;
        this.m33 = r16;
    }
    
    public void preApply(final PMatrix2D left) {
        this.preApply(left.m00, left.m01, 0.0f, left.m02, left.m10, left.m11, 0.0f, left.m12, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
    }
    
    public void preApply(final PMatrix3D left) {
        this.preApply(left.m00, left.m01, left.m02, left.m03, left.m10, left.m11, left.m12, left.m13, left.m20, left.m21, left.m22, left.m23, left.m30, left.m31, left.m32, left.m33);
    }
    
    public void preApply(final float n00, final float n01, final float n02, final float n10, final float n11, final float n12) {
        this.preApply(n00, n01, 0.0f, n02, n10, n11, 0.0f, n12, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
    }
    
    public void preApply(final float n00, final float n01, final float n02, final float n03, final float n10, final float n11, final float n12, final float n13, final float n20, final float n21, final float n22, final float n23, final float n30, final float n31, final float n32, final float n33) {
        final float r00 = n00 * this.m00 + n01 * this.m10 + n02 * this.m20 + n03 * this.m30;
        final float r2 = n00 * this.m01 + n01 * this.m11 + n02 * this.m21 + n03 * this.m31;
        final float r3 = n00 * this.m02 + n01 * this.m12 + n02 * this.m22 + n03 * this.m32;
        final float r4 = n00 * this.m03 + n01 * this.m13 + n02 * this.m23 + n03 * this.m33;
        final float r5 = n10 * this.m00 + n11 * this.m10 + n12 * this.m20 + n13 * this.m30;
        final float r6 = n10 * this.m01 + n11 * this.m11 + n12 * this.m21 + n13 * this.m31;
        final float r7 = n10 * this.m02 + n11 * this.m12 + n12 * this.m22 + n13 * this.m32;
        final float r8 = n10 * this.m03 + n11 * this.m13 + n12 * this.m23 + n13 * this.m33;
        final float r9 = n20 * this.m00 + n21 * this.m10 + n22 * this.m20 + n23 * this.m30;
        final float r10 = n20 * this.m01 + n21 * this.m11 + n22 * this.m21 + n23 * this.m31;
        final float r11 = n20 * this.m02 + n21 * this.m12 + n22 * this.m22 + n23 * this.m32;
        final float r12 = n20 * this.m03 + n21 * this.m13 + n22 * this.m23 + n23 * this.m33;
        final float r13 = n30 * this.m00 + n31 * this.m10 + n32 * this.m20 + n33 * this.m30;
        final float r14 = n30 * this.m01 + n31 * this.m11 + n32 * this.m21 + n33 * this.m31;
        final float r15 = n30 * this.m02 + n31 * this.m12 + n32 * this.m22 + n33 * this.m32;
        final float r16 = n30 * this.m03 + n31 * this.m13 + n32 * this.m23 + n33 * this.m33;
        this.m00 = r00;
        this.m01 = r2;
        this.m02 = r3;
        this.m03 = r4;
        this.m10 = r5;
        this.m11 = r6;
        this.m12 = r7;
        this.m13 = r8;
        this.m20 = r9;
        this.m21 = r10;
        this.m22 = r11;
        this.m23 = r12;
        this.m30 = r13;
        this.m31 = r14;
        this.m32 = r15;
        this.m33 = r16;
    }
    
    public PVector mult(final PVector source, PVector target) {
        if (target == null) {
            target = new PVector();
        }
        target.x = this.m00 * source.x + this.m01 * source.y + this.m02 * source.z + this.m03;
        target.y = this.m10 * source.x + this.m11 * source.y + this.m12 * source.z + this.m13;
        target.z = this.m20 * source.x + this.m21 * source.y + this.m22 * source.z + this.m23;
        return target;
    }
    
    public float[] mult(final float[] source, float[] target) {
        if (target == null || target.length < 3) {
            target = new float[3];
        }
        if (source == target) {
            throw new RuntimeException("The source and target vectors used in PMatrix3D.mult() cannot be identical.");
        }
        if (target.length == 3) {
            target[0] = this.m00 * source[0] + this.m01 * source[1] + this.m02 * source[2] + this.m03;
            target[1] = this.m10 * source[0] + this.m11 * source[1] + this.m12 * source[2] + this.m13;
            target[2] = this.m20 * source[0] + this.m21 * source[1] + this.m22 * source[2] + this.m23;
        }
        else if (target.length > 3) {
            target[0] = this.m00 * source[0] + this.m01 * source[1] + this.m02 * source[2] + this.m03 * source[3];
            target[1] = this.m10 * source[0] + this.m11 * source[1] + this.m12 * source[2] + this.m13 * source[3];
            target[2] = this.m20 * source[0] + this.m21 * source[1] + this.m22 * source[2] + this.m23 * source[3];
            target[3] = this.m30 * source[0] + this.m31 * source[1] + this.m32 * source[2] + this.m33 * source[3];
        }
        return target;
    }
    
    public float multX(final float x, final float y) {
        return this.m00 * x + this.m01 * y + this.m03;
    }
    
    public float multY(final float x, final float y) {
        return this.m10 * x + this.m11 * y + this.m13;
    }
    
    public float multX(final float x, final float y, final float z) {
        return this.m00 * x + this.m01 * y + this.m02 * z + this.m03;
    }
    
    public float multY(final float x, final float y, final float z) {
        return this.m10 * x + this.m11 * y + this.m12 * z + this.m13;
    }
    
    public float multZ(final float x, final float y, final float z) {
        return this.m20 * x + this.m21 * y + this.m22 * z + this.m23;
    }
    
    public float multW(final float x, final float y, final float z) {
        return this.m30 * x + this.m31 * y + this.m32 * z + this.m33;
    }
    
    public float multX(final float x, final float y, final float z, final float w) {
        return this.m00 * x + this.m01 * y + this.m02 * z + this.m03 * w;
    }
    
    public float multY(final float x, final float y, final float z, final float w) {
        return this.m10 * x + this.m11 * y + this.m12 * z + this.m13 * w;
    }
    
    public float multZ(final float x, final float y, final float z, final float w) {
        return this.m20 * x + this.m21 * y + this.m22 * z + this.m23 * w;
    }
    
    public float multW(final float x, final float y, final float z, final float w) {
        return this.m30 * x + this.m31 * y + this.m32 * z + this.m33 * w;
    }
    
    public void transpose() {
        float temp = this.m01;
        this.m01 = this.m10;
        this.m10 = temp;
        temp = this.m02;
        this.m02 = this.m20;
        this.m20 = temp;
        temp = this.m03;
        this.m03 = this.m30;
        this.m30 = temp;
        temp = this.m12;
        this.m12 = this.m21;
        this.m21 = temp;
        temp = this.m13;
        this.m13 = this.m31;
        this.m31 = temp;
        temp = this.m23;
        this.m23 = this.m32;
        this.m32 = temp;
    }
    
    public boolean invert() {
        final float determinant = this.determinant();
        if (determinant == 0.0f) {
            return false;
        }
        final float t00 = this.determinant3x3(this.m11, this.m12, this.m13, this.m21, this.m22, this.m23, this.m31, this.m32, this.m33);
        final float t2 = -this.determinant3x3(this.m10, this.m12, this.m13, this.m20, this.m22, this.m23, this.m30, this.m32, this.m33);
        final float t3 = this.determinant3x3(this.m10, this.m11, this.m13, this.m20, this.m21, this.m23, this.m30, this.m31, this.m33);
        final float t4 = -this.determinant3x3(this.m10, this.m11, this.m12, this.m20, this.m21, this.m22, this.m30, this.m31, this.m32);
        final float t5 = -this.determinant3x3(this.m01, this.m02, this.m03, this.m21, this.m22, this.m23, this.m31, this.m32, this.m33);
        final float t6 = this.determinant3x3(this.m00, this.m02, this.m03, this.m20, this.m22, this.m23, this.m30, this.m32, this.m33);
        final float t7 = -this.determinant3x3(this.m00, this.m01, this.m03, this.m20, this.m21, this.m23, this.m30, this.m31, this.m33);
        final float t8 = this.determinant3x3(this.m00, this.m01, this.m02, this.m20, this.m21, this.m22, this.m30, this.m31, this.m32);
        final float t9 = this.determinant3x3(this.m01, this.m02, this.m03, this.m11, this.m12, this.m13, this.m31, this.m32, this.m33);
        final float t10 = -this.determinant3x3(this.m00, this.m02, this.m03, this.m10, this.m12, this.m13, this.m30, this.m32, this.m33);
        final float t11 = this.determinant3x3(this.m00, this.m01, this.m03, this.m10, this.m11, this.m13, this.m30, this.m31, this.m33);
        final float t12 = -this.determinant3x3(this.m00, this.m01, this.m02, this.m10, this.m11, this.m12, this.m30, this.m31, this.m32);
        final float t13 = -this.determinant3x3(this.m01, this.m02, this.m03, this.m11, this.m12, this.m13, this.m21, this.m22, this.m23);
        final float t14 = this.determinant3x3(this.m00, this.m02, this.m03, this.m10, this.m12, this.m13, this.m20, this.m22, this.m23);
        final float t15 = -this.determinant3x3(this.m00, this.m01, this.m03, this.m10, this.m11, this.m13, this.m20, this.m21, this.m23);
        final float t16 = this.determinant3x3(this.m00, this.m01, this.m02, this.m10, this.m11, this.m12, this.m20, this.m21, this.m22);
        this.m00 = t00 / determinant;
        this.m01 = t5 / determinant;
        this.m02 = t9 / determinant;
        this.m03 = t13 / determinant;
        this.m10 = t2 / determinant;
        this.m11 = t6 / determinant;
        this.m12 = t10 / determinant;
        this.m13 = t14 / determinant;
        this.m20 = t3 / determinant;
        this.m21 = t7 / determinant;
        this.m22 = t11 / determinant;
        this.m23 = t15 / determinant;
        this.m30 = t4 / determinant;
        this.m31 = t8 / determinant;
        this.m32 = t12 / determinant;
        this.m33 = t16 / determinant;
        return true;
    }
    
    private float determinant3x3(final float t00, final float t01, final float t02, final float t10, final float t11, final float t12, final float t20, final float t21, final float t22) {
        return t00 * (t11 * t22 - t12 * t21) + t01 * (t12 * t20 - t10 * t22) + t02 * (t10 * t21 - t11 * t20);
    }
    
    public float determinant() {
        float f = this.m00 * (this.m11 * this.m22 * this.m33 + this.m12 * this.m23 * this.m31 + this.m13 * this.m21 * this.m32 - this.m13 * this.m22 * this.m31 - this.m11 * this.m23 * this.m32 - this.m12 * this.m21 * this.m33);
        f -= this.m01 * (this.m10 * this.m22 * this.m33 + this.m12 * this.m23 * this.m30 + this.m13 * this.m20 * this.m32 - this.m13 * this.m22 * this.m30 - this.m10 * this.m23 * this.m32 - this.m12 * this.m20 * this.m33);
        f += this.m02 * (this.m10 * this.m21 * this.m33 + this.m11 * this.m23 * this.m30 + this.m13 * this.m20 * this.m31 - this.m13 * this.m21 * this.m30 - this.m10 * this.m23 * this.m31 - this.m11 * this.m20 * this.m33);
        f -= this.m03 * (this.m10 * this.m21 * this.m32 + this.m11 * this.m22 * this.m30 + this.m12 * this.m20 * this.m31 - this.m12 * this.m21 * this.m30 - this.m10 * this.m22 * this.m31 - this.m11 * this.m20 * this.m32);
        return f;
    }
    
    protected void invTranslate(final float tx, final float ty, final float tz) {
        this.preApply(1.0f, 0.0f, 0.0f, -tx, 0.0f, 1.0f, 0.0f, -ty, 0.0f, 0.0f, 1.0f, -tz, 0.0f, 0.0f, 0.0f, 1.0f);
    }
    
    protected void invRotateX(final float angle) {
        final float c = this.cos(-angle);
        final float s = this.sin(-angle);
        this.preApply(1.0f, 0.0f, 0.0f, 0.0f, 0.0f, c, -s, 0.0f, 0.0f, s, c, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
    }
    
    protected void invRotateY(final float angle) {
        final float c = this.cos(-angle);
        final float s = this.sin(-angle);
        this.preApply(c, 0.0f, s, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, -s, 0.0f, c, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
    }
    
    protected void invRotateZ(final float angle) {
        final float c = this.cos(-angle);
        final float s = this.sin(-angle);
        this.preApply(c, -s, 0.0f, 0.0f, s, c, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
    }
    
    protected void invRotate(final float angle, final float v0, final float v1, final float v2) {
        final float c = this.cos(-angle);
        final float s = this.sin(-angle);
        final float t = 1.0f - c;
        this.preApply(t * v0 * v0 + c, t * v0 * v1 - s * v2, t * v0 * v2 + s * v1, 0.0f, t * v0 * v1 + s * v2, t * v1 * v1 + c, t * v1 * v2 - s * v0, 0.0f, t * v0 * v2 - s * v1, t * v1 * v2 + s * v0, t * v2 * v2 + c, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
    }
    
    protected void invScale(final float x, final float y, final float z) {
        this.preApply(1.0f / x, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f / y, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f / z, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
    }
    
    protected boolean invApply(final float n00, final float n01, final float n02, final float n03, final float n10, final float n11, final float n12, final float n13, final float n20, final float n21, final float n22, final float n23, final float n30, final float n31, final float n32, final float n33) {
        if (this.inverseCopy == null) {
            this.inverseCopy = new PMatrix3D();
        }
        this.inverseCopy.set(n00, n01, n02, n03, n10, n11, n12, n13, n20, n21, n22, n23, n30, n31, n32, n33);
        if (!this.inverseCopy.invert()) {
            return false;
        }
        this.preApply(this.inverseCopy);
        return true;
    }
    
    public void print() {
        int big = (int)Math.abs(this.max(this.max(this.max(this.max(this.abs(this.m00), this.abs(this.m01)), this.max(this.abs(this.m02), this.abs(this.m03))), this.max(this.max(this.abs(this.m10), this.abs(this.m11)), this.max(this.abs(this.m12), this.abs(this.m13)))), this.max(this.max(this.max(this.abs(this.m20), this.abs(this.m21)), this.max(this.abs(this.m22), this.abs(this.m23))), this.max(this.max(this.abs(this.m30), this.abs(this.m31)), this.max(this.abs(this.m32), this.abs(this.m33))))));
        int digits = 1;
        if (Float.isNaN(big) || Float.isInfinite(big)) {
            digits = 5;
        }
        else {
            while ((big /= 10) != 0) {
                ++digits;
            }
        }
        System.out.println(String.valueOf(PApplet.nfs(this.m00, digits, 4)) + " " + PApplet.nfs(this.m01, digits, 4) + " " + PApplet.nfs(this.m02, digits, 4) + " " + PApplet.nfs(this.m03, digits, 4));
        System.out.println(String.valueOf(PApplet.nfs(this.m10, digits, 4)) + " " + PApplet.nfs(this.m11, digits, 4) + " " + PApplet.nfs(this.m12, digits, 4) + " " + PApplet.nfs(this.m13, digits, 4));
        System.out.println(String.valueOf(PApplet.nfs(this.m20, digits, 4)) + " " + PApplet.nfs(this.m21, digits, 4) + " " + PApplet.nfs(this.m22, digits, 4) + " " + PApplet.nfs(this.m23, digits, 4));
        System.out.println(String.valueOf(PApplet.nfs(this.m30, digits, 4)) + " " + PApplet.nfs(this.m31, digits, 4) + " " + PApplet.nfs(this.m32, digits, 4) + " " + PApplet.nfs(this.m33, digits, 4));
        System.out.println();
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
}
