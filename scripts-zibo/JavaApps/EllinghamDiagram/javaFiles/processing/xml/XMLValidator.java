// 
// Decompiled by Procyon v0.5.30
// 

package processing.xml;

import java.util.Enumeration;
import java.io.Reader;
import java.io.StringReader;
import java.util.Stack;
import java.util.Properties;
import java.util.Hashtable;

public class XMLValidator
{
    protected XMLEntityResolver parameterEntityResolver;
    protected Hashtable<String, Properties> attributeDefaultValues;
    protected Stack<Properties> currentElements;
    
    public XMLValidator() {
        this.attributeDefaultValues = new Hashtable<String, Properties>();
        this.currentElements = new Stack<Properties>();
        this.parameterEntityResolver = new XMLEntityResolver();
    }
    
    protected void finalize() throws Throwable {
        this.parameterEntityResolver = null;
        this.attributeDefaultValues.clear();
        this.attributeDefaultValues = null;
        this.currentElements.clear();
        this.currentElements = null;
        super.finalize();
    }
    
    public void setParameterEntityResolver(final XMLEntityResolver resolver) {
        this.parameterEntityResolver = resolver;
    }
    
    public XMLEntityResolver getParameterEntityResolver() {
        return this.parameterEntityResolver;
    }
    
    public void parseDTD(final String publicID, final StdXMLReader reader, final XMLEntityResolver entityResolver, final boolean external) throws Exception {
        XMLUtil.skipWhitespace(reader, null);
        final int origLevel = reader.getStreamLevel();
        while (true) {
            final String str = XMLUtil.read(reader, '%');
            char ch = str.charAt(0);
            if (ch == '%') {
                XMLUtil.processEntity(str, reader, this.parameterEntityResolver);
            }
            else {
                if (ch == '<') {
                    this.processElement(reader, entityResolver);
                }
                else {
                    if (ch == ']') {
                        return;
                    }
                    XMLUtil.errorInvalidInput(reader.getSystemID(), reader.getLineNr(), str);
                }
                do {
                    ch = reader.read();
                    if (external && reader.getStreamLevel() < origLevel) {
                        reader.unread(ch);
                        return;
                    }
                } while (ch == ' ' || ch == '\t' || ch == '\n' || ch == '\r');
                reader.unread(ch);
            }
        }
    }
    
    protected void processElement(final StdXMLReader reader, final XMLEntityResolver entityResolver) throws Exception {
        String str = XMLUtil.read(reader, '%');
        char ch = str.charAt(0);
        if (ch != '!') {
            XMLUtil.skipTag(reader);
            return;
        }
        str = XMLUtil.read(reader, '%');
        ch = str.charAt(0);
        switch (ch) {
            case '-': {
                XMLUtil.skipComment(reader);
                break;
            }
            case '[': {
                this.processConditionalSection(reader, entityResolver);
                break;
            }
            case 'E': {
                this.processEntity(reader, entityResolver);
                break;
            }
            case 'A': {
                this.processAttList(reader, entityResolver);
                break;
            }
            default: {
                XMLUtil.skipTag(reader);
                break;
            }
        }
    }
    
    protected void processConditionalSection(final StdXMLReader reader, final XMLEntityResolver entityResolver) throws Exception {
        XMLUtil.skipWhitespace(reader, null);
        String str = XMLUtil.read(reader, '%');
        char ch = str.charAt(0);
        if (ch != 'I') {
            XMLUtil.skipTag(reader);
            return;
        }
        str = XMLUtil.read(reader, '%');
        ch = str.charAt(0);
        switch (ch) {
            case 'G': {
                this.processIgnoreSection(reader, entityResolver);
            }
            case 'N': {
                if (!XMLUtil.checkLiteral(reader, "CLUDE")) {
                    XMLUtil.skipTag(reader);
                    return;
                }
                XMLUtil.skipWhitespace(reader, null);
                str = XMLUtil.read(reader, '%');
                ch = str.charAt(0);
                if (ch != '[') {
                    XMLUtil.skipTag(reader);
                    return;
                }
                final Reader subreader = new CDATAReader(reader);
                final StringBuffer buf = new StringBuffer(1024);
                while (true) {
                    final int ch2 = subreader.read();
                    if (ch2 < 0) {
                        break;
                    }
                    buf.append((char)ch2);
                }
                subreader.close();
                reader.startNewStream(new StringReader(buf.toString()));
            }
            default: {
                XMLUtil.skipTag(reader);
            }
        }
    }
    
    protected void processIgnoreSection(final StdXMLReader reader, final XMLEntityResolver entityResolver) throws Exception {
        if (!XMLUtil.checkLiteral(reader, "NORE")) {
            XMLUtil.skipTag(reader);
            return;
        }
        XMLUtil.skipWhitespace(reader, null);
        final String str = XMLUtil.read(reader, '%');
        final char ch = str.charAt(0);
        if (ch != '[') {
            XMLUtil.skipTag(reader);
            return;
        }
        final Reader subreader = new CDATAReader(reader);
        subreader.close();
    }
    
    protected void processAttList(final StdXMLReader reader, final XMLEntityResolver entityResolver) throws Exception {
        if (!XMLUtil.checkLiteral(reader, "TTLIST")) {
            XMLUtil.skipTag(reader);
            return;
        }
        XMLUtil.skipWhitespace(reader, null);
        String str;
        char ch;
        for (str = XMLUtil.read(reader, '%'), ch = str.charAt(0); ch == '%'; ch = str.charAt(0)) {
            XMLUtil.processEntity(str, reader, this.parameterEntityResolver);
            str = XMLUtil.read(reader, '%');
        }
        reader.unread(ch);
        final String elementName = XMLUtil.scanIdentifier(reader);
        XMLUtil.skipWhitespace(reader, null);
        for (str = XMLUtil.read(reader, '%'), ch = str.charAt(0); ch == '%'; ch = str.charAt(0)) {
            XMLUtil.processEntity(str, reader, this.parameterEntityResolver);
            str = XMLUtil.read(reader, '%');
        }
        final Properties props = new Properties();
        while (ch != '>') {
            reader.unread(ch);
            final String attName = XMLUtil.scanIdentifier(reader);
            XMLUtil.skipWhitespace(reader, null);
            for (str = XMLUtil.read(reader, '%'), ch = str.charAt(0); ch == '%'; ch = str.charAt(0)) {
                XMLUtil.processEntity(str, reader, this.parameterEntityResolver);
                str = XMLUtil.read(reader, '%');
            }
            if (ch == '(') {
                while (ch != ')') {
                    for (str = XMLUtil.read(reader, '%'), ch = str.charAt(0); ch == '%'; ch = str.charAt(0)) {
                        XMLUtil.processEntity(str, reader, this.parameterEntityResolver);
                        str = XMLUtil.read(reader, '%');
                    }
                }
            }
            else {
                reader.unread(ch);
                XMLUtil.scanIdentifier(reader);
            }
            XMLUtil.skipWhitespace(reader, null);
            for (str = XMLUtil.read(reader, '%'), ch = str.charAt(0); ch == '%'; ch = str.charAt(0)) {
                XMLUtil.processEntity(str, reader, this.parameterEntityResolver);
                str = XMLUtil.read(reader, '%');
            }
            if (ch == '#') {
                str = XMLUtil.scanIdentifier(reader);
                XMLUtil.skipWhitespace(reader, null);
                if (!str.equals("FIXED")) {
                    XMLUtil.skipWhitespace(reader, null);
                    for (str = XMLUtil.read(reader, '%'), ch = str.charAt(0); ch == '%'; ch = str.charAt(0)) {
                        XMLUtil.processEntity(str, reader, this.parameterEntityResolver);
                        str = XMLUtil.read(reader, '%');
                    }
                    continue;
                }
            }
            else {
                reader.unread(ch);
            }
            final String value = XMLUtil.scanString(reader, '%', this.parameterEntityResolver);
            ((Hashtable<String, String>)props).put(attName, value);
            XMLUtil.skipWhitespace(reader, null);
            for (str = XMLUtil.read(reader, '%'), ch = str.charAt(0); ch == '%'; ch = str.charAt(0)) {
                XMLUtil.processEntity(str, reader, this.parameterEntityResolver);
                str = XMLUtil.read(reader, '%');
            }
        }
        if (!props.isEmpty()) {
            this.attributeDefaultValues.put(elementName, props);
        }
    }
    
    protected void processEntity(final StdXMLReader reader, XMLEntityResolver entityResolver) throws Exception {
        if (!XMLUtil.checkLiteral(reader, "NTITY")) {
            XMLUtil.skipTag(reader);
            return;
        }
        XMLUtil.skipWhitespace(reader, null);
        char ch = XMLUtil.readChar(reader, '\0');
        if (ch == '%') {
            XMLUtil.skipWhitespace(reader, null);
            entityResolver = this.parameterEntityResolver;
        }
        else {
            reader.unread(ch);
        }
        final String key = XMLUtil.scanIdentifier(reader);
        XMLUtil.skipWhitespace(reader, null);
        ch = XMLUtil.readChar(reader, '%');
        String systemID = null;
        String publicID = null;
        switch (ch) {
            case 'P': {
                if (!XMLUtil.checkLiteral(reader, "UBLIC")) {
                    XMLUtil.skipTag(reader);
                    return;
                }
                XMLUtil.skipWhitespace(reader, null);
                publicID = XMLUtil.scanString(reader, '%', this.parameterEntityResolver);
                XMLUtil.skipWhitespace(reader, null);
                systemID = XMLUtil.scanString(reader, '%', this.parameterEntityResolver);
                XMLUtil.skipWhitespace(reader, null);
                XMLUtil.readChar(reader, '%');
                break;
            }
            case 'S': {
                if (!XMLUtil.checkLiteral(reader, "YSTEM")) {
                    XMLUtil.skipTag(reader);
                    return;
                }
                XMLUtil.skipWhitespace(reader, null);
                systemID = XMLUtil.scanString(reader, '%', this.parameterEntityResolver);
                XMLUtil.skipWhitespace(reader, null);
                XMLUtil.readChar(reader, '%');
                break;
            }
            case '\"':
            case '\'': {
                reader.unread(ch);
                final String value = XMLUtil.scanString(reader, '%', this.parameterEntityResolver);
                entityResolver.addInternalEntity(key, value);
                XMLUtil.skipWhitespace(reader, null);
                XMLUtil.readChar(reader, '%');
                break;
            }
            default: {
                XMLUtil.skipTag(reader);
                break;
            }
        }
        if (systemID != null) {
            entityResolver.addExternalEntity(key, publicID, systemID);
        }
    }
    
    public void elementStarted(final String name, final String systemId, final int lineNr) {
        Properties attribs = this.attributeDefaultValues.get(name);
        if (attribs == null) {
            attribs = new Properties();
        }
        else {
            attribs = (Properties)attribs.clone();
        }
        this.currentElements.push(attribs);
    }
    
    public void elementEnded(final String name, final String systemId, final int lineNr) {
    }
    
    public void elementAttributesProcessed(final String name, final Properties extraAttributes, final String systemId, final int lineNr) {
        final Properties props = this.currentElements.pop();
        final Enumeration<?> en = ((Hashtable<?, V>)props).keys();
        while (en.hasMoreElements()) {
            final String key = (String)en.nextElement();
            ((Hashtable<String, Object>)extraAttributes).put(key, ((Hashtable<K, Object>)props).get(key));
        }
    }
    
    public void attributeAdded(final String key, final String value, final String systemId, final int lineNr) {
        final Properties props = this.currentElements.peek();
        if (props.containsKey(key)) {
            props.remove(key);
        }
    }
    
    public void PCDataAdded(final String systemId, final int lineNr) {
    }
}
