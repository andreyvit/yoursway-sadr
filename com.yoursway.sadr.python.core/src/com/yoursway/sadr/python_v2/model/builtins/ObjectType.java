package com.yoursway.sadr.python_v2.model.builtins;


import com.yoursway.sadr.python_v2.model.PythonArguments;
import com.yoursway.sadr.python_v2.model.RuntimeObject;

public class ObjectType extends PythonClassType {
    public ObjectType() {
        setSuperClasses(null);
        FunctionObject init = new FunctionObject("__init__") {
            @Override
            public RuntimeObject evaluate(PythonArguments args) {
                return Builtins.createNone();
            }
        };
        setAttribute(init.name(), init);
        FunctionObject call = new FunctionObject("__call__") {
            @Override
            public RuntimeObject evaluate(PythonArguments args) {
                return new PythonObjectWithValue<ObjectType>(Builtins.OBJECT, null);
            }
        };
        setAttribute(call.name(), call);
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
