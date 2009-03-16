package com.yoursway.sadr.python_v2.model.builtins.types;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.yoursway.sadr.python_v2.model.PythonArgumentsReader;
import com.yoursway.sadr.python_v2.model.RuntimeArguments;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;
import com.yoursway.sadr.python_v2.model.builtins.values.DictValue;

public class DictType extends BuiltinType {
    public PythonObject __call__(RuntimeArguments args) throws PythonException {
        PythonArgumentsReader reader = new PythonArgumentsReader(args);
        HashMap<String, PythonObject> kwargs = reader.lastKwargs();
        return DictType.wrapStrDict(kwargs);
    }
    
    public PythonObject __getitem__(RuntimeArguments args) throws PythonException {
        List<PythonObject> list = args.readArgs(2);
        DictValue array = DictType.instance.coerce(list.get(0));
        PythonValue value = ((PythonValue) list.get(1));
        return array.getDict().get(value);
    }
    
    @Override
    public DictValue coerce(PythonObject object) throws PythonException {
        throw new CoersionFailed();
    }
    
    public DictValue coerce(DictValue object) throws PythonException {
        if (object.isInstance(this))
            return object;
        throw new CoersionFailed();
    }
    
    public PythonObject _len(RuntimeArguments args) throws PythonException {
        DictValue array = DictType.instance.coerce(args.readSingle());
        return IntegerType.wrap(array.getDict().size());
    }
    
    private DictType() {
    }
    
    public static final DictType instance = new DictType();
    
    @Override
    public String describe() {
        return "dict";
    }
    
    public static DictValue wrap() {
        return new DictValue();
    }
    
    public static DictValue wrap(HashMap<PythonObject, PythonObject> dict) {
        return new DictValue(dict);
    }
    
    public static DictValue wrapStrDict(HashMap<String, PythonObject> strDict) {
        HashMap<PythonObject, PythonObject> hashMap = new HashMap<PythonObject, PythonObject>();
        for (Entry<String, PythonObject> entry : strDict.entrySet()) {
            PythonObject key = StringType.wrap(entry.getKey());
            hashMap.put(key, entry.getValue());
        }
        return new DictValue(hashMap);
    }
}
