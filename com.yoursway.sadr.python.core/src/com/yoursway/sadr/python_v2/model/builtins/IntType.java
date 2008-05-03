package com.yoursway.sadr.python_v2.model.builtins;

import com.yoursway.sadr.python.core.typeinferencing.constructs.IntegerLiteralC;
import com.yoursway.sadr.python_v2.model.RuntimeObject;

//TODO generalize implementation for built-in types.
public class IntType extends PythonClassImpl implements PythonClass {
    
    private static void addIntTypeAttributes(IntType inst) {
        //TODO add attributes
    }
    
    private IntType() {
        addIntTypeAttributes(this);
    }
    
    private static IntType instance = new IntType();
    
    static IntType instance() {
        return instance;
    }
    
    public static RuntimeObject newIntObject(IntegerLiteralC literal) {
        return new PythonObjectWithValue<IntegerLiteralC>(instance(), literal);
    }
}
