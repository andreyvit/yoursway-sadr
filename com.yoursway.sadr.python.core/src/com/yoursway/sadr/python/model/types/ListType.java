package com.yoursway.sadr.python.model.types;

import java.util.Collection;
import java.util.List;

import com.yoursway.sadr.python.model.values.BuiltinFunctionObject;
import com.yoursway.sadr.python.model.values.IntegerValue;
import com.yoursway.sadr.python.model.values.ListValue;
import com.yoursway.sadr.python.model.values.NoneValue;
import com.yoursway.sadr.python.objects.RuntimeArguments;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;

public class ListType extends BuiltinType {
    
    public PythonValue __getitem__(RuntimeArguments args) throws PythonException {
        List<PythonValue> list = args.readArgs(2);
        ListValue array = coerce(list.get(0));
        IntegerValue index = IntegerType.instance.coerce(list.get(1));
        return array.getList().get((int) index.value());
    }
    
    public PythonValue __len__(RuntimeArguments args) throws PythonException {
        ListValue list = coerce(args.readSingle());
        return IntegerType.wrap(list.getList().size());
    }
    
    private ListType() {
        setAttribute(new BuiltinFunctionObject("append") {
            @Override
            public PythonValue evaluate(RuntimeArguments args) {
                return NoneValue.instance;
            }
        });
    }
    
    public static final ListType instance = new ListType();
    
    @Override
    public String describe() {
        return "list";
    }
    
    public static ListValue wrap(Collection<PythonValue> list) {
        return new ListValue(list);
    }
    
    @Override
    public ListValue coerce(PythonValue value) throws PythonException {
        if (value instanceof ListValue)
            return (ListValue) value;
        throw new CoersionFailed();
    }
}
