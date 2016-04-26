// 
// Decompiled by Procyon v0.5.30
// 

package processing.xml;

import java.util.Enumeration;
import java.util.Vector;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.io.PrintWriter;

public class XMLWriter
{
    private PrintWriter writer;
    
    public XMLWriter(final Writer writer) {
        if (writer instanceof PrintWriter) {
            this.writer = (PrintWriter)writer;
        }
        else {
            this.writer = new PrintWriter(writer);
        }
    }
    
    public XMLWriter(final OutputStream stream) {
        this.writer = new PrintWriter(stream);
    }
    
    protected void finalize() throws Throwable {
        this.writer = null;
        super.finalize();
    }
    
    public void write(final XMLElement xml) throws IOException {
        this.write(xml, false, 0, true);
    }
    
    public void write(final XMLElement xml, final boolean prettyPrint) throws IOException {
        this.write(xml, prettyPrint, 0, true);
    }
    
    public void write(final XMLElement xml, final boolean prettyPrint, final int indent) throws IOException {
        this.write(xml, prettyPrint, indent, true);
    }
    
    public void write(final XMLElement xml, final boolean prettyPrint, final int indent, final boolean collapseEmptyElements) throws IOException {
        if (prettyPrint) {
            for (int i = 0; i < indent; ++i) {
                this.writer.print(' ');
            }
        }
        if (xml.getLocalName() == null) {
            if (xml.getContent() != null) {
                if (prettyPrint) {
                    this.writeEncoded(xml.getContent().trim());
                    this.writer.println();
                }
                else {
                    this.writeEncoded(xml.getContent());
                }
            }
        }
        else {
            this.writer.print('<');
            this.writer.print(xml.getName());
            final Vector<String> nsprefixes = new Vector<String>();
            if (xml.getNamespace() != null) {
                if (xml.getLocalName().equals(xml.getName())) {
                    this.writer.print(" xmlns=\"" + xml.getNamespace() + '\"');
                }
                else {
                    String prefix = xml.getName();
                    prefix = prefix.substring(0, prefix.indexOf(58));
                    nsprefixes.addElement(prefix);
                    this.writer.print(" xmlns:" + prefix);
                    this.writer.print("=\"" + xml.getNamespace() + "\"");
                }
            }
            Enumeration<?> en = xml.enumerateAttributeNames();
            while (en.hasMoreElements()) {
                final String key = (String)en.nextElement();
                final int index = key.indexOf(58);
                if (index >= 0) {
                    final String namespace = xml.getAttributeNamespace(key);
                    if (namespace == null) {
                        continue;
                    }
                    final String prefix2 = key.substring(0, index);
                    if (nsprefixes.contains(prefix2)) {
                        continue;
                    }
                    this.writer.print(" xmlns:" + prefix2);
                    this.writer.print("=\"" + namespace + '\"');
                    nsprefixes.addElement(prefix2);
                }
            }
            en = xml.enumerateAttributeNames();
            while (en.hasMoreElements()) {
                final String key = (String)en.nextElement();
                final String value = xml.getAttribute(key, null);
                this.writer.print(" " + key + "=\"");
                this.writeEncoded(value);
                this.writer.print('\"');
            }
            if (xml.getContent() != null && xml.getContent().length() > 0) {
                this.writer.print('>');
                this.writeEncoded(xml.getContent());
                this.writer.print("</" + xml.getName() + '>');
                if (prettyPrint) {
                    this.writer.println();
                }
            }
            else if (xml.hasChildren() || !collapseEmptyElements) {
                this.writer.print('>');
                if (prettyPrint) {
                    this.writer.println();
                }
                en = xml.enumerateChildren();
                while (en.hasMoreElements()) {
                    final XMLElement child = (XMLElement)en.nextElement();
                    this.write(child, prettyPrint, indent + 4, collapseEmptyElements);
                }
                if (prettyPrint) {
                    for (int j = 0; j < indent; ++j) {
                        this.writer.print(' ');
                    }
                }
                this.writer.print("</" + xml.getName() + ">");
                if (prettyPrint) {
                    this.writer.println();
                }
            }
            else {
                this.writer.print("/>");
                if (prettyPrint) {
                    this.writer.println();
                }
            }
        }
        this.writer.flush();
    }
    
    private void writeEncoded(final String str) {
        for (int i = 0; i < str.length(); ++i) {
            final char c = str.charAt(i);
            switch (c) {
                case '\n': {
                    this.writer.print(c);
                    break;
                }
                case '<': {
                    this.writer.print("&lt;");
                    break;
                }
                case '>': {
                    this.writer.print("&gt;");
                    break;
                }
                case '&': {
                    this.writer.print("&amp;");
                    break;
                }
                case '\'': {
                    this.writer.print("&apos;");
                    break;
                }
                case '\"': {
                    this.writer.print("&quot;");
                    break;
                }
                default: {
                    if (c < ' ' || c > '~') {
                        this.writer.print("&#x");
                        this.writer.print(Integer.toString(c, 16));
                        this.writer.print(';');
                        break;
                    }
                    this.writer.print(c);
                    break;
                }
            }
        }
    }
}
