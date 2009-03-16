package com.yoursway.sadr.python_v2.model.builtins.types;

import java.util.Collections;
import java.util.List;

import com.yoursway.sadr.python_v2.model.RuntimeArguments;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;
import com.yoursway.sadr.python_v2.model.builtins.values.InstanceValue;
import com.yoursway.sadr.python_v2.model.builtins.values.NoneValue;

public class ObjectType extends BuiltinType {
    
    public PythonObject __init__(RuntimeArguments args) {
        return NoneValue.instance;
    }
    
    public PythonObject __call__(RuntimeArguments args) {
        return new InstanceValue(instance);
    }
    
    public ObjectType() {
    }
    
    @Override
    public List<PythonType> getSuperClasses() {
        return Collections.emptyList();
    }
    
    public final static ObjectType instance = new ObjectType();
    
    @Override
    public String describe() {
        return "object";
    }
}
