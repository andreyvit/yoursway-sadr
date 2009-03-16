package com.yoursway.sadr.python_v2.model.builtins.values;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.model.RuntimeArguments;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;
import com.yoursway.sadr.python_v2.model.builtins.types.PythonException;

public class BuiltinFunctionObject extends FunctionObject {
    public BuiltinFunctionObject(String name) {
        super(name);
    }
    
    @Override
    public PythonValueSet call(Krocodile crocodile, RuntimeArguments args) {
        try {
            PythonObject val = evaluate(args);
            if (val == null) {
                return PythonValueSet.EMPTY;
            }
            return new PythonValueSet(val, crocodile);
        } catch (PythonException e) {
            return PythonValueSet.EMPTY;
        }
        
    }
    
    /**
     * @throws PythonException
     */
    public PythonObject evaluate(RuntimeArguments args) throws PythonException {
        throw new NotImplementedException();
    }
}
