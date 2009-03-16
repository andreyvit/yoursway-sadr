package com.yoursway.sadr.python.goals;

import kilim.pausable;

import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public class ExpressionValueGoal extends AbstractValueGoal {
    
    private final PythonConstruct construct;
    private final PythonDynamicContext dc;
    
    public ExpressionValueGoal(PythonConstruct construct, PythonDynamicContext dc) {
        if (construct == null)
            throw new NullPointerException("construct is null");
        if (dc == null)
            throw new NullPointerException("dc is null");
        this.construct = construct;
        this.dc = dc;
    }
    
    public Goal<PythonValueSet> cloneGoal() {
        return new ExpressionValueGoal(construct, dc);
    }
    
    public int debugSlot() {
        return 0;
    }
    
    @pausable
    public PythonValueSet evaluate() {
        return construct.evaluateValue(dc, InfoKind.VALUE);
    }
    
}
