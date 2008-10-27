package com.yoursway.sadr.python_v2.constructs;

import org.eclipse.dltk.ast.references.VariableReference;

import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.goals.ResolveNameToObjectGoal;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.succeeder.IGoal;

public class VariableReferenceC extends PythonConstructImpl<VariableReference> {
    
    VariableReferenceC(Scope sc, VariableReference node) {
        super(sc, node);
    }
    
    @Override
    public IGoal evaluate(Context context, PythonValueSetAcceptor acceptor) {
        return new ResolveNameToObjectGoal(this, context, acceptor);
    }
    
    @Override
    public boolean match(Frog frog) {
        return node.getName().equals(frog);
    }
    
    public String name() {
        return node.getName();
    }
}
