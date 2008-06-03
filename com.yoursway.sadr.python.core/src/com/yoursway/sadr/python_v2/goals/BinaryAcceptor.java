package com.yoursway.sadr.python_v2.goals;

import java.util.HashMap;
import java.util.Map;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.succeeder.IAcceptor;

public abstract class BinaryAcceptor implements IAcceptor {
    private final Map<Context, RuntimeObject> left = new HashMap<Context, RuntimeObject>();
    private final Map<Context, RuntimeObject> right = new HashMap<Context, RuntimeObject>();
    
    public void addLeft(RuntimeObject result, Context context) {
        left.put(context, result);
    }
    
    public void addRight(RuntimeObject result, Context context) {
        right.put(context, result);
    }
    
    public Map<Context, RuntimeObject> getLeft() {
        return left;
    }
    
    public Map<Context, RuntimeObject> getRight() {
        return right;
    }
}
