package com.yoursway.sadr.blocks.swamp.tests.mocks.frogs;

import com.yoursway.sadr.blocks.swamp.formulas.AbstractFormula;

public class VariableF extends AbstractFormula {
    
    private final String variableName;
    
    public VariableF(String variableName) {
        if (variableName == null)
            throw new NullPointerException("variableName is null");
        this.variableName = variableName;
    }
    
    public String getVariableName() {
        return variableName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((variableName == null) ? 0 : variableName.hashCode());
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
        VariableF other = (VariableF) obj;
        if (variableName == null) {
            if (other.variableName != null)
                return false;
        } else if (!variableName.equals(other.variableName))
            return false;
        return true;
    }
    
}
