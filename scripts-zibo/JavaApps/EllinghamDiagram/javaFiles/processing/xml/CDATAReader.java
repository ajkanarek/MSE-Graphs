// 
// Decompiled by Procyon v0.5.30
// 

package processing.xml;

import java.io.IOException;
import java.io.Reader;

class CDATAReader extends Reader
{
    private StdXMLReader reader;
    private char savedChar;
    private boolean atEndOfData;
    
    CDATAReader(final StdXMLReader reader) {
        this.reader = reader;
        this.savedChar = '\0';
        this.atEndOfData = false;
    }
    
    protected void finalize() throws Throwable {
        this.reader = null;
        super.finalize();
    }
    
    public int read(final char[] buffer, final int offset, int size) throws IOException {
        int charsRead = 0;
        if (this.atEndOfData) {
            return -1;
        }
        if (offset + size > buffer.length) {
            size = buffer.length - offset;
        }
        while (charsRead < size) {
            char ch = this.savedChar;
            if (ch == '\0') {
                ch = this.reader.read();
            }
            else {
                this.savedChar = '\0';
            }
            if (ch == ']') {
                final char ch2 = this.reader.read();
                if (ch2 == ']') {
                    final char ch3 = this.reader.read();
                    if (ch3 == '>') {
                        this.atEndOfData = true;
                        break;
                    }
                    this.savedChar = ch2;
                    this.reader.unread(ch3);
                }
                else {
                    this.reader.unread(ch2);
                }
            }
            buffer[charsRead] = ch;
            ++charsRead;
        }
        if (charsRead == 0) {
            charsRead = -1;
        }
        return charsRead;
    }
    
    public void close() throws IOException {
        while (!this.atEndOfData) {
            char ch = this.savedChar;
            if (ch == '\0') {
                ch = this.reader.read();
            }
            else {
                this.savedChar = '\0';
            }
            if (ch == ']') {
                final char ch2 = this.reader.read();
                if (ch2 == ']') {
                    final char ch3 = this.reader.read();
                    if (ch3 == '>') {
                        break;
                    }
                    this.savedChar = ch2;
                    this.reader.unread(ch3);
                }
                else {
                    this.reader.unread(ch2);
                }
            }
        }
        this.atEndOfData = true;
    }
}
