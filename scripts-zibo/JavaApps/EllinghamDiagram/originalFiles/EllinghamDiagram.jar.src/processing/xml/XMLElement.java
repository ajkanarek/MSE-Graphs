/*      */ package processing.xml;
/*      */ 
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.io.Reader;
/*      */ import java.io.Serializable;
/*      */ import java.io.StringReader;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Properties;
/*      */ import java.util.Vector;
/*      */ import processing.core.PApplet;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class XMLElement
/*      */   implements Serializable
/*      */ {
/*      */   public static final int NO_LINE = -1;
/*      */   private XMLElement parent;
/*      */   private Vector<XMLAttribute> attributes;
/*      */   private Vector<XMLElement> children;
/*      */   private String name;
/*      */   private String fullName;
/*      */   private String namespace;
/*      */   private String content;
/*      */   private String systemID;
/*      */   private int lineNr;
/*      */   
/*      */   public XMLElement()
/*      */   {
/*  117 */     this(null, null, null, -1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void set(String fullName, String namespace, String systemID, int lineNr)
/*      */   {
/*  125 */     this.fullName = fullName;
/*  126 */     if (namespace == null) {
/*  127 */       this.name = fullName;
/*      */     } else {
/*  129 */       int index = fullName.indexOf(':');
/*  130 */       if (index >= 0) {
/*  131 */         this.name = fullName.substring(index + 1);
/*      */       } else {
/*  133 */         this.name = fullName;
/*      */       }
/*      */     }
/*  136 */     this.namespace = namespace;
/*  137 */     this.lineNr = lineNr;
/*  138 */     this.systemID = systemID;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public XMLElement(String fullName, String namespace, String systemID, int lineNr)
/*      */   {
/*  191 */     this.attributes = new Vector();
/*  192 */     this.children = new Vector(8);
/*  193 */     this.fullName = fullName;
/*  194 */     if (namespace == null) {
/*  195 */       this.name = fullName;
/*      */     } else {
/*  197 */       int index = fullName.indexOf(':');
/*  198 */       if (index >= 0) {
/*  199 */         this.name = fullName.substring(index + 1);
/*      */       } else {
/*  201 */         this.name = fullName;
/*      */       }
/*      */     }
/*  204 */     this.namespace = namespace;
/*  205 */     this.content = null;
/*  206 */     this.lineNr = lineNr;
/*  207 */     this.systemID = systemID;
/*  208 */     this.parent = null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public XMLElement(PApplet parent, String filename)
/*      */   {
/*  221 */     this();
/*  222 */     parseFromReader(parent.createReader(filename));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public XMLElement(Reader r)
/*      */   {
/*  229 */     this();
/*  230 */     parseFromReader(r);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public XMLElement(String xml)
/*      */   {
/*  237 */     this();
/*  238 */     parseFromReader(new StringReader(xml));
/*      */   }
/*      */   
/*      */   protected void parseFromReader(Reader r)
/*      */   {
/*      */     try {
/*  244 */       StdXMLParser parser = new StdXMLParser();
/*  245 */       parser.setBuilder(new StdXMLBuilder(this));
/*  246 */       parser.setValidator(new XMLValidator());
/*  247 */       parser.setReader(new StdXMLReader(r));
/*      */       
/*  249 */       parser.parse();
/*      */ 
/*      */     }
/*      */     catch (XMLException e)
/*      */     {
/*  254 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public XMLElement createPCDataElement()
/*      */   {
/*  277 */     return new XMLElement();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public XMLElement createElement(String fullName, String namespace)
/*      */   {
/*  315 */     return new XMLElement(fullName, namespace, null, -1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public XMLElement createElement(String fullName, String namespace, String systemID, int lineNr)
/*      */   {
/*  331 */     return new XMLElement(fullName, namespace, systemID, lineNr);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected void finalize()
/*      */     throws Throwable
/*      */   {
/*  339 */     this.attributes.clear();
/*  340 */     this.attributes = null;
/*  341 */     this.children = null;
/*  342 */     this.fullName = null;
/*  343 */     this.name = null;
/*  344 */     this.namespace = null;
/*  345 */     this.content = null;
/*  346 */     this.systemID = null;
/*  347 */     this.parent = null;
/*  348 */     super.finalize();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public XMLElement getParent()
/*      */   {
/*  357 */     return this.parent;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getName()
/*      */   {
/*  370 */     return this.fullName;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getLocalName()
/*      */   {
/*  380 */     return this.name;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getNamespace()
/*      */   {
/*  391 */     return this.namespace;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setName(String name)
/*      */   {
/*  402 */     this.name = name;
/*  403 */     this.fullName = name;
/*  404 */     this.namespace = null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setName(String fullName, String namespace)
/*      */   {
/*  415 */     int index = fullName.indexOf(':');
/*  416 */     if ((namespace == null) || (index < 0)) {
/*  417 */       this.name = fullName;
/*      */     } else {
/*  419 */       this.name = fullName.substring(index + 1);
/*      */     }
/*  421 */     this.fullName = fullName;
/*  422 */     this.namespace = namespace;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void addChild(XMLElement child)
/*      */   {
/*  432 */     if (child == null) {
/*  433 */       throw new IllegalArgumentException("child must not be null");
/*      */     }
/*  435 */     if ((child.getLocalName() == null) && (!this.children.isEmpty())) {
/*  436 */       XMLElement lastChild = (XMLElement)this.children.lastElement();
/*      */       
/*  438 */       if (lastChild.getLocalName() == null) {
/*  439 */         lastChild.setContent(lastChild.getContent() + 
/*  440 */           child.getContent());
/*  441 */         return;
/*      */       }
/*      */     }
/*  444 */     child.parent = this;
/*  445 */     this.children.addElement(child);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void insertChild(XMLElement child, int index)
/*      */   {
/*  456 */     if (child == null) {
/*  457 */       throw new IllegalArgumentException("child must not be null");
/*      */     }
/*  459 */     if ((child.getLocalName() == null) && (!this.children.isEmpty())) {
/*  460 */       XMLElement lastChild = (XMLElement)this.children.lastElement();
/*  461 */       if (lastChild.getLocalName() == null) {
/*  462 */         lastChild.setContent(lastChild.getContent() + 
/*  463 */           child.getContent());
/*  464 */         return;
/*      */       }
/*      */     }
/*  467 */     child.parent = this;
/*  468 */     this.children.insertElementAt(child, index);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void removeChild(XMLElement child)
/*      */   {
/*  478 */     if (child == null) {
/*  479 */       throw new IllegalArgumentException("child must not be null");
/*      */     }
/*  481 */     this.children.removeElement(child);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void removeChildAtIndex(int index)
/*      */   {
/*  491 */     this.children.removeElementAt(index);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Enumeration<XMLElement> enumerateChildren()
/*      */   {
/*  501 */     return this.children.elements();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isLeaf()
/*      */   {
/*  511 */     return this.children.isEmpty();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean hasChildren()
/*      */   {
/*  521 */     return !this.children.isEmpty();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getChildCount()
/*      */   {
/*  534 */     return this.children.size();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String[] listChildren()
/*      */   {
/*  553 */     int childCount = getChildCount();
/*  554 */     String[] outgoing = new String[childCount];
/*  555 */     for (int i = 0; i < childCount; i++) {
/*  556 */       outgoing[i] = getChild(i).getName();
/*      */     }
/*  558 */     return outgoing;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public XMLElement[] getChildren()
/*      */   {
/*  566 */     int childCount = getChildCount();
/*  567 */     XMLElement[] kids = new XMLElement[childCount];
/*  568 */     this.children.copyInto(kids);
/*  569 */     return kids;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public XMLElement getChild(int index)
/*      */   {
/*  579 */     return (XMLElement)this.children.elementAt(index);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public XMLElement getChild(String path)
/*      */   {
/*  597 */     if (path.indexOf('/') != -1) {
/*  598 */       return getChildRecursive(PApplet.split(path, '/'), 0);
/*      */     }
/*  600 */     int childCount = getChildCount();
/*  601 */     for (int i = 0; i < childCount; i++) {
/*  602 */       XMLElement kid = getChild(i);
/*  603 */       String kidName = kid.getName();
/*  604 */       if ((kidName != null) && (kidName.equals(path))) {
/*  605 */         return kid;
/*      */       }
/*      */     }
/*  608 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected XMLElement getChildRecursive(String[] items, int offset)
/*      */   {
/*  621 */     if (Character.isDigit(items[offset].charAt(0))) {
/*  622 */       XMLElement kid = getChild(Integer.parseInt(items[offset]));
/*  623 */       if (offset == items.length - 1) {
/*  624 */         return kid;
/*      */       }
/*  626 */       return kid.getChildRecursive(items, offset + 1);
/*      */     }
/*      */     
/*  629 */     int childCount = getChildCount();
/*  630 */     for (int i = 0; i < childCount; i++) {
/*  631 */       XMLElement kid = getChild(i);
/*  632 */       String kidName = kid.getName();
/*  633 */       if ((kidName != null) && (kidName.equals(items[offset]))) {
/*  634 */         if (offset == items.length - 1) {
/*  635 */           return kid;
/*      */         }
/*  637 */         return kid.getChildRecursive(items, offset + 1);
/*      */       }
/*      */     }
/*      */     
/*  641 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public XMLElement getChildAtIndex(int index)
/*      */     throws ArrayIndexOutOfBoundsException
/*      */   {
/*  657 */     return (XMLElement)this.children.elementAt(index);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public XMLElement[] getChildren(String path)
/*      */   {
/*  725 */     if (path.indexOf('/') != -1) {
/*  726 */       return getChildrenRecursive(PApplet.split(path, '/'), 0);
/*      */     }
/*      */     
/*      */ 
/*  730 */     if (Character.isDigit(path.charAt(0))) {
/*  731 */       return new XMLElement[] { getChild(Integer.parseInt(path)) };
/*      */     }
/*  733 */     int childCount = getChildCount();
/*  734 */     XMLElement[] matches = new XMLElement[childCount];
/*  735 */     int matchCount = 0;
/*  736 */     for (int i = 0; i < childCount; i++) {
/*  737 */       XMLElement kid = getChild(i);
/*  738 */       String kidName = kid.getName();
/*  739 */       if ((kidName != null) && (kidName.equals(path))) {
/*  740 */         matches[(matchCount++)] = kid;
/*      */       }
/*      */     }
/*  743 */     return (XMLElement[])PApplet.subset(matches, 0, matchCount);
/*      */   }
/*      */   
/*      */   protected XMLElement[] getChildrenRecursive(String[] items, int offset)
/*      */   {
/*  748 */     if (offset == items.length - 1) {
/*  749 */       return getChildren(items[offset]);
/*      */     }
/*  751 */     XMLElement[] matches = getChildren(items[offset]);
/*  752 */     XMLElement[] outgoing = new XMLElement[0];
/*  753 */     for (int i = 0; i < matches.length; i++) {
/*  754 */       XMLElement[] kidMatches = matches[i].getChildrenRecursive(items, offset + 1);
/*  755 */       outgoing = (XMLElement[])PApplet.concat(outgoing, kidMatches);
/*      */     }
/*  757 */     return outgoing;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private XMLAttribute findAttribute(String fullName)
/*      */   {
/*  821 */     Enumeration<XMLAttribute> en = this.attributes.elements();
/*  822 */     while (en.hasMoreElements()) {
/*  823 */       XMLAttribute attr = (XMLAttribute)en.nextElement();
/*  824 */       if (attr.getFullName().equals(fullName)) {
/*  825 */         return attr;
/*      */       }
/*      */     }
/*  828 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private XMLAttribute findAttribute(String name, String namespace)
/*      */   {
/*  842 */     Enumeration<XMLAttribute> en = this.attributes.elements();
/*  843 */     while (en.hasMoreElements()) {
/*  844 */       XMLAttribute attr = (XMLAttribute)en.nextElement();
/*  845 */       boolean found = attr.getName().equals(name);
/*  846 */       if (namespace == null) {
/*  847 */         found &= attr.getNamespace() == null;
/*      */       } else {
/*  849 */         found &= namespace.equals(attr.getNamespace());
/*      */       }
/*      */       
/*  852 */       if (found) {
/*  853 */         return attr;
/*      */       }
/*      */     }
/*  856 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getAttributeCount()
/*      */   {
/*  864 */     return this.attributes.size();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getAttribute(String name)
/*      */   {
/*  876 */     return getAttribute(name, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getAttribute(String name, String defaultValue)
/*      */   {
/*  890 */     XMLAttribute attr = findAttribute(name);
/*  891 */     if (attr == null) {
/*  892 */       return defaultValue;
/*      */     }
/*  894 */     return attr.getValue();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getAttribute(String name, String namespace, String defaultValue)
/*      */   {
/*  911 */     XMLAttribute attr = findAttribute(name, namespace);
/*  912 */     if (attr == null) {
/*  913 */       return defaultValue;
/*      */     }
/*  915 */     return attr.getValue();
/*      */   }
/*      */   
/*      */ 
/*      */   public String getStringAttribute(String name)
/*      */   {
/*  921 */     return getAttribute(name);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getStringAttribute(String name, String defaultValue)
/*      */   {
/*  936 */     return getAttribute(name, defaultValue);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getStringAttribute(String name, String namespace, String defaultValue)
/*      */   {
/*  943 */     return getAttribute(name, namespace, defaultValue);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int getIntAttribute(String name)
/*      */   {
/*  950 */     return getIntAttribute(name, 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getIntAttribute(String name, int defaultValue)
/*      */   {
/*  968 */     String value = getAttribute(name, Integer.toString(defaultValue));
/*  969 */     return Integer.parseInt(value);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getIntAttribute(String name, String namespace, int defaultValue)
/*      */   {
/*  985 */     String value = getAttribute(name, namespace, 
/*  986 */       Integer.toString(defaultValue));
/*  987 */     return Integer.parseInt(value);
/*      */   }
/*      */   
/*      */   public float getFloatAttribute(String name)
/*      */   {
/*  992 */     return getFloatAttribute(name, 0.0F);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float getFloatAttribute(String name, float defaultValue)
/*      */   {
/* 1011 */     String value = getAttribute(name, Float.toString(defaultValue));
/* 1012 */     return Float.parseFloat(value);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float getFloatAttribute(String name, String namespace, float defaultValue)
/*      */   {
/* 1029 */     String value = getAttribute(name, namespace, 
/* 1030 */       Float.toString(defaultValue));
/* 1031 */     return Float.parseFloat(value);
/*      */   }
/*      */   
/*      */   public double getDoubleAttribute(String name)
/*      */   {
/* 1036 */     return getDoubleAttribute(name, 0.0D);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public double getDoubleAttribute(String name, double defaultValue)
/*      */   {
/* 1050 */     String value = getAttribute(name, Double.toString(defaultValue));
/* 1051 */     return Double.parseDouble(value);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public double getDoubleAttribute(String name, String namespace, double defaultValue)
/*      */   {
/* 1067 */     String value = getAttribute(name, namespace, 
/* 1068 */       Double.toString(defaultValue));
/* 1069 */     return Double.parseDouble(value);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getAttributeType(String name)
/*      */   {
/* 1081 */     XMLAttribute attr = findAttribute(name);
/* 1082 */     if (attr == null) {
/* 1083 */       return null;
/*      */     }
/* 1085 */     return attr.getType();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getAttributeNamespace(String name)
/*      */   {
/* 1098 */     XMLAttribute attr = findAttribute(name);
/* 1099 */     if (attr == null) {
/* 1100 */       return null;
/*      */     }
/* 1102 */     return attr.getNamespace();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getAttributeType(String name, String namespace)
/*      */   {
/* 1117 */     XMLAttribute attr = findAttribute(name, namespace);
/* 1118 */     if (attr == null) {
/* 1119 */       return null;
/*      */     }
/* 1121 */     return attr.getType();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setAttribute(String name, String value)
/*      */   {
/* 1134 */     XMLAttribute attr = findAttribute(name);
/* 1135 */     if (attr == null) {
/* 1136 */       attr = new XMLAttribute(name, name, null, value, "CDATA");
/* 1137 */       this.attributes.addElement(attr);
/*      */     } else {
/* 1139 */       attr.setValue(value);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setAttribute(String fullName, String namespace, String value)
/*      */   {
/* 1154 */     int index = fullName.indexOf(':');
/* 1155 */     String vorname = fullName.substring(index + 1);
/* 1156 */     XMLAttribute attr = findAttribute(vorname, namespace);
/* 1157 */     if (attr == null) {
/* 1158 */       attr = new XMLAttribute(fullName, vorname, namespace, value, "CDATA");
/* 1159 */       this.attributes.addElement(attr);
/*      */     } else {
/* 1161 */       attr.setValue(value);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void removeAttribute(String name)
/*      */   {
/* 1172 */     for (int i = 0; i < this.attributes.size(); i++) {
/* 1173 */       XMLAttribute attr = (XMLAttribute)this.attributes.elementAt(i);
/* 1174 */       if (attr.getFullName().equals(name)) {
/* 1175 */         this.attributes.removeElementAt(i);
/* 1176 */         return;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void removeAttribute(String name, String namespace)
/*      */   {
/* 1190 */     for (int i = 0; i < this.attributes.size(); i++) {
/* 1191 */       XMLAttribute attr = (XMLAttribute)this.attributes.elementAt(i);
/* 1192 */       boolean found = attr.getName().equals(name);
/* 1193 */       if (namespace == null) {
/* 1194 */         found &= attr.getNamespace() == null;
/*      */       } else {
/* 1196 */         found &= attr.getNamespace().equals(namespace);
/*      */       }
/*      */       
/* 1199 */       if (found) {
/* 1200 */         this.attributes.removeElementAt(i);
/* 1201 */         return;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Enumeration<String> enumerateAttributeNames()
/*      */   {
/* 1213 */     Vector<String> result = new Vector();
/* 1214 */     Enumeration<XMLAttribute> en = this.attributes.elements();
/* 1215 */     while (en.hasMoreElements()) {
/* 1216 */       XMLAttribute attr = (XMLAttribute)en.nextElement();
/* 1217 */       result.addElement(attr.getFullName());
/*      */     }
/* 1219 */     return result.elements();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean hasAttribute(String name)
/*      */   {
/* 1229 */     return findAttribute(name) != null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean hasAttribute(String name, String namespace)
/*      */   {
/* 1240 */     return findAttribute(name, namespace) != null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Properties getAttributes()
/*      */   {
/* 1250 */     Properties result = new Properties();
/* 1251 */     Enumeration<XMLAttribute> en = this.attributes.elements();
/* 1252 */     while (en.hasMoreElements()) {
/* 1253 */       XMLAttribute attr = (XMLAttribute)en.nextElement();
/* 1254 */       result.put(attr.getFullName(), attr.getValue());
/*      */     }
/* 1256 */     return result;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Properties getAttributesInNamespace(String namespace)
/*      */   {
/* 1268 */     Properties result = new Properties();
/* 1269 */     Enumeration<XMLAttribute> en = this.attributes.elements();
/* 1270 */     while (en.hasMoreElements()) {
/* 1271 */       XMLAttribute attr = (XMLAttribute)en.nextElement();
/* 1272 */       if (namespace == null) {
/* 1273 */         if (attr.getNamespace() == null) {
/* 1274 */           result.put(attr.getName(), attr.getValue());
/*      */         }
/*      */       }
/* 1277 */       else if (namespace.equals(attr.getNamespace())) {
/* 1278 */         result.put(attr.getName(), attr.getValue());
/*      */       }
/*      */     }
/*      */     
/* 1282 */     return result;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getSystemID()
/*      */   {
/* 1294 */     return this.systemID;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getLineNr()
/*      */   {
/* 1307 */     return this.lineNr;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getContent()
/*      */   {
/* 1324 */     return this.content;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setContent(String content)
/*      */   {
/* 1335 */     this.content = content;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean equals(Object rawElement)
/*      */   {
/*      */     try
/*      */     {
/* 1346 */       return equalsXMLElement((XMLElement)rawElement);
/*      */     } catch (ClassCastException e) {}
/* 1348 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean equalsXMLElement(XMLElement rawElement)
/*      */   {
/* 1359 */     if (!this.name.equals(rawElement.getLocalName())) {
/* 1360 */       return false;
/*      */     }
/* 1362 */     if (this.attributes.size() != rawElement.getAttributeCount()) {
/* 1363 */       return false;
/*      */     }
/* 1365 */     Enumeration<XMLAttribute> en = this.attributes.elements();
/* 1366 */     while (en.hasMoreElements()) {
/* 1367 */       XMLAttribute attr = (XMLAttribute)en.nextElement();
/* 1368 */       if (!rawElement.hasAttribute(attr.getName(), attr.getNamespace())) {
/* 1369 */         return false;
/*      */       }
/* 1371 */       String value = rawElement.getAttribute(attr.getName(), 
/* 1372 */         attr.getNamespace(), 
/* 1373 */         null);
/* 1374 */       if (!attr.getValue().equals(value)) {
/* 1375 */         return false;
/*      */       }
/* 1377 */       String type = rawElement.getAttributeType(attr.getName(), 
/* 1378 */         attr.getNamespace());
/* 1379 */       if (!attr.getType().equals(type)) {
/* 1380 */         return false;
/*      */       }
/*      */     }
/* 1383 */     if (this.children.size() != rawElement.getChildCount()) {
/* 1384 */       return false;
/*      */     }
/* 1386 */     for (int i = 0; i < this.children.size(); i++) {
/* 1387 */       XMLElement child1 = getChildAtIndex(i);
/* 1388 */       XMLElement child2 = rawElement.getChildAtIndex(i);
/*      */       
/* 1390 */       if (!child1.equalsXMLElement(child2)) {
/* 1391 */         return false;
/*      */       }
/*      */     }
/* 1394 */     return true;
/*      */   }
/*      */   
/*      */   public String toString()
/*      */   {
/* 1399 */     return toString(true);
/*      */   }
/*      */   
/*      */   public String toString(boolean pretty)
/*      */   {
/* 1404 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 1405 */     OutputStreamWriter osw = new OutputStreamWriter(baos);
/* 1406 */     XMLWriter writer = new XMLWriter(osw);
/*      */     try {
/* 1408 */       if (pretty) {
/* 1409 */         writer.write(this, true, 2, true);
/*      */       } else {
/* 1411 */         writer.write(this, false, 0, true);
/*      */       }
/*      */     } catch (IOException e) {
/* 1414 */       e.printStackTrace();
/*      */     }
/* 1416 */     return baos.toString();
/*      */   }
/*      */ }


/* Location:              /Users/AndrewKanarek/Desktop/MICHIGAN/KiefferWork/scripts-zibo/JavaApps/EllinghamDiagram/core.jar!/processing/xml/XMLElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */