package com.yoursway.sadr.python.analysis.objectmodel.values;

import java.util.List;

import kilim.pausable;

import com.yoursway.sadr.python.analysis.Alias;
import com.yoursway.sadr.python.analysis.context.dynamic.BoundFunctionDynamicContextDecorator;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.index.data.PassedArgumentInfo;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstruct;
import com.yoursway.sadr.python.analysis.objectmodel.types.FunctionType;
import com.yoursway.sadr.python.analysis.objectmodel.types.PythonType;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSetBuilder;

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
