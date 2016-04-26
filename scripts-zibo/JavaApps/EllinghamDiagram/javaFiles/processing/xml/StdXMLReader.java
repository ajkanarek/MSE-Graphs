// 
// Decompiled by Procyon v0.5.30
// 

package processing.xml;

import java.io.UnsupportedEncodingException;
import java.io.InputStreamReader;
import java.io.PushbackInputStream;
import java.io.PushbackReader;
import java.io.LineNumberReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.Stack;

public class StdXMLReader
{
    private Stack<StackedReader> readers;
    private StackedReader currentReader;
    
    public static StdXMLReader stringReader(final String str) {
        return new StdXMLReader(new StringReader(str));
    }
    
    public static StdXMLReader fileReader(final String filename) throws FileNotFoundException, IOException {
        final StdXMLReader r = new StdXMLReader(new FileInputStream(filename));
        r.setSystemID(filename);
        for (int i = 0; i < r.readers.size(); ++i) {
            final StackedReader sr = r.readers.elementAt(i);
            sr.systemId = r.currentReader.systemId;
        }
        return r;
    }
    
    public StdXMLReader(final String publicID, String systemID) throws MalformedURLException, FileNotFoundException, IOException {
        URL systemIDasURL = null;
        try {
            systemIDasURL = new URL(systemID);
        }
        catch (MalformedURLException e) {
            systemID = "file:" + systemID;
            try {
                systemIDasURL = new URL(systemID);
            }
            catch (MalformedURLException e2) {
                throw e;
            }
        }
        this.currentReader = new StackedReader((StackedReader)null);
        this.readers = new Stack<StackedReader>();
        final Reader reader = this.openStream(publicID, systemIDasURL.toString());
        this.currentReader.lineReader = new LineNumberReader(reader);
        this.currentReader.pbReader = new PushbackReader(this.currentReader.lineReader, 2);
    }
    
    public StdXMLReader(final Reader reader) {
        this.currentReader = new StackedReader((StackedReader)null);
        this.readers = new Stack<StackedReader>();
        this.currentReader.lineReader = new LineNumberReader(reader);
        this.currentReader.pbReader = new PushbackReader(this.currentReader.lineReader, 2);
        this.currentReader.publicId = "";
        try {
            this.currentReader.systemId = new URL("file:.");
        }
        catch (MalformedURLException ex) {}
    }
    
    protected void finalize() throws Throwable {
        this.currentReader.lineReader = null;
        this.currentReader.pbReader = null;
        this.currentReader.systemId = null;
        this.currentReader.publicId = null;
        this.currentReader = null;
        this.readers.clear();
        super.finalize();
    }
    
    protected String getEncoding(final String str) {
        if (!str.startsWith("<?xml")) {
            return null;
        }
        int index2;
        for (int index = 5; index < str.length(); index = index2 + 1) {
            final StringBuffer key = new StringBuffer();
            while (index < str.length()) {
                if (str.charAt(index) > ' ') {
                    break;
                }
                ++index;
            }
            while (index < str.length() && str.charAt(index) >= 'a') {
                if (str.charAt(index) > 'z') {
                    break;
                }
                key.append(str.charAt(index));
                ++index;
            }
            while (index < str.length() && str.charAt(index) <= ' ') {
                ++index;
            }
            if (index >= str.length()) {
                break;
            }
            if (str.charAt(index) != '=') {
                break;
            }
            while (index < str.length() && str.charAt(index) != '\'' && str.charAt(index) != '\"') {
                ++index;
            }
            if (index >= str.length()) {
                break;
            }
            final char delimiter = str.charAt(index);
            ++index;
            index2 = str.indexOf(delimiter, index);
            if (index2 < 0) {
                break;
            }
            if (key.toString().equals("encoding")) {
                return str.substring(index, index2);
            }
        }
        return null;
    }
    
    protected Reader stream2reader(final InputStream stream, final StringBuffer charsRead) throws IOException {
        final PushbackInputStream pbstream = new PushbackInputStream(stream);
        int b = pbstream.read();
        switch (b) {
            case 0:
            case 254:
            case 255: {
                pbstream.unread(b);
                return new InputStreamReader(pbstream, "UTF-16");
            }
            case 239: {
                for (int i = 0; i < 2; ++i) {
                    pbstream.read();
                }
                return new InputStreamReader(pbstream, "UTF-8");
            }
            case 60: {
                b = pbstream.read();
                charsRead.append('<');
                while (b > 0 && b != 62) {
                    charsRead.append((char)b);
                    b = pbstream.read();
                }
                if (b > 0) {
                    charsRead.append((char)b);
                }
                final String encoding = this.getEncoding(charsRead.toString());
                if (encoding == null) {
                    return new InputStreamReader(pbstream, "UTF-8");
                }
                charsRead.setLength(0);
                try {
                    return new InputStreamReader(pbstream, encoding);
                }
                catch (UnsupportedEncodingException e) {
                    return new InputStreamReader(pbstream, "UTF-8");
                }
                break;
            }
        }
        charsRead.append((char)b);
        return new InputStreamReader(pbstream, "UTF-8");
    }
    
    public StdXMLReader(final InputStream stream) throws IOException {
        final StringBuffer charsRead = new StringBuffer();
        final Reader reader = this.stream2reader(stream, charsRead);
        this.currentReader = new StackedReader((StackedReader)null);
        this.readers = new Stack<StackedReader>();
        this.currentReader.lineReader = new LineNumberReader(reader);
        this.currentReader.pbReader = new PushbackReader(this.currentReader.lineReader, 2);
        this.currentReader.publicId = "";
        try {
            this.currentReader.systemId = new URL("file:.");
        }
        catch (MalformedURLException ex) {}
        this.startNewStream(new StringReader(charsRead.toString()));
    }
    
    public char read() throws IOException {
        int ch;
        for (ch = this.currentReader.pbReader.read(); ch < 0; ch = this.currentReader.pbReader.read()) {
            if (this.readers.empty()) {
                throw new IOException("Unexpected EOF");
            }
            this.currentReader.pbReader.close();
            this.currentReader = this.readers.pop();
        }
        return (char)ch;
    }
    
    public boolean atEOFOfCurrentStream() throws IOException {
        final int ch = this.currentReader.pbReader.read();
        if (ch < 0) {
            return true;
        }
        this.currentReader.pbReader.unread(ch);
        return false;
    }
    
    public boolean atEOF() throws IOException {
        int ch;
        for (ch = this.currentReader.pbReader.read(); ch < 0; ch = this.currentReader.pbReader.read()) {
            if (this.readers.empty()) {
                return true;
            }
            this.currentReader.pbReader.close();
            this.currentReader = this.readers.pop();
        }
        this.currentReader.pbReader.unread(ch);
        return false;
    }
    
    public void unread(final char ch) throws IOException {
        this.currentReader.pbReader.unread(ch);
    }
    
    public Reader openStream(final String publicID, final String systemID) throws MalformedURLException, FileNotFoundException, IOException {
        URL url = new URL(this.currentReader.systemId, systemID);
        if (url.getRef() != null) {
            final String ref = url.getRef();
            if (url.getFile().length() > 0) {
                url = new URL(url.getProtocol(), url.getHost(), url.getPort(), url.getFile());
                url = new URL("jar:" + url + '!' + ref);
            }
            else {
                url = StdXMLReader.class.getResource(ref);
            }
        }
        this.currentReader.publicId = publicID;
        this.currentReader.systemId = url;
        final StringBuffer charsRead = new StringBuffer();
        final Reader reader = this.stream2reader(url.openStream(), charsRead);
        if (charsRead.length() == 0) {
            return reader;
        }
        final String charsReadStr = charsRead.toString();
        final PushbackReader pbreader = new PushbackReader(reader, charsReadStr.length());
        for (int i = charsReadStr.length() - 1; i >= 0; --i) {
            pbreader.unread(charsReadStr.charAt(i));
        }
        return pbreader;
    }
    
    public void startNewStream(final Reader reader) {
        this.startNewStream(reader, false);
    }
    
    public void startNewStream(final Reader reader, final boolean isInternalEntity) {
        final StackedReader oldReader = this.currentReader;
        this.readers.push(this.currentReader);
        this.currentReader = new StackedReader((StackedReader)null);
        if (isInternalEntity) {
            this.currentReader.lineReader = null;
            this.currentReader.pbReader = new PushbackReader(reader, 2);
        }
        else {
            this.currentReader.lineReader = new LineNumberReader(reader);
            this.currentReader.pbReader = new PushbackReader(this.currentReader.lineReader, 2);
        }
        this.currentReader.systemId = oldReader.systemId;
        this.currentReader.publicId = oldReader.publicId;
    }
    
    public int getStreamLevel() {
        return this.readers.size();
    }
    
    public int getLineNr() {
        if (this.currentReader.lineReader != null) {
            return this.currentReader.lineReader.getLineNumber() + 1;
        }
        final StackedReader sr = this.readers.peek();
        if (sr.lineReader == null) {
            return 0;
        }
        return sr.lineReader.getLineNumber() + 1;
    }
    
    public void setSystemID(final String systemID) throws MalformedURLException {
        this.currentReader.systemId = new URL(this.currentReader.systemId, systemID);
    }
    
    public void setPublicID(final String publicID) {
        this.currentReader.publicId = publicID;
    }
    
    public String getSystemID() {
        return this.currentReader.systemId.toString();
    }
    
    public String getPublicID() {
        return this.currentReader.publicId;
    }
    
    private class StackedReader
    {
        PushbackReader pbReader;
        LineNumberReader lineReader;
        URL systemId;
        String publicId;
    }
}
