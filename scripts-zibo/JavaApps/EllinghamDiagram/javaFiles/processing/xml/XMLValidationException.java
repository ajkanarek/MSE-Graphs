// 
// Decompiled by Procyon v0.5.30
// 

package processing.xml;

public class XMLValidationException extends XMLException
{
    public static final int MISSING_ELEMENT = 1;
    public static final int UNEXPECTED_ELEMENT = 2;
    public static final int MISSING_ATTRIBUTE = 3;
    public static final int UNEXPECTED_ATTRIBUTE = 4;
    public static final int ATTRIBUTE_WITH_INVALID_VALUE = 5;
    public static final int MISSING_PCDATA = 6;
    public static final int UNEXPECTED_PCDATA = 7;
    public static final int MISC_ERROR = 0;
    private String elementName;
    private String attributeName;
    private String attributeValue;
    
    public XMLValidationException(final int errorType, final String systemID, final int lineNr, final String elementName, final String attributeName, final String attributeValue, final String msg) {
        super(systemID, lineNr, null, String.valueOf(msg) + ((elementName == null) ? "" : (", element=" + elementName)) + ((attributeName == null) ? "" : (", attribute=" + attributeName)) + ((attributeValue == null) ? "" : (", value='" + attributeValue + "'")), false);
        this.elementName = elementName;
        this.attributeName = attributeName;
        this.attributeValue = attributeValue;
    }
    
    protected void finalize() throws Throwable {
        this.elementName = null;
        this.attributeName = null;
        this.attributeValue = null;
        super.finalize();
    }
    
    public String getElementName() {
        return this.elementName;
    }
    
    public String getAttributeName() {
        return this.attributeName;
    }
    
    public String getAttributeValue() {
        return this.attributeValue;
    }
}
