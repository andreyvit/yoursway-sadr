package com.yoursway.sadr.python_v2.model.builtins;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.yoursway.sadr.blocks.foundation.values.Value;
import com.yoursway.sadr.python_v2.model.RuntimeObject;

public class TupleValue implements Value {
    
    private final List<RuntimeObject> list;
    
    public TupleValue(List<RuntimeObject> list) {
        this.list = newArrayList(list);
    }
    
    public String describe() {
        //HACK:
        String string = list.toString();
        return '(' + string.substring(1, string.length() - 1) + ')';
    }
    
    public List<RuntimeObject> getList() {
        return list;
    }
    
}
