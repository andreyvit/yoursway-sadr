package com.yoursway.sadr.blocks.swamp.tests.mocks.frogs;

import com.yoursway.sadr.blocks.swamp.formulas.AbstractFormula;
import com.yoursway.sadr.blocks.swamp.formulas.Formula;

public class DotAccessF extends AbstractFormula {
    
    private final Formula receiver;
    private final String fieldName;
    
    public DotAccessF(Formula receiver, String fieldName) {
        if (receiver == null)
            throw new NullPointerException("receiver is null");
        if (fieldName == null)
            throw new NullPointerException("fieldName is null");
        this.receiver = receiver;
        this.fieldName = fieldName;
    }
    
    public Formula getReceiver() {
        return receiver;
    }
    
    public String getFieldName() {
        return fieldName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((fieldName == null) ? 0 : fieldName.hashCode());
        result = prime * result + ((receiver == null) ? 0 : receiver.hashCode());
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
        DotAccessF other = (DotAccessF) obj;
        if (fieldName == null) {
            if (other.fieldName != null)
                return false;
        } else if (!fieldName.equals(other.fieldName))
            return false;
        if (receiver == null) {
            if (other.receiver != null)
                return false;
        } else if (!receiver.equals(other.receiver))
            return false;
        return true;
    }
    
}
