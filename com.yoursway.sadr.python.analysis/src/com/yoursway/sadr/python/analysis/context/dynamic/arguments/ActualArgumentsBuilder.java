package com.yoursway.sadr.python.analysis.context.dynamic.arguments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yoursway.sadr.python.analysis.context.dynamic.Arguments;
import com.yoursway.sadr.python.analysis.lang.unodes.Bnode;

public class ActualArgumentsBuilder {
    
    private final List<Bnode> positional = new ArrayList<Bnode>();
    private final Map<String, Bnode> keyword = new HashMap<String, Bnode>();
    private Bnode star = null;
    private Bnode superstar = null;
    
    public void addPositional(Bnode construct) {
        if (construct == null)
            throw new NullPointerException("construct is null");
        positional.add(construct);
    }
    
    public void addKeyword(String name, Bnode value) {
        if (name == null)
            throw new NullPointerException("name is null");
        if (value == null)
            throw new NullPointerException("value is null");
        keyword.put(name, value);
    }
    
    public void addStar(Bnode value) {
        if (value == null)
            throw new NullPointerException("value is null");
        star = value;
    }
    
    public void addSuperstar(Bnode value) {
        if (value == null)
            throw new NullPointerException("value is null");
        superstar = value;
    }
    
    public Arguments build() {
        return new ActualArguments(positional, keyword, star, superstar);
    }
    
    void addAll(List<Bnode> positional, Map<String, Bnode> keyword, Bnode star, Bnode superstar) {
        this.positional.addAll(positional);
        this.keyword.putAll(keyword);
        this.star = star;
        this.superstar = superstar;
    }
    
}
