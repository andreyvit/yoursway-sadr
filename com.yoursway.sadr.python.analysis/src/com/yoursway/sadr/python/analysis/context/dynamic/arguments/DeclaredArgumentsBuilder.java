package com.yoursway.sadr.python.analysis.context.dynamic.arguments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DeclaredArgumentsBuilder {
    
    private final List<Argument> positional = new ArrayList<Argument>();
    private final Map<String, Argument> keyword = new HashMap<String, Argument>();
    private Argument star = null;
    private Argument superstar = null;
    
    public void add(String name, Argument value) {
        if (name == null)
            throw new NullPointerException("name is null");
        if (value == null)
            throw new NullPointerException("value is null");
        positional.add(value);
        keyword.put(name, value);
    }
    
    public void addStar(Argument value) {
        if (value == null)
            throw new NullPointerException("value is null");
        star = value;
    }
    
    public void addSuperstar(Argument value) {
        if (value == null)
            throw new NullPointerException("value is null");
        superstar = value;
    }
    
    public DeclaredArguments build() {
        return new DeclaredArguments(positional, keyword, star, superstar);
    }
    
    void addAll(List<Argument> positional, Map<String, Argument> keyword, Argument star,
            Argument superstar) {
        this.positional.addAll(positional);
        this.keyword.putAll(keyword);
        this.star = star;
        this.superstar = superstar;
    }
    
}
