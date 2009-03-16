package com.yoursway.sadr.python_v2.constructs;

import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.python.parser.ast.PythonArgument;

import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public class ArgumentC extends PythonConstructImpl<PythonArgument> {
    
    private final PythonConstruct init;
    
    ArgumentC(Scope scope, PythonArgument node) {
        super(scope, node);
        Assert.isLegal(node.getName() != null, "node.getName() should be != null");
        if (getPostChildren().size() > 0)
            init = getPostChildren().get(0);
        else
            init = null;
    }
    
    int getStar() {
        return node.getStar();
    }
    
    public String getName() {
        return node.getName();
    }
    
    @Override
    public PythonValueSet evaluate(Krocodile context) {
        Assert.isLegal(init != null, "node.getInitialization() should be != null");
        return init.evaluate(context);
    }
}
