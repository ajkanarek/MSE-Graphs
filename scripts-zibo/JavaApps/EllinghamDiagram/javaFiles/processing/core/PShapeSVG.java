// 
// Decompiled by Procyon v0.5.30
// 

package processing.core;

import java.awt.image.WritableRaster;
import java.awt.image.Raster;
import java.awt.PaintContext;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.Rectangle;
import java.awt.image.ColorModel;
import java.awt.geom.Point2D;
import java.awt.geom.AffineTransform;
import java.util.HashMap;
import java.awt.Paint;
import processing.xml.XMLElement;

public class PShapeSVG extends PShape
{
    XMLElement element;
    float opacity;
    float strokeOpacity;
    float fillOpacity;
    Gradient strokeGradient;
    Paint strokeGradientPaint;
    String strokeName;
    Gradient fillGradient;
    Paint fillGradientPaint;
    String fillName;
    
    public PShapeSVG(final PApplet parent, final String filename) {
        this(new XMLElement(parent, filename));
    }
    
    public PShapeSVG(final XMLElement svg) {
        this(null, svg);
        if (!svg.getName().equals("svg")) {
            throw new RuntimeException("root is not <svg>, it's <" + svg.getName() + ">");
        }
        final String viewBoxStr = svg.getStringAttribute("viewBox");
        if (viewBoxStr != null) {
            final int[] viewBox = PApplet.parseInt(PApplet.splitTokens(viewBoxStr));
            this.width = viewBox[2];
            this.height = viewBox[3];
        }
        final String unitWidth = svg.getStringAttribute("width");
        final String unitHeight = svg.getStringAttribute("height");
        if (unitWidth != null) {
            this.width = parseUnitSize(unitWidth);
            this.height = parseUnitSize(unitHeight);
        }
        else if (this.width == 0.0f || this.height == 0.0f) {
            PGraphics.showWarning("The width and/or height is not readable in the <svg> tag of this file.");
            this.width = 1.0f;
            this.height = 1.0f;
        }
        this.parseChildren(svg);
    }
    
    public PShapeSVG(final PShapeSVG parent, final XMLElement properties) {
        this.parent = parent;
        if (parent == null) {
            this.stroke = false;
            this.strokeColor = -16777216;
            this.strokeWeight = 1.0f;
            this.strokeCap = 1;
            this.strokeJoin = 8;
            this.strokeGradient = null;
            this.strokeGradientPaint = null;
            this.strokeName = null;
            this.fill = true;
            this.fillColor = -16777216;
            this.fillGradient = null;
            this.fillGradientPaint = null;
            this.fillName = null;
            this.strokeOpacity = 1.0f;
            this.fillOpacity = 1.0f;
            this.opacity = 1.0f;
        }
        else {
            this.stroke = parent.stroke;
            this.strokeColor = parent.strokeColor;
            this.strokeWeight = parent.strokeWeight;
            this.strokeCap = parent.strokeCap;
            this.strokeJoin = parent.strokeJoin;
            this.strokeGradient = parent.strokeGradient;
            this.strokeGradientPaint = parent.strokeGradientPaint;
            this.strokeName = parent.strokeName;
            this.fill = parent.fill;
            this.fillColor = parent.fillColor;
            this.fillGradient = parent.fillGradient;
            this.fillGradientPaint = parent.fillGradientPaint;
            this.fillName = parent.fillName;
            this.opacity = parent.opacity;
        }
        this.element = properties;
        this.name = properties.getStringAttribute("id");
        if (this.name != null) {
            while (true) {
                final String[] m = PApplet.match(this.name, "_x([A-Za-z0-9]{2})_");
                if (m == null) {
                    break;
                }
                final char repair = (char)PApplet.unhex(m[1]);
                this.name = this.name.replace(m[0], new StringBuilder().append(repair).toString());
            }
        }
        final String displayStr = properties.getStringAttribute("display", "inline");
        this.visible = !displayStr.equals("none");
        final String transformStr = properties.getStringAttribute("transform");
        if (transformStr != null) {
            this.matrix = parseMatrix(transformStr);
        }
        this.parseColors(properties);
        this.parseChildren(properties);
    }
    
    protected void parseChildren(final XMLElement graphics) {
        final XMLElement[] elements = graphics.getChildren();
        this.children = new PShape[elements.length];
        this.childCount = 0;
        XMLElement[] array;
        for (int length = (array = elements).length, i = 0; i < length; ++i) {
            final XMLElement elem = array[i];
            final PShape kid = this.parseChild(elem);
            if (kid != null) {
                this.addChild(kid);
            }
        }
    }
    
    protected PShape parseChild(final XMLElement elem) {
        final String name = elem.getName();
        PShapeSVG shape = null;
        if (name.equals("g")) {
            shape = new PShapeSVG(this, elem);
        }
        else if (name.equals("defs")) {
            shape = new PShapeSVG(this, elem);
        }
        else if (name.equals("line")) {
            shape = new PShapeSVG(this, elem);
            shape.parseLine();
        }
        else if (name.equals("circle")) {
            shape = new PShapeSVG(this, elem);
            shape.parseEllipse(true);
        }
        else if (name.equals("ellipse")) {
            shape = new PShapeSVG(this, elem);
            shape.parseEllipse(false);
        }
        else if (name.equals("rect")) {
            shape = new PShapeSVG(this, elem);
            shape.parseRect();
        }
        else if (name.equals("polygon")) {
            shape = new PShapeSVG(this, elem);
            shape.parsePoly(true);
        }
        else if (name.equals("polyline")) {
            shape = new PShapeSVG(this, elem);
            shape.parsePoly(false);
        }
        else if (name.equals("path")) {
            shape = new PShapeSVG(this, elem);
            shape.parsePath();
        }
        else {
            if (name.equals("radialGradient")) {
                return new RadialGradient(this, elem);
            }
            if (name.equals("linearGradient")) {
                return new LinearGradient(this, elem);
            }
            if (name.equals("text") || name.equals("font")) {
                PGraphics.showWarning("Text and fonts in SVG files are not currently supported, convert text to outlines instead.");
            }
            else if (name.equals("filter")) {
                PGraphics.showWarning("Filters are not supported.");
            }
            else if (name.equals("mask")) {
                PGraphics.showWarning("Masks are not supported.");
            }
            else if (name.equals("pattern")) {
                PGraphics.showWarning("Patterns are not supported.");
            }
            else if (!name.equals("stop") && !name.equals("sodipodi:namedview")) {
                PGraphics.showWarning("Ignoring  <" + name + "> tag.");
            }
        }
        return shape;
    }
    
    protected void parseLine() {
        this.primitive = 4;
        this.family = 1;
        this.params = new float[] { getFloatWithUnit(this.element, "x1"), getFloatWithUnit(this.element, "y1"), getFloatWithUnit(this.element, "x2"), getFloatWithUnit(this.element, "y2") };
    }
    
    protected void parseEllipse(final boolean circle) {
        this.primitive = 31;
        this.family = 1;
        (this.params = new float[4])[0] = getFloatWithUnit(this.element, "cx");
        this.params[1] = getFloatWithUnit(this.element, "cy");
        float rx;
        float ry;
        if (circle) {
            ry = (rx = getFloatWithUnit(this.element, "r"));
        }
        else {
            rx = getFloatWithUnit(this.element, "rx");
            ry = getFloatWithUnit(this.element, "ry");
        }
        final float[] params = this.params;
        final int n = 0;
        params[n] -= rx;
        final float[] params2 = this.params;
        final int n2 = 1;
        params2[n2] -= ry;
        this.params[2] = rx * 2.0f;
        this.params[3] = ry * 2.0f;
    }
    
    protected void parseRect() {
        this.primitive = 30;
        this.family = 1;
        this.params = new float[] { getFloatWithUnit(this.element, "x"), getFloatWithUnit(this.element, "y"), getFloatWithUnit(this.element, "width"), getFloatWithUnit(this.element, "height") };
    }
    
    protected void parsePoly(final boolean close) {
        this.family = 2;
        this.close = close;
        final String pointsAttr = this.element.getStringAttribute("points");
        if (pointsAttr != null) {
            final String[] pointsBuffer = PApplet.splitTokens(pointsAttr);
            this.vertexCount = pointsBuffer.length;
            this.vertices = new float[this.vertexCount][2];
            for (int i = 0; i < this.vertexCount; ++i) {
                final String[] pb = PApplet.split(pointsBuffer[i], ',');
                this.vertices[i][0] = Float.valueOf(pb[0]);
                this.vertices[i][1] = Float.valueOf(pb[1]);
            }
        }
    }
    
    protected void parsePath() {
        this.family = 2;
        this.primitive = 0;
        final String pathData = this.element.getStringAttribute("d");
        if (pathData == null) {
            return;
        }
        final char[] pathDataChars = pathData.toCharArray();
        final StringBuffer pathBuffer = new StringBuffer();
        boolean lastSeparate = false;
        for (int i = 0; i < pathDataChars.length; ++i) {
            final char c = pathDataChars[i];
            boolean separate = false;
            if (c == 'M' || c == 'm' || c == 'L' || c == 'l' || c == 'H' || c == 'h' || c == 'V' || c == 'v' || c == 'C' || c == 'c' || c == 'S' || c == 's' || c == 'Q' || c == 'q' || c == 'T' || c == 't' || c == 'Z' || c == 'z' || c == ',') {
                separate = true;
                if (i != 0) {
                    pathBuffer.append("|");
                }
            }
            if (c == 'Z' || c == 'z') {
                separate = false;
            }
            if (c == '-' && !lastSeparate && (i == 0 || pathDataChars[i - 1] != 'e')) {
                pathBuffer.append("|");
            }
            if (c != ',') {
                pathBuffer.append(c);
            }
            if (separate && c != ',' && c != '-') {
                pathBuffer.append("|");
            }
            lastSeparate = separate;
        }
        final String[] pathDataKeys = PApplet.splitTokens(pathBuffer.toString(), "| \t\n\r\f ");
        this.vertices = new float[pathDataKeys.length][2];
        this.vertexCodes = new int[pathDataKeys.length];
        float cx = 0.0f;
        float cy = 0.0f;
        int j = 0;
        char implicitCommand = '\0';
        while (j < pathDataKeys.length) {
            char c2 = pathDataKeys[j].charAt(0);
            if (((c2 >= '0' && c2 <= '9') || c2 == '-') && implicitCommand != '\0') {
                c2 = implicitCommand;
                --j;
            }
            else {
                implicitCommand = c2;
            }
            switch (c2) {
                case 'M': {
                    cx = PApplet.parseFloat(pathDataKeys[j + 1]);
                    cy = PApplet.parseFloat(pathDataKeys[j + 2]);
                    this.parsePathMoveto(cx, cy);
                    implicitCommand = 'L';
                    j += 3;
                    continue;
                }
                case 'm': {
                    cx += PApplet.parseFloat(pathDataKeys[j + 1]);
                    cy += PApplet.parseFloat(pathDataKeys[j + 2]);
                    this.parsePathMoveto(cx, cy);
                    implicitCommand = 'l';
                    j += 3;
                    continue;
                }
                case 'L': {
                    cx = PApplet.parseFloat(pathDataKeys[j + 1]);
                    cy = PApplet.parseFloat(pathDataKeys[j + 2]);
                    this.parsePathLineto(cx, cy);
                    j += 3;
                    continue;
                }
                case 'l': {
                    cx += PApplet.parseFloat(pathDataKeys[j + 1]);
                    cy += PApplet.parseFloat(pathDataKeys[j + 2]);
                    this.parsePathLineto(cx, cy);
                    j += 3;
                    continue;
                }
                case 'H': {
                    cx = PApplet.parseFloat(pathDataKeys[j + 1]);
                    this.parsePathLineto(cx, cy);
                    j += 2;
                    continue;
                }
                case 'h': {
                    cx += PApplet.parseFloat(pathDataKeys[j + 1]);
                    this.parsePathLineto(cx, cy);
                    j += 2;
                    continue;
                }
                case 'V': {
                    cy = PApplet.parseFloat(pathDataKeys[j + 1]);
                    this.parsePathLineto(cx, cy);
                    j += 2;
                    continue;
                }
                case 'v': {
                    cy += PApplet.parseFloat(pathDataKeys[j + 1]);
                    this.parsePathLineto(cx, cy);
                    j += 2;
                    continue;
                }
                case 'C': {
                    final float ctrlX1 = PApplet.parseFloat(pathDataKeys[j + 1]);
                    final float ctrlY1 = PApplet.parseFloat(pathDataKeys[j + 2]);
                    final float ctrlX2 = PApplet.parseFloat(pathDataKeys[j + 3]);
                    final float ctrlY2 = PApplet.parseFloat(pathDataKeys[j + 4]);
                    final float endX = PApplet.parseFloat(pathDataKeys[j + 5]);
                    final float endY = PApplet.parseFloat(pathDataKeys[j + 6]);
                    this.parsePathCurveto(ctrlX1, ctrlY1, ctrlX2, ctrlY2, endX, endY);
                    cx = endX;
                    cy = endY;
                    j += 7;
                    continue;
                }
                case 'c': {
                    final float ctrlX1 = cx + PApplet.parseFloat(pathDataKeys[j + 1]);
                    final float ctrlY1 = cy + PApplet.parseFloat(pathDataKeys[j + 2]);
                    final float ctrlX2 = cx + PApplet.parseFloat(pathDataKeys[j + 3]);
                    final float ctrlY2 = cy + PApplet.parseFloat(pathDataKeys[j + 4]);
                    final float endX = cx + PApplet.parseFloat(pathDataKeys[j + 5]);
                    final float endY = cy + PApplet.parseFloat(pathDataKeys[j + 6]);
                    this.parsePathCurveto(ctrlX1, ctrlY1, ctrlX2, ctrlY2, endX, endY);
                    cx = endX;
                    cy = endY;
                    j += 7;
                    continue;
                }
                case 'S': {
                    final float ppx = this.vertices[this.vertexCount - 2][0];
                    final float ppy = this.vertices[this.vertexCount - 2][1];
                    final float px = this.vertices[this.vertexCount - 1][0];
                    final float py = this.vertices[this.vertexCount - 1][1];
                    final float ctrlX3 = px + (px - ppx);
                    final float ctrlY3 = py + (py - ppy);
                    final float ctrlX4 = PApplet.parseFloat(pathDataKeys[j + 1]);
                    final float ctrlY4 = PApplet.parseFloat(pathDataKeys[j + 2]);
                    final float endX2 = PApplet.parseFloat(pathDataKeys[j + 3]);
                    final float endY2 = PApplet.parseFloat(pathDataKeys[j + 4]);
                    this.parsePathCurveto(ctrlX3, ctrlY3, ctrlX4, ctrlY4, endX2, endY2);
                    cx = endX2;
                    cy = endY2;
                    j += 5;
                    continue;
                }
                case 's': {
                    final float ppx = this.vertices[this.vertexCount - 2][0];
                    final float ppy = this.vertices[this.vertexCount - 2][1];
                    final float px = this.vertices[this.vertexCount - 1][0];
                    final float py = this.vertices[this.vertexCount - 1][1];
                    final float ctrlX3 = px + (px - ppx);
                    final float ctrlY3 = py + (py - ppy);
                    final float ctrlX4 = cx + PApplet.parseFloat(pathDataKeys[j + 1]);
                    final float ctrlY4 = cy + PApplet.parseFloat(pathDataKeys[j + 2]);
                    final float endX2 = cx + PApplet.parseFloat(pathDataKeys[j + 3]);
                    final float endY2 = cy + PApplet.parseFloat(pathDataKeys[j + 4]);
                    this.parsePathCurveto(ctrlX3, ctrlY3, ctrlX4, ctrlY4, endX2, endY2);
                    cx = endX2;
                    cy = endY2;
                    j += 5;
                    continue;
                }
                case 'Q': {
                    final float ctrlX5 = PApplet.parseFloat(pathDataKeys[j + 1]);
                    final float ctrlY5 = PApplet.parseFloat(pathDataKeys[j + 2]);
                    final float endX3 = PApplet.parseFloat(pathDataKeys[j + 3]);
                    final float endY3 = PApplet.parseFloat(pathDataKeys[j + 4]);
                    this.parsePathQuadto(cx, cy, ctrlX5, ctrlY5, endX3, endY3);
                    cx = endX3;
                    cy = endY3;
                    j += 5;
                    continue;
                }
                case 'q': {
                    final float ctrlX5 = cx + PApplet.parseFloat(pathDataKeys[j + 1]);
                    final float ctrlY5 = cy + PApplet.parseFloat(pathDataKeys[j + 2]);
                    final float endX3 = cx + PApplet.parseFloat(pathDataKeys[j + 3]);
                    final float endY3 = cy + PApplet.parseFloat(pathDataKeys[j + 4]);
                    this.parsePathQuadto(cx, cy, ctrlX5, ctrlY5, endX3, endY3);
                    cx = endX3;
                    cy = endY3;
                    j += 5;
                    continue;
                }
                case 'T': {
                    final float ppx = this.vertices[this.vertexCount - 2][0];
                    final float ppy = this.vertices[this.vertexCount - 2][1];
                    final float px = this.vertices[this.vertexCount - 1][0];
                    final float py = this.vertices[this.vertexCount - 1][1];
                    final float ctrlX6 = px + (px - ppx);
                    final float ctrlY6 = py + (py - ppy);
                    final float endX4 = PApplet.parseFloat(pathDataKeys[j + 1]);
                    final float endY4 = PApplet.parseFloat(pathDataKeys[j + 2]);
                    this.parsePathQuadto(cx, cy, ctrlX6, ctrlY6, endX4, endY4);
                    cx = endX4;
                    cy = endY4;
                    j += 3;
                    continue;
                }
                case 't': {
                    final float ppx = this.vertices[this.vertexCount - 2][0];
                    final float ppy = this.vertices[this.vertexCount - 2][1];
                    final float px = this.vertices[this.vertexCount - 1][0];
                    final float py = this.vertices[this.vertexCount - 1][1];
                    final float ctrlX6 = px + (px - ppx);
                    final float ctrlY6 = py + (py - ppy);
                    final float endX4 = cx + PApplet.parseFloat(pathDataKeys[j + 1]);
                    final float endY4 = cy + PApplet.parseFloat(pathDataKeys[j + 2]);
                    this.parsePathQuadto(cx, cy, ctrlX6, ctrlY6, endX4, endY4);
                    cx = endX4;
                    cy = endY4;
                    j += 3;
                    continue;
                }
                case 'Z':
                case 'z': {
                    this.close = true;
                    ++j;
                    continue;
                }
                default: {
                    final String parsed = PApplet.join(PApplet.subset(pathDataKeys, 0, j), ",");
                    final String unparsed = PApplet.join(PApplet.subset(pathDataKeys, j), ",");
                    System.err.println("parsed: " + parsed);
                    System.err.println("unparsed: " + unparsed);
                    if (pathDataKeys[j].equals("a") || pathDataKeys[j].equals("A")) {
                        final String msg = "Sorry, elliptical arc support for SVG files is not yet implemented (See bug #996 for details)";
                        throw new RuntimeException(msg);
                    }
                    throw new RuntimeException("shape command not handled: " + pathDataKeys[j]);
                }
            }
        }
    }
    
    private void parsePathVertex(final float x, final float y) {
        if (this.vertexCount == this.vertices.length) {
            final float[][] temp = new float[this.vertexCount << 1][2];
            System.arraycopy(this.vertices, 0, temp, 0, this.vertexCount);
            this.vertices = temp;
        }
        this.vertices[this.vertexCount][0] = x;
        this.vertices[this.vertexCount][1] = y;
        ++this.vertexCount;
    }
    
    private void parsePathCode(final int what) {
        if (this.vertexCodeCount == this.vertexCodes.length) {
            this.vertexCodes = PApplet.expand(this.vertexCodes);
        }
        this.vertexCodes[this.vertexCodeCount++] = what;
    }
    
    private void parsePathMoveto(final float px, final float py) {
        if (this.vertexCount > 0) {
            this.parsePathCode(3);
        }
        this.parsePathCode(0);
        this.parsePathVertex(px, py);
    }
    
    private void parsePathLineto(final float px, final float py) {
        this.parsePathCode(0);
        this.parsePathVertex(px, py);
    }
    
    private void parsePathCurveto(final float x1, final float y1, final float x2, final float y2, final float x3, final float y3) {
        this.parsePathCode(1);
        this.parsePathVertex(x1, y1);
        this.parsePathVertex(x2, y2);
        this.parsePathVertex(x3, y3);
    }
    
    private void parsePathQuadto(final float x1, final float y1, final float cx, final float cy, final float x2, final float y2) {
        this.parsePathCode(1);
        this.parsePathVertex(x1 + (cx - x1) * 2.0f / 3.0f, y1 + (cy - y1) * 2.0f / 3.0f);
        this.parsePathVertex(x2 + (cx - x2) * 2.0f / 3.0f, y2 + (cy - y2) * 2.0f / 3.0f);
        this.parsePathVertex(x2, y2);
    }
    
    protected static PMatrix2D parseMatrix(final String matrixStr) {
        final String[] pieces = PApplet.match(matrixStr, "\\s*(\\w+)\\((.*)\\)");
        if (pieces == null) {
            System.err.println("Could not parse transform " + matrixStr);
            return null;
        }
        final float[] m = PApplet.parseFloat(PApplet.splitTokens(pieces[2], ", "));
        if (pieces[1].equals("matrix")) {
            return new PMatrix2D(m[0], m[2], m[4], m[1], m[3], m[5]);
        }
        if (pieces[1].equals("translate")) {
            final float tx = m[0];
            final float ty = (m.length == 2) ? m[1] : m[0];
            return new PMatrix2D(1.0f, 0.0f, tx, 0.0f, 1.0f, ty);
        }
        if (pieces[1].equals("scale")) {
            final float sx = m[0];
            final float sy = (m.length == 2) ? m[1] : m[0];
            return new PMatrix2D(sx, 0.0f, 0.0f, 0.0f, sy, 0.0f);
        }
        if (pieces[1].equals("rotate")) {
            final float angle = m[0];
            if (m.length == 1) {
                final float c = PApplet.cos(angle);
                final float s = PApplet.sin(angle);
                return new PMatrix2D(c, -s, 0.0f, s, c, 0.0f);
            }
            if (m.length == 3) {
                final PMatrix2D mat = new PMatrix2D(0.0f, 1.0f, m[1], 1.0f, 0.0f, m[2]);
                mat.rotate(m[0]);
                mat.translate(-m[1], -m[2]);
                return mat;
            }
        }
        else {
            if (pieces[1].equals("skewX")) {
                return new PMatrix2D(1.0f, 0.0f, 1.0f, PApplet.tan(m[0]), 0.0f, 0.0f);
            }
            if (pieces[1].equals("skewY")) {
                return new PMatrix2D(1.0f, 0.0f, 1.0f, 0.0f, PApplet.tan(m[0]), 0.0f);
            }
        }
        return null;
    }
    
    protected void parseColors(final XMLElement properties) {
        if (properties.hasAttribute("opacity")) {
            final String opacityText = properties.getStringAttribute("opacity");
            this.setOpacity(opacityText);
        }
        if (properties.hasAttribute("stroke")) {
            final String strokeText = properties.getStringAttribute("stroke");
            this.setColor(strokeText, false);
        }
        if (properties.hasAttribute("stroke-opacity")) {
            final String strokeOpacityText = properties.getStringAttribute("stroke-opacity");
            this.setStrokeOpacity(strokeOpacityText);
        }
        if (properties.hasAttribute("stroke-width")) {
            final String lineweight = properties.getStringAttribute("stroke-width");
            this.setStrokeWeight(lineweight);
        }
        if (properties.hasAttribute("stroke-linejoin")) {
            final String linejoin = properties.getStringAttribute("stroke-linejoin");
            this.setStrokeJoin(linejoin);
        }
        if (properties.hasAttribute("stroke-linecap")) {
            final String linecap = properties.getStringAttribute("stroke-linecap");
            this.setStrokeCap(linecap);
        }
        if (properties.hasAttribute("fill")) {
            final String fillText = properties.getStringAttribute("fill");
            this.setColor(fillText, true);
        }
        if (properties.hasAttribute("fill-opacity")) {
            final String fillOpacityText = properties.getStringAttribute("fill-opacity");
            this.setFillOpacity(fillOpacityText);
        }
        if (properties.hasAttribute("style")) {
            final String styleText = properties.getStringAttribute("style");
            final String[] styleTokens = PApplet.splitTokens(styleText, ";");
            for (int i = 0; i < styleTokens.length; ++i) {
                final String[] tokens = PApplet.splitTokens(styleTokens[i], ":");
                tokens[0] = PApplet.trim(tokens[0]);
                if (tokens[0].equals("fill")) {
                    this.setColor(tokens[1], true);
                }
                else if (tokens[0].equals("fill-opacity")) {
                    this.setFillOpacity(tokens[1]);
                }
                else if (tokens[0].equals("stroke")) {
                    this.setColor(tokens[1], false);
                }
                else if (tokens[0].equals("stroke-width")) {
                    this.setStrokeWeight(tokens[1]);
                }
                else if (tokens[0].equals("stroke-linecap")) {
                    this.setStrokeCap(tokens[1]);
                }
                else if (tokens[0].equals("stroke-linejoin")) {
                    this.setStrokeJoin(tokens[1]);
                }
                else if (tokens[0].equals("stroke-opacity")) {
                    this.setStrokeOpacity(tokens[1]);
                }
                else if (tokens[0].equals("opacity")) {
                    this.setOpacity(tokens[1]);
                }
            }
        }
    }
    
    void setOpacity(final String opacityText) {
        this.opacity = PApplet.parseFloat(opacityText);
        this.strokeColor = ((int)(this.opacity * 255.0f) << 24 | (this.strokeColor & 0xFFFFFF));
        this.fillColor = ((int)(this.opacity * 255.0f) << 24 | (this.fillColor & 0xFFFFFF));
    }
    
    void setStrokeWeight(final String lineweight) {
        this.strokeWeight = parseUnitSize(lineweight);
    }
    
    void setStrokeOpacity(final String opacityText) {
        this.strokeOpacity = PApplet.parseFloat(opacityText);
        this.strokeColor = ((int)(this.strokeOpacity * 255.0f) << 24 | (this.strokeColor & 0xFFFFFF));
    }
    
    void setStrokeJoin(final String linejoin) {
        if (!linejoin.equals("inherit")) {
            if (linejoin.equals("miter")) {
                this.strokeJoin = 8;
            }
            else if (linejoin.equals("round")) {
                this.strokeJoin = 2;
            }
            else if (linejoin.equals("bevel")) {
                this.strokeJoin = 32;
            }
        }
    }
    
    void setStrokeCap(final String linecap) {
        if (!linecap.equals("inherit")) {
            if (linecap.equals("butt")) {
                this.strokeCap = 1;
            }
            else if (linecap.equals("round")) {
                this.strokeCap = 2;
            }
            else if (linecap.equals("square")) {
                this.strokeCap = 4;
            }
        }
    }
    
    void setFillOpacity(final String opacityText) {
        this.fillOpacity = PApplet.parseFloat(opacityText);
        this.fillColor = ((int)(this.fillOpacity * 255.0f) << 24 | (this.fillColor & 0xFFFFFF));
    }
    
    void setColor(String colorText, final boolean isFill) {
        final int opacityMask = this.fillColor & 0xFF000000;
        boolean visible = true;
        int color = 0;
        String name = "";
        Gradient gradient = null;
        Paint paint = null;
        if (colorText.equals("none")) {
            visible = false;
        }
        else if (colorText.equals("black")) {
            color = opacityMask;
        }
        else if (colorText.equals("white")) {
            color = (opacityMask | 0xFFFFFF);
        }
        else if (colorText.startsWith("#")) {
            if (colorText.length() == 4) {
                colorText = colorText.replaceAll("^#(.)(.)(.)$", "#$1$1$2$2$3$3");
            }
            color = (opacityMask | (Integer.parseInt(colorText.substring(1), 16) & 0xFFFFFF));
        }
        else if (colorText.startsWith("rgb")) {
            color = (opacityMask | parseRGB(colorText));
        }
        else if (colorText.startsWith("url(#")) {
            name = colorText.substring(5, colorText.length() - 1);
            final Object object = this.findChild(name);
            if (object instanceof Gradient) {
                gradient = (Gradient)object;
                paint = this.calcGradientPaint(gradient);
            }
            else {
                System.err.println("url " + name + " refers to unexpected data: " + object);
            }
        }
        if (isFill) {
            this.fill = visible;
            this.fillColor = color;
            this.fillName = name;
            this.fillGradient = gradient;
            this.fillGradientPaint = paint;
        }
        else {
            this.stroke = visible;
            this.strokeColor = color;
            this.strokeName = name;
            this.strokeGradient = gradient;
            this.strokeGradientPaint = paint;
        }
    }
    
    protected static int parseRGB(final String what) {
        final int leftParen = what.indexOf(40) + 1;
        final int rightParen = what.indexOf(41);
        final String sub = what.substring(leftParen, rightParen);
        final int[] values = PApplet.parseInt(PApplet.splitTokens(sub, ", "));
        return values[0] << 16 | values[1] << 8 | values[2];
    }
    
    protected static HashMap<String, String> parseStyleAttributes(final String style) {
        final HashMap<String, String> table = new HashMap<String, String>();
        final String[] pieces = style.split(";");
        for (int i = 0; i < pieces.length; ++i) {
            final String[] parts = pieces[i].split(":");
            table.put(parts[0], parts[1]);
        }
        return table;
    }
    
    protected static float getFloatWithUnit(final XMLElement element, final String attribute) {
        final String val = element.getStringAttribute(attribute);
        return (val == null) ? 0.0f : parseUnitSize(val);
    }
    
    protected static float parseUnitSize(final String text) {
        final int len = text.length() - 2;
        if (text.endsWith("pt")) {
            return PApplet.parseFloat(text.substring(0, len)) * 1.25f;
        }
        if (text.endsWith("pc")) {
            return PApplet.parseFloat(text.substring(0, len)) * 15.0f;
        }
        if (text.endsWith("mm")) {
            return PApplet.parseFloat(text.substring(0, len)) * 3.543307f;
        }
        if (text.endsWith("cm")) {
            return PApplet.parseFloat(text.substring(0, len)) * 35.43307f;
        }
        if (text.endsWith("in")) {
            return PApplet.parseFloat(text.substring(0, len)) * 90.0f;
        }
        if (text.endsWith("px")) {
            return PApplet.parseFloat(text.substring(0, len));
        }
        return PApplet.parseFloat(text);
    }
    
    protected Paint calcGradientPaint(final Gradient gradient) {
        if (gradient instanceof LinearGradient) {
            final LinearGradient grad = (LinearGradient)gradient;
            return new LinearGradientPaint(grad.x1, grad.y1, grad.x2, grad.y2, grad.offset, grad.color, grad.count, this.opacity);
        }
        if (gradient instanceof RadialGradient) {
            final RadialGradient grad2 = (RadialGradient)gradient;
            return new RadialGradientPaint(grad2.cx, grad2.cy, grad2.r, grad2.offset, grad2.color, grad2.count, this.opacity);
        }
        return null;
    }
    
    protected void styles(final PGraphics g) {
        super.styles(g);
        if (g instanceof PGraphicsJava2D) {
            final PGraphicsJava2D p2d = (PGraphicsJava2D)g;
            if (this.strokeGradient != null) {
                p2d.strokeGradient = true;
                p2d.strokeGradientObject = this.strokeGradientPaint;
            }
            if (this.fillGradient != null) {
                p2d.fillGradient = true;
                p2d.fillGradientObject = this.fillGradientPaint;
            }
        }
    }
    
    public PShape getChild(final String name) {
        PShape found = super.getChild(name);
        if (found == null) {
            found = super.getChild(name.replace(' ', '_'));
        }
        if (found != null) {
            found.width = this.width;
            found.height = this.height;
        }
        return found;
    }
    
    public void print() {
        PApplet.println(this.element.toString());
    }
    
    static class Gradient extends PShapeSVG
    {
        AffineTransform transform;
        float[] offset;
        int[] color;
        int count;
        
        public Gradient(final PShapeSVG parent, final XMLElement properties) {
            super(parent, properties);
            final XMLElement[] elements = properties.getChildren();
            this.offset = new float[elements.length];
            this.color = new int[elements.length];
            for (int i = 0; i < elements.length; ++i) {
                final XMLElement elem = elements[i];
                final String name = elem.getName();
                if (name.equals("stop")) {
                    String offsetAttr = elem.getStringAttribute("offset");
                    float div = 1.0f;
                    if (offsetAttr.endsWith("%")) {
                        div = 100.0f;
                        offsetAttr = offsetAttr.substring(0, offsetAttr.length() - 1);
                    }
                    this.offset[this.count] = PApplet.parseFloat(offsetAttr) / div;
                    final String style = elem.getStringAttribute("style");
                    final HashMap<String, String> styles = PShapeSVG.parseStyleAttributes(style);
                    String colorStr = styles.get("stop-color");
                    if (colorStr == null) {
                        colorStr = "#000000";
                    }
                    String opacityStr = styles.get("stop-opacity");
                    if (opacityStr == null) {
                        opacityStr = "1";
                    }
                    final int tupacity = (int)(PApplet.parseFloat(opacityStr) * 255.0f);
                    this.color[this.count] = (tupacity << 24 | Integer.parseInt(colorStr.substring(1), 16));
                    ++this.count;
                }
            }
            this.offset = PApplet.subset(this.offset, 0, this.count);
            this.color = PApplet.subset(this.color, 0, this.count);
        }
    }
    
    class LinearGradient extends Gradient
    {
        float x1;
        float y1;
        float x2;
        float y2;
        
        public LinearGradient(final PShapeSVG parent, final XMLElement properties) {
            super(parent, properties);
            this.x1 = PShapeSVG.getFloatWithUnit(properties, "x1");
            this.y1 = PShapeSVG.getFloatWithUnit(properties, "y1");
            this.x2 = PShapeSVG.getFloatWithUnit(properties, "x2");
            this.y2 = PShapeSVG.getFloatWithUnit(properties, "y2");
            final String transformStr = properties.getStringAttribute("gradientTransform");
            if (transformStr != null) {
                final float[] t = PShapeSVG.parseMatrix(transformStr).get(null);
                this.transform = new AffineTransform(t[0], t[3], t[1], t[4], t[2], t[5]);
                final Point2D t2 = this.transform.transform(new Point2D.Float(this.x1, this.y1), null);
                final Point2D t3 = this.transform.transform(new Point2D.Float(this.x2, this.y2), null);
                this.x1 = (float)t2.getX();
                this.y1 = (float)t2.getY();
                this.x2 = (float)t3.getX();
                this.y2 = (float)t3.getY();
            }
        }
    }
    
    class RadialGradient extends Gradient
    {
        float cx;
        float cy;
        float r;
        
        public RadialGradient(final PShapeSVG parent, final XMLElement properties) {
            super(parent, properties);
            this.cx = PShapeSVG.getFloatWithUnit(properties, "cx");
            this.cy = PShapeSVG.getFloatWithUnit(properties, "cy");
            this.r = PShapeSVG.getFloatWithUnit(properties, "r");
            final String transformStr = properties.getStringAttribute("gradientTransform");
            if (transformStr != null) {
                final float[] t = PShapeSVG.parseMatrix(transformStr).get(null);
                this.transform = new AffineTransform(t[0], t[3], t[1], t[4], t[2], t[5]);
                final Point2D t2 = this.transform.transform(new Point2D.Float(this.cx, this.cy), null);
                final Point2D t3 = this.transform.transform(new Point2D.Float(this.cx + this.r, this.cy), null);
                this.cx = (float)t2.getX();
                this.cy = (float)t2.getY();
                this.r = (float)(t3.getX() - t2.getX());
            }
        }
    }
    
    class LinearGradientPaint implements Paint
    {
        float x1;
        float y1;
        float x2;
        float y2;
        float[] offset;
        int[] color;
        int count;
        float opacity;
        
        public LinearGradientPaint(final float x1, final float y1, final float x2, final float y2, final float[] offset, final int[] color, final int count, final float opacity) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.offset = offset;
            this.color = color;
            this.count = count;
            this.opacity = opacity;
        }
        
        public PaintContext createContext(final ColorModel cm, final Rectangle deviceBounds, final Rectangle2D userBounds, final AffineTransform xform, final RenderingHints hints) {
            final Point2D t1 = xform.transform(new Point2D.Float(this.x1, this.y1), null);
            final Point2D t2 = xform.transform(new Point2D.Float(this.x2, this.y2), null);
            return new LinearGradientContext((float)t1.getX(), (float)t1.getY(), (float)t2.getX(), (float)t2.getY());
        }
        
        public int getTransparency() {
            return 3;
        }
        
        public class LinearGradientContext implements PaintContext
        {
            int ACCURACY;
            float tx1;
            float ty1;
            float tx2;
            float ty2;
            
            public LinearGradientContext(final float tx1, final float ty1, final float tx2, final float ty2) {
                this.ACCURACY = 2;
                this.tx1 = tx1;
                this.ty1 = ty1;
                this.tx2 = tx2;
                this.ty2 = ty2;
            }
            
            public void dispose() {
            }
            
            public ColorModel getColorModel() {
                return ColorModel.getRGBdefault();
            }
            
            public Raster getRaster(final int x, final int y, final int w, final int h) {
                final WritableRaster raster = this.getColorModel().createCompatibleWritableRaster(w, h);
                final int[] data = new int[w * h * 4];
                float nx = this.tx2 - this.tx1;
                float ny = this.ty2 - this.ty1;
                final float len = (float)Math.sqrt(nx * nx + ny * ny);
                if (len != 0.0f) {
                    nx /= len;
                    ny /= len;
                }
                final int span = (int)PApplet.dist(this.tx1, this.ty1, this.tx2, this.ty2) * this.ACCURACY;
                if (span <= 0) {
                    int index = 0;
                    for (int j = 0; j < h; ++j) {
                        for (int i = 0; i < w; ++i) {
                            data[index++] = 0;
                            data[index++] = 0;
                            data[index++] = 0;
                            data[index++] = 255;
                        }
                    }
                }
                else {
                    final int[][] interp = new int[span][4];
                    int prev = 0;
                    for (int i = 1; i < LinearGradientPaint.this.count; ++i) {
                        final int c0 = LinearGradientPaint.this.color[i - 1];
                        final int c2 = LinearGradientPaint.this.color[i];
                        final int last = (int)(LinearGradientPaint.this.offset[i] * (span - 1));
                        for (int k = prev; k <= last; ++k) {
                            final float btwn = PApplet.norm(k, prev, last);
                            interp[k][0] = (int)PApplet.lerp(c0 >> 16 & 0xFF, c2 >> 16 & 0xFF, btwn);
                            interp[k][1] = (int)PApplet.lerp(c0 >> 8 & 0xFF, c2 >> 8 & 0xFF, btwn);
                            interp[k][2] = (int)PApplet.lerp(c0 & 0xFF, c2 & 0xFF, btwn);
                            interp[k][3] = (int)(PApplet.lerp(c0 >> 24 & 0xFF, c2 >> 24 & 0xFF, btwn) * LinearGradientPaint.this.opacity);
                        }
                        prev = last;
                    }
                    int index2 = 0;
                    for (int l = 0; l < h; ++l) {
                        for (int m = 0; m < w; ++m) {
                            final float px = x + m - this.tx1;
                            final float py = y + l - this.ty1;
                            int which = (int)((px * nx + py * ny) * this.ACCURACY);
                            if (which < 0) {
                                which = 0;
                            }
                            if (which > interp.length - 1) {
                                which = interp.length - 1;
                            }
                            data[index2++] = interp[which][0];
                            data[index2++] = interp[which][1];
                            data[index2++] = interp[which][2];
                            data[index2++] = interp[which][3];
                        }
                    }
                }
                raster.setPixels(0, 0, w, h, data);
                return raster;
            }
        }
    }
    
    class RadialGradientPaint implements Paint
    {
        float cx;
        float cy;
        float radius;
        float[] offset;
        int[] color;
        int count;
        float opacity;
        
        public RadialGradientPaint(final float cx, final float cy, final float radius, final float[] offset, final int[] color, final int count, final float opacity) {
            this.cx = cx;
            this.cy = cy;
            this.radius = radius;
            this.offset = offset;
            this.color = color;
            this.count = count;
            this.opacity = opacity;
        }
        
        public PaintContext createContext(final ColorModel cm, final Rectangle deviceBounds, final Rectangle2D userBounds, final AffineTransform xform, final RenderingHints hints) {
            return new RadialGradientContext();
        }
        
        public int getTransparency() {
            return 3;
        }
        
        public class RadialGradientContext implements PaintContext
        {
            int ACCURACY;
            
            public RadialGradientContext() {
                this.ACCURACY = 5;
            }
            
            public void dispose() {
            }
            
            public ColorModel getColorModel() {
                return ColorModel.getRGBdefault();
            }
            
            public Raster getRaster(final int x, final int y, final int w, final int h) {
                final WritableRaster raster = this.getColorModel().createCompatibleWritableRaster(w, h);
                final int span = (int)RadialGradientPaint.this.radius * this.ACCURACY;
                final int[][] interp = new int[span][4];
                int prev = 0;
                for (int i = 1; i < RadialGradientPaint.this.count; ++i) {
                    final int c0 = RadialGradientPaint.this.color[i - 1];
                    final int c2 = RadialGradientPaint.this.color[i];
                    final int last = (int)(RadialGradientPaint.this.offset[i] * (span - 1));
                    for (int j = prev; j <= last; ++j) {
                        final float btwn = PApplet.norm(j, prev, last);
                        interp[j][0] = (int)PApplet.lerp(c0 >> 16 & 0xFF, c2 >> 16 & 0xFF, btwn);
                        interp[j][1] = (int)PApplet.lerp(c0 >> 8 & 0xFF, c2 >> 8 & 0xFF, btwn);
                        interp[j][2] = (int)PApplet.lerp(c0 & 0xFF, c2 & 0xFF, btwn);
                        interp[j][3] = (int)(PApplet.lerp(c0 >> 24 & 0xFF, c2 >> 24 & 0xFF, btwn) * RadialGradientPaint.this.opacity);
                    }
                    prev = last;
                }
                final int[] data = new int[w * h * 4];
                int index = 0;
                for (int k = 0; k < h; ++k) {
                    for (int l = 0; l < w; ++l) {
                        final float distance = PApplet.dist(RadialGradientPaint.this.cx, RadialGradientPaint.this.cy, x + l, y + k);
                        final int which = PApplet.min((int)(distance * this.ACCURACY), interp.length - 1);
                        data[index++] = interp[which][0];
                        data[index++] = interp[which][1];
                        data[index++] = interp[which][2];
                        data[index++] = interp[which][3];
                    }
                }
                raster.setPixels(0, 0, w, h, data);
                return raster;
            }
        }
    }
}
