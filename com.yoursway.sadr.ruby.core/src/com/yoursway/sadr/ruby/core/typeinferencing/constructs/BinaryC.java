package com.yoursway.sadr.ruby.core.typeinferencing.constructs;

import org.eclipse.dltk.ruby.ast.RubyBinaryExpression;


public abstract class BinaryC extends RubyConstructImpl<RubyBinaryExpression> {
    
    BinaryC(RubyStaticContext sc, RubyBinaryExpression node) {
        super(sc, node);
    }
    
}
