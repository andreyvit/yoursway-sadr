package com.yoursway.sadr.python.core.typeinferencing.goals;

import org.eclipse.dltk.ast.expressions.CallExpression;

import com.yoursway.sadr.engine.Result;
import com.yoursway.sadr.python.core.typeinferencing.engine.Construct;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

public class CallersInfo implements Result {
    
    private final Construct<Scope, CallExpression>[] callers;
    
    public CallersInfo(Construct<Scope, CallExpression>[] callers) {
        this.callers = callers;
    }
    
    public Construct<Scope, CallExpression>[] callers() {
        return callers;
    }
    
}
