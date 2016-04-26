/*     */ package processing.xml;
/*     */ 
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Properties;
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
/*     */ public class XMLValidator
/*     */ {
/*     */   protected XMLEntityResolver parameterEntityResolver;
/*     */   protected Hashtable<String, Properties> attributeDefaultValues;
/*     */   protected Stack<Properties> currentElements;
/*     */   
/*     */   public XMLValidator()
/*     */   {
/*  76 */     this.attributeDefaultValues = new Hashtable();
/*  77 */     this.currentElements = new Stack();
/*  78 */     this.parameterEntityResolver = new XMLEntityResolver();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void finalize()
/*     */     throws Throwable
/*     */   {
/*  88 */     this.parameterEntityResolver = null;
/*  89 */     this.attributeDefaultValues.clear();
/*  90 */     this.attributeDefaultValues = null;
/*  91 */     this.currentElements.clear();
/*  92 */     this.currentElements = null;
/*  93 */     super.finalize();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setParameterEntityResolver(XMLEntityResolver resolver)
/*     */   {
/* 104 */     this.parameterEntityResolver = resolver;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public XMLEntityResolver getParameterEntityResolver()
/*     */   {
/* 115 */     return this.parameterEntityResolver;
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
/*     */   public void parseDTD(String publicID, StdXMLReader reader, XMLEntityResolver entityResolver, boolean external)
/*     */     throws Exception
/*     */   {
/* 137 */     XMLUtil.skipWhitespace(reader, null);
/* 138 */     int origLevel = reader.getStreamLevel();
/*     */     for (;;)
/*     */     {
/* 141 */       String str = XMLUtil.read(reader, '%');
/* 142 */       char ch = str.charAt(0);
/*     */       
/* 144 */       if (ch == '%') {
/* 145 */         XMLUtil.processEntity(str, reader, 
/* 146 */           this.parameterEntityResolver);
/*     */       } else {
/* 148 */         if (ch == '<') {
/* 149 */           processElement(reader, entityResolver);
/* 150 */         } else { if (ch == ']') {
/* 151 */             return;
/*     */           }
/* 153 */           XMLUtil.errorInvalidInput(reader.getSystemID(), 
/* 154 */             reader.getLineNr(), 
/* 155 */             str);
/*     */         }
/*     */         do
/*     */         {
/* 159 */           ch = reader.read();
/*     */           
/* 161 */           if ((external) && (reader.getStreamLevel() < origLevel)) {
/* 162 */             reader.unread(ch);
/* 163 */             return;
/*     */           }
/* 165 */         } while ((ch == ' ') || (ch == '\t') || (ch == '\n') || 
/* 166 */           (ch == '\r'));
/*     */         
/* 168 */         reader.unread(ch);
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
/*     */   protected void processElement(StdXMLReader reader, XMLEntityResolver entityResolver)
/*     */     throws Exception
/*     */   {
/* 186 */     String str = XMLUtil.read(reader, '%');
/* 187 */     char ch = str.charAt(0);
/*     */     
/* 189 */     if (ch != '!') {
/* 190 */       XMLUtil.skipTag(reader);
/* 191 */       return;
/*     */     }
/*     */     
/* 194 */     str = XMLUtil.read(reader, '%');
/* 195 */     ch = str.charAt(0);
/*     */     
/* 197 */     switch (ch) {
/*     */     case '-': 
/* 199 */       XMLUtil.skipComment(reader);
/* 200 */       break;
/*     */     
/*     */     case '[': 
/* 203 */       processConditionalSection(reader, entityResolver);
/* 204 */       break;
/*     */     
/*     */     case 'E': 
/* 207 */       processEntity(reader, entityResolver);
/* 208 */       break;
/*     */     
/*     */     case 'A': 
/* 211 */       processAttList(reader, entityResolver);
/* 212 */       break;
/*     */     
/*     */     default: 
/* 215 */       XMLUtil.skipTag(reader);
/*     */     }
/*     */     
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
/*     */   protected void processConditionalSection(StdXMLReader reader, XMLEntityResolver entityResolver)
/*     */     throws Exception
/*     */   {
/* 233 */     XMLUtil.skipWhitespace(reader, null);
/*     */     
/* 235 */     String str = XMLUtil.read(reader, '%');
/* 236 */     char ch = str.charAt(0);
/*     */     
/* 238 */     if (ch != 'I') {
/* 239 */       XMLUtil.skipTag(reader);
/* 240 */       return;
/*     */     }
/*     */     
/* 243 */     str = XMLUtil.read(reader, '%');
/* 244 */     ch = str.charAt(0);
/*     */     
/* 246 */     switch (ch) {
/*     */     case 'G': 
/* 248 */       processIgnoreSection(reader, entityResolver); return;
/*     */     
/*     */     case 'N': 
/*     */       break;
/*     */     
/*     */ 
/*     */     default: 
/* 255 */       XMLUtil.skipTag(reader);
/* 256 */       return;
/*     */     }
/*     */     
/* 259 */     if (!XMLUtil.checkLiteral(reader, "CLUDE")) {
/* 260 */       XMLUtil.skipTag(reader);
/* 261 */       return;
/*     */     }
/*     */     
/* 264 */     XMLUtil.skipWhitespace(reader, null);
/*     */     
/* 266 */     str = XMLUtil.read(reader, '%');
/* 267 */     ch = str.charAt(0);
/*     */     
/* 269 */     if (ch != '[') {
/* 270 */       XMLUtil.skipTag(reader);
/* 271 */       return;
/*     */     }
/*     */     
/* 274 */     Reader subreader = new CDATAReader(reader);
/* 275 */     StringBuffer buf = new StringBuffer(1024);
/*     */     for (;;)
/*     */     {
/* 278 */       int ch2 = subreader.read();
/*     */       
/* 280 */       if (ch2 < 0) {
/*     */         break;
/*     */       }
/*     */       
/* 284 */       buf.append((char)ch2);
/*     */     }
/*     */     
/* 287 */     subreader.close();
/* 288 */     reader.startNewStream(new StringReader(buf.toString()));
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
/*     */   protected void processIgnoreSection(StdXMLReader reader, XMLEntityResolver entityResolver)
/*     */     throws Exception
/*     */   {
/* 305 */     if (!XMLUtil.checkLiteral(reader, "NORE")) {
/* 306 */       XMLUtil.skipTag(reader);
/* 307 */       return;
/*     */     }
/*     */     
/* 310 */     XMLUtil.skipWhitespace(reader, null);
/*     */     
/* 312 */     String str = XMLUtil.read(reader, '%');
/* 313 */     char ch = str.charAt(0);
/*     */     
/* 315 */     if (ch != '[') {
/* 316 */       XMLUtil.skipTag(reader);
/* 317 */       return;
/*     */     }
/*     */     
/* 320 */     Reader subreader = new CDATAReader(reader);
/* 321 */     subreader.close();
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
/*     */   protected void processAttList(StdXMLReader reader, XMLEntityResolver entityResolver)
/*     */     throws Exception
/*     */   {
/* 338 */     if (!XMLUtil.checkLiteral(reader, "TTLIST")) {
/* 339 */       XMLUtil.skipTag(reader);
/* 340 */       return;
/*     */     }
/*     */     
/* 343 */     XMLUtil.skipWhitespace(reader, null);
/* 344 */     String str = XMLUtil.read(reader, '%');
/* 345 */     char ch = str.charAt(0);
/* 346 */     while (ch == '%') {
/* 347 */       XMLUtil.processEntity(str, reader, 
/* 348 */         this.parameterEntityResolver);
/* 349 */       str = XMLUtil.read(reader, '%');
/* 350 */       ch = str.charAt(0);
/*     */     }
/* 352 */     reader.unread(ch);
/* 353 */     String elementName = XMLUtil.scanIdentifier(reader);
/* 354 */     XMLUtil.skipWhitespace(reader, null);
/*     */     
/* 356 */     str = XMLUtil.read(reader, '%');
/* 357 */     ch = str.charAt(0);
/* 358 */     while (ch == '%') {
/* 359 */       XMLUtil.processEntity(str, reader, 
/* 360 */         this.parameterEntityResolver);
/* 361 */       str = XMLUtil.read(reader, '%');
/* 362 */       ch = str.charAt(0);
/*     */     }
/*     */     
/* 365 */     Properties props = new Properties();
/*     */     
/* 367 */     while (ch != '>') {
/* 368 */       reader.unread(ch);
/* 369 */       String attName = XMLUtil.scanIdentifier(reader);
/* 370 */       XMLUtil.skipWhitespace(reader, null);
/* 371 */       str = XMLUtil.read(reader, '%');
/* 372 */       ch = str.charAt(0);
/* 373 */       while (ch == '%') {
/* 374 */         XMLUtil.processEntity(str, reader, 
/* 375 */           this.parameterEntityResolver);
/* 376 */         str = XMLUtil.read(reader, '%');
/* 377 */         ch = str.charAt(0);
/*     */       }
/*     */       
/* 380 */       if (ch == '(') {
/* 381 */         for (; ch != ')'; 
/*     */             
/*     */ 
/* 384 */             ch == '%')
/*     */         {
/* 382 */           str = XMLUtil.read(reader, '%');
/* 383 */           ch = str.charAt(0);
/* 384 */           continue;
/* 385 */           XMLUtil.processEntity(str, reader, 
/* 386 */             this.parameterEntityResolver);
/* 387 */           str = XMLUtil.read(reader, '%');
/* 388 */           ch = str.charAt(0);
/*     */         }
/*     */       }
/*     */       else {
/* 392 */         reader.unread(ch);
/* 393 */         XMLUtil.scanIdentifier(reader);
/*     */       }
/*     */       
/* 396 */       XMLUtil.skipWhitespace(reader, null);
/* 397 */       str = XMLUtil.read(reader, '%');
/* 398 */       ch = str.charAt(0);
/* 399 */       while (ch == '%') {
/* 400 */         XMLUtil.processEntity(str, reader, 
/* 401 */           this.parameterEntityResolver);
/* 402 */         str = XMLUtil.read(reader, '%');
/* 403 */         ch = str.charAt(0);
/*     */       }
/*     */       
/* 406 */       if (ch == '#') {
/* 407 */         str = XMLUtil.scanIdentifier(reader);
/* 408 */         XMLUtil.skipWhitespace(reader, null);
/*     */         
/* 410 */         if (!str.equals("FIXED")) {
/* 411 */           XMLUtil.skipWhitespace(reader, null);
/*     */           
/* 413 */           str = XMLUtil.read(reader, '%');
/* 414 */           ch = str.charAt(0);
/* 415 */           while (ch == '%') {
/* 416 */             XMLUtil.processEntity(str, reader, 
/* 417 */               this.parameterEntityResolver);
/* 418 */             str = XMLUtil.read(reader, '%');
/* 419 */             ch = str.charAt(0);
/*     */           }
/*     */           
/* 422 */           continue;
/*     */         }
/*     */       } else {
/* 425 */         reader.unread(ch);
/*     */       }
/*     */       
/* 428 */       String value = XMLUtil.scanString(reader, '%', 
/* 429 */         this.parameterEntityResolver);
/* 430 */       props.put(attName, value);
/* 431 */       XMLUtil.skipWhitespace(reader, null);
/*     */       
/* 433 */       str = XMLUtil.read(reader, '%');
/* 434 */       ch = str.charAt(0);
/* 435 */       while (ch == '%') {
/* 436 */         XMLUtil.processEntity(str, reader, 
/* 437 */           this.parameterEntityResolver);
/* 438 */         str = XMLUtil.read(reader, '%');
/* 439 */         ch = str.charAt(0);
/*     */       }
/*     */     }
/*     */     
/* 443 */     if (!props.isEmpty()) {
/* 444 */       this.attributeDefaultValues.put(elementName, props);
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
/*     */   protected void processEntity(StdXMLReader reader, XMLEntityResolver entityResolver)
/*     */     throws Exception
/*     */   {
/* 462 */     if (!XMLUtil.checkLiteral(reader, "NTITY")) {
/* 463 */       XMLUtil.skipTag(reader);
/* 464 */       return;
/*     */     }
/*     */     
/* 467 */     XMLUtil.skipWhitespace(reader, null);
/* 468 */     char ch = XMLUtil.readChar(reader, '\000');
/*     */     
/* 470 */     if (ch == '%') {
/* 471 */       XMLUtil.skipWhitespace(reader, null);
/* 472 */       entityResolver = this.parameterEntityResolver;
/*     */     } else {
/* 474 */       reader.unread(ch);
/*     */     }
/*     */     
/* 477 */     String key = XMLUtil.scanIdentifier(reader);
/* 478 */     XMLUtil.skipWhitespace(reader, null);
/* 479 */     ch = XMLUtil.readChar(reader, '%');
/* 480 */     String systemID = null;
/* 481 */     String publicID = null;
/*     */     
/* 483 */     switch (ch) {
/*     */     case 'P': 
/* 485 */       if (!XMLUtil.checkLiteral(reader, "UBLIC")) {
/* 486 */         XMLUtil.skipTag(reader);
/* 487 */         return;
/*     */       }
/*     */       
/* 490 */       XMLUtil.skipWhitespace(reader, null);
/* 491 */       publicID = XMLUtil.scanString(reader, '%', 
/* 492 */         this.parameterEntityResolver);
/* 493 */       XMLUtil.skipWhitespace(reader, null);
/* 494 */       systemID = XMLUtil.scanString(reader, '%', 
/* 495 */         this.parameterEntityResolver);
/* 496 */       XMLUtil.skipWhitespace(reader, null);
/* 497 */       XMLUtil.readChar(reader, '%');
/* 498 */       break;
/*     */     
/*     */     case 'S': 
/* 501 */       if (!XMLUtil.checkLiteral(reader, "YSTEM")) {
/* 502 */         XMLUtil.skipTag(reader);
/* 503 */         return;
/*     */       }
/*     */       
/* 506 */       XMLUtil.skipWhitespace(reader, null);
/* 507 */       systemID = XMLUtil.scanString(reader, '%', 
/* 508 */         this.parameterEntityResolver);
/* 509 */       XMLUtil.skipWhitespace(reader, null);
/* 510 */       XMLUtil.readChar(reader, '%');
/* 511 */       break;
/*     */     
/*     */     case '"': 
/*     */     case '\'': 
/* 515 */       reader.unread(ch);
/* 516 */       String value = XMLUtil.scanString(reader, '%', 
/* 517 */         this.parameterEntityResolver);
/* 518 */       entityResolver.addInternalEntity(key, value);
/* 519 */       XMLUtil.skipWhitespace(reader, null);
/* 520 */       XMLUtil.readChar(reader, '%');
/* 521 */       break;
/*     */     
/*     */     default: 
/* 524 */       XMLUtil.skipTag(reader);
/*     */     }
/*     */     
/* 527 */     if (systemID != null) {
/* 528 */       entityResolver.addExternalEntity(key, publicID, systemID);
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
/*     */   public void elementStarted(String name, String systemId, int lineNr)
/*     */   {
/* 544 */     Properties attribs = 
/* 545 */       (Properties)this.attributeDefaultValues.get(name);
/*     */     
/* 547 */     if (attribs == null) {
/* 548 */       attribs = new Properties();
/*     */     } else {
/* 550 */       attribs = (Properties)attribs.clone();
/*     */     }
/*     */     
/* 553 */     this.currentElements.push(attribs);
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
/*     */   public void elementEnded(String name, String systemId, int lineNr) {}
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
/*     */   public void elementAttributesProcessed(String name, Properties extraAttributes, String systemId, int lineNr)
/*     */   {
/* 588 */     Properties props = (Properties)this.currentElements.pop();
/* 589 */     Enumeration<?> en = props.keys();
/*     */     
/* 591 */     while (en.hasMoreElements()) {
/* 592 */       String key = (String)en.nextElement();
/* 593 */       extraAttributes.put(key, props.get(key));
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
/*     */   public void attributeAdded(String key, String value, String systemId, int lineNr)
/*     */   {
/* 611 */     Properties props = (Properties)this.currentElements.peek();
/*     */     
/* 613 */     if (props.containsKey(key)) {
/* 614 */       props.remove(key);
/*     */     }
/*     */   }
/*     */   
/*     */   public void PCDataAdded(String systemId, int lineNr) {}
/*     */ }


/* Location:              /Users/AndrewKanarek/Desktop/MICHIGAN/KiefferWork/scripts-zibo/JavaApps/EllinghamDiagram/core.jar!/processing/xml/XMLValidator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */