package com.yoursway.sadr.python_v2.model.builtins;

import static com.google.common.collect.Lists.newArrayList;

import java.util.Collection;
import java.util.List;

import com.yoursway.sadr.blocks.foundation.values.AbstractValue;
import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;

public class ListValue extends AbstractValue {
    
    private final List<RuntimeObject> list;
    
    public ListValue(Collection<RuntimeObject> list) {
        this.list = newArrayList(list);
    }
    
    @Override
    public String describe() {
        return list.toString();
    }
    
    public List<RuntimeObject> getList() {
        return list;
    }
    
}
