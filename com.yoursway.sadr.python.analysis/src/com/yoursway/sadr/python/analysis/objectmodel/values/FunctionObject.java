package com.yoursway.sadr.python.analysis.objectmodel.values;

import java.util.ArrayList;
import java.util.Collection;

import kilim.pausable;

import com.yoursway.sadr.engine.Analysis;
import com.yoursway.sadr.python.analysis.context.dynamic.arguments.DeclaredArguments;
import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.index.queries.ReturnsIndexQuery;
import com.yoursway.sadr.python.analysis.index.queries.ReturnsRequestor;
import com.yoursway.sadr.python.analysis.lang.unodes.Bnode;

public final class FunctionObject extends LambdaOrFunctionObject {
    
    private final String name;
    
    public FunctionObject(String name, DeclaredArguments args, PythonLexicalContext inner) {
        super(args, inner);
        if (name == null)
            throw new NullPointerException("name is null");
        this.name = name;
    }
    
    @Override
    public String describe() {
        return "function " + name;
    }
    
    @Override
    public String name() {
        return name;
    }
    
    @Override
    @pausable
    protected Collection<Bnode> findReturnedValues() {
        final Collection<Bnode> result = new ArrayList<Bnode>();
        Analysis.queryIndex(new ReturnsIndexQuery(lc.getArea().getReturnableArea(), lc.getScope()
                .getFileScope().getSourceUnit()), new ReturnsRequestor() {
            public void returnedValue(Bnode value) {
                result.add(value);
            }
        });
        return result;
    }
    
}
