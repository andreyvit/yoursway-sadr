package com.yoursway.sadr.python_v2.constructs;

import org.eclipse.dltk.python.parser.ast.expressions.PythonCallExpression;

import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

public class ProcedureCallC extends CallC {
    
    ProcedureCallC(Scope sc, PythonCallExpression node) {
        super(sc, node);
    }
    
    @Override
    public String toString() {
        return node.getName() + "()";
    }
    
    @Override
    public PythonConstruct getReceiver() {
        return null;
    }
}
