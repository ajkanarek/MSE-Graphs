// 
// Decompiled by Procyon v0.5.30
// 

package processing.core;

import java.awt.image.WritableRaster;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.InputStream;
import java.awt.RenderingHints;
import java.util.Arrays;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.awt.Font;

public class PFont implements PConstants
{
    protected int glyphCount;
    protected Glyph[] glyphs;
    protected String name;
    protected String psname;
    protected int size;
    protected boolean smooth;
    protected int ascent;
    protected int descent;
    protected int[] ascii;
    protected boolean lazy;
    protected Font font;
    protected boolean stream;
    protected boolean fontSearched;
    protected static Font[] fonts;
    protected static HashMap<String, Font> fontDifferent;
    BufferedImage lazyImage;
    Graphics2D lazyGraphics;
    FontMetrics lazyMetrics;
    int[] lazySamples;
    static final char[] EXTRA_CHARS;
    public static char[] CHARSET;
    
    static {
        EXTRA_CHARS = new char[] { '\u0080', '\u0081', '\u0082', '\u0083', '\u0084', '\u0085', '\u0086', '\u0087', '\u0088', '\u0089', '\u008a', '\u008b', '\u008c', '\u008d', '\u008e', '\u008f', '\u0090', '\u0091', '\u0092', '\u0093', '\u0094', '\u0095', '\u0096', '\u0097', '\u0098', '\u0099', '\u009a', '\u009b', '\u009c', '\u009d', '\u009e', '\u009f', ' ', '¡', '¢', '£', '¤', '¥', '¦', '§', '¨', '©', 'ª', '«', '¬', '\u00ad', '®', '¯', '°', '±', '´', 'µ', '¶', '·', '¸', 'º', '»', '¿', '\u00c0', '\u00c1', '\u00c2', '\u00c3', '\u00c4', '\u00c5', '\u00c6', '\u00c7', '\u00c8', '\u00c9', '\u00ca', '\u00cb', '\u00cc', '\u00cd', '\u00ce', '\u00cf', '\u00d1', '\u00d2', '\u00d3', '\u00d4', '\u00d5', '\u00d6', '\u00d7', '\u00d8', '\u00d9', '\u00da', '\u00db', '\u00dc', '\u00dd', '\u00df', '\u00e0', '\u00e1', '\u00e2', '\u00e3', '\u00e4', '\u00e5', '\u00e6', '\u00e7', '\u00e8', '\u00e9', '\u00ea', '\u00eb', '\u00ec', '\u00ed', '\u00ee', '\u00ef', '\u00f1', '\u00f2', '\u00f3', '\u00f4', '\u00f5', '\u00f6', '\u00f7', '\u00f8', '\u00f9', '\u00fa', '\u00fb', '\u00fc', '\u00fd', '\u00ff', '\u0102', '\u0103', '\u0104', '\u0105', '\u0106', '\u0107', '\u010c', '\u010d', '\u010e', '\u010f', '\u0110', '\u0111', '\u0118', '\u0119', '\u011a', '\u011b', '\u0131', '\u0139', '\u013a', '\u013d', '\u013e', '\u0141', '\u0142', '\u0143', '\u0144', '\u0147', '\u0148', '\u0150', '\u0151', '\u0152', '\u0153', '\u0154', '\u0155', '\u0158', '\u0159', '\u015a', '\u015b', '\u015e', '\u015f', '\u0160', '\u0161', '\u0162', '\u0163', '\u0164', '\u0165', '\u016e', '\u016f', '\u0170', '\u0171', '\u0178', '\u0179', '\u017a', '\u017b', '\u017c', '\u017d', '\u017e', '\u0192', '\u02c6', '\u02c7', '\u02d8', '\u02d9', '\u02da', '\u02db', '\u02dc', '\u02dd', '\u03a9', '\u03c0', '\u2013', '\u2014', '\u2018', '\u2019', '\u201a', '\u201c', '\u201d', '\u201e', '\u2020', '\u2021', '\u2022', '\u2026', '\u2030', '\u2039', '\u203a', '\u2044', '\u20ac', '\u2122', '\u2202', '\u2206', '\u220f', '\u2211', '\u221a', '\u221e', '\u222b', '\u2248', '\u2260', '\u2264', '\u2265', '\u25ca', '\uf8ff', '\ufb01', '\ufb02' };
        PFont.CHARSET = new char[94 + PFont.EXTRA_CHARS.length];
        int index = 0;
        for (int i = 33; i <= 126; ++i) {
            PFont.CHARSET[index++] = (char)i;
        }
        for (int i = 0; i < PFont.EXTRA_CHARS.length; ++i) {
            PFont.CHARSET[index++] = PFont.EXTRA_CHARS[i];
        }
    }
    
    public PFont() {
    }
    
    public PFont(final Font font, final boolean smooth) {
        this(font, smooth, null);
    }
    
    public PFont(final Font font, final boolean smooth, final char[] charset) {
        this.font = font;
        this.smooth = smooth;
        this.name = font.getName();
        this.psname = font.getPSName();
        this.size = font.getSize();
        final int initialCount = 10;
        this.glyphs = new Glyph[initialCount];
        Arrays.fill(this.ascii = new int[128], -1);
        final int mbox3 = this.size * 3;
        this.lazyImage = new BufferedImage(mbox3, mbox3, 1);
        (this.lazyGraphics = (Graphics2D)this.lazyImage.getGraphics()).setRenderingHint(RenderingHints.KEY_ANTIALIASING, smooth ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
        this.lazyGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, smooth ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        this.lazyGraphics.setFont(font);
        this.lazyMetrics = this.lazyGraphics.getFontMetrics();
        this.lazySamples = new int[mbox3 * mbox3];
        if (charset == null) {
            this.lazy = true;
        }
        else {
            Arrays.sort(charset);
            this.glyphs = new Glyph[charset.length];
            this.glyphCount = 0;
            for (final char c : charset) {
                if (font.canDisplay(c)) {
                    final Glyph glyf = new Glyph(c);
                    if (glyf.value < 128) {
                        this.ascii[glyf.value] = this.glyphCount;
                    }
                    this.glyphs[this.glyphCount++] = glyf;
                }
            }
            if (this.glyphCount != charset.length) {
                this.glyphs = (Glyph[])PApplet.subset(this.glyphs, 0, this.glyphCount);
            }
        }
        if (this.ascent == 0) {
            if (font.canDisplay('d')) {
                new Glyph('d');
            }
            else {
                this.ascent = this.lazyMetrics.getAscent();
            }
        }
        if (this.descent == 0) {
            if (font.canDisplay('p')) {
                new Glyph('p');
            }
            else {
                this.descent = this.lazyMetrics.getDescent();
            }
        }
    }
    
    public PFont(final Font font, final boolean smooth, final char[] charset, final boolean stream) {
        this(font, smooth, charset);
        this.stream = stream;
    }
    
    public PFont(final InputStream input) throws IOException {
        final DataInputStream is = new DataInputStream(input);
        this.glyphCount = is.readInt();
        final int version = is.readInt();
        this.size = is.readInt();
        is.readInt();
        this.ascent = is.readInt();
        this.descent = is.readInt();
        this.glyphs = new Glyph[this.glyphCount];
        Arrays.fill(this.ascii = new int[128], -1);
        for (int i = 0; i < this.glyphCount; ++i) {
            final Glyph glyph = new Glyph(is);
            if (glyph.value < 128) {
                this.ascii[glyph.value] = i;
            }
            this.glyphs[i] = glyph;
        }
        if (this.ascent == 0 && this.descent == 0) {
            throw new RuntimeException("Please use \"Create Font\" to re-create this font.");
        }
        Glyph[] glyphs;
        for (int length = (glyphs = this.glyphs).length, j = 0; j < length; ++j) {
            final Glyph glyph2 = glyphs[j];
            glyph2.readBitmap(is);
        }
        if (version >= 10) {
            this.name = is.readUTF();
            this.psname = is.readUTF();
        }
        if (version == 11) {
            this.smooth = is.readBoolean();
        }
    }
    
    public void save(final OutputStream output) throws IOException {
        final DataOutputStream os = new DataOutputStream(output);
        os.writeInt(this.glyphCount);
        if (this.name == null || this.psname == null) {
            this.name = "";
            this.psname = "";
        }
        os.writeInt(11);
        os.writeInt(this.size);
        os.writeInt(0);
        os.writeInt(this.ascent);
        os.writeInt(this.descent);
        for (int i = 0; i < this.glyphCount; ++i) {
            this.glyphs[i].writeHeader(os);
        }
        for (int i = 0; i < this.glyphCount; ++i) {
            this.glyphs[i].writeBitmap(os);
        }
        os.writeUTF(this.name);
        os.writeUTF(this.psname);
        os.writeBoolean(this.smooth);
        os.flush();
    }
    
    protected void addGlyph(final char c) {
        final Glyph glyph = new Glyph(c);
        if (this.glyphCount == this.glyphs.length) {
            this.glyphs = (Glyph[])PApplet.expand(this.glyphs);
        }
        if (this.glyphCount == 0) {
            this.glyphs[this.glyphCount] = glyph;
            if (glyph.value < 128) {
                this.ascii[glyph.value] = 0;
            }
        }
        else if (this.glyphs[this.glyphCount - 1].value < glyph.value) {
            this.glyphs[this.glyphCount] = glyph;
            if (glyph.value < 128) {
                this.ascii[glyph.value] = this.glyphCount;
            }
        }
        else {
            int i = 0;
            while (i < this.glyphCount) {
                if (this.glyphs[i].value > c) {
                    for (int j = this.glyphCount; j > i; --j) {
                        this.glyphs[j] = this.glyphs[j - 1];
                        if (this.glyphs[j].value < 128) {
                            this.ascii[this.glyphs[j].value] = j;
                        }
                    }
                    this.glyphs[i] = glyph;
                    if (c < '\u0080') {
                        this.ascii[c] = i;
                        break;
                    }
                    break;
                }
                else {
                    ++i;
                }
            }
        }
        ++this.glyphCount;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getPostScriptName() {
        return this.psname;
    }
    
    public void setFont(final Font font) {
        this.font = font;
    }
    
    public Font getFont() {
        return this.font;
    }
    
    public boolean isStream() {
        return this.stream;
    }
    
    public Font findFont() {
        if (this.font == null && !this.fontSearched) {
            this.font = new Font(this.name, 0, this.size);
            if (!this.font.getPSName().equals(this.psname)) {
                this.font = new Font(this.psname, 0, this.size);
            }
            if (!this.font.getPSName().equals(this.psname)) {
                this.font = null;
            }
            this.fontSearched = true;
        }
        return this.font;
    }
    
    public Glyph getGlyph(final char c) {
        final int index = this.index(c);
        return (index == -1) ? null : this.glyphs[index];
    }
    
    protected int index(final char c) {
        if (!this.lazy) {
            return this.indexActual(c);
        }
        final int index = this.indexActual(c);
        if (index != -1) {
            return index;
        }
        if (this.font.canDisplay(c)) {
            this.addGlyph(c);
            return this.indexActual(c);
        }
        return -1;
    }
    
    protected int indexActual(final char c) {
        if (this.glyphCount == 0) {
            return -1;
        }
        if (c < '\u0080') {
            return this.ascii[c];
        }
        return this.indexHunt(c, 0, this.glyphCount - 1);
    }
    
    protected int indexHunt(final int c, final int start, final int stop) {
        final int pivot = (start + stop) / 2;
        if (c == this.glyphs[pivot].value) {
            return pivot;
        }
        if (start >= stop) {
            return -1;
        }
        if (c < this.glyphs[pivot].value) {
            return this.indexHunt(c, start, pivot - 1);
        }
        return this.indexHunt(c, pivot + 1, stop);
    }
    
    public float kern(final char a, final char b) {
        return 0.0f;
    }
    
    public float ascent() {
        return this.ascent / this.size;
    }
    
    public float descent() {
        return this.descent / this.size;
    }
    
    public float width(final char c) {
        if (c == ' ') {
            return this.width('i');
        }
        final int cc = this.index(c);
        if (cc == -1) {
            return 0.0f;
        }
        return this.glyphs[cc].setWidth / this.size;
    }
    
    public static String[] list() {
        loadFonts();
        final String[] list = new String[PFont.fonts.length];
        for (int i = 0; i < list.length; ++i) {
            list[i] = PFont.fonts[i].getName();
        }
        return list;
    }
    
    public static void loadFonts() {
        if (PFont.fonts == null) {
            final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            PFont.fonts = ge.getAllFonts();
            if (PApplet.platform == 2) {
                PFont.fontDifferent = new HashMap<String, Font>();
                Font[] fonts;
                for (int length = (fonts = PFont.fonts).length, i = 0; i < length; ++i) {
                    final Font font = fonts[i];
                    PFont.fontDifferent.put(font.getName(), font);
                }
            }
        }
    }
    
    public static Font findFont(final String name) {
        loadFonts();
        if (PApplet.platform == 2) {
            final Font maybe = PFont.fontDifferent.get(name);
            if (maybe != null) {
                return maybe;
            }
        }
        return new Font(name, 0, 1);
    }
    
    public class Glyph
    {
        PImage image;
        int value;
        int height;
        int width;
        int setWidth;
        int topExtent;
        int leftExtent;
        
        protected Glyph() {
        }
        
        protected Glyph(final DataInputStream is) throws IOException {
            this.readHeader(is);
        }
        
        protected void readHeader(final DataInputStream is) throws IOException {
            this.value = is.readInt();
            this.height = is.readInt();
            this.width = is.readInt();
            this.setWidth = is.readInt();
            this.topExtent = is.readInt();
            this.leftExtent = is.readInt();
            is.readInt();
            if (this.value == 100 && PFont.this.ascent == 0) {
                PFont.this.ascent = this.topExtent;
            }
            if (this.value == 112 && PFont.this.descent == 0) {
                PFont.this.descent = -this.topExtent + this.height;
            }
        }
        
        protected void writeHeader(final DataOutputStream os) throws IOException {
            os.writeInt(this.value);
            os.writeInt(this.height);
            os.writeInt(this.width);
            os.writeInt(this.setWidth);
            os.writeInt(this.topExtent);
            os.writeInt(this.leftExtent);
            os.writeInt(0);
        }
        
        protected void readBitmap(final DataInputStream is) throws IOException {
            this.image = new PImage(this.width, this.height, 4);
            final int bitmapSize = this.width * this.height;
            final byte[] temp = new byte[bitmapSize];
            is.readFully(temp);
            final int w = this.width;
            final int h = this.height;
            final int[] pixels = this.image.pixels;
            for (int y = 0; y < h; ++y) {
                for (int x = 0; x < w; ++x) {
                    pixels[y * this.width + x] = (temp[y * w + x] & 0xFF);
                }
            }
        }
        
        protected void writeBitmap(final DataOutputStream os) throws IOException {
            final int[] pixels = this.image.pixels;
            for (int y = 0; y < this.height; ++y) {
                for (int x = 0; x < this.width; ++x) {
                    os.write(pixels[y * this.width + x] & 0xFF);
                }
            }
        }
        
        protected Glyph(final char c) {
            final int mbox3 = PFont.this.size * 3;
            PFont.this.lazyGraphics.setColor(Color.white);
            PFont.this.lazyGraphics.fillRect(0, 0, mbox3, mbox3);
            PFont.this.lazyGraphics.setColor(Color.black);
            PFont.this.lazyGraphics.drawString(String.valueOf(c), PFont.this.size, PFont.this.size * 2);
            final WritableRaster raster = PFont.this.lazyImage.getRaster();
            raster.getDataElements(0, 0, mbox3, mbox3, PFont.this.lazySamples);
            int minX = 1000;
            int maxX = 0;
            int minY = 1000;
            int maxY = 0;
            boolean pixelFound = false;
            for (int y = 0; y < mbox3; ++y) {
                for (int x = 0; x < mbox3; ++x) {
                    final int sample = PFont.this.lazySamples[y * mbox3 + x] & 0xFF;
                    if (sample != 255) {
                        if (x < minX) {
                            minX = x;
                        }
                        if (y < minY) {
                            minY = y;
                        }
                        if (x > maxX) {
                            maxX = x;
                        }
                        if (y > maxY) {
                            maxY = y;
                        }
                        pixelFound = true;
                    }
                }
            }
            if (!pixelFound) {
                minY = (minX = 0);
                maxY = (maxX = 0);
            }
            this.value = c;
            this.height = maxY - minY + 1;
            this.width = maxX - minX + 1;
            this.setWidth = PFont.this.lazyMetrics.charWidth(c);
            this.topExtent = PFont.this.size * 2 - minY;
            this.leftExtent = minX - PFont.this.size;
            this.image = new PImage(this.width, this.height, 4);
            final int[] pixels = this.image.pixels;
            for (int y2 = minY; y2 <= maxY; ++y2) {
                for (int x2 = minX; x2 <= maxX; ++x2) {
                    final int val = 255 - (PFont.this.lazySamples[y2 * mbox3 + x2] & 0xFF);
                    final int pindex = (y2 - minY) * this.width + (x2 - minX);
                    pixels[pindex] = val;
                }
            }
            if (this.value == 100 && PFont.this.ascent == 0) {
                PFont.this.ascent = this.topExtent;
            }
            if (this.value == 112 && PFont.this.descent == 0) {
                PFont.this.descent = -this.topExtent + this.height;
            }
        }
    }
}
