package com.yoursway.sadr.python.constructs;

import kilim.pausable;

import org.eclipse.dltk.ast.expressions.FloatNumericLiteral;

import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.model.types.FloatType;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public class FloatLiteralC extends PythonConstructImpl<FloatNumericLiteral> {
    
    FloatLiteralC(PythonStaticContext sc, FloatNumericLiteral node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
    }
    
    @pausable
    public PythonValueSet evaluateValue(PythonDynamicContext dc, InfoKind infoKind) {
        return new PythonValueSet(FloatType.wrap(this));
    }
    
}
