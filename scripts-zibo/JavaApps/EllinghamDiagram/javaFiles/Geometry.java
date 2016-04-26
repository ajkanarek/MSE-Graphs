// 
// Decompiled by Procyon v0.5.30
// 

public class Geometry
{
    public static float[] intersection(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8) {
        final float n9 = n3 - n;
        final float n10 = n4 - n2;
        final float n11 = n7 - n5;
        final float n12 = n8 - n6;
        final float n13 = n5 - n3;
        final float n14 = n6 - n4;
        final float n15 = n7 - n3;
        final float n16 = n8 - n4;
        final float n17 = n - n7;
        final float n18 = n2 - n8;
        final float n19 = n3 - n7;
        final float n20 = n4 - n8;
        if (outer(n9, n10, n13, n14) * outer(n9, n10, n15, n16) >= 0.0f) {
            return null;
        }
        if (outer(n11, n12, n17, n18) * outer(n11, n12, n19, n20) >= 0.0f) {
            return null;
        }
        if ((n - n3) * (n6 - n8) - (n2 - n4) * (n5 - n7) == 0.0f) {
            return null;
        }
        return new float[] { ((n * n4 - n2 * n3) * (n5 - n7) - (n - n3) * (n5 * n8 - n6 * n7)) / ((n - n3) * (n6 - n8) - (n2 - n4) * (n5 - n7)), ((n * n4 - n2 * n3) * (n6 - n8) - (n2 - n4) * (n5 * n8 - n6 * n7)) / ((n - n3) * (n6 - n8) - (n2 - n4) * (n5 - n7)) };
    }
    
    public static float outer(final float n, final float n2, final float n3, final float n4) {
        return n * n4 - n3 * n2;
    }
    
    public static int Color(float n) {
        if (n < 0.0f) {
            n = 0.0f;
        }
        if (n > 1.0f) {
            n = 1.0f;
        }
        if (n < 0.2) {
            return 0xFF000000 | (int)(n * 5.0f * 255.0f);
        }
        if (n < 0.4) {
            return 0xFF0000FF | (int)((n - 0.2) * 5.0 * 255.0) << 8;
        }
        if (n < 0.6) {
            return 0xFF00FF00 | (int)((0.6 - n) * 5.0 * 255.0);
        }
        if (n < 0.8) {
            return 0xFF00FF00 | (int)((n - 0.6) * 5.0 * 255.0) << 16;
        }
        return 0xFFFF0000 | (int)((1.0 - n) * 5.0 * 255.0) << 8;
    }
}
