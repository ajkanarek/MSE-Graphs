/*     */ package processing.xml;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.Writer;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Vector;
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
/*     */ public class XMLWriter
/*     */ {
/*     */   private PrintWriter writer;
/*     */   
/*     */   public XMLWriter(Writer writer)
/*     */   {
/*  61 */     if ((writer instanceof PrintWriter)) {
/*  62 */       this.writer = ((PrintWriter)writer);
/*     */     } else {
/*  64 */       this.writer = new PrintWriter(writer);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public XMLWriter(OutputStream stream)
/*     */   {
/*  76 */     this.writer = new PrintWriter(stream);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void finalize()
/*     */     throws Throwable
/*     */   {
/*  86 */     this.writer = null;
/*  87 */     super.finalize();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void write(XMLElement xml)
/*     */     throws IOException
/*     */   {
/*  99 */     write(xml, false, 0, true);
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
/*     */   public void write(XMLElement xml, boolean prettyPrint)
/*     */     throws IOException
/*     */   {
/* 114 */     write(xml, prettyPrint, 0, true);
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
/*     */   public void write(XMLElement xml, boolean prettyPrint, int indent)
/*     */     throws IOException
/*     */   {
/* 131 */     write(xml, prettyPrint, indent, true);
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
/*     */   public void write(XMLElement xml, boolean prettyPrint, int indent, boolean collapseEmptyElements)
/*     */     throws IOException
/*     */   {
/* 149 */     if (prettyPrint) {
/* 150 */       for (int i = 0; i < indent; i++) {
/* 151 */         this.writer.print(' ');
/*     */       }
/*     */     }
/*     */     
/* 155 */     if (xml.getLocalName() == null) {
/* 156 */       if (xml.getContent() != null) {
/* 157 */         if (prettyPrint) {
/* 158 */           writeEncoded(xml.getContent().trim());
/* 159 */           this.writer.println();
/*     */         } else {
/* 161 */           writeEncoded(xml.getContent());
/*     */         }
/*     */       }
/*     */     } else {
/* 165 */       this.writer.print('<');
/* 166 */       this.writer.print(xml.getName());
/* 167 */       Vector<String> nsprefixes = new Vector();
/*     */       
/* 169 */       if (xml.getNamespace() != null) {
/* 170 */         if (xml.getLocalName().equals(xml.getName())) {
/* 171 */           this.writer.print(" xmlns=\"" + xml.getNamespace() + '"');
/*     */         } else {
/* 173 */           String prefix = xml.getName();
/* 174 */           prefix = prefix.substring(0, prefix.indexOf(':'));
/* 175 */           nsprefixes.addElement(prefix);
/* 176 */           this.writer.print(" xmlns:" + prefix);
/* 177 */           this.writer.print("=\"" + xml.getNamespace() + "\"");
/*     */         }
/*     */       }
/*     */       
/* 181 */       Enumeration<?> en = xml.enumerateAttributeNames();
/*     */       
/* 183 */       while (en.hasMoreElements()) {
/* 184 */         String key = (String)en.nextElement();
/* 185 */         int index = key.indexOf(':');
/*     */         
/* 187 */         if (index >= 0) {
/* 188 */           String namespace = xml.getAttributeNamespace(key);
/*     */           
/* 190 */           if (namespace != null) {
/* 191 */             String prefix = key.substring(0, index);
/*     */             
/* 193 */             if (!nsprefixes.contains(prefix)) {
/* 194 */               this.writer.print(" xmlns:" + prefix);
/* 195 */               this.writer.print("=\"" + namespace + '"');
/* 196 */               nsprefixes.addElement(prefix);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 202 */       en = xml.enumerateAttributeNames();
/*     */       
/* 204 */       while (en.hasMoreElements()) {
/* 205 */         String key = (String)en.nextElement();
/* 206 */         String value = xml.getAttribute(key, null);
/* 207 */         this.writer.print(" " + key + "=\"");
/* 208 */         writeEncoded(value);
/* 209 */         this.writer.print('"');
/*     */       }
/*     */       
/* 212 */       if ((xml.getContent() != null) && 
/* 213 */         (xml.getContent().length() > 0)) {
/* 214 */         this.writer.print('>');
/* 215 */         writeEncoded(xml.getContent());
/* 216 */         this.writer.print("</" + xml.getName() + '>');
/*     */         
/* 218 */         if (prettyPrint) {
/* 219 */           this.writer.println();
/*     */         }
/* 221 */       } else if ((xml.hasChildren()) || (!collapseEmptyElements)) {
/* 222 */         this.writer.print('>');
/*     */         
/* 224 */         if (prettyPrint) {
/* 225 */           this.writer.println();
/*     */         }
/*     */         
/* 228 */         en = xml.enumerateChildren();
/*     */         
/* 230 */         while (en.hasMoreElements()) {
/* 231 */           XMLElement child = (XMLElement)en.nextElement();
/* 232 */           write(child, prettyPrint, indent + 4, 
/* 233 */             collapseEmptyElements);
/*     */         }
/*     */         
/* 236 */         if (prettyPrint) {
/* 237 */           for (int i = 0; i < indent; i++) {
/* 238 */             this.writer.print(' ');
/*     */           }
/*     */         }
/*     */         
/* 242 */         this.writer.print("</" + xml.getName() + ">");
/*     */         
/* 244 */         if (prettyPrint) {
/* 245 */           this.writer.println();
/*     */         }
/*     */       } else {
/* 248 */         this.writer.print("/>");
/*     */         
/* 250 */         if (prettyPrint) {
/* 251 */           this.writer.println();
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 256 */     this.writer.flush();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void writeEncoded(String str)
/*     */   {
/* 267 */     for (int i = 0; i < str.length(); i++) {
/* 268 */       char c = str.charAt(i);
/*     */       
/* 270 */       switch (c) {
/*     */       case '\n': 
/* 272 */         this.writer.print(c);
/* 273 */         break;
/*     */       
/*     */       case '<': 
/* 276 */         this.writer.print("&lt;");
/* 277 */         break;
/*     */       
/*     */       case '>': 
/* 280 */         this.writer.print("&gt;");
/* 281 */         break;
/*     */       
/*     */       case '&': 
/* 284 */         this.writer.print("&amp;");
/* 285 */         break;
/*     */       
/*     */       case '\'': 
/* 288 */         this.writer.print("&apos;");
/* 289 */         break;
/*     */       
/*     */       case '"': 
/* 292 */         this.writer.print("&quot;");
/* 293 */         break;
/*     */       
/*     */       default: 
/* 296 */         if ((c < ' ') || (c > '~')) {
/* 297 */           this.writer.print("&#x");
/* 298 */           this.writer.print(Integer.toString(c, 16));
/* 299 */           this.writer.print(';');
/*     */         } else {
/* 301 */           this.writer.print(c);
/*     */         }
/*     */         break;
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/AndrewKanarek/Desktop/MICHIGAN/KiefferWork/scripts-zibo/JavaApps/EllinghamDiagram/core.jar!/processing/xml/XMLWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */