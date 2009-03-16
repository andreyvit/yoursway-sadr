package com.yoursway.sadr.python.constructs;

import kilim.pausable;

import org.eclipse.dltk.ast.expressions.NumericLiteral;

import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.model.values.IntegerValue;
import com.yoursway.sadr.python.model.values.NumericValue;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public class IntegerLiteralC extends PythonConstructImpl<NumericLiteral> {
    
    IntegerLiteralC(PythonStaticContext sc, NumericLiteral node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
    }
    
    @pausable
    public PythonValueSet evaluateValue(PythonDynamicContext dc, InfoKind infoKind) {
        NumericValue val = new IntegerValue(this.node().getIntValue());
        return new PythonValueSet(val);
    }
    
}
