package com.yoursway.sadr.python.analysis.lang.constructs.ast;

import org.eclipse.dltk.python.parser.ast.expressions.ComplexNumericLiteral;

import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstructImpl;
import com.yoursway.sadr.python.analysis.lang.unodes.Unode;
import com.yoursway.sadr.python.analysis.lang.unodes.literals.ScalarLiteralUnode;
import com.yoursway.sadr.python.analysis.objectmodel.types.ComplexType;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public class ComplexLiteralC extends PythonConstructImpl<ComplexNumericLiteral> {
    
    ComplexLiteralC(PythonLexicalContext sc, ComplexNumericLiteral node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
    }
    
    @Override
    public Unode toUnode() {
        return new ScalarLiteralUnode(new PythonValueSet(ComplexType.wrap(this)));
    }
    
}
