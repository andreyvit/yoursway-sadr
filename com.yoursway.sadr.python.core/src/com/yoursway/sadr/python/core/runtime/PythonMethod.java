package com.yoursway.sadr.python.core.runtime;

import java.util.ArrayList;
import java.util.List;

public abstract class PythonMethod implements Callable {
    
    private final PythonBasicClass klass;
    private final PythonCallableArgument[] arguments;
    
    public PythonMethod(PythonBasicClass klass, String name, PythonCallableArgument[] arguments) {
        this.klass = klass;
        this.arguments = arguments;
        for (int i = 0; i < arguments.length; i++)
            arguments[i].index = i;
        klass.addMethod(this, name);
    }
    
    public PythonBasicClass klass() {
        return klass;
    }
    
    public abstract String name();
    
    public String[] parameterNames() {
        List<String> res = new ArrayList<String>();
        for (PythonCallableArgument argument : arguments)
            res.add(argument.name());
        return res.toArray(new String[res.size()]);
    }
    
    public PythonCallableArgument[] arguments() {
        return arguments;
    }
    
    public boolean canBeCalledFrom(PythonBasicClass receiver) {
        PythonMetaType klass = runtimeClass();
        if (receiver instanceof PythonMetaClass)
            if (!isStatic())
                return false;
            else
                return (((PythonMetaClass) receiver).instanceClass().isDerivedFrom(klass));
        else
            return ((PythonMetaType) receiver).isDerivedFrom(klass);
        
    }
    
    public PythonMetaType runtimeClass() {
        if (isStatic())
            return ((PythonMetaClass) klass).instanceClass();
        else
            return (PythonMetaType) klass;
    }
    
    public boolean isStatic() {
        return klass instanceof PythonMetaClass;
    }
    
    public String qualifiedName() {
        return runtimeClass().name() + (isStatic() ? "::" : ":") + name();
    }
    
}
