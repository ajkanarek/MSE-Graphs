// 
// Decompiled by Procyon v0.5.30
// 

package thermodynamics;

import processing.core.PApplet;

public class HelloLibrary
{
    PApplet myParent;
    int myVariable;
    public static final String VERSION = "0.1.1";
    
    public HelloLibrary(final PApplet myParent) {
        this.myVariable = 0;
        this.myParent = myParent;
        this.welcome();
    }
    
    private void welcome() {
        System.out.println("thermodynamics 0.1.1");
    }
    
    public String sayHello() {
        return "hello library.";
    }
    
    public static String version() {
        return "0.1.1";
    }
    
    public void setVariable(final int n, final int n2) {
        this.myVariable = n + n2;
    }
    
    public int getVariable() {
        return this.myVariable;
    }
}
