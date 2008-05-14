package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.python.core.typeinferencing.constructs.ClassDeclarationC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.model.Context;

public class FindClassMethodGoal extends ResolveNameToObjectGoal {
    private final ClassDeclarationC decl;
    
    public FindClassMethodGoal(ClassDeclarationC decl, String name, PythonValueSetAcceptor acceptor,
            Context context) {
        super(null, name, acceptor, context);
        this.decl = decl;
    }
    
    @Override
    public void preRun() {
        PythonConstruct result = findInScope(decl);
        processResultConstruct(result);
    }
}
