/*     */ package processing.xml;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLException
/*     */   extends Exception
/*     */ {
/*     */   private String msg;
/*     */   private String systemID;
/*     */   private int lineNr;
/*     */   private Exception encapsulatedException;
/*     */   
/*     */   public XMLException(String msg)
/*     */   {
/*  77 */     this(null, -1, null, msg, false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public XMLException(Exception e)
/*     */   {
/*  88 */     this(null, -1, e, "Nested Exception", false);
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
/*     */   public XMLException(String systemID, int lineNr, Exception e)
/*     */   {
/* 105 */     this(systemID, lineNr, e, "Nested Exception", true);
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
/*     */   public XMLException(String systemID, int lineNr, String msg)
/*     */   {
/* 122 */     this(systemID, lineNr, null, msg, true);
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
/*     */   public XMLException(String systemID, int lineNr, Exception e, String msg, boolean reportParams)
/*     */   {
/* 144 */     super(buildMessage(systemID, lineNr, e, msg, reportParams));
/* 145 */     this.systemID = systemID;
/* 146 */     this.lineNr = lineNr;
/* 147 */     this.encapsulatedException = e;
/* 148 */     this.msg = buildMessage(systemID, lineNr, e, msg, 
/* 149 */       reportParams);
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
/*     */   private static String buildMessage(String systemID, int lineNr, Exception e, String msg, boolean reportParams)
/*     */   {
/* 170 */     String str = msg;
/*     */     
/* 172 */     if (reportParams) {
/* 173 */       if (systemID != null) {
/* 174 */         str = str + ", SystemID='" + systemID + "'";
/*     */       }
/*     */       
/* 177 */       if (lineNr >= 0) {
/* 178 */         str = str + ", Line=" + lineNr;
/*     */       }
/*     */       
/* 181 */       if (e != null) {
/* 182 */         str = str + ", Exception: " + e;
/*     */       }
/*     */     }
/*     */     
/* 186 */     return str;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void finalize()
/*     */     throws Throwable
/*     */   {
/* 196 */     this.systemID = null;
/* 197 */     this.encapsulatedException = null;
/* 198 */     super.finalize();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getSystemID()
/*     */   {
/* 208 */     return this.systemID;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getLineNr()
/*     */   {
/* 218 */     return this.lineNr;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Exception getException()
/*     */   {
/* 228 */     return this.encapsulatedException;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void printStackTrace(PrintWriter writer)
/*     */   {
/* 239 */     super.printStackTrace(writer);
/*     */     
/* 241 */     if (this.encapsulatedException != null) {
/* 242 */       writer.println("*** Nested Exception:");
/* 243 */       this.encapsulatedException.printStackTrace(writer);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void printStackTrace(PrintStream stream)
/*     */   {
/* 255 */     super.printStackTrace(stream);
/*     */     
/* 257 */     if (this.encapsulatedException != null) {
/* 258 */       stream.println("*** Nested Exception:");
/* 259 */       this.encapsulatedException.printStackTrace(stream);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void printStackTrace()
/*     */   {
/* 269 */     super.printStackTrace();
/*     */     
/* 271 */     if (this.encapsulatedException != null) {
/* 272 */       System.err.println("*** Nested Exception:");
/* 273 */       this.encapsulatedException.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 283 */     return this.msg;
/*     */   }
/*     */ }


/* Location:              /Users/AndrewKanarek/Desktop/MICHIGAN/KiefferWork/scripts-zibo/JavaApps/EllinghamDiagram/core.jar!/processing/xml/XMLException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */