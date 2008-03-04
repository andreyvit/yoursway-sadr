package com.yoursway.sadr.python.completion;

import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.internal.core.SourceRange;

public class RubySyntaxUtils {
    
    public static final String ARRAY_GET_METHOD = "[]".intern();
    
    public static final String ARRAY_PUT_METHOD = "[]=".intern();
    
    // FIXME Kalugin-WTF get the actual list from Andrey Tarantsov
    private static final String[] operatorMethods = { "[]", "[]=", "**", "!", "~", "+", "-", "*", "/", "%",
            "<<", ">>", "&", "^", "|", "<=", ">", "<", ">=", "<=>", "==", "===", "!=", "=~", "+@", "-@" };
    
    /**
     * If position is located inside a operator method, returns full region
     * correcponding to that operator.
     * 
     * @param contents
     * @param pos
     * @return range, if position is inside op., <code>null</code> if not
     */
    public static ISourceRange insideMethodOperator(String contents, int pos) {
        for (String op : operatorMethods) {
            int opLength = op.length();
            for (int j = 0; j < opLength; j++)
                try {
                    int start = pos - j;
                    int end = pos - j + opLength;
                    String piece = contents.substring(start, end);
                    if (!piece.equals(op))
                        continue;
                    return new SourceRange(start, opLength);
                } catch (IndexOutOfBoundsException e) {
                    continue;
                }
        }
        return null;
    }
    
    // FIXME Kalugin-WTF what about ? and !
    // Re: they are specially processed, ?,!,=(yes, you forgot =) is not a name chars
    public static boolean isNameChar(char c) {
        return (Character.isLetterOrDigit(c) || c == '_');
    }
    
    /**
     * Tries to find name enclosing given position. See isRubyName method for
     * "name" definion.
     * 
     * @param contents
     * @param pos
     * @return
     */
    public static ISourceRange getEnclosingName(CharSequence contents, int pos) {
        if (pos < 0 || pos >= contents.length())
            return null;
        
        int start = pos - 1;
        int end = pos;
        
        while (start >= 0 && isNameChar(contents.charAt(start)))
            start--;
        if (start > 0)
            if (contents.charAt(start) == '@') {
                start--;
                if (start > 0 && contents.charAt(start) == '@')
                    start--;
            } else if (contents.charAt(start) == '$')
                start--;
        
        end = start + 1;
        
        if (end < contents.length() && contents.charAt(end) == '@') {
            end++;
            if (end < contents.length() && contents.charAt(end) == '@')
                end++;
        } else if (end < contents.length() && contents.charAt(end) == '$')
            end++;
        
        while (end < contents.length() && isNameChar(contents.charAt(end)))
            end++;
        if (end < contents.length()) {
            char c = contents.charAt(end);
            if (c == '?' || c == '!' || c == '=')
                end++;
            else {
                
            }
            
        }
        
        int actualStart = start + 1;
        int actualEnd = end - 1;
        if (actualStart > actualEnd)
            return null;
        
        return new SourceRange(actualStart, actualEnd - actualStart + 1);
    }
    
    /**
     * Checks whether given name is an Ruby name. It's name if it's operator or
     * matches folling regexp:
     * 
     * <pre>
     * &circ;(@{0,2}|\\$)[_a-zA-Z0-9]+[\\?!=]?$
     * </pre>
     * 
     * @param str
     * @return
     */
    public static boolean isRubyName(String str) {
        for (String element : operatorMethods)
            if (element.equals(str))
                return true;
        return str.matches("^(@{0,2}|\\$)[_a-zA-Z0-9]+[\\?!=]?$");
    }
    
    public static boolean isLessStrictIdentifierCharacter(char ch) {
        return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9') || ch == '_'
                || ch == '@' || ch == '$';
    }
    
    public static boolean isStrictIdentifierCharacter(char ch) {
        return (ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9') || ch == '_' || ch == '@' || ch == '$';
    }
    
    public static boolean isIdentifierCharacter(char ch) {
        return (ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9') || ch == '_' || ch == '?' || ch == '!'
                || ch == '@' || ch == '$';
    }
    
    public static int getInclusiveStartOfIdentifierEndingAt(CharSequence document, int inclusiveEndOffset) {
        char ch = document.charAt(inclusiveEndOffset);
        if (!isIdentifierCharacter(ch))
            return -1;
        for (int offset = inclusiveEndOffset - 1; offset >= 0; offset--)
            if (!isIdentifierCharacter(document.charAt(offset)))
                return offset + 1;
        return 0;
    }
    
    public static boolean isValidRegexpModifier(char ch) {
        switch (ch) {
        case 'i':
        case 'x':
        case 'm':
        case 'o':
        case 'n':
        case 'e':
        case 's':
        case 'u':
            return true;
        default:
            return false;
        }
    }
    
    public static boolean isValidPercentStringStarter(char ch) {
        switch (ch) {
        case 'Q':
        case 'q':
        case 'W':
        case 'w':
        case 'x':
        case 'r':
        case 's':
            return true;
        default:
            return false;
        }
    }
    
    public static char getPercentStringTerminator(char leader) {
        switch (leader) {
        case '(':
            return ')';
        case '[':
            return ']';
        case '{':
            return '}';
        case '<':
            return '>';
        case '!':
        case '@':
        case '#':
        case '$':
        case '%':
        case '^':
        case '&':
        case '*':
        case ')':
        case '-':
        case '_':
        case '=':
        case '+':
        case ']':
        case '}':
        case ';':
        case ':':
        case '\'':
        case '"':
        case '\\':
        case '|':
        case ',':
        case '.':
        case '>':
        case '/':
        case '?':
        case '`':
        case '~':
            return leader;
        default:
            return (char) 0;
        }
    }
    
    public static int skipWhitespaceForward(char[] content, final int offset) {
        return skipWhitespaceForward(content, offset, content.length);
    }
    
    public static int skipWhitespaceForward(char[] content, final int offset, final int end) {
        for (int result = offset; result < end; result++) {
            if (result < 0 || result >= content.length)
                break;
            if (!isWhitespace(content[result]))
                return result;
        }
        return -1;
    }
    
    public static boolean isWhitespace(char ch) {
        switch (ch) {
        case ' ':
        case '\t':
        case '\n':
        case '\r':
            return true;
        default:
            return false;
        }
    }
    
    public static boolean isNonNewLineWhitespace(char ch) {
        switch (ch) {
        case ' ':
        case '\t':
            return true;
        default:
            return false;
        }
    }
    
    public static boolean isRubyOperator(String op) {
        for (String element : operatorMethods)
            if (op.equals(element))
                return true;
        return false;
    }
    
}
