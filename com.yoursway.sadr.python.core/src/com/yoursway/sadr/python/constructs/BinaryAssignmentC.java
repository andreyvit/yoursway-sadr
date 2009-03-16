package com.yoursway.sadr.python.constructs;

import org.eclipse.dltk.python.parser.ast.expressions.BinaryExpression;

public class BinaryAssignmentC extends BinaryC {
    
    //    private final String opName;
    //    
    BinaryAssignmentC(PythonStaticContext sc, BinaryExpression node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
        //        this.opName = ibinoplist.get(node.getOperator());
    }
}
