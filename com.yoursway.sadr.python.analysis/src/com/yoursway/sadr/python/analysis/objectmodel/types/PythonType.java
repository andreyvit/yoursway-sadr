package com.yoursway.sadr.python.analysis.objectmodel.types;

import static com.google.common.collect.Lists.immutableList;

import java.util.List;

import kilim.pausable;

import com.yoursway.sadr.blocks.foundation.types.Type;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.objectmodel.values.PythonValue;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

/**
 * Represents a class type object. Supports class attributes name resolution.
 */
public abstract class PythonType extends PythonValue implements Type {
    
    PythonType() {
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
        return "type";
    }
    
    @Override
    public PythonValue getBuiltinAttribute(String name) {
        return null;
    }
    
    @Override
    public boolean isInstance(PythonType type) {
        return this.equals(type) || getSuperClasses().contains(type);
    }
    
    public PythonValue coerce(PythonValue value) throws PythonException {
        throw new CoersionFailed();
    }
    
    protected boolean hasType(List<PythonValue> objects) {
        for (PythonValue object : objects) {
            if (object.isInstance(this))
                return true;
        }
        return false;
    }
    
    @pausable
    public PythonValueSet getAttr(String name, PythonLexicalContext sc, PythonDynamicContext dc) {
        return PythonValueSet.EMPTY;
    }
    
}
