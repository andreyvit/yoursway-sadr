package com.yoursway.sadr.python.analysis.lang.constructs.ast;

import org.eclipse.dltk.ast.expressions.NumericLiteral;

import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstructImpl;
import com.yoursway.sadr.python.analysis.lang.unodes.Unode;
import com.yoursway.sadr.python.analysis.lang.unodes.literals.ScalarLiteralUnode;
import com.yoursway.sadr.python.analysis.objectmodel.values.IntegerValue;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public class IntegerLiteralC extends PythonConstructImpl<NumericLiteral> {
    
    IntegerLiteralC(PythonLexicalContext sc, NumericLiteral node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
    }
    
    public long getValue() {
        return this.node().getIntValue();
    }
    
    @Override
    public Unode toUnode() {
        return new ScalarLiteralUnode(new PythonValueSet(new IntegerValue(getValue())));
    }
    
}
