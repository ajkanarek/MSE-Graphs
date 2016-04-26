/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class EllinghamDiagram$EllinghamBoard
/*     */ {
/*     */   EllinghamDiagram.EllinghamPlot plot;
/*     */   int posX;
/*     */   int posY;
/*     */   int sizeX;
/*     */   int sizeY;
/*     */   
/*     */   EllinghamDiagram$EllinghamBoard(EllinghamDiagram paramEllinghamDiagram, EllinghamDiagram.EllinghamPlot paramEllinghamPlot, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/* 116 */     this.plot = paramEllinghamPlot;
/* 117 */     this.posX = paramInt1;
/* 118 */     this.posY = paramInt2;
/* 119 */     this.sizeX = paramInt3;
/* 120 */     this.sizeY = paramInt4;
/*     */   }
/*     */   
/*     */   public void draw() {
/* 124 */     this.this$0.pushMatrix();
/* 125 */     this.this$0.translate(this.posX, this.posY);
/* 126 */     this.this$0.noStroke();
/* 127 */     this.this$0.fill(300);
/* 128 */     this.this$0.rect(0.0F, 0.0F, this.sizeX, this.sizeY);
/* 129 */     this.this$0.fill(0);
/* 130 */     this.this$0.textFont(this.plot.font, 12.0F);
/* 131 */     String str = null;
/* 132 */     switch (this.plot.control) {
/* 133 */     case 'O':  str = "pO2"; break;
/* 134 */     case 'H':  str = "H2/H2O ratio"; break;
/* 135 */     case 'C':  str = "CO/CO2 ratio";
/*     */     }
/* 137 */     if (str != null)
/* 138 */       this.this$0.text(str + " : " + String.format("%.3e", new Object[] { Double.valueOf(this.plot.controlValue) }), 20.0F, 20.0F);
/* 139 */     this.this$0.text("... ", 20.0F, 40.0F);
/*     */     
/*     */ 
/* 142 */     this.this$0.popMatrix();
/*     */   }
/*     */   
/*     */   public String status() {
/* 146 */     String str = null;
/* 147 */     switch (this.plot.control) {
/* 148 */     case 'O':  str = "pO2"; break;
/* 149 */     case 'H':  str = "H2/H2O ratio"; break;
/* 150 */     case 'C':  str = "CO/CO2 ratio";
/*     */     }
/* 152 */     if (str != null)
/* 153 */       return str + " : " + String.format("%.3e", new Object[] { Double.valueOf(this.plot.controlValue) });
/* 154 */     return "";
/*     */   }
/*     */ }


/* Location:              /Users/AndrewKanarek/Desktop/MICHIGAN/KiefferWork/scripts-zibo/JavaApps/EllinghamDiagram/EllinghamDiagram.jar!/EllinghamDiagram$EllinghamBoard.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */