package com.yoursway.sadr.python_v2.model.builtins;

import java.util.HashMap;
import java.util.List;

import com.yoursway.sadr.blocks.integer_literals.IntegerValue;
import com.yoursway.sadr.python_v2.model.RuntimeObject;

public class TupleType extends PythonClassType {
    private TupleType() {
        setAttribute(new FunctionObject("__getitem__") {
            @Override
            @SuppressWarnings("unchecked")
            public RuntimeObject evaluate(List<RuntimeObject> args, HashMap<String, RuntimeObject> kwargs) {
                TupleValue array = ((PythonObjectWithValue<TupleValue>) args.get(0)).getValue();
                IntegerValue index = ((PythonObjectWithValue<IntegerValue>) args.get(1)).getValue();
                return array.getList().get((int) index.value());
            }
        });
    }
    
    private static final TupleType instance = new TupleType();
    
    public static TupleType instance() {
        return instance;
    }
    
    @Override
    public String describe() {
        return "tuple";
    }
    
    public static PythonObjectWithValue<TupleValue> newTupleObject(List<RuntimeObject> list) {
        return new PythonObjectWithValue<TupleValue>(instance(), new TupleValue(list));
    }
    
}
