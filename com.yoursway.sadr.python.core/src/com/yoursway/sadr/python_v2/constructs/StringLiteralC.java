package com.yoursway.sadr.python_v2.constructs;

import org.eclipse.dltk.ast.expressions.StringLiteral;

import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.model.builtins.types.StringType;
import com.yoursway.sadr.python_v2.model.builtins.types.UnicodeType;

public class StringLiteralC extends PythonConstructImpl<StringLiteral> {
    
    private final boolean isUnicode;
    private final String value;
    
    StringLiteralC(Scope sc, StringLiteral node) {
        super(sc, node);
        String v = node.getValue();
        if (v.startsWith("u") || v.startsWith("U")) {
            isUnicode = true;
            v = v.substring(1);
        } else {
            isUnicode = false;
        }
        v = unquote(v);
        value = v.replaceAll("\\n", "\n");
        //        v = v.replaceAll("<CR>", "\n");
    }
    
    private static boolean isQuoted(String text, String quotes) {
        return text.startsWith(quotes) && text.endsWith(quotes);
    }
    
    private static String unquote(String text) {
        if (isQuoted(text, "\"\"\"") || isQuoted(text, "'''"))
            return text.substring(3, text.length() - 3);
        if (isQuoted(text, "\"") || isQuoted(text, "'") || isQuoted(text, "`"))
            return text.substring(1, text.length() - 1);
        return text;
    }
    
    @Override
    public PythonValueSet evaluate(Krocodile context) {
        return new PythonValueSet(isUnicode ? UnicodeType.wrap(this) : StringType.wrap(this), context);
    }
    
    public String stringValue() {
        return value;
    }
    
    public boolean isUnicode() {
        return isUnicode;
    }
}
