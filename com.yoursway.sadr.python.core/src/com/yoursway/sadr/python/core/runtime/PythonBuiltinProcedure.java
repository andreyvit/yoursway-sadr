package com.yoursway.sadr.python.core.runtime;

import static com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo.emptyValueInfo;

import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;

public class PythonBuiltinProcedure extends PythonProcedure {
    
    public PythonBuiltinProcedure(PythonRuntimeModel model, String name, PythonCallableArgument... arguments) {
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
