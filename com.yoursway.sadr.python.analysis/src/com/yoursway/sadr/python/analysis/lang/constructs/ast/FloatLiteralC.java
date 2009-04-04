package com.yoursway.sadr.python.analysis.lang.constructs.ast;

import kilim.pausable;

import org.eclipse.dltk.ast.expressions.FloatNumericLiteral;

import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstructImpl;
import com.yoursway.sadr.python.analysis.objectmodel.types.FloatType;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public class FloatLiteralC extends PythonConstructImpl<FloatNumericLiteral> {
    
    FloatLiteralC(PythonLexicalContext sc, FloatNumericLiteral node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
    }
    
    @pausable
    public PythonValueSet evaluateValue(PythonDynamicContext dc) {
        return new PythonValueSet(FloatType.wrap(this));
    }
    
}
