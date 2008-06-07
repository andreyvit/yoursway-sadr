package com.yoursway.sadr.python_v2.constructs;

import org.eclipse.dltk.ast.expressions.StringLiteral;

import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.goals.ExpressionValueGoal;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.goals.sideeffects.ValueF;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.builtins.StringType;
import com.yoursway.sadr.succeeder.IGoal;

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
    
    private static String unquote(String text) {
        if (text.startsWith("\"\"\"") && text.endsWith("\"\"\"") || text.startsWith("'''")
                && text.endsWith("'''"))
            return text.substring(3, text.length() - 3);
        if (text.startsWith("\"") && text.endsWith("\"") || text.startsWith("'") && text.endsWith("'")
                || text.startsWith("`") && text.endsWith("`"))
            return text.substring(1, text.length() - 1);
        return text;
    }
    
    @Override
    public IGoal evaluate(Context context, PythonValueSetAcceptor acceptor) {
        return new ExpressionValueGoal(context, acceptor) {
            public void preRun() {
                acceptor.addResult(StringType.wrap(StringLiteralC.this), getContext());
                updateGrade(acceptor, Grade.DONE);
            }
            
            @Override
            public String describe() {
                return super.describe() + "\nfor expression " + StringLiteralC.this.toString();
            }
        };
    }
    
    public String stringValue() {
        return value;
    }
    
    public boolean isUnicode() {
        return isUnicode;
    }
    
    @Override
    public Frog toFrog() {
        return new ValueF(StringType.wrap(this));
    }
    
}
