package com.yoursway.sadr.python_v2.model.builtins;

import com.yoursway.sadr.blocks.foundation.values.AbstractValue;
import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.blocks.foundation.values.RuntimeValue;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;

public class PythonValue<ValueType extends AbstractValue> extends PythonObject implements
        RuntimeValue<ValueType> {
    
    private final ValueType value;
    
    /**
     * @param declaration
     *            a construct that creates the value; can not be null.
     */
    public PythonValue(PythonClassType type, ValueType value, PythonConstruct declaration) {
        super(type, declaration);
        if (value == null) {
            throw new NullPointerException("value is null");
        }
        this.value = value;
    }
    
    /**
     * For only instances that have no source code constructs creating them such
     * as built-ins.
     */
    public PythonValue(PythonClassType type, ValueType value) {
        super(type);
        if (value == null) {
            throw new NullPointerException("value is null");
        }
        this.value = value;
    }
    
    @Override
    public RuntimeObject getScopedAttribute(String name) {
        RuntimeObject attribute = super.getScopedAttribute(name);
        if (attribute == null) {
            attribute = this.getType().getScopedAttribute(name);
        }
        return attribute;
    }
    
    public ValueType getValue() {
        return value;
    }
    
    @Override
    public String describe() {
        return value.describe();
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public <T extends AbstractValue> T convertValue(Class<T> type) {
        if (!(type.isInstance(value))) {
            return null;
        }
        AbstractValue value = getValue();
        return (T) value;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final PythonValue<?> other = (PythonValue<?>) obj;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }
    
}
