package com.yoursway.sadr.python_v2.model.builtins;

import java.util.List;

import com.yoursway.sadr.blocks.integer_literals.IntegerValue;
import com.yoursway.sadr.python_v2.model.ArgumentsUtil;
import com.yoursway.sadr.python_v2.model.PythonArguments;
import com.yoursway.sadr.python_v2.model.RuntimeObject;

public class ListType extends PythonClassType {
    private ListType() {
        setAttribute(new FunctionObject("append") {
            @Override
            public RuntimeObject evaluate(PythonArguments args) {
                return Builtins.NONE;
            }
        });
        
        setAttribute(new FunctionObject("__getitem__") {
            @Override
            @SuppressWarnings("unchecked")
            public RuntimeObject evaluate(PythonArguments args) {
                List<RuntimeObject> list = ArgumentsUtil.getArgs(args, 2);
                ListValue array = list.get(0).convertValue(ListType.instance());
                IntegerValue index = list.get(1).convertValue(IntType.instance());
                return array.getList().get((int) index.value());
            }
        });
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
