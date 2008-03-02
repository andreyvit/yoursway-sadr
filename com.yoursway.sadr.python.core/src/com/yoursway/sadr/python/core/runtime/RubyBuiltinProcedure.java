package com.yoursway.sadr.python.core.runtime;

import static com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo.emptyValueInfo;

import com.yoursway.sadr.python.core.typeinferencing.constructs.dtl.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo;

public class RubyBuiltinProcedure extends RubyProcedure {
    
    public RubyBuiltinProcedure(RubyRuntimeModel model, String name, RubyArgument... arguments) {
        super(model, name, arguments);
    }
    
    public PythonConstruct construct() {
        throw new UnsupportedOperationException();
    }
    
    public ValueInfo evaluateBuiltin(ValueInfo[] arguments) {
        return emptyValueInfo();
    }
    
    public boolean isBuiltin() {
        return true;
    }
    
}
