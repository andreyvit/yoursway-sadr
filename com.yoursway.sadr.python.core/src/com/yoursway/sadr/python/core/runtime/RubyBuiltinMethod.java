package com.yoursway.sadr.python.core.runtime;

import static com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo.emptyValueInfo;

import com.yoursway.sadr.python.core.typeinferencing.constructs.dtl.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo;

public class RubyBuiltinMethod extends RubyMethod {
    
    private final String name;
    
    public RubyBuiltinMethod(RubyBasicClass klass, String name, RubyArgument... arguments) {
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
