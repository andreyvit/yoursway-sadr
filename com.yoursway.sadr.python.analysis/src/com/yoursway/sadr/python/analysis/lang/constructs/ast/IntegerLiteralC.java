package com.yoursway.sadr.python.analysis.lang.constructs.ast;

import kilim.pausable;

import org.eclipse.dltk.ast.expressions.NumericLiteral;

import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonStaticContext;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstructImpl;
import com.yoursway.sadr.python.analysis.lang.unodes.Unode;
import com.yoursway.sadr.python.analysis.lang.unodes.literals.ScalarLiteralUnode;
import com.yoursway.sadr.python.analysis.objectmodel.values.IntegerValue;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public class IntegerLiteralC extends PythonConstructImpl<NumericLiteral> {
    
    IntegerLiteralC(PythonStaticContext sc, NumericLiteral node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
    }
    
    @pausable
    public PythonValueSet evaluateValue(PythonDynamicContext dc, InfoKind infoKind) {
        return createValue();
    }
    
    private PythonValueSet createValue() {
        return new PythonValueSet(new IntegerValue(getValue()));
    }
    
    public long getValue() {
        return this.node().getIntValue();
    }
    
    @Override
    public Unode toUnode() {
        return new ScalarLiteralUnode(createValue());
    }
    
}
