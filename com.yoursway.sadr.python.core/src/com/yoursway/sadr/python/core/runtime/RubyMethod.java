package com.yoursway.sadr.python.core.runtime;

import java.util.ArrayList;
import java.util.List;

public abstract class RubyMethod implements Callable {
    
    private final RubyBasicClass klass;
    private final RubyArgument[] arguments;
    
    public RubyMethod(RubyBasicClass klass, String name, RubyArgument[] arguments) {
        this.klass = klass;
        this.arguments = arguments;
        for (int i = 0; i < arguments.length; i++)
            arguments[i].index = i;
        klass.addMethod(this, name);
    }
    
    public RubyBasicClass klass() {
        return klass;
    }
    
    public abstract String name();
    
    public String[] parameterNames() {
        List<String> res = new ArrayList<String>();
        for (RubyArgument argument : arguments)
            res.add(argument.name());
        return res.toArray(new String[res.size()]);
    }
    
    public RubyArgument[] arguments() {
        return arguments;
    }
    
    public boolean canBeCalledFrom(RubyBasicClass receiver) {
        RubyClass klass = runtimeClass();
        if (receiver instanceof RubyMetaClass)
            if (!isStatic())
                return false;
            else
                return (((RubyMetaClass) receiver).instanceClass().isDerivedFrom(klass));
        else
            return ((RubyClass) receiver).isDerivedFrom(klass);
        
    }
    
    public RubyClass runtimeClass() {
        if (isStatic())
            return ((RubyMetaClass) klass).instanceClass();
        else
            return (RubyClass) klass;
    }
    
    public boolean isStatic() {
        return klass instanceof RubyMetaClass;
    }
    
    public String qualifiedName() {
        return runtimeClass().name() + (isStatic() ? "::" : ":") + name();
    }
    
}
