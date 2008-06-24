package com.yoursway.sadr.python_v2.model.builtins;

import static com.yoursway.sadr.python.core.typeinferencing.values.InstanceRegistrar.BUILTIN_INSTANCE_ID;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.yoursway.sadr.blocks.foundation.values.AbstractValue;
import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python.core.typeinferencing.values.InstanceRegistrar;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;

public class PythonObject implements RuntimeObject {
    
    Map<String, RuntimeObject> attributes = new HashMap<String, RuntimeObject>();
    private PythonClassType type;
    
    private PythonConstruct decl; // only if current object is made from this construct 
    private final int id;//object's identity
    
    public Map<String, RuntimeObject> getAttributes() {
        return attributes;
    }
    
    /**
     * This constructor is intended to be for built-ins only!
     */
    public PythonObject(PythonClassType type) {
        this.type = type;
        this.decl = null;
        this.id = BUILTIN_INSTANCE_ID;
    }
    
    /**
     * Creates object for a source code construct (<code>declaration</code>).
     * 
     * @param declaration
     *            a construct that creates the object; can not be null.
     */
    public PythonObject(PythonClassType type, PythonConstruct declaration) {
        if (declaration == null)
            throw new IllegalArgumentException();
        this.type = type;
        this.decl = declaration;
        this.id = InstanceRegistrar.registerInstance(this);
    }
    
    public RuntimeObject getAttribute(String name) {
        return attributes.get(name);
    }
    
    //TODO refactor setAttribute() method
    public void setAttribute(String name, RuntimeObject object) {
        if (null == name || null == object) {
            throw new IllegalArgumentException("setAttribute failed for class"
                    + this.getClass().getSimpleName());
        }
        attributes.put(name, object);
    }
    
    /**
     * Should be asynchronous
     */
    public PythonClassType getType() {
        return type;
    }
    
    public void setType(PythonClassType type) {
        this.type = type;
    }
    
    public Set<String> getAttributeNames() {
        return attributes.keySet();
    }
    
    @Override
    public String toString() {
        return this.describe();
    }
    
    public String describe() {
        return type.describe() + " " + " instance #" + id;
    }
    
    public AbstractValue cast(PythonValue<?> value) {
        return null;
    }
    
    public <T extends AbstractValue> T convertValue(Class<T> type) {
        return null;
    }
    
    public PythonConstruct getDecl() {
        return this.decl;
    }
    
    public void setDecl(PythonConstruct decl) {
        if (decl == null)
            throw new IllegalArgumentException();
        if (this.decl != null)
            throw new IllegalStateException();
        this.decl = decl;
    }
}
