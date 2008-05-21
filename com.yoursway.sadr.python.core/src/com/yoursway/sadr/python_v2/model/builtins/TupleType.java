package com.yoursway.sadr.python_v2.model.builtins;

import java.util.List;

import com.yoursway.sadr.blocks.integer_literals.IntegerValue;
import com.yoursway.sadr.python_v2.model.ArgumentsUtil;
import com.yoursway.sadr.python_v2.model.PythonArguments;
import com.yoursway.sadr.python_v2.model.RuntimeObject;

public class TupleType extends PythonClassType {
    private TupleType() {
        setAttribute(new FunctionObject("__getitem__") {
            @Override
            @SuppressWarnings("unchecked")
            public RuntimeObject evaluate(PythonArguments args) {
                List<RuntimeObject> list = ArgumentsUtil.getArgs(args, 2);
                TupleValue array = list.get(0).convertValue(TupleType.instance());
                IntegerValue index = list.get(1).convertValue(IntType.instance());
                List<RuntimeObject> items = array.getList();
                return items.get((int) index.value());
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
    
    public static PythonObjectWithValue<TupleValue> wrap(List<RuntimeObject> list) {
        return new PythonObjectWithValue<TupleValue>(instance(), new TupleValue(list));
    }
}
