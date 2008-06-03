package com.yoursway.sadr.python_v2.model.builtins;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python_v2.model.PythonArguments;
import com.yoursway.sadr.python_v2.model.PythonArgumentsReader;

public class DictType extends PythonClassType {
    public RuntimeObject __call__(PythonArguments args) {
        PythonArgumentsReader reader = new PythonArgumentsReader(args);
        HashMap<String, RuntimeObject> kwargs = reader.lastKwargs();
        return DictType.wrapStrDict(kwargs);
    }
    
    public RuntimeObject __getitem__(PythonArguments args) {
        List<RuntimeObject> list = args.readArgs(2);
        DictValue array = list.get(0).convertValue(DictType.instance());
        PythonValue<?> value = ((PythonValue<?>) list.get(1));
        return array.getDict().get(value);
    }
    
    public RuntimeObject _len(PythonArguments args) {
        DictValue array = args.castSingle(DictType.instance());
        return IntegerType.wrap(array.getDict().size());
    }
    
    private DictType() {
    }
    
    private static final DictType instance = new DictType();
    
    public static DictType instance() {
        return instance;
    }
    
    @Override
    public String describe() {
        return "dict";
    }
    
    public static PythonValue<DictValue> wrap() {
        return new PythonValue<DictValue>(instance(), new DictValue());
    }
    
    public static PythonValue<DictValue> wrap(HashMap<RuntimeObject, RuntimeObject> dict) {
        return new PythonValue<DictValue>(instance(), new DictValue(dict));
    }
    
    public static PythonValue<DictValue> wrapStrDict(HashMap<String, RuntimeObject> strDict) {
        PythonValue<DictValue> dict = new PythonValue<DictValue>(instance(), new DictValue());
        HashMap<RuntimeObject, RuntimeObject> hashMap = dict.getValue().getDict();
        for (Entry<String, RuntimeObject> entry : strDict.entrySet()) {
            RuntimeObject key = StringType.wrap(entry.getKey());
            hashMap.put(key, entry.getValue());
        }
        return dict;
    }
}
