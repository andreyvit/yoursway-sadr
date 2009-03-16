package com.yoursway.sadr.python.constructs;

import kilim.pausable;

import org.eclipse.dltk.ast.expressions.BigNumericLiteral;

import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.model.types.LongType;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public class BigIntegerLiteralC extends PythonConstructImpl<BigNumericLiteral> {
    
    BigIntegerLiteralC(PythonStaticContext sc, BigNumericLiteral literal, PythonConstructImpl<?> parent) {
        super(sc, literal, parent);
    }
    
    @pausable
    public PythonValueSet evaluateValue(PythonDynamicContext dc, InfoKind infoKind) {
        return new PythonValueSet(LongType.wrap(this), dc);
    }
    
}
