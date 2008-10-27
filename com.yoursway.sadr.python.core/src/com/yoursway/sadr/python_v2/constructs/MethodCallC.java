package com.yoursway.sadr.python_v2.constructs;

import org.eclipse.dltk.python.parser.ast.expressions.PythonCallExpression;

import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

@Deprecated
public class MethodCallC extends CallC {
    
    private final PythonConstruct receiver;
    
    MethodCallC(Scope sc, PythonCallExpression node) {
        super(sc, node);
        receiver = getPreChildren().get(RECEIVER);
    }
    
    @Override
    public String toString() {
        return node.toString();
    }
    
    @Override
    public PythonConstruct getReceiver() {
        return receiver;
    }
}
