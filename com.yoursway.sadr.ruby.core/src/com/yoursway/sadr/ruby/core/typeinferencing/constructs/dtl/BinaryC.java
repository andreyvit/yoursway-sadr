package com.yoursway.sadr.ruby.core.typeinferencing.constructs.dtl;

import org.eclipse.dltk.ruby.ast.RubyBinaryExpression;

import com.yoursway.sadr.ruby.core.typeinferencing.constructs.StaticContext;

public abstract class BinaryC extends DtlConstruct<RubyBinaryExpression> {
    
    BinaryC(StaticContext sc, RubyBinaryExpression node) {
        super(sc, node);
    }
    
}
