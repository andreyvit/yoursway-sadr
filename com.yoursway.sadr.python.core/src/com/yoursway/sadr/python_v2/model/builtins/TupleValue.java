package com.yoursway.sadr.python_v2.model.builtins;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.yoursway.sadr.blocks.foundation.values.AbstractValue;
import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;

public class TupleValue extends AbstractValue {
    
    private final List<RuntimeObject> list;
    
    public TupleValue(List<RuntimeObject> list) {
        this.list = newArrayList(list);
    }
    
    @Override
    public String describe() {
        //HACK:
        String string = list.toString();
        return '(' + string.substring(1, string.length() - 1) + ')';
    }
    
    public List<RuntimeObject> getList() {
        return list;
    }
    
}
