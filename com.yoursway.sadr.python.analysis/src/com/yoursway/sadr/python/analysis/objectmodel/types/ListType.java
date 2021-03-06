package com.yoursway.sadr.python.analysis.objectmodel.types;

import com.yoursway.sadr.python.analysis.objectmodel.values.ListValue;
import com.yoursway.sadr.python.analysis.objectmodel.values.PythonValue;

public class ListType extends PythonType {
    
    //    public PythonValue __getitem__(RuntimeArguments args) throws PythonException {
    //        List<PythonValue> list = args.readArgs(2);
    //        ListValue array = coerce(list.get(0));
    //        IntegerValue index = IntegerType.instance.coerce(list.get(1));
    //        return array.getList().get((int) index.value());
    //    }
    
    //    public PythonValue __len__(RuntimeArguments args) throws PythonException {
    //        ListValue list = coerce(args.readSingle());
    //        return IntegerType.wrap(list.getList().size());
    //    }
    
    private ListType() {
        //        setAttribute(new BuiltinFunctionObject("append") {
        //            @Override
        //            public PythonValueSet calculate(PythonDynamicContext dc) {
        //                return NoneValue.instance;
        //            }
        //        });
    }
    
    public static final ListType instance = new ListType();
    
    @Override
    public String describe() {
        return "list";
    }
    
    //    public static ListValue wrap(Collection<PythonValue> list) {
    //        return new ListValue(list);
    //    }
    
    @Override
    public ListValue coerce(PythonValue value) throws PythonException {
        if (value instanceof ListValue)
            return (ListValue) value;
        throw new CoersionFailed();
    }
}
