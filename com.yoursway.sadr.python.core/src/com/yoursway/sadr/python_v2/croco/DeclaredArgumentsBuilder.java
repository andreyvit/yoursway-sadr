package com.yoursway.sadr.python_v2.croco;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yoursway.sadr.python.constructs.ArgumentC;

public class DeclaredArgumentsBuilder {
    
    private final List<ArgumentC> positional = new ArrayList<ArgumentC>();
    private final Map<String, ArgumentC> keyword = new HashMap<String, ArgumentC>();
    private ArgumentC star = null;
    private ArgumentC superstar = null;
    
    public void add(String name, ArgumentC value) {
        if (name == null)
            throw new NullPointerException("name is null");
        if (value == null)
            throw new NullPointerException("value is null");
        positional.add(value);
        keyword.put(name, value);
    }
    
    public void addStar(ArgumentC value) {
        if (value == null)
            throw new NullPointerException("value is null");
        star = value;
    }
    
    public void addSuperstar(ArgumentC value) {
        if (value == null)
            throw new NullPointerException("value is null");
        superstar = value;
    }
    
    public DeclaredArguments build() {
        return new DeclaredArguments(positional, keyword, star, superstar);
    }
    
    void addAll(List<ArgumentC> positional, Map<String, ArgumentC> keyword, ArgumentC star,
            ArgumentC superstar) {
        this.positional.addAll(positional);
        this.keyword.putAll(keyword);
        this.star = star;
        this.superstar = superstar;
    }
    
}
