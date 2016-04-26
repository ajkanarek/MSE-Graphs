/*     */ package processing.xml;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class XMLUtil
/*     */ {
/*     */   static void skipComment(StdXMLReader reader)
/*     */     throws IOException, XMLParseException
/*     */   {
/*  58 */     if (reader.read() != '-') {
/*  59 */       errorExpectedInput(reader.getSystemID(), 
/*  60 */         reader.getLineNr(), 
/*  61 */         "<!--");
/*     */     }
/*     */     
/*  64 */     int dashesRead = 0;
/*     */     for (;;)
/*     */     {
/*  67 */       char ch = reader.read();
/*     */       
/*  69 */       switch (ch) {
/*     */       case '-': 
/*  71 */         dashesRead++;
/*  72 */         break;
/*     */       
/*     */       case '>': 
/*  75 */         if (dashesRead == 2) {
/*  76 */           return;
/*     */         }
/*  78 */         dashesRead = 0;
/*  79 */         break;
/*     */       
/*     */       default: 
/*  82 */         dashesRead = 0;
/*     */       }
/*     */       
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
/*     */   static void skipTag(StdXMLReader reader)
/*     */     throws IOException, XMLParseException
/*     */   {
/* 100 */     int level = 1;
/*     */     
/* 102 */     while (level > 0) {
/* 103 */       char ch = reader.read();
/*     */       
/* 105 */       switch (ch) {
/*     */       case '<': 
/* 107 */         level++;
/* 108 */         break;
/*     */       
/*     */       case '>': 
/* 111 */         level--;
/*     */       }
/*     */       
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
/*     */   static String scanPublicID(StringBuffer publicID, StdXMLReader reader)
/*     */     throws IOException, XMLParseException
/*     */   {
/* 134 */     if (!checkLiteral(reader, "UBLIC")) {
/* 135 */       return null;
/*     */     }
/*     */     
/* 138 */     skipWhitespace(reader, null);
/* 139 */     publicID.append(scanString(reader, '\000', null));
/* 140 */     skipWhitespace(reader, null);
/* 141 */     return scanString(reader, '\000', null);
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
/*     */   static String scanSystemID(StdXMLReader reader)
/*     */     throws IOException, XMLParseException
/*     */   {
/* 159 */     if (!checkLiteral(reader, "YSTEM")) {
/* 160 */       return null;
/*     */     }
/*     */     
/* 163 */     skipWhitespace(reader, null);
/* 164 */     return scanString(reader, '\000', null);
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
/*     */   static String scanIdentifier(StdXMLReader reader)
/*     */     throws IOException, XMLParseException
/*     */   {
/* 180 */     StringBuffer result = new StringBuffer();
/*     */     char ch;
/*     */     for (;;) {
/* 183 */       ch = reader.read();
/*     */       
/* 185 */       if ((ch != '_') && (ch != ':') && (ch != '-') && (ch != '.') && 
/* 186 */         ((ch < 'a') || (ch > 'z')) && 
/* 187 */         ((ch < 'A') || (ch > 'Z')) && 
/* 188 */         ((ch < '0') || (ch > '9')) && (ch <= '~')) break;
/* 189 */       result.append(ch);
/*     */     }
/* 191 */     reader.unread(ch);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 196 */     return result.toString();
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
/*     */   static String scanString(StdXMLReader reader, char entityChar, XMLEntityResolver entityResolver)
/*     */     throws IOException, XMLParseException
/*     */   {
/* 216 */     StringBuffer result = new StringBuffer();
/* 217 */     int startingLevel = reader.getStreamLevel();
/* 218 */     char delim = reader.read();
/*     */     
/* 220 */     if ((delim != '\'') && (delim != '"')) {
/* 221 */       errorExpectedInput(reader.getSystemID(), 
/* 222 */         reader.getLineNr(), 
/* 223 */         "delimited string");
/*     */     }
/*     */     for (;;)
/*     */     {
/* 227 */       String str = read(reader, entityChar);
/* 228 */       char ch = str.charAt(0);
/*     */       
/* 230 */       if (ch == entityChar) {
/* 231 */         if (str.charAt(1) == '#') {
/* 232 */           result.append(processCharLiteral(str));
/*     */         } else {
/* 234 */           processEntity(str, reader, entityResolver);
/*     */         }
/* 236 */       } else if (ch == '&') {
/* 237 */         reader.unread(ch);
/* 238 */         str = read(reader, '&');
/* 239 */         if (str.charAt(1) == '#') {
/* 240 */           result.append(processCharLiteral(str));
/*     */         } else {
/* 242 */           result.append(str);
/*     */         }
/* 244 */       } else if (reader.getStreamLevel() == startingLevel) {
/* 245 */         if (ch == delim)
/*     */           break;
/* 247 */         if ((ch == '\t') || (ch == '\n') || (ch == '\r')) {
/* 248 */           result.append(' ');
/*     */         } else {
/* 250 */           result.append(ch);
/*     */         }
/*     */       } else {
/* 253 */         result.append(ch);
/*     */       }
/*     */     }
/*     */     
/* 257 */     return result.toString();
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
/*     */   static void processEntity(String entity, StdXMLReader reader, XMLEntityResolver entityResolver)
/*     */     throws IOException, XMLParseException
/*     */   {
/* 277 */     entity = entity.substring(1, entity.length() - 1);
/* 278 */     Reader entityReader = entityResolver.getEntity(reader, entity);
/*     */     
/* 280 */     if (entityReader == null) {
/* 281 */       errorInvalidEntity(reader.getSystemID(), 
/* 282 */         reader.getLineNr(), 
/* 283 */         entity);
/*     */     }
/*     */     
/* 286 */     boolean externalEntity = entityResolver.isExternalEntity(entity);
/* 287 */     reader.startNewStream(entityReader, !externalEntity);
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
/*     */   static char processCharLiteral(String entity)
/*     */     throws IOException, XMLParseException
/*     */   {
/* 303 */     if (entity.charAt(2) == 'x') {
/* 304 */       entity = entity.substring(3, entity.length() - 1);
/* 305 */       return (char)Integer.parseInt(entity, 16);
/*     */     }
/* 307 */     entity = entity.substring(2, entity.length() - 1);
/* 308 */     return (char)Integer.parseInt(entity, 10);
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
/*     */   static void skipWhitespace(StdXMLReader reader, StringBuffer buffer)
/*     */     throws IOException
/*     */   {
/* 329 */     if (buffer == null) {
/*     */       char ch;
/* 331 */       do { ch = reader.read();
/* 332 */       } while ((ch == ' ') || (ch == '\t') || (ch == '\n'));
/*     */     } else {
/*     */       for (;;) {
/* 335 */         char ch = reader.read();
/*     */         
/* 337 */         if ((ch != ' ') && (ch != '\t') && (ch != '\n')) {
/*     */           break;
/*     */         }
/*     */         
/* 341 */         if (ch == '\n') {
/* 342 */           buffer.append('\n');
/*     */         } else {
/* 344 */           buffer.append(' ');
/*     */         }
/*     */       }
/*     */     }
/*     */     char ch;
/* 349 */     reader.unread(ch);
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
/*     */   static String read(StdXMLReader reader, char entityChar)
/*     */     throws IOException, XMLParseException
/*     */   {
/* 367 */     char ch = reader.read();
/* 368 */     StringBuffer buf = new StringBuffer();
/* 369 */     buf.append(ch);
/*     */     
/* 371 */     if (ch == entityChar) {
/* 372 */       while (ch != ';') {
/* 373 */         ch = reader.read();
/* 374 */         buf.append(ch);
/*     */       }
/*     */     }
/*     */     
/* 378 */     return buf.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static char readChar(StdXMLReader reader, char entityChar)
/*     */     throws IOException, XMLParseException
/*     */   {
/* 391 */     String str = read(reader, entityChar);
/* 392 */     char ch = str.charAt(0);
/*     */     
/* 394 */     if (ch == entityChar) {
/* 395 */       errorUnexpectedEntity(reader.getSystemID(), 
/* 396 */         reader.getLineNr(), 
/* 397 */         str);
/*     */     }
/*     */     
/* 400 */     return ch;
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
/*     */   static boolean checkLiteral(StdXMLReader reader, String literal)
/*     */     throws IOException, XMLParseException
/*     */   {
/* 419 */     for (int i = 0; i < literal.length(); i++) {
/* 420 */       if (reader.read() != literal.charAt(i)) {
/* 421 */         return false;
/*     */       }
/*     */     }
/*     */     
/* 425 */     return true;
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
/*     */   static void errorExpectedInput(String systemID, int lineNr, String expectedString)
/*     */     throws XMLParseException
/*     */   {
/* 442 */     throw new XMLParseException(systemID, lineNr, 
/* 443 */       "Expected: " + expectedString);
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
/*     */   static void errorInvalidEntity(String systemID, int lineNr, String entity)
/*     */     throws XMLParseException
/*     */   {
/* 460 */     throw new XMLParseException(systemID, lineNr, 
/* 461 */       "Invalid entity: `&" + entity + ";'");
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
/*     */   static void errorUnexpectedEntity(String systemID, int lineNr, String entity)
/*     */     throws XMLParseException
/*     */   {
/* 478 */     throw new XMLParseException(systemID, lineNr, 
/* 479 */       "No entity reference is expected here (" + 
/* 480 */       entity + ")");
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
/*     */   static void errorUnexpectedCDATA(String systemID, int lineNr)
/*     */     throws XMLParseException
/*     */   {
/* 495 */     throw new XMLParseException(systemID, lineNr, 
/* 496 */       "No CDATA section is expected here");
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
/*     */   static void errorInvalidInput(String systemID, int lineNr, String unexpectedString)
/*     */     throws XMLParseException
/*     */   {
/* 513 */     throw new XMLParseException(systemID, lineNr, 
/* 514 */       "Invalid input: " + unexpectedString);
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
/*     */   static void errorWrongClosingTag(String systemID, int lineNr, String expectedName, String wrongName)
/*     */     throws XMLParseException
/*     */   {
/* 533 */     throw new XMLParseException(systemID, lineNr, 
/* 534 */       "Closing tag does not match opening tag: `" + 
/* 535 */       wrongName + "' != `" + expectedName + 
/* 536 */       "'");
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
/*     */   static void errorClosingTagNotEmpty(String systemID, int lineNr)
/*     */     throws XMLParseException
/*     */   {
/* 551 */     throw new XMLParseException(systemID, lineNr, 
/* 552 */       "Closing tag must be empty");
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
/*     */   static void errorMissingElement(String systemID, int lineNr, String parentElementName, String missingElementName)
/*     */     throws XMLValidationException
/*     */   {
/* 570 */     throw new XMLValidationException(
/* 571 */       1, 
/* 572 */       systemID, lineNr, 
/* 573 */       missingElementName, 
/* 574 */       null, 
/* 575 */       null, 
/* 576 */       "Element " + parentElementName + 
/* 577 */       " expects to have a " + missingElementName);
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
/*     */   static void errorUnexpectedElement(String systemID, int lineNr, String parentElementName, String unexpectedElementName)
/*     */     throws XMLValidationException
/*     */   {
/* 596 */     throw new XMLValidationException(
/* 597 */       2, 
/* 598 */       systemID, lineNr, 
/* 599 */       unexpectedElementName, 
/* 600 */       null, 
/* 601 */       null, 
/* 602 */       "Unexpected " + unexpectedElementName + " in a " + 
/* 603 */       parentElementName);
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
/*     */   static void errorMissingAttribute(String systemID, int lineNr, String elementName, String attributeName)
/*     */     throws XMLValidationException
/*     */   {
/* 622 */     throw new XMLValidationException(
/* 623 */       3, 
/* 624 */       systemID, lineNr, 
/* 625 */       elementName, 
/* 626 */       attributeName, 
/* 627 */       null, 
/* 628 */       "Element " + elementName + " expects an attribute named " + 
/* 629 */       attributeName);
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
/*     */   static void errorUnexpectedAttribute(String systemID, int lineNr, String elementName, String attributeName)
/*     */     throws XMLValidationException
/*     */   {
/* 648 */     throw new XMLValidationException(
/* 649 */       4, 
/* 650 */       systemID, lineNr, 
/* 651 */       elementName, 
/* 652 */       attributeName, 
/* 653 */       null, 
/* 654 */       "Element " + elementName + " did not expect an attribute " + 
/* 655 */       "named " + attributeName);
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
/*     */   static void errorInvalidAttributeValue(String systemID, int lineNr, String elementName, String attributeName, String attributeValue)
/*     */     throws XMLValidationException
/*     */   {
/* 676 */     throw new XMLValidationException(
/* 677 */       5, 
/* 678 */       systemID, lineNr, 
/* 679 */       elementName, 
/* 680 */       attributeName, 
/* 681 */       attributeValue, 
/* 682 */       "Invalid value for attribute " + attributeName);
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
/*     */   static void errorMissingPCData(String systemID, int lineNr, String parentElementName)
/*     */     throws XMLValidationException
/*     */   {
/* 699 */     throw new XMLValidationException(
/* 700 */       6, 
/* 701 */       systemID, lineNr, 
/* 702 */       null, 
/* 703 */       null, 
/* 704 */       null, 
/* 705 */       "Missing #PCDATA in element " + parentElementName);
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
/*     */   static void errorUnexpectedPCData(String systemID, int lineNr, String parentElementName)
/*     */     throws XMLValidationException
/*     */   {
/* 722 */     throw new XMLValidationException(
/* 723 */       7, 
/* 724 */       systemID, lineNr, 
/* 725 */       null, 
/* 726 */       null, 
/* 727 */       null, 
/* 728 */       "Unexpected #PCDATA in element " + parentElementName);
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
/*     */   static void validationError(String systemID, int lineNr, String message, String elementName, String attributeName, String attributeValue)
/*     */     throws XMLValidationException
/*     */   {
/* 750 */     throw new XMLValidationException(0, 
/* 751 */       systemID, lineNr, 
/* 752 */       elementName, 
/* 753 */       attributeName, 
/* 754 */       attributeValue, 
/* 755 */       message);
/*     */   }
/*     */ }


/* Location:              /Users/AndrewKanarek/Desktop/MICHIGAN/KiefferWork/scripts-zibo/JavaApps/EllinghamDiagram/core.jar!/processing/xml/XMLUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */