package com.yoursway.sadr.python_v2.goals;

import java.util.HashMap;
import java.util.Map;

import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;
import com.yoursway.sadr.succeeder.IAcceptor;

public abstract class BinaryAcceptor implements IAcceptor {
    private final Map<Krocodile, PythonObject> left = new HashMap<Krocodile, PythonObject>();
    private final Map<Krocodile, PythonObject> right = new HashMap<Krocodile, PythonObject>();
    
    public void addLeft(PythonObject result, Krocodile context) {
        left.put(context, result);
    }
    
    public void addRight(PythonObject result, Krocodile context) {
        right.put(context, result);
    }
    
    public Map<Krocodile, PythonObject> getLeft() {
        return left;
    }
    
    public Map<Krocodile, PythonObject> getRight() {
        return right;
    }
}
