package com.yoursway.sadr.ruby.core.typeinferencing.constructs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;

public abstract class AbstractConstruct implements IConstruct {
    
    private final StaticContext staticContext;
    
    public AbstractConstruct(StaticContext sc) {
        if (sc == null)
            throw new NullPointerException("AbstractConstruct(sc == null)");
        this.staticContext = sc;
    }
    
    public StaticContext staticContext() {
        return staticContext;
    }
    
    public abstract IConstruct staticallyEnclosingConstruct();
    
    public abstract IConstruct subconstructFor(ASTNode node);
    
    protected abstract IConstruct wrap(StaticContext sc, ASTNode node);
    
    protected List<IConstruct> wrap(StaticContext sc, List<ASTNode> nodes) {
        ArrayList<IConstruct> result = new ArrayList<IConstruct>(nodes.size());
        for (ASTNode node : nodes)
            result.add(wrap(sc, node));
        return result;
    }
    
}
