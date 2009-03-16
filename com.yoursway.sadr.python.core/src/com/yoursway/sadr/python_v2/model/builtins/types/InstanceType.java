/**
 * 
 */
package com.yoursway.sadr.python_v2.model.builtins.types;

import java.util.ArrayList;

import com.yoursway.sadr.python_v2.constructs.ClassDeclarationC;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.CreateInstanceGoal;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.model.RuntimeArguments;
import com.yoursway.sadr.python_v2.model.builtins.values.CallableObject;

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
    
    public PythonValueSet call(Krocodile crocodile, RuntimeArguments args) {
        return new CreateInstanceGoal(this, args, crocodile).evaluate();
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