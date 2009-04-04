package com.yoursway.sadr.python.analysis.context.dynamic.arguments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yoursway.sadr.python.analysis.context.dynamic.Arguments;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstruct;

public class ActualArgumentsBuilder {
    
    private final List<PythonConstruct> positional = new ArrayList<PythonConstruct>();
    private final Map<String, PythonConstruct> keyword = new HashMap<String, PythonConstruct>();
    private PythonConstruct star = null;
    private PythonConstruct superstar = null;
    
    public void addPositional(PythonConstruct construct) {
        if (construct == null)
            throw new NullPointerException("construct is null");
        positional.add(construct);
    }
    
    public void addKeyword(String name, PythonConstruct value) {
        if (name == null)
            throw new NullPointerException("name is null");
        if (value == null)
            throw new NullPointerException("value is null");
        keyword.put(name, value);
    }
    
    public void addStar(PythonConstruct value) {
        if (value == null)
            throw new NullPointerException("value is null");
        star = value;
    }
    
    public void addSuperstar(PythonConstruct value) {
        if (value == null)
            throw new NullPointerException("value is null");
        superstar = value;
    }
    
    public Arguments build() {
        return new ActualArguments(positional, keyword, star, superstar);
    }
    
    void addAll(List<PythonConstruct> positional, Map<String, PythonConstruct> keyword, PythonConstruct star,
            PythonConstruct superstar) {
        this.positional.addAll(positional);
        this.keyword.putAll(keyword);
        this.star = star;
        this.superstar = superstar;
    }
    
}
