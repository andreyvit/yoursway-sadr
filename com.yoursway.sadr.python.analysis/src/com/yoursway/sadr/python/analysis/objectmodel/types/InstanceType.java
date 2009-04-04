/**
 * 
 */
package com.yoursway.sadr.python.analysis.objectmodel.types;

import java.util.ArrayList;

import kilim.pausable;

import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonStaticContext;
import com.yoursway.sadr.python.analysis.lang.constructs.ast.ClassDeclarationC;
import com.yoursway.sadr.python.analysis.lang.unodes.indexable.AttributeUnode;
import com.yoursway.sadr.python.analysis.lang.unodes.indexable.VariableUnode;
import com.yoursway.sadr.python.analysis.objectmodel.values.CallableObject;
import com.yoursway.sadr.python.analysis.objectmodel.values.InstanceValue;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSetBuilder;

public class InstanceType extends PythonType implements CallableObject {
    private ArrayList<PythonType> supers = new ArrayList<PythonType>();
    
    public InstanceType(ClassDeclarationC classDeclarationC) {
        super(classDeclarationC);
    }
    
    public void setSuperClasses(ArrayList<PythonType> supers) {
        this.supers = supers;
    }
    
    @Override
    public ArrayList<PythonType> getSuperClasses() {
        return supers;
    }
    
    @Override
    public PythonType getType() {
        return TypeType.instance;
    }
    
    @Override
    public ClassDeclarationC getDecl() {
        return (ClassDeclarationC) super.getDecl();
    }
    
    @Override
    public String name() {
        return getDecl().name();
    }
    
    @Override
    @pausable
    public void call(PythonDynamicContext dc, PythonValueSetBuilder builder) {
        builder.addResult(new InstanceValue(this));
    }
    
    @Override
    @pausable
    public PythonValueSet getAttr(String attrName, PythonStaticContext sc, PythonDynamicContext dc) {
        System.out.println("InstanceType.getAttr(" + name() + ", " + attrName + ")");
        return new AttributeUnode(new VariableUnode(name()), attrName).calculateValue(sc, dc);
    }
    
}