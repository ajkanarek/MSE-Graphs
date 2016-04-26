/*      */ package processing.core;
/*      */ 
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
/*      */ public class PShape
/*      */   implements PConstants
/*      */ {
/*      */   protected String name;
/*      */   protected HashMap<String, PShape> nameTable;
/*      */   public static final int GROUP = 0;
/*      */   public static final int PRIMITIVE = 1;
/*      */   public static final int PATH = 2;
/*      */   public static final int GEOMETRY = 3;
/*      */   protected int family;
/*      */   protected int primitive;
/*      */   protected PMatrix matrix;
/*      */   protected PImage image;
/*      */   public float width;
/*      */   public float height;
/*  113 */   protected boolean visible = true;
/*      */   
/*      */   protected boolean stroke;
/*      */   
/*      */   protected int strokeColor;
/*      */   
/*      */   protected float strokeWeight;
/*      */   
/*      */   protected int strokeCap;
/*      */   protected int strokeJoin;
/*      */   protected boolean fill;
/*      */   protected int fillColor;
/*  125 */   protected boolean style = true;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected float[] params;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected int vertexCount;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected float[][] vertices;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final int VERTEX = 0;
/*      */   
/*      */ 
/*      */ 
/*      */   public static final int BEZIER_VERTEX = 1;
/*      */   
/*      */ 
/*      */ 
/*      */   public static final int CURVE_VERTEX = 2;
/*      */   
/*      */ 
/*      */ 
/*      */   public static final int BREAK = 3;
/*      */   
/*      */ 
/*      */ 
/*      */   protected int vertexCodeCount;
/*      */   
/*      */ 
/*      */ 
/*      */   protected int[] vertexCodes;
/*      */   
/*      */ 
/*      */ 
/*      */   protected boolean close;
/*      */   
/*      */ 
/*      */ 
/*      */   protected PShape parent;
/*      */   
/*      */ 
/*      */ 
/*      */   protected int childCount;
/*      */   
/*      */ 
/*      */ 
/*      */   protected PShape[] children;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public PShape()
/*      */   {
/*  188 */     this.family = 0;
/*      */   }
/*      */   
/*      */   public PShape(int family)
/*      */   {
/*  193 */     this.family = family;
/*      */   }
/*      */   
/*      */   public void setName(String name)
/*      */   {
/*  198 */     this.name = name;
/*      */   }
/*      */   
/*      */   public String getName()
/*      */   {
/*  203 */     return this.name;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isVisible()
/*      */   {
/*  215 */     return this.visible;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setVisible(boolean visible)
/*      */   {
/*  227 */     this.visible = visible;
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
/*      */   public void disableStyle()
/*      */   {
/*  241 */     this.style = false;
/*      */     
/*  243 */     for (int i = 0; i < this.childCount; i++) {
/*  244 */       this.children[i].disableStyle();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void enableStyle()
/*      */   {
/*  255 */     this.style = true;
/*      */     
/*  257 */     for (int i = 0; i < this.childCount; i++) {
/*  258 */       this.children[i].enableStyle();
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
/*      */   public float getWidth()
/*      */   {
/*  280 */     return this.width;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float getHeight()
/*      */   {
/*  289 */     return this.height;
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
/*      */   protected void pre(PGraphics g)
/*      */   {
/*  313 */     if (this.matrix != null) {
/*  314 */       g.pushMatrix();
/*  315 */       g.applyMatrix(this.matrix);
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
/*  332 */     if (this.style) {
/*  333 */       g.pushStyle();
/*  334 */       styles(g);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void styles(PGraphics g)
/*      */   {
/*  343 */     if (this.stroke) {
/*  344 */       g.stroke(this.strokeColor);
/*  345 */       g.strokeWeight(this.strokeWeight);
/*  346 */       g.strokeCap(this.strokeCap);
/*  347 */       g.strokeJoin(this.strokeJoin);
/*      */     } else {
/*  349 */       g.noStroke();
/*      */     }
/*      */     
/*  352 */     if (this.fill)
/*      */     {
/*  354 */       g.fill(this.fillColor);
/*      */     } else {
/*  356 */       g.noFill();
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
/*      */   public void post(PGraphics g)
/*      */   {
/*  380 */     if (this.matrix != null) {
/*  381 */       g.popMatrix();
/*      */     }
/*      */     
/*  384 */     if (this.style) {
/*  385 */       g.popStyle();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void draw(PGraphics g)
/*      */   {
/*  396 */     if (this.visible) {
/*  397 */       pre(g);
/*  398 */       drawImpl(g);
/*  399 */       post(g);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void drawImpl(PGraphics g)
/*      */   {
/*  409 */     if (this.family == 0) {
/*  410 */       drawGroup(g);
/*  411 */     } else if (this.family == 1) {
/*  412 */       drawPrimitive(g);
/*  413 */     } else if (this.family == 3) {
/*  414 */       drawGeometry(g);
/*  415 */     } else if (this.family == 2) {
/*  416 */       drawPath(g);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void drawGroup(PGraphics g)
/*      */   {
/*  422 */     for (int i = 0; i < this.childCount; i++) {
/*  423 */       this.children[i].draw(g);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void drawPrimitive(PGraphics g)
/*      */   {
/*  429 */     if (this.primitive == 2) {
/*  430 */       g.point(this.params[0], this.params[1]);
/*      */     }
/*  432 */     else if (this.primitive == 4) {
/*  433 */       if (this.params.length == 4) {
/*  434 */         g.line(this.params[0], this.params[1], 
/*  435 */           this.params[2], this.params[3]);
/*      */       } else {
/*  437 */         g.line(this.params[0], this.params[1], this.params[2], 
/*  438 */           this.params[3], this.params[4], this.params[5]);
/*      */       }
/*      */     }
/*  441 */     else if (this.primitive == 8) {
/*  442 */       g.triangle(this.params[0], this.params[1], 
/*  443 */         this.params[2], this.params[3], 
/*  444 */         this.params[4], this.params[5]);
/*      */     }
/*  446 */     else if (this.primitive == 16) {
/*  447 */       g.quad(this.params[0], this.params[1], 
/*  448 */         this.params[2], this.params[3], 
/*  449 */         this.params[4], this.params[5], 
/*  450 */         this.params[6], this.params[7]);
/*      */     }
/*  452 */     else if (this.primitive == 30) {
/*  453 */       if (this.image != null) {
/*  454 */         g.imageMode(0);
/*  455 */         g.image(this.image, this.params[0], this.params[1], this.params[2], this.params[3]);
/*      */       } else {
/*  457 */         g.rectMode(0);
/*  458 */         g.rect(this.params[0], this.params[1], this.params[2], this.params[3]);
/*      */       }
/*      */     }
/*  461 */     else if (this.primitive == 31) {
/*  462 */       g.ellipseMode(0);
/*  463 */       g.ellipse(this.params[0], this.params[1], this.params[2], this.params[3]);
/*      */     }
/*  465 */     else if (this.primitive == 32) {
/*  466 */       g.ellipseMode(0);
/*  467 */       g.arc(this.params[0], this.params[1], this.params[2], this.params[3], this.params[4], this.params[5]);
/*      */     }
/*  469 */     else if (this.primitive == 41) {
/*  470 */       if (this.params.length == 1) {
/*  471 */         g.box(this.params[0]);
/*      */       } else {
/*  473 */         g.box(this.params[0], this.params[1], this.params[2]);
/*      */       }
/*      */     }
/*  476 */     else if (this.primitive == 40) {
/*  477 */       g.sphere(this.params[0]);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void drawGeometry(PGraphics g)
/*      */   {
/*  483 */     g.beginShape(this.primitive);
/*  484 */     if (this.style) {
/*  485 */       for (int i = 0; i < this.vertexCount; i++) {
/*  486 */         g.vertex(this.vertices[i]);
/*      */       }
/*      */     } else {
/*  489 */       for (int i = 0; i < this.vertexCount; i++) {
/*  490 */         float[] vert = this.vertices[i];
/*  491 */         if (vert[2] == 0.0F) {
/*  492 */           g.vertex(vert[0], vert[1]);
/*      */         } else {
/*  494 */           g.vertex(vert[0], vert[1], vert[2]);
/*      */         }
/*      */       }
/*      */     }
/*  498 */     g.endShape();
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
/*      */   protected void drawPath(PGraphics g)
/*      */   {
/*  560 */     if (this.vertices == null) { return;
/*      */     }
/*  562 */     g.beginShape();
/*      */     
/*  564 */     if (this.vertexCodeCount == 0) {
/*  565 */       if (this.vertices[0].length == 2) {
/*  566 */         for (int i = 0; i < this.vertexCount; i++) {
/*  567 */           g.vertex(this.vertices[i][0], this.vertices[i][1]);
/*      */         }
/*      */       } else {
/*  570 */         for (int i = 0; i < this.vertexCount; i++) {
/*  571 */           g.vertex(this.vertices[i][0], this.vertices[i][1], this.vertices[i][2]);
/*      */         }
/*      */       }
/*      */     }
/*      */     else {
/*  576 */       int index = 0;
/*      */       
/*  578 */       if (this.vertices[0].length == 2) {
/*  579 */         for (int j = 0; j < this.vertexCodeCount; j++) {
/*  580 */           switch (this.vertexCodes[j])
/*      */           {
/*      */           case 0: 
/*  583 */             g.vertex(this.vertices[index][0], this.vertices[index][1]);
/*  584 */             index++;
/*  585 */             break;
/*      */           
/*      */           case 1: 
/*  588 */             g.bezierVertex(this.vertices[(index + 0)][0], this.vertices[(index + 0)][1], 
/*  589 */               this.vertices[(index + 1)][0], this.vertices[(index + 1)][1], 
/*  590 */               this.vertices[(index + 2)][0], this.vertices[(index + 2)][1]);
/*  591 */             index += 3;
/*  592 */             break;
/*      */           
/*      */           case 2: 
/*  595 */             g.curveVertex(this.vertices[index][0], this.vertices[index][1]);
/*  596 */             index++;
/*      */           
/*      */           case 3: 
/*  599 */             g.breakShape();
/*      */           }
/*      */         }
/*      */       } else {
/*  603 */         for (int j = 0; j < this.vertexCodeCount; j++) {
/*  604 */           switch (this.vertexCodes[j])
/*      */           {
/*      */           case 0: 
/*  607 */             g.vertex(this.vertices[index][0], this.vertices[index][1], this.vertices[index][2]);
/*  608 */             index++;
/*  609 */             break;
/*      */           
/*      */           case 1: 
/*  612 */             g.bezierVertex(this.vertices[(index + 0)][0], this.vertices[(index + 0)][1], this.vertices[(index + 0)][2], 
/*  613 */               this.vertices[(index + 1)][0], this.vertices[(index + 1)][1], this.vertices[(index + 1)][2], 
/*  614 */               this.vertices[(index + 2)][0], this.vertices[(index + 2)][1], this.vertices[(index + 2)][2]);
/*  615 */             index += 3;
/*  616 */             break;
/*      */           
/*      */           case 2: 
/*  619 */             g.curveVertex(this.vertices[index][0], this.vertices[index][1], this.vertices[index][2]);
/*  620 */             index++;
/*      */           
/*      */           case 3: 
/*  623 */             g.breakShape();
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  628 */     g.endShape(this.close ? 2 : 1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getChildCount()
/*      */   {
/*  636 */     return this.childCount;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public PShape getChild(int index)
/*      */   {
/*  644 */     return this.children[index];
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public PShape getChild(String target)
/*      */   {
/*  655 */     if ((this.name != null) && (this.name.equals(target))) {
/*  656 */       return this;
/*      */     }
/*  658 */     if (this.nameTable != null) {
/*  659 */       PShape found = (PShape)this.nameTable.get(target);
/*  660 */       if (found != null) return found;
/*      */     }
/*  662 */     for (int i = 0; i < this.childCount; i++) {
/*  663 */       PShape found = this.children[i].getChild(target);
/*  664 */       if (found != null) return found;
/*      */     }
/*  666 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public PShape findChild(String target)
/*      */   {
/*  675 */     if (this.parent == null) {
/*  676 */       return getChild(target);
/*      */     }
/*      */     
/*  679 */     return this.parent.findChild(target);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void addChild(PShape who)
/*      */   {
/*  686 */     if (this.children == null) {
/*  687 */       this.children = new PShape[1];
/*      */     }
/*  689 */     if (this.childCount == this.children.length) {
/*  690 */       this.children = ((PShape[])PApplet.expand(this.children));
/*      */     }
/*  692 */     this.children[(this.childCount++)] = who;
/*  693 */     who.parent = this;
/*      */     
/*  695 */     if (who.getName() != null) {
/*  696 */       addName(who.getName(), who);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void addName(String nom, PShape shape)
/*      */   {
/*  705 */     if (this.parent != null) {
/*  706 */       this.parent.addName(nom, shape);
/*      */     } else {
/*  708 */       if (this.nameTable == null) {
/*  709 */         this.nameTable = new HashMap();
/*      */       }
/*  711 */       this.nameTable.put(nom, shape);
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
/*      */   public int getFamily()
/*      */   {
/*  729 */     return this.family;
/*      */   }
/*      */   
/*      */   public int getPrimitive()
/*      */   {
/*  734 */     return this.primitive;
/*      */   }
/*      */   
/*      */   public float[] getParams()
/*      */   {
/*  739 */     return getParams(null);
/*      */   }
/*      */   
/*      */   public float[] getParams(float[] target)
/*      */   {
/*  744 */     if ((target == null) || (target.length != this.params.length)) {
/*  745 */       target = new float[this.params.length];
/*      */     }
/*  747 */     PApplet.arrayCopy(this.params, target);
/*  748 */     return target;
/*      */   }
/*      */   
/*      */   public float getParam(int index)
/*      */   {
/*  753 */     return this.params[index];
/*      */   }
/*      */   
/*      */   public int getVertexCount()
/*      */   {
/*  758 */     return this.vertexCount;
/*      */   }
/*      */   
/*      */   public float[] getVertex(int index)
/*      */   {
/*  763 */     if ((index < 0) || (index >= this.vertexCount)) {
/*  764 */       String msg = "No vertex " + index + " for this shape, " + 
/*  765 */         "only vertices 0 through " + (this.vertexCount - 1) + ".";
/*  766 */       throw new IllegalArgumentException(msg);
/*      */     }
/*  768 */     return this.vertices[index];
/*      */   }
/*      */   
/*      */   public float getVertexX(int index)
/*      */   {
/*  773 */     return this.vertices[index][0];
/*      */   }
/*      */   
/*      */   public float getVertexY(int index)
/*      */   {
/*  778 */     return this.vertices[index][1];
/*      */   }
/*      */   
/*      */   public float getVertexZ(int index)
/*      */   {
/*  783 */     return this.vertices[index][2];
/*      */   }
/*      */   
/*      */   public int[] getVertexCodes()
/*      */   {
/*  788 */     if (this.vertexCodes.length != this.vertexCodeCount) {
/*  789 */       this.vertexCodes = PApplet.subset(this.vertexCodes, 0, this.vertexCodeCount);
/*      */     }
/*  791 */     return this.vertexCodes;
/*      */   }
/*      */   
/*      */   public int getVertexCodeCount()
/*      */   {
/*  796 */     return this.vertexCodeCount;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getVertexCode(int index)
/*      */   {
/*  804 */     return this.vertexCodes[index];
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean contains(float x, float y)
/*      */   {
/*  813 */     if (this.family == 2) {
/*  814 */       boolean c = false;
/*  815 */       int i = 0; for (int j = this.vertexCount - 1; i < this.vertexCount; j = i++) {
/*  816 */         if ((this.vertices[i][1] > y ? 1 : 0) != (this.vertices[j][1] > y ? 1 : 0))
/*      */         {
/*  818 */           if (x < (this.vertices[j][0] - this.vertices[i][0]) * (
/*  819 */             y - this.vertices[i][1]) / (
/*  820 */             this.vertices[j][1] - this.vertices[i][1]) + 
/*  821 */             this.vertices[i][0])
/*  822 */             c = !c;
/*      */         }
/*      */       }
/*  825 */       return c;
/*      */     }
/*  827 */     throw new IllegalArgumentException("The contains() method is only implemented for paths.");
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
/*  841 */     checkMatrix(2);
/*  842 */     this.matrix.translate(tx, ty);
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
/*      */   public void translate(float tx, float ty, float tz)
/*      */   {
/*  855 */     checkMatrix(3);
/*  856 */     this.matrix.translate(tx, ty, 0.0F);
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
/*      */   public void rotateX(float angle)
/*      */   {
/*  870 */     rotate(angle, 1.0F, 0.0F, 0.0F);
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
/*      */   public void rotateY(float angle)
/*      */   {
/*  884 */     rotate(angle, 0.0F, 1.0F, 0.0F);
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
/*      */   public void rotateZ(float angle)
/*      */   {
/*  899 */     rotate(angle, 0.0F, 0.0F, 1.0F);
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
/*  913 */     checkMatrix(2);
/*  914 */     this.matrix.rotate(angle);
/*      */   }
/*      */   
/*      */   public void rotate(float angle, float v0, float v1, float v2)
/*      */   {
/*  919 */     checkMatrix(3);
/*  920 */     this.matrix.rotate(angle, v0, v1, v2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void scale(float s)
/*      */   {
/*  930 */     checkMatrix(2);
/*  931 */     this.matrix.scale(s);
/*      */   }
/*      */   
/*      */   public void scale(float x, float y)
/*      */   {
/*  936 */     checkMatrix(2);
/*  937 */     this.matrix.scale(x, y);
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
/*      */   public void scale(float x, float y, float z)
/*      */   {
/*  954 */     checkMatrix(3);
/*  955 */     this.matrix.scale(x, y, z);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void resetMatrix()
/*      */   {
/*  963 */     checkMatrix(2);
/*  964 */     this.matrix.reset();
/*      */   }
/*      */   
/*      */   public void applyMatrix(PMatrix source)
/*      */   {
/*  969 */     if ((source instanceof PMatrix2D)) {
/*  970 */       applyMatrix((PMatrix2D)source);
/*  971 */     } else if ((source instanceof PMatrix3D)) {
/*  972 */       applyMatrix((PMatrix3D)source);
/*      */     }
/*      */   }
/*      */   
/*      */   public void applyMatrix(PMatrix2D source)
/*      */   {
/*  978 */     applyMatrix(source.m00, source.m01, 0.0F, source.m02, 
/*  979 */       source.m10, source.m11, 0.0F, source.m12, 
/*  980 */       0.0F, 0.0F, 1.0F, 0.0F, 
/*  981 */       0.0F, 0.0F, 0.0F, 1.0F);
/*      */   }
/*      */   
/*      */ 
/*      */   public void applyMatrix(float n00, float n01, float n02, float n10, float n11, float n12)
/*      */   {
/*  987 */     checkMatrix(2);
/*  988 */     this.matrix.apply(n00, n01, n02, 0.0F, 
/*  989 */       n10, n11, n12, 0.0F, 
/*  990 */       0.0F, 0.0F, 1.0F, 0.0F, 
/*  991 */       0.0F, 0.0F, 0.0F, 1.0F);
/*      */   }
/*      */   
/*      */   public void apply(PMatrix3D source)
/*      */   {
/*  996 */     applyMatrix(source.m00, source.m01, source.m02, source.m03, 
/*  997 */       source.m10, source.m11, source.m12, source.m13, 
/*  998 */       source.m20, source.m21, source.m22, source.m23, 
/*  999 */       source.m30, source.m31, source.m32, source.m33);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void applyMatrix(float n00, float n01, float n02, float n03, float n10, float n11, float n12, float n13, float n20, float n21, float n22, float n23, float n30, float n31, float n32, float n33)
/*      */   {
/* 1007 */     checkMatrix(3);
/* 1008 */     this.matrix.apply(n00, n01, n02, n03, 
/* 1009 */       n10, n11, n12, n13, 
/* 1010 */       n20, n21, n22, n23, 
/* 1011 */       n30, n31, n32, n33);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void checkMatrix(int dimensions)
/*      */   {
/* 1023 */     if (this.matrix == null) {
/* 1024 */       if (dimensions == 2) {
/* 1025 */         this.matrix = new PMatrix2D();
/*      */       } else {
/* 1027 */         this.matrix = new PMatrix3D();
/*      */       }
/* 1029 */     } else if ((dimensions == 3) && ((this.matrix instanceof PMatrix2D)))
/*      */     {
/* 1031 */       this.matrix = new PMatrix3D(this.matrix);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/AndrewKanarek/Desktop/MICHIGAN/KiefferWork/scripts-zibo/JavaApps/EllinghamDiagram/core.jar!/processing/core/PShape.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */