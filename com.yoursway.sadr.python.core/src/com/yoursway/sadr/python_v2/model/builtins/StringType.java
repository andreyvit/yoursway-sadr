package com.yoursway.sadr.python_v2.model.builtins;

import java.util.List;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.blocks.integer_literals.NumericValue;
import com.yoursway.sadr.python.core.typeinferencing.values.StringValue;
import com.yoursway.sadr.python_v2.constructs.StringLiteralC;
import com.yoursway.sadr.python_v2.model.PythonArguments;

public class StringType extends PythonClassType {
    private static final long MAX_LENGTH = 1000000;
    
    public RuntimeObject __int__(PythonArguments args) {
        RuntimeObject str = args.readSingle();
        StringValue value = str.convertValue(StringValue.class);
        int parsed = Integer.parseInt(value.toString());
        return IntegerType.wrap(parsed);
    }
    
    public RuntimeObject __str__(PythonArguments args) {
        RuntimeObject str = args.readSingle();
        if (str instanceof StringType)
            return wrap("");
        return wrap(str.toString());
    }
    
    public RuntimeObject __unicode__(PythonArguments args) {
        RuntimeObject str = args.readSingle();
        if (str instanceof StringType)
            return wrap("", true);
        return wrap(str.toString(), true);
    }
    
    public RuntimeObject __add__(PythonArguments args) {
        List<StringValue> values = args.castArgs(2, StringValue.class);
        StringValue result = values.get(0).add(values.get(1));
        return wrap(result);
    }
    
    public RuntimeObject __mod__(PythonArguments args) {
        List<StringValue> values = args.castArgs(2, StringValue.class);
        StringValue result = values.get(0).format(values.get(1));
        return wrap(result);
    }
    
    public RuntimeObject __mul__(PythonArguments args) {
        List<RuntimeObject> values = args.readArgs(2);
        StringValue str = values.get(0).convertValue(StringValue.class);
        NumericValue cnt = values.get(1).convertValue(NumericValue.class);
        if (!cnt.coercibleToInt())
            return null;
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
        setAttribute(new RedirectFunctionObject("__call__", "__str__"));
    }
    
    private static StringType instance = new StringType();
    
    public static StringType instance() {
        return instance;
    }
    
    public static RuntimeObject wrap(StringLiteralC literal) {
        StringValue value = new StringValue(literal.stringValue(), literal.isUnicode());
        return new PythonValue<StringValue>(instance(), value, literal);
    }
    
    public static RuntimeObject wrap(StringValue value) {
        return new PythonValue<StringValue>(instance(), value, null);
    }
    
    public static RuntimeObject wrap(String value, boolean isUnicode) {
        return wrap(new StringValue(value, isUnicode));
    }
    
    public static RuntimeObject wrap(String value) {
        return wrap(new StringValue(value));
    }
    
    @Override
    public String describe() {
        return "str";
    }
    
}
