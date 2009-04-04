package com.yoursway.sadr.python.analysis.goals;

import kilim.pausable;

import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.lang.unodes.Bnode;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public class ExpressionValueGoal extends AbstractValueGoal {
    
    private final Bnode construct;
    private final PythonDynamicContext dc;
    private final int hashCode;
    
    public ExpressionValueGoal(Bnode construct, PythonDynamicContext dc) {
        if (construct == null)
            throw new NullPointerException("construct is null");
        if (dc == null)
            throw new NullPointerException("dc is null");
        this.construct = construct;
        this.dc = dc;
        this.hashCode = computeHashCode();
    }
    
    public Goal<PythonValueSet> cloneGoal() {
        return new ExpressionValueGoal(construct, dc);
    }
    
    public int debugSlot() {
        return 0;
    }
    
    @pausable
    public PythonValueSet evaluate() {
        return construct.toAlias(dc).calculateValue();
    }
    
    @Override
    public int hashCode() {
        return hashCode;
    }
    
    private int computeHashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((construct == null) ? 0 : construct.hashCode());
        result = prime * result + ((dc == null) ? 0 : dc.hashCode());
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
        ExpressionValueGoal other = (ExpressionValueGoal) obj;
        if (construct == null) {
            if (other.construct != null)
                return false;
        } else if (!construct.equals(other.construct))
            return false;
        if (dc == null) {
            if (other.dc != null)
                return false;
        } else if (!dc.equals(other.dc))
            return false;
        return true;
    }
    
}
