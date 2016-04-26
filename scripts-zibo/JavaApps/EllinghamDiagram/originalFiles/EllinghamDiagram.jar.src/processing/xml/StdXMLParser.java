/*     */ package processing.xml;
/*     */ 
/*     */ import java.io.Reader;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Properties;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StdXMLParser
/*     */ {
/*     */   private StdXMLBuilder builder;
/*     */   private StdXMLReader reader;
/*     */   private XMLEntityResolver entityResolver;
/*     */   private XMLValidator validator;
/*     */   
/*     */   public StdXMLParser()
/*     */   {
/*  76 */     this.builder = null;
/*  77 */     this.validator = null;
/*  78 */     this.reader = null;
/*  79 */     this.entityResolver = new XMLEntityResolver();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void finalize()
/*     */     throws Throwable
/*     */   {
/*  89 */     this.builder = null;
/*  90 */     this.reader = null;
/*  91 */     this.entityResolver = null;
/*  92 */     this.validator = null;
/*  93 */     super.finalize();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setBuilder(StdXMLBuilder builder)
/*     */   {
/* 104 */     this.builder = builder;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public StdXMLBuilder getBuilder()
/*     */   {
/* 115 */     return this.builder;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setValidator(XMLValidator validator)
/*     */   {
/* 126 */     this.validator = validator;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public XMLValidator getValidator()
/*     */   {
/* 137 */     return this.validator;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setResolver(XMLEntityResolver resolver)
/*     */   {
/* 148 */     this.entityResolver = resolver;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public XMLEntityResolver getResolver()
/*     */   {
/* 159 */     return this.entityResolver;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setReader(StdXMLReader reader)
/*     */   {
/* 170 */     this.reader = reader;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public StdXMLReader getReader()
/*     */   {
/* 181 */     return this.reader;
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
/*     */   public Object parse()
/*     */     throws XMLException
/*     */   {
/*     */     try
/*     */     {
/* 197 */       this.builder.startBuilding(this.reader.getSystemID(), 
/* 198 */         this.reader.getLineNr());
/* 199 */       scanData();
/* 200 */       return this.builder.getResult();
/*     */     } catch (XMLException e) {
/* 202 */       throw e;
/*     */     } catch (Exception e) {
/* 204 */       throw new XMLException(e);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void scanData()
/*     */     throws Exception
/*     */   {
/* 216 */     while ((!this.reader.atEOF()) && (this.builder.getResult() == null)) {
/* 217 */       String str = XMLUtil.read(this.reader, '&');
/* 218 */       char ch = str.charAt(0);
/* 219 */       if (ch == '&') {
/* 220 */         XMLUtil.processEntity(str, this.reader, this.entityResolver);
/*     */       }
/*     */       else
/*     */       {
/* 224 */         switch (ch) {
/*     */         case '<': 
/* 226 */           scanSomeTag(false, 
/* 227 */             null, 
/* 228 */             new Properties());
/* 229 */           break;
/*     */         
/*     */         case '\t': 
/*     */         case '\n': 
/*     */         case '\r': 
/*     */         case ' ': 
/*     */           break;
/*     */         
/*     */ 
/*     */         default: 
/* 239 */           XMLUtil.errorInvalidInput(this.reader.getSystemID(), 
/* 240 */             this.reader.getLineNr(), 
/* 241 */             "`" + ch + "' (0x" + 
/* 242 */             Integer.toHexString(ch) + 
/* 243 */             ')');
/*     */         }
/*     */         
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
/*     */   protected void scanSomeTag(boolean allowCDATA, String defaultNamespace, Properties namespaces)
/*     */     throws Exception
/*     */   {
/* 264 */     String str = XMLUtil.read(this.reader, '&');
/* 265 */     char ch = str.charAt(0);
/*     */     
/* 267 */     if (ch == '&') {
/* 268 */       XMLUtil.errorUnexpectedEntity(this.reader.getSystemID(), 
/* 269 */         this.reader.getLineNr(), 
/* 270 */         str);
/*     */     }
/*     */     
/* 273 */     switch (ch) {
/*     */     case '?': 
/* 275 */       processPI();
/* 276 */       break;
/*     */     
/*     */     case '!': 
/* 279 */       processSpecialTag(allowCDATA);
/* 280 */       break;
/*     */     
/*     */     default: 
/* 283 */       this.reader.unread(ch);
/* 284 */       processElement(defaultNamespace, namespaces);
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
/*     */   protected void processPI()
/*     */     throws Exception
/*     */   {
/* 298 */     XMLUtil.skipWhitespace(this.reader, null);
/* 299 */     String target = XMLUtil.scanIdentifier(this.reader);
/* 300 */     XMLUtil.skipWhitespace(this.reader, null);
/* 301 */     Reader r = new PIReader(this.reader);
/*     */     
/* 303 */     if (!target.equalsIgnoreCase("xml")) {
/* 304 */       this.builder.newProcessingInstruction(target, r);
/*     */     }
/*     */     
/* 307 */     r.close();
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
/*     */   protected void processSpecialTag(boolean allowCDATA)
/*     */     throws Exception
/*     */   {
/* 322 */     String str = XMLUtil.read(this.reader, '&');
/* 323 */     char ch = str.charAt(0);
/*     */     
/* 325 */     if (ch == '&') {
/* 326 */       XMLUtil.errorUnexpectedEntity(this.reader.getSystemID(), 
/* 327 */         this.reader.getLineNr(), 
/* 328 */         str);
/*     */     }
/*     */     
/* 331 */     switch (ch) {
/*     */     case '[': 
/* 333 */       if (allowCDATA) {
/* 334 */         processCDATA();
/*     */       } else {
/* 336 */         XMLUtil.errorUnexpectedCDATA(this.reader.getSystemID(), 
/* 337 */           this.reader.getLineNr());
/*     */       }
/*     */       
/* 340 */       return;
/*     */     
/*     */     case 'D': 
/* 343 */       processDocType();
/* 344 */       return;
/*     */     
/*     */     case '-': 
/* 347 */       XMLUtil.skipComment(this.reader);
/* 348 */       return;
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void processCDATA()
/*     */     throws Exception
/*     */   {
/* 360 */     if (!XMLUtil.checkLiteral(this.reader, "CDATA[")) {
/* 361 */       XMLUtil.errorExpectedInput(this.reader.getSystemID(), 
/* 362 */         this.reader.getLineNr(), 
/* 363 */         "<![[CDATA[");
/*     */     }
/*     */     
/* 366 */     this.validator.PCDataAdded(this.reader.getSystemID(), 
/* 367 */       this.reader.getLineNr());
/* 368 */     Reader r = new CDATAReader(this.reader);
/* 369 */     this.builder.addPCData(r, this.reader.getSystemID(), 
/* 370 */       this.reader.getLineNr());
/* 371 */     r.close();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void processDocType()
/*     */     throws Exception
/*     */   {
/* 382 */     if (!XMLUtil.checkLiteral(this.reader, "OCTYPE")) {
/* 383 */       XMLUtil.errorExpectedInput(this.reader.getSystemID(), 
/* 384 */         this.reader.getLineNr(), 
/* 385 */         "<!DOCTYPE");
/* 386 */       return;
/*     */     }
/*     */     
/* 389 */     XMLUtil.skipWhitespace(this.reader, null);
/* 390 */     String systemID = null;
/* 391 */     StringBuffer publicID = new StringBuffer();
/* 392 */     XMLUtil.scanIdentifier(this.reader);
/*     */     
/* 394 */     XMLUtil.skipWhitespace(this.reader, null);
/* 395 */     char ch = this.reader.read();
/*     */     
/* 397 */     if (ch == 'P') {
/* 398 */       systemID = XMLUtil.scanPublicID(publicID, this.reader);
/* 399 */       XMLUtil.skipWhitespace(this.reader, null);
/* 400 */       ch = this.reader.read();
/* 401 */     } else if (ch == 'S') {
/* 402 */       systemID = XMLUtil.scanSystemID(this.reader);
/* 403 */       XMLUtil.skipWhitespace(this.reader, null);
/* 404 */       ch = this.reader.read();
/*     */     }
/*     */     
/* 407 */     if (ch == '[') {
/* 408 */       this.validator.parseDTD(publicID.toString(), 
/* 409 */         this.reader, 
/* 410 */         this.entityResolver, 
/* 411 */         false);
/* 412 */       XMLUtil.skipWhitespace(this.reader, null);
/* 413 */       ch = this.reader.read();
/*     */     }
/*     */     
/* 416 */     if (ch != '>') {
/* 417 */       XMLUtil.errorExpectedInput(this.reader.getSystemID(), 
/* 418 */         this.reader.getLineNr(), 
/* 419 */         "`>'");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void processElement(String defaultNamespace, Properties namespaces)
/*     */     throws Exception
/*     */   {
/* 453 */     String fullName = XMLUtil.scanIdentifier(this.reader);
/* 454 */     String name = fullName;
/* 455 */     XMLUtil.skipWhitespace(this.reader, null);
/* 456 */     String prefix = null;
/* 457 */     int colonIndex = name.indexOf(':');
/*     */     
/* 459 */     if (colonIndex > 0) {
/* 460 */       prefix = name.substring(0, colonIndex);
/* 461 */       name = name.substring(colonIndex + 1);
/*     */     }
/*     */     
/* 464 */     Vector<String> attrNames = new Vector();
/* 465 */     Vector<String> attrValues = new Vector();
/* 466 */     Vector<String> attrTypes = new Vector();
/*     */     
/* 468 */     this.validator.elementStarted(fullName, 
/* 469 */       this.reader.getSystemID(), 
/* 470 */       this.reader.getLineNr());
/*     */     
/*     */     for (;;)
/*     */     {
/* 474 */       char ch = this.reader.read();
/*     */       
/* 476 */       if ((ch == '/') || (ch == '>')) {
/*     */         break;
/*     */       }
/*     */       
/* 480 */       this.reader.unread(ch);
/* 481 */       processAttribute(attrNames, attrValues, attrTypes);
/* 482 */       XMLUtil.skipWhitespace(this.reader, null);
/*     */     }
/*     */     char ch;
/* 485 */     Properties extraAttributes = new Properties();
/* 486 */     this.validator.elementAttributesProcessed(fullName, 
/* 487 */       extraAttributes, 
/* 488 */       this.reader.getSystemID(), 
/* 489 */       this.reader.getLineNr());
/* 490 */     Enumeration<?> en = extraAttributes.keys();
/*     */     
/* 492 */     while (en.hasMoreElements()) {
/* 493 */       String key = (String)en.nextElement();
/* 494 */       String value = extraAttributes.getProperty(key);
/* 495 */       attrNames.addElement(key);
/* 496 */       attrValues.addElement(value);
/* 497 */       attrTypes.addElement("CDATA");
/*     */     }
/*     */     
/* 500 */     for (int i = 0; i < attrNames.size(); i++) {
/* 501 */       String key = (String)attrNames.elementAt(i);
/* 502 */       String value = (String)attrValues.elementAt(i);
/*     */       
/*     */ 
/* 505 */       if (key.equals("xmlns")) {
/* 506 */         defaultNamespace = value;
/* 507 */       } else if (key.startsWith("xmlns:")) {
/* 508 */         namespaces.put(key.substring(6), value);
/*     */       }
/*     */     }
/*     */     
/* 512 */     if (prefix == null) {
/* 513 */       this.builder.startElement(name, prefix, defaultNamespace, 
/* 514 */         this.reader.getSystemID(), 
/* 515 */         this.reader.getLineNr());
/*     */     } else {
/* 517 */       this.builder.startElement(name, prefix, 
/* 518 */         namespaces.getProperty(prefix), 
/* 519 */         this.reader.getSystemID(), 
/* 520 */         this.reader.getLineNr());
/*     */     }
/*     */     
/* 523 */     for (int i = 0; i < attrNames.size(); i++) {
/* 524 */       String key = (String)attrNames.elementAt(i);
/*     */       
/* 526 */       if (!key.startsWith("xmlns"))
/*     */       {
/*     */ 
/*     */ 
/* 530 */         String value = (String)attrValues.elementAt(i);
/* 531 */         String type = (String)attrTypes.elementAt(i);
/* 532 */         colonIndex = key.indexOf(':');
/*     */         
/* 534 */         if (colonIndex > 0) {
/* 535 */           String attPrefix = key.substring(0, colonIndex);
/* 536 */           key = key.substring(colonIndex + 1);
/* 537 */           this.builder.addAttribute(key, attPrefix, 
/* 538 */             namespaces.getProperty(attPrefix), 
/* 539 */             value, type);
/*     */         } else {
/* 541 */           this.builder.addAttribute(key, null, null, value, type);
/*     */         }
/*     */       }
/*     */     }
/* 545 */     if (prefix == null) {
/* 546 */       this.builder.elementAttributesProcessed(name, prefix, 
/* 547 */         defaultNamespace);
/*     */     } else {
/* 549 */       this.builder.elementAttributesProcessed(name, prefix, 
/* 550 */         namespaces
/* 551 */         .getProperty(prefix));
/*     */     }
/*     */     
/* 554 */     if (ch == '/') {
/* 555 */       if (this.reader.read() != '>') {
/* 556 */         XMLUtil.errorExpectedInput(this.reader.getSystemID(), 
/* 557 */           this.reader.getLineNr(), 
/* 558 */           "`>'");
/*     */       }
/*     */       
/* 561 */       this.validator.elementEnded(name, 
/* 562 */         this.reader.getSystemID(), 
/* 563 */         this.reader.getLineNr());
/*     */       
/* 565 */       if (prefix == null) {
/* 566 */         this.builder.endElement(name, prefix, defaultNamespace);
/*     */       } else {
/* 568 */         this.builder.endElement(name, prefix, 
/* 569 */           namespaces.getProperty(prefix));
/*     */       }
/*     */       
/* 572 */       return;
/*     */     }
/*     */     
/* 575 */     StringBuffer buffer = new StringBuffer(16);
/*     */     for (;;)
/*     */     {
/* 578 */       buffer.setLength(0);
/*     */       
/*     */       for (;;)
/*     */       {
/* 582 */         XMLUtil.skipWhitespace(this.reader, buffer);
/* 583 */         String str = XMLUtil.read(this.reader, '&');
/*     */         
/* 585 */         if ((str.charAt(0) != '&') || (str.charAt(1) == '#')) break;
/* 586 */         XMLUtil.processEntity(str, this.reader, 
/* 587 */           this.entityResolver);
/*     */       }
/*     */       
/*     */ 
/*     */       String str;
/*     */       
/* 593 */       if (str.charAt(0) == '<') {
/* 594 */         str = XMLUtil.read(this.reader, '\000');
/*     */         
/* 596 */         if (str.charAt(0) == '/') {
/* 597 */           XMLUtil.skipWhitespace(this.reader, null);
/* 598 */           str = XMLUtil.scanIdentifier(this.reader);
/*     */           
/* 600 */           if (!str.equals(fullName)) {
/* 601 */             XMLUtil.errorWrongClosingTag(this.reader.getSystemID(), 
/* 602 */               this.reader.getLineNr(), 
/* 603 */               name, str);
/*     */           }
/*     */           
/* 606 */           XMLUtil.skipWhitespace(this.reader, null);
/*     */           
/* 608 */           if (this.reader.read() != '>') {
/* 609 */             XMLUtil.errorClosingTagNotEmpty(this.reader.getSystemID(), 
/* 610 */               this.reader.getLineNr());
/*     */           }
/*     */           
/* 613 */           this.validator.elementEnded(fullName, 
/* 614 */             this.reader.getSystemID(), 
/* 615 */             this.reader.getLineNr());
/* 616 */           if (prefix == null) {
/* 617 */             this.builder.endElement(name, prefix, defaultNamespace); break;
/*     */           }
/* 619 */           this.builder.endElement(name, prefix, 
/* 620 */             namespaces.getProperty(prefix));
/*     */           
/* 622 */           break;
/*     */         }
/* 624 */         this.reader.unread(str.charAt(0));
/* 625 */         scanSomeTag(true, 
/* 626 */           defaultNamespace, 
/* 627 */           (Properties)namespaces.clone());
/*     */       }
/*     */       else {
/* 630 */         if (str.charAt(0) == '&') {
/* 631 */           ch = XMLUtil.processCharLiteral(str);
/* 632 */           buffer.append(ch);
/*     */         } else {
/* 634 */           this.reader.unread(str.charAt(0));
/*     */         }
/* 636 */         this.validator.PCDataAdded(this.reader.getSystemID(), 
/* 637 */           this.reader.getLineNr());
/* 638 */         Reader r = new ContentReader(this.reader, 
/* 639 */           this.entityResolver, 
/* 640 */           buffer.toString());
/* 641 */         this.builder.addPCData(r, this.reader.getSystemID(), 
/* 642 */           this.reader.getLineNr());
/* 643 */         r.close();
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
/*     */   protected void processAttribute(Vector<String> attrNames, Vector<String> attrValues, Vector<String> attrTypes)
/*     */     throws Exception
/*     */   {
/* 664 */     String key = XMLUtil.scanIdentifier(this.reader);
/* 665 */     XMLUtil.skipWhitespace(this.reader, null);
/*     */     
/* 667 */     if (!XMLUtil.read(this.reader, '&').equals("=")) {
/* 668 */       XMLUtil.errorExpectedInput(this.reader.getSystemID(), 
/* 669 */         this.reader.getLineNr(), 
/* 670 */         "`='");
/*     */     }
/*     */     
/* 673 */     XMLUtil.skipWhitespace(this.reader, null);
/* 674 */     String value = XMLUtil.scanString(this.reader, '&', 
/* 675 */       this.entityResolver);
/* 676 */     attrNames.addElement(key);
/* 677 */     attrValues.addElement(value);
/* 678 */     attrTypes.addElement("CDATA");
/* 679 */     this.validator.attributeAdded(key, value, 
/* 680 */       this.reader.getSystemID(), 
/* 681 */       this.reader.getLineNr());
/*     */   }
/*     */ }


/* Location:              /Users/AndrewKanarek/Desktop/MICHIGAN/KiefferWork/scripts-zibo/JavaApps/EllinghamDiagram/core.jar!/processing/xml/StdXMLParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */