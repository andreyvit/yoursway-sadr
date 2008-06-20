package com.yoursway.sadr.blocks.swamp.tests.mocks.frogs;

import com.yoursway.sadr.blocks.swamp.formulas.AbstractFormula;

public class LiteralF extends AbstractFormula {
    
    private final Object value;
    
    public LiteralF(Object value) {
        if (value == null)
            throw new NullPointerException("value is null");
        this.value = value;
    }

    public Object getValue() {
        return value;
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
        LiteralF other = (LiteralF) obj;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }
    
}
