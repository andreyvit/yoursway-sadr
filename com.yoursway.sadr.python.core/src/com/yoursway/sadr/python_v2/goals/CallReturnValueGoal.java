package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.python.core.runtime.contributions.Context;
import com.yoursway.sadr.python.core.typeinferencing.constructs.CallC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstructVisitor;
import com.yoursway.sadr.python.core.typeinferencing.constructs.ReturnC;
import com.yoursway.sadr.succeeder.CheckpointToken;

public class CallReturnValueGoal extends ContextSensitiveGoal {
    
    private final CallC call;
    
    public CallReturnValueGoal(CallC call, Context context) {
        super(context);
        this.call = call;
    }
    
    public void preRun() {
        if (getContext() == null) {
            //TODO schedule search and new goals for each result. 
        }
        evaluateInContext(getContext());
    }
    
    private void evaluateInContext(Context context) {
        call.traverse(new PythonConstructVisitor() {
            @Override
            public boolean visit(ReturnC construct) {
                ReturnC ret = construct;
                schedule(new ExpressionValueGoal(ret.getReturnedConstruct(), getContext(), acceptor));
                return false;
            }
        });
    }
    
    public CheckpointToken flush() {
        // TODO Auto-generated method stub
        return null;
    }
}
