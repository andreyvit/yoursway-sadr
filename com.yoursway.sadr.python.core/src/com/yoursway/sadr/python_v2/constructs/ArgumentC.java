package com.yoursway.sadr.python_v2.constructs;

import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.python.parser.ast.PythonArgument;

import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.succeeder.IGoal;

public class ArgumentC extends PythonConstructImpl<PythonArgument> {
    
    private final PythonConstruct init;
    
    ArgumentC(Scope scope, PythonArgument node) {
        super(scope, node);
        Assert.isLegal(node.getName() != null, "node.getName() should be != null");
        if (node.getInitialization() != null)
            init = PythonConstructFactory.wrap(node.getInitialization(), scope);
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
    public IGoal evaluate(Context context, PythonValueSetAcceptor acceptor) {
        Assert.isLegal(init != null, "node.getInitialization() should be != null");
        return init.evaluate(context, acceptor);
    }
}
