package com.yoursway.sadr.python_v2.model.builtins.types;

import java.util.Collection;
import java.util.List;

import com.yoursway.sadr.python_v2.model.RuntimeArguments;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;
import com.yoursway.sadr.python_v2.model.builtins.values.DictValue;
import com.yoursway.sadr.python_v2.model.builtins.values.IntegerValue;
import com.yoursway.sadr.python_v2.model.builtins.values.ListValue;
import com.yoursway.sadr.python_v2.model.builtins.values.TupleValue;

public class TupleType extends BuiltinType {
    public PythonObject __call__(RuntimeArguments args) throws PythonException {
        PythonObject value = args.readSingle();
        if (value instanceof TupleValue)
            return value;
        if (value instanceof ListValue)
            return wrap(((ListValue) value).getList());
        if (value instanceof DictValue)
            return wrap(((DictValue) value).getDict().keySet());
        throw new CoersionFailed();
    }
    
    public PythonObject __getitem__(RuntimeArguments args) throws PythonException {
        List<PythonObject> list = args.readArgs(2);
        TupleValue array = coerce(list.get(0));
        IntegerValue index = IntegerType.instance.coerce(list.get(1));
        List<PythonObject> items = array.getList();
        return items.get((int) index.value());
    }
    
    public PythonObject __len__(RuntimeArguments args) throws PythonException {
        TupleValue list = coerce(args.readSingle());
        return IntegerType.wrap(list.getList().size());
    }
    
    private TupleType() {
    }
    
    public final static TupleType instance = new TupleType();
    
    @Override
    public String describe() {
        return "tuple";
    }
    
    public static TupleValue wrap(Collection<PythonObject> list) {
        return new TupleValue(list);
    }
    
    @Override
    public TupleValue coerce(PythonObject value) throws PythonException {
        if (value instanceof TupleValue)
            return (TupleValue) value;
        throw new CoersionFailed();
    }
}
