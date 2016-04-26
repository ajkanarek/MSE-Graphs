/*     */ package processing.xml;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.util.Stack;
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
/*     */ public class StdXMLBuilder
/*     */ {
/*     */   private Stack<XMLElement> stack;
/*     */   private XMLElement root;
/*     */   private XMLElement parent;
/*     */   
/*     */   public StdXMLBuilder()
/*     */   {
/*  73 */     this.stack = null;
/*  74 */     this.root = null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public StdXMLBuilder(XMLElement parent)
/*     */   {
/*  81 */     this.parent = parent;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void finalize()
/*     */     throws Throwable
/*     */   {
/* 105 */     this.root = null;
/* 106 */     this.stack.clear();
/* 107 */     this.stack = null;
/* 108 */     super.finalize();
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
/*     */   public void startBuilding(String systemID, int lineNr)
/*     */   {
/* 121 */     this.stack = new Stack();
/* 122 */     this.root = null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void newProcessingInstruction(String target, Reader reader) {}
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
/*     */   public void startElement(String name, String nsPrefix, String nsURI, String systemID, int lineNr)
/*     */   {
/* 160 */     String fullName = name;
/*     */     
/* 162 */     if (nsPrefix != null) {
/* 163 */       fullName = nsPrefix + ':' + name;
/*     */     }
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
/* 179 */     if (this.stack.empty())
/*     */     {
/* 181 */       this.parent.set(fullName, nsURI, systemID, lineNr);
/* 182 */       this.stack.push(this.parent);
/* 183 */       this.root = this.parent;
/*     */     } else {
/* 185 */       XMLElement top = (XMLElement)this.stack.peek();
/*     */       
/* 187 */       XMLElement elt = new XMLElement(fullName, nsURI, systemID, lineNr);
/* 188 */       top.addChild(elt);
/* 189 */       this.stack.push(elt);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void elementAttributesProcessed(String name, String nsPrefix, String nsURI) {}
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
/*     */   public void endElement(String name, String nsPrefix, String nsURI)
/*     */   {
/* 232 */     XMLElement elt = (XMLElement)this.stack.pop();
/*     */     
/* 234 */     if (elt.getChildCount() == 1) {
/* 235 */       XMLElement child = elt.getChildAtIndex(0);
/*     */       
/* 237 */       if (child.getLocalName() == null) {
/* 238 */         elt.setContent(child.getContent());
/* 239 */         elt.removeChildAtIndex(0);
/*     */       }
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
/*     */   public void addAttribute(String key, String nsPrefix, String nsURI, String value, String type)
/*     */     throws Exception
/*     */   {
/* 269 */     String fullName = key;
/*     */     
/* 271 */     if (nsPrefix != null) {
/* 272 */       fullName = nsPrefix + ':' + key;
/*     */     }
/*     */     
/* 275 */     XMLElement top = (XMLElement)this.stack.peek();
/*     */     
/* 277 */     if (top.hasAttribute(fullName)) {
/* 278 */       throw new XMLParseException(top.getSystemID(), 
/* 279 */         top.getLineNr(), 
/* 280 */         "Duplicate attribute: " + key);
/*     */     }
/*     */     
/* 283 */     if (nsPrefix != null) {
/* 284 */       top.setAttribute(fullName, nsURI, value);
/*     */     } else {
/* 286 */       top.setAttribute(fullName, value);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addPCData(Reader reader, String systemID, int lineNr)
/*     */   {
/* 307 */     int bufSize = 2048;
/* 308 */     int sizeRead = 0;
/* 309 */     StringBuffer str = new StringBuffer(bufSize);
/* 310 */     char[] buf = new char[bufSize];
/*     */     for (;;)
/*     */     {
/* 313 */       if (sizeRead >= bufSize) {
/* 314 */         bufSize *= 2;
/* 315 */         str.ensureCapacity(bufSize);
/*     */       }
/*     */       
/*     */ 
/*     */       try
/*     */       {
/* 321 */         size = reader.read(buf);
/*     */       } catch (IOException e) { int size;
/* 323 */         break;
/*     */       }
/*     */       int size;
/* 326 */       if (size < 0) {
/*     */         break;
/*     */       }
/*     */       
/* 330 */       str.append(buf, 0, size);
/* 331 */       sizeRead += size;
/*     */     }
/*     */     
/*     */ 
/* 335 */     XMLElement elt = new XMLElement(null, null, systemID, lineNr);
/* 336 */     elt.setContent(str.toString());
/*     */     
/* 338 */     if (!this.stack.empty()) {
/* 339 */       XMLElement top = (XMLElement)this.stack.peek();
/* 340 */       top.addChild(elt);
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
/*     */   public Object getResult()
/*     */   {
/* 353 */     return this.root;
/*     */   }
/*     */ }


/* Location:              /Users/AndrewKanarek/Desktop/MICHIGAN/KiefferWork/scripts-zibo/JavaApps/EllinghamDiagram/core.jar!/processing/xml/StdXMLBuilder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */