/*      */ package processing.core;
/*      */ 
/*      */ import java.awt.BasicStroke;
/*      */ import java.awt.Color;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Image;
/*      */ import java.awt.Paint;
/*      */ import java.awt.RenderingHints;
/*      */ import java.awt.Shape;
/*      */ import java.awt.geom.AffineTransform;
/*      */ import java.awt.geom.Arc2D.Float;
/*      */ import java.awt.geom.Ellipse2D.Float;
/*      */ import java.awt.geom.GeneralPath;
/*      */ import java.awt.geom.Line2D.Float;
/*      */ import java.awt.geom.Rectangle2D.Float;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.awt.image.WritableRaster;
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
/*      */ public class PGraphicsJava2D
/*      */   extends PGraphics
/*      */ {
/*      */   public Graphics2D g2;
/*      */   GeneralPath gpath;
/*      */   boolean breakShape;
/*      */   float[] curveCoordX;
/*      */   float[] curveCoordY;
/*      */   float[] curveDrawX;
/*      */   float[] curveDrawY;
/*      */   int transformCount;
/*   66 */   AffineTransform[] transformStack = new AffineTransform[32];
/*   67 */   double[] transform = new double[6];
/*      */   
/*   69 */   Line2D.Float line = new Line2D.Float();
/*   70 */   Ellipse2D.Float ellipse = new Ellipse2D.Float();
/*   71 */   Rectangle2D.Float rect = new Rectangle2D.Float();
/*   72 */   Arc2D.Float arc = new Arc2D.Float();
/*      */   
/*      */ 
/*      */ 
/*      */   protected Color tintColorObject;
/*      */   
/*      */ 
/*      */ 
/*      */   protected Color fillColorObject;
/*      */   
/*      */ 
/*      */ 
/*      */   public boolean fillGradient;
/*      */   
/*      */ 
/*      */ 
/*      */   public Paint fillGradientObject;
/*      */   
/*      */ 
/*      */ 
/*      */   protected Color strokeColorObject;
/*      */   
/*      */ 
/*      */ 
/*      */   public boolean strokeGradient;
/*      */   
/*      */ 
/*      */ 
/*      */   public Paint strokeGradientObject;
/*      */   
/*      */ 
/*      */ 
/*      */   int[] clearPixels;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setSize(int iwidth, int iheight)
/*      */   {
/*  111 */     this.width = iwidth;
/*  112 */     this.height = iheight;
/*  113 */     this.width1 = (this.width - 1);
/*  114 */     this.height1 = (this.height - 1);
/*      */     
/*  116 */     allocate();
/*  117 */     reapplySettings();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void allocate()
/*      */   {
/*  125 */     this.image = new BufferedImage(this.width, this.height, 2);
/*  126 */     this.g2 = ((Graphics2D)this.image.getGraphics());
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
/*      */   public boolean canDraw()
/*      */   {
/*  145 */     return true;
/*      */   }
/*      */   
/*      */   public void beginDraw()
/*      */   {
/*  150 */     checkSettings();
/*      */     
/*  152 */     resetMatrix();
/*      */     
/*      */ 
/*  155 */     this.vertexCount = 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void endDraw()
/*      */   {
/*  165 */     if (!this.primarySurface) {
/*  166 */       loadPixels();
/*      */     }
/*  168 */     this.modified = true;
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
/*      */   public void beginShape(int kind)
/*      */   {
/*  207 */     this.shape = kind;
/*  208 */     this.vertexCount = 0;
/*  209 */     this.curveVertexCount = 0;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  216 */     this.gpath = null;
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
/*      */   public void texture(PImage image)
/*      */   {
/*  230 */     showMethodWarning("texture");
/*      */   }
/*      */   
/*      */   public void vertex(float x, float y)
/*      */   {
/*  235 */     this.curveVertexCount = 0;
/*      */     
/*      */ 
/*  238 */     if (this.vertexCount == this.vertices.length) {
/*  239 */       float[][] temp = new float[this.vertexCount << 1][36];
/*  240 */       System.arraycopy(this.vertices, 0, temp, 0, this.vertexCount);
/*  241 */       this.vertices = temp;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  246 */     this.vertices[this.vertexCount][0] = x;
/*  247 */     this.vertices[this.vertexCount][1] = y;
/*  248 */     this.vertexCount += 1;
/*      */     
/*  250 */     switch (this.shape)
/*      */     {
/*      */     case 2: 
/*  253 */       point(x, y);
/*  254 */       break;
/*      */     
/*      */     case 4: 
/*  257 */       if (this.vertexCount % 2 == 0) {
/*  258 */         line(this.vertices[(this.vertexCount - 2)][0], 
/*  259 */           this.vertices[(this.vertexCount - 2)][1], x, y);
/*      */       }
/*  261 */       break;
/*      */     
/*      */     case 9: 
/*  264 */       if (this.vertexCount % 3 == 0) {
/*  265 */         triangle(this.vertices[(this.vertexCount - 3)][0], 
/*  266 */           this.vertices[(this.vertexCount - 3)][1], 
/*  267 */           this.vertices[(this.vertexCount - 2)][0], 
/*  268 */           this.vertices[(this.vertexCount - 2)][1], 
/*  269 */           x, y);
/*      */       }
/*  271 */       break;
/*      */     
/*      */     case 10: 
/*  274 */       if (this.vertexCount >= 3) {
/*  275 */         triangle(this.vertices[(this.vertexCount - 2)][0], 
/*  276 */           this.vertices[(this.vertexCount - 2)][1], 
/*  277 */           this.vertices[(this.vertexCount - 1)][0], 
/*  278 */           this.vertices[(this.vertexCount - 1)][1], 
/*  279 */           this.vertices[(this.vertexCount - 3)][0], 
/*  280 */           this.vertices[(this.vertexCount - 3)][1]);
/*      */       }
/*  282 */       break;
/*      */     
/*      */     case 11: 
/*  285 */       if (this.vertexCount == 3) {
/*  286 */         triangle(this.vertices[0][0], this.vertices[0][1], 
/*  287 */           this.vertices[1][0], this.vertices[1][1], 
/*  288 */           x, y);
/*  289 */       } else if (this.vertexCount > 3) {
/*  290 */         this.gpath = new GeneralPath();
/*      */         
/*      */ 
/*  293 */         this.gpath.moveTo(this.vertices[0][0], 
/*  294 */           this.vertices[0][1]);
/*  295 */         this.gpath.lineTo(this.vertices[(this.vertexCount - 2)][0], 
/*  296 */           this.vertices[(this.vertexCount - 2)][1]);
/*  297 */         this.gpath.lineTo(x, y);
/*  298 */         drawShape(this.gpath);
/*      */       }
/*  300 */       break;
/*      */     
/*      */     case 16: 
/*  303 */       if (this.vertexCount % 4 == 0) {
/*  304 */         quad(this.vertices[(this.vertexCount - 4)][0], 
/*  305 */           this.vertices[(this.vertexCount - 4)][1], 
/*  306 */           this.vertices[(this.vertexCount - 3)][0], 
/*  307 */           this.vertices[(this.vertexCount - 3)][1], 
/*  308 */           this.vertices[(this.vertexCount - 2)][0], 
/*  309 */           this.vertices[(this.vertexCount - 2)][1], 
/*  310 */           x, y);
/*      */       }
/*  312 */       break;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     case 17: 
/*  318 */       if ((this.vertexCount >= 4) && (this.vertexCount % 2 == 0)) {
/*  319 */         quad(this.vertices[(this.vertexCount - 4)][0], 
/*  320 */           this.vertices[(this.vertexCount - 4)][1], 
/*  321 */           this.vertices[(this.vertexCount - 2)][0], 
/*  322 */           this.vertices[(this.vertexCount - 2)][1], 
/*  323 */           x, y, 
/*  324 */           this.vertices[(this.vertexCount - 3)][0], 
/*  325 */           this.vertices[(this.vertexCount - 3)][1]);
/*      */       }
/*  327 */       break;
/*      */     
/*      */     case 20: 
/*  330 */       if (this.gpath == null) {
/*  331 */         this.gpath = new GeneralPath();
/*  332 */         this.gpath.moveTo(x, y);
/*  333 */       } else if (this.breakShape) {
/*  334 */         this.gpath.moveTo(x, y);
/*  335 */         this.breakShape = false;
/*      */       } else {
/*  337 */         this.gpath.lineTo(x, y);
/*      */       }
/*      */       break;
/*      */     }
/*      */   }
/*      */   
/*      */   public void vertex(float x, float y, float z)
/*      */   {
/*  345 */     showDepthWarningXYZ("vertex");
/*      */   }
/*      */   
/*      */   public void vertex(float x, float y, float u, float v)
/*      */   {
/*  350 */     showVariationWarning("vertex(x, y, u, v)");
/*      */   }
/*      */   
/*      */   public void vertex(float x, float y, float z, float u, float v)
/*      */   {
/*  355 */     showDepthWarningXYZ("vertex");
/*      */   }
/*      */   
/*      */   public void breakShape()
/*      */   {
/*  360 */     this.breakShape = true;
/*      */   }
/*      */   
/*      */   public void endShape(int mode)
/*      */   {
/*  365 */     if ((this.gpath != null) && 
/*  366 */       (this.shape == 20)) {
/*  367 */       if (mode == 2) {
/*  368 */         this.gpath.closePath();
/*      */       }
/*  370 */       drawShape(this.gpath);
/*      */     }
/*      */     
/*  373 */     this.shape = 0;
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
/*      */   public void bezierVertex(float x1, float y1, float x2, float y2, float x3, float y3)
/*      */   {
/*  386 */     bezierVertexCheck();
/*  387 */     this.gpath.curveTo(x1, y1, x2, y2, x3, y3);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void bezierVertex(float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4)
/*      */   {
/*  394 */     showDepthWarningXYZ("bezierVertex");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void curveVertexCheck()
/*      */   {
/*  405 */     super.curveVertexCheck();
/*      */     
/*  407 */     if (this.curveCoordX == null) {
/*  408 */       this.curveCoordX = new float[4];
/*  409 */       this.curveCoordY = new float[4];
/*  410 */       this.curveDrawX = new float[4];
/*  411 */       this.curveDrawY = new float[4];
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void curveVertexSegment(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4)
/*      */   {
/*  420 */     this.curveCoordX[0] = x1;
/*  421 */     this.curveCoordY[0] = y1;
/*      */     
/*  423 */     this.curveCoordX[1] = x2;
/*  424 */     this.curveCoordY[1] = y2;
/*      */     
/*  426 */     this.curveCoordX[2] = x3;
/*  427 */     this.curveCoordY[2] = y3;
/*      */     
/*  429 */     this.curveCoordX[3] = x4;
/*  430 */     this.curveCoordY[3] = y4;
/*      */     
/*  432 */     this.curveToBezierMatrix.mult(this.curveCoordX, this.curveDrawX);
/*  433 */     this.curveToBezierMatrix.mult(this.curveCoordY, this.curveDrawY);
/*      */     
/*      */ 
/*      */ 
/*  437 */     if (this.gpath == null) {
/*  438 */       this.gpath = new GeneralPath();
/*  439 */       this.gpath.moveTo(this.curveDrawX[0], this.curveDrawY[0]);
/*      */     }
/*      */     
/*  442 */     this.gpath.curveTo(this.curveDrawX[1], this.curveDrawY[1], 
/*  443 */       this.curveDrawX[2], this.curveDrawY[2], 
/*  444 */       this.curveDrawX[3], this.curveDrawY[3]);
/*      */   }
/*      */   
/*      */   public void curveVertex(float x, float y, float z)
/*      */   {
/*  449 */     showDepthWarningXYZ("curveVertex");
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
/*      */   public void point(float x, float y)
/*      */   {
/*  469 */     if (this.stroke)
/*      */     {
/*  471 */       line(x, y, x + 1.0E-4F, y + 1.0E-4F);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void line(float x1, float y1, float x2, float y2)
/*      */   {
/*  480 */     this.line.setLine(x1, y1, x2, y2);
/*  481 */     strokeShape(this.line);
/*      */   }
/*      */   
/*      */ 
/*      */   public void triangle(float x1, float y1, float x2, float y2, float x3, float y3)
/*      */   {
/*  487 */     this.gpath = new GeneralPath();
/*  488 */     this.gpath.moveTo(x1, y1);
/*  489 */     this.gpath.lineTo(x2, y2);
/*  490 */     this.gpath.lineTo(x3, y3);
/*  491 */     this.gpath.closePath();
/*  492 */     drawShape(this.gpath);
/*      */   }
/*      */   
/*      */ 
/*      */   public void quad(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4)
/*      */   {
/*  498 */     GeneralPath gp = new GeneralPath();
/*  499 */     gp.moveTo(x1, y1);
/*  500 */     gp.lineTo(x2, y2);
/*  501 */     gp.lineTo(x3, y3);
/*  502 */     gp.lineTo(x4, y4);
/*  503 */     gp.closePath();
/*  504 */     drawShape(gp);
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
/*      */   protected void rectImpl(float x1, float y1, float x2, float y2)
/*      */   {
/*  521 */     this.rect.setFrame(x1, y1, x2 - x1, y2 - y1);
/*  522 */     drawShape(this.rect);
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
/*      */   protected void ellipseImpl(float x, float y, float w, float h)
/*      */   {
/*  539 */     this.ellipse.setFrame(x, y, w, h);
/*  540 */     drawShape(this.ellipse);
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
/*      */   protected void arcImpl(float x, float y, float w, float h, float start, float stop)
/*      */   {
/*  559 */     start = -start * 57.295776F;
/*  560 */     stop = -stop * 57.295776F;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  572 */     float sweep = stop - start;
/*      */     
/*      */ 
/*  575 */     if (this.fill)
/*      */     {
/*  577 */       this.arc.setArc(x, y, w, h, start, sweep, 2);
/*  578 */       fillShape(this.arc);
/*      */     }
/*  580 */     if (this.stroke)
/*      */     {
/*  582 */       this.arc.setArc(x, y, w, h, start, sweep, 0);
/*  583 */       strokeShape(this.arc);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void fillShape(Shape s)
/*      */   {
/*  595 */     if (this.fillGradient) {
/*  596 */       this.g2.setPaint(this.fillGradientObject);
/*  597 */       this.g2.fill(s);
/*  598 */     } else if (this.fill) {
/*  599 */       this.g2.setColor(this.fillColorObject);
/*  600 */       this.g2.fill(s);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void strokeShape(Shape s)
/*      */   {
/*  606 */     if (this.strokeGradient) {
/*  607 */       this.g2.setPaint(this.strokeGradientObject);
/*  608 */       this.g2.draw(s);
/*  609 */     } else if (this.stroke) {
/*  610 */       this.g2.setColor(this.strokeColorObject);
/*  611 */       this.g2.draw(s);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void drawShape(Shape s)
/*      */   {
/*  617 */     if (this.fillGradient) {
/*  618 */       this.g2.setPaint(this.fillGradientObject);
/*  619 */       this.g2.fill(s);
/*  620 */     } else if (this.fill) {
/*  621 */       this.g2.setColor(this.fillColorObject);
/*  622 */       this.g2.fill(s);
/*      */     }
/*  624 */     if (this.strokeGradient) {
/*  625 */       this.g2.setPaint(this.strokeGradientObject);
/*  626 */       this.g2.draw(s);
/*  627 */     } else if (this.stroke) {
/*  628 */       this.g2.setColor(this.strokeColorObject);
/*  629 */       this.g2.draw(s);
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
/*  644 */     showMethodWarning("box");
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
/*  661 */     showMethodWarning("sphere");
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
/*      */   public void bezierDetail(int detail) {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void curveDetail(int detail) {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  744 */     this.smooth = true;
/*  745 */     this.g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
/*  746 */       RenderingHints.VALUE_ANTIALIAS_ON);
/*  747 */     this.g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
/*      */     
/*  749 */       RenderingHints.VALUE_INTERPOLATION_BICUBIC);
/*      */   }
/*      */   
/*      */   public void noSmooth()
/*      */   {
/*  754 */     this.smooth = false;
/*  755 */     this.g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
/*  756 */       RenderingHints.VALUE_ANTIALIAS_OFF);
/*  757 */     this.g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
/*  758 */       RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
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
/*      */   protected void imageImpl(PImage who, float x1, float y1, float x2, float y2, int u1, int v1, int u2, int v2)
/*      */   {
/*  789 */     if ((who.width <= 0) || (who.height <= 0)) { return;
/*      */     }
/*  791 */     if (who.getCache(this) == null)
/*      */     {
/*  793 */       who.setCache(this, new ImageCache(who));
/*  794 */       who.updatePixels();
/*  795 */       who.modified = true;
/*      */     }
/*      */     
/*  798 */     ImageCache cash = (ImageCache)who.getCache(this);
/*      */     
/*      */ 
/*  801 */     if (((this.tint) && (!cash.tinted)) || 
/*  802 */       ((this.tint) && (cash.tintedColor != this.tintColor)) || (
/*  803 */       (!this.tint) && (cash.tinted)))
/*      */     {
/*  805 */       who.updatePixels();
/*      */     }
/*      */     
/*  808 */     if (who.modified) {
/*  809 */       cash.update(this.tint, this.tintColor);
/*  810 */       who.modified = false;
/*      */     }
/*      */     
/*  813 */     this.g2.drawImage(((ImageCache)who.getCache(this)).image, 
/*  814 */       (int)x1, (int)y1, (int)x2, (int)y2, 
/*  815 */       u1, v1, u2, v2, null);
/*      */   }
/*      */   
/*      */   class ImageCache
/*      */   {
/*      */     PImage source;
/*      */     boolean tinted;
/*      */     int tintedColor;
/*      */     int[] tintedPixels;
/*      */     BufferedImage image;
/*      */     
/*      */     public ImageCache(PImage source) {
/*  827 */       this.source = source;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public void update(boolean tint, int tintColor)
/*      */     {
/*  841 */       int bufferType = 2;
/*  842 */       boolean opaque = (tintColor & 0xFF000000) == -16777216;
/*  843 */       if ((this.source.format == 1) && (
/*  844 */         (!tint) || ((tint) && (opaque)))) {
/*  845 */         bufferType = 1;
/*      */       }
/*      */       
/*  848 */       boolean wrongType = (this.image != null) && (this.image.getType() != bufferType);
/*  849 */       if ((this.image == null) || (wrongType)) {
/*  850 */         this.image = new BufferedImage(this.source.width, this.source.height, bufferType);
/*      */       }
/*      */       
/*  853 */       WritableRaster wr = this.image.getRaster();
/*  854 */       if (tint) {
/*  855 */         if ((this.tintedPixels == null) || (this.tintedPixels.length != this.source.width)) {
/*  856 */           this.tintedPixels = new int[this.source.width];
/*      */         }
/*  858 */         int a2 = tintColor >> 24 & 0xFF;
/*  859 */         int r2 = tintColor >> 16 & 0xFF;
/*  860 */         int g2 = tintColor >> 8 & 0xFF;
/*  861 */         int b2 = tintColor & 0xFF;
/*      */         
/*  863 */         if (bufferType == 1)
/*      */         {
/*  865 */           int index = 0;
/*  866 */           for (int y = 0; y < this.source.height; y++) {
/*  867 */             for (int x = 0; x < this.source.width; x++) {
/*  868 */               int argb1 = this.source.pixels[(index++)];
/*  869 */               int r1 = argb1 >> 16 & 0xFF;
/*  870 */               int g1 = argb1 >> 8 & 0xFF;
/*  871 */               int b1 = argb1 & 0xFF;
/*      */               
/*  873 */               this.tintedPixels[x] = 
/*  874 */                 ((r2 * r1 & 0xFF00) << 8 | 
/*  875 */                 g2 * g1 & 0xFF00 | 
/*  876 */                 (b2 * b1 & 0xFF00) >> 8);
/*      */             }
/*  878 */             wr.setDataElements(0, y, this.source.width, 1, this.tintedPixels);
/*      */ 
/*      */ 
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */         }
/*  886 */         else if (bufferType == 2) {
/*  887 */           int index = 0;
/*  888 */           for (int y = 0; y < this.source.height; y++) {
/*  889 */             if (this.source.format == 1) {
/*  890 */               int alpha = tintColor & 0xFF000000;
/*  891 */               for (int x = 0; x < this.source.width; x++) {
/*  892 */                 int argb1 = this.source.pixels[(index++)];
/*  893 */                 int r1 = argb1 >> 16 & 0xFF;
/*  894 */                 int g1 = argb1 >> 8 & 0xFF;
/*  895 */                 int b1 = argb1 & 0xFF;
/*  896 */                 this.tintedPixels[x] = 
/*      */                 
/*      */ 
/*  899 */                   (alpha | (r2 * r1 & 0xFF00) << 8 | g2 * g1 & 0xFF00 | (b2 * b1 & 0xFF00) >> 8);
/*      */               }
/*  901 */             } else if (this.source.format == 2) {
/*  902 */               for (int x = 0; x < this.source.width; x++) {
/*  903 */                 int argb1 = this.source.pixels[(index++)];
/*  904 */                 int a1 = argb1 >> 24 & 0xFF;
/*  905 */                 int r1 = argb1 >> 16 & 0xFF;
/*  906 */                 int g1 = argb1 >> 8 & 0xFF;
/*  907 */                 int b1 = argb1 & 0xFF;
/*  908 */                 this.tintedPixels[x] = 
/*  909 */                   ((a2 * a1 & 0xFF00) << 16 | 
/*  910 */                   (r2 * r1 & 0xFF00) << 8 | 
/*  911 */                   g2 * g1 & 0xFF00 | 
/*  912 */                   (b2 * b1 & 0xFF00) >> 8);
/*      */               }
/*  914 */             } else if (this.source.format == 4) {
/*  915 */               int lower = tintColor & 0xFFFFFF;
/*  916 */               for (int x = 0; x < this.source.width; x++) {
/*  917 */                 int a1 = this.source.pixels[(index++)];
/*  918 */                 this.tintedPixels[x] = 
/*  919 */                   ((a2 * a1 & 0xFF00) << 16 | lower);
/*      */               }
/*      */             }
/*  922 */             wr.setDataElements(0, y, this.source.width, 1, this.tintedPixels);
/*      */           }
/*      */           
/*      */         }
/*      */         
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  931 */         wr.setDataElements(0, 0, this.source.width, this.source.height, this.source.pixels);
/*      */       }
/*  933 */       this.tinted = tint;
/*  934 */       this.tintedColor = tintColor;
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
/*      */   public float textAscent()
/*      */   {
/*  970 */     if (this.textFont == null) {
/*  971 */       defaultFontOrDeath("textAscent");
/*      */     }
/*  973 */     Font font = this.textFont.getFont();
/*  974 */     if (font == null) {
/*  975 */       return super.textAscent();
/*      */     }
/*  977 */     FontMetrics metrics = this.parent.getFontMetrics(font);
/*  978 */     return metrics.getAscent();
/*      */   }
/*      */   
/*      */   public float textDescent()
/*      */   {
/*  983 */     if (this.textFont == null) {
/*  984 */       defaultFontOrDeath("textAscent");
/*      */     }
/*  986 */     Font font = this.textFont.getFont();
/*  987 */     if (font == null) {
/*  988 */       return super.textDescent();
/*      */     }
/*  990 */     FontMetrics metrics = this.parent.getFontMetrics(font);
/*  991 */     return metrics.getDescent();
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
/*      */   protected boolean textModeCheck(int mode)
/*      */   {
/* 1008 */     return (mode == 4) || (mode == 256);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void textSize(float size)
/*      */   {
/* 1019 */     if (this.textFont == null) {
/* 1020 */       defaultFontOrDeath("textAscent", size);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1029 */     Font font = this.textFont.getFont();
/* 1030 */     if (font != null) {
/* 1031 */       Font dfont = font.deriveFont(size);
/* 1032 */       this.g2.setFont(dfont);
/* 1033 */       this.textFont.setFont(dfont);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1039 */     super.textSize(size);
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
/* 1050 */     Font font = this.textFont.getFont();
/* 1051 */     if (font == null) {
/* 1052 */       return super.textWidthImpl(buffer, start, stop);
/*      */     }
/*      */     
/* 1055 */     int length = stop - start;
/* 1056 */     FontMetrics metrics = this.g2.getFontMetrics(font);
/* 1057 */     return metrics.charsWidth(buffer, start, length);
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
/*      */   protected void textLineImpl(char[] buffer, int start, int stop, float x, float y)
/*      */   {
/* 1081 */     Font font = this.textFont.getFont();
/* 1082 */     if (font == null) {
/* 1083 */       super.textLineImpl(buffer, start, stop, x, y);
/* 1084 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1103 */     Object antialias = 
/* 1104 */       this.g2.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
/* 1105 */     if (antialias == null)
/*      */     {
/* 1107 */       antialias = RenderingHints.VALUE_ANTIALIAS_DEFAULT;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1113 */     this.g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
/* 1114 */       this.textFont.smooth ? 
/* 1115 */       RenderingHints.VALUE_ANTIALIAS_ON : 
/* 1116 */       RenderingHints.VALUE_ANTIALIAS_OFF);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1122 */     this.g2.setColor(this.fillColorObject);
/* 1123 */     int length = stop - start;
/* 1124 */     this.g2.drawChars(buffer, start, length, (int)(x + 0.5F), (int)(y + 0.5F));
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1137 */     this.g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, antialias);
/*      */     
/* 1139 */     this.textX = (x + textWidthImpl(buffer, start, stop));
/* 1140 */     this.textY = y;
/* 1141 */     this.textZ = 0.0F;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void pushMatrix()
/*      */   {
/* 1152 */     if (this.transformCount == this.transformStack.length) {
/* 1153 */       throw new RuntimeException("pushMatrix() cannot use push more than " + 
/* 1154 */         this.transformStack.length + " times");
/*      */     }
/* 1156 */     this.transformStack[this.transformCount] = this.g2.getTransform();
/* 1157 */     this.transformCount += 1;
/*      */   }
/*      */   
/*      */   public void popMatrix()
/*      */   {
/* 1162 */     if (this.transformCount == 0) {
/* 1163 */       throw new RuntimeException("missing a popMatrix() to go with that pushMatrix()");
/*      */     }
/*      */     
/* 1166 */     this.transformCount -= 1;
/* 1167 */     this.g2.setTransform(this.transformStack[this.transformCount]);
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
/* 1178 */     this.g2.translate(tx, ty);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void rotate(float angle)
/*      */   {
/* 1186 */     this.g2.rotate(angle);
/*      */   }
/*      */   
/*      */   public void rotateX(float angle)
/*      */   {
/* 1191 */     showDepthWarning("rotateX");
/*      */   }
/*      */   
/*      */   public void rotateY(float angle)
/*      */   {
/* 1196 */     showDepthWarning("rotateY");
/*      */   }
/*      */   
/*      */   public void rotateZ(float angle)
/*      */   {
/* 1201 */     showDepthWarning("rotateZ");
/*      */   }
/*      */   
/*      */   public void rotate(float angle, float vx, float vy, float vz)
/*      */   {
/* 1206 */     showVariationWarning("rotate");
/*      */   }
/*      */   
/*      */   public void scale(float s)
/*      */   {
/* 1211 */     this.g2.scale(s, s);
/*      */   }
/*      */   
/*      */   public void scale(float sx, float sy)
/*      */   {
/* 1216 */     this.g2.scale(sx, sy);
/*      */   }
/*      */   
/*      */   public void scale(float sx, float sy, float sz)
/*      */   {
/* 1221 */     showDepthWarningXYZ("scale");
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
/* 1232 */     this.g2.setTransform(new AffineTransform());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void applyMatrix(float n00, float n01, float n02, float n10, float n11, float n12)
/*      */   {
/* 1243 */     this.g2.transform(new AffineTransform(n00, n10, n01, n11, n02, n12));
/*      */   }
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
/* 1255 */     showVariationWarning("applyMatrix");
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
/* 1266 */     return getMatrix(null);
/*      */   }
/*      */   
/*      */   public PMatrix2D getMatrix(PMatrix2D target)
/*      */   {
/* 1271 */     if (target == null) {
/* 1272 */       target = new PMatrix2D();
/*      */     }
/* 1274 */     this.g2.getTransform().getMatrix(this.transform);
/* 1275 */     target.set((float)this.transform[0], (float)this.transform[2], (float)this.transform[4], 
/* 1276 */       (float)this.transform[1], (float)this.transform[3], (float)this.transform[5]);
/* 1277 */     return target;
/*      */   }
/*      */   
/*      */   public PMatrix3D getMatrix(PMatrix3D target)
/*      */   {
/* 1282 */     showVariationWarning("getMatrix");
/* 1283 */     return target;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setMatrix(PMatrix2D source)
/*      */   {
/* 1291 */     this.g2.setTransform(new AffineTransform(source.m00, source.m10, 
/* 1292 */       source.m01, source.m11, 
/* 1293 */       source.m02, source.m12));
/*      */   }
/*      */   
/*      */   public void setMatrix(PMatrix3D source)
/*      */   {
/* 1298 */     showVariationWarning("setMatrix");
/*      */   }
/*      */   
/*      */   public void printMatrix()
/*      */   {
/* 1303 */     getMatrix(null).print();
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
/*      */   public float screenX(float x, float y)
/*      */   {
/* 1342 */     this.g2.getTransform().getMatrix(this.transform);
/* 1343 */     return (float)this.transform[0] * x + (float)this.transform[2] * y + (float)this.transform[4];
/*      */   }
/*      */   
/*      */   public float screenY(float x, float y)
/*      */   {
/* 1348 */     this.g2.getTransform().getMatrix(this.transform);
/* 1349 */     return (float)this.transform[1] * x + (float)this.transform[3] * y + (float)this.transform[5];
/*      */   }
/*      */   
/*      */   public float screenX(float x, float y, float z)
/*      */   {
/* 1354 */     showDepthWarningXYZ("screenX");
/* 1355 */     return 0.0F;
/*      */   }
/*      */   
/*      */   public float screenY(float x, float y, float z)
/*      */   {
/* 1360 */     showDepthWarningXYZ("screenY");
/* 1361 */     return 0.0F;
/*      */   }
/*      */   
/*      */   public float screenZ(float x, float y, float z)
/*      */   {
/* 1366 */     showDepthWarningXYZ("screenZ");
/* 1367 */     return 0.0F;
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
/*      */   public void strokeCap(int cap)
/*      */   {
/* 1395 */     super.strokeCap(cap);
/* 1396 */     strokeImpl();
/*      */   }
/*      */   
/*      */   public void strokeJoin(int join)
/*      */   {
/* 1401 */     super.strokeJoin(join);
/* 1402 */     strokeImpl();
/*      */   }
/*      */   
/*      */   public void strokeWeight(float weight)
/*      */   {
/* 1407 */     super.strokeWeight(weight);
/* 1408 */     strokeImpl();
/*      */   }
/*      */   
/*      */   protected void strokeImpl()
/*      */   {
/* 1413 */     int cap = 0;
/* 1414 */     if (this.strokeCap == 2) {
/* 1415 */       cap = 1;
/* 1416 */     } else if (this.strokeCap == 4) {
/* 1417 */       cap = 2;
/*      */     }
/*      */     
/* 1420 */     int join = 2;
/* 1421 */     if (this.strokeJoin == 8) {
/* 1422 */       join = 0;
/* 1423 */     } else if (this.strokeJoin == 2) {
/* 1424 */       join = 1;
/*      */     }
/*      */     
/* 1427 */     this.g2.setStroke(new BasicStroke(this.strokeWeight, cap, join));
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
/*      */   protected void strokeFromCalc()
/*      */   {
/* 1440 */     super.strokeFromCalc();
/* 1441 */     this.strokeColorObject = new Color(this.strokeColor, true);
/* 1442 */     this.strokeGradient = false;
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
/*      */   protected void tintFromCalc()
/*      */   {
/* 1455 */     super.tintFromCalc();
/*      */     
/* 1457 */     this.tintColorObject = new Color(this.tintColor, true);
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
/*      */   protected void fillFromCalc()
/*      */   {
/* 1470 */     super.fillFromCalc();
/* 1471 */     this.fillColorObject = new Color(this.fillColor, true);
/* 1472 */     this.fillGradient = false;
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
/*      */   public void backgroundImpl()
/*      */   {
/* 1537 */     if (this.backgroundAlpha)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1544 */       WritableRaster raster = ((BufferedImage)this.image).getRaster();
/* 1545 */       if ((this.clearPixels == null) || (this.clearPixels.length < this.width)) {
/* 1546 */         this.clearPixels = new int[this.width];
/*      */       }
/* 1548 */       Arrays.fill(this.clearPixels, this.backgroundColor);
/* 1549 */       for (int i = 0; i < this.height; i++) {
/* 1550 */         raster.setDataElements(0, i, this.width, 1, this.clearPixels);
/*      */       }
/*      */       
/*      */     }
/*      */     else
/*      */     {
/* 1556 */       pushMatrix();
/* 1557 */       resetMatrix();
/* 1558 */       this.g2.setColor(new Color(this.backgroundColor));
/* 1559 */       this.g2.fillRect(0, 0, this.width, this.height);
/* 1560 */       popMatrix();
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
/*      */   public void beginRaw(PGraphics recorderRaw)
/*      */   {
/* 1613 */     showMethodWarning("beginRaw");
/*      */   }
/*      */   
/*      */   public void endRaw()
/*      */   {
/* 1618 */     showMethodWarning("endRaw");
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
/*      */   public void loadPixels()
/*      */   {
/* 1655 */     if ((this.pixels == null) || (this.pixels.length != this.width * this.height)) {
/* 1656 */       this.pixels = new int[this.width * this.height];
/*      */     }
/*      */     
/* 1659 */     WritableRaster raster = ((BufferedImage)this.image).getRaster();
/* 1660 */     raster.getDataElements(0, 0, this.width, this.height, this.pixels);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void updatePixels()
/*      */   {
/* 1672 */     WritableRaster raster = ((BufferedImage)this.image).getRaster();
/* 1673 */     raster.setDataElements(0, 0, this.width, this.height, this.pixels);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void updatePixels(int x, int y, int c, int d)
/*      */   {
/* 1685 */     if ((x != 0) || (y != 0) || (c != this.width) || (d != this.height))
/*      */     {
/* 1687 */       showVariationWarning("updatePixels(x, y, w, h)");
/*      */     }
/* 1689 */     updatePixels();
/*      */   }
/*      */   
/*      */   public void resize(int wide, int high)
/*      */   {
/* 1694 */     showMethodWarning("resize");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1704 */   static int[] getset = new int[1];
/*      */   
/*      */   public int get(int x, int y)
/*      */   {
/* 1708 */     if ((x < 0) || (y < 0) || (x >= this.width) || (y >= this.height)) { return 0;
/*      */     }
/* 1710 */     WritableRaster raster = ((BufferedImage)this.image).getRaster();
/* 1711 */     raster.getDataElements(x, y, getset);
/* 1712 */     return getset[0];
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public PImage getImpl(int x, int y, int w, int h)
/*      */   {
/* 1720 */     PImage output = new PImage(w, h);
/* 1721 */     output.parent = this.parent;
/*      */     
/*      */ 
/*      */ 
/* 1725 */     WritableRaster raster = ((BufferedImage)this.image).getRaster();
/* 1726 */     raster.getDataElements(x, y, w, h, output.pixels);
/*      */     
/* 1728 */     return output;
/*      */   }
/*      */   
/*      */   public PImage get()
/*      */   {
/* 1733 */     return get(0, 0, this.width, this.height);
/*      */   }
/*      */   
/*      */   public void set(int x, int y, int argb)
/*      */   {
/* 1738 */     if ((x < 0) || (y < 0) || (x >= this.width) || (y >= this.height)) { return;
/*      */     }
/* 1740 */     getset[0] = argb;
/* 1741 */     WritableRaster raster = ((BufferedImage)this.image).getRaster();
/* 1742 */     raster.setDataElements(x, y, getset);
/*      */   }
/*      */   
/*      */ 
/*      */   protected void setImpl(int dx, int dy, int sx, int sy, int sw, int sh, PImage src)
/*      */   {
/* 1748 */     WritableRaster raster = ((BufferedImage)this.image).getRaster();
/* 1749 */     if ((sx == 0) && (sy == 0) && (sw == src.width) && (sh == src.height)) {
/* 1750 */       raster.setDataElements(dx, dy, src.width, src.height, src.pixels);
/*      */     }
/*      */     else {
/* 1753 */       PImage temp = src.get(sx, sy, sw, sh);
/* 1754 */       raster.setDataElements(dx, dy, temp.width, temp.height, temp.pixels);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void mask(int[] alpha)
/*      */   {
/* 1766 */     showMethodWarning("mask");
/*      */   }
/*      */   
/*      */   public void mask(PImage alpha)
/*      */   {
/* 1771 */     showMethodWarning("mask");
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
/*      */   public void copy(int sx, int sy, int sw, int sh, int dx, int dy, int dw, int dh)
/*      */   {
/* 1798 */     if ((sw != dw) || (sh != dh))
/*      */     {
/* 1800 */       copy(this, sx, sy, sw, sh, dx, dy, dw, dh);
/*      */     }
/*      */     else {
/* 1803 */       dx -= sx;
/* 1804 */       dy -= sy;
/* 1805 */       this.g2.copyArea(sx, sy, sw, sh, dx, dy);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/AndrewKanarek/Desktop/MICHIGAN/KiefferWork/scripts-zibo/JavaApps/EllinghamDiagram/core.jar!/processing/core/PGraphicsJava2D.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */