package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.python_v2.constructs.ClassDeclarationC;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.model.Context;

public class FindClassMethodGoal extends ResolveNameToObjectGoal {
    private final ClassDeclarationC decl;
    
    public FindClassMethodGoal(final ClassDeclarationC decl, final String name,
            final PythonValueSetAcceptor acceptor, final Context context) {
        super(decl, name, acceptor, context);
        this.decl = decl;
        
    }
    
    @Override
    public void preRun() {
        PythonConstruct result = findInScope(decl);
        processResultConstruct(result);
    }
}
