package com.yoursway.sadr.python.analysis.lang.constructs.ast;

import org.eclipse.dltk.ast.expressions.FloatNumericLiteral;

import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstructImpl;
import com.yoursway.sadr.python.analysis.lang.unodes.Unode;
import com.yoursway.sadr.python.analysis.lang.unodes.literals.ScalarLiteralUnode;
import com.yoursway.sadr.python.analysis.objectmodel.types.FloatType;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public class FloatLiteralC extends PythonConstructImpl<FloatNumericLiteral> {
    
    FloatLiteralC(PythonLexicalContext sc, FloatNumericLiteral node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
    }
    
    @Override
    public Unode toUnode() {
        return new ScalarLiteralUnode(new PythonValueSet(FloatType.wrap(this)));
    }
    
}
