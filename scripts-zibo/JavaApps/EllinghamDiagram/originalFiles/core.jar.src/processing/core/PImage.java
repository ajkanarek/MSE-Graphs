/*      */ package processing.core;
/*      */ 
/*      */ import java.awt.Image;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.awt.image.PixelGrabber;
/*      */ import java.awt.image.WritableRaster;
/*      */ import java.io.BufferedOutputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.util.HashMap;
/*      */ import javax.imageio.ImageIO;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PImage
/*      */   implements PConstants, Cloneable
/*      */ {
/*      */   public int format;
/*      */   public int[] pixels;
/*      */   public int width;
/*      */   public int height;
/*      */   public PApplet parent;
/*      */   protected HashMap<Object, Object> cacheMap;
/*      */   protected boolean modified;
/*      */   protected int mx1;
/*      */   protected int my1;
/*      */   protected int mx2;
/*      */   protected int my2;
/*      */   private int fracU;
/*      */   private int ifU;
/*      */   private int fracV;
/*      */   private int ifV;
/*      */   private int u1;
/*      */   private int u2;
/*      */   private int v1;
/*      */   private int v2;
/*      */   private int sX;
/*      */   private int sY;
/*      */   private int iw;
/*      */   private int iw1;
/*      */   private int ih1;
/*      */   private int ul;
/*      */   private int ll;
/*      */   private int ur;
/*      */   private int lr;
/*      */   private int cUL;
/*      */   private int cLL;
/*      */   private int cUR;
/*      */   private int cLR;
/*      */   private int srcXOffset;
/*      */   private int srcYOffset;
/*      */   private int r;
/*      */   private int g;
/*      */   private int b;
/*      */   private int a;
/*      */   private int[] srcBuffer;
/*      */   static final int PRECISIONB = 15;
/*      */   static final int PRECISIONF = 32768;
/*      */   static final int PREC_MAXVAL = 32767;
/*      */   static final int PREC_ALPHA_SHIFT = 9;
/*      */   static final int PREC_RED_SHIFT = 1;
/*      */   private int blurRadius;
/*      */   private int blurKernelSize;
/*      */   private int[] blurKernel;
/*      */   private int[][] blurMult;
/*      */   
/*      */   public PImage()
/*      */   {
/*  147 */     this.format = 2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public PImage(int width, int height)
/*      */   {
/*  158 */     init(width, height, 1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public PImage(int width, int height, int format)
/*      */   {
/*  177 */     init(width, height, format);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void init(int width, int height, int format)
/*      */   {
/*  189 */     this.width = width;
/*  190 */     this.height = height;
/*  191 */     this.pixels = new int[width * height];
/*  192 */     this.format = format;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void checkAlpha()
/*      */   {
/*  201 */     if (this.pixels == null) { return;
/*      */     }
/*  203 */     for (int i = 0; i < this.pixels.length; i++)
/*      */     {
/*      */ 
/*  206 */       if ((this.pixels[i] & 0xFF000000) != -16777216) {
/*  207 */         this.format = 2;
/*  208 */         break;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public PImage(Image img)
/*      */   {
/*  226 */     this.format = 1;
/*  227 */     if ((img instanceof BufferedImage)) {
/*  228 */       BufferedImage bi = (BufferedImage)img;
/*  229 */       this.width = bi.getWidth();
/*  230 */       this.height = bi.getHeight();
/*  231 */       this.pixels = new int[this.width * this.height];
/*  232 */       WritableRaster raster = bi.getRaster();
/*  233 */       raster.getDataElements(0, 0, this.width, this.height, this.pixels);
/*  234 */       if (bi.getType() == 2) {
/*  235 */         this.format = 2;
/*      */       }
/*      */     }
/*      */     else {
/*  239 */       this.width = img.getWidth(null);
/*  240 */       this.height = img.getHeight(null);
/*  241 */       this.pixels = new int[this.width * this.height];
/*  242 */       PixelGrabber pg = 
/*  243 */         new PixelGrabber(img, 0, 0, this.width, this.height, this.pixels, 0, this.width);
/*      */       try {
/*  245 */         pg.grabPixels();
/*      */       }
/*      */       catch (InterruptedException localInterruptedException) {}
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public Image getImage()
/*      */   {
/*  255 */     loadPixels();
/*  256 */     int type = this.format == 1 ? 
/*  257 */       1 : 2;
/*  258 */     BufferedImage image = new BufferedImage(this.width, this.height, type);
/*  259 */     WritableRaster wr = image.getRaster();
/*  260 */     wr.setDataElements(0, 0, this.width, this.height, this.pixels);
/*  261 */     return image;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setCache(Object parent, Object storage)
/*      */   {
/*  276 */     if (this.cacheMap == null) this.cacheMap = new HashMap();
/*  277 */     this.cacheMap.put(parent, storage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object getCache(Object parent)
/*      */   {
/*  290 */     if (this.cacheMap == null) return null;
/*  291 */     return this.cacheMap.get(parent);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void removeCache(Object parent)
/*      */   {
/*  300 */     if (this.cacheMap != null) {
/*  301 */       this.cacheMap.remove(parent);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isModified()
/*      */   {
/*  313 */     return this.modified;
/*      */   }
/*      */   
/*      */   public void setModified()
/*      */   {
/*  318 */     this.modified = true;
/*      */   }
/*      */   
/*      */   public void setModified(boolean m)
/*      */   {
/*  323 */     this.modified = m;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void loadPixels() {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void updatePixels()
/*      */   {
/*  343 */     updatePixelsImpl(0, 0, this.width, this.height);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void updatePixels(int x, int y, int w, int h)
/*      */   {
/*  373 */     updatePixelsImpl(x, y, w, h);
/*      */   }
/*      */   
/*      */   protected void updatePixelsImpl(int x, int y, int w, int h)
/*      */   {
/*  378 */     int x2 = x + w;
/*  379 */     int y2 = y + h;
/*      */     
/*  381 */     if (!this.modified) {
/*  382 */       this.mx1 = x;
/*  383 */       this.mx2 = x2;
/*  384 */       this.my1 = y;
/*  385 */       this.my2 = y2;
/*  386 */       this.modified = true;
/*      */     }
/*      */     else {
/*  389 */       if (x < this.mx1) this.mx1 = x;
/*  390 */       if (x > this.mx2) this.mx2 = x;
/*  391 */       if (y < this.my1) this.my1 = y;
/*  392 */       if (y > this.my2) { this.my2 = y;
/*      */       }
/*  394 */       if (x2 < this.mx1) this.mx1 = x2;
/*  395 */       if (x2 > this.mx2) this.mx2 = x2;
/*  396 */       if (y2 < this.my1) this.my1 = y2;
/*  397 */       if (y2 > this.my2) { this.my2 = y2;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object clone()
/*      */     throws CloneNotSupportedException
/*      */   {
/*  417 */     PImage c = (PImage)super.clone();
/*      */     
/*      */ 
/*      */ 
/*  421 */     c.pixels = new int[this.width * this.height];
/*  422 */     System.arraycopy(this.pixels, 0, c.pixels, 0, this.pixels.length);
/*      */     
/*      */ 
/*  425 */     return c;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void resize(int wide, int high)
/*      */   {
/*  441 */     loadPixels();
/*      */     
/*  443 */     if ((wide <= 0) && (high <= 0)) {
/*  444 */       this.width = 0;
/*  445 */       this.height = 0;
/*  446 */       this.pixels = new int[0];
/*      */     }
/*      */     else {
/*  449 */       if (wide == 0) {
/*  450 */         float diff = high / this.height;
/*  451 */         wide = (int)(this.width * diff);
/*  452 */       } else if (high == 0) {
/*  453 */         float diff = wide / this.width;
/*  454 */         high = (int)(this.height * diff);
/*      */       }
/*  456 */       PImage temp = new PImage(wide, high, this.format);
/*  457 */       temp.copy(this, 0, 0, this.width, this.height, 0, 0, wide, high);
/*  458 */       this.width = wide;
/*  459 */       this.height = high;
/*  460 */       this.pixels = temp.pixels;
/*      */     }
/*      */     
/*  463 */     updatePixels();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int get(int x, int y)
/*      */   {
/*  492 */     if ((x < 0) || (y < 0) || (x >= this.width) || (y >= this.height)) { return 0;
/*      */     }
/*  494 */     switch (this.format) {
/*      */     case 1: 
/*  496 */       return this.pixels[(y * this.width + x)] | 0xFF000000;
/*      */     
/*      */     case 2: 
/*  499 */       return this.pixels[(y * this.width + x)];
/*      */     
/*      */     case 4: 
/*  502 */       return this.pixels[(y * this.width + x)] << 24 | 0xFFFFFF;
/*      */     }
/*  504 */     return 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public PImage get(int x, int y, int w, int h)
/*      */   {
/*  537 */     if (x < 0) {
/*  538 */       w += x;
/*  539 */       x = 0;
/*      */     }
/*  541 */     if (y < 0) {
/*  542 */       h += y;
/*  543 */       y = 0;
/*      */     }
/*      */     
/*  546 */     if (x + w > this.width) w = this.width - x;
/*  547 */     if (y + h > this.height) { h = this.height - y;
/*      */     }
/*  549 */     return getImpl(x, y, w, h);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected PImage getImpl(int x, int y, int w, int h)
/*      */   {
/*  560 */     PImage newbie = new PImage(w, h, this.format);
/*  561 */     newbie.parent = this.parent;
/*      */     
/*  563 */     int index = y * this.width + x;
/*  564 */     int index2 = 0;
/*  565 */     for (int row = y; row < y + h; row++) {
/*  566 */       System.arraycopy(this.pixels, index, newbie.pixels, index2, w);
/*  567 */       index += this.width;
/*  568 */       index2 += w;
/*      */     }
/*  570 */     return newbie;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public PImage get()
/*      */   {
/*      */     try
/*      */     {
/*  579 */       PImage clone = (PImage)clone();
/*      */       
/*      */ 
/*  582 */       clone.cacheMap = null;
/*  583 */       return clone;
/*      */     } catch (CloneNotSupportedException e) {}
/*  585 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void set(int x, int y, int c)
/*      */   {
/*  604 */     if ((x < 0) || (y < 0) || (x >= this.width) || (y >= this.height)) return;
/*  605 */     this.pixels[(y * this.width + x)] = c;
/*  606 */     updatePixelsImpl(x, y, x + 1, y + 1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void set(int x, int y, PImage src)
/*      */   {
/*  616 */     int sx = 0;
/*  617 */     int sy = 0;
/*  618 */     int sw = src.width;
/*  619 */     int sh = src.height;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  625 */     if (x < 0) {
/*  626 */       sx -= x;
/*  627 */       sw += x;
/*  628 */       x = 0;
/*      */     }
/*  630 */     if (y < 0) {
/*  631 */       sy -= y;
/*  632 */       sh += y;
/*  633 */       y = 0;
/*      */     }
/*  635 */     if (x + sw > this.width) {
/*  636 */       sw = this.width - x;
/*      */     }
/*  638 */     if (y + sh > this.height) {
/*  639 */       sh = this.height - y;
/*      */     }
/*      */     
/*      */ 
/*  643 */     if ((sw <= 0) || (sh <= 0)) { return;
/*      */     }
/*  645 */     setImpl(x, y, sx, sy, sw, sh, src);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void setImpl(int dx, int dy, int sx, int sy, int sw, int sh, PImage src)
/*      */   {
/*  655 */     int srcOffset = sy * src.width + sx;
/*  656 */     int dstOffset = dy * this.width + dx;
/*      */     
/*  658 */     for (int y = sy; y < sy + sh; y++) {
/*  659 */       System.arraycopy(src.pixels, srcOffset, this.pixels, dstOffset, sw);
/*  660 */       srcOffset += src.width;
/*  661 */       dstOffset += this.width;
/*      */     }
/*  663 */     updatePixelsImpl(sx, sy, sx + sw, sy + sh);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void mask(int[] maskArray)
/*      */   {
/*  689 */     loadPixels();
/*      */     
/*  691 */     if (maskArray.length != this.pixels.length) {
/*  692 */       throw new RuntimeException("The PImage used with mask() must be the same size as the applet.");
/*      */     }
/*      */     
/*  695 */     for (int i = 0; i < this.pixels.length; i++) {
/*  696 */       this.pixels[i] = ((maskArray[i] & 0xFF) << 24 | this.pixels[i] & 0xFFFFFF);
/*      */     }
/*  698 */     this.format = 2;
/*  699 */     updatePixels();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void mask(PImage maskImg)
/*      */   {
/*  715 */     maskImg.loadPixels();
/*  716 */     mask(maskImg.pixels);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void filter(int kind)
/*      */   {
/*  725 */     loadPixels();
/*      */     
/*  727 */     switch (kind)
/*      */     {
/*      */ 
/*      */ 
/*      */     case 11: 
/*  732 */       filter(11, 1.0F);
/*  733 */       break;
/*      */     
/*      */     case 12: 
/*  736 */       if (this.format == 4)
/*      */       {
/*  738 */         for (int i = 0; i < this.pixels.length; i++) {
/*  739 */           int col = 255 - this.pixels[i];
/*  740 */           this.pixels[i] = (0xFF000000 | col << 16 | col << 8 | col);
/*      */         }
/*  742 */         this.format = 1;
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*      */ 
/*  748 */         for (int i = 0; i < this.pixels.length; i++) {
/*  749 */           int col = this.pixels[i];
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*  754 */           int lum = 77 * (col >> 16 & 0xFF) + 151 * (col >> 8 & 0xFF) + 28 * (col & 0xFF) >> 8;
/*  755 */           this.pixels[i] = (col & 0xFF000000 | lum << 16 | lum << 8 | lum);
/*      */         }
/*      */       }
/*  758 */       break;
/*      */     
/*      */     case 13: 
/*  761 */       for (int i = 0; i < this.pixels.length; i++)
/*      */       {
/*  763 */         this.pixels[i] ^= 0xFFFFFF;
/*      */       }
/*  765 */       break;
/*      */     
/*      */     case 15: 
/*  768 */       throw new RuntimeException("Use filter(POSTERIZE, int levels) instead of filter(POSTERIZE)");
/*      */     
/*      */ 
/*      */     case 14: 
/*  772 */       for (int i = 0; i < this.pixels.length; i++) {
/*  773 */         this.pixels[i] |= 0xFF000000;
/*      */       }
/*  775 */       this.format = 1;
/*  776 */       break;
/*      */     
/*      */     case 16: 
/*  779 */       filter(16, 0.5F);
/*  780 */       break;
/*      */     
/*      */ 
/*      */     case 17: 
/*  784 */       dilate(true);
/*  785 */       break;
/*      */     
/*      */     case 18: 
/*  788 */       dilate(false);
/*      */     }
/*      */     
/*  791 */     updatePixels();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void filter(int kind, float param)
/*      */   {
/*  821 */     loadPixels();
/*      */     
/*  823 */     switch (kind) {
/*      */     case 11: 
/*  825 */       if (this.format == 4) {
/*  826 */         blurAlpha(param);
/*  827 */       } else if (this.format == 2) {
/*  828 */         blurARGB(param);
/*      */       } else
/*  830 */         blurRGB(param);
/*  831 */       break;
/*      */     
/*      */     case 12: 
/*  834 */       throw new RuntimeException("Use filter(GRAY) instead of filter(GRAY, param)");
/*      */     
/*      */ 
/*      */     case 13: 
/*  838 */       throw new RuntimeException("Use filter(INVERT) instead of filter(INVERT, param)");
/*      */     
/*      */ 
/*      */     case 14: 
/*  842 */       throw new RuntimeException("Use filter(OPAQUE) instead of filter(OPAQUE, param)");
/*      */     
/*      */ 
/*      */     case 15: 
/*  846 */       int levels = (int)param;
/*  847 */       if ((levels < 2) || (levels > 255)) {
/*  848 */         throw new RuntimeException("Levels must be between 2 and 255 for filter(POSTERIZE, levels)");
/*      */       }
/*      */       
/*  851 */       int levels1 = levels - 1;
/*  852 */       for (int i = 0; i < this.pixels.length; i++) {
/*  853 */         int rlevel = this.pixels[i] >> 16 & 0xFF;
/*  854 */         int glevel = this.pixels[i] >> 8 & 0xFF;
/*  855 */         int blevel = this.pixels[i] & 0xFF;
/*  856 */         rlevel = (rlevel * levels >> 8) * 255 / levels1;
/*  857 */         glevel = (glevel * levels >> 8) * 255 / levels1;
/*  858 */         blevel = (blevel * levels >> 8) * 255 / levels1;
/*  859 */         this.pixels[i] = 
/*      */         
/*      */ 
/*  862 */           (0xFF000000 & this.pixels[i] | rlevel << 16 | glevel << 8 | blevel);
/*      */       }
/*  864 */       break;
/*      */     
/*      */     case 16: 
/*  867 */       int thresh = (int)(param * 255.0F);
/*  868 */       for (int i = 0; i < this.pixels.length; i++) {
/*  869 */         int max = Math.max((this.pixels[i] & 0xFF0000) >> 16, 
/*  870 */           Math.max((this.pixels[i] & 0xFF00) >> 8, 
/*  871 */           this.pixels[i] & 0xFF));
/*  872 */         this.pixels[i] = 
/*  873 */           (this.pixels[i] & 0xFF000000 | (max < thresh ? 0 : 16777215));
/*      */       }
/*  875 */       break;
/*      */     
/*      */ 
/*      */     case 17: 
/*  879 */       throw new RuntimeException("Use filter(ERODE) instead of filter(ERODE, param)");
/*      */     
/*      */     case 18: 
/*  882 */       throw new RuntimeException("Use filter(DILATE) instead of filter(DILATE, param)");
/*      */     }
/*      */     
/*  885 */     updatePixels();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void buildBlurKernel(float r)
/*      */   {
/*  897 */     int radius = (int)(r * 3.5F);
/*  898 */     radius = radius < 248 ? radius : radius < 1 ? 1 : 248;
/*  899 */     if (this.blurRadius != radius) {
/*  900 */       this.blurRadius = radius;
/*  901 */       this.blurKernelSize = (1 + this.blurRadius << 1);
/*  902 */       this.blurKernel = new int[this.blurKernelSize];
/*  903 */       this.blurMult = new int[this.blurKernelSize]['Ā'];
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  908 */       int i = 1; for (int radiusi = radius - 1; i < radius; i++) { int bki;
/*  909 */         this.blurKernel[(radius + i)] = (this.blurKernel[radiusi] = bki = radiusi * radiusi);
/*  910 */         int[] bm = this.blurMult[(radius + i)];
/*  911 */         int[] bmi = this.blurMult[(radiusi--)];
/*  912 */         for (int j = 0; j < 256; j++)
/*  913 */           bm[j] = (bmi[j] = bki * j);
/*      */       }
/*  915 */       int bk = this.blurKernel[radius] = radius * radius;
/*  916 */       int[] bm = this.blurMult[radius];
/*  917 */       for (int j = 0; j < 256; j++) {
/*  918 */         bm[j] = (bk * j);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   protected void blurAlpha(float r)
/*      */   {
/*  926 */     int[] b2 = new int[this.pixels.length];
/*  927 */     int yi = 0;
/*      */     
/*  929 */     buildBlurKernel(r);
/*      */     
/*  931 */     for (int y = 0; y < this.height; y++) {
/*  932 */       for (int x = 0; x < this.width; x++) {
/*      */         int sum;
/*  934 */         int cb = sum = 0;
/*  935 */         int read = x - this.blurRadius;
/*  936 */         int bk0; if (read < 0) {
/*  937 */           int bk0 = -read;
/*  938 */           read = 0;
/*      */         } else {
/*  940 */           if (read >= this.width)
/*      */             break;
/*  942 */           bk0 = 0;
/*      */         }
/*  944 */         for (int i = bk0; i < this.blurKernelSize; i++) {
/*  945 */           if (read >= this.width)
/*      */             break;
/*  947 */           int c = this.pixels[(read + yi)];
/*  948 */           int[] bm = this.blurMult[i];
/*  949 */           cb += bm[(c & 0xFF)];
/*  950 */           sum += this.blurKernel[i];
/*  951 */           read++;
/*      */         }
/*  953 */         int ri = yi + x;
/*  954 */         b2[ri] = (cb / sum);
/*      */       }
/*  956 */       yi += this.width;
/*      */     }
/*      */     
/*  959 */     yi = 0;
/*  960 */     int ym = -this.blurRadius;
/*  961 */     int ymi = ym * this.width;
/*      */     
/*  963 */     for (int y = 0; y < this.height; y++) {
/*  964 */       for (int x = 0; x < this.width; x++) {
/*      */         int sum;
/*  966 */         int cb = sum = 0;
/*  967 */         int read; int bk0; int ri; int read; if (ym < 0) { int ri;
/*  968 */           int bk0 = ri = -ym;
/*  969 */           read = x;
/*      */         } else {
/*  971 */           if (ym >= this.height)
/*      */             break;
/*  973 */           bk0 = 0;
/*  974 */           ri = ym;
/*  975 */           read = x + ymi;
/*      */         }
/*  977 */         for (int i = bk0; i < this.blurKernelSize; i++) {
/*  978 */           if (ri >= this.height)
/*      */             break;
/*  980 */           int[] bm = this.blurMult[i];
/*  981 */           cb += bm[b2[read]];
/*  982 */           sum += this.blurKernel[i];
/*  983 */           ri++;
/*  984 */           read += this.width;
/*      */         }
/*  986 */         this.pixels[(x + yi)] = (cb / sum);
/*      */       }
/*  988 */       yi += this.width;
/*  989 */       ymi += this.width;
/*  990 */       ym++;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected void blurRGB(float r)
/*      */   {
/*  998 */     int[] r2 = new int[this.pixels.length];
/*  999 */     int[] g2 = new int[this.pixels.length];
/* 1000 */     int[] b2 = new int[this.pixels.length];
/* 1001 */     int yi = 0;
/*      */     
/* 1003 */     buildBlurKernel(r);
/*      */     
/* 1005 */     for (int y = 0; y < this.height; y++) {
/* 1006 */       for (int x = 0; x < this.width; x++) { int sum;
/* 1007 */         int cr; int cg; int cb = cg = cr = sum = 0;
/* 1008 */         int read = x - this.blurRadius;
/* 1009 */         int bk0; if (read < 0) {
/* 1010 */           int bk0 = -read;
/* 1011 */           read = 0;
/*      */         } else {
/* 1013 */           if (read >= this.width)
/*      */             break;
/* 1015 */           bk0 = 0;
/*      */         }
/* 1017 */         for (int i = bk0; i < this.blurKernelSize; i++) {
/* 1018 */           if (read >= this.width)
/*      */             break;
/* 1020 */           int c = this.pixels[(read + yi)];
/* 1021 */           int[] bm = this.blurMult[i];
/* 1022 */           cr += bm[((c & 0xFF0000) >> 16)];
/* 1023 */           cg += bm[((c & 0xFF00) >> 8)];
/* 1024 */           cb += bm[(c & 0xFF)];
/* 1025 */           sum += this.blurKernel[i];
/* 1026 */           read++;
/*      */         }
/* 1028 */         int ri = yi + x;
/* 1029 */         r2[ri] = (cr / sum);
/* 1030 */         g2[ri] = (cg / sum);
/* 1031 */         b2[ri] = (cb / sum);
/*      */       }
/* 1033 */       yi += this.width;
/*      */     }
/*      */     
/* 1036 */     yi = 0;
/* 1037 */     int ym = -this.blurRadius;
/* 1038 */     int ymi = ym * this.width;
/*      */     
/* 1040 */     for (int y = 0; y < this.height; y++) {
/* 1041 */       for (int x = 0; x < this.width; x++) { int sum;
/* 1042 */         int cr; int cg; int cb = cg = cr = sum = 0;
/* 1043 */         int read; int bk0; int ri; int read; if (ym < 0) { int ri;
/* 1044 */           int bk0 = ri = -ym;
/* 1045 */           read = x;
/*      */         } else {
/* 1047 */           if (ym >= this.height)
/*      */             break;
/* 1049 */           bk0 = 0;
/* 1050 */           ri = ym;
/* 1051 */           read = x + ymi;
/*      */         }
/* 1053 */         for (int i = bk0; i < this.blurKernelSize; i++) {
/* 1054 */           if (ri >= this.height)
/*      */             break;
/* 1056 */           int[] bm = this.blurMult[i];
/* 1057 */           cr += bm[r2[read]];
/* 1058 */           cg += bm[g2[read]];
/* 1059 */           cb += bm[b2[read]];
/* 1060 */           sum += this.blurKernel[i];
/* 1061 */           ri++;
/* 1062 */           read += this.width;
/*      */         }
/* 1064 */         this.pixels[(x + yi)] = (0xFF000000 | cr / sum << 16 | cg / sum << 8 | cb / sum);
/*      */       }
/* 1066 */       yi += this.width;
/* 1067 */       ymi += this.width;
/* 1068 */       ym++;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected void blurARGB(float r)
/*      */   {
/* 1076 */     int wh = this.pixels.length;
/* 1077 */     int[] r2 = new int[wh];
/* 1078 */     int[] g2 = new int[wh];
/* 1079 */     int[] b2 = new int[wh];
/* 1080 */     int[] a2 = new int[wh];
/* 1081 */     int yi = 0;
/*      */     
/* 1083 */     buildBlurKernel(r);
/*      */     
/* 1085 */     for (int y = 0; y < this.height; y++) {
/* 1086 */       for (int x = 0; x < this.width; x++) { int sum;
/* 1087 */         int ca; int cr; int cg; int cb = cg = cr = ca = sum = 0;
/* 1088 */         int read = x - this.blurRadius;
/* 1089 */         int bk0; if (read < 0) {
/* 1090 */           int bk0 = -read;
/* 1091 */           read = 0;
/*      */         } else {
/* 1093 */           if (read >= this.width)
/*      */             break;
/* 1095 */           bk0 = 0;
/*      */         }
/* 1097 */         for (int i = bk0; i < this.blurKernelSize; i++) {
/* 1098 */           if (read >= this.width)
/*      */             break;
/* 1100 */           int c = this.pixels[(read + yi)];
/* 1101 */           int[] bm = this.blurMult[i];
/* 1102 */           ca += bm[((c & 0xFF000000) >>> 24)];
/* 1103 */           cr += bm[((c & 0xFF0000) >> 16)];
/* 1104 */           cg += bm[((c & 0xFF00) >> 8)];
/* 1105 */           cb += bm[(c & 0xFF)];
/* 1106 */           sum += this.blurKernel[i];
/* 1107 */           read++;
/*      */         }
/* 1109 */         int ri = yi + x;
/* 1110 */         a2[ri] = (ca / sum);
/* 1111 */         r2[ri] = (cr / sum);
/* 1112 */         g2[ri] = (cg / sum);
/* 1113 */         b2[ri] = (cb / sum);
/*      */       }
/* 1115 */       yi += this.width;
/*      */     }
/*      */     
/* 1118 */     yi = 0;
/* 1119 */     int ym = -this.blurRadius;
/* 1120 */     int ymi = ym * this.width;
/*      */     
/* 1122 */     for (int y = 0; y < this.height; y++) {
/* 1123 */       for (int x = 0; x < this.width; x++) { int sum;
/* 1124 */         int ca; int cr; int cg; int cb = cg = cr = ca = sum = 0;
/* 1125 */         int read; int bk0; int ri; int read; if (ym < 0) { int ri;
/* 1126 */           int bk0 = ri = -ym;
/* 1127 */           read = x;
/*      */         } else {
/* 1129 */           if (ym >= this.height)
/*      */             break;
/* 1131 */           bk0 = 0;
/* 1132 */           ri = ym;
/* 1133 */           read = x + ymi;
/*      */         }
/* 1135 */         for (int i = bk0; i < this.blurKernelSize; i++) {
/* 1136 */           if (ri >= this.height)
/*      */             break;
/* 1138 */           int[] bm = this.blurMult[i];
/* 1139 */           ca += bm[a2[read]];
/* 1140 */           cr += bm[r2[read]];
/* 1141 */           cg += bm[g2[read]];
/* 1142 */           cb += bm[b2[read]];
/* 1143 */           sum += this.blurKernel[i];
/* 1144 */           ri++;
/* 1145 */           read += this.width;
/*      */         }
/* 1147 */         this.pixels[(x + yi)] = (ca / sum << 24 | cr / sum << 16 | cg / sum << 8 | cb / sum);
/*      */       }
/* 1149 */       yi += this.width;
/* 1150 */       ymi += this.width;
/* 1151 */       ym++;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void dilate(boolean isInverted)
/*      */   {
/* 1161 */     int currIdx = 0;
/* 1162 */     int maxIdx = this.pixels.length;
/* 1163 */     int[] out = new int[maxIdx];
/*      */     
/* 1165 */     if (!isInverted)
/*      */     {
/* 1167 */       for (; currIdx < maxIdx; 
/*      */           
/*      */ 
/* 1170 */           currIdx < maxRowIdx)
/*      */       {
/* 1168 */         currRowIdx = currIdx;
/* 1169 */         maxRowIdx = currIdx + this.width;
/* 1170 */         continue;
/*      */         
/* 1172 */         colOrig = colOut = this.pixels[currIdx];
/* 1173 */         idxLeft = currIdx - 1;
/* 1174 */         idxRight = currIdx + 1;
/* 1175 */         idxUp = currIdx - this.width;
/* 1176 */         idxDown = currIdx + this.width;
/* 1177 */         if (idxLeft < currRowIdx)
/* 1178 */           idxLeft = currIdx;
/* 1179 */         if (idxRight >= maxRowIdx)
/* 1180 */           idxRight = currIdx;
/* 1181 */         if (idxUp < 0)
/* 1182 */           idxUp = currIdx;
/* 1183 */         if (idxDown >= maxIdx) {
/* 1184 */           idxDown = currIdx;
/*      */         }
/* 1186 */         colUp = this.pixels[idxUp];
/* 1187 */         colLeft = this.pixels[idxLeft];
/* 1188 */         colDown = this.pixels[idxDown];
/* 1189 */         colRight = this.pixels[idxRight];
/*      */         
/*      */ 
/* 1192 */         currLum = 
/* 1193 */           77 * (colOrig >> 16 & 0xFF) + 151 * (colOrig >> 8 & 0xFF) + 28 * (colOrig & 0xFF);
/* 1194 */         lumLeft = 
/* 1195 */           77 * (colLeft >> 16 & 0xFF) + 151 * (colLeft >> 8 & 0xFF) + 28 * (colLeft & 0xFF);
/* 1196 */         lumRight = 
/* 1197 */           77 * (colRight >> 16 & 0xFF) + 151 * (colRight >> 8 & 0xFF) + 28 * (colRight & 0xFF);
/* 1198 */         lumUp = 
/* 1199 */           77 * (colUp >> 16 & 0xFF) + 151 * (colUp >> 8 & 0xFF) + 28 * (colUp & 0xFF);
/* 1200 */         lumDown = 
/* 1201 */           77 * (colDown >> 16 & 0xFF) + 151 * (colDown >> 8 & 0xFF) + 28 * (colDown & 0xFF);
/*      */         
/* 1203 */         if (lumLeft > currLum) {
/* 1204 */           colOut = colLeft;
/* 1205 */           currLum = lumLeft;
/*      */         }
/* 1207 */         if (lumRight > currLum) {
/* 1208 */           colOut = colRight;
/* 1209 */           currLum = lumRight;
/*      */         }
/* 1211 */         if (lumUp > currLum) {
/* 1212 */           colOut = colUp;
/* 1213 */           currLum = lumUp;
/*      */         }
/* 1215 */         if (lumDown > currLum) {
/* 1216 */           colOut = colDown;
/* 1217 */           currLum = lumDown;
/*      */         }
/* 1219 */         out[(currIdx++)] = colOut;
/*      */       }
/*      */     }
/*      */     else {
/*      */       int maxRowIdx;
/* 1224 */       for (; currIdx < maxIdx; 
/*      */           
/*      */ 
/* 1227 */           currIdx < maxRowIdx)
/*      */       {
/*      */         int currRowIdx;
/*      */         int maxRowIdx;
/*      */         int colOut;
/*      */         int colOrig;
/*      */         int idxLeft;
/*      */         int idxRight;
/*      */         int idxUp;
/*      */         int idxDown;
/*      */         int colUp;
/*      */         int colLeft;
/*      */         int colDown;
/*      */         int colRight;
/*      */         int currLum;
/*      */         int lumLeft;
/*      */         int lumRight;
/*      */         int lumUp;
/*      */         int lumDown;
/* 1225 */         int currRowIdx = currIdx;
/* 1226 */         maxRowIdx = currIdx + this.width;
/* 1227 */         continue;
/*      */         int colOut;
/* 1229 */         int colOrig = colOut = this.pixels[currIdx];
/* 1230 */         int idxLeft = currIdx - 1;
/* 1231 */         int idxRight = currIdx + 1;
/* 1232 */         int idxUp = currIdx - this.width;
/* 1233 */         int idxDown = currIdx + this.width;
/* 1234 */         if (idxLeft < currRowIdx)
/* 1235 */           idxLeft = currIdx;
/* 1236 */         if (idxRight >= maxRowIdx)
/* 1237 */           idxRight = currIdx;
/* 1238 */         if (idxUp < 0)
/* 1239 */           idxUp = currIdx;
/* 1240 */         if (idxDown >= maxIdx) {
/* 1241 */           idxDown = currIdx;
/*      */         }
/* 1243 */         int colUp = this.pixels[idxUp];
/* 1244 */         int colLeft = this.pixels[idxLeft];
/* 1245 */         int colDown = this.pixels[idxDown];
/* 1246 */         int colRight = this.pixels[idxRight];
/*      */         
/*      */ 
/* 1249 */         int currLum = 
/* 1250 */           77 * (colOrig >> 16 & 0xFF) + 151 * (colOrig >> 8 & 0xFF) + 28 * (colOrig & 0xFF);
/* 1251 */         int lumLeft = 
/* 1252 */           77 * (colLeft >> 16 & 0xFF) + 151 * (colLeft >> 8 & 0xFF) + 28 * (colLeft & 0xFF);
/* 1253 */         int lumRight = 
/* 1254 */           77 * (colRight >> 16 & 0xFF) + 151 * (colRight >> 8 & 0xFF) + 28 * (colRight & 0xFF);
/* 1255 */         int lumUp = 
/* 1256 */           77 * (colUp >> 16 & 0xFF) + 151 * (colUp >> 8 & 0xFF) + 28 * (colUp & 0xFF);
/* 1257 */         int lumDown = 
/* 1258 */           77 * (colDown >> 16 & 0xFF) + 151 * (colDown >> 8 & 0xFF) + 28 * (colDown & 0xFF);
/*      */         
/* 1260 */         if (lumLeft < currLum) {
/* 1261 */           colOut = colLeft;
/* 1262 */           currLum = lumLeft;
/*      */         }
/* 1264 */         if (lumRight < currLum) {
/* 1265 */           colOut = colRight;
/* 1266 */           currLum = lumRight;
/*      */         }
/* 1268 */         if (lumUp < currLum) {
/* 1269 */           colOut = colUp;
/* 1270 */           currLum = lumUp;
/*      */         }
/* 1272 */         if (lumDown < currLum) {
/* 1273 */           colOut = colDown;
/* 1274 */           currLum = lumDown;
/*      */         }
/* 1276 */         out[(currIdx++)] = colOut;
/*      */       }
/*      */     }
/*      */     
/* 1280 */     System.arraycopy(out, 0, this.pixels, 0, maxIdx);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void copy(int sx, int sy, int sw, int sh, int dx, int dy, int dw, int dh)
/*      */   {
/* 1296 */     blend(this, sx, sy, sw, sh, dx, dy, dw, dh, 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void copy(PImage src, int sx, int sy, int sw, int sh, int dx, int dy, int dw, int dh)
/*      */   {
/* 1322 */     blend(src, sx, sy, sw, sh, dx, dy, dw, dh, 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int blendColor(int c1, int c2, int mode)
/*      */   {
/* 1397 */     switch (mode) {
/* 1398 */     case 0:  return c2;
/* 1399 */     case 1:  return blend_blend(c1, c2);
/*      */     case 2: 
/* 1401 */       return blend_add_pin(c1, c2);
/* 1402 */     case 4:  return blend_sub_pin(c1, c2);
/*      */     case 8: 
/* 1404 */       return blend_lightest(c1, c2);
/* 1405 */     case 16:  return blend_darkest(c1, c2);
/*      */     case 32: 
/* 1407 */       return blend_difference(c1, c2);
/* 1408 */     case 64:  return blend_exclusion(c1, c2);
/*      */     case 128: 
/* 1410 */       return blend_multiply(c1, c2);
/* 1411 */     case 256:  return blend_screen(c1, c2);
/*      */     case 1024: 
/* 1413 */       return blend_hard_light(c1, c2);
/* 1414 */     case 2048:  return blend_soft_light(c1, c2);
/* 1415 */     case 512:  return blend_overlay(c1, c2);
/*      */     case 4096: 
/* 1417 */       return blend_dodge(c1, c2);
/* 1418 */     case 8192:  return blend_burn(c1, c2);
/*      */     }
/* 1420 */     return 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void blend(int sx, int sy, int sw, int sh, int dx, int dy, int dw, int dh, int mode)
/*      */   {
/* 1431 */     blend(this, sx, sy, sw, sh, dx, dy, dw, dh, mode);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void blend(PImage src, int sx, int sy, int sw, int sh, int dx, int dy, int dw, int dh, int mode)
/*      */   {
/* 1492 */     int sx2 = sx + sw;
/* 1493 */     int sy2 = sy + sh;
/* 1494 */     int dx2 = dx + dw;
/* 1495 */     int dy2 = dy + dh;
/*      */     
/* 1497 */     loadPixels();
/* 1498 */     if (src == this) {
/* 1499 */       if (intersect(sx, sy, sx2, sy2, dx, dy, dx2, dy2)) {
/* 1500 */         blit_resize(get(sx, sy, sx2 - sx, sy2 - sy), 
/* 1501 */           0, 0, sx2 - sx - 1, sy2 - sy - 1, 
/* 1502 */           this.pixels, this.width, this.height, dx, dy, dx2, dy2, mode);
/*      */       }
/*      */       else {
/* 1505 */         blit_resize(src, sx, sy, sx2, sy2, 
/* 1506 */           this.pixels, this.width, this.height, dx, dy, dx2, dy2, mode);
/*      */       }
/*      */     } else {
/* 1509 */       src.loadPixels();
/* 1510 */       blit_resize(src, sx, sy, sx2, sy2, 
/* 1511 */         this.pixels, this.width, this.height, dx, dy, dx2, dy2, mode);
/*      */     }
/*      */     
/* 1514 */     updatePixels();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean intersect(int sx1, int sy1, int sx2, int sy2, int dx1, int dy1, int dx2, int dy2)
/*      */   {
/* 1523 */     int sw = sx2 - sx1 + 1;
/* 1524 */     int sh = sy2 - sy1 + 1;
/* 1525 */     int dw = dx2 - dx1 + 1;
/* 1526 */     int dh = dy2 - dy1 + 1;
/*      */     
/* 1528 */     if (dx1 < sx1) {
/* 1529 */       dw += dx1 - sx1;
/* 1530 */       if (dw > sw) {
/* 1531 */         dw = sw;
/*      */       }
/*      */     } else {
/* 1534 */       int w = sw + sx1 - dx1;
/* 1535 */       if (dw > w) {
/* 1536 */         dw = w;
/*      */       }
/*      */     }
/* 1539 */     if (dy1 < sy1) {
/* 1540 */       dh += dy1 - sy1;
/* 1541 */       if (dh > sh) {
/* 1542 */         dh = sh;
/*      */       }
/*      */     } else {
/* 1545 */       int h = sh + sy1 - dy1;
/* 1546 */       if (dh > h) {
/* 1547 */         dh = h;
/*      */       }
/*      */     }
/* 1550 */     return (dw > 0) && (dh > 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void blit_resize(PImage img, int srcX1, int srcY1, int srcX2, int srcY2, int[] destPixels, int screenW, int screenH, int destX1, int destY1, int destX2, int destY2, int mode)
/*      */   {
/* 1567 */     if (srcX1 < 0) srcX1 = 0;
/* 1568 */     if (srcY1 < 0) srcY1 = 0;
/* 1569 */     if (srcX2 > img.width) srcX2 = img.width;
/* 1570 */     if (srcY2 > img.height) { srcY2 = img.height;
/*      */     }
/* 1572 */     int srcW = srcX2 - srcX1;
/* 1573 */     int srcH = srcY2 - srcY1;
/* 1574 */     int destW = destX2 - destX1;
/* 1575 */     int destH = destY2 - destY1;
/*      */     
/* 1577 */     boolean smooth = true;
/*      */     
/* 1579 */     if (!smooth) {
/* 1580 */       srcW++;srcH++;
/*      */     }
/*      */     
/* 1583 */     if ((destW <= 0) || (destH <= 0) || 
/* 1584 */       (srcW <= 0) || (srcH <= 0) || 
/* 1585 */       (destX1 >= screenW) || (destY1 >= screenH) || 
/* 1586 */       (srcX1 >= img.width) || (srcY1 >= img.height)) {
/* 1587 */       return;
/*      */     }
/*      */     
/* 1590 */     int dx = (int)(srcW / destW * 32768.0F);
/* 1591 */     int dy = (int)(srcH / destH * 32768.0F);
/*      */     
/* 1593 */     this.srcXOffset = (destX1 < 0 ? -destX1 * dx : srcX1 * 32768);
/* 1594 */     this.srcYOffset = (destY1 < 0 ? -destY1 * dy : srcY1 * 32768);
/*      */     
/* 1596 */     if (destX1 < 0) {
/* 1597 */       destW += destX1;
/* 1598 */       destX1 = 0;
/*      */     }
/* 1600 */     if (destY1 < 0) {
/* 1601 */       destH += destY1;
/* 1602 */       destY1 = 0;
/*      */     }
/*      */     
/* 1605 */     destW = low(destW, screenW - destX1);
/* 1606 */     destH = low(destH, screenH - destY1);
/*      */     
/* 1608 */     int destOffset = destY1 * screenW + destX1;
/* 1609 */     this.srcBuffer = img.pixels;
/*      */     
/* 1611 */     if (smooth)
/*      */     {
/* 1613 */       this.iw = img.width;
/* 1614 */       this.iw1 = (img.width - 1);
/* 1615 */       this.ih1 = (img.height - 1);
/*      */       
/* 1617 */       switch (mode)
/*      */       {
/*      */       case 1: 
/* 1620 */         for (int y = 0; y < destH; y++) {
/* 1621 */           filter_new_scanline();
/* 1622 */           for (int x = 0; x < destW; x++)
/*      */           {
/* 1624 */             destPixels[(destOffset + x)] = 
/* 1625 */               blend_blend(destPixels[(destOffset + x)], filter_bilinear());
/* 1626 */             this.sX += dx;
/*      */           }
/* 1628 */           destOffset += screenW;
/* 1629 */           this.srcYOffset += dy;
/*      */         }
/* 1631 */         break;
/*      */       
/*      */       case 2: 
/* 1634 */         for (int y = 0; y < destH; y++) {
/* 1635 */           filter_new_scanline();
/* 1636 */           for (int x = 0; x < destW; x++) {
/* 1637 */             destPixels[(destOffset + x)] = 
/* 1638 */               blend_add_pin(destPixels[(destOffset + x)], filter_bilinear());
/* 1639 */             this.sX += dx;
/*      */           }
/* 1641 */           destOffset += screenW;
/* 1642 */           this.srcYOffset += dy;
/*      */         }
/* 1644 */         break;
/*      */       
/*      */       case 4: 
/* 1647 */         for (int y = 0; y < destH; y++) {
/* 1648 */           filter_new_scanline();
/* 1649 */           for (int x = 0; x < destW; x++) {
/* 1650 */             destPixels[(destOffset + x)] = 
/* 1651 */               blend_sub_pin(destPixels[(destOffset + x)], filter_bilinear());
/* 1652 */             this.sX += dx;
/*      */           }
/* 1654 */           destOffset += screenW;
/* 1655 */           this.srcYOffset += dy;
/*      */         }
/* 1657 */         break;
/*      */       
/*      */       case 8: 
/* 1660 */         for (int y = 0; y < destH; y++) {
/* 1661 */           filter_new_scanline();
/* 1662 */           for (int x = 0; x < destW; x++) {
/* 1663 */             destPixels[(destOffset + x)] = 
/* 1664 */               blend_lightest(destPixels[(destOffset + x)], filter_bilinear());
/* 1665 */             this.sX += dx;
/*      */           }
/* 1667 */           destOffset += screenW;
/* 1668 */           this.srcYOffset += dy;
/*      */         }
/* 1670 */         break;
/*      */       
/*      */       case 16: 
/* 1673 */         for (int y = 0; y < destH; y++) {
/* 1674 */           filter_new_scanline();
/* 1675 */           for (int x = 0; x < destW; x++) {
/* 1676 */             destPixels[(destOffset + x)] = 
/* 1677 */               blend_darkest(destPixels[(destOffset + x)], filter_bilinear());
/* 1678 */             this.sX += dx;
/*      */           }
/* 1680 */           destOffset += screenW;
/* 1681 */           this.srcYOffset += dy;
/*      */         }
/* 1683 */         break;
/*      */       
/*      */       case 0: 
/* 1686 */         for (int y = 0; y < destH; y++) {
/* 1687 */           filter_new_scanline();
/* 1688 */           for (int x = 0; x < destW; x++) {
/* 1689 */             destPixels[(destOffset + x)] = filter_bilinear();
/* 1690 */             this.sX += dx;
/*      */           }
/* 1692 */           destOffset += screenW;
/* 1693 */           this.srcYOffset += dy;
/*      */         }
/* 1695 */         break;
/*      */       
/*      */       case 32: 
/* 1698 */         for (int y = 0; y < destH; y++) {
/* 1699 */           filter_new_scanline();
/* 1700 */           for (int x = 0; x < destW; x++) {
/* 1701 */             destPixels[(destOffset + x)] = 
/* 1702 */               blend_difference(destPixels[(destOffset + x)], filter_bilinear());
/* 1703 */             this.sX += dx;
/*      */           }
/* 1705 */           destOffset += screenW;
/* 1706 */           this.srcYOffset += dy;
/*      */         }
/* 1708 */         break;
/*      */       
/*      */       case 64: 
/* 1711 */         for (int y = 0; y < destH; y++) {
/* 1712 */           filter_new_scanline();
/* 1713 */           for (int x = 0; x < destW; x++) {
/* 1714 */             destPixels[(destOffset + x)] = 
/* 1715 */               blend_exclusion(destPixels[(destOffset + x)], filter_bilinear());
/* 1716 */             this.sX += dx;
/*      */           }
/* 1718 */           destOffset += screenW;
/* 1719 */           this.srcYOffset += dy;
/*      */         }
/* 1721 */         break;
/*      */       
/*      */       case 128: 
/* 1724 */         for (int y = 0; y < destH; y++) {
/* 1725 */           filter_new_scanline();
/* 1726 */           for (int x = 0; x < destW; x++) {
/* 1727 */             destPixels[(destOffset + x)] = 
/* 1728 */               blend_multiply(destPixels[(destOffset + x)], filter_bilinear());
/* 1729 */             this.sX += dx;
/*      */           }
/* 1731 */           destOffset += screenW;
/* 1732 */           this.srcYOffset += dy;
/*      */         }
/* 1734 */         break;
/*      */       
/*      */       case 256: 
/* 1737 */         for (int y = 0; y < destH; y++) {
/* 1738 */           filter_new_scanline();
/* 1739 */           for (int x = 0; x < destW; x++) {
/* 1740 */             destPixels[(destOffset + x)] = 
/* 1741 */               blend_screen(destPixels[(destOffset + x)], filter_bilinear());
/* 1742 */             this.sX += dx;
/*      */           }
/* 1744 */           destOffset += screenW;
/* 1745 */           this.srcYOffset += dy;
/*      */         }
/* 1747 */         break;
/*      */       
/*      */       case 512: 
/* 1750 */         for (int y = 0; y < destH; y++) {
/* 1751 */           filter_new_scanline();
/* 1752 */           for (int x = 0; x < destW; x++) {
/* 1753 */             destPixels[(destOffset + x)] = 
/* 1754 */               blend_overlay(destPixels[(destOffset + x)], filter_bilinear());
/* 1755 */             this.sX += dx;
/*      */           }
/* 1757 */           destOffset += screenW;
/* 1758 */           this.srcYOffset += dy;
/*      */         }
/* 1760 */         break;
/*      */       
/*      */       case 1024: 
/* 1763 */         for (int y = 0; y < destH; y++) {
/* 1764 */           filter_new_scanline();
/* 1765 */           for (int x = 0; x < destW; x++) {
/* 1766 */             destPixels[(destOffset + x)] = 
/* 1767 */               blend_hard_light(destPixels[(destOffset + x)], filter_bilinear());
/* 1768 */             this.sX += dx;
/*      */           }
/* 1770 */           destOffset += screenW;
/* 1771 */           this.srcYOffset += dy;
/*      */         }
/* 1773 */         break;
/*      */       
/*      */       case 2048: 
/* 1776 */         for (int y = 0; y < destH; y++) {
/* 1777 */           filter_new_scanline();
/* 1778 */           for (int x = 0; x < destW; x++) {
/* 1779 */             destPixels[(destOffset + x)] = 
/* 1780 */               blend_soft_light(destPixels[(destOffset + x)], filter_bilinear());
/* 1781 */             this.sX += dx;
/*      */           }
/* 1783 */           destOffset += screenW;
/* 1784 */           this.srcYOffset += dy;
/*      */         }
/* 1786 */         break;
/*      */       
/*      */ 
/*      */       case 4096: 
/* 1790 */         for (int y = 0; y < destH; y++) {
/* 1791 */           filter_new_scanline();
/* 1792 */           for (int x = 0; x < destW; x++) {
/* 1793 */             destPixels[(destOffset + x)] = 
/* 1794 */               blend_dodge(destPixels[(destOffset + x)], filter_bilinear());
/* 1795 */             this.sX += dx;
/*      */           }
/* 1797 */           destOffset += screenW;
/* 1798 */           this.srcYOffset += dy;
/*      */         }
/* 1800 */         break;
/*      */       
/*      */       case 8192: 
/* 1803 */         for (int y = 0; y < destH; y++) {
/* 1804 */           filter_new_scanline();
/* 1805 */           for (int x = 0; x < destW; x++) {
/* 1806 */             destPixels[(destOffset + x)] = 
/* 1807 */               blend_burn(destPixels[(destOffset + x)], filter_bilinear());
/* 1808 */             this.sX += dx;
/*      */           }
/* 1810 */           destOffset += screenW;
/* 1811 */           this.srcYOffset += dy;
/*      */         }
/*      */       
/*      */       }
/*      */       
/*      */     }
/*      */     else
/*      */     {
/* 1819 */       switch (mode)
/*      */       {
/*      */       case 1: 
/* 1822 */         for (int y = 0; y < destH; y++) {
/* 1823 */           this.sX = this.srcXOffset;
/* 1824 */           this.sY = ((this.srcYOffset >> 15) * img.width);
/* 1825 */           for (int x = 0; x < destW; x++)
/*      */           {
/* 1827 */             destPixels[(destOffset + x)] = 
/* 1828 */               blend_blend(destPixels[(destOffset + x)], 
/* 1829 */               this.srcBuffer[(this.sY + (this.sX >> 15))]);
/* 1830 */             this.sX += dx;
/*      */           }
/* 1832 */           destOffset += screenW;
/* 1833 */           this.srcYOffset += dy;
/*      */         }
/* 1835 */         break;
/*      */       
/*      */       case 2: 
/* 1838 */         for (int y = 0; y < destH; y++) {
/* 1839 */           this.sX = this.srcXOffset;
/* 1840 */           this.sY = ((this.srcYOffset >> 15) * img.width);
/* 1841 */           for (int x = 0; x < destW; x++) {
/* 1842 */             destPixels[(destOffset + x)] = 
/* 1843 */               blend_add_pin(destPixels[(destOffset + x)], 
/* 1844 */               this.srcBuffer[(this.sY + (this.sX >> 15))]);
/* 1845 */             this.sX += dx;
/*      */           }
/* 1847 */           destOffset += screenW;
/* 1848 */           this.srcYOffset += dy;
/*      */         }
/* 1850 */         break;
/*      */       
/*      */       case 4: 
/* 1853 */         for (int y = 0; y < destH; y++) {
/* 1854 */           this.sX = this.srcXOffset;
/* 1855 */           this.sY = ((this.srcYOffset >> 15) * img.width);
/* 1856 */           for (int x = 0; x < destW; x++) {
/* 1857 */             destPixels[(destOffset + x)] = 
/* 1858 */               blend_sub_pin(destPixels[(destOffset + x)], 
/* 1859 */               this.srcBuffer[(this.sY + (this.sX >> 15))]);
/* 1860 */             this.sX += dx;
/*      */           }
/* 1862 */           destOffset += screenW;
/* 1863 */           this.srcYOffset += dy;
/*      */         }
/* 1865 */         break;
/*      */       
/*      */       case 8: 
/* 1868 */         for (int y = 0; y < destH; y++) {
/* 1869 */           this.sX = this.srcXOffset;
/* 1870 */           this.sY = ((this.srcYOffset >> 15) * img.width);
/* 1871 */           for (int x = 0; x < destW; x++) {
/* 1872 */             destPixels[(destOffset + x)] = 
/* 1873 */               blend_lightest(destPixels[(destOffset + x)], 
/* 1874 */               this.srcBuffer[(this.sY + (this.sX >> 15))]);
/* 1875 */             this.sX += dx;
/*      */           }
/* 1877 */           destOffset += screenW;
/* 1878 */           this.srcYOffset += dy;
/*      */         }
/* 1880 */         break;
/*      */       
/*      */       case 16: 
/* 1883 */         for (int y = 0; y < destH; y++) {
/* 1884 */           this.sX = this.srcXOffset;
/* 1885 */           this.sY = ((this.srcYOffset >> 15) * img.width);
/* 1886 */           for (int x = 0; x < destW; x++) {
/* 1887 */             destPixels[(destOffset + x)] = 
/* 1888 */               blend_darkest(destPixels[(destOffset + x)], 
/* 1889 */               this.srcBuffer[(this.sY + (this.sX >> 15))]);
/* 1890 */             this.sX += dx;
/*      */           }
/* 1892 */           destOffset += screenW;
/* 1893 */           this.srcYOffset += dy;
/*      */         }
/* 1895 */         break;
/*      */       
/*      */       case 0: 
/* 1898 */         for (int y = 0; y < destH; y++) {
/* 1899 */           this.sX = this.srcXOffset;
/* 1900 */           this.sY = ((this.srcYOffset >> 15) * img.width);
/* 1901 */           for (int x = 0; x < destW; x++) {
/* 1902 */             destPixels[(destOffset + x)] = this.srcBuffer[(this.sY + (this.sX >> 15))];
/* 1903 */             this.sX += dx;
/*      */           }
/* 1905 */           destOffset += screenW;
/* 1906 */           this.srcYOffset += dy;
/*      */         }
/* 1908 */         break;
/*      */       
/*      */       case 32: 
/* 1911 */         for (int y = 0; y < destH; y++) {
/* 1912 */           this.sX = this.srcXOffset;
/* 1913 */           this.sY = ((this.srcYOffset >> 15) * img.width);
/* 1914 */           for (int x = 0; x < destW; x++) {
/* 1915 */             destPixels[(destOffset + x)] = 
/* 1916 */               blend_difference(destPixels[(destOffset + x)], 
/* 1917 */               this.srcBuffer[(this.sY + (this.sX >> 15))]);
/* 1918 */             this.sX += dx;
/*      */           }
/* 1920 */           destOffset += screenW;
/* 1921 */           this.srcYOffset += dy;
/*      */         }
/* 1923 */         break;
/*      */       
/*      */       case 64: 
/* 1926 */         for (int y = 0; y < destH; y++) {
/* 1927 */           this.sX = this.srcXOffset;
/* 1928 */           this.sY = ((this.srcYOffset >> 15) * img.width);
/* 1929 */           for (int x = 0; x < destW; x++) {
/* 1930 */             destPixels[(destOffset + x)] = 
/* 1931 */               blend_exclusion(destPixels[(destOffset + x)], 
/* 1932 */               this.srcBuffer[(this.sY + (this.sX >> 15))]);
/* 1933 */             this.sX += dx;
/*      */           }
/* 1935 */           destOffset += screenW;
/* 1936 */           this.srcYOffset += dy;
/*      */         }
/* 1938 */         break;
/*      */       
/*      */       case 128: 
/* 1941 */         for (int y = 0; y < destH; y++) {
/* 1942 */           this.sX = this.srcXOffset;
/* 1943 */           this.sY = ((this.srcYOffset >> 15) * img.width);
/* 1944 */           for (int x = 0; x < destW; x++) {
/* 1945 */             destPixels[(destOffset + x)] = 
/* 1946 */               blend_multiply(destPixels[(destOffset + x)], 
/* 1947 */               this.srcBuffer[(this.sY + (this.sX >> 15))]);
/* 1948 */             this.sX += dx;
/*      */           }
/* 1950 */           destOffset += screenW;
/* 1951 */           this.srcYOffset += dy;
/*      */         }
/* 1953 */         break;
/*      */       
/*      */       case 256: 
/* 1956 */         for (int y = 0; y < destH; y++) {
/* 1957 */           this.sX = this.srcXOffset;
/* 1958 */           this.sY = ((this.srcYOffset >> 15) * img.width);
/* 1959 */           for (int x = 0; x < destW; x++) {
/* 1960 */             destPixels[(destOffset + x)] = 
/* 1961 */               blend_screen(destPixels[(destOffset + x)], 
/* 1962 */               this.srcBuffer[(this.sY + (this.sX >> 15))]);
/* 1963 */             this.sX += dx;
/*      */           }
/* 1965 */           destOffset += screenW;
/* 1966 */           this.srcYOffset += dy;
/*      */         }
/* 1968 */         break;
/*      */       
/*      */       case 512: 
/* 1971 */         for (int y = 0; y < destH; y++) {
/* 1972 */           this.sX = this.srcXOffset;
/* 1973 */           this.sY = ((this.srcYOffset >> 15) * img.width);
/* 1974 */           for (int x = 0; x < destW; x++) {
/* 1975 */             destPixels[(destOffset + x)] = 
/* 1976 */               blend_overlay(destPixels[(destOffset + x)], 
/* 1977 */               this.srcBuffer[(this.sY + (this.sX >> 15))]);
/* 1978 */             this.sX += dx;
/*      */           }
/* 1980 */           destOffset += screenW;
/* 1981 */           this.srcYOffset += dy;
/*      */         }
/* 1983 */         break;
/*      */       
/*      */       case 1024: 
/* 1986 */         for (int y = 0; y < destH; y++) {
/* 1987 */           this.sX = this.srcXOffset;
/* 1988 */           this.sY = ((this.srcYOffset >> 15) * img.width);
/* 1989 */           for (int x = 0; x < destW; x++) {
/* 1990 */             destPixels[(destOffset + x)] = 
/* 1991 */               blend_hard_light(destPixels[(destOffset + x)], 
/* 1992 */               this.srcBuffer[(this.sY + (this.sX >> 15))]);
/* 1993 */             this.sX += dx;
/*      */           }
/* 1995 */           destOffset += screenW;
/* 1996 */           this.srcYOffset += dy;
/*      */         }
/* 1998 */         break;
/*      */       
/*      */       case 2048: 
/* 2001 */         for (int y = 0; y < destH; y++) {
/* 2002 */           this.sX = this.srcXOffset;
/* 2003 */           this.sY = ((this.srcYOffset >> 15) * img.width);
/* 2004 */           for (int x = 0; x < destW; x++) {
/* 2005 */             destPixels[(destOffset + x)] = 
/* 2006 */               blend_soft_light(destPixels[(destOffset + x)], 
/* 2007 */               this.srcBuffer[(this.sY + (this.sX >> 15))]);
/* 2008 */             this.sX += dx;
/*      */           }
/* 2010 */           destOffset += screenW;
/* 2011 */           this.srcYOffset += dy;
/*      */         }
/* 2013 */         break;
/*      */       
/*      */ 
/*      */       case 4096: 
/* 2017 */         for (int y = 0; y < destH; y++) {
/* 2018 */           this.sX = this.srcXOffset;
/* 2019 */           this.sY = ((this.srcYOffset >> 15) * img.width);
/* 2020 */           for (int x = 0; x < destW; x++) {
/* 2021 */             destPixels[(destOffset + x)] = 
/* 2022 */               blend_dodge(destPixels[(destOffset + x)], 
/* 2023 */               this.srcBuffer[(this.sY + (this.sX >> 15))]);
/* 2024 */             this.sX += dx;
/*      */           }
/* 2026 */           destOffset += screenW;
/* 2027 */           this.srcYOffset += dy;
/*      */         }
/* 2029 */         break;
/*      */       
/*      */       case 8192: 
/* 2032 */         for (int y = 0; y < destH; y++) {
/* 2033 */           this.sX = this.srcXOffset;
/* 2034 */           this.sY = ((this.srcYOffset >> 15) * img.width);
/* 2035 */           for (int x = 0; x < destW; x++) {
/* 2036 */             destPixels[(destOffset + x)] = 
/* 2037 */               blend_burn(destPixels[(destOffset + x)], 
/* 2038 */               this.srcBuffer[(this.sY + (this.sX >> 15))]);
/* 2039 */             this.sX += dx;
/*      */           }
/* 2041 */           destOffset += screenW;
/* 2042 */           this.srcYOffset += dy;
/*      */         }
/*      */       }
/*      */       
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void filter_new_scanline()
/*      */   {
/* 2052 */     this.sX = this.srcXOffset;
/* 2053 */     this.fracV = (this.srcYOffset & 0x7FFF);
/* 2054 */     this.ifV = (32767 - this.fracV);
/* 2055 */     this.v1 = ((this.srcYOffset >> 15) * this.iw);
/* 2056 */     this.v2 = (low((this.srcYOffset >> 15) + 1, this.ih1) * this.iw);
/*      */   }
/*      */   
/*      */   private int filter_bilinear()
/*      */   {
/* 2061 */     this.fracU = (this.sX & 0x7FFF);
/* 2062 */     this.ifU = (32767 - this.fracU);
/* 2063 */     this.ul = (this.ifU * this.ifV >> 15);
/* 2064 */     this.ll = (this.ifU * this.fracV >> 15);
/* 2065 */     this.ur = (this.fracU * this.ifV >> 15);
/* 2066 */     this.lr = (this.fracU * this.fracV >> 15);
/* 2067 */     this.u1 = (this.sX >> 15);
/* 2068 */     this.u2 = low(this.u1 + 1, this.iw1);
/*      */     
/*      */ 
/* 2071 */     this.cUL = this.srcBuffer[(this.v1 + this.u1)];
/* 2072 */     this.cUR = this.srcBuffer[(this.v1 + this.u2)];
/* 2073 */     this.cLL = this.srcBuffer[(this.v2 + this.u1)];
/* 2074 */     this.cLR = this.srcBuffer[(this.v2 + this.u2)];
/*      */     
/* 2076 */     this.r = 
/*      */     
/* 2078 */       (this.ul * ((this.cUL & 0xFF0000) >> 16) + this.ll * ((this.cLL & 0xFF0000) >> 16) + this.ur * ((this.cUR & 0xFF0000) >> 16) + this.lr * ((this.cLR & 0xFF0000) >> 16) << 1 & 0xFF0000);
/*      */     
/* 2080 */     this.g = 
/*      */     
/* 2082 */       (this.ul * (this.cUL & 0xFF00) + this.ll * (this.cLL & 0xFF00) + this.ur * (this.cUR & 0xFF00) + this.lr * (this.cLR & 0xFF00) >>> 15 & 0xFF00);
/*      */     
/* 2084 */     this.b = 
/*      */     
/* 2086 */       (this.ul * (this.cUL & 0xFF) + this.ll * (this.cLL & 0xFF) + this.ur * (this.cUR & 0xFF) + this.lr * (this.cLR & 0xFF) >>> 15);
/*      */     
/* 2088 */     this.a = 
/*      */     
/* 2090 */       (this.ul * ((this.cUL & 0xFF000000) >>> 24) + this.ll * ((this.cLL & 0xFF000000) >>> 24) + this.ur * ((this.cUR & 0xFF000000) >>> 24) + this.lr * ((this.cLR & 0xFF000000) >>> 24) << 9 & 0xFF000000);
/*      */     
/* 2092 */     return this.a | this.r | this.g | this.b;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int low(int a, int b)
/*      */   {
/* 2103 */     return a < b ? a : b;
/*      */   }
/*      */   
/*      */   private static int high(int a, int b)
/*      */   {
/* 2108 */     return a > b ? a : b;
/*      */   }
/*      */   
/*      */   private static int peg(int n)
/*      */   {
/* 2113 */     return n > 255 ? 255 : n < 0 ? 0 : n;
/*      */   }
/*      */   
/*      */   private static int mix(int a, int b, int f) {
/* 2117 */     return a + ((b - a) * f >> 8);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int blend_blend(int a, int b)
/*      */   {
/* 2128 */     int f = (b & 0xFF000000) >>> 24;
/*      */     
/* 2130 */     return low(((a & 0xFF000000) >>> 24) + f, 255) << 24 | 
/* 2131 */       mix(a & 0xFF0000, b & 0xFF0000, f) & 0xFF0000 | 
/* 2132 */       mix(a & 0xFF00, b & 0xFF00, f) & 0xFF00 | 
/* 2133 */       mix(a & 0xFF, b & 0xFF, f);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int blend_add_pin(int a, int b)
/*      */   {
/* 2141 */     int f = (b & 0xFF000000) >>> 24;
/*      */     
/* 2143 */     return low(((a & 0xFF000000) >>> 24) + f, 255) << 24 | 
/* 2144 */       low((a & 0xFF0000) + 
/* 2145 */       ((b & 0xFF0000) >> 8) * f, 16711680) & 0xFF0000 | 
/* 2146 */       low((a & 0xFF00) + 
/* 2147 */       ((b & 0xFF00) >> 8) * f, 65280) & 0xFF00 | 
/* 2148 */       low((a & 0xFF) + (
/* 2149 */       (b & 0xFF) * f >> 8), 255);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int blend_sub_pin(int a, int b)
/*      */   {
/* 2157 */     int f = (b & 0xFF000000) >>> 24;
/*      */     
/* 2159 */     return low(((a & 0xFF000000) >>> 24) + f, 255) << 24 | 
/* 2160 */       high((a & 0xFF0000) - ((b & 0xFF0000) >> 8) * f, 
/* 2161 */       65280) & 0xFF0000 | 
/* 2162 */       high((a & 0xFF00) - ((b & 0xFF00) >> 8) * f, 
/* 2163 */       255) & 0xFF00 | 
/* 2164 */       high((a & 0xFF) - ((b & 0xFF) * f >> 8), 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int blend_lightest(int a, int b)
/*      */   {
/* 2172 */     int f = (b & 0xFF000000) >>> 24;
/*      */     
/* 2174 */     return low(((a & 0xFF000000) >>> 24) + f, 255) << 24 | 
/* 2175 */       high(a & 0xFF0000, ((b & 0xFF0000) >> 8) * f) & 0xFF0000 | 
/* 2176 */       high(a & 0xFF00, ((b & 0xFF00) >> 8) * f) & 0xFF00 | 
/* 2177 */       high(a & 0xFF, (b & 0xFF) * f >> 8);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int blend_darkest(int a, int b)
/*      */   {
/* 2185 */     int f = (b & 0xFF000000) >>> 24;
/*      */     
/* 2187 */     return low(((a & 0xFF000000) >>> 24) + f, 255) << 24 | 
/* 2188 */       mix(a & 0xFF0000, 
/* 2189 */       low(a & 0xFF0000, 
/* 2190 */       ((b & 0xFF0000) >> 8) * f), f) & 0xFF0000 | 
/* 2191 */       mix(a & 0xFF00, 
/* 2192 */       low(a & 0xFF00, 
/* 2193 */       ((b & 0xFF00) >> 8) * f), f) & 0xFF00 | 
/* 2194 */       mix(a & 0xFF, 
/* 2195 */       low(a & 0xFF, 
/* 2196 */       (b & 0xFF) * f >> 8), f);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int blend_difference(int a, int b)
/*      */   {
/* 2206 */     int f = (b & 0xFF000000) >>> 24;
/* 2207 */     int ar = (a & 0xFF0000) >> 16;
/* 2208 */     int ag = (a & 0xFF00) >> 8;
/* 2209 */     int ab = a & 0xFF;
/* 2210 */     int br = (b & 0xFF0000) >> 16;
/* 2211 */     int bg = (b & 0xFF00) >> 8;
/* 2212 */     int bb = b & 0xFF;
/*      */     
/* 2214 */     int cr = ar > br ? ar - br : br - ar;
/* 2215 */     int cg = ag > bg ? ag - bg : bg - ag;
/* 2216 */     int cb = ab > bb ? ab - bb : bb - ab;
/*      */     
/* 2218 */     return low(((a & 0xFF000000) >>> 24) + f, 255) << 24 | 
/* 2219 */       peg(ar + ((cr - ar) * f >> 8)) << 16 | 
/* 2220 */       peg(ag + ((cg - ag) * f >> 8)) << 8 | 
/* 2221 */       peg(ab + ((cb - ab) * f >> 8));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int blend_exclusion(int a, int b)
/*      */   {
/* 2232 */     int f = (b & 0xFF000000) >>> 24;
/* 2233 */     int ar = (a & 0xFF0000) >> 16;
/* 2234 */     int ag = (a & 0xFF00) >> 8;
/* 2235 */     int ab = a & 0xFF;
/* 2236 */     int br = (b & 0xFF0000) >> 16;
/* 2237 */     int bg = (b & 0xFF00) >> 8;
/* 2238 */     int bb = b & 0xFF;
/*      */     
/* 2240 */     int cr = ar + br - (ar * br >> 7);
/* 2241 */     int cg = ag + bg - (ag * bg >> 7);
/* 2242 */     int cb = ab + bb - (ab * bb >> 7);
/*      */     
/* 2244 */     return low(((a & 0xFF000000) >>> 24) + f, 255) << 24 | 
/* 2245 */       peg(ar + ((cr - ar) * f >> 8)) << 16 | 
/* 2246 */       peg(ag + ((cg - ag) * f >> 8)) << 8 | 
/* 2247 */       peg(ab + ((cb - ab) * f >> 8));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int blend_multiply(int a, int b)
/*      */   {
/* 2257 */     int f = (b & 0xFF000000) >>> 24;
/* 2258 */     int ar = (a & 0xFF0000) >> 16;
/* 2259 */     int ag = (a & 0xFF00) >> 8;
/* 2260 */     int ab = a & 0xFF;
/* 2261 */     int br = (b & 0xFF0000) >> 16;
/* 2262 */     int bg = (b & 0xFF00) >> 8;
/* 2263 */     int bb = b & 0xFF;
/*      */     
/* 2265 */     int cr = ar * br >> 8;
/* 2266 */     int cg = ag * bg >> 8;
/* 2267 */     int cb = ab * bb >> 8;
/*      */     
/* 2269 */     return low(((a & 0xFF000000) >>> 24) + f, 255) << 24 | 
/* 2270 */       peg(ar + ((cr - ar) * f >> 8)) << 16 | 
/* 2271 */       peg(ag + ((cg - ag) * f >> 8)) << 8 | 
/* 2272 */       peg(ab + ((cb - ab) * f >> 8));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int blend_screen(int a, int b)
/*      */   {
/* 2282 */     int f = (b & 0xFF000000) >>> 24;
/* 2283 */     int ar = (a & 0xFF0000) >> 16;
/* 2284 */     int ag = (a & 0xFF00) >> 8;
/* 2285 */     int ab = a & 0xFF;
/* 2286 */     int br = (b & 0xFF0000) >> 16;
/* 2287 */     int bg = (b & 0xFF00) >> 8;
/* 2288 */     int bb = b & 0xFF;
/*      */     
/* 2290 */     int cr = 255 - ((255 - ar) * (255 - br) >> 8);
/* 2291 */     int cg = 255 - ((255 - ag) * (255 - bg) >> 8);
/* 2292 */     int cb = 255 - ((255 - ab) * (255 - bb) >> 8);
/*      */     
/* 2294 */     return low(((a & 0xFF000000) >>> 24) + f, 255) << 24 | 
/* 2295 */       peg(ar + ((cr - ar) * f >> 8)) << 16 | 
/* 2296 */       peg(ag + ((cg - ag) * f >> 8)) << 8 | 
/* 2297 */       peg(ab + ((cb - ab) * f >> 8));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int blend_overlay(int a, int b)
/*      */   {
/* 2310 */     int f = (b & 0xFF000000) >>> 24;
/* 2311 */     int ar = (a & 0xFF0000) >> 16;
/* 2312 */     int ag = (a & 0xFF00) >> 8;
/* 2313 */     int ab = a & 0xFF;
/* 2314 */     int br = (b & 0xFF0000) >> 16;
/* 2315 */     int bg = (b & 0xFF00) >> 8;
/* 2316 */     int bb = b & 0xFF;
/*      */     
/* 2318 */     int cr = ar < 128 ? ar * br >> 7 : 255 - ((255 - ar) * (255 - br) >> 7);
/* 2319 */     int cg = ag < 128 ? ag * bg >> 7 : 255 - ((255 - ag) * (255 - bg) >> 7);
/* 2320 */     int cb = ab < 128 ? ab * bb >> 7 : 255 - ((255 - ab) * (255 - bb) >> 7);
/*      */     
/* 2322 */     return low(((a & 0xFF000000) >>> 24) + f, 255) << 24 | 
/* 2323 */       peg(ar + ((cr - ar) * f >> 8)) << 16 | 
/* 2324 */       peg(ag + ((cg - ag) * f >> 8)) << 8 | 
/* 2325 */       peg(ab + ((cb - ab) * f >> 8));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int blend_hard_light(int a, int b)
/*      */   {
/* 2338 */     int f = (b & 0xFF000000) >>> 24;
/* 2339 */     int ar = (a & 0xFF0000) >> 16;
/* 2340 */     int ag = (a & 0xFF00) >> 8;
/* 2341 */     int ab = a & 0xFF;
/* 2342 */     int br = (b & 0xFF0000) >> 16;
/* 2343 */     int bg = (b & 0xFF00) >> 8;
/* 2344 */     int bb = b & 0xFF;
/*      */     
/* 2346 */     int cr = br < 128 ? ar * br >> 7 : 255 - ((255 - ar) * (255 - br) >> 7);
/* 2347 */     int cg = bg < 128 ? ag * bg >> 7 : 255 - ((255 - ag) * (255 - bg) >> 7);
/* 2348 */     int cb = bb < 128 ? ab * bb >> 7 : 255 - ((255 - ab) * (255 - bb) >> 7);
/*      */     
/* 2350 */     return low(((a & 0xFF000000) >>> 24) + f, 255) << 24 | 
/* 2351 */       peg(ar + ((cr - ar) * f >> 8)) << 16 | 
/* 2352 */       peg(ag + ((cg - ag) * f >> 8)) << 8 | 
/* 2353 */       peg(ab + ((cb - ab) * f >> 8));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int blend_soft_light(int a, int b)
/*      */   {
/* 2363 */     int f = (b & 0xFF000000) >>> 24;
/* 2364 */     int ar = (a & 0xFF0000) >> 16;
/* 2365 */     int ag = (a & 0xFF00) >> 8;
/* 2366 */     int ab = a & 0xFF;
/* 2367 */     int br = (b & 0xFF0000) >> 16;
/* 2368 */     int bg = (b & 0xFF00) >> 8;
/* 2369 */     int bb = b & 0xFF;
/*      */     
/* 2371 */     int cr = (ar * br >> 7) + (ar * ar >> 8) - (ar * ar * br >> 15);
/* 2372 */     int cg = (ag * bg >> 7) + (ag * ag >> 8) - (ag * ag * bg >> 15);
/* 2373 */     int cb = (ab * bb >> 7) + (ab * ab >> 8) - (ab * ab * bb >> 15);
/*      */     
/* 2375 */     return low(((a & 0xFF000000) >>> 24) + f, 255) << 24 | 
/* 2376 */       peg(ar + ((cr - ar) * f >> 8)) << 16 | 
/* 2377 */       peg(ag + ((cg - ag) * f >> 8)) << 8 | 
/* 2378 */       peg(ab + ((cb - ab) * f >> 8));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int blend_dodge(int a, int b)
/*      */   {
/* 2388 */     int f = (b & 0xFF000000) >>> 24;
/* 2389 */     int ar = (a & 0xFF0000) >> 16;
/* 2390 */     int ag = (a & 0xFF00) >> 8;
/* 2391 */     int ab = a & 0xFF;
/* 2392 */     int br = (b & 0xFF0000) >> 16;
/* 2393 */     int bg = (b & 0xFF00) >> 8;
/* 2394 */     int bb = b & 0xFF;
/*      */     
/* 2396 */     int cr = br == 255 ? 255 : peg((ar << 8) / (255 - br));
/* 2397 */     int cg = bg == 255 ? 255 : peg((ag << 8) / (255 - bg));
/* 2398 */     int cb = bb == 255 ? 255 : peg((ab << 8) / (255 - bb));
/*      */     
/* 2400 */     return low(((a & 0xFF000000) >>> 24) + f, 255) << 24 | 
/* 2401 */       peg(ar + ((cr - ar) * f >> 8)) << 16 | 
/* 2402 */       peg(ag + ((cg - ag) * f >> 8)) << 8 | 
/* 2403 */       peg(ab + ((cb - ab) * f >> 8));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int blend_burn(int a, int b)
/*      */   {
/* 2413 */     int f = (b & 0xFF000000) >>> 24;
/* 2414 */     int ar = (a & 0xFF0000) >> 16;
/* 2415 */     int ag = (a & 0xFF00) >> 8;
/* 2416 */     int ab = a & 0xFF;
/* 2417 */     int br = (b & 0xFF0000) >> 16;
/* 2418 */     int bg = (b & 0xFF00) >> 8;
/* 2419 */     int bb = b & 0xFF;
/*      */     
/* 2421 */     int cr = br == 0 ? 0 : 255 - peg((255 - ar << 8) / br);
/* 2422 */     int cg = bg == 0 ? 0 : 255 - peg((255 - ag << 8) / bg);
/* 2423 */     int cb = bb == 0 ? 0 : 255 - peg((255 - ab << 8) / bb);
/*      */     
/* 2425 */     return low(((a & 0xFF000000) >>> 24) + f, 255) << 24 | 
/* 2426 */       peg(ar + ((cr - ar) * f >> 8)) << 16 | 
/* 2427 */       peg(ag + ((cg - ag) * f >> 8)) << 8 | 
/* 2428 */       peg(ab + ((cb - ab) * f >> 8));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2437 */   static byte[] TIFF_HEADER = {
/* 2438 */     77, 77, 0, 42, 0, 0, 0, 8, 0, 9, 0, -2, 0, 4, 0, 0, 0, 1, 
/* 2439 */     0, 0, 0, 0, 1, 0, 0, 3, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 3, 0, 0, 0, 1, 
/* 2440 */     0, 0, 0, 0, 1, 2, 0, 3, 0, 0, 0, 3, 0, 0, 0, 122, 1, 6, 0, 3, 
/* 2441 */     0, 0, 0, 1, 0, 2, 0, 0, 1, 17, 0, 4, 0, 0, 0, 1, 0, 0, 3, 0, 1, 21, 
/* 2442 */     0, 3, 0, 0, 0, 1, 0, 3, 0, 0, 1, 22, 0, 3, 0, 0, 0, 1, 
/* 2443 */     0, 0, 0, 0, 1, 23, 0, 4, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 8, 08 };
/*      */   
/*      */   static final String TIFF_ERROR = "Error: Processing can only read its own TIFF files.";
/*      */   
/*      */   protected String[] saveImageFormats;
/*      */   
/*      */   protected static PImage loadTIFF(byte[] tiff)
/*      */   {
/* 2451 */     if ((tiff[42] != tiff[102]) || 
/* 2452 */       (tiff[43] != tiff[103])) {
/* 2453 */       System.err.println("Error: Processing can only read its own TIFF files.");
/* 2454 */       return null;
/*      */     }
/*      */     
/* 2457 */     int width = 
/* 2458 */       (tiff[30] & 0xFF) << 8 | tiff[31] & 0xFF;
/* 2459 */     int height = 
/* 2460 */       (tiff[42] & 0xFF) << 8 | tiff[43] & 0xFF;
/*      */     
/* 2462 */     int count = 
/* 2463 */       (tiff[114] & 0xFF) << 24 | 
/* 2464 */       (tiff[115] & 0xFF) << 16 | 
/* 2465 */       (tiff[116] & 0xFF) << 8 | 
/* 2466 */       tiff[117] & 0xFF;
/* 2467 */     if (count != width * height * 3) {
/* 2468 */       System.err.println("Error: Processing can only read its own TIFF files. (" + width + ", " + height + ")");
/* 2469 */       return null;
/*      */     }
/*      */     
/*      */ 
/* 2473 */     for (int i = 0; i < TIFF_HEADER.length; i++) {
/* 2474 */       if ((i != 30) && (i != 31) && (i != 42) && (i != 43) && 
/* 2475 */         (i != 102) && (i != 103) && 
/* 2476 */         (i != 114) && (i != 115) && (i != 116) && (i != 117))
/*      */       {
/* 2478 */         if (tiff[i] != TIFF_HEADER[i]) {
/* 2479 */           System.err.println("Error: Processing can only read its own TIFF files. (" + i + ")");
/* 2480 */           return null;
/*      */         }
/*      */       }
/*      */     }
/* 2484 */     PImage outgoing = new PImage(width, height, 1);
/* 2485 */     int index = 768;
/* 2486 */     count /= 3;
/* 2487 */     for (int i = 0; i < count; i++) {
/* 2488 */       outgoing.pixels[i] = 
/* 2489 */         (0xFF000000 | 
/* 2490 */         (tiff[(index++)] & 0xFF) << 16 | 
/* 2491 */         (tiff[(index++)] & 0xFF) << 8 | 
/* 2492 */         tiff[(index++)] & 0xFF);
/*      */     }
/* 2494 */     return outgoing;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean saveTIFF(OutputStream output)
/*      */   {
/*      */     try
/*      */     {
/* 2507 */       byte[] tiff = new byte['̀'];
/* 2508 */       System.arraycopy(TIFF_HEADER, 0, tiff, 0, TIFF_HEADER.length);
/*      */       
/* 2510 */       tiff[30] = ((byte)(this.width >> 8 & 0xFF));
/* 2511 */       tiff[31] = ((byte)(this.width & 0xFF));
/* 2512 */       tiff[42] = (tiff[102] = (byte)(this.height >> 8 & 0xFF));
/* 2513 */       tiff[43] = (tiff[103] = (byte)(this.height & 0xFF));
/*      */       
/* 2515 */       int count = this.width * this.height * 3;
/* 2516 */       tiff[114] = ((byte)(count >> 24 & 0xFF));
/* 2517 */       tiff[115] = ((byte)(count >> 16 & 0xFF));
/* 2518 */       tiff[116] = ((byte)(count >> 8 & 0xFF));
/* 2519 */       tiff[117] = ((byte)(count & 0xFF));
/*      */       
/*      */ 
/* 2522 */       output.write(tiff);
/*      */       
/* 2524 */       for (int i = 0; i < this.pixels.length; i++) {
/* 2525 */         output.write(this.pixels[i] >> 16 & 0xFF);
/* 2526 */         output.write(this.pixels[i] >> 8 & 0xFF);
/* 2527 */         output.write(this.pixels[i] & 0xFF);
/*      */       }
/* 2529 */       output.flush();
/* 2530 */       return true;
/*      */     }
/*      */     catch (IOException e) {
/* 2533 */       e.printStackTrace();
/*      */     }
/* 2535 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean saveTGA(OutputStream output)
/*      */   {
/* 2558 */     byte[] header = new byte[18];
/*      */     
/* 2560 */     if (this.format == 4) {
/* 2561 */       header[2] = 11;
/* 2562 */       header[16] = 8;
/* 2563 */       header[17] = 40;
/*      */     }
/* 2565 */     else if (this.format == 1) {
/* 2566 */       header[2] = 10;
/* 2567 */       header[16] = 24;
/* 2568 */       header[17] = 32;
/*      */     }
/* 2570 */     else if (this.format == 2) {
/* 2571 */       header[2] = 10;
/* 2572 */       header[16] = 32;
/* 2573 */       header[17] = 40;
/*      */     }
/*      */     else {
/* 2576 */       throw new RuntimeException("Image format not recognized inside save()");
/*      */     }
/*      */     
/* 2579 */     header[12] = ((byte)(this.width & 0xFF));
/* 2580 */     header[13] = ((byte)(this.width >> 8));
/* 2581 */     header[14] = ((byte)(this.height & 0xFF));
/* 2582 */     header[15] = ((byte)(this.height >> 8));
/*      */     try
/*      */     {
/* 2585 */       output.write(header);
/*      */       
/* 2587 */       int maxLen = this.height * this.width;
/* 2588 */       int index = 0;
/*      */       
/* 2590 */       int[] currChunk = new int[''];
/*      */       
/*      */ 
/*      */ 
/* 2594 */       if (this.format == 4) {
/* 2595 */         while (index < maxLen) {
/* 2596 */           isRLE = false;
/* 2597 */           rle = 1;
/* 2598 */           currChunk[0] = (col = this.pixels[index] & 0xFF);
/* 2599 */           while (index + rle < maxLen) {
/* 2600 */             if ((col != (this.pixels[(index + rle)] & 0xFF)) || (rle == 128)) {
/* 2601 */               isRLE = rle > 1;
/* 2602 */               break;
/*      */             }
/* 2604 */             rle++;
/*      */           }
/* 2606 */           if (isRLE) {
/* 2607 */             output.write(0x80 | rle - 1);
/* 2608 */             output.write(col);
/*      */           }
/*      */           else {
/* 2611 */             rle = 1;
/* 2612 */             while (index + rle < maxLen) {
/* 2613 */               cscan = this.pixels[(index + rle)] & 0xFF;
/* 2614 */               if (((col != cscan) && (rle < 128)) || (rle < 3)) {
/* 2615 */                 currChunk[rle] = (col = cscan);
/*      */               } else {
/* 2617 */                 if (col != cscan) break; rle -= 2;
/* 2618 */                 break;
/*      */               }
/* 2620 */               rle++;
/*      */             }
/* 2622 */             output.write(rle - 1);
/* 2623 */             for (i = 0; i < rle; i++) output.write(currChunk[i]);
/*      */           }
/* 2625 */           index += rle;
/*      */         }
/*      */       } else {
/* 2628 */         while (index < maxLen) { boolean isRLE;
/* 2629 */           int rle; int col; int cscan; int i; boolean isRLE = false;
/* 2630 */           int col; currChunk[0] = (col = this.pixels[index]);
/* 2631 */           int rle = 1;
/*      */           
/*      */ 
/* 2634 */           while (index + rle < maxLen) {
/* 2635 */             if ((col != this.pixels[(index + rle)]) || (rle == 128)) {
/* 2636 */               isRLE = rle > 1;
/* 2637 */               break;
/*      */             }
/* 2639 */             rle++;
/*      */           }
/* 2641 */           if (isRLE) {
/* 2642 */             output.write(0x80 | rle - 1);
/* 2643 */             output.write(col & 0xFF);
/* 2644 */             output.write(col >> 8 & 0xFF);
/* 2645 */             output.write(col >> 16 & 0xFF);
/* 2646 */             if (this.format == 2) output.write(col >>> 24 & 0xFF);
/*      */           }
/*      */           else {
/* 2649 */             rle = 1;
/* 2650 */             while (index + rle < maxLen) {
/* 2651 */               if (((col != this.pixels[(index + rle)]) && (rle < 128)) || (rle < 3)) {
/* 2652 */                 currChunk[rle] = (col = this.pixels[(index + rle)]);
/*      */               }
/*      */               else
/*      */               {
/* 2656 */                 if (col != this.pixels[(index + rle)]) break; rle -= 2;
/* 2657 */                 break;
/*      */               }
/* 2659 */               rle++;
/*      */             }
/*      */             
/* 2662 */             output.write(rle - 1);
/* 2663 */             if (this.format == 2) {
/* 2664 */               for (int i = 0; i < rle; i++) {
/* 2665 */                 col = currChunk[i];
/* 2666 */                 output.write(col & 0xFF);
/* 2667 */                 output.write(col >> 8 & 0xFF);
/* 2668 */                 output.write(col >> 16 & 0xFF);
/* 2669 */                 output.write(col >>> 24 & 0xFF);
/*      */               }
/*      */             } else {
/* 2672 */               for (int i = 0; i < rle; i++) {
/* 2673 */                 col = currChunk[i];
/* 2674 */                 output.write(col & 0xFF);
/* 2675 */                 output.write(col >> 8 & 0xFF);
/* 2676 */                 output.write(col >> 16 & 0xFF);
/*      */               }
/*      */             }
/*      */           }
/* 2680 */           index += rle;
/*      */         }
/*      */       }
/* 2683 */       output.flush();
/* 2684 */       return true;
/*      */     }
/*      */     catch (IOException e) {
/* 2687 */       e.printStackTrace(); }
/* 2688 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void saveImageIO(String path)
/*      */     throws IOException
/*      */   {
/*      */     try
/*      */     {
/* 2701 */       BufferedImage bimage = 
/* 2702 */         new BufferedImage(this.width, this.height, this.format == 2 ? 
/* 2703 */         2 : 
/* 2704 */         1);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2725 */       bimage.setRGB(0, 0, this.width, this.height, this.pixels, 0, this.width);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2741 */       File file = new File(path);
/* 2742 */       String extension = path.substring(path.lastIndexOf('.') + 1);
/*      */       
/* 2744 */       ImageIO.write(bimage, extension, file);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2757 */       e.printStackTrace();
/* 2758 */       throw new IOException("image save failed.");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void save(String filename)
/*      */   {
/* 2805 */     boolean success = false;
/*      */     
/* 2807 */     File file = new File(filename);
/* 2808 */     if (!file.isAbsolute()) {
/* 2809 */       if (this.parent != null)
/*      */       {
/* 2811 */         filename = this.parent.savePath(filename);
/*      */       } else {
/* 2813 */         String msg = "PImage.save() requires an absolute path. Use createImage(), or pass savePath() to save().";
/*      */         
/* 2815 */         PGraphics.showException(msg);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 2820 */     loadPixels();
/*      */     try
/*      */     {
/* 2823 */       OutputStream os = null;
/*      */       
/* 2825 */       if (this.saveImageFormats == null) {
/* 2826 */         this.saveImageFormats = ImageIO.getWriterFormatNames();
/*      */       }
/* 2828 */       if (this.saveImageFormats != null) {
/* 2829 */         for (int i = 0; i < this.saveImageFormats.length; i++) {
/* 2830 */           if (filename.endsWith("." + this.saveImageFormats[i])) {
/* 2831 */             saveImageIO(filename);
/* 2832 */             return;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2837 */       if (filename.toLowerCase().endsWith(".tga")) {
/* 2838 */         os = new BufferedOutputStream(new FileOutputStream(filename), 32768);
/* 2839 */         success = saveTGA(os);
/*      */       }
/*      */       else {
/* 2842 */         if ((!filename.toLowerCase().endsWith(".tif")) && 
/* 2843 */           (!filename.toLowerCase().endsWith(".tiff")))
/*      */         {
/* 2845 */           filename = filename + ".tif";
/*      */         }
/* 2847 */         os = new BufferedOutputStream(new FileOutputStream(filename), 32768);
/* 2848 */         success = saveTIFF(os);
/*      */       }
/* 2850 */       os.flush();
/* 2851 */       os.close();
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/* 2855 */       e.printStackTrace();
/* 2856 */       success = false;
/*      */     }
/* 2858 */     if (!success) {
/* 2859 */       throw new RuntimeException("Error while saving image.");
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/AndrewKanarek/Desktop/MICHIGAN/KiefferWork/scripts-zibo/JavaApps/EllinghamDiagram/core.jar!/processing/core/PImage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */