package com.yoursway.sadr.python_v2.model.builtins;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.python.parser.ast.PythonClassDeclaration;

import com.yoursway.sadr.python.core.runtime.PythonMethod;
import com.yoursway.sadr.python.core.runtime.requestors.methods.MethodRequestor;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.model.RuntimeObject;

/**
 * Represents a class type object. Supports class attributes name resolution.
 */
public class PythonClassType extends PythonObject {
    
    private List<PythonClassType> supers;
    
    public PythonClassType() {
        super(Builtins.TYPE);
        supers = new ArrayList<PythonClassType>(1);
        supers.add(Builtins.OBJECT);
        
    }
    
    public PythonClassType(PythonClassType superClass) {
        super(Builtins.TYPE);
        supers = new ArrayList<PythonClassType>(1);
        supers.add(superClass);
    }
    
    public PythonClassType(List<PythonClassType> supers) {
        super(Builtins.TYPE);
        setSuperClasses(supers);
    }
    
    public List<PythonClassType> getSuperClasses() {
        return supers;
    }
    
    public void setSuperClasses(List<PythonClassType> supers) {
        if (supers == null)
            this.supers = new ArrayList<PythonClassType>(0);
        else
            this.supers = supers;
    }
    
    @Override
    public RuntimeObject getAttribute(String name) {
        if (attributes.containsKey(name))
            return attributes.get(name);
        else
            return lookupInSuperclasses(name);
        
    }
    
    protected RuntimeObject lookupInSuperclasses(String name) {
        for (PythonClassType cls : supers) {
            RuntimeObject object = cls.getDict().get(name);
            if (object != null)
                return object;
        }
        return null;
    }
    
    public void setAttribute(FunctionObject object) {
        setAttribute(object.name(), object);
    }
    
    @Override
    public String describe() {
        PythonConstruct decl = instanceHistory().sourceDeclaration();
        if (decl == null) {
            return "type";
        }
        ASTNode node = decl.node();
        if (node instanceof PythonClassDeclaration) {
            return ((PythonClassDeclaration) node).getName();
        } else
            return "(unknown)";
    }
    
    public void findMethodsByPrefix(String prefix, Collection<PythonMethod> methods) {
        // TODO Auto-generated method stub
        
    }
    
    public void findMethod(String name, MethodRequestor requestor) {
        // TODO Auto-generated method stub
        
    }
}