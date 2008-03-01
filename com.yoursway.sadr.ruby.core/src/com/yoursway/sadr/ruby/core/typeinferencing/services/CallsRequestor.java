package com.yoursway.sadr.ruby.core.typeinferencing.services;

import org.eclipse.dltk.ast.expressions.CallExpression;

import com.yoursway.sadr.ruby.core.typeinferencing.scopes.FileScope;

public interface CallsRequestor {
    
    void call(CallExpression call, FileScope fileScope);
    
}
