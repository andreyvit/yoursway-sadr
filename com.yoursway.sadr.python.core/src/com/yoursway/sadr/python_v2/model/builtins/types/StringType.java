package com.yoursway.sadr.python_v2.model.builtins.types;

import java.util.List;

import com.yoursway.sadr.python_v2.constructs.StringLiteralC;
import com.yoursway.sadr.python_v2.model.RuntimeArguments;
import com.yoursway.sadr.python_v2.model.TypeError;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;
import com.yoursway.sadr.python_v2.model.builtins.values.NumericValue;
import com.yoursway.sadr.python_v2.model.builtins.values.StringValue;

public class StringType extends BaseStringType {
    private static final long MAX_LENGTH = 1000000;
    
    public PythonObject __int__(RuntimeArguments args) throws PythonException {
        //FIXME: no this method
        PythonObject str = args.readSingle();
        StringValue value = coerce(str);
        int parsed = Integer.parseInt(value.toString());
        return IntegerType.wrap(parsed);
    }
    
    public StringValue __call__(RuntimeArguments args) throws TypeError {
        //FIXME: no this method
        PythonObject str = args.readSingle();
        if (str instanceof StringType)
            return wrap("");
        return wrap(str.toString());
    }
    
    public StringValue __unicode__(RuntimeArguments args) throws TypeError {
        //FIXME: no this method
        PythonObject str = args.readSingle();
        if (str instanceof StringType)
            return wrap("", true);
        return wrap(str.toString(), true);
    }
    
    public StringValue __add__(RuntimeArguments args) throws PythonException {
        List<StringValue> values = coerce(2, args);
        StringValue result = values.get(0).add(values.get(1));
        return result;
    }
    
    public StringValue __mod__(RuntimeArguments args) throws PythonException {
        List<PythonObject> values = args.readArgs(2);
        StringValue str = coerce(values.get(0));
        StringValue result = str.format(values.get(1));
        return result;
    }
    
    public StringValue __mul__(RuntimeArguments args) throws PythonException {
        List<PythonObject> values = args.readArgs(2);
        StringValue str = coerce(values.get(0));
        NumericValue cnt = IntegerType.instance.coerce(values.get(1));
        if (!cnt.coercibleToInt())
            throw new ValueError();
        if (str.value().length() == 0 || cnt.coerceToInt() <= 0)
            return wrap("");
        if (str.value().length() * cnt.coerceToInt() > MAX_LENGTH)
            return null; //OutOfMemory error
        StringBuilder bld = new StringBuilder();
        for (long i = cnt.coerceToInt(); i > 0; i /= 2) {
            bld.append(bld);
            if (i % 2 != 0)
                bld.append(str);
        }
        return wrap(bld.toString(), str.isUnicode());
    }
    
    private StringType() {
    }
    
    public static StringType instance = new StringType();
    
    public static StringValue wrap(StringLiteralC literal) {
        return new StringValue(literal.stringValue(), literal.isUnicode());
    }
    
    public static StringValue wrap(String value, boolean isUnicode) {
        return new StringValue(value, isUnicode);
    }
    
    public static StringValue wrap(String value) {
        return new StringValue(value);
    }
    
    @Override
    public String describe() {
        return "str";
    }
    
    @Override
    public StringValue coerce(PythonObject val) throws CoersionFailed {
        StringValue str = super.coerce(val);
        if (!str.isUnicode()) {
            return str;
        }
        throw new CoersionFailed();
    }
}
