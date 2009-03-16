package com.yoursway.sadr.python_v2.model.builtins.values;

import static com.google.common.collect.Lists.newArrayList;

import java.util.Collection;
import java.util.List;

import com.yoursway.sadr.python_v2.model.builtins.PythonObject;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;
import com.yoursway.sadr.python_v2.model.builtins.types.ListType;
import com.yoursway.sadr.python_v2.model.builtins.types.PythonType;

public class ListValue extends PythonValue {
    
    private final List<PythonObject> list;
    
    public ListValue(Collection<PythonObject> list) {
        this.list = newArrayList(list);
    }
    
    @Override
    public String describe() {
        return list.toString();
    }
    
    public List<PythonObject> getList() {
        return list;
    }
    
    @Override
    public PythonType getType() {
        return ListType.instance;
    }
    
}
