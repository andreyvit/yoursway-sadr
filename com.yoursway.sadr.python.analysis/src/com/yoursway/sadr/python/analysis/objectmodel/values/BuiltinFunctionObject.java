package com.yoursway.sadr.python.analysis.objectmodel.values;

import kilim.pausable;

import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.objectmodel.types.FunctionType;
import com.yoursway.sadr.python.analysis.objectmodel.types.PythonType;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSetBuilder;

public abstract class BuiltinFunctionObject extends PythonValue implements CallableObject {
    
    private final String name;
    
    public BuiltinFunctionObject(String name) {
        if (name == null)
            throw new NullPointerException("name is null");
        this.name = name;
    }
    
    @Override
    public String describe() {
        return "builtin " + name();
    }
    
    @Override
    public String name() {
        return name;
    }
    
    @Override
    @pausable
    public void call(PythonDynamicContext dc, PythonValueSetBuilder builder) {
        builder.addResults(calculate(dc));
    }
    
    @pausable
    protected abstract PythonValueSet calculate(PythonDynamicContext dc);
    
    @Override
    public PythonType getType() {
        return FunctionType.instance;
    }
    
    @Override
    public void bind(PythonValue self, PythonValueSetBuilder builder) {
        builder.addResult(new BoundFunctionObject(this, self));
    }
    
}
