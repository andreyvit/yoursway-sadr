package com.yoursway.sadr.python_v2.model.builtins;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import com.yoursway.sadr.python_v2.model.RuntimeObject;

public class DictType extends PythonClassType {
    private DictType() {
        FunctionObject create = new FunctionObject("__call__") {
            @Override
            public RuntimeObject evaluate(List<RuntimeObject> args, HashMap<String, RuntimeObject> kwargs) {
                PythonObjectWithValue<DictValue> dictObject = DictType.newDictObject();
                HashMap<RuntimeObject, RuntimeObject> dict = dictObject.getValue().getDict();
                Set<Entry<String, RuntimeObject>> kwentries = kwargs.entrySet();
                for (Entry<String, RuntimeObject> entry : kwentries) {
                    RuntimeObject key = StringType.newStringObject(entry.getKey());
                    dict.put(key, entry.getValue());
                }
                return dictObject;
            }
        };
        setAttribute("__call__", create);
    }
    
    private static final DictType instance = new DictType();
    
    public static DictType instance() {
        return instance;
    }
    
    @Override
    public String describe() {
        return "dict";
    }
    
    public static PythonObjectWithValue<DictValue> newDictObject() {
        return new PythonObjectWithValue<DictValue>(instance(), new DictValue());
    }
    
}
