package com.yoursway.sadr.python_v2.model.builtins;

import java.util.List;

import com.yoursway.sadr.python.core.typeinferencing.values.StringValue;
import com.yoursway.sadr.python_v2.constructs.StringLiteralC;
import com.yoursway.sadr.python_v2.model.PythonArguments;
import com.yoursway.sadr.python_v2.model.RuntimeObject;

public class StringType extends PythonClassType {
    public RuntimeObject __int__(PythonArguments args) {
        RuntimeObject str = args.readSingle();
        StringValue value = str.convertValue(instance());
        int parsed = Integer.parseInt(value.toString());
        return IntegerType.wrap(parsed);
    }
    
    public RuntimeObject __str__(PythonArguments args) {
        RuntimeObject str = args.readSingle();
        if (str instanceof StringType)
            return wrap("");
        return wrap(str.toString());
    }
    
    public RuntimeObject __add__(PythonArguments args) {
        List<StringValue> values = args.castArgs(2, instance());
        StringValue result = values.get(0).add(values.get(1));
        return wrap(result);
    }
    
    public RuntimeObject __mod__(PythonArguments args) {
        List<StringValue> values = args.castArgs(2, instance());
        StringValue result = values.get(0).format(values.get(1));
        return wrap(result);
    }
    
    private StringType() {
        setAttribute(new RedirectFunctionObject("__call__", "__str__"));
    }
    
    private static StringType instance = new StringType();
    
    public static StringType instance() {
        return instance;
    }
    
    public static RuntimeObject wrap(StringLiteralC literal) {
        StringValue value = new StringValue(literal.stringValue());
        return new PythonValue<StringValue>(instance(), value, literal);
    }
    
    public static RuntimeObject wrap(StringValue value) {
        return new PythonValue<StringValue>(instance(), value, null);
    }
    
    public static RuntimeObject wrap(String value) {
        return wrap(new StringValue(value));
    }
    
    @Override
    public String describe() {
        return "str";
    }
    
}
