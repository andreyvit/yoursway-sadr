package com.yoursway.sadr.ruby.core.runtime;

import java.util.ArrayList;
import java.util.List;

public abstract class RubyProcedure implements Callable {
    
    private final String name;
    private final RubyArgument[] arguments;
    
    public RubyProcedure(RubyRuntimeModel model, String name, RubyArgument[] arguments) {
        this.name = name;
        this.arguments = arguments;
        for (int i = 0; i < arguments.length; i++)
            arguments[i].index = i;
        model.addProcedure(this);
    }
    
    public String name() {
        return name;
    }
    
    public String[] parameterNames() {
        List<String> res = new ArrayList<String>();
        for (RubyArgument argument : arguments)
            res.add(argument.name());
        return res.toArray(new String[res.size()]);
    }
    
    public RubyArgument[] arguments() {
        return arguments;
    }
    
    public boolean matches(String prefix) {
        return name.startsWith(prefix);
    }
    
    public String qualifiedName() {
        return name;
    }
    
}
