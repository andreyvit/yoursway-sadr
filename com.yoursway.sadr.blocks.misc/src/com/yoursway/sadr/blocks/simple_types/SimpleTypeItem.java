package com.yoursway.sadr.blocks.simple_types;

import com.yoursway.sadr.blocks.foundation.types.AbstractType;

public class SimpleTypeItem extends AbstractType {
    
    private final SimpleType simpleType;
    
    public SimpleTypeItem(SimpleType simpleType) {
        if (simpleType == null)
            throw new NullPointerException("simpleType is null");
        this.simpleType = simpleType;
    }
    
    @Override
    public String toString() {
        return simpleType.name();
    }
    
    public SimpleType simpleType() {
        return simpleType;
    }
    
    public String describe() {
        return simpleType.name();
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((simpleType == null) ? 0 : simpleType.hashCode());
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
        final SimpleTypeItem other = (SimpleTypeItem) obj;
        if (simpleType == null) {
            if (other.simpleType != null)
                return false;
        } else if (!simpleType.equals(other.simpleType))
            return false;
        return true;
    }
    
}
