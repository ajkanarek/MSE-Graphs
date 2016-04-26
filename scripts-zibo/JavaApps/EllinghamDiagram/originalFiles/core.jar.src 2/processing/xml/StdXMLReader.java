/*     */ package processing.xml;
/*     */ 
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.LineNumberReader;
/*     */ import java.io.PushbackInputStream;
/*     */ import java.io.PushbackReader;
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StdXMLReader
/*     */ {
/*     */   private Stack<StackedReader> readers;
/*     */   private StackedReader currentReader;
/*     */   
/*     */   public static StdXMLReader stringReader(String str)
/*     */   {
/*  97 */     return new StdXMLReader(new StringReader(str));
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
/*     */   public static StdXMLReader fileReader(String filename)
/*     */     throws FileNotFoundException, IOException
/*     */   {
/* 115 */     StdXMLReader r = new StdXMLReader(new FileInputStream(filename));
/* 116 */     r.setSystemID(filename);
/*     */     
/* 118 */     for (int i = 0; i < r.readers.size(); i++) {
/* 119 */       StackedReader sr = (StackedReader)r.readers.elementAt(i);
/* 120 */       sr.systemId = r.currentReader.systemId;
/*     */     }
/*     */     
/* 123 */     return r;
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
/*     */   public StdXMLReader(String publicID, String systemID)
/*     */     throws MalformedURLException, FileNotFoundException, IOException
/*     */   {
/* 146 */     URL systemIDasURL = null;
/*     */     try
/*     */     {
/* 149 */       systemIDasURL = new URL(systemID);
/*     */     } catch (MalformedURLException e) {
/* 151 */       systemID = "file:" + systemID;
/*     */       try
/*     */       {
/* 154 */         systemIDasURL = new URL(systemID);
/*     */       } catch (MalformedURLException e2) {
/* 156 */         throw e;
/*     */       }
/*     */     }
/*     */     
/* 160 */     this.currentReader = new StackedReader(null);
/* 161 */     this.readers = new Stack();
/* 162 */     Reader reader = openStream(publicID, systemIDasURL.toString());
/* 163 */     this.currentReader.lineReader = new LineNumberReader(reader);
/* 164 */     this.currentReader.pbReader = 
/* 165 */       new PushbackReader(this.currentReader.lineReader, 2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public StdXMLReader(Reader reader)
/*     */   {
/* 176 */     this.currentReader = new StackedReader(null);
/* 177 */     this.readers = new Stack();
/* 178 */     this.currentReader.lineReader = new LineNumberReader(reader);
/* 179 */     this.currentReader.pbReader = 
/* 180 */       new PushbackReader(this.currentReader.lineReader, 2);
/* 181 */     this.currentReader.publicId = "";
/*     */     try
/*     */     {
/* 184 */       this.currentReader.systemId = new URL("file:.");
/*     */     }
/*     */     catch (MalformedURLException localMalformedURLException) {}
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void finalize()
/*     */     throws Throwable
/*     */   {
/* 197 */     this.currentReader.lineReader = null;
/* 198 */     this.currentReader.pbReader = null;
/* 199 */     this.currentReader.systemId = null;
/* 200 */     this.currentReader.publicId = null;
/* 201 */     this.currentReader = null;
/* 202 */     this.readers.clear();
/* 203 */     super.finalize();
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
/*     */   protected String getEncoding(String str)
/*     */   {
/* 216 */     if (!str.startsWith("<?xml")) {
/* 217 */       return null;
/*     */     }
/*     */     
/* 220 */     int index = 5;
/*     */     
/* 222 */     while (index < str.length()) {
/* 223 */       StringBuffer key = new StringBuffer();
/*     */       for (;;)
/*     */       {
/* 226 */         index++;
/* 225 */         if (index < str.length()) { if (str.charAt(index) > ' ') {
/*     */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */       do
/*     */       {
/* 232 */         key.append(str.charAt(index));
/* 233 */         index++;
/* 229 */         if ((index >= str.length()) || 
/* 230 */           (str.charAt(index) < 'a')) break;
/* 231 */       } while (str.charAt(index) <= 'z');
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 236 */       while ((index < str.length()) && (str.charAt(index) <= ' ')) {
/* 237 */         index++;
/*     */       }
/*     */       
/* 240 */       if (index >= str.length()) break; if (str.charAt(index) != '=') {
/*     */         break;
/*     */       }
/*     */       
/* 244 */       while ((index < str.length()) && (str.charAt(index) != '\'') && 
/* 245 */         (str.charAt(index) != '"')) {
/* 246 */         index++;
/*     */       }
/*     */       
/* 249 */       if (index >= str.length()) {
/*     */         break;
/*     */       }
/*     */       
/* 253 */       char delimiter = str.charAt(index);
/* 254 */       index++;
/* 255 */       int index2 = str.indexOf(delimiter, index);
/*     */       
/* 257 */       if (index2 < 0) {
/*     */         break;
/*     */       }
/*     */       
/* 261 */       if (key.toString().equals("encoding")) {
/* 262 */         return str.substring(index, index2);
/*     */       }
/*     */       
/* 265 */       index = index2 + 1;
/*     */     }
/*     */     
/* 268 */     return null;
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
/*     */   protected Reader stream2reader(InputStream stream, StringBuffer charsRead)
/*     */     throws IOException
/*     */   {
/* 285 */     PushbackInputStream pbstream = new PushbackInputStream(stream);
/* 286 */     int b = pbstream.read();
/*     */     
/* 288 */     switch (b) {
/*     */     case 0: 
/*     */     case 254: 
/*     */     case 255: 
/* 292 */       pbstream.unread(b);
/* 293 */       return new InputStreamReader(pbstream, "UTF-16");
/*     */     
/*     */     case 239: 
/* 296 */       for (int i = 0; i < 2; i++) {
/* 297 */         pbstream.read();
/*     */       }
/*     */       
/* 300 */       return new InputStreamReader(pbstream, "UTF-8");
/*     */     
/*     */     case 60: 
/* 303 */       b = pbstream.read();
/* 304 */       charsRead.append('<');
/*     */       
/* 306 */       while ((b > 0) && (b != 62)) {
/* 307 */         charsRead.append((char)b);
/* 308 */         b = pbstream.read();
/*     */       }
/*     */       
/* 311 */       if (b > 0) {
/* 312 */         charsRead.append((char)b);
/*     */       }
/*     */       
/* 315 */       String encoding = getEncoding(charsRead.toString());
/*     */       
/* 317 */       if (encoding == null) {
/* 318 */         return new InputStreamReader(pbstream, "UTF-8");
/*     */       }
/*     */       
/* 321 */       charsRead.setLength(0);
/*     */       try
/*     */       {
/* 324 */         return new InputStreamReader(pbstream, encoding);
/*     */       } catch (UnsupportedEncodingException e) {
/* 326 */         return new InputStreamReader(pbstream, "UTF-8");
/*     */       }
/*     */     }
/*     */     
/* 330 */     charsRead.append((char)b);
/* 331 */     return new InputStreamReader(pbstream, "UTF-8");
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
/*     */   public StdXMLReader(InputStream stream)
/*     */     throws IOException
/*     */   {
/* 349 */     StringBuffer charsRead = new StringBuffer();
/* 350 */     Reader reader = stream2reader(stream, charsRead);
/* 351 */     this.currentReader = new StackedReader(null);
/* 352 */     this.readers = new Stack();
/* 353 */     this.currentReader.lineReader = new LineNumberReader(reader);
/* 354 */     this.currentReader.pbReader = 
/* 355 */       new PushbackReader(this.currentReader.lineReader, 2);
/* 356 */     this.currentReader.publicId = "";
/*     */     try
/*     */     {
/* 359 */       this.currentReader.systemId = new URL("file:.");
/*     */     }
/*     */     catch (MalformedURLException localMalformedURLException) {}
/*     */     
/*     */ 
/* 364 */     startNewStream(new StringReader(charsRead.toString()));
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
/*     */   public char read()
/*     */     throws IOException
/*     */   {
/* 379 */     int ch = this.currentReader.pbReader.read();
/*     */     
/* 381 */     while (ch < 0) {
/* 382 */       if (this.readers.empty()) {
/* 383 */         throw new IOException("Unexpected EOF");
/*     */       }
/*     */       
/* 386 */       this.currentReader.pbReader.close();
/* 387 */       this.currentReader = ((StackedReader)this.readers.pop());
/* 388 */       ch = this.currentReader.pbReader.read();
/*     */     }
/*     */     
/* 391 */     return (char)ch;
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
/*     */   public boolean atEOFOfCurrentStream()
/*     */     throws IOException
/*     */   {
/* 405 */     int ch = this.currentReader.pbReader.read();
/*     */     
/* 407 */     if (ch < 0) {
/* 408 */       return true;
/*     */     }
/* 410 */     this.currentReader.pbReader.unread(ch);
/* 411 */     return false;
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
/*     */   public boolean atEOF()
/*     */     throws IOException
/*     */   {
/* 425 */     int ch = this.currentReader.pbReader.read();
/*     */     
/* 427 */     while (ch < 0) {
/* 428 */       if (this.readers.empty()) {
/* 429 */         return true;
/*     */       }
/*     */       
/* 432 */       this.currentReader.pbReader.close();
/* 433 */       this.currentReader = ((StackedReader)this.readers.pop());
/* 434 */       ch = this.currentReader.pbReader.read();
/*     */     }
/*     */     
/* 437 */     this.currentReader.pbReader.unread(ch);
/* 438 */     return false;
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
/*     */   public void unread(char ch)
/*     */     throws IOException
/*     */   {
/* 453 */     this.currentReader.pbReader.unread(ch);
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
/*     */   public Reader openStream(String publicID, String systemID)
/*     */     throws MalformedURLException, FileNotFoundException, IOException
/*     */   {
/* 476 */     URL url = new URL(this.currentReader.systemId, systemID);
/*     */     
/* 478 */     if (url.getRef() != null) {
/* 479 */       String ref = url.getRef();
/*     */       
/* 481 */       if (url.getFile().length() > 0) {
/* 482 */         url = new URL(url.getProtocol(), url.getHost(), url.getPort(), 
/* 483 */           url.getFile());
/* 484 */         url = new URL("jar:" + url + '!' + ref);
/*     */       } else {
/* 486 */         url = StdXMLReader.class.getResource(ref);
/*     */       }
/*     */     }
/*     */     
/* 490 */     this.currentReader.publicId = publicID;
/* 491 */     this.currentReader.systemId = url;
/* 492 */     StringBuffer charsRead = new StringBuffer();
/* 493 */     Reader reader = stream2reader(url.openStream(), charsRead);
/*     */     
/* 495 */     if (charsRead.length() == 0) {
/* 496 */       return reader;
/*     */     }
/*     */     
/* 499 */     String charsReadStr = charsRead.toString();
/* 500 */     PushbackReader pbreader = new PushbackReader(reader, 
/* 501 */       charsReadStr.length());
/*     */     
/* 503 */     for (int i = charsReadStr.length() - 1; i >= 0; i--) {
/* 504 */       pbreader.unread(charsReadStr.charAt(i));
/*     */     }
/*     */     
/* 507 */     return pbreader;
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
/*     */   public void startNewStream(Reader reader)
/*     */   {
/* 520 */     startNewStream(reader, false);
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
/*     */   public void startNewStream(Reader reader, boolean isInternalEntity)
/*     */   {
/* 536 */     StackedReader oldReader = this.currentReader;
/* 537 */     this.readers.push(this.currentReader);
/* 538 */     this.currentReader = new StackedReader(null);
/*     */     
/* 540 */     if (isInternalEntity) {
/* 541 */       this.currentReader.lineReader = null;
/* 542 */       this.currentReader.pbReader = new PushbackReader(reader, 2);
/*     */     } else {
/* 544 */       this.currentReader.lineReader = new LineNumberReader(reader);
/* 545 */       this.currentReader.pbReader = 
/* 546 */         new PushbackReader(this.currentReader.lineReader, 2);
/*     */     }
/*     */     
/* 549 */     this.currentReader.systemId = oldReader.systemId;
/* 550 */     this.currentReader.publicId = oldReader.publicId;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getStreamLevel()
/*     */   {
/* 559 */     return this.readers.size();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getLineNr()
/*     */   {
/* 568 */     if (this.currentReader.lineReader == null) {
/* 569 */       StackedReader sr = (StackedReader)this.readers.peek();
/*     */       
/* 571 */       if (sr.lineReader == null) {
/* 572 */         return 0;
/*     */       }
/* 574 */       return sr.lineReader.getLineNumber() + 1;
/*     */     }
/*     */     
/*     */ 
/* 578 */     return this.currentReader.lineReader.getLineNumber() + 1;
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
/*     */   public void setSystemID(String systemID)
/*     */     throws MalformedURLException
/*     */   {
/* 593 */     this.currentReader.systemId = new URL(this.currentReader.systemId, 
/* 594 */       systemID);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPublicID(String publicID)
/*     */   {
/* 605 */     this.currentReader.publicId = publicID;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getSystemID()
/*     */   {
/* 614 */     return this.currentReader.systemId.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getPublicID()
/*     */   {
/* 623 */     return this.currentReader.publicId;
/*     */   }
/*     */   
/*     */   private class StackedReader
/*     */   {
/*     */     PushbackReader pbReader;
/*     */     LineNumberReader lineReader;
/*     */     URL systemId;
/*     */     String publicId;
/*     */     
/*     */     private StackedReader() {}
/*     */   }
/*     */ }


/* Location:              /Users/AndrewKanarek/Desktop/MICHIGAN/KiefferWork/scripts-zibo/JavaApps/EllinghamDiagram/core.jar!/processing/xml/StdXMLReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */