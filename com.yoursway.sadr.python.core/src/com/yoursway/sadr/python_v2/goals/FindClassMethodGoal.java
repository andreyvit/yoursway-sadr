package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python_v2.constructs.ClassDeclarationC;
import com.yoursway.sadr.python_v2.constructs.PythonVariableAcceptor;
import com.yoursway.sadr.python_v2.croco.Frog;
import com.yoursway.sadr.python_v2.model.Context;

public class FindClassMethodGoal extends ResolveNameToObjectGoal {
    
    public FindClassMethodGoal(final ClassDeclarationC decl, final Frog matcher,
            final PythonVariableAcceptor acceptor, final Context context) {
        super(matcher, decl, context, acceptor);
    }
    
    @Override
    public void preRun() {
        //        boolean result = findInScope(decl, processor);
        updateGrade(resultsAcceptor(), Grade.DONE);
    }
}
