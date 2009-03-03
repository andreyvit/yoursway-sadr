package com.yoursway.sadr.python_v2.constructs;

import org.eclipse.dltk.ast.expressions.NumericLiteral;

import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.model.builtins.IntegerType;

public class IntegerLiteralC extends PythonConstructImpl<NumericLiteral> {
    
    IntegerLiteralC(Scope sc, NumericLiteral node) {
        super(sc, node);
    }
    
    @Override
    public PythonValueSet evaluate(final Krocodile context) {
        return new PythonValueSet(IntegerType.wrap(this), context);
    }
}
