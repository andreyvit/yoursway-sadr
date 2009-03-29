package com.yoursway.sadr.python.model.values;

import kilim.pausable;

import com.yoursway.sadr.python.constructs.CallableDeclaration;
import com.yoursway.sadr.python.model.types.FunctionType;
import com.yoursway.sadr.python.model.types.PythonType;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetBuilder;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;

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
    public CallableDeclaration getDecl() {
        return null;
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
