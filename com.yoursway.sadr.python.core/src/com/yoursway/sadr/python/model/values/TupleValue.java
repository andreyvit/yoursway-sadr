package com.yoursway.sadr.python.model.values;

import static com.google.common.collect.Lists.newArrayList;

import java.util.Collection;
import java.util.List;

import com.yoursway.sadr.python.model.types.PythonType;
import com.yoursway.sadr.python.model.types.TupleType;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;

public class TupleValue extends PythonValue {
    
    private final List<PythonValue> list;
    
    public TupleValue(Collection<PythonValue> list) {
        this.list = newArrayList(list);
    }
    
    @Override
    public String describe() {
        String string = list.toString();
        return '(' + string.substring(1, string.length() - 1) + ')';
    }
    
    public List<PythonValue> getList() {
        return list;
    }
    
    @Override
    public PythonType getType() {
        return TupleType.instance;
    }
    
}
