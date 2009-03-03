package com.yoursway.sadr.python_v2.constructs;

import org.eclipse.dltk.ast.expressions.BigNumericLiteral;

import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.model.builtins.LongType;

public class BigIntegerLiteralC extends PythonConstructImpl<BigNumericLiteral> {
    
    BigIntegerLiteralC(Scope sc, BigNumericLiteral literal) {
        super(sc, literal);
    }
    
    @Override
    public PythonValueSet evaluate(final Krocodile context) {
        return new PythonValueSet(LongType.wrap(this), context);
    }
}
