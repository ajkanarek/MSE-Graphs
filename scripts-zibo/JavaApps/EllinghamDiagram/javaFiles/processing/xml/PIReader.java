// 
// Decompiled by Procyon v0.5.30
// 

package processing.xml;

import java.io.IOException;
import java.io.Reader;

class PIReader extends Reader
{
    private StdXMLReader reader;
    private boolean atEndOfData;
    
    PIReader(final StdXMLReader reader) {
        this.reader = reader;
        this.atEndOfData = false;
    }
    
    protected void finalize() throws Throwable {
        this.reader = null;
        super.finalize();
    }
    
    public int read(final char[] buffer, final int offset, int size) throws IOException {
        if (this.atEndOfData) {
            return -1;
        }
        int charsRead = 0;
        if (offset + size > buffer.length) {
            size = buffer.length - offset;
        }
        while (charsRead < size) {
            final char ch = this.reader.read();
            if (ch == '?') {
                final char ch2 = this.reader.read();
                if (ch2 == '>') {
                    this.atEndOfData = true;
                    break;
                }
                this.reader.unread(ch2);
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
            final char ch = this.reader.read();
            if (ch == '?') {
                final char ch2 = this.reader.read();
                if (ch2 != '>') {
                    continue;
                }
                this.atEndOfData = true;
            }
        }
    }
}
