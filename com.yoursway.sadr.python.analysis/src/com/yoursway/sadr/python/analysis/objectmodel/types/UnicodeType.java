package com.yoursway.sadr.python.analysis.objectmodel.types;

import com.yoursway.sadr.python.analysis.objectmodel.values.PythonValue;
import com.yoursway.sadr.python.analysis.objectmodel.values.StringValue;

public class UnicodeType extends BaseStringType {
    
    //    
    //    public PythonValue __int__(RuntimeArguments args) throws PythonException {
    //        PythonValue str = args.readSingle();
    //        StringValue value = coerce(str);
    //        int parsed = Integer.parseInt(value.toString());
    //        return IntegerType.wrap(parsed);
    //    }
    //    
    //    public PythonValue __str__(RuntimeArguments args) throws TypeError {
    //        PythonValue str = args.readSingle();
    //        if (str instanceof UnicodeType)
    //            return wrap("");
    //        return wrap(str.toString());
    //    }
    //    
    //    public PythonValue __call__(RuntimeArguments args) throws TypeError {
    //        PythonValue str = args.readSingle();
    //        if (str instanceof UnicodeType)
    //            return wrap("", true);
    //        return wrap(str.toString(), true);
    //    }
    //    
    //    public PythonValue __add__(RuntimeArguments args) throws PythonException {
    //        List<StringValue> values = coerce(2, args);
    //        StringValue result = values.get(0).add(values.get(1));
    //        return result;
    //    }
    //    
    //    public PythonValue __mod__(RuntimeArguments args) throws PythonException {
    //        List<StringValue> values = coerce(2, args);
    //        StringValue result = values.get(0).format(values.get(1));
    //        return result;
    //    }
    //    
    //    public PythonValue __mul__(RuntimeArguments args) throws PythonException {
    //        List<PythonValue> values = args.readArgs(2);
    //        StringValue str = coerce(values.get(0));
    //        NumericValue cnt = IntegerType.instance.coerce(values.get(1));
    //        if (str.value().length() == 0 || cnt.coerceToInt() <= 0)
    //            return wrap("");
    //        if (str.value().length() * cnt.coerceToInt() > MAX_LENGTH)
    //            return null; //OutOfMemory error
    //        StringBuilder bld = new StringBuilder();
    //        for (long i = cnt.coerceToInt(); i > 0; i /= 2) {
    //            bld.append(bld);
    //            if (i % 2 != 0)
    //                bld.append(str);
    //        }
    //        return wrap(bld.toString(), str.isUnicode());
    //    }
    //    
    private UnicodeType() {
        //        setAttribute(new RedirectFunctionObject("__call__", "__str__"));
    }
    
    public static UnicodeType instance = new UnicodeType();
    
    public static PythonValue wrap(String value) {
        return new StringValue(value, true);
    }
    
    public static PythonValue wrap(String value, boolean isUnicode) {
        return new StringValue(value, isUnicode);
    }
    
    @Override
    public String describe() {
        return "unicode";
    }
    
    @Override
    public StringValue coerce(PythonValue val) throws CoersionFailed {
        StringValue str = super.coerce(val);
        if (str.isUnicode()) {
            return str;
        }
        throw new CoersionFailed();
    }
    
}
