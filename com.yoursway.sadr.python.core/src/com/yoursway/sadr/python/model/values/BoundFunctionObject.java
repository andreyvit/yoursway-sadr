package com.yoursway.sadr.python.model.values;

import java.util.List;

import kilim.pausable;

import com.yoursway.sadr.python.constructs.PythonConstruct;
import com.yoursway.sadr.python.index.unodes.Alias;
import com.yoursway.sadr.python.model.PassedArgumentInfo;
import com.yoursway.sadr.python.model.types.FunctionType;
import com.yoursway.sadr.python.model.types.PythonType;
import com.yoursway.sadr.python_v2.croco.BoundFunctionDynamicContextDecorator;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetBuilder;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;

public final class BoundFunctionObject extends PythonValue implements CallableObject {
    
    private final CallableObject func;
    private final PythonValue self;
    
    public BoundFunctionObject(CallableObject func, PythonValue self) {
        if (func == null)
            throw new NullPointerException("func is null");
        if (self == null)
            throw new NullPointerException("self is null");
        this.func = func;
        this.self = self;
    }
    
    @Override
    public String describe() {
        return "bound method " + name() + " of " + self.describe();
    }
    
    @Override
    public String name() {
        return func.name();
    }
    
    @Override
    public PythonConstruct getDecl() {
        return func.getDecl();
    }
    
    @Override
    @pausable
    public void call(PythonDynamicContext dc, PythonValueSetBuilder builder) {
        func.call(new BoundFunctionDynamicContextDecorator(dc, new PythonValueSet(self)), builder);
    }
    
    @Override
    public PythonType getType() {
        return FunctionType.instance;
    }
    
    @Override
    public void computeArgumentAliases(PassedArgumentInfo info, PythonDynamicContext dc, List<Alias> unodes) {
        func.computeArgumentAliases(info.translateToUnbound(), dc, unodes);
    }
    
}
