/*     */ import java.awt.event.MouseEvent;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import processing.core.PApplet;
/*     */ import processing.core.PFont;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class EllinghamDiagram$EllinghamPlot
/*     */ {
/*     */   int xpos;
/*     */   int ypos;
/*     */   int xsize;
/*     */   int ysize;
/*     */   Plot plot;
/*     */   PApplet applet;
/*     */   PFont font;
/* 165 */   int[] padding = { 50, 60, 65, 50 };
/*     */   int HX1;
/*     */   int HY1;
/*     */   int HX2;
/*     */   int HY2;
/*     */   int HX3;
/* 171 */   int HY3; int HX4; int HY4; int CX1; int CY1; int CX2; int CY2; int CX3; int CY3; int CX4; int CY4; int OX1; int OY1; int OX2; int OY2; int OX3; int OY3; int startx; int starty; int O; int H; int C; int endx; int endy; int hHX = -1; int hHY = -1; int hCX = -1; int hCY = -1; int hOX = -1; int hOY = -1;
/*     */   boolean controlH;
/*     */   boolean controlC;
/*     */   boolean controlO;
/*     */   char control;
/*     */   double controlValue;
/*     */   public double controlValueH;
/*     */   public double controlValueC;
/*     */   public double controlValueO;
/*     */   boolean updated;
/*     */   boolean dragging;
/* 182 */   static final float R = 8.314472F; TextLabel labelH; TextLabel labelC; TextLabel labelO; TextLabel labelY; ArrayList<Float> intersectX = new ArrayList();
/* 183 */   ArrayList<Float> intersectY = new ArrayList();
/* 184 */   int labelIndex = -1;
/*     */   
/*     */   EllinghamDiagram$EllinghamPlot(EllinghamDiagram paramEllinghamDiagram, PApplet paramPApplet, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 187 */     this.xpos = paramInt1;
/* 188 */     this.ypos = paramInt2;
/* 189 */     this.xsize = paramInt3;
/* 190 */     this.ysize = paramInt4;
/* 191 */     this.applet = paramPApplet;
/*     */     
/* 193 */     this.plot = new Plot(paramPApplet, paramInt1 + this.padding[3], paramInt2 + this.padding[0], paramInt3 - this.padding[1] - this.padding[3], paramInt4 - this.padding[0] - this.padding[2]);
/* 194 */     this.font = this.plot.font;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 201 */     float[] arrayOfFloat1 = { 0.0F, 961.78F, 2160.0F };
/* 202 */     float[] arrayOfFloat2 = { -24.98394F, 102.203766F, 260.63242F };
/* 203 */     this.plot.AddData(arrayOfFloat1, arrayOfFloat2);
/* 204 */     this.plot.AddTag("4Ag+O<sub>2</sub>=2Ag<sub>2</sub>O");
/*     */     
/* 206 */     arrayOfFloat1 = new float[] { 0.0F, 1084.62F, 1230.0F };
/* 207 */     arrayOfFloat2 = new float[] { -286.59F, -136.37463F, -110.60081F };
/* 208 */     this.plot.AddData(arrayOfFloat1, arrayOfFloat2);
/* 209 */     this.plot.AddTag("4Cu+O<sub>2</sub>=2Cu<sub>2</sub>O");
/*     */     
/* 211 */     arrayOfFloat1 = new float[] { 0.0F, 1454.85F, 2400.0F };
/* 212 */     arrayOfFloat2 = new float[] { -424.244F, -173.98228F, 7.601352F };
/* 213 */     this.plot.AddData(arrayOfFloat1, arrayOfFloat2);
/* 214 */     this.plot.AddTag("2Ni+O<sub>2</sub>=2NiO");
/*     */     
/* 216 */     arrayOfFloat1 = new float[] { 0.0F, 1495.0F, 1933.0F };
/* 217 */     arrayOfFloat2 = new float[] { -428.55078F, -213.59164F, -150.62038F };
/* 218 */     this.plot.AddData(arrayOfFloat1, arrayOfFloat2);
/* 219 */     this.plot.AddTag("2Co+O<sub>2</sub>=2CoO");
/*     */     
/* 221 */     arrayOfFloat1 = new float[] { 0.0F, 2400.0F };
/* 222 */     arrayOfFloat2 = new float[] { -394.3293F, -396.34546F };
/* 223 */     this.plot.AddData(arrayOfFloat1, arrayOfFloat2);
/* 224 */     this.plot.AddTag("C+O<sub>2</sub>=CO<sub>2</sub>");
/*     */     
/* 226 */     arrayOfFloat1 = new float[] { 0.0F, 2400.0F };
/* 227 */     arrayOfFloat2 = new float[] { -271.2569F, -692.00494F };
/* 228 */     this.plot.AddData(arrayOfFloat1, arrayOfFloat2);
/* 229 */     this.plot.AddTag("2C+O<sub>2</sub>=2CO");
/*     */     
/* 231 */     arrayOfFloat1 = new float[] { 0.0F, 2400.0F };
/* 232 */     arrayOfFloat2 = new float[] { -516.6072F, -99.93942F };
/* 233 */     this.plot.AddData(arrayOfFloat1, arrayOfFloat2);
/* 234 */     this.plot.AddTag("2CO+O<sub>2</sub>=2CO<sub>2</sub>");
/*     */     
/* 236 */     arrayOfFloat1 = new float[] { 100.0F, 2400.0F };
/* 237 */     arrayOfFloat2 = new float[] { -464.5059F, -196.40804F };
/* 238 */     this.plot.AddData(arrayOfFloat1, arrayOfFloat2);
/* 239 */     this.plot.AddTag("2H<sub>2</sub>+O<sub>2</sub>=2H<sub>2</sub>O");
/*     */     
/* 241 */     arrayOfFloat1 = new float[] { 0.0F, 1370.0F, 1537.85F, 2400.0F };
/* 242 */     arrayOfFloat2 = new float[] { -492.2649F, -315.92532F, -294.323F, -225.00954F };
/* 243 */     this.plot.AddData(arrayOfFloat1, arrayOfFloat2);
/* 244 */     this.plot.AddTag("2Fe+O<sub>2</sub>=2FeO");
/*     */     
/* 246 */     arrayOfFloat1 = new float[] { 0.0F, 1537.85F };
/* 247 */     arrayOfFloat2 = new float[] { -509.1399F, -272.74777F };
/* 248 */     this.plot.AddData(arrayOfFloat1, arrayOfFloat2);
/* 249 */     this.plot.AddTag(fraction(3, 2) + "Fe+O<sub>2</sub>=Fe<sub>3</sub>O<sub>4</sub>");
/*     */     
/* 251 */     arrayOfFloat1 = new float[] { 0.0F, 1906.85F };
/* 252 */     arrayOfFloat2 = new float[] { -695.0572F, -380.6484F };
/* 253 */     this.plot.AddData(arrayOfFloat1, arrayOfFloat2);
/* 254 */     this.plot.AddTag(fraction(4, 3) + "Cr+O<sub>2</sub>=" + fraction(2, 3) + "Cr<sub>2</sub>O<sub>3</sub>");
/*     */     
/* 256 */     arrayOfFloat1 = new float[] { 0.0F, 1245.85F, 1785.0F };
/* 257 */     arrayOfFloat2 = new float[] { -736.1293F, -545.9383F, -450.58936F };
/* 258 */     this.plot.AddData(arrayOfFloat1, arrayOfFloat2);
/* 259 */     this.plot.AddTag("2Mn+O<sub>2</sub>=2MnO");
/*     */     
/* 261 */     arrayOfFloat1 = new float[] { 0.0F, 842.0F, 1484.0F, 2400.0F };
/* 262 */     arrayOfFloat2 = new float[] { -1226.578F, -1046.3558F, -908.9678F, -547.676F };
/* 263 */     this.plot.AddData(arrayOfFloat1, arrayOfFloat2);
/* 264 */     this.plot.AddTag("2Ca+O<sub>2</sub>=2CaO");
/*     */     
/* 266 */     arrayOfFloat1 = new float[] { 0.0F, 649.85F, 1089.85F, 2400.0F };
/* 267 */     arrayOfFloat2 = new float[] { -1144.2928F, -1003.5591F, -908.2947F, -368.55072F };
/* 268 */     this.plot.AddData(arrayOfFloat1, arrayOfFloat2);
/* 269 */     this.plot.AddTag("2Mg+O<sub>2</sub>=2MgO");
/*     */     
/* 271 */     arrayOfFloat1 = new float[] { 0.0F, 1413.85F };
/* 272 */     arrayOfFloat2 = new float[] { -859.325F, -611.8732F };
/* 273 */     this.plot.AddData(arrayOfFloat1, arrayOfFloat2);
/* 274 */     this.plot.AddTag("Si+O<sub>2</sub>=SiO<sub>2</sub>");
/*     */     
/* 276 */     arrayOfFloat1 = new float[] { 0.0F, 1668.0F, 1800.0F };
/* 277 */     arrayOfFloat2 = new float[] { -862.771F, -574.1793F, -551.3433F };
/* 278 */     this.plot.AddData(arrayOfFloat1, arrayOfFloat2);
/* 279 */     this.plot.AddTag("Ti+O<sub>2</sub>=TiO<sub>2</sub>");
/*     */     
/* 281 */     arrayOfFloat1 = new float[] { 0.0F, 660.32F, 2054.0F };
/* 282 */     arrayOfFloat2 = new float[] { -1065.3215F, -921.4227F, -617.7817F };
/* 283 */     this.plot.AddData(arrayOfFloat1, arrayOfFloat2);
/* 284 */     this.plot.AddTag(fraction(4, 3) + "Al+O<sub>2</sub>=" + fraction(2, 3) + "Al<sub>2</sub>O<sub>3</sub>");
/*     */     
/* 286 */     this.plot.HideAll();
/* 287 */     this.plot.ShowAxis();
/* 288 */     this.plot.SetLineType(Plot.Circled);
/* 289 */     this.plot.SetAxis(0.0F, 2400.0F, -1200.0F, 0.0F);
/* 290 */     this.plot.SetXTitle("Temperature (°C)");
/*     */     
/*     */ 
/* 293 */     this.HX1 = (paramInt1 + this.padding[3]);
/* 294 */     this.HY1 = (paramInt2 + 20);
/* 295 */     this.HX2 = (paramInt1 + paramInt3 - 40);
/* 296 */     this.HY2 = (paramInt2 + 20);
/* 297 */     this.HX3 = (paramInt1 + paramInt3 - 40);
/* 298 */     this.HY3 = (paramInt2 + paramInt4 - 40);
/* 299 */     this.HX4 = (paramInt1 + this.padding[3]);
/* 300 */     this.HY4 = (paramInt2 + paramInt4 - 40);
/*     */     
/*     */ 
/* 303 */     this.CX1 = (paramInt1 + this.padding[3]);
/* 304 */     this.CY1 = (paramInt2 + 40);
/* 305 */     this.CX2 = (paramInt1 + paramInt3 - 60);
/* 306 */     this.CY2 = (paramInt2 + 40);
/* 307 */     this.CX3 = (paramInt1 + paramInt3 - 60);
/* 308 */     this.CY3 = (paramInt2 + paramInt4 - 60);
/* 309 */     this.CX4 = (paramInt1 + this.padding[3]);
/* 310 */     this.CY4 = (paramInt2 + paramInt4 - 60);
/*     */     
/*     */ 
/* 313 */     this.OX1 = (paramInt1 + paramInt3 - 20);
/* 314 */     this.OY1 = (paramInt2 + 20);
/* 315 */     this.OX2 = (paramInt1 + paramInt3 - 20);
/* 316 */     this.OY2 = (paramInt2 + paramInt4 - 20);
/* 317 */     this.OX3 = (paramInt1 + this.padding[3]);
/* 318 */     this.OY3 = (paramInt2 + paramInt4 - 20);
/*     */     
/*     */ 
/* 321 */     this.labelH = new TextLabel(paramPApplet, "H_2 /H_2 O ratio");
/* 322 */     this.labelH.SetAlign(37, 102);
/* 323 */     this.labelC = new TextLabel(paramPApplet, "CO/CO_2  ratio");
/* 324 */     this.labelC.SetAlign(37, 102);
/* 325 */     this.labelO = new TextLabel(paramPApplet, "pO_2");
/* 326 */     this.labelO.SetAlign(3, 102);
/* 327 */     this.labelY = new TextLabel(paramPApplet, "Standard Free Energies of formulation of oxides (ΔG=RT ln pO_2 )");
/* 328 */     this.labelY.SetRotation(-1.5707964F);
/*     */     
/*     */ 
/* 331 */     this.startx = (paramInt1 + this.padding[3]);
/* 332 */     this.starty = (paramInt2 + this.padding[0] + this.plot.padding[0]);
/* 333 */     this.endx = (paramInt1 + this.padding[3]);
/* 334 */     this.endy = (paramInt2 + paramInt4 - this.padding[2] - this.plot.padding[2]);
/* 335 */     this.O = this.starty;
/* 336 */     this.H = (this.starty + (this.endy - this.starty) * 495 / 1200);
/* 337 */     this.C = (this.starty + (this.endy - this.starty) * 566 / 1200);
/*     */     
/* 339 */     this.updated = true;
/*     */   }
/*     */   
/*     */   public String fraction(int paramInt1, int paramInt2) {
/* 343 */     return "<span style='font-size:80%;letter-spacing:-1px;'><sup>" + paramInt1 + "</sup>&frasl;<sub>" + paramInt2 + "</sub> </span>";
/*     */   }
/*     */   
/*     */   public void ForcePlot() {
/* 347 */     this.updated = true;
/* 348 */     ScribePlot();
/*     */   }
/*     */   
/*     */   public int addData(float paramFloat1, float paramFloat2, String paramString, float paramFloat3, float paramFloat4) {
/* 352 */     float[] arrayOfFloat1 = { paramFloat3, paramFloat4 };
/* 353 */     float[] arrayOfFloat2 = { paramFloat1 + paramFloat3 * paramFloat2, paramFloat1 + paramFloat4 * paramFloat2 };
/* 354 */     this.plot.AddData(arrayOfFloat1, arrayOfFloat2);
/* 355 */     this.plot.AddTag(paramString);
/* 356 */     int i = this.plot.data.size() - 1;
/* 357 */     this.plot.SetHidden(i, false);
/* 358 */     findIntersects();
/* 359 */     this.updated = true;
/* 360 */     return i;
/*     */   }
/*     */   
/*     */   public String formatFloat(float paramFloat) {
/* 364 */     if (EllinghamDiagram.abs(paramFloat) < 10.0F)
/* 365 */       return String.format("%.3f", new Object[] { Float.valueOf(paramFloat) });
/* 366 */     if (EllinghamDiagram.abs(paramFloat) < 100.0F)
/* 367 */       return String.format("%.2f", new Object[] { Float.valueOf(paramFloat) });
/* 368 */     if (EllinghamDiagram.abs(paramFloat) < 1000.0F)
/* 369 */       return String.format("%.1f", new Object[] { Float.valueOf(paramFloat) });
/* 370 */     return String.format("%d", new Object[] { Integer.valueOf((int)paramFloat) });
/*     */   }
/*     */   
/*     */   public void ScribePlot() {
/* 374 */     if (!this.updated) return;
/* 375 */     this.plot.ForcePlot();
/*     */     
/*     */ 
/* 378 */     this.this$0.pushMatrix();
/* 379 */     this.this$0.rotate(-1.5707964F);
/* 380 */     this.this$0.translate(-this.ypos * 2 - this.ysize, 0.0F);
/* 381 */     this.this$0.text("Standard Free Energies of formation of oxides (ΔG)", this.ypos, this.xpos + 10, this.ysize, 25.0F);
/* 382 */     this.this$0.popMatrix();
/*     */     
/* 384 */     this.this$0.textFont(this.font, 12.0F);
/* 385 */     this.this$0.textAlign(3, 3);
/* 386 */     this.this$0.line(this.startx, this.starty, this.endx, this.endy);
/* 387 */     this.this$0.stroke(0.0F, 200.0F, 0.0F);this.this$0.fill(0.0F, 200.0F, 0.0F);
/* 388 */     this.this$0.ellipse(this.startx, this.starty, 4.0F, 4.0F);
/* 389 */     this.this$0.line(this.startx - 3, this.starty, this.startx, this.starty);
/* 390 */     this.this$0.stroke(0.0F, 0.0F, 200.0F);this.this$0.fill(0.0F, 0.0F, 200.0F);
/* 391 */     this.this$0.ellipse(this.startx, this.H, 4.0F, 4.0F);
/* 392 */     this.this$0.stroke(200.0F, 0.0F, 0.0F);this.this$0.fill(200.0F, 0.0F, 0.0F);
/* 393 */     this.this$0.ellipse(this.startx, this.C, 4.0F, 4.0F);
/* 394 */     this.this$0.stroke(0);this.this$0.fill(0);
/* 395 */     this.this$0.line(this.endx - 3, this.endy, this.endx, this.endy);
/* 396 */     this.this$0.text("O", this.startx - 10, this.starty);
/* 397 */     this.this$0.text("H", this.startx - 10, this.H);
/* 398 */     this.this$0.text("C", this.startx - 10, this.C);
/* 399 */     this.this$0.text("0K", this.endx - 10, this.endy);
/*     */     
/*     */ 
/* 402 */     this.this$0.textAlign(37);
/* 403 */     this.labelH.draw(this.HX1, this.HY1 - 3);
/* 404 */     this.this$0.line(this.HX1, this.HY1, this.HX2, this.HY2);
/* 405 */     this.this$0.line(this.HX2, this.HY2, this.HX3, this.HY3);
/* 406 */     this.this$0.line(this.HX3, this.HY3, this.HX4, this.HY4);
/* 407 */     this.labelH.draw(this.HX4, this.HY4 - 3);
/*     */     
/*     */ 
/* 410 */     this.labelC.draw(this.CX1, this.CY1 - 3);
/* 411 */     this.this$0.line(this.CX1, this.CY1, this.CX2, this.CY2);
/* 412 */     this.this$0.line(this.CX2, this.CY2, this.CX3, this.CY3);
/* 413 */     this.this$0.line(this.CX3, this.CY3, this.CX4, this.CY4);
/* 414 */     this.labelC.draw(this.CX4, this.CY4 - 3);
/*     */     
/*     */ 
/* 417 */     this.labelO.draw(this.OX1, this.OY1 - 3);
/* 418 */     this.this$0.line(this.OX1, this.OY1, this.OX2, this.OY2);
/* 419 */     this.this$0.line(this.OX2, this.OY2, this.OX3, this.OY3);
/*     */     
/*     */ 
/* 422 */     if (this.controlO) {
/* 423 */       this.this$0.stroke(0.0F, 200.0F, 0.0F);this.this$0.fill(0.0F, 200.0F, 0.0F);
/* 424 */       this.this$0.line(this.startx, this.starty, this.hOX, this.hOY);
/* 425 */       this.this$0.ellipse(this.hOX, this.hOY, 10.0F, 10.0F);
/*     */     }
/*     */     
/* 428 */     if (this.controlC) {
/* 429 */       this.this$0.stroke(200.0F, 0.0F, 0.0F);this.this$0.fill(200.0F, 0.0F, 0.0F);
/* 430 */       this.this$0.line(this.startx, this.C, this.hCX, this.hCY);
/* 431 */       this.this$0.ellipse(this.hCX, this.hCY, 10.0F, 10.0F);
/*     */     }
/*     */     
/* 434 */     if (this.controlH) {
/* 435 */       this.this$0.stroke(0.0F, 0.0F, 200.0F);this.this$0.fill(0.0F, 0.0F, 200.0F);
/* 436 */       this.this$0.line(this.startx, this.H, this.hHX, this.hHY);
/* 437 */       this.this$0.ellipse(this.hHX, this.hHY, 10.0F, 10.0F); }
/*     */     float f2;
/*     */     float f3;
/*     */     float f4;
/* 441 */     float f5; String str2; for (int i = 0; i < this.intersectX.size(); i++) {
/* 442 */       f2 = ((Float)this.intersectX.get(i)).floatValue();
/* 443 */       f3 = ((Float)this.intersectY.get(i)).floatValue();
/* 444 */       f4 = f2 / 6.469F + this.padding[3] + this.plot.padding[3];
/* 445 */       f5 = this.padding[0] + this.plot.padding[0] - f3 / 2.857F;
/*     */       
/* 447 */       this.this$0.fill(255);
/* 448 */       this.this$0.stroke(0);
/* 449 */       if (f4 + 200.0F > this.applet.width)
/* 450 */         f4 -= 80.0F;
/* 451 */       this.this$0.rect(f4 + 5.0F, f5 + 5.0F, 70.0F, 35.0F);
/*     */       
/* 453 */       str2 = formatFloat(f2);
/* 454 */       String str3 = formatFloat(f3);
/* 455 */       this.this$0.fill(0);
/* 456 */       this.this$0.textAlign(37, 101);
/* 457 */       this.this$0.text("T = " + str2, f4 + 10.0F, f5 + 10.0F);
/* 458 */       this.this$0.text("ΔG = " + str3, f4 + 10.0F, f5 + 25.0F);
/*     */     }
/*     */     
/* 461 */     for (i = 0; i < this.intersectX.size(); i++) {
/* 462 */       f2 = ((Float)this.intersectX.get(i)).floatValue();
/* 463 */       f3 = ((Float)this.intersectY.get(i)).floatValue();
/* 464 */       f4 = f2 / 6.469F + this.padding[3] + this.plot.padding[3];
/* 465 */       f5 = this.padding[0] + this.plot.padding[0] - f3 / 2.857F;
/* 466 */       this.this$0.stroke(255.0F, 0.0F, 0.0F);
/* 467 */       this.this$0.fill(255.0F, 0.0F, 0.0F);
/* 468 */       this.this$0.ellipse(f4, f5, 8.0F, 8.0F);
/*     */     }
/*     */     
/*     */ 
/* 472 */     if (this.labelIndex != -1) {
/* 473 */       float f1 = ((Float)this.intersectX.get(this.labelIndex)).floatValue();
/* 474 */       f2 = ((Float)this.intersectY.get(this.labelIndex)).floatValue();
/* 475 */       f3 = f1 / 6.469F + this.padding[3] + this.plot.padding[3];
/* 476 */       f4 = this.padding[0] + this.plot.padding[0] - f2 / 2.857F;
/* 477 */       this.this$0.fill(255);
/* 478 */       this.this$0.stroke(0);
/* 479 */       if (f3 + 200.0F > this.applet.width)
/* 480 */         f3 -= 80.0F;
/* 481 */       this.this$0.rect(f3 + 5.0F, f4 + 5.0F, 70.0F, 35.0F);
/*     */       
/* 483 */       String str1 = formatFloat(f1);
/* 484 */       str2 = formatFloat(f2);
/* 485 */       this.this$0.fill(0);
/* 486 */       this.this$0.textAlign(37, 101);
/* 487 */       this.this$0.text("T = " + str1, f3 + 10.0F, f4 + 10.0F);
/* 488 */       this.this$0.text("ΔG = " + str2, f3 + 10.0F, f4 + 25.0F);
/*     */     }
/*     */     
/* 491 */     this.updated = false;
/*     */   }
/*     */   
/*     */   public boolean OnLine(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/* 496 */     if (paramInt1 == paramInt3)
/* 497 */       return (EllinghamDiagram.abs(this.this$0.mouseX - paramInt1) < 8) && (EllinghamDiagram.min(paramInt2, paramInt4) <= this.this$0.mouseY) && (this.this$0.mouseY <= EllinghamDiagram.max(paramInt2, paramInt4));
/* 498 */     if (paramInt2 == paramInt4)
/* 499 */       return (EllinghamDiagram.abs(this.this$0.mouseY - paramInt2) < 8) && (EllinghamDiagram.min(paramInt1, paramInt3) <= this.this$0.mouseX) && (this.this$0.mouseX <= EllinghamDiagram.max(paramInt1, paramInt3));
/* 500 */     int[] arrayOfInt = ClosestOnLine(paramInt1, paramInt2, paramInt3, paramInt4);
/* 501 */     return (arrayOfInt[2] != -1) && (arrayOfInt[2] <= 49);
/*     */   }
/*     */   
/*     */ 
/*     */   public int[] ClosestOnLine(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/* 507 */     int i = paramInt3 - paramInt1;int j = paramInt4 - paramInt2;
/* 508 */     int k = this.this$0.mouseX - paramInt1;int m = this.this$0.mouseY - paramInt2;
/* 509 */     int n = this.this$0.mouseX - paramInt3;int i1 = this.this$0.mouseY - paramInt4;
/* 510 */     int[] arrayOfInt = new int[3];
/* 511 */     if ((i == 0) && (j == 0)) {
/* 512 */       System.err.println("invalid line");
/* 513 */       return arrayOfInt;
/*     */     }
/* 515 */     if ((k == 0) && (m == 0)) {
/* 516 */       arrayOfInt[0] = paramInt1;
/* 517 */       arrayOfInt[1] = paramInt2;
/* 518 */       arrayOfInt[2] = 0;
/* 519 */       return arrayOfInt;
/*     */     }
/* 521 */     if ((n == 0) && (i1 == 0)) {
/* 522 */       arrayOfInt[0] = paramInt3;
/* 523 */       arrayOfInt[1] = paramInt4;
/* 524 */       arrayOfInt[2] = 0;
/* 525 */       return arrayOfInt;
/*     */     }
/*     */     
/*     */ 
/* 529 */     float f1 = (i * k + j * m) / (i * i + j * j);
/* 530 */     float f2 = f1 * i;float f3 = f1 * j;
/* 531 */     if (f1 <= 0.0F) {
/* 532 */       arrayOfInt[0] = paramInt1;
/* 533 */       arrayOfInt[1] = paramInt2;
/* 534 */       arrayOfInt[2] = (k * k + m * m);
/* 535 */       return arrayOfInt;
/*     */     }
/* 537 */     if (f2 * f2 + f3 * f3 > i * i + j * j) {
/* 538 */       arrayOfInt[0] = paramInt3;
/* 539 */       arrayOfInt[1] = paramInt4;
/* 540 */       arrayOfInt[2] = (n * n + i1 * i1);
/* 541 */       return arrayOfInt;
/*     */     }
/* 543 */     arrayOfInt[0] = (paramInt1 + (int)f2);
/* 544 */     arrayOfInt[1] = (paramInt2 + (int)f3);
/* 545 */     int i2 = this.this$0.mouseX - arrayOfInt[0];
/* 546 */     int i3 = this.this$0.mouseY - arrayOfInt[1];
/* 547 */     arrayOfInt[2] = (i2 * i2 + i3 * i3);
/* 548 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */   public void mousePressed() {
/* 552 */     int i = 0;
/* 553 */     int j = this.control;
/* 554 */     if ((OnLine(this.OX1, this.OY1, this.OX2, this.OY2)) || (OnLine(this.OX2, this.OY2, this.OX3, this.OY3))) {
/* 555 */       this.control = 'O';
/* 556 */       i = 1;
/*     */     }
/* 558 */     if ((OnLine(this.HX1, this.HY1, this.HX2, this.HY2)) || (OnLine(this.HX2, this.HY2, this.HX3, this.HY3)) || (OnLine(this.HX3, this.HY3, this.HX4, this.HY4))) {
/* 559 */       this.control = 'H';
/* 560 */       i = 1;
/*     */     }
/* 562 */     if ((OnLine(this.CX1, this.CY1, this.CX2, this.CY2)) || (OnLine(this.CX2, this.CY2, this.CX3, this.CY3)) || (OnLine(this.CX3, this.CY3, this.CX4, this.CY4))) {
/* 563 */       this.control = 'C';
/* 564 */       i = 1;
/*     */     }
/* 566 */     this.dragging = true;
/* 567 */     if ((j != 0) && (i == 0)) {
/* 568 */       this.dragging = false;
/*     */     }
/* 570 */     if (this.this$0.mouseEvent.getClickCount() == 2) {
/* 571 */       this.control = '\000';
/* 572 */       this.controlO = (this.controlH = this.controlC = 0);
/* 573 */       this.controlValueO = (this.controlValueH = this.controlValueC = 0.0D);
/* 574 */       this.updated = true;
/* 575 */       this.dragging = false;
/* 576 */       findIntersects();
/* 577 */       return;
/*     */     }
/* 579 */     mouseDragged();
/*     */   }
/*     */   
/*     */   public void mouseDragged() {
/* 583 */     if (!this.dragging) { return;
/*     */     }
/*     */     
/* 586 */     int i = this.ypos + this.padding[0] + this.plot.padding[0];
/* 587 */     int j = this.xpos + this.padding[3] + this.plot.padding[3];
/* 588 */     int[] arrayOfInt1; int[] arrayOfInt2; int[] arrayOfInt3; if (this.control == 'O') {
/* 589 */       arrayOfInt1 = ClosestOnLine(this.OX1, i, this.OX2, this.OY2);
/* 590 */       arrayOfInt2 = ClosestOnLine(this.OX2, this.OY2, j, this.OY3);
/* 591 */       arrayOfInt3 = arrayOfInt1[2] < arrayOfInt2[2] ? arrayOfInt1 : arrayOfInt2;
/* 592 */       double d = 0.0D;
/* 593 */       this.hOX = arrayOfInt3[0];
/* 594 */       this.hOY = arrayOfInt3[1];
/* 595 */       this.controlO = true;
/* 596 */       float f2; int k; if (arrayOfInt3 == arrayOfInt1) {
/* 597 */         f2 = 3216.46F;
/* 598 */         k = arrayOfInt1[1] - (this.ypos + this.padding[0] + this.plot.padding[0]);
/* 599 */         int m = this.OY2 - (this.ypos + this.padding[0] + this.plot.padding[0]);
/* 600 */         if (k < 0) k = 0;
/* 601 */         d = EllinghamDiagram.exp(-1485710.0F * k / m / 8.314472F / f2);
/*     */       } else {
/* 603 */         f2 = -1485.71F;
/* 604 */         k = arrayOfInt2[0] - (this.xpos + this.padding[3]);
/* 605 */         float f3 = k * 6.829F;
/* 606 */         d = Math.exp(1000.0F * f2 / 8.314472F / f3);
/*     */       }
/* 608 */       this.controlValueO = (this.controlValue = d); }
/*     */     int[] arrayOfInt4;
/* 610 */     float f1; if (this.control == 'H') {
/* 611 */       arrayOfInt1 = ClosestOnLine(j, this.HY1, this.HX2, this.HY2);
/* 612 */       arrayOfInt2 = ClosestOnLine(this.HX2, this.HY2, this.HX3, this.HY3);
/* 613 */       arrayOfInt3 = ClosestOnLine(this.HX3, this.HY3, j, this.HY4);
/* 614 */       arrayOfInt4 = arrayOfInt1[2] < arrayOfInt2[2] ? arrayOfInt1 : arrayOfInt2;
/* 615 */       arrayOfInt4 = arrayOfInt4[2] < arrayOfInt3[2] ? arrayOfInt4 : arrayOfInt3;
/* 616 */       this.hHX = arrayOfInt4[0];
/* 617 */       this.hHY = arrayOfInt4[1];
/* 618 */       this.controlH = true;
/* 619 */       f1 = (this.H - this.hHY) / (this.hHX - this.startx) * 371.0F / 840.0F;
/* 620 */       this.controlValueH = (this.controlValue = EllinghamDiagram.exp(1000.0F * (f1 - 0.1117F) / -16.628944F));
/*     */     }
/* 622 */     if (this.control == 'C') {
/* 623 */       arrayOfInt1 = ClosestOnLine(j, this.CY1, this.CX2, this.CY2);
/* 624 */       arrayOfInt2 = ClosestOnLine(this.CX2, this.CY2, this.CX3, this.CY3);
/* 625 */       arrayOfInt3 = ClosestOnLine(this.CX3, this.CY3, j, this.CY4);
/* 626 */       arrayOfInt4 = arrayOfInt1[2] < arrayOfInt2[2] ? arrayOfInt1 : arrayOfInt2;
/* 627 */       arrayOfInt4 = arrayOfInt4[2] < arrayOfInt3[2] ? arrayOfInt4 : arrayOfInt3;
/* 628 */       this.hCX = arrayOfInt4[0];
/* 629 */       this.hCY = arrayOfInt4[1];
/* 630 */       this.controlC = true;
/* 631 */       f1 = (this.C - this.hCY) / (this.hCX - this.startx) * 371.0F / 840.0F;
/* 632 */       this.controlValueC = (this.controlValue = EllinghamDiagram.exp(1000.0F * (f1 - 0.1736F) / -16.628944F));
/*     */     }
/* 634 */     if ((this.controlO) || (this.controlH) || (this.controlC)) {
/* 635 */       this.updated = true;
/* 636 */       findIntersects();
/*     */     }
/*     */   }
/*     */   
/*     */   public void findIntersects()
/*     */   {
/* 642 */     float[] arrayOfFloat1 = { this.hOX, this.hHX, this.hCX };
/* 643 */     float[] arrayOfFloat2 = { this.hOY, this.hHY, this.hCY };
/* 644 */     char[] arrayOfChar = { 'O', 'H', 'C' };
/* 645 */     boolean[] arrayOfBoolean = { this.controlO, this.controlH, this.controlC };
/* 646 */     this.intersectX.clear();
/* 647 */     this.intersectY.clear();
/* 648 */     for (int i = 0; i < 3; i++) {
/* 649 */       int j = arrayOfChar[i];
/* 650 */       if (arrayOfBoolean[i] != 0) {
/* 651 */         float f1 = -273.16F;
/* 652 */         float f2 = j == 79 ? 0.0F : j == 72 ? '︑' : '﷊';
/* 653 */         float f3 = 6.469F * (arrayOfFloat1[i] - this.padding[3] - this.plot.padding[3]);
/* 654 */         float f4 = 2.857F * (this.padding[0] + this.plot.padding[0] - arrayOfFloat2[i]);
/* 655 */         int k = this.plot.data.size();
/* 656 */         for (int m = 0; m < k; m++) {
/* 657 */           Plot.Data localData = (Plot.Data)this.plot.data.get(m);
/* 658 */           if (!localData.hidden)
/* 659 */             for (int n = 0; n < localData.Xdata.length - 1; n++) {
/* 660 */               float[] arrayOfFloat3 = Geometry.intersection(localData.Xdata[n], localData.Ydata[n], localData.Xdata[(n + 1)], localData.Ydata[(n + 1)], f1, f2, f3, f4);
/* 661 */               if (arrayOfFloat3 != null) {
/* 662 */                 this.intersectX.add(Float.valueOf(arrayOfFloat3[0]));
/* 663 */                 this.intersectY.add(Float.valueOf(arrayOfFloat3[1]));
/*     */               }
/*     */             }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void mouseReleased() {
/* 672 */     this.dragging = false;
/*     */   }
/*     */   
/*     */   public String getTags() {
/* 676 */     return this.plot.GetTags();
/*     */   }
/*     */   
/*     */   public void setHidden(int paramInt, boolean paramBoolean) {
/* 680 */     this.plot.SetHidden(paramInt, paramBoolean);
/* 681 */     findIntersects();
/* 682 */     this.updated = true;
/*     */   }
/*     */   
/*     */   public boolean getHidden(int paramInt) {
/* 686 */     if ((paramInt < 0) || (paramInt >= this.plot.data.size())) return false;
/* 687 */     return ((Plot.Data)this.plot.data.get(paramInt)).hidden;
/*     */   }
/*     */   
/*     */   public void mouseMoved()
/*     */   {
/* 692 */     int i = this.labelIndex;
/* 693 */     for (int j = 0; j < this.intersectX.size(); j++) {
/* 694 */       float f1 = ((Float)this.intersectX.get(j)).floatValue();
/* 695 */       float f2 = ((Float)this.intersectY.get(j)).floatValue();
/* 696 */       float f3 = f1 / 6.469F + this.padding[3] + this.plot.padding[3];
/* 697 */       float f4 = this.padding[0] + this.plot.padding[0] - f2 / 2.857F;
/* 698 */       if ((EllinghamDiagram.abs(this.this$0.mouseX - f3) <= 5.0F) && (EllinghamDiagram.abs(this.this$0.mouseY - f4) <= 5.0F)) {
/* 699 */         this.labelIndex = j;
/* 700 */         if (i != this.labelIndex)
/* 701 */           this.updated = true;
/* 702 */         return;
/*     */       }
/*     */     }
/*     */     
/* 706 */     if (i != this.labelIndex)
/* 707 */       this.updated = true;
/*     */   }
/*     */   
/* 710 */   public double getValueO() { return this.controlValueO; }
/* 711 */   public double getValueH() { return this.controlValueH; }
/* 712 */   public double getValueC() { return this.controlValueC; }
/*     */ }


/* Location:              /Users/AndrewKanarek/Desktop/MICHIGAN/KiefferWork/scripts-zibo/JavaApps/EllinghamDiagram/EllinghamDiagram.jar!/EllinghamDiagram$EllinghamPlot.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */