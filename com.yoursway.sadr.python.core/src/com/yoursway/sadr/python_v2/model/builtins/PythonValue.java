package com.yoursway.sadr.python_v2.model.builtins;

import static com.yoursway.sadr.python.model.values.InstanceRegistrar.BUILTIN_INSTANCE_ID;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import kilim.pausable;

import com.yoursway.sadr.blocks.foundation.values.AbstractValue;
import com.yoursway.sadr.python.constructs.PythonConstruct;
import com.yoursway.sadr.python.constructs.PythonStaticContext;
import com.yoursway.sadr.python.index.punodes.Punode;
import com.yoursway.sadr.python.index.unodes.Bnode;
import com.yoursway.sadr.python.model.PassedArgumentInfo;
import com.yoursway.sadr.python.model.types.PythonException;
import com.yoursway.sadr.python.model.types.PythonType;
import com.yoursway.sadr.python.model.values.InstanceRegistrar;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetBuilder;

public abstract class PythonValue extends AbstractValue {
    private PythonConstruct decl;
    private final int id;
    
    public PythonValue() {
        this.decl = null;
        this.id = BUILTIN_INSTANCE_ID;
    }
    
    public PythonValue(PythonConstruct declaration) {
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
    
    public PythonValue getBuiltinAttribute(String name) {
        return this.getType().getBuiltinAttribute(name);
    }
    
    public String name() {
        return null;
    }
    
    public boolean isInstance(PythonType type) {
        return getType().isInstance(type);
    }
    
    public PythonValue cast(PythonType type) throws PythonException {
        return type.coerce(this);
    }
    
    @pausable
    public void call(PythonDynamicContext dc, PythonValueSetBuilder builder) {
    }
    
    @pausable
    public void getAttrFromType(String name, PythonStaticContext sc, PythonDynamicContext dc,
            PythonValueSetBuilder builder) {
        getType().getAttr(name, sc, dc).bind(this, builder);
    }
    
    public void bind(PythonValue self, PythonValueSetBuilder builder) {
        builder.addResult(this);
    }
    
    public void obtainIntegerValue(Set<Long> result) {
    }
    
    public void computeArgumentAliases(PassedArgumentInfo info, PythonDynamicContext dc, List<Bnode> unodes) {
    }
    
    @pausable
    public void findRenames(Punode punode, PythonStaticContext sc, PythonDynamicContext dc,
            Collection<Bnode> aliases) {
    }
    
}
