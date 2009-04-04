package com.yoursway.sadr.python.analysis.lang.constructs.ast;

import org.eclipse.dltk.ast.expressions.BigNumericLiteral;

import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstructImpl;
import com.yoursway.sadr.python.analysis.lang.unodes.Unode;
import com.yoursway.sadr.python.analysis.lang.unodes.literals.ScalarLiteralUnode;
import com.yoursway.sadr.python.analysis.objectmodel.types.LongType;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public class BigIntegerLiteralC extends PythonConstructImpl<BigNumericLiteral> {
    
    BigIntegerLiteralC(PythonLexicalContext sc, BigNumericLiteral literal, PythonConstructImpl<?> parent) {
        super(sc, literal, parent);
    }
    
    @Override
    public Unode toUnode() {
        return new ScalarLiteralUnode(new PythonValueSet(LongType.wrap(this)));
    }
    
}
