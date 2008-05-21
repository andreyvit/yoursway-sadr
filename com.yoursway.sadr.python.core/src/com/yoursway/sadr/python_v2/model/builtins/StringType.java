package com.yoursway.sadr.python_v2.model.builtins;

import java.util.List;

import com.yoursway.sadr.python.core.typeinferencing.constructs.StringLiteralC;
import com.yoursway.sadr.python.core.typeinferencing.values.StringValue;
import com.yoursway.sadr.python_v2.model.ArgumentsUtil;
import com.yoursway.sadr.python_v2.model.PythonArguments;
import com.yoursway.sadr.python_v2.model.RuntimeObject;

public class StringType extends PythonClassType {
    private static List<StringValue> castArgs(PythonArguments args) {
        return ArgumentsUtil.getCastedArgs(args, 2, instance());
    }
    
    private StringType() {
        setAttribute(new FunctionObject("__add__") {
            @Override
            public RuntimeObject evaluate(PythonArguments args) {
                List<StringValue> castedArgs = castArgs(args);
                StringValue result = castedArgs.get(0).add(castedArgs.get(1));
                return wrap(result);
            }
        });
    }
    
    private static StringType instance = new StringType();
    
    public static StringType instance() {
        return instance;
    }
    
    public static RuntimeObject wrap(StringLiteralC literal) {
        StringValue value = new StringValue(literal.stringValue());
        return new PythonObjectWithValue<StringValue>(instance(), value, literal);
    }
    
    public static RuntimeObject wrap(StringValue value) {
        return new PythonObjectWithValue<StringValue>(instance(), value, null);
    }
    
    public static RuntimeObject wrap(String value) {
        return wrap(new StringValue(value));
    }
    
    @Override
    public String describe() {
        return "str";
    }
    
}
