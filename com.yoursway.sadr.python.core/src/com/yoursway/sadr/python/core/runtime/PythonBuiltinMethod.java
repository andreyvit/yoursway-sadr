package com.yoursway.sadr.python.core.runtime;

import static com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo.emptyValueInfo;

import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo;

public class PythonBuiltinMethod extends PythonMethod {
    
    private final String name;
    
    public PythonBuiltinMethod(PythonBasicClass klass, String name, PythonCallableArgument... arguments) {
        super(klass, name, arguments);
        this.name = name;
    }
    
    @Override
    public String name() {
        return name;
    }
    
    public PythonConstruct construct() {
        throw new UnsupportedOperationException();
    }
    
    public ValueInfo evaluateBuiltin(ValueInfo receiver, ValueInfo[] arguments) {
        return emptyValueInfo();
    }
    
    public boolean isBuiltin() {
        return true;
    }
    
}
