package com.yoursway.sadr.python_v2.model.builtins;

import java.util.HashMap;
import java.util.Map.Entry;

import com.yoursway.sadr.python_v2.model.PythonArguments;
import com.yoursway.sadr.python_v2.model.PythonArgumentsReader;
import com.yoursway.sadr.python_v2.model.RuntimeObject;

public class DictType extends PythonClassType {
    private DictType() {
        setAttribute(new FunctionObject("__call__") {
            @Override
            public RuntimeObject evaluate(PythonArguments args) {
                PythonArgumentsReader reader = new PythonArgumentsReader(args);
                HashMap<String, RuntimeObject> kwargs = reader.lastKwargs();
                return DictType.wrapStrDict(kwargs);
            }
        });
    }
    
    private static final DictType instance = new DictType();
    
    public static DictType instance() {
        return instance;
    }
    
    @Override
    public String describe() {
        return "dict";
    }
    
    public static PythonObjectWithValue<DictValue> wrap() {
        return new PythonObjectWithValue<DictValue>(instance(), new DictValue());
    }
    
    public static PythonObjectWithValue<DictValue> wrap(HashMap<RuntimeObject, RuntimeObject> dict) {
        return new PythonObjectWithValue<DictValue>(instance(), new DictValue(dict));
    }
    
    public static PythonObjectWithValue<DictValue> wrapStrDict(HashMap<String, RuntimeObject> strDict) {
        PythonObjectWithValue<DictValue> dict = new PythonObjectWithValue<DictValue>(instance(),
                new DictValue());
        HashMap<RuntimeObject, RuntimeObject> hashMap = dict.getValue().getDict();
        for (Entry<String, RuntimeObject> entry : strDict.entrySet()) {
            RuntimeObject key = StringType.wrap(entry.getKey());
            hashMap.put(key, entry.getValue());
        }
        return dict;
    }
}
