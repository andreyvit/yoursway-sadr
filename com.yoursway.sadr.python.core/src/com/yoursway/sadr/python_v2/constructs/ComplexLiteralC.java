package com.yoursway.sadr.python_v2.constructs;

import org.eclipse.dltk.python.parser.ast.expressions.ComplexNumericLiteral;

import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.model.builtins.ComplexType;

public class ComplexLiteralC extends PythonConstructImpl<ComplexNumericLiteral> {
    
    ComplexLiteralC(Scope sc, ComplexNumericLiteral node) {
        super(sc, node);
    }
    
    @Override
    public PythonValueSet evaluate(final Krocodile context) {
        return new PythonValueSet(ComplexType.wrap(this), context);
    }
}
