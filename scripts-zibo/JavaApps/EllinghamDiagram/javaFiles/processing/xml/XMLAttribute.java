// 
// Decompiled by Procyon v0.5.30
// 

package processing.xml;

class XMLAttribute
{
    private String fullName;
    private String name;
    private String namespace;
    private String value;
    private String type;
    
    XMLAttribute(final String fullName, final String name, final String namespace, final String value, final String type) {
        this.fullName = fullName;
        this.name = name;
        this.namespace = namespace;
        this.value = value;
        this.type = type;
    }
    
    String getFullName() {
        return this.fullName;
    }
    
    String getName() {
        return this.name;
    }
    
    String getNamespace() {
        return this.namespace;
    }
    
    String getValue() {
        return this.value;
    }
    
    void setValue(final String value) {
        this.value = value;
    }
    
    String getType() {
        return this.type;
    }
}
