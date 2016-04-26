/*      */ package processing.core;
/*      */ 
/*      */ import java.awt.Paint;
/*      */ import java.awt.PaintContext;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.RenderingHints;
/*      */ import java.awt.geom.AffineTransform;
/*      */ import java.awt.geom.Point2D;
/*      */ import java.awt.geom.Point2D.Float;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.awt.image.ColorModel;
/*      */ import java.awt.image.Raster;
/*      */ import java.awt.image.WritableRaster;
/*      */ import java.io.PrintStream;
/*      */ import java.util.HashMap;
/*      */ import processing.xml.XMLElement;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PShapeSVG
/*      */   extends PShape
/*      */ {
/*      */   XMLElement element;
/*      */   float opacity;
/*      */   float strokeOpacity;
/*      */   float fillOpacity;
/*      */   Gradient strokeGradient;
/*      */   Paint strokeGradientPaint;
/*      */   String strokeName;
/*      */   Gradient fillGradient;
/*      */   Paint fillGradientPaint;
/*      */   String fillName;
/*      */   
/*      */   public PShapeSVG(PApplet parent, String filename)
/*      */   {
/*  168 */     this(new XMLElement(parent, filename));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public PShapeSVG(XMLElement svg)
/*      */   {
/*  176 */     this(null, svg);
/*      */     
/*  178 */     if (!svg.getName().equals("svg")) {
/*  179 */       throw new RuntimeException("root is not <svg>, it's <" + svg.getName() + ">");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  184 */     String viewBoxStr = svg.getStringAttribute("viewBox");
/*  185 */     if (viewBoxStr != null) {
/*  186 */       int[] viewBox = PApplet.parseInt(PApplet.splitTokens(viewBoxStr));
/*  187 */       this.width = viewBox[2];
/*  188 */       this.height = viewBox[3];
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  194 */     String unitWidth = svg.getStringAttribute("width");
/*  195 */     String unitHeight = svg.getStringAttribute("height");
/*  196 */     if (unitWidth != null) {
/*  197 */       this.width = parseUnitSize(unitWidth);
/*  198 */       this.height = parseUnitSize(unitHeight);
/*      */     }
/*  200 */     else if ((this.width == 0.0F) || (this.height == 0.0F))
/*      */     {
/*  202 */       PGraphics.showWarning("The width and/or height is not readable in the <svg> tag of this file.");
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  207 */       this.width = 1.0F;
/*  208 */       this.height = 1.0F;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  213 */     parseChildren(svg);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public PShapeSVG(PShapeSVG parent, XMLElement properties)
/*      */   {
/*  220 */     this.parent = parent;
/*      */     
/*  222 */     if (parent == null)
/*      */     {
/*  224 */       this.stroke = false;
/*  225 */       this.strokeColor = -16777216;
/*  226 */       this.strokeWeight = 1.0F;
/*  227 */       this.strokeCap = 1;
/*  228 */       this.strokeJoin = 8;
/*  229 */       this.strokeGradient = null;
/*  230 */       this.strokeGradientPaint = null;
/*  231 */       this.strokeName = null;
/*      */       
/*  233 */       this.fill = true;
/*  234 */       this.fillColor = -16777216;
/*  235 */       this.fillGradient = null;
/*  236 */       this.fillGradientPaint = null;
/*  237 */       this.fillName = null;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  242 */       this.strokeOpacity = 1.0F;
/*  243 */       this.fillOpacity = 1.0F;
/*  244 */       this.opacity = 1.0F;
/*      */     }
/*      */     else {
/*  247 */       this.stroke = parent.stroke;
/*  248 */       this.strokeColor = parent.strokeColor;
/*  249 */       this.strokeWeight = parent.strokeWeight;
/*  250 */       this.strokeCap = parent.strokeCap;
/*  251 */       this.strokeJoin = parent.strokeJoin;
/*  252 */       this.strokeGradient = parent.strokeGradient;
/*  253 */       this.strokeGradientPaint = parent.strokeGradientPaint;
/*  254 */       this.strokeName = parent.strokeName;
/*      */       
/*  256 */       this.fill = parent.fill;
/*  257 */       this.fillColor = parent.fillColor;
/*  258 */       this.fillGradient = parent.fillGradient;
/*  259 */       this.fillGradientPaint = parent.fillGradientPaint;
/*  260 */       this.fillName = parent.fillName;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  265 */       this.opacity = parent.opacity;
/*      */     }
/*      */     
/*  268 */     this.element = properties;
/*  269 */     this.name = properties.getStringAttribute("id");
/*      */     
/*  271 */     if (this.name != null) {
/*      */       for (;;) {
/*  273 */         String[] m = PApplet.match(this.name, "_x([A-Za-z0-9]{2})_");
/*  274 */         if (m == null) break;
/*  275 */         char repair = (char)PApplet.unhex(m[1]);
/*  276 */         this.name = this.name.replace(m[0], repair);
/*      */       }
/*      */     }
/*      */     
/*  280 */     String displayStr = properties.getStringAttribute("display", "inline");
/*  281 */     this.visible = (!displayStr.equals("none"));
/*      */     
/*  283 */     String transformStr = properties.getStringAttribute("transform");
/*  284 */     if (transformStr != null) {
/*  285 */       this.matrix = parseMatrix(transformStr);
/*      */     }
/*      */     
/*  288 */     parseColors(properties);
/*  289 */     parseChildren(properties);
/*      */   }
/*      */   
/*      */   protected void parseChildren(XMLElement graphics)
/*      */   {
/*  294 */     XMLElement[] elements = graphics.getChildren();
/*  295 */     this.children = new PShape[elements.length];
/*  296 */     this.childCount = 0;
/*      */     XMLElement[] arrayOfXMLElement1;
/*  298 */     int j = (arrayOfXMLElement1 = elements).length; for (int i = 0; i < j; i++) { XMLElement elem = arrayOfXMLElement1[i];
/*  299 */       PShape kid = parseChild(elem);
/*  300 */       if (kid != null)
/*      */       {
/*      */ 
/*      */ 
/*  304 */         addChild(kid);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected PShape parseChild(XMLElement elem)
/*      */   {
/*  315 */     String name = elem.getName();
/*  316 */     PShapeSVG shape = null;
/*      */     
/*  318 */     if (name.equals("g"))
/*      */     {
/*  320 */       shape = new PShapeSVG(this, elem);
/*      */     }
/*  322 */     else if (name.equals("defs"))
/*      */     {
/*      */ 
/*      */ 
/*  326 */       shape = new PShapeSVG(this, elem);
/*      */     }
/*  328 */     else if (name.equals("line"))
/*      */     {
/*      */ 
/*  331 */       shape = new PShapeSVG(this, elem);
/*  332 */       shape.parseLine();
/*      */     }
/*  334 */     else if (name.equals("circle"))
/*      */     {
/*  336 */       shape = new PShapeSVG(this, elem);
/*  337 */       shape.parseEllipse(true);
/*      */     }
/*  339 */     else if (name.equals("ellipse"))
/*      */     {
/*  341 */       shape = new PShapeSVG(this, elem);
/*  342 */       shape.parseEllipse(false);
/*      */     }
/*  344 */     else if (name.equals("rect"))
/*      */     {
/*  346 */       shape = new PShapeSVG(this, elem);
/*  347 */       shape.parseRect();
/*      */     }
/*  349 */     else if (name.equals("polygon"))
/*      */     {
/*  351 */       shape = new PShapeSVG(this, elem);
/*  352 */       shape.parsePoly(true);
/*      */     }
/*  354 */     else if (name.equals("polyline"))
/*      */     {
/*  356 */       shape = new PShapeSVG(this, elem);
/*  357 */       shape.parsePoly(false);
/*      */     }
/*  359 */     else if (name.equals("path"))
/*      */     {
/*  361 */       shape = new PShapeSVG(this, elem);
/*  362 */       shape.parsePath();
/*      */     } else {
/*  364 */       if (name.equals("radialGradient")) {
/*  365 */         return new RadialGradient(this, elem);
/*      */       }
/*  367 */       if (name.equals("linearGradient")) {
/*  368 */         return new LinearGradient(this, elem);
/*      */       }
/*  370 */       if ((name.equals("text")) || (name.equals("font"))) {
/*  371 */         PGraphics.showWarning("Text and fonts in SVG files are not currently supported, convert text to outlines instead.");
/*      */ 
/*      */ 
/*      */       }
/*  375 */       else if (name.equals("filter")) {
/*  376 */         PGraphics.showWarning("Filters are not supported.");
/*      */       }
/*  378 */       else if (name.equals("mask")) {
/*  379 */         PGraphics.showWarning("Masks are not supported.");
/*      */       }
/*  381 */       else if (name.equals("pattern")) {
/*  382 */         PGraphics.showWarning("Patterns are not supported.");
/*      */       }
/*  384 */       else if (!name.equals("stop"))
/*      */       {
/*      */ 
/*  387 */         if (!name.equals("sodipodi:namedview"))
/*      */         {
/*      */ 
/*      */ 
/*  391 */           PGraphics.showWarning("Ignoring  <" + name + "> tag."); } }
/*      */     }
/*  393 */     return shape;
/*      */   }
/*      */   
/*      */   protected void parseLine()
/*      */   {
/*  398 */     this.primitive = 4;
/*  399 */     this.family = 1;
/*  400 */     this.params = new float[] {
/*  401 */       getFloatWithUnit(this.element, "x1"), 
/*  402 */       getFloatWithUnit(this.element, "y1"), 
/*  403 */       getFloatWithUnit(this.element, "x2"), 
/*  404 */       getFloatWithUnit(this.element, "y2") };
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void parseEllipse(boolean circle)
/*      */   {
/*  414 */     this.primitive = 31;
/*  415 */     this.family = 1;
/*  416 */     this.params = new float[4];
/*      */     
/*  418 */     this.params[0] = getFloatWithUnit(this.element, "cx");
/*  419 */     this.params[1] = getFloatWithUnit(this.element, "cy");
/*      */     float rx;
/*      */     float rx;
/*  422 */     float ry; if (circle) { float ry;
/*  423 */       rx = ry = getFloatWithUnit(this.element, "r");
/*      */     } else {
/*  425 */       rx = getFloatWithUnit(this.element, "rx");
/*  426 */       ry = getFloatWithUnit(this.element, "ry");
/*      */     }
/*  428 */     this.params[0] -= rx;
/*  429 */     this.params[1] -= ry;
/*      */     
/*  431 */     this.params[2] = (rx * 2.0F);
/*  432 */     this.params[3] = (ry * 2.0F);
/*      */   }
/*      */   
/*      */   protected void parseRect()
/*      */   {
/*  437 */     this.primitive = 30;
/*  438 */     this.family = 1;
/*  439 */     this.params = new float[] {
/*  440 */       getFloatWithUnit(this.element, "x"), 
/*  441 */       getFloatWithUnit(this.element, "y"), 
/*  442 */       getFloatWithUnit(this.element, "width"), 
/*  443 */       getFloatWithUnit(this.element, "height") };
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void parsePoly(boolean close)
/*      */   {
/*  453 */     this.family = 2;
/*  454 */     this.close = close;
/*      */     
/*  456 */     String pointsAttr = this.element.getStringAttribute("points");
/*  457 */     if (pointsAttr != null) {
/*  458 */       String[] pointsBuffer = PApplet.splitTokens(pointsAttr);
/*  459 */       this.vertexCount = pointsBuffer.length;
/*  460 */       this.vertices = new float[this.vertexCount][2];
/*  461 */       for (int i = 0; i < this.vertexCount; i++) {
/*  462 */         String[] pb = PApplet.split(pointsBuffer[i], ',');
/*  463 */         this.vertices[i][0] = Float.valueOf(pb[0]).floatValue();
/*  464 */         this.vertices[i][1] = Float.valueOf(pb[1]).floatValue();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   protected void parsePath()
/*      */   {
/*  471 */     this.family = 2;
/*  472 */     this.primitive = 0;
/*      */     
/*  474 */     String pathData = this.element.getStringAttribute("d");
/*  475 */     if (pathData == null) return;
/*  476 */     char[] pathDataChars = pathData.toCharArray();
/*      */     
/*  478 */     StringBuffer pathBuffer = new StringBuffer();
/*  479 */     boolean lastSeparate = false;
/*      */     
/*  481 */     for (int i = 0; i < pathDataChars.length; i++) {
/*  482 */       char c = pathDataChars[i];
/*  483 */       boolean separate = false;
/*      */       
/*  485 */       if ((c == 'M') || (c == 'm') || 
/*  486 */         (c == 'L') || (c == 'l') || 
/*  487 */         (c == 'H') || (c == 'h') || 
/*  488 */         (c == 'V') || (c == 'v') || 
/*  489 */         (c == 'C') || (c == 'c') || 
/*  490 */         (c == 'S') || (c == 's') || 
/*  491 */         (c == 'Q') || (c == 'q') || 
/*  492 */         (c == 'T') || (c == 't') || 
/*  493 */         (c == 'Z') || (c == 'z') || 
/*  494 */         (c == ',')) {
/*  495 */         separate = true;
/*  496 */         if (i != 0) {
/*  497 */           pathBuffer.append("|");
/*      */         }
/*      */       }
/*  500 */       if ((c == 'Z') || (c == 'z')) {
/*  501 */         separate = false;
/*      */       }
/*  503 */       if ((c == '-') && (!lastSeparate))
/*      */       {
/*      */ 
/*  506 */         if ((i == 0) || (pathDataChars[(i - 1)] != 'e')) {
/*  507 */           pathBuffer.append("|");
/*      */         }
/*      */       }
/*  510 */       if (c != ',') {
/*  511 */         pathBuffer.append(c);
/*      */       }
/*  513 */       if ((separate) && (c != ',') && (c != '-')) {
/*  514 */         pathBuffer.append("|");
/*      */       }
/*  516 */       lastSeparate = separate;
/*      */     }
/*      */     
/*      */ 
/*  520 */     String[] pathDataKeys = 
/*  521 */       PApplet.splitTokens(pathBuffer.toString(), "| \t\n\r\f ");
/*  522 */     this.vertices = new float[pathDataKeys.length][2];
/*  523 */     this.vertexCodes = new int[pathDataKeys.length];
/*      */     
/*  525 */     float cx = 0.0F;
/*  526 */     float cy = 0.0F;
/*  527 */     int i = 0;
/*      */     
/*  529 */     char implicitCommand = '\000';
/*      */     
/*  531 */     while (i < pathDataKeys.length) {
/*  532 */       char c = pathDataKeys[i].charAt(0);
/*  533 */       if (((c >= '0') && (c <= '9')) || ((c == '-') && (implicitCommand != 0))) {
/*  534 */         c = implicitCommand;
/*  535 */         i--;
/*      */       } else {
/*  537 */         implicitCommand = c;
/*      */       }
/*  539 */       switch (c)
/*      */       {
/*      */       case 'M': 
/*  542 */         cx = PApplet.parseFloat(pathDataKeys[(i + 1)]);
/*  543 */         cy = PApplet.parseFloat(pathDataKeys[(i + 2)]);
/*  544 */         parsePathMoveto(cx, cy);
/*  545 */         implicitCommand = 'L';
/*  546 */         i += 3;
/*  547 */         break;
/*      */       
/*      */       case 'm': 
/*  550 */         cx += PApplet.parseFloat(pathDataKeys[(i + 1)]);
/*  551 */         cy += PApplet.parseFloat(pathDataKeys[(i + 2)]);
/*  552 */         parsePathMoveto(cx, cy);
/*  553 */         implicitCommand = 'l';
/*  554 */         i += 3;
/*  555 */         break;
/*      */       
/*      */       case 'L': 
/*  558 */         cx = PApplet.parseFloat(pathDataKeys[(i + 1)]);
/*  559 */         cy = PApplet.parseFloat(pathDataKeys[(i + 2)]);
/*  560 */         parsePathLineto(cx, cy);
/*  561 */         i += 3;
/*  562 */         break;
/*      */       
/*      */       case 'l': 
/*  565 */         cx += PApplet.parseFloat(pathDataKeys[(i + 1)]);
/*  566 */         cy += PApplet.parseFloat(pathDataKeys[(i + 2)]);
/*  567 */         parsePathLineto(cx, cy);
/*  568 */         i += 3;
/*  569 */         break;
/*      */       
/*      */ 
/*      */       case 'H': 
/*  573 */         cx = PApplet.parseFloat(pathDataKeys[(i + 1)]);
/*  574 */         parsePathLineto(cx, cy);
/*  575 */         i += 2;
/*  576 */         break;
/*      */       
/*      */ 
/*      */       case 'h': 
/*  580 */         cx += PApplet.parseFloat(pathDataKeys[(i + 1)]);
/*  581 */         parsePathLineto(cx, cy);
/*  582 */         i += 2;
/*  583 */         break;
/*      */       
/*      */       case 'V': 
/*  586 */         cy = PApplet.parseFloat(pathDataKeys[(i + 1)]);
/*  587 */         parsePathLineto(cx, cy);
/*  588 */         i += 2;
/*  589 */         break;
/*      */       
/*      */       case 'v': 
/*  592 */         cy += PApplet.parseFloat(pathDataKeys[(i + 1)]);
/*  593 */         parsePathLineto(cx, cy);
/*  594 */         i += 2;
/*  595 */         break;
/*      */       
/*      */ 
/*      */       case 'C': 
/*  599 */         float ctrlX1 = PApplet.parseFloat(pathDataKeys[(i + 1)]);
/*  600 */         float ctrlY1 = PApplet.parseFloat(pathDataKeys[(i + 2)]);
/*  601 */         float ctrlX2 = PApplet.parseFloat(pathDataKeys[(i + 3)]);
/*  602 */         float ctrlY2 = PApplet.parseFloat(pathDataKeys[(i + 4)]);
/*  603 */         float endX = PApplet.parseFloat(pathDataKeys[(i + 5)]);
/*  604 */         float endY = PApplet.parseFloat(pathDataKeys[(i + 6)]);
/*  605 */         parsePathCurveto(ctrlX1, ctrlY1, ctrlX2, ctrlY2, endX, endY);
/*  606 */         cx = endX;
/*  607 */         cy = endY;
/*  608 */         i += 7;
/*      */         
/*  610 */         break;
/*      */       
/*      */ 
/*      */       case 'c': 
/*  614 */         float ctrlX1 = cx + PApplet.parseFloat(pathDataKeys[(i + 1)]);
/*  615 */         float ctrlY1 = cy + PApplet.parseFloat(pathDataKeys[(i + 2)]);
/*  616 */         float ctrlX2 = cx + PApplet.parseFloat(pathDataKeys[(i + 3)]);
/*  617 */         float ctrlY2 = cy + PApplet.parseFloat(pathDataKeys[(i + 4)]);
/*  618 */         float endX = cx + PApplet.parseFloat(pathDataKeys[(i + 5)]);
/*  619 */         float endY = cy + PApplet.parseFloat(pathDataKeys[(i + 6)]);
/*  620 */         parsePathCurveto(ctrlX1, ctrlY1, ctrlX2, ctrlY2, endX, endY);
/*  621 */         cx = endX;
/*  622 */         cy = endY;
/*  623 */         i += 7;
/*      */         
/*  625 */         break;
/*      */       
/*      */ 
/*      */       case 'S': 
/*  629 */         float ppx = this.vertices[(this.vertexCount - 2)][0];
/*  630 */         float ppy = this.vertices[(this.vertexCount - 2)][1];
/*  631 */         float px = this.vertices[(this.vertexCount - 1)][0];
/*  632 */         float py = this.vertices[(this.vertexCount - 1)][1];
/*  633 */         float ctrlX1 = px + (px - ppx);
/*  634 */         float ctrlY1 = py + (py - ppy);
/*  635 */         float ctrlX2 = PApplet.parseFloat(pathDataKeys[(i + 1)]);
/*  636 */         float ctrlY2 = PApplet.parseFloat(pathDataKeys[(i + 2)]);
/*  637 */         float endX = PApplet.parseFloat(pathDataKeys[(i + 3)]);
/*  638 */         float endY = PApplet.parseFloat(pathDataKeys[(i + 4)]);
/*  639 */         parsePathCurveto(ctrlX1, ctrlY1, ctrlX2, ctrlY2, endX, endY);
/*  640 */         cx = endX;
/*  641 */         cy = endY;
/*  642 */         i += 5;
/*      */         
/*  644 */         break;
/*      */       
/*      */ 
/*      */       case 's': 
/*  648 */         float ppx = this.vertices[(this.vertexCount - 2)][0];
/*  649 */         float ppy = this.vertices[(this.vertexCount - 2)][1];
/*  650 */         float px = this.vertices[(this.vertexCount - 1)][0];
/*  651 */         float py = this.vertices[(this.vertexCount - 1)][1];
/*  652 */         float ctrlX1 = px + (px - ppx);
/*  653 */         float ctrlY1 = py + (py - ppy);
/*  654 */         float ctrlX2 = cx + PApplet.parseFloat(pathDataKeys[(i + 1)]);
/*  655 */         float ctrlY2 = cy + PApplet.parseFloat(pathDataKeys[(i + 2)]);
/*  656 */         float endX = cx + PApplet.parseFloat(pathDataKeys[(i + 3)]);
/*  657 */         float endY = cy + PApplet.parseFloat(pathDataKeys[(i + 4)]);
/*  658 */         parsePathCurveto(ctrlX1, ctrlY1, ctrlX2, ctrlY2, endX, endY);
/*  659 */         cx = endX;
/*  660 */         cy = endY;
/*  661 */         i += 5;
/*      */         
/*  663 */         break;
/*      */       
/*      */ 
/*      */       case 'Q': 
/*  667 */         float ctrlX = PApplet.parseFloat(pathDataKeys[(i + 1)]);
/*  668 */         float ctrlY = PApplet.parseFloat(pathDataKeys[(i + 2)]);
/*  669 */         float endX = PApplet.parseFloat(pathDataKeys[(i + 3)]);
/*  670 */         float endY = PApplet.parseFloat(pathDataKeys[(i + 4)]);
/*  671 */         parsePathQuadto(cx, cy, ctrlX, ctrlY, endX, endY);
/*  672 */         cx = endX;
/*  673 */         cy = endY;
/*  674 */         i += 5;
/*      */         
/*  676 */         break;
/*      */       
/*      */ 
/*      */       case 'q': 
/*  680 */         float ctrlX = cx + PApplet.parseFloat(pathDataKeys[(i + 1)]);
/*  681 */         float ctrlY = cy + PApplet.parseFloat(pathDataKeys[(i + 2)]);
/*  682 */         float endX = cx + PApplet.parseFloat(pathDataKeys[(i + 3)]);
/*  683 */         float endY = cy + PApplet.parseFloat(pathDataKeys[(i + 4)]);
/*  684 */         parsePathQuadto(cx, cy, ctrlX, ctrlY, endX, endY);
/*  685 */         cx = endX;
/*  686 */         cy = endY;
/*  687 */         i += 5;
/*      */         
/*  689 */         break;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       case 'T': 
/*  698 */         float ppx = this.vertices[(this.vertexCount - 2)][0];
/*  699 */         float ppy = this.vertices[(this.vertexCount - 2)][1];
/*  700 */         float px = this.vertices[(this.vertexCount - 1)][0];
/*  701 */         float py = this.vertices[(this.vertexCount - 1)][1];
/*  702 */         float ctrlX = px + (px - ppx);
/*  703 */         float ctrlY = py + (py - ppy);
/*  704 */         float endX = PApplet.parseFloat(pathDataKeys[(i + 1)]);
/*  705 */         float endY = PApplet.parseFloat(pathDataKeys[(i + 2)]);
/*  706 */         parsePathQuadto(cx, cy, ctrlX, ctrlY, endX, endY);
/*  707 */         cx = endX;
/*  708 */         cy = endY;
/*  709 */         i += 3;
/*      */         
/*  711 */         break;
/*      */       
/*      */ 
/*      */       case 't': 
/*  715 */         float ppx = this.vertices[(this.vertexCount - 2)][0];
/*  716 */         float ppy = this.vertices[(this.vertexCount - 2)][1];
/*  717 */         float px = this.vertices[(this.vertexCount - 1)][0];
/*  718 */         float py = this.vertices[(this.vertexCount - 1)][1];
/*  719 */         float ctrlX = px + (px - ppx);
/*  720 */         float ctrlY = py + (py - ppy);
/*  721 */         float endX = cx + PApplet.parseFloat(pathDataKeys[(i + 1)]);
/*  722 */         float endY = cy + PApplet.parseFloat(pathDataKeys[(i + 2)]);
/*  723 */         parsePathQuadto(cx, cy, ctrlX, ctrlY, endX, endY);
/*  724 */         cx = endX;
/*  725 */         cy = endY;
/*  726 */         i += 3;
/*      */         
/*  728 */         break;
/*      */       
/*      */       case 'Z': 
/*      */       case 'z': 
/*  732 */         this.close = true;
/*  733 */         i++;
/*  734 */         break;
/*      */       
/*      */       default: 
/*  737 */         String parsed = 
/*  738 */           PApplet.join(PApplet.subset(pathDataKeys, 0, i), ",");
/*  739 */         String unparsed = 
/*  740 */           PApplet.join(PApplet.subset(pathDataKeys, i), ",");
/*  741 */         System.err.println("parsed: " + parsed);
/*  742 */         System.err.println("unparsed: " + unparsed);
/*  743 */         if ((pathDataKeys[i].equals("a")) || (pathDataKeys[i].equals("A"))) {
/*  744 */           String msg = "Sorry, elliptical arc support for SVG files is not yet implemented (See bug #996 for details)";
/*      */           
/*  746 */           throw new RuntimeException(msg);
/*      */         }
/*  748 */         throw new RuntimeException("shape command not handled: " + pathDataKeys[i]);
/*      */       }
/*      */       
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
/*      */   private void parsePathVertex(float x, float y)
/*      */   {
/*  764 */     if (this.vertexCount == this.vertices.length)
/*      */     {
/*  766 */       float[][] temp = new float[this.vertexCount << 1][2];
/*  767 */       System.arraycopy(this.vertices, 0, temp, 0, this.vertexCount);
/*  768 */       this.vertices = temp;
/*      */     }
/*  770 */     this.vertices[this.vertexCount][0] = x;
/*  771 */     this.vertices[this.vertexCount][1] = y;
/*  772 */     this.vertexCount += 1;
/*      */   }
/*      */   
/*      */   private void parsePathCode(int what)
/*      */   {
/*  777 */     if (this.vertexCodeCount == this.vertexCodes.length) {
/*  778 */       this.vertexCodes = PApplet.expand(this.vertexCodes);
/*      */     }
/*  780 */     this.vertexCodes[(this.vertexCodeCount++)] = what;
/*      */   }
/*      */   
/*      */   private void parsePathMoveto(float px, float py)
/*      */   {
/*  785 */     if (this.vertexCount > 0) {
/*  786 */       parsePathCode(3);
/*      */     }
/*  788 */     parsePathCode(0);
/*  789 */     parsePathVertex(px, py);
/*      */   }
/*      */   
/*      */   private void parsePathLineto(float px, float py)
/*      */   {
/*  794 */     parsePathCode(0);
/*  795 */     parsePathVertex(px, py);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void parsePathCurveto(float x1, float y1, float x2, float y2, float x3, float y3)
/*      */   {
/*  802 */     parsePathCode(1);
/*  803 */     parsePathVertex(x1, y1);
/*  804 */     parsePathVertex(x2, y2);
/*  805 */     parsePathVertex(x3, y3);
/*      */   }
/*      */   
/*      */ 
/*      */   private void parsePathQuadto(float x1, float y1, float cx, float cy, float x2, float y2)
/*      */   {
/*  811 */     parsePathCode(1);
/*      */     
/*  813 */     parsePathVertex(x1 + (cx - x1) * 2.0F / 3.0F, y1 + (cy - y1) * 2.0F / 3.0F);
/*  814 */     parsePathVertex(x2 + (cx - x2) * 2.0F / 3.0F, y2 + (cy - y2) * 2.0F / 3.0F);
/*  815 */     parsePathVertex(x2, y2);
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
/*      */   protected static PMatrix2D parseMatrix(String matrixStr)
/*      */   {
/*  829 */     String[] pieces = PApplet.match(matrixStr, "\\s*(\\w+)\\((.*)\\)");
/*  830 */     if (pieces == null) {
/*  831 */       System.err.println("Could not parse transform " + matrixStr);
/*  832 */       return null;
/*      */     }
/*  834 */     float[] m = PApplet.parseFloat(PApplet.splitTokens(pieces[2], ", "));
/*  835 */     if (pieces[1].equals("matrix")) {
/*  836 */       return new PMatrix2D(m[0], m[2], m[4], m[1], m[3], m[5]);
/*      */     }
/*  838 */     if (pieces[1].equals("translate")) {
/*  839 */       float tx = m[0];
/*  840 */       float ty = m.length == 2 ? m[1] : m[0];
/*      */       
/*  842 */       return new PMatrix2D(1.0F, 0.0F, tx, 0.0F, 1.0F, ty);
/*      */     }
/*  844 */     if (pieces[1].equals("scale")) {
/*  845 */       float sx = m[0];
/*  846 */       float sy = m.length == 2 ? m[1] : m[0];
/*      */       
/*  848 */       return new PMatrix2D(sx, 0.0F, 0.0F, 0.0F, sy, 0.0F);
/*      */     }
/*  850 */     if (pieces[1].equals("rotate")) {
/*  851 */       float angle = m[0];
/*      */       
/*  853 */       if (m.length == 1) {
/*  854 */         float c = PApplet.cos(angle);
/*  855 */         float s = PApplet.sin(angle);
/*      */         
/*  857 */         return new PMatrix2D(c, -s, 0.0F, s, c, 0.0F);
/*      */       }
/*  859 */       if (m.length == 3) {
/*  860 */         PMatrix2D mat = new PMatrix2D(0.0F, 1.0F, m[1], 1.0F, 0.0F, m[2]);
/*  861 */         mat.rotate(m[0]);
/*  862 */         mat.translate(-m[1], -m[2]);
/*  863 */         return mat;
/*      */       }
/*      */     } else {
/*  866 */       if (pieces[1].equals("skewX")) {
/*  867 */         return new PMatrix2D(1.0F, 0.0F, 1.0F, PApplet.tan(m[0]), 0.0F, 0.0F);
/*      */       }
/*  869 */       if (pieces[1].equals("skewY"))
/*  870 */         return new PMatrix2D(1.0F, 0.0F, 1.0F, 0.0F, PApplet.tan(m[0]), 0.0F);
/*      */     }
/*  872 */     return null;
/*      */   }
/*      */   
/*      */   protected void parseColors(XMLElement properties)
/*      */   {
/*  877 */     if (properties.hasAttribute("opacity")) {
/*  878 */       String opacityText = properties.getStringAttribute("opacity");
/*  879 */       setOpacity(opacityText);
/*      */     }
/*      */     
/*  882 */     if (properties.hasAttribute("stroke")) {
/*  883 */       String strokeText = properties.getStringAttribute("stroke");
/*  884 */       setColor(strokeText, false);
/*      */     }
/*      */     
/*  887 */     if (properties.hasAttribute("stroke-opacity")) {
/*  888 */       String strokeOpacityText = properties.getStringAttribute("stroke-opacity");
/*  889 */       setStrokeOpacity(strokeOpacityText);
/*      */     }
/*      */     
/*  892 */     if (properties.hasAttribute("stroke-width"))
/*      */     {
/*  894 */       String lineweight = properties.getStringAttribute("stroke-width");
/*  895 */       setStrokeWeight(lineweight);
/*      */     }
/*      */     
/*  898 */     if (properties.hasAttribute("stroke-linejoin")) {
/*  899 */       String linejoin = properties.getStringAttribute("stroke-linejoin");
/*  900 */       setStrokeJoin(linejoin);
/*      */     }
/*      */     
/*  903 */     if (properties.hasAttribute("stroke-linecap")) {
/*  904 */       String linecap = properties.getStringAttribute("stroke-linecap");
/*  905 */       setStrokeCap(linecap);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  910 */     if (properties.hasAttribute("fill")) {
/*  911 */       String fillText = properties.getStringAttribute("fill");
/*  912 */       setColor(fillText, true);
/*      */     }
/*      */     
/*  915 */     if (properties.hasAttribute("fill-opacity")) {
/*  916 */       String fillOpacityText = properties.getStringAttribute("fill-opacity");
/*  917 */       setFillOpacity(fillOpacityText);
/*      */     }
/*      */     
/*  920 */     if (properties.hasAttribute("style")) {
/*  921 */       String styleText = properties.getStringAttribute("style");
/*  922 */       String[] styleTokens = PApplet.splitTokens(styleText, ";");
/*      */       
/*      */ 
/*  925 */       for (int i = 0; i < styleTokens.length; i++) {
/*  926 */         String[] tokens = PApplet.splitTokens(styleTokens[i], ":");
/*      */         
/*      */ 
/*  929 */         tokens[0] = PApplet.trim(tokens[0]);
/*      */         
/*  931 */         if (tokens[0].equals("fill")) {
/*  932 */           setColor(tokens[1], true);
/*      */         }
/*  934 */         else if (tokens[0].equals("fill-opacity")) {
/*  935 */           setFillOpacity(tokens[1]);
/*      */         }
/*  937 */         else if (tokens[0].equals("stroke")) {
/*  938 */           setColor(tokens[1], false);
/*      */         }
/*  940 */         else if (tokens[0].equals("stroke-width")) {
/*  941 */           setStrokeWeight(tokens[1]);
/*      */         }
/*  943 */         else if (tokens[0].equals("stroke-linecap")) {
/*  944 */           setStrokeCap(tokens[1]);
/*      */         }
/*  946 */         else if (tokens[0].equals("stroke-linejoin")) {
/*  947 */           setStrokeJoin(tokens[1]);
/*      */         }
/*  949 */         else if (tokens[0].equals("stroke-opacity")) {
/*  950 */           setStrokeOpacity(tokens[1]);
/*      */         }
/*  952 */         else if (tokens[0].equals("opacity")) {
/*  953 */           setOpacity(tokens[1]);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   void setOpacity(String opacityText)
/*      */   {
/*  964 */     this.opacity = PApplet.parseFloat(opacityText);
/*  965 */     this.strokeColor = ((int)(this.opacity * 255.0F) << 24 | this.strokeColor & 0xFFFFFF);
/*  966 */     this.fillColor = ((int)(this.opacity * 255.0F) << 24 | this.fillColor & 0xFFFFFF);
/*      */   }
/*      */   
/*      */   void setStrokeWeight(String lineweight)
/*      */   {
/*  971 */     this.strokeWeight = parseUnitSize(lineweight);
/*      */   }
/*      */   
/*      */   void setStrokeOpacity(String opacityText)
/*      */   {
/*  976 */     this.strokeOpacity = PApplet.parseFloat(opacityText);
/*  977 */     this.strokeColor = ((int)(this.strokeOpacity * 255.0F) << 24 | this.strokeColor & 0xFFFFFF);
/*      */   }
/*      */   
/*      */   void setStrokeJoin(String linejoin)
/*      */   {
/*  982 */     if (!linejoin.equals("inherit"))
/*      */     {
/*      */ 
/*  985 */       if (linejoin.equals("miter")) {
/*  986 */         this.strokeJoin = 8;
/*      */       }
/*  988 */       else if (linejoin.equals("round")) {
/*  989 */         this.strokeJoin = 2;
/*      */       }
/*  991 */       else if (linejoin.equals("bevel")) {
/*  992 */         this.strokeJoin = 32;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   void setStrokeCap(String linecap) {
/*  998 */     if (!linecap.equals("inherit"))
/*      */     {
/*      */ 
/* 1001 */       if (linecap.equals("butt")) {
/* 1002 */         this.strokeCap = 1;
/*      */       }
/* 1004 */       else if (linecap.equals("round")) {
/* 1005 */         this.strokeCap = 2;
/*      */       }
/* 1007 */       else if (linecap.equals("square")) {
/* 1008 */         this.strokeCap = 4;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   void setFillOpacity(String opacityText) {
/* 1014 */     this.fillOpacity = PApplet.parseFloat(opacityText);
/* 1015 */     this.fillColor = ((int)(this.fillOpacity * 255.0F) << 24 | this.fillColor & 0xFFFFFF);
/*      */   }
/*      */   
/*      */   void setColor(String colorText, boolean isFill)
/*      */   {
/* 1020 */     int opacityMask = this.fillColor & 0xFF000000;
/* 1021 */     boolean visible = true;
/* 1022 */     int color = 0;
/* 1023 */     String name = "";
/* 1024 */     Gradient gradient = null;
/* 1025 */     Paint paint = null;
/* 1026 */     if (colorText.equals("none")) {
/* 1027 */       visible = false;
/* 1028 */     } else if (colorText.equals("black")) {
/* 1029 */       color = opacityMask;
/* 1030 */     } else if (colorText.equals("white")) {
/* 1031 */       color = opacityMask | 0xFFFFFF;
/* 1032 */     } else if (colorText.startsWith("#")) {
/* 1033 */       if (colorText.length() == 4)
/*      */       {
/* 1035 */         colorText = colorText.replaceAll("^#(.)(.)(.)$", "#$1$1$2$2$3$3");
/*      */       }
/* 1037 */       color = opacityMask | 
/* 1038 */         Integer.parseInt(colorText.substring(1), 16) & 0xFFFFFF;
/*      */     }
/* 1040 */     else if (colorText.startsWith("rgb")) {
/* 1041 */       color = opacityMask | parseRGB(colorText);
/* 1042 */     } else if (colorText.startsWith("url(#")) {
/* 1043 */       name = colorText.substring(5, colorText.length() - 1);
/*      */       
/* 1045 */       Object object = findChild(name);
/*      */       
/* 1047 */       if ((object instanceof Gradient)) {
/* 1048 */         gradient = (Gradient)object;
/* 1049 */         paint = calcGradientPaint(gradient);
/*      */       }
/*      */       else
/*      */       {
/* 1053 */         System.err.println("url " + name + " refers to unexpected data: " + object);
/*      */       }
/*      */     }
/* 1056 */     if (isFill) {
/* 1057 */       this.fill = visible;
/* 1058 */       this.fillColor = color;
/* 1059 */       this.fillName = name;
/* 1060 */       this.fillGradient = gradient;
/* 1061 */       this.fillGradientPaint = paint;
/*      */     } else {
/* 1063 */       this.stroke = visible;
/* 1064 */       this.strokeColor = color;
/* 1065 */       this.strokeName = name;
/* 1066 */       this.strokeGradient = gradient;
/* 1067 */       this.strokeGradientPaint = paint;
/*      */     }
/*      */   }
/*      */   
/*      */   protected static int parseRGB(String what)
/*      */   {
/* 1073 */     int leftParen = what.indexOf('(') + 1;
/* 1074 */     int rightParen = what.indexOf(')');
/* 1075 */     String sub = what.substring(leftParen, rightParen);
/* 1076 */     int[] values = PApplet.parseInt(PApplet.splitTokens(sub, ", "));
/* 1077 */     return values[0] << 16 | values[1] << 8 | values[2];
/*      */   }
/*      */   
/*      */   protected static HashMap<String, String> parseStyleAttributes(String style)
/*      */   {
/* 1082 */     HashMap<String, String> table = new HashMap();
/* 1083 */     String[] pieces = style.split(";");
/* 1084 */     for (int i = 0; i < pieces.length; i++) {
/* 1085 */       String[] parts = pieces[i].split(":");
/* 1086 */       table.put(parts[0], parts[1]);
/*      */     }
/* 1088 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static float getFloatWithUnit(XMLElement element, String attribute)
/*      */   {
/* 1100 */     String val = element.getStringAttribute(attribute);
/* 1101 */     return val == null ? 0.0F : parseUnitSize(val);
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
/*      */   protected static float parseUnitSize(String text)
/*      */   {
/* 1118 */     int len = text.length() - 2;
/*      */     
/* 1120 */     if (text.endsWith("pt"))
/* 1121 */       return PApplet.parseFloat(text.substring(0, len)) * 1.25F;
/* 1122 */     if (text.endsWith("pc"))
/* 1123 */       return PApplet.parseFloat(text.substring(0, len)) * 15.0F;
/* 1124 */     if (text.endsWith("mm"))
/* 1125 */       return PApplet.parseFloat(text.substring(0, len)) * 3.543307F;
/* 1126 */     if (text.endsWith("cm"))
/* 1127 */       return PApplet.parseFloat(text.substring(0, len)) * 35.43307F;
/* 1128 */     if (text.endsWith("in"))
/* 1129 */       return PApplet.parseFloat(text.substring(0, len)) * 90.0F;
/* 1130 */     if (text.endsWith("px")) {
/* 1131 */       return PApplet.parseFloat(text.substring(0, len));
/*      */     }
/* 1133 */     return PApplet.parseFloat(text);
/*      */   }
/*      */   
/*      */ 
/*      */   static class Gradient
/*      */     extends PShapeSVG
/*      */   {
/*      */     AffineTransform transform;
/*      */     
/*      */     float[] offset;
/*      */     
/*      */     int[] color;
/*      */     int count;
/*      */     
/*      */     public Gradient(PShapeSVG parent, XMLElement properties)
/*      */     {
/* 1149 */       super(properties);
/*      */       
/* 1151 */       XMLElement[] elements = properties.getChildren();
/* 1152 */       this.offset = new float[elements.length];
/* 1153 */       this.color = new int[elements.length];
/*      */       
/*      */ 
/* 1156 */       for (int i = 0; i < elements.length; i++) {
/* 1157 */         XMLElement elem = elements[i];
/* 1158 */         String name = elem.getName();
/* 1159 */         if (name.equals("stop")) {
/* 1160 */           String offsetAttr = elem.getStringAttribute("offset");
/* 1161 */           float div = 1.0F;
/* 1162 */           if (offsetAttr.endsWith("%")) {
/* 1163 */             div = 100.0F;
/* 1164 */             offsetAttr = offsetAttr.substring(0, offsetAttr.length() - 1);
/*      */           }
/* 1166 */           this.offset[this.count] = (PApplet.parseFloat(offsetAttr) / div);
/* 1167 */           String style = elem.getStringAttribute("style");
/* 1168 */           HashMap<String, String> styles = parseStyleAttributes(style);
/*      */           
/* 1170 */           String colorStr = (String)styles.get("stop-color");
/* 1171 */           if (colorStr == null) colorStr = "#000000";
/* 1172 */           String opacityStr = (String)styles.get("stop-opacity");
/* 1173 */           if (opacityStr == null) opacityStr = "1";
/* 1174 */           int tupacity = (int)(PApplet.parseFloat(opacityStr) * 255.0F);
/* 1175 */           this.color[this.count] = 
/* 1176 */             (tupacity << 24 | Integer.parseInt(colorStr.substring(1), 16));
/* 1177 */           this.count += 1;
/*      */         }
/*      */       }
/* 1180 */       this.offset = PApplet.subset(this.offset, 0, this.count);
/* 1181 */       this.color = PApplet.subset(this.color, 0, this.count);
/*      */     }
/*      */   }
/*      */   
/*      */   class LinearGradient extends PShapeSVG.Gradient { float x1;
/*      */     float y1;
/*      */     float x2;
/*      */     float y2;
/*      */     
/* 1190 */     public LinearGradient(PShapeSVG parent, XMLElement properties) { super(properties);
/*      */       
/* 1192 */       this.x1 = getFloatWithUnit(properties, "x1");
/* 1193 */       this.y1 = getFloatWithUnit(properties, "y1");
/* 1194 */       this.x2 = getFloatWithUnit(properties, "x2");
/* 1195 */       this.y2 = getFloatWithUnit(properties, "y2");
/*      */       
/* 1197 */       String transformStr = 
/* 1198 */         properties.getStringAttribute("gradientTransform");
/*      */       
/* 1200 */       if (transformStr != null) {
/* 1201 */         float[] t = parseMatrix(transformStr).get(null);
/* 1202 */         this.transform = new AffineTransform(t[0], t[3], t[1], t[4], t[2], t[5]);
/*      */         
/* 1204 */         Point2D t1 = this.transform.transform(new Point2D.Float(this.x1, this.y1), null);
/* 1205 */         Point2D t2 = this.transform.transform(new Point2D.Float(this.x2, this.y2), null);
/*      */         
/* 1207 */         this.x1 = ((float)t1.getX());
/* 1208 */         this.y1 = ((float)t1.getY());
/* 1209 */         this.x2 = ((float)t2.getX());
/* 1210 */         this.y2 = ((float)t2.getY());
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   class RadialGradient extends PShapeSVG.Gradient {
/*      */     float cx;
/*      */     float cy;
/*      */     float r;
/*      */     
/* 1220 */     public RadialGradient(PShapeSVG parent, XMLElement properties) { super(properties);
/*      */       
/* 1222 */       this.cx = getFloatWithUnit(properties, "cx");
/* 1223 */       this.cy = getFloatWithUnit(properties, "cy");
/* 1224 */       this.r = getFloatWithUnit(properties, "r");
/*      */       
/* 1226 */       String transformStr = 
/* 1227 */         properties.getStringAttribute("gradientTransform");
/*      */       
/* 1229 */       if (transformStr != null) {
/* 1230 */         float[] t = parseMatrix(transformStr).get(null);
/* 1231 */         this.transform = new AffineTransform(t[0], t[3], t[1], t[4], t[2], t[5]);
/*      */         
/* 1233 */         Point2D t1 = this.transform.transform(new Point2D.Float(this.cx, this.cy), null);
/* 1234 */         Point2D t2 = this.transform.transform(new Point2D.Float(this.cx + this.r, this.cy), null);
/*      */         
/* 1236 */         this.cx = ((float)t1.getX());
/* 1237 */         this.cy = ((float)t1.getY());
/* 1238 */         this.r = ((float)(t2.getX() - t1.getX()));
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   class LinearGradientPaint implements Paint
/*      */   {
/*      */     float x1;
/*      */     float y1;
/*      */     float x2;
/*      */     float y2;
/*      */     float[] offset;
/*      */     int[] color;
/*      */     int count;
/*      */     float opacity;
/*      */     
/*      */     public LinearGradientPaint(float x1, float y1, float x2, float y2, float[] offset, int[] color, int count, float opacity) {
/* 1255 */       this.x1 = x1;
/* 1256 */       this.y1 = y1;
/* 1257 */       this.x2 = x2;
/* 1258 */       this.y2 = y2;
/* 1259 */       this.offset = offset;
/* 1260 */       this.color = color;
/* 1261 */       this.count = count;
/* 1262 */       this.opacity = opacity;
/*      */     }
/*      */     
/*      */ 
/*      */     public PaintContext createContext(ColorModel cm, Rectangle deviceBounds, Rectangle2D userBounds, AffineTransform xform, RenderingHints hints)
/*      */     {
/* 1268 */       Point2D t1 = xform.transform(new Point2D.Float(this.x1, this.y1), null);
/* 1269 */       Point2D t2 = xform.transform(new Point2D.Float(this.x2, this.y2), null);
/* 1270 */       return new LinearGradientContext((float)t1.getX(), (float)t1.getY(), 
/* 1271 */         (float)t2.getX(), (float)t2.getY());
/*      */     }
/*      */     
/*      */     public int getTransparency() {
/* 1275 */       return 3;
/*      */     }
/*      */     
/*      */     public class LinearGradientContext implements PaintContext {
/* 1279 */       int ACCURACY = 2;
/*      */       float tx1;
/*      */       float ty1;
/*      */       
/* 1283 */       public LinearGradientContext(float tx1, float ty1, float tx2, float ty2) { this.tx1 = tx1;
/* 1284 */         this.ty1 = ty1;
/* 1285 */         this.tx2 = tx2;
/* 1286 */         this.ty2 = ty2;
/*      */       }
/*      */       
/*      */       public void dispose() {}
/*      */       
/* 1291 */       public ColorModel getColorModel() { return ColorModel.getRGBdefault(); }
/*      */       
/*      */       public Raster getRaster(int x, int y, int w, int h) {
/* 1294 */         WritableRaster raster = 
/* 1295 */           getColorModel().createCompatibleWritableRaster(w, h);
/*      */         
/* 1297 */         int[] data = new int[w * h * 4];
/*      */         
/*      */ 
/* 1300 */         float nx = this.tx2 - this.tx1;
/* 1301 */         float ny = this.ty2 - this.ty1;
/* 1302 */         float len = (float)Math.sqrt(nx * nx + ny * ny);
/* 1303 */         if (len != 0.0F) {
/* 1304 */           nx /= len;
/* 1305 */           ny /= len;
/*      */         }
/*      */         
/* 1308 */         int span = (int)PApplet.dist(this.tx1, this.ty1, this.tx2, this.ty2) * this.ACCURACY;
/* 1309 */         if (span <= 0)
/*      */         {
/*      */ 
/* 1312 */           int index = 0;
/* 1313 */           for (int j = 0; j < h; j++) {
/* 1314 */             for (int i = 0; i < w; i++) {
/* 1315 */               data[(index++)] = 0;
/* 1316 */               data[(index++)] = 0;
/* 1317 */               data[(index++)] = 0;
/* 1318 */               data[(index++)] = 255;
/*      */             }
/*      */           }
/*      */         }
/*      */         else {
/* 1323 */           int[][] interp = new int[span][4];
/* 1324 */           int prev = 0;
/* 1325 */           for (int i = 1; i < PShapeSVG.LinearGradientPaint.this.count; i++) {
/* 1326 */             int c0 = PShapeSVG.LinearGradientPaint.this.color[(i - 1)];
/* 1327 */             int c1 = PShapeSVG.LinearGradientPaint.this.color[i];
/* 1328 */             int last = (int)(PShapeSVG.LinearGradientPaint.this.offset[i] * (span - 1));
/*      */             
/* 1330 */             for (int j = prev; j <= last; j++) {
/* 1331 */               float btwn = PApplet.norm(j, prev, last);
/* 1332 */               interp[j][0] = ((int)PApplet.lerp(c0 >> 16 & 0xFF, c1 >> 16 & 0xFF, btwn));
/* 1333 */               interp[j][1] = ((int)PApplet.lerp(c0 >> 8 & 0xFF, c1 >> 8 & 0xFF, btwn));
/* 1334 */               interp[j][2] = ((int)PApplet.lerp(c0 & 0xFF, c1 & 0xFF, btwn));
/* 1335 */               interp[j][3] = ((int)(PApplet.lerp(c0 >> 24 & 0xFF, c1 >> 24 & 0xFF, btwn) * PShapeSVG.LinearGradientPaint.this.opacity));
/*      */             }
/*      */             
/* 1338 */             prev = last;
/*      */           }
/*      */           
/* 1341 */           int index = 0;
/* 1342 */           for (int j = 0; j < h; j++) {
/* 1343 */             for (int i = 0; i < w; i++)
/*      */             {
/*      */ 
/* 1346 */               float px = x + i - this.tx1;
/* 1347 */               float py = y + j - this.ty1;
/*      */               
/*      */ 
/* 1350 */               int which = (int)((px * nx + py * ny) * this.ACCURACY);
/* 1351 */               if (which < 0) which = 0;
/* 1352 */               if (which > interp.length - 1) { which = interp.length - 1;
/*      */               }
/*      */               
/* 1355 */               data[(index++)] = interp[which][0];
/* 1356 */               data[(index++)] = interp[which][1];
/* 1357 */               data[(index++)] = interp[which][2];
/* 1358 */               data[(index++)] = interp[which][3];
/*      */             }
/*      */           }
/*      */         }
/* 1362 */         raster.setPixels(0, 0, w, h, data);
/*      */         
/* 1364 */         return raster;
/*      */       }
/*      */       
/*      */       float tx2;
/*      */       float ty2;
/*      */     }
/*      */   }
/*      */   
/*      */   class RadialGradientPaint implements Paint { float cx;
/*      */     float cy;
/*      */     float radius;
/*      */     float[] offset;
/*      */     int[] color;
/*      */     int count;
/*      */     float opacity;
/*      */     
/* 1380 */     public RadialGradientPaint(float cx, float cy, float radius, float[] offset, int[] color, int count, float opacity) { this.cx = cx;
/* 1381 */       this.cy = cy;
/* 1382 */       this.radius = radius;
/* 1383 */       this.offset = offset;
/* 1384 */       this.color = color;
/* 1385 */       this.count = count;
/* 1386 */       this.opacity = opacity;
/*      */     }
/*      */     
/*      */ 
/*      */     public PaintContext createContext(ColorModel cm, Rectangle deviceBounds, Rectangle2D userBounds, AffineTransform xform, RenderingHints hints)
/*      */     {
/* 1392 */       return new RadialGradientContext();
/*      */     }
/*      */     
/*      */ 
/* 1396 */     public int getTransparency() { return 3; }
/*      */     
/*      */     public class RadialGradientContext implements PaintContext { public RadialGradientContext() {}
/*      */       
/* 1400 */       int ACCURACY = 5;
/*      */       
/*      */       public void dispose() {}
/*      */       
/* 1404 */       public ColorModel getColorModel() { return ColorModel.getRGBdefault(); }
/*      */       
/*      */       public Raster getRaster(int x, int y, int w, int h) {
/* 1407 */         WritableRaster raster = 
/* 1408 */           getColorModel().createCompatibleWritableRaster(w, h);
/*      */         
/* 1410 */         int span = (int)PShapeSVG.RadialGradientPaint.this.radius * this.ACCURACY;
/* 1411 */         int[][] interp = new int[span][4];
/* 1412 */         int prev = 0;
/* 1413 */         for (int i = 1; i < PShapeSVG.RadialGradientPaint.this.count; i++) {
/* 1414 */           int c0 = PShapeSVG.RadialGradientPaint.this.color[(i - 1)];
/* 1415 */           int c1 = PShapeSVG.RadialGradientPaint.this.color[i];
/* 1416 */           int last = (int)(PShapeSVG.RadialGradientPaint.this.offset[i] * (span - 1));
/* 1417 */           for (int j = prev; j <= last; j++) {
/* 1418 */             float btwn = PApplet.norm(j, prev, last);
/* 1419 */             interp[j][0] = ((int)PApplet.lerp(c0 >> 16 & 0xFF, c1 >> 16 & 0xFF, btwn));
/* 1420 */             interp[j][1] = ((int)PApplet.lerp(c0 >> 8 & 0xFF, c1 >> 8 & 0xFF, btwn));
/* 1421 */             interp[j][2] = ((int)PApplet.lerp(c0 & 0xFF, c1 & 0xFF, btwn));
/* 1422 */             interp[j][3] = ((int)(PApplet.lerp(c0 >> 24 & 0xFF, c1 >> 24 & 0xFF, btwn) * PShapeSVG.RadialGradientPaint.this.opacity));
/*      */           }
/* 1424 */           prev = last;
/*      */         }
/*      */         
/* 1427 */         int[] data = new int[w * h * 4];
/* 1428 */         int index = 0;
/* 1429 */         for (int j = 0; j < h; j++) {
/* 1430 */           for (int i = 0; i < w; i++) {
/* 1431 */             float distance = PApplet.dist(PShapeSVG.RadialGradientPaint.this.cx, PShapeSVG.RadialGradientPaint.this.cy, x + i, y + j);
/* 1432 */             int which = PApplet.min((int)(distance * this.ACCURACY), interp.length - 1);
/*      */             
/* 1434 */             data[(index++)] = interp[which][0];
/* 1435 */             data[(index++)] = interp[which][1];
/* 1436 */             data[(index++)] = interp[which][2];
/* 1437 */             data[(index++)] = interp[which][3];
/*      */           }
/*      */         }
/* 1440 */         raster.setPixels(0, 0, w, h, data);
/*      */         
/* 1442 */         return raster;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   protected Paint calcGradientPaint(Gradient gradient)
/*      */   {
/* 1449 */     if ((gradient instanceof LinearGradient)) {
/* 1450 */       LinearGradient grad = (LinearGradient)gradient;
/* 1451 */       return new LinearGradientPaint(grad.x1, grad.y1, grad.x2, grad.y2, 
/* 1452 */         grad.offset, grad.color, grad.count, 
/* 1453 */         this.opacity);
/*      */     }
/* 1455 */     if ((gradient instanceof RadialGradient)) {
/* 1456 */       RadialGradient grad = (RadialGradient)gradient;
/* 1457 */       return new RadialGradientPaint(grad.cx, grad.cy, grad.r, 
/* 1458 */         grad.offset, grad.color, grad.count, 
/* 1459 */         this.opacity);
/*      */     }
/* 1461 */     return null;
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
/*      */   protected void styles(PGraphics g)
/*      */   {
/* 1493 */     super.styles(g);
/*      */     
/* 1495 */     if ((g instanceof PGraphicsJava2D)) {
/* 1496 */       PGraphicsJava2D p2d = (PGraphicsJava2D)g;
/*      */       
/* 1498 */       if (this.strokeGradient != null) {
/* 1499 */         p2d.strokeGradient = true;
/* 1500 */         p2d.strokeGradientObject = this.strokeGradientPaint;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1505 */       if (this.fillGradient != null) {
/* 1506 */         p2d.fillGradient = true;
/* 1507 */         p2d.fillGradientObject = this.fillGradientPaint;
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
/*      */   public PShape getChild(String name)
/*      */   {
/* 1539 */     PShape found = super.getChild(name);
/* 1540 */     if (found == null)
/*      */     {
/*      */ 
/* 1543 */       found = super.getChild(name.replace(' ', '_'));
/*      */     }
/*      */     
/* 1546 */     if (found != null)
/*      */     {
/*      */ 
/* 1549 */       found.width = this.width;
/* 1550 */       found.height = this.height;
/*      */     }
/* 1552 */     return found;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void print()
/*      */   {
/* 1560 */     PApplet.println(this.element.toString());
/*      */   }
/*      */ }


/* Location:              /Users/AndrewKanarek/Desktop/MICHIGAN/KiefferWork/scripts-zibo/JavaApps/EllinghamDiagram/core.jar!/processing/core/PShapeSVG.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */