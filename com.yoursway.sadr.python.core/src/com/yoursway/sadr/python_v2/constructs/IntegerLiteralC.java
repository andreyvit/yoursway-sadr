package com.yoursway.sadr.python_v2.constructs;

import kilim.pausable;

import org.eclipse.dltk.ast.expressions.NumericLiteral;

import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.model.builtins.values.IntegerValue;
import com.yoursway.sadr.python_v2.model.builtins.values.NumericValue;

public class IntegerLiteralC extends PythonConstructImpl<NumericLiteral> {
    
    IntegerLiteralC(Scope sc, NumericLiteral node) {
        super(sc, node);
    }
    
    @Override
    @pausable
    public PythonValueSet evaluate(final Krocodile context) {
        NumericValue val = new IntegerValue(this.node().getIntValue());
        return new PythonValueSet(val, context);
    }
}
