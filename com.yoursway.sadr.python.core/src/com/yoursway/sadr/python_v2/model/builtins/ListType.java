package com.yoursway.sadr.python_v2.model.builtins;

import java.util.List;

import com.yoursway.sadr.blocks.integer_literals.IntegerValue;
import com.yoursway.sadr.python_v2.model.PythonArguments;
import com.yoursway.sadr.python_v2.model.RuntimeObject;

public class ListType extends PythonClassType {
    
    public RuntimeObject __getitem__(PythonArguments args) {
        List<RuntimeObject> list = args.readArgs(2);
        ListValue array = list.get(0).convertValue(ListType.instance());
        IntegerValue index = list.get(1).convertValue(IntegerType.instance());
        return array.getList().get((int) index.value());
    }
    
    public RuntimeObject __len__(PythonArguments args) {
        ListValue list = args.castSingle(ListType.instance());
        return IntegerType.wrap(list.getList().size());
    }
    
    private ListType() {
        setAttribute(new SyncFunctionObject("append") {
            @Override
            public RuntimeObject evaluate(PythonArguments args) {
                return Builtins.NONE;
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
    
    public static PythonValue<ListValue> wrap(List<RuntimeObject> list) {
        return new PythonValue<ListValue>(instance(), new ListValue(list));
    }
    
}
