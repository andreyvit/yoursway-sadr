package com.yoursway.sadr.python.core.typeinferencing.constructs;

import com.yoursway.sadr.python_v2.goals.ContextSensitiveGoal;
import com.yoursway.sadr.python_v2.goals.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.RuntimeObject;

public class FindClassAttributeGoal extends ContextSensitiveGoal {
    
    private final RuntimeObject object;
    private final PythonValueSetAcceptor acceptor;
    private final PythonConstruct variable;
    
    public FindClassAttributeGoal(RuntimeObject object, PythonConstruct variable,
            PythonValueSetAcceptor acceptor, Context context) {
        super(context);
        this.object = object;
        this.variable = variable;
        this.acceptor = acceptor;
    }
    
    public void preRun() {
        // TODO Auto-generated method stub
        
    }
}
