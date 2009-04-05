package com.yoursway.sadr.python.analysis.objectmodel.types;

import java.util.HashMap;
import java.util.Map.Entry;

import com.yoursway.sadr.python.analysis.objectmodel.values.DictValue;
import com.yoursway.sadr.python.analysis.objectmodel.values.PythonValue;

public class DictType extends PythonType {
    //    public PythonValue __call__(RuntimeArguments args) throws PythonException {
    //        PythonArgumentsReader reader = new PythonArgumentsReader(args);
    //        HashMap<String, PythonValue> kwargs = reader.lastKwargs();
    //        return DictType.wrapStrDict(kwargs);
    //    }
    
    //    public PythonValue __getitem__(RuntimeArguments args) throws PythonException {
    //        List<PythonValue> list = args.readArgs(2);
    //        DictValue array = DictType.instance.coerce(list.get(0));
    //        PythonValue value = list.get(1);
    //        return array.getDict().get(value);
    //    }
    
    //    public PythonValue __len__(RuntimeArguments args) throws PythonException {
    //        DictValue array = DictType.instance.coerce(args.readSingle());
    //        return IntegerType.wrap(array.getDict().size());
    //    }
    
    @Override
    public DictValue coerce(PythonValue object) throws PythonException {
        if (object.isInstance(this))
            return (DictValue) object;
        throw new CoersionFailed();
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
    
    public static DictValue wrap(HashMap<PythonValue, PythonValue> dict) {
        return new DictValue(dict);
    }
    
    public static DictValue wrapStrDict(HashMap<String, PythonValue> strDict) {
        HashMap<PythonValue, PythonValue> hashMap = new HashMap<PythonValue, PythonValue>();
        for (Entry<String, PythonValue> entry : strDict.entrySet()) {
            PythonValue key = StringType.wrap(entry.getKey());
            hashMap.put(key, entry.getValue());
        }
        return new DictValue(hashMap);
    }
}
