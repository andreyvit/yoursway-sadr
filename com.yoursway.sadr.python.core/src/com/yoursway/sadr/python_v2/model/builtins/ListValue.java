package com.yoursway.sadr.python_v2.model.builtins;

import java.util.List;

import com.yoursway.sadr.blocks.foundation.values.Value;
import com.yoursway.sadr.python_v2.model.RuntimeObject;

public class ListValue implements Value {
    
    private final List<RuntimeObject> list;
    
    public ListValue(List<RuntimeObject> list) {
        this.list = list;
    }
    
    public String describe() {
        return list.toString();
    }
    
    public List<RuntimeObject> getList() {
        return list;
    }
    
}
