package com.yoursway.sadr.python_v2.constructs;

import org.eclipse.dltk.python.parser.ast.expressions.PythonCallExpression;

@Deprecated
public class MethodCallC extends CallC {
    
    private final PythonConstruct receiver;
    
    MethodCallC(Scope sc, PythonCallExpression node) {
        super(sc, node);
        receiver = getPreChildren().get(CALLABLE_INDEX);
    }
    
    @Override
    public String toString() {
        return node.getName() + "(" + node.getArgs().getChilds() + ")";
    }
    
    @Override
    public PythonConstruct getReceiver() {
        return receiver;
    }
}
