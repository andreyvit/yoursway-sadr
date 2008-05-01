package com.yoursway.sadr.python.core.typeinferencing.constructs;

import org.eclipse.dltk.python.parser.ast.expressions.BinaryExpression;

import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

public abstract class BinaryC extends PythonConstructImpl<BinaryExpression> {
    
    BinaryC(Scope sc, BinaryExpression node) {
        super(sc, node);
    }
    
}
