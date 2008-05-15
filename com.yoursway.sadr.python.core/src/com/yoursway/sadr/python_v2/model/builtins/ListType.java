package com.yoursway.sadr.python_v2.model.builtins;

import java.util.HashMap;
import java.util.List;

import com.yoursway.sadr.python_v2.model.RuntimeObject;

public class ListType extends PythonClassType {
    private ListType() {
        FunctionObject append = new FunctionObject("append") {
            @Override
            public RuntimeObject evaluate(List<RuntimeObject> args, HashMap<String, RuntimeObject> kwargs) {
                assert args.size() == 2;
                //TODO
                return Builtins.NONE;
            }
        };
        setAttribute("append", append);
    }
    
    private static final ListType instance = new ListType();
    
    public static ListType instance() {
        return instance;
    }
    
    @Override
    public String describe() {
        return "list";
    }
    
    public static PythonObjectWithValue<ListValue> newListObject(List<RuntimeObject> list) {
        return new PythonObjectWithValue<ListValue>(instance(), new ListValue(list));
    }
    
}
