package com.yoursway.sadr.python.model.types;

import java.util.Collections;
import java.util.List;

import com.yoursway.sadr.python.model.values.InstanceValue;
import com.yoursway.sadr.python.model.values.NoneValue;
import com.yoursway.sadr.python.objects.RuntimeArguments;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;

public class ObjectType extends BuiltinType {
    
    public PythonValue __init__(RuntimeArguments args) {
        return NoneValue.instance;
    }
    
    public PythonValue __call__(RuntimeArguments args) {
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
