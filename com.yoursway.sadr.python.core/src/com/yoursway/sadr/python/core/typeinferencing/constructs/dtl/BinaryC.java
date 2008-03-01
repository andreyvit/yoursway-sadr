package com.yoursway.sadr.python.core.typeinferencing.constructs.dtl;

import org.eclipse.dltk.ruby.ast.RubyBinaryExpression;

import com.yoursway.sadr.python.core.typeinferencing.constructs.StaticContext;

public abstract class BinaryC extends PythonConstruct<RubyBinaryExpression> {
    
    BinaryC(StaticContext sc, RubyBinaryExpression node) {
        super(sc, node);
    }
    
}
