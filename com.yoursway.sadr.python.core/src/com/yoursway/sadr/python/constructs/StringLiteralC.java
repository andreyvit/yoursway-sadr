package com.yoursway.sadr.python.constructs;

import kilim.pausable;

import org.eclipse.dltk.ast.expressions.StringLiteral;

import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.index.unodes.ScalarLiteralUnode;
import com.yoursway.sadr.python.index.unodes.Unode;
import com.yoursway.sadr.python.model.types.StringType;
import com.yoursway.sadr.python.model.types.UnicodeType;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public class StringLiteralC extends PythonConstructImpl<StringLiteral> {
    
    private final boolean isUnicode;
    private final String value;
    
    StringLiteralC(PythonStaticContext sc, StringLiteral node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
        
        String v = node.getValue();
        if (v.startsWith("u") || v.startsWith("U")) {
            isUnicode = true;
            v = v.substring(1);
        } else {
            isUnicode = false;
        }
        v = unquote(v);
        value = v.replaceAll("\\n", "\n");
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
    
    public String stringValue() {
        return value;
    }
    
    public boolean isUnicode() {
        return isUnicode;
    }
    
    @pausable
    public PythonValueSet evaluateValue(PythonDynamicContext dc, InfoKind infoKind) {
        return createValue();
    }
    
    private PythonValueSet createValue() {
        return new PythonValueSet(isUnicode ? UnicodeType.wrap(this) : StringType.wrap(this));
    }
    
    @Override
    public Unode toUnode() {
        return new ScalarLiteralUnode(createValue());
    }
    
}
