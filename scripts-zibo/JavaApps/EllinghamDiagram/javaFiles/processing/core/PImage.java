// 
// Decompiled by Procyon v0.5.30
// 

package processing.core;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.awt.image.RenderedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.awt.image.WritableRaster;
import java.awt.image.PixelGrabber;
import java.awt.image.ImageObserver;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.util.HashMap;

public class PImage implements PConstants, Cloneable
{
    public int format;
    public int[] pixels;
    public int width;
    public int height;
    public PApplet parent;
    protected HashMap<Object, Object> cacheMap;
    protected boolean modified;
    protected int mx1;
    protected int my1;
    protected int mx2;
    protected int my2;
    private int fracU;
    private int ifU;
    private int fracV;
    private int ifV;
    private int u1;
    private int u2;
    private int v1;
    private int v2;
    private int sX;
    private int sY;
    private int iw;
    private int iw1;
    private int ih1;
    private int ul;
    private int ll;
    private int ur;
    private int lr;
    private int cUL;
    private int cLL;
    private int cUR;
    private int cLR;
    private int srcXOffset;
    private int srcYOffset;
    private int r;
    private int g;
    private int b;
    private int a;
    private int[] srcBuffer;
    static final int PRECISIONB = 15;
    static final int PRECISIONF = 32768;
    static final int PREC_MAXVAL = 32767;
    static final int PREC_ALPHA_SHIFT = 9;
    static final int PREC_RED_SHIFT = 1;
    private int blurRadius;
    private int blurKernelSize;
    private int[] blurKernel;
    private int[][] blurMult;
    static byte[] TIFF_HEADER;
    static final String TIFF_ERROR = "Error: Processing can only read its own TIFF files.";
    protected String[] saveImageFormats;
    
    static {
        PImage.TIFF_HEADER = new byte[] { 77, 77, 0, 42, 0, 0, 0, 8, 0, 9, 0, -2, 0, 4, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 3, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 3, 0, 0, 0, 1, 0, 0, 0, 0, 1, 2, 0, 3, 0, 0, 0, 3, 0, 0, 0, 122, 1, 6, 0, 3, 0, 0, 0, 1, 0, 2, 0, 0, 1, 17, 0, 4, 0, 0, 0, 1, 0, 0, 3, 0, 1, 21, 0, 3, 0, 0, 0, 1, 0, 3, 0, 0, 1, 22, 0, 3, 0, 0, 0, 1, 0, 0, 0, 0, 1, 23, 0, 4, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 8, 0, 8 };
    }
    
    public PImage() {
        this.format = 2;
    }
    
    public PImage(final int width, final int height) {
        this.init(width, height, 1);
    }
    
    public PImage(final int width, final int height, final int format) {
        this.init(width, height, format);
    }
    
    public void init(final int width, final int height, final int format) {
        this.width = width;
        this.height = height;
        this.pixels = new int[width * height];
        this.format = format;
    }
    
    protected void checkAlpha() {
        if (this.pixels == null) {
            return;
        }
        for (int i = 0; i < this.pixels.length; ++i) {
            if ((this.pixels[i] & 0xFF000000) != 0xFF000000) {
                this.format = 2;
                break;
            }
        }
    }
    
    public PImage(final Image img) {
        this.format = 1;
        if (img instanceof BufferedImage) {
            final BufferedImage bi = (BufferedImage)img;
            this.width = bi.getWidth();
            this.height = bi.getHeight();
            this.pixels = new int[this.width * this.height];
            final WritableRaster raster = bi.getRaster();
            raster.getDataElements(0, 0, this.width, this.height, this.pixels);
            if (bi.getType() == 2) {
                this.format = 2;
            }
        }
        else {
            this.width = img.getWidth(null);
            this.height = img.getHeight(null);
            this.pixels = new int[this.width * this.height];
            final PixelGrabber pg = new PixelGrabber(img, 0, 0, this.width, this.height, this.pixels, 0, this.width);
            try {
                pg.grabPixels();
            }
            catch (InterruptedException ex) {}
        }
    }
    
    public Image getImage() {
        this.loadPixels();
        final int type = (this.format == 1) ? 1 : 2;
        final BufferedImage image = new BufferedImage(this.width, this.height, type);
        final WritableRaster wr = image.getRaster();
        wr.setDataElements(0, 0, this.width, this.height, this.pixels);
        return image;
    }
    
    public void setCache(final Object parent, final Object storage) {
        if (this.cacheMap == null) {
            this.cacheMap = new HashMap<Object, Object>();
        }
        this.cacheMap.put(parent, storage);
    }
    
    public Object getCache(final Object parent) {
        if (this.cacheMap == null) {
            return null;
        }
        return this.cacheMap.get(parent);
    }
    
    public void removeCache(final Object parent) {
        if (this.cacheMap != null) {
            this.cacheMap.remove(parent);
        }
    }
    
    public boolean isModified() {
        return this.modified;
    }
    
    public void setModified() {
        this.modified = true;
    }
    
    public void setModified(final boolean m) {
        this.modified = m;
    }
    
    public void loadPixels() {
    }
    
    public void updatePixels() {
        this.updatePixelsImpl(0, 0, this.width, this.height);
    }
    
    public void updatePixels(final int x, final int y, final int w, final int h) {
        this.updatePixelsImpl(x, y, w, h);
    }
    
    protected void updatePixelsImpl(final int x, final int y, final int w, final int h) {
        final int x2 = x + w;
        final int y2 = y + h;
        if (!this.modified) {
            this.mx1 = x;
            this.mx2 = x2;
            this.my1 = y;
            this.my2 = y2;
            this.modified = true;
        }
        else {
            if (x < this.mx1) {
                this.mx1 = x;
            }
            if (x > this.mx2) {
                this.mx2 = x;
            }
            if (y < this.my1) {
                this.my1 = y;
            }
            if (y > this.my2) {
                this.my2 = y;
            }
            if (x2 < this.mx1) {
                this.mx1 = x2;
            }
            if (x2 > this.mx2) {
                this.mx2 = x2;
            }
            if (y2 < this.my1) {
                this.my1 = y2;
            }
            if (y2 > this.my2) {
                this.my2 = y2;
            }
        }
    }
    
    public Object clone() throws CloneNotSupportedException {
        final PImage c = (PImage)super.clone();
        c.pixels = new int[this.width * this.height];
        System.arraycopy(this.pixels, 0, c.pixels, 0, this.pixels.length);
        return c;
    }
    
    public void resize(int wide, int high) {
        this.loadPixels();
        if (wide <= 0 && high <= 0) {
            this.width = 0;
            this.height = 0;
            this.pixels = new int[0];
        }
        else {
            if (wide == 0) {
                final float diff = high / this.height;
                wide = (int)(this.width * diff);
            }
            else if (high == 0) {
                final float diff = wide / this.width;
                high = (int)(this.height * diff);
            }
            final PImage temp = new PImage(wide, high, this.format);
            temp.copy(this, 0, 0, this.width, this.height, 0, 0, wide, high);
            this.width = wide;
            this.height = high;
            this.pixels = temp.pixels;
        }
        this.updatePixels();
    }
    
    public int get(final int x, final int y) {
        if (x < 0 || y < 0 || x >= this.width || y >= this.height) {
            return 0;
        }
        switch (this.format) {
            case 1: {
                return this.pixels[y * this.width + x] | 0xFF000000;
            }
            case 2: {
                return this.pixels[y * this.width + x];
            }
            case 4: {
                return this.pixels[y * this.width + x] << 24 | 0xFFFFFF;
            }
            default: {
                return 0;
            }
        }
    }
    
    public PImage get(int x, int y, int w, int h) {
        if (x < 0) {
            w += x;
            x = 0;
        }
        if (y < 0) {
            h += y;
            y = 0;
        }
        if (x + w > this.width) {
            w = this.width - x;
        }
        if (y + h > this.height) {
            h = this.height - y;
        }
        return this.getImpl(x, y, w, h);
    }
    
    protected PImage getImpl(final int x, final int y, final int w, final int h) {
        final PImage newbie = new PImage(w, h, this.format);
        newbie.parent = this.parent;
        int index = y * this.width + x;
        int index2 = 0;
        for (int row = y; row < y + h; ++row) {
            System.arraycopy(this.pixels, index, newbie.pixels, index2, w);
            index += this.width;
            index2 += w;
        }
        return newbie;
    }
    
    public PImage get() {
        try {
            final PImage clone = (PImage)this.clone();
            clone.cacheMap = null;
            return clone;
        }
        catch (CloneNotSupportedException e) {
            return null;
        }
    }
    
    public void set(final int x, final int y, final int c) {
        if (x < 0 || y < 0 || x >= this.width || y >= this.height) {
            return;
        }
        this.pixels[y * this.width + x] = c;
        this.updatePixelsImpl(x, y, x + 1, y + 1);
    }
    
    public void set(int x, int y, final PImage src) {
        int sx = 0;
        int sy = 0;
        int sw = src.width;
        int sh = src.height;
        if (x < 0) {
            sx -= x;
            sw += x;
            x = 0;
        }
        if (y < 0) {
            sy -= y;
            sh += y;
            y = 0;
        }
        if (x + sw > this.width) {
            sw = this.width - x;
        }
        if (y + sh > this.height) {
            sh = this.height - y;
        }
        if (sw <= 0 || sh <= 0) {
            return;
        }
        this.setImpl(x, y, sx, sy, sw, sh, src);
    }
    
    protected void setImpl(final int dx, final int dy, final int sx, final int sy, final int sw, final int sh, final PImage src) {
        int srcOffset = sy * src.width + sx;
        int dstOffset = dy * this.width + dx;
        for (int y = sy; y < sy + sh; ++y) {
            System.arraycopy(src.pixels, srcOffset, this.pixels, dstOffset, sw);
            srcOffset += src.width;
            dstOffset += this.width;
        }
        this.updatePixelsImpl(sx, sy, sx + sw, sy + sh);
    }
    
    public void mask(final int[] maskArray) {
        this.loadPixels();
        if (maskArray.length != this.pixels.length) {
            throw new RuntimeException("The PImage used with mask() must be the same size as the applet.");
        }
        for (int i = 0; i < this.pixels.length; ++i) {
            this.pixels[i] = ((maskArray[i] & 0xFF) << 24 | (this.pixels[i] & 0xFFFFFF));
        }
        this.format = 2;
        this.updatePixels();
    }
    
    public void mask(final PImage maskImg) {
        maskImg.loadPixels();
        this.mask(maskImg.pixels);
    }
    
    public void filter(final int kind) {
        this.loadPixels();
        switch (kind) {
            case 11: {
                this.filter(11, 1.0f);
                break;
            }
            case 12: {
                if (this.format == 4) {
                    for (int i = 0; i < this.pixels.length; ++i) {
                        final int col = 255 - this.pixels[i];
                        this.pixels[i] = (0xFF000000 | col << 16 | col << 8 | col);
                    }
                    this.format = 1;
                    break;
                }
                for (int i = 0; i < this.pixels.length; ++i) {
                    final int col = this.pixels[i];
                    final int lum = 77 * (col >> 16 & 0xFF) + 151 * (col >> 8 & 0xFF) + 28 * (col & 0xFF) >> 8;
                    this.pixels[i] = ((col & 0xFF000000) | lum << 16 | lum << 8 | lum);
                }
                break;
            }
            case 13: {
                for (int i = 0; i < this.pixels.length; ++i) {
                    final int[] pixels = this.pixels;
                    final int n = i;
                    pixels[n] ^= 0xFFFFFF;
                }
                break;
            }
            case 15: {
                throw new RuntimeException("Use filter(POSTERIZE, int levels) instead of filter(POSTERIZE)");
            }
            case 14: {
                for (int i = 0; i < this.pixels.length; ++i) {
                    final int[] pixels2 = this.pixels;
                    final int n2 = i;
                    pixels2[n2] |= 0xFF000000;
                }
                this.format = 1;
                break;
            }
            case 16: {
                this.filter(16, 0.5f);
                break;
            }
            case 17: {
                this.dilate(true);
                break;
            }
            case 18: {
                this.dilate(false);
                break;
            }
        }
        this.updatePixels();
    }
    
    public void filter(final int kind, final float param) {
        this.loadPixels();
        switch (kind) {
            case 11: {
                if (this.format == 4) {
                    this.blurAlpha(param);
                    break;
                }
                if (this.format == 2) {
                    this.blurARGB(param);
                    break;
                }
                this.blurRGB(param);
                break;
            }
            case 12: {
                throw new RuntimeException("Use filter(GRAY) instead of filter(GRAY, param)");
            }
            case 13: {
                throw new RuntimeException("Use filter(INVERT) instead of filter(INVERT, param)");
            }
            case 14: {
                throw new RuntimeException("Use filter(OPAQUE) instead of filter(OPAQUE, param)");
            }
            case 15: {
                final int levels = (int)param;
                if (levels < 2 || levels > 255) {
                    throw new RuntimeException("Levels must be between 2 and 255 for filter(POSTERIZE, levels)");
                }
                final int levels2 = levels - 1;
                for (int i = 0; i < this.pixels.length; ++i) {
                    int rlevel = this.pixels[i] >> 16 & 0xFF;
                    int glevel = this.pixels[i] >> 8 & 0xFF;
                    int blevel = this.pixels[i] & 0xFF;
                    rlevel = (rlevel * levels >> 8) * 255 / levels2;
                    glevel = (glevel * levels >> 8) * 255 / levels2;
                    blevel = (blevel * levels >> 8) * 255 / levels2;
                    this.pixels[i] = ((0xFF000000 & this.pixels[i]) | rlevel << 16 | glevel << 8 | blevel);
                }
                break;
            }
            case 16: {
                final int thresh = (int)(param * 255.0f);
                for (int j = 0; j < this.pixels.length; ++j) {
                    final int max = Math.max((this.pixels[j] & 0xFF0000) >> 16, Math.max((this.pixels[j] & 0xFF00) >> 8, this.pixels[j] & 0xFF));
                    this.pixels[j] = ((this.pixels[j] & 0xFF000000) | ((max < thresh) ? 0 : 16777215));
                }
                break;
            }
            case 17: {
                throw new RuntimeException("Use filter(ERODE) instead of filter(ERODE, param)");
            }
            case 18: {
                throw new RuntimeException("Use filter(DILATE) instead of filter(DILATE, param)");
            }
        }
        this.updatePixels();
    }
    
    protected void buildBlurKernel(final float r) {
        int radius = (int)(r * 3.5f);
        radius = ((radius < 1) ? 1 : ((radius < 248) ? radius : 248));
        if (this.blurRadius != radius) {
            this.blurRadius = radius;
            this.blurKernelSize = 1 + this.blurRadius << 1;
            this.blurKernel = new int[this.blurKernelSize];
            this.blurMult = new int[this.blurKernelSize][256];
            int i = 1;
            int radiusi = radius - 1;
            while (i < radius) {
                final int bki;
                this.blurKernel[radius + i] = (this.blurKernel[radiusi] = (bki = radiusi * radiusi));
                final int[] bm = this.blurMult[radius + i];
                final int[] bmi = this.blurMult[radiusi--];
                for (int j = 0; j < 256; ++j) {
                    bm[j] = (bmi[j] = bki * j);
                }
                ++i;
            }
            final int[] blurKernel = this.blurKernel;
            final int n = radius;
            final int n2 = radius * radius;
            blurKernel[n] = n2;
            final int bk = n2;
            final int[] bm = this.blurMult[radius];
            for (int k = 0; k < 256; ++k) {
                bm[k] = bk * k;
            }
        }
    }
    
    protected void blurAlpha(final float r) {
        final int[] b2 = new int[this.pixels.length];
        int yi = 0;
        this.buildBlurKernel(r);
        for (int y = 0; y < this.height; ++y) {
            for (int x = 0; x < this.width; ++x) {
                int cb;
                int sum = cb = 0;
                int read = x - this.blurRadius;
                int bk0;
                if (read < 0) {
                    bk0 = -read;
                    read = 0;
                }
                else {
                    if (read >= this.width) {
                        break;
                    }
                    bk0 = 0;
                }
                for (int i = bk0; i < this.blurKernelSize && read < this.width; ++read, ++i) {
                    final int c = this.pixels[read + yi];
                    final int[] bm = this.blurMult[i];
                    cb += bm[c & 0xFF];
                    sum += this.blurKernel[i];
                }
                final int ri = yi + x;
                b2[ri] = cb / sum;
            }
            yi += this.width;
        }
        yi = 0;
        int ym = -this.blurRadius;
        int ymi = ym * this.width;
        for (int y = 0; y < this.height; ++y) {
            for (int x = 0; x < this.width; ++x) {
                int cb;
                int sum = cb = 0;
                int read;
                int bk0;
                int ri;
                if (ym < 0) {
                    ri = (bk0 = -ym);
                    read = x;
                }
                else {
                    if (ym >= this.height) {
                        break;
                    }
                    bk0 = 0;
                    ri = ym;
                    read = x + ymi;
                }
                for (int i = bk0; i < this.blurKernelSize && ri < this.height; ++ri, read += this.width, ++i) {
                    final int[] bm2 = this.blurMult[i];
                    cb += bm2[b2[read]];
                    sum += this.blurKernel[i];
                }
                this.pixels[x + yi] = cb / sum;
            }
            yi += this.width;
            ymi += this.width;
            ++ym;
        }
    }
    
    protected void blurRGB(final float r) {
        final int[] r2 = new int[this.pixels.length];
        final int[] g2 = new int[this.pixels.length];
        final int[] b2 = new int[this.pixels.length];
        int yi = 0;
        this.buildBlurKernel(r);
        for (int y = 0; y < this.height; ++y) {
            for (int x = 0; x < this.width; ++x) {
                int sum;
                int cr;
                int cb;
                int cg = cb = (cr = (sum = 0));
                int read = x - this.blurRadius;
                int bk0;
                if (read < 0) {
                    bk0 = -read;
                    read = 0;
                }
                else {
                    if (read >= this.width) {
                        break;
                    }
                    bk0 = 0;
                }
                for (int i = bk0; i < this.blurKernelSize && read < this.width; ++read, ++i) {
                    final int c = this.pixels[read + yi];
                    final int[] bm = this.blurMult[i];
                    cr += bm[(c & 0xFF0000) >> 16];
                    cg += bm[(c & 0xFF00) >> 8];
                    cb += bm[c & 0xFF];
                    sum += this.blurKernel[i];
                }
                final int ri = yi + x;
                r2[ri] = cr / sum;
                g2[ri] = cg / sum;
                b2[ri] = cb / sum;
            }
            yi += this.width;
        }
        yi = 0;
        int ym = -this.blurRadius;
        int ymi = ym * this.width;
        for (int y = 0; y < this.height; ++y) {
            for (int x = 0; x < this.width; ++x) {
                int sum;
                int cr;
                int cb;
                int cg = cb = (cr = (sum = 0));
                int read;
                int bk0;
                int ri;
                if (ym < 0) {
                    ri = (bk0 = -ym);
                    read = x;
                }
                else {
                    if (ym >= this.height) {
                        break;
                    }
                    bk0 = 0;
                    ri = ym;
                    read = x + ymi;
                }
                for (int i = bk0; i < this.blurKernelSize && ri < this.height; ++ri, read += this.width, ++i) {
                    final int[] bm2 = this.blurMult[i];
                    cr += bm2[r2[read]];
                    cg += bm2[g2[read]];
                    cb += bm2[b2[read]];
                    sum += this.blurKernel[i];
                }
                this.pixels[x + yi] = (0xFF000000 | cr / sum << 16 | cg / sum << 8 | cb / sum);
            }
            yi += this.width;
            ymi += this.width;
            ++ym;
        }
    }
    
    protected void blurARGB(final float r) {
        final int wh = this.pixels.length;
        final int[] r2 = new int[wh];
        final int[] g2 = new int[wh];
        final int[] b2 = new int[wh];
        final int[] a2 = new int[wh];
        int yi = 0;
        this.buildBlurKernel(r);
        for (int y = 0; y < this.height; ++y) {
            for (int x = 0; x < this.width; ++x) {
                int sum;
                int ca;
                int cr;
                int cb;
                int cg = cb = (cr = (ca = (sum = 0)));
                int read = x - this.blurRadius;
                int bk0;
                if (read < 0) {
                    bk0 = -read;
                    read = 0;
                }
                else {
                    if (read >= this.width) {
                        break;
                    }
                    bk0 = 0;
                }
                for (int i = bk0; i < this.blurKernelSize && read < this.width; ++read, ++i) {
                    final int c = this.pixels[read + yi];
                    final int[] bm = this.blurMult[i];
                    ca += bm[(c & 0xFF000000) >>> 24];
                    cr += bm[(c & 0xFF0000) >> 16];
                    cg += bm[(c & 0xFF00) >> 8];
                    cb += bm[c & 0xFF];
                    sum += this.blurKernel[i];
                }
                final int ri = yi + x;
                a2[ri] = ca / sum;
                r2[ri] = cr / sum;
                g2[ri] = cg / sum;
                b2[ri] = cb / sum;
            }
            yi += this.width;
        }
        yi = 0;
        int ym = -this.blurRadius;
        int ymi = ym * this.width;
        for (int y = 0; y < this.height; ++y) {
            for (int x = 0; x < this.width; ++x) {
                int sum;
                int ca;
                int cr;
                int cb;
                int cg = cb = (cr = (ca = (sum = 0)));
                int read;
                int bk0;
                int ri;
                if (ym < 0) {
                    ri = (bk0 = -ym);
                    read = x;
                }
                else {
                    if (ym >= this.height) {
                        break;
                    }
                    bk0 = 0;
                    ri = ym;
                    read = x + ymi;
                }
                for (int i = bk0; i < this.blurKernelSize && ri < this.height; ++ri, read += this.width, ++i) {
                    final int[] bm2 = this.blurMult[i];
                    ca += bm2[a2[read]];
                    cr += bm2[r2[read]];
                    cg += bm2[g2[read]];
                    cb += bm2[b2[read]];
                    sum += this.blurKernel[i];
                }
                this.pixels[x + yi] = (ca / sum << 24 | cr / sum << 16 | cg / sum << 8 | cb / sum);
            }
            yi += this.width;
            ymi += this.width;
            ++ym;
        }
    }
    
    protected void dilate(final boolean isInverted) {
        int currIdx = 0;
        final int maxIdx = this.pixels.length;
        final int[] out = new int[maxIdx];
        if (!isInverted) {
            while (currIdx < maxIdx) {
                final int currRowIdx = currIdx;
                int colOut;
                for (int maxRowIdx = currIdx + this.width; currIdx < maxRowIdx; out[currIdx++] = colOut) {
                    final int colOrig;
                    colOut = (colOrig = this.pixels[currIdx]);
                    int idxLeft = currIdx - 1;
                    int idxRight = currIdx + 1;
                    int idxUp = currIdx - this.width;
                    int idxDown = currIdx + this.width;
                    if (idxLeft < currRowIdx) {
                        idxLeft = currIdx;
                    }
                    if (idxRight >= maxRowIdx) {
                        idxRight = currIdx;
                    }
                    if (idxUp < 0) {
                        idxUp = currIdx;
                    }
                    if (idxDown >= maxIdx) {
                        idxDown = currIdx;
                    }
                    final int colUp = this.pixels[idxUp];
                    final int colLeft = this.pixels[idxLeft];
                    final int colDown = this.pixels[idxDown];
                    final int colRight = this.pixels[idxRight];
                    int currLum = 77 * (colOrig >> 16 & 0xFF) + 151 * (colOrig >> 8 & 0xFF) + 28 * (colOrig & 0xFF);
                    final int lumLeft = 77 * (colLeft >> 16 & 0xFF) + 151 * (colLeft >> 8 & 0xFF) + 28 * (colLeft & 0xFF);
                    final int lumRight = 77 * (colRight >> 16 & 0xFF) + 151 * (colRight >> 8 & 0xFF) + 28 * (colRight & 0xFF);
                    final int lumUp = 77 * (colUp >> 16 & 0xFF) + 151 * (colUp >> 8 & 0xFF) + 28 * (colUp & 0xFF);
                    final int lumDown = 77 * (colDown >> 16 & 0xFF) + 151 * (colDown >> 8 & 0xFF) + 28 * (colDown & 0xFF);
                    if (lumLeft > currLum) {
                        colOut = colLeft;
                        currLum = lumLeft;
                    }
                    if (lumRight > currLum) {
                        colOut = colRight;
                        currLum = lumRight;
                    }
                    if (lumUp > currLum) {
                        colOut = colUp;
                        currLum = lumUp;
                    }
                    if (lumDown > currLum) {
                        colOut = colDown;
                        currLum = lumDown;
                    }
                }
            }
        }
        else {
            while (currIdx < maxIdx) {
                final int currRowIdx = currIdx;
                int colOut;
                for (int maxRowIdx = currIdx + this.width; currIdx < maxRowIdx; out[currIdx++] = colOut) {
                    final int colOrig;
                    colOut = (colOrig = this.pixels[currIdx]);
                    int idxLeft = currIdx - 1;
                    int idxRight = currIdx + 1;
                    int idxUp = currIdx - this.width;
                    int idxDown = currIdx + this.width;
                    if (idxLeft < currRowIdx) {
                        idxLeft = currIdx;
                    }
                    if (idxRight >= maxRowIdx) {
                        idxRight = currIdx;
                    }
                    if (idxUp < 0) {
                        idxUp = currIdx;
                    }
                    if (idxDown >= maxIdx) {
                        idxDown = currIdx;
                    }
                    final int colUp = this.pixels[idxUp];
                    final int colLeft = this.pixels[idxLeft];
                    final int colDown = this.pixels[idxDown];
                    final int colRight = this.pixels[idxRight];
                    int currLum = 77 * (colOrig >> 16 & 0xFF) + 151 * (colOrig >> 8 & 0xFF) + 28 * (colOrig & 0xFF);
                    final int lumLeft = 77 * (colLeft >> 16 & 0xFF) + 151 * (colLeft >> 8 & 0xFF) + 28 * (colLeft & 0xFF);
                    final int lumRight = 77 * (colRight >> 16 & 0xFF) + 151 * (colRight >> 8 & 0xFF) + 28 * (colRight & 0xFF);
                    final int lumUp = 77 * (colUp >> 16 & 0xFF) + 151 * (colUp >> 8 & 0xFF) + 28 * (colUp & 0xFF);
                    final int lumDown = 77 * (colDown >> 16 & 0xFF) + 151 * (colDown >> 8 & 0xFF) + 28 * (colDown & 0xFF);
                    if (lumLeft < currLum) {
                        colOut = colLeft;
                        currLum = lumLeft;
                    }
                    if (lumRight < currLum) {
                        colOut = colRight;
                        currLum = lumRight;
                    }
                    if (lumUp < currLum) {
                        colOut = colUp;
                        currLum = lumUp;
                    }
                    if (lumDown < currLum) {
                        colOut = colDown;
                        currLum = lumDown;
                    }
                }
            }
        }
        System.arraycopy(out, 0, this.pixels, 0, maxIdx);
    }
    
    public void copy(final int sx, final int sy, final int sw, final int sh, final int dx, final int dy, final int dw, final int dh) {
        this.blend(this, sx, sy, sw, sh, dx, dy, dw, dh, 0);
    }
    
    public void copy(final PImage src, final int sx, final int sy, final int sw, final int sh, final int dx, final int dy, final int dw, final int dh) {
        this.blend(src, sx, sy, sw, sh, dx, dy, dw, dh, 0);
    }
    
    public static int blendColor(final int c1, final int c2, final int mode) {
        switch (mode) {
            case 0: {
                return c2;
            }
            case 1: {
                return blend_blend(c1, c2);
            }
            case 2: {
                return blend_add_pin(c1, c2);
            }
            case 4: {
                return blend_sub_pin(c1, c2);
            }
            case 8: {
                return blend_lightest(c1, c2);
            }
            case 16: {
                return blend_darkest(c1, c2);
            }
            case 32: {
                return blend_difference(c1, c2);
            }
            case 64: {
                return blend_exclusion(c1, c2);
            }
            case 128: {
                return blend_multiply(c1, c2);
            }
            case 256: {
                return blend_screen(c1, c2);
            }
            case 1024: {
                return blend_hard_light(c1, c2);
            }
            case 2048: {
                return blend_soft_light(c1, c2);
            }
            case 512: {
                return blend_overlay(c1, c2);
            }
            case 4096: {
                return blend_dodge(c1, c2);
            }
            case 8192: {
                return blend_burn(c1, c2);
            }
            default: {
                return 0;
            }
        }
    }
    
    public void blend(final int sx, final int sy, final int sw, final int sh, final int dx, final int dy, final int dw, final int dh, final int mode) {
        this.blend(this, sx, sy, sw, sh, dx, dy, dw, dh, mode);
    }
    
    public void blend(final PImage src, final int sx, final int sy, final int sw, final int sh, final int dx, final int dy, final int dw, final int dh, final int mode) {
        final int sx2 = sx + sw;
        final int sy2 = sy + sh;
        final int dx2 = dx + dw;
        final int dy2 = dy + dh;
        this.loadPixels();
        if (src == this) {
            if (this.intersect(sx, sy, sx2, sy2, dx, dy, dx2, dy2)) {
                this.blit_resize(this.get(sx, sy, sx2 - sx, sy2 - sy), 0, 0, sx2 - sx - 1, sy2 - sy - 1, this.pixels, this.width, this.height, dx, dy, dx2, dy2, mode);
            }
            else {
                this.blit_resize(src, sx, sy, sx2, sy2, this.pixels, this.width, this.height, dx, dy, dx2, dy2, mode);
            }
        }
        else {
            src.loadPixels();
            this.blit_resize(src, sx, sy, sx2, sy2, this.pixels, this.width, this.height, dx, dy, dx2, dy2, mode);
        }
        this.updatePixels();
    }
    
    private boolean intersect(final int sx1, final int sy1, final int sx2, final int sy2, final int dx1, final int dy1, final int dx2, final int dy2) {
        final int sw = sx2 - sx1 + 1;
        final int sh = sy2 - sy1 + 1;
        int dw = dx2 - dx1 + 1;
        int dh = dy2 - dy1 + 1;
        if (dx1 < sx1) {
            dw += dx1 - sx1;
            if (dw > sw) {
                dw = sw;
            }
        }
        else {
            final int w = sw + sx1 - dx1;
            if (dw > w) {
                dw = w;
            }
        }
        if (dy1 < sy1) {
            dh += dy1 - sy1;
            if (dh > sh) {
                dh = sh;
            }
        }
        else {
            final int h = sh + sy1 - dy1;
            if (dh > h) {
                dh = h;
            }
        }
        return dw > 0 && dh > 0;
    }
    
    private void blit_resize(final PImage img, int srcX1, int srcY1, int srcX2, int srcY2, final int[] destPixels, final int screenW, final int screenH, int destX1, int destY1, final int destX2, final int destY2, final int mode) {
        if (srcX1 < 0) {
            srcX1 = 0;
        }
        if (srcY1 < 0) {
            srcY1 = 0;
        }
        if (srcX2 > img.width) {
            srcX2 = img.width;
        }
        if (srcY2 > img.height) {
            srcY2 = img.height;
        }
        int srcW = srcX2 - srcX1;
        int srcH = srcY2 - srcY1;
        int destW = destX2 - destX1;
        int destH = destY2 - destY1;
        final boolean smooth = true;
        if (!smooth) {
            ++srcW;
            ++srcH;
        }
        if (destW <= 0 || destH <= 0 || srcW <= 0 || srcH <= 0 || destX1 >= screenW || destY1 >= screenH || srcX1 >= img.width || srcY1 >= img.height) {
            return;
        }
        final int dx = (int)(srcW / destW * 32768.0f);
        final int dy = (int)(srcH / destH * 32768.0f);
        this.srcXOffset = ((destX1 < 0) ? (-destX1 * dx) : (srcX1 * 32768));
        this.srcYOffset = ((destY1 < 0) ? (-destY1 * dy) : (srcY1 * 32768));
        if (destX1 < 0) {
            destW += destX1;
            destX1 = 0;
        }
        if (destY1 < 0) {
            destH += destY1;
            destY1 = 0;
        }
        destW = low(destW, screenW - destX1);
        destH = low(destH, screenH - destY1);
        int destOffset = destY1 * screenW + destX1;
        this.srcBuffer = img.pixels;
        if (smooth) {
            this.iw = img.width;
            this.iw1 = img.width - 1;
            this.ih1 = img.height - 1;
            switch (mode) {
                case 1: {
                    for (int y = 0; y < destH; ++y) {
                        this.filter_new_scanline();
                        for (int x = 0; x < destW; ++x) {
                            destPixels[destOffset + x] = blend_blend(destPixels[destOffset + x], this.filter_bilinear());
                            this.sX += dx;
                        }
                        destOffset += screenW;
                        this.srcYOffset += dy;
                    }
                    break;
                }
                case 2: {
                    for (int y = 0; y < destH; ++y) {
                        this.filter_new_scanline();
                        for (int x = 0; x < destW; ++x) {
                            destPixels[destOffset + x] = blend_add_pin(destPixels[destOffset + x], this.filter_bilinear());
                            this.sX += dx;
                        }
                        destOffset += screenW;
                        this.srcYOffset += dy;
                    }
                    break;
                }
                case 4: {
                    for (int y = 0; y < destH; ++y) {
                        this.filter_new_scanline();
                        for (int x = 0; x < destW; ++x) {
                            destPixels[destOffset + x] = blend_sub_pin(destPixels[destOffset + x], this.filter_bilinear());
                            this.sX += dx;
                        }
                        destOffset += screenW;
                        this.srcYOffset += dy;
                    }
                    break;
                }
                case 8: {
                    for (int y = 0; y < destH; ++y) {
                        this.filter_new_scanline();
                        for (int x = 0; x < destW; ++x) {
                            destPixels[destOffset + x] = blend_lightest(destPixels[destOffset + x], this.filter_bilinear());
                            this.sX += dx;
                        }
                        destOffset += screenW;
                        this.srcYOffset += dy;
                    }
                    break;
                }
                case 16: {
                    for (int y = 0; y < destH; ++y) {
                        this.filter_new_scanline();
                        for (int x = 0; x < destW; ++x) {
                            destPixels[destOffset + x] = blend_darkest(destPixels[destOffset + x], this.filter_bilinear());
                            this.sX += dx;
                        }
                        destOffset += screenW;
                        this.srcYOffset += dy;
                    }
                    break;
                }
                case 0: {
                    for (int y = 0; y < destH; ++y) {
                        this.filter_new_scanline();
                        for (int x = 0; x < destW; ++x) {
                            destPixels[destOffset + x] = this.filter_bilinear();
                            this.sX += dx;
                        }
                        destOffset += screenW;
                        this.srcYOffset += dy;
                    }
                    break;
                }
                case 32: {
                    for (int y = 0; y < destH; ++y) {
                        this.filter_new_scanline();
                        for (int x = 0; x < destW; ++x) {
                            destPixels[destOffset + x] = blend_difference(destPixels[destOffset + x], this.filter_bilinear());
                            this.sX += dx;
                        }
                        destOffset += screenW;
                        this.srcYOffset += dy;
                    }
                    break;
                }
                case 64: {
                    for (int y = 0; y < destH; ++y) {
                        this.filter_new_scanline();
                        for (int x = 0; x < destW; ++x) {
                            destPixels[destOffset + x] = blend_exclusion(destPixels[destOffset + x], this.filter_bilinear());
                            this.sX += dx;
                        }
                        destOffset += screenW;
                        this.srcYOffset += dy;
                    }
                    break;
                }
                case 128: {
                    for (int y = 0; y < destH; ++y) {
                        this.filter_new_scanline();
                        for (int x = 0; x < destW; ++x) {
                            destPixels[destOffset + x] = blend_multiply(destPixels[destOffset + x], this.filter_bilinear());
                            this.sX += dx;
                        }
                        destOffset += screenW;
                        this.srcYOffset += dy;
                    }
                    break;
                }
                case 256: {
                    for (int y = 0; y < destH; ++y) {
                        this.filter_new_scanline();
                        for (int x = 0; x < destW; ++x) {
                            destPixels[destOffset + x] = blend_screen(destPixels[destOffset + x], this.filter_bilinear());
                            this.sX += dx;
                        }
                        destOffset += screenW;
                        this.srcYOffset += dy;
                    }
                    break;
                }
                case 512: {
                    for (int y = 0; y < destH; ++y) {
                        this.filter_new_scanline();
                        for (int x = 0; x < destW; ++x) {
                            destPixels[destOffset + x] = blend_overlay(destPixels[destOffset + x], this.filter_bilinear());
                            this.sX += dx;
                        }
                        destOffset += screenW;
                        this.srcYOffset += dy;
                    }
                    break;
                }
                case 1024: {
                    for (int y = 0; y < destH; ++y) {
                        this.filter_new_scanline();
                        for (int x = 0; x < destW; ++x) {
                            destPixels[destOffset + x] = blend_hard_light(destPixels[destOffset + x], this.filter_bilinear());
                            this.sX += dx;
                        }
                        destOffset += screenW;
                        this.srcYOffset += dy;
                    }
                    break;
                }
                case 2048: {
                    for (int y = 0; y < destH; ++y) {
                        this.filter_new_scanline();
                        for (int x = 0; x < destW; ++x) {
                            destPixels[destOffset + x] = blend_soft_light(destPixels[destOffset + x], this.filter_bilinear());
                            this.sX += dx;
                        }
                        destOffset += screenW;
                        this.srcYOffset += dy;
                    }
                    break;
                }
                case 4096: {
                    for (int y = 0; y < destH; ++y) {
                        this.filter_new_scanline();
                        for (int x = 0; x < destW; ++x) {
                            destPixels[destOffset + x] = blend_dodge(destPixels[destOffset + x], this.filter_bilinear());
                            this.sX += dx;
                        }
                        destOffset += screenW;
                        this.srcYOffset += dy;
                    }
                    break;
                }
                case 8192: {
                    for (int y = 0; y < destH; ++y) {
                        this.filter_new_scanline();
                        for (int x = 0; x < destW; ++x) {
                            destPixels[destOffset + x] = blend_burn(destPixels[destOffset + x], this.filter_bilinear());
                            this.sX += dx;
                        }
                        destOffset += screenW;
                        this.srcYOffset += dy;
                    }
                    break;
                }
            }
        }
        else {
            switch (mode) {
                case 1: {
                    for (int y = 0; y < destH; ++y) {
                        this.sX = this.srcXOffset;
                        this.sY = (this.srcYOffset >> 15) * img.width;
                        for (int x = 0; x < destW; ++x) {
                            destPixels[destOffset + x] = blend_blend(destPixels[destOffset + x], this.srcBuffer[this.sY + (this.sX >> 15)]);
                            this.sX += dx;
                        }
                        destOffset += screenW;
                        this.srcYOffset += dy;
                    }
                    break;
                }
                case 2: {
                    for (int y = 0; y < destH; ++y) {
                        this.sX = this.srcXOffset;
                        this.sY = (this.srcYOffset >> 15) * img.width;
                        for (int x = 0; x < destW; ++x) {
                            destPixels[destOffset + x] = blend_add_pin(destPixels[destOffset + x], this.srcBuffer[this.sY + (this.sX >> 15)]);
                            this.sX += dx;
                        }
                        destOffset += screenW;
                        this.srcYOffset += dy;
                    }
                    break;
                }
                case 4: {
                    for (int y = 0; y < destH; ++y) {
                        this.sX = this.srcXOffset;
                        this.sY = (this.srcYOffset >> 15) * img.width;
                        for (int x = 0; x < destW; ++x) {
                            destPixels[destOffset + x] = blend_sub_pin(destPixels[destOffset + x], this.srcBuffer[this.sY + (this.sX >> 15)]);
                            this.sX += dx;
                        }
                        destOffset += screenW;
                        this.srcYOffset += dy;
                    }
                    break;
                }
                case 8: {
                    for (int y = 0; y < destH; ++y) {
                        this.sX = this.srcXOffset;
                        this.sY = (this.srcYOffset >> 15) * img.width;
                        for (int x = 0; x < destW; ++x) {
                            destPixels[destOffset + x] = blend_lightest(destPixels[destOffset + x], this.srcBuffer[this.sY + (this.sX >> 15)]);
                            this.sX += dx;
                        }
                        destOffset += screenW;
                        this.srcYOffset += dy;
                    }
                    break;
                }
                case 16: {
                    for (int y = 0; y < destH; ++y) {
                        this.sX = this.srcXOffset;
                        this.sY = (this.srcYOffset >> 15) * img.width;
                        for (int x = 0; x < destW; ++x) {
                            destPixels[destOffset + x] = blend_darkest(destPixels[destOffset + x], this.srcBuffer[this.sY + (this.sX >> 15)]);
                            this.sX += dx;
                        }
                        destOffset += screenW;
                        this.srcYOffset += dy;
                    }
                    break;
                }
                case 0: {
                    for (int y = 0; y < destH; ++y) {
                        this.sX = this.srcXOffset;
                        this.sY = (this.srcYOffset >> 15) * img.width;
                        for (int x = 0; x < destW; ++x) {
                            destPixels[destOffset + x] = this.srcBuffer[this.sY + (this.sX >> 15)];
                            this.sX += dx;
                        }
                        destOffset += screenW;
                        this.srcYOffset += dy;
                    }
                    break;
                }
                case 32: {
                    for (int y = 0; y < destH; ++y) {
                        this.sX = this.srcXOffset;
                        this.sY = (this.srcYOffset >> 15) * img.width;
                        for (int x = 0; x < destW; ++x) {
                            destPixels[destOffset + x] = blend_difference(destPixels[destOffset + x], this.srcBuffer[this.sY + (this.sX >> 15)]);
                            this.sX += dx;
                        }
                        destOffset += screenW;
                        this.srcYOffset += dy;
                    }
                    break;
                }
                case 64: {
                    for (int y = 0; y < destH; ++y) {
                        this.sX = this.srcXOffset;
                        this.sY = (this.srcYOffset >> 15) * img.width;
                        for (int x = 0; x < destW; ++x) {
                            destPixels[destOffset + x] = blend_exclusion(destPixels[destOffset + x], this.srcBuffer[this.sY + (this.sX >> 15)]);
                            this.sX += dx;
                        }
                        destOffset += screenW;
                        this.srcYOffset += dy;
                    }
                    break;
                }
                case 128: {
                    for (int y = 0; y < destH; ++y) {
                        this.sX = this.srcXOffset;
                        this.sY = (this.srcYOffset >> 15) * img.width;
                        for (int x = 0; x < destW; ++x) {
                            destPixels[destOffset + x] = blend_multiply(destPixels[destOffset + x], this.srcBuffer[this.sY + (this.sX >> 15)]);
                            this.sX += dx;
                        }
                        destOffset += screenW;
                        this.srcYOffset += dy;
                    }
                    break;
                }
                case 256: {
                    for (int y = 0; y < destH; ++y) {
                        this.sX = this.srcXOffset;
                        this.sY = (this.srcYOffset >> 15) * img.width;
                        for (int x = 0; x < destW; ++x) {
                            destPixels[destOffset + x] = blend_screen(destPixels[destOffset + x], this.srcBuffer[this.sY + (this.sX >> 15)]);
                            this.sX += dx;
                        }
                        destOffset += screenW;
                        this.srcYOffset += dy;
                    }
                    break;
                }
                case 512: {
                    for (int y = 0; y < destH; ++y) {
                        this.sX = this.srcXOffset;
                        this.sY = (this.srcYOffset >> 15) * img.width;
                        for (int x = 0; x < destW; ++x) {
                            destPixels[destOffset + x] = blend_overlay(destPixels[destOffset + x], this.srcBuffer[this.sY + (this.sX >> 15)]);
                            this.sX += dx;
                        }
                        destOffset += screenW;
                        this.srcYOffset += dy;
                    }
                    break;
                }
                case 1024: {
                    for (int y = 0; y < destH; ++y) {
                        this.sX = this.srcXOffset;
                        this.sY = (this.srcYOffset >> 15) * img.width;
                        for (int x = 0; x < destW; ++x) {
                            destPixels[destOffset + x] = blend_hard_light(destPixels[destOffset + x], this.srcBuffer[this.sY + (this.sX >> 15)]);
                            this.sX += dx;
                        }
                        destOffset += screenW;
                        this.srcYOffset += dy;
                    }
                    break;
                }
                case 2048: {
                    for (int y = 0; y < destH; ++y) {
                        this.sX = this.srcXOffset;
                        this.sY = (this.srcYOffset >> 15) * img.width;
                        for (int x = 0; x < destW; ++x) {
                            destPixels[destOffset + x] = blend_soft_light(destPixels[destOffset + x], this.srcBuffer[this.sY + (this.sX >> 15)]);
                            this.sX += dx;
                        }
                        destOffset += screenW;
                        this.srcYOffset += dy;
                    }
                    break;
                }
                case 4096: {
                    for (int y = 0; y < destH; ++y) {
                        this.sX = this.srcXOffset;
                        this.sY = (this.srcYOffset >> 15) * img.width;
                        for (int x = 0; x < destW; ++x) {
                            destPixels[destOffset + x] = blend_dodge(destPixels[destOffset + x], this.srcBuffer[this.sY + (this.sX >> 15)]);
                            this.sX += dx;
                        }
                        destOffset += screenW;
                        this.srcYOffset += dy;
                    }
                    break;
                }
                case 8192: {
                    for (int y = 0; y < destH; ++y) {
                        this.sX = this.srcXOffset;
                        this.sY = (this.srcYOffset >> 15) * img.width;
                        for (int x = 0; x < destW; ++x) {
                            destPixels[destOffset + x] = blend_burn(destPixels[destOffset + x], this.srcBuffer[this.sY + (this.sX >> 15)]);
                            this.sX += dx;
                        }
                        destOffset += screenW;
                        this.srcYOffset += dy;
                    }
                    break;
                }
            }
        }
    }
    
    private void filter_new_scanline() {
        this.sX = this.srcXOffset;
        this.fracV = (this.srcYOffset & 0x7FFF);
        this.ifV = 32767 - this.fracV;
        this.v1 = (this.srcYOffset >> 15) * this.iw;
        this.v2 = low((this.srcYOffset >> 15) + 1, this.ih1) * this.iw;
    }
    
    private int filter_bilinear() {
        this.fracU = (this.sX & 0x7FFF);
        this.ifU = 32767 - this.fracU;
        this.ul = this.ifU * this.ifV >> 15;
        this.ll = this.ifU * this.fracV >> 15;
        this.ur = this.fracU * this.ifV >> 15;
        this.lr = this.fracU * this.fracV >> 15;
        this.u1 = this.sX >> 15;
        this.u2 = low(this.u1 + 1, this.iw1);
        this.cUL = this.srcBuffer[this.v1 + this.u1];
        this.cUR = this.srcBuffer[this.v1 + this.u2];
        this.cLL = this.srcBuffer[this.v2 + this.u1];
        this.cLR = this.srcBuffer[this.v2 + this.u2];
        this.r = (this.ul * ((this.cUL & 0xFF0000) >> 16) + this.ll * ((this.cLL & 0xFF0000) >> 16) + this.ur * ((this.cUR & 0xFF0000) >> 16) + this.lr * ((this.cLR & 0xFF0000) >> 16) << 1 & 0xFF0000);
        this.g = (this.ul * (this.cUL & 0xFF00) + this.ll * (this.cLL & 0xFF00) + this.ur * (this.cUR & 0xFF00) + this.lr * (this.cLR & 0xFF00) >>> 15 & 0xFF00);
        this.b = this.ul * (this.cUL & 0xFF) + this.ll * (this.cLL & 0xFF) + this.ur * (this.cUR & 0xFF) + this.lr * (this.cLR & 0xFF) >>> 15;
        this.a = (this.ul * ((this.cUL & 0xFF000000) >>> 24) + this.ll * ((this.cLL & 0xFF000000) >>> 24) + this.ur * ((this.cUR & 0xFF000000) >>> 24) + this.lr * ((this.cLR & 0xFF000000) >>> 24) << 9 & 0xFF000000);
        return this.a | this.r | this.g | this.b;
    }
    
    private static int low(final int a, final int b) {
        return (a < b) ? a : b;
    }
    
    private static int high(final int a, final int b) {
        return (a > b) ? a : b;
    }
    
    private static int peg(final int n) {
        return (n < 0) ? 0 : ((n > 255) ? 255 : n);
    }
    
    private static int mix(final int a, final int b, final int f) {
        return a + ((b - a) * f >> 8);
    }
    
    private static int blend_blend(final int a, final int b) {
        final int f = (b & 0xFF000000) >>> 24;
        return low(((a & 0xFF000000) >>> 24) + f, 255) << 24 | (mix(a & 0xFF0000, b & 0xFF0000, f) & 0xFF0000) | (mix(a & 0xFF00, b & 0xFF00, f) & 0xFF00) | mix(a & 0xFF, b & 0xFF, f);
    }
    
    private static int blend_add_pin(final int a, final int b) {
        final int f = (b & 0xFF000000) >>> 24;
        return low(((a & 0xFF000000) >>> 24) + f, 255) << 24 | (low((a & 0xFF0000) + ((b & 0xFF0000) >> 8) * f, 16711680) & 0xFF0000) | (low((a & 0xFF00) + ((b & 0xFF00) >> 8) * f, 65280) & 0xFF00) | low((a & 0xFF) + ((b & 0xFF) * f >> 8), 255);
    }
    
    private static int blend_sub_pin(final int a, final int b) {
        final int f = (b & 0xFF000000) >>> 24;
        return low(((a & 0xFF000000) >>> 24) + f, 255) << 24 | (high((a & 0xFF0000) - ((b & 0xFF0000) >> 8) * f, 65280) & 0xFF0000) | (high((a & 0xFF00) - ((b & 0xFF00) >> 8) * f, 255) & 0xFF00) | high((a & 0xFF) - ((b & 0xFF) * f >> 8), 0);
    }
    
    private static int blend_lightest(final int a, final int b) {
        final int f = (b & 0xFF000000) >>> 24;
        return low(((a & 0xFF000000) >>> 24) + f, 255) << 24 | (high(a & 0xFF0000, ((b & 0xFF0000) >> 8) * f) & 0xFF0000) | (high(a & 0xFF00, ((b & 0xFF00) >> 8) * f) & 0xFF00) | high(a & 0xFF, (b & 0xFF) * f >> 8);
    }
    
    private static int blend_darkest(final int a, final int b) {
        final int f = (b & 0xFF000000) >>> 24;
        return low(((a & 0xFF000000) >>> 24) + f, 255) << 24 | (mix(a & 0xFF0000, low(a & 0xFF0000, ((b & 0xFF0000) >> 8) * f), f) & 0xFF0000) | (mix(a & 0xFF00, low(a & 0xFF00, ((b & 0xFF00) >> 8) * f), f) & 0xFF00) | mix(a & 0xFF, low(a & 0xFF, (b & 0xFF) * f >> 8), f);
    }
    
    private static int blend_difference(final int a, final int b) {
        final int f = (b & 0xFF000000) >>> 24;
        final int ar = (a & 0xFF0000) >> 16;
        final int ag = (a & 0xFF00) >> 8;
        final int ab = a & 0xFF;
        final int br = (b & 0xFF0000) >> 16;
        final int bg = (b & 0xFF00) >> 8;
        final int bb = b & 0xFF;
        final int cr = (ar > br) ? (ar - br) : (br - ar);
        final int cg = (ag > bg) ? (ag - bg) : (bg - ag);
        final int cb = (ab > bb) ? (ab - bb) : (bb - ab);
        return low(((a & 0xFF000000) >>> 24) + f, 255) << 24 | peg(ar + ((cr - ar) * f >> 8)) << 16 | peg(ag + ((cg - ag) * f >> 8)) << 8 | peg(ab + ((cb - ab) * f >> 8));
    }
    
    private static int blend_exclusion(final int a, final int b) {
        final int f = (b & 0xFF000000) >>> 24;
        final int ar = (a & 0xFF0000) >> 16;
        final int ag = (a & 0xFF00) >> 8;
        final int ab = a & 0xFF;
        final int br = (b & 0xFF0000) >> 16;
        final int bg = (b & 0xFF00) >> 8;
        final int bb = b & 0xFF;
        final int cr = ar + br - (ar * br >> 7);
        final int cg = ag + bg - (ag * bg >> 7);
        final int cb = ab + bb - (ab * bb >> 7);
        return low(((a & 0xFF000000) >>> 24) + f, 255) << 24 | peg(ar + ((cr - ar) * f >> 8)) << 16 | peg(ag + ((cg - ag) * f >> 8)) << 8 | peg(ab + ((cb - ab) * f >> 8));
    }
    
    private static int blend_multiply(final int a, final int b) {
        final int f = (b & 0xFF000000) >>> 24;
        final int ar = (a & 0xFF0000) >> 16;
        final int ag = (a & 0xFF00) >> 8;
        final int ab = a & 0xFF;
        final int br = (b & 0xFF0000) >> 16;
        final int bg = (b & 0xFF00) >> 8;
        final int bb = b & 0xFF;
        final int cr = ar * br >> 8;
        final int cg = ag * bg >> 8;
        final int cb = ab * bb >> 8;
        return low(((a & 0xFF000000) >>> 24) + f, 255) << 24 | peg(ar + ((cr - ar) * f >> 8)) << 16 | peg(ag + ((cg - ag) * f >> 8)) << 8 | peg(ab + ((cb - ab) * f >> 8));
    }
    
    private static int blend_screen(final int a, final int b) {
        final int f = (b & 0xFF000000) >>> 24;
        final int ar = (a & 0xFF0000) >> 16;
        final int ag = (a & 0xFF00) >> 8;
        final int ab = a & 0xFF;
        final int br = (b & 0xFF0000) >> 16;
        final int bg = (b & 0xFF00) >> 8;
        final int bb = b & 0xFF;
        final int cr = 255 - ((255 - ar) * (255 - br) >> 8);
        final int cg = 255 - ((255 - ag) * (255 - bg) >> 8);
        final int cb = 255 - ((255 - ab) * (255 - bb) >> 8);
        return low(((a & 0xFF000000) >>> 24) + f, 255) << 24 | peg(ar + ((cr - ar) * f >> 8)) << 16 | peg(ag + ((cg - ag) * f >> 8)) << 8 | peg(ab + ((cb - ab) * f >> 8));
    }
    
    private static int blend_overlay(final int a, final int b) {
        final int f = (b & 0xFF000000) >>> 24;
        final int ar = (a & 0xFF0000) >> 16;
        final int ag = (a & 0xFF00) >> 8;
        final int ab = a & 0xFF;
        final int br = (b & 0xFF0000) >> 16;
        final int bg = (b & 0xFF00) >> 8;
        final int bb = b & 0xFF;
        final int cr = (ar < 128) ? (ar * br >> 7) : (255 - ((255 - ar) * (255 - br) >> 7));
        final int cg = (ag < 128) ? (ag * bg >> 7) : (255 - ((255 - ag) * (255 - bg) >> 7));
        final int cb = (ab < 128) ? (ab * bb >> 7) : (255 - ((255 - ab) * (255 - bb) >> 7));
        return low(((a & 0xFF000000) >>> 24) + f, 255) << 24 | peg(ar + ((cr - ar) * f >> 8)) << 16 | peg(ag + ((cg - ag) * f >> 8)) << 8 | peg(ab + ((cb - ab) * f >> 8));
    }
    
    private static int blend_hard_light(final int a, final int b) {
        final int f = (b & 0xFF000000) >>> 24;
        final int ar = (a & 0xFF0000) >> 16;
        final int ag = (a & 0xFF00) >> 8;
        final int ab = a & 0xFF;
        final int br = (b & 0xFF0000) >> 16;
        final int bg = (b & 0xFF00) >> 8;
        final int bb = b & 0xFF;
        final int cr = (br < 128) ? (ar * br >> 7) : (255 - ((255 - ar) * (255 - br) >> 7));
        final int cg = (bg < 128) ? (ag * bg >> 7) : (255 - ((255 - ag) * (255 - bg) >> 7));
        final int cb = (bb < 128) ? (ab * bb >> 7) : (255 - ((255 - ab) * (255 - bb) >> 7));
        return low(((a & 0xFF000000) >>> 24) + f, 255) << 24 | peg(ar + ((cr - ar) * f >> 8)) << 16 | peg(ag + ((cg - ag) * f >> 8)) << 8 | peg(ab + ((cb - ab) * f >> 8));
    }
    
    private static int blend_soft_light(final int a, final int b) {
        final int f = (b & 0xFF000000) >>> 24;
        final int ar = (a & 0xFF0000) >> 16;
        final int ag = (a & 0xFF00) >> 8;
        final int ab = a & 0xFF;
        final int br = (b & 0xFF0000) >> 16;
        final int bg = (b & 0xFF00) >> 8;
        final int bb = b & 0xFF;
        final int cr = (ar * br >> 7) + (ar * ar >> 8) - (ar * ar * br >> 15);
        final int cg = (ag * bg >> 7) + (ag * ag >> 8) - (ag * ag * bg >> 15);
        final int cb = (ab * bb >> 7) + (ab * ab >> 8) - (ab * ab * bb >> 15);
        return low(((a & 0xFF000000) >>> 24) + f, 255) << 24 | peg(ar + ((cr - ar) * f >> 8)) << 16 | peg(ag + ((cg - ag) * f >> 8)) << 8 | peg(ab + ((cb - ab) * f >> 8));
    }
    
    private static int blend_dodge(final int a, final int b) {
        final int f = (b & 0xFF000000) >>> 24;
        final int ar = (a & 0xFF0000) >> 16;
        final int ag = (a & 0xFF00) >> 8;
        final int ab = a & 0xFF;
        final int br = (b & 0xFF0000) >> 16;
        final int bg = (b & 0xFF00) >> 8;
        final int bb = b & 0xFF;
        final int cr = (br == 255) ? 255 : peg((ar << 8) / (255 - br));
        final int cg = (bg == 255) ? 255 : peg((ag << 8) / (255 - bg));
        final int cb = (bb == 255) ? 255 : peg((ab << 8) / (255 - bb));
        return low(((a & 0xFF000000) >>> 24) + f, 255) << 24 | peg(ar + ((cr - ar) * f >> 8)) << 16 | peg(ag + ((cg - ag) * f >> 8)) << 8 | peg(ab + ((cb - ab) * f >> 8));
    }
    
    private static int blend_burn(final int a, final int b) {
        final int f = (b & 0xFF000000) >>> 24;
        final int ar = (a & 0xFF0000) >> 16;
        final int ag = (a & 0xFF00) >> 8;
        final int ab = a & 0xFF;
        final int br = (b & 0xFF0000) >> 16;
        final int bg = (b & 0xFF00) >> 8;
        final int bb = b & 0xFF;
        final int cr = (br == 0) ? 0 : (255 - peg((255 - ar << 8) / br));
        final int cg = (bg == 0) ? 0 : (255 - peg((255 - ag << 8) / bg));
        final int cb = (bb == 0) ? 0 : (255 - peg((255 - ab << 8) / bb));
        return low(((a & 0xFF000000) >>> 24) + f, 255) << 24 | peg(ar + ((cr - ar) * f >> 8)) << 16 | peg(ag + ((cg - ag) * f >> 8)) << 8 | peg(ab + ((cb - ab) * f >> 8));
    }
    
    protected static PImage loadTIFF(final byte[] tiff) {
        if (tiff[42] != tiff[102] || tiff[43] != tiff[103]) {
            System.err.println("Error: Processing can only read its own TIFF files.");
            return null;
        }
        final int width = (tiff[30] & 0xFF) << 8 | (tiff[31] & 0xFF);
        final int height = (tiff[42] & 0xFF) << 8 | (tiff[43] & 0xFF);
        int count = (tiff[114] & 0xFF) << 24 | (tiff[115] & 0xFF) << 16 | (tiff[116] & 0xFF) << 8 | (tiff[117] & 0xFF);
        if (count != width * height * 3) {
            System.err.println("Error: Processing can only read its own TIFF files. (" + width + ", " + height + ")");
            return null;
        }
        for (int i = 0; i < PImage.TIFF_HEADER.length; ++i) {
            if (i != 30 && i != 31 && i != 42 && i != 43 && i != 102 && i != 103 && i != 114 && i != 115 && i != 116) {
                if (i != 117) {
                    if (tiff[i] != PImage.TIFF_HEADER[i]) {
                        System.err.println("Error: Processing can only read its own TIFF files. (" + i + ")");
                        return null;
                    }
                }
            }
        }
        final PImage outgoing = new PImage(width, height, 1);
        int index = 768;
        count /= 3;
        for (int j = 0; j < count; ++j) {
            outgoing.pixels[j] = (0xFF000000 | (tiff[index++] & 0xFF) << 16 | (tiff[index++] & 0xFF) << 8 | (tiff[index++] & 0xFF));
        }
        return outgoing;
    }
    
    protected boolean saveTIFF(final OutputStream output) {
        try {
            final byte[] tiff = new byte[768];
            System.arraycopy(PImage.TIFF_HEADER, 0, tiff, 0, PImage.TIFF_HEADER.length);
            tiff[30] = (byte)(this.width >> 8 & 0xFF);
            tiff[31] = (byte)(this.width & 0xFF);
            tiff[42] = (tiff[102] = (byte)(this.height >> 8 & 0xFF));
            tiff[43] = (tiff[103] = (byte)(this.height & 0xFF));
            final int count = this.width * this.height * 3;
            tiff[114] = (byte)(count >> 24 & 0xFF);
            tiff[115] = (byte)(count >> 16 & 0xFF);
            tiff[116] = (byte)(count >> 8 & 0xFF);
            tiff[117] = (byte)(count & 0xFF);
            output.write(tiff);
            for (int i = 0; i < this.pixels.length; ++i) {
                output.write(this.pixels[i] >> 16 & 0xFF);
                output.write(this.pixels[i] >> 8 & 0xFF);
                output.write(this.pixels[i] & 0xFF);
            }
            output.flush();
            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    protected boolean saveTGA(final OutputStream output) {
        final byte[] header = new byte[18];
        if (this.format == 4) {
            header[2] = 11;
            header[16] = 8;
            header[17] = 40;
        }
        else if (this.format == 1) {
            header[2] = 10;
            header[16] = 24;
            header[17] = 32;
        }
        else {
            if (this.format != 2) {
                throw new RuntimeException("Image format not recognized inside save()");
            }
            header[2] = 10;
            header[16] = 32;
            header[17] = 40;
        }
        header[12] = (byte)(this.width & 0xFF);
        header[13] = (byte)(this.width >> 8);
        header[14] = (byte)(this.height & 0xFF);
        header[15] = (byte)(this.height >> 8);
        try {
            output.write(header);
            final int maxLen = this.height * this.width;
            int index = 0;
            final int[] currChunk = new int[128];
            if (this.format == 4) {
                while (index < maxLen) {
                    boolean isRLE = false;
                    int rle = 1;
                    int col = currChunk[0] = (this.pixels[index] & 0xFF);
                    while (index + rle < maxLen) {
                        if (col != (this.pixels[index + rle] & 0xFF) || rle == 128) {
                            isRLE = (rle > 1);
                            break;
                        }
                        ++rle;
                    }
                    if (isRLE) {
                        output.write(0x80 | rle - 1);
                        output.write(col);
                    }
                    else {
                        rle = 1;
                        while (index + rle < maxLen) {
                            final int cscan = this.pixels[index + rle] & 0xFF;
                            if ((col != cscan && rle < 128) || rle < 3) {
                                col = (currChunk[rle] = cscan);
                                ++rle;
                            }
                            else {
                                if (col == cscan) {
                                    rle -= 2;
                                    break;
                                }
                                break;
                            }
                        }
                        output.write(rle - 1);
                        for (int i = 0; i < rle; ++i) {
                            output.write(currChunk[i]);
                        }
                    }
                    index += rle;
                }
            }
            else {
                while (index < maxLen) {
                    boolean isRLE = false;
                    int col = currChunk[0] = this.pixels[index];
                    int rle;
                    for (rle = 1; index + rle < maxLen; ++rle) {
                        if (col != this.pixels[index + rle] || rle == 128) {
                            isRLE = (rle > 1);
                            break;
                        }
                    }
                    if (isRLE) {
                        output.write(0x80 | rle - 1);
                        output.write(col & 0xFF);
                        output.write(col >> 8 & 0xFF);
                        output.write(col >> 16 & 0xFF);
                        if (this.format == 2) {
                            output.write(col >>> 24 & 0xFF);
                        }
                    }
                    else {
                        rle = 1;
                        while (index + rle < maxLen) {
                            if ((col != this.pixels[index + rle] && rle < 128) || rle < 3) {
                                col = (currChunk[rle] = this.pixels[index + rle]);
                                ++rle;
                            }
                            else {
                                if (col == this.pixels[index + rle]) {
                                    rle -= 2;
                                    break;
                                }
                                break;
                            }
                        }
                        output.write(rle - 1);
                        if (this.format == 2) {
                            for (int i = 0; i < rle; ++i) {
                                col = currChunk[i];
                                output.write(col & 0xFF);
                                output.write(col >> 8 & 0xFF);
                                output.write(col >> 16 & 0xFF);
                                output.write(col >>> 24 & 0xFF);
                            }
                        }
                        else {
                            for (int i = 0; i < rle; ++i) {
                                col = currChunk[i];
                                output.write(col & 0xFF);
                                output.write(col >> 8 & 0xFF);
                                output.write(col >> 16 & 0xFF);
                            }
                        }
                    }
                    index += rle;
                }
            }
            output.flush();
            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    protected void saveImageIO(final String path) throws IOException {
        try {
            final BufferedImage bimage = new BufferedImage(this.width, this.height, (this.format == 2) ? 2 : 1);
            bimage.setRGB(0, 0, this.width, this.height, this.pixels, 0, this.width);
            final File file = new File(path);
            final String extension = path.substring(path.lastIndexOf(46) + 1);
            ImageIO.write(bimage, extension, file);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IOException("image save failed.");
        }
    }
    
    public void save(String filename) {
        boolean success = false;
        final File file = new File(filename);
        if (!file.isAbsolute()) {
            if (this.parent != null) {
                filename = this.parent.savePath(filename);
            }
            else {
                final String msg = "PImage.save() requires an absolute path. Use createImage(), or pass savePath() to save().";
                PGraphics.showException(msg);
            }
        }
        this.loadPixels();
        try {
            OutputStream os = null;
            if (this.saveImageFormats == null) {
                this.saveImageFormats = ImageIO.getWriterFormatNames();
            }
            if (this.saveImageFormats != null) {
                for (int i = 0; i < this.saveImageFormats.length; ++i) {
                    if (filename.endsWith("." + this.saveImageFormats[i])) {
                        this.saveImageIO(filename);
                        return;
                    }
                }
            }
            if (filename.toLowerCase().endsWith(".tga")) {
                os = new BufferedOutputStream(new FileOutputStream(filename), 32768);
                success = this.saveTGA(os);
            }
            else {
                if (!filename.toLowerCase().endsWith(".tif") && !filename.toLowerCase().endsWith(".tiff")) {
                    filename = String.valueOf(filename) + ".tif";
                }
                os = new BufferedOutputStream(new FileOutputStream(filename), 32768);
                success = this.saveTIFF(os);
            }
            os.flush();
            os.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            success = false;
        }
        if (!success) {
            throw new RuntimeException("Error while saving image.");
        }
    }
}
