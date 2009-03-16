package com.yoursway.sadr.python.model;

import com.yoursway.sadr.python.constructs.ArrayAccessC;
import com.yoursway.sadr.python.constructs.CallC;
import com.yoursway.sadr.python.constructs.IfC;
import com.yoursway.sadr.python.constructs.PythonConstruct;
import com.yoursway.sadr.python.model.values.CallableObject;
import com.yoursway.sadr.python.objects.RuntimeArguments;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;

public class CallResolver {
    
    public static PythonValueSet findMethod(PythonValue pvalue, String string, PythonDynamicContext crocodile) {
        // TODO Auto-generated method stub
        return null;
    }
    
    public static PythonValueSet callFunction(PythonValue method, RuntimeArguments runtimeArguments,
            PythonDynamicContext crocodile, PythonConstruct decl) {
        // TODO Auto-generated method stub
        return null;
    }
    
    public static PythonValueSet callMethod(PythonValue arrayObject, String string, RuntimeArguments args,
            PythonDynamicContext context, ArrayAccessC arrayAccessC) {
        // TODO Auto-generated method stub
        return null;
    }
    
    public static PythonValueSet callFunction(CallableObject method, RuntimeArguments real,
            PythonDynamicContext crocodile, CallC decl) {
        // TODO Auto-generated method stub
        return null;
    }
    
    public static PythonValueSet callMethod(PythonValue method, String string, RuntimeArguments real,
            PythonDynamicContext crocodile, CallC callC) {
        // TODO Auto-generated method stub
        return null;
    }
    
    public static PythonValueSet callMethod(PythonValue result, String string, RuntimeArguments args,
            PythonDynamicContext crocodile, IfC ifC) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
