package com.yoursway.sadr.python_v2.constructs;

import org.eclipse.dltk.ast.references.VariableReference;

import com.yoursway.sadr.blocks.integer_literals.BooleanValue;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.model.builtins.BooleanType;

public class BooleanLiteralC extends PythonConstructImpl<VariableReference> {
    
    public BooleanLiteralC(Scope sc, VariableReference node) {
        super(sc, node);
    }
    
    public boolean getValue() {
        String repr = node.getName();
        if (!BooleanValue.TRUE.equals(repr) && !BooleanValue.FALSE.equals(repr))
            throw new NullPointerException();
        return BooleanValue.TRUE.equals(repr);
    }
    
    @Override
    public PythonValueSet evaluate(Krocodile context) {
        return new PythonValueSet(BooleanType.wrap(this), context);
    }
}
