package com.yoursway.sadr.python_v2.model.builtins;

import java.util.ArrayList;
import java.util.List;

import com.yoursway.sadr.python_v2.model.RuntimeObject;

public class ObjectType extends PythonObject implements PythonClass {
    
    public ObjectType() {
        super(Builtins.OBJECT);
        FunctionObject init = new FunctionObject("__init__") {
            @Override
            public RuntimeObject evaluate(List<RuntimeObject> args) {
                return null;
            }
        };
        setAttribute(init.name(), init);
    }
    
    public List<PythonClass> getSuperClasses() {
        return new ArrayList<PythonClass>(0);
    }
    
    public void setSuperClasses(List<PythonClass> supers) {
        throw new IllegalStateException("Bad action");
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
