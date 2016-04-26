// 
// Decompiled by Procyon v0.5.30
// 

package processing.xml;

import java.io.StringReader;
import java.io.Reader;
import java.util.Hashtable;

public class XMLEntityResolver
{
    private Hashtable<String, Object> entities;
    
    public XMLEntityResolver() {
        (this.entities = new Hashtable<String, Object>()).put("amp", "&#38;");
        this.entities.put("quot", "&#34;");
        this.entities.put("apos", "&#39;");
        this.entities.put("lt", "&#60;");
        this.entities.put("gt", "&#62;");
    }
    
    protected void finalize() throws Throwable {
        this.entities.clear();
        this.entities = null;
        super.finalize();
    }
    
    public void addInternalEntity(final String name, final String value) {
        if (!this.entities.containsKey(name)) {
            this.entities.put(name, value);
        }
    }
    
    public void addExternalEntity(final String name, final String publicID, final String systemID) {
        if (!this.entities.containsKey(name)) {
            this.entities.put(name, new String[] { publicID, systemID });
        }
    }
    
    public Reader getEntity(final StdXMLReader xmlReader, final String name) throws XMLParseException {
        final Object obj = this.entities.get(name);
        if (obj == null) {
            return null;
        }
        if (obj instanceof String) {
            return new StringReader((String)obj);
        }
        final String[] id = (String[])obj;
        return this.openExternalEntity(xmlReader, id[0], id[1]);
    }
    
    public boolean isExternalEntity(final String name) {
        final Object obj = this.entities.get(name);
        return !(obj instanceof String);
    }
    
    protected Reader openExternalEntity(final StdXMLReader xmlReader, final String publicID, final String systemID) throws XMLParseException {
        final String parentSystemID = xmlReader.getSystemID();
        try {
            return xmlReader.openStream(publicID, systemID);
        }
        catch (Exception e) {
            throw new XMLParseException(parentSystemID, xmlReader.getLineNr(), "Could not open external entity at system ID: " + systemID);
        }
    }
}
