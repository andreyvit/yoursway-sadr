package com.yoursway.sadr.python_v2.constructs;

import org.eclipse.dltk.ast.expressions.FloatNumericLiteral;

import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.model.builtins.FloatType;

public class FloatLiteralC extends PythonConstructImpl<FloatNumericLiteral> {
    FloatLiteralC(Scope sc, FloatNumericLiteral node) {
        super(sc, node);
    }
    
    @Override
    public PythonValueSet evaluate(final Krocodile context) {
        return new PythonValueSet(FloatType.wrap(this), context);
    }
}
