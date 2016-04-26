// 
// Decompiled by Procyon v0.5.30
// 

package processing.xml;

public class XMLParseException extends XMLException
{
    public XMLParseException(final String msg) {
        super(msg);
    }
    
    public XMLParseException(final String systemID, final int lineNr, final String msg) {
        super(systemID, lineNr, null, msg, true);
    }
}
