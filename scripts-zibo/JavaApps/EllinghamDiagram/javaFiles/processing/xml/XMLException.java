// 
// Decompiled by Procyon v0.5.30
// 

package processing.xml;

import java.io.PrintStream;
import java.io.PrintWriter;

public class XMLException extends Exception
{
    private String msg;
    private String systemID;
    private int lineNr;
    private Exception encapsulatedException;
    
    public XMLException(final String msg) {
        this(null, -1, null, msg, false);
    }
    
    public XMLException(final Exception e) {
        this(null, -1, e, "Nested Exception", false);
    }
    
    public XMLException(final String systemID, final int lineNr, final Exception e) {
        this(systemID, lineNr, e, "Nested Exception", true);
    }
    
    public XMLException(final String systemID, final int lineNr, final String msg) {
        this(systemID, lineNr, null, msg, true);
    }
    
    public XMLException(final String systemID, final int lineNr, final Exception e, final String msg, final boolean reportParams) {
        super(buildMessage(systemID, lineNr, e, msg, reportParams));
        this.systemID = systemID;
        this.lineNr = lineNr;
        this.encapsulatedException = e;
        this.msg = buildMessage(systemID, lineNr, e, msg, reportParams);
    }
    
    private static String buildMessage(final String systemID, final int lineNr, final Exception e, final String msg, final boolean reportParams) {
        String str = msg;
        if (reportParams) {
            if (systemID != null) {
                str = String.valueOf(str) + ", SystemID='" + systemID + "'";
            }
            if (lineNr >= 0) {
                str = String.valueOf(str) + ", Line=" + lineNr;
            }
            if (e != null) {
                str = String.valueOf(str) + ", Exception: " + e;
            }
        }
        return str;
    }
    
    protected void finalize() throws Throwable {
        this.systemID = null;
        this.encapsulatedException = null;
        super.finalize();
    }
    
    public String getSystemID() {
        return this.systemID;
    }
    
    public int getLineNr() {
        return this.lineNr;
    }
    
    public Exception getException() {
        return this.encapsulatedException;
    }
    
    public void printStackTrace(final PrintWriter writer) {
        super.printStackTrace(writer);
        if (this.encapsulatedException != null) {
            writer.println("*** Nested Exception:");
            this.encapsulatedException.printStackTrace(writer);
        }
    }
    
    public void printStackTrace(final PrintStream stream) {
        super.printStackTrace(stream);
        if (this.encapsulatedException != null) {
            stream.println("*** Nested Exception:");
            this.encapsulatedException.printStackTrace(stream);
        }
    }
    
    public void printStackTrace() {
        super.printStackTrace();
        if (this.encapsulatedException != null) {
            System.err.println("*** Nested Exception:");
            this.encapsulatedException.printStackTrace();
        }
    }
    
    public String toString() {
        return this.msg;
    }
}
