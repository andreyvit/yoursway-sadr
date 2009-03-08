package com.yoursway.sadr.python_v2.constructs;

import org.eclipse.dltk.python.parser.ast.expressions.BinaryExpression;


public class BinaryAssignmentC extends BinaryC {
    
    //    private final String opName;
    //    
    BinaryAssignmentC(Scope sc, BinaryExpression node) {
        super(sc, node);
        //        this.opName = ibinoplist.get(node.getOperator());
    }
}
