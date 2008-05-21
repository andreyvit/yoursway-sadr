package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.python.core.typeinferencing.constructs.ClassDeclarationC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.goals.internal.CallResolver;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.PythonArguments;
import com.yoursway.sadr.python_v2.model.RuntimeObject;
import com.yoursway.sadr.python_v2.model.builtins.FunctionObject;
import com.yoursway.sadr.succeeder.IGrade;

public class FindClassMethodGoal extends ResolveNameToObjectGoal {
    private final ClassDeclarationC decl;
    
    public FindClassMethodGoal(final ClassDeclarationC decl, final String name, final PythonArguments args,
            final PythonValueSetAcceptor parentAcceptor, final Context context) {
        super(decl, name, null, context);
        this.setAcceptor(new PythonValueSetAcceptor() {
            
            public <T> void checkpoint(IGrade<T> grade) {
                RuntimeObject result = getResultByContext(context);
                if (result == null) {
                    acceptor().addResult(null, context);
                    updateGrade(acceptor(), grade);
                    return;
                }
                schedule(CallResolver.callFunction((FunctionObject) result, args, parentAcceptor, context));
            }
            
        });
        this.decl = decl;
        
    }
    
    @Override
    public void preRun() {
        PythonConstruct result = findInScope(decl);
        processResultConstruct(result);
    }
}
