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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ContentReader
/*     */   extends Reader
/*     */ {
/*     */   private StdXMLReader reader;
/*     */   private String buffer;
/*     */   private int bufferIndex;
/*     */   private XMLEntityResolver resolver;
/*     */   
/*     */   ContentReader(StdXMLReader reader, XMLEntityResolver resolver, String buffer)
/*     */   {
/*  82 */     this.reader = reader;
/*  83 */     this.resolver = resolver;
/*  84 */     this.buffer = buffer;
/*  85 */     this.bufferIndex = 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void finalize()
/*     */     throws Throwable
/*     */   {
/*  95 */     this.reader = null;
/*  96 */     this.resolver = null;
/*  97 */     this.buffer = null;
/*  98 */     super.finalize();
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
/*     */   public int read(char[] outputBuffer, int offset, int size)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 120 */       int charsRead = 0;
/* 121 */       int bufferLength = this.buffer.length();
/*     */       
/* 123 */       if (offset + size > outputBuffer.length) {
/* 124 */         size = outputBuffer.length - offset;
/*     */       }
/*     */       
/* 127 */       while (charsRead < size) {
/* 128 */         String str = "";
/*     */         char ch;
/*     */         char ch;
/* 131 */         if (this.bufferIndex >= bufferLength) {
/* 132 */           str = XMLUtil.read(this.reader, '&');
/* 133 */           ch = str.charAt(0);
/*     */         } else {
/* 135 */           ch = this.buffer.charAt(this.bufferIndex);
/* 136 */           this.bufferIndex += 1;
/* 137 */           outputBuffer[charsRead] = ch;
/* 138 */           charsRead++;
/* 139 */           continue;
/*     */         }
/*     */         
/* 142 */         if (ch == '<') {
/* 143 */           this.reader.unread(ch);
/* 144 */           break;
/*     */         }
/*     */         
/* 147 */         if ((ch == '&') && (str.length() > 1)) {
/* 148 */           if (str.charAt(1) == '#') {
/* 149 */             ch = XMLUtil.processCharLiteral(str);
/*     */           } else {
/* 151 */             XMLUtil.processEntity(str, this.reader, this.resolver);
/* 152 */             continue;
/*     */           }
/*     */         }
/*     */         
/* 156 */         outputBuffer[charsRead] = ch;
/* 157 */         charsRead++;
/*     */       }
/*     */       
/* 160 */       if (charsRead == 0) {}
/* 161 */       return -1;
/*     */ 
/*     */     }
/*     */     catch (XMLParseException e)
/*     */     {
/* 166 */       throw new IOException(e.getMessage());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 181 */       int bufferLength = this.buffer.length();
/*     */       for (;;)
/*     */       {
/* 184 */         String str = "";
/*     */         char ch;
/*     */         char ch;
/* 187 */         if (this.bufferIndex >= bufferLength) {
/* 188 */           str = XMLUtil.read(this.reader, '&');
/* 189 */           ch = str.charAt(0);
/*     */         } else {
/* 191 */           ch = this.buffer.charAt(this.bufferIndex);
/* 192 */           this.bufferIndex += 1;
/* 193 */           continue;
/*     */         }
/*     */         
/* 196 */         if (ch == '<') {
/* 197 */           this.reader.unread(ch);
/* 198 */           break;
/*     */         }
/*     */         
/* 201 */         if ((ch == '&') && (str.length() > 1) && 
/* 202 */           (str.charAt(1) != '#')) {
/* 203 */           XMLUtil.processEntity(str, this.reader, this.resolver);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (XMLParseException e) {
/* 208 */       throw new IOException(e.getMessage());
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/AndrewKanarek/Desktop/MICHIGAN/KiefferWork/scripts-zibo/JavaApps/EllinghamDiagram/core.jar!/processing/xml/ContentReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */