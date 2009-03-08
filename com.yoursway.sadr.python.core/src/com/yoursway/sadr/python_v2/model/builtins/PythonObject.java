package com.yoursway.sadr.python_v2.model.builtins;

import static com.yoursway.sadr.python_v2.model.builtins.values.InstanceRegistrar.BUILTIN_INSTANCE_ID;

import com.yoursway.sadr.blocks.foundation.values.AbstractValue;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.model.builtins.types.PythonException;
import com.yoursway.sadr.python_v2.model.builtins.types.PythonType;
import com.yoursway.sadr.python_v2.model.builtins.values.InstanceRegistrar;

public abstract class PythonObject extends AbstractValue {
    private PythonConstruct decl;
    private final int id;
    
    public PythonObject() {
        this.decl = null;
        this.id = BUILTIN_INSTANCE_ID;
    }
    
    public PythonObject(PythonConstruct declaration) {
        this.decl = declaration;
        this.id = InstanceRegistrar.registerInstance(this);
    }
    
    public abstract PythonType getType();
    
    @Override
    public String describe() {
        return getType().describe() + " " + " instance #" + id;
    }
    
    public PythonConstruct getDecl() {
        return this.decl;
    }
    
    public void setDecl(PythonConstruct decl) {
        if (this.decl != null)
            throw new IllegalStateException("Already assigned");
        if (decl == null)
            throw new NullPointerException("Decl should never be null!");
        this.decl = decl;
    }
    
    public PythonObject getScopedAttribute(String name) {
        return this.getType().getScopedAttribute(name);
    }
    
    public String name() {
        return null;
    }
    
    public boolean isInstance(PythonType type) {
        return getType().isInstance(type);
    }
    
    public PythonObject cast(PythonType type) throws PythonException {
        return type.coerce(this);
    }
}
