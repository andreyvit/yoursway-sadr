package com.yoursway.sadr.python.analysis.lang.constructs.ast;

import kilim.pausable;

import org.eclipse.dltk.ast.expressions.BigNumericLiteral;

import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonStaticContext;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstructImpl;
import com.yoursway.sadr.python.analysis.objectmodel.types.LongType;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public class BigIntegerLiteralC extends PythonConstructImpl<BigNumericLiteral> {
    
    BigIntegerLiteralC(PythonStaticContext sc, BigNumericLiteral literal, PythonConstructImpl<?> parent) {
        super(sc, literal, parent);
    }
    
    @pausable
    public PythonValueSet evaluateValue(PythonDynamicContext dc, InfoKind infoKind) {
        return new PythonValueSet(LongType.wrap(this));
    }
    
}
