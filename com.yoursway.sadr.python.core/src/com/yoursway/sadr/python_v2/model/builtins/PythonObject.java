package com.yoursway.sadr.python_v2.model.builtins;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.python.parser.ast.PythonClassDeclaration;

import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.model.InstanceHistory;
import com.yoursway.sadr.python_v2.model.InstanceHistoryImpl;
import com.yoursway.sadr.python_v2.model.RuntimeObject;

public class PythonObject implements RuntimeObject {
    
    private final InstanceHistoryImpl history;
    
    Map<String, RuntimeObject> attributes = new HashMap<String, RuntimeObject>();
    private PythonClass type;
    
    protected RuntimeObject lookupInSuperclasses(String name) {
        for (PythonClass cls : type.getSuperClasses()) {
            RuntimeObject object = cls.getDict().get(name);
            if (object != null)
                return object;
        }
        return null;
    }
    
    public Map<String, RuntimeObject> getDict() {
        return attributes;
    }
    
    public PythonObject(PythonClass type) {
        //assert type != null;
        this.type = type;
        history = new InstanceHistoryImpl(this, null);
    }
    
    public PythonObject(PythonClass type, PythonConstruct declaration) {
        this.type = type;
        history = new InstanceHistoryImpl(this, declaration);
    }
    
    public RuntimeObject getAttribute(String name) {
        //TODO try __getattribute__ execution.
        if (!attributes.containsKey(name)) {
            // TODO try __getattr__ execution
            if (this.type.getDict().containsKey(name))
                return type.getDict().get(name);
            return lookupInSuperclasses(name);
        }
        return attributes.get(name);
    }
    
    //TODO refactor setAttribute() method
    public void setAttribute(String name, RuntimeObject object) {
        assert null != name && null != object;
        //TODO try __setattr__ execution
        attributes.put(name, object);
    }
    
    public RuntimeObject getType() {
        return type;
    }
    
    public void setType(PythonClass type) {
        this.type = type;
    }
    
    public Set<String> getAttributeNames() {
        return attributes.keySet();
    }
    
    public InstanceHistory instanceHistory() {
        return history;
    }
    
    @Override
    public String toString() {
        return this.describe();
    }
    
    public String describe() {
        PythonConstruct declaration = instanceHistory().sourceDeclaration();
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
}
