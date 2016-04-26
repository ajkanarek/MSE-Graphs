// 
// Decompiled by Procyon v0.5.30
// 

package thermodynamics;

public class FFT
{
    public static float[] fft(final float[] array) {
        if (array == null) {
            return null;
        }
        return four1(array, (array.length - 1) / 2, 1);
    }
    
    public static float[] four1(final float[] array, final int n, final int n2) {
        final int i = n << 1;
        int n3 = 1;
        for (int j = 1; j < i; j += 2) {
            if (n3 > j) {
                final float n4 = array[n3];
                array[n3] = array[j];
                array[j] = n4;
                final float n5 = array[n3 + 1];
                array[n3 + 1] = array[j + 1];
                array[j + 1] = n5;
            }
            int n6;
            for (n6 = i >> 1; n6 >= 2 && n3 > n6; n3 -= n6, n6 >>= 1) {}
            n3 += n6;
        }
        int n8;
        for (int n7 = 2; i > n7; n7 = n8) {
            n8 = n7 << 1;
            final float n9 = n2 * (6.2831855f / n7);
            final float n10 = (float)Math.sin(0.5 * n9);
            final float n11 = -2.0f * n10 * n10;
            final float n12 = (float)Math.sin(n9);
            float n13 = 1.0f;
            float n14 = 0.0f;
            for (int k = 1; k < n7; k += 2) {
                for (int l = k; l <= i; l += n8) {
                    final int n15 = l + n7;
                    final float n16 = n13 * array[n15] - n14 * array[n15 + 1];
                    final float n17 = n13 * array[n15 + 1] + n14 * array[n15];
                    array[n15] = array[l] - n16;
                    array[n15 + 1] = array[l + 1] - n17;
                    final int n18 = l;
                    array[n18] += n16;
                    final int n19 = l + 1;
                    array[n19] += n17;
                }
                final float n20;
                n13 += (n20 = n13) * n11 - n14 * n12;
                n14 += n14 * n11 + n20 * n12;
            }
        }
        return array;
    }
}
