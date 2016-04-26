// 
// Decompiled by Procyon v0.5.30
// 

package processing.xml;

import java.io.IOException;
import java.io.Reader;

class ContentReader extends Reader
{
    private StdXMLReader reader;
    private String buffer;
    private int bufferIndex;
    private XMLEntityResolver resolver;
    
    ContentReader(final StdXMLReader reader, final XMLEntityResolver resolver, final String buffer) {
        this.reader = reader;
        this.resolver = resolver;
        this.buffer = buffer;
        this.bufferIndex = 0;
    }
    
    protected void finalize() throws Throwable {
        this.reader = null;
        this.resolver = null;
        this.buffer = null;
        super.finalize();
    }
    
    public int read(final char[] outputBuffer, final int offset, int size) throws IOException {
        try {
            int charsRead = 0;
            final int bufferLength = this.buffer.length();
            if (offset + size > outputBuffer.length) {
                size = outputBuffer.length - offset;
            }
            while (charsRead < size) {
                String str = "";
                if (this.bufferIndex >= bufferLength) {
                    str = XMLUtil.read(this.reader, '&');
                    char ch = str.charAt(0);
                    if (ch == '<') {
                        this.reader.unread(ch);
                        break;
                    }
                    if (ch == '&' && str.length() > 1) {
                        if (str.charAt(1) != '#') {
                            XMLUtil.processEntity(str, this.reader, this.resolver);
                            continue;
                        }
                        ch = XMLUtil.processCharLiteral(str);
                    }
                    outputBuffer[charsRead] = ch;
                    ++charsRead;
                }
                else {
                    final char ch = this.buffer.charAt(this.bufferIndex);
                    ++this.bufferIndex;
                    outputBuffer[charsRead] = ch;
                    ++charsRead;
                }
            }
            if (charsRead == 0) {
                charsRead = -1;
            }
            return charsRead;
        }
        catch (XMLParseException e) {
            throw new IOException(e.getMessage());
        }
    }
    
    public void close() throws IOException {
        try {
            final int bufferLength = this.buffer.length();
            char ch;
            while (true) {
                String str = "";
                if (this.bufferIndex >= bufferLength) {
                    str = XMLUtil.read(this.reader, '&');
                    ch = str.charAt(0);
                    if (ch == '<') {
                        break;
                    }
                    if (ch != '&' || str.length() <= 1 || str.charAt(1) == '#') {
                        continue;
                    }
                    XMLUtil.processEntity(str, this.reader, this.resolver);
                }
                else {
                    ch = this.buffer.charAt(this.bufferIndex);
                    ++this.bufferIndex;
                }
            }
            this.reader.unread(ch);
        }
        catch (XMLParseException e) {
            throw new IOException(e.getMessage());
        }
    }
}
