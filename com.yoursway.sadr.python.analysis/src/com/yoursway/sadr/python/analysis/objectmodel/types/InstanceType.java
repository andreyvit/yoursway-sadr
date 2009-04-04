/**
 * 
 */
package com.yoursway.sadr.python.analysis.objectmodel.types;

import java.util.ArrayList;

import kilim.pausable;

import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.lang.unodes.indexable.AttributeUnode;
import com.yoursway.sadr.python.analysis.lang.unodes.indexable.VariableUnode;
import com.yoursway.sadr.python.analysis.objectmodel.values.CallableObject;
import com.yoursway.sadr.python.analysis.objectmodel.values.InstanceValue;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSetBuilder;

public class InstanceType extends PythonType implements CallableObject {
    private ArrayList<PythonType> supers = new ArrayList<PythonType>();
    private final String name;
    
    public InstanceType(String name) {
        if (name == null)
            throw new NullPointerException("name is null");
        this.name = name;
    }
    
    @Override
    public String describe() {
        return name;
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
    public String name() {
        return name;
    }
    
    @Override
    @pausable
    public void call(PythonDynamicContext dc, PythonValueSetBuilder builder) {
        builder.addResult(new InstanceValue(this));
    }
    
    @Override
    @pausable
    public PythonValueSet getAttr(String attrName, PythonLexicalContext sc, PythonDynamicContext dc) {
        System.out.println("InstanceType.getAttr(" + name() + ", " + attrName + ")");
        return new AttributeUnode(new VariableUnode(name()), attrName).calculateValue(sc, dc);
    }
    
}