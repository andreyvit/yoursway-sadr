package com.yoursway.sadr.python_v2.model.builtins;

import java.util.List;

import com.yoursway.sadr.blocks.foundation.values.AbstractValue;
import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;

public class ListValue extends AbstractValue {
    
    private final List<RuntimeObject> list;
    
    public ListValue(List<RuntimeObject> list) {
        this.list = list;
    }
    
    @Override
    public String describe() {
        return list.toString();
    }
    
    public List<RuntimeObject> getList() {
        return list;
    }
    
}
