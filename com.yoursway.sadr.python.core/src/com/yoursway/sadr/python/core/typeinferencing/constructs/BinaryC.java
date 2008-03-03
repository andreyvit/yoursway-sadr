package com.yoursway.sadr.python.core.typeinferencing.constructs;

import org.eclipse.dltk.python.parser.ast.expressions.BinaryExpression;


public abstract class BinaryC extends PythonConstructImpl<BinaryExpression> {
    
    BinaryC(PythonStaticContext sc, BinaryExpression node) {
        super(sc, node);
    }
    
}
