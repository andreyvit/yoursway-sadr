package com.yoursway.sadr.python.goals;

import static com.yoursway.utils.DebugOutputHelper.reflectionBasedToString;

import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public abstract class AbstractValueGoal implements Goal<PythonValueSet> {
    
    public boolean cachable() {
        return true;
    }
    
    public PythonValueSet createRecursiveResult() {
        return PythonValueSet.EMPTY;
    }
    
    public boolean hasComplexUnnaturalRelationshipWithRecursion() {
        return false;
    }
    
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        String simpleName = getClass().getSimpleName().replaceAll("ValueGoal", "VG");
        s.append(simpleName).append(" <").append(describeParameters()).append(">");
        return s.toString();
    }
    
    protected String describeParameters() {
        return reflectionBasedToString(this);
    }
    
}
