package com.yoursway.sadr.python.analysis.context.dynamic.arguments;

import com.yoursway.sadr.python.analysis.index.IndexRequest;
import com.yoursway.sadr.python.analysis.index.data.PassedKeywordArgumentInfo;
import com.yoursway.sadr.python.analysis.index.data.PassedPositionalArgumentInfo;
import com.yoursway.sadr.python.analysis.lang.unodes.Bnode;

public class CallArgument {
    
    private final Bnode value;
    private final String name;
    private final int stars;
    
    public CallArgument(Bnode value, String name, int stars) {
        if (value == null)
            throw new NullPointerException("value is null");
        this.value = value;
        this.name = name;
        this.stars = stars;
    }
    
    public void addTo(ActualArgumentsBuilder builder) {
        if (name != null)
            builder.addKeyword(name, value);
        else if (stars == 1)
            builder.addStar(value);
        else if (stars == 2)
            builder.addSuperstar(value);
        else
            builder.addPositional(value);
    }
    
    public void actOnIndex(IndexRequest r, Bnode callable, int index) {
        if (stars > 0)
            return;
        if (name != null)
            r.addPassedArgument(value.unode(), new PassedKeywordArgumentInfo(callable, name));
        else
            r.addPassedArgument(value.unode(), new PassedPositionalArgumentInfo(callable, index));
    }
    
}
