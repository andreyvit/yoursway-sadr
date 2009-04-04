package com.yoursway.sadr.python.analysis.context.dynamic.arguments;

import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.index.IndexRequest;
import com.yoursway.sadr.python.analysis.lang.unodes.Bnode;
import com.yoursway.sadr.python.analysis.lang.unodes.indexable.VariableUnode;
import com.yoursway.sadr.python.analysis.lang.unodes.proxies.ArgumentUnode;

public class Argument {
    
    private final String name;
    private final int star;
    private final Bnode init;
    private final int index;
    
    public Argument(String name, int index, int star, Bnode init) {
        this.name = name;
        this.index = index;
        this.star = star;
        this.init = init;
    }
    
    public String getName() {
        return name;
    }
    
    public void addTo(DeclaredArgumentsBuilder builder) {
        if (star == 0)
            builder.add(name, this);
        else if (star == 1)
            builder.addStar(this);
        else if (star == 2)
            builder.addStar(this);
        else
            throw new AssertionError("Unreachable");
    }
    
    public void addToIndex(IndexRequest r, PythonLexicalContext inner) {
        r.addAssignmentWithoutWrapping(new VariableUnode(name), inner, new Bnode(new ArgumentUnode(index,
                name, init), inner));
    }
    
}
