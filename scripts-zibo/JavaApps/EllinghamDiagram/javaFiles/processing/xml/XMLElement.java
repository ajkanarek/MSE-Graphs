// 
// Decompiled by Procyon v0.5.30
// 

package processing.xml;

import java.util.Hashtable;
import java.io.IOException;
import java.io.Writer;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.ByteArrayOutputStream;
import java.util.Properties;
import java.util.Enumeration;
import java.io.StringReader;
import java.io.Reader;
import processing.core.PApplet;
import java.util.Vector;
import java.io.Serializable;

public class XMLElement implements Serializable
{
    public static final int NO_LINE = -1;
    private XMLElement parent;
    private Vector<XMLAttribute> attributes;
    private Vector<XMLElement> children;
    private String name;
    private String fullName;
    private String namespace;
    private String content;
    private String systemID;
    private int lineNr;
    
    public XMLElement() {
        this(null, null, null, -1);
    }
    
    protected void set(final String fullName, final String namespace, final String systemID, final int lineNr) {
        this.fullName = fullName;
        if (namespace == null) {
            this.name = fullName;
        }
        else {
            final int index = fullName.indexOf(58);
            if (index >= 0) {
                this.name = fullName.substring(index + 1);
            }
            else {
                this.name = fullName;
            }
        }
        this.namespace = namespace;
        this.lineNr = lineNr;
        this.systemID = systemID;
    }
    
    public XMLElement(final String fullName, final String namespace, final String systemID, final int lineNr) {
        this.attributes = new Vector<XMLAttribute>();
        this.children = new Vector<XMLElement>(8);
        this.fullName = fullName;
        if (namespace == null) {
            this.name = fullName;
        }
        else {
            final int index = fullName.indexOf(58);
            if (index >= 0) {
                this.name = fullName.substring(index + 1);
            }
            else {
                this.name = fullName;
            }
        }
        this.namespace = namespace;
        this.content = null;
        this.lineNr = lineNr;
        this.systemID = systemID;
        this.parent = null;
    }
    
    public XMLElement(final PApplet parent, final String filename) {
        this();
        this.parseFromReader(parent.createReader(filename));
    }
    
    public XMLElement(final Reader r) {
        this();
        this.parseFromReader(r);
    }
    
    public XMLElement(final String xml) {
        this();
        this.parseFromReader(new StringReader(xml));
    }
    
    protected void parseFromReader(final Reader r) {
        try {
            final StdXMLParser parser = new StdXMLParser();
            parser.setBuilder(new StdXMLBuilder(this));
            parser.setValidator(new XMLValidator());
            parser.setReader(new StdXMLReader(r));
            parser.parse();
        }
        catch (XMLException e) {
            e.printStackTrace();
        }
    }
    
    public XMLElement createPCDataElement() {
        return new XMLElement();
    }
    
    public XMLElement createElement(final String fullName, final String namespace) {
        return new XMLElement(fullName, namespace, null, -1);
    }
    
    public XMLElement createElement(final String fullName, final String namespace, final String systemID, final int lineNr) {
        return new XMLElement(fullName, namespace, systemID, lineNr);
    }
    
    protected void finalize() throws Throwable {
        this.attributes.clear();
        this.attributes = null;
        this.children = null;
        this.fullName = null;
        this.name = null;
        this.namespace = null;
        this.content = null;
        this.systemID = null;
        this.parent = null;
        super.finalize();
    }
    
    public XMLElement getParent() {
        return this.parent;
    }
    
    public String getName() {
        return this.fullName;
    }
    
    public String getLocalName() {
        return this.name;
    }
    
    public String getNamespace() {
        return this.namespace;
    }
    
    public void setName(final String name) {
        this.name = name;
        this.fullName = name;
        this.namespace = null;
    }
    
    public void setName(final String fullName, final String namespace) {
        final int index = fullName.indexOf(58);
        if (namespace == null || index < 0) {
            this.name = fullName;
        }
        else {
            this.name = fullName.substring(index + 1);
        }
        this.fullName = fullName;
        this.namespace = namespace;
    }
    
    public void addChild(final XMLElement child) {
        if (child == null) {
            throw new IllegalArgumentException("child must not be null");
        }
        if (child.getLocalName() == null && !this.children.isEmpty()) {
            final XMLElement lastChild = this.children.lastElement();
            if (lastChild.getLocalName() == null) {
                lastChild.setContent(String.valueOf(lastChild.getContent()) + child.getContent());
                return;
            }
        }
        child.parent = this;
        this.children.addElement(child);
    }
    
    public void insertChild(final XMLElement child, final int index) {
        if (child == null) {
            throw new IllegalArgumentException("child must not be null");
        }
        if (child.getLocalName() == null && !this.children.isEmpty()) {
            final XMLElement lastChild = this.children.lastElement();
            if (lastChild.getLocalName() == null) {
                lastChild.setContent(String.valueOf(lastChild.getContent()) + child.getContent());
                return;
            }
        }
        child.parent = this;
        this.children.insertElementAt(child, index);
    }
    
    public void removeChild(final XMLElement child) {
        if (child == null) {
            throw new IllegalArgumentException("child must not be null");
        }
        this.children.removeElement(child);
    }
    
    public void removeChildAtIndex(final int index) {
        this.children.removeElementAt(index);
    }
    
    public Enumeration<XMLElement> enumerateChildren() {
        return this.children.elements();
    }
    
    public boolean isLeaf() {
        return this.children.isEmpty();
    }
    
    public boolean hasChildren() {
        return !this.children.isEmpty();
    }
    
    public int getChildCount() {
        return this.children.size();
    }
    
    public String[] listChildren() {
        final int childCount = this.getChildCount();
        final String[] outgoing = new String[childCount];
        for (int i = 0; i < childCount; ++i) {
            outgoing[i] = this.getChild(i).getName();
        }
        return outgoing;
    }
    
    public XMLElement[] getChildren() {
        final int childCount = this.getChildCount();
        final XMLElement[] kids = new XMLElement[childCount];
        this.children.copyInto(kids);
        return kids;
    }
    
    public XMLElement getChild(final int index) {
        return this.children.elementAt(index);
    }
    
    public XMLElement getChild(final String path) {
        if (path.indexOf(47) != -1) {
            return this.getChildRecursive(PApplet.split(path, '/'), 0);
        }
        for (int childCount = this.getChildCount(), i = 0; i < childCount; ++i) {
            final XMLElement kid = this.getChild(i);
            final String kidName = kid.getName();
            if (kidName != null && kidName.equals(path)) {
                return kid;
            }
        }
        return null;
    }
    
    protected XMLElement getChildRecursive(final String[] items, final int offset) {
        if (!Character.isDigit(items[offset].charAt(0))) {
            final int childCount = this.getChildCount();
            int i = 0;
            while (i < childCount) {
                final XMLElement kid = this.getChild(i);
                final String kidName = kid.getName();
                if (kidName != null && kidName.equals(items[offset])) {
                    if (offset == items.length - 1) {
                        return kid;
                    }
                    return kid.getChildRecursive(items, offset + 1);
                }
                else {
                    ++i;
                }
            }
            return null;
        }
        final XMLElement kid2 = this.getChild(Integer.parseInt(items[offset]));
        if (offset == items.length - 1) {
            return kid2;
        }
        return kid2.getChildRecursive(items, offset + 1);
    }
    
    public XMLElement getChildAtIndex(final int index) throws ArrayIndexOutOfBoundsException {
        return this.children.elementAt(index);
    }
    
    public XMLElement[] getChildren(final String path) {
        if (path.indexOf(47) != -1) {
            return this.getChildrenRecursive(PApplet.split(path, '/'), 0);
        }
        if (Character.isDigit(path.charAt(0))) {
            return new XMLElement[] { this.getChild(Integer.parseInt(path)) };
        }
        final int childCount = this.getChildCount();
        final XMLElement[] matches = new XMLElement[childCount];
        int matchCount = 0;
        for (int i = 0; i < childCount; ++i) {
            final XMLElement kid = this.getChild(i);
            final String kidName = kid.getName();
            if (kidName != null && kidName.equals(path)) {
                matches[matchCount++] = kid;
            }
        }
        return (XMLElement[])PApplet.subset(matches, 0, matchCount);
    }
    
    protected XMLElement[] getChildrenRecursive(final String[] items, final int offset) {
        if (offset == items.length - 1) {
            return this.getChildren(items[offset]);
        }
        final XMLElement[] matches = this.getChildren(items[offset]);
        XMLElement[] outgoing = new XMLElement[0];
        for (int i = 0; i < matches.length; ++i) {
            final XMLElement[] kidMatches = matches[i].getChildrenRecursive(items, offset + 1);
            outgoing = (XMLElement[])PApplet.concat(outgoing, kidMatches);
        }
        return outgoing;
    }
    
    private XMLAttribute findAttribute(final String fullName) {
        final Enumeration<XMLAttribute> en = this.attributes.elements();
        while (en.hasMoreElements()) {
            final XMLAttribute attr = en.nextElement();
            if (attr.getFullName().equals(fullName)) {
                return attr;
            }
        }
        return null;
    }
    
    private XMLAttribute findAttribute(final String name, final String namespace) {
        final Enumeration<XMLAttribute> en = this.attributes.elements();
        while (en.hasMoreElements()) {
            final XMLAttribute attr = en.nextElement();
            boolean found = attr.getName().equals(name);
            if (namespace == null) {
                found &= (attr.getNamespace() == null);
            }
            else {
                found &= namespace.equals(attr.getNamespace());
            }
            if (found) {
                return attr;
            }
        }
        return null;
    }
    
    public int getAttributeCount() {
        return this.attributes.size();
    }
    
    public String getAttribute(final String name) {
        return this.getAttribute(name, null);
    }
    
    public String getAttribute(final String name, final String defaultValue) {
        final XMLAttribute attr = this.findAttribute(name);
        if (attr == null) {
            return defaultValue;
        }
        return attr.getValue();
    }
    
    public String getAttribute(final String name, final String namespace, final String defaultValue) {
        final XMLAttribute attr = this.findAttribute(name, namespace);
        if (attr == null) {
            return defaultValue;
        }
        return attr.getValue();
    }
    
    public String getStringAttribute(final String name) {
        return this.getAttribute(name);
    }
    
    public String getStringAttribute(final String name, final String defaultValue) {
        return this.getAttribute(name, defaultValue);
    }
    
    public String getStringAttribute(final String name, final String namespace, final String defaultValue) {
        return this.getAttribute(name, namespace, defaultValue);
    }
    
    public int getIntAttribute(final String name) {
        return this.getIntAttribute(name, 0);
    }
    
    public int getIntAttribute(final String name, final int defaultValue) {
        final String value = this.getAttribute(name, Integer.toString(defaultValue));
        return Integer.parseInt(value);
    }
    
    public int getIntAttribute(final String name, final String namespace, final int defaultValue) {
        final String value = this.getAttribute(name, namespace, Integer.toString(defaultValue));
        return Integer.parseInt(value);
    }
    
    public float getFloatAttribute(final String name) {
        return this.getFloatAttribute(name, 0.0f);
    }
    
    public float getFloatAttribute(final String name, final float defaultValue) {
        final String value = this.getAttribute(name, Float.toString(defaultValue));
        return Float.parseFloat(value);
    }
    
    public float getFloatAttribute(final String name, final String namespace, final float defaultValue) {
        final String value = this.getAttribute(name, namespace, Float.toString(defaultValue));
        return Float.parseFloat(value);
    }
    
    public double getDoubleAttribute(final String name) {
        return this.getDoubleAttribute(name, 0.0);
    }
    
    public double getDoubleAttribute(final String name, final double defaultValue) {
        final String value = this.getAttribute(name, Double.toString(defaultValue));
        return Double.parseDouble(value);
    }
    
    public double getDoubleAttribute(final String name, final String namespace, final double defaultValue) {
        final String value = this.getAttribute(name, namespace, Double.toString(defaultValue));
        return Double.parseDouble(value);
    }
    
    public String getAttributeType(final String name) {
        final XMLAttribute attr = this.findAttribute(name);
        if (attr == null) {
            return null;
        }
        return attr.getType();
    }
    
    public String getAttributeNamespace(final String name) {
        final XMLAttribute attr = this.findAttribute(name);
        if (attr == null) {
            return null;
        }
        return attr.getNamespace();
    }
    
    public String getAttributeType(final String name, final String namespace) {
        final XMLAttribute attr = this.findAttribute(name, namespace);
        if (attr == null) {
            return null;
        }
        return attr.getType();
    }
    
    public void setAttribute(final String name, final String value) {
        XMLAttribute attr = this.findAttribute(name);
        if (attr == null) {
            attr = new XMLAttribute(name, name, null, value, "CDATA");
            this.attributes.addElement(attr);
        }
        else {
            attr.setValue(value);
        }
    }
    
    public void setAttribute(final String fullName, final String namespace, final String value) {
        final int index = fullName.indexOf(58);
        final String vorname = fullName.substring(index + 1);
        XMLAttribute attr = this.findAttribute(vorname, namespace);
        if (attr == null) {
            attr = new XMLAttribute(fullName, vorname, namespace, value, "CDATA");
            this.attributes.addElement(attr);
        }
        else {
            attr.setValue(value);
        }
    }
    
    public void removeAttribute(final String name) {
        for (int i = 0; i < this.attributes.size(); ++i) {
            final XMLAttribute attr = this.attributes.elementAt(i);
            if (attr.getFullName().equals(name)) {
                this.attributes.removeElementAt(i);
                return;
            }
        }
    }
    
    public void removeAttribute(final String name, final String namespace) {
        for (int i = 0; i < this.attributes.size(); ++i) {
            final XMLAttribute attr = this.attributes.elementAt(i);
            boolean found = attr.getName().equals(name);
            if (namespace == null) {
                found &= (attr.getNamespace() == null);
            }
            else {
                found &= attr.getNamespace().equals(namespace);
            }
            if (found) {
                this.attributes.removeElementAt(i);
                return;
            }
        }
    }
    
    public Enumeration<String> enumerateAttributeNames() {
        final Vector<String> result = new Vector<String>();
        final Enumeration<XMLAttribute> en = this.attributes.elements();
        while (en.hasMoreElements()) {
            final XMLAttribute attr = en.nextElement();
            result.addElement(attr.getFullName());
        }
        return result.elements();
    }
    
    public boolean hasAttribute(final String name) {
        return this.findAttribute(name) != null;
    }
    
    public boolean hasAttribute(final String name, final String namespace) {
        return this.findAttribute(name, namespace) != null;
    }
    
    public Properties getAttributes() {
        final Properties result = new Properties();
        final Enumeration<XMLAttribute> en = this.attributes.elements();
        while (en.hasMoreElements()) {
            final XMLAttribute attr = en.nextElement();
            ((Hashtable<String, String>)result).put(attr.getFullName(), attr.getValue());
        }
        return result;
    }
    
    public Properties getAttributesInNamespace(final String namespace) {
        final Properties result = new Properties();
        final Enumeration<XMLAttribute> en = this.attributes.elements();
        while (en.hasMoreElements()) {
            final XMLAttribute attr = en.nextElement();
            if (namespace == null) {
                if (attr.getNamespace() != null) {
                    continue;
                }
                ((Hashtable<String, String>)result).put(attr.getName(), attr.getValue());
            }
            else {
                if (!namespace.equals(attr.getNamespace())) {
                    continue;
                }
                ((Hashtable<String, String>)result).put(attr.getName(), attr.getValue());
            }
        }
        return result;
    }
    
    public String getSystemID() {
        return this.systemID;
    }
    
    public int getLineNr() {
        return this.lineNr;
    }
    
    public String getContent() {
        return this.content;
    }
    
    public void setContent(final String content) {
        this.content = content;
    }
    
    public boolean equals(final Object rawElement) {
        try {
            return this.equalsXMLElement((XMLElement)rawElement);
        }
        catch (ClassCastException e) {
            return false;
        }
    }
    
    public boolean equalsXMLElement(final XMLElement rawElement) {
        if (!this.name.equals(rawElement.getLocalName())) {
            return false;
        }
        if (this.attributes.size() != rawElement.getAttributeCount()) {
            return false;
        }
        final Enumeration<XMLAttribute> en = this.attributes.elements();
        while (en.hasMoreElements()) {
            final XMLAttribute attr = en.nextElement();
            if (!rawElement.hasAttribute(attr.getName(), attr.getNamespace())) {
                return false;
            }
            final String value = rawElement.getAttribute(attr.getName(), attr.getNamespace(), null);
            if (!attr.getValue().equals(value)) {
                return false;
            }
            final String type = rawElement.getAttributeType(attr.getName(), attr.getNamespace());
            if (!attr.getType().equals(type)) {
                return false;
            }
        }
        if (this.children.size() != rawElement.getChildCount()) {
            return false;
        }
        for (int i = 0; i < this.children.size(); ++i) {
            final XMLElement child1 = this.getChildAtIndex(i);
            final XMLElement child2 = rawElement.getChildAtIndex(i);
            if (!child1.equalsXMLElement(child2)) {
                return false;
            }
        }
        return true;
    }
    
    public String toString() {
        return this.toString(true);
    }
    
    public String toString(final boolean pretty) {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final OutputStreamWriter osw = new OutputStreamWriter(baos);
        final XMLWriter writer = new XMLWriter(osw);
        try {
            if (pretty) {
                writer.write(this, true, 2, true);
            }
            else {
                writer.write(this, false, 0, true);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toString();
    }
}
