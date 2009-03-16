package com.yoursway.sadr.python_v2.model.builtins.types;

import static com.google.common.collect.Lists.immutableList;

import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.python.parser.ast.PythonClassDeclaration;

import com.yoursway.sadr.blocks.foundation.types.Type;
import com.yoursway.sadr.python_v2.constructs.ClassDeclarationC;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;

/**
 * Represents a class type object. Supports class attributes name resolution.
 */
public abstract class PythonType extends PythonObject implements Type {
    protected static boolean hasType(List<PythonObject> objects, PythonType type) {
        for (PythonObject object : objects) {
            if (object.isInstance(type))
                return true;
        }
        return false;
    }

    PythonType() {
    }
    
    public PythonType(ClassDeclarationC decl) {
        super(decl);
    }
    
    public List<PythonType> getSuperClasses() {
        return immutableList((PythonType) ObjectType.instance);
    }
    
    @Override
    public PythonType getType() {
        return TypeType.instance;
    }
    
    @Override
    public String describe() {
        PythonConstruct decl = getDecl();
        if (decl == null) {
            return "type";
        }
        ASTNode node = decl.node();
        if (node instanceof PythonClassDeclaration) {
            return ((PythonClassDeclaration) node).getName();
        } else
            return "(unknown)";
    }
    
    @Override
    public PythonObject getScopedAttribute(String name) {
        return null;
    }
    
    @Override
    public boolean isInstance(PythonType type) {
        return this.equals(type) || getSuperClasses().contains(type);
    }
    
    public PythonValue coerce(PythonObject value) throws PythonException {
        throw new CoersionFailed();
    }
}