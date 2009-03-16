/**
 * 
 */
package com.yoursway.sadr.python.model.types;

import java.util.ArrayList;

import com.yoursway.sadr.python.constructs.ClassDeclarationC;
import com.yoursway.sadr.python.model.values.CallableObject;
import com.yoursway.sadr.python.objects.RuntimeArguments;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

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
    
    public PythonValueSet call(PythonDynamicContext crocodile, RuntimeArguments args) {
        //        return new CreateInstanceGoal(this, args, crocodile).evaluate();
        throw new UnsupportedOperationException();
    }
    
    @Override
    public ClassDeclarationC getDecl() {
        return (ClassDeclarationC) super.getDecl();
    }
    
    @Override
    public String name() {
        return getDecl().name();
    }
}