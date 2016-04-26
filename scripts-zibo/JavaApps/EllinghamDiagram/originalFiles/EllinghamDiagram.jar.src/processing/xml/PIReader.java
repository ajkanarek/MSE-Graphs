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
/*     */ class PIReader
/*     */   extends Reader
/*     */ {
/*     */   private StdXMLReader reader;
/*     */   private boolean atEndOfData;
/*     */   
/*     */   PIReader(StdXMLReader reader)
/*     */   {
/*  66 */     this.reader = reader;
/*  67 */     this.atEndOfData = false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void finalize()
/*     */     throws Throwable
/*     */   {
/*  77 */     this.reader = null;
/*  78 */     super.finalize();
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
/*  99 */     if (this.atEndOfData) {
/* 100 */       return -1;
/*     */     }
/*     */     
/* 103 */     int charsRead = 0;
/*     */     
/* 105 */     if (offset + size > buffer.length) {
/* 106 */       size = buffer.length - offset;
/*     */     }
/*     */     
/* 109 */     while (charsRead < size) {
/* 110 */       char ch = this.reader.read();
/*     */       
/* 112 */       if (ch == '?') {
/* 113 */         char ch2 = this.reader.read();
/*     */         
/* 115 */         if (ch2 == '>') {
/* 116 */           this.atEndOfData = true;
/* 117 */           break;
/*     */         }
/*     */         
/* 120 */         this.reader.unread(ch2);
/*     */       }
/*     */       
/* 123 */       buffer[charsRead] = ch;
/* 124 */       charsRead++;
/*     */     }
/*     */     
/* 127 */     if (charsRead == 0) {
/* 128 */       charsRead = -1;
/*     */     }
/*     */     
/* 131 */     return charsRead;
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
/* 144 */     while (!this.atEndOfData) {
/* 145 */       char ch = this.reader.read();
/*     */       
/* 147 */       if (ch == '?') {
/* 148 */         char ch2 = this.reader.read();
/*     */         
/* 150 */         if (ch2 == '>') {
/* 151 */           this.atEndOfData = true;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/AndrewKanarek/Desktop/MICHIGAN/KiefferWork/scripts-zibo/JavaApps/EllinghamDiagram/core.jar!/processing/xml/PIReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */