package com.yoursway.sadr.blocks.foundation.valueinfo;

import static com.yoursway.sadr.python.core.typeinferencing.valuesets.ValueSetFactory.emptyValueSet;

import java.util.Collection;

import com.yoursway.sadr.blocks.foundation.types.Type;
import com.yoursway.sadr.blocks.foundation.typesets.TypeSet;
import com.yoursway.sadr.blocks.foundation.typesets.TypeSetBuilder;
import com.yoursway.sadr.blocks.foundation.values.Value;
import com.yoursway.sadr.core.IValueInfo;
import com.yoursway.sadr.python.core.typeinferencing.valuesets.ValueSet;

public class ValueInfo implements IValueInfo {
    
    private final TypeSet typeSet;
    private final ValueSet valueSet;
    
    public ValueInfo(ValueSet valueSet) {
    	TypeSetBuilder types = new TypeSetBuilder();
    	for (Value value : valueSet.containedValues()) {
    		types.add(value.getType());
		}
        this.typeSet = types.build();
        this.valueSet = valueSet;
    }
    
    public TypeSet getTypeSet() {
        return typeSet;
    }
    
    public ValueSet getValueSet() {
        return valueSet;
    }
    
    public static ValueInfo emptyValueInfo() {
        return new ValueInfo(emptyValueSet());
    }
    
    public static ValueInfo createResult(ValueSet valueSet) {
        return new ValueInfo(valueSet);
    }
    
    public boolean isEmpty() {
        return typeSet.isEmpty();
    }
    
    public String[] describePossibleTypes() {
        return typeSet.describePossibleTypes();
    }
    
    public String[] describePossibleValues() {
        return valueSet.describePossibleValues();
    }
    
    public Collection<Value> containedValues() {
        return valueSet.containedValues();
    }
    
    public Collection<Type> containedTypes() {
        return typeSet.containedTypes();
    }
    
    public static ValueInfo from(IValueInfo result) {
        if (result instanceof ValueInfo)
            return (ValueInfo) result;
        if (result.isEmpty())
            return emptyValueInfo();
        throw new IllegalArgumentException("Illegal input ValueInfo: " + result);
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((typeSet == null) ? 0 : typeSet.hashCode());
        result = prime * result + ((valueSet == null) ? 0 : valueSet.hashCode());
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
        final ValueInfo other = (ValueInfo) obj;
        if (typeSet == null) {
            if (other.typeSet != null)
                return false;
        } else if (!typeSet.equals(other.typeSet))
            return false;
        if (valueSet == null) {
            if (other.valueSet != null)
                return false;
        } else if (!valueSet.equals(other.valueSet))
            return false;
        return true;
    }
    
    @Override
    public String toString() {
    	return "types="+typeSet.toString()+"\n"+"values="+valueSet.toString();
    }
}
