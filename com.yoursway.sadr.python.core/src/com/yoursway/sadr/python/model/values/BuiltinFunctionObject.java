package com.yoursway.sadr.python.model.values;

import com.yoursway.sadr.python.model.types.PythonException;
import com.yoursway.sadr.python.model.types.PythonType;
import com.yoursway.sadr.python.objects.RuntimeArguments;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;

//
//import sun.reflect.generics.reflectiveObjects.NotImplementedException;
//
//import com.yoursway.sadr.python.objects.RuntimeArguments;
//import com.yoursway.sadr.python_v2.croco.Krocodile;
//import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
//import com.yoursway.sadr.python_v2.model.builtins.PythonValue;
//import com.yoursway.sadr.python_v2.model.builtins.types.PythonException;
//
public class BuiltinFunctionObject extends PythonValue {
    //extends FunctionObject {
    //    
    public BuiltinFunctionObject(String name) {
        //        super(name);
    }
    
    //    
    //    @Override
    public PythonValueSet call(PythonDynamicContext crocodile, RuntimeArguments args) {
        //        try {
        //            PythonValue val = evaluate(args);
        //            if (val == null) {
        //                return PythonValueSet.EMPTY;
        //            }
        //            return new PythonValueSet(val, crocodile);
        //        } catch (PythonException e) {
        //            return PythonValueSet.EMPTY;
        //        }
        //        
        return null;
    }
    
    /**
     * @throws PythonException
     */
    public PythonValue evaluate(RuntimeArguments args) throws PythonException {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public PythonType getType() {
        throw new UnsupportedOperationException();
    }
    
}
