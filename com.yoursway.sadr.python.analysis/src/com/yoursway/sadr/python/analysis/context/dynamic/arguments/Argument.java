package com.yoursway.sadr.python.analysis.context.dynamic.arguments;

import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.index.IndexRequest;
import com.yoursway.sadr.python.analysis.lang.unodes.Bnode;
import com.yoursway.sadr.python.analysis.lang.unodes.indexable.VariableUnode;
import com.yoursway.sadr.python.analysis.lang.unodes.proxies.ArgumentUnode;

public class Argument {
    
    private final String name;
    private final Starness starness;
    private final Bnode init;
    private final int index;
    
    public Argument(String name, int index, Starness starness, Bnode init) {
        this.name = name;
        this.index = index;
        this.starness = starness;
        this.init = init;
    }
    
    public String getName() {
        return name;
    }
    
    public void addTo(DeclaredArgumentsBuilder builder) {
        if (starness == Starness.REGULAR)
            builder.add(name, this);
        else if (starness == Starness.STAR)
            builder.addStar(this);
        else if (starness == Starness.DOUBLE_STAR)
            builder.addSuperstar(this);
        else
            throw new AssertionError("Unreachable");
    }
    
    public void addToIndex(IndexRequest r, PythonLexicalContext inner) {
        r.addAssignmentWithoutWrapping(new VariableUnode(name), inner, new Bnode(new ArgumentUnode(index,
                name, init, starness), inner));
    }
    
}
