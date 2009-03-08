package com.yoursway.sadr.python_v2.model.builtins.values;

import static com.google.common.collect.Lists.newArrayList;

import java.util.Collection;
import java.util.List;

import com.yoursway.sadr.python_v2.model.builtins.PythonObject;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;
import com.yoursway.sadr.python_v2.model.builtins.types.PythonType;
import com.yoursway.sadr.python_v2.model.builtins.types.TupleType;

public class TupleValue extends PythonValue {
    
    private final List<PythonObject> list;
    
    public TupleValue(Collection<PythonObject> list) {
        this.list = newArrayList(list);
    }
    
    @Override
    public String describe() {
        String string = list.toString();
        return '(' + string.substring(1, string.length() - 1) + ')';
    }
    
    public List<PythonObject> getList() {
        return list;
    }
    
    @Override
    public PythonType getType() {
        return TupleType.instance;
    }
    
}
