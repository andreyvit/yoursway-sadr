package com.yoursway.sadr.python.analysis.objectmodel.values;

import java.util.Collection;
import java.util.Collections;

import kilim.pausable;

import com.yoursway.sadr.python.analysis.context.dynamic.arguments.DeclaredArguments;
import com.yoursway.sadr.python.analysis.lang.unodes.Bnode;

public final class LambdaObject extends LambdaOrFunctionObject {
    
    private final Bnode body;
    
    public LambdaObject(DeclaredArguments args, Bnode body) {
        super(args, body.lc());
        this.body = body;
    }
    
    @Override
    public String describe() {
        return "function <lambda>";
    }
    
    @Override
    public String name() {
        return "<lambda>";
    }
    
    @Override
    @pausable
    protected Collection<Bnode> findReturnedValues() {
        return Collections.singleton(body);
    }
    
}
