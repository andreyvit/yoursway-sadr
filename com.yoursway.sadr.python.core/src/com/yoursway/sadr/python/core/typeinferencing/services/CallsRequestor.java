package com.yoursway.sadr.python.core.typeinferencing.services;

import org.eclipse.dltk.ast.expressions.CallExpression;

import com.yoursway.sadr.python.core.typeinferencing.scopes.FileScope;

public interface CallsRequestor {
    
    void call(CallExpression call, FileScope fileScope);
    
}
