package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python_v2.constructs.ClassDeclarationC;
import com.yoursway.sadr.python_v2.constructs.Frog;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.model.Context;

public class FindClassMethodGoal extends ResolveNameToObjectGoal {
    private final ClassDeclarationC decl;
    
    public FindClassMethodGoal(final ClassDeclarationC decl, final Frog matcher,
            final PythonValueSetAcceptor acceptor, final Context context) {
        super(decl, matcher, acceptor, context);
        this.decl = decl;
        
    }
    
    @Override
    public void preRun() {
        final boolean[] result = { false };
        ConstructRequestor processor = new ConstructRequestor() {
            @Override
            public void yield(PythonConstruct construct) {
                processResultConstruct(construct);
            }
        };
        
        result[0] = findInScope(decl, processor);
        updateGrade(acceptor(), Grade.DONE);
    }
}
