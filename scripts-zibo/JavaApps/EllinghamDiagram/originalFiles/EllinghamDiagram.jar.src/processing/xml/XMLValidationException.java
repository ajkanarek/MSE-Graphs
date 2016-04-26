/*     */ package processing.xml;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLValidationException
/*     */   extends XMLException
/*     */ {
/*     */   public static final int MISSING_ELEMENT = 1;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final int UNEXPECTED_ELEMENT = 2;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final int MISSING_ATTRIBUTE = 3;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final int UNEXPECTED_ATTRIBUTE = 4;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final int ATTRIBUTE_WITH_INVALID_VALUE = 5;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final int MISSING_PCDATA = 6;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final int UNEXPECTED_PCDATA = 7;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final int MISC_ERROR = 0;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private String elementName;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private String attributeName;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private String attributeValue;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public XMLValidationException(int errorType, String systemID, int lineNr, String elementName, String attributeName, String attributeValue, String msg)
/*     */   {
/* 141 */     super(systemID, lineNr, null, msg + (elementName == null ? "" : new StringBuilder(", element=").append(elementName).toString()) + (attributeName == null ? "" : new StringBuilder(", attribute=").append(attributeName).toString()) + (attributeValue == null ? "" : new StringBuilder(", value='").append(attributeValue).append("'").toString()), false);
/* 142 */     this.elementName = elementName;
/* 143 */     this.attributeName = attributeName;
/* 144 */     this.attributeValue = attributeValue;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void finalize()
/*     */     throws Throwable
/*     */   {
/* 154 */     this.elementName = null;
/* 155 */     this.attributeName = null;
/* 156 */     this.attributeValue = null;
/* 157 */     super.finalize();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getElementName()
/*     */   {
/* 167 */     return this.elementName;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getAttributeName()
/*     */   {
/* 177 */     return this.attributeName;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getAttributeValue()
/*     */   {
/* 187 */     return this.attributeValue;
/*     */   }
/*     */ }


/* Location:              /Users/AndrewKanarek/Desktop/MICHIGAN/KiefferWork/scripts-zibo/JavaApps/EllinghamDiagram/core.jar!/processing/xml/XMLValidationException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */