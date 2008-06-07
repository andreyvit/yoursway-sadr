package com.yoursway.sadr.python_v2.model.builtins;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.python.parser.ast.PythonClassDeclaration;

import com.yoursway.sadr.blocks.foundation.values.AbstractValue;
import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;

public class PythonObject implements RuntimeObject {
    
    Map<String, RuntimeObject> attributes = new HashMap<String, RuntimeObject>();
    private PythonClassType type;
    
    private final PythonConstruct decl; // only if current object is made from this construct 
    
    public Map<String, RuntimeObject> getAttributes() {
        return attributes;
    }
    
    public PythonObject(PythonClassType type) {
        //assert type != null;
        this.type = type;
        this.decl = null;
    }
    
    public PythonObject(PythonClassType type, PythonConstruct declaration) {
        this.type = type;
        this.decl = declaration;
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
        PythonConstruct declaration = getDecl();
        if (declaration == null) {
            throw new IllegalStateException("No declarations found. Can't describe "
                    + this.getClass().getSimpleName() + " instance.");
        }
        ASTNode node = declaration.node();
        if (node instanceof PythonClassDeclaration) {
            return ((PythonClassDeclaration) node).getName();
        } else
            return "(unknown object)";
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
}
