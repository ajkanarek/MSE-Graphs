/*     */ package processing.core;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.WritableRaster;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
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
/*     */ public class PFont
/*     */   implements PConstants
/*     */ {
/*     */   protected int glyphCount;
/*     */   protected Glyph[] glyphs;
/*     */   protected String name;
/*     */   protected String psname;
/*     */   protected int size;
/*     */   protected boolean smooth;
/*     */   protected int ascent;
/*     */   protected int descent;
/*     */   protected int[] ascii;
/*     */   protected boolean lazy;
/*     */   protected Font font;
/*     */   protected boolean stream;
/*     */   protected boolean fontSearched;
/*     */   protected static Font[] fonts;
/*     */   protected static HashMap<String, Font> fontDifferent;
/*     */   BufferedImage lazyImage;
/*     */   Graphics2D lazyGraphics;
/*     */   FontMetrics lazyMetrics;
/*     */   int[] lazySamples;
/*     */   
/*     */   public PFont(Font font, boolean smooth)
/*     */   {
/* 153 */     this(font, smooth, null);
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
/*     */   public PFont(Font font, boolean smooth, char[] charset)
/*     */   {
/* 167 */     this.font = font;
/* 168 */     this.smooth = smooth;
/*     */     
/* 170 */     this.name = font.getName();
/* 171 */     this.psname = font.getPSName();
/* 172 */     this.size = font.getSize();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 179 */     int initialCount = 10;
/* 180 */     this.glyphs = new Glyph[initialCount];
/*     */     
/* 182 */     this.ascii = new int[''];
/* 183 */     Arrays.fill(this.ascii, -1);
/*     */     
/* 185 */     int mbox3 = this.size * 3;
/*     */     
/* 187 */     this.lazyImage = new BufferedImage(mbox3, mbox3, 1);
/* 188 */     this.lazyGraphics = ((Graphics2D)this.lazyImage.getGraphics());
/* 189 */     this.lazyGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
/* 190 */       smooth ? 
/* 191 */       RenderingHints.VALUE_ANTIALIAS_ON : 
/* 192 */       RenderingHints.VALUE_ANTIALIAS_OFF);
/*     */     
/* 194 */     this.lazyGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
/* 195 */       smooth ? 
/* 196 */       RenderingHints.VALUE_TEXT_ANTIALIAS_ON : 
/* 197 */       RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
/*     */     
/* 199 */     this.lazyGraphics.setFont(font);
/* 200 */     this.lazyMetrics = this.lazyGraphics.getFontMetrics();
/* 201 */     this.lazySamples = new int[mbox3 * mbox3];
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 209 */     if (charset == null) {
/* 210 */       this.lazy = true;
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 215 */       Arrays.sort(charset);
/*     */       
/* 217 */       this.glyphs = new Glyph[charset.length];
/*     */       
/* 219 */       this.glyphCount = 0;
/* 220 */       char[] arrayOfChar; int j = (arrayOfChar = charset).length; for (int i = 0; i < j; i++) { char c = arrayOfChar[i];
/* 221 */         if (font.canDisplay(c)) {
/* 222 */           Glyph glyf = new Glyph(c);
/* 223 */           if (glyf.value < 128) {
/* 224 */             this.ascii[glyf.value] = this.glyphCount;
/*     */           }
/* 226 */           this.glyphs[(this.glyphCount++)] = glyf;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 231 */       if (this.glyphCount != charset.length) {
/* 232 */         this.glyphs = ((Glyph[])PApplet.subset(this.glyphs, 0, this.glyphCount));
/*     */       }
/*     */     }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 265 */     if (this.ascent == 0) {
/* 266 */       if (font.canDisplay('d')) {
/* 267 */         new Glyph('d');
/*     */       } else {
/* 269 */         this.ascent = this.lazyMetrics.getAscent();
/*     */       }
/*     */     }
/* 272 */     if (this.descent == 0) {
/* 273 */       if (font.canDisplay('p')) {
/* 274 */         new Glyph('p');
/*     */       } else {
/* 276 */         this.descent = this.lazyMetrics.getDescent();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PFont(Font font, boolean smooth, char[] charset, boolean stream)
/*     */   {
/* 287 */     this(font, smooth, charset);
/* 288 */     this.stream = stream;
/*     */   }
/*     */   
/*     */   public PFont(InputStream input) throws IOException
/*     */   {
/* 293 */     DataInputStream is = new DataInputStream(input);
/*     */     
/*     */ 
/* 296 */     this.glyphCount = is.readInt();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 301 */     int version = is.readInt();
/*     */     
/*     */ 
/*     */ 
/* 305 */     this.size = is.readInt();
/*     */     
/*     */ 
/*     */ 
/* 309 */     is.readInt();
/*     */     
/* 311 */     this.ascent = is.readInt();
/* 312 */     this.descent = is.readInt();
/*     */     
/*     */ 
/* 315 */     this.glyphs = new Glyph[this.glyphCount];
/*     */     
/* 317 */     this.ascii = new int[''];
/* 318 */     Arrays.fill(this.ascii, -1);
/*     */     
/*     */ 
/* 321 */     for (int i = 0; i < this.glyphCount; i++) {
/* 322 */       glyph = new Glyph(is);
/*     */       
/* 324 */       if (glyph.value < 128) {
/* 325 */         this.ascii[glyph.value] = i;
/*     */       }
/* 327 */       this.glyphs[i] = glyph;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 332 */     if ((this.ascent == 0) && (this.descent == 0)) {
/* 333 */       throw new RuntimeException("Please use \"Create Font\" to re-create this font.");
/*     */     }
/*     */     
/*     */     Glyph[] arrayOfGlyph;
/* 337 */     Glyph localGlyph1 = (arrayOfGlyph = this.glyphs).length; for (Glyph glyph = 0; glyph < localGlyph1; glyph++) { Glyph glyph = arrayOfGlyph[glyph];
/* 338 */       glyph.readBitmap(is);
/*     */     }
/*     */     
/* 341 */     if (version >= 10) {
/* 342 */       this.name = is.readUTF();
/* 343 */       this.psname = is.readUTF();
/*     */     }
/* 345 */     if (version == 11) {
/* 346 */       this.smooth = is.readBoolean();
/*     */     }
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
/*     */   public void save(OutputStream output)
/*     */     throws IOException
/*     */   {
/* 361 */     DataOutputStream os = new DataOutputStream(output);
/*     */     
/* 363 */     os.writeInt(this.glyphCount);
/*     */     
/* 365 */     if ((this.name == null) || (this.psname == null)) {
/* 366 */       this.name = "";
/* 367 */       this.psname = "";
/*     */     }
/*     */     
/* 370 */     os.writeInt(11);
/* 371 */     os.writeInt(this.size);
/* 372 */     os.writeInt(0);
/* 373 */     os.writeInt(this.ascent);
/* 374 */     os.writeInt(this.descent);
/*     */     
/* 376 */     for (int i = 0; i < this.glyphCount; i++) {
/* 377 */       this.glyphs[i].writeHeader(os);
/*     */     }
/*     */     
/* 380 */     for (int i = 0; i < this.glyphCount; i++) {
/* 381 */       this.glyphs[i].writeBitmap(os);
/*     */     }
/*     */     
/*     */ 
/* 385 */     os.writeUTF(this.name);
/* 386 */     os.writeUTF(this.psname);
/* 387 */     os.writeBoolean(this.smooth);
/*     */     
/* 389 */     os.flush();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void addGlyph(char c)
/*     */   {
/* 398 */     Glyph glyph = new Glyph(c);
/*     */     
/* 400 */     if (this.glyphCount == this.glyphs.length) {
/* 401 */       this.glyphs = ((Glyph[])PApplet.expand(this.glyphs));
/*     */     }
/* 403 */     if (this.glyphCount == 0) {
/* 404 */       this.glyphs[this.glyphCount] = glyph;
/* 405 */       if (glyph.value < 128) {
/* 406 */         this.ascii[glyph.value] = 0;
/*     */       }
/*     */     }
/* 409 */     else if (this.glyphs[(this.glyphCount - 1)].value < glyph.value) {
/* 410 */       this.glyphs[this.glyphCount] = glyph;
/* 411 */       if (glyph.value < 128) {
/* 412 */         this.ascii[glyph.value] = this.glyphCount;
/*     */       }
/*     */     }
/*     */     else {
/* 416 */       for (int i = 0; i < this.glyphCount; i++) {
/* 417 */         if (this.glyphs[i].value > c) {
/* 418 */           for (int j = this.glyphCount; j > i; j--) {
/* 419 */             this.glyphs[j] = this.glyphs[(j - 1)];
/* 420 */             if (this.glyphs[j].value < 128) {
/* 421 */               this.ascii[this.glyphs[j].value] = j;
/*     */             }
/*     */           }
/* 424 */           this.glyphs[i] = glyph;
/*     */           
/* 426 */           if (c >= '') break; this.ascii[c] = i;
/* 427 */           break;
/*     */         }
/*     */       }
/*     */     }
/* 431 */     this.glyphCount += 1;
/*     */   }
/*     */   
/*     */   public String getName()
/*     */   {
/* 436 */     return this.name;
/*     */   }
/*     */   
/*     */   public String getPostScriptName()
/*     */   {
/* 441 */     return this.psname;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setFont(Font font)
/*     */   {
/* 449 */     this.font = font;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Font getFont()
/*     */   {
/* 460 */     return this.font;
/*     */   }
/*     */   
/*     */   public boolean isStream()
/*     */   {
/* 465 */     return this.stream;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Font findFont()
/*     */   {
/* 474 */     if ((this.font == null) && 
/* 475 */       (!this.fontSearched))
/*     */     {
/* 477 */       this.font = new Font(this.name, 0, this.size);
/*     */       
/* 479 */       if (!this.font.getPSName().equals(this.psname))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/* 484 */         this.font = new Font(this.psname, 0, this.size);
/*     */       }
/*     */       
/* 487 */       if (!this.font.getPSName().equals(this.psname)) {
/* 488 */         this.font = null;
/*     */       }
/* 490 */       this.fontSearched = true;
/*     */     }
/*     */     
/* 493 */     return this.font;
/*     */   }
/*     */   
/*     */   public Glyph getGlyph(char c)
/*     */   {
/* 498 */     int index = index(c);
/* 499 */     return index == -1 ? null : this.glyphs[index];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected int index(char c)
/*     */   {
/* 508 */     if (this.lazy) {
/* 509 */       int index = indexActual(c);
/* 510 */       if (index != -1) {
/* 511 */         return index;
/*     */       }
/* 513 */       if (this.font.canDisplay(c))
/*     */       {
/* 515 */         addGlyph(c);
/*     */         
/* 517 */         return indexActual(c);
/*     */       }
/*     */       
/* 520 */       return -1;
/*     */     }
/*     */     
/*     */ 
/* 524 */     return indexActual(c);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected int indexActual(char c)
/*     */   {
/* 533 */     if (this.glyphCount == 0) { return -1;
/*     */     }
/*     */     
/* 536 */     if (c < '') { return this.ascii[c];
/*     */     }
/*     */     
/*     */ 
/* 540 */     return indexHunt(c, 0, this.glyphCount - 1);
/*     */   }
/*     */   
/*     */   protected int indexHunt(int c, int start, int stop)
/*     */   {
/* 545 */     int pivot = (start + stop) / 2;
/*     */     
/*     */ 
/* 548 */     if (c == this.glyphs[pivot].value) { return pivot;
/*     */     }
/*     */     
/*     */ 
/* 552 */     if (start >= stop) { return -1;
/*     */     }
/*     */     
/* 555 */     if (c < this.glyphs[pivot].value) { return indexHunt(c, start, pivot - 1);
/*     */     }
/*     */     
/* 558 */     return indexHunt(c, pivot + 1, stop);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float kern(char a, char b)
/*     */   {
/* 567 */     return 0.0F;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float ascent()
/*     */   {
/* 576 */     return this.ascent / this.size;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float descent()
/*     */   {
/* 585 */     return this.descent / this.size;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public float width(char c)
/*     */   {
/* 593 */     if (c == ' ') { return width('i');
/*     */     }
/* 595 */     int cc = index(c);
/* 596 */     if (cc == -1) { return 0.0F;
/*     */     }
/* 598 */     return this.glyphs[cc].setWidth / this.size;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 605 */   static final char[] EXTRA_CHARS = {
/* 606 */     '', '', '', '', '', '', '', '', 
/* 607 */     '', '', '', '', '', '', '', '', 
/* 608 */     '', '', '', '', '', '', '', '', 
/* 609 */     '', '', '', '', '', '', '', '', 
/* 610 */     ' ', '¡', '¢', '£', '¤', '¥', '¦', '§', 
/* 611 */     '¨', '©', 'ª', '«', '¬', '­', '®', '¯', 
/* 612 */     '°', '±', '´', 'µ', '¶', '·', '¸', 'º', 
/* 613 */     '»', '¿', 'À', 'Á', 'Â', 'Ã', 'Ä', 'Å', 
/* 614 */     'Æ', 'Ç', 'È', 'É', 'Ê', 'Ë', 'Ì', 'Í', 
/* 615 */     'Î', 'Ï', 'Ñ', 'Ò', 'Ó', 'Ô', 'Õ', 'Ö', 
/* 616 */     '×', 'Ø', 'Ù', 'Ú', 'Û', 'Ü', 'Ý', 'ß', 
/* 617 */     'à', 'á', 'â', 'ã', 'ä', 'å', 'æ', 'ç', 
/* 618 */     'è', 'é', 'ê', 'ë', 'ì', 'í', 'î', 'ï', 
/* 619 */     'ñ', 'ò', 'ó', 'ô', 'õ', 'ö', '÷', 'ø', 
/* 620 */     'ù', 'ú', 'û', 'ü', 'ý', 'ÿ', 'Ă', 'ă', 
/* 621 */     'Ą', 'ą', 'Ć', 'ć', 'Č', 'č', 'Ď', 'ď', 
/* 622 */     'Đ', 'đ', 'Ę', 'ę', 'Ě', 'ě', 'ı', 'Ĺ', 
/* 623 */     'ĺ', 'Ľ', 'ľ', 'Ł', 'ł', 'Ń', 'ń', 'Ň', 
/* 624 */     'ň', 'Ő', 'ő', 'Œ', 'œ', 'Ŕ', 'ŕ', 'Ř', 
/* 625 */     'ř', 'Ś', 'ś', 'Ş', 'ş', 'Š', 'š', 'Ţ', 
/* 626 */     'ţ', 'Ť', 'ť', 'Ů', 'ů', 'Ű', 'ű', 'Ÿ', 
/* 627 */     'Ź', 'ź', 'Ż', 'ż', 'Ž', 'ž', 'ƒ', 'ˆ', 
/* 628 */     'ˇ', '˘', '˙', '˚', '˛', '˜', '˝', 'Ω', 
/* 629 */     'π', '–', '—', '‘', '’', '‚', '“', '”', 
/* 630 */     '„', '†', '‡', '•', '…', '‰', '‹', '›', 
/* 631 */     '⁄', '€', '™', '∂', '∆', '∏', '∑', '√', 
/* 632 */     '∞', '∫', '≈', '≠', '≤', '≥', '◊', 63743, 
/* 633 */     64257, 64258 };
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
/* 653 */   public static char[] CHARSET = new char[94 + EXTRA_CHARS.length];
/* 654 */   static { int index = 0;
/* 655 */     for (int i = 33; i <= 126; i++) {
/* 656 */       CHARSET[(index++)] = ((char)i);
/*     */     }
/* 658 */     for (int i = 0; i < EXTRA_CHARS.length; i++) {
/* 659 */       CHARSET[(index++)] = EXTRA_CHARS[i];
/*     */     }
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
/*     */   public static String[] list()
/*     */   {
/* 679 */     loadFonts();
/* 680 */     String[] list = new String[fonts.length];
/* 681 */     for (int i = 0; i < list.length; i++) {
/* 682 */       list[i] = fonts[i].getName();
/*     */     }
/* 684 */     return list;
/*     */   }
/*     */   
/*     */   public static void loadFonts()
/*     */   {
/* 689 */     if (fonts == null) {
/* 690 */       GraphicsEnvironment ge = 
/* 691 */         GraphicsEnvironment.getLocalGraphicsEnvironment();
/* 692 */       fonts = ge.getAllFonts();
/* 693 */       if (PApplet.platform == 2) {
/* 694 */         fontDifferent = new HashMap();
/* 695 */         Font[] arrayOfFont; int j = (arrayOfFont = fonts).length; for (int i = 0; i < j; i++) { Font font = arrayOfFont[i];
/*     */           
/* 697 */           fontDifferent.put(font.getName(), font);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Font findFont(String name)
/*     */   {
/*     */     
/*     */     
/*     */ 
/*     */ 
/* 712 */     if (PApplet.platform == 2) {
/* 713 */       Font maybe = (Font)fontDifferent.get(name);
/* 714 */       if (maybe != null) {
/* 715 */         return maybe;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 723 */     return new Font(name, 0, 1);
/*     */   }
/*     */   
/*     */ 
/*     */   public PFont() {}
/*     */   
/*     */ 
/*     */   public class Glyph
/*     */   {
/*     */     PImage image;
/*     */     
/*     */     int value;
/*     */     
/*     */     int height;
/*     */     
/*     */     int width;
/*     */     
/*     */     int setWidth;
/*     */     int topExtent;
/*     */     int leftExtent;
/*     */     
/*     */     protected Glyph() {}
/*     */     
/*     */     protected Glyph(DataInputStream is)
/*     */       throws IOException
/*     */     {
/* 749 */       readHeader(is);
/*     */     }
/*     */     
/*     */     protected void readHeader(DataInputStream is) throws IOException
/*     */     {
/* 754 */       this.value = is.readInt();
/* 755 */       this.height = is.readInt();
/* 756 */       this.width = is.readInt();
/* 757 */       this.setWidth = is.readInt();
/* 758 */       this.topExtent = is.readInt();
/* 759 */       this.leftExtent = is.readInt();
/*     */       
/*     */ 
/* 762 */       is.readInt();
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 767 */       if ((this.value == 100) && 
/* 768 */         (PFont.this.ascent == 0)) { PFont.this.ascent = this.topExtent;
/*     */       }
/* 770 */       if ((this.value == 112) && 
/* 771 */         (PFont.this.descent == 0)) PFont.this.descent = (-this.topExtent + this.height);
/*     */     }
/*     */     
/*     */     protected void writeHeader(DataOutputStream os)
/*     */       throws IOException
/*     */     {
/* 777 */       os.writeInt(this.value);
/* 778 */       os.writeInt(this.height);
/* 779 */       os.writeInt(this.width);
/* 780 */       os.writeInt(this.setWidth);
/* 781 */       os.writeInt(this.topExtent);
/* 782 */       os.writeInt(this.leftExtent);
/* 783 */       os.writeInt(0);
/*     */     }
/*     */     
/*     */     protected void readBitmap(DataInputStream is) throws IOException
/*     */     {
/* 788 */       this.image = new PImage(this.width, this.height, 4);
/* 789 */       int bitmapSize = this.width * this.height;
/*     */       
/* 791 */       byte[] temp = new byte[bitmapSize];
/* 792 */       is.readFully(temp);
/*     */       
/*     */ 
/* 795 */       int w = this.width;
/* 796 */       int h = this.height;
/* 797 */       int[] pixels = this.image.pixels;
/* 798 */       for (int y = 0; y < h; y++) {
/* 799 */         for (int x = 0; x < w; x++) {
/* 800 */           pixels[(y * this.width + x)] = (temp[(y * w + x)] & 0xFF);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     protected void writeBitmap(DataOutputStream os)
/*     */       throws IOException
/*     */     {
/* 810 */       int[] pixels = this.image.pixels;
/* 811 */       for (int y = 0; y < this.height; y++) {
/* 812 */         for (int x = 0; x < this.width; x++) {
/* 813 */           os.write(pixels[(y * this.width + x)] & 0xFF);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */     protected Glyph(char c)
/*     */     {
/* 820 */       int mbox3 = PFont.this.size * 3;
/* 821 */       PFont.this.lazyGraphics.setColor(Color.white);
/* 822 */       PFont.this.lazyGraphics.fillRect(0, 0, mbox3, mbox3);
/* 823 */       PFont.this.lazyGraphics.setColor(Color.black);
/* 824 */       PFont.this.lazyGraphics.drawString(String.valueOf(c), PFont.this.size, PFont.this.size * 2);
/*     */       
/* 826 */       WritableRaster raster = PFont.this.lazyImage.getRaster();
/* 827 */       raster.getDataElements(0, 0, mbox3, mbox3, PFont.this.lazySamples);
/*     */       
/* 829 */       int minX = 1000;int maxX = 0;
/* 830 */       int minY = 1000;int maxY = 0;
/* 831 */       boolean pixelFound = false;
/*     */       
/* 833 */       for (int y = 0; y < mbox3; y++) {
/* 834 */         for (int x = 0; x < mbox3; x++) {
/* 835 */           int sample = PFont.this.lazySamples[(y * mbox3 + x)] & 0xFF;
/* 836 */           if (sample != 255) {
/* 837 */             if (x < minX) minX = x;
/* 838 */             if (y < minY) minY = y;
/* 839 */             if (x > maxX) maxX = x;
/* 840 */             if (y > maxY) maxY = y;
/* 841 */             pixelFound = true;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 846 */       if (!pixelFound) {
/* 847 */         minX = minY = 0;
/* 848 */         maxX = maxY = 0;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 853 */       this.value = c;
/* 854 */       this.height = (maxY - minY + 1);
/* 855 */       this.width = (maxX - minX + 1);
/* 856 */       this.setWidth = PFont.this.lazyMetrics.charWidth(c);
/*     */       
/*     */ 
/*     */ 
/* 860 */       this.topExtent = (PFont.this.size * 2 - minY);
/*     */       
/*     */ 
/* 863 */       this.leftExtent = (minX - PFont.this.size);
/*     */       
/* 865 */       this.image = new PImage(this.width, this.height, 4);
/* 866 */       int[] pixels = this.image.pixels;
/* 867 */       for (int y = minY; y <= maxY; y++) {
/* 868 */         for (int x = minX; x <= maxX; x++) {
/* 869 */           int val = 255 - (PFont.this.lazySamples[(y * mbox3 + x)] & 0xFF);
/* 870 */           int pindex = (y - minY) * this.width + (x - minX);
/* 871 */           pixels[pindex] = val;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 876 */       if ((this.value == 100) && 
/* 877 */         (PFont.this.ascent == 0)) { PFont.this.ascent = this.topExtent;
/*     */       }
/* 879 */       if ((this.value == 112) && 
/* 880 */         (PFont.this.descent == 0)) PFont.this.descent = (-this.topExtent + this.height);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/AndrewKanarek/Desktop/MICHIGAN/KiefferWork/scripts-zibo/JavaApps/EllinghamDiagram/core.jar!/processing/core/PFont.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */