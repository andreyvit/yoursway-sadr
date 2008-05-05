package com.yoursway.sadr.python_v2.model.builtins;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.python.parser.ast.PythonClassDeclaration;

import com.yoursway.sadr.python_v2.model.RuntimeObject;

/**
 * Represents a class type object. Supports class attributes name resolution.
 */
public class PythonClassType extends PythonObject implements PythonClass {
    
    private List<PythonClass> supers;
    
    public PythonClassType() {
        super(Builtins.TYPE);
        supers = new ArrayList<PythonClass>(1);
        supers.add(Builtins.OBJECT);
    }
    
    public PythonClassType(List<PythonClass> supers) {
        super(Builtins.TYPE);
        this.supers = supers;
    }
    
    public List<PythonClass> getSuperClasses() {
        return supers;
    }
    
    public void setSuperClasses(List<PythonClass> supers) {
        this.supers = supers;
    }
    
    @Override
    protected RuntimeObject lookupInSuperclasses(String name) {
        for (PythonClass cls : this.supers) {
            RuntimeObject object = cls.getDict().get(name);
            if (object != null)
                return object;
        }
        return null;
    }
    
    public String describe() {
        ASTNode node = instanceHistory().sourceDeclaration().node();
        if (node instanceof PythonClassDeclaration) {
            return ((PythonClassDeclaration) node).getName();
        } else
            return "(unknown)";
    }
}