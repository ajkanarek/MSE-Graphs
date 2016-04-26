// 
// Decompiled by Procyon v0.5.30
// 

package processing.core;

import java.util.HashMap;

public class PShape implements PConstants
{
    protected String name;
    protected HashMap<String, PShape> nameTable;
    public static final int GROUP = 0;
    public static final int PRIMITIVE = 1;
    public static final int PATH = 2;
    public static final int GEOMETRY = 3;
    protected int family;
    protected int primitive;
    protected PMatrix matrix;
    protected PImage image;
    public float width;
    public float height;
    protected boolean visible;
    protected boolean stroke;
    protected int strokeColor;
    protected float strokeWeight;
    protected int strokeCap;
    protected int strokeJoin;
    protected boolean fill;
    protected int fillColor;
    protected boolean style;
    protected float[] params;
    protected int vertexCount;
    protected float[][] vertices;
    public static final int VERTEX = 0;
    public static final int BEZIER_VERTEX = 1;
    public static final int CURVE_VERTEX = 2;
    public static final int BREAK = 3;
    protected int vertexCodeCount;
    protected int[] vertexCodes;
    protected boolean close;
    protected PShape parent;
    protected int childCount;
    protected PShape[] children;
    
    public PShape() {
        this.visible = true;
        this.style = true;
        this.family = 0;
    }
    
    public PShape(final int family) {
        this.visible = true;
        this.style = true;
        this.family = family;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public boolean isVisible() {
        return this.visible;
    }
    
    public void setVisible(final boolean visible) {
        this.visible = visible;
    }
    
    public void disableStyle() {
        this.style = false;
        for (int i = 0; i < this.childCount; ++i) {
            this.children[i].disableStyle();
        }
    }
    
    public void enableStyle() {
        this.style = true;
        for (int i = 0; i < this.childCount; ++i) {
            this.children[i].enableStyle();
        }
    }
    
    public float getWidth() {
        return this.width;
    }
    
    public float getHeight() {
        return this.height;
    }
    
    protected void pre(final PGraphics g) {
        if (this.matrix != null) {
            g.pushMatrix();
            g.applyMatrix(this.matrix);
        }
        if (this.style) {
            g.pushStyle();
            this.styles(g);
        }
    }
    
    protected void styles(final PGraphics g) {
        if (this.stroke) {
            g.stroke(this.strokeColor);
            g.strokeWeight(this.strokeWeight);
            g.strokeCap(this.strokeCap);
            g.strokeJoin(this.strokeJoin);
        }
        else {
            g.noStroke();
        }
        if (this.fill) {
            g.fill(this.fillColor);
        }
        else {
            g.noFill();
        }
    }
    
    public void post(final PGraphics g) {
        if (this.matrix != null) {
            g.popMatrix();
        }
        if (this.style) {
            g.popStyle();
        }
    }
    
    public void draw(final PGraphics g) {
        if (this.visible) {
            this.pre(g);
            this.drawImpl(g);
            this.post(g);
        }
    }
    
    public void drawImpl(final PGraphics g) {
        if (this.family == 0) {
            this.drawGroup(g);
        }
        else if (this.family == 1) {
            this.drawPrimitive(g);
        }
        else if (this.family == 3) {
            this.drawGeometry(g);
        }
        else if (this.family == 2) {
            this.drawPath(g);
        }
    }
    
    protected void drawGroup(final PGraphics g) {
        for (int i = 0; i < this.childCount; ++i) {
            this.children[i].draw(g);
        }
    }
    
    protected void drawPrimitive(final PGraphics g) {
        if (this.primitive == 2) {
            g.point(this.params[0], this.params[1]);
        }
        else if (this.primitive == 4) {
            if (this.params.length == 4) {
                g.line(this.params[0], this.params[1], this.params[2], this.params[3]);
            }
            else {
                g.line(this.params[0], this.params[1], this.params[2], this.params[3], this.params[4], this.params[5]);
            }
        }
        else if (this.primitive == 8) {
            g.triangle(this.params[0], this.params[1], this.params[2], this.params[3], this.params[4], this.params[5]);
        }
        else if (this.primitive == 16) {
            g.quad(this.params[0], this.params[1], this.params[2], this.params[3], this.params[4], this.params[5], this.params[6], this.params[7]);
        }
        else if (this.primitive == 30) {
            if (this.image != null) {
                g.imageMode(0);
                g.image(this.image, this.params[0], this.params[1], this.params[2], this.params[3]);
            }
            else {
                g.rectMode(0);
                g.rect(this.params[0], this.params[1], this.params[2], this.params[3]);
            }
        }
        else if (this.primitive == 31) {
            g.ellipseMode(0);
            g.ellipse(this.params[0], this.params[1], this.params[2], this.params[3]);
        }
        else if (this.primitive == 32) {
            g.ellipseMode(0);
            g.arc(this.params[0], this.params[1], this.params[2], this.params[3], this.params[4], this.params[5]);
        }
        else if (this.primitive == 41) {
            if (this.params.length == 1) {
                g.box(this.params[0]);
            }
            else {
                g.box(this.params[0], this.params[1], this.params[2]);
            }
        }
        else if (this.primitive == 40) {
            g.sphere(this.params[0]);
        }
    }
    
    protected void drawGeometry(final PGraphics g) {
        g.beginShape(this.primitive);
        if (this.style) {
            for (int i = 0; i < this.vertexCount; ++i) {
                g.vertex(this.vertices[i]);
            }
        }
        else {
            for (int i = 0; i < this.vertexCount; ++i) {
                final float[] vert = this.vertices[i];
                if (vert[2] == 0.0f) {
                    g.vertex(vert[0], vert[1]);
                }
                else {
                    g.vertex(vert[0], vert[1], vert[2]);
                }
            }
        }
        g.endShape();
    }
    
    protected void drawPath(final PGraphics g) {
        if (this.vertices == null) {
            return;
        }
        g.beginShape();
        if (this.vertexCodeCount == 0) {
            if (this.vertices[0].length == 2) {
                for (int i = 0; i < this.vertexCount; ++i) {
                    g.vertex(this.vertices[i][0], this.vertices[i][1]);
                }
            }
            else {
                for (int i = 0; i < this.vertexCount; ++i) {
                    g.vertex(this.vertices[i][0], this.vertices[i][1], this.vertices[i][2]);
                }
            }
        }
        else {
            int index = 0;
            if (this.vertices[0].length == 2) {
                for (int j = 0; j < this.vertexCodeCount; ++j) {
                    switch (this.vertexCodes[j]) {
                        case 0: {
                            g.vertex(this.vertices[index][0], this.vertices[index][1]);
                            ++index;
                            break;
                        }
                        case 1: {
                            g.bezierVertex(this.vertices[index + 0][0], this.vertices[index + 0][1], this.vertices[index + 1][0], this.vertices[index + 1][1], this.vertices[index + 2][0], this.vertices[index + 2][1]);
                            index += 3;
                            break;
                        }
                        case 2: {
                            g.curveVertex(this.vertices[index][0], this.vertices[index][1]);
                            ++index;
                        }
                        case 3: {
                            g.breakShape();
                            break;
                        }
                    }
                }
            }
            else {
                for (int j = 0; j < this.vertexCodeCount; ++j) {
                    switch (this.vertexCodes[j]) {
                        case 0: {
                            g.vertex(this.vertices[index][0], this.vertices[index][1], this.vertices[index][2]);
                            ++index;
                            break;
                        }
                        case 1: {
                            g.bezierVertex(this.vertices[index + 0][0], this.vertices[index + 0][1], this.vertices[index + 0][2], this.vertices[index + 1][0], this.vertices[index + 1][1], this.vertices[index + 1][2], this.vertices[index + 2][0], this.vertices[index + 2][1], this.vertices[index + 2][2]);
                            index += 3;
                            break;
                        }
                        case 2: {
                            g.curveVertex(this.vertices[index][0], this.vertices[index][1], this.vertices[index][2]);
                            ++index;
                        }
                        case 3: {
                            g.breakShape();
                            break;
                        }
                    }
                }
            }
        }
        g.endShape(this.close ? 2 : 1);
    }
    
    public int getChildCount() {
        return this.childCount;
    }
    
    public PShape getChild(final int index) {
        return this.children[index];
    }
    
    public PShape getChild(final String target) {
        if (this.name != null && this.name.equals(target)) {
            return this;
        }
        if (this.nameTable != null) {
            final PShape found = this.nameTable.get(target);
            if (found != null) {
                return found;
            }
        }
        for (int i = 0; i < this.childCount; ++i) {
            final PShape found2 = this.children[i].getChild(target);
            if (found2 != null) {
                return found2;
            }
        }
        return null;
    }
    
    public PShape findChild(final String target) {
        if (this.parent == null) {
            return this.getChild(target);
        }
        return this.parent.findChild(target);
    }
    
    public void addChild(final PShape who) {
        if (this.children == null) {
            this.children = new PShape[1];
        }
        if (this.childCount == this.children.length) {
            this.children = (PShape[])PApplet.expand(this.children);
        }
        this.children[this.childCount++] = who;
        who.parent = this;
        if (who.getName() != null) {
            this.addName(who.getName(), who);
        }
    }
    
    protected void addName(final String nom, final PShape shape) {
        if (this.parent != null) {
            this.parent.addName(nom, shape);
        }
        else {
            if (this.nameTable == null) {
                this.nameTable = new HashMap<String, PShape>();
            }
            this.nameTable.put(nom, shape);
        }
    }
    
    public int getFamily() {
        return this.family;
    }
    
    public int getPrimitive() {
        return this.primitive;
    }
    
    public float[] getParams() {
        return this.getParams(null);
    }
    
    public float[] getParams(float[] target) {
        if (target == null || target.length != this.params.length) {
            target = new float[this.params.length];
        }
        PApplet.arrayCopy(this.params, target);
        return target;
    }
    
    public float getParam(final int index) {
        return this.params[index];
    }
    
    public int getVertexCount() {
        return this.vertexCount;
    }
    
    public float[] getVertex(final int index) {
        if (index < 0 || index >= this.vertexCount) {
            final String msg = "No vertex " + index + " for this shape, " + "only vertices 0 through " + (this.vertexCount - 1) + ".";
            throw new IllegalArgumentException(msg);
        }
        return this.vertices[index];
    }
    
    public float getVertexX(final int index) {
        return this.vertices[index][0];
    }
    
    public float getVertexY(final int index) {
        return this.vertices[index][1];
    }
    
    public float getVertexZ(final int index) {
        return this.vertices[index][2];
    }
    
    public int[] getVertexCodes() {
        if (this.vertexCodes.length != this.vertexCodeCount) {
            this.vertexCodes = PApplet.subset(this.vertexCodes, 0, this.vertexCodeCount);
        }
        return this.vertexCodes;
    }
    
    public int getVertexCodeCount() {
        return this.vertexCodeCount;
    }
    
    public int getVertexCode(final int index) {
        return this.vertexCodes[index];
    }
    
    public boolean contains(final float x, final float y) {
        if (this.family == 2) {
            boolean c = false;
            int i = 0;
            int j = this.vertexCount - 1;
            while (i < this.vertexCount) {
                if (this.vertices[i][1] > y != this.vertices[j][1] > y && x < (this.vertices[j][0] - this.vertices[i][0]) * (y - this.vertices[i][1]) / (this.vertices[j][1] - this.vertices[i][1]) + this.vertices[i][0]) {
                    c = !c;
                }
                j = i++;
            }
            return c;
        }
        throw new IllegalArgumentException("The contains() method is only implemented for paths.");
    }
    
    public void translate(final float tx, final float ty) {
        this.checkMatrix(2);
        this.matrix.translate(tx, ty);
    }
    
    public void translate(final float tx, final float ty, final float tz) {
        this.checkMatrix(3);
        this.matrix.translate(tx, ty, 0.0f);
    }
    
    public void rotateX(final float angle) {
        this.rotate(angle, 1.0f, 0.0f, 0.0f);
    }
    
    public void rotateY(final float angle) {
        this.rotate(angle, 0.0f, 1.0f, 0.0f);
    }
    
    public void rotateZ(final float angle) {
        this.rotate(angle, 0.0f, 0.0f, 1.0f);
    }
    
    public void rotate(final float angle) {
        this.checkMatrix(2);
        this.matrix.rotate(angle);
    }
    
    public void rotate(final float angle, final float v0, final float v1, final float v2) {
        this.checkMatrix(3);
        this.matrix.rotate(angle, v0, v1, v2);
    }
    
    public void scale(final float s) {
        this.checkMatrix(2);
        this.matrix.scale(s);
    }
    
    public void scale(final float x, final float y) {
        this.checkMatrix(2);
        this.matrix.scale(x, y);
    }
    
    public void scale(final float x, final float y, final float z) {
        this.checkMatrix(3);
        this.matrix.scale(x, y, z);
    }
    
    public void resetMatrix() {
        this.checkMatrix(2);
        this.matrix.reset();
    }
    
    public void applyMatrix(final PMatrix source) {
        if (source instanceof PMatrix2D) {
            this.applyMatrix((PMatrix2D)source);
        }
        else if (source instanceof PMatrix3D) {
            this.applyMatrix(source);
        }
    }
    
    public void applyMatrix(final PMatrix2D source) {
        this.applyMatrix(source.m00, source.m01, 0.0f, source.m02, source.m10, source.m11, 0.0f, source.m12, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
    }
    
    public void applyMatrix(final float n00, final float n01, final float n02, final float n10, final float n11, final float n12) {
        this.checkMatrix(2);
        this.matrix.apply(n00, n01, n02, 0.0f, n10, n11, n12, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
    }
    
    public void apply(final PMatrix3D source) {
        this.applyMatrix(source.m00, source.m01, source.m02, source.m03, source.m10, source.m11, source.m12, source.m13, source.m20, source.m21, source.m22, source.m23, source.m30, source.m31, source.m32, source.m33);
    }
    
    public void applyMatrix(final float n00, final float n01, final float n02, final float n03, final float n10, final float n11, final float n12, final float n13, final float n20, final float n21, final float n22, final float n23, final float n30, final float n31, final float n32, final float n33) {
        this.checkMatrix(3);
        this.matrix.apply(n00, n01, n02, n03, n10, n11, n12, n13, n20, n21, n22, n23, n30, n31, n32, n33);
    }
    
    protected void checkMatrix(final int dimensions) {
        if (this.matrix == null) {
            if (dimensions == 2) {
                this.matrix = new PMatrix2D();
            }
            else {
                this.matrix = new PMatrix3D();
            }
        }
        else if (dimensions == 3 && this.matrix instanceof PMatrix2D) {
            this.matrix = new PMatrix3D(this.matrix);
        }
    }
}
