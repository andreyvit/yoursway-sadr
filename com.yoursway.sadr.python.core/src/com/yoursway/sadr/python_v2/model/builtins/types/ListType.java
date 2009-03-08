package com.yoursway.sadr.python_v2.model.builtins.types;

import java.util.Collection;
import java.util.List;

import com.yoursway.sadr.python_v2.model.RuntimeArguments;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;
import com.yoursway.sadr.python_v2.model.builtins.values.BuiltinFunctionObject;
import com.yoursway.sadr.python_v2.model.builtins.values.IntegerValue;
import com.yoursway.sadr.python_v2.model.builtins.values.ListValue;
import com.yoursway.sadr.python_v2.model.builtins.values.NoneValue;

public class ListType extends BuiltinType {
    
    public PythonObject __getitem__(RuntimeArguments args) throws PythonException {
        List<PythonObject> list = args.readArgs(2);
        ListValue array = coerce(list.get(0));
        IntegerValue index = IntegerType.instance.coerce(list.get(1));
        return array.getList().get((int) index.value());
    }
    
    public PythonObject __len__(RuntimeArguments args) throws PythonException {
        ListValue list = coerce(args.readSingle());
        return IntegerType.wrap(list.getList().size());
    }
    
    private ListType() {
        setAttribute(new BuiltinFunctionObject("append") {
            @Override
            public PythonObject evaluate(RuntimeArguments args) {
                return NoneValue.instance;
            }
        });
    }
    
    public static final ListType instance = new ListType();
    
    @Override
    public String describe() {
        return "list";
    }
    
    public static ListValue wrap(Collection<PythonObject> list) {
        return new ListValue(list);
    }
    
    @Override
    public ListValue coerce(PythonObject value) throws PythonException {
        if (value instanceof ListValue)
            return (ListValue) value;
        throw new CoersionFailed();
    }
}
