package com.yoursway.sadr.python.analysis.objectmodel.types;

import java.util.Collection;

import com.yoursway.sadr.python.analysis.objectmodel.values.PythonValue;
import com.yoursway.sadr.python.analysis.objectmodel.values.TupleValue;

public class TupleType extends BuiltinType {
    //    public PythonValue __call__(RuntimeArguments args) throws PythonException {
    //        PythonValue value = args.readSingle();
    //        if (value instanceof TupleValue)
    //            return value;
    //        //        if (value instanceof ListValue)
    //        //            return wrap(((ListValue) value).getList());
    //        if (value instanceof DictValue)
    //            return wrap(((DictValue) value).getDict().keySet());
    //        throw new CoersionFailed();
    //    }
    //    
    //    public PythonValue __getitem__(RuntimeArguments args) throws PythonException {
    //        List<PythonValue> list = args.readArgs(2);
    //        TupleValue array = coerce(list.get(0));
    //        IntegerValue index = IntegerType.instance.coerce(list.get(1));
    //        List<PythonValue> items = array.getList();
    //        return items.get((int) index.value());
    //    }
    //    
    //    public PythonValue __len__(RuntimeArguments args) throws PythonException {
    //        TupleValue list = coerce(args.readSingle());
    //        return IntegerType.wrap(list.getList().size());
    //    }
    //    
    private TupleType() {
    }
    
    public final static TupleType instance = new TupleType();
    
    @Override
    public String describe() {
        return "tuple";
    }
    
    public static TupleValue wrap(Collection<PythonValue> list) {
        return new TupleValue(list);
    }
    
    @Override
    public TupleValue coerce(PythonValue value) throws PythonException {
        if (value instanceof TupleValue)
            return (TupleValue) value;
        throw new CoersionFailed();
    }
}
