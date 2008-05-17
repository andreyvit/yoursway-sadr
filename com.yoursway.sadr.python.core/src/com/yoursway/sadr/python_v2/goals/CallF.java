package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.python.core.typeinferencing.constructs.Frog;
import com.yoursway.sadr.python.core.typeinferencing.values.InstanceValue;
import com.yoursway.sadr.python_v2.goals.internal.CreateInstanceGoal;
import com.yoursway.sadr.python_v2.model.RuntimeObject;

public class CallF extends Frog {
    
    private final Frog expression;
    
    public CallF(Frog expression) {
        if (expression == null)
            throw new NullPointerException("expression is null");
        this.expression = expression;
    }
    
    @Override
    public Frog replace(Frog lhs, Frog rhs) {
        if (lhs.equals(this))
            return rhs;
        return new CallF(expression.replace(lhs, rhs));
    }
    
    @Override
    public RuntimeObject compact() {
        if (expression instanceof UserClassF) {
            UserClassF klass = (UserClassF) expression;
            return new InstanceValue(klass.createType(), CreateInstanceGoal.instanceRegistrar);
        }
        return null;
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + expression + ")";
    }
}
