/**
 * 
 */
package com.yoursway.sadr.python.model.types;

import java.util.ArrayList;

import com.yoursway.sadr.python.constructs.ClassDeclarationC;
import com.yoursway.sadr.python.model.values.CallableObject;
import com.yoursway.sadr.python.model.values.InstanceValue;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetBuilder;

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
    public void call(PythonDynamicContext dc, PythonValueSetBuilder builder) {
        builder.addResult(new InstanceValue(this));
    }
    
}