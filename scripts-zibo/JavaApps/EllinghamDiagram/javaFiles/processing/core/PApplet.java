// 
// Decompiled by Procyon v0.5.30
// 

package processing.core;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.Label;
import java.awt.LayoutManager;
import java.awt.Window;
import java.awt.GraphicsEnvironment;
import java.awt.Color;
import java.awt.Insets;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.BufferedInputStream;
import java.net.URLConnection;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.BufferedReader;
import javax.swing.JFileChooser;
import java.awt.FileDialog;
import java.awt.Font;
import java.io.Reader;
import processing.xml.XMLElement;
import java.util.zip.GZIPInputStream;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.awt.MediaTracker;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.ImageProducer;
import java.awt.image.MemoryImageSource;
import java.awt.Cursor;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.awt.event.FocusEvent;
import java.awt.event.ComponentListener;
import java.awt.Rectangle;
import java.awt.Component;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentAdapter;
import java.awt.image.ImageObserver;
import java.awt.Graphics;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import javax.swing.SwingUtilities;
import java.lang.reflect.Method;
import java.awt.Toolkit;
import java.text.NumberFormat;
import java.util.regex.Pattern;
import java.util.HashMap;
import java.io.File;
import java.util.Random;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.applet.Applet;

public class PApplet extends Applet implements PConstants, Runnable, MouseListener, MouseMotionListener, KeyListener, FocusListener
{
    public static final String javaVersionName;
    public static final float javaVersion;
    public static int platform;
    public static boolean useQuartz;
    public static final int MENU_SHORTCUT;
    public PGraphics g;
    public Frame frame;
    public int screenWidth;
    public int screenHeight;
    public Dimension screen;
    public PGraphics recorder;
    public String[] args;
    public String sketchPath;
    static final boolean THREAD_DEBUG = false;
    public static final int DEFAULT_WIDTH = 100;
    public static final int DEFAULT_HEIGHT = 100;
    public static final int MIN_WINDOW_WIDTH = 128;
    public static final int MIN_WINDOW_HEIGHT = 128;
    public boolean defaultSize;
    volatile boolean resizeRequest;
    volatile int resizeWidth;
    volatile int resizeHeight;
    public int[] pixels;
    public int width;
    public int height;
    public int mouseX;
    public int mouseY;
    public int pmouseX;
    public int pmouseY;
    protected int dmouseX;
    protected int dmouseY;
    protected int emouseX;
    protected int emouseY;
    public boolean firstMouse;
    public int mouseButton;
    public boolean mousePressed;
    public MouseEvent mouseEvent;
    public char key;
    public int keyCode;
    public boolean keyPressed;
    public KeyEvent keyEvent;
    public boolean focused;
    public boolean online;
    long millisOffset;
    public float frameRate;
    protected long frameRateLastNanos;
    protected float frameRateTarget;
    protected long frameRatePeriod;
    protected boolean looping;
    protected boolean redraw;
    public int frameCount;
    public boolean finished;
    protected boolean exitCalled;
    Thread thread;
    protected RegisteredMethods sizeMethods;
    protected RegisteredMethods preMethods;
    protected RegisteredMethods drawMethods;
    protected RegisteredMethods postMethods;
    protected RegisteredMethods mouseEventMethods;
    protected RegisteredMethods keyEventMethods;
    protected RegisteredMethods disposeMethods;
    public static final String ARGS_EDITOR_LOCATION = "--editor-location";
    public static final String ARGS_EXTERNAL = "--external";
    public static final String ARGS_LOCATION = "--location";
    public static final String ARGS_DISPLAY = "--display";
    public static final String ARGS_BGCOLOR = "--bgcolor";
    public static final String ARGS_PRESENT = "--present";
    public static final String ARGS_EXCLUSIVE = "--exclusive";
    public static final String ARGS_STOP_COLOR = "--stop-color";
    public static final String ARGS_HIDE_STOP = "--hide-stop";
    public static final String ARGS_SKETCH_FOLDER = "--sketch-path";
    public static final String EXTERNAL_STOP = "__STOP__";
    public static final String EXTERNAL_MOVE = "__MOVE__";
    boolean external;
    static final String ERROR_MIN_MAX = "Cannot use min() or max() on an empty array.";
    MouseEvent[] mouseEventQueue;
    int mouseEventCount;
    KeyEvent[] keyEventQueue;
    int keyEventCount;
    static String openLauncher;
    int cursorType;
    boolean cursorVisible;
    PImage invisibleCursor;
    Random internalRandom;
    static final int PERLIN_YWRAPB = 4;
    static final int PERLIN_YWRAP = 16;
    static final int PERLIN_ZWRAPB = 8;
    static final int PERLIN_ZWRAP = 256;
    static final int PERLIN_SIZE = 4095;
    int perlin_octaves;
    float perlin_amp_falloff;
    int perlin_TWOPI;
    int perlin_PI;
    float[] perlin_cosTable;
    float[] perlin;
    Random perlinRandom;
    protected String[] loadImageFormats;
    public int requestImageMax;
    volatile int requestImageCount;
    public File selectedFile;
    protected Frame parentFrame;
    protected static HashMap<String, Pattern> matchPatterns;
    private static NumberFormat int_nf;
    private static int int_nf_digits;
    private static boolean int_nf_commas;
    private static NumberFormat float_nf;
    private static int float_nf_left;
    private static int float_nf_right;
    private static boolean float_nf_commas;
    public static final byte[] ICON_IMAGE;
    
    static {
        javaVersionName = System.getProperty("java.version");
        javaVersion = new Float(PApplet.javaVersionName.substring(0, 3));
        final String osname = System.getProperty("os.name");
        if (osname.indexOf("Mac") != -1) {
            PApplet.platform = 2;
        }
        else if (osname.indexOf("Windows") != -1) {
            PApplet.platform = 1;
        }
        else if (osname.equals("Linux")) {
            PApplet.platform = 3;
        }
        else {
            PApplet.platform = 0;
        }
        PApplet.useQuartz = true;
        MENU_SHORTCUT = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
        ICON_IMAGE = new byte[] { 71, 73, 70, 56, 57, 97, 16, 0, 16, 0, -77, 0, 0, 0, 0, 0, -1, -1, -1, 12, 12, 13, -15, -15, -14, 45, 57, 74, 54, 80, 111, 47, 71, 97, 62, 88, 117, 1, 14, 27, 7, 41, 73, 15, 52, 85, 2, 31, 55, 4, 54, 94, 18, 69, 109, 37, 87, 126, -1, -1, -1, 33, -7, 4, 1, 0, 0, 15, 0, 44, 0, 0, 0, 0, 16, 0, 16, 0, 0, 4, 122, -16, -107, 114, -86, -67, 83, 30, -42, 26, -17, -100, -45, 56, -57, -108, 48, 40, 122, -90, 104, 67, -91, -51, 32, -53, 77, -78, -100, 47, -86, 12, 76, -110, -20, -74, -101, 97, -93, 27, 40, 20, -65, 65, 48, -111, 99, -20, -112, -117, -123, -47, -105, 24, 114, -112, 74, 69, 84, 25, 93, 88, -75, 9, 46, 2, 49, 88, -116, -67, 7, -19, -83, 60, 38, 3, -34, 2, 66, -95, 27, -98, 13, 4, -17, 55, 33, 109, 11, 11, -2, -128, 121, 123, 62, 91, 120, -128, 127, 122, 115, 102, 2, 119, 0, -116, -113, -119, 6, 102, 121, -108, -126, 5, 18, 6, 4, -102, -101, -100, 114, 15, 17, 0, 59 };
    }
    
    public PApplet() {
        this.screen = Toolkit.getDefaultToolkit().getScreenSize();
        this.focused = false;
        this.online = false;
        this.frameRate = 10.0f;
        this.frameRateLastNanos = 0L;
        this.frameRateTarget = 60.0f;
        this.frameRatePeriod = 16666666L;
        this.external = false;
        this.mouseEventQueue = new MouseEvent[10];
        this.keyEventQueue = new KeyEvent[10];
        this.cursorType = 0;
        this.cursorVisible = true;
        this.perlin_octaves = 4;
        this.perlin_amp_falloff = 0.5f;
        this.requestImageMax = 4;
    }
    
    public void init() {
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        this.screenWidth = screen.width;
        this.screenHeight = screen.height;
        this.setFocusTraversalKeysEnabled(false);
        this.millisOffset = System.currentTimeMillis();
        this.finished = false;
        this.looping = true;
        this.redraw = true;
        this.firstMouse = true;
        this.sizeMethods = new RegisteredMethods();
        this.preMethods = new RegisteredMethods();
        this.drawMethods = new RegisteredMethods();
        this.postMethods = new RegisteredMethods();
        this.mouseEventMethods = new RegisteredMethods();
        this.keyEventMethods = new RegisteredMethods();
        this.disposeMethods = new RegisteredMethods();
        try {
            this.getAppletContext();
            this.online = true;
        }
        catch (NullPointerException e) {
            this.online = false;
        }
        try {
            if (this.sketchPath == null) {
                this.sketchPath = System.getProperty("user.dir");
            }
        }
        catch (Exception ex) {}
        final Dimension size = this.getSize();
        if (size.width != 0 && size.height != 0) {
            this.g = this.makeGraphics(size.width, size.height, this.getSketchRenderer(), null, true);
        }
        else {
            this.defaultSize = true;
            final int w = this.getSketchWidth();
            final int h = this.getSketchHeight();
            this.g = this.makeGraphics(w, h, this.getSketchRenderer(), null, true);
            this.setSize(w, h);
            this.setPreferredSize(new Dimension(w, h));
        }
        this.width = this.g.width;
        this.height = this.g.height;
        this.addListeners();
        this.start();
    }
    
    public int getSketchWidth() {
        return 100;
    }
    
    public int getSketchHeight() {
        return 100;
    }
    
    public String getSketchRenderer() {
        return "processing.core.PGraphicsJava2D";
    }
    
    public void start() {
        this.finished = false;
        if (this.thread != null) {
            return;
        }
        (this.thread = new Thread(this, "Animation Thread")).start();
    }
    
    public void stop() {
        this.finished = true;
        if (this.thread == null) {
            return;
        }
        this.thread = null;
        if (this.g != null) {
            this.g.dispose();
        }
        this.disposeMethods.handle();
    }
    
    public void destroy() {
        this.stop();
    }
    
    public void registerSize(final Object o) {
        final Class[] methodArgs = { Integer.TYPE, Integer.TYPE };
        this.registerWithArgs(this.sizeMethods, "size", o, methodArgs);
    }
    
    public void registerPre(final Object o) {
        this.registerNoArgs(this.preMethods, "pre", o);
    }
    
    public void registerDraw(final Object o) {
        this.registerNoArgs(this.drawMethods, "draw", o);
    }
    
    public void registerPost(final Object o) {
        this.registerNoArgs(this.postMethods, "post", o);
    }
    
    public void registerMouseEvent(final Object o) {
        final Class[] methodArgs = { MouseEvent.class };
        this.registerWithArgs(this.mouseEventMethods, "mouseEvent", o, methodArgs);
    }
    
    public void registerKeyEvent(final Object o) {
        final Class[] methodArgs = { KeyEvent.class };
        this.registerWithArgs(this.keyEventMethods, "keyEvent", o, methodArgs);
    }
    
    public void registerDispose(final Object o) {
        this.registerNoArgs(this.disposeMethods, "dispose", o);
    }
    
    protected void registerNoArgs(final RegisteredMethods meth, final String name, final Object o) {
        final Class<?> c = o.getClass();
        try {
            final Method method = c.getMethod(name, (Class<?>[])new Class[0]);
            meth.add(o, method);
        }
        catch (NoSuchMethodException nsme) {
            this.die("There is no public " + name + "() method in the class " + o.getClass().getName());
        }
        catch (Exception e) {
            this.die("Could not register " + name + " + () for " + o, e);
        }
    }
    
    protected void registerWithArgs(final RegisteredMethods meth, final String name, final Object o, final Class<?>[] cargs) {
        final Class<?> c = o.getClass();
        try {
            final Method method = c.getMethod(name, cargs);
            meth.add(o, method);
        }
        catch (NoSuchMethodException nsme) {
            this.die("There is no public " + name + "() method in the class " + o.getClass().getName());
        }
        catch (Exception e) {
            this.die("Could not register " + name + " + () for " + o, e);
        }
    }
    
    public void unregisterSize(final Object o) {
        final Class[] methodArgs = { Integer.TYPE, Integer.TYPE };
        this.unregisterWithArgs(this.sizeMethods, "size", o, methodArgs);
    }
    
    public void unregisterPre(final Object o) {
        this.unregisterNoArgs(this.preMethods, "pre", o);
    }
    
    public void unregisterDraw(final Object o) {
        this.unregisterNoArgs(this.drawMethods, "draw", o);
    }
    
    public void unregisterPost(final Object o) {
        this.unregisterNoArgs(this.postMethods, "post", o);
    }
    
    public void unregisterMouseEvent(final Object o) {
        final Class[] methodArgs = { MouseEvent.class };
        this.unregisterWithArgs(this.mouseEventMethods, "mouseEvent", o, methodArgs);
    }
    
    public void unregisterKeyEvent(final Object o) {
        final Class[] methodArgs = { KeyEvent.class };
        this.unregisterWithArgs(this.keyEventMethods, "keyEvent", o, methodArgs);
    }
    
    public void unregisterDispose(final Object o) {
        this.unregisterNoArgs(this.disposeMethods, "dispose", o);
    }
    
    protected void unregisterNoArgs(final RegisteredMethods meth, final String name, final Object o) {
        final Class<?> c = o.getClass();
        try {
            final Method method = c.getMethod(name, (Class<?>[])new Class[0]);
            meth.remove(o, method);
        }
        catch (Exception e) {
            this.die("Could not unregister " + name + "() for " + o, e);
        }
    }
    
    protected void unregisterWithArgs(final RegisteredMethods meth, final String name, final Object o, final Class<?>[] cargs) {
        final Class<?> c = o.getClass();
        try {
            final Method method = c.getMethod(name, cargs);
            meth.remove(o, method);
        }
        catch (Exception e) {
            this.die("Could not unregister " + name + "() for " + o, e);
        }
    }
    
    public void setup() {
    }
    
    public void draw() {
        this.finished = true;
    }
    
    protected void resizeRenderer(final int iwidth, final int iheight) {
        if (this.width != iwidth || this.height != iheight) {
            this.g.setSize(iwidth, iheight);
            this.width = iwidth;
            this.height = iheight;
        }
    }
    
    public void size(final int iwidth, final int iheight) {
        this.size(iwidth, iheight, "processing.core.PGraphicsJava2D", null);
    }
    
    public void size(final int iwidth, final int iheight, final String irenderer) {
        this.size(iwidth, iheight, irenderer, null);
    }
    
    public void size(final int iwidth, final int iheight, final String irenderer, String ipath) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                PApplet.this.setPreferredSize(new Dimension(iwidth, iheight));
                PApplet.this.setSize(iwidth, iheight);
            }
        });
        if (ipath != null) {
            ipath = this.savePath(ipath);
        }
        final String currentRenderer = this.g.getClass().getName();
        if (currentRenderer.equals(irenderer)) {
            this.resizeRenderer(iwidth, iheight);
            return;
        }
        this.g = this.makeGraphics(iwidth, iheight, irenderer, ipath, true);
        this.width = iwidth;
        this.height = iheight;
        this.defaultSize = false;
        throw new RendererChangeException();
    }
    
    public PGraphics createGraphics(final int iwidth, final int iheight, final String irenderer) {
        final PGraphics pg = this.makeGraphics(iwidth, iheight, irenderer, null, false);
        return pg;
    }
    
    public PGraphics createGraphics(final int iwidth, final int iheight, final String irenderer, String ipath) {
        if (ipath != null) {
            ipath = this.savePath(ipath);
        }
        final PGraphics pg = this.makeGraphics(iwidth, iheight, irenderer, ipath, false);
        pg.parent = this;
        return pg;
    }
    
    protected PGraphics makeGraphics(final int iwidth, final int iheight, final String irenderer, final String ipath, final boolean iprimary) {
        if (irenderer.equals("processing.opengl.PGraphicsOpenGL") && PApplet.platform == 1) {
            final String s = System.getProperty("java.version");
            if (s != null && s.equals("1.5.0_10")) {
                System.err.println("OpenGL support is broken with Java 1.5.0_10");
                System.err.println("See http://dev.processing.org/bugs/show_bug.cgi?id=513 for more info.");
                throw new RuntimeException("Please update your Java installation (see bug #513)");
            }
        }
        final String openglError = "Before using OpenGL, first select Import Library > opengl from the Sketch menu.";
        try {
            final Class<?> rendererClass = Thread.currentThread().getContextClassLoader().loadClass(irenderer);
            final Constructor<?> constructor = rendererClass.getConstructor((Class<?>[])new Class[0]);
            final PGraphics pg = (PGraphics)constructor.newInstance(new Object[0]);
            pg.setParent(this);
            pg.setPrimary(iprimary);
            if (ipath != null) {
                pg.setPath(ipath);
            }
            pg.setSize(iwidth, iheight);
            return pg;
        }
        catch (InvocationTargetException ite) {
            final String msg = ite.getTargetException().getMessage();
            if (msg != null && msg.indexOf("no jogl in java.library.path") != -1) {
                throw new RuntimeException(String.valueOf(openglError) + " (The native library is missing.)");
            }
            ite.getTargetException().printStackTrace();
            final Throwable target = ite.getTargetException();
            if (PApplet.platform == 2) {
                target.printStackTrace(System.out);
            }
            throw new RuntimeException(target.getMessage());
        }
        catch (ClassNotFoundException cnfe) {
            if (cnfe.getMessage().indexOf("processing.opengl.PGraphicsGL") != -1) {
                throw new RuntimeException(String.valueOf(openglError) + " (The library .jar file is missing.)");
            }
            throw new RuntimeException("You need to use \"Import Library\" to add " + irenderer + " to your sketch.");
        }
        catch (Exception e) {
            if (e instanceof IllegalArgumentException || e instanceof NoSuchMethodException || e instanceof IllegalAccessException) {
                e.printStackTrace();
                final String msg = String.valueOf(irenderer) + " needs to be updated " + "for the current release of Processing.";
                throw new RuntimeException(msg);
            }
            if (PApplet.platform == 2) {
                e.printStackTrace(System.out);
            }
            throw new RuntimeException(e.getMessage());
        }
    }
    
    public PImage createImage(final int wide, final int high, final int format) {
        final PImage image = new PImage(wide, high, format);
        image.parent = this;
        return image;
    }
    
    public void update(final Graphics screen) {
        this.paint(screen);
    }
    
    public void paint(final Graphics screen) {
        if (this.frameCount == 0) {
            return;
        }
        if (this.g != null && this.g.image != null) {
            screen.drawImage(this.g.image, 0, 0, null);
        }
    }
    
    protected void paint() {
        try {
            final Graphics screen = this.getGraphics();
            if (screen != null) {
                if (this.g != null && this.g.image != null) {
                    screen.drawImage(this.g.image, 0, 0, null);
                }
                Toolkit.getDefaultToolkit().sync();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void run() {
        long beforeTime = System.nanoTime();
        long overSleepTime = 0L;
        int noDelays = 0;
        final int NO_DELAYS_PER_YIELD = 15;
        while (Thread.currentThread() == this.thread && !this.finished) {
            if (this.resizeRequest) {
                this.resizeRenderer(this.resizeWidth, this.resizeHeight);
                this.resizeRequest = false;
            }
            this.handleDraw();
            if (this.frameCount == 1) {
                this.requestFocusInWindow();
            }
            final long afterTime = System.nanoTime();
            final long timeDiff = afterTime - beforeTime;
            final long sleepTime = this.frameRatePeriod - timeDiff - overSleepTime;
            if (sleepTime > 0L) {
                try {
                    Thread.sleep(sleepTime / 1000000L, (int)(sleepTime % 1000000L));
                    noDelays = 0;
                }
                catch (InterruptedException ex) {}
                overSleepTime = System.nanoTime() - afterTime - sleepTime;
            }
            else {
                overSleepTime = 0L;
                if (noDelays > 15) {
                    Thread.yield();
                    noDelays = 0;
                }
            }
            beforeTime = System.nanoTime();
        }
        this.stop();
        if (this.exitCalled) {
            this.exit2();
        }
    }
    
    public void handleDraw() {
        if (this.g != null && (this.looping || this.redraw)) {
            if (!this.g.canDraw()) {
                return;
            }
            this.g.beginDraw();
            if (this.recorder != null) {
                this.recorder.beginDraw();
            }
            final long now = System.nanoTime();
            if (this.frameCount == 0) {
                try {
                    this.setup();
                }
                catch (RendererChangeException e) {
                    return;
                }
                this.defaultSize = false;
            }
            else {
                final double rate = 1000000.0 / ((now - this.frameRateLastNanos) / 1000000.0);
                final float instantaneousRate = (float)rate / 1000.0f;
                this.frameRate = this.frameRate * 0.9f + instantaneousRate * 0.1f;
                this.preMethods.handle();
                this.pmouseX = this.dmouseX;
                this.pmouseY = this.dmouseY;
                this.draw();
                this.dmouseX = this.mouseX;
                this.dmouseY = this.mouseY;
                this.dequeueMouseEvents();
                this.dequeueKeyEvents();
                this.drawMethods.handle();
                this.redraw = false;
            }
            this.g.endDraw();
            if (this.recorder != null) {
                this.recorder.endDraw();
            }
            this.frameRateLastNanos = now;
            ++this.frameCount;
            this.paint();
            this.postMethods.handle();
        }
    }
    
    public synchronized void redraw() {
        if (!this.looping) {
            this.redraw = true;
        }
    }
    
    public synchronized void loop() {
        if (!this.looping) {
            this.looping = true;
        }
    }
    
    public synchronized void noLoop() {
        if (this.looping) {
            this.looping = false;
        }
    }
    
    public void addListeners() {
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addKeyListener(this);
        this.addFocusListener(this);
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(final ComponentEvent e) {
                final Component c = e.getComponent();
                final Rectangle bounds = c.getBounds();
                PApplet.this.resizeRequest = true;
                PApplet.this.resizeWidth = bounds.width;
                PApplet.this.resizeHeight = bounds.height;
            }
        });
    }
    
    protected void enqueueMouseEvent(final MouseEvent e) {
        synchronized (this.mouseEventQueue) {
            if (this.mouseEventCount == this.mouseEventQueue.length) {
                final MouseEvent[] temp = new MouseEvent[this.mouseEventCount << 1];
                System.arraycopy(this.mouseEventQueue, 0, temp, 0, this.mouseEventCount);
                this.mouseEventQueue = temp;
            }
            this.mouseEventQueue[this.mouseEventCount++] = e;
        }
        // monitorexit(this.mouseEventQueue)
    }
    
    protected void dequeueMouseEvents() {
        synchronized (this.mouseEventQueue) {
            for (int i = 0; i < this.mouseEventCount; ++i) {
                this.handleMouseEvent(this.mouseEvent = this.mouseEventQueue[i]);
            }
            this.mouseEventCount = 0;
        }
        // monitorexit(this.mouseEventQueue)
    }
    
    protected void handleMouseEvent(final MouseEvent event) {
        final int id = event.getID();
        if (id == 506 || id == 503) {
            this.pmouseX = this.emouseX;
            this.pmouseY = this.emouseY;
            this.mouseX = event.getX();
            this.mouseY = event.getY();
        }
        this.mouseEvent = event;
        final int modifiers = event.getModifiers();
        if ((modifiers & 0x10) != 0x0) {
            this.mouseButton = 37;
        }
        else if ((modifiers & 0x8) != 0x0) {
            this.mouseButton = 3;
        }
        else if ((modifiers & 0x4) != 0x0) {
            this.mouseButton = 39;
        }
        if (PApplet.platform == 2 && this.mouseEvent.isPopupTrigger()) {
            this.mouseButton = 39;
        }
        this.mouseEventMethods.handle(new Object[] { event });
        if (this.firstMouse) {
            this.pmouseX = this.mouseX;
            this.pmouseY = this.mouseY;
            this.dmouseX = this.mouseX;
            this.dmouseY = this.mouseY;
            this.firstMouse = false;
        }
        switch (id) {
            case 501: {
                this.mousePressed = true;
                this.mousePressed();
                break;
            }
            case 502: {
                this.mousePressed = false;
                this.mouseReleased();
                break;
            }
            case 500: {
                this.mouseClicked();
                break;
            }
            case 506: {
                this.mouseDragged();
                break;
            }
            case 503: {
                this.mouseMoved();
                break;
            }
        }
        if (id == 506 || id == 503) {
            this.emouseX = this.mouseX;
            this.emouseY = this.mouseY;
        }
    }
    
    protected void checkMouseEvent(final MouseEvent event) {
        if (this.looping) {
            this.enqueueMouseEvent(event);
        }
        else {
            this.handleMouseEvent(event);
        }
    }
    
    public void mousePressed(final MouseEvent e) {
        this.checkMouseEvent(e);
    }
    
    public void mouseReleased(final MouseEvent e) {
        this.checkMouseEvent(e);
    }
    
    public void mouseClicked(final MouseEvent e) {
        this.checkMouseEvent(e);
    }
    
    public void mouseEntered(final MouseEvent e) {
        this.checkMouseEvent(e);
    }
    
    public void mouseExited(final MouseEvent e) {
        this.checkMouseEvent(e);
    }
    
    public void mouseDragged(final MouseEvent e) {
        this.checkMouseEvent(e);
    }
    
    public void mouseMoved(final MouseEvent e) {
        this.checkMouseEvent(e);
    }
    
    public void mousePressed() {
    }
    
    public void mouseReleased() {
    }
    
    public void mouseClicked() {
    }
    
    public void mouseDragged() {
    }
    
    public void mouseMoved() {
    }
    
    protected void enqueueKeyEvent(final KeyEvent e) {
        synchronized (this.keyEventQueue) {
            if (this.keyEventCount == this.keyEventQueue.length) {
                final KeyEvent[] temp = new KeyEvent[this.keyEventCount << 1];
                System.arraycopy(this.keyEventQueue, 0, temp, 0, this.keyEventCount);
                this.keyEventQueue = temp;
            }
            this.keyEventQueue[this.keyEventCount++] = e;
        }
        // monitorexit(this.keyEventQueue)
    }
    
    protected void dequeueKeyEvents() {
        synchronized (this.keyEventQueue) {
            for (int i = 0; i < this.keyEventCount; ++i) {
                this.handleKeyEvent(this.keyEvent = this.keyEventQueue[i]);
            }
            this.keyEventCount = 0;
        }
        // monitorexit(this.keyEventQueue)
    }
    
    protected void handleKeyEvent(final KeyEvent event) {
        this.keyEvent = event;
        this.key = event.getKeyChar();
        this.keyCode = event.getKeyCode();
        this.keyEventMethods.handle(new Object[] { event });
        switch (event.getID()) {
            case 401: {
                this.keyPressed = true;
                this.keyPressed();
                break;
            }
            case 402: {
                this.keyPressed = false;
                this.keyReleased();
                break;
            }
            case 400: {
                this.keyTyped();
                break;
            }
        }
        if (event.getID() == 401) {
            if (this.key == '\u001b') {
                this.exit();
            }
            if (this.external && event.getModifiers() == PApplet.MENU_SHORTCUT && event.getKeyCode() == 87) {
                this.exit();
            }
        }
    }
    
    protected void checkKeyEvent(final KeyEvent event) {
        if (this.looping) {
            this.enqueueKeyEvent(event);
        }
        else {
            this.handleKeyEvent(event);
        }
    }
    
    public void keyPressed(final KeyEvent e) {
        this.checkKeyEvent(e);
    }
    
    public void keyReleased(final KeyEvent e) {
        this.checkKeyEvent(e);
    }
    
    public void keyTyped(final KeyEvent e) {
        this.checkKeyEvent(e);
    }
    
    public void keyPressed() {
    }
    
    public void keyReleased() {
    }
    
    public void keyTyped() {
    }
    
    public void focusGained() {
    }
    
    public void focusGained(final FocusEvent e) {
        this.focused = true;
        this.focusGained();
    }
    
    public void focusLost() {
    }
    
    public void focusLost(final FocusEvent e) {
        this.focused = false;
        this.focusLost();
    }
    
    public int millis() {
        return (int)(System.currentTimeMillis() - this.millisOffset);
    }
    
    public static int second() {
        return Calendar.getInstance().get(13);
    }
    
    public static int minute() {
        return Calendar.getInstance().get(12);
    }
    
    public static int hour() {
        return Calendar.getInstance().get(11);
    }
    
    public static int day() {
        return Calendar.getInstance().get(5);
    }
    
    public static int month() {
        return Calendar.getInstance().get(2) + 1;
    }
    
    public static int year() {
        return Calendar.getInstance().get(1);
    }
    
    public void delay(final int napTime) {
        if (this.frameCount != 0 && napTime > 0) {
            try {
                Thread.sleep(napTime);
            }
            catch (InterruptedException ex) {}
        }
    }
    
    public void frameRate(final float newRateTarget) {
        this.frameRateTarget = newRateTarget;
        this.frameRatePeriod = (long)(1.0E9 / this.frameRateTarget);
    }
    
    public String param(final String what) {
        if (this.online) {
            return this.getParameter(what);
        }
        System.err.println("param() only works inside a web browser");
        return null;
    }
    
    public void status(final String what) {
        if (this.online) {
            this.showStatus(what);
        }
        else {
            System.out.println(what);
        }
    }
    
    public void link(final String here) {
        this.link(here, null);
    }
    
    public void link(String url, final String frameTitle) {
        if (this.online) {
            try {
                if (frameTitle == null) {
                    this.getAppletContext().showDocument(new URL(url));
                    return;
                }
                this.getAppletContext().showDocument(new URL(url), frameTitle);
                return;
            }
            catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Could not open " + url);
            }
        }
        try {
            if (PApplet.platform == 1) {
                url = url.replaceAll("&", "^&");
                Runtime.getRuntime().exec("cmd /c start " + url);
            }
            else if (PApplet.platform == 2) {
                try {
                    final Class<?> eieio = Class.forName("com.apple.eio.FileManager");
                    final Method openMethod = eieio.getMethod("openURL", String.class);
                    openMethod.invoke(null, url);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                open(url);
            }
        }
        catch (IOException e2) {
            e2.printStackTrace();
            throw new RuntimeException("Could not open " + url);
        }
    }
    
    public static void open(final String filename) {
        open(new String[] { filename });
    }
    
    public static Process open(final String[] argv) {
        String[] params = null;
        if (PApplet.platform == 1) {
            params = new String[] { "cmd", "/c" };
        }
        else if (PApplet.platform == 2) {
            params = new String[] { "open" };
        }
        else if (PApplet.platform == 3) {
            if (PApplet.openLauncher == null) {
                try {
                    final Process p = Runtime.getRuntime().exec(new String[] { "gnome-open" });
                    p.waitFor();
                    PApplet.openLauncher = "gnome-open";
                }
                catch (Exception ex) {}
            }
            if (PApplet.openLauncher == null) {
                try {
                    final Process p = Runtime.getRuntime().exec(new String[] { "kde-open" });
                    p.waitFor();
                    PApplet.openLauncher = "kde-open";
                }
                catch (Exception ex2) {}
            }
            if (PApplet.openLauncher == null) {
                System.err.println("Could not find gnome-open or kde-open, the open() command may not work.");
            }
            if (PApplet.openLauncher != null) {
                params = new String[] { PApplet.openLauncher };
            }
        }
        if (params == null) {
            return exec(argv);
        }
        if (params[0].equals(argv[0])) {
            return exec(argv);
        }
        params = concat(params, argv);
        return exec(params);
    }
    
    public static Process exec(final String[] argv) {
        try {
            return Runtime.getRuntime().exec(argv);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Could not open " + join(argv, ' '));
        }
    }
    
    public void die(final String what) {
        this.stop();
        throw new RuntimeException(what);
    }
    
    public void die(final String what, final Exception e) {
        if (e != null) {
            e.printStackTrace();
        }
        this.die(what);
    }
    
    public void exit() {
        if (this.thread == null) {
            this.exit2();
        }
        else if (this.looping) {
            this.finished = true;
            this.exitCalled = true;
        }
        else if (!this.looping) {
            this.stop();
            this.exit2();
        }
    }
    
    void exit2() {
        try {
            System.exit(0);
        }
        catch (SecurityException ex) {}
    }
    
    public void method(final String name) {
        try {
            final Method method = this.getClass().getMethod(name, (Class<?>[])new Class[0]);
            method.invoke(this, new Object[0]);
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e2) {
            e2.printStackTrace();
        }
        catch (InvocationTargetException e3) {
            e3.getTargetException().printStackTrace();
        }
        catch (NoSuchMethodException nsme) {
            System.err.println("There is no public " + name + "() method " + "in the class " + this.getClass().getName());
        }
        catch (Exception e4) {
            e4.printStackTrace();
        }
    }
    
    public void thread(final String name) {
        final Thread later = new Thread() {
            public void run() {
                PApplet.this.method(name);
            }
        };
        later.start();
    }
    
    public void save(final String filename) {
        this.g.save(this.savePath(filename));
    }
    
    public void saveFrame() {
        try {
            this.g.save(this.savePath("screen-" + nf(this.frameCount, 4) + ".tif"));
        }
        catch (SecurityException se) {
            System.err.println("Can't use saveFrame() when running in a browser, unless using a signed applet.");
        }
    }
    
    public void saveFrame(final String what) {
        try {
            this.g.save(this.savePath(this.insertFrame(what)));
        }
        catch (SecurityException se) {
            System.err.println("Can't use saveFrame() when running in a browser, unless using a signed applet.");
        }
    }
    
    protected String insertFrame(final String what) {
        final int first = what.indexOf(35);
        final int last = what.lastIndexOf(35);
        if (first != -1 && last - first > 0) {
            final String prefix = what.substring(0, first);
            final int count = last - first + 1;
            final String suffix = what.substring(last + 1);
            return String.valueOf(prefix) + nf(this.frameCount, count) + suffix;
        }
        return what;
    }
    
    public void cursor(final int cursorType) {
        this.setCursor(Cursor.getPredefinedCursor(cursorType));
        this.cursorVisible = true;
        this.cursorType = cursorType;
    }
    
    public void cursor(final PImage image) {
        this.cursor(image, image.width / 2, image.height / 2);
    }
    
    public void cursor(final PImage image, final int hotspotX, final int hotspotY) {
        final Image jimage = this.createImage(new MemoryImageSource(image.width, image.height, image.pixels, 0, image.width));
        final Point hotspot = new Point(hotspotX, hotspotY);
        final Toolkit tk = Toolkit.getDefaultToolkit();
        final Cursor cursor = tk.createCustomCursor(jimage, hotspot, "Custom Cursor");
        this.setCursor(cursor);
        this.cursorVisible = true;
    }
    
    public void cursor() {
        if (!this.cursorVisible) {
            this.cursorVisible = true;
            this.setCursor(Cursor.getPredefinedCursor(this.cursorType));
        }
    }
    
    public void noCursor() {
        if (!this.cursorVisible) {
            return;
        }
        if (this.invisibleCursor == null) {
            this.invisibleCursor = new PImage(16, 16, 2);
        }
        this.cursor(this.invisibleCursor, 8, 8);
        this.cursorVisible = false;
    }
    
    public static void print(final byte what) {
        System.out.print(what);
        System.out.flush();
    }
    
    public static void print(final boolean what) {
        System.out.print(what);
        System.out.flush();
    }
    
    public static void print(final char what) {
        System.out.print(what);
        System.out.flush();
    }
    
    public static void print(final int what) {
        System.out.print(what);
        System.out.flush();
    }
    
    public static void print(final float what) {
        System.out.print(what);
        System.out.flush();
    }
    
    public static void print(final String what) {
        System.out.print(what);
        System.out.flush();
    }
    
    public static void print(final Object what) {
        if (what == null) {
            System.out.print("null");
        }
        else {
            System.out.println(what.toString());
        }
    }
    
    public static void println() {
        System.out.println();
    }
    
    public static void println(final byte what) {
        print(what);
        System.out.println();
    }
    
    public static void println(final boolean what) {
        print(what);
        System.out.println();
    }
    
    public static void println(final char what) {
        print(what);
        System.out.println();
    }
    
    public static void println(final int what) {
        print(what);
        System.out.println();
    }
    
    public static void println(final float what) {
        print(what);
        System.out.println();
    }
    
    public static void println(final String what) {
        print(what);
        System.out.println();
    }
    
    public static void println(final Object what) {
        if (what == null) {
            System.out.println("null");
        }
        else {
            final String name = what.getClass().getName();
            if (name.charAt(0) == '[') {
                switch (name.charAt(1)) {
                    case '[': {
                        System.out.println(what);
                        break;
                    }
                    case 'L': {
                        final Object[] poo = (Object[])what;
                        for (int i = 0; i < poo.length; ++i) {
                            if (poo[i] instanceof String) {
                                System.out.println("[" + i + "] \"" + poo[i] + "\"");
                            }
                            else {
                                System.out.println("[" + i + "] " + poo[i]);
                            }
                        }
                        break;
                    }
                    case 'Z': {
                        final boolean[] zz = (boolean[])what;
                        for (int j = 0; j < zz.length; ++j) {
                            System.out.println("[" + j + "] " + zz[j]);
                        }
                        break;
                    }
                    case 'B': {
                        final byte[] bb = (byte[])what;
                        for (int k = 0; k < bb.length; ++k) {
                            System.out.println("[" + k + "] " + bb[k]);
                        }
                        break;
                    }
                    case 'C': {
                        final char[] cc = (char[])what;
                        for (int l = 0; l < cc.length; ++l) {
                            System.out.println("[" + l + "] '" + cc[l] + "'");
                        }
                        break;
                    }
                    case 'I': {
                        final int[] ii = (int[])what;
                        for (int m = 0; m < ii.length; ++m) {
                            System.out.println("[" + m + "] " + ii[m]);
                        }
                        break;
                    }
                    case 'F': {
                        final float[] ff = (float[])what;
                        for (int i2 = 0; i2 < ff.length; ++i2) {
                            System.out.println("[" + i2 + "] " + ff[i2]);
                        }
                        break;
                    }
                    case 'D': {
                        final double[] dd = (double[])what;
                        for (int i3 = 0; i3 < dd.length; ++i3) {
                            System.out.println("[" + i3 + "] " + dd[i3]);
                        }
                        break;
                    }
                    default: {
                        System.out.println(what);
                        break;
                    }
                }
            }
            else {
                System.out.println(what);
            }
        }
    }
    
    public static final float abs(final float n) {
        return (n < 0.0f) ? (-n) : n;
    }
    
    public static final int abs(final int n) {
        return (n < 0) ? (-n) : n;
    }
    
    public static final float sq(final float a) {
        return a * a;
    }
    
    public static final float sqrt(final float a) {
        return (float)Math.sqrt(a);
    }
    
    public static final float log(final float a) {
        return (float)Math.log(a);
    }
    
    public static final float exp(final float a) {
        return (float)Math.exp(a);
    }
    
    public static final float pow(final float a, final float b) {
        return (float)Math.pow(a, b);
    }
    
    public static final int max(final int a, final int b) {
        return (a > b) ? a : b;
    }
    
    public static final float max(final float a, final float b) {
        return (a > b) ? a : b;
    }
    
    public static final int max(final int a, final int b, final int c) {
        return (a > b) ? ((a > c) ? a : c) : ((b > c) ? b : c);
    }
    
    public static final float max(final float a, final float b, final float c) {
        return (a > b) ? ((a > c) ? a : c) : ((b > c) ? b : c);
    }
    
    public static final int max(final int[] list) {
        if (list.length == 0) {
            throw new ArrayIndexOutOfBoundsException("Cannot use min() or max() on an empty array.");
        }
        int max = list[0];
        for (int i = 1; i < list.length; ++i) {
            if (list[i] > max) {
                max = list[i];
            }
        }
        return max;
    }
    
    public static final float max(final float[] list) {
        if (list.length == 0) {
            throw new ArrayIndexOutOfBoundsException("Cannot use min() or max() on an empty array.");
        }
        float max = list[0];
        for (int i = 1; i < list.length; ++i) {
            if (list[i] > max) {
                max = list[i];
            }
        }
        return max;
    }
    
    public static final int min(final int a, final int b) {
        return (a < b) ? a : b;
    }
    
    public static final float min(final float a, final float b) {
        return (a < b) ? a : b;
    }
    
    public static final int min(final int a, final int b, final int c) {
        return (a < b) ? ((a < c) ? a : c) : ((b < c) ? b : c);
    }
    
    public static final float min(final float a, final float b, final float c) {
        return (a < b) ? ((a < c) ? a : c) : ((b < c) ? b : c);
    }
    
    public static final int min(final int[] list) {
        if (list.length == 0) {
            throw new ArrayIndexOutOfBoundsException("Cannot use min() or max() on an empty array.");
        }
        int min = list[0];
        for (int i = 1; i < list.length; ++i) {
            if (list[i] < min) {
                min = list[i];
            }
        }
        return min;
    }
    
    public static final float min(final float[] list) {
        if (list.length == 0) {
            throw new ArrayIndexOutOfBoundsException("Cannot use min() or max() on an empty array.");
        }
        float min = list[0];
        for (int i = 1; i < list.length; ++i) {
            if (list[i] < min) {
                min = list[i];
            }
        }
        return min;
    }
    
    public static final int constrain(final int amt, final int low, final int high) {
        return (amt < low) ? low : ((amt > high) ? high : amt);
    }
    
    public static final float constrain(final float amt, final float low, final float high) {
        return (amt < low) ? low : ((amt > high) ? high : amt);
    }
    
    public static final float sin(final float angle) {
        return (float)Math.sin(angle);
    }
    
    public static final float cos(final float angle) {
        return (float)Math.cos(angle);
    }
    
    public static final float tan(final float angle) {
        return (float)Math.tan(angle);
    }
    
    public static final float asin(final float value) {
        return (float)Math.asin(value);
    }
    
    public static final float acos(final float value) {
        return (float)Math.acos(value);
    }
    
    public static final float atan(final float value) {
        return (float)Math.atan(value);
    }
    
    public static final float atan2(final float a, final float b) {
        return (float)Math.atan2(a, b);
    }
    
    public static final float degrees(final float radians) {
        return radians * 57.295776f;
    }
    
    public static final float radians(final float degrees) {
        return degrees * 0.017453292f;
    }
    
    public static final int ceil(final float what) {
        return (int)Math.ceil(what);
    }
    
    public static final int floor(final float what) {
        return (int)Math.floor(what);
    }
    
    public static final int round(final float what) {
        return Math.round(what);
    }
    
    public static final float mag(final float a, final float b) {
        return (float)Math.sqrt(a * a + b * b);
    }
    
    public static final float mag(final float a, final float b, final float c) {
        return (float)Math.sqrt(a * a + b * b + c * c);
    }
    
    public static final float dist(final float x1, final float y1, final float x2, final float y2) {
        return sqrt(sq(x2 - x1) + sq(y2 - y1));
    }
    
    public static final float dist(final float x1, final float y1, final float z1, final float x2, final float y2, final float z2) {
        return sqrt(sq(x2 - x1) + sq(y2 - y1) + sq(z2 - z1));
    }
    
    public static final float lerp(final float start, final float stop, final float amt) {
        return start + (stop - start) * amt;
    }
    
    public static final float norm(final float value, final float start, final float stop) {
        return (value - start) / (stop - start);
    }
    
    public static final float map(final float value, final float istart, final float istop, final float ostart, final float ostop) {
        return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
    }
    
    public final float random(final float howbig) {
        if (howbig == 0.0f) {
            return 0.0f;
        }
        if (this.internalRandom == null) {
            this.internalRandom = new Random();
        }
        float value = 0.0f;
        do {
            value = this.internalRandom.nextFloat() * howbig;
        } while (value == howbig);
        return value;
    }
    
    public final float random(final float howsmall, final float howbig) {
        if (howsmall >= howbig) {
            return howsmall;
        }
        final float diff = howbig - howsmall;
        return this.random(diff) + howsmall;
    }
    
    public final void randomSeed(final long what) {
        if (this.internalRandom == null) {
            this.internalRandom = new Random();
        }
        this.internalRandom.setSeed(what);
    }
    
    public float noise(final float x) {
        return this.noise(x, 0.0f, 0.0f);
    }
    
    public float noise(final float x, final float y) {
        return this.noise(x, y, 0.0f);
    }
    
    public float noise(float x, float y, float z) {
        if (this.perlin == null) {
            if (this.perlinRandom == null) {
                this.perlinRandom = new Random();
            }
            this.perlin = new float[4096];
            for (int i = 0; i < 4096; ++i) {
                this.perlin[i] = this.perlinRandom.nextFloat();
            }
            this.perlin_cosTable = PGraphics.cosLUT;
            final int n4 = 720;
            this.perlin_PI = n4;
            this.perlin_TWOPI = n4;
            this.perlin_PI >>= 1;
        }
        if (x < 0.0f) {
            x = -x;
        }
        if (y < 0.0f) {
            y = -y;
        }
        if (z < 0.0f) {
            z = -z;
        }
        int xi = (int)x;
        int yi = (int)y;
        int zi = (int)z;
        float xf = x - xi;
        float yf = y - yi;
        float zf = z - zi;
        float r = 0.0f;
        float ampl = 0.5f;
        for (int j = 0; j < this.perlin_octaves; ++j) {
            int of = xi + (yi << 4) + (zi << 8);
            final float rxf = this.noise_fsc(xf);
            final float ryf = this.noise_fsc(yf);
            float n1 = this.perlin[of & 0xFFF];
            n1 += rxf * (this.perlin[of + 1 & 0xFFF] - n1);
            float n2 = this.perlin[of + 16 & 0xFFF];
            n2 += rxf * (this.perlin[of + 16 + 1 & 0xFFF] - n2);
            n1 += ryf * (n2 - n1);
            of += 256;
            n2 = this.perlin[of & 0xFFF];
            n2 += rxf * (this.perlin[of + 1 & 0xFFF] - n2);
            float n3 = this.perlin[of + 16 & 0xFFF];
            n3 += rxf * (this.perlin[of + 16 + 1 & 0xFFF] - n3);
            n2 += ryf * (n3 - n2);
            n1 += this.noise_fsc(zf) * (n2 - n1);
            r += n1 * ampl;
            ampl *= this.perlin_amp_falloff;
            xi <<= 1;
            xf *= 2.0f;
            yi <<= 1;
            yf *= 2.0f;
            zi <<= 1;
            zf *= 2.0f;
            if (xf >= 1.0f) {
                ++xi;
                --xf;
            }
            if (yf >= 1.0f) {
                ++yi;
                --yf;
            }
            if (zf >= 1.0f) {
                ++zi;
                --zf;
            }
        }
        return r;
    }
    
    private float noise_fsc(final float i) {
        return 0.5f * (1.0f - this.perlin_cosTable[(int)(i * this.perlin_PI) % this.perlin_TWOPI]);
    }
    
    public void noiseDetail(final int lod) {
        if (lod > 0) {
            this.perlin_octaves = lod;
        }
    }
    
    public void noiseDetail(final int lod, final float falloff) {
        if (lod > 0) {
            this.perlin_octaves = lod;
        }
        if (falloff > 0.0f) {
            this.perlin_amp_falloff = falloff;
        }
    }
    
    public void noiseSeed(final long what) {
        if (this.perlinRandom == null) {
            this.perlinRandom = new Random();
        }
        this.perlinRandom.setSeed(what);
        this.perlin = null;
    }
    
    public PImage loadImage(final String filename) {
        return this.loadImage(filename, null);
    }
    
    public PImage loadImage(final String filename, String extension) {
        if (extension == null) {
            final String lower = filename.toLowerCase();
            final int dot = filename.lastIndexOf(46);
            if (dot == -1) {
                extension = "unknown";
            }
            extension = lower.substring(dot + 1);
            final int question = extension.indexOf(63);
            if (question != -1) {
                extension = extension.substring(0, question);
            }
        }
        extension = extension.toLowerCase();
        if (extension.equals("tga")) {
            try {
                return this.loadImageTGA(filename);
            }
            catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        if (extension.equals("tif") || extension.equals("tiff")) {
            final byte[] bytes = this.loadBytes(filename);
            return (bytes == null) ? null : PImage.loadTIFF(bytes);
        }
        try {
            if (extension.equals("jpg") || extension.equals("jpeg") || extension.equals("gif") || extension.equals("png") || extension.equals("unknown")) {
                final byte[] bytes = this.loadBytes(filename);
                if (bytes == null) {
                    return null;
                }
                final Image awtImage = Toolkit.getDefaultToolkit().createImage(bytes);
                final PImage image = this.loadImageMT(awtImage);
                if (image.width == -1) {
                    System.err.println("The file " + filename + " contains bad image data, or may not be an image.");
                }
                if (extension.equals("gif") || extension.equals("png")) {
                    image.checkAlpha();
                }
                return image;
            }
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
        if (this.loadImageFormats == null) {
            this.loadImageFormats = ImageIO.getReaderFormatNames();
        }
        if (this.loadImageFormats != null) {
            for (int i = 0; i < this.loadImageFormats.length; ++i) {
                if (extension.equals(this.loadImageFormats[i])) {
                    return this.loadImageIO(filename);
                }
            }
        }
        System.err.println("Could not find a method to load " + filename);
        return null;
    }
    
    public PImage requestImage(final String filename) {
        return this.requestImage(filename, null);
    }
    
    public PImage requestImage(final String filename, final String extension) {
        final PImage vessel = this.createImage(0, 0, 2);
        final AsyncImageLoader ail = new AsyncImageLoader(filename, extension, vessel);
        ail.start();
        return vessel;
    }
    
    protected PImage loadImageMT(final Image awtImage) {
        final MediaTracker tracker = new MediaTracker(this);
        tracker.addImage(awtImage, 0);
        try {
            tracker.waitForAll();
        }
        catch (InterruptedException ex) {}
        final PImage image = new PImage(awtImage);
        image.parent = this;
        return image;
    }
    
    protected PImage loadImageIO(final String filename) {
        final InputStream stream = this.createInput(filename);
        if (stream == null) {
            System.err.println("The image " + filename + " could not be found.");
            return null;
        }
        try {
            final BufferedImage bi = ImageIO.read(stream);
            final PImage outgoing = new PImage(bi.getWidth(), bi.getHeight());
            outgoing.parent = this;
            bi.getRGB(0, 0, outgoing.width, outgoing.height, outgoing.pixels, 0, outgoing.width);
            outgoing.checkAlpha();
            return outgoing;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    protected PImage loadImageTGA(final String filename) throws IOException {
        final InputStream is = this.createInput(filename);
        if (is == null) {
            return null;
        }
        final byte[] header = new byte[18];
        int offset = 0;
        do {
            final int count = is.read(header, offset, header.length - offset);
            if (count == -1) {
                return null;
            }
            offset += count;
        } while (offset < 18);
        int format = 0;
        if ((header[2] == 3 || header[2] == 11) && header[16] == 8 && (header[17] == 8 || header[17] == 40)) {
            format = 4;
        }
        else if ((header[2] == 2 || header[2] == 10) && header[16] == 24 && (header[17] == 32 || header[17] == 0)) {
            format = 1;
        }
        else if ((header[2] == 2 || header[2] == 10) && header[16] == 32 && (header[17] == 8 || header[17] == 40)) {
            format = 2;
        }
        if (format == 0) {
            System.err.println("Unknown .tga file format for " + filename);
            return null;
        }
        final int w = ((header[13] & 0xFF) << 8) + (header[12] & 0xFF);
        final int h = ((header[15] & 0xFF) << 8) + (header[14] & 0xFF);
        final PImage outgoing = this.createImage(w, h, format);
        final boolean reversed = (header[17] & 0x20) != 0x0;
        if (header[2] == 2 || header[2] == 3) {
            if (reversed) {
                int index = (h - 1) * w;
                switch (format) {
                    case 4: {
                        for (int y = h - 1; y >= 0; --y) {
                            for (int x = 0; x < w; ++x) {
                                outgoing.pixels[index + x] = is.read();
                            }
                            index -= w;
                        }
                        break;
                    }
                    case 1: {
                        for (int y = h - 1; y >= 0; --y) {
                            for (int x = 0; x < w; ++x) {
                                outgoing.pixels[index + x] = (is.read() | is.read() << 8 | is.read() << 16 | 0xFF000000);
                            }
                            index -= w;
                        }
                        break;
                    }
                    case 2: {
                        for (int y = h - 1; y >= 0; --y) {
                            for (int x = 0; x < w; ++x) {
                                outgoing.pixels[index + x] = (is.read() | is.read() << 8 | is.read() << 16 | is.read() << 24);
                            }
                            index -= w;
                        }
                        break;
                    }
                }
            }
            else {
                final int count2 = w * h;
                switch (format) {
                    case 4: {
                        for (int i = 0; i < count2; ++i) {
                            outgoing.pixels[i] = is.read();
                        }
                        break;
                    }
                    case 1: {
                        for (int i = 0; i < count2; ++i) {
                            outgoing.pixels[i] = (is.read() | is.read() << 8 | is.read() << 16 | 0xFF000000);
                        }
                        break;
                    }
                    case 2: {
                        for (int i = 0; i < count2; ++i) {
                            outgoing.pixels[i] = (is.read() | is.read() << 8 | is.read() << 16 | is.read() << 24);
                        }
                        break;
                    }
                }
            }
        }
        else {
            int index = 0;
            final int[] px = outgoing.pixels;
            while (index < px.length) {
                int num = is.read();
                final boolean isRLE = (num & 0x80) != 0x0;
                if (isRLE) {
                    num -= 127;
                    int pixel = 0;
                    switch (format) {
                        case 4: {
                            pixel = is.read();
                            break;
                        }
                        case 1: {
                            pixel = (0xFF000000 | is.read() | is.read() << 8 | is.read() << 16);
                            break;
                        }
                        case 2: {
                            pixel = (is.read() | is.read() << 8 | is.read() << 16 | is.read() << 24);
                            break;
                        }
                    }
                    for (int j = 0; j < num; ++j) {
                        px[index++] = pixel;
                        if (index == px.length) {
                            break;
                        }
                    }
                }
                else {
                    ++num;
                    switch (format) {
                        default: {
                            continue;
                        }
                        case 4: {
                            for (int k = 0; k < num; ++k) {
                                px[index++] = is.read();
                            }
                            continue;
                        }
                        case 1: {
                            for (int k = 0; k < num; ++k) {
                                px[index++] = (0xFF000000 | is.read() | is.read() << 8 | is.read() << 16);
                            }
                            continue;
                        }
                        case 2: {
                            for (int k = 0; k < num; ++k) {
                                px[index++] = (is.read() | is.read() << 8 | is.read() << 16 | is.read() << 24);
                            }
                            continue;
                        }
                    }
                }
            }
            if (!reversed) {
                final int[] temp = new int[w];
                for (int y2 = 0; y2 < h / 2; ++y2) {
                    final int z = h - 1 - y2;
                    System.arraycopy(px, y2 * w, temp, 0, w);
                    System.arraycopy(px, z * w, px, y2 * w, w);
                    System.arraycopy(temp, 0, px, z * w, w);
                }
            }
        }
        return outgoing;
    }
    
    public PShape loadShape(final String filename) {
        if (filename.toLowerCase().endsWith(".svg")) {
            return new PShapeSVG(this, filename);
        }
        if (filename.toLowerCase().endsWith(".svgz")) {
            try {
                final InputStream input = new GZIPInputStream(this.createInput(filename));
                final XMLElement xml = new XMLElement(createReader(input));
                return new PShapeSVG(xml);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public PFont loadFont(final String filename) {
        try {
            final InputStream input = this.createInput(filename);
            return new PFont(input);
        }
        catch (Exception e) {
            this.die("Could not load font " + filename + ". " + "Make sure that the font has been copied " + "to the data folder of your sketch.", e);
            return null;
        }
    }
    
    protected PFont createDefaultFont(final float size) {
        return this.createFont("SansSerif", size, true, null);
    }
    
    public PFont createFont(final String name, final float size) {
        return this.createFont(name, size, true, null);
    }
    
    public PFont createFont(final String name, final float size, final boolean smooth) {
        return this.createFont(name, size, smooth, null);
    }
    
    public PFont createFont(final String name, final float size, final boolean smooth, final char[] charset) {
        final String lowerName = name.toLowerCase();
        Font baseFont = null;
        try {
            InputStream stream = null;
            if (lowerName.endsWith(".otf") || lowerName.endsWith(".ttf")) {
                stream = this.createInput(name);
                if (stream == null) {
                    System.err.println("The font \"" + name + "\" " + "is missing or inaccessible, make sure " + "the URL is valid or that the file has been " + "added to your sketch and is readable.");
                    return null;
                }
                baseFont = Font.createFont(0, this.createInput(name));
            }
            else {
                baseFont = PFont.findFont(name);
            }
            return new PFont(baseFont.deriveFont(size), smooth, charset, stream != null);
        }
        catch (Exception e) {
            System.err.println("Problem createFont(" + name + ")");
            e.printStackTrace();
            return null;
        }
    }
    
    protected void checkParentFrame() {
        if (this.parentFrame == null) {
            for (Component comp = this.getParent(); comp != null; comp = comp.getParent()) {
                if (comp instanceof Frame) {
                    this.parentFrame = (Frame)comp;
                    break;
                }
            }
            if (this.parentFrame == null) {
                this.parentFrame = new Frame();
            }
        }
    }
    
    public String selectInput() {
        return this.selectInput("Select a file...");
    }
    
    public String selectInput(final String prompt) {
        return this.selectFileImpl(prompt, 0);
    }
    
    public String selectOutput() {
        return this.selectOutput("Save as...");
    }
    
    public String selectOutput(final String prompt) {
        return this.selectFileImpl(prompt, 1);
    }
    
    protected String selectFileImpl(final String prompt, final int mode) {
        this.checkParentFrame();
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    final FileDialog fileDialog = new FileDialog(PApplet.this.parentFrame, prompt, mode);
                    fileDialog.setVisible(true);
                    final String directory = fileDialog.getDirectory();
                    final String filename = fileDialog.getFile();
                    PApplet.this.selectedFile = ((filename == null) ? null : new File(directory, filename));
                }
            });
            return (this.selectedFile == null) ? null : this.selectedFile.getAbsolutePath();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public String selectFolder() {
        return this.selectFolder("Select a folder...");
    }
    
    public String selectFolder(final String prompt) {
        this.checkParentFrame();
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    if (PApplet.platform == 2) {
                        final FileDialog fileDialog = new FileDialog(PApplet.this.parentFrame, prompt, 0);
                        System.setProperty("apple.awt.fileDialogForDirectories", "true");
                        fileDialog.setVisible(true);
                        System.setProperty("apple.awt.fileDialogForDirectories", "false");
                        final String filename = fileDialog.getFile();
                        PApplet.this.selectedFile = ((filename == null) ? null : new File(fileDialog.getDirectory(), fileDialog.getFile()));
                    }
                    else {
                        final JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setDialogTitle(prompt);
                        fileChooser.setFileSelectionMode(1);
                        final int returned = fileChooser.showOpenDialog(PApplet.this.parentFrame);
                        System.out.println(returned);
                        if (returned == 1) {
                            PApplet.this.selectedFile = null;
                        }
                        else {
                            PApplet.this.selectedFile = fileChooser.getSelectedFile();
                        }
                    }
                }
            });
            return (this.selectedFile == null) ? null : this.selectedFile.getAbsolutePath();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public BufferedReader createReader(final String filename) {
        try {
            final InputStream is = this.createInput(filename);
            if (is == null) {
                System.err.println(String.valueOf(filename) + " does not exist or could not be read");
                return null;
            }
            return createReader(is);
        }
        catch (Exception e) {
            if (filename == null) {
                System.err.println("Filename passed to reader() was null");
            }
            else {
                System.err.println("Couldn't create a reader for " + filename);
            }
            return null;
        }
    }
    
    public static BufferedReader createReader(final File file) {
        try {
            InputStream is = new FileInputStream(file);
            if (file.getName().toLowerCase().endsWith(".gz")) {
                is = new GZIPInputStream(is);
            }
            return createReader(is);
        }
        catch (Exception e) {
            if (file == null) {
                throw new RuntimeException("File passed to createReader() was null");
            }
            e.printStackTrace();
            throw new RuntimeException("Couldn't create a reader for " + file.getAbsolutePath());
        }
    }
    
    public static BufferedReader createReader(final InputStream input) {
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(input, "UTF-8");
        }
        catch (UnsupportedEncodingException ex) {}
        return new BufferedReader(isr);
    }
    
    public PrintWriter createWriter(final String filename) {
        return createWriter(this.saveFile(filename));
    }
    
    public static PrintWriter createWriter(final File file) {
        try {
            createPath(file);
            OutputStream output = new FileOutputStream(file);
            if (file.getName().toLowerCase().endsWith(".gz")) {
                output = new GZIPOutputStream(output);
            }
            return createWriter(output);
        }
        catch (Exception e) {
            if (file == null) {
                throw new RuntimeException("File passed to createWriter() was null");
            }
            e.printStackTrace();
            throw new RuntimeException("Couldn't create a writer for " + file.getAbsolutePath());
        }
    }
    
    public static PrintWriter createWriter(final OutputStream output) {
        try {
            final OutputStreamWriter osw = new OutputStreamWriter(output, "UTF-8");
            return new PrintWriter(osw);
        }
        catch (UnsupportedEncodingException ex) {
            return null;
        }
    }
    
    public InputStream openStream(final String filename) {
        return this.createInput(filename);
    }
    
    public InputStream createInput(final String filename) {
        final InputStream input = this.createInputRaw(filename);
        if (input != null && filename.toLowerCase().endsWith(".gz")) {
            try {
                return new GZIPInputStream(input);
            }
            catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return input;
    }
    
    public InputStream createInputRaw(final String filename) {
        InputStream stream = null;
        if (filename == null) {
            return null;
        }
        if (filename.length() == 0) {
            return null;
        }
        if (filename.indexOf(":") != -1) {
            try {
                final URL url = new URL(filename);
                stream = url.openStream();
                return stream;
            }
            catch (MalformedURLException ex) {}
            catch (FileNotFoundException ex2) {}
            catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        try {
            File file = new File(this.dataPath(filename));
            if (!file.exists()) {
                file = new File(this.sketchPath, filename);
            }
            if (file.isDirectory()) {
                return null;
            }
            if (file.exists()) {
                try {
                    final String filePath = file.getCanonicalPath();
                    final String filenameActual = new File(filePath).getName();
                    final String filenameShort = new File(filename).getName();
                    if (!filenameActual.equals(filenameShort)) {
                        throw new RuntimeException("This file is named " + filenameActual + " not " + filename + ". Rename the file " + "or change your code.");
                    }
                }
                catch (IOException ex3) {}
            }
            stream = new FileInputStream(file);
            if (stream != null) {
                return stream;
            }
        }
        catch (IOException ex4) {}
        catch (SecurityException ex5) {}
        final ClassLoader cl = this.getClass().getClassLoader();
        stream = cl.getResourceAsStream("data/" + filename);
        if (stream != null) {
            final String cn = stream.getClass().getName();
            if (!cn.equals("sun.plugin.cache.EmptyInputStream")) {
                return stream;
            }
        }
        stream = cl.getResourceAsStream(filename);
        if (stream != null) {
            final String cn = stream.getClass().getName();
            if (!cn.equals("sun.plugin.cache.EmptyInputStream")) {
                return stream;
            }
        }
        try {
            final URL base = this.getDocumentBase();
            if (base != null) {
                final URL url2 = new URL(base, filename);
                final URLConnection conn = url2.openConnection();
                return conn.getInputStream();
            }
        }
        catch (Exception ex6) {}
        try {
            final URL base = this.getDocumentBase();
            if (base != null) {
                final URL url2 = new URL(base, "data/" + filename);
                final URLConnection conn = url2.openConnection();
                return conn.getInputStream();
            }
        }
        catch (Exception ex7) {}
        try {
            try {
                try {
                    stream = new FileInputStream(this.dataPath(filename));
                    if (stream != null) {
                        return stream;
                    }
                }
                catch (IOException ex8) {}
                try {
                    stream = new FileInputStream(this.sketchPath(filename));
                    if (stream != null) {
                        return stream;
                    }
                }
                catch (Exception ex9) {}
                try {
                    stream = new FileInputStream(filename);
                    if (stream != null) {
                        return stream;
                    }
                    return null;
                }
                catch (IOException ex10) {}
            }
            catch (SecurityException ex11) {}
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
        return null;
    }
    
    public static InputStream createInput(final File file) {
        if (file == null) {
            throw new IllegalArgumentException("File passed to createInput() was null");
        }
        try {
            final InputStream input = new FileInputStream(file);
            if (file.getName().toLowerCase().endsWith(".gz")) {
                return new GZIPInputStream(input);
            }
            return input;
        }
        catch (IOException e) {
            System.err.println("Could not createInput() for " + file);
            e.printStackTrace();
            return null;
        }
    }
    
    public byte[] loadBytes(final String filename) {
        final InputStream is = this.createInput(filename);
        if (is != null) {
            return loadBytes(is);
        }
        System.err.println("The file \"" + filename + "\" " + "is missing or inaccessible, make sure " + "the URL is valid or that the file has been " + "added to your sketch and is readable.");
        return null;
    }
    
    public static byte[] loadBytes(final InputStream input) {
        try {
            final BufferedInputStream bis = new BufferedInputStream(input);
            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            for (int c = bis.read(); c != -1; c = bis.read()) {
                out.write(c);
            }
            return out.toByteArray();
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static byte[] loadBytes(final File file) {
        final InputStream is = createInput(file);
        return loadBytes(is);
    }
    
    public static String[] loadStrings(final File file) {
        final InputStream is = createInput(file);
        if (is != null) {
            return loadStrings(is);
        }
        return null;
    }
    
    public String[] loadStrings(final String filename) {
        final InputStream is = this.createInput(filename);
        if (is != null) {
            return loadStrings(is);
        }
        System.err.println("The file \"" + filename + "\" " + "is missing or inaccessible, make sure " + "the URL is valid or that the file has been " + "added to your sketch and is readable.");
        return null;
    }
    
    public static String[] loadStrings(final InputStream input) {
        try {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
            String[] lines = new String[100];
            int lineCount = 0;
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (lineCount == lines.length) {
                    final String[] temp = new String[lineCount << 1];
                    System.arraycopy(lines, 0, temp, 0, lineCount);
                    lines = temp;
                }
                lines[lineCount++] = line;
            }
            reader.close();
            if (lineCount == lines.length) {
                return lines;
            }
            final String[] output = new String[lineCount];
            System.arraycopy(lines, 0, output, 0, lineCount);
            return output;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public OutputStream createOutput(final String filename) {
        return createOutput(this.saveFile(filename));
    }
    
    public static OutputStream createOutput(final File file) {
        try {
            createPath(file);
            final FileOutputStream fos = new FileOutputStream(file);
            if (file.getName().toLowerCase().endsWith(".gz")) {
                return new GZIPOutputStream(fos);
            }
            return fos;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean saveStream(final String targetFilename, final String sourceLocation) {
        return this.saveStream(this.saveFile(targetFilename), sourceLocation);
    }
    
    public boolean saveStream(final File targetFile, final String sourceLocation) {
        return saveStream(targetFile, this.createInputRaw(sourceLocation));
    }
    
    public static boolean saveStream(final File targetFile, final InputStream sourceStream) {
        File tempFile = null;
        try {
            final File parentDir = targetFile.getParentFile();
            tempFile = File.createTempFile(targetFile.getName(), null, parentDir);
            final BufferedInputStream bis = new BufferedInputStream(sourceStream, 16384);
            final FileOutputStream fos = new FileOutputStream(tempFile);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            final byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            bos.flush();
            bos.close();
            bos = null;
            if (!tempFile.renameTo(targetFile)) {
                System.err.println("Could not rename temporary file " + tempFile.getAbsolutePath());
                return false;
            }
            return true;
        }
        catch (IOException e) {
            if (tempFile != null) {
                tempFile.delete();
            }
            e.printStackTrace();
            return false;
        }
    }
    
    public void saveBytes(final String filename, final byte[] buffer) {
        saveBytes(this.saveFile(filename), buffer);
    }
    
    public static void saveBytes(final File file, final byte[] buffer) {
        File tempFile = null;
        try {
            final File parentDir = file.getParentFile();
            tempFile = File.createTempFile(file.getName(), null, parentDir);
            OutputStream output = createOutput(tempFile);
            saveBytes(output, buffer);
            output.close();
            output = null;
            if (!tempFile.renameTo(file)) {
                System.err.println("Could not rename temporary file " + tempFile.getAbsolutePath());
            }
        }
        catch (IOException e) {
            System.err.println("error saving bytes to " + file);
            if (tempFile != null) {
                tempFile.delete();
            }
            e.printStackTrace();
        }
    }
    
    public static void saveBytes(final OutputStream output, final byte[] buffer) {
        try {
            output.write(buffer);
            output.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void saveStrings(final String filename, final String[] strings) {
        saveStrings(this.saveFile(filename), strings);
    }
    
    public static void saveStrings(final File file, final String[] strings) {
        saveStrings(createOutput(file), strings);
    }
    
    public static void saveStrings(final OutputStream output, final String[] strings) {
        final PrintWriter writer = createWriter(output);
        for (int i = 0; i < strings.length; ++i) {
            writer.println(strings[i]);
        }
        writer.flush();
        writer.close();
    }
    
    public String sketchPath(final String where) {
        if (this.sketchPath == null) {
            return where;
        }
        try {
            if (new File(where).isAbsolute()) {
                return where;
            }
        }
        catch (Exception ex) {}
        return String.valueOf(this.sketchPath) + File.separator + where;
    }
    
    public File sketchFile(final String where) {
        return new File(this.sketchPath(where));
    }
    
    public String savePath(final String where) {
        if (where == null) {
            return null;
        }
        final String filename = this.sketchPath(where);
        createPath(filename);
        return filename;
    }
    
    public File saveFile(final String where) {
        return new File(this.savePath(where));
    }
    
    public String dataPath(final String where) {
        if (new File(where).isAbsolute()) {
            return where;
        }
        return String.valueOf(this.sketchPath) + File.separator + "data" + File.separator + where;
    }
    
    public File dataFile(final String where) {
        return new File(this.dataPath(where));
    }
    
    public static void createPath(final String path) {
        createPath(new File(path));
    }
    
    public static void createPath(final File file) {
        try {
            final String parent = file.getParent();
            if (parent != null) {
                final File unit = new File(parent);
                if (!unit.exists()) {
                    unit.mkdirs();
                }
            }
        }
        catch (SecurityException se) {
            System.err.println("You don't have permissions to create " + file.getAbsolutePath());
        }
    }
    
    public static byte[] sort(final byte[] what) {
        return sort(what, what.length);
    }
    
    public static byte[] sort(final byte[] what, final int count) {
        final byte[] outgoing = new byte[what.length];
        System.arraycopy(what, 0, outgoing, 0, what.length);
        Arrays.sort(outgoing, 0, count);
        return outgoing;
    }
    
    public static char[] sort(final char[] what) {
        return sort(what, what.length);
    }
    
    public static char[] sort(final char[] what, final int count) {
        final char[] outgoing = new char[what.length];
        System.arraycopy(what, 0, outgoing, 0, what.length);
        Arrays.sort(outgoing, 0, count);
        return outgoing;
    }
    
    public static int[] sort(final int[] what) {
        return sort(what, what.length);
    }
    
    public static int[] sort(final int[] what, final int count) {
        final int[] outgoing = new int[what.length];
        System.arraycopy(what, 0, outgoing, 0, what.length);
        Arrays.sort(outgoing, 0, count);
        return outgoing;
    }
    
    public static float[] sort(final float[] what) {
        return sort(what, what.length);
    }
    
    public static float[] sort(final float[] what, final int count) {
        final float[] outgoing = new float[what.length];
        System.arraycopy(what, 0, outgoing, 0, what.length);
        Arrays.sort(outgoing, 0, count);
        return outgoing;
    }
    
    public static String[] sort(final String[] what) {
        return sort(what, what.length);
    }
    
    public static String[] sort(final String[] what, final int count) {
        final String[] outgoing = new String[what.length];
        System.arraycopy(what, 0, outgoing, 0, what.length);
        Arrays.sort(outgoing, 0, count);
        return outgoing;
    }
    
    public static void arrayCopy(final Object src, final int srcPosition, final Object dst, final int dstPosition, final int length) {
        System.arraycopy(src, srcPosition, dst, dstPosition, length);
    }
    
    public static void arrayCopy(final Object src, final Object dst, final int length) {
        System.arraycopy(src, 0, dst, 0, length);
    }
    
    public static void arrayCopy(final Object src, final Object dst) {
        System.arraycopy(src, 0, dst, 0, Array.getLength(src));
    }
    
    public static void arraycopy(final Object src, final int srcPosition, final Object dst, final int dstPosition, final int length) {
        System.arraycopy(src, srcPosition, dst, dstPosition, length);
    }
    
    public static void arraycopy(final Object src, final Object dst, final int length) {
        System.arraycopy(src, 0, dst, 0, length);
    }
    
    public static void arraycopy(final Object src, final Object dst) {
        System.arraycopy(src, 0, dst, 0, Array.getLength(src));
    }
    
    public static boolean[] expand(final boolean[] list) {
        return expand(list, list.length << 1);
    }
    
    public static boolean[] expand(final boolean[] list, final int newSize) {
        final boolean[] temp = new boolean[newSize];
        System.arraycopy(list, 0, temp, 0, Math.min(newSize, list.length));
        return temp;
    }
    
    public static byte[] expand(final byte[] list) {
        return expand(list, list.length << 1);
    }
    
    public static byte[] expand(final byte[] list, final int newSize) {
        final byte[] temp = new byte[newSize];
        System.arraycopy(list, 0, temp, 0, Math.min(newSize, list.length));
        return temp;
    }
    
    public static char[] expand(final char[] list) {
        return expand(list, list.length << 1);
    }
    
    public static char[] expand(final char[] list, final int newSize) {
        final char[] temp = new char[newSize];
        System.arraycopy(list, 0, temp, 0, Math.min(newSize, list.length));
        return temp;
    }
    
    public static int[] expand(final int[] list) {
        return expand(list, list.length << 1);
    }
    
    public static int[] expand(final int[] list, final int newSize) {
        final int[] temp = new int[newSize];
        System.arraycopy(list, 0, temp, 0, Math.min(newSize, list.length));
        return temp;
    }
    
    public static float[] expand(final float[] list) {
        return expand(list, list.length << 1);
    }
    
    public static float[] expand(final float[] list, final int newSize) {
        final float[] temp = new float[newSize];
        System.arraycopy(list, 0, temp, 0, Math.min(newSize, list.length));
        return temp;
    }
    
    public static String[] expand(final String[] list) {
        return expand(list, list.length << 1);
    }
    
    public static String[] expand(final String[] list, final int newSize) {
        final String[] temp = new String[newSize];
        System.arraycopy(list, 0, temp, 0, Math.min(newSize, list.length));
        return temp;
    }
    
    public static Object expand(final Object array) {
        return expand(array, Array.getLength(array) << 1);
    }
    
    public static Object expand(final Object list, final int newSize) {
        final Class<?> type = list.getClass().getComponentType();
        final Object temp = Array.newInstance(type, newSize);
        System.arraycopy(list, 0, temp, 0, Math.min(Array.getLength(list), newSize));
        return temp;
    }
    
    public static byte[] append(byte[] b, final byte value) {
        b = expand(b, b.length + 1);
        b[b.length - 1] = value;
        return b;
    }
    
    public static char[] append(char[] b, final char value) {
        b = expand(b, b.length + 1);
        b[b.length - 1] = value;
        return b;
    }
    
    public static int[] append(int[] b, final int value) {
        b = expand(b, b.length + 1);
        b[b.length - 1] = value;
        return b;
    }
    
    public static float[] append(float[] b, final float value) {
        b = expand(b, b.length + 1);
        b[b.length - 1] = value;
        return b;
    }
    
    public static String[] append(String[] b, final String value) {
        b = expand(b, b.length + 1);
        b[b.length - 1] = value;
        return b;
    }
    
    public static Object append(Object b, final Object value) {
        final int length = Array.getLength(b);
        b = expand(b, length + 1);
        Array.set(b, length, value);
        return b;
    }
    
    public static boolean[] shorten(final boolean[] list) {
        return subset(list, 0, list.length - 1);
    }
    
    public static byte[] shorten(final byte[] list) {
        return subset(list, 0, list.length - 1);
    }
    
    public static char[] shorten(final char[] list) {
        return subset(list, 0, list.length - 1);
    }
    
    public static int[] shorten(final int[] list) {
        return subset(list, 0, list.length - 1);
    }
    
    public static float[] shorten(final float[] list) {
        return subset(list, 0, list.length - 1);
    }
    
    public static String[] shorten(final String[] list) {
        return subset(list, 0, list.length - 1);
    }
    
    public static Object shorten(final Object list) {
        final int length = Array.getLength(list);
        return subset(list, 0, length - 1);
    }
    
    public static final boolean[] splice(final boolean[] list, final boolean v, final int index) {
        final boolean[] outgoing = new boolean[list.length + 1];
        System.arraycopy(list, 0, outgoing, 0, index);
        outgoing[index] = v;
        System.arraycopy(list, index, outgoing, index + 1, list.length - index);
        return outgoing;
    }
    
    public static final boolean[] splice(final boolean[] list, final boolean[] v, final int index) {
        final boolean[] outgoing = new boolean[list.length + v.length];
        System.arraycopy(list, 0, outgoing, 0, index);
        System.arraycopy(v, 0, outgoing, index, v.length);
        System.arraycopy(list, index, outgoing, index + v.length, list.length - index);
        return outgoing;
    }
    
    public static final byte[] splice(final byte[] list, final byte v, final int index) {
        final byte[] outgoing = new byte[list.length + 1];
        System.arraycopy(list, 0, outgoing, 0, index);
        outgoing[index] = v;
        System.arraycopy(list, index, outgoing, index + 1, list.length - index);
        return outgoing;
    }
    
    public static final byte[] splice(final byte[] list, final byte[] v, final int index) {
        final byte[] outgoing = new byte[list.length + v.length];
        System.arraycopy(list, 0, outgoing, 0, index);
        System.arraycopy(v, 0, outgoing, index, v.length);
        System.arraycopy(list, index, outgoing, index + v.length, list.length - index);
        return outgoing;
    }
    
    public static final char[] splice(final char[] list, final char v, final int index) {
        final char[] outgoing = new char[list.length + 1];
        System.arraycopy(list, 0, outgoing, 0, index);
        outgoing[index] = v;
        System.arraycopy(list, index, outgoing, index + 1, list.length - index);
        return outgoing;
    }
    
    public static final char[] splice(final char[] list, final char[] v, final int index) {
        final char[] outgoing = new char[list.length + v.length];
        System.arraycopy(list, 0, outgoing, 0, index);
        System.arraycopy(v, 0, outgoing, index, v.length);
        System.arraycopy(list, index, outgoing, index + v.length, list.length - index);
        return outgoing;
    }
    
    public static final int[] splice(final int[] list, final int v, final int index) {
        final int[] outgoing = new int[list.length + 1];
        System.arraycopy(list, 0, outgoing, 0, index);
        outgoing[index] = v;
        System.arraycopy(list, index, outgoing, index + 1, list.length - index);
        return outgoing;
    }
    
    public static final int[] splice(final int[] list, final int[] v, final int index) {
        final int[] outgoing = new int[list.length + v.length];
        System.arraycopy(list, 0, outgoing, 0, index);
        System.arraycopy(v, 0, outgoing, index, v.length);
        System.arraycopy(list, index, outgoing, index + v.length, list.length - index);
        return outgoing;
    }
    
    public static final float[] splice(final float[] list, final float v, final int index) {
        final float[] outgoing = new float[list.length + 1];
        System.arraycopy(list, 0, outgoing, 0, index);
        outgoing[index] = v;
        System.arraycopy(list, index, outgoing, index + 1, list.length - index);
        return outgoing;
    }
    
    public static final float[] splice(final float[] list, final float[] v, final int index) {
        final float[] outgoing = new float[list.length + v.length];
        System.arraycopy(list, 0, outgoing, 0, index);
        System.arraycopy(v, 0, outgoing, index, v.length);
        System.arraycopy(list, index, outgoing, index + v.length, list.length - index);
        return outgoing;
    }
    
    public static final String[] splice(final String[] list, final String v, final int index) {
        final String[] outgoing = new String[list.length + 1];
        System.arraycopy(list, 0, outgoing, 0, index);
        outgoing[index] = v;
        System.arraycopy(list, index, outgoing, index + 1, list.length - index);
        return outgoing;
    }
    
    public static final String[] splice(final String[] list, final String[] v, final int index) {
        final String[] outgoing = new String[list.length + v.length];
        System.arraycopy(list, 0, outgoing, 0, index);
        System.arraycopy(v, 0, outgoing, index, v.length);
        System.arraycopy(list, index, outgoing, index + v.length, list.length - index);
        return outgoing;
    }
    
    public static final Object splice(final Object list, final Object v, final int index) {
        Object[] outgoing = null;
        final int length = Array.getLength(list);
        if (v.getClass().getName().charAt(0) == '[') {
            final int vlength = Array.getLength(v);
            outgoing = new Object[length + vlength];
            System.arraycopy(list, 0, outgoing, 0, index);
            System.arraycopy(v, 0, outgoing, index, vlength);
            System.arraycopy(list, index, outgoing, index + vlength, length - index);
        }
        else {
            outgoing = new Object[length + 1];
            System.arraycopy(list, 0, outgoing, 0, index);
            Array.set(outgoing, index, v);
            System.arraycopy(list, index, outgoing, index + 1, length - index);
        }
        return outgoing;
    }
    
    public static boolean[] subset(final boolean[] list, final int start) {
        return subset(list, start, list.length - start);
    }
    
    public static boolean[] subset(final boolean[] list, final int start, final int count) {
        final boolean[] output = new boolean[count];
        System.arraycopy(list, start, output, 0, count);
        return output;
    }
    
    public static byte[] subset(final byte[] list, final int start) {
        return subset(list, start, list.length - start);
    }
    
    public static byte[] subset(final byte[] list, final int start, final int count) {
        final byte[] output = new byte[count];
        System.arraycopy(list, start, output, 0, count);
        return output;
    }
    
    public static char[] subset(final char[] list, final int start) {
        return subset(list, start, list.length - start);
    }
    
    public static char[] subset(final char[] list, final int start, final int count) {
        final char[] output = new char[count];
        System.arraycopy(list, start, output, 0, count);
        return output;
    }
    
    public static int[] subset(final int[] list, final int start) {
        return subset(list, start, list.length - start);
    }
    
    public static int[] subset(final int[] list, final int start, final int count) {
        final int[] output = new int[count];
        System.arraycopy(list, start, output, 0, count);
        return output;
    }
    
    public static float[] subset(final float[] list, final int start) {
        return subset(list, start, list.length - start);
    }
    
    public static float[] subset(final float[] list, final int start, final int count) {
        final float[] output = new float[count];
        System.arraycopy(list, start, output, 0, count);
        return output;
    }
    
    public static String[] subset(final String[] list, final int start) {
        return subset(list, start, list.length - start);
    }
    
    public static String[] subset(final String[] list, final int start, final int count) {
        final String[] output = new String[count];
        System.arraycopy(list, start, output, 0, count);
        return output;
    }
    
    public static Object subset(final Object list, final int start) {
        final int length = Array.getLength(list);
        return subset(list, start, length - start);
    }
    
    public static Object subset(final Object list, final int start, final int count) {
        final Class<?> type = list.getClass().getComponentType();
        final Object outgoing = Array.newInstance(type, count);
        System.arraycopy(list, start, outgoing, 0, count);
        return outgoing;
    }
    
    public static boolean[] concat(final boolean[] a, final boolean[] b) {
        final boolean[] c = new boolean[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }
    
    public static byte[] concat(final byte[] a, final byte[] b) {
        final byte[] c = new byte[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }
    
    public static char[] concat(final char[] a, final char[] b) {
        final char[] c = new char[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }
    
    public static int[] concat(final int[] a, final int[] b) {
        final int[] c = new int[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }
    
    public static float[] concat(final float[] a, final float[] b) {
        final float[] c = new float[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }
    
    public static String[] concat(final String[] a, final String[] b) {
        final String[] c = new String[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }
    
    public static Object concat(final Object a, final Object b) {
        final Class<?> type = a.getClass().getComponentType();
        final int alength = Array.getLength(a);
        final int blength = Array.getLength(b);
        final Object outgoing = Array.newInstance(type, alength + blength);
        System.arraycopy(a, 0, outgoing, 0, alength);
        System.arraycopy(b, 0, outgoing, alength, blength);
        return outgoing;
    }
    
    public static boolean[] reverse(final boolean[] list) {
        final boolean[] outgoing = new boolean[list.length];
        final int length1 = list.length - 1;
        for (int i = 0; i < list.length; ++i) {
            outgoing[i] = list[length1 - i];
        }
        return outgoing;
    }
    
    public static byte[] reverse(final byte[] list) {
        final byte[] outgoing = new byte[list.length];
        final int length1 = list.length - 1;
        for (int i = 0; i < list.length; ++i) {
            outgoing[i] = list[length1 - i];
        }
        return outgoing;
    }
    
    public static char[] reverse(final char[] list) {
        final char[] outgoing = new char[list.length];
        final int length1 = list.length - 1;
        for (int i = 0; i < list.length; ++i) {
            outgoing[i] = list[length1 - i];
        }
        return outgoing;
    }
    
    public static int[] reverse(final int[] list) {
        final int[] outgoing = new int[list.length];
        final int length1 = list.length - 1;
        for (int i = 0; i < list.length; ++i) {
            outgoing[i] = list[length1 - i];
        }
        return outgoing;
    }
    
    public static float[] reverse(final float[] list) {
        final float[] outgoing = new float[list.length];
        final int length1 = list.length - 1;
        for (int i = 0; i < list.length; ++i) {
            outgoing[i] = list[length1 - i];
        }
        return outgoing;
    }
    
    public static String[] reverse(final String[] list) {
        final String[] outgoing = new String[list.length];
        final int length1 = list.length - 1;
        for (int i = 0; i < list.length; ++i) {
            outgoing[i] = list[length1 - i];
        }
        return outgoing;
    }
    
    public static Object reverse(final Object list) {
        final Class<?> type = list.getClass().getComponentType();
        final int length = Array.getLength(list);
        final Object outgoing = Array.newInstance(type, length);
        for (int i = 0; i < length; ++i) {
            Array.set(outgoing, i, Array.get(list, length - 1 - i));
        }
        return outgoing;
    }
    
    public static String trim(final String str) {
        return str.replace(' ', ' ').trim();
    }
    
    public static String[] trim(final String[] array) {
        final String[] outgoing = new String[array.length];
        for (int i = 0; i < array.length; ++i) {
            outgoing[i] = array[i].replace(' ', ' ').trim();
        }
        return outgoing;
    }
    
    public static String join(final String[] str, final char separator) {
        return join(str, String.valueOf(separator));
    }
    
    public static String join(final String[] str, final String separator) {
        final StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < str.length; ++i) {
            if (i != 0) {
                buffer.append(separator);
            }
            buffer.append(str[i]);
        }
        return buffer.toString();
    }
    
    public static String[] splitTokens(final String what) {
        return splitTokens(what, " \t\n\r\f ");
    }
    
    public static String[] splitTokens(final String what, final String delim) {
        final StringTokenizer toker = new StringTokenizer(what, delim);
        final String[] pieces = new String[toker.countTokens()];
        int index = 0;
        while (toker.hasMoreTokens()) {
            pieces[index++] = toker.nextToken();
        }
        return pieces;
    }
    
    public static String[] split(final String what, final char delim) {
        if (what == null) {
            return null;
        }
        final char[] chars = what.toCharArray();
        int splitCount = 0;
        for (int i = 0; i < chars.length; ++i) {
            if (chars[i] == delim) {
                ++splitCount;
            }
        }
        if (splitCount == 0) {
            final String[] splits = { new String(what) };
            return splits;
        }
        final String[] splits = new String[splitCount + 1];
        int splitIndex = 0;
        int startIndex = 0;
        for (int j = 0; j < chars.length; ++j) {
            if (chars[j] == delim) {
                splits[splitIndex++] = new String(chars, startIndex, j - startIndex);
                startIndex = j + 1;
            }
        }
        splits[splitIndex] = new String(chars, startIndex, chars.length - startIndex);
        return splits;
    }
    
    public static String[] split(final String what, final String delim) {
        final ArrayList<String> items = new ArrayList<String>();
        int offset;
        int index;
        for (offset = 0; (index = what.indexOf(delim, offset)) != -1; offset = index + delim.length()) {
            items.add(what.substring(offset, index));
        }
        items.add(what.substring(offset));
        final String[] outgoing = new String[items.size()];
        items.toArray(outgoing);
        return outgoing;
    }
    
    static Pattern matchPattern(final String regexp) {
        Pattern p = null;
        if (PApplet.matchPatterns == null) {
            PApplet.matchPatterns = new HashMap<String, Pattern>();
        }
        else {
            p = PApplet.matchPatterns.get(regexp);
        }
        if (p == null) {
            if (PApplet.matchPatterns.size() == 10) {
                PApplet.matchPatterns.clear();
            }
            p = Pattern.compile(regexp, 40);
            PApplet.matchPatterns.put(regexp, p);
        }
        return p;
    }
    
    public static String[] match(final String what, final String regexp) {
        final Pattern p = matchPattern(regexp);
        final Matcher m = p.matcher(what);
        if (m.find()) {
            final int count = m.groupCount() + 1;
            final String[] groups = new String[count];
            for (int i = 0; i < count; ++i) {
                groups[i] = m.group(i);
            }
            return groups;
        }
        return null;
    }
    
    public static String[][] matchAll(final String what, final String regexp) {
        final Pattern p = matchPattern(regexp);
        final Matcher m = p.matcher(what);
        final ArrayList<String[]> results = new ArrayList<String[]>();
        final int count = m.groupCount() + 1;
        while (m.find()) {
            final String[] groups = new String[count];
            for (int i = 0; i < count; ++i) {
                groups[i] = m.group(i);
            }
            results.add(groups);
        }
        if (results.isEmpty()) {
            return null;
        }
        final String[][] matches = new String[results.size()][count];
        for (int i = 0; i < matches.length; ++i) {
            matches[i] = results.get(i);
        }
        return matches;
    }
    
    public static final boolean parseBoolean(final int what) {
        return what != 0;
    }
    
    public static final boolean parseBoolean(final String what) {
        return new Boolean(what);
    }
    
    public static final boolean[] parseBoolean(final byte[] what) {
        final boolean[] outgoing = new boolean[what.length];
        for (int i = 0; i < what.length; ++i) {
            outgoing[i] = (what[i] != 0);
        }
        return outgoing;
    }
    
    public static final boolean[] parseBoolean(final int[] what) {
        final boolean[] outgoing = new boolean[what.length];
        for (int i = 0; i < what.length; ++i) {
            outgoing[i] = (what[i] != 0);
        }
        return outgoing;
    }
    
    public static final boolean[] parseBoolean(final String[] what) {
        final boolean[] outgoing = new boolean[what.length];
        for (int i = 0; i < what.length; ++i) {
            outgoing[i] = new Boolean(what[i]);
        }
        return outgoing;
    }
    
    public static final byte parseByte(final boolean what) {
        return (byte)(what ? 1 : 0);
    }
    
    public static final byte parseByte(final char what) {
        return (byte)what;
    }
    
    public static final byte parseByte(final int what) {
        return (byte)what;
    }
    
    public static final byte parseByte(final float what) {
        return (byte)what;
    }
    
    public static final byte[] parseByte(final boolean[] what) {
        final byte[] outgoing = new byte[what.length];
        for (int i = 0; i < what.length; ++i) {
            outgoing[i] = (byte)(what[i] ? 1 : 0);
        }
        return outgoing;
    }
    
    public static final byte[] parseByte(final char[] what) {
        final byte[] outgoing = new byte[what.length];
        for (int i = 0; i < what.length; ++i) {
            outgoing[i] = (byte)what[i];
        }
        return outgoing;
    }
    
    public static final byte[] parseByte(final int[] what) {
        final byte[] outgoing = new byte[what.length];
        for (int i = 0; i < what.length; ++i) {
            outgoing[i] = (byte)what[i];
        }
        return outgoing;
    }
    
    public static final byte[] parseByte(final float[] what) {
        final byte[] outgoing = new byte[what.length];
        for (int i = 0; i < what.length; ++i) {
            outgoing[i] = (byte)what[i];
        }
        return outgoing;
    }
    
    public static final char parseChar(final byte what) {
        return (char)(what & 0xFF);
    }
    
    public static final char parseChar(final int what) {
        return (char)what;
    }
    
    public static final char[] parseChar(final byte[] what) {
        final char[] outgoing = new char[what.length];
        for (int i = 0; i < what.length; ++i) {
            outgoing[i] = (char)(what[i] & 0xFF);
        }
        return outgoing;
    }
    
    public static final char[] parseChar(final int[] what) {
        final char[] outgoing = new char[what.length];
        for (int i = 0; i < what.length; ++i) {
            outgoing[i] = (char)what[i];
        }
        return outgoing;
    }
    
    public static final int parseInt(final boolean what) {
        return what ? 1 : 0;
    }
    
    public static final int parseInt(final byte what) {
        return what & 0xFF;
    }
    
    public static final int parseInt(final char what) {
        return what;
    }
    
    public static final int parseInt(final float what) {
        return (int)what;
    }
    
    public static final int parseInt(final String what) {
        return parseInt(what, 0);
    }
    
    public static final int parseInt(final String what, final int otherwise) {
        try {
            final int offset = what.indexOf(46);
            if (offset == -1) {
                return Integer.parseInt(what);
            }
            return Integer.parseInt(what.substring(0, offset));
        }
        catch (NumberFormatException ex) {
            return otherwise;
        }
    }
    
    public static final int[] parseInt(final boolean[] what) {
        final int[] list = new int[what.length];
        for (int i = 0; i < what.length; ++i) {
            list[i] = (what[i] ? 1 : 0);
        }
        return list;
    }
    
    public static final int[] parseInt(final byte[] what) {
        final int[] list = new int[what.length];
        for (int i = 0; i < what.length; ++i) {
            list[i] = (what[i] & 0xFF);
        }
        return list;
    }
    
    public static final int[] parseInt(final char[] what) {
        final int[] list = new int[what.length];
        for (int i = 0; i < what.length; ++i) {
            list[i] = what[i];
        }
        return list;
    }
    
    public static int[] parseInt(final float[] what) {
        final int[] inties = new int[what.length];
        for (int i = 0; i < what.length; ++i) {
            inties[i] = (int)what[i];
        }
        return inties;
    }
    
    public static int[] parseInt(final String[] what) {
        return parseInt(what, 0);
    }
    
    public static int[] parseInt(final String[] what, final int missing) {
        final int[] output = new int[what.length];
        for (int i = 0; i < what.length; ++i) {
            try {
                output[i] = Integer.parseInt(what[i]);
            }
            catch (NumberFormatException e) {
                output[i] = missing;
            }
        }
        return output;
    }
    
    public static final float parseFloat(final int what) {
        return what;
    }
    
    public static final float parseFloat(final String what) {
        return parseFloat(what, Float.NaN);
    }
    
    public static final float parseFloat(final String what, final float otherwise) {
        try {
            return new Float(what);
        }
        catch (NumberFormatException ex) {
            return otherwise;
        }
    }
    
    public static final float[] parseByte(final byte[] what) {
        final float[] floaties = new float[what.length];
        for (int i = 0; i < what.length; ++i) {
            floaties[i] = what[i];
        }
        return floaties;
    }
    
    public static final float[] parseFloat(final int[] what) {
        final float[] floaties = new float[what.length];
        for (int i = 0; i < what.length; ++i) {
            floaties[i] = what[i];
        }
        return floaties;
    }
    
    public static final float[] parseFloat(final String[] what) {
        return parseFloat(what, Float.NaN);
    }
    
    public static final float[] parseFloat(final String[] what, final float missing) {
        final float[] output = new float[what.length];
        for (int i = 0; i < what.length; ++i) {
            try {
                output[i] = new Float(what[i]);
            }
            catch (NumberFormatException e) {
                output[i] = missing;
            }
        }
        return output;
    }
    
    public static final String str(final boolean x) {
        return String.valueOf(x);
    }
    
    public static final String str(final byte x) {
        return String.valueOf(x);
    }
    
    public static final String str(final char x) {
        return String.valueOf(x);
    }
    
    public static final String str(final int x) {
        return String.valueOf(x);
    }
    
    public static final String str(final float x) {
        return String.valueOf(x);
    }
    
    public static final String[] str(final boolean[] x) {
        final String[] s = new String[x.length];
        for (int i = 0; i < x.length; ++i) {
            s[i] = String.valueOf(x[i]);
        }
        return s;
    }
    
    public static final String[] str(final byte[] x) {
        final String[] s = new String[x.length];
        for (int i = 0; i < x.length; ++i) {
            s[i] = String.valueOf(x[i]);
        }
        return s;
    }
    
    public static final String[] str(final char[] x) {
        final String[] s = new String[x.length];
        for (int i = 0; i < x.length; ++i) {
            s[i] = String.valueOf(x[i]);
        }
        return s;
    }
    
    public static final String[] str(final int[] x) {
        final String[] s = new String[x.length];
        for (int i = 0; i < x.length; ++i) {
            s[i] = String.valueOf(x[i]);
        }
        return s;
    }
    
    public static final String[] str(final float[] x) {
        final String[] s = new String[x.length];
        for (int i = 0; i < x.length; ++i) {
            s[i] = String.valueOf(x[i]);
        }
        return s;
    }
    
    public static String[] nf(final int[] num, final int digits) {
        final String[] formatted = new String[num.length];
        for (int i = 0; i < formatted.length; ++i) {
            formatted[i] = nf(num[i], digits);
        }
        return formatted;
    }
    
    public static String nf(final int num, final int digits) {
        if (PApplet.int_nf != null && PApplet.int_nf_digits == digits && !PApplet.int_nf_commas) {
            return PApplet.int_nf.format(num);
        }
        (PApplet.int_nf = NumberFormat.getInstance()).setGroupingUsed(false);
        PApplet.int_nf_commas = false;
        PApplet.int_nf.setMinimumIntegerDigits(digits);
        PApplet.int_nf_digits = digits;
        return PApplet.int_nf.format(num);
    }
    
    public static String[] nfc(final int[] num) {
        final String[] formatted = new String[num.length];
        for (int i = 0; i < formatted.length; ++i) {
            formatted[i] = nfc(num[i]);
        }
        return formatted;
    }
    
    public static String nfc(final int num) {
        if (PApplet.int_nf != null && PApplet.int_nf_digits == 0 && PApplet.int_nf_commas) {
            return PApplet.int_nf.format(num);
        }
        (PApplet.int_nf = NumberFormat.getInstance()).setGroupingUsed(true);
        PApplet.int_nf_commas = true;
        PApplet.int_nf.setMinimumIntegerDigits(0);
        PApplet.int_nf_digits = 0;
        return PApplet.int_nf.format(num);
    }
    
    public static String nfs(final int num, final int digits) {
        return (num < 0) ? nf(num, digits) : (String.valueOf(' ') + nf(num, digits));
    }
    
    public static String[] nfs(final int[] num, final int digits) {
        final String[] formatted = new String[num.length];
        for (int i = 0; i < formatted.length; ++i) {
            formatted[i] = nfs(num[i], digits);
        }
        return formatted;
    }
    
    public static String nfp(final int num, final int digits) {
        return (num < 0) ? nf(num, digits) : (String.valueOf('+') + nf(num, digits));
    }
    
    public static String[] nfp(final int[] num, final int digits) {
        final String[] formatted = new String[num.length];
        for (int i = 0; i < formatted.length; ++i) {
            formatted[i] = nfp(num[i], digits);
        }
        return formatted;
    }
    
    public static String[] nf(final float[] num, final int left, final int right) {
        final String[] formatted = new String[num.length];
        for (int i = 0; i < formatted.length; ++i) {
            formatted[i] = nf(num[i], left, right);
        }
        return formatted;
    }
    
    public static String nf(final float num, final int left, final int right) {
        if (PApplet.float_nf != null && PApplet.float_nf_left == left && PApplet.float_nf_right == right && !PApplet.float_nf_commas) {
            return PApplet.float_nf.format(num);
        }
        (PApplet.float_nf = NumberFormat.getInstance()).setGroupingUsed(false);
        PApplet.float_nf_commas = false;
        if (left != 0) {
            PApplet.float_nf.setMinimumIntegerDigits(left);
        }
        if (right != 0) {
            PApplet.float_nf.setMinimumFractionDigits(right);
            PApplet.float_nf.setMaximumFractionDigits(right);
        }
        PApplet.float_nf_left = left;
        PApplet.float_nf_right = right;
        return PApplet.float_nf.format(num);
    }
    
    public static String[] nfc(final float[] num, final int right) {
        final String[] formatted = new String[num.length];
        for (int i = 0; i < formatted.length; ++i) {
            formatted[i] = nfc(num[i], right);
        }
        return formatted;
    }
    
    public static String nfc(final float num, final int right) {
        if (PApplet.float_nf != null && PApplet.float_nf_left == 0 && PApplet.float_nf_right == right && PApplet.float_nf_commas) {
            return PApplet.float_nf.format(num);
        }
        (PApplet.float_nf = NumberFormat.getInstance()).setGroupingUsed(true);
        PApplet.float_nf_commas = true;
        if (right != 0) {
            PApplet.float_nf.setMinimumFractionDigits(right);
            PApplet.float_nf.setMaximumFractionDigits(right);
        }
        PApplet.float_nf_left = 0;
        PApplet.float_nf_right = right;
        return PApplet.float_nf.format(num);
    }
    
    public static String[] nfs(final float[] num, final int left, final int right) {
        final String[] formatted = new String[num.length];
        for (int i = 0; i < formatted.length; ++i) {
            formatted[i] = nfs(num[i], left, right);
        }
        return formatted;
    }
    
    public static String nfs(final float num, final int left, final int right) {
        return (num < 0.0f) ? nf(num, left, right) : (String.valueOf(' ') + nf(num, left, right));
    }
    
    public static String[] nfp(final float[] num, final int left, final int right) {
        final String[] formatted = new String[num.length];
        for (int i = 0; i < formatted.length; ++i) {
            formatted[i] = nfp(num[i], left, right);
        }
        return formatted;
    }
    
    public static String nfp(final float num, final int left, final int right) {
        return (num < 0.0f) ? nf(num, left, right) : (String.valueOf('+') + nf(num, left, right));
    }
    
    public static final String hex(final byte what) {
        return hex(what, 2);
    }
    
    public static final String hex(final char what) {
        return hex(what, 4);
    }
    
    public static final String hex(final int what) {
        return hex(what, 8);
    }
    
    public static final String hex(final int what, final int digits) {
        final String stuff = Integer.toHexString(what).toUpperCase();
        final int length = stuff.length();
        if (length > digits) {
            return stuff.substring(length - digits);
        }
        if (length < digits) {
            return String.valueOf("00000000".substring(8 - (digits - length))) + stuff;
        }
        return stuff;
    }
    
    public static final int unhex(final String what) {
        return (int)Long.parseLong(what, 16);
    }
    
    public static final String binary(final byte what) {
        return binary(what, 8);
    }
    
    public static final String binary(final char what) {
        return binary(what, 16);
    }
    
    public static final String binary(final int what) {
        return Integer.toBinaryString(what);
    }
    
    public static final String binary(final int what, final int digits) {
        final String stuff = Integer.toBinaryString(what);
        final int length = stuff.length();
        if (length > digits) {
            return stuff.substring(length - digits);
        }
        if (length < digits) {
            final int offset = 32 - (digits - length);
            return String.valueOf("00000000000000000000000000000000".substring(offset)) + stuff;
        }
        return stuff;
    }
    
    public static final int unbinary(final String what) {
        return Integer.parseInt(what, 2);
    }
    
    public final int color(int gray) {
        if (this.g == null) {
            if (gray > 255) {
                gray = 255;
            }
            else if (gray < 0) {
                gray = 0;
            }
            return 0xFF000000 | gray << 16 | gray << 8 | gray;
        }
        return this.g.color(gray);
    }
    
    public final int color(final float fgray) {
        if (this.g == null) {
            int gray = (int)fgray;
            if (gray > 255) {
                gray = 255;
            }
            else if (gray < 0) {
                gray = 0;
            }
            return 0xFF000000 | gray << 16 | gray << 8 | gray;
        }
        return this.g.color(fgray);
    }
    
    public final int color(final int gray, int alpha) {
        if (this.g != null) {
            return this.g.color(gray, alpha);
        }
        if (alpha > 255) {
            alpha = 255;
        }
        else if (alpha < 0) {
            alpha = 0;
        }
        if (gray > 255) {
            return alpha << 24 | (gray & 0xFFFFFF);
        }
        return alpha << 24 | gray << 16 | gray << 8 | gray;
    }
    
    public final int color(final float fgray, final float falpha) {
        if (this.g == null) {
            int gray = (int)fgray;
            int alpha = (int)falpha;
            if (gray > 255) {
                gray = 255;
            }
            else if (gray < 0) {
                gray = 0;
            }
            if (alpha > 255) {
                alpha = 255;
            }
            else if (alpha < 0) {
                alpha = 0;
            }
            return 0xFF000000 | gray << 16 | gray << 8 | gray;
        }
        return this.g.color(fgray, falpha);
    }
    
    public final int color(int x, int y, int z) {
        if (this.g == null) {
            if (x > 255) {
                x = 255;
            }
            else if (x < 0) {
                x = 0;
            }
            if (y > 255) {
                y = 255;
            }
            else if (y < 0) {
                y = 0;
            }
            if (z > 255) {
                z = 255;
            }
            else if (z < 0) {
                z = 0;
            }
            return 0xFF000000 | x << 16 | y << 8 | z;
        }
        return this.g.color(x, y, z);
    }
    
    public final int color(float x, float y, float z) {
        if (this.g == null) {
            if (x > 255.0f) {
                x = 255.0f;
            }
            else if (x < 0.0f) {
                x = 0.0f;
            }
            if (y > 255.0f) {
                y = 255.0f;
            }
            else if (y < 0.0f) {
                y = 0.0f;
            }
            if (z > 255.0f) {
                z = 255.0f;
            }
            else if (z < 0.0f) {
                z = 0.0f;
            }
            return 0xFF000000 | (int)x << 16 | (int)y << 8 | (int)z;
        }
        return this.g.color(x, y, z);
    }
    
    public final int color(int x, int y, int z, int a) {
        if (this.g == null) {
            if (a > 255) {
                a = 255;
            }
            else if (a < 0) {
                a = 0;
            }
            if (x > 255) {
                x = 255;
            }
            else if (x < 0) {
                x = 0;
            }
            if (y > 255) {
                y = 255;
            }
            else if (y < 0) {
                y = 0;
            }
            if (z > 255) {
                z = 255;
            }
            else if (z < 0) {
                z = 0;
            }
            return a << 24 | x << 16 | y << 8 | z;
        }
        return this.g.color(x, y, z, a);
    }
    
    public final int color(float x, float y, float z, float a) {
        if (this.g == null) {
            if (a > 255.0f) {
                a = 255.0f;
            }
            else if (a < 0.0f) {
                a = 0.0f;
            }
            if (x > 255.0f) {
                x = 255.0f;
            }
            else if (x < 0.0f) {
                x = 0.0f;
            }
            if (y > 255.0f) {
                y = 255.0f;
            }
            else if (y < 0.0f) {
                y = 0.0f;
            }
            if (z > 255.0f) {
                z = 255.0f;
            }
            else if (z < 0.0f) {
                z = 0.0f;
            }
            return (int)a << 24 | (int)x << 16 | (int)y << 8 | (int)z;
        }
        return this.g.color(x, y, z, a);
    }
    
    public void setupExternalMessages() {
        this.frame.addComponentListener(new ComponentAdapter() {
            public void componentMoved(final ComponentEvent e) {
                final Point where = ((Frame)e.getSource()).getLocation();
                System.err.println("__MOVE__ " + where.x + " " + where.y);
                System.err.flush();
            }
        });
        this.frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(final WindowEvent e) {
                PApplet.this.exit();
            }
        });
    }
    
    public void setupFrameResizeListener() {
        this.frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(final ComponentEvent e) {
                if (PApplet.this.frame.isResizable()) {
                    final Frame farm = (Frame)e.getComponent();
                    if (farm.isVisible()) {
                        final Insets insets = farm.getInsets();
                        final Dimension windowSize = farm.getSize();
                        final int usableW = windowSize.width - insets.left - insets.right;
                        final int usableH = windowSize.height - insets.top - insets.bottom;
                        PApplet.this.setBounds(insets.left, insets.top, usableW, usableH);
                    }
                }
            }
        });
    }
    
    public static void main(final String[] args) {
        if (PApplet.platform == 2) {
            System.setProperty("apple.awt.graphics.UseQuartz", String.valueOf(PApplet.useQuartz));
        }
        if (args.length < 1) {
            System.err.println("Usage: PApplet <appletname>");
            System.err.println("For additional options, see the Javadoc for PApplet");
            System.exit(1);
        }
        boolean external = false;
        int[] location = null;
        int[] editorLocation = null;
        String name = null;
        boolean present = false;
        boolean exclusive = false;
        Color backgroundColor = Color.BLACK;
        Color stopColor = Color.GRAY;
        GraphicsDevice displayDevice = null;
        boolean hideStop = false;
        String param = null;
        String value = null;
        String folder = null;
        try {
            folder = System.getProperty("user.dir");
        }
        catch (Exception ex) {}
        for (int argIndex = 0; argIndex < args.length; ++argIndex) {
            final int equals = args[argIndex].indexOf(61);
            if (equals != -1) {
                param = args[argIndex].substring(0, equals);
                value = args[argIndex].substring(equals + 1);
                if (param.equals("--editor-location")) {
                    external = true;
                    editorLocation = parseInt(split(value, ','));
                }
                else if (param.equals("--display")) {
                    final int deviceIndex = Integer.parseInt(value) - 1;
                    final GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
                    final GraphicsDevice[] devices = environment.getScreenDevices();
                    if (deviceIndex >= 0 && deviceIndex < devices.length) {
                        displayDevice = devices[deviceIndex];
                    }
                    else {
                        System.err.println("Display " + value + " does not exist, " + "using the default display instead.");
                    }
                }
                else if (param.equals("--bgcolor")) {
                    if (value.charAt(0) == '#') {
                        value = value.substring(1);
                    }
                    backgroundColor = new Color(Integer.parseInt(value, 16));
                }
                else if (param.equals("--stop-color")) {
                    if (value.charAt(0) == '#') {
                        value = value.substring(1);
                    }
                    stopColor = new Color(Integer.parseInt(value, 16));
                }
                else if (param.equals("--sketch-path")) {
                    folder = value;
                }
                else if (param.equals("--location")) {
                    location = parseInt(split(value, ','));
                }
            }
            else if (args[argIndex].equals("--present")) {
                present = true;
            }
            else if (args[argIndex].equals("--exclusive")) {
                exclusive = true;
            }
            else if (args[argIndex].equals("--hide-stop")) {
                hideStop = true;
            }
            else {
                if (!args[argIndex].equals("--external")) {
                    name = args[argIndex];
                    break;
                }
                external = true;
            }
        }
        if (displayDevice == null) {
            final GraphicsEnvironment environment2 = GraphicsEnvironment.getLocalGraphicsEnvironment();
            displayDevice = environment2.getDefaultScreenDevice();
        }
        final Frame frame = new Frame(displayDevice.getDefaultConfiguration());
        frame.setResizable(false);
        final Image image = Toolkit.getDefaultToolkit().createImage(PApplet.ICON_IMAGE);
        frame.setIconImage(image);
        frame.setTitle(name);
        PApplet applet;
        try {
            final Class<?> c = Thread.currentThread().getContextClassLoader().loadClass(name);
            applet = (PApplet)c.newInstance();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        applet.frame = frame;
        applet.sketchPath = folder;
        applet.args = subset(args, 1);
        applet.external = external;
        Rectangle fullScreenRect = null;
        if (present) {
            frame.setUndecorated(true);
            frame.setBackground(backgroundColor);
            if (exclusive) {
                displayDevice.setFullScreenWindow(frame);
                frame.setExtendedState(6);
                fullScreenRect = frame.getBounds();
            }
            else {
                final DisplayMode mode = displayDevice.getDisplayMode();
                fullScreenRect = new Rectangle(0, 0, mode.getWidth(), mode.getHeight());
                frame.setBounds(fullScreenRect);
                frame.setVisible(true);
            }
        }
        frame.setLayout(null);
        frame.add(applet);
        if (present) {
            frame.invalidate();
        }
        else {
            frame.pack();
        }
        applet.init();
        while (applet.defaultSize && !applet.finished) {
            try {
                Thread.sleep(5L);
            }
            catch (InterruptedException ex2) {}
        }
        if (present) {
            frame.setBounds(fullScreenRect);
            applet.setBounds((fullScreenRect.width - applet.width) / 2, (fullScreenRect.height - applet.height) / 2, applet.width, applet.height);
            if (!hideStop) {
                final Label label = new Label("stop");
                label.setForeground(stopColor);
                label.addMouseListener(new MouseAdapter() {
                    public void mousePressed(final MouseEvent e) {
                        System.exit(0);
                    }
                });
                frame.add(label);
                Dimension labelSize = label.getPreferredSize();
                labelSize = new Dimension(100, labelSize.height);
                label.setSize(labelSize);
                label.setLocation(20, fullScreenRect.height - labelSize.height - 20);
            }
            if (external) {
                applet.setupExternalMessages();
            }
        }
        else {
            final Insets insets = frame.getInsets();
            final int windowW = Math.max(applet.width, 128) + insets.left + insets.right;
            final int windowH = Math.max(applet.height, 128) + insets.top + insets.bottom;
            frame.setSize(windowW, windowH);
            if (location != null) {
                frame.setLocation(location[0], location[1]);
            }
            else if (external) {
                int locationX = editorLocation[0] - 20;
                int locationY = editorLocation[1];
                if (locationX - windowW > 10) {
                    frame.setLocation(locationX - windowW, locationY);
                }
                else {
                    locationX = editorLocation[0] + 66;
                    locationY = editorLocation[1] + 66;
                    if (locationX + windowW > applet.screenWidth - 33 || locationY + windowH > applet.screenHeight - 33) {
                        locationX = (applet.screenWidth - windowW) / 2;
                        locationY = (applet.screenHeight - windowH) / 2;
                    }
                    frame.setLocation(locationX, locationY);
                }
            }
            else {
                frame.setLocation((applet.screenWidth - applet.width) / 2, (applet.screenHeight - applet.height) / 2);
            }
            final Point frameLoc = frame.getLocation();
            if (frameLoc.y < 0) {
                frame.setLocation(frameLoc.x, 30);
            }
            if (backgroundColor == Color.black) {
                backgroundColor = SystemColor.control;
            }
            frame.setBackground(backgroundColor);
            final int usableWindowH = windowH - insets.top - insets.bottom;
            applet.setBounds((windowW - applet.width) / 2, insets.top + (usableWindowH - applet.height) / 2, applet.width, applet.height);
            if (external) {
                applet.setupExternalMessages();
            }
            else {
                frame.addWindowListener(new WindowAdapter() {
                    public void windowClosing(final WindowEvent e) {
                        System.exit(0);
                    }
                });
            }
            applet.setupFrameResizeListener();
            if (applet.displayable()) {
                frame.setVisible(true);
            }
        }
    }
    
    public PGraphics beginRecord(final String renderer, String filename) {
        filename = this.insertFrame(filename);
        final PGraphics rec = this.createGraphics(this.width, this.height, renderer, filename);
        this.beginRecord(rec);
        return rec;
    }
    
    public void beginRecord(final PGraphics recorder) {
        (this.recorder = recorder).beginDraw();
    }
    
    public void endRecord() {
        if (this.recorder != null) {
            this.recorder.endDraw();
            this.recorder.dispose();
            this.recorder = null;
        }
    }
    
    public PGraphics beginRaw(final String renderer, String filename) {
        filename = this.insertFrame(filename);
        final PGraphics rec = this.createGraphics(this.width, this.height, renderer, filename);
        this.g.beginRaw(rec);
        return rec;
    }
    
    public void beginRaw(final PGraphics rawGraphics) {
        this.g.beginRaw(rawGraphics);
    }
    
    public void endRaw() {
        this.g.endRaw();
    }
    
    public void loadPixels() {
        this.g.loadPixels();
        this.pixels = this.g.pixels;
    }
    
    public void updatePixels() {
        this.g.updatePixels();
    }
    
    public void updatePixels(final int x1, final int y1, final int x2, final int y2) {
        this.g.updatePixels(x1, y1, x2, y2);
    }
    
    public void flush() {
        if (this.recorder != null) {
            this.recorder.flush();
        }
        this.g.flush();
    }
    
    public void hint(final int which) {
        if (this.recorder != null) {
            this.recorder.hint(which);
        }
        this.g.hint(which);
    }
    
    public void beginShape() {
        if (this.recorder != null) {
            this.recorder.beginShape();
        }
        this.g.beginShape();
    }
    
    public void beginShape(final int kind) {
        if (this.recorder != null) {
            this.recorder.beginShape(kind);
        }
        this.g.beginShape(kind);
    }
    
    public void edge(final boolean edge) {
        if (this.recorder != null) {
            this.recorder.edge(edge);
        }
        this.g.edge(edge);
    }
    
    public void normal(final float nx, final float ny, final float nz) {
        if (this.recorder != null) {
            this.recorder.normal(nx, ny, nz);
        }
        this.g.normal(nx, ny, nz);
    }
    
    public void textureMode(final int mode) {
        if (this.recorder != null) {
            this.recorder.textureMode(mode);
        }
        this.g.textureMode(mode);
    }
    
    public void texture(final PImage image) {
        if (this.recorder != null) {
            this.recorder.texture(image);
        }
        this.g.texture(image);
    }
    
    public void vertex(final float x, final float y) {
        if (this.recorder != null) {
            this.recorder.vertex(x, y);
        }
        this.g.vertex(x, y);
    }
    
    public void vertex(final float x, final float y, final float z) {
        if (this.recorder != null) {
            this.recorder.vertex(x, y, z);
        }
        this.g.vertex(x, y, z);
    }
    
    public void vertex(final float[] v) {
        if (this.recorder != null) {
            this.recorder.vertex(v);
        }
        this.g.vertex(v);
    }
    
    public void vertex(final float x, final float y, final float u, final float v) {
        if (this.recorder != null) {
            this.recorder.vertex(x, y, u, v);
        }
        this.g.vertex(x, y, u, v);
    }
    
    public void vertex(final float x, final float y, final float z, final float u, final float v) {
        if (this.recorder != null) {
            this.recorder.vertex(x, y, z, u, v);
        }
        this.g.vertex(x, y, z, u, v);
    }
    
    public void breakShape() {
        if (this.recorder != null) {
            this.recorder.breakShape();
        }
        this.g.breakShape();
    }
    
    public void endShape() {
        if (this.recorder != null) {
            this.recorder.endShape();
        }
        this.g.endShape();
    }
    
    public void endShape(final int mode) {
        if (this.recorder != null) {
            this.recorder.endShape(mode);
        }
        this.g.endShape(mode);
    }
    
    public void bezierVertex(final float x2, final float y2, final float x3, final float y3, final float x4, final float y4) {
        if (this.recorder != null) {
            this.recorder.bezierVertex(x2, y2, x3, y3, x4, y4);
        }
        this.g.bezierVertex(x2, y2, x3, y3, x4, y4);
    }
    
    public void bezierVertex(final float x2, final float y2, final float z2, final float x3, final float y3, final float z3, final float x4, final float y4, final float z4) {
        if (this.recorder != null) {
            this.recorder.bezierVertex(x2, y2, z2, x3, y3, z3, x4, y4, z4);
        }
        this.g.bezierVertex(x2, y2, z2, x3, y3, z3, x4, y4, z4);
    }
    
    public void curveVertex(final float x, final float y) {
        if (this.recorder != null) {
            this.recorder.curveVertex(x, y);
        }
        this.g.curveVertex(x, y);
    }
    
    public void curveVertex(final float x, final float y, final float z) {
        if (this.recorder != null) {
            this.recorder.curveVertex(x, y, z);
        }
        this.g.curveVertex(x, y, z);
    }
    
    public void point(final float x, final float y) {
        if (this.recorder != null) {
            this.recorder.point(x, y);
        }
        this.g.point(x, y);
    }
    
    public void point(final float x, final float y, final float z) {
        if (this.recorder != null) {
            this.recorder.point(x, y, z);
        }
        this.g.point(x, y, z);
    }
    
    public void line(final float x1, final float y1, final float x2, final float y2) {
        if (this.recorder != null) {
            this.recorder.line(x1, y1, x2, y2);
        }
        this.g.line(x1, y1, x2, y2);
    }
    
    public void line(final float x1, final float y1, final float z1, final float x2, final float y2, final float z2) {
        if (this.recorder != null) {
            this.recorder.line(x1, y1, z1, x2, y2, z2);
        }
        this.g.line(x1, y1, z1, x2, y2, z2);
    }
    
    public void triangle(final float x1, final float y1, final float x2, final float y2, final float x3, final float y3) {
        if (this.recorder != null) {
            this.recorder.triangle(x1, y1, x2, y2, x3, y3);
        }
        this.g.triangle(x1, y1, x2, y2, x3, y3);
    }
    
    public void quad(final float x1, final float y1, final float x2, final float y2, final float x3, final float y3, final float x4, final float y4) {
        if (this.recorder != null) {
            this.recorder.quad(x1, y1, x2, y2, x3, y3, x4, y4);
        }
        this.g.quad(x1, y1, x2, y2, x3, y3, x4, y4);
    }
    
    public void rectMode(final int mode) {
        if (this.recorder != null) {
            this.recorder.rectMode(mode);
        }
        this.g.rectMode(mode);
    }
    
    public void rect(final float a, final float b, final float c, final float d) {
        if (this.recorder != null) {
            this.recorder.rect(a, b, c, d);
        }
        this.g.rect(a, b, c, d);
    }
    
    public void rect(final float a, final float b, final float c, final float d, final float hr, final float vr) {
        if (this.recorder != null) {
            this.recorder.rect(a, b, c, d, hr, vr);
        }
        this.g.rect(a, b, c, d, hr, vr);
    }
    
    public void rect(final float a, final float b, final float c, final float d, final float tl, final float tr, final float bl, final float br) {
        if (this.recorder != null) {
            this.recorder.rect(a, b, c, d, tl, tr, bl, br);
        }
        this.g.rect(a, b, c, d, tl, tr, bl, br);
    }
    
    public void ellipseMode(final int mode) {
        if (this.recorder != null) {
            this.recorder.ellipseMode(mode);
        }
        this.g.ellipseMode(mode);
    }
    
    public void ellipse(final float a, final float b, final float c, final float d) {
        if (this.recorder != null) {
            this.recorder.ellipse(a, b, c, d);
        }
        this.g.ellipse(a, b, c, d);
    }
    
    public void arc(final float a, final float b, final float c, final float d, final float start, final float stop) {
        if (this.recorder != null) {
            this.recorder.arc(a, b, c, d, start, stop);
        }
        this.g.arc(a, b, c, d, start, stop);
    }
    
    public void box(final float size) {
        if (this.recorder != null) {
            this.recorder.box(size);
        }
        this.g.box(size);
    }
    
    public void box(final float w, final float h, final float d) {
        if (this.recorder != null) {
            this.recorder.box(w, h, d);
        }
        this.g.box(w, h, d);
    }
    
    public void sphereDetail(final int res) {
        if (this.recorder != null) {
            this.recorder.sphereDetail(res);
        }
        this.g.sphereDetail(res);
    }
    
    public void sphereDetail(final int ures, final int vres) {
        if (this.recorder != null) {
            this.recorder.sphereDetail(ures, vres);
        }
        this.g.sphereDetail(ures, vres);
    }
    
    public void sphere(final float r) {
        if (this.recorder != null) {
            this.recorder.sphere(r);
        }
        this.g.sphere(r);
    }
    
    public float bezierPoint(final float a, final float b, final float c, final float d, final float t) {
        return this.g.bezierPoint(a, b, c, d, t);
    }
    
    public float bezierTangent(final float a, final float b, final float c, final float d, final float t) {
        return this.g.bezierTangent(a, b, c, d, t);
    }
    
    public void bezierDetail(final int detail) {
        if (this.recorder != null) {
            this.recorder.bezierDetail(detail);
        }
        this.g.bezierDetail(detail);
    }
    
    public void bezier(final float x1, final float y1, final float x2, final float y2, final float x3, final float y3, final float x4, final float y4) {
        if (this.recorder != null) {
            this.recorder.bezier(x1, y1, x2, y2, x3, y3, x4, y4);
        }
        this.g.bezier(x1, y1, x2, y2, x3, y3, x4, y4);
    }
    
    public void bezier(final float x1, final float y1, final float z1, final float x2, final float y2, final float z2, final float x3, final float y3, final float z3, final float x4, final float y4, final float z4) {
        if (this.recorder != null) {
            this.recorder.bezier(x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4);
        }
        this.g.bezier(x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4);
    }
    
    public float curvePoint(final float a, final float b, final float c, final float d, final float t) {
        return this.g.curvePoint(a, b, c, d, t);
    }
    
    public float curveTangent(final float a, final float b, final float c, final float d, final float t) {
        return this.g.curveTangent(a, b, c, d, t);
    }
    
    public void curveDetail(final int detail) {
        if (this.recorder != null) {
            this.recorder.curveDetail(detail);
        }
        this.g.curveDetail(detail);
    }
    
    public void curveTightness(final float tightness) {
        if (this.recorder != null) {
            this.recorder.curveTightness(tightness);
        }
        this.g.curveTightness(tightness);
    }
    
    public void curve(final float x1, final float y1, final float x2, final float y2, final float x3, final float y3, final float x4, final float y4) {
        if (this.recorder != null) {
            this.recorder.curve(x1, y1, x2, y2, x3, y3, x4, y4);
        }
        this.g.curve(x1, y1, x2, y2, x3, y3, x4, y4);
    }
    
    public void curve(final float x1, final float y1, final float z1, final float x2, final float y2, final float z2, final float x3, final float y3, final float z3, final float x4, final float y4, final float z4) {
        if (this.recorder != null) {
            this.recorder.curve(x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4);
        }
        this.g.curve(x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4);
    }
    
    public void smooth() {
        if (this.recorder != null) {
            this.recorder.smooth();
        }
        this.g.smooth();
    }
    
    public void noSmooth() {
        if (this.recorder != null) {
            this.recorder.noSmooth();
        }
        this.g.noSmooth();
    }
    
    public void imageMode(final int mode) {
        if (this.recorder != null) {
            this.recorder.imageMode(mode);
        }
        this.g.imageMode(mode);
    }
    
    public void image(final PImage image, final float x, final float y) {
        if (this.recorder != null) {
            this.recorder.image(image, x, y);
        }
        this.g.image(image, x, y);
    }
    
    public void image(final PImage image, final float x, final float y, final float c, final float d) {
        if (this.recorder != null) {
            this.recorder.image(image, x, y, c, d);
        }
        this.g.image(image, x, y, c, d);
    }
    
    public void image(final PImage image, final float a, final float b, final float c, final float d, final int u1, final int v1, final int u2, final int v2) {
        if (this.recorder != null) {
            this.recorder.image(image, a, b, c, d, u1, v1, u2, v2);
        }
        this.g.image(image, a, b, c, d, u1, v1, u2, v2);
    }
    
    public void shapeMode(final int mode) {
        if (this.recorder != null) {
            this.recorder.shapeMode(mode);
        }
        this.g.shapeMode(mode);
    }
    
    public void shape(final PShape shape) {
        if (this.recorder != null) {
            this.recorder.shape(shape);
        }
        this.g.shape(shape);
    }
    
    public void shape(final PShape shape, final float x, final float y) {
        if (this.recorder != null) {
            this.recorder.shape(shape, x, y);
        }
        this.g.shape(shape, x, y);
    }
    
    public void shape(final PShape shape, final float x, final float y, final float c, final float d) {
        if (this.recorder != null) {
            this.recorder.shape(shape, x, y, c, d);
        }
        this.g.shape(shape, x, y, c, d);
    }
    
    public void textAlign(final int align) {
        if (this.recorder != null) {
            this.recorder.textAlign(align);
        }
        this.g.textAlign(align);
    }
    
    public void textAlign(final int alignX, final int alignY) {
        if (this.recorder != null) {
            this.recorder.textAlign(alignX, alignY);
        }
        this.g.textAlign(alignX, alignY);
    }
    
    public float textAscent() {
        return this.g.textAscent();
    }
    
    public float textDescent() {
        return this.g.textDescent();
    }
    
    public void textFont(final PFont which) {
        if (this.recorder != null) {
            this.recorder.textFont(which);
        }
        this.g.textFont(which);
    }
    
    public void textFont(final PFont which, final float size) {
        if (this.recorder != null) {
            this.recorder.textFont(which, size);
        }
        this.g.textFont(which, size);
    }
    
    public void textLeading(final float leading) {
        if (this.recorder != null) {
            this.recorder.textLeading(leading);
        }
        this.g.textLeading(leading);
    }
    
    public void textMode(final int mode) {
        if (this.recorder != null) {
            this.recorder.textMode(mode);
        }
        this.g.textMode(mode);
    }
    
    public void textSize(final float size) {
        if (this.recorder != null) {
            this.recorder.textSize(size);
        }
        this.g.textSize(size);
    }
    
    public float textWidth(final char c) {
        return this.g.textWidth(c);
    }
    
    public float textWidth(final String str) {
        return this.g.textWidth(str);
    }
    
    public float textWidth(final char[] chars, final int start, final int length) {
        return this.g.textWidth(chars, start, length);
    }
    
    public void text(final char c) {
        if (this.recorder != null) {
            this.recorder.text(c);
        }
        this.g.text(c);
    }
    
    public void text(final char c, final float x, final float y) {
        if (this.recorder != null) {
            this.recorder.text(c, x, y);
        }
        this.g.text(c, x, y);
    }
    
    public void text(final char c, final float x, final float y, final float z) {
        if (this.recorder != null) {
            this.recorder.text(c, x, y, z);
        }
        this.g.text(c, x, y, z);
    }
    
    public void text(final String str) {
        if (this.recorder != null) {
            this.recorder.text(str);
        }
        this.g.text(str);
    }
    
    public void text(final String str, final float x, final float y) {
        if (this.recorder != null) {
            this.recorder.text(str, x, y);
        }
        this.g.text(str, x, y);
    }
    
    public void text(final char[] chars, final int start, final int stop, final float x, final float y) {
        if (this.recorder != null) {
            this.recorder.text(chars, start, stop, x, y);
        }
        this.g.text(chars, start, stop, x, y);
    }
    
    public void text(final String str, final float x, final float y, final float z) {
        if (this.recorder != null) {
            this.recorder.text(str, x, y, z);
        }
        this.g.text(str, x, y, z);
    }
    
    public void text(final char[] chars, final int start, final int stop, final float x, final float y, final float z) {
        if (this.recorder != null) {
            this.recorder.text(chars, start, stop, x, y, z);
        }
        this.g.text(chars, start, stop, x, y, z);
    }
    
    public void text(final String str, final float x1, final float y1, final float x2, final float y2) {
        if (this.recorder != null) {
            this.recorder.text(str, x1, y1, x2, y2);
        }
        this.g.text(str, x1, y1, x2, y2);
    }
    
    public void text(final String s, final float x1, final float y1, final float x2, final float y2, final float z) {
        if (this.recorder != null) {
            this.recorder.text(s, x1, y1, x2, y2, z);
        }
        this.g.text(s, x1, y1, x2, y2, z);
    }
    
    public void text(final int num, final float x, final float y) {
        if (this.recorder != null) {
            this.recorder.text(num, x, y);
        }
        this.g.text(num, x, y);
    }
    
    public void text(final int num, final float x, final float y, final float z) {
        if (this.recorder != null) {
            this.recorder.text(num, x, y, z);
        }
        this.g.text(num, x, y, z);
    }
    
    public void text(final float num, final float x, final float y) {
        if (this.recorder != null) {
            this.recorder.text(num, x, y);
        }
        this.g.text(num, x, y);
    }
    
    public void text(final float num, final float x, final float y, final float z) {
        if (this.recorder != null) {
            this.recorder.text(num, x, y, z);
        }
        this.g.text(num, x, y, z);
    }
    
    public void pushMatrix() {
        if (this.recorder != null) {
            this.recorder.pushMatrix();
        }
        this.g.pushMatrix();
    }
    
    public void popMatrix() {
        if (this.recorder != null) {
            this.recorder.popMatrix();
        }
        this.g.popMatrix();
    }
    
    public void translate(final float tx, final float ty) {
        if (this.recorder != null) {
            this.recorder.translate(tx, ty);
        }
        this.g.translate(tx, ty);
    }
    
    public void translate(final float tx, final float ty, final float tz) {
        if (this.recorder != null) {
            this.recorder.translate(tx, ty, tz);
        }
        this.g.translate(tx, ty, tz);
    }
    
    public void rotate(final float angle) {
        if (this.recorder != null) {
            this.recorder.rotate(angle);
        }
        this.g.rotate(angle);
    }
    
    public void rotateX(final float angle) {
        if (this.recorder != null) {
            this.recorder.rotateX(angle);
        }
        this.g.rotateX(angle);
    }
    
    public void rotateY(final float angle) {
        if (this.recorder != null) {
            this.recorder.rotateY(angle);
        }
        this.g.rotateY(angle);
    }
    
    public void rotateZ(final float angle) {
        if (this.recorder != null) {
            this.recorder.rotateZ(angle);
        }
        this.g.rotateZ(angle);
    }
    
    public void rotate(final float angle, final float vx, final float vy, final float vz) {
        if (this.recorder != null) {
            this.recorder.rotate(angle, vx, vy, vz);
        }
        this.g.rotate(angle, vx, vy, vz);
    }
    
    public void scale(final float s) {
        if (this.recorder != null) {
            this.recorder.scale(s);
        }
        this.g.scale(s);
    }
    
    public void scale(final float sx, final float sy) {
        if (this.recorder != null) {
            this.recorder.scale(sx, sy);
        }
        this.g.scale(sx, sy);
    }
    
    public void scale(final float x, final float y, final float z) {
        if (this.recorder != null) {
            this.recorder.scale(x, y, z);
        }
        this.g.scale(x, y, z);
    }
    
    public void skewX(final float angle) {
        if (this.recorder != null) {
            this.recorder.skewX(angle);
        }
        this.g.skewX(angle);
    }
    
    public void skewY(final float angle) {
        if (this.recorder != null) {
            this.recorder.skewY(angle);
        }
        this.g.skewY(angle);
    }
    
    public void resetMatrix() {
        if (this.recorder != null) {
            this.recorder.resetMatrix();
        }
        this.g.resetMatrix();
    }
    
    public void applyMatrix(final PMatrix source) {
        if (this.recorder != null) {
            this.recorder.applyMatrix(source);
        }
        this.g.applyMatrix(source);
    }
    
    public void applyMatrix(final PMatrix2D source) {
        if (this.recorder != null) {
            this.recorder.applyMatrix(source);
        }
        this.g.applyMatrix(source);
    }
    
    public void applyMatrix(final float n00, final float n01, final float n02, final float n10, final float n11, final float n12) {
        if (this.recorder != null) {
            this.recorder.applyMatrix(n00, n01, n02, n10, n11, n12);
        }
        this.g.applyMatrix(n00, n01, n02, n10, n11, n12);
    }
    
    public void applyMatrix(final PMatrix3D source) {
        if (this.recorder != null) {
            this.recorder.applyMatrix(source);
        }
        this.g.applyMatrix(source);
    }
    
    public void applyMatrix(final float n00, final float n01, final float n02, final float n03, final float n10, final float n11, final float n12, final float n13, final float n20, final float n21, final float n22, final float n23, final float n30, final float n31, final float n32, final float n33) {
        if (this.recorder != null) {
            this.recorder.applyMatrix(n00, n01, n02, n03, n10, n11, n12, n13, n20, n21, n22, n23, n30, n31, n32, n33);
        }
        this.g.applyMatrix(n00, n01, n02, n03, n10, n11, n12, n13, n20, n21, n22, n23, n30, n31, n32, n33);
    }
    
    public PMatrix getMatrix() {
        return this.g.getMatrix();
    }
    
    public PMatrix2D getMatrix(final PMatrix2D target) {
        return this.g.getMatrix(target);
    }
    
    public PMatrix3D getMatrix(final PMatrix3D target) {
        return this.g.getMatrix(target);
    }
    
    public void setMatrix(final PMatrix source) {
        if (this.recorder != null) {
            this.recorder.setMatrix(source);
        }
        this.g.setMatrix(source);
    }
    
    public void setMatrix(final PMatrix2D source) {
        if (this.recorder != null) {
            this.recorder.setMatrix(source);
        }
        this.g.setMatrix(source);
    }
    
    public void setMatrix(final PMatrix3D source) {
        if (this.recorder != null) {
            this.recorder.setMatrix(source);
        }
        this.g.setMatrix(source);
    }
    
    public void printMatrix() {
        if (this.recorder != null) {
            this.recorder.printMatrix();
        }
        this.g.printMatrix();
    }
    
    public void beginCamera() {
        if (this.recorder != null) {
            this.recorder.beginCamera();
        }
        this.g.beginCamera();
    }
    
    public void endCamera() {
        if (this.recorder != null) {
            this.recorder.endCamera();
        }
        this.g.endCamera();
    }
    
    public void camera() {
        if (this.recorder != null) {
            this.recorder.camera();
        }
        this.g.camera();
    }
    
    public void camera(final float eyeX, final float eyeY, final float eyeZ, final float centerX, final float centerY, final float centerZ, final float upX, final float upY, final float upZ) {
        if (this.recorder != null) {
            this.recorder.camera(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
        }
        this.g.camera(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
    }
    
    public void printCamera() {
        if (this.recorder != null) {
            this.recorder.printCamera();
        }
        this.g.printCamera();
    }
    
    public void ortho() {
        if (this.recorder != null) {
            this.recorder.ortho();
        }
        this.g.ortho();
    }
    
    public void ortho(final float left, final float right, final float bottom, final float top, final float near, final float far) {
        if (this.recorder != null) {
            this.recorder.ortho(left, right, bottom, top, near, far);
        }
        this.g.ortho(left, right, bottom, top, near, far);
    }
    
    public void perspective() {
        if (this.recorder != null) {
            this.recorder.perspective();
        }
        this.g.perspective();
    }
    
    public void perspective(final float fovy, final float aspect, final float zNear, final float zFar) {
        if (this.recorder != null) {
            this.recorder.perspective(fovy, aspect, zNear, zFar);
        }
        this.g.perspective(fovy, aspect, zNear, zFar);
    }
    
    public void frustum(final float left, final float right, final float bottom, final float top, final float near, final float far) {
        if (this.recorder != null) {
            this.recorder.frustum(left, right, bottom, top, near, far);
        }
        this.g.frustum(left, right, bottom, top, near, far);
    }
    
    public void printProjection() {
        if (this.recorder != null) {
            this.recorder.printProjection();
        }
        this.g.printProjection();
    }
    
    public float screenX(final float x, final float y) {
        return this.g.screenX(x, y);
    }
    
    public float screenY(final float x, final float y) {
        return this.g.screenY(x, y);
    }
    
    public float screenX(final float x, final float y, final float z) {
        return this.g.screenX(x, y, z);
    }
    
    public float screenY(final float x, final float y, final float z) {
        return this.g.screenY(x, y, z);
    }
    
    public float screenZ(final float x, final float y, final float z) {
        return this.g.screenZ(x, y, z);
    }
    
    public float modelX(final float x, final float y, final float z) {
        return this.g.modelX(x, y, z);
    }
    
    public float modelY(final float x, final float y, final float z) {
        return this.g.modelY(x, y, z);
    }
    
    public float modelZ(final float x, final float y, final float z) {
        return this.g.modelZ(x, y, z);
    }
    
    public void pushStyle() {
        if (this.recorder != null) {
            this.recorder.pushStyle();
        }
        this.g.pushStyle();
    }
    
    public void popStyle() {
        if (this.recorder != null) {
            this.recorder.popStyle();
        }
        this.g.popStyle();
    }
    
    public void style(final PStyle s) {
        if (this.recorder != null) {
            this.recorder.style(s);
        }
        this.g.style(s);
    }
    
    public void strokeWeight(final float weight) {
        if (this.recorder != null) {
            this.recorder.strokeWeight(weight);
        }
        this.g.strokeWeight(weight);
    }
    
    public void strokeJoin(final int join) {
        if (this.recorder != null) {
            this.recorder.strokeJoin(join);
        }
        this.g.strokeJoin(join);
    }
    
    public void strokeCap(final int cap) {
        if (this.recorder != null) {
            this.recorder.strokeCap(cap);
        }
        this.g.strokeCap(cap);
    }
    
    public void noStroke() {
        if (this.recorder != null) {
            this.recorder.noStroke();
        }
        this.g.noStroke();
    }
    
    public void stroke(final int rgb) {
        if (this.recorder != null) {
            this.recorder.stroke(rgb);
        }
        this.g.stroke(rgb);
    }
    
    public void stroke(final int rgb, final float alpha) {
        if (this.recorder != null) {
            this.recorder.stroke(rgb, alpha);
        }
        this.g.stroke(rgb, alpha);
    }
    
    public void stroke(final float gray) {
        if (this.recorder != null) {
            this.recorder.stroke(gray);
        }
        this.g.stroke(gray);
    }
    
    public void stroke(final float gray, final float alpha) {
        if (this.recorder != null) {
            this.recorder.stroke(gray, alpha);
        }
        this.g.stroke(gray, alpha);
    }
    
    public void stroke(final float x, final float y, final float z) {
        if (this.recorder != null) {
            this.recorder.stroke(x, y, z);
        }
        this.g.stroke(x, y, z);
    }
    
    public void stroke(final float x, final float y, final float z, final float a) {
        if (this.recorder != null) {
            this.recorder.stroke(x, y, z, a);
        }
        this.g.stroke(x, y, z, a);
    }
    
    public void noTint() {
        if (this.recorder != null) {
            this.recorder.noTint();
        }
        this.g.noTint();
    }
    
    public void tint(final int rgb) {
        if (this.recorder != null) {
            this.recorder.tint(rgb);
        }
        this.g.tint(rgb);
    }
    
    public void tint(final int rgb, final float alpha) {
        if (this.recorder != null) {
            this.recorder.tint(rgb, alpha);
        }
        this.g.tint(rgb, alpha);
    }
    
    public void tint(final float gray) {
        if (this.recorder != null) {
            this.recorder.tint(gray);
        }
        this.g.tint(gray);
    }
    
    public void tint(final float gray, final float alpha) {
        if (this.recorder != null) {
            this.recorder.tint(gray, alpha);
        }
        this.g.tint(gray, alpha);
    }
    
    public void tint(final float x, final float y, final float z) {
        if (this.recorder != null) {
            this.recorder.tint(x, y, z);
        }
        this.g.tint(x, y, z);
    }
    
    public void tint(final float x, final float y, final float z, final float a) {
        if (this.recorder != null) {
            this.recorder.tint(x, y, z, a);
        }
        this.g.tint(x, y, z, a);
    }
    
    public void noFill() {
        if (this.recorder != null) {
            this.recorder.noFill();
        }
        this.g.noFill();
    }
    
    public void fill(final int rgb) {
        if (this.recorder != null) {
            this.recorder.fill(rgb);
        }
        this.g.fill(rgb);
    }
    
    public void fill(final int rgb, final float alpha) {
        if (this.recorder != null) {
            this.recorder.fill(rgb, alpha);
        }
        this.g.fill(rgb, alpha);
    }
    
    public void fill(final float gray) {
        if (this.recorder != null) {
            this.recorder.fill(gray);
        }
        this.g.fill(gray);
    }
    
    public void fill(final float gray, final float alpha) {
        if (this.recorder != null) {
            this.recorder.fill(gray, alpha);
        }
        this.g.fill(gray, alpha);
    }
    
    public void fill(final float x, final float y, final float z) {
        if (this.recorder != null) {
            this.recorder.fill(x, y, z);
        }
        this.g.fill(x, y, z);
    }
    
    public void fill(final float x, final float y, final float z, final float a) {
        if (this.recorder != null) {
            this.recorder.fill(x, y, z, a);
        }
        this.g.fill(x, y, z, a);
    }
    
    public void ambient(final int rgb) {
        if (this.recorder != null) {
            this.recorder.ambient(rgb);
        }
        this.g.ambient(rgb);
    }
    
    public void ambient(final float gray) {
        if (this.recorder != null) {
            this.recorder.ambient(gray);
        }
        this.g.ambient(gray);
    }
    
    public void ambient(final float x, final float y, final float z) {
        if (this.recorder != null) {
            this.recorder.ambient(x, y, z);
        }
        this.g.ambient(x, y, z);
    }
    
    public void specular(final int rgb) {
        if (this.recorder != null) {
            this.recorder.specular(rgb);
        }
        this.g.specular(rgb);
    }
    
    public void specular(final float gray) {
        if (this.recorder != null) {
            this.recorder.specular(gray);
        }
        this.g.specular(gray);
    }
    
    public void specular(final float x, final float y, final float z) {
        if (this.recorder != null) {
            this.recorder.specular(x, y, z);
        }
        this.g.specular(x, y, z);
    }
    
    public void shininess(final float shine) {
        if (this.recorder != null) {
            this.recorder.shininess(shine);
        }
        this.g.shininess(shine);
    }
    
    public void emissive(final int rgb) {
        if (this.recorder != null) {
            this.recorder.emissive(rgb);
        }
        this.g.emissive(rgb);
    }
    
    public void emissive(final float gray) {
        if (this.recorder != null) {
            this.recorder.emissive(gray);
        }
        this.g.emissive(gray);
    }
    
    public void emissive(final float x, final float y, final float z) {
        if (this.recorder != null) {
            this.recorder.emissive(x, y, z);
        }
        this.g.emissive(x, y, z);
    }
    
    public void lights() {
        if (this.recorder != null) {
            this.recorder.lights();
        }
        this.g.lights();
    }
    
    public void noLights() {
        if (this.recorder != null) {
            this.recorder.noLights();
        }
        this.g.noLights();
    }
    
    public void ambientLight(final float red, final float green, final float blue) {
        if (this.recorder != null) {
            this.recorder.ambientLight(red, green, blue);
        }
        this.g.ambientLight(red, green, blue);
    }
    
    public void ambientLight(final float red, final float green, final float blue, final float x, final float y, final float z) {
        if (this.recorder != null) {
            this.recorder.ambientLight(red, green, blue, x, y, z);
        }
        this.g.ambientLight(red, green, blue, x, y, z);
    }
    
    public void directionalLight(final float red, final float green, final float blue, final float nx, final float ny, final float nz) {
        if (this.recorder != null) {
            this.recorder.directionalLight(red, green, blue, nx, ny, nz);
        }
        this.g.directionalLight(red, green, blue, nx, ny, nz);
    }
    
    public void pointLight(final float red, final float green, final float blue, final float x, final float y, final float z) {
        if (this.recorder != null) {
            this.recorder.pointLight(red, green, blue, x, y, z);
        }
        this.g.pointLight(red, green, blue, x, y, z);
    }
    
    public void spotLight(final float red, final float green, final float blue, final float x, final float y, final float z, final float nx, final float ny, final float nz, final float angle, final float concentration) {
        if (this.recorder != null) {
            this.recorder.spotLight(red, green, blue, x, y, z, nx, ny, nz, angle, concentration);
        }
        this.g.spotLight(red, green, blue, x, y, z, nx, ny, nz, angle, concentration);
    }
    
    public void lightFalloff(final float constant, final float linear, final float quadratic) {
        if (this.recorder != null) {
            this.recorder.lightFalloff(constant, linear, quadratic);
        }
        this.g.lightFalloff(constant, linear, quadratic);
    }
    
    public void lightSpecular(final float x, final float y, final float z) {
        if (this.recorder != null) {
            this.recorder.lightSpecular(x, y, z);
        }
        this.g.lightSpecular(x, y, z);
    }
    
    public void background(final int rgb) {
        if (this.recorder != null) {
            this.recorder.background(rgb);
        }
        this.g.background(rgb);
    }
    
    public void background(final int rgb, final float alpha) {
        if (this.recorder != null) {
            this.recorder.background(rgb, alpha);
        }
        this.g.background(rgb, alpha);
    }
    
    public void background(final float gray) {
        if (this.recorder != null) {
            this.recorder.background(gray);
        }
        this.g.background(gray);
    }
    
    public void background(final float gray, final float alpha) {
        if (this.recorder != null) {
            this.recorder.background(gray, alpha);
        }
        this.g.background(gray, alpha);
    }
    
    public void background(final float x, final float y, final float z) {
        if (this.recorder != null) {
            this.recorder.background(x, y, z);
        }
        this.g.background(x, y, z);
    }
    
    public void background(final float x, final float y, final float z, final float a) {
        if (this.recorder != null) {
            this.recorder.background(x, y, z, a);
        }
        this.g.background(x, y, z, a);
    }
    
    public void background(final PImage image) {
        if (this.recorder != null) {
            this.recorder.background(image);
        }
        this.g.background(image);
    }
    
    public void colorMode(final int mode) {
        if (this.recorder != null) {
            this.recorder.colorMode(mode);
        }
        this.g.colorMode(mode);
    }
    
    public void colorMode(final int mode, final float max) {
        if (this.recorder != null) {
            this.recorder.colorMode(mode, max);
        }
        this.g.colorMode(mode, max);
    }
    
    public void colorMode(final int mode, final float maxX, final float maxY, final float maxZ) {
        if (this.recorder != null) {
            this.recorder.colorMode(mode, maxX, maxY, maxZ);
        }
        this.g.colorMode(mode, maxX, maxY, maxZ);
    }
    
    public void colorMode(final int mode, final float maxX, final float maxY, final float maxZ, final float maxA) {
        if (this.recorder != null) {
            this.recorder.colorMode(mode, maxX, maxY, maxZ, maxA);
        }
        this.g.colorMode(mode, maxX, maxY, maxZ, maxA);
    }
    
    public final float alpha(final int what) {
        return this.g.alpha(what);
    }
    
    public final float red(final int what) {
        return this.g.red(what);
    }
    
    public final float green(final int what) {
        return this.g.green(what);
    }
    
    public final float blue(final int what) {
        return this.g.blue(what);
    }
    
    public final float hue(final int what) {
        return this.g.hue(what);
    }
    
    public final float saturation(final int what) {
        return this.g.saturation(what);
    }
    
    public final float brightness(final int what) {
        return this.g.brightness(what);
    }
    
    public int lerpColor(final int c1, final int c2, final float amt) {
        return this.g.lerpColor(c1, c2, amt);
    }
    
    public static int lerpColor(final int c1, final int c2, final float amt, final int mode) {
        return PGraphics.lerpColor(c1, c2, amt, mode);
    }
    
    public boolean displayable() {
        return this.g.displayable();
    }
    
    public void setCache(final Object parent, final Object storage) {
        if (this.recorder != null) {
            this.recorder.setCache(parent, storage);
        }
        this.g.setCache(parent, storage);
    }
    
    public Object getCache(final Object parent) {
        return this.g.getCache(parent);
    }
    
    public void removeCache(final Object parent) {
        if (this.recorder != null) {
            this.recorder.removeCache(parent);
        }
        this.g.removeCache(parent);
    }
    
    public int get(final int x, final int y) {
        return this.g.get(x, y);
    }
    
    public PImage get(final int x, final int y, final int w, final int h) {
        return this.g.get(x, y, w, h);
    }
    
    public PImage get() {
        return this.g.get();
    }
    
    public void set(final int x, final int y, final int c) {
        if (this.recorder != null) {
            this.recorder.set(x, y, c);
        }
        this.g.set(x, y, c);
    }
    
    public void set(final int x, final int y, final PImage src) {
        if (this.recorder != null) {
            this.recorder.set(x, y, src);
        }
        this.g.set(x, y, src);
    }
    
    public void mask(final int[] maskArray) {
        if (this.recorder != null) {
            this.recorder.mask(maskArray);
        }
        this.g.mask(maskArray);
    }
    
    public void mask(final PImage maskImg) {
        if (this.recorder != null) {
            this.recorder.mask(maskImg);
        }
        this.g.mask(maskImg);
    }
    
    public void filter(final int kind) {
        if (this.recorder != null) {
            this.recorder.filter(kind);
        }
        this.g.filter(kind);
    }
    
    public void filter(final int kind, final float param) {
        if (this.recorder != null) {
            this.recorder.filter(kind, param);
        }
        this.g.filter(kind, param);
    }
    
    public void copy(final int sx, final int sy, final int sw, final int sh, final int dx, final int dy, final int dw, final int dh) {
        if (this.recorder != null) {
            this.recorder.copy(sx, sy, sw, sh, dx, dy, dw, dh);
        }
        this.g.copy(sx, sy, sw, sh, dx, dy, dw, dh);
    }
    
    public void copy(final PImage src, final int sx, final int sy, final int sw, final int sh, final int dx, final int dy, final int dw, final int dh) {
        if (this.recorder != null) {
            this.recorder.copy(src, sx, sy, sw, sh, dx, dy, dw, dh);
        }
        this.g.copy(src, sx, sy, sw, sh, dx, dy, dw, dh);
    }
    
    public static int blendColor(final int c1, final int c2, final int mode) {
        return PImage.blendColor(c1, c2, mode);
    }
    
    public void blend(final int sx, final int sy, final int sw, final int sh, final int dx, final int dy, final int dw, final int dh, final int mode) {
        if (this.recorder != null) {
            this.recorder.blend(sx, sy, sw, sh, dx, dy, dw, dh, mode);
        }
        this.g.blend(sx, sy, sw, sh, dx, dy, dw, dh, mode);
    }
    
    public void blend(final PImage src, final int sx, final int sy, final int sw, final int sh, final int dx, final int dy, final int dw, final int dh, final int mode) {
        if (this.recorder != null) {
            this.recorder.blend(src, sx, sy, sw, sh, dx, dy, dw, dh, mode);
        }
        this.g.blend(src, sx, sy, sw, sh, dx, dy, dw, dh, mode);
    }
    
    public static class RendererChangeException extends RuntimeException
    {
    }
    
    public class RegisteredMethods
    {
        int count;
        Object[] objects;
        Method[] methods;
        
        public void handle() {
            this.handle(new Object[0]);
        }
        
        public void handle(final Object[] oargs) {
            for (int i = 0; i < this.count; ++i) {
                try {
                    this.methods[i].invoke(this.objects[i], oargs);
                }
                catch (Exception e) {
                    if (e instanceof InvocationTargetException) {
                        final InvocationTargetException ite = (InvocationTargetException)e;
                        ite.getTargetException().printStackTrace();
                    }
                    else {
                        e.printStackTrace();
                    }
                }
            }
        }
        
        public void add(final Object object, final Method method) {
            if (this.objects == null) {
                this.objects = new Object[5];
                this.methods = new Method[5];
            }
            if (this.count == this.objects.length) {
                this.objects = (Object[])PApplet.expand(this.objects);
                this.methods = (Method[])PApplet.expand(this.methods);
            }
            this.objects[this.count] = object;
            this.methods[this.count] = method;
            ++this.count;
        }
        
        public void remove(final Object object, final Method method) {
            final int index = this.findIndex(object, method);
            if (index != -1) {
                --this.count;
                for (int i = index; i < this.count; ++i) {
                    this.objects[i] = this.objects[i + 1];
                    this.methods[i] = this.methods[i + 1];
                }
                this.objects[this.count] = null;
                this.methods[this.count] = null;
            }
        }
        
        protected int findIndex(final Object object, final Method method) {
            for (int i = 0; i < this.count; ++i) {
                if (this.objects[i] == object && this.methods[i].equals(method)) {
                    return i;
                }
            }
            return -1;
        }
    }
    
    class AsyncImageLoader extends Thread
    {
        String filename;
        String extension;
        PImage vessel;
        
        public AsyncImageLoader(final String filename, final String extension, final PImage vessel) {
            this.filename = filename;
            this.extension = extension;
            this.vessel = vessel;
        }
        
        public void run() {
            while (PApplet.this.requestImageCount == PApplet.this.requestImageMax) {
                try {
                    Thread.sleep(10L);
                }
                catch (InterruptedException ex) {}
            }
            final PApplet this$0 = PApplet.this;
            ++this$0.requestImageCount;
            final PImage actual = PApplet.this.loadImage(this.filename, this.extension);
            if (actual == null) {
                this.vessel.width = -1;
                this.vessel.height = -1;
            }
            else {
                this.vessel.width = actual.width;
                this.vessel.height = actual.height;
                this.vessel.format = actual.format;
                this.vessel.pixels = actual.pixels;
            }
            final PApplet this$2 = PApplet.this;
            --this$2.requestImageCount;
        }
    }
}
