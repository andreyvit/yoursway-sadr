package com.yoursway.sadr.python_v2.constructs;

import org.eclipse.dltk.python.parser.ast.expressions.PythonCallExpression;

public class ProcedureCallC extends CallC {
    
    ProcedureCallC(Scope sc, PythonCallExpression node) {
        super(sc, node);
    }
    
    @Override
    public String toString() {
        return node.getName() + "(" + node.getArgs().getChilds() + ")";
    }
    
    @Override
    public PythonConstruct getReceiver() {
        return null;
    }
}
