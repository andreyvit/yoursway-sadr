package com.yoursway.sadr.python.constructs;

import kilim.pausable;

import org.eclipse.dltk.python.parser.ast.expressions.ComplexNumericLiteral;

import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.model.types.ComplexType;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public class ComplexLiteralC extends PythonConstructImpl<ComplexNumericLiteral> {
    
    ComplexLiteralC(PythonStaticContext sc, ComplexNumericLiteral node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
    }
    
    @pausable
    public PythonValueSet evaluateValue(PythonDynamicContext dc, InfoKind infoKind) {
        return new PythonValueSet(ComplexType.wrap(this));
    }
    
}
