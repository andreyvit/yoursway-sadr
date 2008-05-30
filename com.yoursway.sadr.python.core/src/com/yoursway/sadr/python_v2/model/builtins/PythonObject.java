package com.yoursway.sadr.python_v2.model.builtins;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.python.parser.ast.PythonClassDeclaration;

import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.model.InstanceHistory;
import com.yoursway.sadr.python_v2.model.InstanceHistoryImpl;
import com.yoursway.sadr.python_v2.model.RuntimeObject;

public class PythonObject implements RuntimeObject {
    
    private final InstanceHistoryImpl history;
    
    Map<String, RuntimeObject> attributes = new HashMap<String, RuntimeObject>();
    private PythonClassType type;
    
    public Map<String, RuntimeObject> getAttributes() {
        return attributes;
    }
    
    public PythonObject(PythonClassType type) {
        //assert type != null;
        this.type = type;
        history = new InstanceHistoryImpl(this, null);
    }
    
    public PythonObject(PythonClassType type, PythonConstruct declaration) {
        this.type = type;
        history = new InstanceHistoryImpl(this, declaration);
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
    public RuntimeObject getType() {
        return type;
    }
    
    public void setType(PythonClassType type) {
        this.type = type;
    }
    
    public Set<String> getAttributeNames() {
        return attributes.keySet();
    }
    
    /**
     * Remove?
     */
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
    
    public <T> T convertValue(RuntimeObject type) {
        return null;
    }
    
    public PythonConstruct getDecl() {
        return history.sourceDeclaration();
    }
}
