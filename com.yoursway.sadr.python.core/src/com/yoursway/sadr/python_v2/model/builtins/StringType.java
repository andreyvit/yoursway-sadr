package com.yoursway.sadr.python_v2.model.builtins;

import java.util.List;

import com.yoursway.sadr.blocks.foundation.values.Value;
import com.yoursway.sadr.python.core.typeinferencing.values.StringValue;
import com.yoursway.sadr.python_v2.constructs.StringLiteralC;
import com.yoursway.sadr.python_v2.model.PythonArguments;
import com.yoursway.sadr.python_v2.model.RuntimeObject;

public class StringType extends PythonClassType {
    public RuntimeObject __call__(PythonArguments args) {
        Value value = args.getSingle();
        return wrap(value.toString());
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
