// 
// Decompiled by Procyon v0.5.30
// 

package processing.xml;

import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Vector;
import java.io.Reader;
import java.util.Properties;

public class StdXMLParser
{
    private StdXMLBuilder builder;
    private StdXMLReader reader;
    private XMLEntityResolver entityResolver;
    private XMLValidator validator;
    
    public StdXMLParser() {
        this.builder = null;
        this.validator = null;
        this.reader = null;
        this.entityResolver = new XMLEntityResolver();
    }
    
    protected void finalize() throws Throwable {
        this.builder = null;
        this.reader = null;
        this.entityResolver = null;
        this.validator = null;
        super.finalize();
    }
    
    public void setBuilder(final StdXMLBuilder builder) {
        this.builder = builder;
    }
    
    public StdXMLBuilder getBuilder() {
        return this.builder;
    }
    
    public void setValidator(final XMLValidator validator) {
        this.validator = validator;
    }
    
    public XMLValidator getValidator() {
        return this.validator;
    }
    
    public void setResolver(final XMLEntityResolver resolver) {
        this.entityResolver = resolver;
    }
    
    public XMLEntityResolver getResolver() {
        return this.entityResolver;
    }
    
    public void setReader(final StdXMLReader reader) {
        this.reader = reader;
    }
    
    public StdXMLReader getReader() {
        return this.reader;
    }
    
    public Object parse() throws XMLException {
        try {
            this.builder.startBuilding(this.reader.getSystemID(), this.reader.getLineNr());
            this.scanData();
            return this.builder.getResult();
        }
        catch (XMLException e) {
            throw e;
        }
        catch (Exception e2) {
            throw new XMLException(e2);
        }
    }
    
    protected void scanData() throws Exception {
        while (!this.reader.atEOF() && this.builder.getResult() == null) {
            final String str = XMLUtil.read(this.reader, '&');
            final char ch = str.charAt(0);
            if (ch == '&') {
                XMLUtil.processEntity(str, this.reader, this.entityResolver);
            }
            else {
                switch (ch) {
                    case '<': {
                        this.scanSomeTag(false, null, new Properties());
                        continue;
                    }
                    case '\t':
                    case '\n':
                    case '\r':
                    case ' ': {
                        continue;
                    }
                    default: {
                        XMLUtil.errorInvalidInput(this.reader.getSystemID(), this.reader.getLineNr(), "`" + ch + "' (0x" + Integer.toHexString(ch) + ')');
                        continue;
                    }
                }
            }
        }
    }
    
    protected void scanSomeTag(final boolean allowCDATA, final String defaultNamespace, final Properties namespaces) throws Exception {
        final String str = XMLUtil.read(this.reader, '&');
        final char ch = str.charAt(0);
        if (ch == '&') {
            XMLUtil.errorUnexpectedEntity(this.reader.getSystemID(), this.reader.getLineNr(), str);
        }
        switch (ch) {
            case '?': {
                this.processPI();
                break;
            }
            case '!': {
                this.processSpecialTag(allowCDATA);
                break;
            }
            default: {
                this.reader.unread(ch);
                this.processElement(defaultNamespace, namespaces);
                break;
            }
        }
    }
    
    protected void processPI() throws Exception {
        XMLUtil.skipWhitespace(this.reader, null);
        final String target = XMLUtil.scanIdentifier(this.reader);
        XMLUtil.skipWhitespace(this.reader, null);
        final Reader r = new PIReader(this.reader);
        if (!target.equalsIgnoreCase("xml")) {
            this.builder.newProcessingInstruction(target, r);
        }
        r.close();
    }
    
    protected void processSpecialTag(final boolean allowCDATA) throws Exception {
        final String str = XMLUtil.read(this.reader, '&');
        final char ch = str.charAt(0);
        if (ch == '&') {
            XMLUtil.errorUnexpectedEntity(this.reader.getSystemID(), this.reader.getLineNr(), str);
        }
        switch (ch) {
            case '[': {
                if (allowCDATA) {
                    this.processCDATA();
                }
                else {
                    XMLUtil.errorUnexpectedCDATA(this.reader.getSystemID(), this.reader.getLineNr());
                }
            }
            case 'D': {
                this.processDocType();
            }
            case '-': {
                XMLUtil.skipComment(this.reader);
            }
            default: {}
        }
    }
    
    protected void processCDATA() throws Exception {
        if (!XMLUtil.checkLiteral(this.reader, "CDATA[")) {
            XMLUtil.errorExpectedInput(this.reader.getSystemID(), this.reader.getLineNr(), "<![[CDATA[");
        }
        this.validator.PCDataAdded(this.reader.getSystemID(), this.reader.getLineNr());
        final Reader r = new CDATAReader(this.reader);
        this.builder.addPCData(r, this.reader.getSystemID(), this.reader.getLineNr());
        r.close();
    }
    
    protected void processDocType() throws Exception {
        if (!XMLUtil.checkLiteral(this.reader, "OCTYPE")) {
            XMLUtil.errorExpectedInput(this.reader.getSystemID(), this.reader.getLineNr(), "<!DOCTYPE");
            return;
        }
        XMLUtil.skipWhitespace(this.reader, null);
        String systemID = null;
        final StringBuffer publicID = new StringBuffer();
        XMLUtil.scanIdentifier(this.reader);
        XMLUtil.skipWhitespace(this.reader, null);
        char ch = this.reader.read();
        if (ch == 'P') {
            systemID = XMLUtil.scanPublicID(publicID, this.reader);
            XMLUtil.skipWhitespace(this.reader, null);
            ch = this.reader.read();
        }
        else if (ch == 'S') {
            systemID = XMLUtil.scanSystemID(this.reader);
            XMLUtil.skipWhitespace(this.reader, null);
            ch = this.reader.read();
        }
        if (ch == '[') {
            this.validator.parseDTD(publicID.toString(), this.reader, this.entityResolver, false);
            XMLUtil.skipWhitespace(this.reader, null);
            ch = this.reader.read();
        }
        if (ch != '>') {
            XMLUtil.errorExpectedInput(this.reader.getSystemID(), this.reader.getLineNr(), "`>'");
        }
    }
    
    protected void processElement(String defaultNamespace, final Properties namespaces) throws Exception {
        String name;
        final String fullName = name = XMLUtil.scanIdentifier(this.reader);
        XMLUtil.skipWhitespace(this.reader, null);
        String prefix = null;
        int colonIndex = name.indexOf(58);
        if (colonIndex > 0) {
            prefix = name.substring(0, colonIndex);
            name = name.substring(colonIndex + 1);
        }
        final Vector<String> attrNames = new Vector<String>();
        final Vector<String> attrValues = new Vector<String>();
        final Vector<String> attrTypes = new Vector<String>();
        this.validator.elementStarted(fullName, this.reader.getSystemID(), this.reader.getLineNr());
        char ch;
        while (true) {
            ch = this.reader.read();
            if (ch == '/' || ch == '>') {
                break;
            }
            this.reader.unread(ch);
            this.processAttribute(attrNames, attrValues, attrTypes);
            XMLUtil.skipWhitespace(this.reader, null);
        }
        final Properties extraAttributes = new Properties();
        this.validator.elementAttributesProcessed(fullName, extraAttributes, this.reader.getSystemID(), this.reader.getLineNr());
        final Enumeration<?> en = ((Hashtable<?, V>)extraAttributes).keys();
        while (en.hasMoreElements()) {
            final String key = (String)en.nextElement();
            final String value = extraAttributes.getProperty(key);
            attrNames.addElement(key);
            attrValues.addElement(value);
            attrTypes.addElement("CDATA");
        }
        for (int i = 0; i < attrNames.size(); ++i) {
            final String key2 = attrNames.elementAt(i);
            final String value2 = attrValues.elementAt(i);
            if (key2.equals("xmlns")) {
                defaultNamespace = value2;
            }
            else if (key2.startsWith("xmlns:")) {
                ((Hashtable<String, String>)namespaces).put(key2.substring(6), value2);
            }
        }
        if (prefix == null) {
            this.builder.startElement(name, prefix, defaultNamespace, this.reader.getSystemID(), this.reader.getLineNr());
        }
        else {
            this.builder.startElement(name, prefix, namespaces.getProperty(prefix), this.reader.getSystemID(), this.reader.getLineNr());
        }
        for (int i = 0; i < attrNames.size(); ++i) {
            String key2 = attrNames.elementAt(i);
            if (!key2.startsWith("xmlns")) {
                final String value2 = attrValues.elementAt(i);
                final String type = attrTypes.elementAt(i);
                colonIndex = key2.indexOf(58);
                if (colonIndex > 0) {
                    final String attPrefix = key2.substring(0, colonIndex);
                    key2 = key2.substring(colonIndex + 1);
                    this.builder.addAttribute(key2, attPrefix, namespaces.getProperty(attPrefix), value2, type);
                }
                else {
                    this.builder.addAttribute(key2, null, null, value2, type);
                }
            }
        }
        if (prefix == null) {
            this.builder.elementAttributesProcessed(name, prefix, defaultNamespace);
        }
        else {
            this.builder.elementAttributesProcessed(name, prefix, namespaces.getProperty(prefix));
        }
        if (ch == '/') {
            if (this.reader.read() != '>') {
                XMLUtil.errorExpectedInput(this.reader.getSystemID(), this.reader.getLineNr(), "`>'");
            }
            this.validator.elementEnded(name, this.reader.getSystemID(), this.reader.getLineNr());
            if (prefix == null) {
                this.builder.endElement(name, prefix, defaultNamespace);
            }
            else {
                this.builder.endElement(name, prefix, namespaces.getProperty(prefix));
            }
            return;
        }
        final StringBuffer buffer = new StringBuffer(16);
        while (true) {
            buffer.setLength(0);
            String str;
            while (true) {
                XMLUtil.skipWhitespace(this.reader, buffer);
                str = XMLUtil.read(this.reader, '&');
                if (str.charAt(0) != '&' || str.charAt(1) == '#') {
                    break;
                }
                XMLUtil.processEntity(str, this.reader, this.entityResolver);
            }
            if (str.charAt(0) == '<') {
                str = XMLUtil.read(this.reader, '\0');
                if (str.charAt(0) == '/') {
                    break;
                }
                this.reader.unread(str.charAt(0));
                this.scanSomeTag(true, defaultNamespace, (Properties)namespaces.clone());
            }
            else {
                if (str.charAt(0) == '&') {
                    ch = XMLUtil.processCharLiteral(str);
                    buffer.append(ch);
                }
                else {
                    this.reader.unread(str.charAt(0));
                }
                this.validator.PCDataAdded(this.reader.getSystemID(), this.reader.getLineNr());
                final Reader r = new ContentReader(this.reader, this.entityResolver, buffer.toString());
                this.builder.addPCData(r, this.reader.getSystemID(), this.reader.getLineNr());
                r.close();
            }
        }
        XMLUtil.skipWhitespace(this.reader, null);
        String str = XMLUtil.scanIdentifier(this.reader);
        if (!str.equals(fullName)) {
            XMLUtil.errorWrongClosingTag(this.reader.getSystemID(), this.reader.getLineNr(), name, str);
        }
        XMLUtil.skipWhitespace(this.reader, null);
        if (this.reader.read() != '>') {
            XMLUtil.errorClosingTagNotEmpty(this.reader.getSystemID(), this.reader.getLineNr());
        }
        this.validator.elementEnded(fullName, this.reader.getSystemID(), this.reader.getLineNr());
        if (prefix == null) {
            this.builder.endElement(name, prefix, defaultNamespace);
        }
        else {
            this.builder.endElement(name, prefix, namespaces.getProperty(prefix));
        }
    }
    
    protected void processAttribute(final Vector<String> attrNames, final Vector<String> attrValues, final Vector<String> attrTypes) throws Exception {
        final String key = XMLUtil.scanIdentifier(this.reader);
        XMLUtil.skipWhitespace(this.reader, null);
        if (!XMLUtil.read(this.reader, '&').equals("=")) {
            XMLUtil.errorExpectedInput(this.reader.getSystemID(), this.reader.getLineNr(), "`='");
        }
        XMLUtil.skipWhitespace(this.reader, null);
        final String value = XMLUtil.scanString(this.reader, '&', this.entityResolver);
        attrNames.addElement(key);
        attrValues.addElement(value);
        attrTypes.addElement("CDATA");
        this.validator.attributeAdded(key, value, this.reader.getSystemID(), this.reader.getLineNr());
    }
}
