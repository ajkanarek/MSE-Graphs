// 
// Decompiled by Procyon v0.5.30
// 

package processing.xml;

import java.io.IOException;
import java.io.Reader;
import java.util.Stack;

public class StdXMLBuilder
{
    private Stack<XMLElement> stack;
    private XMLElement root;
    private XMLElement parent;
    
    public StdXMLBuilder() {
        this.stack = null;
        this.root = null;
    }
    
    public StdXMLBuilder(final XMLElement parent) {
        this.parent = parent;
    }
    
    protected void finalize() throws Throwable {
        this.root = null;
        this.stack.clear();
        this.stack = null;
        super.finalize();
    }
    
    public void startBuilding(final String systemID, final int lineNr) {
        this.stack = new Stack<XMLElement>();
        this.root = null;
    }
    
    public void newProcessingInstruction(final String target, final Reader reader) {
    }
    
    public void startElement(final String name, final String nsPrefix, final String nsURI, final String systemID, final int lineNr) {
        String fullName = name;
        if (nsPrefix != null) {
            fullName = String.valueOf(nsPrefix) + ':' + name;
        }
        if (this.stack.empty()) {
            this.parent.set(fullName, nsURI, systemID, lineNr);
            this.stack.push(this.parent);
            this.root = this.parent;
        }
        else {
            final XMLElement top = this.stack.peek();
            final XMLElement elt = new XMLElement(fullName, nsURI, systemID, lineNr);
            top.addChild(elt);
            this.stack.push(elt);
        }
    }
    
    public void elementAttributesProcessed(final String name, final String nsPrefix, final String nsURI) {
    }
    
    public void endElement(final String name, final String nsPrefix, final String nsURI) {
        final XMLElement elt = this.stack.pop();
        if (elt.getChildCount() == 1) {
            final XMLElement child = elt.getChildAtIndex(0);
            if (child.getLocalName() == null) {
                elt.setContent(child.getContent());
                elt.removeChildAtIndex(0);
            }
        }
    }
    
    public void addAttribute(final String key, final String nsPrefix, final String nsURI, final String value, final String type) throws Exception {
        String fullName = key;
        if (nsPrefix != null) {
            fullName = String.valueOf(nsPrefix) + ':' + key;
        }
        final XMLElement top = this.stack.peek();
        if (top.hasAttribute(fullName)) {
            throw new XMLParseException(top.getSystemID(), top.getLineNr(), "Duplicate attribute: " + key);
        }
        if (nsPrefix != null) {
            top.setAttribute(fullName, nsURI, value);
        }
        else {
            top.setAttribute(fullName, value);
        }
    }
    
    public void addPCData(final Reader reader, final String systemID, final int lineNr) {
        int bufSize = 2048;
        int sizeRead = 0;
        final StringBuffer str = new StringBuffer(bufSize);
        final char[] buf = new char[bufSize];
        while (true) {
            if (sizeRead >= bufSize) {
                bufSize *= 2;
                str.ensureCapacity(bufSize);
            }
            int size;
            try {
                size = reader.read(buf);
            }
            catch (IOException e) {
                break;
            }
            if (size < 0) {
                break;
            }
            str.append(buf, 0, size);
            sizeRead += size;
        }
        final XMLElement elt = new XMLElement(null, null, systemID, lineNr);
        elt.setContent(str.toString());
        if (!this.stack.empty()) {
            final XMLElement top = this.stack.peek();
            top.addChild(elt);
        }
    }
    
    public Object getResult() {
        return this.root;
    }
}
