package com.yoursway.sadr.core.constructs;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractConstruct<C extends IConstruct<?, SC, DC, N>, SC extends StaticContext<C, SC, DC, N>, DC extends DynamicContext, N>
        implements IConstruct<C, SC, DC, N> {
    
    private final SC dtlStaticContext;
    
    public AbstractConstruct(SC sc) {
        this.dtlStaticContext = sc;
    }
    
    public SC staticContext() {
        return dtlStaticContext;
    }
    
    protected abstract C wrap(SC sc, N node);
    
    protected List<C> wrap(SC sc, List<N> nodes) {
        ArrayList<C> result = new ArrayList<C>(nodes.size());
        for (N node : nodes)
            result.add(wrap(sc, node));
        return result;
    }
    
}
