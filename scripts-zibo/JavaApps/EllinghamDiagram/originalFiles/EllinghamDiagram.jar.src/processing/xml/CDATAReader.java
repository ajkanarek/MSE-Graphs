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
/*     */ class CDATAReader
/*     */   extends Reader
/*     */ {
/*     */   private StdXMLReader reader;
/*     */   private char savedChar;
/*     */   private boolean atEndOfData;
/*     */   
/*     */   CDATAReader(StdXMLReader reader)
/*     */   {
/*  72 */     this.reader = reader;
/*  73 */     this.savedChar = '\000';
/*  74 */     this.atEndOfData = false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void finalize()
/*     */     throws Throwable
/*     */   {
/*  84 */     this.reader = null;
/*  85 */     super.finalize();
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
/*     */   public int read(char[] buffer, int offset, int size)
/*     */     throws IOException
/*     */   {
/* 106 */     int charsRead = 0;
/*     */     
/* 108 */     if (this.atEndOfData) {
/* 109 */       return -1;
/*     */     }
/*     */     
/* 112 */     if (offset + size > buffer.length) {
/* 113 */       size = buffer.length - offset;
/*     */     }
/*     */     
/* 116 */     while (charsRead < size) {
/* 117 */       char ch = this.savedChar;
/*     */       
/* 119 */       if (ch == 0) {
/* 120 */         ch = this.reader.read();
/*     */       } else {
/* 122 */         this.savedChar = '\000';
/*     */       }
/*     */       
/* 125 */       if (ch == ']') {
/* 126 */         char ch2 = this.reader.read();
/*     */         
/* 128 */         if (ch2 == ']') {
/* 129 */           char ch3 = this.reader.read();
/*     */           
/* 131 */           if (ch3 == '>') {
/* 132 */             this.atEndOfData = true;
/* 133 */             break;
/*     */           }
/*     */           
/* 136 */           this.savedChar = ch2;
/* 137 */           this.reader.unread(ch3);
/*     */         } else {
/* 139 */           this.reader.unread(ch2);
/*     */         }
/*     */       }
/* 142 */       buffer[charsRead] = ch;
/* 143 */       charsRead++;
/*     */     }
/*     */     
/* 146 */     if (charsRead == 0) {
/* 147 */       charsRead = -1;
/*     */     }
/*     */     
/* 150 */     return charsRead;
/*     */   }
/*     */   
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
/* 163 */     while (!this.atEndOfData) {
/* 164 */       char ch = this.savedChar;
/*     */       
/* 166 */       if (ch == 0) {
/* 167 */         ch = this.reader.read();
/*     */       } else {
/* 169 */         this.savedChar = '\000';
/*     */       }
/*     */       
/* 172 */       if (ch == ']') {
/* 173 */         char ch2 = this.reader.read();
/*     */         
/* 175 */         if (ch2 == ']') {
/* 176 */           char ch3 = this.reader.read();
/*     */           
/* 178 */           if (ch3 == '>') {
/*     */             break;
/*     */           }
/*     */           
/* 182 */           this.savedChar = ch2;
/* 183 */           this.reader.unread(ch3);
/*     */         } else {
/* 185 */           this.reader.unread(ch2);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 190 */     this.atEndOfData = true;
/*     */   }
/*     */ }


/* Location:              /Users/AndrewKanarek/Desktop/MICHIGAN/KiefferWork/scripts-zibo/JavaApps/EllinghamDiagram/core.jar!/processing/xml/CDATAReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */