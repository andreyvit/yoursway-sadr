package com.yoursway.sadr.python_v2.model.builtins;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.model.PythonArguments;

public class SyncFunctionObject extends FunctionObject {
    public SyncFunctionObject(String name) {
        super(name);
    }
    
    @Override
    public PythonValueSet call(Krocodile crocodile, PythonArguments args) {
        return new PythonValueSet(evaluate(args), crocodile);
    }
    
    public RuntimeObject evaluate(PythonArguments args) {
        throw new NotImplementedException();
    }
    
    @Override
    public String describe() {
        return "Function " + name();
    }
}
