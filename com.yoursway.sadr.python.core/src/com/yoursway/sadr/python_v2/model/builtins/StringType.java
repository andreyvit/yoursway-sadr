package com.yoursway.sadr.python_v2.model.builtins;

import java.util.List;

import com.yoursway.sadr.python.core.typeinferencing.constructs.StringLiteralC;
import com.yoursway.sadr.python.core.typeinferencing.values.StringValue;
import com.yoursway.sadr.python_v2.model.RuntimeObject;

public class StringType extends PythonClassType {
    private static PythonObjectWithValue<StringValue>[] castArguments(List<RuntimeObject> args) {
        assert args.size() == 2;
        return new PythonObjectWithValue[] { (PythonObjectWithValue<StringValue>) args.get(0),
                (PythonObjectWithValue<StringValue>) args.get(1) };
    }
    
    private StringType() {
        FunctionObject addFunc = new FunctionObject() {
            @Override
            public RuntimeObject evaluate(List<RuntimeObject> args) {
                PythonObjectWithValue<StringValue>[] castedArgs = castArguments(args);
                StringValue result = castedArgs[0].getValue().add(castedArgs[1].getValue());
                return new PythonObjectWithValue<StringValue>(instance(), result, null);
            }
        };
        setAttribute("__add__", addFunc);
    }
    
    private static StringType instance = new StringType();
    
    static StringType instance() {
        return instance;
    }
    
    public static RuntimeObject newStringObject(StringLiteralC literal) {
        StringValue value = new StringValue(literal.stringValue());
        return new PythonObjectWithValue<StringValue>(instance(), value, literal);
    }
    
    @Override
    public String describe() {
        return "str";
    }
}
