package com.yoursway.sadr.python_v2.model.builtins;

import com.yoursway.sadr.python_v2.constructs.PythonCallable;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.model.PythonArguments;

public class FunctionObject extends PythonObject {
    private final String name;
    private PythonClassType boundClass = null;
    
    public FunctionObject(PythonCallable decl) {
        super(Builtins.FUNCTION, decl);
        this.name = decl.name();
        setAttribute("__name__", StringType.wrap(name));
    }
    
    public FunctionObject(String name) {
        super(Builtins.FUNCTION);
        this.name = name;
        setAttribute("__name__", StringType.wrap(name));
    }
    
    public boolean isBound() {
        return boundClass != null;
    }
    
    public PythonClassType getBoundClass() {
        return boundClass;
    }
    
    @Override
    public String describe() {
        return "function " + name();
    }
    
    public String name() {
        return name;
    }
    
    public void bind(PythonClassType instanceType) {
        this.boundClass = instanceType;
    }
    
    @Override
    public PythonCallable getDecl() {
        return (PythonCallable) super.getDecl();
    }
    
    public PythonValueSet call(Krocodile crocodile, PythonArguments args) {
        if (getDecl() == null) {
            throw new IllegalStateException();
        }
        return getDecl().call(crocodile, args);
    }
    
    @Override
    public PythonValueSet getAttribute(Krocodile crocodile) {
        return super.getAttribute(crocodile);
    }
}
