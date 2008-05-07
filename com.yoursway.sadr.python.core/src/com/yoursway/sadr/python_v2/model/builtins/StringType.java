package com.yoursway.sadr.python_v2.model.builtins;

import com.yoursway.sadr.python.core.typeinferencing.constructs.StringLiteralC;
import com.yoursway.sadr.python_v2.model.RuntimeObject;

public class StringType extends PythonClassType {
    private static void addStringTypeAttributes(StringType inst) {
        //TODO add attributes
    }
    
    private StringType() {
        addStringTypeAttributes(this);
    }
    
    private static StringType instance = new StringType();
    
    static StringType instance() {
        return instance;
    }
    
    public static RuntimeObject newStringObject(StringLiteralC literal) {
        return new PythonObjectWithValue<StringLiteralC>(instance(), literal);
    }
}
