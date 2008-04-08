package com.yoursway.sadr.ruby.core.typeinferencing.constructs.dtl;

import org.eclipse.dltk.ruby.ast.RubyBinaryExpression;

import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyStaticContext;

public abstract class BinaryC extends RubyConstructImpl<RubyBinaryExpression> {
    
    BinaryC(RubyStaticContext sc, RubyBinaryExpression node) {
        super(sc, node);
    }
    
}
