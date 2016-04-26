/*      */ package processing.core;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Image;
/*      */ import java.io.PrintStream;
/*      */ import java.util.HashMap;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PGraphics
/*      */   extends PImage
/*      */   implements PConstants
/*      */ {
/*      */   protected int width1;
/*      */   protected int height1;
/*      */   public int pixelCount;
/*  142 */   public boolean smooth = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean settingsInited;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected PGraphics raw;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected String path;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean primarySurface;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  179 */   protected boolean[] hints = new boolean[10];
/*      */   
/*      */ 
/*      */   public int colorMode;
/*      */   
/*      */ 
/*      */   public float colorModeX;
/*      */   
/*      */ 
/*      */   public float colorModeY;
/*      */   
/*      */ 
/*      */   public float colorModeZ;
/*      */   
/*      */ 
/*      */   public float colorModeA;
/*      */   
/*      */ 
/*      */   boolean colorModeScale;
/*      */   
/*      */ 
/*      */   boolean colorModeDefault;
/*      */   
/*      */ 
/*      */   public boolean tint;
/*      */   
/*      */ 
/*      */   public int tintColor;
/*      */   
/*      */ 
/*      */   protected boolean tintAlpha;
/*      */   
/*      */ 
/*      */   protected float tintR;
/*      */   
/*      */ 
/*      */   protected float tintG;
/*      */   
/*      */ 
/*      */   protected float tintB;
/*      */   
/*      */ 
/*      */   protected float tintA;
/*      */   
/*      */ 
/*      */   protected int tintRi;
/*      */   
/*      */ 
/*      */   protected int tintGi;
/*      */   
/*      */ 
/*      */   protected int tintBi;
/*      */   
/*      */ 
/*      */   protected int tintAi;
/*      */   
/*      */ 
/*      */   public boolean fill;
/*      */   
/*      */ 
/*  239 */   public int fillColor = -1;
/*      */   
/*      */   protected boolean fillAlpha;
/*      */   
/*      */   protected float fillR;
/*      */   
/*      */   protected float fillG;
/*      */   protected float fillB;
/*      */   protected float fillA;
/*      */   protected int fillRi;
/*      */   protected int fillGi;
/*      */   protected int fillBi;
/*      */   protected int fillAi;
/*      */   public boolean stroke;
/*  253 */   public int strokeColor = -16777216;
/*      */   
/*      */   protected boolean strokeAlpha;
/*      */   
/*      */   protected float strokeR;
/*      */   
/*      */   protected float strokeG;
/*      */   
/*      */   protected float strokeB;
/*      */   
/*      */   protected float strokeA;
/*      */   
/*      */   protected int strokeRi;
/*      */   protected int strokeGi;
/*      */   protected int strokeBi;
/*      */   protected int strokeAi;
/*      */   protected static final float DEFAULT_STROKE_WEIGHT = 1.0F;
/*      */   protected static final int DEFAULT_STROKE_JOIN = 8;
/*      */   protected static final int DEFAULT_STROKE_CAP = 2;
/*  272 */   public float strokeWeight = 1.0F;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  279 */   public int strokeJoin = 8;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  286 */   public int strokeCap = 2;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public int rectMode;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public int ellipseMode;
/*      */   
/*      */ 
/*      */ 
/*      */   public int shapeMode;
/*      */   
/*      */ 
/*      */ 
/*  304 */   public int imageMode = 0;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public PFont textFont;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  314 */   public int textAlign = 37;
/*      */   
/*      */ 
/*  317 */   public int textAlignY = 0;
/*      */   
/*      */ 
/*  320 */   public int textMode = 4;
/*      */   
/*      */   public float textSize;
/*      */   
/*      */   public float textLeading;
/*      */   
/*      */   public float ambientR;
/*      */   
/*      */   public float ambientG;
/*      */   
/*      */   public float ambientB;
/*      */   
/*      */   public float specularR;
/*      */   
/*      */   public float specularG;
/*      */   
/*      */   public float specularB;
/*      */   
/*      */   public float emissiveR;
/*      */   
/*      */   public float emissiveG;
/*      */   
/*      */   public float emissiveB;
/*      */   public float shininess;
/*      */   static final int STYLE_STACK_DEPTH = 64;
/*  345 */   PStyle[] styleStack = new PStyle[64];
/*      */   
/*      */ 
/*      */ 
/*      */   int styleStackDepth;
/*      */   
/*      */ 
/*      */ 
/*  353 */   public int backgroundColor = -3355444;
/*      */   
/*      */   protected boolean backgroundAlpha;
/*      */   
/*      */   protected float backgroundR;
/*      */   
/*      */   protected float backgroundG;
/*      */   
/*      */   protected float backgroundB;
/*      */   
/*      */   protected float backgroundA;
/*      */   
/*      */   protected int backgroundRi;
/*      */   
/*      */   protected int backgroundGi;
/*      */   
/*      */   protected int backgroundBi;
/*      */   
/*      */   protected int backgroundAi;
/*      */   
/*      */   static final int MATRIX_STACK_DEPTH = 32;
/*      */   
/*      */   public Image image;
/*      */   
/*      */   protected float calcR;
/*      */   
/*      */   protected float calcG;
/*      */   
/*      */   protected float calcB;
/*      */   
/*      */   protected float calcA;
/*      */   
/*      */   protected int calcRi;
/*      */   
/*      */   protected int calcGi;
/*      */   
/*      */   protected int calcBi;
/*      */   
/*      */   protected int calcAi;
/*      */   
/*      */   protected int calcColor;
/*      */   
/*      */   protected boolean calcAlpha;
/*      */   
/*      */   int cacheHsbKey;
/*  398 */   float[] cacheHsbValue = new float[3];
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected int shape;
/*      */   
/*      */ 
/*      */ 
/*      */   static final int DEFAULT_VERTICES = 512;
/*      */   
/*      */ 
/*      */ 
/*  411 */   protected float[][] vertices = new float['Ȁ'][36];
/*      */   
/*      */ 
/*      */   protected int vertexCount;
/*      */   
/*  416 */   protected boolean bezierInited = false;
/*  417 */   public int bezierDetail = 20;
/*      */   
/*      */ 
/*      */ 
/*  421 */   protected PMatrix3D bezierBasisMatrix = new PMatrix3D(-1.0F, 3.0F, -3.0F, 1.0F, 
/*  422 */     3.0F, -6.0F, 3.0F, 0.0F, 
/*  423 */     -3.0F, 3.0F, 0.0F, 0.0F, 
/*  424 */     1.0F, 0.0F, 0.0F, 0.0F);
/*      */   
/*      */ 
/*      */ 
/*      */   protected PMatrix3D bezierDrawMatrix;
/*      */   
/*      */ 
/*  431 */   protected boolean curveInited = false;
/*  432 */   protected int curveDetail = 20;
/*  433 */   public float curveTightness = 0.0F;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected PMatrix3D curveBasisMatrix;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected PMatrix3D curveDrawMatrix;
/*      */   
/*      */ 
/*      */ 
/*      */   protected PMatrix3D bezierBasisInverse;
/*      */   
/*      */ 
/*      */ 
/*      */   protected PMatrix3D curveToBezierMatrix;
/*      */   
/*      */ 
/*      */ 
/*      */   protected float[][] curveVertices;
/*      */   
/*      */ 
/*      */ 
/*      */   protected int curveVertexCount;
/*      */   
/*      */ 
/*      */ 
/*  463 */   protected static final float[] sinLUT = new float['ː'];
/*  464 */   protected static final float[] cosLUT = new float['ː'];
/*  465 */   static { for (int i = 0; i < 720; i++) {
/*  466 */       sinLUT[i] = ((float)Math.sin(i * 0.017453292F * 0.5F));
/*  467 */       cosLUT[i] = ((float)Math.cos(i * 0.017453292F * 0.5F));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected static final float SINCOS_PRECISION = 0.5F;
/*      */   
/*      */ 
/*      */   protected static final int SINCOS_LENGTH = 720;
/*      */   
/*      */ 
/*      */   protected float textX;
/*      */   
/*      */ 
/*      */   protected float textY;
/*      */   
/*      */   protected float textZ;
/*      */   
/*  486 */   protected char[] textBuffer = new char[' '];
/*  487 */   protected char[] textWidthBuffer = new char[' '];
/*      */   
/*      */   protected int textBreakCount;
/*      */   
/*      */   protected int[] textBreakStart;
/*      */   
/*      */   protected int[] textBreakStop;
/*      */   
/*  495 */   public boolean edge = true;
/*      */   
/*      */ 
/*      */ 
/*      */   protected static final int NORMAL_MODE_AUTO = 0;
/*      */   
/*      */ 
/*      */ 
/*      */   protected static final int NORMAL_MODE_SHAPE = 1;
/*      */   
/*      */ 
/*      */ 
/*      */   protected static final int NORMAL_MODE_VERTEX = 2;
/*      */   
/*      */ 
/*      */   protected int normalMode;
/*      */   
/*      */ 
/*      */   public float normalX;
/*      */   
/*      */ 
/*      */   public float normalY;
/*      */   
/*      */ 
/*      */   public float normalZ;
/*      */   
/*      */ 
/*      */   public int textureMode;
/*      */   
/*      */ 
/*      */   public float textureU;
/*      */   
/*      */ 
/*      */   public float textureV;
/*      */   
/*      */ 
/*      */   public PImage textureImage;
/*      */   
/*      */ 
/*      */   float[] sphereX;
/*      */   
/*      */ 
/*      */   float[] sphereY;
/*      */   
/*      */ 
/*      */   float[] sphereZ;
/*      */   
/*      */ 
/*  543 */   public int sphereDetailU = 0;
/*      */   
/*  545 */   public int sphereDetailV = 0;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   static float[] lerpColorHSB1;
/*      */   
/*      */ 
/*      */ 
/*      */   static float[] lerpColorHSB2;
/*      */   
/*      */ 
/*      */ 
/*      */   protected static HashMap<String, Object> warnings;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setParent(PApplet parent)
/*      */   {
/*  565 */     this.parent = parent;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setPrimary(boolean primary)
/*      */   {
/*  575 */     this.primarySurface = primary;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  580 */     if (this.primarySurface) {
/*  581 */       this.format = 1;
/*      */     }
/*      */   }
/*      */   
/*      */   public void setPath(String path)
/*      */   {
/*  587 */     this.path = path;
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
/*      */   public void setSize(int w, int h)
/*      */   {
/*  603 */     this.width = w;
/*  604 */     this.height = h;
/*  605 */     this.width1 = (this.width - 1);
/*  606 */     this.height1 = (this.height - 1);
/*      */     
/*  608 */     allocate();
/*  609 */     reapplySettings();
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
/*      */   protected void allocate() {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void dispose() {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean canDraw()
/*      */   {
/*  641 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void beginDraw() {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void endDraw() {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void flush() {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void checkSettings()
/*      */   {
/*  679 */     if (!this.settingsInited) { defaultSettings();
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
/*      */   protected void defaultSettings()
/*      */   {
/*  695 */     noSmooth();
/*      */     
/*  697 */     colorMode(1, 255.0F);
/*  698 */     fill(255);
/*  699 */     stroke(0);
/*      */     
/*      */ 
/*      */ 
/*  703 */     strokeWeight(1.0F);
/*  704 */     strokeJoin(8);
/*  705 */     strokeCap(2);
/*      */     
/*      */ 
/*  708 */     this.shape = 0;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  713 */     rectMode(0);
/*  714 */     ellipseMode(3);
/*      */     
/*      */ 
/*  717 */     this.textFont = null;
/*  718 */     this.textSize = 12.0F;
/*  719 */     this.textLeading = 14.0F;
/*  720 */     this.textAlign = 37;
/*  721 */     this.textMode = 4;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  729 */     if (this.primarySurface)
/*      */     {
/*  731 */       background(this.backgroundColor);
/*      */     }
/*      */     
/*  734 */     this.settingsInited = true;
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
/*      */   protected void reapplySettings()
/*      */   {
/*  752 */     if (!this.settingsInited) { return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  757 */     colorMode(this.colorMode, this.colorModeX, this.colorModeY, this.colorModeZ);
/*  758 */     if (this.fill)
/*      */     {
/*  760 */       fill(this.fillColor);
/*      */     } else {
/*  762 */       noFill();
/*      */     }
/*  764 */     if (this.stroke) {
/*  765 */       stroke(this.strokeColor);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  770 */       strokeWeight(this.strokeWeight);
/*      */       
/*      */ 
/*  773 */       strokeCap(this.strokeCap);
/*      */       
/*      */ 
/*  776 */       strokeJoin(this.strokeJoin);
/*      */     }
/*      */     else {
/*  779 */       noStroke();
/*      */     }
/*  781 */     if (this.tint) {
/*  782 */       tint(this.tintColor);
/*      */     } else {
/*  784 */       noTint();
/*      */     }
/*  786 */     if (this.smooth) {
/*  787 */       smooth();
/*      */     }
/*      */     else {
/*  790 */       noSmooth();
/*      */     }
/*  792 */     if (this.textFont != null)
/*      */     {
/*      */ 
/*  795 */       float saveLeading = this.textLeading;
/*  796 */       textFont(this.textFont, this.textSize);
/*  797 */       textLeading(saveLeading);
/*      */     }
/*  799 */     textMode(this.textMode);
/*  800 */     textAlign(this.textAlign, this.textAlignY);
/*  801 */     background(this.backgroundColor);
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
/*      */   public void hint(int which)
/*      */   {
/*  830 */     if (which > 0) {
/*  831 */       this.hints[which] = true;
/*      */     } else {
/*  833 */       this.hints[(-which)] = false;
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
/*      */   public void beginShape()
/*      */   {
/*  847 */     beginShape(20);
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
/*      */   public void beginShape(int kind)
/*      */   {
/*  874 */     this.shape = kind;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void edge(boolean edge)
/*      */   {
/*  883 */     this.edge = edge;
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
/*      */   public void normal(float nx, float ny, float nz)
/*      */   {
/*  903 */     this.normalX = nx;
/*  904 */     this.normalY = ny;
/*  905 */     this.normalZ = nz;
/*      */     
/*      */ 
/*      */ 
/*  909 */     if (this.shape != 0) {
/*  910 */       if (this.normalMode == 0)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  918 */         this.normalMode = 1;
/*      */       }
/*  920 */       else if (this.normalMode == 1)
/*      */       {
/*  922 */         this.normalMode = 2;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void textureMode(int mode)
/*      */   {
/*  933 */     this.textureMode = mode;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void texture(PImage image)
/*      */   {
/*  944 */     this.textureImage = image;
/*      */   }
/*      */   
/*      */   protected void vertexCheck()
/*      */   {
/*  949 */     if (this.vertexCount == this.vertices.length) {
/*  950 */       float[][] temp = new float[this.vertexCount << 1][36];
/*  951 */       System.arraycopy(this.vertices, 0, temp, 0, this.vertexCount);
/*  952 */       this.vertices = temp;
/*      */     }
/*      */   }
/*      */   
/*      */   public void vertex(float x, float y)
/*      */   {
/*  958 */     vertexCheck();
/*  959 */     float[] vertex = this.vertices[this.vertexCount];
/*      */     
/*  961 */     this.curveVertexCount = 0;
/*      */     
/*  963 */     vertex[0] = x;
/*  964 */     vertex[1] = y;
/*      */     
/*  966 */     vertex[12] = (this.edge ? 1 : 0);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  974 */     if ((this.fill) || (this.textureImage != null)) {
/*  975 */       if (this.textureImage == null) {
/*  976 */         vertex[3] = this.fillR;
/*  977 */         vertex[4] = this.fillG;
/*  978 */         vertex[5] = this.fillB;
/*  979 */         vertex[6] = this.fillA;
/*      */       }
/*  981 */       else if (this.tint) {
/*  982 */         vertex[3] = this.tintR;
/*  983 */         vertex[4] = this.tintG;
/*  984 */         vertex[5] = this.tintB;
/*  985 */         vertex[6] = this.tintA;
/*      */       } else {
/*  987 */         vertex[3] = 1.0F;
/*  988 */         vertex[4] = 1.0F;
/*  989 */         vertex[5] = 1.0F;
/*  990 */         vertex[6] = 1.0F;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  995 */     if (this.stroke) {
/*  996 */       vertex[13] = this.strokeR;
/*  997 */       vertex[14] = this.strokeG;
/*  998 */       vertex[15] = this.strokeB;
/*  999 */       vertex[16] = this.strokeA;
/* 1000 */       vertex[17] = this.strokeWeight;
/*      */     }
/*      */     
/* 1003 */     if (this.textureImage != null) {
/* 1004 */       vertex[7] = this.textureU;
/* 1005 */       vertex[8] = this.textureV;
/*      */     }
/*      */     
/* 1008 */     this.vertexCount += 1;
/*      */   }
/*      */   
/*      */   public void vertex(float x, float y, float z)
/*      */   {
/* 1013 */     vertexCheck();
/* 1014 */     float[] vertex = this.vertices[this.vertexCount];
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1020 */     if ((this.shape == 20) && 
/* 1021 */       (this.vertexCount > 0)) {
/* 1022 */       float[] pvertex = this.vertices[(this.vertexCount - 1)];
/* 1023 */       if ((Math.abs(pvertex[0] - x) < 1.0E-4F) && 
/* 1024 */         (Math.abs(pvertex[1] - y) < 1.0E-4F) && 
/* 1025 */         (Math.abs(pvertex[2] - z) < 1.0E-4F))
/*      */       {
/*      */ 
/* 1028 */         return;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1036 */     this.curveVertexCount = 0;
/*      */     
/* 1038 */     vertex[0] = x;
/* 1039 */     vertex[1] = y;
/* 1040 */     vertex[2] = z;
/*      */     
/* 1042 */     vertex[12] = (this.edge ? 1 : 0);
/*      */     
/* 1044 */     if ((this.fill) || (this.textureImage != null)) {
/* 1045 */       if (this.textureImage == null) {
/* 1046 */         vertex[3] = this.fillR;
/* 1047 */         vertex[4] = this.fillG;
/* 1048 */         vertex[5] = this.fillB;
/* 1049 */         vertex[6] = this.fillA;
/*      */       }
/* 1051 */       else if (this.tint) {
/* 1052 */         vertex[3] = this.tintR;
/* 1053 */         vertex[4] = this.tintG;
/* 1054 */         vertex[5] = this.tintB;
/* 1055 */         vertex[6] = this.tintA;
/*      */       } else {
/* 1057 */         vertex[3] = 1.0F;
/* 1058 */         vertex[4] = 1.0F;
/* 1059 */         vertex[5] = 1.0F;
/* 1060 */         vertex[6] = 1.0F;
/*      */       }
/*      */       
/*      */ 
/* 1064 */       vertex[25] = this.ambientR;
/* 1065 */       vertex[26] = this.ambientG;
/* 1066 */       vertex[27] = this.ambientB;
/*      */       
/* 1068 */       vertex[28] = this.specularR;
/* 1069 */       vertex[29] = this.specularG;
/* 1070 */       vertex[30] = this.specularB;
/*      */       
/*      */ 
/* 1073 */       vertex[31] = this.shininess;
/*      */       
/* 1075 */       vertex[32] = this.emissiveR;
/* 1076 */       vertex[33] = this.emissiveG;
/* 1077 */       vertex[34] = this.emissiveB;
/*      */     }
/*      */     
/* 1080 */     if (this.stroke) {
/* 1081 */       vertex[13] = this.strokeR;
/* 1082 */       vertex[14] = this.strokeG;
/* 1083 */       vertex[15] = this.strokeB;
/* 1084 */       vertex[16] = this.strokeA;
/* 1085 */       vertex[17] = this.strokeWeight;
/*      */     }
/*      */     
/* 1088 */     if (this.textureImage != null) {
/* 1089 */       vertex[7] = this.textureU;
/* 1090 */       vertex[8] = this.textureV;
/*      */     }
/*      */     
/* 1093 */     vertex[9] = this.normalX;
/* 1094 */     vertex[10] = this.normalY;
/* 1095 */     vertex[11] = this.normalZ;
/*      */     
/* 1097 */     vertex[35] = 0.0F;
/*      */     
/* 1099 */     this.vertexCount += 1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void vertex(float[] v)
/*      */   {
/* 1109 */     vertexCheck();
/* 1110 */     this.curveVertexCount = 0;
/* 1111 */     float[] vertex = this.vertices[this.vertexCount];
/* 1112 */     System.arraycopy(v, 0, vertex, 0, 36);
/* 1113 */     this.vertexCount += 1;
/*      */   }
/*      */   
/*      */   public void vertex(float x, float y, float u, float v)
/*      */   {
/* 1118 */     vertexTexture(u, v);
/* 1119 */     vertex(x, y);
/*      */   }
/*      */   
/*      */   public void vertex(float x, float y, float z, float u, float v)
/*      */   {
/* 1124 */     vertexTexture(u, v);
/* 1125 */     vertex(x, y, z);
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
/*      */   protected void vertexTexture(float u, float v)
/*      */   {
/* 1153 */     if (this.textureImage == null) {
/* 1154 */       throw new RuntimeException("You must first call texture() before using u and v coordinates with vertex()");
/*      */     }
/*      */     
/* 1157 */     if (this.textureMode == 2) {
/* 1158 */       u /= this.textureImage.width;
/* 1159 */       v /= this.textureImage.height;
/*      */     }
/*      */     
/* 1162 */     this.textureU = u;
/* 1163 */     this.textureV = v;
/*      */     
/* 1165 */     if (this.textureU < 0.0F) { this.textureU = 0.0F;
/* 1166 */     } else if (this.textureU > 1.0F) { this.textureU = 1.0F;
/*      */     }
/* 1168 */     if (this.textureV < 0.0F) { this.textureV = 0.0F;
/* 1169 */     } else if (this.textureV > 1.0F) { this.textureV = 1.0F;
/*      */     }
/*      */   }
/*      */   
/*      */   public void breakShape()
/*      */   {
/* 1175 */     showWarning("This renderer cannot currently handle concave shapes, or shapes with holes.");
/*      */   }
/*      */   
/*      */ 
/*      */   public void endShape()
/*      */   {
/* 1181 */     endShape(1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void endShape(int mode) {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void bezierVertexCheck()
/*      */   {
/* 1196 */     if ((this.shape == 0) || (this.shape != 20)) {
/* 1197 */       throw new RuntimeException("beginShape() or beginShape(POLYGON) must be used before bezierVertex()");
/*      */     }
/*      */     
/* 1200 */     if (this.vertexCount == 0) {
/* 1201 */       throw new RuntimeException("vertex() must be used at least oncebefore bezierVertex()");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void bezierVertex(float x2, float y2, float x3, float y3, float x4, float y4)
/*      */   {
/* 1210 */     bezierInitCheck();
/* 1211 */     bezierVertexCheck();
/* 1212 */     PMatrix3D draw = this.bezierDrawMatrix;
/*      */     
/* 1214 */     float[] prev = this.vertices[(this.vertexCount - 1)];
/* 1215 */     float x1 = prev[0];
/* 1216 */     float y1 = prev[1];
/*      */     
/* 1218 */     float xplot1 = draw.m10 * x1 + draw.m11 * x2 + draw.m12 * x3 + draw.m13 * x4;
/* 1219 */     float xplot2 = draw.m20 * x1 + draw.m21 * x2 + draw.m22 * x3 + draw.m23 * x4;
/* 1220 */     float xplot3 = draw.m30 * x1 + draw.m31 * x2 + draw.m32 * x3 + draw.m33 * x4;
/*      */     
/* 1222 */     float yplot1 = draw.m10 * y1 + draw.m11 * y2 + draw.m12 * y3 + draw.m13 * y4;
/* 1223 */     float yplot2 = draw.m20 * y1 + draw.m21 * y2 + draw.m22 * y3 + draw.m23 * y4;
/* 1224 */     float yplot3 = draw.m30 * y1 + draw.m31 * y2 + draw.m32 * y3 + draw.m33 * y4;
/*      */     
/* 1226 */     for (int j = 0; j < this.bezierDetail; j++) {
/* 1227 */       x1 += xplot1;xplot1 += xplot2;xplot2 += xplot3;
/* 1228 */       y1 += yplot1;yplot1 += yplot2;yplot2 += yplot3;
/* 1229 */       vertex(x1, y1);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void bezierVertex(float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4)
/*      */   {
/* 1237 */     bezierInitCheck();
/* 1238 */     bezierVertexCheck();
/* 1239 */     PMatrix3D draw = this.bezierDrawMatrix;
/*      */     
/* 1241 */     float[] prev = this.vertices[(this.vertexCount - 1)];
/* 1242 */     float x1 = prev[0];
/* 1243 */     float y1 = prev[1];
/* 1244 */     float z1 = prev[2];
/*      */     
/* 1246 */     float xplot1 = draw.m10 * x1 + draw.m11 * x2 + draw.m12 * x3 + draw.m13 * x4;
/* 1247 */     float xplot2 = draw.m20 * x1 + draw.m21 * x2 + draw.m22 * x3 + draw.m23 * x4;
/* 1248 */     float xplot3 = draw.m30 * x1 + draw.m31 * x2 + draw.m32 * x3 + draw.m33 * x4;
/*      */     
/* 1250 */     float yplot1 = draw.m10 * y1 + draw.m11 * y2 + draw.m12 * y3 + draw.m13 * y4;
/* 1251 */     float yplot2 = draw.m20 * y1 + draw.m21 * y2 + draw.m22 * y3 + draw.m23 * y4;
/* 1252 */     float yplot3 = draw.m30 * y1 + draw.m31 * y2 + draw.m32 * y3 + draw.m33 * y4;
/*      */     
/* 1254 */     float zplot1 = draw.m10 * z1 + draw.m11 * z2 + draw.m12 * z3 + draw.m13 * z4;
/* 1255 */     float zplot2 = draw.m20 * z1 + draw.m21 * z2 + draw.m22 * z3 + draw.m23 * z4;
/* 1256 */     float zplot3 = draw.m30 * z1 + draw.m31 * z2 + draw.m32 * z3 + draw.m33 * z4;
/*      */     
/* 1258 */     for (int j = 0; j < this.bezierDetail; j++) {
/* 1259 */       x1 += xplot1;xplot1 += xplot2;xplot2 += xplot3;
/* 1260 */       y1 += yplot1;yplot1 += yplot2;yplot2 += yplot3;
/* 1261 */       z1 += zplot1;zplot1 += zplot2;zplot2 += zplot3;
/* 1262 */       vertex(x1, y1, z1);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void curveVertexCheck()
/*      */   {
/* 1272 */     if (this.shape != 20) {
/* 1273 */       throw new RuntimeException("You must use beginShape() or beginShape(POLYGON) before curveVertex()");
/*      */     }
/*      */     
/*      */ 
/* 1277 */     if (this.curveVertices == null) {
/* 1278 */       this.curveVertices = new float[''][3];
/*      */     }
/*      */     
/* 1281 */     if (this.curveVertexCount == this.curveVertices.length)
/*      */     {
/* 1283 */       float[][] temp = new float[this.curveVertexCount << 1][3];
/* 1284 */       System.arraycopy(this.curveVertices, 0, temp, 0, this.curveVertexCount);
/* 1285 */       this.curveVertices = temp;
/*      */     }
/* 1287 */     curveInitCheck();
/*      */   }
/*      */   
/*      */   public void curveVertex(float x, float y)
/*      */   {
/* 1292 */     curveVertexCheck();
/* 1293 */     float[] vertex = this.curveVertices[this.curveVertexCount];
/* 1294 */     vertex[0] = x;
/* 1295 */     vertex[1] = y;
/* 1296 */     this.curveVertexCount += 1;
/*      */     
/*      */ 
/* 1299 */     if (this.curveVertexCount > 3) {
/* 1300 */       curveVertexSegment(this.curveVertices[(this.curveVertexCount - 4)][0], 
/* 1301 */         this.curveVertices[(this.curveVertexCount - 4)][1], 
/* 1302 */         this.curveVertices[(this.curveVertexCount - 3)][0], 
/* 1303 */         this.curveVertices[(this.curveVertexCount - 3)][1], 
/* 1304 */         this.curveVertices[(this.curveVertexCount - 2)][0], 
/* 1305 */         this.curveVertices[(this.curveVertexCount - 2)][1], 
/* 1306 */         this.curveVertices[(this.curveVertexCount - 1)][0], 
/* 1307 */         this.curveVertices[(this.curveVertexCount - 1)][1]);
/*      */     }
/*      */   }
/*      */   
/*      */   public void curveVertex(float x, float y, float z)
/*      */   {
/* 1313 */     curveVertexCheck();
/* 1314 */     float[] vertex = this.curveVertices[this.curveVertexCount];
/* 1315 */     vertex[0] = x;
/* 1316 */     vertex[1] = y;
/* 1317 */     vertex[2] = z;
/* 1318 */     this.curveVertexCount += 1;
/*      */     
/*      */ 
/* 1321 */     if (this.curveVertexCount > 3) {
/* 1322 */       curveVertexSegment(this.curveVertices[(this.curveVertexCount - 4)][0], 
/* 1323 */         this.curveVertices[(this.curveVertexCount - 4)][1], 
/* 1324 */         this.curveVertices[(this.curveVertexCount - 4)][2], 
/* 1325 */         this.curveVertices[(this.curveVertexCount - 3)][0], 
/* 1326 */         this.curveVertices[(this.curveVertexCount - 3)][1], 
/* 1327 */         this.curveVertices[(this.curveVertexCount - 3)][2], 
/* 1328 */         this.curveVertices[(this.curveVertexCount - 2)][0], 
/* 1329 */         this.curveVertices[(this.curveVertexCount - 2)][1], 
/* 1330 */         this.curveVertices[(this.curveVertexCount - 2)][2], 
/* 1331 */         this.curveVertices[(this.curveVertexCount - 1)][0], 
/* 1332 */         this.curveVertices[(this.curveVertexCount - 1)][1], 
/* 1333 */         this.curveVertices[(this.curveVertexCount - 1)][2]);
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
/*      */   protected void curveVertexSegment(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4)
/*      */   {
/* 1346 */     float x0 = x2;
/* 1347 */     float y0 = y2;
/*      */     
/* 1349 */     PMatrix3D draw = this.curveDrawMatrix;
/*      */     
/* 1351 */     float xplot1 = draw.m10 * x1 + draw.m11 * x2 + draw.m12 * x3 + draw.m13 * x4;
/* 1352 */     float xplot2 = draw.m20 * x1 + draw.m21 * x2 + draw.m22 * x3 + draw.m23 * x4;
/* 1353 */     float xplot3 = draw.m30 * x1 + draw.m31 * x2 + draw.m32 * x3 + draw.m33 * x4;
/*      */     
/* 1355 */     float yplot1 = draw.m10 * y1 + draw.m11 * y2 + draw.m12 * y3 + draw.m13 * y4;
/* 1356 */     float yplot2 = draw.m20 * y1 + draw.m21 * y2 + draw.m22 * y3 + draw.m23 * y4;
/* 1357 */     float yplot3 = draw.m30 * y1 + draw.m31 * y2 + draw.m32 * y3 + draw.m33 * y4;
/*      */     
/*      */ 
/* 1360 */     int savedCount = this.curveVertexCount;
/*      */     
/* 1362 */     vertex(x0, y0);
/* 1363 */     for (int j = 0; j < this.curveDetail; j++) {
/* 1364 */       x0 += xplot1;xplot1 += xplot2;xplot2 += xplot3;
/* 1365 */       y0 += yplot1;yplot1 += yplot2;yplot2 += yplot3;
/* 1366 */       vertex(x0, y0);
/*      */     }
/* 1368 */     this.curveVertexCount = savedCount;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void curveVertexSegment(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4)
/*      */   {
/* 1380 */     float x0 = x2;
/* 1381 */     float y0 = y2;
/* 1382 */     float z0 = z2;
/*      */     
/* 1384 */     PMatrix3D draw = this.curveDrawMatrix;
/*      */     
/* 1386 */     float xplot1 = draw.m10 * x1 + draw.m11 * x2 + draw.m12 * x3 + draw.m13 * x4;
/* 1387 */     float xplot2 = draw.m20 * x1 + draw.m21 * x2 + draw.m22 * x3 + draw.m23 * x4;
/* 1388 */     float xplot3 = draw.m30 * x1 + draw.m31 * x2 + draw.m32 * x3 + draw.m33 * x4;
/*      */     
/* 1390 */     float yplot1 = draw.m10 * y1 + draw.m11 * y2 + draw.m12 * y3 + draw.m13 * y4;
/* 1391 */     float yplot2 = draw.m20 * y1 + draw.m21 * y2 + draw.m22 * y3 + draw.m23 * y4;
/* 1392 */     float yplot3 = draw.m30 * y1 + draw.m31 * y2 + draw.m32 * y3 + draw.m33 * y4;
/*      */     
/*      */ 
/* 1395 */     int savedCount = this.curveVertexCount;
/*      */     
/* 1397 */     float zplot1 = draw.m10 * z1 + draw.m11 * z2 + draw.m12 * z3 + draw.m13 * z4;
/* 1398 */     float zplot2 = draw.m20 * z1 + draw.m21 * z2 + draw.m22 * z3 + draw.m23 * z4;
/* 1399 */     float zplot3 = draw.m30 * z1 + draw.m31 * z2 + draw.m32 * z3 + draw.m33 * z4;
/*      */     
/* 1401 */     vertex(x0, y0, z0);
/* 1402 */     for (int j = 0; j < this.curveDetail; j++) {
/* 1403 */       x0 += xplot1;xplot1 += xplot2;xplot2 += xplot3;
/* 1404 */       y0 += yplot1;yplot1 += yplot2;yplot2 += yplot3;
/* 1405 */       z0 += zplot1;zplot1 += zplot2;zplot2 += zplot3;
/* 1406 */       vertex(x0, y0, z0);
/*      */     }
/* 1408 */     this.curveVertexCount = savedCount;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void point(float x, float y)
/*      */   {
/* 1419 */     beginShape(2);
/* 1420 */     vertex(x, y);
/* 1421 */     endShape();
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
/*      */   public void point(float x, float y, float z)
/*      */   {
/* 1446 */     beginShape(2);
/* 1447 */     vertex(x, y, z);
/* 1448 */     endShape();
/*      */   }
/*      */   
/*      */   public void line(float x1, float y1, float x2, float y2)
/*      */   {
/* 1453 */     beginShape(4);
/* 1454 */     vertex(x1, y1);
/* 1455 */     vertex(x2, y2);
/* 1456 */     endShape();
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
/*      */   public void line(float x1, float y1, float z1, float x2, float y2, float z2)
/*      */   {
/* 1488 */     beginShape(4);
/* 1489 */     vertex(x1, y1, z1);
/* 1490 */     vertex(x2, y2, z2);
/* 1491 */     endShape();
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
/*      */   public void triangle(float x1, float y1, float x2, float y2, float x3, float y3)
/*      */   {
/* 1512 */     beginShape(9);
/* 1513 */     vertex(x1, y1);
/* 1514 */     vertex(x2, y2);
/* 1515 */     vertex(x3, y3);
/* 1516 */     endShape();
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
/*      */   public void quad(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4)
/*      */   {
/* 1540 */     beginShape(16);
/* 1541 */     vertex(x1, y1);
/* 1542 */     vertex(x2, y2);
/* 1543 */     vertex(x3, y3);
/* 1544 */     vertex(x4, y4);
/* 1545 */     endShape();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void rectMode(int mode)
/*      */   {
/* 1556 */     this.rectMode = mode;
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
/*      */   public void rect(float a, float b, float c, float d)
/*      */   {
/* 1577 */     switch (this.rectMode) {
/*      */     case 1: 
/*      */       break;
/*      */     case 0: 
/* 1581 */       c += a;d += b;
/* 1582 */       break;
/*      */     case 2: 
/* 1584 */       float hradius = c;
/* 1585 */       float vradius = d;
/* 1586 */       c = a + hradius;
/* 1587 */       d = b + vradius;
/* 1588 */       a -= hradius;
/* 1589 */       b -= vradius;
/* 1590 */       break;
/*      */     case 3: 
/* 1592 */       float hradius = c / 2.0F;
/* 1593 */       float vradius = d / 2.0F;
/* 1594 */       c = a + hradius;
/* 1595 */       d = b + vradius;
/* 1596 */       a -= hradius;
/* 1597 */       b -= vradius;
/*      */     }
/*      */     
/* 1600 */     if (a > c) {
/* 1601 */       float temp = a;a = c;c = temp;
/*      */     }
/*      */     
/* 1604 */     if (b > d) {
/* 1605 */       float temp = b;b = d;d = temp;
/*      */     }
/*      */     
/* 1608 */     rectImpl(a, b, c, d);
/*      */   }
/*      */   
/*      */   protected void rectImpl(float x1, float y1, float x2, float y2)
/*      */   {
/* 1613 */     quad(x1, y1, x2, y1, x2, y2, x1, y2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void quadraticVertex(float cpx, float cpy, float x, float y)
/*      */   {
/* 1622 */     float[] prev = this.vertices[(this.vertexCount - 1)];
/* 1623 */     float prevX = prev[0];
/* 1624 */     float prevY = prev[1];
/* 1625 */     float cp1x = prevX + 0.6666667F * (cpx - prevX);
/* 1626 */     float cp1y = prevY + 0.6666667F * (cpy - prevY);
/* 1627 */     float cp2x = cp1x + (x - prevX) / 3.0F;
/* 1628 */     float cp2y = cp1y + (y - prevY) / 3.0F;
/* 1629 */     bezierVertex(cp1x, cp1y, cp2x, cp2y, x, y);
/*      */   }
/*      */   
/*      */ 
/*      */   public void rect(float a, float b, float c, float d, float hr, float vr)
/*      */   {
/* 1635 */     switch (this.rectMode) {
/*      */     case 1: 
/*      */       break;
/*      */     case 0: 
/* 1639 */       c += a;d += b;
/* 1640 */       break;
/*      */     case 2: 
/* 1642 */       float hradius = c;
/* 1643 */       float vradius = d;
/* 1644 */       c = a + hradius;
/* 1645 */       d = b + vradius;
/* 1646 */       a -= hradius;
/* 1647 */       b -= vradius;
/* 1648 */       break;
/*      */     case 3: 
/* 1650 */       float hradius = c / 2.0F;
/* 1651 */       float vradius = d / 2.0F;
/* 1652 */       c = a + hradius;
/* 1653 */       d = b + vradius;
/* 1654 */       a -= hradius;
/* 1655 */       b -= vradius;
/*      */     }
/*      */     
/* 1658 */     if (a > c) {
/* 1659 */       float temp = a;a = c;c = temp;
/*      */     }
/*      */     
/* 1662 */     if (b > d) {
/* 1663 */       float temp = b;b = d;d = temp;
/*      */     }
/*      */     
/* 1666 */     rectImpl(a, b, c, d, hr, vr);
/*      */   }
/*      */   
/*      */   protected void rectImpl(float x1, float y1, float x2, float y2, float hr, float vr)
/*      */   {
/* 1671 */     beginShape();
/* 1672 */     vertex(x1 + hr, y1);
/* 1673 */     vertex(x2 - hr, y1);
/* 1674 */     quadraticVertex(x2, y1, x2, y1 + vr);
/* 1675 */     vertex(x2, y2 - vr);
/* 1676 */     quadraticVertex(x2, y2, x2 - hr, y2);
/* 1677 */     vertex(x1 + hr, y2);
/* 1678 */     quadraticVertex(x1, y2, x1, y2 - vr);
/* 1679 */     vertex(x1, y1 + vr);
/* 1680 */     quadraticVertex(x1, y1, x1 + hr, y1);
/* 1681 */     endShape();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void rect(float a, float b, float c, float d, float tl, float tr, float bl, float br)
/*      */   {
/* 1688 */     switch (this.rectMode) {
/*      */     case 1: 
/*      */       break;
/*      */     case 0: 
/* 1692 */       c += a;d += b;
/* 1693 */       break;
/*      */     case 2: 
/* 1695 */       float hradius = c;
/* 1696 */       float vradius = d;
/* 1697 */       c = a + hradius;
/* 1698 */       d = b + vradius;
/* 1699 */       a -= hradius;
/* 1700 */       b -= vradius;
/* 1701 */       break;
/*      */     case 3: 
/* 1703 */       float hradius = c / 2.0F;
/* 1704 */       float vradius = d / 2.0F;
/* 1705 */       c = a + hradius;
/* 1706 */       d = b + vradius;
/* 1707 */       a -= hradius;
/* 1708 */       b -= vradius;
/*      */     }
/*      */     
/* 1711 */     if (a > c) {
/* 1712 */       float temp = a;a = c;c = temp;
/*      */     }
/*      */     
/* 1715 */     if (b > d) {
/* 1716 */       float temp = b;b = d;d = temp;
/*      */     }
/*      */     
/* 1719 */     rectImpl(a, b, c, d, tl, tr, bl, br);
/*      */   }
/*      */   
/*      */ 
/*      */   protected void rectImpl(float x1, float y1, float x2, float y2, float tl, float tr, float bl, float br)
/*      */   {
/* 1725 */     beginShape();
/* 1726 */     vertex(x1 + tl, y1);
/* 1727 */     vertex(x2 - tr, y1);
/* 1728 */     quadraticVertex(x2, y1, x2, y1 + tr);
/* 1729 */     vertex(x2, y2 - br);
/* 1730 */     quadraticVertex(x2, y2, x2 - br, y2);
/* 1731 */     vertex(x1 + bl, y2);
/* 1732 */     quadraticVertex(x1, y2, x1, y2 - bl);
/* 1733 */     vertex(x1, y1 + tl);
/* 1734 */     quadraticVertex(x1, y1, x1 + tl, y1);
/* 1735 */     endShape();
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
/*      */   public void ellipseMode(int mode)
/*      */   {
/* 1763 */     this.ellipseMode = mode;
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
/*      */   public void ellipse(float a, float b, float c, float d)
/*      */   {
/* 1782 */     float x = a;
/* 1783 */     float y = b;
/* 1784 */     float w = c;
/* 1785 */     float h = d;
/*      */     
/* 1787 */     if (this.ellipseMode == 1) {
/* 1788 */       w = c - a;
/* 1789 */       h = d - b;
/*      */     }
/* 1791 */     else if (this.ellipseMode == 2) {
/* 1792 */       x = a - c;
/* 1793 */       y = b - d;
/* 1794 */       w = c * 2.0F;
/* 1795 */       h = d * 2.0F;
/*      */     }
/* 1797 */     else if (this.ellipseMode == 3) {
/* 1798 */       x = a - c / 2.0F;
/* 1799 */       y = b - d / 2.0F;
/*      */     }
/*      */     
/* 1802 */     if (w < 0.0F) {
/* 1803 */       x += w;
/* 1804 */       w = -w;
/*      */     }
/*      */     
/* 1807 */     if (h < 0.0F) {
/* 1808 */       y += h;
/* 1809 */       h = -h;
/*      */     }
/*      */     
/* 1812 */     ellipseImpl(x, y, w, h);
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
/*      */   protected void ellipseImpl(float x, float y, float w, float h) {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void arc(float a, float b, float c, float d, float start, float stop)
/*      */   {
/* 1842 */     float x = a;
/* 1843 */     float y = b;
/* 1844 */     float w = c;
/* 1845 */     float h = d;
/*      */     
/* 1847 */     if (this.ellipseMode == 1) {
/* 1848 */       w = c - a;
/* 1849 */       h = d - b;
/*      */     }
/* 1851 */     else if (this.ellipseMode == 2) {
/* 1852 */       x = a - c;
/* 1853 */       y = b - d;
/* 1854 */       w = c * 2.0F;
/* 1855 */       h = d * 2.0F;
/*      */     }
/* 1857 */     else if (this.ellipseMode == 3) {
/* 1858 */       x = a - c / 2.0F;
/* 1859 */       y = b - d / 2.0F;
/*      */     }
/*      */     
/*      */ 
/* 1863 */     if ((Float.isInfinite(start)) || (Float.isInfinite(stop))) { return;
/*      */     }
/* 1865 */     if (stop < start) { return;
/*      */     }
/*      */     
/* 1868 */     while (start < 0.0F) {
/* 1869 */       start += 6.2831855F;
/* 1870 */       stop += 6.2831855F;
/*      */     }
/*      */     
/* 1873 */     if (stop - start > 6.2831855F) {
/* 1874 */       start = 0.0F;
/* 1875 */       stop = 6.2831855F;
/*      */     }
/*      */     
/* 1878 */     arcImpl(x, y, w, h, start, stop);
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
/*      */   protected void arcImpl(float x, float y, float w, float h, float start, float stop) {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void box(float size)
/*      */   {
/* 1903 */     box(size, size, size);
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
/*      */   public void box(float w, float h, float d)
/*      */   {
/* 1919 */     float x1 = -w / 2.0F;float x2 = w / 2.0F;
/* 1920 */     float y1 = -h / 2.0F;float y2 = h / 2.0F;
/* 1921 */     float z1 = -d / 2.0F;float z2 = d / 2.0F;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1926 */     beginShape(16);
/*      */     
/*      */ 
/* 1929 */     normal(0.0F, 0.0F, 1.0F);
/* 1930 */     vertex(x1, y1, z1);
/* 1931 */     vertex(x2, y1, z1);
/* 1932 */     vertex(x2, y2, z1);
/* 1933 */     vertex(x1, y2, z1);
/*      */     
/*      */ 
/* 1936 */     normal(1.0F, 0.0F, 0.0F);
/* 1937 */     vertex(x2, y1, z1);
/* 1938 */     vertex(x2, y1, z2);
/* 1939 */     vertex(x2, y2, z2);
/* 1940 */     vertex(x2, y2, z1);
/*      */     
/*      */ 
/* 1943 */     normal(0.0F, 0.0F, -1.0F);
/* 1944 */     vertex(x2, y1, z2);
/* 1945 */     vertex(x1, y1, z2);
/* 1946 */     vertex(x1, y2, z2);
/* 1947 */     vertex(x2, y2, z2);
/*      */     
/*      */ 
/* 1950 */     normal(-1.0F, 0.0F, 0.0F);
/* 1951 */     vertex(x1, y1, z2);
/* 1952 */     vertex(x1, y1, z1);
/* 1953 */     vertex(x1, y2, z1);
/* 1954 */     vertex(x1, y2, z2);
/*      */     
/*      */ 
/* 1957 */     normal(0.0F, 1.0F, 0.0F);
/* 1958 */     vertex(x1, y1, z2);
/* 1959 */     vertex(x2, y1, z2);
/* 1960 */     vertex(x2, y1, z1);
/* 1961 */     vertex(x1, y1, z1);
/*      */     
/*      */ 
/* 1964 */     normal(0.0F, -1.0F, 0.0F);
/* 1965 */     vertex(x1, y2, z1);
/* 1966 */     vertex(x2, y2, z1);
/* 1967 */     vertex(x2, y2, z2);
/* 1968 */     vertex(x1, y2, z2);
/*      */     
/* 1970 */     endShape();
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
/*      */   public void sphereDetail(int res)
/*      */   {
/* 1984 */     sphereDetail(res, res);
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
/*      */   public void sphereDetail(int ures, int vres)
/*      */   {
/* 2020 */     if (ures < 3) ures = 3;
/* 2021 */     if (vres < 2) vres = 2;
/* 2022 */     if ((ures == this.sphereDetailU) && (vres == this.sphereDetailV)) { return;
/*      */     }
/* 2024 */     float delta = 720.0F / ures;
/* 2025 */     float[] cx = new float[ures];
/* 2026 */     float[] cz = new float[ures];
/*      */     
/* 2028 */     for (int i = 0; i < ures; i++) {
/* 2029 */       cx[i] = cosLUT[((int)(i * delta) % 720)];
/* 2030 */       cz[i] = sinLUT[((int)(i * delta) % 720)];
/*      */     }
/*      */     
/*      */ 
/* 2034 */     int vertCount = ures * (vres - 1) + 2;
/* 2035 */     int currVert = 0;
/*      */     
/*      */ 
/* 2038 */     this.sphereX = new float[vertCount];
/* 2039 */     this.sphereY = new float[vertCount];
/* 2040 */     this.sphereZ = new float[vertCount];
/*      */     
/* 2042 */     float angle_step = 360.0F / vres;
/* 2043 */     float angle = angle_step;
/*      */     
/*      */ 
/* 2046 */     for (int i = 1; i < vres; i++) {
/* 2047 */       float curradius = sinLUT[((int)angle % 720)];
/* 2048 */       float currY = -cosLUT[((int)angle % 720)];
/* 2049 */       for (int j = 0; j < ures; j++) {
/* 2050 */         this.sphereX[currVert] = (cx[j] * curradius);
/* 2051 */         this.sphereY[currVert] = currY;
/* 2052 */         this.sphereZ[(currVert++)] = (cz[j] * curradius);
/*      */       }
/* 2054 */       angle += angle_step;
/*      */     }
/* 2056 */     this.sphereDetailU = ures;
/* 2057 */     this.sphereDetailV = vres;
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
/*      */   public void sphere(float r)
/*      */   {
/* 2089 */     if ((this.sphereDetailU < 3) || (this.sphereDetailV < 2)) {
/* 2090 */       sphereDetail(30);
/*      */     }
/*      */     
/* 2093 */     pushMatrix();
/* 2094 */     scale(r);
/* 2095 */     edge(false);
/*      */     
/*      */ 
/* 2098 */     beginShape(10);
/* 2099 */     for (int i = 0; i < this.sphereDetailU; i++) {
/* 2100 */       normal(0.0F, -1.0F, 0.0F);
/* 2101 */       vertex(0.0F, -1.0F, 0.0F);
/* 2102 */       normal(this.sphereX[i], this.sphereY[i], this.sphereZ[i]);
/* 2103 */       vertex(this.sphereX[i], this.sphereY[i], this.sphereZ[i]);
/*      */     }
/*      */     
/* 2106 */     vertex(0.0F, -1.0F, 0.0F);
/* 2107 */     normal(this.sphereX[0], this.sphereY[0], this.sphereZ[0]);
/* 2108 */     vertex(this.sphereX[0], this.sphereY[0], this.sphereZ[0]);
/* 2109 */     endShape();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 2114 */     int voff = 0;
/* 2115 */     for (int i = 2; i < this.sphereDetailV; i++) { int v11;
/* 2116 */       int v1 = v11 = voff;
/* 2117 */       voff += this.sphereDetailU;
/* 2118 */       int v2 = voff;
/* 2119 */       beginShape(10);
/* 2120 */       for (int j = 0; j < this.sphereDetailU; j++) {
/* 2121 */         normal(this.sphereX[v1], this.sphereY[v1], this.sphereZ[v1]);
/* 2122 */         vertex(this.sphereX[v1], this.sphereY[v1], this.sphereZ[(v1++)]);
/* 2123 */         normal(this.sphereX[v2], this.sphereY[v2], this.sphereZ[v2]);
/* 2124 */         vertex(this.sphereX[v2], this.sphereY[v2], this.sphereZ[(v2++)]);
/*      */       }
/*      */       
/* 2127 */       v1 = v11;
/* 2128 */       v2 = voff;
/* 2129 */       normal(this.sphereX[v1], this.sphereY[v1], this.sphereZ[v1]);
/* 2130 */       vertex(this.sphereX[v1], this.sphereY[v1], this.sphereZ[v1]);
/* 2131 */       normal(this.sphereX[v2], this.sphereY[v2], this.sphereZ[v2]);
/* 2132 */       vertex(this.sphereX[v2], this.sphereY[v2], this.sphereZ[v2]);
/* 2133 */       endShape();
/*      */     }
/*      */     
/*      */ 
/* 2137 */     beginShape(10);
/* 2138 */     for (int i = 0; i < this.sphereDetailU; i++) {
/* 2139 */       int v2 = voff + i;
/* 2140 */       normal(this.sphereX[v2], this.sphereY[v2], this.sphereZ[v2]);
/* 2141 */       vertex(this.sphereX[v2], this.sphereY[v2], this.sphereZ[v2]);
/* 2142 */       normal(0.0F, 1.0F, 0.0F);
/* 2143 */       vertex(0.0F, 1.0F, 0.0F);
/*      */     }
/* 2145 */     normal(this.sphereX[voff], this.sphereY[voff], this.sphereZ[voff]);
/* 2146 */     vertex(this.sphereX[voff], this.sphereY[voff], this.sphereZ[voff]);
/* 2147 */     normal(0.0F, 1.0F, 0.0F);
/* 2148 */     vertex(0.0F, 1.0F, 0.0F);
/* 2149 */     endShape();
/*      */     
/* 2151 */     edge(true);
/* 2152 */     popMatrix();
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
/*      */   public float bezierPoint(float a, float b, float c, float d, float t)
/*      */   {
/* 2205 */     float t1 = 1.0F - t;
/* 2206 */     return a * t1 * t1 * t1 + 3.0F * b * t * t1 * t1 + 3.0F * c * t * t * t1 + d * t * t * t;
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
/*      */   public float bezierTangent(float a, float b, float c, float d, float t)
/*      */   {
/* 2229 */     return 3.0F * t * t * (-a + 3.0F * b - 3.0F * c + d) + 
/* 2230 */       6.0F * t * (a - 2.0F * b + c) + 
/* 2231 */       3.0F * (-a + b);
/*      */   }
/*      */   
/*      */   protected void bezierInitCheck()
/*      */   {
/* 2236 */     if (!this.bezierInited) {
/* 2237 */       bezierInit();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   protected void bezierInit()
/*      */   {
/* 2244 */     bezierDetail(this.bezierDetail);
/* 2245 */     this.bezierInited = true;
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
/*      */   public void bezierDetail(int detail)
/*      */   {
/* 2260 */     this.bezierDetail = detail;
/*      */     
/* 2262 */     if (this.bezierDrawMatrix == null) {
/* 2263 */       this.bezierDrawMatrix = new PMatrix3D();
/*      */     }
/*      */     
/*      */ 
/* 2267 */     splineForward(detail, this.bezierDrawMatrix);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2273 */     this.bezierDrawMatrix.apply(this.bezierBasisMatrix);
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
/*      */   public void bezier(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4)
/*      */   {
/* 2330 */     beginShape();
/* 2331 */     vertex(x1, y1);
/* 2332 */     bezierVertex(x2, y2, x3, y3, x4, y4);
/* 2333 */     endShape();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void bezier(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4)
/*      */   {
/* 2341 */     beginShape();
/* 2342 */     vertex(x1, y1, z1);
/* 2343 */     bezierVertex(x2, y2, z2, 
/* 2344 */       x3, y3, z3, 
/* 2345 */       x4, y4, z4);
/* 2346 */     endShape();
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
/*      */   public float curvePoint(float a, float b, float c, float d, float t)
/*      */   {
/* 2375 */     curveInitCheck();
/*      */     
/* 2377 */     float tt = t * t;
/* 2378 */     float ttt = t * tt;
/* 2379 */     PMatrix3D cb = this.curveBasisMatrix;
/*      */     
/*      */ 
/* 2382 */     return a * (ttt * cb.m00 + tt * cb.m10 + t * cb.m20 + cb.m30) + 
/* 2383 */       b * (ttt * cb.m01 + tt * cb.m11 + t * cb.m21 + cb.m31) + 
/* 2384 */       c * (ttt * cb.m02 + tt * cb.m12 + t * cb.m22 + cb.m32) + 
/* 2385 */       d * (ttt * cb.m03 + tt * cb.m13 + t * cb.m23 + cb.m33);
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
/*      */   public float curveTangent(float a, float b, float c, float d, float t)
/*      */   {
/* 2408 */     curveInitCheck();
/*      */     
/* 2410 */     float tt3 = t * t * 3.0F;
/* 2411 */     float t2 = t * 2.0F;
/* 2412 */     PMatrix3D cb = this.curveBasisMatrix;
/*      */     
/*      */ 
/* 2415 */     return a * (tt3 * cb.m00 + t2 * cb.m10 + cb.m20) + 
/* 2416 */       b * (tt3 * cb.m01 + t2 * cb.m11 + cb.m21) + 
/* 2417 */       c * (tt3 * cb.m02 + t2 * cb.m12 + cb.m22) + 
/* 2418 */       d * (tt3 * cb.m03 + t2 * cb.m13 + cb.m23);
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
/*      */   public void curveDetail(int detail)
/*      */   {
/* 2435 */     this.curveDetail = detail;
/* 2436 */     curveInit();
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
/*      */   public void curveTightness(float tightness)
/*      */   {
/* 2458 */     this.curveTightness = tightness;
/* 2459 */     curveInit();
/*      */   }
/*      */   
/*      */   protected void curveInitCheck()
/*      */   {
/* 2464 */     if (!this.curveInited) {
/* 2465 */       curveInit();
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
/*      */   protected void curveInit()
/*      */   {
/* 2483 */     if (this.curveDrawMatrix == null) {
/* 2484 */       this.curveBasisMatrix = new PMatrix3D();
/* 2485 */       this.curveDrawMatrix = new PMatrix3D();
/* 2486 */       this.curveInited = true;
/*      */     }
/*      */     
/* 2489 */     float s = this.curveTightness;
/* 2490 */     this.curveBasisMatrix.set((s - 1.0F) / 2.0F, (s + 3.0F) / 2.0F, (-3.0F - s) / 2.0F, (1.0F - s) / 2.0F, 
/* 2491 */       1.0F - s, (-5.0F - s) / 2.0F, s + 2.0F, (s - 1.0F) / 2.0F, 
/* 2492 */       (s - 1.0F) / 2.0F, 0.0F, (1.0F - s) / 2.0F, 0.0F, 
/* 2493 */       0.0F, 1.0F, 0.0F, 0.0F);
/*      */     
/*      */ 
/* 2496 */     splineForward(this.curveDetail, this.curveDrawMatrix);
/*      */     
/* 2498 */     if (this.bezierBasisInverse == null) {
/* 2499 */       this.bezierBasisInverse = this.bezierBasisMatrix.get();
/* 2500 */       this.bezierBasisInverse.invert();
/* 2501 */       this.curveToBezierMatrix = new PMatrix3D();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 2507 */     this.curveToBezierMatrix.set(this.curveBasisMatrix);
/* 2508 */     this.curveToBezierMatrix.preApply(this.bezierBasisInverse);
/*      */     
/*      */ 
/*      */ 
/* 2512 */     this.curveDrawMatrix.apply(this.curveBasisMatrix);
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
/*      */   public void curve(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4)
/*      */   {
/* 2564 */     beginShape();
/* 2565 */     curveVertex(x1, y1);
/* 2566 */     curveVertex(x2, y2);
/* 2567 */     curveVertex(x3, y3);
/* 2568 */     curveVertex(x4, y4);
/* 2569 */     endShape();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void curve(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4)
/*      */   {
/* 2577 */     beginShape();
/* 2578 */     curveVertex(x1, y1, z1);
/* 2579 */     curveVertex(x2, y2, z2);
/* 2580 */     curveVertex(x3, y3, z3);
/* 2581 */     curveVertex(x4, y4, z4);
/* 2582 */     endShape();
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
/*      */   protected void splineForward(int segments, PMatrix3D matrix)
/*      */   {
/* 2602 */     float f = 1.0F / segments;
/* 2603 */     float ff = f * f;
/* 2604 */     float fff = ff * f;
/*      */     
/* 2606 */     matrix.set(0.0F, 0.0F, 0.0F, 1.0F, 
/* 2607 */       fff, ff, f, 0.0F, 
/* 2608 */       6.0F * fff, 2.0F * ff, 0.0F, 0.0F, 
/* 2609 */       6.0F * fff, 0.0F, 0.0F, 0.0F);
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
/*      */   public void smooth()
/*      */   {
/* 2624 */     this.smooth = true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void noSmooth()
/*      */   {
/* 2632 */     this.smooth = false;
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
/*      */   public void imageMode(int mode)
/*      */   {
/* 2664 */     if ((mode == 0) || (mode == 1) || (mode == 3)) {
/* 2665 */       this.imageMode = mode;
/*      */     } else {
/* 2667 */       String msg = 
/* 2668 */         "imageMode() only works with CORNER, CORNERS, or CENTER";
/* 2669 */       throw new RuntimeException(msg);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void image(PImage image, float x, float y)
/*      */   {
/* 2677 */     if ((image.width == -1) || (image.height == -1)) { return;
/*      */     }
/* 2679 */     if ((this.imageMode == 0) || (this.imageMode == 1)) {
/* 2680 */       imageImpl(image, 
/* 2681 */         x, y, x + image.width, y + image.height, 
/* 2682 */         0, 0, image.width, image.height);
/*      */     }
/* 2684 */     else if (this.imageMode == 3) {
/* 2685 */       float x1 = x - image.width / 2;
/* 2686 */       float y1 = y - image.height / 2;
/* 2687 */       imageImpl(image, 
/* 2688 */         x1, y1, x1 + image.width, y1 + image.height, 
/* 2689 */         0, 0, image.width, image.height);
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
/*      */   public void image(PImage image, float x, float y, float c, float d)
/*      */   {
/* 2728 */     image(image, x, y, c, d, 0, 0, image.width, image.height);
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
/*      */   public void image(PImage image, float a, float b, float c, float d, int u1, int v1, int u2, int v2)
/*      */   {
/* 2742 */     if ((image.width == -1) || (image.height == -1)) { return;
/*      */     }
/* 2744 */     if (this.imageMode == 0) {
/* 2745 */       if (c < 0.0F) {
/* 2746 */         a += c;c = -c;
/*      */       }
/* 2748 */       if (d < 0.0F) {
/* 2749 */         b += d;d = -d;
/*      */       }
/*      */       
/* 2752 */       imageImpl(image, 
/* 2753 */         a, b, a + c, b + d, 
/* 2754 */         u1, v1, u2, v2);
/*      */     }
/* 2756 */     else if (this.imageMode == 1) {
/* 2757 */       if (c < a) {
/* 2758 */         float temp = a;a = c;c = temp;
/*      */       }
/* 2760 */       if (d < b) {
/* 2761 */         float temp = b;b = d;d = temp;
/*      */       }
/*      */       
/* 2764 */       imageImpl(image, 
/* 2765 */         a, b, c, d, 
/* 2766 */         u1, v1, u2, v2);
/*      */     }
/* 2768 */     else if (this.imageMode == 3)
/*      */     {
/* 2770 */       if (c < 0.0F) c = -c;
/* 2771 */       if (d < 0.0F) d = -d;
/* 2772 */       float x1 = a - c / 2.0F;
/* 2773 */       float y1 = b - d / 2.0F;
/*      */       
/* 2775 */       imageImpl(image, 
/* 2776 */         x1, y1, x1 + c, y1 + d, 
/* 2777 */         u1, v1, u2, v2);
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
/*      */   protected void imageImpl(PImage image, float x1, float y1, float x2, float y2, int u1, int v1, int u2, int v2)
/*      */   {
/* 2792 */     boolean savedStroke = this.stroke;
/*      */     
/* 2794 */     int savedTextureMode = this.textureMode;
/*      */     
/* 2796 */     this.stroke = false;
/*      */     
/* 2798 */     this.textureMode = 2;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2818 */     beginShape(16);
/* 2819 */     texture(image);
/* 2820 */     vertex(x1, y1, u1, v1);
/* 2821 */     vertex(x1, y2, u1, v2);
/* 2822 */     vertex(x2, y2, u2, v2);
/* 2823 */     vertex(x2, y1, u2, v1);
/* 2824 */     endShape();
/*      */     
/* 2826 */     this.stroke = savedStroke;
/*      */     
/* 2828 */     this.textureMode = savedTextureMode;
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
/*      */   public void shapeMode(int mode)
/*      */   {
/* 2864 */     this.shapeMode = mode;
/*      */   }
/*      */   
/*      */   public void shape(PShape shape)
/*      */   {
/* 2869 */     if (shape.isVisible()) {
/* 2870 */       if (this.shapeMode == 3) {
/* 2871 */         pushMatrix();
/* 2872 */         translate(-shape.getWidth() / 2.0F, -shape.getHeight() / 2.0F);
/*      */       }
/*      */       
/* 2875 */       shape.draw(this);
/*      */       
/* 2877 */       if (this.shapeMode == 3) {
/* 2878 */         popMatrix();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void shape(PShape shape, float x, float y)
/*      */   {
/* 2888 */     if (shape.isVisible()) {
/* 2889 */       pushMatrix();
/*      */       
/* 2891 */       if (this.shapeMode == 3) {
/* 2892 */         translate(x - shape.getWidth() / 2.0F, y - shape.getHeight() / 2.0F);
/*      */       }
/* 2894 */       else if ((this.shapeMode == 0) || (this.shapeMode == 1)) {
/* 2895 */         translate(x, y);
/*      */       }
/* 2897 */       shape.draw(this);
/*      */       
/* 2899 */       popMatrix();
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
/*      */   public void shape(PShape shape, float x, float y, float c, float d)
/*      */   {
/* 2934 */     if (shape.isVisible()) {
/* 2935 */       pushMatrix();
/*      */       
/* 2937 */       if (this.shapeMode == 3)
/*      */       {
/* 2939 */         translate(x - c / 2.0F, y - d / 2.0F);
/* 2940 */         scale(c / shape.getWidth(), d / shape.getHeight());
/*      */       }
/* 2942 */       else if (this.shapeMode == 0) {
/* 2943 */         translate(x, y);
/* 2944 */         scale(c / shape.getWidth(), d / shape.getHeight());
/*      */       }
/* 2946 */       else if (this.shapeMode == 1)
/*      */       {
/* 2948 */         c -= x;
/* 2949 */         d -= y;
/*      */         
/* 2951 */         translate(x, y);
/* 2952 */         scale(c / shape.getWidth(), d / shape.getHeight());
/*      */       }
/* 2954 */       shape.draw(this);
/*      */       
/* 2956 */       popMatrix();
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
/*      */   public void textAlign(int align)
/*      */   {
/* 2972 */     textAlign(align, 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void textAlign(int alignX, int alignY)
/*      */   {
/* 2982 */     this.textAlign = alignX;
/* 2983 */     this.textAlignY = alignY;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float textAscent()
/*      */   {
/* 2993 */     if (this.textFont == null) {
/* 2994 */       defaultFontOrDeath("textAscent");
/*      */     }
/* 2996 */     return this.textFont.ascent() * (this.textMode == 256 ? this.textFont.size : this.textSize);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float textDescent()
/*      */   {
/* 3006 */     if (this.textFont == null) {
/* 3007 */       defaultFontOrDeath("textDescent");
/*      */     }
/* 3009 */     return this.textFont.descent() * (this.textMode == 256 ? this.textFont.size : this.textSize);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void textFont(PFont which)
/*      */   {
/* 3019 */     if (which != null) {
/* 3020 */       this.textFont = which;
/* 3021 */       if (this.hints[3] != 0)
/*      */       {
/* 3023 */         which.findFont();
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3049 */       textSize(which.size);
/*      */     }
/*      */     else {
/* 3052 */       throw new RuntimeException("A null PFont was passed to textFont()");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void textFont(PFont which, float size)
/*      */   {
/* 3061 */     textFont(which);
/* 3062 */     textSize(size);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void textLeading(float leading)
/*      */   {
/* 3072 */     this.textLeading = leading;
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
/*      */   public void textMode(int mode)
/*      */   {
/* 3085 */     if ((mode == 37) || (mode == 39)) {
/* 3086 */       showWarning("Since Processing beta, textMode() is now textAlign().");
/* 3087 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3094 */     if (textModeCheck(mode)) {
/* 3095 */       this.textMode = mode;
/*      */     } else {
/* 3097 */       String modeStr = String.valueOf(mode);
/* 3098 */       switch (mode) {
/* 3099 */       case 256:  modeStr = "SCREEN"; break;
/* 3100 */       case 4:  modeStr = "MODEL"; break;
/* 3101 */       case 5:  modeStr = "SHAPE";
/*      */       }
/* 3103 */       showWarning("textMode(" + modeStr + ") is not supported by this renderer.");
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
/*      */   protected boolean textModeCheck(int mode)
/*      */   {
/* 3119 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void textSize(float size)
/*      */   {
/* 3127 */     if (this.textFont == null) {
/* 3128 */       defaultFontOrDeath("textSize", size);
/*      */     }
/* 3130 */     this.textSize = size;
/* 3131 */     this.textLeading = ((textAscent() + textDescent()) * 1.275F);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public float textWidth(char c)
/*      */   {
/* 3139 */     this.textWidthBuffer[0] = c;
/* 3140 */     return textWidthImpl(this.textWidthBuffer, 0, 1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float textWidth(String str)
/*      */   {
/* 3149 */     if (this.textFont == null) {
/* 3150 */       defaultFontOrDeath("textWidth");
/*      */     }
/*      */     
/* 3153 */     int length = str.length();
/* 3154 */     if (length > this.textWidthBuffer.length) {
/* 3155 */       this.textWidthBuffer = new char[length + 10];
/*      */     }
/* 3157 */     str.getChars(0, length, this.textWidthBuffer, 0);
/*      */     
/* 3159 */     float wide = 0.0F;
/* 3160 */     int index = 0;
/* 3161 */     int start = 0;
/*      */     
/* 3163 */     while (index < length) {
/* 3164 */       if (this.textWidthBuffer[index] == '\n') {
/* 3165 */         wide = Math.max(wide, textWidthImpl(this.textWidthBuffer, start, index));
/* 3166 */         start = index + 1;
/*      */       }
/* 3168 */       index++;
/*      */     }
/* 3170 */     if (start < length) {
/* 3171 */       wide = Math.max(wide, textWidthImpl(this.textWidthBuffer, start, index));
/*      */     }
/* 3173 */     return wide;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public float textWidth(char[] chars, int start, int length)
/*      */   {
/* 3181 */     return textWidthImpl(chars, start, start + length);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected float textWidthImpl(char[] buffer, int start, int stop)
/*      */   {
/* 3192 */     float wide = 0.0F;
/* 3193 */     for (int i = start; i < stop; i++)
/*      */     {
/* 3195 */       wide += this.textFont.width(buffer[i]) * this.textSize;
/*      */     }
/* 3197 */     return wide;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void text(char c)
/*      */   {
/* 3208 */     text(c, this.textX, this.textY, this.textZ);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void text(char c, float x, float y)
/*      */   {
/* 3218 */     if (this.textFont == null) {
/* 3219 */       defaultFontOrDeath("text");
/*      */     }
/*      */     
/* 3222 */     if (this.textMode == 256) { loadPixels();
/*      */     }
/* 3224 */     if (this.textAlignY == 3) {
/* 3225 */       y += textAscent() / 2.0F;
/* 3226 */     } else if (this.textAlignY == 101) {
/* 3227 */       y += textAscent();
/* 3228 */     } else if (this.textAlignY == 102) {
/* 3229 */       y -= textDescent();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 3234 */     this.textBuffer[0] = c;
/* 3235 */     textLineAlignImpl(this.textBuffer, 0, 1, x, y);
/*      */     
/* 3237 */     if (this.textMode == 256) { updatePixels();
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
/*      */   public void text(char c, float x, float y, float z)
/*      */   {
/* 3250 */     if (z != 0.0F) { translate(0.0F, 0.0F, z);
/*      */     }
/* 3252 */     text(c, x, y);
/* 3253 */     this.textZ = z;
/*      */     
/* 3255 */     if (z != 0.0F) { translate(0.0F, 0.0F, -z);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void text(String str)
/*      */   {
/* 3263 */     text(str, this.textX, this.textY, this.textZ);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void text(String str, float x, float y)
/*      */   {
/* 3274 */     if (this.textFont == null) {
/* 3275 */       defaultFontOrDeath("text");
/*      */     }
/*      */     
/* 3278 */     if (this.textMode == 256) { loadPixels();
/*      */     }
/* 3280 */     int length = str.length();
/* 3281 */     if (length > this.textBuffer.length) {
/* 3282 */       this.textBuffer = new char[length + 10];
/*      */     }
/* 3284 */     str.getChars(0, length, this.textBuffer, 0);
/* 3285 */     text(this.textBuffer, 0, length, x, y);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void text(char[] chars, int start, int stop, float x, float y)
/*      */   {
/* 3296 */     float high = 0.0F;
/* 3297 */     for (int i = start; i < stop; i++) {
/* 3298 */       if (chars[i] == '\n') {
/* 3299 */         high += this.textLeading;
/*      */       }
/*      */     }
/* 3302 */     if (this.textAlignY == 3)
/*      */     {
/*      */ 
/*      */ 
/* 3306 */       y += (textAscent() - high) / 2.0F;
/* 3307 */     } else if (this.textAlignY == 101)
/*      */     {
/*      */ 
/* 3310 */       y += textAscent();
/* 3311 */     } else if (this.textAlignY == 102)
/*      */     {
/*      */ 
/* 3314 */       y -= textDescent() + high;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 3320 */     int index = 0;
/* 3321 */     while (index < stop) {
/* 3322 */       if (chars[index] == '\n') {
/* 3323 */         textLineAlignImpl(chars, start, index, x, y);
/* 3324 */         start = index + 1;
/* 3325 */         y += this.textLeading;
/*      */       }
/* 3327 */       index++;
/*      */     }
/* 3329 */     if (start < stop) {
/* 3330 */       textLineAlignImpl(chars, start, index, x, y);
/*      */     }
/* 3332 */     if (this.textMode == 256) { updatePixels();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void text(String str, float x, float y, float z)
/*      */   {
/* 3340 */     if (z != 0.0F) { translate(0.0F, 0.0F, z);
/*      */     }
/* 3342 */     text(str, x, y);
/* 3343 */     this.textZ = z;
/*      */     
/* 3345 */     if (z != 0.0F) { translate(0.0F, 0.0F, -z);
/*      */     }
/*      */   }
/*      */   
/*      */   public void text(char[] chars, int start, int stop, float x, float y, float z)
/*      */   {
/* 3351 */     if (z != 0.0F) { translate(0.0F, 0.0F, z);
/*      */     }
/* 3353 */     text(chars, start, stop, x, y);
/* 3354 */     this.textZ = z;
/*      */     
/* 3356 */     if (z != 0.0F) { translate(0.0F, 0.0F, -z);
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
/*      */   public void text(String str, float x1, float y1, float x2, float y2)
/*      */   {
/* 3374 */     if (this.textFont == null) {
/* 3375 */       defaultFontOrDeath("text");
/*      */     }
/*      */     
/* 3378 */     if (this.textMode == 256) { loadPixels();
/*      */     }
/*      */     
/* 3381 */     switch (this.rectMode) {
/*      */     case 0: 
/* 3383 */       x2 += x1;y2 += y1;
/* 3384 */       break;
/*      */     case 2: 
/* 3386 */       float hradius = x2;
/* 3387 */       float vradius = y2;
/* 3388 */       x2 = x1 + hradius;
/* 3389 */       y2 = y1 + vradius;
/* 3390 */       x1 -= hradius;
/* 3391 */       y1 -= vradius;
/* 3392 */       break;
/*      */     case 3: 
/* 3394 */       float hradius = x2 / 2.0F;
/* 3395 */       float vradius = y2 / 2.0F;
/* 3396 */       x2 = x1 + hradius;
/* 3397 */       y2 = y1 + vradius;
/* 3398 */       x1 -= hradius;
/* 3399 */       y1 -= vradius;
/*      */     }
/* 3401 */     if (x2 < x1) {
/* 3402 */       float temp = x1;x1 = x2;x2 = temp;
/*      */     }
/* 3404 */     if (y2 < y1) {
/* 3405 */       float temp = y1;y1 = y2;y2 = temp;
/*      */     }
/*      */     
/*      */ 
/* 3409 */     float boxWidth = x2 - x1;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3416 */     float spaceWidth = textWidth(' ');
/*      */     
/* 3418 */     if (this.textBreakStart == null) {
/* 3419 */       this.textBreakStart = new int[20];
/* 3420 */       this.textBreakStop = new int[20];
/*      */     }
/* 3422 */     this.textBreakCount = 0;
/*      */     
/* 3424 */     int length = str.length();
/* 3425 */     if (length + 1 > this.textBuffer.length) {
/* 3426 */       this.textBuffer = new char[length + 1];
/*      */     }
/* 3428 */     str.getChars(0, length, this.textBuffer, 0);
/*      */     
/* 3430 */     this.textBuffer[(length++)] = '\n';
/*      */     
/* 3432 */     int sentenceStart = 0;
/* 3433 */     for (int i = 0; i < length; i++) {
/* 3434 */       if (this.textBuffer[i] == '\n')
/*      */       {
/*      */ 
/* 3437 */         boolean legit = 
/* 3438 */           textSentence(this.textBuffer, sentenceStart, i, boxWidth, spaceWidth);
/* 3439 */         if (!legit) {
/*      */           break;
/*      */         }
/* 3442 */         sentenceStart = i + 1;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 3448 */     float lineX = x1;
/* 3449 */     if (this.textAlign == 3) {
/* 3450 */       lineX += boxWidth / 2.0F;
/* 3451 */     } else if (this.textAlign == 39) {
/* 3452 */       lineX = x2;
/*      */     }
/*      */     
/* 3455 */     float boxHeight = y2 - y1;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 3460 */     float topAndBottom = textAscent() + textDescent();
/* 3461 */     int lineFitCount = 1 + PApplet.floor((boxHeight - topAndBottom) / this.textLeading);
/* 3462 */     int lineCount = Math.min(this.textBreakCount, lineFitCount);
/*      */     
/* 3464 */     if (this.textAlignY == 3) {
/* 3465 */       float lineHigh = textAscent() + this.textLeading * (lineCount - 1);
/* 3466 */       float y = y1 + textAscent() + (boxHeight - lineHigh) / 2.0F;
/* 3467 */       for (int i = 0; i < lineCount; i++) {
/* 3468 */         textLineAlignImpl(this.textBuffer, this.textBreakStart[i], this.textBreakStop[i], lineX, y);
/* 3469 */         y += this.textLeading;
/*      */       }
/*      */     }
/* 3472 */     else if (this.textAlignY == 102) {
/* 3473 */       float y = y2 - textDescent() - this.textLeading * (lineCount - 1);
/* 3474 */       for (int i = 0; i < lineCount; i++) {
/* 3475 */         textLineAlignImpl(this.textBuffer, this.textBreakStart[i], this.textBreakStop[i], lineX, y);
/* 3476 */         y += this.textLeading;
/*      */       }
/*      */     }
/*      */     else {
/* 3480 */       float y = y1 + textAscent();
/* 3481 */       for (int i = 0; i < lineCount; i++) {
/* 3482 */         textLineAlignImpl(this.textBuffer, this.textBreakStart[i], this.textBreakStop[i], lineX, y);
/* 3483 */         y += this.textLeading;
/*      */       }
/*      */     }
/*      */     
/* 3487 */     if (this.textMode == 256) { updatePixels();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean textSentence(char[] buffer, int start, int stop, float boxWidth, float spaceWidth)
/*      */   {
/* 3497 */     float runningX = 0.0F;
/*      */     
/*      */ 
/*      */ 
/* 3501 */     int lineStart = start;
/* 3502 */     int wordStart = start;
/* 3503 */     int index = start;
/* 3504 */     while (index <= stop)
/*      */     {
/* 3506 */       if ((buffer[index] == ' ') || (index == stop)) {
/* 3507 */         float wordWidth = textWidthImpl(buffer, wordStart, index);
/*      */         
/* 3509 */         if (runningX + wordWidth > boxWidth) {
/* 3510 */           if (runningX != 0.0F)
/*      */           {
/* 3512 */             index = wordStart;
/* 3513 */             textSentenceBreak(lineStart, index);
/*      */             
/*      */             do
/*      */             {
/* 3517 */               index++;
/* 3516 */               if (index >= stop) break; } while (buffer[index] == ' ');
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*      */ 
/*      */             do
/*      */             {
/* 3524 */               index--;
/* 3525 */               if (index == wordStart)
/*      */               {
/*      */ 
/* 3528 */                 return false;
/*      */               }
/* 3530 */               wordWidth = textWidthImpl(buffer, wordStart, index);
/* 3531 */             } while (wordWidth > boxWidth);
/*      */             
/*      */ 
/* 3534 */             textSentenceBreak(lineStart, index);
/*      */           }
/* 3536 */           lineStart = index;
/* 3537 */           wordStart = index;
/* 3538 */           runningX = 0.0F;
/*      */         }
/* 3540 */         else if (index == stop)
/*      */         {
/*      */ 
/* 3543 */           textSentenceBreak(lineStart, index);
/*      */           
/* 3545 */           index++;
/*      */         }
/*      */         else {
/* 3548 */           runningX += wordWidth + spaceWidth;
/* 3549 */           wordStart = index + 1;
/* 3550 */           index++;
/*      */         }
/*      */       } else {
/* 3553 */         index++;
/*      */       }
/*      */     }
/*      */     
/* 3557 */     return true;
/*      */   }
/*      */   
/*      */   protected void textSentenceBreak(int start, int stop)
/*      */   {
/* 3562 */     if (this.textBreakCount == this.textBreakStart.length) {
/* 3563 */       this.textBreakStart = PApplet.expand(this.textBreakStart);
/* 3564 */       this.textBreakStop = PApplet.expand(this.textBreakStop);
/*      */     }
/* 3566 */     this.textBreakStart[this.textBreakCount] = start;
/* 3567 */     this.textBreakStop[this.textBreakCount] = stop;
/* 3568 */     this.textBreakCount += 1;
/*      */   }
/*      */   
/*      */   public void text(String s, float x1, float y1, float x2, float y2, float z)
/*      */   {
/* 3573 */     if (z != 0.0F) { translate(0.0F, 0.0F, z);
/*      */     }
/* 3575 */     text(s, x1, y1, x2, y2);
/* 3576 */     this.textZ = z;
/*      */     
/* 3578 */     if (z != 0.0F) translate(0.0F, 0.0F, -z);
/*      */   }
/*      */   
/*      */   public void text(int num, float x, float y)
/*      */   {
/* 3583 */     text(String.valueOf(num), x, y);
/*      */   }
/*      */   
/*      */   public void text(int num, float x, float y, float z)
/*      */   {
/* 3588 */     text(String.valueOf(num), x, y, z);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void text(float num, float x, float y)
/*      */   {
/* 3600 */     text(PApplet.nfs(num, 0, 3), x, y);
/*      */   }
/*      */   
/*      */   public void text(float num, float x, float y, float z)
/*      */   {
/* 3605 */     text(PApplet.nfs(num, 0, 3), x, y, z);
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
/*      */   protected void textLineAlignImpl(char[] buffer, int start, int stop, float x, float y)
/*      */   {
/* 3624 */     if (this.textAlign == 3) {
/* 3625 */       x -= textWidthImpl(buffer, start, stop) / 2.0F;
/*      */     }
/* 3627 */     else if (this.textAlign == 39) {
/* 3628 */       x -= textWidthImpl(buffer, start, stop);
/*      */     }
/*      */     
/* 3631 */     textLineImpl(buffer, start, stop, x, y);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void textLineImpl(char[] buffer, int start, int stop, float x, float y)
/*      */   {
/* 3640 */     for (int index = start; index < stop; index++) {
/* 3641 */       textCharImpl(buffer[index], x, y);
/*      */       
/*      */ 
/* 3644 */       x += textWidth(buffer[index]);
/*      */     }
/* 3646 */     this.textX = x;
/* 3647 */     this.textY = y;
/* 3648 */     this.textZ = 0.0F;
/*      */   }
/*      */   
/*      */   protected void textCharImpl(char ch, float x, float y)
/*      */   {
/* 3653 */     PFont.Glyph glyph = this.textFont.getGlyph(ch);
/* 3654 */     if (glyph != null) {
/* 3655 */       if (this.textMode == 4) {
/* 3656 */         float high = glyph.height / this.textFont.size;
/* 3657 */         float bwidth = glyph.width / this.textFont.size;
/* 3658 */         float lextent = glyph.leftExtent / this.textFont.size;
/* 3659 */         float textent = glyph.topExtent / this.textFont.size;
/*      */         
/* 3661 */         float x1 = x + lextent * this.textSize;
/* 3662 */         float y1 = y - textent * this.textSize;
/* 3663 */         float x2 = x1 + bwidth * this.textSize;
/* 3664 */         float y2 = y1 + high * this.textSize;
/*      */         
/* 3666 */         textCharModelImpl(glyph.image, 
/* 3667 */           x1, y1, x2, y2, 
/* 3668 */           glyph.width, glyph.height);
/*      */       }
/* 3670 */       else if (this.textMode == 256) {
/* 3671 */         int xx = (int)x + glyph.leftExtent;
/* 3672 */         int yy = (int)y - glyph.topExtent;
/*      */         
/* 3674 */         int w0 = glyph.width;
/* 3675 */         int h0 = glyph.height;
/*      */         
/* 3677 */         textCharScreenImpl(glyph.image, xx, yy, w0, h0);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void textCharModelImpl(PImage glyph, float x1, float y1, float x2, float y2, int u2, int v2)
/*      */   {
/* 3687 */     boolean savedTint = this.tint;
/* 3688 */     int savedTintColor = this.tintColor;
/* 3689 */     float savedTintR = this.tintR;
/* 3690 */     float savedTintG = this.tintG;
/* 3691 */     float savedTintB = this.tintB;
/* 3692 */     float savedTintA = this.tintA;
/* 3693 */     boolean savedTintAlpha = this.tintAlpha;
/*      */     
/* 3695 */     this.tint = true;
/* 3696 */     this.tintColor = this.fillColor;
/* 3697 */     this.tintR = this.fillR;
/* 3698 */     this.tintG = this.fillG;
/* 3699 */     this.tintB = this.fillB;
/* 3700 */     this.tintA = this.fillA;
/* 3701 */     this.tintAlpha = this.fillAlpha;
/*      */     
/* 3703 */     imageImpl(glyph, x1, y1, x2, y2, 0, 0, u2, v2);
/*      */     
/* 3705 */     this.tint = savedTint;
/* 3706 */     this.tintColor = savedTintColor;
/* 3707 */     this.tintR = savedTintR;
/* 3708 */     this.tintG = savedTintG;
/* 3709 */     this.tintB = savedTintB;
/* 3710 */     this.tintA = savedTintA;
/* 3711 */     this.tintAlpha = savedTintAlpha;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected void textCharScreenImpl(PImage glyph, int xx, int yy, int w0, int h0)
/*      */   {
/* 3718 */     int x0 = 0;
/* 3719 */     int y0 = 0;
/*      */     
/* 3721 */     if ((xx >= this.width) || (yy >= this.height) || 
/* 3722 */       (xx + w0 < 0) || (yy + h0 < 0)) { return;
/*      */     }
/* 3724 */     if (xx < 0) {
/* 3725 */       x0 -= xx;
/* 3726 */       w0 += xx;
/* 3727 */       xx = 0;
/*      */     }
/* 3729 */     if (yy < 0) {
/* 3730 */       y0 -= yy;
/* 3731 */       h0 += yy;
/* 3732 */       yy = 0;
/*      */     }
/* 3734 */     if (xx + w0 > this.width) {
/* 3735 */       w0 -= xx + w0 - this.width;
/*      */     }
/* 3737 */     if (yy + h0 > this.height) {
/* 3738 */       h0 -= yy + h0 - this.height;
/*      */     }
/*      */     
/* 3741 */     int fr = this.fillRi;
/* 3742 */     int fg = this.fillGi;
/* 3743 */     int fb = this.fillBi;
/* 3744 */     int fa = this.fillAi;
/*      */     
/* 3746 */     int[] pixels1 = glyph.pixels;
/*      */     
/*      */ 
/* 3749 */     for (int row = y0; row < y0 + h0; row++) {
/* 3750 */       for (int col = x0; col < x0 + w0; col++)
/*      */       {
/* 3752 */         int a1 = fa * pixels1[(row * glyph.width + col)] >> 8;
/* 3753 */         int a2 = a1 ^ 0xFF;
/*      */         
/* 3755 */         int p2 = this.pixels[((yy + row - y0) * this.width + (xx + col - x0))];
/*      */         
/* 3757 */         this.pixels[((yy + row - y0) * this.width + xx + col - x0)] = 
/* 3758 */           (0xFF000000 | 
/* 3759 */           (a1 * fr + a2 * (p2 >> 16 & 0xFF) & 0xFF00) << 8 | 
/* 3760 */           a1 * fg + a2 * (p2 >> 8 & 0xFF) & 0xFF00 | 
/* 3761 */           a1 * fb + a2 * (p2 & 0xFF) >> 8);
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
/*      */   public void pushMatrix()
/*      */   {
/* 3777 */     showMethodWarning("pushMatrix");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void popMatrix()
/*      */   {
/* 3785 */     showMethodWarning("popMatrix");
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
/*      */   public void translate(float tx, float ty)
/*      */   {
/* 3799 */     showMissingWarning("translate");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void translate(float tx, float ty, float tz)
/*      */   {
/* 3807 */     showMissingWarning("translate");
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
/*      */   public void rotate(float angle)
/*      */   {
/* 3821 */     showMissingWarning("rotate");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void rotateX(float angle)
/*      */   {
/* 3829 */     showMethodWarning("rotateX");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void rotateY(float angle)
/*      */   {
/* 3837 */     showMethodWarning("rotateY");
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
/*      */   public void rotateZ(float angle)
/*      */   {
/* 3850 */     showMethodWarning("rotateZ");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void rotate(float angle, float vx, float vy, float vz)
/*      */   {
/* 3858 */     showMissingWarning("rotate");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void scale(float s)
/*      */   {
/* 3866 */     showMissingWarning("scale");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void scale(float sx, float sy)
/*      */   {
/* 3877 */     showMissingWarning("scale");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void scale(float x, float y, float z)
/*      */   {
/* 3885 */     showMissingWarning("scale");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void skewX(float angle)
/*      */   {
/* 3893 */     showMissingWarning("skewX");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void skewY(float angle)
/*      */   {
/* 3901 */     showMissingWarning("skewY");
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
/*      */   public void resetMatrix()
/*      */   {
/* 3914 */     showMethodWarning("resetMatrix");
/*      */   }
/*      */   
/*      */   public void applyMatrix(PMatrix source)
/*      */   {
/* 3919 */     if ((source instanceof PMatrix2D)) {
/* 3920 */       applyMatrix((PMatrix2D)source);
/* 3921 */     } else if ((source instanceof PMatrix3D)) {
/* 3922 */       applyMatrix((PMatrix3D)source);
/*      */     }
/*      */   }
/*      */   
/*      */   public void applyMatrix(PMatrix2D source)
/*      */   {
/* 3928 */     applyMatrix(source.m00, source.m01, source.m02, 
/* 3929 */       source.m10, source.m11, source.m12);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void applyMatrix(float n00, float n01, float n02, float n10, float n11, float n12)
/*      */   {
/* 3938 */     showMissingWarning("applyMatrix");
/*      */   }
/*      */   
/*      */   public void applyMatrix(PMatrix3D source)
/*      */   {
/* 3943 */     applyMatrix(source.m00, source.m01, source.m02, source.m03, 
/* 3944 */       source.m10, source.m11, source.m12, source.m13, 
/* 3945 */       source.m20, source.m21, source.m22, source.m23, 
/* 3946 */       source.m30, source.m31, source.m32, source.m33);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void applyMatrix(float n00, float n01, float n02, float n03, float n10, float n11, float n12, float n13, float n20, float n21, float n22, float n23, float n30, float n31, float n32, float n33)
/*      */   {
/* 3957 */     showMissingWarning("applyMatrix");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public PMatrix getMatrix()
/*      */   {
/* 3968 */     showMissingWarning("getMatrix");
/* 3969 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public PMatrix2D getMatrix(PMatrix2D target)
/*      */   {
/* 3978 */     showMissingWarning("getMatrix");
/* 3979 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public PMatrix3D getMatrix(PMatrix3D target)
/*      */   {
/* 3988 */     showMissingWarning("getMatrix");
/* 3989 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setMatrix(PMatrix source)
/*      */   {
/* 3997 */     if ((source instanceof PMatrix2D)) {
/* 3998 */       setMatrix((PMatrix2D)source);
/* 3999 */     } else if ((source instanceof PMatrix3D)) {
/* 4000 */       setMatrix((PMatrix3D)source);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setMatrix(PMatrix2D source)
/*      */   {
/* 4009 */     showMissingWarning("setMatrix");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setMatrix(PMatrix3D source)
/*      */   {
/* 4017 */     showMissingWarning("setMatrix");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void printMatrix()
/*      */   {
/* 4025 */     showMethodWarning("printMatrix");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void beginCamera()
/*      */   {
/* 4036 */     showMethodWarning("beginCamera");
/*      */   }
/*      */   
/*      */   public void endCamera()
/*      */   {
/* 4041 */     showMethodWarning("endCamera");
/*      */   }
/*      */   
/*      */   public void camera()
/*      */   {
/* 4046 */     showMissingWarning("camera");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void camera(float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ)
/*      */   {
/* 4053 */     showMissingWarning("camera");
/*      */   }
/*      */   
/*      */   public void printCamera()
/*      */   {
/* 4058 */     showMethodWarning("printCamera");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void ortho()
/*      */   {
/* 4069 */     showMissingWarning("ortho");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void ortho(float left, float right, float bottom, float top, float near, float far)
/*      */   {
/* 4076 */     showMissingWarning("ortho");
/*      */   }
/*      */   
/*      */   public void perspective()
/*      */   {
/* 4081 */     showMissingWarning("perspective");
/*      */   }
/*      */   
/*      */   public void perspective(float fovy, float aspect, float zNear, float zFar)
/*      */   {
/* 4086 */     showMissingWarning("perspective");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void frustum(float left, float right, float bottom, float top, float near, float far)
/*      */   {
/* 4093 */     showMethodWarning("frustum");
/*      */   }
/*      */   
/*      */   public void printProjection()
/*      */   {
/* 4098 */     showMethodWarning("printCamera");
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
/*      */   public float screenX(float x, float y)
/*      */   {
/* 4114 */     showMissingWarning("screenX");
/* 4115 */     return 0.0F;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float screenY(float x, float y)
/*      */   {
/* 4125 */     showMissingWarning("screenY");
/* 4126 */     return 0.0F;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float screenX(float x, float y, float z)
/*      */   {
/* 4138 */     showMissingWarning("screenX");
/* 4139 */     return 0.0F;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float screenY(float x, float y, float z)
/*      */   {
/* 4151 */     showMissingWarning("screenY");
/* 4152 */     return 0.0F;
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
/*      */   public float screenZ(float x, float y, float z)
/*      */   {
/* 4168 */     showMissingWarning("screenZ");
/* 4169 */     return 0.0F;
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
/*      */   public float modelX(float x, float y, float z)
/*      */   {
/* 4183 */     showMissingWarning("modelX");
/* 4184 */     return 0.0F;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public float modelY(float x, float y, float z)
/*      */   {
/* 4192 */     showMissingWarning("modelY");
/* 4193 */     return 0.0F;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public float modelZ(float x, float y, float z)
/*      */   {
/* 4201 */     showMissingWarning("modelZ");
/* 4202 */     return 0.0F;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void pushStyle()
/*      */   {
/* 4213 */     if (this.styleStackDepth == this.styleStack.length) {
/* 4214 */       this.styleStack = ((PStyle[])PApplet.expand(this.styleStack));
/*      */     }
/* 4216 */     if (this.styleStack[this.styleStackDepth] == null) {
/* 4217 */       this.styleStack[this.styleStackDepth] = new PStyle();
/*      */     }
/* 4219 */     PStyle s = this.styleStack[(this.styleStackDepth++)];
/* 4220 */     getStyle(s);
/*      */   }
/*      */   
/*      */   public void popStyle()
/*      */   {
/* 4225 */     if (this.styleStackDepth == 0) {
/* 4226 */       throw new RuntimeException("Too many popStyle() without enough pushStyle()");
/*      */     }
/* 4228 */     this.styleStackDepth -= 1;
/* 4229 */     style(this.styleStack[this.styleStackDepth]);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void style(PStyle s)
/*      */   {
/* 4240 */     imageMode(s.imageMode);
/* 4241 */     rectMode(s.rectMode);
/* 4242 */     ellipseMode(s.ellipseMode);
/* 4243 */     shapeMode(s.shapeMode);
/*      */     
/* 4245 */     if (s.tint) {
/* 4246 */       tint(s.tintColor);
/*      */     } else {
/* 4248 */       noTint();
/*      */     }
/* 4250 */     if (s.fill) {
/* 4251 */       fill(s.fillColor);
/*      */     } else {
/* 4253 */       noFill();
/*      */     }
/* 4255 */     if (s.stroke) {
/* 4256 */       stroke(s.strokeColor);
/*      */     } else {
/* 4258 */       noStroke();
/*      */     }
/* 4260 */     strokeWeight(s.strokeWeight);
/* 4261 */     strokeCap(s.strokeCap);
/* 4262 */     strokeJoin(s.strokeJoin);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 4267 */     colorMode(1, 1.0F);
/* 4268 */     ambient(s.ambientR, s.ambientG, s.ambientB);
/* 4269 */     emissive(s.emissiveR, s.emissiveG, s.emissiveB);
/* 4270 */     specular(s.specularR, s.specularG, s.specularB);
/* 4271 */     shininess(s.shininess);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4291 */     colorMode(s.colorMode, 
/* 4292 */       s.colorModeX, s.colorModeY, s.colorModeZ, s.colorModeA);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4298 */     if (s.textFont != null) {
/* 4299 */       textFont(s.textFont, s.textSize);
/* 4300 */       textLeading(s.textLeading);
/*      */     }
/*      */     
/* 4303 */     textAlign(s.textAlign, s.textAlignY);
/* 4304 */     textMode(s.textMode);
/*      */   }
/*      */   
/*      */   public PStyle getStyle()
/*      */   {
/* 4309 */     return getStyle(null);
/*      */   }
/*      */   
/*      */   public PStyle getStyle(PStyle s)
/*      */   {
/* 4314 */     if (s == null) {
/* 4315 */       s = new PStyle();
/*      */     }
/*      */     
/* 4318 */     s.imageMode = this.imageMode;
/* 4319 */     s.rectMode = this.rectMode;
/* 4320 */     s.ellipseMode = this.ellipseMode;
/* 4321 */     s.shapeMode = this.shapeMode;
/*      */     
/* 4323 */     s.colorMode = this.colorMode;
/* 4324 */     s.colorModeX = this.colorModeX;
/* 4325 */     s.colorModeY = this.colorModeY;
/* 4326 */     s.colorModeZ = this.colorModeZ;
/* 4327 */     s.colorModeA = this.colorModeA;
/*      */     
/* 4329 */     s.tint = this.tint;
/* 4330 */     s.tintColor = this.tintColor;
/* 4331 */     s.fill = this.fill;
/* 4332 */     s.fillColor = this.fillColor;
/* 4333 */     s.stroke = this.stroke;
/* 4334 */     s.strokeColor = this.strokeColor;
/* 4335 */     s.strokeWeight = this.strokeWeight;
/* 4336 */     s.strokeCap = this.strokeCap;
/* 4337 */     s.strokeJoin = this.strokeJoin;
/*      */     
/* 4339 */     s.ambientR = this.ambientR;
/* 4340 */     s.ambientG = this.ambientG;
/* 4341 */     s.ambientB = this.ambientB;
/* 4342 */     s.specularR = this.specularR;
/* 4343 */     s.specularG = this.specularG;
/* 4344 */     s.specularB = this.specularB;
/* 4345 */     s.emissiveR = this.emissiveR;
/* 4346 */     s.emissiveG = this.emissiveG;
/* 4347 */     s.emissiveB = this.emissiveB;
/* 4348 */     s.shininess = this.shininess;
/*      */     
/* 4350 */     s.textFont = this.textFont;
/* 4351 */     s.textAlign = this.textAlign;
/* 4352 */     s.textAlignY = this.textAlignY;
/* 4353 */     s.textMode = this.textMode;
/* 4354 */     s.textSize = this.textSize;
/* 4355 */     s.textLeading = this.textLeading;
/*      */     
/* 4357 */     return s;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void strokeWeight(float weight)
/*      */   {
/* 4368 */     this.strokeWeight = weight;
/*      */   }
/*      */   
/*      */   public void strokeJoin(int join)
/*      */   {
/* 4373 */     this.strokeJoin = join;
/*      */   }
/*      */   
/*      */   public void strokeCap(int cap)
/*      */   {
/* 4378 */     this.strokeCap = cap;
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
/*      */   public void noStroke()
/*      */   {
/* 4397 */     this.stroke = false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void stroke(int rgb)
/*      */   {
/* 4408 */     colorCalc(rgb);
/* 4409 */     strokeFromCalc();
/*      */   }
/*      */   
/*      */   public void stroke(int rgb, float alpha)
/*      */   {
/* 4414 */     colorCalc(rgb, alpha);
/* 4415 */     strokeFromCalc();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void stroke(float gray)
/*      */   {
/* 4424 */     colorCalc(gray);
/* 4425 */     strokeFromCalc();
/*      */   }
/*      */   
/*      */   public void stroke(float gray, float alpha)
/*      */   {
/* 4430 */     colorCalc(gray, alpha);
/* 4431 */     strokeFromCalc();
/*      */   }
/*      */   
/*      */   public void stroke(float x, float y, float z)
/*      */   {
/* 4436 */     colorCalc(x, y, z);
/* 4437 */     strokeFromCalc();
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
/*      */   public void stroke(float x, float y, float z, float a)
/*      */   {
/* 4464 */     colorCalc(x, y, z, a);
/* 4465 */     strokeFromCalc();
/*      */   }
/*      */   
/*      */   protected void strokeFromCalc()
/*      */   {
/* 4470 */     this.stroke = true;
/* 4471 */     this.strokeR = this.calcR;
/* 4472 */     this.strokeG = this.calcG;
/* 4473 */     this.strokeB = this.calcB;
/* 4474 */     this.strokeA = this.calcA;
/* 4475 */     this.strokeRi = this.calcRi;
/* 4476 */     this.strokeGi = this.calcGi;
/* 4477 */     this.strokeBi = this.calcBi;
/* 4478 */     this.strokeAi = this.calcAi;
/* 4479 */     this.strokeColor = this.calcColor;
/* 4480 */     this.strokeAlpha = this.calcAlpha;
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
/*      */   public void noTint()
/*      */   {
/* 4498 */     this.tint = false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void tint(int rgb)
/*      */   {
/* 4506 */     colorCalc(rgb);
/* 4507 */     tintFromCalc();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void tint(int rgb, float alpha)
/*      */   {
/* 4517 */     colorCalc(rgb, alpha);
/* 4518 */     tintFromCalc();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void tint(float gray)
/*      */   {
/* 4526 */     colorCalc(gray);
/* 4527 */     tintFromCalc();
/*      */   }
/*      */   
/*      */   public void tint(float gray, float alpha)
/*      */   {
/* 4532 */     colorCalc(gray, alpha);
/* 4533 */     tintFromCalc();
/*      */   }
/*      */   
/*      */   public void tint(float x, float y, float z)
/*      */   {
/* 4538 */     colorCalc(x, y, z);
/* 4539 */     tintFromCalc();
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
/*      */   public void tint(float x, float y, float z, float a)
/*      */   {
/* 4573 */     colorCalc(x, y, z, a);
/* 4574 */     tintFromCalc();
/*      */   }
/*      */   
/*      */   protected void tintFromCalc()
/*      */   {
/* 4579 */     this.tint = true;
/* 4580 */     this.tintR = this.calcR;
/* 4581 */     this.tintG = this.calcG;
/* 4582 */     this.tintB = this.calcB;
/* 4583 */     this.tintA = this.calcA;
/* 4584 */     this.tintRi = this.calcRi;
/* 4585 */     this.tintGi = this.calcGi;
/* 4586 */     this.tintBi = this.calcBi;
/* 4587 */     this.tintAi = this.calcAi;
/* 4588 */     this.tintColor = this.calcColor;
/* 4589 */     this.tintAlpha = this.calcAlpha;
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
/*      */   public void noFill()
/*      */   {
/* 4609 */     this.fill = false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void fill(int rgb)
/*      */   {
/* 4618 */     colorCalc(rgb);
/* 4619 */     fillFromCalc();
/*      */   }
/*      */   
/*      */   public void fill(int rgb, float alpha)
/*      */   {
/* 4624 */     colorCalc(rgb, alpha);
/* 4625 */     fillFromCalc();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void fill(float gray)
/*      */   {
/* 4633 */     colorCalc(gray);
/* 4634 */     fillFromCalc();
/*      */   }
/*      */   
/*      */   public void fill(float gray, float alpha)
/*      */   {
/* 4639 */     colorCalc(gray, alpha);
/* 4640 */     fillFromCalc();
/*      */   }
/*      */   
/*      */   public void fill(float x, float y, float z)
/*      */   {
/* 4645 */     colorCalc(x, y, z);
/* 4646 */     fillFromCalc();
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
/*      */   public void fill(float x, float y, float z, float a)
/*      */   {
/* 4669 */     colorCalc(x, y, z, a);
/* 4670 */     fillFromCalc();
/*      */   }
/*      */   
/*      */   protected void fillFromCalc()
/*      */   {
/* 4675 */     this.fill = true;
/* 4676 */     this.fillR = this.calcR;
/* 4677 */     this.fillG = this.calcG;
/* 4678 */     this.fillB = this.calcB;
/* 4679 */     this.fillA = this.calcA;
/* 4680 */     this.fillRi = this.calcRi;
/* 4681 */     this.fillGi = this.calcGi;
/* 4682 */     this.fillBi = this.calcBi;
/* 4683 */     this.fillAi = this.calcAi;
/* 4684 */     this.fillColor = this.calcColor;
/* 4685 */     this.fillAlpha = this.calcAlpha;
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
/*      */   public void ambient(int rgb)
/*      */   {
/* 4703 */     colorCalc(rgb);
/* 4704 */     ambientFromCalc();
/*      */   }
/*      */   
/*      */   public void ambient(float gray)
/*      */   {
/* 4709 */     colorCalc(gray);
/* 4710 */     ambientFromCalc();
/*      */   }
/*      */   
/*      */   public void ambient(float x, float y, float z)
/*      */   {
/* 4715 */     colorCalc(x, y, z);
/* 4716 */     ambientFromCalc();
/*      */   }
/*      */   
/*      */   protected void ambientFromCalc()
/*      */   {
/* 4721 */     this.ambientR = this.calcR;
/* 4722 */     this.ambientG = this.calcG;
/* 4723 */     this.ambientB = this.calcB;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void specular(int rgb)
/*      */   {
/* 4735 */     colorCalc(rgb);
/* 4736 */     specularFromCalc();
/*      */   }
/*      */   
/*      */   public void specular(float gray)
/*      */   {
/* 4741 */     colorCalc(gray);
/* 4742 */     specularFromCalc();
/*      */   }
/*      */   
/*      */   public void specular(float x, float y, float z)
/*      */   {
/* 4747 */     colorCalc(x, y, z);
/* 4748 */     specularFromCalc();
/*      */   }
/*      */   
/*      */   protected void specularFromCalc()
/*      */   {
/* 4753 */     this.specularR = this.calcR;
/* 4754 */     this.specularG = this.calcG;
/* 4755 */     this.specularB = this.calcB;
/*      */   }
/*      */   
/*      */   public void shininess(float shine)
/*      */   {
/* 4760 */     this.shininess = shine;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void emissive(int rgb)
/*      */   {
/* 4772 */     colorCalc(rgb);
/* 4773 */     emissiveFromCalc();
/*      */   }
/*      */   
/*      */   public void emissive(float gray)
/*      */   {
/* 4778 */     colorCalc(gray);
/* 4779 */     emissiveFromCalc();
/*      */   }
/*      */   
/*      */   public void emissive(float x, float y, float z)
/*      */   {
/* 4784 */     colorCalc(x, y, z);
/* 4785 */     emissiveFromCalc();
/*      */   }
/*      */   
/*      */   protected void emissiveFromCalc()
/*      */   {
/* 4790 */     this.emissiveR = this.calcR;
/* 4791 */     this.emissiveG = this.calcG;
/* 4792 */     this.emissiveB = this.calcB;
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
/*      */   public void lights()
/*      */   {
/* 4807 */     showMethodWarning("lights");
/*      */   }
/*      */   
/*      */   public void noLights() {
/* 4811 */     showMethodWarning("noLights");
/*      */   }
/*      */   
/*      */   public void ambientLight(float red, float green, float blue) {
/* 4815 */     showMethodWarning("ambientLight");
/*      */   }
/*      */   
/*      */   public void ambientLight(float red, float green, float blue, float x, float y, float z)
/*      */   {
/* 4820 */     showMethodWarning("ambientLight");
/*      */   }
/*      */   
/*      */   public void directionalLight(float red, float green, float blue, float nx, float ny, float nz)
/*      */   {
/* 4825 */     showMethodWarning("directionalLight");
/*      */   }
/*      */   
/*      */   public void pointLight(float red, float green, float blue, float x, float y, float z)
/*      */   {
/* 4830 */     showMethodWarning("pointLight");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void spotLight(float red, float green, float blue, float x, float y, float z, float nx, float ny, float nz, float angle, float concentration)
/*      */   {
/* 4837 */     showMethodWarning("spotLight");
/*      */   }
/*      */   
/*      */   public void lightFalloff(float constant, float linear, float quadratic) {
/* 4841 */     showMethodWarning("lightFalloff");
/*      */   }
/*      */   
/*      */   public void lightSpecular(float x, float y, float z) {
/* 4845 */     showMethodWarning("lightSpecular");
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
/*      */   public void background(int rgb)
/*      */   {
/* 4880 */     colorCalc(rgb);
/* 4881 */     backgroundFromCalc();
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
/*      */   public void background(int rgb, float alpha)
/*      */   {
/* 4902 */     colorCalc(rgb, alpha);
/* 4903 */     backgroundFromCalc();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void background(float gray)
/*      */   {
/* 4912 */     colorCalc(gray);
/* 4913 */     backgroundFromCalc();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void background(float gray, float alpha)
/*      */   {
/* 4924 */     if (this.format == 1) {
/* 4925 */       background(gray);
/*      */     }
/*      */     else {
/* 4928 */       colorCalc(gray, alpha);
/* 4929 */       backgroundFromCalc();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void background(float x, float y, float z)
/*      */   {
/* 4940 */     colorCalc(x, y, z);
/* 4941 */     backgroundFromCalc();
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
/*      */   public void background(float x, float y, float z, float a)
/*      */   {
/* 4981 */     colorCalc(x, y, z, a);
/* 4982 */     backgroundFromCalc();
/*      */   }
/*      */   
/*      */   protected void backgroundFromCalc()
/*      */   {
/* 4987 */     this.backgroundR = this.calcR;
/* 4988 */     this.backgroundG = this.calcG;
/* 4989 */     this.backgroundB = this.calcB;
/* 4990 */     this.backgroundA = (this.format == 1 ? this.colorModeA : this.calcA);
/* 4991 */     this.backgroundRi = this.calcRi;
/* 4992 */     this.backgroundGi = this.calcGi;
/* 4993 */     this.backgroundBi = this.calcBi;
/* 4994 */     this.backgroundAi = (this.format == 1 ? 255 : this.calcAi);
/* 4995 */     this.backgroundAlpha = (this.format == 1 ? false : this.calcAlpha);
/* 4996 */     this.backgroundColor = this.calcColor;
/*      */     
/* 4998 */     backgroundImpl();
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
/*      */   public void background(PImage image)
/*      */   {
/* 5015 */     if ((image.width != this.width) || (image.height != this.height)) {
/* 5016 */       throw new RuntimeException("background image must be the same size as your application");
/*      */     }
/* 5018 */     if ((image.format != 1) && (image.format != 2)) {
/* 5019 */       throw new RuntimeException("background images should be RGB or ARGB");
/*      */     }
/* 5021 */     this.backgroundColor = 0;
/* 5022 */     backgroundImpl(image);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void backgroundImpl(PImage image)
/*      */   {
/* 5032 */     set(0, 0, image);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void backgroundImpl()
/*      */   {
/* 5043 */     pushStyle();
/* 5044 */     pushMatrix();
/* 5045 */     resetMatrix();
/* 5046 */     fill(this.backgroundColor);
/* 5047 */     rect(0.0F, 0.0F, this.width, this.height);
/* 5048 */     popMatrix();
/* 5049 */     popStyle();
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
/*      */   public void colorMode(int mode)
/*      */   {
/* 5089 */     colorMode(mode, this.colorModeX, this.colorModeY, this.colorModeZ, this.colorModeA);
/*      */   }
/*      */   
/*      */   public void colorMode(int mode, float max)
/*      */   {
/* 5094 */     colorMode(mode, max, max, max, max);
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
/*      */   public void colorMode(int mode, float maxX, float maxY, float maxZ)
/*      */   {
/* 5108 */     colorMode(mode, maxX, maxY, maxZ, this.colorModeA);
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
/*      */   public void colorMode(int mode, float maxX, float maxY, float maxZ, float maxA)
/*      */   {
/* 5127 */     this.colorMode = mode;
/*      */     
/* 5129 */     this.colorModeX = maxX;
/* 5130 */     this.colorModeY = maxY;
/* 5131 */     this.colorModeZ = maxZ;
/* 5132 */     this.colorModeA = maxA;
/*      */     
/*      */ 
/* 5135 */     this.colorModeScale = 
/* 5136 */       ((maxA != 1.0F) || (maxX != maxY) || (maxY != maxZ) || (maxZ != maxA));
/*      */     
/*      */ 
/*      */ 
/* 5140 */     this.colorModeDefault = ((this.colorMode == 1) && 
/* 5141 */       (this.colorModeA == 255.0F) && (this.colorModeX == 255.0F) && 
/* 5142 */       (this.colorModeY == 255.0F) && (this.colorModeZ == 255.0F));
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
/*      */   protected void colorCalc(int rgb)
/*      */   {
/* 5183 */     if (((rgb & 0xFF000000) == 0) && (rgb <= this.colorModeX)) {
/* 5184 */       colorCalc(rgb);
/*      */     }
/*      */     else {
/* 5187 */       colorCalcARGB(rgb, this.colorModeA);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void colorCalc(int rgb, float alpha)
/*      */   {
/* 5193 */     if (((rgb & 0xFF000000) == 0) && (rgb <= this.colorModeX)) {
/* 5194 */       colorCalc(rgb, alpha);
/*      */     }
/*      */     else {
/* 5197 */       colorCalcARGB(rgb, alpha);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void colorCalc(float gray)
/*      */   {
/* 5203 */     colorCalc(gray, this.colorModeA);
/*      */   }
/*      */   
/*      */   protected void colorCalc(float gray, float alpha)
/*      */   {
/* 5208 */     if (gray > this.colorModeX) gray = this.colorModeX;
/* 5209 */     if (alpha > this.colorModeA) { alpha = this.colorModeA;
/*      */     }
/* 5211 */     if (gray < 0.0F) gray = 0.0F;
/* 5212 */     if (alpha < 0.0F) { alpha = 0.0F;
/*      */     }
/* 5214 */     this.calcR = (this.colorModeScale ? gray / this.colorModeX : gray);
/* 5215 */     this.calcG = this.calcR;
/* 5216 */     this.calcB = this.calcR;
/* 5217 */     this.calcA = (this.colorModeScale ? alpha / this.colorModeA : alpha);
/*      */     
/* 5219 */     this.calcRi = ((int)(this.calcR * 255.0F));this.calcGi = ((int)(this.calcG * 255.0F));
/* 5220 */     this.calcBi = ((int)(this.calcB * 255.0F));this.calcAi = ((int)(this.calcA * 255.0F));
/* 5221 */     this.calcColor = (this.calcAi << 24 | this.calcRi << 16 | this.calcGi << 8 | this.calcBi);
/* 5222 */     this.calcAlpha = (this.calcAi != 255);
/*      */   }
/*      */   
/*      */   protected void colorCalc(float x, float y, float z)
/*      */   {
/* 5227 */     colorCalc(x, y, z, this.colorModeA);
/*      */   }
/*      */   
/*      */   protected void colorCalc(float x, float y, float z, float a)
/*      */   {
/* 5232 */     if (x > this.colorModeX) x = this.colorModeX;
/* 5233 */     if (y > this.colorModeY) y = this.colorModeY;
/* 5234 */     if (z > this.colorModeZ) z = this.colorModeZ;
/* 5235 */     if (a > this.colorModeA) { a = this.colorModeA;
/*      */     }
/* 5237 */     if (x < 0.0F) x = 0.0F;
/* 5238 */     if (y < 0.0F) y = 0.0F;
/* 5239 */     if (z < 0.0F) z = 0.0F;
/* 5240 */     if (a < 0.0F) { a = 0.0F;
/*      */     }
/* 5242 */     switch (this.colorMode) {
/*      */     case 1: 
/* 5244 */       if (this.colorModeScale) {
/* 5245 */         this.calcR = (x / this.colorModeX);
/* 5246 */         this.calcG = (y / this.colorModeY);
/* 5247 */         this.calcB = (z / this.colorModeZ);
/* 5248 */         this.calcA = (a / this.colorModeA);
/*      */       } else {
/* 5250 */         this.calcR = x;this.calcG = y;this.calcB = z;this.calcA = a;
/*      */       }
/* 5252 */       break;
/*      */     
/*      */     case 3: 
/* 5255 */       x /= this.colorModeX;
/* 5256 */       y /= this.colorModeY;
/* 5257 */       z /= this.colorModeZ;
/*      */       
/* 5259 */       this.calcA = (this.colorModeScale ? a / this.colorModeA : a);
/*      */       
/* 5261 */       if (y == 0.0F) {
/* 5262 */         this.calcR = (this.calcG = this.calcB = z);
/*      */       }
/*      */       else {
/* 5265 */         float which = (x - (int)x) * 6.0F;
/* 5266 */         float f = which - (int)which;
/* 5267 */         float p = z * (1.0F - y);
/* 5268 */         float q = z * (1.0F - y * f);
/* 5269 */         float t = z * (1.0F - y * (1.0F - f));
/*      */         
/* 5271 */         switch ((int)which) {
/* 5272 */         case 0:  this.calcR = z;this.calcG = t;this.calcB = p; break;
/* 5273 */         case 1:  this.calcR = q;this.calcG = z;this.calcB = p; break;
/* 5274 */         case 2:  this.calcR = p;this.calcG = z;this.calcB = t; break;
/* 5275 */         case 3:  this.calcR = p;this.calcG = q;this.calcB = z; break;
/* 5276 */         case 4:  this.calcR = t;this.calcG = p;this.calcB = z; break;
/* 5277 */         case 5:  this.calcR = z;this.calcG = p;this.calcB = q;
/*      */         }
/*      */       }
/*      */       break;
/*      */     }
/* 5282 */     this.calcRi = ((int)(255.0F * this.calcR));this.calcGi = ((int)(255.0F * this.calcG));
/* 5283 */     this.calcBi = ((int)(255.0F * this.calcB));this.calcAi = ((int)(255.0F * this.calcA));
/* 5284 */     this.calcColor = (this.calcAi << 24 | this.calcRi << 16 | this.calcGi << 8 | this.calcBi);
/* 5285 */     this.calcAlpha = (this.calcAi != 255);
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
/*      */   protected void colorCalcARGB(int argb, float alpha)
/*      */   {
/* 5301 */     if (alpha == this.colorModeA) {
/* 5302 */       this.calcAi = (argb >> 24 & 0xFF);
/* 5303 */       this.calcColor = argb;
/*      */     } else {
/* 5305 */       this.calcAi = ((int)((argb >> 24 & 0xFF) * (alpha / this.colorModeA)));
/* 5306 */       this.calcColor = (this.calcAi << 24 | argb & 0xFFFFFF);
/*      */     }
/* 5308 */     this.calcRi = (argb >> 16 & 0xFF);
/* 5309 */     this.calcGi = (argb >> 8 & 0xFF);
/* 5310 */     this.calcBi = (argb & 0xFF);
/* 5311 */     this.calcA = (this.calcAi / 255.0F);
/* 5312 */     this.calcR = (this.calcRi / 255.0F);
/* 5313 */     this.calcG = (this.calcGi / 255.0F);
/* 5314 */     this.calcB = (this.calcBi / 255.0F);
/* 5315 */     this.calcAlpha = (this.calcAi != 255);
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
/*      */   public final int color(int gray)
/*      */   {
/* 5334 */     if (((gray & 0xFF000000) == 0) && (gray <= this.colorModeX)) {
/* 5335 */       if (this.colorModeDefault)
/*      */       {
/* 5337 */         if (gray > 255) gray = 255; else if (gray < 0) gray = 0;
/* 5338 */         return 0xFF000000 | gray << 16 | gray << 8 | gray;
/*      */       }
/* 5340 */       colorCalc(gray);
/*      */     }
/*      */     else {
/* 5343 */       colorCalcARGB(gray, this.colorModeA);
/*      */     }
/* 5345 */     return this.calcColor;
/*      */   }
/*      */   
/*      */   public final int color(float gray)
/*      */   {
/* 5350 */     colorCalc(gray);
/* 5351 */     return this.calcColor;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public final int color(int gray, int alpha)
/*      */   {
/* 5359 */     if (this.colorModeDefault)
/*      */     {
/* 5361 */       if (gray > 255) gray = 255; else if (gray < 0) gray = 0;
/* 5362 */       if (alpha > 255) alpha = 255; else if (alpha < 0) { alpha = 0;
/*      */       }
/* 5364 */       return (alpha & 0xFF) << 24 | gray << 16 | gray << 8 | gray;
/*      */     }
/* 5366 */     colorCalc(gray, alpha);
/* 5367 */     return this.calcColor;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public final int color(int rgb, float alpha)
/*      */   {
/* 5375 */     if (((rgb & 0xFF000000) == 0) && (rgb <= this.colorModeX)) {
/* 5376 */       colorCalc(rgb, alpha);
/*      */     } else {
/* 5378 */       colorCalcARGB(rgb, alpha);
/*      */     }
/* 5380 */     return this.calcColor;
/*      */   }
/*      */   
/*      */   public final int color(float gray, float alpha)
/*      */   {
/* 5385 */     colorCalc(gray, alpha);
/* 5386 */     return this.calcColor;
/*      */   }
/*      */   
/*      */   public final int color(int x, int y, int z)
/*      */   {
/* 5391 */     if (this.colorModeDefault)
/*      */     {
/* 5393 */       if (x > 255) x = 255; else if (x < 0) x = 0;
/* 5394 */       if (y > 255) y = 255; else if (y < 0) y = 0;
/* 5395 */       if (z > 255) z = 255; else if (z < 0) { z = 0;
/*      */       }
/* 5397 */       return 0xFF000000 | x << 16 | y << 8 | z;
/*      */     }
/* 5399 */     colorCalc(x, y, z);
/* 5400 */     return this.calcColor;
/*      */   }
/*      */   
/*      */   public final int color(float x, float y, float z)
/*      */   {
/* 5405 */     colorCalc(x, y, z);
/* 5406 */     return this.calcColor;
/*      */   }
/*      */   
/*      */   public final int color(int x, int y, int z, int a)
/*      */   {
/* 5411 */     if (this.colorModeDefault)
/*      */     {
/* 5413 */       if (a > 255) a = 255; else if (a < 0) a = 0;
/* 5414 */       if (x > 255) x = 255; else if (x < 0) x = 0;
/* 5415 */       if (y > 255) y = 255; else if (y < 0) y = 0;
/* 5416 */       if (z > 255) z = 255; else if (z < 0) { z = 0;
/*      */       }
/* 5418 */       return a << 24 | x << 16 | y << 8 | z;
/*      */     }
/* 5420 */     colorCalc(x, y, z, a);
/* 5421 */     return this.calcColor;
/*      */   }
/*      */   
/*      */   public final int color(float x, float y, float z, float a)
/*      */   {
/* 5426 */     colorCalc(x, y, z, a);
/* 5427 */     return this.calcColor;
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
/*      */   public final float alpha(int what)
/*      */   {
/* 5446 */     float c = what >> 24 & 0xFF;
/* 5447 */     if (this.colorModeA == 255.0F) return c;
/* 5448 */     return c / 255.0F * this.colorModeA;
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
/*      */   public final float red(int what)
/*      */   {
/* 5466 */     float c = what >> 16 & 0xFF;
/* 5467 */     if (this.colorModeDefault) return c;
/* 5468 */     return c / 255.0F * this.colorModeX;
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
/*      */   public final float green(int what)
/*      */   {
/* 5486 */     float c = what >> 8 & 0xFF;
/* 5487 */     if (this.colorModeDefault) return c;
/* 5488 */     return c / 255.0F * this.colorModeY;
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
/*      */   public final float blue(int what)
/*      */   {
/* 5505 */     float c = what & 0xFF;
/* 5506 */     if (this.colorModeDefault) return c;
/* 5507 */     return c / 255.0F * this.colorModeZ;
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
/*      */   public final float hue(int what)
/*      */   {
/* 5524 */     if (what != this.cacheHsbKey) {
/* 5525 */       Color.RGBtoHSB(what >> 16 & 0xFF, what >> 8 & 0xFF, 
/* 5526 */         what & 0xFF, this.cacheHsbValue);
/* 5527 */       this.cacheHsbKey = what;
/*      */     }
/* 5529 */     return this.cacheHsbValue[0] * this.colorModeX;
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
/*      */   public final float saturation(int what)
/*      */   {
/* 5546 */     if (what != this.cacheHsbKey) {
/* 5547 */       Color.RGBtoHSB(what >> 16 & 0xFF, what >> 8 & 0xFF, 
/* 5548 */         what & 0xFF, this.cacheHsbValue);
/* 5549 */       this.cacheHsbKey = what;
/*      */     }
/* 5551 */     return this.cacheHsbValue[1] * this.colorModeY;
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
/*      */   public final float brightness(int what)
/*      */   {
/* 5569 */     if (what != this.cacheHsbKey) {
/* 5570 */       Color.RGBtoHSB(what >> 16 & 0xFF, what >> 8 & 0xFF, 
/* 5571 */         what & 0xFF, this.cacheHsbValue);
/* 5572 */       this.cacheHsbKey = what;
/*      */     }
/* 5574 */     return this.cacheHsbValue[2] * this.colorModeZ;
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
/*      */   public int lerpColor(int c1, int c2, float amt)
/*      */   {
/* 5598 */     return lerpColor(c1, c2, amt, this.colorMode);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int lerpColor(int c1, int c2, float amt, int mode)
/*      */   {
/* 5609 */     if (mode == 1) {
/* 5610 */       float a1 = c1 >> 24 & 0xFF;
/* 5611 */       float r1 = c1 >> 16 & 0xFF;
/* 5612 */       float g1 = c1 >> 8 & 0xFF;
/* 5613 */       float b1 = c1 & 0xFF;
/* 5614 */       float a2 = c2 >> 24 & 0xFF;
/* 5615 */       float r2 = c2 >> 16 & 0xFF;
/* 5616 */       float g2 = c2 >> 8 & 0xFF;
/* 5617 */       float b2 = c2 & 0xFF;
/*      */       
/* 5619 */       return (int)(a1 + (a2 - a1) * amt) << 24 | 
/* 5620 */         (int)(r1 + (r2 - r1) * amt) << 16 | 
/* 5621 */         (int)(g1 + (g2 - g1) * amt) << 8 | 
/* 5622 */         (int)(b1 + (b2 - b1) * amt);
/*      */     }
/* 5624 */     if (mode == 3) {
/* 5625 */       if (lerpColorHSB1 == null) {
/* 5626 */         lerpColorHSB1 = new float[3];
/* 5627 */         lerpColorHSB2 = new float[3];
/*      */       }
/*      */       
/* 5630 */       float a1 = c1 >> 24 & 0xFF;
/* 5631 */       float a2 = c2 >> 24 & 0xFF;
/* 5632 */       int alfa = (int)(a1 + (a2 - a1) * amt) << 24;
/*      */       
/* 5634 */       Color.RGBtoHSB(c1 >> 16 & 0xFF, c1 >> 8 & 0xFF, c1 & 0xFF, 
/* 5635 */         lerpColorHSB1);
/* 5636 */       Color.RGBtoHSB(c2 >> 16 & 0xFF, c2 >> 8 & 0xFF, c2 & 0xFF, 
/* 5637 */         lerpColorHSB2);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5664 */       float ho = PApplet.lerp(lerpColorHSB1[0], lerpColorHSB2[0], amt);
/* 5665 */       float so = PApplet.lerp(lerpColorHSB1[1], lerpColorHSB2[1], amt);
/* 5666 */       float bo = PApplet.lerp(lerpColorHSB1[2], lerpColorHSB2[2], amt);
/*      */       
/* 5668 */       return alfa | Color.HSBtoRGB(ho, so, bo) & 0xFFFFFF;
/*      */     }
/* 5670 */     return 0;
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
/*      */   public void beginRaw(PGraphics rawGraphics)
/*      */   {
/* 5684 */     this.raw = rawGraphics;
/* 5685 */     rawGraphics.beginDraw();
/*      */   }
/*      */   
/*      */   public void endRaw()
/*      */   {
/* 5690 */     if (this.raw != null)
/*      */     {
/*      */ 
/* 5693 */       flush();
/*      */       
/*      */ 
/*      */ 
/* 5697 */       this.raw.endDraw();
/* 5698 */       this.raw.dispose();
/* 5699 */       this.raw = null;
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
/*      */   public static void showWarning(String msg)
/*      */   {
/* 5718 */     if (warnings == null) {
/* 5719 */       warnings = new HashMap();
/*      */     }
/* 5721 */     if (!warnings.containsKey(msg)) {
/* 5722 */       System.err.println(msg);
/* 5723 */       warnings.put(msg, new Object());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static void showDepthWarning(String method)
/*      */   {
/* 5733 */     showWarning(
/* 5734 */       method + "() can only be used with a renderer that " + "supports 3D, such as P3D or OPENGL.");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static void showDepthWarningXYZ(String method)
/*      */   {
/* 5744 */     showWarning(
/*      */     
/*      */ 
/* 5747 */       method + "() with x, y, and z coordinates " + "can only be used with a renderer that " + "supports 3D, such as P3D or OPENGL. " + "Use a version without a z-coordinate instead.");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static void showMethodWarning(String method)
/*      */   {
/* 5755 */     showWarning(method + "() is not available with this renderer.");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static void showVariationWarning(String str)
/*      */   {
/* 5765 */     showWarning(str + " is not available with this renderer.");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static void showMissingWarning(String method)
/*      */   {
/* 5775 */     showWarning(
/* 5776 */       method + "(), or this particular variation of it, " + "is not available with this renderer.");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void showException(String msg)
/*      */   {
/* 5786 */     throw new RuntimeException(msg);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void defaultFontOrDeath(String method)
/*      */   {
/* 5794 */     defaultFontOrDeath(method, 12.0F);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void defaultFontOrDeath(String method, float size)
/*      */   {
/* 5804 */     if (this.parent != null) {
/* 5805 */       this.textFont = this.parent.createDefaultFont(size);
/*      */     } else {
/* 5807 */       throw new RuntimeException("Use textFont() before " + method + "()");
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
/*      */   public boolean displayable()
/*      */   {
/* 5827 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean is2D()
/*      */   {
/* 5835 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean is3D()
/*      */   {
/* 5843 */     return false;
/*      */   }
/*      */ }


/* Location:              /Users/AndrewKanarek/Desktop/MICHIGAN/KiefferWork/scripts-zibo/JavaApps/EllinghamDiagram/core.jar!/processing/core/PGraphics.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */