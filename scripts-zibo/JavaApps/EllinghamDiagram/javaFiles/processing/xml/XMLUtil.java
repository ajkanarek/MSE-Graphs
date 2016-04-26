// 
// Decompiled by Procyon v0.5.30
// 

package processing.xml;

import java.io.Reader;
import java.io.IOException;

class XMLUtil
{
    static void skipComment(final StdXMLReader reader) throws IOException, XMLParseException {
        if (reader.read() != '-') {
            errorExpectedInput(reader.getSystemID(), reader.getLineNr(), "<!--");
        }
        int dashesRead = 0;
    Block_2:
        while (true) {
            final char ch = reader.read();
            switch (ch) {
                case '-': {
                    ++dashesRead;
                    continue;
                }
                case '>': {
                    if (dashesRead == 2) {
                        break Block_2;
                    }
                    dashesRead = 0;
                    continue;
                }
                default: {
                    dashesRead = 0;
                    continue;
                }
            }
        }
    }
    
    static void skipTag(final StdXMLReader reader) throws IOException, XMLParseException {
        int level = 1;
        while (level > 0) {
            final char ch = reader.read();
            switch (ch) {
                default: {
                    continue;
                }
                case '<': {
                    ++level;
                    continue;
                }
                case '>': {
                    --level;
                    continue;
                }
            }
        }
    }
    
    static String scanPublicID(final StringBuffer publicID, final StdXMLReader reader) throws IOException, XMLParseException {
        if (!checkLiteral(reader, "UBLIC")) {
            return null;
        }
        skipWhitespace(reader, null);
        publicID.append(scanString(reader, '\0', null));
        skipWhitespace(reader, null);
        return scanString(reader, '\0', null);
    }
    
    static String scanSystemID(final StdXMLReader reader) throws IOException, XMLParseException {
        if (!checkLiteral(reader, "YSTEM")) {
            return null;
        }
        skipWhitespace(reader, null);
        return scanString(reader, '\0', null);
    }
    
    static String scanIdentifier(final StdXMLReader reader) throws IOException, XMLParseException {
        final StringBuffer result = new StringBuffer();
        char ch;
        while (true) {
            ch = reader.read();
            if (ch != '_' && ch != ':' && ch != '-' && ch != '.' && (ch < 'a' || ch > 'z') && (ch < 'A' || ch > 'Z') && (ch < '0' || ch > '9') && ch <= '~') {
                break;
            }
            result.append(ch);
        }
        reader.unread(ch);
        return result.toString();
    }
    
    static String scanString(final StdXMLReader reader, final char entityChar, final XMLEntityResolver entityResolver) throws IOException, XMLParseException {
        final StringBuffer result = new StringBuffer();
        final int startingLevel = reader.getStreamLevel();
        final char delim = reader.read();
        if (delim != '\'' && delim != '\"') {
            errorExpectedInput(reader.getSystemID(), reader.getLineNr(), "delimited string");
        }
        while (true) {
            String str = read(reader, entityChar);
            final char ch = str.charAt(0);
            if (ch == entityChar) {
                if (str.charAt(1) == '#') {
                    result.append(processCharLiteral(str));
                }
                else {
                    processEntity(str, reader, entityResolver);
                }
            }
            else if (ch == '&') {
                reader.unread(ch);
                str = read(reader, '&');
                if (str.charAt(1) == '#') {
                    result.append(processCharLiteral(str));
                }
                else {
                    result.append(str);
                }
            }
            else if (reader.getStreamLevel() == startingLevel) {
                if (ch == delim) {
                    break;
                }
                if (ch == '\t' || ch == '\n' || ch == '\r') {
                    result.append(' ');
                }
                else {
                    result.append(ch);
                }
            }
            else {
                result.append(ch);
            }
        }
        return result.toString();
    }
    
    static void processEntity(String entity, final StdXMLReader reader, final XMLEntityResolver entityResolver) throws IOException, XMLParseException {
        entity = entity.substring(1, entity.length() - 1);
        final Reader entityReader = entityResolver.getEntity(reader, entity);
        if (entityReader == null) {
            errorInvalidEntity(reader.getSystemID(), reader.getLineNr(), entity);
        }
        final boolean externalEntity = entityResolver.isExternalEntity(entity);
        reader.startNewStream(entityReader, !externalEntity);
    }
    
    static char processCharLiteral(String entity) throws IOException, XMLParseException {
        if (entity.charAt(2) == 'x') {
            entity = entity.substring(3, entity.length() - 1);
            return (char)Integer.parseInt(entity, 16);
        }
        entity = entity.substring(2, entity.length() - 1);
        return (char)Integer.parseInt(entity, 10);
    }
    
    static void skipWhitespace(final StdXMLReader reader, final StringBuffer buffer) throws IOException {
        char ch;
        if (buffer == null) {
            do {
                ch = reader.read();
            } while (ch == ' ' || ch == '\t' || ch == '\n');
        }
        else {
            while (true) {
                ch = reader.read();
                if (ch != ' ' && ch != '\t' && ch != '\n') {
                    break;
                }
                if (ch == '\n') {
                    buffer.append('\n');
                }
                else {
                    buffer.append(' ');
                }
            }
        }
        reader.unread(ch);
    }
    
    static String read(final StdXMLReader reader, final char entityChar) throws IOException, XMLParseException {
        char ch = reader.read();
        final StringBuffer buf = new StringBuffer();
        buf.append(ch);
        if (ch == entityChar) {
            while (ch != ';') {
                ch = reader.read();
                buf.append(ch);
            }
        }
        return buf.toString();
    }
    
    static char readChar(final StdXMLReader reader, final char entityChar) throws IOException, XMLParseException {
        final String str = read(reader, entityChar);
        final char ch = str.charAt(0);
        if (ch == entityChar) {
            errorUnexpectedEntity(reader.getSystemID(), reader.getLineNr(), str);
        }
        return ch;
    }
    
    static boolean checkLiteral(final StdXMLReader reader, final String literal) throws IOException, XMLParseException {
        for (int i = 0; i < literal.length(); ++i) {
            if (reader.read() != literal.charAt(i)) {
                return false;
            }
        }
        return true;
    }
    
    static void errorExpectedInput(final String systemID, final int lineNr, final String expectedString) throws XMLParseException {
        throw new XMLParseException(systemID, lineNr, "Expected: " + expectedString);
    }
    
    static void errorInvalidEntity(final String systemID, final int lineNr, final String entity) throws XMLParseException {
        throw new XMLParseException(systemID, lineNr, "Invalid entity: `&" + entity + ";'");
    }
    
    static void errorUnexpectedEntity(final String systemID, final int lineNr, final String entity) throws XMLParseException {
        throw new XMLParseException(systemID, lineNr, "No entity reference is expected here (" + entity + ")");
    }
    
    static void errorUnexpectedCDATA(final String systemID, final int lineNr) throws XMLParseException {
        throw new XMLParseException(systemID, lineNr, "No CDATA section is expected here");
    }
    
    static void errorInvalidInput(final String systemID, final int lineNr, final String unexpectedString) throws XMLParseException {
        throw new XMLParseException(systemID, lineNr, "Invalid input: " + unexpectedString);
    }
    
    static void errorWrongClosingTag(final String systemID, final int lineNr, final String expectedName, final String wrongName) throws XMLParseException {
        throw new XMLParseException(systemID, lineNr, "Closing tag does not match opening tag: `" + wrongName + "' != `" + expectedName + "'");
    }
    
    static void errorClosingTagNotEmpty(final String systemID, final int lineNr) throws XMLParseException {
        throw new XMLParseException(systemID, lineNr, "Closing tag must be empty");
    }
    
    static void errorMissingElement(final String systemID, final int lineNr, final String parentElementName, final String missingElementName) throws XMLValidationException {
        throw new XMLValidationException(1, systemID, lineNr, missingElementName, null, null, "Element " + parentElementName + " expects to have a " + missingElementName);
    }
    
    static void errorUnexpectedElement(final String systemID, final int lineNr, final String parentElementName, final String unexpectedElementName) throws XMLValidationException {
        throw new XMLValidationException(2, systemID, lineNr, unexpectedElementName, null, null, "Unexpected " + unexpectedElementName + " in a " + parentElementName);
    }
    
    static void errorMissingAttribute(final String systemID, final int lineNr, final String elementName, final String attributeName) throws XMLValidationException {
        throw new XMLValidationException(3, systemID, lineNr, elementName, attributeName, null, "Element " + elementName + " expects an attribute named " + attributeName);
    }
    
    static void errorUnexpectedAttribute(final String systemID, final int lineNr, final String elementName, final String attributeName) throws XMLValidationException {
        throw new XMLValidationException(4, systemID, lineNr, elementName, attributeName, null, "Element " + elementName + " did not expect an attribute " + "named " + attributeName);
    }
    
    static void errorInvalidAttributeValue(final String systemID, final int lineNr, final String elementName, final String attributeName, final String attributeValue) throws XMLValidationException {
        throw new XMLValidationException(5, systemID, lineNr, elementName, attributeName, attributeValue, "Invalid value for attribute " + attributeName);
    }
    
    static void errorMissingPCData(final String systemID, final int lineNr, final String parentElementName) throws XMLValidationException {
        throw new XMLValidationException(6, systemID, lineNr, null, null, null, "Missing #PCDATA in element " + parentElementName);
    }
    
    static void errorUnexpectedPCData(final String systemID, final int lineNr, final String parentElementName) throws XMLValidationException {
        throw new XMLValidationException(7, systemID, lineNr, null, null, null, "Unexpected #PCDATA in element " + parentElementName);
    }
    
    static void validationError(final String systemID, final int lineNr, final String message, final String elementName, final String attributeName, final String attributeValue) throws XMLValidationException {
        throw new XMLValidationException(0, systemID, lineNr, elementName, attributeName, attributeValue, message);
    }
}
