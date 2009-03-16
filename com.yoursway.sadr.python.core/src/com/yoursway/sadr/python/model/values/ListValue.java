package com.yoursway.sadr.python.model.values;

import static com.google.common.collect.Lists.newArrayList;

import java.util.Collection;
import java.util.List;

import com.yoursway.sadr.python.model.types.ListType;
import com.yoursway.sadr.python.model.types.PythonType;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;

public class ListValue extends PythonValue {
    
    private final List<PythonValue> list;
    
    public ListValue(Collection<PythonValue> list) {
        this.list = newArrayList(list);
    }
    
    @Override
    public String describe() {
        return list.toString();
    }
    
    public List<PythonValue> getList() {
        return list;
    }
    
    @Override
    public PythonType getType() {
        return ListType.instance;
    }
    
}
