package com.yoursway.sadr.python_v2.goals;

import java.util.HashMap;
import java.util.Map;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.succeeder.IAcceptor;

public abstract class BinaryAcceptor implements IAcceptor {
    private final Map<Krocodile, RuntimeObject> left = new HashMap<Krocodile, RuntimeObject>();
    private final Map<Krocodile, RuntimeObject> right = new HashMap<Krocodile, RuntimeObject>();
    
    public void addLeft(RuntimeObject result, Krocodile context) {
        left.put(context, result);
    }
    
    public void addRight(RuntimeObject result, Krocodile context) {
        right.put(context, result);
    }
    
    public Map<Krocodile, RuntimeObject> getLeft() {
        return left;
    }
    
    public Map<Krocodile, RuntimeObject> getRight() {
        return right;
    }
}
