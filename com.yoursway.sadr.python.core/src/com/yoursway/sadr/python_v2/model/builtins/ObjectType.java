package com.yoursway.sadr.python_v2.model.builtins;

import com.yoursway.sadr.python.core.typeinferencing.values.InstanceRegistrarImpl;
import com.yoursway.sadr.python.core.typeinferencing.values.InstanceValue;
import com.yoursway.sadr.python_v2.model.PythonArguments;
import com.yoursway.sadr.python_v2.model.RuntimeObject;

public class ObjectType extends PythonClassType {
    final InstanceRegistrarImpl registrar = new InstanceRegistrarImpl();
    
    public RuntimeObject __init__(PythonArguments args) {
        return Builtins.createNone();
    }
    
    public RuntimeObject __call__(PythonArguments args) {
        return new PythonValue<InstanceValue>(ObjectType.instance(), new InstanceValue(ObjectType.instance(),
                registrar));
    }
    
    public ObjectType() {
        setSuperClasses(null);
    }
    
    private static ObjectType instance = new ObjectType();
    
    static ObjectType instance() {
        return instance;
    }
    
    @Override
    public String describe() {
        return "object";
    }
}
