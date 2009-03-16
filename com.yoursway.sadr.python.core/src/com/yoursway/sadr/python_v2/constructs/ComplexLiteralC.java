package com.yoursway.sadr.python_v2.constructs;

import org.eclipse.dltk.python.parser.ast.expressions.ComplexNumericLiteral;

import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.model.builtins.types.ComplexType;

public class ComplexLiteralC extends PythonConstructImpl<ComplexNumericLiteral> {
    
    ComplexLiteralC(Scope sc, ComplexNumericLiteral node) {
        super(sc, node);
    }
    
    @Override
    public PythonValueSet evaluate(final Krocodile context) {
        return new PythonValueSet(ComplexType.wrap(this), context);
    }
}
