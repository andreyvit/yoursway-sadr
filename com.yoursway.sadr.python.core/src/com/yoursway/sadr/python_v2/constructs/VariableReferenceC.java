package com.yoursway.sadr.python_v2.constructs;

import kilim.pausable;

import org.eclipse.dltk.ast.references.VariableReference;

import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.ResolveNameToObjectGoal;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public class VariableReferenceC extends PythonConstructImpl<VariableReference> {
    
    VariableReferenceC(Scope sc, VariableReference node) {
        super(sc, node);
    }
    
    @Override
    @pausable
    public PythonValueSet evaluate(final Krocodile context) {
        return new ResolveNameToObjectGoal(this, context).evaluate();
    }
    
    public String name() {
        return node.getName();
    }
}
