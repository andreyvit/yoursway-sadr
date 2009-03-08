package com.yoursway.sadr.python_v2.constructs;

import org.eclipse.dltk.python.parser.ast.statements.ReturnStatement;

import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public class ReturnC extends PythonConstructImpl<ReturnStatement> {
    
    ReturnC(Scope sc, ReturnStatement node) {
        super(sc, node);
    }
    
    public PythonConstruct getReturnedConstruct() {
        if (getPostChildren().size() != 1) {
            throw new IllegalStateException("Return statement contract violated!");
        }
        return getPostChildren().get(0);
    }
    
    @Override
    public PythonValueSet evaluate(Krocodile context) {
        return getReturnedConstruct().evaluate(context);
    }
}
