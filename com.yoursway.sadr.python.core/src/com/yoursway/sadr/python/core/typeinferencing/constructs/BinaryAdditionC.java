package com.yoursway.sadr.python.core.typeinferencing.constructs;

import org.eclipse.dltk.python.parser.ast.expressions.BinaryExpression;

import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

public class BinaryAdditionC extends BinaryC {
    
    BinaryAdditionC(Scope sc, BinaryExpression node) {
        super(sc, node);
    }
    
}
