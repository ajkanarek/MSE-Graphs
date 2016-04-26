/*      */ package processing.core;
/*      */ 
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.image.DirectColorModel;
/*      */ import java.awt.image.MemoryImageSource;
/*      */ import java.util.Arrays;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PGraphics3D
/*      */   extends PGraphics
/*      */ {
/*      */   public float[] zbuffer;
/*      */   public PMatrix3D modelview;
/*      */   public PMatrix3D modelviewInv;
/*      */   protected boolean sizeChanged;
/*      */   public PMatrix3D camera;
/*      */   protected PMatrix3D cameraInv;
/*      */   public float cameraFOV;
/*      */   public float cameraX;
/*      */   public float cameraY;
/*      */   public float cameraZ;
/*      */   public float cameraNear;
/*      */   public float cameraFar;
/*      */   public float cameraAspect;
/*      */   public PMatrix3D projection;
/*      */   public static final int MAX_LIGHTS = 8;
/*   89 */   public int lightCount = 0;
/*      */   
/*      */ 
/*      */   public int[] lightType;
/*      */   
/*      */ 
/*      */   public PVector[] lightPosition;
/*      */   
/*      */ 
/*      */   public PVector[] lightNormal;
/*      */   
/*      */ 
/*      */   public float[] lightFalloffConstant;
/*      */   
/*      */ 
/*      */   public float[] lightFalloffLinear;
/*      */   
/*      */   public float[] lightFalloffQuadratic;
/*      */   
/*      */   public float[] lightSpotAngle;
/*      */   
/*      */   public float[] lightSpotAngleCos;
/*      */   
/*      */   public float[] lightSpotConcentration;
/*      */   
/*      */   public float[][] lightDiffuse;
/*      */   
/*      */   public float[][] lightSpecular;
/*      */   
/*      */   public float[] currentLightSpecular;
/*      */   
/*      */   public float currentLightFalloffConstant;
/*      */   
/*      */   public float currentLightFalloffLinear;
/*      */   
/*      */   public float currentLightFalloffQuadratic;
/*      */   
/*      */   public static final int TRI_DIFFUSE_R = 0;
/*      */   
/*      */   public static final int TRI_DIFFUSE_G = 1;
/*      */   
/*      */   public static final int TRI_DIFFUSE_B = 2;
/*      */   
/*      */   public static final int TRI_DIFFUSE_A = 3;
/*      */   
/*      */   public static final int TRI_SPECULAR_R = 4;
/*      */   
/*      */   public static final int TRI_SPECULAR_G = 5;
/*      */   
/*      */   public static final int TRI_SPECULAR_B = 6;
/*      */   
/*      */   public static final int TRI_COLOR_COUNT = 7;
/*      */   
/*      */   private boolean lightingDependsOnVertexPosition;
/*      */   
/*      */   static final int LIGHT_AMBIENT_R = 0;
/*      */   
/*      */   static final int LIGHT_AMBIENT_G = 1;
/*      */   
/*      */   static final int LIGHT_AMBIENT_B = 2;
/*      */   
/*      */   static final int LIGHT_DIFFUSE_R = 3;
/*      */   
/*      */   static final int LIGHT_DIFFUSE_G = 4;
/*      */   
/*      */   static final int LIGHT_DIFFUSE_B = 5;
/*      */   
/*      */   static final int LIGHT_SPECULAR_R = 6;
/*      */   
/*      */   static final int LIGHT_SPECULAR_G = 7;
/*      */   
/*      */   static final int LIGHT_SPECULAR_B = 8;
/*      */   
/*      */   static final int LIGHT_COLOR_COUNT = 9;
/*      */   
/*  164 */   protected float[] tempLightingContribution = new float[9];
/*      */   
/*      */ 
/*      */ 
/*  168 */   protected PVector lightTriangleNorm = new PVector();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean manipulatingCamera;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  179 */   float[][] matrixStack = new float[32][16];
/*  180 */   float[][] matrixInvStack = new float[32][16];
/*      */   
/*      */ 
/*      */   int matrixStackDepth;
/*      */   
/*      */ 
/*      */   protected PMatrix3D forwardTransform;
/*      */   
/*      */ 
/*      */   protected PMatrix3D reverseTransform;
/*      */   
/*      */ 
/*      */   protected float leftScreen;
/*      */   
/*      */   protected float rightScreen;
/*      */   
/*      */   protected float topScreen;
/*      */   
/*      */   protected float bottomScreen;
/*      */   
/*      */   protected float nearPlane;
/*      */   
/*  202 */   private boolean frustumMode = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  208 */   protected static boolean s_enableAccurateTextures = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public PSmoothTriangle smoothTriangle;
/*      */   
/*      */ 
/*      */ 
/*      */   protected int shapeFirst;
/*      */   
/*      */ 
/*      */ 
/*      */   protected int shapeLast;
/*      */   
/*      */ 
/*      */ 
/*      */   protected int shapeLastPlusClipped;
/*      */   
/*      */ 
/*      */ 
/*  229 */   protected int[] vertexOrder = new int['Ȁ'];
/*      */   
/*      */ 
/*      */ 
/*      */   protected int pathCount;
/*      */   
/*      */ 
/*      */ 
/*  237 */   protected int[] pathOffset = new int[64];
/*  238 */   protected int[] pathLength = new int[64];
/*      */   
/*      */ 
/*      */   protected static final int VERTEX1 = 0;
/*      */   
/*      */   protected static final int VERTEX2 = 1;
/*      */   
/*      */   protected static final int VERTEX3 = 2;
/*      */   
/*      */   protected static final int STROKE_COLOR = 1;
/*      */   
/*      */   protected static final int TEXTURE_INDEX = 3;
/*      */   
/*      */   protected static final int POINT_FIELD_COUNT = 2;
/*      */   
/*      */   protected static final int LINE_FIELD_COUNT = 2;
/*      */   
/*      */   protected static final int TRIANGLE_FIELD_COUNT = 4;
/*      */   
/*      */   static final int DEFAULT_POINTS = 512;
/*      */   
/*  259 */   protected int[][] points = new int['Ȁ'][2];
/*      */   
/*      */   protected int pointCount;
/*      */   
/*      */   static final int DEFAULT_LINES = 512;
/*      */   public PLine line;
/*  265 */   protected int[][] lines = new int['Ȁ'][2];
/*      */   
/*      */   protected int lineCount;
/*      */   
/*      */   static final int DEFAULT_TRIANGLES = 256;
/*      */   
/*      */   public PTriangle triangle;
/*  272 */   protected int[][] triangles = new int['Ā'][4];
/*      */   
/*  274 */   protected float[][][] triangleColors = new float['Ā'][3][7];
/*      */   
/*      */ 
/*      */   protected int triangleCount;
/*      */   
/*      */ 
/*      */   static final int DEFAULT_TEXTURES = 3;
/*      */   
/*      */ 
/*  283 */   protected PImage[] textures = new PImage[3];
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   int textureIndex;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   DirectColorModel cm;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   MemoryImageSource mis;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setSize(int iwidth, int iheight)
/*      */   {
/*  318 */     this.width = iwidth;
/*  319 */     this.height = iheight;
/*  320 */     this.width1 = (this.width - 1);
/*  321 */     this.height1 = (this.height - 1);
/*      */     
/*  323 */     allocate();
/*  324 */     reapplySettings();
/*      */     
/*      */ 
/*  327 */     this.lightType = new int[8];
/*  328 */     this.lightPosition = new PVector[8];
/*  329 */     this.lightNormal = new PVector[8];
/*  330 */     for (int i = 0; i < 8; i++) {
/*  331 */       this.lightPosition[i] = new PVector();
/*  332 */       this.lightNormal[i] = new PVector();
/*      */     }
/*  334 */     this.lightDiffuse = new float[8][3];
/*  335 */     this.lightSpecular = new float[8][3];
/*  336 */     this.lightFalloffConstant = new float[8];
/*  337 */     this.lightFalloffLinear = new float[8];
/*  338 */     this.lightFalloffQuadratic = new float[8];
/*  339 */     this.lightSpotAngle = new float[8];
/*  340 */     this.lightSpotAngleCos = new float[8];
/*  341 */     this.lightSpotConcentration = new float[8];
/*  342 */     this.currentLightSpecular = new float[3];
/*      */     
/*  344 */     this.projection = new PMatrix3D();
/*  345 */     this.modelview = new PMatrix3D();
/*  346 */     this.modelviewInv = new PMatrix3D();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  352 */     this.forwardTransform = this.modelview;
/*  353 */     this.reverseTransform = this.modelviewInv;
/*      */     
/*      */ 
/*  356 */     this.cameraFOV = 1.0471976F;
/*  357 */     this.cameraX = (this.width / 2.0F);
/*  358 */     this.cameraY = (this.height / 2.0F);
/*  359 */     this.cameraZ = (this.cameraY / (float)Math.tan(this.cameraFOV / 2.0F));
/*  360 */     this.cameraNear = (this.cameraZ / 10.0F);
/*  361 */     this.cameraFar = (this.cameraZ * 10.0F);
/*  362 */     this.cameraAspect = (this.width / this.height);
/*      */     
/*  364 */     this.camera = new PMatrix3D();
/*  365 */     this.cameraInv = new PMatrix3D();
/*      */     
/*      */ 
/*  368 */     this.sizeChanged = true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void allocate()
/*      */   {
/*  376 */     this.pixelCount = (this.width * this.height);
/*  377 */     this.pixels = new int[this.pixelCount];
/*  378 */     this.zbuffer = new float[this.pixelCount];
/*      */     
/*  380 */     if (this.primarySurface) {
/*  381 */       this.cm = new DirectColorModel(32, 16711680, 65280, 255);
/*  382 */       this.mis = new MemoryImageSource(this.width, this.height, this.pixels, 0, this.width);
/*  383 */       this.mis.setFullBufferUpdates(true);
/*  384 */       this.mis.setAnimated(true);
/*  385 */       this.image = Toolkit.getDefaultToolkit().createImage(this.mis);
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*  390 */       Arrays.fill(this.zbuffer, Float.MAX_VALUE);
/*      */     }
/*      */     
/*  393 */     this.line = new PLine(this);
/*  394 */     this.triangle = new PTriangle(this);
/*  395 */     this.smoothTriangle = new PSmoothTriangle(this);
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
/*      */   public void beginDraw()
/*      */   {
/*  412 */     if (!this.settingsInited) { defaultSettings();
/*      */     }
/*  414 */     if (this.sizeChanged)
/*      */     {
/*  416 */       camera();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  421 */       perspective();
/*      */       
/*      */ 
/*  424 */       this.sizeChanged = false;
/*      */     }
/*      */     
/*  427 */     resetMatrix();
/*      */     
/*      */ 
/*  430 */     this.vertexCount = 0;
/*      */     
/*  432 */     this.modelview.set(this.camera);
/*  433 */     this.modelviewInv.set(this.cameraInv);
/*      */     
/*      */ 
/*  436 */     this.lightCount = 0;
/*  437 */     this.lightingDependsOnVertexPosition = false;
/*  438 */     lightFalloff(1.0F, 0.0F, 0.0F);
/*  439 */     lightSpecular(0.0F, 0.0F, 0.0F);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  452 */     this.shapeFirst = 0;
/*      */     
/*      */ 
/*  455 */     Arrays.fill(this.textures, null);
/*  456 */     this.textureIndex = 0;
/*      */     
/*  458 */     normal(0.0F, 0.0F, 1.0F);
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
/*      */   public void endDraw()
/*      */   {
/*  472 */     if (this.hints[5] != 0) {
/*  473 */       flush();
/*      */     }
/*  475 */     if (this.mis != null) {
/*  476 */       this.mis.newPixels(this.pixels, this.cm, 0, this.width);
/*      */     }
/*      */     
/*      */ 
/*  480 */     updatePixels();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void defaultSettings()
/*      */   {
/*  491 */     super.defaultSettings();
/*      */     
/*  493 */     this.manipulatingCamera = false;
/*  494 */     this.forwardTransform = this.modelview;
/*  495 */     this.reverseTransform = this.modelviewInv;
/*      */     
/*      */ 
/*  498 */     camera();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  503 */     perspective();
/*      */     
/*      */ 
/*  506 */     textureMode(2);
/*      */     
/*  508 */     emissive(0.0F);
/*  509 */     specular(0.5F);
/*  510 */     shininess(1.0F);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void hint(int which)
/*      */   {
/*  521 */     if (which == -5) {
/*  522 */       flush();
/*  523 */     } else if ((which == 4) && 
/*  524 */       (this.zbuffer != null)) {
/*  525 */       Arrays.fill(this.zbuffer, Float.MAX_VALUE);
/*      */     }
/*      */     
/*  528 */     super.hint(which);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void beginShape(int kind)
/*      */   {
/*  539 */     this.shape = kind;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  546 */     if (this.hints[5] != 0)
/*      */     {
/*      */ 
/*  549 */       this.shapeFirst = this.vertexCount;
/*  550 */       this.shapeLast = 0;
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*  555 */       this.vertexCount = 0;
/*  556 */       if (this.line != null) this.line.reset();
/*  557 */       this.lineCount = 0;
/*      */       
/*  559 */       if (this.triangle != null) this.triangle.reset();
/*  560 */       this.triangleCount = 0;
/*      */     }
/*      */     
/*  563 */     this.textureImage = null;
/*  564 */     this.curveVertexCount = 0;
/*  565 */     this.normalMode = 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void texture(PImage image)
/*      */   {
/*  577 */     this.textureImage = image;
/*      */     
/*  579 */     if (this.textureIndex == this.textures.length - 1) {
/*  580 */       this.textures = ((PImage[])PApplet.expand(this.textures));
/*      */     }
/*  582 */     if (this.textures[this.textureIndex] != null) {
/*  583 */       this.textureIndex += 1;
/*      */     }
/*  585 */     this.textures[this.textureIndex] = image;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void vertex(float x, float y)
/*      */   {
/*  592 */     vertex(x, y, 0.0F);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void vertex(float x, float y, float u, float v)
/*      */   {
/*  601 */     vertex(x, y, 0.0F, u, v);
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
/*      */   public void endShape(int mode)
/*      */   {
/*  615 */     this.shapeLast = this.vertexCount;
/*  616 */     this.shapeLastPlusClipped = this.shapeLast;
/*      */     
/*      */ 
/*      */ 
/*  620 */     if (this.vertexCount == 0) {
/*  621 */       this.shape = 0;
/*  622 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  627 */     endShapeModelToCamera(this.shapeFirst, this.shapeLast);
/*      */     
/*  629 */     if (this.stroke) {
/*  630 */       endShapeStroke(mode);
/*      */     }
/*      */     
/*  633 */     if ((this.fill) || (this.textureImage != null)) {
/*  634 */       endShapeFill();
/*      */     }
/*      */     
/*      */ 
/*  638 */     endShapeLighting((this.lightCount > 0) && (this.fill));
/*      */     
/*      */ 
/*      */ 
/*  642 */     endShapeCameraToScreen(this.shapeFirst, this.shapeLastPlusClipped);
/*      */     
/*      */ 
/*      */ 
/*  646 */     if (this.hints[5] == 0) {
/*  647 */       if (((this.fill) || (this.textureImage != null)) && 
/*  648 */         (this.triangleCount > 0)) {
/*  649 */         renderTriangles(0, this.triangleCount);
/*  650 */         if (this.raw != null) {
/*  651 */           rawTriangles(0, this.triangleCount);
/*      */         }
/*  653 */         this.triangleCount = 0;
/*      */       }
/*      */       
/*  656 */       if (this.stroke) {
/*  657 */         if (this.pointCount > 0) {
/*  658 */           renderPoints(0, this.pointCount);
/*  659 */           if (this.raw != null) {
/*  660 */             rawPoints(0, this.pointCount);
/*      */           }
/*  662 */           this.pointCount = 0;
/*      */         }
/*      */         
/*  665 */         if (this.lineCount > 0) {
/*  666 */           renderLines(0, this.lineCount);
/*  667 */           if (this.raw != null) {
/*  668 */             rawLines(0, this.lineCount);
/*      */           }
/*  670 */           this.lineCount = 0;
/*      */         }
/*      */       }
/*  673 */       this.pathCount = 0;
/*      */     }
/*      */     
/*  676 */     this.shape = 0;
/*      */   }
/*      */   
/*      */   protected void endShapeModelToCamera(int start, int stop)
/*      */   {
/*  681 */     for (int i = start; i < stop; i++) {
/*  682 */       float[] vertex = this.vertices[i];
/*      */       
/*  684 */       vertex[21] = 
/*  685 */         (this.modelview.m00 * vertex[0] + this.modelview.m01 * vertex[1] + 
/*  686 */         this.modelview.m02 * vertex[2] + this.modelview.m03);
/*  687 */       vertex[22] = 
/*  688 */         (this.modelview.m10 * vertex[0] + this.modelview.m11 * vertex[1] + 
/*  689 */         this.modelview.m12 * vertex[2] + this.modelview.m13);
/*  690 */       vertex[23] = 
/*  691 */         (this.modelview.m20 * vertex[0] + this.modelview.m21 * vertex[1] + 
/*  692 */         this.modelview.m22 * vertex[2] + this.modelview.m23);
/*  693 */       vertex[24] = 
/*  694 */         (this.modelview.m30 * vertex[0] + this.modelview.m31 * vertex[1] + 
/*  695 */         this.modelview.m32 * vertex[2] + this.modelview.m33);
/*      */       
/*      */ 
/*  698 */       if ((vertex[24] != 0.0F) && (vertex[24] != 1.0F)) {
/*  699 */         vertex[21] /= vertex[24];
/*  700 */         vertex[22] /= vertex[24];
/*  701 */         vertex[23] /= vertex[24];
/*      */       }
/*  703 */       vertex[24] = 1.0F;
/*      */     }
/*      */   }
/*      */   
/*      */   protected void endShapeStroke(int mode)
/*      */   {
/*  709 */     switch (this.shape)
/*      */     {
/*      */     case 2: 
/*  712 */       int stop = this.shapeLast;
/*  713 */       for (int i = this.shapeFirst; i < stop; i++)
/*      */       {
/*  715 */         addPoint(i);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  722 */       break;
/*      */     
/*      */ 
/*      */ 
/*      */     case 4: 
/*  727 */       int first = this.lineCount;
/*  728 */       int stop = this.shapeLast - 1;
/*      */       
/*      */ 
/*      */ 
/*  732 */       if (this.shape != 4) { addLineBreak();
/*      */       }
/*  734 */       for (int i = this.shapeFirst; i < stop; i += 2)
/*      */       {
/*  736 */         if (this.shape == 4) addLineBreak();
/*  737 */         addLine(i, i + 1);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  742 */       if (mode == 2) {
/*  743 */         addLine(stop, this.lines[first][0]);
/*      */       }
/*      */       
/*  746 */       break;
/*      */     
/*      */ 
/*      */     case 9: 
/*  750 */       for (int i = this.shapeFirst; i < this.shapeLast - 2; i += 3) {
/*  751 */         addLineBreak();
/*      */         
/*  753 */         addLine(i + 0, i + 1);
/*  754 */         addLine(i + 1, i + 2);
/*  755 */         addLine(i + 2, i + 0);
/*      */       }
/*      */       
/*  758 */       break;
/*      */     
/*      */ 
/*      */ 
/*      */     case 10: 
/*  763 */       int stop = this.shapeLast - 1;
/*      */       
/*  765 */       addLineBreak();
/*  766 */       for (int i = this.shapeFirst; i < stop; i++)
/*      */       {
/*  768 */         addLine(i, i + 1);
/*      */       }
/*      */       
/*      */ 
/*  772 */       stop = this.shapeLast - 2;
/*  773 */       for (int i = this.shapeFirst; i < stop; i++) {
/*  774 */         addLineBreak();
/*  775 */         addLine(i, i + 2);
/*      */       }
/*      */       
/*  778 */       break;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     case 11: 
/*  784 */       for (int i = this.shapeFirst + 1; i < this.shapeLast; i++) {
/*  785 */         addLineBreak();
/*  786 */         addLine(this.shapeFirst, i);
/*      */       }
/*      */       
/*      */ 
/*  790 */       addLineBreak();
/*  791 */       for (int i = this.shapeFirst + 1; i < this.shapeLast - 1; i++) {
/*  792 */         addLine(i, i + 1);
/*      */       }
/*      */       
/*  795 */       addLine(this.shapeLast - 1, this.shapeFirst + 1);
/*      */       
/*  797 */       break;
/*      */     
/*      */ 
/*      */     case 16: 
/*  801 */       for (int i = this.shapeFirst; i < this.shapeLast; i += 4) {
/*  802 */         addLineBreak();
/*      */         
/*  804 */         addLine(i + 0, i + 1);
/*  805 */         addLine(i + 1, i + 2);
/*  806 */         addLine(i + 2, i + 3);
/*  807 */         addLine(i + 3, i + 0);
/*      */       }
/*      */       
/*  810 */       break;
/*      */     
/*      */ 
/*      */     case 17: 
/*  814 */       for (int i = this.shapeFirst; i < this.shapeLast - 3; i += 2) {
/*  815 */         addLineBreak();
/*  816 */         addLine(i + 0, i + 2);
/*  817 */         addLine(i + 2, i + 3);
/*  818 */         addLine(i + 3, i + 1);
/*  819 */         addLine(i + 1, i + 0);
/*      */       }
/*      */       
/*  822 */       break;
/*      */     
/*      */ 
/*      */ 
/*      */     case 20: 
/*  827 */       int stop = this.shapeLast - 1;
/*      */       
/*  829 */       addLineBreak();
/*  830 */       for (int i = this.shapeFirst; i < stop; i++) {
/*  831 */         addLine(i, i + 1);
/*      */       }
/*  833 */       if (mode == 2)
/*      */       {
/*  835 */         addLine(stop, this.shapeFirst);
/*      */       }
/*      */       break;
/*      */     }
/*      */     
/*      */   }
/*      */   
/*      */   protected void endShapeFill()
/*      */   {
/*  844 */     switch (this.shape)
/*      */     {
/*      */     case 11: 
/*  847 */       int stop = this.shapeLast - 1;
/*  848 */       for (int i = this.shapeFirst + 1; i < stop; i++) {
/*  849 */         addTriangle(this.shapeFirst, i, i + 1);
/*      */       }
/*      */       
/*  852 */       break;
/*      */     
/*      */ 
/*      */     case 9: 
/*  856 */       int stop = this.shapeLast - 2;
/*  857 */       for (int i = this.shapeFirst; i < stop; i += 3)
/*      */       {
/*      */ 
/*  860 */         if (i % 2 == 0) {
/*  861 */           addTriangle(i, i + 2, i + 1);
/*      */         } else {
/*  863 */           addTriangle(i, i + 1, i + 2);
/*      */         }
/*      */       }
/*      */       
/*  867 */       break;
/*      */     
/*      */ 
/*      */     case 10: 
/*  871 */       int stop = this.shapeLast - 2;
/*  872 */       for (int i = this.shapeFirst; i < stop; i++)
/*      */       {
/*      */ 
/*  875 */         if (i % 2 == 0) {
/*  876 */           addTriangle(i, i + 2, i + 1);
/*      */         } else {
/*  878 */           addTriangle(i, i + 1, i + 2);
/*      */         }
/*      */       }
/*      */       
/*  882 */       break;
/*      */     
/*      */ 
/*      */     case 16: 
/*  886 */       int stop = this.vertexCount - 3;
/*  887 */       for (int i = this.shapeFirst; i < stop; i += 4)
/*      */       {
/*  889 */         addTriangle(i, i + 1, i + 2);
/*      */         
/*  891 */         addTriangle(i, i + 2, i + 3);
/*      */       }
/*      */       
/*  894 */       break;
/*      */     
/*      */ 
/*      */     case 17: 
/*  898 */       int stop = this.vertexCount - 3;
/*  899 */       for (int i = this.shapeFirst; i < stop; i += 2)
/*      */       {
/*  901 */         addTriangle(i + 0, i + 2, i + 1);
/*      */         
/*  903 */         addTriangle(i + 2, i + 3, i + 1);
/*      */       }
/*      */       
/*  906 */       break;
/*      */     
/*      */ 
/*      */     case 20: 
/*  910 */       addPolygonTriangles();
/*      */     }
/*      */     
/*      */   }
/*      */   
/*      */ 
/*      */   protected void endShapeLighting(boolean lights)
/*      */   {
/*  918 */     if (lights)
/*      */     {
/*      */ 
/*      */ 
/*  922 */       if ((!this.lightingDependsOnVertexPosition) && (this.normalMode == 1)) {
/*  923 */         calcLightingContribution(this.shapeFirst, this.tempLightingContribution);
/*  924 */         for (int tri = 0; tri < this.triangleCount; tri++) {
/*  925 */           lightTriangle(tri, this.tempLightingContribution);
/*      */         }
/*      */       } else {
/*  928 */         for (int tri = 0; tri < this.triangleCount; tri++) {
/*  929 */           lightTriangle(tri);
/*      */         }
/*      */       }
/*      */     } else {
/*  933 */       for (int tri = 0; tri < this.triangleCount; tri++) {
/*  934 */         int index = this.triangles[tri][0];
/*  935 */         copyPrelitVertexColor(tri, index, 0);
/*  936 */         index = this.triangles[tri][1];
/*  937 */         copyPrelitVertexColor(tri, index, 1);
/*  938 */         index = this.triangles[tri][2];
/*  939 */         copyPrelitVertexColor(tri, index, 2);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   protected void endShapeCameraToScreen(int start, int stop)
/*      */   {
/*  946 */     for (int i = start; i < stop; i++) {
/*  947 */       float[] vx = this.vertices[i];
/*      */       
/*  949 */       float ox = 
/*  950 */         this.projection.m00 * vx[21] + this.projection.m01 * vx[22] + 
/*  951 */         this.projection.m02 * vx[23] + this.projection.m03 * vx[24];
/*  952 */       float oy = 
/*  953 */         this.projection.m10 * vx[21] + this.projection.m11 * vx[22] + 
/*  954 */         this.projection.m12 * vx[23] + this.projection.m13 * vx[24];
/*  955 */       float oz = 
/*  956 */         this.projection.m20 * vx[21] + this.projection.m21 * vx[22] + 
/*  957 */         this.projection.m22 * vx[23] + this.projection.m23 * vx[24];
/*  958 */       float ow = 
/*  959 */         this.projection.m30 * vx[21] + this.projection.m31 * vx[22] + 
/*  960 */         this.projection.m32 * vx[23] + this.projection.m33 * vx[24];
/*      */       
/*  962 */       if ((ow != 0.0F) && (ow != 1.0F)) {
/*  963 */         ox /= ow;oy /= ow;oz /= ow;
/*      */       }
/*      */       
/*  966 */       vx[18] = (this.width * (1.0F + ox) / 2.0F);
/*  967 */       vx[19] = (this.height * (1.0F + oy) / 2.0F);
/*  968 */       vx[20] = ((oz + 1.0F) / 2.0F);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void addPoint(int a)
/*      */   {
/*  980 */     if (this.pointCount == this.points.length) {
/*  981 */       int[][] temp = new int[this.pointCount << 1][2];
/*  982 */       System.arraycopy(this.points, 0, temp, 0, this.pointCount);
/*  983 */       this.points = temp;
/*      */     }
/*  985 */     this.points[this.pointCount][0] = a;
/*      */     
/*  987 */     this.points[this.pointCount][1] = this.strokeColor;
/*      */     
/*  989 */     this.pointCount += 1;
/*      */   }
/*      */   
/*      */   protected void renderPoints(int start, int stop)
/*      */   {
/*  994 */     if (this.strokeWeight != 1.0F) {
/*  995 */       for (int i = start; i < stop; i++) {
/*  996 */         float[] a = this.vertices[this.points[i][0]];
/*  997 */         renderLineVertices(a, a);
/*      */       }
/*      */     } else {
/* 1000 */       for (int i = start; i < stop; i++) {
/* 1001 */         float[] a = this.vertices[this.points[i][0]];
/* 1002 */         int sx = (int)(a[18] + 0.4999F);
/* 1003 */         int sy = (int)(a[19] + 0.4999F);
/* 1004 */         if ((sx >= 0) && (sx < this.width) && (sy >= 0) && (sy < this.height)) {
/* 1005 */           int index = sy * this.width + sx;
/* 1006 */           this.pixels[index] = this.points[i][1];
/* 1007 */           this.zbuffer[index] = a[20];
/*      */         }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void rawPoints(int start, int stop)
/*      */   {
/* 1070 */     this.raw.colorMode(1, 1.0F);
/* 1071 */     this.raw.noFill();
/* 1072 */     this.raw.strokeWeight(this.vertices[this.lines[start][0]][17]);
/* 1073 */     this.raw.beginShape(2);
/*      */     
/* 1075 */     for (int i = start; i < stop; i++) {
/* 1076 */       float[] a = this.vertices[this.lines[i][0]];
/*      */       
/* 1078 */       if (this.raw.is3D()) {
/* 1079 */         if (a[24] != 0.0F) {
/* 1080 */           this.raw.stroke(a[13], a[14], a[15], a[16]);
/* 1081 */           this.raw.vertex(a[21] / a[24], a[22] / a[24], a[23] / a[24]);
/*      */         }
/*      */       } else {
/* 1084 */         this.raw.stroke(a[13], a[14], a[15], a[16]);
/* 1085 */         this.raw.vertex(a[18], a[19]);
/*      */       }
/*      */     }
/* 1088 */     this.raw.endShape();
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
/*      */   protected final void addLineBreak()
/*      */   {
/* 1102 */     if (this.pathCount == this.pathOffset.length) {
/* 1103 */       this.pathOffset = PApplet.expand(this.pathOffset);
/* 1104 */       this.pathLength = PApplet.expand(this.pathLength);
/*      */     }
/* 1106 */     this.pathOffset[this.pathCount] = this.lineCount;
/* 1107 */     this.pathLength[this.pathCount] = 0;
/* 1108 */     this.pathCount += 1;
/*      */   }
/*      */   
/*      */   protected void addLine(int a, int b)
/*      */   {
/* 1113 */     addLineWithClip(a, b);
/*      */   }
/*      */   
/*      */   protected final void addLineWithClip(int a, int b)
/*      */   {
/* 1118 */     float az = this.vertices[a][23];
/* 1119 */     float bz = this.vertices[b][23];
/* 1120 */     if (az > this.cameraNear) {
/* 1121 */       if (bz > this.cameraNear) {
/* 1122 */         return;
/*      */       }
/* 1124 */       int cb = interpolateClipVertex(a, b);
/* 1125 */       addLineWithoutClip(cb, b);
/* 1126 */       return;
/*      */     }
/*      */     
/* 1129 */     if (bz <= this.cameraNear) {
/* 1130 */       addLineWithoutClip(a, b);
/* 1131 */       return;
/*      */     }
/* 1133 */     int cb = interpolateClipVertex(a, b);
/* 1134 */     addLineWithoutClip(a, cb);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected final void addLineWithoutClip(int a, int b)
/*      */   {
/* 1141 */     if (this.lineCount == this.lines.length) {
/* 1142 */       int[][] temp = new int[this.lineCount << 1][2];
/* 1143 */       System.arraycopy(this.lines, 0, temp, 0, this.lineCount);
/* 1144 */       this.lines = temp;
/*      */     }
/* 1146 */     this.lines[this.lineCount][0] = a;
/* 1147 */     this.lines[this.lineCount][1] = b;
/*      */     
/*      */ 
/*      */ 
/* 1151 */     this.lineCount += 1;
/*      */     
/*      */ 
/* 1154 */     this.pathLength[(this.pathCount - 1)] += 1;
/*      */   }
/*      */   
/*      */   protected void renderLines(int start, int stop)
/*      */   {
/* 1159 */     for (int i = start; i < stop; i++) {
/* 1160 */       renderLineVertices(this.vertices[this.lines[i][0]], 
/* 1161 */         this.vertices[this.lines[i][1]]);
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
/*      */   protected void renderLineVertices(float[] a, float[] b)
/*      */   {
/* 1199 */     if ((a[17] > 1.25F) || (a[17] < 0.75F)) {
/* 1200 */       float ox1 = a[18];
/* 1201 */       float oy1 = a[19];
/* 1202 */       float ox2 = b[18];
/* 1203 */       float oy2 = b[19];
/*      */       
/*      */ 
/* 1206 */       float weight = a[17] / 2.0F;
/*      */       
/*      */ 
/* 1209 */       if ((ox1 == ox2) && (oy1 == oy2)) {
/* 1210 */         oy1 -= weight;
/* 1211 */         oy2 += weight;
/*      */       }
/*      */       
/* 1214 */       float dX = ox2 - ox1 + 1.0E-4F;
/* 1215 */       float dY = oy2 - oy1 + 1.0E-4F;
/* 1216 */       float len = (float)Math.sqrt(dX * dX + dY * dY);
/*      */       
/* 1218 */       float rh = weight / len;
/*      */       
/* 1220 */       float dx0 = rh * dY;
/* 1221 */       float dy0 = rh * dX;
/* 1222 */       float dx1 = rh * dY;
/* 1223 */       float dy1 = rh * dX;
/*      */       
/* 1225 */       float ax1 = ox1 + dx0;
/* 1226 */       float ay1 = oy1 - dy0;
/*      */       
/* 1228 */       float ax2 = ox1 - dx0;
/* 1229 */       float ay2 = oy1 + dy0;
/*      */       
/* 1231 */       float bx1 = ox2 + dx1;
/* 1232 */       float by1 = oy2 - dy1;
/*      */       
/* 1234 */       float bx2 = ox2 - dx1;
/* 1235 */       float by2 = oy2 + dy1;
/*      */       
/* 1237 */       if (this.smooth) {
/* 1238 */         this.smoothTriangle.reset(3);
/* 1239 */         this.smoothTriangle.smooth = true;
/* 1240 */         this.smoothTriangle.interpARGB = true;
/*      */         
/*      */ 
/* 1243 */         this.smoothTriangle.setVertices(ax1, ay1, a[20], 
/* 1244 */           bx2, by2, b[20], 
/* 1245 */           ax2, ay2, a[20]);
/* 1246 */         this.smoothTriangle.setIntensities(a[13], a[14], a[15], a[16], 
/* 1247 */           b[13], b[14], b[15], b[16], 
/* 1248 */           a[13], a[14], a[15], a[16]);
/* 1249 */         this.smoothTriangle.render();
/*      */         
/*      */ 
/* 1252 */         this.smoothTriangle.setVertices(ax1, ay1, a[20], 
/* 1253 */           bx2, by2, b[20], 
/* 1254 */           bx1, by1, b[20]);
/* 1255 */         this.smoothTriangle.setIntensities(a[13], a[14], a[15], a[16], 
/* 1256 */           b[13], b[14], b[15], b[16], 
/* 1257 */           b[13], b[14], b[15], b[16]);
/* 1258 */         this.smoothTriangle.render();
/*      */       }
/*      */       else {
/* 1261 */         this.triangle.reset();
/*      */         
/*      */ 
/* 1264 */         this.triangle.setVertices(ax1, ay1, a[20], 
/* 1265 */           bx2, by2, b[20], 
/* 1266 */           ax2, ay2, a[20]);
/* 1267 */         this.triangle.setIntensities(a[13], a[14], a[15], a[16], 
/* 1268 */           b[13], b[14], b[15], b[16], 
/* 1269 */           a[13], a[14], a[15], a[16]);
/* 1270 */         this.triangle.render();
/*      */         
/*      */ 
/* 1273 */         this.triangle.setVertices(ax1, ay1, a[20], 
/* 1274 */           bx2, by2, b[20], 
/* 1275 */           bx1, by1, b[20]);
/* 1276 */         this.triangle.setIntensities(a[13], a[14], a[15], a[16], 
/* 1277 */           b[13], b[14], b[15], b[16], 
/* 1278 */           b[13], b[14], b[15], b[16]);
/* 1279 */         this.triangle.render();
/*      */       }
/*      */     }
/*      */     else {
/* 1283 */       this.line.reset();
/*      */       
/* 1285 */       this.line.setIntensities(a[13], a[14], a[15], a[16], 
/* 1286 */         b[13], b[14], b[15], b[16]);
/*      */       
/* 1288 */       this.line.setVertices(a[18], a[19], a[20], 
/* 1289 */         b[18], b[19], b[20]);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1307 */       this.line.draw();
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
/*      */   protected void rawLines(int start, int stop)
/*      */   {
/* 1326 */     this.raw.colorMode(1, 1.0F);
/* 1327 */     this.raw.noFill();
/* 1328 */     this.raw.beginShape(4);
/*      */     
/* 1330 */     for (int i = start; i < stop; i++) {
/* 1331 */       float[] a = this.vertices[this.lines[i][0]];
/* 1332 */       float[] b = this.vertices[this.lines[i][1]];
/* 1333 */       this.raw.strokeWeight(this.vertices[this.lines[i][1]][17]);
/*      */       
/* 1335 */       if (this.raw.is3D()) {
/* 1336 */         if ((a[24] != 0.0F) && (b[24] != 0.0F)) {
/* 1337 */           this.raw.stroke(a[13], a[14], a[15], a[16]);
/* 1338 */           this.raw.vertex(a[21] / a[24], a[22] / a[24], a[23] / a[24]);
/* 1339 */           this.raw.stroke(b[13], b[14], b[15], b[16]);
/* 1340 */           this.raw.vertex(b[21] / b[24], b[22] / b[24], b[23] / b[24]);
/*      */         }
/* 1342 */       } else if (this.raw.is2D()) {
/* 1343 */         this.raw.stroke(a[13], a[14], a[15], a[16]);
/* 1344 */         this.raw.vertex(a[18], a[19]);
/* 1345 */         this.raw.stroke(b[13], b[14], b[15], b[16]);
/* 1346 */         this.raw.vertex(b[18], b[19]);
/*      */       }
/*      */     }
/* 1349 */     this.raw.endShape();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void addTriangle(int a, int b, int c)
/*      */   {
/* 1360 */     addTriangleWithClip(a, b, c);
/*      */   }
/*      */   
/*      */   protected final void addTriangleWithClip(int a, int b, int c)
/*      */   {
/* 1365 */     boolean aClipped = false;
/* 1366 */     boolean bClipped = false;
/* 1367 */     int clippedCount = 0;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1372 */     this.cameraNear = -8.0F;
/* 1373 */     if (this.vertices[a][23] > this.cameraNear) {
/* 1374 */       aClipped = true;
/* 1375 */       clippedCount++;
/*      */     }
/* 1377 */     if (this.vertices[b][23] > this.cameraNear) {
/* 1378 */       bClipped = true;
/* 1379 */       clippedCount++;
/*      */     }
/* 1381 */     if (this.vertices[c][23] > this.cameraNear)
/*      */     {
/* 1383 */       clippedCount++;
/*      */     }
/* 1385 */     if (clippedCount == 0)
/*      */     {
/*      */ 
/*      */ 
/* 1389 */       addTriangleWithoutClip(a, b, c);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     }
/* 1395 */     else if (clippedCount != 3)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/* 1400 */       if (clippedCount == 2) { int cc;
/*      */         int ca;
/*      */         int cb;
/*      */         int cc;
/* 1404 */         if (!aClipped) {
/* 1405 */           int ca = a;
/* 1406 */           int cb = b;
/* 1407 */           cc = c;
/*      */         } else { int cc;
/* 1409 */           if (!bClipped) {
/* 1410 */             int ca = b;
/* 1411 */             int cb = a;
/* 1412 */             cc = c;
/*      */           }
/*      */           else {
/* 1415 */             ca = c;
/* 1416 */             cb = b;
/* 1417 */             cc = a;
/*      */           }
/*      */         }
/* 1420 */         int cd = interpolateClipVertex(ca, cb);
/* 1421 */         int ce = interpolateClipVertex(ca, cc);
/* 1422 */         addTriangleWithoutClip(ca, cd, ce);
/*      */       }
/*      */       else
/*      */       {
/*      */         int cc;
/*      */         
/*      */         int ca;
/*      */         
/*      */         int cb;
/*      */         int cc;
/* 1432 */         if (aClipped)
/*      */         {
/* 1434 */           int ca = c;
/* 1435 */           int cb = b;
/* 1436 */           cc = a;
/*      */         } else { int cc;
/* 1438 */           if (bClipped)
/*      */           {
/* 1440 */             int ca = a;
/* 1441 */             int cb = c;
/* 1442 */             cc = b;
/*      */           }
/*      */           else
/*      */           {
/* 1446 */             ca = a;
/* 1447 */             cb = b;
/* 1448 */             cc = c;
/*      */           }
/*      */         }
/* 1451 */         int cd = interpolateClipVertex(ca, cc);
/* 1452 */         int ce = interpolateClipVertex(cb, cc);
/* 1453 */         addTriangleWithoutClip(ca, cd, cb);
/*      */         
/*      */ 
/*      */ 
/* 1457 */         addTriangleWithoutClip(cb, cd, ce);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   protected final int interpolateClipVertex(int a, int b) {
/*      */     float[] vb;
/*      */     float[] va;
/*      */     float[] vb;
/* 1466 */     if (this.vertices[a][23] < this.vertices[b][23]) {
/* 1467 */       float[] va = this.vertices[b];
/* 1468 */       vb = this.vertices[a];
/*      */     }
/*      */     else {
/* 1471 */       va = this.vertices[a];
/* 1472 */       vb = this.vertices[b];
/*      */     }
/* 1474 */     float az = va[23];
/* 1475 */     float bz = vb[23];
/*      */     
/* 1477 */     float dz = az - bz;
/*      */     
/* 1479 */     if (dz == 0.0F) {
/* 1480 */       return a;
/*      */     }
/*      */     
/*      */ 
/* 1484 */     float pa = (this.cameraNear - bz) / dz;
/* 1485 */     float pb = 1.0F - pa;
/*      */     
/* 1487 */     vertex(pa * va[0] + pb * vb[0], 
/* 1488 */       pa * va[1] + pb * vb[1], 
/* 1489 */       pa * va[2] + pb * vb[2]);
/* 1490 */     int irv = this.vertexCount - 1;
/* 1491 */     this.shapeLastPlusClipped += 1;
/*      */     
/* 1493 */     float[] rv = this.vertices[irv];
/*      */     
/* 1495 */     rv[18] = (pa * va[18] + pb * vb[18]);
/* 1496 */     rv[19] = (pa * va[19] + pb * vb[19]);
/* 1497 */     rv[20] = (pa * va[20] + pb * vb[20]);
/*      */     
/* 1499 */     rv[21] = (pa * va[21] + pb * vb[21]);
/* 1500 */     rv[22] = (pa * va[22] + pb * vb[22]);
/* 1501 */     rv[23] = (pa * va[23] + pb * vb[23]);
/* 1502 */     rv[24] = (pa * va[24] + pb * vb[24]);
/*      */     
/* 1504 */     rv[3] = (pa * va[3] + pb * vb[3]);
/* 1505 */     rv[4] = (pa * va[4] + pb * vb[4]);
/* 1506 */     rv[5] = (pa * va[5] + pb * vb[5]);
/* 1507 */     rv[6] = (pa * va[6] + pb * vb[6]);
/*      */     
/* 1509 */     rv[7] = (pa * va[7] + pb * vb[7]);
/* 1510 */     rv[8] = (pa * va[8] + pb * vb[8]);
/*      */     
/* 1512 */     rv[13] = (pa * va[13] + pb * vb[13]);
/* 1513 */     rv[14] = (pa * va[14] + pb * vb[14]);
/* 1514 */     rv[15] = (pa * va[15] + pb * vb[15]);
/* 1515 */     rv[16] = (pa * va[16] + pb * vb[16]);
/*      */     
/* 1517 */     rv[9] = (pa * va[9] + pb * vb[9]);
/* 1518 */     rv[10] = (pa * va[10] + pb * vb[10]);
/* 1519 */     rv[11] = (pa * va[11] + pb * vb[11]);
/*      */     
/*      */ 
/*      */ 
/* 1523 */     rv[25] = (pa * va[25] + pb * vb[25]);
/* 1524 */     rv[26] = (pa * va[26] + pb * vb[26]);
/* 1525 */     rv[27] = (pa * va[27] + pb * vb[27]);
/*      */     
/* 1527 */     rv[28] = (pa * va[28] + pb * vb[28]);
/* 1528 */     rv[29] = (pa * va[29] + pb * vb[29]);
/* 1529 */     rv[30] = (pa * va[30] + pb * vb[30]);
/*      */     
/*      */ 
/* 1532 */     rv[32] = (pa * va[32] + pb * vb[32]);
/* 1533 */     rv[33] = (pa * va[33] + pb * vb[33]);
/* 1534 */     rv[34] = (pa * va[34] + pb * vb[34]);
/*      */     
/* 1536 */     rv[31] = (pa * va[31] + pb * vb[31]);
/*      */     
/* 1538 */     rv[35] = 0.0F;
/*      */     
/* 1540 */     return irv;
/*      */   }
/*      */   
/*      */   protected final void addTriangleWithoutClip(int a, int b, int c)
/*      */   {
/* 1545 */     if (this.triangleCount == this.triangles.length) {
/* 1546 */       int[][] temp = new int[this.triangleCount << 1][4];
/* 1547 */       System.arraycopy(this.triangles, 0, temp, 0, this.triangleCount);
/* 1548 */       this.triangles = temp;
/*      */       
/* 1550 */       float[][][] ftemp = new float[this.triangleCount << 1][3][7];
/* 1551 */       System.arraycopy(this.triangleColors, 0, ftemp, 0, this.triangleCount);
/* 1552 */       this.triangleColors = ftemp;
/*      */     }
/* 1554 */     this.triangles[this.triangleCount][0] = a;
/* 1555 */     this.triangles[this.triangleCount][1] = b;
/* 1556 */     this.triangles[this.triangleCount][2] = c;
/*      */     
/* 1558 */     if (this.textureImage == null) {
/* 1559 */       this.triangles[this.triangleCount][3] = -1;
/*      */     } else {
/* 1561 */       this.triangles[this.triangleCount][3] = this.textureIndex;
/*      */     }
/*      */     
/*      */ 
/* 1565 */     this.triangleCount += 1;
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
/*      */   protected void addPolygonTriangles()
/*      */   {
/* 1578 */     if (this.vertexOrder.length != this.vertices.length) {
/* 1579 */       int[] temp = new int[this.vertices.length];
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1584 */       PApplet.arrayCopy(this.vertexOrder, temp, this.vertexOrder.length);
/* 1585 */       this.vertexOrder = temp;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1594 */     int d1 = 0;
/* 1595 */     int d2 = 1;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1618 */     float area = 0.0F;
/* 1619 */     int p = this.shapeLast - 1; for (int q = this.shapeFirst; q < this.shapeLast; p = q++)
/*      */     {
/* 1621 */       area = area + (this.vertices[q][d1] * this.vertices[p][d2] - this.vertices[p][d1] * this.vertices[q][d2]);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1626 */     if (area == 0.0F)
/*      */     {
/* 1628 */       boolean foundValidX = false;
/* 1629 */       boolean foundValidY = false;
/*      */       
/* 1631 */       for (int i = this.shapeFirst; i < this.shapeLast; i++) {
/* 1632 */         for (int j = i; j < this.shapeLast; j++) {
/* 1633 */           if (this.vertices[i][0] != this.vertices[j][0]) foundValidX = true;
/* 1634 */           if (this.vertices[i][1] != this.vertices[j][1]) { foundValidY = true;
/*      */           }
/*      */         }
/*      */       }
/* 1638 */       if (foundValidX)
/*      */       {
/* 1640 */         d2 = 2;
/* 1641 */       } else if (foundValidY)
/*      */       {
/* 1643 */         d1 = 1;
/* 1644 */         d2 = 2;
/*      */       }
/*      */       else {
/* 1647 */         return;
/*      */       }
/*      */       
/*      */ 
/* 1651 */       int p = this.shapeLast - 1; for (int q = this.shapeFirst; q < this.shapeLast; p = q++)
/*      */       {
/* 1653 */         area = area + (this.vertices[q][d1] * this.vertices[p][d2] - this.vertices[p][d1] * this.vertices[q][d2]);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1660 */     float[] vfirst = this.vertices[this.shapeFirst];
/* 1661 */     float[] vlast = this.vertices[(this.shapeLast - 1)];
/* 1662 */     if ((abs(vfirst[0] - vlast[0]) < 1.0E-4F) && 
/* 1663 */       (abs(vfirst[1] - vlast[1]) < 1.0E-4F) && 
/* 1664 */       (abs(vfirst[2] - vlast[2]) < 1.0E-4F)) {
/* 1665 */       this.shapeLast -= 1;
/*      */     }
/*      */     
/*      */ 
/* 1669 */     int j = 0;
/* 1670 */     if (area > 0.0F) {
/* 1671 */       for (int i = this.shapeFirst; i < this.shapeLast; i++) {
/* 1672 */         j = i - this.shapeFirst;
/* 1673 */         this.vertexOrder[j] = i;
/*      */       }
/*      */     } else {
/* 1676 */       for (int i = this.shapeFirst; i < this.shapeLast; i++) {
/* 1677 */         j = i - this.shapeFirst;
/* 1678 */         this.vertexOrder[j] = (this.shapeLast - 1 - j);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1683 */     int vc = this.shapeLast - this.shapeFirst;
/* 1684 */     int count = 2 * vc;
/*      */     
/* 1686 */     int m = 0; for (int v = vc - 1; vc > 2;) {
/* 1687 */       boolean snip = true;
/*      */       
/*      */ 
/* 1690 */       if (count-- <= 0) {
/*      */         break;
/*      */       }
/*      */       
/*      */ 
/* 1695 */       int u = v; if (vc <= u) u = 0;
/* 1696 */       v = u + 1; if (vc <= v) v = 0;
/* 1697 */       int w = v + 1; if (vc <= w) { w = 0;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1707 */       double Ax = -10.0F * this.vertices[this.vertexOrder[u]][d1];
/* 1708 */       double Ay = 10.0F * this.vertices[this.vertexOrder[u]][d2];
/* 1709 */       double Bx = -10.0F * this.vertices[this.vertexOrder[v]][d1];
/* 1710 */       double By = 10.0F * this.vertices[this.vertexOrder[v]][d2];
/* 1711 */       double Cx = -10.0F * this.vertices[this.vertexOrder[w]][d1];
/* 1712 */       double Cy = 10.0F * this.vertices[this.vertexOrder[w]][d2];
/*      */       
/*      */ 
/* 1715 */       if (9.999999747378752E-5D <= (Bx - Ax) * (Cy - Ay) - (By - Ay) * (Cx - Ax))
/*      */       {
/*      */ 
/*      */ 
/* 1719 */         for (int p = 0; p < vc; p++) {
/* 1720 */           if ((p != u) && (p != v) && (p != w))
/*      */           {
/*      */ 
/*      */ 
/* 1724 */             double Px = -10.0F * this.vertices[this.vertexOrder[p]][d1];
/* 1725 */             double Py = 10.0F * this.vertices[this.vertexOrder[p]][d2];
/*      */             
/* 1727 */             double ax = Cx - Bx;double ay = Cy - By;
/* 1728 */             double bx = Ax - Cx;double by = Ay - Cy;
/* 1729 */             double cx = Bx - Ax;double cy = By - Ay;
/* 1730 */             double apx = Px - Ax;double apy = Py - Ay;
/* 1731 */             double bpx = Px - Bx;double bpy = Py - By;
/* 1732 */             double cpx = Px - Cx;double cpy = Py - Cy;
/*      */             
/* 1734 */             double aCROSSbp = ax * bpy - ay * bpx;
/* 1735 */             double cCROSSap = cx * apy - cy * apx;
/* 1736 */             double bCROSScp = bx * cpy - by * cpx;
/*      */             
/* 1738 */             if ((aCROSSbp >= 0.0D) && (bCROSScp >= 0.0D) && (cCROSSap >= 0.0D)) {
/* 1739 */               snip = false;
/*      */             }
/*      */           }
/*      */         }
/* 1743 */         if (snip) {
/* 1744 */           addTriangle(this.vertexOrder[u], this.vertexOrder[v], this.vertexOrder[w]);
/*      */           
/* 1746 */           m++;
/*      */           
/*      */ 
/* 1749 */           int s = v; for (int t = v + 1; t < vc; t++) {
/* 1750 */             this.vertexOrder[s] = this.vertexOrder[t];s++;
/*      */           }
/* 1752 */           vc--;
/*      */           
/*      */ 
/* 1755 */           count = 2 * vc;
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void toWorldNormal(float nx, float ny, float nz, float[] out) {
/* 1762 */     out[0] = 
/* 1763 */       (this.modelviewInv.m00 * nx + this.modelviewInv.m10 * ny + 
/* 1764 */       this.modelviewInv.m20 * nz + this.modelviewInv.m30);
/* 1765 */     out[1] = 
/* 1766 */       (this.modelviewInv.m01 * nx + this.modelviewInv.m11 * ny + 
/* 1767 */       this.modelviewInv.m21 * nz + this.modelviewInv.m31);
/* 1768 */     out[2] = 
/* 1769 */       (this.modelviewInv.m02 * nx + this.modelviewInv.m12 * ny + 
/* 1770 */       this.modelviewInv.m22 * nz + this.modelviewInv.m32);
/* 1771 */     out[3] = 
/* 1772 */       (this.modelviewInv.m03 * nx + this.modelviewInv.m13 * ny + 
/* 1773 */       this.modelviewInv.m23 * nz + this.modelviewInv.m33);
/*      */     
/* 1775 */     if ((out[3] != 0.0F) && (out[3] != 1.0F))
/*      */     {
/* 1777 */       out[0] /= out[3];out[1] /= out[3];out[2] /= out[3];
/*      */     }
/* 1779 */     out[3] = 1.0F;
/*      */     
/* 1781 */     float nlen = mag(out[0], out[1], out[2]);
/* 1782 */     if ((nlen != 0.0F) && (nlen != 1.0F)) {
/* 1783 */       out[0] /= nlen;out[1] /= nlen;out[2] /= nlen;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 1790 */   float[] worldNormal = new float[4];
/*      */   
/*      */ 
/*      */   private void calcLightingContribution(int vIndex, float[] contribution)
/*      */   {
/* 1795 */     calcLightingContribution(vIndex, contribution, false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void calcLightingContribution(int vIndex, float[] contribution, boolean normalIsWorld)
/*      */   {
/* 1802 */     float[] v = this.vertices[vIndex];
/*      */     
/* 1804 */     float sr = v[28];
/* 1805 */     float sg = v[29];
/* 1806 */     float sb = v[30];
/*      */     
/* 1808 */     float wx = v[21];
/* 1809 */     float wy = v[22];
/* 1810 */     float wz = v[23];
/* 1811 */     float shine = v[31];
/*      */     
/* 1813 */     float nx = v[9];
/* 1814 */     float ny = v[10];
/* 1815 */     float nz = v[11];
/*      */     
/* 1817 */     if (!normalIsWorld)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1831 */       toWorldNormal(v[9], v[10], v[11], this.worldNormal);
/* 1832 */       nx = this.worldNormal[0];
/* 1833 */       ny = this.worldNormal[1];
/* 1834 */       nz = this.worldNormal[2];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1858 */       nx = v[9];
/* 1859 */       ny = v[10];
/* 1860 */       nz = v[11];
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1866 */     float dir = dot(nx, ny, nz, -wx, -wy, -wz);
/*      */     
/*      */ 
/*      */ 
/* 1870 */     if (dir < 0.0F) {
/* 1871 */       nx = -nx;
/* 1872 */       ny = -ny;
/* 1873 */       nz = -nz;
/*      */     }
/*      */     
/*      */ 
/* 1877 */     contribution[0] = 0.0F;
/* 1878 */     contribution[1] = 0.0F;
/* 1879 */     contribution[2] = 0.0F;
/*      */     
/* 1881 */     contribution[3] = 0.0F;
/* 1882 */     contribution[4] = 0.0F;
/* 1883 */     contribution[5] = 0.0F;
/*      */     
/* 1885 */     contribution[6] = 0.0F;
/* 1886 */     contribution[7] = 0.0F;
/* 1887 */     contribution[8] = 0.0F;
/*      */     
/*      */ 
/*      */ 
/* 1891 */     for (int i = 0; i < this.lightCount; i++)
/*      */     {
/* 1893 */       float denom = this.lightFalloffConstant[i];
/* 1894 */       float spotTerm = 1.0F;
/*      */       
/* 1896 */       if (this.lightType[i] == 0) {
/* 1897 */         if ((this.lightFalloffQuadratic[i] != 0.0F) || (this.lightFalloffLinear[i] != 0.0F))
/*      */         {
/* 1899 */           float distSq = mag(this.lightPosition[i].x - wx, 
/* 1900 */             this.lightPosition[i].y - wy, 
/* 1901 */             this.lightPosition[i].z - wz);
/*      */           
/* 1903 */           denom = denom + (this.lightFalloffQuadratic[i] * distSq + 
/* 1904 */             this.lightFalloffLinear[i] * sqrt(distSq));
/*      */         }
/* 1906 */         if (denom == 0.0F) { denom = 1.0F;
/*      */         }
/* 1908 */         contribution[0] += this.lightDiffuse[i][0] / denom;
/* 1909 */         contribution[1] += this.lightDiffuse[i][1] / denom;
/* 1910 */         contribution[2] += this.lightDiffuse[i][2] / denom;
/*      */ 
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*      */ 
/* 1917 */         float lightDir_dot_li = 0.0F;
/* 1918 */         float n_dot_li = 0.0F;
/*      */         float lix;
/* 1920 */         float liy; float liz; if (this.lightType[i] == 1) {
/* 1921 */           float lix = -this.lightNormal[i].x;
/* 1922 */           float liy = -this.lightNormal[i].y;
/* 1923 */           float liz = -this.lightNormal[i].z;
/* 1924 */           denom = 1.0F;
/* 1925 */           n_dot_li = nx * lix + ny * liy + nz * liz;
/*      */           
/* 1927 */           if (n_dot_li <= 0.0F) {
/*      */             continue;
/*      */           }
/*      */         } else {
/* 1931 */           lix = this.lightPosition[i].x - wx;
/* 1932 */           liy = this.lightPosition[i].y - wy;
/* 1933 */           liz = this.lightPosition[i].z - wz;
/*      */           
/* 1935 */           float distSq = mag(lix, liy, liz);
/* 1936 */           if (distSq != 0.0F) {
/* 1937 */             lix /= distSq;
/* 1938 */             liy /= distSq;
/* 1939 */             liz /= distSq;
/*      */           }
/* 1941 */           n_dot_li = nx * lix + ny * liy + nz * liz;
/*      */           
/* 1943 */           if (n_dot_li <= 0.0F) {
/*      */             continue;
/*      */           }
/*      */           
/* 1947 */           if (this.lightType[i] == 3) {
/* 1948 */             lightDir_dot_li = 
/* 1949 */               -(this.lightNormal[i].x * lix + 
/* 1950 */               this.lightNormal[i].y * liy + 
/* 1951 */               this.lightNormal[i].z * liz);
/*      */             
/* 1953 */             if (lightDir_dot_li <= this.lightSpotAngleCos[i]) {
/*      */               continue;
/*      */             }
/* 1956 */             spotTerm = (float)Math.pow(lightDir_dot_li, this.lightSpotConcentration[i]);
/*      */           }
/*      */           
/* 1959 */           if ((this.lightFalloffQuadratic[i] != 0.0F) || (this.lightFalloffLinear[i] != 0.0F))
/*      */           {
/*      */ 
/* 1962 */             denom = denom + (this.lightFalloffQuadratic[i] * distSq + 
/* 1963 */               this.lightFalloffLinear[i] * sqrt(distSq));
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 1970 */         if (denom == 0.0F)
/* 1971 */           denom = 1.0F;
/* 1972 */         float mul = n_dot_li * spotTerm / denom;
/* 1973 */         contribution[3] += this.lightDiffuse[i][0] * mul;
/* 1974 */         contribution[4] += this.lightDiffuse[i][1] * mul;
/* 1975 */         contribution[5] += this.lightDiffuse[i][2] * mul;
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 1980 */         if (((sr > 0.0F) || (sg > 0.0F) || (sb > 0.0F)) && (
/* 1981 */           (this.lightSpecular[i][0] > 0.0F) || 
/* 1982 */           (this.lightSpecular[i][1] > 0.0F) || 
/* 1983 */           (this.lightSpecular[i][2] > 0.0F)))
/*      */         {
/* 1985 */           float vmag = mag(wx, wy, wz);
/* 1986 */           if (vmag != 0.0F) {
/* 1987 */             wx /= vmag;
/* 1988 */             wy /= vmag;
/* 1989 */             wz /= vmag;
/*      */           }
/* 1991 */           float sx = lix - wx;
/* 1992 */           float sy = liy - wy;
/* 1993 */           float sz = liz - wz;
/* 1994 */           vmag = mag(sx, sy, sz);
/* 1995 */           if (vmag != 0.0F) {
/* 1996 */             sx /= vmag;
/* 1997 */             sy /= vmag;
/* 1998 */             sz /= vmag;
/*      */           }
/* 2000 */           float s_dot_n = sx * nx + sy * ny + sz * nz;
/*      */           
/* 2002 */           if (s_dot_n > 0.0F) {
/* 2003 */             s_dot_n = (float)Math.pow(s_dot_n, shine);
/* 2004 */             mul = s_dot_n * spotTerm / denom;
/* 2005 */             contribution[6] += this.lightSpecular[i][0] * mul;
/* 2006 */             contribution[7] += this.lightSpecular[i][1] * mul;
/* 2007 */             contribution[8] += this.lightSpecular[i][2] * mul;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void applyLightingContribution(int vIndex, float[] contribution)
/*      */   {
/* 2021 */     float[] v = this.vertices[vIndex];
/*      */     
/* 2023 */     v[3] = clamp(v[32] + v[25] * contribution[0] + v[3] * contribution[3]);
/* 2024 */     v[4] = clamp(v[33] + v[26] * contribution[1] + v[4] * contribution[4]);
/* 2025 */     v[5] = clamp(v[34] + v[27] * contribution[2] + v[5] * contribution[5]);
/* 2026 */     v[6] = clamp(v[6]);
/*      */     
/* 2028 */     v[28] = clamp(v[28] * contribution[6]);
/* 2029 */     v[29] = clamp(v[29] * contribution[7]);
/* 2030 */     v[30] = clamp(v[30] * contribution[8]);
/*      */     
/*      */ 
/* 2033 */     v[35] = 1.0F;
/*      */   }
/*      */   
/*      */   private void lightVertex(int vIndex, float[] contribution)
/*      */   {
/* 2038 */     calcLightingContribution(vIndex, contribution);
/* 2039 */     applyLightingContribution(vIndex, contribution);
/*      */   }
/*      */   
/*      */   private void lightUnlitVertex(int vIndex, float[] contribution)
/*      */   {
/* 2044 */     if (this.vertices[vIndex][35] == 0.0F) {
/* 2045 */       lightVertex(vIndex, contribution);
/*      */     }
/*      */   }
/*      */   
/*      */   private void copyPrelitVertexColor(int triIndex, int index, int colorIndex)
/*      */   {
/* 2051 */     float[] triColor = this.triangleColors[triIndex][colorIndex];
/* 2052 */     float[] v = this.vertices[index];
/*      */     
/* 2054 */     triColor[0] = v[3];
/* 2055 */     triColor[1] = v[4];
/* 2056 */     triColor[2] = v[5];
/* 2057 */     triColor[3] = v[6];
/* 2058 */     triColor[4] = v[28];
/* 2059 */     triColor[5] = v[29];
/* 2060 */     triColor[6] = v[30];
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void copyVertexColor(int triIndex, int index, int colorIndex, float[] contrib)
/*      */   {
/* 2067 */     float[] triColor = this.triangleColors[triIndex][colorIndex];
/* 2068 */     float[] v = this.vertices[index];
/*      */     
/* 2070 */     triColor[0] = 
/* 2071 */       clamp(v[32] + v[25] * contrib[0] + v[3] * contrib[3]);
/* 2072 */     triColor[1] = 
/* 2073 */       clamp(v[33] + v[26] * contrib[1] + v[4] * contrib[4]);
/* 2074 */     triColor[2] = 
/* 2075 */       clamp(v[34] + v[27] * contrib[2] + v[5] * contrib[5]);
/* 2076 */     triColor[3] = clamp(v[6]);
/*      */     
/* 2078 */     triColor[4] = clamp(v[28] * contrib[6]);
/* 2079 */     triColor[5] = clamp(v[29] * contrib[7]);
/* 2080 */     triColor[6] = clamp(v[30] * contrib[8]);
/*      */   }
/*      */   
/*      */   private void lightTriangle(int triIndex, float[] lightContribution)
/*      */   {
/* 2085 */     int vIndex = this.triangles[triIndex][0];
/* 2086 */     copyVertexColor(triIndex, vIndex, 0, lightContribution);
/* 2087 */     vIndex = this.triangles[triIndex][1];
/* 2088 */     copyVertexColor(triIndex, vIndex, 1, lightContribution);
/* 2089 */     vIndex = this.triangles[triIndex][2];
/* 2090 */     copyVertexColor(triIndex, vIndex, 2, lightContribution);
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
/*      */   private void lightTriangle(int triIndex)
/*      */   {
/* 2116 */     if (this.normalMode == 2) {
/* 2117 */       int vIndex = this.triangles[triIndex][0];
/* 2118 */       lightUnlitVertex(vIndex, this.tempLightingContribution);
/* 2119 */       copyPrelitVertexColor(triIndex, vIndex, 0);
/*      */       
/* 2121 */       vIndex = this.triangles[triIndex][1];
/* 2122 */       lightUnlitVertex(vIndex, this.tempLightingContribution);
/* 2123 */       copyPrelitVertexColor(triIndex, vIndex, 1);
/*      */       
/* 2125 */       vIndex = this.triangles[triIndex][2];
/* 2126 */       lightUnlitVertex(vIndex, this.tempLightingContribution);
/* 2127 */       copyPrelitVertexColor(triIndex, vIndex, 2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     }
/* 2136 */     else if (!this.lightingDependsOnVertexPosition) {
/* 2137 */       int vIndex = this.triangles[triIndex][0];
/* 2138 */       int vIndex2 = this.triangles[triIndex][1];
/* 2139 */       int vIndex3 = this.triangles[triIndex][2];
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2153 */       cross(this.vertices[vIndex2][21] - this.vertices[vIndex][21], 
/* 2154 */         this.vertices[vIndex2][22] - this.vertices[vIndex][22], 
/* 2155 */         this.vertices[vIndex2][23] - this.vertices[vIndex][23], 
/* 2156 */         this.vertices[vIndex3][21] - this.vertices[vIndex][21], 
/* 2157 */         this.vertices[vIndex3][22] - this.vertices[vIndex][22], 
/* 2158 */         this.vertices[vIndex3][23] - this.vertices[vIndex][23], this.lightTriangleNorm);
/*      */       
/* 2160 */       this.lightTriangleNorm.normalize();
/* 2161 */       this.vertices[vIndex][9] = this.lightTriangleNorm.x;
/* 2162 */       this.vertices[vIndex][10] = this.lightTriangleNorm.y;
/* 2163 */       this.vertices[vIndex][11] = this.lightTriangleNorm.z;
/*      */       
/*      */ 
/* 2166 */       calcLightingContribution(vIndex, this.tempLightingContribution, true);
/* 2167 */       copyVertexColor(triIndex, vIndex, 0, this.tempLightingContribution);
/* 2168 */       copyVertexColor(triIndex, vIndex2, 1, this.tempLightingContribution);
/* 2169 */       copyVertexColor(triIndex, vIndex3, 2, this.tempLightingContribution);
/*      */ 
/*      */ 
/*      */ 
/*      */     }
/* 2174 */     else if (this.normalMode == 1) {
/* 2175 */       int vIndex = this.triangles[triIndex][0];
/* 2176 */       this.vertices[vIndex][9] = this.vertices[this.shapeFirst][9];
/* 2177 */       this.vertices[vIndex][10] = this.vertices[this.shapeFirst][10];
/* 2178 */       this.vertices[vIndex][11] = this.vertices[this.shapeFirst][11];
/* 2179 */       calcLightingContribution(vIndex, this.tempLightingContribution);
/* 2180 */       copyVertexColor(triIndex, vIndex, 0, this.tempLightingContribution);
/*      */       
/* 2182 */       vIndex = this.triangles[triIndex][1];
/* 2183 */       this.vertices[vIndex][9] = this.vertices[this.shapeFirst][9];
/* 2184 */       this.vertices[vIndex][10] = this.vertices[this.shapeFirst][10];
/* 2185 */       this.vertices[vIndex][11] = this.vertices[this.shapeFirst][11];
/* 2186 */       calcLightingContribution(vIndex, this.tempLightingContribution);
/* 2187 */       copyVertexColor(triIndex, vIndex, 1, this.tempLightingContribution);
/*      */       
/* 2189 */       vIndex = this.triangles[triIndex][2];
/* 2190 */       this.vertices[vIndex][9] = this.vertices[this.shapeFirst][9];
/* 2191 */       this.vertices[vIndex][10] = this.vertices[this.shapeFirst][10];
/* 2192 */       this.vertices[vIndex][11] = this.vertices[this.shapeFirst][11];
/* 2193 */       calcLightingContribution(vIndex, this.tempLightingContribution);
/* 2194 */       copyVertexColor(triIndex, vIndex, 2, this.tempLightingContribution);
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 2199 */       int vIndex = this.triangles[triIndex][0];
/* 2200 */       int vIndex2 = this.triangles[triIndex][1];
/* 2201 */       int vIndex3 = this.triangles[triIndex][2];
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2215 */       cross(this.vertices[vIndex2][21] - this.vertices[vIndex][21], 
/* 2216 */         this.vertices[vIndex2][22] - this.vertices[vIndex][22], 
/* 2217 */         this.vertices[vIndex2][23] - this.vertices[vIndex][23], 
/* 2218 */         this.vertices[vIndex3][21] - this.vertices[vIndex][21], 
/* 2219 */         this.vertices[vIndex3][22] - this.vertices[vIndex][22], 
/* 2220 */         this.vertices[vIndex3][23] - this.vertices[vIndex][23], this.lightTriangleNorm);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 2225 */       this.lightTriangleNorm.normalize();
/* 2226 */       this.vertices[vIndex][9] = this.lightTriangleNorm.x;
/* 2227 */       this.vertices[vIndex][10] = this.lightTriangleNorm.y;
/* 2228 */       this.vertices[vIndex][11] = this.lightTriangleNorm.z;
/*      */       
/* 2230 */       calcLightingContribution(vIndex, this.tempLightingContribution, true);
/* 2231 */       copyVertexColor(triIndex, vIndex, 0, this.tempLightingContribution);
/*      */       
/* 2233 */       this.vertices[vIndex2][9] = this.lightTriangleNorm.x;
/* 2234 */       this.vertices[vIndex2][10] = this.lightTriangleNorm.y;
/* 2235 */       this.vertices[vIndex2][11] = this.lightTriangleNorm.z;
/*      */       
/* 2237 */       calcLightingContribution(vIndex2, this.tempLightingContribution, true);
/* 2238 */       copyVertexColor(triIndex, vIndex2, 1, this.tempLightingContribution);
/*      */       
/* 2240 */       this.vertices[vIndex3][9] = this.lightTriangleNorm.x;
/* 2241 */       this.vertices[vIndex3][10] = this.lightTriangleNorm.y;
/* 2242 */       this.vertices[vIndex3][11] = this.lightTriangleNorm.z;
/*      */       
/* 2244 */       calcLightingContribution(vIndex3, this.tempLightingContribution, true);
/* 2245 */       copyVertexColor(triIndex, vIndex3, 2, this.tempLightingContribution);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   protected void renderTriangles(int start, int stop)
/*      */   {
/* 2252 */     for (int i = start; i < stop; i++) {
/* 2253 */       float[] a = this.vertices[this.triangles[i][0]];
/* 2254 */       float[] b = this.vertices[this.triangles[i][1]];
/* 2255 */       float[] c = this.vertices[this.triangles[i][2]];
/* 2256 */       int tex = this.triangles[i][3];
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2281 */       this.triangle.reset();
/*      */       
/*      */ 
/*      */ 
/* 2285 */       float ar = clamp(this.triangleColors[i][0][0] + this.triangleColors[i][0][4]);
/* 2286 */       float ag = clamp(this.triangleColors[i][0][1] + this.triangleColors[i][0][5]);
/* 2287 */       float ab = clamp(this.triangleColors[i][0][2] + this.triangleColors[i][0][6]);
/* 2288 */       float br = clamp(this.triangleColors[i][1][0] + this.triangleColors[i][1][4]);
/* 2289 */       float bg = clamp(this.triangleColors[i][1][1] + this.triangleColors[i][1][5]);
/* 2290 */       float bb = clamp(this.triangleColors[i][1][2] + this.triangleColors[i][1][6]);
/* 2291 */       float cr = clamp(this.triangleColors[i][2][0] + this.triangleColors[i][2][4]);
/* 2292 */       float cg = clamp(this.triangleColors[i][2][1] + this.triangleColors[i][2][5]);
/* 2293 */       float cb = clamp(this.triangleColors[i][2][2] + this.triangleColors[i][2][6]);
/*      */       
/*      */ 
/* 2296 */       boolean failedToPrecalc = false;
/* 2297 */       if ((s_enableAccurateTextures) && (this.frustumMode)) {
/* 2298 */         boolean textured = true;
/* 2299 */         this.smoothTriangle.reset(3);
/* 2300 */         this.smoothTriangle.smooth = true;
/* 2301 */         this.smoothTriangle.interpARGB = true;
/* 2302 */         this.smoothTriangle.setIntensities(ar, ag, ab, a[6], 
/* 2303 */           br, bg, bb, b[6], 
/* 2304 */           cr, cg, cb, c[6]);
/* 2305 */         if ((tex > -1) && (this.textures[tex] != null)) {
/* 2306 */           this.smoothTriangle.setCamVertices(a[21], a[22], a[23], 
/* 2307 */             b[21], b[22], b[23], 
/* 2308 */             c[21], c[22], c[23]);
/* 2309 */           this.smoothTriangle.interpUV = true;
/* 2310 */           this.smoothTriangle.texture(this.textures[tex]);
/* 2311 */           float umult = this.textures[tex].width;
/* 2312 */           float vmult = this.textures[tex].height;
/* 2313 */           this.smoothTriangle.vertices[0][7] = (a[7] * umult);
/* 2314 */           this.smoothTriangle.vertices[0][8] = (a[8] * vmult);
/* 2315 */           this.smoothTriangle.vertices[1][7] = (b[7] * umult);
/* 2316 */           this.smoothTriangle.vertices[1][8] = (b[8] * vmult);
/* 2317 */           this.smoothTriangle.vertices[2][7] = (c[7] * umult);
/* 2318 */           this.smoothTriangle.vertices[2][8] = (c[8] * vmult);
/*      */         } else {
/* 2320 */           this.smoothTriangle.interpUV = false;
/* 2321 */           textured = false;
/*      */         }
/*      */         
/* 2324 */         this.smoothTriangle.setVertices(a[18], a[19], a[20], 
/* 2325 */           b[18], b[19], b[20], 
/* 2326 */           c[18], c[19], c[20]);
/*      */         
/*      */ 
/* 2329 */         if ((!textured) || (this.smoothTriangle.precomputeAccurateTexturing())) {
/* 2330 */           this.smoothTriangle.render();
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 2335 */           failedToPrecalc = true;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2343 */       if ((!s_enableAccurateTextures) || (failedToPrecalc) || (!this.frustumMode)) {
/* 2344 */         if ((tex > -1) && (this.textures[tex] != null)) {
/* 2345 */           this.triangle.setTexture(this.textures[tex]);
/* 2346 */           this.triangle.setUV(a[7], a[8], b[7], b[8], c[7], c[8]);
/*      */         }
/*      */         
/* 2349 */         this.triangle.setIntensities(ar, ag, ab, a[6], 
/* 2350 */           br, bg, bb, b[6], 
/* 2351 */           cr, cg, cb, c[6]);
/*      */         
/* 2353 */         this.triangle.setVertices(a[18], a[19], a[20], 
/* 2354 */           b[18], b[19], b[20], 
/* 2355 */           c[18], c[19], c[20]);
/*      */         
/* 2357 */         this.triangle.render();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void rawTriangles(int start, int stop)
/*      */   {
/* 2382 */     this.raw.colorMode(1, 1.0F);
/* 2383 */     this.raw.noStroke();
/* 2384 */     this.raw.beginShape(9);
/*      */     
/* 2386 */     for (int i = start; i < stop; i++) {
/* 2387 */       float[] a = this.vertices[this.triangles[i][0]];
/* 2388 */       float[] b = this.vertices[this.triangles[i][1]];
/* 2389 */       float[] c = this.vertices[this.triangles[i][2]];
/*      */       
/* 2391 */       float ar = clamp(this.triangleColors[i][0][0] + this.triangleColors[i][0][4]);
/* 2392 */       float ag = clamp(this.triangleColors[i][0][1] + this.triangleColors[i][0][5]);
/* 2393 */       float ab = clamp(this.triangleColors[i][0][2] + this.triangleColors[i][0][6]);
/* 2394 */       float br = clamp(this.triangleColors[i][1][0] + this.triangleColors[i][1][4]);
/* 2395 */       float bg = clamp(this.triangleColors[i][1][1] + this.triangleColors[i][1][5]);
/* 2396 */       float bb = clamp(this.triangleColors[i][1][2] + this.triangleColors[i][1][6]);
/* 2397 */       float cr = clamp(this.triangleColors[i][2][0] + this.triangleColors[i][2][4]);
/* 2398 */       float cg = clamp(this.triangleColors[i][2][1] + this.triangleColors[i][2][5]);
/* 2399 */       float cb = clamp(this.triangleColors[i][2][2] + this.triangleColors[i][2][6]);
/*      */       
/* 2401 */       int tex = this.triangles[i][3];
/* 2402 */       PImage texImage = tex > -1 ? this.textures[tex] : null;
/* 2403 */       if (texImage != null) {
/* 2404 */         if (this.raw.is3D()) {
/* 2405 */           if ((a[24] != 0.0F) && (b[24] != 0.0F) && (c[24] != 0.0F)) {
/* 2406 */             this.raw.fill(ar, ag, ab, a[6]);
/* 2407 */             this.raw.vertex(a[21] / a[24], a[22] / a[24], a[23] / a[24], a[7], a[8]);
/* 2408 */             this.raw.fill(br, bg, bb, b[6]);
/* 2409 */             this.raw.vertex(b[21] / b[24], b[22] / b[24], b[23] / b[24], b[7], b[8]);
/* 2410 */             this.raw.fill(cr, cg, cb, c[6]);
/* 2411 */             this.raw.vertex(c[21] / c[24], c[22] / c[24], c[23] / c[24], c[7], c[8]);
/*      */           }
/* 2413 */         } else if (this.raw.is2D()) {
/* 2414 */           this.raw.fill(ar, ag, ab, a[6]);
/* 2415 */           this.raw.vertex(a[18], a[19], a[7], a[8]);
/* 2416 */           this.raw.fill(br, bg, bb, b[6]);
/* 2417 */           this.raw.vertex(b[18], b[19], b[7], b[8]);
/* 2418 */           this.raw.fill(cr, cg, cb, c[6]);
/* 2419 */           this.raw.vertex(c[18], c[19], c[7], c[8]);
/*      */         }
/*      */       }
/* 2422 */       else if (this.raw.is3D()) {
/* 2423 */         if ((a[24] != 0.0F) && (b[24] != 0.0F) && (c[24] != 0.0F)) {
/* 2424 */           this.raw.fill(ar, ag, ab, a[6]);
/* 2425 */           this.raw.vertex(a[21] / a[24], a[22] / a[24], a[23] / a[24]);
/* 2426 */           this.raw.fill(br, bg, bb, b[6]);
/* 2427 */           this.raw.vertex(b[21] / b[24], b[22] / b[24], b[23] / b[24]);
/* 2428 */           this.raw.fill(cr, cg, cb, c[6]);
/* 2429 */           this.raw.vertex(c[21] / c[24], c[22] / c[24], c[23] / c[24]);
/*      */         }
/* 2431 */       } else if (this.raw.is2D()) {
/* 2432 */         this.raw.fill(ar, ag, ab, a[6]);
/* 2433 */         this.raw.vertex(a[18], a[19]);
/* 2434 */         this.raw.fill(br, bg, bb, b[6]);
/* 2435 */         this.raw.vertex(b[18], b[19]);
/* 2436 */         this.raw.fill(cr, cg, cb, c[6]);
/* 2437 */         this.raw.vertex(c[18], c[19]);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 2442 */     this.raw.endShape();
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
/*      */   public void flush()
/*      */   {
/* 2477 */     if (this.hints[5] != 0) {
/* 2478 */       sort();
/*      */     }
/* 2480 */     render();
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
/*      */   protected void render()
/*      */   {
/* 2505 */     if (this.pointCount > 0) {
/* 2506 */       renderPoints(0, this.pointCount);
/* 2507 */       if (this.raw != null) {
/* 2508 */         rawPoints(0, this.pointCount);
/*      */       }
/* 2510 */       this.pointCount = 0;
/*      */     }
/* 2512 */     if (this.lineCount > 0) {
/* 2513 */       renderLines(0, this.lineCount);
/* 2514 */       if (this.raw != null) {
/* 2515 */         rawLines(0, this.lineCount);
/*      */       }
/* 2517 */       this.lineCount = 0;
/* 2518 */       this.pathCount = 0;
/*      */     }
/* 2520 */     if (this.triangleCount > 0) {
/* 2521 */       renderTriangles(0, this.triangleCount);
/* 2522 */       if (this.raw != null) {
/* 2523 */         rawTriangles(0, this.triangleCount);
/*      */       }
/* 2525 */       this.triangleCount = 0;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void sort()
/*      */   {
/* 2536 */     if (this.triangleCount > 0) {
/* 2537 */       sortTrianglesInternal(0, this.triangleCount - 1);
/*      */     }
/*      */   }
/*      */   
/*      */   private void sortTrianglesInternal(int i, int j)
/*      */   {
/* 2543 */     int pivotIndex = (i + j) / 2;
/* 2544 */     sortTrianglesSwap(pivotIndex, j);
/* 2545 */     int k = sortTrianglesPartition(i - 1, j);
/* 2546 */     sortTrianglesSwap(k, j);
/* 2547 */     if (k - i > 1) sortTrianglesInternal(i, k - 1);
/* 2548 */     if (j - k > 1) sortTrianglesInternal(k + 1, j);
/*      */   }
/*      */   
/*      */   private int sortTrianglesPartition(int left, int right)
/*      */   {
/* 2553 */     int pivot = right;
/*      */     do {
/* 2555 */       while (sortTrianglesCompare(++left, pivot) < 0.0F) {}
/* 2556 */       while ((right != 0) && 
/* 2557 */         (sortTrianglesCompare(--right, pivot) > 0.0F)) {}
/* 2558 */       sortTrianglesSwap(left, right);
/* 2559 */     } while (left < right);
/* 2560 */     sortTrianglesSwap(left, right);
/* 2561 */     return left;
/*      */   }
/*      */   
/*      */   private void sortTrianglesSwap(int a, int b)
/*      */   {
/* 2566 */     int[] tempi = this.triangles[a];
/* 2567 */     this.triangles[a] = this.triangles[b];
/* 2568 */     this.triangles[b] = tempi;
/* 2569 */     float[][] tempf = this.triangleColors[a];
/* 2570 */     this.triangleColors[a] = this.triangleColors[b];
/* 2571 */     this.triangleColors[b] = tempf;
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
/*      */   private float sortTrianglesCompare(int a, int b)
/*      */   {
/* 2586 */     return this.vertices[this.triangles[b][0]][20] + 
/* 2587 */       this.vertices[this.triangles[b][1]][20] + 
/* 2588 */       this.vertices[this.triangles[b][2]][20] - (
/* 2589 */       this.vertices[this.triangles[a][0]][20] + 
/* 2590 */       this.vertices[this.triangles[a][1]][20] + 
/* 2591 */       this.vertices[this.triangles[a][2]][20]);
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
/*      */   protected void ellipseImpl(float x, float y, float w, float h)
/*      */   {
/* 2653 */     float radiusH = w / 2.0F;
/* 2654 */     float radiusV = h / 2.0F;
/*      */     
/* 2656 */     float centerX = x + radiusH;
/* 2657 */     float centerY = y + radiusV;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2665 */     int rough = (int)(4.0D + Math.sqrt(w + h) * 3.0D);
/* 2666 */     int accuracy = PApplet.constrain(rough, 6, 100);
/*      */     
/* 2668 */     if (this.fill)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2674 */       float inc = 720.0F / accuracy;
/* 2675 */       float val = 0.0F;
/*      */       
/* 2677 */       boolean strokeSaved = this.stroke;
/* 2678 */       this.stroke = false;
/* 2679 */       boolean smoothSaved = this.smooth;
/* 2680 */       if ((this.smooth) && (this.stroke)) {
/* 2681 */         this.smooth = false;
/*      */       }
/*      */       
/* 2684 */       beginShape(11);
/* 2685 */       normal(0.0F, 0.0F, 1.0F);
/* 2686 */       vertex(centerX, centerY);
/* 2687 */       for (int i = 0; i < accuracy; i++) {
/* 2688 */         vertex(centerX + cosLUT[((int)val)] * radiusH, 
/* 2689 */           centerY + sinLUT[((int)val)] * radiusV);
/* 2690 */         val = (val + inc) % 720.0F;
/*      */       }
/*      */       
/* 2693 */       vertex(centerX + cosLUT[0] * radiusH, 
/* 2694 */         centerY + sinLUT[0] * radiusV);
/* 2695 */       endShape();
/*      */       
/* 2697 */       this.stroke = strokeSaved;
/* 2698 */       this.smooth = smoothSaved;
/*      */     }
/*      */     
/* 2701 */     if (this.stroke)
/*      */     {
/*      */ 
/*      */ 
/* 2705 */       float inc = 720.0F / accuracy;
/* 2706 */       float val = 0.0F;
/*      */       
/* 2708 */       boolean savedFill = this.fill;
/* 2709 */       this.fill = false;
/*      */       
/* 2711 */       val = 0.0F;
/* 2712 */       beginShape();
/* 2713 */       for (int i = 0; i < accuracy; i++) {
/* 2714 */         vertex(centerX + cosLUT[((int)val)] * radiusH, 
/* 2715 */           centerY + sinLUT[((int)val)] * radiusV);
/* 2716 */         val = (val + inc) % 720.0F;
/*      */       }
/* 2718 */       endShape(2);
/*      */       
/* 2720 */       this.fill = savedFill;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void arcImpl(float x, float y, float w, float h, float start, float stop)
/*      */   {
/* 2731 */     float hr = w / 2.0F;
/* 2732 */     float vr = h / 2.0F;
/*      */     
/* 2734 */     float centerX = x + hr;
/* 2735 */     float centerY = y + vr;
/*      */     
/* 2737 */     if (this.fill)
/*      */     {
/* 2739 */       boolean savedStroke = this.stroke;
/* 2740 */       this.stroke = false;
/*      */       
/* 2742 */       int startLUT = (int)(0.5F + start / 6.2831855F * 720.0F);
/* 2743 */       int stopLUT = (int)(0.5F + stop / 6.2831855F * 720.0F);
/*      */       
/* 2745 */       beginShape(11);
/* 2746 */       vertex(centerX, centerY);
/* 2747 */       int increment = 1;
/* 2748 */       for (int i = startLUT; i < stopLUT; i += increment) {
/* 2749 */         int ii = i % 720;
/*      */         
/* 2751 */         if (ii < 0) ii += 720;
/* 2752 */         vertex(centerX + cosLUT[ii] * hr, 
/* 2753 */           centerY + sinLUT[ii] * vr);
/*      */       }
/*      */       
/* 2756 */       vertex(centerX + cosLUT[(stopLUT % 720)] * hr, 
/* 2757 */         centerY + sinLUT[(stopLUT % 720)] * vr);
/* 2758 */       endShape();
/*      */       
/* 2760 */       this.stroke = savedStroke;
/*      */     }
/*      */     
/* 2763 */     if (this.stroke)
/*      */     {
/*      */ 
/*      */ 
/* 2767 */       boolean savedFill = this.fill;
/* 2768 */       this.fill = false;
/*      */       
/* 2770 */       int startLUT = (int)(0.5F + start / 6.2831855F * 720.0F);
/* 2771 */       int stopLUT = (int)(0.5F + stop / 6.2831855F * 720.0F);
/*      */       
/* 2773 */       beginShape();
/* 2774 */       int increment = 1;
/* 2775 */       for (int i = startLUT; i < stopLUT; i += increment) {
/* 2776 */         int ii = i % 720;
/* 2777 */         if (ii < 0) ii += 720;
/* 2778 */         vertex(centerX + cosLUT[ii] * hr, 
/* 2779 */           centerY + sinLUT[ii] * vr);
/*      */       }
/*      */       
/* 2782 */       vertex(centerX + cosLUT[(stopLUT % 720)] * hr, 
/* 2783 */         centerY + sinLUT[(stopLUT % 720)] * vr);
/* 2784 */       endShape();
/*      */       
/* 2786 */       this.fill = savedFill;
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
/*      */   public void box(float w, float h, float d)
/*      */   {
/* 2801 */     if (this.triangle != null) {
/* 2802 */       this.triangle.setCulling(true);
/*      */     }
/*      */     
/* 2805 */     super.box(w, h, d);
/*      */     
/* 2807 */     if (this.triangle != null) {
/* 2808 */       this.triangle.setCulling(false);
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
/*      */   public void sphere(float r)
/*      */   {
/* 2826 */     if (this.triangle != null) {
/* 2827 */       this.triangle.setCulling(true);
/*      */     }
/*      */     
/* 2830 */     super.sphere(r);
/*      */     
/* 2832 */     if (this.triangle != null) {
/* 2833 */       this.triangle.setCulling(false);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 2903 */     s_enableAccurateTextures = true;
/* 2904 */     this.smooth = true;
/*      */   }
/*      */   
/*      */   public void noSmooth()
/*      */   {
/* 2909 */     s_enableAccurateTextures = false;
/* 2910 */     this.smooth = false;
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
/*      */   protected boolean textModeCheck(int mode)
/*      */   {
/* 2967 */     return (this.textMode == 4) || (this.textMode == 256);
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
/*      */   public void pushMatrix()
/*      */   {
/* 2994 */     if (this.matrixStackDepth == 32) {
/* 2995 */       throw new RuntimeException("Too many calls to pushMatrix().");
/*      */     }
/* 2997 */     this.modelview.get(this.matrixStack[this.matrixStackDepth]);
/* 2998 */     this.modelviewInv.get(this.matrixInvStack[this.matrixStackDepth]);
/* 2999 */     this.matrixStackDepth += 1;
/*      */   }
/*      */   
/*      */   public void popMatrix()
/*      */   {
/* 3004 */     if (this.matrixStackDepth == 0) {
/* 3005 */       throw new RuntimeException("Too many calls to popMatrix(), and not enough to pushMatrix().");
/*      */     }
/* 3007 */     this.matrixStackDepth -= 1;
/* 3008 */     this.modelview.set(this.matrixStack[this.matrixStackDepth]);
/* 3009 */     this.modelviewInv.set(this.matrixInvStack[this.matrixStackDepth]);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void translate(float tx, float ty)
/*      */   {
/* 3020 */     translate(tx, ty, 0.0F);
/*      */   }
/*      */   
/*      */   public void translate(float tx, float ty, float tz)
/*      */   {
/* 3025 */     this.forwardTransform.translate(tx, ty, tz);
/* 3026 */     this.reverseTransform.invTranslate(tx, ty, tz);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void rotate(float angle)
/*      */   {
/* 3037 */     rotateZ(angle);
/*      */   }
/*      */   
/*      */   public void rotateX(float angle)
/*      */   {
/* 3042 */     this.forwardTransform.rotateX(angle);
/* 3043 */     this.reverseTransform.invRotateX(angle);
/*      */   }
/*      */   
/*      */   public void rotateY(float angle)
/*      */   {
/* 3048 */     this.forwardTransform.rotateY(angle);
/* 3049 */     this.reverseTransform.invRotateY(angle);
/*      */   }
/*      */   
/*      */   public void rotateZ(float angle)
/*      */   {
/* 3054 */     this.forwardTransform.rotateZ(angle);
/* 3055 */     this.reverseTransform.invRotateZ(angle);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void rotate(float angle, float v0, float v1, float v2)
/*      */   {
/* 3064 */     this.forwardTransform.rotate(angle, v0, v1, v2);
/* 3065 */     this.reverseTransform.invRotate(angle, v0, v1, v2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void scale(float s)
/*      */   {
/* 3073 */     scale(s, s, s);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void scale(float sx, float sy)
/*      */   {
/* 3081 */     scale(sx, sy, 1.0F);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void scale(float x, float y, float z)
/*      */   {
/* 3089 */     this.forwardTransform.scale(x, y, z);
/* 3090 */     this.reverseTransform.invScale(x, y, z);
/*      */   }
/*      */   
/*      */   public void skewX(float angle)
/*      */   {
/* 3095 */     float t = (float)Math.tan(angle);
/* 3096 */     applyMatrix(1.0F, t, 0.0F, 0.0F, 
/* 3097 */       0.0F, 1.0F, 0.0F, 0.0F, 
/* 3098 */       0.0F, 0.0F, 1.0F, 0.0F, 
/* 3099 */       0.0F, 0.0F, 0.0F, 1.0F);
/*      */   }
/*      */   
/*      */   public void skewY(float angle)
/*      */   {
/* 3104 */     float t = (float)Math.tan(angle);
/* 3105 */     applyMatrix(1.0F, 0.0F, 0.0F, 0.0F, 
/* 3106 */       t, 1.0F, 0.0F, 0.0F, 
/* 3107 */       0.0F, 0.0F, 1.0F, 0.0F, 
/* 3108 */       0.0F, 0.0F, 0.0F, 1.0F);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void resetMatrix()
/*      */   {
/* 3119 */     this.forwardTransform.reset();
/* 3120 */     this.reverseTransform.reset();
/*      */   }
/*      */   
/*      */   public void applyMatrix(PMatrix2D source)
/*      */   {
/* 3125 */     applyMatrix(source.m00, source.m01, source.m02, 
/* 3126 */       source.m10, source.m11, source.m12);
/*      */   }
/*      */   
/*      */ 
/*      */   public void applyMatrix(float n00, float n01, float n02, float n10, float n11, float n12)
/*      */   {
/* 3132 */     applyMatrix(n00, n01, n02, 0.0F, 
/* 3133 */       n10, n11, n12, 0.0F, 
/* 3134 */       0.0F, 0.0F, 1.0F, 0.0F, 
/* 3135 */       0.0F, 0.0F, 0.0F, 1.0F);
/*      */   }
/*      */   
/*      */   public void applyMatrix(PMatrix3D source)
/*      */   {
/* 3140 */     applyMatrix(source.m00, source.m01, source.m02, source.m03, 
/* 3141 */       source.m10, source.m11, source.m12, source.m13, 
/* 3142 */       source.m20, source.m21, source.m22, source.m23, 
/* 3143 */       source.m30, source.m31, source.m32, source.m33);
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
/*      */   public void applyMatrix(float n00, float n01, float n02, float n03, float n10, float n11, float n12, float n13, float n20, float n21, float n22, float n23, float n30, float n31, float n32, float n33)
/*      */   {
/* 3157 */     this.forwardTransform.apply(n00, n01, n02, n03, 
/* 3158 */       n10, n11, n12, n13, 
/* 3159 */       n20, n21, n22, n23, 
/* 3160 */       n30, n31, n32, n33);
/*      */     
/* 3162 */     this.reverseTransform.invApply(n00, n01, n02, n03, 
/* 3163 */       n10, n11, n12, n13, 
/* 3164 */       n20, n21, n22, n23, 
/* 3165 */       n30, n31, n32, n33);
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
/* 3176 */     return this.modelview.get();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public PMatrix3D getMatrix(PMatrix3D target)
/*      */   {
/* 3184 */     if (target == null) {
/* 3185 */       target = new PMatrix3D();
/*      */     }
/* 3187 */     target.set(this.modelview);
/* 3188 */     return target;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setMatrix(PMatrix2D source)
/*      */   {
/* 3197 */     resetMatrix();
/* 3198 */     applyMatrix(source);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setMatrix(PMatrix3D source)
/*      */   {
/* 3207 */     resetMatrix();
/* 3208 */     applyMatrix(source);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void printMatrix()
/*      */   {
/* 3216 */     this.modelview.print();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void beginCamera()
/*      */   {
/* 3307 */     if (this.manipulatingCamera) {
/* 3308 */       throw new RuntimeException("beginCamera() cannot be called again before endCamera()");
/*      */     }
/*      */     
/* 3311 */     this.manipulatingCamera = true;
/* 3312 */     this.forwardTransform = this.cameraInv;
/* 3313 */     this.reverseTransform = this.camera;
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
/*      */   public void endCamera()
/*      */   {
/* 3327 */     if (!this.manipulatingCamera) {
/* 3328 */       throw new RuntimeException("Cannot call endCamera() without first calling beginCamera()");
/*      */     }
/*      */     
/*      */ 
/* 3332 */     this.modelview.set(this.camera);
/* 3333 */     this.modelviewInv.set(this.cameraInv);
/*      */     
/*      */ 
/* 3336 */     this.forwardTransform = this.modelview;
/* 3337 */     this.reverseTransform = this.modelviewInv;
/*      */     
/*      */ 
/* 3340 */     this.manipulatingCamera = false;
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
/*      */   public void camera()
/*      */   {
/* 3397 */     camera(this.cameraX, this.cameraY, this.cameraZ, 
/* 3398 */       this.cameraX, this.cameraY, 0.0F, 
/* 3399 */       0.0F, 1.0F, 0.0F);
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
/*      */   public void camera(float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ)
/*      */   {
/* 3456 */     float z0 = eyeX - centerX;
/* 3457 */     float z1 = eyeY - centerY;
/* 3458 */     float z2 = eyeZ - centerZ;
/* 3459 */     float mag = sqrt(z0 * z0 + z1 * z1 + z2 * z2);
/*      */     
/* 3461 */     if (mag != 0.0F) {
/* 3462 */       z0 /= mag;
/* 3463 */       z1 /= mag;
/* 3464 */       z2 /= mag;
/*      */     }
/*      */     
/* 3467 */     float y0 = upX;
/* 3468 */     float y1 = upY;
/* 3469 */     float y2 = upZ;
/*      */     
/* 3471 */     float x0 = y1 * z2 - y2 * z1;
/* 3472 */     float x1 = -y0 * z2 + y2 * z0;
/* 3473 */     float x2 = y0 * z1 - y1 * z0;
/*      */     
/* 3475 */     y0 = z1 * x2 - z2 * x1;
/* 3476 */     y1 = -z0 * x2 + z2 * x0;
/* 3477 */     y2 = z0 * x1 - z1 * x0;
/*      */     
/* 3479 */     mag = sqrt(x0 * x0 + x1 * x1 + x2 * x2);
/* 3480 */     if (mag != 0.0F) {
/* 3481 */       x0 /= mag;
/* 3482 */       x1 /= mag;
/* 3483 */       x2 /= mag;
/*      */     }
/*      */     
/* 3486 */     mag = sqrt(y0 * y0 + y1 * y1 + y2 * y2);
/* 3487 */     if (mag != 0.0F) {
/* 3488 */       y0 /= mag;
/* 3489 */       y1 /= mag;
/* 3490 */       y2 /= mag;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 3495 */     this.camera.set(x0, x1, x2, 0.0F, 
/* 3496 */       y0, y1, y2, 0.0F, 
/* 3497 */       z0, z1, z2, 0.0F, 
/* 3498 */       0.0F, 0.0F, 0.0F, 1.0F);
/* 3499 */     this.camera.translate(-eyeX, -eyeY, -eyeZ);
/*      */     
/* 3501 */     this.cameraInv.reset();
/* 3502 */     this.cameraInv.invApply(x0, x1, x2, 0.0F, 
/* 3503 */       y0, y1, y2, 0.0F, 
/* 3504 */       z0, z1, z2, 0.0F, 
/* 3505 */       0.0F, 0.0F, 0.0F, 1.0F);
/* 3506 */     this.cameraInv.translate(eyeX, eyeY, eyeZ);
/*      */     
/* 3508 */     this.modelview.set(this.camera);
/* 3509 */     this.modelviewInv.set(this.cameraInv);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void printCamera()
/*      */   {
/* 3517 */     this.camera.print();
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
/*      */   public void ortho()
/*      */   {
/* 3531 */     ortho(0.0F, this.width, 0.0F, this.height, -10.0F, 10.0F);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void ortho(float left, float right, float bottom, float top, float near, float far)
/*      */   {
/* 3543 */     float x = 2.0F / (right - left);
/* 3544 */     float y = 2.0F / (top - bottom);
/* 3545 */     float z = -2.0F / (far - near);
/*      */     
/* 3547 */     float tx = -(right + left) / (right - left);
/* 3548 */     float ty = -(top + bottom) / (top - bottom);
/* 3549 */     float tz = -(far + near) / (far - near);
/*      */     
/* 3551 */     this.projection.set(x, 0.0F, 0.0F, tx, 
/* 3552 */       0.0F, y, 0.0F, ty, 
/* 3553 */       0.0F, 0.0F, z, tz, 
/* 3554 */       0.0F, 0.0F, 0.0F, 1.0F);
/* 3555 */     updateProjection();
/*      */     
/* 3557 */     this.frustumMode = false;
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
/*      */   public void perspective()
/*      */   {
/* 3583 */     perspective(this.cameraFOV, this.cameraAspect, this.cameraNear, this.cameraFar);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void perspective(float fov, float aspect, float zNear, float zFar)
/*      */   {
/* 3592 */     float ymax = zNear * (float)Math.tan(fov / 2.0F);
/* 3593 */     float ymin = -ymax;
/*      */     
/* 3595 */     float xmin = ymin * aspect;
/* 3596 */     float xmax = ymax * aspect;
/*      */     
/* 3598 */     frustum(xmin, xmax, ymin, ymax, zNear, zFar);
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
/*      */   public void frustum(float left, float right, float bottom, float top, float znear, float zfar)
/*      */   {
/* 3611 */     this.leftScreen = left;
/* 3612 */     this.rightScreen = right;
/* 3613 */     this.bottomScreen = bottom;
/* 3614 */     this.topScreen = top;
/* 3615 */     this.nearPlane = znear;
/* 3616 */     this.frustumMode = true;
/*      */     
/*      */ 
/* 3619 */     this.projection.set(2.0F * znear / (right - left), 0.0F, (right + left) / (right - left), 0.0F, 
/* 3620 */       0.0F, 2.0F * znear / (top - bottom), (top + bottom) / (top - bottom), 0.0F, 
/* 3621 */       0.0F, 0.0F, -(zfar + znear) / (zfar - znear), -(2.0F * zfar * znear) / (zfar - znear), 
/* 3622 */       0.0F, 0.0F, -1.0F, 0.0F);
/* 3623 */     updateProjection();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void updateProjection() {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void printProjection()
/*      */   {
/* 3636 */     this.projection.print();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float screenX(float x, float y)
/*      */   {
/* 3647 */     return screenX(x, y, 0.0F);
/*      */   }
/*      */   
/*      */   public float screenY(float x, float y)
/*      */   {
/* 3652 */     return screenY(x, y, 0.0F);
/*      */   }
/*      */   
/*      */   public float screenX(float x, float y, float z)
/*      */   {
/* 3657 */     float ax = 
/* 3658 */       this.modelview.m00 * x + this.modelview.m01 * y + this.modelview.m02 * z + this.modelview.m03;
/* 3659 */     float ay = 
/* 3660 */       this.modelview.m10 * x + this.modelview.m11 * y + this.modelview.m12 * z + this.modelview.m13;
/* 3661 */     float az = 
/* 3662 */       this.modelview.m20 * x + this.modelview.m21 * y + this.modelview.m22 * z + this.modelview.m23;
/* 3663 */     float aw = 
/* 3664 */       this.modelview.m30 * x + this.modelview.m31 * y + this.modelview.m32 * z + this.modelview.m33;
/*      */     
/* 3666 */     float ox = 
/* 3667 */       this.projection.m00 * ax + this.projection.m01 * ay + 
/* 3668 */       this.projection.m02 * az + this.projection.m03 * aw;
/* 3669 */     float ow = 
/* 3670 */       this.projection.m30 * ax + this.projection.m31 * ay + 
/* 3671 */       this.projection.m32 * az + this.projection.m33 * aw;
/*      */     
/* 3673 */     if (ow != 0.0F) ox /= ow;
/* 3674 */     return this.width * (1.0F + ox) / 2.0F;
/*      */   }
/*      */   
/*      */   public float screenY(float x, float y, float z)
/*      */   {
/* 3679 */     float ax = 
/* 3680 */       this.modelview.m00 * x + this.modelview.m01 * y + this.modelview.m02 * z + this.modelview.m03;
/* 3681 */     float ay = 
/* 3682 */       this.modelview.m10 * x + this.modelview.m11 * y + this.modelview.m12 * z + this.modelview.m13;
/* 3683 */     float az = 
/* 3684 */       this.modelview.m20 * x + this.modelview.m21 * y + this.modelview.m22 * z + this.modelview.m23;
/* 3685 */     float aw = 
/* 3686 */       this.modelview.m30 * x + this.modelview.m31 * y + this.modelview.m32 * z + this.modelview.m33;
/*      */     
/* 3688 */     float oy = 
/* 3689 */       this.projection.m10 * ax + this.projection.m11 * ay + 
/* 3690 */       this.projection.m12 * az + this.projection.m13 * aw;
/* 3691 */     float ow = 
/* 3692 */       this.projection.m30 * ax + this.projection.m31 * ay + 
/* 3693 */       this.projection.m32 * az + this.projection.m33 * aw;
/*      */     
/* 3695 */     if (ow != 0.0F) oy /= ow;
/* 3696 */     return this.height * (1.0F + oy) / 2.0F;
/*      */   }
/*      */   
/*      */   public float screenZ(float x, float y, float z)
/*      */   {
/* 3701 */     float ax = 
/* 3702 */       this.modelview.m00 * x + this.modelview.m01 * y + this.modelview.m02 * z + this.modelview.m03;
/* 3703 */     float ay = 
/* 3704 */       this.modelview.m10 * x + this.modelview.m11 * y + this.modelview.m12 * z + this.modelview.m13;
/* 3705 */     float az = 
/* 3706 */       this.modelview.m20 * x + this.modelview.m21 * y + this.modelview.m22 * z + this.modelview.m23;
/* 3707 */     float aw = 
/* 3708 */       this.modelview.m30 * x + this.modelview.m31 * y + this.modelview.m32 * z + this.modelview.m33;
/*      */     
/* 3710 */     float oz = 
/* 3711 */       this.projection.m20 * ax + this.projection.m21 * ay + 
/* 3712 */       this.projection.m22 * az + this.projection.m23 * aw;
/* 3713 */     float ow = 
/* 3714 */       this.projection.m30 * ax + this.projection.m31 * ay + 
/* 3715 */       this.projection.m32 * az + this.projection.m33 * aw;
/*      */     
/* 3717 */     if (ow != 0.0F) oz /= ow;
/* 3718 */     return (oz + 1.0F) / 2.0F;
/*      */   }
/*      */   
/*      */   public float modelX(float x, float y, float z)
/*      */   {
/* 3723 */     float ax = 
/* 3724 */       this.modelview.m00 * x + this.modelview.m01 * y + this.modelview.m02 * z + this.modelview.m03;
/* 3725 */     float ay = 
/* 3726 */       this.modelview.m10 * x + this.modelview.m11 * y + this.modelview.m12 * z + this.modelview.m13;
/* 3727 */     float az = 
/* 3728 */       this.modelview.m20 * x + this.modelview.m21 * y + this.modelview.m22 * z + this.modelview.m23;
/* 3729 */     float aw = 
/* 3730 */       this.modelview.m30 * x + this.modelview.m31 * y + this.modelview.m32 * z + this.modelview.m33;
/*      */     
/* 3732 */     float ox = 
/* 3733 */       this.cameraInv.m00 * ax + this.cameraInv.m01 * ay + 
/* 3734 */       this.cameraInv.m02 * az + this.cameraInv.m03 * aw;
/* 3735 */     float ow = 
/* 3736 */       this.cameraInv.m30 * ax + this.cameraInv.m31 * ay + 
/* 3737 */       this.cameraInv.m32 * az + this.cameraInv.m33 * aw;
/*      */     
/* 3739 */     return ow != 0.0F ? ox / ow : ox;
/*      */   }
/*      */   
/*      */   public float modelY(float x, float y, float z)
/*      */   {
/* 3744 */     float ax = 
/* 3745 */       this.modelview.m00 * x + this.modelview.m01 * y + this.modelview.m02 * z + this.modelview.m03;
/* 3746 */     float ay = 
/* 3747 */       this.modelview.m10 * x + this.modelview.m11 * y + this.modelview.m12 * z + this.modelview.m13;
/* 3748 */     float az = 
/* 3749 */       this.modelview.m20 * x + this.modelview.m21 * y + this.modelview.m22 * z + this.modelview.m23;
/* 3750 */     float aw = 
/* 3751 */       this.modelview.m30 * x + this.modelview.m31 * y + this.modelview.m32 * z + this.modelview.m33;
/*      */     
/* 3753 */     float oy = 
/* 3754 */       this.cameraInv.m10 * ax + this.cameraInv.m11 * ay + 
/* 3755 */       this.cameraInv.m12 * az + this.cameraInv.m13 * aw;
/* 3756 */     float ow = 
/* 3757 */       this.cameraInv.m30 * ax + this.cameraInv.m31 * ay + 
/* 3758 */       this.cameraInv.m32 * az + this.cameraInv.m33 * aw;
/*      */     
/* 3760 */     return ow != 0.0F ? oy / ow : oy;
/*      */   }
/*      */   
/*      */   public float modelZ(float x, float y, float z)
/*      */   {
/* 3765 */     float ax = 
/* 3766 */       this.modelview.m00 * x + this.modelview.m01 * y + this.modelview.m02 * z + this.modelview.m03;
/* 3767 */     float ay = 
/* 3768 */       this.modelview.m10 * x + this.modelview.m11 * y + this.modelview.m12 * z + this.modelview.m13;
/* 3769 */     float az = 
/* 3770 */       this.modelview.m20 * x + this.modelview.m21 * y + this.modelview.m22 * z + this.modelview.m23;
/* 3771 */     float aw = 
/* 3772 */       this.modelview.m30 * x + this.modelview.m31 * y + this.modelview.m32 * z + this.modelview.m33;
/*      */     
/* 3774 */     float oz = 
/* 3775 */       this.cameraInv.m20 * ax + this.cameraInv.m21 * ay + 
/* 3776 */       this.cameraInv.m22 * az + this.cameraInv.m23 * aw;
/* 3777 */     float ow = 
/* 3778 */       this.cameraInv.m30 * ax + this.cameraInv.m31 * ay + 
/* 3779 */       this.cameraInv.m32 * az + this.cameraInv.m33 * aw;
/*      */     
/* 3781 */     return ow != 0.0F ? oz / ow : oz;
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
/*      */   public void strokeJoin(int join)
/*      */   {
/* 3807 */     if (join != 8) {
/* 3808 */       showMethodWarning("strokeJoin");
/*      */     }
/*      */   }
/*      */   
/*      */   public void strokeCap(int cap)
/*      */   {
/* 3814 */     if (cap != 2) {
/* 3815 */       showMethodWarning("strokeCap");
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
/*      */   protected void fillFromCalc()
/*      */   {
/* 3843 */     super.fillFromCalc();
/* 3844 */     ambientFromCalc();
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
/* 3862 */   PVector lightPositionVec = new PVector();
/* 3863 */   PVector lightDirectionVec = new PVector();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 3992 */     int colorModeSaved = this.colorMode;
/* 3993 */     this.colorMode = 1;
/*      */     
/* 3995 */     lightFalloff(1.0F, 0.0F, 0.0F);
/* 3996 */     lightSpecular(0.0F, 0.0F, 0.0F);
/*      */     
/* 3998 */     ambientLight(this.colorModeX * 0.5F, 
/* 3999 */       this.colorModeY * 0.5F, 
/* 4000 */       this.colorModeZ * 0.5F);
/* 4001 */     directionalLight(this.colorModeX * 0.5F, 
/* 4002 */       this.colorModeY * 0.5F, 
/* 4003 */       this.colorModeZ * 0.5F, 
/* 4004 */       0.0F, 0.0F, -1.0F);
/*      */     
/* 4006 */     this.colorMode = colorModeSaved;
/*      */     
/* 4008 */     this.lightingDependsOnVertexPosition = false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void noLights()
/*      */   {
/* 4017 */     flush();
/*      */     
/* 4019 */     this.lightCount = 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void ambientLight(float r, float g, float b)
/*      */   {
/* 4027 */     ambientLight(r, g, b, 0.0F, 0.0F, 0.0F);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void ambientLight(float r, float g, float b, float x, float y, float z)
/*      */   {
/* 4038 */     if (this.lightCount == 8) {
/* 4039 */       throw new RuntimeException("can only create 8 lights");
/*      */     }
/* 4041 */     colorCalc(r, g, b);
/* 4042 */     this.lightDiffuse[this.lightCount][0] = this.calcR;
/* 4043 */     this.lightDiffuse[this.lightCount][1] = this.calcG;
/* 4044 */     this.lightDiffuse[this.lightCount][2] = this.calcB;
/*      */     
/* 4046 */     this.lightType[this.lightCount] = 0;
/* 4047 */     this.lightFalloffConstant[this.lightCount] = this.currentLightFalloffConstant;
/* 4048 */     this.lightFalloffLinear[this.lightCount] = this.currentLightFalloffLinear;
/* 4049 */     this.lightFalloffQuadratic[this.lightCount] = this.currentLightFalloffQuadratic;
/* 4050 */     lightPosition(this.lightCount, x, y, z);
/* 4051 */     this.lightCount += 1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void directionalLight(float r, float g, float b, float nx, float ny, float nz)
/*      */   {
/* 4058 */     if (this.lightCount == 8) {
/* 4059 */       throw new RuntimeException("can only create 8 lights");
/*      */     }
/* 4061 */     colorCalc(r, g, b);
/* 4062 */     this.lightDiffuse[this.lightCount][0] = this.calcR;
/* 4063 */     this.lightDiffuse[this.lightCount][1] = this.calcG;
/* 4064 */     this.lightDiffuse[this.lightCount][2] = this.calcB;
/*      */     
/* 4066 */     this.lightType[this.lightCount] = 1;
/* 4067 */     this.lightFalloffConstant[this.lightCount] = this.currentLightFalloffConstant;
/* 4068 */     this.lightFalloffLinear[this.lightCount] = this.currentLightFalloffLinear;
/* 4069 */     this.lightFalloffQuadratic[this.lightCount] = this.currentLightFalloffQuadratic;
/* 4070 */     this.lightSpecular[this.lightCount][0] = this.currentLightSpecular[0];
/* 4071 */     this.lightSpecular[this.lightCount][1] = this.currentLightSpecular[1];
/* 4072 */     this.lightSpecular[this.lightCount][2] = this.currentLightSpecular[2];
/* 4073 */     lightDirection(this.lightCount, nx, ny, nz);
/* 4074 */     this.lightCount += 1;
/*      */   }
/*      */   
/*      */ 
/*      */   public void pointLight(float r, float g, float b, float x, float y, float z)
/*      */   {
/* 4080 */     if (this.lightCount == 8) {
/* 4081 */       throw new RuntimeException("can only create 8 lights");
/*      */     }
/* 4083 */     colorCalc(r, g, b);
/* 4084 */     this.lightDiffuse[this.lightCount][0] = this.calcR;
/* 4085 */     this.lightDiffuse[this.lightCount][1] = this.calcG;
/* 4086 */     this.lightDiffuse[this.lightCount][2] = this.calcB;
/*      */     
/* 4088 */     this.lightType[this.lightCount] = 2;
/* 4089 */     this.lightFalloffConstant[this.lightCount] = this.currentLightFalloffConstant;
/* 4090 */     this.lightFalloffLinear[this.lightCount] = this.currentLightFalloffLinear;
/* 4091 */     this.lightFalloffQuadratic[this.lightCount] = this.currentLightFalloffQuadratic;
/* 4092 */     this.lightSpecular[this.lightCount][0] = this.currentLightSpecular[0];
/* 4093 */     this.lightSpecular[this.lightCount][1] = this.currentLightSpecular[1];
/* 4094 */     this.lightSpecular[this.lightCount][2] = this.currentLightSpecular[2];
/* 4095 */     lightPosition(this.lightCount, x, y, z);
/* 4096 */     this.lightCount += 1;
/*      */     
/* 4098 */     this.lightingDependsOnVertexPosition = true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void spotLight(float r, float g, float b, float x, float y, float z, float nx, float ny, float nz, float angle, float concentration)
/*      */   {
/* 4106 */     if (this.lightCount == 8) {
/* 4107 */       throw new RuntimeException("can only create 8 lights");
/*      */     }
/* 4109 */     colorCalc(r, g, b);
/* 4110 */     this.lightDiffuse[this.lightCount][0] = this.calcR;
/* 4111 */     this.lightDiffuse[this.lightCount][1] = this.calcG;
/* 4112 */     this.lightDiffuse[this.lightCount][2] = this.calcB;
/*      */     
/* 4114 */     this.lightType[this.lightCount] = 3;
/* 4115 */     this.lightFalloffConstant[this.lightCount] = this.currentLightFalloffConstant;
/* 4116 */     this.lightFalloffLinear[this.lightCount] = this.currentLightFalloffLinear;
/* 4117 */     this.lightFalloffQuadratic[this.lightCount] = this.currentLightFalloffQuadratic;
/* 4118 */     this.lightSpecular[this.lightCount][0] = this.currentLightSpecular[0];
/* 4119 */     this.lightSpecular[this.lightCount][1] = this.currentLightSpecular[1];
/* 4120 */     this.lightSpecular[this.lightCount][2] = this.currentLightSpecular[2];
/* 4121 */     lightPosition(this.lightCount, x, y, z);
/* 4122 */     lightDirection(this.lightCount, nx, ny, nz);
/* 4123 */     this.lightSpotAngle[this.lightCount] = angle;
/* 4124 */     this.lightSpotAngleCos[this.lightCount] = Math.max(0.0F, (float)Math.cos(angle));
/* 4125 */     this.lightSpotConcentration[this.lightCount] = concentration;
/* 4126 */     this.lightCount += 1;
/*      */     
/* 4128 */     this.lightingDependsOnVertexPosition = true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void lightFalloff(float constant, float linear, float quadratic)
/*      */   {
/* 4137 */     this.currentLightFalloffConstant = constant;
/* 4138 */     this.currentLightFalloffLinear = linear;
/* 4139 */     this.currentLightFalloffQuadratic = quadratic;
/*      */     
/* 4141 */     this.lightingDependsOnVertexPosition = true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void lightSpecular(float x, float y, float z)
/*      */   {
/* 4149 */     colorCalc(x, y, z);
/* 4150 */     this.currentLightSpecular[0] = this.calcR;
/* 4151 */     this.currentLightSpecular[1] = this.calcG;
/* 4152 */     this.currentLightSpecular[2] = this.calcB;
/*      */     
/* 4154 */     this.lightingDependsOnVertexPosition = true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void lightPosition(int num, float x, float y, float z)
/*      */   {
/* 4163 */     this.lightPositionVec.set(x, y, z);
/* 4164 */     this.modelview.mult(this.lightPositionVec, this.lightPosition[num]);
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
/*      */   protected void lightDirection(int num, float x, float y, float z)
/*      */   {
/* 4181 */     this.lightNormal[num].set(this.modelviewInv.m00 * x + this.modelviewInv.m10 * y + this.modelviewInv.m20 * z + this.modelviewInv.m30, 
/* 4182 */       this.modelviewInv.m01 * x + this.modelviewInv.m11 * y + this.modelviewInv.m21 * z + this.modelviewInv.m31, 
/* 4183 */       this.modelviewInv.m02 * x + this.modelviewInv.m12 * y + this.modelviewInv.m22 * z + this.modelviewInv.m32);
/* 4184 */     this.lightNormal[num].normalize();
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
/*      */   protected void backgroundImpl(PImage image)
/*      */   {
/* 4227 */     System.arraycopy(image.pixels, 0, this.pixels, 0, this.pixels.length);
/* 4228 */     Arrays.fill(this.zbuffer, Float.MAX_VALUE);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void backgroundImpl()
/*      */   {
/* 4236 */     Arrays.fill(this.pixels, this.backgroundColor);
/* 4237 */     Arrays.fill(this.zbuffer, Float.MAX_VALUE);
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
/*      */   public boolean is2D()
/*      */   {
/* 4308 */     return false;
/*      */   }
/*      */   
/*      */   public boolean is3D()
/*      */   {
/* 4313 */     return true;
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
/*      */   private final float sqrt(float a)
/*      */   {
/* 4344 */     return (float)Math.sqrt(a);
/*      */   }
/*      */   
/*      */   private final float mag(float a, float b, float c)
/*      */   {
/* 4349 */     return (float)Math.sqrt(a * a + b * b + c * c);
/*      */   }
/*      */   
/*      */   private final float clamp(float a)
/*      */   {
/* 4354 */     return a < 1.0F ? a : 1.0F;
/*      */   }
/*      */   
/*      */   private final float abs(float a)
/*      */   {
/* 4359 */     return a < 0.0F ? -a : a;
/*      */   }
/*      */   
/*      */ 
/*      */   private float dot(float ax, float ay, float az, float bx, float by, float bz)
/*      */   {
/* 4365 */     return ax * bx + ay * by + az * bz;
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
/*      */   private final void cross(float a0, float a1, float a2, float b0, float b1, float b2, PVector out)
/*      */   {
/* 4383 */     out.x = (a1 * b2 - a2 * b1);
/* 4384 */     out.y = (a2 * b0 - a0 * b2);
/* 4385 */     out.z = (a0 * b1 - a1 * b0);
/*      */   }
/*      */ }


/* Location:              /Users/AndrewKanarek/Desktop/MICHIGAN/KiefferWork/scripts-zibo/JavaApps/EllinghamDiagram/core.jar!/processing/core/PGraphics3D.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */