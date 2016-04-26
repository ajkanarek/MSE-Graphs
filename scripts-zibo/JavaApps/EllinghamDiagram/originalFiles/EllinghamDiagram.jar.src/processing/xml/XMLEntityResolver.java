/*     */ package processing.xml;
/*     */ 
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ import java.util.Hashtable;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLEntityResolver
/*     */ {
/*     */   private Hashtable<String, Object> entities;
/*     */   
/*     */   public XMLEntityResolver()
/*     */   {
/*  57 */     this.entities = new Hashtable();
/*  58 */     this.entities.put("amp", "&#38;");
/*  59 */     this.entities.put("quot", "&#34;");
/*  60 */     this.entities.put("apos", "&#39;");
/*  61 */     this.entities.put("lt", "&#60;");
/*  62 */     this.entities.put("gt", "&#62;");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void finalize()
/*     */     throws Throwable
/*     */   {
/*  72 */     this.entities.clear();
/*  73 */     this.entities = null;
/*  74 */     super.finalize();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addInternalEntity(String name, String value)
/*     */   {
/*  87 */     if (!this.entities.containsKey(name)) {
/*  88 */       this.entities.put(name, value);
/*     */     }
/*     */   }
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
/*     */   public void addExternalEntity(String name, String publicID, String systemID)
/*     */   {
/* 104 */     if (!this.entities.containsKey(name)) {
/* 105 */       this.entities.put(name, new String[] { publicID, systemID });
/*     */     }
/*     */   }
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
/*     */   public Reader getEntity(StdXMLReader xmlReader, String name)
/*     */     throws XMLParseException
/*     */   {
/* 122 */     Object obj = this.entities.get(name);
/*     */     
/* 124 */     if (obj == null)
/* 125 */       return null;
/* 126 */     if ((obj instanceof String)) {
/* 127 */       return new StringReader((String)obj);
/*     */     }
/* 129 */     String[] id = (String[])obj;
/* 130 */     return openExternalEntity(xmlReader, id[0], id[1]);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isExternalEntity(String name)
/*     */   {
/* 142 */     Object obj = this.entities.get(name);
/* 143 */     return !(obj instanceof String);
/*     */   }
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
/*     */ 
/*     */   protected Reader openExternalEntity(StdXMLReader xmlReader, String publicID, String systemID)
/*     */     throws XMLParseException
/*     */   {
/* 161 */     String parentSystemID = xmlReader.getSystemID();
/*     */     try
/*     */     {
/* 164 */       return xmlReader.openStream(publicID, systemID);
/*     */     } catch (Exception e) {
/* 166 */       throw new XMLParseException(parentSystemID, 
/* 167 */         xmlReader.getLineNr(), 
/* 168 */         "Could not open external entity at system ID: " + 
/* 169 */         systemID);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/AndrewKanarek/Desktop/MICHIGAN/KiefferWork/scripts-zibo/JavaApps/EllinghamDiagram/core.jar!/processing/xml/XMLEntityResolver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */