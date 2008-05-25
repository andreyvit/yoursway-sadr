package com.yoursway.sadr.python_v2.model.builtins;

import java.util.List;

import com.yoursway.sadr.blocks.integer_literals.IntegerValue;
import com.yoursway.sadr.python_v2.model.PythonArguments;
import com.yoursway.sadr.python_v2.model.RuntimeObject;

public class TupleType extends PythonClassType {
    public RuntimeObject __getitem__(PythonArguments args) {
        List<RuntimeObject> list = args.readArgs(2);
        TupleValue array = list.get(0).convertValue(TupleType.instance());
        IntegerValue index = list.get(1).convertValue(IntegerType.instance());
        List<RuntimeObject> items = array.getList();
        return items.get((int) index.value());
    }
    
    public RuntimeObject __len__(PythonArguments args) {
        TupleValue list = args.castSingle(TupleType.instance());
        return IntegerType.wrap(list.getList().size());
    }
    
    private TupleType() {
    }
    
    private static final TupleType instance = new TupleType();
    
    public static TupleType instance() {
        return instance;
    }
    
    @Override
    public String describe() {
        return "tuple";
    }
    
    public static PythonValue<TupleValue> wrap(List<RuntimeObject> list) {
        return new PythonValue<TupleValue>(instance(), new TupleValue(list));
    }
}
