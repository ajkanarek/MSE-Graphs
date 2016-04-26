/*       */ package processing.core;
/*       */ 
/*       */ import java.applet.Applet;
/*       */ import java.applet.AppletContext;
/*       */ import java.awt.Color;
/*       */ import java.awt.Component;
/*       */ import java.awt.Cursor;
/*       */ import java.awt.Dimension;
/*       */ import java.awt.DisplayMode;
/*       */ import java.awt.FileDialog;
/*       */ import java.awt.Font;
/*       */ import java.awt.Frame;
/*       */ import java.awt.Graphics;
/*       */ import java.awt.GraphicsDevice;
/*       */ import java.awt.GraphicsEnvironment;
/*       */ import java.awt.Image;
/*       */ import java.awt.Insets;
/*       */ import java.awt.Label;
/*       */ import java.awt.MediaTracker;
/*       */ import java.awt.Point;
/*       */ import java.awt.Rectangle;
/*       */ import java.awt.SystemColor;
/*       */ import java.awt.Toolkit;
/*       */ import java.awt.event.ComponentAdapter;
/*       */ import java.awt.event.ComponentEvent;
/*       */ import java.awt.event.FocusEvent;
/*       */ import java.awt.event.FocusListener;
/*       */ import java.awt.event.KeyEvent;
/*       */ import java.awt.event.KeyListener;
/*       */ import java.awt.event.MouseAdapter;
/*       */ import java.awt.event.MouseEvent;
/*       */ import java.awt.event.MouseListener;
/*       */ import java.awt.event.MouseMotionListener;
/*       */ import java.awt.event.WindowAdapter;
/*       */ import java.awt.event.WindowEvent;
/*       */ import java.awt.image.BufferedImage;
/*       */ import java.awt.image.MemoryImageSource;
/*       */ import java.io.BufferedInputStream;
/*       */ import java.io.BufferedOutputStream;
/*       */ import java.io.BufferedReader;
/*       */ import java.io.ByteArrayOutputStream;
/*       */ import java.io.File;
/*       */ import java.io.FileInputStream;
/*       */ import java.io.FileNotFoundException;
/*       */ import java.io.FileOutputStream;
/*       */ import java.io.IOException;
/*       */ import java.io.InputStream;
/*       */ import java.io.InputStreamReader;
/*       */ import java.io.OutputStream;
/*       */ import java.io.OutputStreamWriter;
/*       */ import java.io.PrintStream;
/*       */ import java.io.PrintWriter;
/*       */ import java.io.UnsupportedEncodingException;
/*       */ import java.lang.reflect.Array;
/*       */ import java.lang.reflect.Constructor;
/*       */ import java.lang.reflect.InvocationTargetException;
/*       */ import java.lang.reflect.Method;
/*       */ import java.net.MalformedURLException;
/*       */ import java.net.URL;
/*       */ import java.net.URLConnection;
/*       */ import java.text.NumberFormat;
/*       */ import java.util.ArrayList;
/*       */ import java.util.Arrays;
/*       */ import java.util.Calendar;
/*       */ import java.util.HashMap;
/*       */ import java.util.Random;
/*       */ import java.util.StringTokenizer;
/*       */ import java.util.regex.Matcher;
/*       */ import java.util.regex.Pattern;
/*       */ import java.util.zip.GZIPInputStream;
/*       */ import java.util.zip.GZIPOutputStream;
/*       */ import javax.imageio.ImageIO;
/*       */ import javax.swing.JFileChooser;
/*       */ import javax.swing.SwingUtilities;
/*       */ import processing.xml.XMLElement;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ public class PApplet
/*       */   extends Applet
/*       */   implements PConstants, Runnable, MouseListener, MouseMotionListener, KeyListener, FocusListener
/*       */ {
/*   174 */   public static final String javaVersionName = System.getProperty("java.version");
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*   188 */   public static final float javaVersion = new Float(javaVersionName.substring(0, 3)).floatValue();
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static int platform;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   static
/*       */   {
/*   208 */     String osname = System.getProperty("os.name");
/*       */     
/*   210 */     if (osname.indexOf("Mac") != -1) {
/*   211 */       platform = 2;
/*       */     }
/*   213 */     else if (osname.indexOf("Windows") != -1) {
/*   214 */       platform = 1;
/*       */     }
/*   216 */     else if (osname.equals("Linux")) {
/*   217 */       platform = 3;
/*       */     }
/*       */     else {
/*   220 */       platform = 0;
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*   241 */   public static boolean useQuartz = true;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*   249 */   public static final int MENU_SHORTCUT = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public PGraphics g;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public Frame frame;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public int screenWidth;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public int screenHeight;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   /**
/*       */    * @deprecated
/*       */    */
/*   285 */   public Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public PGraphics recorder;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public String[] args;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public String sketchPath;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   static final boolean THREAD_DEBUG = false;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final int DEFAULT_WIDTH = 100;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final int DEFAULT_HEIGHT = 100;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final int MIN_WINDOW_WIDTH = 128;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final int MIN_WINDOW_HEIGHT = 128;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public boolean defaultSize;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   volatile boolean resizeRequest;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   volatile int resizeWidth;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   volatile int resizeHeight;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public int[] pixels;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public int width;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public int height;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public int mouseX;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public int mouseY;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public int pmouseX;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public int pmouseY;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected int dmouseX;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected int dmouseY;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected int emouseX;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected int emouseY;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public boolean firstMouse;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public int mouseButton;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public boolean mousePressed;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public MouseEvent mouseEvent;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public char key;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public int keyCode;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public boolean keyPressed;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public KeyEvent keyEvent;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*   527 */   public boolean focused = false;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*   536 */   public boolean online = false;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   long millisOffset;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*   554 */   public float frameRate = 10.0F;
/*       */   
/*   556 */   protected long frameRateLastNanos = 0L;
/*       */   
/*       */ 
/*   559 */   protected float frameRateTarget = 60.0F;
/*   560 */   protected long frameRatePeriod = 16666666L;
/*       */   
/*       */ 
/*       */ 
/*       */   protected boolean looping;
/*       */   
/*       */ 
/*       */ 
/*       */   protected boolean redraw;
/*       */   
/*       */ 
/*       */ 
/*       */   public int frameCount;
/*       */   
/*       */ 
/*       */ 
/*       */   public boolean finished;
/*       */   
/*       */ 
/*       */ 
/*       */   protected boolean exitCalled;
/*       */   
/*       */ 
/*       */ 
/*       */   Thread thread;
/*       */   
/*       */ 
/*       */ 
/*       */   protected RegisteredMethods sizeMethods;
/*       */   
/*       */ 
/*       */ 
/*       */   protected RegisteredMethods preMethods;
/*       */   
/*       */ 
/*       */ 
/*       */   protected RegisteredMethods drawMethods;
/*       */   
/*       */ 
/*       */ 
/*       */   protected RegisteredMethods postMethods;
/*       */   
/*       */ 
/*       */ 
/*       */   protected RegisteredMethods mouseEventMethods;
/*       */   
/*       */ 
/*       */ 
/*       */   protected RegisteredMethods keyEventMethods;
/*       */   
/*       */ 
/*       */ 
/*       */   protected RegisteredMethods disposeMethods;
/*       */   
/*       */ 
/*       */ 
/*       */   public static final String ARGS_EDITOR_LOCATION = "--editor-location";
/*       */   
/*       */ 
/*       */   public static final String ARGS_EXTERNAL = "--external";
/*       */   
/*       */ 
/*       */   public static final String ARGS_LOCATION = "--location";
/*       */   
/*       */ 
/*       */   public static final String ARGS_DISPLAY = "--display";
/*       */   
/*       */ 
/*       */   public static final String ARGS_BGCOLOR = "--bgcolor";
/*       */   
/*       */ 
/*       */   public static final String ARGS_PRESENT = "--present";
/*       */   
/*       */ 
/*       */   public static final String ARGS_EXCLUSIVE = "--exclusive";
/*       */   
/*       */ 
/*       */   public static final String ARGS_STOP_COLOR = "--stop-color";
/*       */   
/*       */ 
/*       */   public static final String ARGS_HIDE_STOP = "--hide-stop";
/*       */   
/*       */ 
/*       */   public static final String ARGS_SKETCH_FOLDER = "--sketch-path";
/*       */   
/*       */ 
/*       */   public static final String EXTERNAL_STOP = "__STOP__";
/*       */   
/*       */ 
/*       */   public static final String EXTERNAL_MOVE = "__MOVE__";
/*       */   
/*       */ 
/*   652 */   boolean external = false;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   static final String ERROR_MIN_MAX = "Cannot use min() or max() on an empty array.";
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void init()
/*       */   {
/*   669 */     Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
/*   670 */     this.screenWidth = screen.width;
/*   671 */     this.screenHeight = screen.height;
/*       */     
/*       */ 
/*   674 */     setFocusTraversalKeysEnabled(false);
/*       */     
/*   676 */     this.millisOffset = System.currentTimeMillis();
/*       */     
/*   678 */     this.finished = false;
/*       */     
/*       */ 
/*   681 */     this.looping = true;
/*   682 */     this.redraw = true;
/*   683 */     this.firstMouse = true;
/*       */     
/*       */ 
/*   686 */     this.sizeMethods = new RegisteredMethods();
/*   687 */     this.preMethods = new RegisteredMethods();
/*   688 */     this.drawMethods = new RegisteredMethods();
/*   689 */     this.postMethods = new RegisteredMethods();
/*   690 */     this.mouseEventMethods = new RegisteredMethods();
/*   691 */     this.keyEventMethods = new RegisteredMethods();
/*   692 */     this.disposeMethods = new RegisteredMethods();
/*       */     try
/*       */     {
/*   695 */       getAppletContext();
/*   696 */       this.online = true;
/*       */     } catch (NullPointerException e) {
/*   698 */       this.online = false;
/*       */     }
/*       */     try
/*       */     {
/*   702 */       if (this.sketchPath == null) {
/*   703 */         this.sketchPath = System.getProperty("user.dir");
/*       */       }
/*       */     }
/*       */     catch (Exception localException) {}
/*   707 */     Dimension size = getSize();
/*   708 */     if ((size.width != 0) && (size.height != 0))
/*       */     {
/*       */ 
/*       */ 
/*       */ 
/*   713 */       this.g = makeGraphics(size.width, size.height, getSketchRenderer(), null, true);
/*       */ 
/*       */     }
/*       */     else
/*       */     {
/*       */ 
/*   719 */       this.defaultSize = true;
/*   720 */       int w = getSketchWidth();
/*   721 */       int h = getSketchHeight();
/*   722 */       this.g = makeGraphics(w, h, getSketchRenderer(), null, true);
/*       */       
/*   724 */       setSize(w, h);
/*   725 */       setPreferredSize(new Dimension(w, h));
/*       */     }
/*   727 */     this.width = this.g.width;
/*   728 */     this.height = this.g.height;
/*       */     
/*   730 */     addListeners();
/*       */     
/*       */ 
/*       */ 
/*   734 */     start();
/*       */   }
/*       */   
/*       */   public int getSketchWidth()
/*       */   {
/*   739 */     return 100;
/*       */   }
/*       */   
/*       */   public int getSketchHeight()
/*       */   {
/*   744 */     return 100;
/*       */   }
/*       */   
/*       */   public String getSketchRenderer()
/*       */   {
/*   749 */     return "processing.core.PGraphicsJava2D";
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void start()
/*       */   {
/*   765 */     this.finished = false;
/*       */     
/*   767 */     if (this.thread != null) return;
/*   768 */     this.thread = new Thread(this, "Animation Thread");
/*   769 */     this.thread.start();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void stop()
/*       */   {
/*   783 */     this.finished = true;
/*       */     
/*       */ 
/*   786 */     if (this.thread == null) return;
/*   787 */     this.thread = null;
/*       */     
/*       */ 
/*   790 */     if (this.g != null) { this.g.dispose();
/*       */     }
/*       */     
/*       */ 
/*   794 */     this.disposeMethods.handle();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void destroy()
/*       */   {
/*   813 */     stop();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public static class RendererChangeException
/*       */     extends RuntimeException
/*       */   {}
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public class RegisteredMethods
/*       */   {
/*       */     int count;
/*       */     
/*       */ 
/*       */     Object[] objects;
/*       */     
/*       */ 
/*       */     Method[] methods;
/*       */     
/*       */ 
/*       */ 
/*       */     public RegisteredMethods() {}
/*       */     
/*       */ 
/*       */ 
/*       */     public void handle()
/*       */     {
/*   844 */       handle(new Object[0]);
/*       */     }
/*       */     
/*       */     public void handle(Object[] oargs) {
/*   848 */       for (int i = 0; i < this.count; i++) {
/*       */         try
/*       */         {
/*   851 */           this.methods[i].invoke(this.objects[i], oargs);
/*       */         } catch (Exception e) {
/*   853 */           if ((e instanceof InvocationTargetException)) {
/*   854 */             InvocationTargetException ite = (InvocationTargetException)e;
/*   855 */             ite.getTargetException().printStackTrace();
/*       */           } else {
/*   857 */             e.printStackTrace();
/*       */           }
/*       */         }
/*       */       }
/*       */     }
/*       */     
/*       */     public void add(Object object, Method method) {
/*   864 */       if (this.objects == null) {
/*   865 */         this.objects = new Object[5];
/*   866 */         this.methods = new Method[5];
/*       */       }
/*   868 */       if (this.count == this.objects.length) {
/*   869 */         this.objects = ((Object[])PApplet.expand(this.objects));
/*   870 */         this.methods = ((Method[])PApplet.expand(this.methods));
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*   878 */       this.objects[this.count] = object;
/*   879 */       this.methods[this.count] = method;
/*   880 */       this.count += 1;
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     public void remove(Object object, Method method)
/*       */     {
/*   890 */       int index = findIndex(object, method);
/*   891 */       if (index != -1)
/*       */       {
/*   893 */         this.count -= 1;
/*   894 */         for (int i = index; i < this.count; i++) {
/*   895 */           this.objects[i] = this.objects[(i + 1)];
/*   896 */           this.methods[i] = this.methods[(i + 1)];
/*       */         }
/*       */         
/*   899 */         this.objects[this.count] = null;
/*   900 */         this.methods[this.count] = null;
/*       */       }
/*       */     }
/*       */     
/*       */     protected int findIndex(Object object, Method method) {
/*   905 */       for (int i = 0; i < this.count; i++) {
/*   906 */         if ((this.objects[i] == object) && (this.methods[i].equals(method)))
/*       */         {
/*       */ 
/*       */ 
/*       */ 
/*   911 */           return i;
/*       */         }
/*       */       }
/*   914 */       return -1;
/*       */     }
/*       */   }
/*       */   
/*       */   public void registerSize(Object o)
/*       */   {
/*   920 */     Class[] methodArgs = { Integer.TYPE, Integer.TYPE };
/*   921 */     registerWithArgs(this.sizeMethods, "size", o, methodArgs);
/*       */   }
/*       */   
/*       */   public void registerPre(Object o) {
/*   925 */     registerNoArgs(this.preMethods, "pre", o);
/*       */   }
/*       */   
/*       */   public void registerDraw(Object o) {
/*   929 */     registerNoArgs(this.drawMethods, "draw", o);
/*       */   }
/*       */   
/*       */   public void registerPost(Object o) {
/*   933 */     registerNoArgs(this.postMethods, "post", o);
/*       */   }
/*       */   
/*       */   public void registerMouseEvent(Object o) {
/*   937 */     Class[] methodArgs = { MouseEvent.class };
/*   938 */     registerWithArgs(this.mouseEventMethods, "mouseEvent", o, methodArgs);
/*       */   }
/*       */   
/*       */   public void registerKeyEvent(Object o)
/*       */   {
/*   943 */     Class[] methodArgs = { KeyEvent.class };
/*   944 */     registerWithArgs(this.keyEventMethods, "keyEvent", o, methodArgs);
/*       */   }
/*       */   
/*       */   public void registerDispose(Object o) {
/*   948 */     registerNoArgs(this.disposeMethods, "dispose", o);
/*       */   }
/*       */   
/*       */ 
/*       */   protected void registerNoArgs(RegisteredMethods meth, String name, Object o)
/*       */   {
/*   954 */     Class<?> c = o.getClass();
/*       */     try {
/*   956 */       Method method = c.getMethod(name, new Class[0]);
/*   957 */       meth.add(o, method);
/*       */     }
/*       */     catch (NoSuchMethodException nsme) {
/*   960 */       die("There is no public " + name + "() method in the class " + 
/*   961 */         o.getClass().getName());
/*       */     }
/*       */     catch (Exception e) {
/*   964 */       die("Could not register " + name + " + () for " + o, e);
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */   protected void registerWithArgs(RegisteredMethods meth, String name, Object o, Class<?>[] cargs)
/*       */   {
/*   971 */     Class<?> c = o.getClass();
/*       */     try {
/*   973 */       Method method = c.getMethod(name, cargs);
/*   974 */       meth.add(o, method);
/*       */     }
/*       */     catch (NoSuchMethodException nsme) {
/*   977 */       die("There is no public " + name + "() method in the class " + 
/*   978 */         o.getClass().getName());
/*       */     }
/*       */     catch (Exception e) {
/*   981 */       die("Could not register " + name + " + () for " + o, e);
/*       */     }
/*       */   }
/*       */   
/*       */   public void unregisterSize(Object o)
/*       */   {
/*   987 */     Class[] methodArgs = { Integer.TYPE, Integer.TYPE };
/*   988 */     unregisterWithArgs(this.sizeMethods, "size", o, methodArgs);
/*       */   }
/*       */   
/*       */   public void unregisterPre(Object o) {
/*   992 */     unregisterNoArgs(this.preMethods, "pre", o);
/*       */   }
/*       */   
/*       */   public void unregisterDraw(Object o) {
/*   996 */     unregisterNoArgs(this.drawMethods, "draw", o);
/*       */   }
/*       */   
/*       */   public void unregisterPost(Object o) {
/*  1000 */     unregisterNoArgs(this.postMethods, "post", o);
/*       */   }
/*       */   
/*       */   public void unregisterMouseEvent(Object o) {
/*  1004 */     Class[] methodArgs = { MouseEvent.class };
/*  1005 */     unregisterWithArgs(this.mouseEventMethods, "mouseEvent", o, methodArgs);
/*       */   }
/*       */   
/*       */   public void unregisterKeyEvent(Object o) {
/*  1009 */     Class[] methodArgs = { KeyEvent.class };
/*  1010 */     unregisterWithArgs(this.keyEventMethods, "keyEvent", o, methodArgs);
/*       */   }
/*       */   
/*       */   public void unregisterDispose(Object o) {
/*  1014 */     unregisterNoArgs(this.disposeMethods, "dispose", o);
/*       */   }
/*       */   
/*       */ 
/*       */   protected void unregisterNoArgs(RegisteredMethods meth, String name, Object o)
/*       */   {
/*  1020 */     Class<?> c = o.getClass();
/*       */     try {
/*  1022 */       Method method = c.getMethod(name, new Class[0]);
/*  1023 */       meth.remove(o, method);
/*       */     } catch (Exception e) {
/*  1025 */       die("Could not unregister " + name + "() for " + o, e);
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */   protected void unregisterWithArgs(RegisteredMethods meth, String name, Object o, Class<?>[] cargs)
/*       */   {
/*  1032 */     Class<?> c = o.getClass();
/*       */     try {
/*  1034 */       Method method = c.getMethod(name, cargs);
/*  1035 */       meth.remove(o, method);
/*       */     } catch (Exception e) {
/*  1037 */       die("Could not unregister " + name + "() for " + o, e);
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void setup() {}
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void draw()
/*       */   {
/*  1052 */     this.finished = true;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected void resizeRenderer(int iwidth, int iheight)
/*       */   {
/*  1061 */     if ((this.width != iwidth) || (this.height != iheight))
/*       */     {
/*  1063 */       this.g.setSize(iwidth, iheight);
/*  1064 */       this.width = iwidth;
/*  1065 */       this.height = iheight;
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void size(int iwidth, int iheight)
/*       */   {
/*  1101 */     size(iwidth, iheight, "processing.core.PGraphicsJava2D", null);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void size(int iwidth, int iheight, String irenderer)
/*       */   {
/*  1109 */     size(iwidth, iheight, irenderer, null);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void size(final int iwidth, final int iheight, String irenderer, String ipath)
/*       */   {
/*  1129 */     SwingUtilities.invokeLater(new Runnable()
/*       */     {
/*       */       public void run() {
/*  1132 */         PApplet.this.setPreferredSize(new Dimension(iwidth, iheight));
/*  1133 */         PApplet.this.setSize(iwidth, iheight);
/*       */       }
/*       */     });
/*       */     
/*       */ 
/*  1138 */     if (ipath != null) { ipath = savePath(ipath);
/*       */     }
/*  1140 */     String currentRenderer = this.g.getClass().getName();
/*  1141 */     if (currentRenderer.equals(irenderer))
/*       */     {
/*  1143 */       resizeRenderer(iwidth, iheight);
/*       */ 
/*       */     }
/*       */     else
/*       */     {
/*       */ 
/*  1149 */       this.g = makeGraphics(iwidth, iheight, irenderer, ipath, true);
/*  1150 */       this.width = iwidth;
/*  1151 */       this.height = iheight;
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  1157 */       this.defaultSize = false;
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  1163 */       throw new RendererChangeException();
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public PGraphics createGraphics(int iwidth, int iheight, String irenderer)
/*       */   {
/*  1233 */     PGraphics pg = makeGraphics(iwidth, iheight, irenderer, null, false);
/*       */     
/*  1235 */     return pg;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public PGraphics createGraphics(int iwidth, int iheight, String irenderer, String ipath)
/*       */   {
/*  1246 */     if (ipath != null) {
/*  1247 */       ipath = savePath(ipath);
/*       */     }
/*  1249 */     PGraphics pg = makeGraphics(iwidth, iheight, irenderer, ipath, false);
/*  1250 */     pg.parent = this;
/*  1251 */     return pg;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected PGraphics makeGraphics(int iwidth, int iheight, String irenderer, String ipath, boolean iprimary)
/*       */   {
/*  1265 */     if ((irenderer.equals("processing.opengl.PGraphicsOpenGL")) && 
/*  1266 */       (platform == 1)) {
/*  1267 */       String s = System.getProperty("java.version");
/*  1268 */       if ((s != null) && 
/*  1269 */         (s.equals("1.5.0_10"))) {
/*  1270 */         System.err.println("OpenGL support is broken with Java 1.5.0_10");
/*  1271 */         System.err.println("See http://dev.processing.org/bugs/show_bug.cgi?id=513 for more info.");
/*       */         
/*  1273 */         throw new RuntimeException("Please update your Java installation (see bug #513)");
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  1285 */     String openglError = 
/*  1286 */       "Before using OpenGL, first select Import Library > opengl from the Sketch menu.";
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     try
/*       */     {
/*  1317 */       Class<?> rendererClass = 
/*  1318 */         Thread.currentThread().getContextClassLoader().loadClass(irenderer);
/*       */       
/*       */ 
/*       */ 
/*  1322 */       Constructor<?> constructor = rendererClass.getConstructor(new Class[0]);
/*  1323 */       PGraphics pg = (PGraphics)constructor.newInstance(new Object[0]);
/*       */       
/*  1325 */       pg.setParent(this);
/*  1326 */       pg.setPrimary(iprimary);
/*  1327 */       if (ipath != null) pg.setPath(ipath);
/*  1328 */       pg.setSize(iwidth, iheight);
/*       */       
/*       */ 
/*  1331 */       return pg;
/*       */     }
/*       */     catch (InvocationTargetException ite) {
/*  1334 */       String msg = ite.getTargetException().getMessage();
/*  1335 */       if ((msg != null) && 
/*  1336 */         (msg.indexOf("no jogl in java.library.path") != -1)) {
/*  1337 */         throw new RuntimeException(openglError + 
/*  1338 */           " (The native library is missing.)");
/*       */       }
/*       */       
/*  1341 */       ite.getTargetException().printStackTrace();
/*  1342 */       Throwable target = ite.getTargetException();
/*  1343 */       if (platform == 2) { target.printStackTrace(System.out);
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*  1348 */       throw new RuntimeException(target.getMessage());
/*       */     }
/*       */     catch (ClassNotFoundException cnfe)
/*       */     {
/*  1352 */       if (cnfe.getMessage().indexOf("processing.opengl.PGraphicsGL") != -1) {
/*  1353 */         throw new RuntimeException(openglError + 
/*  1354 */           " (The library .jar file is missing.)");
/*       */       }
/*  1356 */       throw new RuntimeException("You need to use \"Import Library\" to add " + 
/*  1357 */         irenderer + " to your sketch.");
/*       */ 
/*       */     }
/*       */     catch (Exception e)
/*       */     {
/*  1362 */       if (((e instanceof IllegalArgumentException)) || 
/*  1363 */         ((e instanceof NoSuchMethodException)) || 
/*  1364 */         ((e instanceof IllegalAccessException))) {
/*  1365 */         e.printStackTrace();
/*       */         
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  1373 */         String msg = irenderer + " needs to be updated " + 
/*  1374 */           "for the current release of Processing.";
/*  1375 */         throw new RuntimeException(msg);
/*       */       }
/*       */       
/*  1378 */       if (platform == 2) e.printStackTrace(System.out);
/*  1379 */       throw new RuntimeException(e.getMessage());
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public PImage createImage(int wide, int high, int format)
/*       */   {
/*  1403 */     PImage image = new PImage(wide, high, format);
/*  1404 */     image.parent = this;
/*  1405 */     return image;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void update(Graphics screen)
/*       */   {
/*  1413 */     paint(screen);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void paint(Graphics screen)
/*       */   {
/*  1421 */     if (this.frameCount == 0)
/*       */     {
/*       */ 
/*       */ 
/*  1425 */       return;
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  1440 */     if ((this.g != null) && (this.g.image != null))
/*       */     {
/*  1442 */       screen.drawImage(this.g.image, 0, 0, null);
/*       */     }
/*       */   }
/*       */   
/*       */   protected void paint()
/*       */   {
/*       */     try
/*       */     {
/*  1450 */       Graphics screen = getGraphics();
/*  1451 */       if (screen != null) {
/*  1452 */         if ((this.g != null) && (this.g.image != null)) {
/*  1453 */           screen.drawImage(this.g.image, 0, 0, null);
/*       */         }
/*  1455 */         Toolkit.getDefaultToolkit().sync();
/*       */       }
/*       */     }
/*       */     catch (Exception e) {
/*  1459 */       e.printStackTrace();
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void run()
/*       */   {
/*  1478 */     long beforeTime = System.nanoTime();
/*  1479 */     long overSleepTime = 0L;
/*       */     
/*  1481 */     int noDelays = 0;
/*       */     
/*       */ 
/*  1484 */     int NO_DELAYS_PER_YIELD = 15;
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  1494 */     while ((Thread.currentThread() == this.thread) && (!this.finished))
/*       */     {
/*       */ 
/*  1497 */       if (this.resizeRequest) {
/*  1498 */         resizeRenderer(this.resizeWidth, this.resizeHeight);
/*  1499 */         this.resizeRequest = false;
/*       */       }
/*       */       
/*       */ 
/*  1503 */       handleDraw();
/*       */       
/*  1505 */       if (this.frameCount == 1)
/*       */       {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  1516 */         requestFocusInWindow();
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  1524 */       long afterTime = System.nanoTime();
/*  1525 */       long timeDiff = afterTime - beforeTime;
/*       */       
/*  1527 */       long sleepTime = this.frameRatePeriod - timeDiff - overSleepTime;
/*       */       
/*  1529 */       if (sleepTime > 0L)
/*       */       {
/*       */         try {
/*  1532 */           Thread.sleep(sleepTime / 1000000L, (int)(sleepTime % 1000000L));
/*  1533 */           noDelays = 0;
/*       */         }
/*       */         catch (InterruptedException localInterruptedException) {}
/*  1536 */         overSleepTime = System.nanoTime() - afterTime - sleepTime;
/*       */ 
/*       */       }
/*       */       else
/*       */       {
/*  1541 */         overSleepTime = 0L;
/*       */         
/*  1543 */         if (noDelays > 15) {
/*  1544 */           Thread.yield();
/*  1545 */           noDelays = 0;
/*       */         }
/*       */       }
/*       */       
/*  1549 */       beforeTime = System.nanoTime();
/*       */     }
/*       */     
/*  1552 */     stop();
/*       */     
/*       */ 
/*       */ 
/*  1556 */     if (this.exitCalled) {
/*  1557 */       exit2();
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */   public void handleDraw()
/*       */   {
/*  1564 */     if ((this.g != null) && ((this.looping) || (this.redraw))) {
/*  1565 */       if (!this.g.canDraw())
/*       */       {
/*       */ 
/*  1568 */         return;
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*  1573 */       this.g.beginDraw();
/*  1574 */       if (this.recorder != null) {
/*  1575 */         this.recorder.beginDraw();
/*       */       }
/*       */       
/*  1578 */       long now = System.nanoTime();
/*       */       
/*  1580 */       if (this.frameCount == 0)
/*       */       {
/*       */         try {
/*  1583 */           setup();
/*       */ 
/*       */         }
/*       */         catch (RendererChangeException e)
/*       */         {
/*  1588 */           return;
/*       */         }
/*  1590 */         this.defaultSize = false;
/*       */       }
/*       */       else
/*       */       {
/*  1594 */         double rate = 1000000.0D / ((now - this.frameRateLastNanos) / 1000000.0D);
/*  1595 */         float instantaneousRate = (float)rate / 1000.0F;
/*  1596 */         this.frameRate = (this.frameRate * 0.9F + instantaneousRate * 0.1F);
/*       */         
/*  1598 */         this.preMethods.handle();
/*       */         
/*       */ 
/*       */ 
/*  1602 */         this.pmouseX = this.dmouseX;
/*  1603 */         this.pmouseY = this.dmouseY;
/*       */         
/*       */ 
/*  1606 */         draw();
/*       */         
/*       */ 
/*       */ 
/*  1610 */         this.dmouseX = this.mouseX;
/*  1611 */         this.dmouseY = this.mouseY;
/*       */         
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  1617 */         dequeueMouseEvents();
/*  1618 */         dequeueKeyEvents();
/*       */         
/*  1620 */         this.drawMethods.handle();
/*       */         
/*  1622 */         this.redraw = false;
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*  1627 */       this.g.endDraw();
/*  1628 */       if (this.recorder != null) {
/*  1629 */         this.recorder.endDraw();
/*       */       }
/*       */       
/*  1632 */       this.frameRateLastNanos = now;
/*  1633 */       this.frameCount += 1;
/*       */       
/*       */ 
/*  1636 */       paint();
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*  1641 */       this.postMethods.handle();
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public synchronized void redraw()
/*       */   {
/*  1651 */     if (!this.looping) {
/*  1652 */       this.redraw = true;
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public synchronized void loop()
/*       */   {
/*  1669 */     if (!this.looping) {
/*  1670 */       this.looping = true;
/*       */     }
/*       */   }
/*       */   
/*       */   public synchronized void noLoop()
/*       */   {
/*  1676 */     if (this.looping) {
/*  1677 */       this.looping = false;
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void addListeners()
/*       */   {
/*  1686 */     addMouseListener(this);
/*  1687 */     addMouseMotionListener(this);
/*  1688 */     addKeyListener(this);
/*  1689 */     addFocusListener(this);
/*       */     
/*  1691 */     addComponentListener(new ComponentAdapter() {
/*       */       public void componentResized(ComponentEvent e) {
/*  1693 */         Component c = e.getComponent();
/*       */         
/*  1695 */         Rectangle bounds = c.getBounds();
/*  1696 */         PApplet.this.resizeRequest = true;
/*  1697 */         PApplet.this.resizeWidth = bounds.width;
/*  1698 */         PApplet.this.resizeHeight = bounds.height;
/*       */       }
/*       */     });
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  1707 */   MouseEvent[] mouseEventQueue = new MouseEvent[10];
/*       */   int mouseEventCount;
/*       */   
/*       */   protected void enqueueMouseEvent(MouseEvent e) {
/*  1711 */     synchronized (this.mouseEventQueue) {
/*  1712 */       if (this.mouseEventCount == this.mouseEventQueue.length) {
/*  1713 */         MouseEvent[] temp = new MouseEvent[this.mouseEventCount << 1];
/*  1714 */         System.arraycopy(this.mouseEventQueue, 0, temp, 0, this.mouseEventCount);
/*  1715 */         this.mouseEventQueue = temp;
/*       */       }
/*  1717 */       this.mouseEventQueue[(this.mouseEventCount++)] = e;
/*       */     }
/*       */   }
/*       */   
/*       */   protected void dequeueMouseEvents() {
/*  1722 */     synchronized (this.mouseEventQueue) {
/*  1723 */       for (int i = 0; i < this.mouseEventCount; i++) {
/*  1724 */         this.mouseEvent = this.mouseEventQueue[i];
/*  1725 */         handleMouseEvent(this.mouseEvent);
/*       */       }
/*  1727 */       this.mouseEventCount = 0;
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected void handleMouseEvent(MouseEvent event)
/*       */   {
/*  1740 */     int id = event.getID();
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  1746 */     if ((id == 506) || 
/*  1747 */       (id == 503)) {
/*  1748 */       this.pmouseX = this.emouseX;
/*  1749 */       this.pmouseY = this.emouseY;
/*  1750 */       this.mouseX = event.getX();
/*  1751 */       this.mouseY = event.getY();
/*       */     }
/*       */     
/*  1754 */     this.mouseEvent = event;
/*       */     
/*  1756 */     int modifiers = event.getModifiers();
/*  1757 */     if ((modifiers & 0x10) != 0) {
/*  1758 */       this.mouseButton = 37;
/*  1759 */     } else if ((modifiers & 0x8) != 0) {
/*  1760 */       this.mouseButton = 3;
/*  1761 */     } else if ((modifiers & 0x4) != 0) {
/*  1762 */       this.mouseButton = 39;
/*       */     }
/*       */     
/*  1765 */     if ((platform == 2) && 
/*  1766 */       (this.mouseEvent.isPopupTrigger())) {
/*  1767 */       this.mouseButton = 39;
/*       */     }
/*       */     
/*       */ 
/*  1771 */     this.mouseEventMethods.handle(new Object[] { event });
/*       */     
/*       */ 
/*       */ 
/*  1775 */     if (this.firstMouse) {
/*  1776 */       this.pmouseX = this.mouseX;
/*  1777 */       this.pmouseY = this.mouseY;
/*  1778 */       this.dmouseX = this.mouseX;
/*  1779 */       this.dmouseY = this.mouseY;
/*  1780 */       this.firstMouse = false;
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*  1785 */     switch (id) {
/*       */     case 501: 
/*  1787 */       this.mousePressed = true;
/*  1788 */       mousePressed();
/*  1789 */       break;
/*       */     case 502: 
/*  1791 */       this.mousePressed = false;
/*  1792 */       mouseReleased();
/*  1793 */       break;
/*       */     case 500: 
/*  1795 */       mouseClicked();
/*  1796 */       break;
/*       */     case 506: 
/*  1798 */       mouseDragged();
/*  1799 */       break;
/*       */     case 503: 
/*  1801 */       mouseMoved();
/*       */     }
/*       */     
/*       */     
/*  1805 */     if ((id == 506) || 
/*  1806 */       (id == 503)) {
/*  1807 */       this.emouseX = this.mouseX;
/*  1808 */       this.emouseY = this.mouseY;
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected void checkMouseEvent(MouseEvent event)
/*       */   {
/*  1819 */     if (this.looping) {
/*  1820 */       enqueueMouseEvent(event);
/*       */     } else {
/*  1822 */       handleMouseEvent(event);
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void mousePressed(MouseEvent e)
/*       */   {
/*  1833 */     checkMouseEvent(e);
/*       */   }
/*       */   
/*       */   public void mouseReleased(MouseEvent e) {
/*  1837 */     checkMouseEvent(e);
/*       */   }
/*       */   
/*       */   public void mouseClicked(MouseEvent e) {
/*  1841 */     checkMouseEvent(e);
/*       */   }
/*       */   
/*       */   public void mouseEntered(MouseEvent e) {
/*  1845 */     checkMouseEvent(e);
/*       */   }
/*       */   
/*       */   public void mouseExited(MouseEvent e) {
/*  1849 */     checkMouseEvent(e);
/*       */   }
/*       */   
/*       */   public void mouseDragged(MouseEvent e) {
/*  1853 */     checkMouseEvent(e);
/*       */   }
/*       */   
/*       */   public void mouseMoved(MouseEvent e) {
/*  1857 */     checkMouseEvent(e);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void mousePressed() {}
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void mouseReleased() {}
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void mouseClicked() {}
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void mouseDragged() {}
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  1938 */   KeyEvent[] keyEventQueue = new KeyEvent[10];
/*       */   
/*       */   public void mouseMoved() {}
/*       */   
/*  1942 */   protected void enqueueKeyEvent(KeyEvent e) { synchronized (this.keyEventQueue) {
/*  1943 */       if (this.keyEventCount == this.keyEventQueue.length) {
/*  1944 */         KeyEvent[] temp = new KeyEvent[this.keyEventCount << 1];
/*  1945 */         System.arraycopy(this.keyEventQueue, 0, temp, 0, this.keyEventCount);
/*  1946 */         this.keyEventQueue = temp;
/*       */       }
/*  1948 */       this.keyEventQueue[(this.keyEventCount++)] = e;
/*       */     }
/*       */   }
/*       */   
/*       */   protected void dequeueKeyEvents() {
/*  1953 */     synchronized (this.keyEventQueue) {
/*  1954 */       for (int i = 0; i < this.keyEventCount; i++) {
/*  1955 */         this.keyEvent = this.keyEventQueue[i];
/*  1956 */         handleKeyEvent(this.keyEvent);
/*       */       }
/*  1958 */       this.keyEventCount = 0;
/*       */     }
/*       */   }
/*       */   
/*       */   protected void handleKeyEvent(KeyEvent event)
/*       */   {
/*  1964 */     this.keyEvent = event;
/*  1965 */     this.key = event.getKeyChar();
/*  1966 */     this.keyCode = event.getKeyCode();
/*       */     
/*  1968 */     this.keyEventMethods.handle(new Object[] { event });
/*       */     
/*  1970 */     switch (event.getID()) {
/*       */     case 401: 
/*  1972 */       this.keyPressed = true;
/*  1973 */       keyPressed();
/*  1974 */       break;
/*       */     case 402: 
/*  1976 */       this.keyPressed = false;
/*  1977 */       keyReleased();
/*  1978 */       break;
/*       */     case 400: 
/*  1980 */       keyTyped();
/*       */     }
/*       */     
/*       */     
/*       */ 
/*       */ 
/*  1986 */     if (event.getID() == 401) {
/*  1987 */       if (this.key == '\033') {
/*  1988 */         exit();
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*  1994 */       if ((this.external) && 
/*  1995 */         (event.getModifiers() == MENU_SHORTCUT) && 
/*  1996 */         (event.getKeyCode() == 87)) {
/*  1997 */         exit();
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */   protected void checkKeyEvent(KeyEvent event)
/*       */   {
/*  2004 */     if (this.looping) {
/*  2005 */       enqueueKeyEvent(event);
/*       */     } else {
/*  2007 */       handleKeyEvent(event);
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  2019 */   public void keyPressed(KeyEvent e) { checkKeyEvent(e); }
/*  2020 */   public void keyReleased(KeyEvent e) { checkKeyEvent(e); }
/*  2021 */   public void keyTyped(KeyEvent e) { checkKeyEvent(e); }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   int keyEventCount;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void keyPressed() {}
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void keyReleased() {}
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void keyTyped() {}
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void focusGained() {}
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void focusGained(FocusEvent e)
/*       */   {
/*  2117 */     this.focused = true;
/*  2118 */     focusGained();
/*       */   }
/*       */   
/*       */   public void focusLost() {}
/*       */   
/*       */   public void focusLost(FocusEvent e)
/*       */   {
/*  2125 */     this.focused = false;
/*  2126 */     focusLost();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public int millis()
/*       */   {
/*  2153 */     return (int)(System.currentTimeMillis() - this.millisOffset);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static int second()
/*       */   {
/*  2167 */     return Calendar.getInstance().get(13);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static int minute()
/*       */   {
/*  2183 */     return Calendar.getInstance().get(12);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static int hour()
/*       */   {
/*  2205 */     return Calendar.getInstance().get(11);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static int day()
/*       */   {
/*  2225 */     return Calendar.getInstance().get(5);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static int month()
/*       */   {
/*  2241 */     return Calendar.getInstance().get(2) + 1;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static int year()
/*       */   {
/*  2257 */     return Calendar.getInstance().get(1);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void delay(int napTime)
/*       */   {
/*  2280 */     if ((this.frameCount != 0) && 
/*  2281 */       (napTime > 0)) {
/*       */       try {
/*  2283 */         Thread.sleep(napTime);
/*       */       }
/*       */       catch (InterruptedException localInterruptedException) {}
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void frameRate(float newRateTarget)
/*       */   {
/*  2307 */     this.frameRateTarget = newRateTarget;
/*  2308 */     this.frameRatePeriod = ((1.0E9D / this.frameRateTarget));
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public String param(String what)
/*       */   {
/*  2328 */     if (this.online) {
/*  2329 */       return getParameter(what);
/*       */     }
/*       */     
/*  2332 */     System.err.println("param() only works inside a web browser");
/*       */     
/*  2334 */     return null;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void status(String what)
/*       */   {
/*  2351 */     if (this.online) {
/*  2352 */       showStatus(what);
/*       */     }
/*       */     else {
/*  2355 */       System.out.println(what);
/*       */     }
/*       */   }
/*       */   
/*       */   public void link(String here)
/*       */   {
/*  2361 */     link(here, null);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void link(String url, String frameTitle)
/*       */   {
/*  2384 */     if (this.online) {
/*       */       try {
/*  2386 */         if (frameTitle == null) {
/*  2387 */           getAppletContext().showDocument(new URL(url));
/*       */         } else {
/*  2389 */           getAppletContext().showDocument(new URL(url), frameTitle);
/*       */         }
/*       */       } catch (Exception e) {
/*  2392 */         e.printStackTrace();
/*  2393 */         throw new RuntimeException("Could not open " + url);
/*       */       }
/*       */     } else {
/*       */       try {
/*  2397 */         if (platform == 1)
/*       */         {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  2409 */           url = url.replaceAll("&", "^&");
/*       */           
/*       */ 
/*       */ 
/*       */ 
/*  2414 */           Runtime.getRuntime().exec("cmd /c start " + url);
/*       */         }
/*  2416 */         else if (platform == 2)
/*       */         {
/*       */ 
/*       */           try
/*       */           {
/*       */ 
/*  2422 */             Class<?> eieio = Class.forName("com.apple.eio.FileManager");
/*  2423 */             Method openMethod = 
/*  2424 */               eieio.getMethod("openURL", new Class[] { String.class });
/*  2425 */             openMethod.invoke(null, new Object[] { url });
/*       */           } catch (Exception e) {
/*  2427 */             e.printStackTrace();
/*       */           }
/*       */         }
/*       */         else
/*       */         {
/*  2432 */           open(url);
/*       */         }
/*       */       } catch (IOException e) {
/*  2435 */         e.printStackTrace();
/*  2436 */         throw new RuntimeException("Could not open " + url);
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static void open(String filename)
/*       */   {
/*  2458 */     open(new String[] { filename });
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static Process open(String[] argv)
/*       */   {
/*  2473 */     String[] params = (String[])null;
/*       */     
/*  2475 */     if (platform == 1)
/*       */     {
/*       */ 
/*       */ 
/*       */ 
/*  2480 */       params = new String[] { "cmd", "/c" };
/*       */     }
/*  2482 */     else if (platform == 2) {
/*  2483 */       params = new String[] { "open" };
/*       */     }
/*  2485 */     else if (platform == 3) {
/*  2486 */       if (openLauncher == null) {
/*       */         try
/*       */         {
/*  2489 */           Process p = Runtime.getRuntime().exec(new String[] { "gnome-open" });
/*  2490 */           p.waitFor();
/*       */           
/*  2492 */           openLauncher = "gnome-open";
/*       */         } catch (Exception localException) {}
/*       */       }
/*  2495 */       if (openLauncher == null) {
/*       */         try
/*       */         {
/*  2498 */           Process p = Runtime.getRuntime().exec(new String[] { "kde-open" });
/*  2499 */           p.waitFor();
/*  2500 */           openLauncher = "kde-open";
/*       */         } catch (Exception localException1) {}
/*       */       }
/*  2503 */       if (openLauncher == null) {
/*  2504 */         System.err.println("Could not find gnome-open or kde-open, the open() command may not work.");
/*       */       }
/*       */       
/*  2507 */       if (openLauncher != null) {
/*  2508 */         params = new String[] { openLauncher };
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*  2514 */     if (params != null)
/*       */     {
/*  2516 */       if (params[0].equals(argv[0]))
/*       */       {
/*  2518 */         return exec(argv);
/*       */       }
/*  2520 */       params = concat(params, argv);
/*  2521 */       return exec(params);
/*       */     }
/*       */     
/*  2524 */     return exec(argv);
/*       */   }
/*       */   
/*       */   public static Process exec(String[] argv)
/*       */   {
/*       */     try
/*       */     {
/*  2531 */       return Runtime.getRuntime().exec(argv);
/*       */     } catch (Exception e) {
/*  2533 */       e.printStackTrace();
/*  2534 */       throw new RuntimeException("Could not open " + join(argv, ' '));
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void die(String what)
/*       */   {
/*  2547 */     stop();
/*  2548 */     throw new RuntimeException(what);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void die(String what, Exception e)
/*       */   {
/*  2556 */     if (e != null) e.printStackTrace();
/*  2557 */     die(what);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void exit()
/*       */   {
/*  2566 */     if (this.thread == null)
/*       */     {
/*       */ 
/*  2569 */       exit2();
/*       */     }
/*  2571 */     else if (this.looping)
/*       */     {
/*  2573 */       this.finished = true;
/*       */       
/*       */ 
/*  2576 */       this.exitCalled = true;
/*       */     }
/*  2578 */     else if (!this.looping)
/*       */     {
/*       */ 
/*  2581 */       stop();
/*       */       
/*       */ 
/*  2584 */       exit2();
/*       */     }
/*       */   }
/*       */   
/*       */   void exit2()
/*       */   {
/*       */     try {
/*  2591 */       System.exit(0);
/*       */     }
/*       */     catch (SecurityException localSecurityException) {}
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void method(String name)
/*       */   {
/*       */     try
/*       */     {
/*  2606 */       Method method = getClass().getMethod(name, new Class[0]);
/*  2607 */       method.invoke(this, new Object[0]);
/*       */     }
/*       */     catch (IllegalArgumentException e) {
/*  2610 */       e.printStackTrace();
/*       */     } catch (IllegalAccessException e) {
/*  2612 */       e.printStackTrace();
/*       */     } catch (InvocationTargetException e) {
/*  2614 */       e.getTargetException().printStackTrace();
/*       */     } catch (NoSuchMethodException nsme) {
/*  2616 */       System.err.println("There is no public " + name + "() method " + 
/*  2617 */         "in the class " + getClass().getName());
/*       */     } catch (Exception e) {
/*  2619 */       e.printStackTrace();
/*       */     }
/*       */   }
/*       */   
/*       */   public void thread(final String name)
/*       */   {
/*  2625 */     Thread later = new Thread() {
/*       */       public void run() {
/*  2627 */         PApplet.this.method(name);
/*       */       }
/*  2629 */     };
/*  2630 */     later.start();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   static String openLauncher;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void save(String filename)
/*       */   {
/*  2679 */     this.g.save(savePath(filename));
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void saveFrame()
/*       */   {
/*       */     try
/*       */     {
/*  2693 */       this.g.save(savePath("screen-" + nf(this.frameCount, 4) + ".tif"));
/*       */     } catch (SecurityException se) {
/*  2695 */       System.err.println("Can't use saveFrame() when running in a browser, unless using a signed applet.");
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void saveFrame(String what)
/*       */   {
/*       */     try
/*       */     {
/*  2713 */       this.g.save(savePath(insertFrame(what)));
/*       */     } catch (SecurityException se) {
/*  2715 */       System.err.println("Can't use saveFrame() when running in a browser, unless using a signed applet.");
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected String insertFrame(String what)
/*       */   {
/*  2729 */     int first = what.indexOf('#');
/*  2730 */     int last = what.lastIndexOf('#');
/*       */     
/*  2732 */     if ((first != -1) && (last - first > 0)) {
/*  2733 */       String prefix = what.substring(0, first);
/*  2734 */       int count = last - first + 1;
/*  2735 */       String suffix = what.substring(last + 1);
/*  2736 */       return prefix + nf(this.frameCount, count) + suffix;
/*       */     }
/*  2738 */     return what;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  2750 */   int cursorType = 0;
/*  2751 */   boolean cursorVisible = true;
/*       */   PImage invisibleCursor;
/*       */   Random internalRandom;
/*       */   static final int PERLIN_YWRAPB = 4;
/*       */   static final int PERLIN_YWRAP = 16;
/*       */   static final int PERLIN_ZWRAPB = 8;
/*       */   static final int PERLIN_ZWRAP = 256;
/*       */   static final int PERLIN_SIZE = 4095;
/*       */   
/*  2760 */   public void cursor(int cursorType) { setCursor(Cursor.getPredefinedCursor(cursorType));
/*  2761 */     this.cursorVisible = true;
/*  2762 */     this.cursorType = cursorType;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void cursor(PImage image)
/*       */   {
/*  2771 */     cursor(image, image.width / 2, image.height / 2);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void cursor(PImage image, int hotspotX, int hotspotY)
/*       */   {
/*  2798 */     Image jimage = 
/*  2799 */       createImage(new MemoryImageSource(image.width, image.height, 
/*  2800 */       image.pixels, 0, image.width));
/*  2801 */     Point hotspot = new Point(hotspotX, hotspotY);
/*  2802 */     Toolkit tk = Toolkit.getDefaultToolkit();
/*  2803 */     Cursor cursor = tk.createCustomCursor(jimage, hotspot, "Custom Cursor");
/*  2804 */     setCursor(cursor);
/*  2805 */     this.cursorVisible = true;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void cursor()
/*       */   {
/*  2818 */     if (!this.cursorVisible) {
/*  2819 */       this.cursorVisible = true;
/*  2820 */       setCursor(Cursor.getPredefinedCursor(this.cursorType));
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void noCursor()
/*       */   {
/*  2835 */     if (!this.cursorVisible) { return;
/*       */     }
/*  2837 */     if (this.invisibleCursor == null) {
/*  2838 */       this.invisibleCursor = new PImage(16, 16, 2);
/*       */     }
/*       */     
/*       */ 
/*  2842 */     cursor(this.invisibleCursor, 8, 8);
/*  2843 */     this.cursorVisible = false;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public static void print(byte what)
/*       */   {
/*  2851 */     System.out.print(what);
/*  2852 */     System.out.flush();
/*       */   }
/*       */   
/*       */   public static void print(boolean what) {
/*  2856 */     System.out.print(what);
/*  2857 */     System.out.flush();
/*       */   }
/*       */   
/*       */   public static void print(char what) {
/*  2861 */     System.out.print(what);
/*  2862 */     System.out.flush();
/*       */   }
/*       */   
/*       */   public static void print(int what) {
/*  2866 */     System.out.print(what);
/*  2867 */     System.out.flush();
/*       */   }
/*       */   
/*       */   public static void print(float what) {
/*  2871 */     System.out.print(what);
/*  2872 */     System.out.flush();
/*       */   }
/*       */   
/*       */   public static void print(String what) {
/*  2876 */     System.out.print(what);
/*  2877 */     System.out.flush();
/*       */   }
/*       */   
/*       */   public static void print(Object what) {
/*  2881 */     if (what == null)
/*       */     {
/*  2883 */       System.out.print("null");
/*       */     } else {
/*  2885 */       System.out.println(what.toString());
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */   public static void println()
/*       */   {
/*  2892 */     System.out.println();
/*       */   }
/*       */   
/*       */ 
/*       */   public static void println(byte what)
/*       */   {
/*  2898 */     print(what);System.out.println();
/*       */   }
/*       */   
/*       */   public static void println(boolean what) {
/*  2902 */     print(what);System.out.println();
/*       */   }
/*       */   
/*       */   public static void println(char what) {
/*  2906 */     print(what);System.out.println();
/*       */   }
/*       */   
/*       */   public static void println(int what) {
/*  2910 */     print(what);System.out.println();
/*       */   }
/*       */   
/*       */   public static void println(float what) {
/*  2914 */     print(what);System.out.println();
/*       */   }
/*       */   
/*       */   public static void println(String what) {
/*  2918 */     print(what);System.out.println();
/*       */   }
/*       */   
/*       */   public static void println(Object what) {
/*  2922 */     if (what == null)
/*       */     {
/*  2924 */       System.out.println("null");
/*       */     }
/*       */     else {
/*  2927 */       String name = what.getClass().getName();
/*  2928 */       if (name.charAt(0) == '[')
/*  2929 */         switch (name.charAt(1))
/*       */         {
/*       */ 
/*       */         case '[': 
/*  2933 */           System.out.println(what);
/*  2934 */           break;
/*       */         
/*       */ 
/*       */         case 'L': 
/*  2938 */           Object[] poo = (Object[])what;
/*  2939 */           for (int i = 0; i < poo.length; i++) {
/*  2940 */             if ((poo[i] instanceof String)) {
/*  2941 */               System.out.println("[" + i + "] \"" + poo[i] + "\"");
/*       */             } else {
/*  2943 */               System.out.println("[" + i + "] " + poo[i]);
/*       */             }
/*       */           }
/*  2946 */           break;
/*       */         
/*       */         case 'Z': 
/*  2949 */           boolean[] zz = (boolean[])what;
/*  2950 */           for (int i = 0; i < zz.length; i++) {
/*  2951 */             System.out.println("[" + i + "] " + zz[i]);
/*       */           }
/*  2953 */           break;
/*       */         
/*       */         case 'B': 
/*  2956 */           byte[] bb = (byte[])what;
/*  2957 */           for (int i = 0; i < bb.length; i++) {
/*  2958 */             System.out.println("[" + i + "] " + bb[i]);
/*       */           }
/*  2960 */           break;
/*       */         
/*       */         case 'C': 
/*  2963 */           char[] cc = (char[])what;
/*  2964 */           for (int i = 0; i < cc.length; i++) {
/*  2965 */             System.out.println("[" + i + "] '" + cc[i] + "'");
/*       */           }
/*  2967 */           break;
/*       */         
/*       */         case 'I': 
/*  2970 */           int[] ii = (int[])what;
/*  2971 */           for (int i = 0; i < ii.length; i++) {
/*  2972 */             System.out.println("[" + i + "] " + ii[i]);
/*       */           }
/*  2974 */           break;
/*       */         
/*       */         case 'F': 
/*  2977 */           float[] ff = (float[])what;
/*  2978 */           for (int i = 0; i < ff.length; i++) {
/*  2979 */             System.out.println("[" + i + "] " + ff[i]);
/*       */           }
/*  2981 */           break;
/*       */         
/*       */         case 'D': 
/*  2984 */           double[] dd = (double[])what;
/*  2985 */           for (int i = 0; i < dd.length; i++) {
/*  2986 */             System.out.println("[" + i + "] " + dd[i]);
/*       */           }
/*  2988 */           break;
/*       */         
/*       */         default: 
/*  2991 */           System.out.println(what);break;
/*       */         }
/*       */          else {
/*  2994 */         System.out.println(what);
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final float abs(float n)
/*       */   {
/*  3025 */     return n < 0.0F ? -n : n;
/*       */   }
/*       */   
/*       */   public static final int abs(int n) {
/*  3029 */     return n < 0 ? -n : n;
/*       */   }
/*       */   
/*       */   public static final float sq(float a) {
/*  3033 */     return a * a;
/*       */   }
/*       */   
/*       */   public static final float sqrt(float a) {
/*  3037 */     return (float)Math.sqrt(a);
/*       */   }
/*       */   
/*       */   public static final float log(float a) {
/*  3041 */     return (float)Math.log(a);
/*       */   }
/*       */   
/*       */   public static final float exp(float a) {
/*  3045 */     return (float)Math.exp(a);
/*       */   }
/*       */   
/*       */   public static final float pow(float a, float b) {
/*  3049 */     return (float)Math.pow(a, b);
/*       */   }
/*       */   
/*       */   public static final int max(int a, int b)
/*       */   {
/*  3054 */     return a > b ? a : b;
/*       */   }
/*       */   
/*       */   public static final float max(float a, float b) {
/*  3058 */     return a > b ? a : b;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final int max(int a, int b, int c)
/*       */   {
/*  3069 */     return b > c ? b : a > b ? c : a > c ? a : c;
/*       */   }
/*       */   
/*       */   public static final float max(float a, float b, float c) {
/*  3073 */     return b > c ? b : a > b ? c : a > c ? a : c;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final int max(int[] list)
/*       */   {
/*  3084 */     if (list.length == 0) {
/*  3085 */       throw new ArrayIndexOutOfBoundsException("Cannot use min() or max() on an empty array.");
/*       */     }
/*  3087 */     int max = list[0];
/*  3088 */     for (int i = 1; i < list.length; i++) {
/*  3089 */       if (list[i] > max) max = list[i];
/*       */     }
/*  3091 */     return max;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final float max(float[] list)
/*       */   {
/*  3101 */     if (list.length == 0) {
/*  3102 */       throw new ArrayIndexOutOfBoundsException("Cannot use min() or max() on an empty array.");
/*       */     }
/*  3104 */     float max = list[0];
/*  3105 */     for (int i = 1; i < list.length; i++) {
/*  3106 */       if (list[i] > max) max = list[i];
/*       */     }
/*  3108 */     return max;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final int min(int a, int b)
/*       */   {
/*  3133 */     return a < b ? a : b;
/*       */   }
/*       */   
/*       */   public static final float min(float a, float b) {
/*  3137 */     return a < b ? a : b;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final int min(int a, int b, int c)
/*       */   {
/*  3148 */     return b < c ? b : a < b ? c : a < c ? a : c;
/*       */   }
/*       */   
/*       */   public static final float min(float a, float b, float c) {
/*  3152 */     return b < c ? b : a < b ? c : a < c ? a : c;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final int min(int[] list)
/*       */   {
/*  3169 */     if (list.length == 0) {
/*  3170 */       throw new ArrayIndexOutOfBoundsException("Cannot use min() or max() on an empty array.");
/*       */     }
/*  3172 */     int min = list[0];
/*  3173 */     for (int i = 1; i < list.length; i++) {
/*  3174 */       if (list[i] < min) min = list[i];
/*       */     }
/*  3176 */     return min;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final float min(float[] list)
/*       */   {
/*  3187 */     if (list.length == 0) {
/*  3188 */       throw new ArrayIndexOutOfBoundsException("Cannot use min() or max() on an empty array.");
/*       */     }
/*  3190 */     float min = list[0];
/*  3191 */     for (int i = 1; i < list.length; i++) {
/*  3192 */       if (list[i] < min) min = list[i];
/*       */     }
/*  3194 */     return min;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final int constrain(int amt, int low, int high)
/*       */   {
/*  3218 */     return amt > high ? high : amt < low ? low : amt;
/*       */   }
/*       */   
/*       */   public static final float constrain(float amt, float low, float high) {
/*  3222 */     return amt > high ? high : amt < low ? low : amt;
/*       */   }
/*       */   
/*       */   public static final float sin(float angle)
/*       */   {
/*  3227 */     return (float)Math.sin(angle);
/*       */   }
/*       */   
/*       */   public static final float cos(float angle) {
/*  3231 */     return (float)Math.cos(angle);
/*       */   }
/*       */   
/*       */   public static final float tan(float angle) {
/*  3235 */     return (float)Math.tan(angle);
/*       */   }
/*       */   
/*       */   public static final float asin(float value)
/*       */   {
/*  3240 */     return (float)Math.asin(value);
/*       */   }
/*       */   
/*       */   public static final float acos(float value) {
/*  3244 */     return (float)Math.acos(value);
/*       */   }
/*       */   
/*       */   public static final float atan(float value) {
/*  3248 */     return (float)Math.atan(value);
/*       */   }
/*       */   
/*       */   public static final float atan2(float a, float b) {
/*  3252 */     return (float)Math.atan2(a, b);
/*       */   }
/*       */   
/*       */   public static final float degrees(float radians)
/*       */   {
/*  3257 */     return radians * 57.295776F;
/*       */   }
/*       */   
/*       */   public static final float radians(float degrees) {
/*  3261 */     return degrees * 0.017453292F;
/*       */   }
/*       */   
/*       */   public static final int ceil(float what)
/*       */   {
/*  3266 */     return (int)Math.ceil(what);
/*       */   }
/*       */   
/*       */   public static final int floor(float what) {
/*  3270 */     return (int)Math.floor(what);
/*       */   }
/*       */   
/*       */   public static final int round(float what) {
/*  3274 */     return Math.round(what);
/*       */   }
/*       */   
/*       */   public static final float mag(float a, float b)
/*       */   {
/*  3279 */     return (float)Math.sqrt(a * a + b * b);
/*       */   }
/*       */   
/*       */   public static final float mag(float a, float b, float c) {
/*  3283 */     return (float)Math.sqrt(a * a + b * b + c * c);
/*       */   }
/*       */   
/*       */   public static final float dist(float x1, float y1, float x2, float y2)
/*       */   {
/*  3288 */     return sqrt(sq(x2 - x1) + sq(y2 - y1));
/*       */   }
/*       */   
/*       */   public static final float dist(float x1, float y1, float z1, float x2, float y2, float z2)
/*       */   {
/*  3293 */     return sqrt(sq(x2 - x1) + sq(y2 - y1) + sq(z2 - z1));
/*       */   }
/*       */   
/*       */   public static final float lerp(float start, float stop, float amt)
/*       */   {
/*  3298 */     return start + (stop - start) * amt;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final float norm(float value, float start, float stop)
/*       */   {
/*  3307 */     return (value - start) / (stop - start);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final float map(float value, float istart, float istop, float ostart, float ostop)
/*       */   {
/*  3317 */     return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public final float random(float howbig)
/*       */   {
/*  3350 */     if (howbig == 0.0F) { return 0.0F;
/*       */     }
/*       */     
/*  3353 */     if (this.internalRandom == null) { this.internalRandom = new Random();
/*       */     }
/*  3355 */     float value = 0.0F;
/*       */     do
/*       */     {
/*  3358 */       value = this.internalRandom.nextFloat() * howbig;
/*  3359 */     } while (value == howbig);
/*  3360 */     return value;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public final float random(float howsmall, float howbig)
/*       */   {
/*  3375 */     if (howsmall >= howbig) return howsmall;
/*  3376 */     float diff = howbig - howsmall;
/*  3377 */     return random(diff) + howsmall;
/*       */   }
/*       */   
/*       */ 
/*       */   public final void randomSeed(long what)
/*       */   {
/*  3383 */     if (this.internalRandom == null) this.internalRandom = new Random();
/*  3384 */     this.internalRandom.setSeed(what);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  3410 */   int perlin_octaves = 4;
/*  3411 */   float perlin_amp_falloff = 0.5F;
/*       */   
/*       */   int perlin_TWOPI;
/*       */   
/*       */   int perlin_PI;
/*       */   
/*       */   float[] perlin_cosTable;
/*       */   
/*       */   float[] perlin;
/*       */   
/*       */   Random perlinRandom;
/*       */   
/*       */   protected String[] loadImageFormats;
/*       */   
/*       */   public float noise(float x)
/*       */   {
/*  3427 */     return noise(x, 0.0F, 0.0F);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   public float noise(float x, float y)
/*       */   {
/*  3434 */     return noise(x, y, 0.0F);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   public float noise(float x, float y, float z)
/*       */   {
/*  3441 */     if (this.perlin == null) {
/*  3442 */       if (this.perlinRandom == null) {
/*  3443 */         this.perlinRandom = new Random();
/*       */       }
/*  3445 */       this.perlin = new float['က'];
/*  3446 */       for (int i = 0; i < 4096; i++) {
/*  3447 */         this.perlin[i] = this.perlinRandom.nextFloat();
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*  3452 */       this.perlin_cosTable = PGraphics.cosLUT;
/*  3453 */       this.perlin_TWOPI = (this.perlin_PI = 'ː');
/*  3454 */       this.perlin_PI >>= 1;
/*       */     }
/*       */     
/*  3457 */     if (x < 0.0F) x = -x;
/*  3458 */     if (y < 0.0F) y = -y;
/*  3459 */     if (z < 0.0F) { z = -z;
/*       */     }
/*  3461 */     int xi = (int)x;int yi = (int)y;int zi = (int)z;
/*  3462 */     float xf = x - xi;
/*  3463 */     float yf = y - yi;
/*  3464 */     float zf = z - zi;
/*       */     
/*       */ 
/*  3467 */     float r = 0.0F;
/*  3468 */     float ampl = 0.5F;
/*       */     
/*       */ 
/*       */ 
/*  3472 */     for (int i = 0; i < this.perlin_octaves; i++) {
/*  3473 */       int of = xi + (yi << 4) + (zi << 8);
/*       */       
/*  3475 */       float rxf = noise_fsc(xf);
/*  3476 */       float ryf = noise_fsc(yf);
/*       */       
/*  3478 */       float n1 = this.perlin[(of & 0xFFF)];
/*  3479 */       n1 += rxf * (this.perlin[(of + 1 & 0xFFF)] - n1);
/*  3480 */       float n2 = this.perlin[(of + 16 & 0xFFF)];
/*  3481 */       n2 += rxf * (this.perlin[(of + 16 + 1 & 0xFFF)] - n2);
/*  3482 */       n1 += ryf * (n2 - n1);
/*       */       
/*  3484 */       of += 256;
/*  3485 */       n2 = this.perlin[(of & 0xFFF)];
/*  3486 */       n2 += rxf * (this.perlin[(of + 1 & 0xFFF)] - n2);
/*  3487 */       float n3 = this.perlin[(of + 16 & 0xFFF)];
/*  3488 */       n3 += rxf * (this.perlin[(of + 16 + 1 & 0xFFF)] - n3);
/*  3489 */       n2 += ryf * (n3 - n2);
/*       */       
/*  3491 */       n1 += noise_fsc(zf) * (n2 - n1);
/*       */       
/*  3493 */       r += n1 * ampl;
/*  3494 */       ampl *= this.perlin_amp_falloff;
/*  3495 */       xi <<= 1;xf *= 2.0F;
/*  3496 */       yi <<= 1;yf *= 2.0F;
/*  3497 */       zi <<= 1;zf *= 2.0F;
/*       */       
/*  3499 */       if (xf >= 1.0F) { xi++;xf -= 1.0F; }
/*  3500 */       if (yf >= 1.0F) { yi++;yf -= 1.0F; }
/*  3501 */       if (zf >= 1.0F) { zi++;zf -= 1.0F;
/*       */       } }
/*  3503 */     return r;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   private float noise_fsc(float i)
/*       */   {
/*  3511 */     return 0.5F * (1.0F - this.perlin_cosTable[((int)(i * this.perlin_PI) % this.perlin_TWOPI)]);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void noiseDetail(int lod)
/*       */   {
/*  3520 */     if (lod > 0) this.perlin_octaves = lod;
/*       */   }
/*       */   
/*       */   public void noiseDetail(int lod, float falloff) {
/*  3524 */     if (lod > 0) this.perlin_octaves = lod;
/*  3525 */     if (falloff > 0.0F) this.perlin_amp_falloff = falloff;
/*       */   }
/*       */   
/*       */   public void noiseSeed(long what) {
/*  3529 */     if (this.perlinRandom == null) this.perlinRandom = new Random();
/*  3530 */     this.perlinRandom.setSeed(what);
/*       */     
/*  3532 */     this.perlin = null;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public PImage loadImage(String filename)
/*       */   {
/*  3598 */     return loadImage(filename, null);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public PImage loadImage(String filename, String extension)
/*       */   {
/*  3626 */     if (extension == null) {
/*  3627 */       String lower = filename.toLowerCase();
/*  3628 */       int dot = filename.lastIndexOf('.');
/*  3629 */       if (dot == -1) {
/*  3630 */         extension = "unknown";
/*       */       }
/*  3632 */       extension = lower.substring(dot + 1);
/*       */       
/*       */ 
/*       */ 
/*  3636 */       int question = extension.indexOf('?');
/*  3637 */       if (question != -1) {
/*  3638 */         extension = extension.substring(0, question);
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*  3643 */     extension = extension.toLowerCase();
/*       */     
/*  3645 */     if (extension.equals("tga")) {
/*       */       try {
/*  3647 */         return loadImageTGA(filename);
/*       */       } catch (IOException e) {
/*  3649 */         e.printStackTrace();
/*  3650 */         return null;
/*       */       }
/*       */     }
/*       */     
/*  3654 */     if ((extension.equals("tif")) || (extension.equals("tiff"))) {
/*  3655 */       byte[] bytes = loadBytes(filename);
/*  3656 */       return bytes == null ? null : PImage.loadTIFF(bytes);
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */     try
/*       */     {
/*  3663 */       if ((extension.equals("jpg")) || (extension.equals("jpeg")) || 
/*  3664 */         (extension.equals("gif")) || (extension.equals("png")) || 
/*  3665 */         (extension.equals("unknown"))) {
/*  3666 */         byte[] bytes = loadBytes(filename);
/*  3667 */         if (bytes == null) {
/*  3668 */           return null;
/*       */         }
/*  3670 */         Image awtImage = Toolkit.getDefaultToolkit().createImage(bytes);
/*  3671 */         PImage image = loadImageMT(awtImage);
/*  3672 */         if (image.width == -1) {
/*  3673 */           System.err.println("The file " + filename + 
/*  3674 */             " contains bad image data, or may not be an image.");
/*       */         }
/*       */         
/*  3677 */         if ((extension.equals("gif")) || (extension.equals("png"))) {
/*  3678 */           image.checkAlpha();
/*       */         }
/*  3680 */         return image;
/*       */       }
/*       */     }
/*       */     catch (Exception e)
/*       */     {
/*  3685 */       e.printStackTrace();
/*       */       
/*       */ 
/*  3688 */       if (this.loadImageFormats == null) {
/*  3689 */         this.loadImageFormats = ImageIO.getReaderFormatNames();
/*       */       }
/*  3691 */       if (this.loadImageFormats != null) {
/*  3692 */         for (int i = 0; i < this.loadImageFormats.length; i++) {
/*  3693 */           if (extension.equals(this.loadImageFormats[i])) {
/*  3694 */             return loadImageIO(filename);
/*       */           }
/*       */         }
/*       */       }
/*       */       
/*       */ 
/*  3700 */       System.err.println("Could not find a method to load " + filename); }
/*  3701 */     return null;
/*       */   }
/*       */   
/*       */   public PImage requestImage(String filename) {
/*  3705 */     return requestImage(filename, null);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public PImage requestImage(String filename, String extension)
/*       */   {
/*  3721 */     PImage vessel = createImage(0, 0, 2);
/*  3722 */     AsyncImageLoader ail = 
/*  3723 */       new AsyncImageLoader(filename, extension, vessel);
/*  3724 */     ail.start();
/*  3725 */     return vessel;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  3738 */   public int requestImageMax = 4;
/*       */   volatile int requestImageCount;
/*       */   public File selectedFile;
/*       */   protected Frame parentFrame;
/*       */   protected static HashMap<String, Pattern> matchPatterns;
/*       */   private static NumberFormat int_nf;
/*       */   
/*       */   class AsyncImageLoader extends Thread {
/*       */     public AsyncImageLoader(String filename, String extension, PImage vessel) {
/*  3747 */       this.filename = filename;
/*  3748 */       this.extension = extension;
/*  3749 */       this.vessel = vessel;
/*       */     }
/*       */     
/*       */     public void run() {
/*  3753 */       while (PApplet.this.requestImageCount == PApplet.this.requestImageMax) {
/*       */         try {
/*  3755 */           Thread.sleep(10L);
/*       */         } catch (InterruptedException localInterruptedException) {}
/*       */       }
/*  3758 */       PApplet.this.requestImageCount += 1;
/*       */       
/*  3760 */       PImage actual = PApplet.this.loadImage(this.filename, this.extension);
/*       */       
/*       */ 
/*  3763 */       if (actual == null) {
/*  3764 */         this.vessel.width = -1;
/*  3765 */         this.vessel.height = -1;
/*       */       }
/*       */       else {
/*  3768 */         this.vessel.width = actual.width;
/*  3769 */         this.vessel.height = actual.height;
/*  3770 */         this.vessel.format = actual.format;
/*  3771 */         this.vessel.pixels = actual.pixels;
/*       */       }
/*  3773 */       PApplet.this.requestImageCount -= 1;
/*       */     }
/*       */     
/*       */     String filename;
/*       */     String extension;
/*       */     PImage vessel;
/*       */   }
/*       */   
/*       */   protected PImage loadImageMT(Image awtImage)
/*       */   {
/*  3783 */     MediaTracker tracker = new MediaTracker(this);
/*  3784 */     tracker.addImage(awtImage, 0);
/*       */     try {
/*  3786 */       tracker.waitForAll();
/*       */     }
/*       */     catch (InterruptedException localInterruptedException) {}
/*       */     
/*       */ 
/*  3791 */     PImage image = new PImage(awtImage);
/*  3792 */     image.parent = this;
/*  3793 */     return image;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   protected PImage loadImageIO(String filename)
/*       */   {
/*  3801 */     InputStream stream = createInput(filename);
/*  3802 */     if (stream == null) {
/*  3803 */       System.err.println("The image " + filename + " could not be found.");
/*  3804 */       return null;
/*       */     }
/*       */     try
/*       */     {
/*  3808 */       BufferedImage bi = ImageIO.read(stream);
/*  3809 */       PImage outgoing = new PImage(bi.getWidth(), bi.getHeight());
/*  3810 */       outgoing.parent = this;
/*       */       
/*  3812 */       bi.getRGB(0, 0, outgoing.width, outgoing.height, 
/*  3813 */         outgoing.pixels, 0, outgoing.width);
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  3822 */       outgoing.checkAlpha();
/*       */       
/*       */ 
/*  3825 */       return outgoing;
/*       */     }
/*       */     catch (Exception e) {
/*  3828 */       e.printStackTrace(); }
/*  3829 */     return null;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected PImage loadImageTGA(String filename)
/*       */     throws IOException
/*       */   {
/*  3842 */     InputStream is = createInput(filename);
/*  3843 */     if (is == null) { return null;
/*       */     }
/*  3845 */     byte[] header = new byte[18];
/*  3846 */     int offset = 0;
/*       */     do {
/*  3848 */       int count = is.read(header, offset, header.length - offset);
/*  3849 */       if (count == -1) return null;
/*  3850 */       offset += count;
/*  3851 */     } while (offset < 18);
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  3870 */     int format = 0;
/*       */     
/*  3872 */     if (((header[2] == 3) || (header[2] == 11)) && 
/*  3873 */       (header[16] == 8) && (
/*  3874 */       (header[17] == 8) || (header[17] == 40))) {
/*  3875 */       format = 4;
/*       */     }
/*  3877 */     else if (((header[2] == 2) || (header[2] == 10)) && 
/*  3878 */       (header[16] == 24) && (
/*  3879 */       (header[17] == 32) || (header[17] == 0))) {
/*  3880 */       format = 1;
/*       */     }
/*  3882 */     else if (((header[2] == 2) || (header[2] == 10)) && 
/*  3883 */       (header[16] == 32) && (
/*  3884 */       (header[17] == 8) || (header[17] == 40))) {
/*  3885 */       format = 2;
/*       */     }
/*       */     
/*  3888 */     if (format == 0) {
/*  3889 */       System.err.println("Unknown .tga file format for " + filename);
/*       */       
/*       */ 
/*       */ 
/*  3893 */       return null;
/*       */     }
/*       */     
/*  3896 */     int w = ((header[13] & 0xFF) << 8) + (header[12] & 0xFF);
/*  3897 */     int h = ((header[15] & 0xFF) << 8) + (header[14] & 0xFF);
/*  3898 */     PImage outgoing = createImage(w, h, format);
/*       */     
/*       */ 
/*       */ 
/*  3902 */     boolean reversed = (header[17] & 0x20) != 0;
/*       */     int count;
/*  3904 */     if ((header[2] == 2) || (header[2] == 3)) {
/*  3905 */       if (reversed) {
/*  3906 */         int index = (h - 1) * w;
/*  3907 */         switch (format) {
/*       */         case 4: 
/*  3909 */           for (int y = h - 1; y >= 0; y--) {
/*  3910 */             for (int x = 0; x < w; x++) {
/*  3911 */               outgoing.pixels[(index + x)] = is.read();
/*       */             }
/*  3913 */             index -= w;
/*       */           }
/*  3915 */           break;
/*       */         case 1: 
/*  3917 */           for (int y = h - 1; y >= 0; y--) {
/*  3918 */             for (int x = 0; x < w; x++) {
/*  3919 */               outgoing.pixels[(index + x)] = 
/*  3920 */                 (is.read() | is.read() << 8 | is.read() << 16 | 
/*  3921 */                 0xFF000000);
/*       */             }
/*  3923 */             index -= w;
/*       */           }
/*  3925 */           break;
/*       */         case 2: 
/*  3927 */           for (int y = h - 1; y >= 0; y--) {
/*  3928 */             for (int x = 0; x < w; x++) {
/*  3929 */               outgoing.pixels[(index + x)] = 
/*  3930 */                 (is.read() | is.read() << 8 | is.read() << 16 | 
/*  3931 */                 is.read() << 24);
/*       */             }
/*  3933 */             index -= w;
/*       */           }
/*       */         }
/*       */       } else {
/*  3937 */         count = w * h;
/*  3938 */       } } else switch (format) {
/*       */       case 4: 
/*  3940 */         for (int i = 0; i < count; i++) {
/*  3941 */           outgoing.pixels[i] = is.read();
/*       */         }
/*  3943 */         break;
/*       */       case 1: 
/*  3945 */         for (int i = 0; i < count; i++) {
/*  3946 */           outgoing.pixels[i] = 
/*  3947 */             (is.read() | is.read() << 8 | is.read() << 16 | 
/*  3948 */             0xFF000000);
/*       */         }
/*  3950 */         break;
/*       */       case 2: 
/*  3952 */         for (int i = 0; i < count; i++) {
/*  3953 */           outgoing.pixels[i] = 
/*  3954 */             (is.read() | is.read() << 8 | is.read() << 16 | 
/*  3955 */             is.read() << 24);
/*       */         }
/*       */       
/*       */ 
/*       */ 
/*       */       case 3: 
/*       */       default: 
/*  3962 */         break;int index = 0;
/*  3963 */         int[] px = outgoing.pixels;
/*       */         
/*  3965 */         while (index < px.length) {
/*  3966 */           int num = is.read();
/*  3967 */           boolean isRLE = (num & 0x80) != 0;
/*  3968 */           if (isRLE) {
/*  3969 */             num -= 127;
/*  3970 */             int pixel = 0;
/*  3971 */             switch (format) {
/*       */             case 4: 
/*  3973 */               pixel = is.read();
/*  3974 */               break;
/*       */             case 1: 
/*  3976 */               pixel = 0xFF000000 | 
/*  3977 */                 is.read() | is.read() << 8 | is.read() << 16;
/*       */               
/*  3979 */               break;
/*       */             case 2: 
/*  3981 */               pixel = is.read() | 
/*  3982 */                 is.read() << 8 | is.read() << 16 | is.read() << 24;
/*       */             }
/*       */             
/*  3985 */             for (int i = 0; i < num; i++) {
/*  3986 */               px[(index++)] = pixel;
/*  3987 */               if (index == px.length)
/*       */                 break;
/*       */             }
/*  3990 */           } else { num++;
/*  3991 */             switch (format) {
/*       */             case 4: 
/*  3993 */               for (int i = 0; i < num; i++) {
/*  3994 */                 px[(index++)] = is.read();
/*       */               }
/*  3996 */               break;
/*       */             case 1: 
/*  3998 */               for (int i = 0; i < num; i++) {
/*  3999 */                 px[(index++)] = 
/*  4000 */                   (0xFF000000 | is.read() | is.read() << 8 | is.read() << 16);
/*       */               }
/*       */               
/*  4003 */               break;
/*       */             case 2: 
/*  4005 */               for (int i = 0; i < num; i++) {
/*  4006 */                 px[(index++)] = 
/*  4007 */                   (is.read() | is.read() << 8 | is.read() << 16 | is.read() << 24);
/*       */               }
/*       */             }
/*       */             
/*       */           }
/*       */         }
/*       */         
/*       */ 
/*  4015 */         if (!reversed) {
/*  4016 */           int[] temp = new int[w];
/*  4017 */           for (int y = 0; y < h / 2; y++) {
/*  4018 */             int z = h - 1 - y;
/*  4019 */             System.arraycopy(px, y * w, temp, 0, w);
/*  4020 */             System.arraycopy(px, z * w, px, y * w, w);
/*  4021 */             System.arraycopy(temp, 0, px, z * w, w);
/*       */           }
/*       */         }
/*       */         break;
/*       */       }
/*  4026 */     return outgoing;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public PShape loadShape(String filename)
/*       */   {
/*  4054 */     if (filename.toLowerCase().endsWith(".svg")) {
/*  4055 */       return new PShapeSVG(this, filename);
/*       */     }
/*  4057 */     if (filename.toLowerCase().endsWith(".svgz")) {
/*       */       try {
/*  4059 */         InputStream input = new GZIPInputStream(createInput(filename));
/*  4060 */         XMLElement xml = new XMLElement(createReader(input));
/*  4061 */         return new PShapeSVG(xml);
/*       */       } catch (IOException e) {
/*  4063 */         e.printStackTrace();
/*       */       }
/*       */     }
/*  4066 */     return null;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public PFont loadFont(String filename)
/*       */   {
/*       */     try
/*       */     {
/*  4078 */       InputStream input = createInput(filename);
/*  4079 */       return new PFont(input);
/*       */     }
/*       */     catch (Exception e) {
/*  4082 */       die("Could not load font " + filename + ". " + 
/*  4083 */         "Make sure that the font has been copied " + 
/*  4084 */         "to the data folder of your sketch.", e);
/*       */     }
/*  4086 */     return null;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected PFont createDefaultFont(float size)
/*       */   {
/*  4098 */     return createFont("SansSerif", size, true, null);
/*       */   }
/*       */   
/*       */   public PFont createFont(String name, float size)
/*       */   {
/*  4103 */     return createFont(name, size, true, null);
/*       */   }
/*       */   
/*       */   public PFont createFont(String name, float size, boolean smooth)
/*       */   {
/*  4108 */     return createFont(name, size, smooth, null);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public PFont createFont(String name, float size, boolean smooth, char[] charset)
/*       */   {
/*  4133 */     String lowerName = name.toLowerCase();
/*  4134 */     Font baseFont = null;
/*       */     try
/*       */     {
/*  4137 */       InputStream stream = null;
/*  4138 */       if ((lowerName.endsWith(".otf")) || (lowerName.endsWith(".ttf"))) {
/*  4139 */         stream = createInput(name);
/*  4140 */         if (stream == null) {
/*  4141 */           System.err.println("The font \"" + name + "\" " + 
/*  4142 */             "is missing or inaccessible, make sure " + 
/*  4143 */             "the URL is valid or that the file has been " + 
/*  4144 */             "added to your sketch and is readable.");
/*  4145 */           return null;
/*       */         }
/*  4147 */         baseFont = Font.createFont(0, createInput(name));
/*       */       }
/*       */       else {
/*  4150 */         baseFont = PFont.findFont(name);
/*       */       }
/*  4152 */       return new PFont(baseFont.deriveFont(size), smooth, charset, 
/*  4153 */         stream != null);
/*       */     }
/*       */     catch (Exception e) {
/*  4156 */       System.err.println("Problem createFont(" + name + ")");
/*  4157 */       e.printStackTrace(); }
/*  4158 */     return null;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   protected void checkParentFrame()
/*       */   {
/*  4174 */     if (this.parentFrame == null) {
/*  4175 */       Component comp = getParent();
/*  4176 */       while (comp != null) {
/*  4177 */         if ((comp instanceof Frame)) {
/*  4178 */           this.parentFrame = ((Frame)comp);
/*  4179 */           break;
/*       */         }
/*  4181 */         comp = comp.getParent();
/*       */       }
/*       */       
/*  4184 */       if (this.parentFrame == null) {
/*  4185 */         this.parentFrame = new Frame();
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public String selectInput()
/*       */   {
/*  4196 */     return selectInput("Select a file...");
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public String selectInput(String prompt)
/*       */   {
/*  4211 */     return selectFileImpl(prompt, 0);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public String selectOutput()
/*       */   {
/*  4220 */     return selectOutput("Save as...");
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public String selectOutput(String prompt)
/*       */   {
/*  4238 */     return selectFileImpl(prompt, 1);
/*       */   }
/*       */   
/*       */   protected String selectFileImpl(final String prompt, final int mode)
/*       */   {
/*  4243 */     checkParentFrame();
/*       */     try
/*       */     {
/*  4246 */       SwingUtilities.invokeAndWait(new Runnable() {
/*       */         public void run() {
/*  4248 */           FileDialog fileDialog = 
/*  4249 */             new FileDialog(PApplet.this.parentFrame, prompt, mode);
/*  4250 */           fileDialog.setVisible(true);
/*  4251 */           String directory = fileDialog.getDirectory();
/*  4252 */           String filename = fileDialog.getFile();
/*  4253 */           PApplet.this.selectedFile = 
/*  4254 */             (filename == null ? null : new File(directory, filename));
/*       */         }
/*  4256 */       });
/*  4257 */       return this.selectedFile == null ? null : this.selectedFile.getAbsolutePath();
/*       */     }
/*       */     catch (Exception e) {
/*  4260 */       e.printStackTrace(); }
/*  4261 */     return null;
/*       */   }
/*       */   
/*       */ 
/*       */   public String selectFolder()
/*       */   {
/*  4267 */     return selectFolder("Select a folder...");
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public String selectFolder(final String prompt)
/*       */   {
/*  4283 */     checkParentFrame();
/*       */     try
/*       */     {
/*  4286 */       SwingUtilities.invokeAndWait(new Runnable() {
/*       */         public void run() {
/*  4288 */           if (PApplet.platform == 2) {
/*  4289 */             FileDialog fileDialog = 
/*  4290 */               new FileDialog(PApplet.this.parentFrame, prompt, 0);
/*  4291 */             System.setProperty("apple.awt.fileDialogForDirectories", "true");
/*  4292 */             fileDialog.setVisible(true);
/*  4293 */             System.setProperty("apple.awt.fileDialogForDirectories", "false");
/*  4294 */             String filename = fileDialog.getFile();
/*  4295 */             PApplet.this.selectedFile = (filename == null ? null : 
/*  4296 */               new File(fileDialog.getDirectory(), fileDialog.getFile()));
/*       */           } else {
/*  4298 */             JFileChooser fileChooser = new JFileChooser();
/*  4299 */             fileChooser.setDialogTitle(prompt);
/*  4300 */             fileChooser.setFileSelectionMode(1);
/*       */             
/*  4302 */             int returned = fileChooser.showOpenDialog(PApplet.this.parentFrame);
/*  4303 */             System.out.println(returned);
/*  4304 */             if (returned == 1) {
/*  4305 */               PApplet.this.selectedFile = null;
/*       */             } else {
/*  4307 */               PApplet.this.selectedFile = fileChooser.getSelectedFile();
/*       */             }
/*       */           }
/*       */         }
/*  4311 */       });
/*  4312 */       return this.selectedFile == null ? null : this.selectedFile.getAbsolutePath();
/*       */     }
/*       */     catch (Exception e) {
/*  4315 */       e.printStackTrace(); }
/*  4316 */     return null;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public BufferedReader createReader(String filename)
/*       */   {
/*       */     try
/*       */     {
/*  4333 */       InputStream is = createInput(filename);
/*  4334 */       if (is == null) {
/*  4335 */         System.err.println(filename + " does not exist or could not be read");
/*  4336 */         return null;
/*       */       }
/*  4338 */       return createReader(is);
/*       */     }
/*       */     catch (Exception e) {
/*  4341 */       if (filename == null) {
/*  4342 */         System.err.println("Filename passed to reader() was null");
/*       */       } else {
/*  4344 */         System.err.println("Couldn't create a reader for " + filename);
/*       */       }
/*       */     }
/*  4347 */     return null;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   public static BufferedReader createReader(File file)
/*       */   {
/*       */     try
/*       */     {
/*  4356 */       InputStream is = new FileInputStream(file);
/*  4357 */       if (file.getName().toLowerCase().endsWith(".gz")) {
/*  4358 */         is = new GZIPInputStream(is);
/*       */       }
/*  4360 */       return createReader(is);
/*       */     }
/*       */     catch (Exception e) {
/*  4363 */       if (file == null) {
/*  4364 */         throw new RuntimeException("File passed to createReader() was null");
/*       */       }
/*  4366 */       e.printStackTrace();
/*  4367 */       throw new RuntimeException("Couldn't create a reader for " + 
/*  4368 */         file.getAbsolutePath());
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static BufferedReader createReader(InputStream input)
/*       */   {
/*  4380 */     InputStreamReader isr = null;
/*       */     try {
/*  4382 */       isr = new InputStreamReader(input, "UTF-8");
/*       */     } catch (UnsupportedEncodingException localUnsupportedEncodingException) {}
/*  4384 */     return new BufferedReader(isr);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public PrintWriter createWriter(String filename)
/*       */   {
/*  4392 */     return createWriter(saveFile(filename));
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public static PrintWriter createWriter(File file)
/*       */   {
/*       */     try
/*       */     {
/*  4402 */       createPath(file);
/*  4403 */       OutputStream output = new FileOutputStream(file);
/*  4404 */       if (file.getName().toLowerCase().endsWith(".gz")) {
/*  4405 */         output = new GZIPOutputStream(output);
/*       */       }
/*  4407 */       return createWriter(output);
/*       */     }
/*       */     catch (Exception e) {
/*  4410 */       if (file == null) {
/*  4411 */         throw new RuntimeException("File passed to createWriter() was null");
/*       */       }
/*  4413 */       e.printStackTrace();
/*  4414 */       throw new RuntimeException("Couldn't create a writer for " + 
/*  4415 */         file.getAbsolutePath());
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static PrintWriter createWriter(OutputStream output)
/*       */   {
/*       */     try
/*       */     {
/*  4428 */       OutputStreamWriter osw = new OutputStreamWriter(output, "UTF-8");
/*  4429 */       return new PrintWriter(osw);
/*       */     } catch (UnsupportedEncodingException localUnsupportedEncodingException) {}
/*  4431 */     return null;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   /**
/*       */    * @deprecated
/*       */    */
/*       */   public InputStream openStream(String filename)
/*       */   {
/*  4444 */     return createInput(filename);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   private static int int_nf_digits;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   private static boolean int_nf_commas;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   private static NumberFormat float_nf;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   private static int float_nf_left;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   private static int float_nf_right;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   private static boolean float_nf_commas;
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public InputStream createInput(String filename)
/*       */   {
/*  4500 */     InputStream input = createInputRaw(filename);
/*  4501 */     if ((input != null) && (filename.toLowerCase().endsWith(".gz"))) {
/*       */       try {
/*  4503 */         return new GZIPInputStream(input);
/*       */       } catch (IOException e) {
/*  4505 */         e.printStackTrace();
/*  4506 */         return null;
/*       */       }
/*       */     }
/*  4509 */     return input;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public InputStream createInputRaw(String filename)
/*       */   {
/*  4517 */     InputStream stream = null;
/*       */     
/*  4519 */     if (filename == null) { return null;
/*       */     }
/*  4521 */     if (filename.length() == 0)
/*       */     {
/*       */ 
/*  4524 */       return null;
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*  4529 */     if (filename.indexOf(":") != -1) {
/*       */       try {
/*  4531 */         URL url = new URL(filename);
/*  4532 */         return url.openStream();
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       }
/*       */       catch (MalformedURLException localMalformedURLException) {}catch (FileNotFoundException localFileNotFoundException) {}catch (IOException e)
/*       */       {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  4544 */         e.printStackTrace();
/*       */         
/*  4546 */         return null;
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     try
/*       */     {
/*  4557 */       File file = new File(dataPath(filename));
/*  4558 */       if (!file.exists())
/*       */       {
/*  4560 */         file = new File(this.sketchPath, filename);
/*       */       }
/*  4562 */       if (file.isDirectory()) {
/*  4563 */         return null;
/*       */       }
/*  4565 */       if (file.exists()) {
/*       */         try
/*       */         {
/*  4568 */           String filePath = file.getCanonicalPath();
/*  4569 */           String filenameActual = new File(filePath).getName();
/*       */           
/*  4571 */           String filenameShort = new File(filename).getName();
/*       */           
/*       */ 
/*       */ 
/*       */ 
/*  4576 */           if (!filenameActual.equals(filenameShort)) {
/*  4577 */             throw new RuntimeException("This file is named " + 
/*  4578 */               filenameActual + " not " + 
/*  4579 */               filename + ". Rename the file " + 
/*  4580 */               "or change your code.");
/*       */           }
/*       */         }
/*       */         catch (IOException localIOException1) {}
/*       */       }
/*       */       
/*  4586 */       stream = new FileInputStream(file);
/*  4587 */       if (stream != null) { return stream;
/*       */       }
/*       */     }
/*       */     catch (IOException localIOException2) {}catch (SecurityException localSecurityException) {}
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  4598 */     ClassLoader cl = getClass().getClassLoader();
/*       */     
/*       */ 
/*       */ 
/*  4602 */     stream = cl.getResourceAsStream("data/" + filename);
/*  4603 */     if (stream != null) {
/*  4604 */       String cn = stream.getClass().getName();
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*  4609 */       if (!cn.equals("sun.plugin.cache.EmptyInputStream")) {
/*  4610 */         return stream;
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*  4617 */     stream = cl.getResourceAsStream(filename);
/*  4618 */     if (stream != null) {
/*  4619 */       String cn = stream.getClass().getName();
/*  4620 */       if (!cn.equals("sun.plugin.cache.EmptyInputStream")) {
/*  4621 */         return stream;
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */     try
/*       */     {
/*  4630 */       URL base = getDocumentBase();
/*  4631 */       if (base != null) {
/*  4632 */         URL url = new URL(base, filename);
/*  4633 */         URLConnection conn = url.openConnection();
/*  4634 */         return conn.getInputStream();
/*       */       }
/*       */       
/*       */ 
/*       */     }
/*       */     catch (Exception localException1)
/*       */     {
/*       */ 
/*       */       try
/*       */       {
/*       */ 
/*  4645 */         URL base = getDocumentBase();
/*  4646 */         if (base != null) {
/*  4647 */           URL url = new URL(base, "data/" + filename);
/*  4648 */           URLConnection conn = url.openConnection();
/*  4649 */           return conn.getInputStream();
/*       */         }
/*       */         
/*       */ 
/*       */       }
/*       */       catch (Exception localException2)
/*       */       {
/*       */         try
/*       */         {
/*  4658 */           stream = new FileInputStream(dataPath(filename));
/*  4659 */           if (stream != null) return stream;
/*       */         }
/*       */         catch (IOException localIOException3) {
/*       */           try {
/*  4663 */             stream = new FileInputStream(sketchPath(filename));
/*  4664 */             if (stream != null) return stream;
/*       */           }
/*       */           catch (Exception localException3) {
/*       */             try {
/*  4668 */               stream = new FileInputStream(filename);
/*  4669 */               if (stream != null) return stream;
/*       */             }
/*       */             catch (IOException localIOException4) {}
/*       */           }
/*       */         }
/*       */         catch (SecurityException localSecurityException1) {}catch (Exception e)
/*       */         {
/*  4676 */           e.printStackTrace();
/*       */         }
/*       */       } }
/*  4679 */     return null;
/*       */   }
/*       */   
/*       */   public static InputStream createInput(File file)
/*       */   {
/*  4684 */     if (file == null) {
/*  4685 */       throw new IllegalArgumentException("File passed to createInput() was null");
/*       */     }
/*       */     try {
/*  4688 */       InputStream input = new FileInputStream(file);
/*  4689 */       if (file.getName().toLowerCase().endsWith(".gz")) {
/*  4690 */         return new GZIPInputStream(input);
/*       */       }
/*  4692 */       return input;
/*       */     }
/*       */     catch (IOException e) {
/*  4695 */       System.err.println("Could not createInput() for " + file);
/*  4696 */       e.printStackTrace(); }
/*  4697 */     return null;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public byte[] loadBytes(String filename)
/*       */   {
/*  4715 */     InputStream is = createInput(filename);
/*  4716 */     if (is != null) { return loadBytes(is);
/*       */     }
/*  4718 */     System.err.println("The file \"" + filename + "\" " + 
/*  4719 */       "is missing or inaccessible, make sure " + 
/*  4720 */       "the URL is valid or that the file has been " + 
/*  4721 */       "added to your sketch and is readable.");
/*  4722 */     return null;
/*       */   }
/*       */   
/*       */   public static byte[] loadBytes(InputStream input)
/*       */   {
/*       */     try {
/*  4728 */       BufferedInputStream bis = new BufferedInputStream(input);
/*  4729 */       ByteArrayOutputStream out = new ByteArrayOutputStream();
/*       */       
/*  4731 */       int c = bis.read();
/*  4732 */       while (c != -1) {
/*  4733 */         out.write(c);
/*  4734 */         c = bis.read();
/*       */       }
/*  4736 */       return out.toByteArray();
/*       */     }
/*       */     catch (IOException e) {
/*  4739 */       e.printStackTrace();
/*       */     }
/*       */     
/*  4742 */     return null;
/*       */   }
/*       */   
/*       */   public static byte[] loadBytes(File file)
/*       */   {
/*  4747 */     InputStream is = createInput(file);
/*  4748 */     return loadBytes(is);
/*       */   }
/*       */   
/*       */   public static String[] loadStrings(File file)
/*       */   {
/*  4753 */     InputStream is = createInput(file);
/*  4754 */     if (is != null) return loadStrings(is);
/*  4755 */     return null;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public String[] loadStrings(String filename)
/*       */   {
/*  4785 */     InputStream is = createInput(filename);
/*  4786 */     if (is != null) { return loadStrings(is);
/*       */     }
/*  4788 */     System.err.println("The file \"" + filename + "\" " + 
/*  4789 */       "is missing or inaccessible, make sure " + 
/*  4790 */       "the URL is valid or that the file has been " + 
/*  4791 */       "added to your sketch and is readable.");
/*  4792 */     return null;
/*       */   }
/*       */   
/*       */   public static String[] loadStrings(InputStream input)
/*       */   {
/*       */     try {
/*  4798 */       BufferedReader reader = 
/*  4799 */         new BufferedReader(new InputStreamReader(input, "UTF-8"));
/*       */       
/*  4801 */       String[] lines = new String[100];
/*  4802 */       int lineCount = 0;
/*  4803 */       String line = null;
/*  4804 */       while ((line = reader.readLine()) != null) {
/*  4805 */         if (lineCount == lines.length) {
/*  4806 */           String[] temp = new String[lineCount << 1];
/*  4807 */           System.arraycopy(lines, 0, temp, 0, lineCount);
/*  4808 */           lines = temp;
/*       */         }
/*  4810 */         lines[(lineCount++)] = line;
/*       */       }
/*  4812 */       reader.close();
/*       */       
/*  4814 */       if (lineCount == lines.length) {
/*  4815 */         return lines;
/*       */       }
/*       */       
/*       */ 
/*  4819 */       String[] output = new String[lineCount];
/*  4820 */       System.arraycopy(lines, 0, output, 0, lineCount);
/*  4821 */       return output;
/*       */     }
/*       */     catch (IOException e) {
/*  4824 */       e.printStackTrace();
/*       */     }
/*       */     
/*  4827 */     return null;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public OutputStream createOutput(String filename)
/*       */   {
/*  4852 */     return createOutput(saveFile(filename));
/*       */   }
/*       */   
/*       */   public static OutputStream createOutput(File file)
/*       */   {
/*       */     try {
/*  4858 */       createPath(file);
/*  4859 */       FileOutputStream fos = new FileOutputStream(file);
/*  4860 */       if (file.getName().toLowerCase().endsWith(".gz")) {
/*  4861 */         return new GZIPOutputStream(fos);
/*       */       }
/*  4863 */       return fos;
/*       */     }
/*       */     catch (IOException e) {
/*  4866 */       e.printStackTrace();
/*       */     }
/*  4868 */     return null;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public boolean saveStream(String targetFilename, String sourceLocation)
/*       */   {
/*  4878 */     return saveStream(saveFile(targetFilename), sourceLocation);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public boolean saveStream(File targetFile, String sourceLocation)
/*       */   {
/*  4889 */     return saveStream(targetFile, createInputRaw(sourceLocation));
/*       */   }
/*       */   
/*       */   public static boolean saveStream(File targetFile, InputStream sourceStream)
/*       */   {
/*  4894 */     File tempFile = null;
/*       */     try {
/*  4896 */       File parentDir = targetFile.getParentFile();
/*  4897 */       tempFile = File.createTempFile(targetFile.getName(), null, parentDir);
/*       */       
/*  4899 */       BufferedInputStream bis = new BufferedInputStream(sourceStream, 16384);
/*  4900 */       FileOutputStream fos = new FileOutputStream(tempFile);
/*  4901 */       BufferedOutputStream bos = new BufferedOutputStream(fos);
/*       */       
/*  4903 */       byte[] buffer = new byte[' '];
/*       */       int bytesRead;
/*  4905 */       while ((bytesRead = bis.read(buffer)) != -1) { int bytesRead;
/*  4906 */         bos.write(buffer, 0, bytesRead);
/*       */       }
/*       */       
/*  4909 */       bos.flush();
/*  4910 */       bos.close();
/*  4911 */       bos = null;
/*       */       
/*  4913 */       if (!tempFile.renameTo(targetFile)) {
/*  4914 */         System.err.println("Could not rename temporary file " + 
/*  4915 */           tempFile.getAbsolutePath());
/*  4916 */         return false;
/*       */       }
/*  4918 */       return true;
/*       */     }
/*       */     catch (IOException e) {
/*  4921 */       if (tempFile != null) {
/*  4922 */         tempFile.delete();
/*       */       }
/*  4924 */       e.printStackTrace(); }
/*  4925 */     return false;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void saveBytes(String filename, byte[] buffer)
/*       */   {
/*  4938 */     saveBytes(saveFile(filename), buffer);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public static void saveBytes(File file, byte[] buffer)
/*       */   {
/*  4946 */     File tempFile = null;
/*       */     try {
/*  4948 */       File parentDir = file.getParentFile();
/*  4949 */       tempFile = File.createTempFile(file.getName(), null, parentDir);
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  4959 */       OutputStream output = createOutput(tempFile);
/*  4960 */       saveBytes(output, buffer);
/*  4961 */       output.close();
/*  4962 */       output = null;
/*       */       
/*  4964 */       if (!tempFile.renameTo(file)) {
/*  4965 */         System.err.println("Could not rename temporary file " + 
/*  4966 */           tempFile.getAbsolutePath());
/*       */       }
/*       */     }
/*       */     catch (IOException e) {
/*  4970 */       System.err.println("error saving bytes to " + file);
/*  4971 */       if (tempFile != null) {
/*  4972 */         tempFile.delete();
/*       */       }
/*  4974 */       e.printStackTrace();
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   public static void saveBytes(OutputStream output, byte[] buffer)
/*       */   {
/*       */     try
/*       */     {
/*  4984 */       output.write(buffer);
/*  4985 */       output.flush();
/*       */     }
/*       */     catch (IOException e) {
/*  4988 */       e.printStackTrace();
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */   public void saveStrings(String filename, String[] strings)
/*       */   {
/*  4995 */     saveStrings(saveFile(filename), strings);
/*       */   }
/*       */   
/*       */   public static void saveStrings(File file, String[] strings)
/*       */   {
/*  5000 */     saveStrings(createOutput(file), strings);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static void saveStrings(OutputStream output, String[] strings)
/*       */   {
/*  5020 */     PrintWriter writer = createWriter(output);
/*  5021 */     for (int i = 0; i < strings.length; i++) {
/*  5022 */       writer.println(strings[i]);
/*       */     }
/*  5024 */     writer.flush();
/*  5025 */     writer.close();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public String sketchPath(String where)
/*       */   {
/*  5047 */     if (this.sketchPath == null) {
/*  5048 */       return where;
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     try
/*       */     {
/*  5057 */       if (new File(where).isAbsolute()) return where;
/*       */     }
/*       */     catch (Exception localException) {}
/*  5060 */     return this.sketchPath + File.separator + where;
/*       */   }
/*       */   
/*       */   public File sketchFile(String where)
/*       */   {
/*  5065 */     return new File(sketchPath(where));
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public String savePath(String where)
/*       */   {
/*  5083 */     if (where == null) return null;
/*  5084 */     String filename = sketchPath(where);
/*  5085 */     createPath(filename);
/*  5086 */     return filename;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public File saveFile(String where)
/*       */   {
/*  5094 */     return new File(savePath(where));
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public String dataPath(String where)
/*       */   {
/*  5113 */     if (new File(where).isAbsolute()) { return where;
/*       */     }
/*  5115 */     return this.sketchPath + File.separator + "data" + File.separator + where;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public File dataFile(String where)
/*       */   {
/*  5124 */     return new File(dataPath(where));
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static void createPath(String path)
/*       */   {
/*  5134 */     createPath(new File(path));
/*       */   }
/*       */   
/*       */   public static void createPath(File file)
/*       */   {
/*       */     try {
/*  5140 */       String parent = file.getParent();
/*  5141 */       if (parent != null) {
/*  5142 */         File unit = new File(parent);
/*  5143 */         if (!unit.exists()) unit.mkdirs();
/*       */       }
/*       */     } catch (SecurityException se) {
/*  5146 */       System.err.println("You don't have permissions to create " + 
/*  5147 */         file.getAbsolutePath());
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static byte[] sort(byte[] what)
/*       */   {
/*  5159 */     return sort(what, what.length);
/*       */   }
/*       */   
/*       */   public static byte[] sort(byte[] what, int count)
/*       */   {
/*  5164 */     byte[] outgoing = new byte[what.length];
/*  5165 */     System.arraycopy(what, 0, outgoing, 0, what.length);
/*  5166 */     Arrays.sort(outgoing, 0, count);
/*  5167 */     return outgoing;
/*       */   }
/*       */   
/*       */   public static char[] sort(char[] what)
/*       */   {
/*  5172 */     return sort(what, what.length);
/*       */   }
/*       */   
/*       */   public static char[] sort(char[] what, int count)
/*       */   {
/*  5177 */     char[] outgoing = new char[what.length];
/*  5178 */     System.arraycopy(what, 0, outgoing, 0, what.length);
/*  5179 */     Arrays.sort(outgoing, 0, count);
/*  5180 */     return outgoing;
/*       */   }
/*       */   
/*       */   public static int[] sort(int[] what)
/*       */   {
/*  5185 */     return sort(what, what.length);
/*       */   }
/*       */   
/*       */   public static int[] sort(int[] what, int count)
/*       */   {
/*  5190 */     int[] outgoing = new int[what.length];
/*  5191 */     System.arraycopy(what, 0, outgoing, 0, what.length);
/*  5192 */     Arrays.sort(outgoing, 0, count);
/*  5193 */     return outgoing;
/*       */   }
/*       */   
/*       */   public static float[] sort(float[] what)
/*       */   {
/*  5198 */     return sort(what, what.length);
/*       */   }
/*       */   
/*       */   public static float[] sort(float[] what, int count)
/*       */   {
/*  5203 */     float[] outgoing = new float[what.length];
/*  5204 */     System.arraycopy(what, 0, outgoing, 0, what.length);
/*  5205 */     Arrays.sort(outgoing, 0, count);
/*  5206 */     return outgoing;
/*       */   }
/*       */   
/*       */   public static String[] sort(String[] what)
/*       */   {
/*  5211 */     return sort(what, what.length);
/*       */   }
/*       */   
/*       */   public static String[] sort(String[] what, int count)
/*       */   {
/*  5216 */     String[] outgoing = new String[what.length];
/*  5217 */     System.arraycopy(what, 0, outgoing, 0, what.length);
/*  5218 */     Arrays.sort(outgoing, 0, count);
/*  5219 */     return outgoing;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static void arrayCopy(Object src, int srcPosition, Object dst, int dstPosition, int length)
/*       */   {
/*  5237 */     System.arraycopy(src, srcPosition, dst, dstPosition, length);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static void arrayCopy(Object src, Object dst, int length)
/*       */   {
/*  5246 */     System.arraycopy(src, 0, dst, 0, length);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static void arrayCopy(Object src, Object dst)
/*       */   {
/*  5256 */     System.arraycopy(src, 0, dst, 0, Array.getLength(src));
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   /**
/*       */    * @deprecated
/*       */    */
/*       */   public static void arraycopy(Object src, int srcPosition, Object dst, int dstPosition, int length)
/*       */   {
/*  5267 */     System.arraycopy(src, srcPosition, dst, dstPosition, length);
/*       */   }
/*       */   
/*       */   /**
/*       */    * @deprecated
/*       */    */
/*       */   public static void arraycopy(Object src, Object dst, int length) {
/*  5274 */     System.arraycopy(src, 0, dst, 0, length);
/*       */   }
/*       */   
/*       */   /**
/*       */    * @deprecated
/*       */    */
/*       */   public static void arraycopy(Object src, Object dst) {
/*  5281 */     System.arraycopy(src, 0, dst, 0, Array.getLength(src));
/*       */   }
/*       */   
/*       */ 
/*       */   public static boolean[] expand(boolean[] list)
/*       */   {
/*  5287 */     return expand(list, list.length << 1);
/*       */   }
/*       */   
/*       */   public static boolean[] expand(boolean[] list, int newSize) {
/*  5291 */     boolean[] temp = new boolean[newSize];
/*  5292 */     System.arraycopy(list, 0, temp, 0, Math.min(newSize, list.length));
/*  5293 */     return temp;
/*       */   }
/*       */   
/*       */   public static byte[] expand(byte[] list)
/*       */   {
/*  5298 */     return expand(list, list.length << 1);
/*       */   }
/*       */   
/*       */   public static byte[] expand(byte[] list, int newSize) {
/*  5302 */     byte[] temp = new byte[newSize];
/*  5303 */     System.arraycopy(list, 0, temp, 0, Math.min(newSize, list.length));
/*  5304 */     return temp;
/*       */   }
/*       */   
/*       */   public static char[] expand(char[] list)
/*       */   {
/*  5309 */     return expand(list, list.length << 1);
/*       */   }
/*       */   
/*       */   public static char[] expand(char[] list, int newSize) {
/*  5313 */     char[] temp = new char[newSize];
/*  5314 */     System.arraycopy(list, 0, temp, 0, Math.min(newSize, list.length));
/*  5315 */     return temp;
/*       */   }
/*       */   
/*       */   public static int[] expand(int[] list)
/*       */   {
/*  5320 */     return expand(list, list.length << 1);
/*       */   }
/*       */   
/*       */   public static int[] expand(int[] list, int newSize) {
/*  5324 */     int[] temp = new int[newSize];
/*  5325 */     System.arraycopy(list, 0, temp, 0, Math.min(newSize, list.length));
/*  5326 */     return temp;
/*       */   }
/*       */   
/*       */   public static float[] expand(float[] list)
/*       */   {
/*  5331 */     return expand(list, list.length << 1);
/*       */   }
/*       */   
/*       */   public static float[] expand(float[] list, int newSize) {
/*  5335 */     float[] temp = new float[newSize];
/*  5336 */     System.arraycopy(list, 0, temp, 0, Math.min(newSize, list.length));
/*  5337 */     return temp;
/*       */   }
/*       */   
/*       */   public static String[] expand(String[] list)
/*       */   {
/*  5342 */     return expand(list, list.length << 1);
/*       */   }
/*       */   
/*       */   public static String[] expand(String[] list, int newSize) {
/*  5346 */     String[] temp = new String[newSize];
/*       */     
/*  5348 */     System.arraycopy(list, 0, temp, 0, Math.min(newSize, list.length));
/*  5349 */     return temp;
/*       */   }
/*       */   
/*       */   public static Object expand(Object array)
/*       */   {
/*  5354 */     return expand(array, Array.getLength(array) << 1);
/*       */   }
/*       */   
/*       */   public static Object expand(Object list, int newSize) {
/*  5358 */     Class<?> type = list.getClass().getComponentType();
/*  5359 */     Object temp = Array.newInstance(type, newSize);
/*  5360 */     System.arraycopy(list, 0, temp, 0, 
/*  5361 */       Math.min(Array.getLength(list), newSize));
/*  5362 */     return temp;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static byte[] append(byte[] b, byte value)
/*       */   {
/*  5373 */     b = expand(b, b.length + 1);
/*  5374 */     b[(b.length - 1)] = value;
/*  5375 */     return b;
/*       */   }
/*       */   
/*       */   public static char[] append(char[] b, char value) {
/*  5379 */     b = expand(b, b.length + 1);
/*  5380 */     b[(b.length - 1)] = value;
/*  5381 */     return b;
/*       */   }
/*       */   
/*       */   public static int[] append(int[] b, int value) {
/*  5385 */     b = expand(b, b.length + 1);
/*  5386 */     b[(b.length - 1)] = value;
/*  5387 */     return b;
/*       */   }
/*       */   
/*       */   public static float[] append(float[] b, float value) {
/*  5391 */     b = expand(b, b.length + 1);
/*  5392 */     b[(b.length - 1)] = value;
/*  5393 */     return b;
/*       */   }
/*       */   
/*       */   public static String[] append(String[] b, String value) {
/*  5397 */     b = expand(b, b.length + 1);
/*  5398 */     b[(b.length - 1)] = value;
/*  5399 */     return b;
/*       */   }
/*       */   
/*       */   public static Object append(Object b, Object value) {
/*  5403 */     int length = Array.getLength(b);
/*  5404 */     b = expand(b, length + 1);
/*  5405 */     Array.set(b, length, value);
/*  5406 */     return b;
/*       */   }
/*       */   
/*       */ 
/*       */   public static boolean[] shorten(boolean[] list)
/*       */   {
/*  5412 */     return subset(list, 0, list.length - 1);
/*       */   }
/*       */   
/*       */   public static byte[] shorten(byte[] list) {
/*  5416 */     return subset(list, 0, list.length - 1);
/*       */   }
/*       */   
/*       */   public static char[] shorten(char[] list) {
/*  5420 */     return subset(list, 0, list.length - 1);
/*       */   }
/*       */   
/*       */   public static int[] shorten(int[] list) {
/*  5424 */     return subset(list, 0, list.length - 1);
/*       */   }
/*       */   
/*       */   public static float[] shorten(float[] list) {
/*  5428 */     return subset(list, 0, list.length - 1);
/*       */   }
/*       */   
/*       */   public static String[] shorten(String[] list) {
/*  5432 */     return subset(list, 0, list.length - 1);
/*       */   }
/*       */   
/*       */   public static Object shorten(Object list) {
/*  5436 */     int length = Array.getLength(list);
/*  5437 */     return subset(list, 0, length - 1);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   public static final boolean[] splice(boolean[] list, boolean v, int index)
/*       */   {
/*  5444 */     boolean[] outgoing = new boolean[list.length + 1];
/*  5445 */     System.arraycopy(list, 0, outgoing, 0, index);
/*  5446 */     outgoing[index] = v;
/*  5447 */     System.arraycopy(list, index, outgoing, index + 1, 
/*  5448 */       list.length - index);
/*  5449 */     return outgoing;
/*       */   }
/*       */   
/*       */   public static final boolean[] splice(boolean[] list, boolean[] v, int index)
/*       */   {
/*  5454 */     boolean[] outgoing = new boolean[list.length + v.length];
/*  5455 */     System.arraycopy(list, 0, outgoing, 0, index);
/*  5456 */     System.arraycopy(v, 0, outgoing, index, v.length);
/*  5457 */     System.arraycopy(list, index, outgoing, index + v.length, 
/*  5458 */       list.length - index);
/*  5459 */     return outgoing;
/*       */   }
/*       */   
/*       */ 
/*       */   public static final byte[] splice(byte[] list, byte v, int index)
/*       */   {
/*  5465 */     byte[] outgoing = new byte[list.length + 1];
/*  5466 */     System.arraycopy(list, 0, outgoing, 0, index);
/*  5467 */     outgoing[index] = v;
/*  5468 */     System.arraycopy(list, index, outgoing, index + 1, 
/*  5469 */       list.length - index);
/*  5470 */     return outgoing;
/*       */   }
/*       */   
/*       */   public static final byte[] splice(byte[] list, byte[] v, int index)
/*       */   {
/*  5475 */     byte[] outgoing = new byte[list.length + v.length];
/*  5476 */     System.arraycopy(list, 0, outgoing, 0, index);
/*  5477 */     System.arraycopy(v, 0, outgoing, index, v.length);
/*  5478 */     System.arraycopy(list, index, outgoing, index + v.length, 
/*  5479 */       list.length - index);
/*  5480 */     return outgoing;
/*       */   }
/*       */   
/*       */ 
/*       */   public static final char[] splice(char[] list, char v, int index)
/*       */   {
/*  5486 */     char[] outgoing = new char[list.length + 1];
/*  5487 */     System.arraycopy(list, 0, outgoing, 0, index);
/*  5488 */     outgoing[index] = v;
/*  5489 */     System.arraycopy(list, index, outgoing, index + 1, 
/*  5490 */       list.length - index);
/*  5491 */     return outgoing;
/*       */   }
/*       */   
/*       */   public static final char[] splice(char[] list, char[] v, int index)
/*       */   {
/*  5496 */     char[] outgoing = new char[list.length + v.length];
/*  5497 */     System.arraycopy(list, 0, outgoing, 0, index);
/*  5498 */     System.arraycopy(v, 0, outgoing, index, v.length);
/*  5499 */     System.arraycopy(list, index, outgoing, index + v.length, 
/*  5500 */       list.length - index);
/*  5501 */     return outgoing;
/*       */   }
/*       */   
/*       */ 
/*       */   public static final int[] splice(int[] list, int v, int index)
/*       */   {
/*  5507 */     int[] outgoing = new int[list.length + 1];
/*  5508 */     System.arraycopy(list, 0, outgoing, 0, index);
/*  5509 */     outgoing[index] = v;
/*  5510 */     System.arraycopy(list, index, outgoing, index + 1, 
/*  5511 */       list.length - index);
/*  5512 */     return outgoing;
/*       */   }
/*       */   
/*       */   public static final int[] splice(int[] list, int[] v, int index)
/*       */   {
/*  5517 */     int[] outgoing = new int[list.length + v.length];
/*  5518 */     System.arraycopy(list, 0, outgoing, 0, index);
/*  5519 */     System.arraycopy(v, 0, outgoing, index, v.length);
/*  5520 */     System.arraycopy(list, index, outgoing, index + v.length, 
/*  5521 */       list.length - index);
/*  5522 */     return outgoing;
/*       */   }
/*       */   
/*       */ 
/*       */   public static final float[] splice(float[] list, float v, int index)
/*       */   {
/*  5528 */     float[] outgoing = new float[list.length + 1];
/*  5529 */     System.arraycopy(list, 0, outgoing, 0, index);
/*  5530 */     outgoing[index] = v;
/*  5531 */     System.arraycopy(list, index, outgoing, index + 1, 
/*  5532 */       list.length - index);
/*  5533 */     return outgoing;
/*       */   }
/*       */   
/*       */   public static final float[] splice(float[] list, float[] v, int index)
/*       */   {
/*  5538 */     float[] outgoing = new float[list.length + v.length];
/*  5539 */     System.arraycopy(list, 0, outgoing, 0, index);
/*  5540 */     System.arraycopy(v, 0, outgoing, index, v.length);
/*  5541 */     System.arraycopy(list, index, outgoing, index + v.length, 
/*  5542 */       list.length - index);
/*  5543 */     return outgoing;
/*       */   }
/*       */   
/*       */ 
/*       */   public static final String[] splice(String[] list, String v, int index)
/*       */   {
/*  5549 */     String[] outgoing = new String[list.length + 1];
/*  5550 */     System.arraycopy(list, 0, outgoing, 0, index);
/*  5551 */     outgoing[index] = v;
/*  5552 */     System.arraycopy(list, index, outgoing, index + 1, 
/*  5553 */       list.length - index);
/*  5554 */     return outgoing;
/*       */   }
/*       */   
/*       */   public static final String[] splice(String[] list, String[] v, int index)
/*       */   {
/*  5559 */     String[] outgoing = new String[list.length + v.length];
/*  5560 */     System.arraycopy(list, 0, outgoing, 0, index);
/*  5561 */     System.arraycopy(v, 0, outgoing, index, v.length);
/*  5562 */     System.arraycopy(list, index, outgoing, index + v.length, 
/*  5563 */       list.length - index);
/*  5564 */     return outgoing;
/*       */   }
/*       */   
/*       */   public static final Object splice(Object list, Object v, int index)
/*       */   {
/*  5569 */     Object[] outgoing = (Object[])null;
/*  5570 */     int length = Array.getLength(list);
/*       */     
/*       */ 
/*  5573 */     if (v.getClass().getName().charAt(0) == '[') {
/*  5574 */       int vlength = Array.getLength(v);
/*  5575 */       outgoing = new Object[length + vlength];
/*  5576 */       System.arraycopy(list, 0, outgoing, 0, index);
/*  5577 */       System.arraycopy(v, 0, outgoing, index, vlength);
/*  5578 */       System.arraycopy(list, index, outgoing, index + vlength, length - index);
/*       */     }
/*       */     else {
/*  5581 */       outgoing = new Object[length + 1];
/*  5582 */       System.arraycopy(list, 0, outgoing, 0, index);
/*  5583 */       Array.set(outgoing, index, v);
/*  5584 */       System.arraycopy(list, index, outgoing, index + 1, length - index);
/*       */     }
/*  5586 */     return outgoing;
/*       */   }
/*       */   
/*       */ 
/*       */   public static boolean[] subset(boolean[] list, int start)
/*       */   {
/*  5592 */     return subset(list, start, list.length - start);
/*       */   }
/*       */   
/*       */   public static boolean[] subset(boolean[] list, int start, int count) {
/*  5596 */     boolean[] output = new boolean[count];
/*  5597 */     System.arraycopy(list, start, output, 0, count);
/*  5598 */     return output;
/*       */   }
/*       */   
/*       */   public static byte[] subset(byte[] list, int start)
/*       */   {
/*  5603 */     return subset(list, start, list.length - start);
/*       */   }
/*       */   
/*       */   public static byte[] subset(byte[] list, int start, int count) {
/*  5607 */     byte[] output = new byte[count];
/*  5608 */     System.arraycopy(list, start, output, 0, count);
/*  5609 */     return output;
/*       */   }
/*       */   
/*       */   public static char[] subset(char[] list, int start)
/*       */   {
/*  5614 */     return subset(list, start, list.length - start);
/*       */   }
/*       */   
/*       */   public static char[] subset(char[] list, int start, int count) {
/*  5618 */     char[] output = new char[count];
/*  5619 */     System.arraycopy(list, start, output, 0, count);
/*  5620 */     return output;
/*       */   }
/*       */   
/*       */   public static int[] subset(int[] list, int start)
/*       */   {
/*  5625 */     return subset(list, start, list.length - start);
/*       */   }
/*       */   
/*       */   public static int[] subset(int[] list, int start, int count) {
/*  5629 */     int[] output = new int[count];
/*  5630 */     System.arraycopy(list, start, output, 0, count);
/*  5631 */     return output;
/*       */   }
/*       */   
/*       */   public static float[] subset(float[] list, int start)
/*       */   {
/*  5636 */     return subset(list, start, list.length - start);
/*       */   }
/*       */   
/*       */   public static float[] subset(float[] list, int start, int count) {
/*  5640 */     float[] output = new float[count];
/*  5641 */     System.arraycopy(list, start, output, 0, count);
/*  5642 */     return output;
/*       */   }
/*       */   
/*       */   public static String[] subset(String[] list, int start)
/*       */   {
/*  5647 */     return subset(list, start, list.length - start);
/*       */   }
/*       */   
/*       */   public static String[] subset(String[] list, int start, int count) {
/*  5651 */     String[] output = new String[count];
/*  5652 */     System.arraycopy(list, start, output, 0, count);
/*  5653 */     return output;
/*       */   }
/*       */   
/*       */   public static Object subset(Object list, int start)
/*       */   {
/*  5658 */     int length = Array.getLength(list);
/*  5659 */     return subset(list, start, length - start);
/*       */   }
/*       */   
/*       */   public static Object subset(Object list, int start, int count) {
/*  5663 */     Class<?> type = list.getClass().getComponentType();
/*  5664 */     Object outgoing = Array.newInstance(type, count);
/*  5665 */     System.arraycopy(list, start, outgoing, 0, count);
/*  5666 */     return outgoing;
/*       */   }
/*       */   
/*       */ 
/*       */   public static boolean[] concat(boolean[] a, boolean[] b)
/*       */   {
/*  5672 */     boolean[] c = new boolean[a.length + b.length];
/*  5673 */     System.arraycopy(a, 0, c, 0, a.length);
/*  5674 */     System.arraycopy(b, 0, c, a.length, b.length);
/*  5675 */     return c;
/*       */   }
/*       */   
/*       */   public static byte[] concat(byte[] a, byte[] b) {
/*  5679 */     byte[] c = new byte[a.length + b.length];
/*  5680 */     System.arraycopy(a, 0, c, 0, a.length);
/*  5681 */     System.arraycopy(b, 0, c, a.length, b.length);
/*  5682 */     return c;
/*       */   }
/*       */   
/*       */   public static char[] concat(char[] a, char[] b) {
/*  5686 */     char[] c = new char[a.length + b.length];
/*  5687 */     System.arraycopy(a, 0, c, 0, a.length);
/*  5688 */     System.arraycopy(b, 0, c, a.length, b.length);
/*  5689 */     return c;
/*       */   }
/*       */   
/*       */   public static int[] concat(int[] a, int[] b) {
/*  5693 */     int[] c = new int[a.length + b.length];
/*  5694 */     System.arraycopy(a, 0, c, 0, a.length);
/*  5695 */     System.arraycopy(b, 0, c, a.length, b.length);
/*  5696 */     return c;
/*       */   }
/*       */   
/*       */   public static float[] concat(float[] a, float[] b) {
/*  5700 */     float[] c = new float[a.length + b.length];
/*  5701 */     System.arraycopy(a, 0, c, 0, a.length);
/*  5702 */     System.arraycopy(b, 0, c, a.length, b.length);
/*  5703 */     return c;
/*       */   }
/*       */   
/*       */   public static String[] concat(String[] a, String[] b) {
/*  5707 */     String[] c = new String[a.length + b.length];
/*  5708 */     System.arraycopy(a, 0, c, 0, a.length);
/*  5709 */     System.arraycopy(b, 0, c, a.length, b.length);
/*  5710 */     return c;
/*       */   }
/*       */   
/*       */   public static Object concat(Object a, Object b) {
/*  5714 */     Class<?> type = a.getClass().getComponentType();
/*  5715 */     int alength = Array.getLength(a);
/*  5716 */     int blength = Array.getLength(b);
/*  5717 */     Object outgoing = Array.newInstance(type, alength + blength);
/*  5718 */     System.arraycopy(a, 0, outgoing, 0, alength);
/*  5719 */     System.arraycopy(b, 0, outgoing, alength, blength);
/*  5720 */     return outgoing;
/*       */   }
/*       */   
/*       */ 
/*       */   public static boolean[] reverse(boolean[] list)
/*       */   {
/*  5726 */     boolean[] outgoing = new boolean[list.length];
/*  5727 */     int length1 = list.length - 1;
/*  5728 */     for (int i = 0; i < list.length; i++) {
/*  5729 */       outgoing[i] = list[(length1 - i)];
/*       */     }
/*  5731 */     return outgoing;
/*       */   }
/*       */   
/*       */   public static byte[] reverse(byte[] list) {
/*  5735 */     byte[] outgoing = new byte[list.length];
/*  5736 */     int length1 = list.length - 1;
/*  5737 */     for (int i = 0; i < list.length; i++) {
/*  5738 */       outgoing[i] = list[(length1 - i)];
/*       */     }
/*  5740 */     return outgoing;
/*       */   }
/*       */   
/*       */   public static char[] reverse(char[] list) {
/*  5744 */     char[] outgoing = new char[list.length];
/*  5745 */     int length1 = list.length - 1;
/*  5746 */     for (int i = 0; i < list.length; i++) {
/*  5747 */       outgoing[i] = list[(length1 - i)];
/*       */     }
/*  5749 */     return outgoing;
/*       */   }
/*       */   
/*       */   public static int[] reverse(int[] list) {
/*  5753 */     int[] outgoing = new int[list.length];
/*  5754 */     int length1 = list.length - 1;
/*  5755 */     for (int i = 0; i < list.length; i++) {
/*  5756 */       outgoing[i] = list[(length1 - i)];
/*       */     }
/*  5758 */     return outgoing;
/*       */   }
/*       */   
/*       */   public static float[] reverse(float[] list) {
/*  5762 */     float[] outgoing = new float[list.length];
/*  5763 */     int length1 = list.length - 1;
/*  5764 */     for (int i = 0; i < list.length; i++) {
/*  5765 */       outgoing[i] = list[(length1 - i)];
/*       */     }
/*  5767 */     return outgoing;
/*       */   }
/*       */   
/*       */   public static String[] reverse(String[] list) {
/*  5771 */     String[] outgoing = new String[list.length];
/*  5772 */     int length1 = list.length - 1;
/*  5773 */     for (int i = 0; i < list.length; i++) {
/*  5774 */       outgoing[i] = list[(length1 - i)];
/*       */     }
/*  5776 */     return outgoing;
/*       */   }
/*       */   
/*       */   public static Object reverse(Object list) {
/*  5780 */     Class<?> type = list.getClass().getComponentType();
/*  5781 */     int length = Array.getLength(list);
/*  5782 */     Object outgoing = Array.newInstance(type, length);
/*  5783 */     for (int i = 0; i < length; i++) {
/*  5784 */       Array.set(outgoing, i, Array.get(list, length - 1 - i));
/*       */     }
/*  5786 */     return outgoing;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static String trim(String str)
/*       */   {
/*  5802 */     return str.replace(' ', ' ').trim();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static String[] trim(String[] array)
/*       */   {
/*  5811 */     String[] outgoing = new String[array.length];
/*  5812 */     for (int i = 0; i < array.length; i++) {
/*  5813 */       outgoing[i] = array[i].replace(' ', ' ').trim();
/*       */     }
/*  5815 */     return outgoing;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static String join(String[] str, char separator)
/*       */   {
/*  5824 */     return join(str, String.valueOf(separator));
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static String join(String[] str, String separator)
/*       */   {
/*  5840 */     StringBuffer buffer = new StringBuffer();
/*  5841 */     for (int i = 0; i < str.length; i++) {
/*  5842 */       if (i != 0) buffer.append(separator);
/*  5843 */       buffer.append(str[i]);
/*       */     }
/*  5845 */     return buffer.toString();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static String[] splitTokens(String what)
/*       */   {
/*  5865 */     return splitTokens(what, " \t\n\r\f ");
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static String[] splitTokens(String what, String delim)
/*       */   {
/*  5884 */     StringTokenizer toker = new StringTokenizer(what, delim);
/*  5885 */     String[] pieces = new String[toker.countTokens()];
/*       */     
/*  5887 */     int index = 0;
/*  5888 */     while (toker.hasMoreTokens()) {
/*  5889 */       pieces[(index++)] = toker.nextToken();
/*       */     }
/*  5891 */     return pieces;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static String[] split(String what, char delim)
/*       */   {
/*  5909 */     if (what == null) { return null;
/*       */     }
/*       */     
/*  5912 */     char[] chars = what.toCharArray();
/*  5913 */     int splitCount = 0;
/*  5914 */     for (int i = 0; i < chars.length; i++) {
/*  5915 */       if (chars[i] == delim) { splitCount++;
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  5923 */     if (splitCount == 0) {
/*  5924 */       String[] splits = new String[1];
/*  5925 */       splits[0] = new String(what);
/*  5926 */       return splits;
/*       */     }
/*       */     
/*  5929 */     String[] splits = new String[splitCount + 1];
/*  5930 */     int splitIndex = 0;
/*  5931 */     int startIndex = 0;
/*  5932 */     for (int i = 0; i < chars.length; i++) {
/*  5933 */       if (chars[i] == delim) {
/*  5934 */         splits[(splitIndex++)] = 
/*  5935 */           new String(chars, startIndex, i - startIndex);
/*  5936 */         startIndex = i + 1;
/*       */       }
/*       */     }
/*       */     
/*  5940 */     splits[splitIndex] = 
/*  5941 */       new String(chars, startIndex, chars.length - startIndex);
/*       */     
/*  5943 */     return splits;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static String[] split(String what, String delim)
/*       */   {
/*  5954 */     ArrayList<String> items = new ArrayList();
/*       */     
/*  5956 */     int offset = 0;
/*  5957 */     int index; while ((index = what.indexOf(delim, offset)) != -1) { int index;
/*  5958 */       items.add(what.substring(offset, index));
/*  5959 */       offset = index + delim.length();
/*       */     }
/*  5961 */     items.add(what.substring(offset));
/*  5962 */     String[] outgoing = new String[items.size()];
/*  5963 */     items.toArray(outgoing);
/*  5964 */     return outgoing;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   static Pattern matchPattern(String regexp)
/*       */   {
/*  5971 */     Pattern p = null;
/*  5972 */     if (matchPatterns == null) {
/*  5973 */       matchPatterns = new HashMap();
/*       */     } else {
/*  5975 */       p = (Pattern)matchPatterns.get(regexp);
/*       */     }
/*  5977 */     if (p == null) {
/*  5978 */       if (matchPatterns.size() == 10)
/*       */       {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  5986 */         matchPatterns.clear();
/*       */       }
/*  5988 */       p = Pattern.compile(regexp, 40);
/*  5989 */       matchPatterns.put(regexp, p);
/*       */     }
/*  5991 */     return p;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static String[] match(String what, String regexp)
/*       */   {
/*  6006 */     Pattern p = matchPattern(regexp);
/*  6007 */     Matcher m = p.matcher(what);
/*  6008 */     if (m.find()) {
/*  6009 */       int count = m.groupCount() + 1;
/*  6010 */       String[] groups = new String[count];
/*  6011 */       for (int i = 0; i < count; i++) {
/*  6012 */         groups[i] = m.group(i);
/*       */       }
/*  6014 */       return groups;
/*       */     }
/*  6016 */     return null;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static String[][] matchAll(String what, String regexp)
/*       */   {
/*  6025 */     Pattern p = matchPattern(regexp);
/*  6026 */     Matcher m = p.matcher(what);
/*  6027 */     ArrayList<String[]> results = new ArrayList();
/*  6028 */     int count = m.groupCount() + 1;
/*  6029 */     while (m.find()) {
/*  6030 */       String[] groups = new String[count];
/*  6031 */       for (int i = 0; i < count; i++) {
/*  6032 */         groups[i] = m.group(i);
/*       */       }
/*  6034 */       results.add(groups);
/*       */     }
/*  6036 */     if (results.isEmpty()) {
/*  6037 */       return null;
/*       */     }
/*  6039 */     String[][] matches = new String[results.size()][count];
/*  6040 */     for (int i = 0; i < matches.length; i++) {
/*  6041 */       matches[i] = ((String[])results.get(i));
/*       */     }
/*  6043 */     return matches;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final boolean parseBoolean(int what)
/*       */   {
/*  6071 */     return what != 0;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final boolean parseBoolean(String what)
/*       */   {
/*  6086 */     return new Boolean(what).booleanValue();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final boolean[] parseBoolean(byte[] what)
/*       */   {
/*  6110 */     boolean[] outgoing = new boolean[what.length];
/*  6111 */     for (int i = 0; i < what.length; i++) {
/*  6112 */       outgoing[i] = (what[i] != 0 ? 1 : false);
/*       */     }
/*  6114 */     return outgoing;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final boolean[] parseBoolean(int[] what)
/*       */   {
/*  6123 */     boolean[] outgoing = new boolean[what.length];
/*  6124 */     for (int i = 0; i < what.length; i++) {
/*  6125 */       outgoing[i] = (what[i] != 0 ? 1 : false);
/*       */     }
/*  6127 */     return outgoing;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final boolean[] parseBoolean(String[] what)
/*       */   {
/*  6142 */     boolean[] outgoing = new boolean[what.length];
/*  6143 */     for (int i = 0; i < what.length; i++) {
/*  6144 */       outgoing[i] = new Boolean(what[i]).booleanValue();
/*       */     }
/*  6146 */     return outgoing;
/*       */   }
/*       */   
/*       */ 
/*       */   public static final byte parseByte(boolean what)
/*       */   {
/*  6152 */     return what ? 1 : 0;
/*       */   }
/*       */   
/*       */   public static final byte parseByte(char what) {
/*  6156 */     return (byte)what;
/*       */   }
/*       */   
/*       */   public static final byte parseByte(int what) {
/*  6160 */     return (byte)what;
/*       */   }
/*       */   
/*       */   public static final byte parseByte(float what) {
/*  6164 */     return (byte)(int)what;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final byte[] parseByte(boolean[] what)
/*       */   {
/*  6177 */     byte[] outgoing = new byte[what.length];
/*  6178 */     for (int i = 0; i < what.length; i++) {
/*  6179 */       outgoing[i] = (what[i] != 0 ? 1 : 0);
/*       */     }
/*  6181 */     return outgoing;
/*       */   }
/*       */   
/*       */   public static final byte[] parseByte(char[] what) {
/*  6185 */     byte[] outgoing = new byte[what.length];
/*  6186 */     for (int i = 0; i < what.length; i++) {
/*  6187 */       outgoing[i] = ((byte)what[i]);
/*       */     }
/*  6189 */     return outgoing;
/*       */   }
/*       */   
/*       */   public static final byte[] parseByte(int[] what) {
/*  6193 */     byte[] outgoing = new byte[what.length];
/*  6194 */     for (int i = 0; i < what.length; i++) {
/*  6195 */       outgoing[i] = ((byte)what[i]);
/*       */     }
/*  6197 */     return outgoing;
/*       */   }
/*       */   
/*       */   public static final byte[] parseByte(float[] what) {
/*  6201 */     byte[] outgoing = new byte[what.length];
/*  6202 */     for (int i = 0; i < what.length; i++) {
/*  6203 */       outgoing[i] = ((byte)(int)what[i]);
/*       */     }
/*  6205 */     return outgoing;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final char parseChar(byte what)
/*       */   {
/*  6227 */     return (char)(what & 0xFF);
/*       */   }
/*       */   
/*       */   public static final char parseChar(int what) {
/*  6231 */     return (char)what;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final char[] parseChar(byte[] what)
/*       */   {
/*  6257 */     char[] outgoing = new char[what.length];
/*  6258 */     for (int i = 0; i < what.length; i++) {
/*  6259 */       outgoing[i] = ((char)(what[i] & 0xFF));
/*       */     }
/*  6261 */     return outgoing;
/*       */   }
/*       */   
/*       */   public static final char[] parseChar(int[] what) {
/*  6265 */     char[] outgoing = new char[what.length];
/*  6266 */     for (int i = 0; i < what.length; i++) {
/*  6267 */       outgoing[i] = ((char)what[i]);
/*       */     }
/*  6269 */     return outgoing;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final int parseInt(boolean what)
/*       */   {
/*  6293 */     return what ? 1 : 0;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   public static final int parseInt(byte what)
/*       */   {
/*  6300 */     return what & 0xFF;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final int parseInt(char what)
/*       */   {
/*  6309 */     return what;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   public static final int parseInt(float what)
/*       */   {
/*  6316 */     return (int)what;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   public static final int parseInt(String what)
/*       */   {
/*  6323 */     return parseInt(what, 0);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   public static final int parseInt(String what, int otherwise)
/*       */   {
/*       */     try
/*       */     {
/*  6332 */       int offset = what.indexOf('.');
/*  6333 */       if (offset == -1) {
/*  6334 */         return Integer.parseInt(what);
/*       */       }
/*  6336 */       return Integer.parseInt(what.substring(0, offset));
/*       */     }
/*       */     catch (NumberFormatException localNumberFormatException) {}
/*  6339 */     return otherwise;
/*       */   }
/*       */   
/*       */ 
/*       */   public static final int[] parseInt(boolean[] what)
/*       */   {
/*  6345 */     int[] list = new int[what.length];
/*  6346 */     for (int i = 0; i < what.length; i++) {
/*  6347 */       list[i] = (what[i] != 0 ? 1 : 0);
/*       */     }
/*  6349 */     return list;
/*       */   }
/*       */   
/*       */   public static final int[] parseInt(byte[] what) {
/*  6353 */     int[] list = new int[what.length];
/*  6354 */     for (int i = 0; i < what.length; i++) {
/*  6355 */       what[i] &= 0xFF;
/*       */     }
/*  6357 */     return list;
/*       */   }
/*       */   
/*       */   public static final int[] parseInt(char[] what) {
/*  6361 */     int[] list = new int[what.length];
/*  6362 */     for (int i = 0; i < what.length; i++) {
/*  6363 */       list[i] = what[i];
/*       */     }
/*  6365 */     return list;
/*       */   }
/*       */   
/*       */   public static int[] parseInt(float[] what) {
/*  6369 */     int[] inties = new int[what.length];
/*  6370 */     for (int i = 0; i < what.length; i++) {
/*  6371 */       inties[i] = ((int)what[i]);
/*       */     }
/*  6373 */     return inties;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static int[] parseInt(String[] what)
/*       */   {
/*  6386 */     return parseInt(what, 0);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static int[] parseInt(String[] what, int missing)
/*       */   {
/*  6400 */     int[] output = new int[what.length];
/*  6401 */     for (int i = 0; i < what.length; i++) {
/*       */       try {
/*  6403 */         output[i] = Integer.parseInt(what[i]);
/*       */       } catch (NumberFormatException e) {
/*  6405 */         output[i] = missing;
/*       */       }
/*       */     }
/*  6408 */     return output;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final float parseFloat(int what)
/*       */   {
/*  6424 */     return what;
/*       */   }
/*       */   
/*       */   public static final float parseFloat(String what) {
/*  6428 */     return parseFloat(what, NaN.0F);
/*       */   }
/*       */   
/*       */   public static final float parseFloat(String what, float otherwise) {
/*       */     try {
/*  6433 */       return new Float(what).floatValue();
/*       */     }
/*       */     catch (NumberFormatException localNumberFormatException) {}
/*  6436 */     return otherwise;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final float[] parseByte(byte[] what)
/*       */   {
/*  6460 */     float[] floaties = new float[what.length];
/*  6461 */     for (int i = 0; i < what.length; i++) {
/*  6462 */       floaties[i] = what[i];
/*       */     }
/*  6464 */     return floaties;
/*       */   }
/*       */   
/*       */   public static final float[] parseFloat(int[] what) {
/*  6468 */     float[] floaties = new float[what.length];
/*  6469 */     for (int i = 0; i < what.length; i++) {
/*  6470 */       floaties[i] = what[i];
/*       */     }
/*  6472 */     return floaties;
/*       */   }
/*       */   
/*       */   public static final float[] parseFloat(String[] what) {
/*  6476 */     return parseFloat(what, NaN.0F);
/*       */   }
/*       */   
/*       */   public static final float[] parseFloat(String[] what, float missing) {
/*  6480 */     float[] output = new float[what.length];
/*  6481 */     for (int i = 0; i < what.length; i++) {
/*       */       try {
/*  6483 */         output[i] = new Float(what[i]).floatValue();
/*       */       } catch (NumberFormatException e) {
/*  6485 */         output[i] = missing;
/*       */       }
/*       */     }
/*  6488 */     return output;
/*       */   }
/*       */   
/*       */ 
/*       */   public static final String str(boolean x)
/*       */   {
/*  6494 */     return String.valueOf(x);
/*       */   }
/*       */   
/*       */   public static final String str(byte x) {
/*  6498 */     return String.valueOf(x);
/*       */   }
/*       */   
/*       */   public static final String str(char x) {
/*  6502 */     return String.valueOf(x);
/*       */   }
/*       */   
/*       */   public static final String str(int x) {
/*  6506 */     return String.valueOf(x);
/*       */   }
/*       */   
/*       */   public static final String str(float x) {
/*  6510 */     return String.valueOf(x);
/*       */   }
/*       */   
/*       */ 
/*       */   public static final String[] str(boolean[] x)
/*       */   {
/*  6516 */     String[] s = new String[x.length];
/*  6517 */     for (int i = 0; i < x.length; i++) s[i] = String.valueOf(x[i]);
/*  6518 */     return s;
/*       */   }
/*       */   
/*       */   public static final String[] str(byte[] x) {
/*  6522 */     String[] s = new String[x.length];
/*  6523 */     for (int i = 0; i < x.length; i++) s[i] = String.valueOf(x[i]);
/*  6524 */     return s;
/*       */   }
/*       */   
/*       */   public static final String[] str(char[] x) {
/*  6528 */     String[] s = new String[x.length];
/*  6529 */     for (int i = 0; i < x.length; i++) s[i] = String.valueOf(x[i]);
/*  6530 */     return s;
/*       */   }
/*       */   
/*       */   public static final String[] str(int[] x) {
/*  6534 */     String[] s = new String[x.length];
/*  6535 */     for (int i = 0; i < x.length; i++) s[i] = String.valueOf(x[i]);
/*  6536 */     return s;
/*       */   }
/*       */   
/*       */   public static final String[] str(float[] x) {
/*  6540 */     String[] s = new String[x.length];
/*  6541 */     for (int i = 0; i < x.length; i++) s[i] = String.valueOf(x[i]);
/*  6542 */     return s;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static String[] nf(int[] num, int digits)
/*       */   {
/*  6560 */     String[] formatted = new String[num.length];
/*  6561 */     for (int i = 0; i < formatted.length; i++) {
/*  6562 */       formatted[i] = nf(num[i], digits);
/*       */     }
/*  6564 */     return formatted;
/*       */   }
/*       */   
/*       */   public static String nf(int num, int digits)
/*       */   {
/*  6569 */     if ((int_nf != null) && 
/*  6570 */       (int_nf_digits == digits) && 
/*  6571 */       (!int_nf_commas)) {
/*  6572 */       return int_nf.format(num);
/*       */     }
/*       */     
/*  6575 */     int_nf = NumberFormat.getInstance();
/*  6576 */     int_nf.setGroupingUsed(false);
/*  6577 */     int_nf_commas = false;
/*  6578 */     int_nf.setMinimumIntegerDigits(digits);
/*  6579 */     int_nf_digits = digits;
/*  6580 */     return int_nf.format(num);
/*       */   }
/*       */   
/*       */   public static String[] nfc(int[] num)
/*       */   {
/*  6585 */     String[] formatted = new String[num.length];
/*  6586 */     for (int i = 0; i < formatted.length; i++) {
/*  6587 */       formatted[i] = nfc(num[i]);
/*       */     }
/*  6589 */     return formatted;
/*       */   }
/*       */   
/*       */   public static String nfc(int num)
/*       */   {
/*  6594 */     if ((int_nf != null) && 
/*  6595 */       (int_nf_digits == 0) && 
/*  6596 */       (int_nf_commas)) {
/*  6597 */       return int_nf.format(num);
/*       */     }
/*       */     
/*  6600 */     int_nf = NumberFormat.getInstance();
/*  6601 */     int_nf.setGroupingUsed(true);
/*  6602 */     int_nf_commas = true;
/*  6603 */     int_nf.setMinimumIntegerDigits(0);
/*  6604 */     int_nf_digits = 0;
/*  6605 */     return int_nf.format(num);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static String nfs(int num, int digits)
/*       */   {
/*  6616 */     return ' ' + nf(num, digits);
/*       */   }
/*       */   
/*       */   public static String[] nfs(int[] num, int digits) {
/*  6620 */     String[] formatted = new String[num.length];
/*  6621 */     for (int i = 0; i < formatted.length; i++) {
/*  6622 */       formatted[i] = nfs(num[i], digits);
/*       */     }
/*  6624 */     return formatted;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static String nfp(int num, int digits)
/*       */   {
/*  6635 */     return '+' + nf(num, digits);
/*       */   }
/*       */   
/*       */   public static String[] nfp(int[] num, int digits) {
/*  6639 */     String[] formatted = new String[num.length];
/*  6640 */     for (int i = 0; i < formatted.length; i++) {
/*  6641 */       formatted[i] = nfp(num[i], digits);
/*       */     }
/*  6643 */     return formatted;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static String[] nf(float[] num, int left, int right)
/*       */   {
/*  6659 */     String[] formatted = new String[num.length];
/*  6660 */     for (int i = 0; i < formatted.length; i++) {
/*  6661 */       formatted[i] = nf(num[i], left, right);
/*       */     }
/*  6663 */     return formatted;
/*       */   }
/*       */   
/*       */   public static String nf(float num, int left, int right)
/*       */   {
/*  6668 */     if ((float_nf != null) && 
/*  6669 */       (float_nf_left == left) && 
/*  6670 */       (float_nf_right == right) && 
/*  6671 */       (!float_nf_commas)) {
/*  6672 */       return float_nf.format(num);
/*       */     }
/*       */     
/*  6675 */     float_nf = NumberFormat.getInstance();
/*  6676 */     float_nf.setGroupingUsed(false);
/*  6677 */     float_nf_commas = false;
/*       */     
/*  6679 */     if (left != 0) float_nf.setMinimumIntegerDigits(left);
/*  6680 */     if (right != 0) {
/*  6681 */       float_nf.setMinimumFractionDigits(right);
/*  6682 */       float_nf.setMaximumFractionDigits(right);
/*       */     }
/*  6684 */     float_nf_left = left;
/*  6685 */     float_nf_right = right;
/*  6686 */     return float_nf.format(num);
/*       */   }
/*       */   
/*       */   public static String[] nfc(float[] num, int right)
/*       */   {
/*  6691 */     String[] formatted = new String[num.length];
/*  6692 */     for (int i = 0; i < formatted.length; i++) {
/*  6693 */       formatted[i] = nfc(num[i], right);
/*       */     }
/*  6695 */     return formatted;
/*       */   }
/*       */   
/*       */   public static String nfc(float num, int right)
/*       */   {
/*  6700 */     if ((float_nf != null) && 
/*  6701 */       (float_nf_left == 0) && 
/*  6702 */       (float_nf_right == right) && 
/*  6703 */       (float_nf_commas)) {
/*  6704 */       return float_nf.format(num);
/*       */     }
/*       */     
/*  6707 */     float_nf = NumberFormat.getInstance();
/*  6708 */     float_nf.setGroupingUsed(true);
/*  6709 */     float_nf_commas = true;
/*       */     
/*  6711 */     if (right != 0) {
/*  6712 */       float_nf.setMinimumFractionDigits(right);
/*  6713 */       float_nf.setMaximumFractionDigits(right);
/*       */     }
/*  6715 */     float_nf_left = 0;
/*  6716 */     float_nf_right = right;
/*  6717 */     return float_nf.format(num);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static String[] nfs(float[] num, int left, int right)
/*       */   {
/*  6726 */     String[] formatted = new String[num.length];
/*  6727 */     for (int i = 0; i < formatted.length; i++) {
/*  6728 */       formatted[i] = nfs(num[i], left, right);
/*       */     }
/*  6730 */     return formatted;
/*       */   }
/*       */   
/*       */   public static String nfs(float num, int left, int right) {
/*  6734 */     return ' ' + nf(num, left, right);
/*       */   }
/*       */   
/*       */   public static String[] nfp(float[] num, int left, int right)
/*       */   {
/*  6739 */     String[] formatted = new String[num.length];
/*  6740 */     for (int i = 0; i < formatted.length; i++) {
/*  6741 */       formatted[i] = nfp(num[i], left, right);
/*       */     }
/*  6743 */     return formatted;
/*       */   }
/*       */   
/*       */   public static String nfp(float num, int left, int right) {
/*  6747 */     return '+' + nf(num, left, right);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final String hex(byte what)
/*       */   {
/*  6758 */     return hex(what, 2);
/*       */   }
/*       */   
/*       */   public static final String hex(char what) {
/*  6762 */     return hex(what, 4);
/*       */   }
/*       */   
/*       */   public static final String hex(int what) {
/*  6766 */     return hex(what, 8);
/*       */   }
/*       */   
/*       */   public static final String hex(int what, int digits) {
/*  6770 */     String stuff = Integer.toHexString(what).toUpperCase();
/*       */     
/*  6772 */     int length = stuff.length();
/*  6773 */     if (length > digits) {
/*  6774 */       return stuff.substring(length - digits);
/*       */     }
/*  6776 */     if (length < digits) {
/*  6777 */       return "00000000".substring(8 - (digits - length)) + stuff;
/*       */     }
/*  6779 */     return stuff;
/*       */   }
/*       */   
/*       */   public static final int unhex(String what)
/*       */   {
/*  6784 */     return (int)Long.parseLong(what, 16);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final String binary(byte what)
/*       */   {
/*  6794 */     return binary(what, 8);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final String binary(char what)
/*       */   {
/*  6803 */     return binary(what, 16);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final String binary(int what)
/*       */   {
/*  6815 */     return Integer.toBinaryString(what);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final String binary(int what, int digits)
/*       */   {
/*  6824 */     String stuff = Integer.toBinaryString(what);
/*       */     
/*  6826 */     int length = stuff.length();
/*  6827 */     if (length > digits) {
/*  6828 */       return stuff.substring(length - digits);
/*       */     }
/*  6830 */     if (length < digits) {
/*  6831 */       int offset = 32 - (digits - length);
/*  6832 */       return "00000000000000000000000000000000".substring(offset) + stuff;
/*       */     }
/*  6834 */     return stuff;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static final int unbinary(String what)
/*       */   {
/*  6843 */     return Integer.parseInt(what, 2);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public final int color(int gray)
/*       */   {
/*  6857 */     if (this.g == null) {
/*  6858 */       if (gray > 255) gray = 255; else if (gray < 0) gray = 0;
/*  6859 */       return 0xFF000000 | gray << 16 | gray << 8 | gray;
/*       */     }
/*  6861 */     return this.g.color(gray);
/*       */   }
/*       */   
/*       */   public final int color(float fgray)
/*       */   {
/*  6866 */     if (this.g == null) {
/*  6867 */       int gray = (int)fgray;
/*  6868 */       if (gray > 255) gray = 255; else if (gray < 0) gray = 0;
/*  6869 */       return 0xFF000000 | gray << 16 | gray << 8 | gray;
/*       */     }
/*  6871 */     return this.g.color(fgray);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public final int color(int gray, int alpha)
/*       */   {
/*  6881 */     if (this.g == null) {
/*  6882 */       if (alpha > 255) alpha = 255; else if (alpha < 0) alpha = 0;
/*  6883 */       if (gray > 255)
/*       */       {
/*  6885 */         return alpha << 24 | gray & 0xFFFFFF;
/*       */       }
/*       */       
/*  6888 */       return alpha << 24 | gray << 16 | gray << 8 | gray;
/*       */     }
/*       */     
/*  6891 */     return this.g.color(gray, alpha);
/*       */   }
/*       */   
/*       */   public final int color(float fgray, float falpha)
/*       */   {
/*  6896 */     if (this.g == null) {
/*  6897 */       int gray = (int)fgray;
/*  6898 */       int alpha = (int)falpha;
/*  6899 */       if (gray > 255) gray = 255; else if (gray < 0) gray = 0;
/*  6900 */       if (alpha > 255) alpha = 255; else if (alpha < 0) alpha = 0;
/*  6901 */       return 0xFF000000 | gray << 16 | gray << 8 | gray;
/*       */     }
/*  6903 */     return this.g.color(fgray, falpha);
/*       */   }
/*       */   
/*       */   public final int color(int x, int y, int z)
/*       */   {
/*  6908 */     if (this.g == null) {
/*  6909 */       if (x > 255) x = 255; else if (x < 0) x = 0;
/*  6910 */       if (y > 255) y = 255; else if (y < 0) y = 0;
/*  6911 */       if (z > 255) z = 255; else if (z < 0) { z = 0;
/*       */       }
/*  6913 */       return 0xFF000000 | x << 16 | y << 8 | z;
/*       */     }
/*  6915 */     return this.g.color(x, y, z);
/*       */   }
/*       */   
/*       */   public final int color(float x, float y, float z)
/*       */   {
/*  6920 */     if (this.g == null) {
/*  6921 */       if (x > 255.0F) x = 255.0F; else if (x < 0.0F) x = 0.0F;
/*  6922 */       if (y > 255.0F) y = 255.0F; else if (y < 0.0F) y = 0.0F;
/*  6923 */       if (z > 255.0F) z = 255.0F; else if (z < 0.0F) { z = 0.0F;
/*       */       }
/*  6925 */       return 0xFF000000 | (int)x << 16 | (int)y << 8 | (int)z;
/*       */     }
/*  6927 */     return this.g.color(x, y, z);
/*       */   }
/*       */   
/*       */   public final int color(int x, int y, int z, int a)
/*       */   {
/*  6932 */     if (this.g == null) {
/*  6933 */       if (a > 255) a = 255; else if (a < 0) a = 0;
/*  6934 */       if (x > 255) x = 255; else if (x < 0) x = 0;
/*  6935 */       if (y > 255) y = 255; else if (y < 0) y = 0;
/*  6936 */       if (z > 255) z = 255; else if (z < 0) { z = 0;
/*       */       }
/*  6938 */       return a << 24 | x << 16 | y << 8 | z;
/*       */     }
/*  6940 */     return this.g.color(x, y, z, a);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public final int color(float x, float y, float z, float a)
/*       */   {
/*  6956 */     if (this.g == null) {
/*  6957 */       if (a > 255.0F) a = 255.0F; else if (a < 0.0F) a = 0.0F;
/*  6958 */       if (x > 255.0F) x = 255.0F; else if (x < 0.0F) x = 0.0F;
/*  6959 */       if (y > 255.0F) y = 255.0F; else if (y < 0.0F) y = 0.0F;
/*  6960 */       if (z > 255.0F) z = 255.0F; else if (z < 0.0F) { z = 0.0F;
/*       */       }
/*  6962 */       return (int)a << 24 | (int)x << 16 | (int)y << 8 | (int)z;
/*       */     }
/*  6964 */     return this.g.color(x, y, z, a);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void setupExternalMessages()
/*       */   {
/*  6983 */     this.frame.addComponentListener(new ComponentAdapter() {
/*       */       public void componentMoved(ComponentEvent e) {
/*  6985 */         Point where = ((Frame)e.getSource()).getLocation();
/*  6986 */         System.err.println("__MOVE__ " + 
/*  6987 */           where.x + " " + where.y);
/*  6988 */         System.err.flush();
/*       */       }
/*       */       
/*  6991 */     });
/*  6992 */     this.frame.addWindowListener(new WindowAdapter()
/*       */     {
/*       */ 
/*       */       public void windowClosing(WindowEvent e)
/*       */       {
/*  6997 */         PApplet.this.exit();
/*       */       }
/*       */     });
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void setupFrameResizeListener()
/*       */   {
/*  7008 */     this.frame.addComponentListener(new ComponentAdapter()
/*       */     {
/*       */ 
/*       */ 
/*       */       public void componentResized(ComponentEvent e)
/*       */       {
/*       */ 
/*  7015 */         if (PApplet.this.frame.isResizable())
/*       */         {
/*       */ 
/*       */ 
/*  7019 */           Frame farm = (Frame)e.getComponent();
/*  7020 */           if (farm.isVisible()) {
/*  7021 */             Insets insets = farm.getInsets();
/*  7022 */             Dimension windowSize = farm.getSize();
/*  7023 */             int usableW = windowSize.width - insets.left - insets.right;
/*  7024 */             int usableH = windowSize.height - insets.top - insets.bottom;
/*       */             
/*       */ 
/*  7027 */             PApplet.this.setBounds(insets.left, insets.top, usableW, usableH);
/*       */           }
/*       */         }
/*       */       }
/*       */     });
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  7038 */   public static final byte[] ICON_IMAGE = {
/*  7039 */     71, 73, 70, 56, 57, 97, 16, 0, 16, 0, -77, 0, 0, 0, 0, 0, -1, -1, -1, 12, 
/*  7040 */     12, 13, -15, -15, -14, 45, 57, 74, 54, 80, 111, 47, 71, 97, 62, 88, 117, 
/*  7041 */     1, 14, 27, 7, 41, 73, 15, 52, 85, 2, 31, 55, 4, 54, 94, 18, 69, 109, 37, 
/*  7042 */     87, 126, -1, -1, -1, 33, -7, 4, 1, 0, 0, 15, 0, 44, 0, 0, 0, 0, 16, 0, 16, 
/*  7043 */     0, 0, 4, 122, -16, -107, 114, -86, -67, 83, 30, -42, 26, -17, -100, -45, 
/*  7044 */     56, -57, -108, 48, 40, 122, -90, 104, 67, -91, -51, 32, -53, 77, -78, -100, 
/*  7045 */     47, -86, 12, 76, -110, -20, -74, -101, 97, -93, 27, 40, 20, -65, 65, 48, 
/*  7046 */     -111, 99, -20, -112, -117, -123, -47, -105, 24, 114, -112, 74, 69, 84, 25, 
/*  7047 */     93, 88, -75, 9, 46, 2, 49, 88, -116, -67, 7, -19, -83, 60, 38, 3, -34, 2, 
/*  7048 */     66, -95, 27, -98, 13, 4, -17, 55, 33, 109, 11, 11, -2, Byte.MIN_VALUE, 121, 123, 62, 
/*  7049 */     91, 120, Byte.MIN_VALUE, Byte.MAX_VALUE, 122, 115, 102, 2, 119, 0, -116, -113, -119, 6, 102, 
/*  7050 */     121, -108, -126, 5, 18, 6, 4, -102, -101, -100, 114, 15, 17, 059 };
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static void main(String[] args)
/*       */   {
/*  7111 */     if (platform == 2)
/*       */     {
/*       */ 
/*  7114 */       System.setProperty("apple.awt.graphics.UseQuartz", 
/*  7115 */         String.valueOf(useQuartz));
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  7126 */     if (args.length < 1) {
/*  7127 */       System.err.println("Usage: PApplet <appletname>");
/*  7128 */       System.err.println("For additional options, see the Javadoc for PApplet");
/*       */       
/*  7130 */       System.exit(1);
/*       */     }
/*       */     
/*  7133 */     boolean external = false;
/*  7134 */     int[] location = (int[])null;
/*  7135 */     int[] editorLocation = (int[])null;
/*       */     
/*  7137 */     String name = null;
/*  7138 */     boolean present = false;
/*  7139 */     boolean exclusive = false;
/*  7140 */     Color backgroundColor = Color.BLACK;
/*  7141 */     Color stopColor = Color.GRAY;
/*  7142 */     GraphicsDevice displayDevice = null;
/*  7143 */     boolean hideStop = false;
/*       */     
/*  7145 */     String param = null;String value = null;
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*  7150 */     String folder = null;
/*       */     try {
/*  7152 */       folder = System.getProperty("user.dir");
/*       */     }
/*       */     catch (Exception localException1) {}
/*  7155 */     int argIndex = 0;
/*  7156 */     while (argIndex < args.length) {
/*  7157 */       int equals = args[argIndex].indexOf('=');
/*  7158 */       if (equals != -1) {
/*  7159 */         param = args[argIndex].substring(0, equals);
/*  7160 */         value = args[argIndex].substring(equals + 1);
/*       */         
/*  7162 */         if (param.equals("--editor-location")) {
/*  7163 */           external = true;
/*  7164 */           editorLocation = parseInt(split(value, ','));
/*       */         }
/*  7166 */         else if (param.equals("--display")) {
/*  7167 */           int deviceIndex = Integer.parseInt(value) - 1;
/*       */           
/*       */ 
/*       */ 
/*       */ 
/*  7172 */           GraphicsEnvironment environment = 
/*  7173 */             GraphicsEnvironment.getLocalGraphicsEnvironment();
/*  7174 */           GraphicsDevice[] devices = environment.getScreenDevices();
/*  7175 */           if ((deviceIndex >= 0) && (deviceIndex < devices.length)) {
/*  7176 */             displayDevice = devices[deviceIndex];
/*       */           } else {
/*  7178 */             System.err.println("Display " + value + " does not exist, " + 
/*  7179 */               "using the default display instead.");
/*       */           }
/*       */         }
/*  7182 */         else if (param.equals("--bgcolor")) {
/*  7183 */           if (value.charAt(0) == '#') value = value.substring(1);
/*  7184 */           backgroundColor = new Color(Integer.parseInt(value, 16));
/*       */         }
/*  7186 */         else if (param.equals("--stop-color")) {
/*  7187 */           if (value.charAt(0) == '#') value = value.substring(1);
/*  7188 */           stopColor = new Color(Integer.parseInt(value, 16));
/*       */         }
/*  7190 */         else if (param.equals("--sketch-path")) {
/*  7191 */           folder = value;
/*       */         }
/*  7193 */         else if (param.equals("--location")) {
/*  7194 */           location = parseInt(split(value, ','));
/*       */         }
/*       */         
/*       */       }
/*  7198 */       else if (args[argIndex].equals("--present")) {
/*  7199 */         present = true;
/*       */       }
/*  7201 */       else if (args[argIndex].equals("--exclusive")) {
/*  7202 */         exclusive = true;
/*       */       }
/*  7204 */       else if (args[argIndex].equals("--hide-stop")) {
/*  7205 */         hideStop = true;
/*       */       }
/*  7207 */       else if (args[argIndex].equals("--external")) {
/*  7208 */         external = true;
/*       */       }
/*       */       else {
/*  7211 */         name = args[argIndex];
/*  7212 */         break;
/*       */       }
/*       */       
/*  7215 */       argIndex++;
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  7223 */     if (displayDevice == null) {
/*  7224 */       GraphicsEnvironment environment = 
/*  7225 */         GraphicsEnvironment.getLocalGraphicsEnvironment();
/*  7226 */       displayDevice = environment.getDefaultScreenDevice();
/*       */     }
/*       */     
/*  7229 */     Frame frame = new Frame(displayDevice.getDefaultConfiguration());
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  7242 */     frame.setResizable(false);
/*       */     
/*       */ 
/*  7245 */     Image image = Toolkit.getDefaultToolkit().createImage(ICON_IMAGE);
/*  7246 */     frame.setIconImage(image);
/*  7247 */     frame.setTitle(name);
/*       */     
/*       */     try
/*       */     {
/*  7251 */       Class<?> c = Thread.currentThread().getContextClassLoader().loadClass(name);
/*  7252 */       applet = (PApplet)c.newInstance();
/*       */     } catch (Exception e) { PApplet applet;
/*  7254 */       throw new RuntimeException(e);
/*       */     }
/*       */     
/*       */     PApplet applet;
/*  7258 */     applet.frame = frame;
/*  7259 */     applet.sketchPath = folder;
/*  7260 */     applet.args = subset(args, 1);
/*  7261 */     applet.external = external;
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*  7266 */     Rectangle fullScreenRect = null;
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  7275 */     if (present) {
/*  7276 */       frame.setUndecorated(true);
/*  7277 */       frame.setBackground(backgroundColor);
/*  7278 */       if (exclusive) {
/*  7279 */         displayDevice.setFullScreenWindow(frame);
/*  7280 */         frame.setExtendedState(6);
/*  7281 */         fullScreenRect = frame.getBounds();
/*       */       } else {
/*  7283 */         DisplayMode mode = displayDevice.getDisplayMode();
/*  7284 */         fullScreenRect = new Rectangle(0, 0, mode.getWidth(), mode.getHeight());
/*  7285 */         frame.setBounds(fullScreenRect);
/*  7286 */         frame.setVisible(true);
/*       */       }
/*       */     }
/*  7289 */     frame.setLayout(null);
/*  7290 */     frame.add(applet);
/*  7291 */     if (present) {
/*  7292 */       frame.invalidate();
/*       */     } else {
/*  7294 */       frame.pack();
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*  7299 */     applet.init();
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  7305 */     while ((applet.defaultSize) && (!applet.finished)) {
/*       */       try
/*       */       {
/*  7308 */         Thread.sleep(5L);
/*       */       }
/*       */       catch (InterruptedException localInterruptedException) {}
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  7317 */     if (present)
/*       */     {
/*  7319 */       frame.setBounds(fullScreenRect);
/*  7320 */       applet.setBounds((fullScreenRect.width - applet.width) / 2, 
/*  7321 */         (fullScreenRect.height - applet.height) / 2, 
/*  7322 */         applet.width, applet.height);
/*       */       
/*  7324 */       if (!hideStop) {
/*  7325 */         Label label = new Label("stop");
/*  7326 */         label.setForeground(stopColor);
/*  7327 */         label.addMouseListener(new MouseAdapter() {
/*       */           public void mousePressed(MouseEvent e) {
/*  7329 */             System.exit(0);
/*       */           }
/*  7331 */         });
/*  7332 */         frame.add(label);
/*       */         
/*  7334 */         Dimension labelSize = label.getPreferredSize();
/*       */         
/*       */ 
/*  7337 */         labelSize = new Dimension(100, labelSize.height);
/*  7338 */         label.setSize(labelSize);
/*  7339 */         label.setLocation(20, fullScreenRect.height - labelSize.height - 20);
/*       */       }
/*       */       
/*       */ 
/*  7343 */       if (external) {
/*  7344 */         applet.setupExternalMessages();
/*       */       }
/*       */       
/*       */ 
/*       */     }
/*       */     else
/*       */     {
/*  7351 */       Insets insets = frame.getInsets();
/*       */       
/*  7353 */       int windowW = Math.max(applet.width, 128) + 
/*  7354 */         insets.left + insets.right;
/*  7355 */       int windowH = Math.max(applet.height, 128) + 
/*  7356 */         insets.top + insets.bottom;
/*       */       
/*  7358 */       frame.setSize(windowW, windowH);
/*       */       
/*  7360 */       if (location != null)
/*       */       {
/*       */ 
/*  7363 */         frame.setLocation(location[0], location[1]);
/*       */       }
/*  7365 */       else if (external) {
/*  7366 */         int locationX = editorLocation[0] - 20;
/*  7367 */         int locationY = editorLocation[1];
/*       */         
/*  7369 */         if (locationX - windowW > 10)
/*       */         {
/*  7371 */           frame.setLocation(locationX - windowW, locationY);
/*       */ 
/*       */         }
/*       */         else
/*       */         {
/*       */ 
/*  7377 */           locationX = editorLocation[0] + 66;
/*  7378 */           locationY = editorLocation[1] + 66;
/*       */           
/*  7380 */           if ((locationX + windowW > applet.screenWidth - 33) || 
/*  7381 */             (locationY + windowH > applet.screenHeight - 33))
/*       */           {
/*  7383 */             locationX = (applet.screenWidth - windowW) / 2;
/*  7384 */             locationY = (applet.screenHeight - windowH) / 2;
/*       */           }
/*  7386 */           frame.setLocation(locationX, locationY);
/*       */         }
/*       */       } else {
/*  7389 */         frame.setLocation((applet.screenWidth - applet.width) / 2, 
/*  7390 */           (applet.screenHeight - applet.height) / 2);
/*       */       }
/*  7392 */       Point frameLoc = frame.getLocation();
/*  7393 */       if (frameLoc.y < 0)
/*       */       {
/*       */ 
/*  7396 */         frame.setLocation(frameLoc.x, 30);
/*       */       }
/*       */       
/*  7399 */       if (backgroundColor == Color.black)
/*       */       {
/*  7401 */         backgroundColor = SystemColor.control;
/*       */       }
/*  7403 */       frame.setBackground(backgroundColor);
/*       */       
/*  7405 */       int usableWindowH = windowH - insets.top - insets.bottom;
/*  7406 */       applet.setBounds((windowW - applet.width) / 2, 
/*  7407 */         insets.top + (usableWindowH - applet.height) / 2, 
/*  7408 */         applet.width, applet.height);
/*       */       
/*  7410 */       if (external) {
/*  7411 */         applet.setupExternalMessages();
/*       */       }
/*       */       else {
/*  7414 */         frame.addWindowListener(new WindowAdapter() {
/*       */           public void windowClosing(WindowEvent e) {
/*  7416 */             System.exit(0);
/*       */           }
/*       */         });
/*       */       }
/*       */       
/*       */ 
/*  7422 */       applet.setupFrameResizeListener();
/*       */       
/*       */ 
/*  7425 */       if (applet.displayable()) {
/*  7426 */         frame.setVisible(true);
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public PGraphics beginRecord(String renderer, String filename)
/*       */   {
/*  7445 */     filename = insertFrame(filename);
/*  7446 */     PGraphics rec = createGraphics(this.width, this.height, renderer, filename);
/*  7447 */     beginRecord(rec);
/*  7448 */     return rec;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void beginRecord(PGraphics recorder)
/*       */   {
/*  7456 */     this.recorder = recorder;
/*  7457 */     recorder.beginDraw();
/*       */   }
/*       */   
/*       */   public void endRecord()
/*       */   {
/*  7462 */     if (this.recorder != null) {
/*  7463 */       this.recorder.endDraw();
/*  7464 */       this.recorder.dispose();
/*  7465 */       this.recorder = null;
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public PGraphics beginRaw(String renderer, String filename)
/*       */   {
/*  7478 */     filename = insertFrame(filename);
/*  7479 */     PGraphics rec = createGraphics(this.width, this.height, renderer, filename);
/*  7480 */     this.g.beginRaw(rec);
/*  7481 */     return rec;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void beginRaw(PGraphics rawGraphics)
/*       */   {
/*  7493 */     this.g.beginRaw(rawGraphics);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void endRaw()
/*       */   {
/*  7505 */     this.g.endRaw();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void loadPixels()
/*       */   {
/*  7525 */     this.g.loadPixels();
/*  7526 */     this.pixels = this.g.pixels;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void updatePixels()
/*       */   {
/*  7541 */     this.g.updatePixels();
/*       */   }
/*       */   
/*       */   public void updatePixels(int x1, int y1, int x2, int y2)
/*       */   {
/*  7546 */     this.g.updatePixels(x1, y1, x2, y2);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void flush()
/*       */   {
/*  7560 */     if (this.recorder != null) this.recorder.flush();
/*  7561 */     this.g.flush();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void hint(int which)
/*       */   {
/*  7584 */     if (this.recorder != null) this.recorder.hint(which);
/*  7585 */     this.g.hint(which);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void beginShape()
/*       */   {
/*  7593 */     if (this.recorder != null) this.recorder.beginShape();
/*  7594 */     this.g.beginShape();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void beginShape(int kind)
/*       */   {
/*  7621 */     if (this.recorder != null) this.recorder.beginShape(kind);
/*  7622 */     this.g.beginShape(kind);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void edge(boolean edge)
/*       */   {
/*  7631 */     if (this.recorder != null) this.recorder.edge(edge);
/*  7632 */     this.g.edge(edge);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void normal(float nx, float ny, float nz)
/*       */   {
/*  7652 */     if (this.recorder != null) this.recorder.normal(nx, ny, nz);
/*  7653 */     this.g.normal(nx, ny, nz);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void textureMode(int mode)
/*       */   {
/*  7662 */     if (this.recorder != null) this.recorder.textureMode(mode);
/*  7663 */     this.g.textureMode(mode);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void texture(PImage image)
/*       */   {
/*  7674 */     if (this.recorder != null) this.recorder.texture(image);
/*  7675 */     this.g.texture(image);
/*       */   }
/*       */   
/*       */   public void vertex(float x, float y)
/*       */   {
/*  7680 */     if (this.recorder != null) this.recorder.vertex(x, y);
/*  7681 */     this.g.vertex(x, y);
/*       */   }
/*       */   
/*       */   public void vertex(float x, float y, float z)
/*       */   {
/*  7686 */     if (this.recorder != null) this.recorder.vertex(x, y, z);
/*  7687 */     this.g.vertex(x, y, z);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void vertex(float[] v)
/*       */   {
/*  7697 */     if (this.recorder != null) this.recorder.vertex(v);
/*  7698 */     this.g.vertex(v);
/*       */   }
/*       */   
/*       */   public void vertex(float x, float y, float u, float v)
/*       */   {
/*  7703 */     if (this.recorder != null) this.recorder.vertex(x, y, u, v);
/*  7704 */     this.g.vertex(x, y, u, v);
/*       */   }
/*       */   
/*       */   public void vertex(float x, float y, float z, float u, float v)
/*       */   {
/*  7709 */     if (this.recorder != null) this.recorder.vertex(x, y, z, u, v);
/*  7710 */     this.g.vertex(x, y, z, u, v);
/*       */   }
/*       */   
/*       */ 
/*       */   public void breakShape()
/*       */   {
/*  7716 */     if (this.recorder != null) this.recorder.breakShape();
/*  7717 */     this.g.breakShape();
/*       */   }
/*       */   
/*       */   public void endShape()
/*       */   {
/*  7722 */     if (this.recorder != null) this.recorder.endShape();
/*  7723 */     this.g.endShape();
/*       */   }
/*       */   
/*       */   public void endShape(int mode)
/*       */   {
/*  7728 */     if (this.recorder != null) this.recorder.endShape(mode);
/*  7729 */     this.g.endShape(mode);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   public void bezierVertex(float x2, float y2, float x3, float y3, float x4, float y4)
/*       */   {
/*  7736 */     if (this.recorder != null) this.recorder.bezierVertex(x2, y2, x3, y3, x4, y4);
/*  7737 */     this.g.bezierVertex(x2, y2, x3, y3, x4, y4);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   public void bezierVertex(float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4)
/*       */   {
/*  7744 */     if (this.recorder != null) this.recorder.bezierVertex(x2, y2, z2, x3, y3, z3, x4, y4, z4);
/*  7745 */     this.g.bezierVertex(x2, y2, z2, x3, y3, z3, x4, y4, z4);
/*       */   }
/*       */   
/*       */   public void curveVertex(float x, float y)
/*       */   {
/*  7750 */     if (this.recorder != null) this.recorder.curveVertex(x, y);
/*  7751 */     this.g.curveVertex(x, y);
/*       */   }
/*       */   
/*       */   public void curveVertex(float x, float y, float z)
/*       */   {
/*  7756 */     if (this.recorder != null) this.recorder.curveVertex(x, y, z);
/*  7757 */     this.g.curveVertex(x, y, z);
/*       */   }
/*       */   
/*       */   public void point(float x, float y)
/*       */   {
/*  7762 */     if (this.recorder != null) this.recorder.point(x, y);
/*  7763 */     this.g.point(x, y);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void point(float x, float y, float z)
/*       */   {
/*  7788 */     if (this.recorder != null) this.recorder.point(x, y, z);
/*  7789 */     this.g.point(x, y, z);
/*       */   }
/*       */   
/*       */   public void line(float x1, float y1, float x2, float y2)
/*       */   {
/*  7794 */     if (this.recorder != null) this.recorder.line(x1, y1, x2, y2);
/*  7795 */     this.g.line(x1, y1, x2, y2);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void line(float x1, float y1, float z1, float x2, float y2, float z2)
/*       */   {
/*  7826 */     if (this.recorder != null) this.recorder.line(x1, y1, z1, x2, y2, z2);
/*  7827 */     this.g.line(x1, y1, z1, x2, y2, z2);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void triangle(float x1, float y1, float x2, float y2, float x3, float y3)
/*       */   {
/*  7848 */     if (this.recorder != null) this.recorder.triangle(x1, y1, x2, y2, x3, y3);
/*  7849 */     this.g.triangle(x1, y1, x2, y2, x3, y3);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void quad(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4)
/*       */   {
/*  7873 */     if (this.recorder != null) this.recorder.quad(x1, y1, x2, y2, x3, y3, x4, y4);
/*  7874 */     this.g.quad(x1, y1, x2, y2, x3, y3, x4, y4);
/*       */   }
/*       */   
/*       */   public void rectMode(int mode)
/*       */   {
/*  7879 */     if (this.recorder != null) this.recorder.rectMode(mode);
/*  7880 */     this.g.rectMode(mode);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void rect(float a, float b, float c, float d)
/*       */   {
/*  7900 */     if (this.recorder != null) this.recorder.rect(a, b, c, d);
/*  7901 */     this.g.rect(a, b, c, d);
/*       */   }
/*       */   
/*       */   public void rect(float a, float b, float c, float d, float hr, float vr)
/*       */   {
/*  7906 */     if (this.recorder != null) this.recorder.rect(a, b, c, d, hr, vr);
/*  7907 */     this.g.rect(a, b, c, d, hr, vr);
/*       */   }
/*       */   
/*       */ 
/*       */   public void rect(float a, float b, float c, float d, float tl, float tr, float bl, float br)
/*       */   {
/*  7913 */     if (this.recorder != null) this.recorder.rect(a, b, c, d, tl, tr, bl, br);
/*  7914 */     this.g.rect(a, b, c, d, tl, tr, bl, br);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void ellipseMode(int mode)
/*       */   {
/*  7936 */     if (this.recorder != null) this.recorder.ellipseMode(mode);
/*  7937 */     this.g.ellipseMode(mode);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void ellipse(float a, float b, float c, float d)
/*       */   {
/*  7956 */     if (this.recorder != null) this.recorder.ellipse(a, b, c, d);
/*  7957 */     this.g.ellipse(a, b, c, d);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void arc(float a, float b, float c, float d, float start, float stop)
/*       */   {
/*  7983 */     if (this.recorder != null) this.recorder.arc(a, b, c, d, start, stop);
/*  7984 */     this.g.arc(a, b, c, d, start, stop);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void box(float size)
/*       */   {
/*  7992 */     if (this.recorder != null) this.recorder.box(size);
/*  7993 */     this.g.box(size);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void box(float w, float h, float d)
/*       */   {
/*  8009 */     if (this.recorder != null) this.recorder.box(w, h, d);
/*  8010 */     this.g.box(w, h, d);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void sphereDetail(int res)
/*       */   {
/*  8018 */     if (this.recorder != null) this.recorder.sphereDetail(res);
/*  8019 */     this.g.sphereDetail(res);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void sphereDetail(int ures, int vres)
/*       */   {
/*  8055 */     if (this.recorder != null) this.recorder.sphereDetail(ures, vres);
/*  8056 */     this.g.sphereDetail(ures, vres);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void sphere(float r)
/*       */   {
/*  8088 */     if (this.recorder != null) this.recorder.sphere(r);
/*  8089 */     this.g.sphere(r);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public float bezierPoint(float a, float b, float c, float d, float t)
/*       */   {
/*  8133 */     return this.g.bezierPoint(a, b, c, d, t);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public float bezierTangent(float a, float b, float c, float d, float t)
/*       */   {
/*  8156 */     return this.g.bezierTangent(a, b, c, d, t);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void bezierDetail(int detail)
/*       */   {
/*  8171 */     if (this.recorder != null) this.recorder.bezierDetail(detail);
/*  8172 */     this.g.bezierDetail(detail);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void bezier(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4)
/*       */   {
/*  8229 */     if (this.recorder != null) this.recorder.bezier(x1, y1, x2, y2, x3, y3, x4, y4);
/*  8230 */     this.g.bezier(x1, y1, x2, y2, x3, y3, x4, y4);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void bezier(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4)
/*       */   {
/*  8238 */     if (this.recorder != null) this.recorder.bezier(x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4);
/*  8239 */     this.g.bezier(x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public float curvePoint(float a, float b, float c, float d, float t)
/*       */   {
/*  8262 */     return this.g.curvePoint(a, b, c, d, t);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public float curveTangent(float a, float b, float c, float d, float t)
/*       */   {
/*  8285 */     return this.g.curveTangent(a, b, c, d, t);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void curveDetail(int detail)
/*       */   {
/*  8302 */     if (this.recorder != null) this.recorder.curveDetail(detail);
/*  8303 */     this.g.curveDetail(detail);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void curveTightness(float tightness)
/*       */   {
/*  8325 */     if (this.recorder != null) this.recorder.curveTightness(tightness);
/*  8326 */     this.g.curveTightness(tightness);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void curve(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4)
/*       */   {
/*  8378 */     if (this.recorder != null) this.recorder.curve(x1, y1, x2, y2, x3, y3, x4, y4);
/*  8379 */     this.g.curve(x1, y1, x2, y2, x3, y3, x4, y4);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void curve(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4)
/*       */   {
/*  8387 */     if (this.recorder != null) this.recorder.curve(x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4);
/*  8388 */     this.g.curve(x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void smooth()
/*       */   {
/*  8397 */     if (this.recorder != null) this.recorder.smooth();
/*  8398 */     this.g.smooth();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void noSmooth()
/*       */   {
/*  8406 */     if (this.recorder != null) this.recorder.noSmooth();
/*  8407 */     this.g.noSmooth();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void imageMode(int mode)
/*       */   {
/*  8433 */     if (this.recorder != null) this.recorder.imageMode(mode);
/*  8434 */     this.g.imageMode(mode);
/*       */   }
/*       */   
/*       */   public void image(PImage image, float x, float y)
/*       */   {
/*  8439 */     if (this.recorder != null) this.recorder.image(image, x, y);
/*  8440 */     this.g.image(image, x, y);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void image(PImage image, float x, float y, float c, float d)
/*       */   {
/*  8478 */     if (this.recorder != null) this.recorder.image(image, x, y, c, d);
/*  8479 */     this.g.image(image, x, y, c, d);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void image(PImage image, float a, float b, float c, float d, int u1, int v1, int u2, int v2)
/*       */   {
/*  8491 */     if (this.recorder != null) this.recorder.image(image, a, b, c, d, u1, v1, u2, v2);
/*  8492 */     this.g.image(image, a, b, c, d, u1, v1, u2, v2);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void shapeMode(int mode)
/*       */   {
/*  8517 */     if (this.recorder != null) this.recorder.shapeMode(mode);
/*  8518 */     this.g.shapeMode(mode);
/*       */   }
/*       */   
/*       */   public void shape(PShape shape)
/*       */   {
/*  8523 */     if (this.recorder != null) this.recorder.shape(shape);
/*  8524 */     this.g.shape(shape);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void shape(PShape shape, float x, float y)
/*       */   {
/*  8532 */     if (this.recorder != null) this.recorder.shape(shape, x, y);
/*  8533 */     this.g.shape(shape, x, y);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void shape(PShape shape, float x, float y, float c, float d)
/*       */   {
/*  8567 */     if (this.recorder != null) this.recorder.shape(shape, x, y, c, d);
/*  8568 */     this.g.shape(shape, x, y, c, d);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void textAlign(int align)
/*       */   {
/*  8577 */     if (this.recorder != null) this.recorder.textAlign(align);
/*  8578 */     this.g.textAlign(align);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void textAlign(int alignX, int alignY)
/*       */   {
/*  8588 */     if (this.recorder != null) this.recorder.textAlign(alignX, alignY);
/*  8589 */     this.g.textAlign(alignX, alignY);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public float textAscent()
/*       */   {
/*  8599 */     return this.g.textAscent();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public float textDescent()
/*       */   {
/*  8609 */     return this.g.textDescent();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void textFont(PFont which)
/*       */   {
/*  8619 */     if (this.recorder != null) this.recorder.textFont(which);
/*  8620 */     this.g.textFont(which);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void textFont(PFont which, float size)
/*       */   {
/*  8628 */     if (this.recorder != null) this.recorder.textFont(which, size);
/*  8629 */     this.g.textFont(which, size);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void textLeading(float leading)
/*       */   {
/*  8639 */     if (this.recorder != null) this.recorder.textLeading(leading);
/*  8640 */     this.g.textLeading(leading);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void textMode(int mode)
/*       */   {
/*  8652 */     if (this.recorder != null) this.recorder.textMode(mode);
/*  8653 */     this.g.textMode(mode);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void textSize(float size)
/*       */   {
/*  8661 */     if (this.recorder != null) this.recorder.textSize(size);
/*  8662 */     this.g.textSize(size);
/*       */   }
/*       */   
/*       */   public float textWidth(char c)
/*       */   {
/*  8667 */     return this.g.textWidth(c);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public float textWidth(String str)
/*       */   {
/*  8676 */     return this.g.textWidth(str);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public float textWidth(char[] chars, int start, int length)
/*       */   {
/*  8684 */     return this.g.textWidth(chars, start, length);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void text(char c)
/*       */   {
/*  8692 */     if (this.recorder != null) this.recorder.text(c);
/*  8693 */     this.g.text(c);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void text(char c, float x, float y)
/*       */   {
/*  8703 */     if (this.recorder != null) this.recorder.text(c, x, y);
/*  8704 */     this.g.text(c, x, y);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void text(char c, float x, float y, float z)
/*       */   {
/*  8712 */     if (this.recorder != null) this.recorder.text(c, x, y, z);
/*  8713 */     this.g.text(c, x, y, z);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void text(String str)
/*       */   {
/*  8721 */     if (this.recorder != null) this.recorder.text(str);
/*  8722 */     this.g.text(str);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void text(String str, float x, float y)
/*       */   {
/*  8733 */     if (this.recorder != null) this.recorder.text(str, x, y);
/*  8734 */     this.g.text(str, x, y);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void text(char[] chars, int start, int stop, float x, float y)
/*       */   {
/*  8744 */     if (this.recorder != null) this.recorder.text(chars, start, stop, x, y);
/*  8745 */     this.g.text(chars, start, stop, x, y);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void text(String str, float x, float y, float z)
/*       */   {
/*  8753 */     if (this.recorder != null) this.recorder.text(str, x, y, z);
/*  8754 */     this.g.text(str, x, y, z);
/*       */   }
/*       */   
/*       */ 
/*       */   public void text(char[] chars, int start, int stop, float x, float y, float z)
/*       */   {
/*  8760 */     if (this.recorder != null) this.recorder.text(chars, start, stop, x, y, z);
/*  8761 */     this.g.text(chars, start, stop, x, y, z);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void text(String str, float x1, float y1, float x2, float y2)
/*       */   {
/*  8779 */     if (this.recorder != null) this.recorder.text(str, x1, y1, x2, y2);
/*  8780 */     this.g.text(str, x1, y1, x2, y2);
/*       */   }
/*       */   
/*       */   public void text(String s, float x1, float y1, float x2, float y2, float z)
/*       */   {
/*  8785 */     if (this.recorder != null) this.recorder.text(s, x1, y1, x2, y2, z);
/*  8786 */     this.g.text(s, x1, y1, x2, y2, z);
/*       */   }
/*       */   
/*       */   public void text(int num, float x, float y)
/*       */   {
/*  8791 */     if (this.recorder != null) this.recorder.text(num, x, y);
/*  8792 */     this.g.text(num, x, y);
/*       */   }
/*       */   
/*       */   public void text(int num, float x, float y, float z)
/*       */   {
/*  8797 */     if (this.recorder != null) this.recorder.text(num, x, y, z);
/*  8798 */     this.g.text(num, x, y, z);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void text(float num, float x, float y)
/*       */   {
/*  8810 */     if (this.recorder != null) this.recorder.text(num, x, y);
/*  8811 */     this.g.text(num, x, y);
/*       */   }
/*       */   
/*       */   public void text(float num, float x, float y, float z)
/*       */   {
/*  8816 */     if (this.recorder != null) this.recorder.text(num, x, y, z);
/*  8817 */     this.g.text(num, x, y, z);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void pushMatrix()
/*       */   {
/*  8825 */     if (this.recorder != null) this.recorder.pushMatrix();
/*  8826 */     this.g.pushMatrix();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void popMatrix()
/*       */   {
/*  8834 */     if (this.recorder != null) this.recorder.popMatrix();
/*  8835 */     this.g.popMatrix();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void translate(float tx, float ty)
/*       */   {
/*  8843 */     if (this.recorder != null) this.recorder.translate(tx, ty);
/*  8844 */     this.g.translate(tx, ty);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void translate(float tx, float ty, float tz)
/*       */   {
/*  8852 */     if (this.recorder != null) this.recorder.translate(tx, ty, tz);
/*  8853 */     this.g.translate(tx, ty, tz);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void rotate(float angle)
/*       */   {
/*  8867 */     if (this.recorder != null) this.recorder.rotate(angle);
/*  8868 */     this.g.rotate(angle);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void rotateX(float angle)
/*       */   {
/*  8876 */     if (this.recorder != null) this.recorder.rotateX(angle);
/*  8877 */     this.g.rotateX(angle);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void rotateY(float angle)
/*       */   {
/*  8885 */     if (this.recorder != null) this.recorder.rotateY(angle);
/*  8886 */     this.g.rotateY(angle);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void rotateZ(float angle)
/*       */   {
/*  8899 */     if (this.recorder != null) this.recorder.rotateZ(angle);
/*  8900 */     this.g.rotateZ(angle);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void rotate(float angle, float vx, float vy, float vz)
/*       */   {
/*  8908 */     if (this.recorder != null) this.recorder.rotate(angle, vx, vy, vz);
/*  8909 */     this.g.rotate(angle, vx, vy, vz);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void scale(float s)
/*       */   {
/*  8917 */     if (this.recorder != null) this.recorder.scale(s);
/*  8918 */     this.g.scale(s);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void scale(float sx, float sy)
/*       */   {
/*  8929 */     if (this.recorder != null) this.recorder.scale(sx, sy);
/*  8930 */     this.g.scale(sx, sy);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void scale(float x, float y, float z)
/*       */   {
/*  8938 */     if (this.recorder != null) this.recorder.scale(x, y, z);
/*  8939 */     this.g.scale(x, y, z);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void skewX(float angle)
/*       */   {
/*  8947 */     if (this.recorder != null) this.recorder.skewX(angle);
/*  8948 */     this.g.skewX(angle);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void skewY(float angle)
/*       */   {
/*  8956 */     if (this.recorder != null) this.recorder.skewY(angle);
/*  8957 */     this.g.skewY(angle);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void resetMatrix()
/*       */   {
/*  8965 */     if (this.recorder != null) this.recorder.resetMatrix();
/*  8966 */     this.g.resetMatrix();
/*       */   }
/*       */   
/*       */   public void applyMatrix(PMatrix source)
/*       */   {
/*  8971 */     if (this.recorder != null) this.recorder.applyMatrix(source);
/*  8972 */     this.g.applyMatrix(source);
/*       */   }
/*       */   
/*       */   public void applyMatrix(PMatrix2D source)
/*       */   {
/*  8977 */     if (this.recorder != null) this.recorder.applyMatrix(source);
/*  8978 */     this.g.applyMatrix(source);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void applyMatrix(float n00, float n01, float n02, float n10, float n11, float n12)
/*       */   {
/*  8987 */     if (this.recorder != null) this.recorder.applyMatrix(n00, n01, n02, n10, n11, n12);
/*  8988 */     this.g.applyMatrix(n00, n01, n02, n10, n11, n12);
/*       */   }
/*       */   
/*       */   public void applyMatrix(PMatrix3D source)
/*       */   {
/*  8993 */     if (this.recorder != null) this.recorder.applyMatrix(source);
/*  8994 */     this.g.applyMatrix(source);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void applyMatrix(float n00, float n01, float n02, float n03, float n10, float n11, float n12, float n13, float n20, float n21, float n22, float n23, float n30, float n31, float n32, float n33)
/*       */   {
/*  9005 */     if (this.recorder != null) this.recorder.applyMatrix(n00, n01, n02, n03, n10, n11, n12, n13, n20, n21, n22, n23, n30, n31, n32, n33);
/*  9006 */     this.g.applyMatrix(n00, n01, n02, n03, n10, n11, n12, n13, n20, n21, n22, n23, n30, n31, n32, n33);
/*       */   }
/*       */   
/*       */   public PMatrix getMatrix()
/*       */   {
/*  9011 */     return this.g.getMatrix();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public PMatrix2D getMatrix(PMatrix2D target)
/*       */   {
/*  9020 */     return this.g.getMatrix(target);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public PMatrix3D getMatrix(PMatrix3D target)
/*       */   {
/*  9029 */     return this.g.getMatrix(target);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void setMatrix(PMatrix source)
/*       */   {
/*  9037 */     if (this.recorder != null) this.recorder.setMatrix(source);
/*  9038 */     this.g.setMatrix(source);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void setMatrix(PMatrix2D source)
/*       */   {
/*  9046 */     if (this.recorder != null) this.recorder.setMatrix(source);
/*  9047 */     this.g.setMatrix(source);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void setMatrix(PMatrix3D source)
/*       */   {
/*  9055 */     if (this.recorder != null) this.recorder.setMatrix(source);
/*  9056 */     this.g.setMatrix(source);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void printMatrix()
/*       */   {
/*  9064 */     if (this.recorder != null) this.recorder.printMatrix();
/*  9065 */     this.g.printMatrix();
/*       */   }
/*       */   
/*       */   public void beginCamera()
/*       */   {
/*  9070 */     if (this.recorder != null) this.recorder.beginCamera();
/*  9071 */     this.g.beginCamera();
/*       */   }
/*       */   
/*       */   public void endCamera()
/*       */   {
/*  9076 */     if (this.recorder != null) this.recorder.endCamera();
/*  9077 */     this.g.endCamera();
/*       */   }
/*       */   
/*       */   public void camera()
/*       */   {
/*  9082 */     if (this.recorder != null) this.recorder.camera();
/*  9083 */     this.g.camera();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   public void camera(float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ)
/*       */   {
/*  9090 */     if (this.recorder != null) this.recorder.camera(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
/*  9091 */     this.g.camera(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
/*       */   }
/*       */   
/*       */   public void printCamera()
/*       */   {
/*  9096 */     if (this.recorder != null) this.recorder.printCamera();
/*  9097 */     this.g.printCamera();
/*       */   }
/*       */   
/*       */   public void ortho()
/*       */   {
/*  9102 */     if (this.recorder != null) this.recorder.ortho();
/*  9103 */     this.g.ortho();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   public void ortho(float left, float right, float bottom, float top, float near, float far)
/*       */   {
/*  9110 */     if (this.recorder != null) this.recorder.ortho(left, right, bottom, top, near, far);
/*  9111 */     this.g.ortho(left, right, bottom, top, near, far);
/*       */   }
/*       */   
/*       */   public void perspective()
/*       */   {
/*  9116 */     if (this.recorder != null) this.recorder.perspective();
/*  9117 */     this.g.perspective();
/*       */   }
/*       */   
/*       */   public void perspective(float fovy, float aspect, float zNear, float zFar)
/*       */   {
/*  9122 */     if (this.recorder != null) this.recorder.perspective(fovy, aspect, zNear, zFar);
/*  9123 */     this.g.perspective(fovy, aspect, zNear, zFar);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   public void frustum(float left, float right, float bottom, float top, float near, float far)
/*       */   {
/*  9130 */     if (this.recorder != null) this.recorder.frustum(left, right, bottom, top, near, far);
/*  9131 */     this.g.frustum(left, right, bottom, top, near, far);
/*       */   }
/*       */   
/*       */   public void printProjection()
/*       */   {
/*  9136 */     if (this.recorder != null) this.recorder.printProjection();
/*  9137 */     this.g.printProjection();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public float screenX(float x, float y)
/*       */   {
/*  9147 */     return this.g.screenX(x, y);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public float screenY(float x, float y)
/*       */   {
/*  9157 */     return this.g.screenY(x, y);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public float screenX(float x, float y, float z)
/*       */   {
/*  9169 */     return this.g.screenX(x, y, z);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public float screenY(float x, float y, float z)
/*       */   {
/*  9181 */     return this.g.screenY(x, y, z);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public float screenZ(float x, float y, float z)
/*       */   {
/*  9197 */     return this.g.screenZ(x, y, z);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public float modelX(float x, float y, float z)
/*       */   {
/*  9211 */     return this.g.modelX(x, y, z);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public float modelY(float x, float y, float z)
/*       */   {
/*  9219 */     return this.g.modelY(x, y, z);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public float modelZ(float x, float y, float z)
/*       */   {
/*  9227 */     return this.g.modelZ(x, y, z);
/*       */   }
/*       */   
/*       */   public void pushStyle()
/*       */   {
/*  9232 */     if (this.recorder != null) this.recorder.pushStyle();
/*  9233 */     this.g.pushStyle();
/*       */   }
/*       */   
/*       */   public void popStyle()
/*       */   {
/*  9238 */     if (this.recorder != null) this.recorder.popStyle();
/*  9239 */     this.g.popStyle();
/*       */   }
/*       */   
/*       */   public void style(PStyle s)
/*       */   {
/*  9244 */     if (this.recorder != null) this.recorder.style(s);
/*  9245 */     this.g.style(s);
/*       */   }
/*       */   
/*       */   public void strokeWeight(float weight)
/*       */   {
/*  9250 */     if (this.recorder != null) this.recorder.strokeWeight(weight);
/*  9251 */     this.g.strokeWeight(weight);
/*       */   }
/*       */   
/*       */   public void strokeJoin(int join)
/*       */   {
/*  9256 */     if (this.recorder != null) this.recorder.strokeJoin(join);
/*  9257 */     this.g.strokeJoin(join);
/*       */   }
/*       */   
/*       */   public void strokeCap(int cap)
/*       */   {
/*  9262 */     if (this.recorder != null) this.recorder.strokeCap(cap);
/*  9263 */     this.g.strokeCap(cap);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void noStroke()
/*       */   {
/*  9276 */     if (this.recorder != null) this.recorder.noStroke();
/*  9277 */     this.g.noStroke();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void stroke(int rgb)
/*       */   {
/*  9288 */     if (this.recorder != null) this.recorder.stroke(rgb);
/*  9289 */     this.g.stroke(rgb);
/*       */   }
/*       */   
/*       */   public void stroke(int rgb, float alpha)
/*       */   {
/*  9294 */     if (this.recorder != null) this.recorder.stroke(rgb, alpha);
/*  9295 */     this.g.stroke(rgb, alpha);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void stroke(float gray)
/*       */   {
/*  9304 */     if (this.recorder != null) this.recorder.stroke(gray);
/*  9305 */     this.g.stroke(gray);
/*       */   }
/*       */   
/*       */   public void stroke(float gray, float alpha)
/*       */   {
/*  9310 */     if (this.recorder != null) this.recorder.stroke(gray, alpha);
/*  9311 */     this.g.stroke(gray, alpha);
/*       */   }
/*       */   
/*       */   public void stroke(float x, float y, float z)
/*       */   {
/*  9316 */     if (this.recorder != null) this.recorder.stroke(x, y, z);
/*  9317 */     this.g.stroke(x, y, z);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void stroke(float x, float y, float z, float a)
/*       */   {
/*  9344 */     if (this.recorder != null) this.recorder.stroke(x, y, z, a);
/*  9345 */     this.g.stroke(x, y, z, a);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void noTint()
/*       */   {
/*  9357 */     if (this.recorder != null) this.recorder.noTint();
/*  9358 */     this.g.noTint();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void tint(int rgb)
/*       */   {
/*  9366 */     if (this.recorder != null) this.recorder.tint(rgb);
/*  9367 */     this.g.tint(rgb);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void tint(int rgb, float alpha)
/*       */   {
/*  9377 */     if (this.recorder != null) this.recorder.tint(rgb, alpha);
/*  9378 */     this.g.tint(rgb, alpha);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void tint(float gray)
/*       */   {
/*  9386 */     if (this.recorder != null) this.recorder.tint(gray);
/*  9387 */     this.g.tint(gray);
/*       */   }
/*       */   
/*       */   public void tint(float gray, float alpha)
/*       */   {
/*  9392 */     if (this.recorder != null) this.recorder.tint(gray, alpha);
/*  9393 */     this.g.tint(gray, alpha);
/*       */   }
/*       */   
/*       */   public void tint(float x, float y, float z)
/*       */   {
/*  9398 */     if (this.recorder != null) this.recorder.tint(x, y, z);
/*  9399 */     this.g.tint(x, y, z);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void tint(float x, float y, float z, float a)
/*       */   {
/*  9433 */     if (this.recorder != null) this.recorder.tint(x, y, z, a);
/*  9434 */     this.g.tint(x, y, z, a);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void noFill()
/*       */   {
/*  9448 */     if (this.recorder != null) this.recorder.noFill();
/*  9449 */     this.g.noFill();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void fill(int rgb)
/*       */   {
/*  9458 */     if (this.recorder != null) this.recorder.fill(rgb);
/*  9459 */     this.g.fill(rgb);
/*       */   }
/*       */   
/*       */   public void fill(int rgb, float alpha)
/*       */   {
/*  9464 */     if (this.recorder != null) this.recorder.fill(rgb, alpha);
/*  9465 */     this.g.fill(rgb, alpha);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void fill(float gray)
/*       */   {
/*  9473 */     if (this.recorder != null) this.recorder.fill(gray);
/*  9474 */     this.g.fill(gray);
/*       */   }
/*       */   
/*       */   public void fill(float gray, float alpha)
/*       */   {
/*  9479 */     if (this.recorder != null) this.recorder.fill(gray, alpha);
/*  9480 */     this.g.fill(gray, alpha);
/*       */   }
/*       */   
/*       */   public void fill(float x, float y, float z)
/*       */   {
/*  9485 */     if (this.recorder != null) this.recorder.fill(x, y, z);
/*  9486 */     this.g.fill(x, y, z);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void fill(float x, float y, float z, float a)
/*       */   {
/*  9509 */     if (this.recorder != null) this.recorder.fill(x, y, z, a);
/*  9510 */     this.g.fill(x, y, z, a);
/*       */   }
/*       */   
/*       */   public void ambient(int rgb)
/*       */   {
/*  9515 */     if (this.recorder != null) this.recorder.ambient(rgb);
/*  9516 */     this.g.ambient(rgb);
/*       */   }
/*       */   
/*       */   public void ambient(float gray)
/*       */   {
/*  9521 */     if (this.recorder != null) this.recorder.ambient(gray);
/*  9522 */     this.g.ambient(gray);
/*       */   }
/*       */   
/*       */   public void ambient(float x, float y, float z)
/*       */   {
/*  9527 */     if (this.recorder != null) this.recorder.ambient(x, y, z);
/*  9528 */     this.g.ambient(x, y, z);
/*       */   }
/*       */   
/*       */   public void specular(int rgb)
/*       */   {
/*  9533 */     if (this.recorder != null) this.recorder.specular(rgb);
/*  9534 */     this.g.specular(rgb);
/*       */   }
/*       */   
/*       */   public void specular(float gray)
/*       */   {
/*  9539 */     if (this.recorder != null) this.recorder.specular(gray);
/*  9540 */     this.g.specular(gray);
/*       */   }
/*       */   
/*       */   public void specular(float x, float y, float z)
/*       */   {
/*  9545 */     if (this.recorder != null) this.recorder.specular(x, y, z);
/*  9546 */     this.g.specular(x, y, z);
/*       */   }
/*       */   
/*       */   public void shininess(float shine)
/*       */   {
/*  9551 */     if (this.recorder != null) this.recorder.shininess(shine);
/*  9552 */     this.g.shininess(shine);
/*       */   }
/*       */   
/*       */   public void emissive(int rgb)
/*       */   {
/*  9557 */     if (this.recorder != null) this.recorder.emissive(rgb);
/*  9558 */     this.g.emissive(rgb);
/*       */   }
/*       */   
/*       */   public void emissive(float gray)
/*       */   {
/*  9563 */     if (this.recorder != null) this.recorder.emissive(gray);
/*  9564 */     this.g.emissive(gray);
/*       */   }
/*       */   
/*       */   public void emissive(float x, float y, float z)
/*       */   {
/*  9569 */     if (this.recorder != null) this.recorder.emissive(x, y, z);
/*  9570 */     this.g.emissive(x, y, z);
/*       */   }
/*       */   
/*       */   public void lights()
/*       */   {
/*  9575 */     if (this.recorder != null) this.recorder.lights();
/*  9576 */     this.g.lights();
/*       */   }
/*       */   
/*       */   public void noLights()
/*       */   {
/*  9581 */     if (this.recorder != null) this.recorder.noLights();
/*  9582 */     this.g.noLights();
/*       */   }
/*       */   
/*       */   public void ambientLight(float red, float green, float blue)
/*       */   {
/*  9587 */     if (this.recorder != null) this.recorder.ambientLight(red, green, blue);
/*  9588 */     this.g.ambientLight(red, green, blue);
/*       */   }
/*       */   
/*       */ 
/*       */   public void ambientLight(float red, float green, float blue, float x, float y, float z)
/*       */   {
/*  9594 */     if (this.recorder != null) this.recorder.ambientLight(red, green, blue, x, y, z);
/*  9595 */     this.g.ambientLight(red, green, blue, x, y, z);
/*       */   }
/*       */   
/*       */ 
/*       */   public void directionalLight(float red, float green, float blue, float nx, float ny, float nz)
/*       */   {
/*  9601 */     if (this.recorder != null) this.recorder.directionalLight(red, green, blue, nx, ny, nz);
/*  9602 */     this.g.directionalLight(red, green, blue, nx, ny, nz);
/*       */   }
/*       */   
/*       */ 
/*       */   public void pointLight(float red, float green, float blue, float x, float y, float z)
/*       */   {
/*  9608 */     if (this.recorder != null) this.recorder.pointLight(red, green, blue, x, y, z);
/*  9609 */     this.g.pointLight(red, green, blue, x, y, z);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void spotLight(float red, float green, float blue, float x, float y, float z, float nx, float ny, float nz, float angle, float concentration)
/*       */   {
/*  9617 */     if (this.recorder != null) this.recorder.spotLight(red, green, blue, x, y, z, nx, ny, nz, angle, concentration);
/*  9618 */     this.g.spotLight(red, green, blue, x, y, z, nx, ny, nz, angle, concentration);
/*       */   }
/*       */   
/*       */   public void lightFalloff(float constant, float linear, float quadratic)
/*       */   {
/*  9623 */     if (this.recorder != null) this.recorder.lightFalloff(constant, linear, quadratic);
/*  9624 */     this.g.lightFalloff(constant, linear, quadratic);
/*       */   }
/*       */   
/*       */   public void lightSpecular(float x, float y, float z)
/*       */   {
/*  9629 */     if (this.recorder != null) this.recorder.lightSpecular(x, y, z);
/*  9630 */     this.g.lightSpecular(x, y, z);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void background(int rgb)
/*       */   {
/*  9648 */     if (this.recorder != null) this.recorder.background(rgb);
/*  9649 */     this.g.background(rgb);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public void background(int rgb, float alpha)
/*       */   {
/*  9657 */     if (this.recorder != null) this.recorder.background(rgb, alpha);
/*  9658 */     this.g.background(rgb, alpha);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void background(float gray)
/*       */   {
/*  9667 */     if (this.recorder != null) this.recorder.background(gray);
/*  9668 */     this.g.background(gray);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void background(float gray, float alpha)
/*       */   {
/*  9678 */     if (this.recorder != null) this.recorder.background(gray, alpha);
/*  9679 */     this.g.background(gray, alpha);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void background(float x, float y, float z)
/*       */   {
/*  9688 */     if (this.recorder != null) this.recorder.background(x, y, z);
/*  9689 */     this.g.background(x, y, z);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void background(float x, float y, float z, float a)
/*       */   {
/*  9720 */     if (this.recorder != null) this.recorder.background(x, y, z, a);
/*  9721 */     this.g.background(x, y, z, a);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void background(PImage image)
/*       */   {
/*  9738 */     if (this.recorder != null) this.recorder.background(image);
/*  9739 */     this.g.background(image);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void colorMode(int mode)
/*       */   {
/*  9748 */     if (this.recorder != null) this.recorder.colorMode(mode);
/*  9749 */     this.g.colorMode(mode);
/*       */   }
/*       */   
/*       */   public void colorMode(int mode, float max)
/*       */   {
/*  9754 */     if (this.recorder != null) this.recorder.colorMode(mode, max);
/*  9755 */     this.g.colorMode(mode, max);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void colorMode(int mode, float maxX, float maxY, float maxZ)
/*       */   {
/*  9769 */     if (this.recorder != null) this.recorder.colorMode(mode, maxX, maxY, maxZ);
/*  9770 */     this.g.colorMode(mode, maxX, maxY, maxZ);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void colorMode(int mode, float maxX, float maxY, float maxZ, float maxA)
/*       */   {
/*  9789 */     if (this.recorder != null) this.recorder.colorMode(mode, maxX, maxY, maxZ, maxA);
/*  9790 */     this.g.colorMode(mode, maxX, maxY, maxZ, maxA);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public final float alpha(int what)
/*       */   {
/*  9801 */     return this.g.alpha(what);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public final float red(int what)
/*       */   {
/*  9819 */     return this.g.red(what);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public final float green(int what)
/*       */   {
/*  9837 */     return this.g.green(what);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public final float blue(int what)
/*       */   {
/*  9854 */     return this.g.blue(what);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public final float hue(int what)
/*       */   {
/*  9871 */     return this.g.hue(what);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public final float saturation(int what)
/*       */   {
/*  9888 */     return this.g.saturation(what);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public final float brightness(int what)
/*       */   {
/*  9906 */     return this.g.brightness(what);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public int lerpColor(int c1, int c2, float amt)
/*       */   {
/*  9922 */     return this.g.lerpColor(c1, c2, amt);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static int lerpColor(int c1, int c2, float amt, int mode)
/*       */   {
/*  9931 */     return PGraphics.lerpColor(c1, c2, amt, mode);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public boolean displayable()
/*       */   {
/*  9944 */     return this.g.displayable();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void setCache(Object parent, Object storage)
/*       */   {
/*  9956 */     if (this.recorder != null) this.recorder.setCache(parent, storage);
/*  9957 */     this.g.setCache(parent, storage);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public Object getCache(Object parent)
/*       */   {
/*  9970 */     return this.g.getCache(parent);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void removeCache(Object parent)
/*       */   {
/*  9979 */     if (this.recorder != null) this.recorder.removeCache(parent);
/*  9980 */     this.g.removeCache(parent);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public int get(int x, int y)
/*       */   {
/* 10003 */     return this.g.get(x, y);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public PImage get(int x, int y, int w, int h)
/*       */   {
/* 10024 */     return this.g.get(x, y, w, h);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public PImage get()
/*       */   {
/* 10032 */     return this.g.get();
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void set(int x, int y, int c)
/*       */   {
/* 10050 */     if (this.recorder != null) this.recorder.set(x, y, c);
/* 10051 */     this.g.set(x, y, c);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void set(int x, int y, PImage src)
/*       */   {
/* 10061 */     if (this.recorder != null) this.recorder.set(x, y, src);
/* 10062 */     this.g.set(x, y, src);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void mask(int[] maskArray)
/*       */   {
/* 10082 */     if (this.recorder != null) this.recorder.mask(maskArray);
/* 10083 */     this.g.mask(maskArray);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void mask(PImage maskImg)
/*       */   {
/* 10099 */     if (this.recorder != null) this.recorder.mask(maskImg);
/* 10100 */     this.g.mask(maskImg);
/*       */   }
/*       */   
/*       */   public void filter(int kind)
/*       */   {
/* 10105 */     if (this.recorder != null) this.recorder.filter(kind);
/* 10106 */     this.g.filter(kind);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void filter(int kind, float param)
/*       */   {
/* 10136 */     if (this.recorder != null) this.recorder.filter(kind, param);
/* 10137 */     this.g.filter(kind, param);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void copy(int sx, int sy, int sw, int sh, int dx, int dy, int dw, int dh)
/*       */   {
/* 10147 */     if (this.recorder != null) this.recorder.copy(sx, sy, sw, sh, dx, dy, dw, dh);
/* 10148 */     this.g.copy(sx, sy, sw, sh, dx, dy, dw, dh);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void copy(PImage src, int sx, int sy, int sw, int sh, int dx, int dy, int dw, int dh)
/*       */   {
/* 10174 */     if (this.recorder != null) this.recorder.copy(src, sx, sy, sw, sh, dx, dy, dw, dh);
/* 10175 */     this.g.copy(src, sx, sy, sw, sh, dx, dy, dw, dh);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static int blendColor(int c1, int c2, int mode)
/*       */   {
/* 10244 */     return PGraphics.blendColor(c1, c2, mode);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void blend(int sx, int sy, int sw, int sh, int dx, int dy, int dw, int dh, int mode)
/*       */   {
/* 10255 */     if (this.recorder != null) this.recorder.blend(sx, sy, sw, sh, dx, dy, dw, dh, mode);
/* 10256 */     this.g.blend(sx, sy, sw, sh, dx, dy, dw, dh, mode);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void blend(PImage src, int sx, int sy, int sw, int sh, int dx, int dy, int dw, int dh, int mode)
/*       */   {
/* 10299 */     if (this.recorder != null) this.recorder.blend(src, sx, sy, sw, sh, dx, dy, dw, dh, mode);
/* 10300 */     this.g.blend(src, sx, sy, sw, sh, dx, dy, dw, dh, mode);
/*       */   }
/*       */ }


/* Location:              /Users/AndrewKanarek/Desktop/MICHIGAN/KiefferWork/scripts-zibo/JavaApps/EllinghamDiagram/core.jar!/processing/core/PApplet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */