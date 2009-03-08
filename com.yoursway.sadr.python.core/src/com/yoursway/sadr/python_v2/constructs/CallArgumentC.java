package com.yoursway.sadr.python_v2.constructs;

import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.python.parser.ast.PythonCallArgument;

import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public class CallArgumentC extends PythonConstructImpl<PythonCallArgument> {
    
    private final PythonConstruct value;
    
    public CallArgumentC(Scope scope, PythonCallArgument node) {
        super(scope, node);
        Assert.isLegal(node.getValue() != null, "node.getValue() should be != null");
        value = getPostChildren().get(0);
    }
    
    int getStar() {
        return node.getStar();
    }
    
    public PythonConstruct getValue() {
        return value;
    }
    
    @Override
    public PythonValueSet evaluate(Krocodile context) {
        return value.evaluate(context);
    }
}
