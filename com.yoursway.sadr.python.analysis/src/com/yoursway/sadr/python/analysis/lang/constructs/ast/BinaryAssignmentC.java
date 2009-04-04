package com.yoursway.sadr.python.analysis.lang.constructs.ast;

import org.eclipse.dltk.python.parser.ast.expressions.BinaryExpression;

import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstructImpl;

public class BinaryAssignmentC extends BinaryC {
    
    //    private final String opName;
    //    
    BinaryAssignmentC(PythonLexicalContext sc, BinaryExpression node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
        //        this.opName = ibinoplist.get(node.getOperator());
    }
}
