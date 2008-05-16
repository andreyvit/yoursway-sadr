package com.yoursway.sadr.python_v2.goals;

import java.util.HashMap;
import java.util.List;

import com.yoursway.sadr.python.core.typeinferencing.constructs.ClassDeclarationC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.goals.internal.CallResolver;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.RuntimeObject;
import com.yoursway.sadr.python_v2.model.builtins.FunctionObject;
import com.yoursway.sadr.succeeder.IGrade;

public class FindClassMethodGoal extends ResolveNameToObjectGoal {
    private final ClassDeclarationC decl;
    
    public FindClassMethodGoal(ClassDeclarationC decl, String name, final List<RuntimeObject> args,
            final HashMap<String, RuntimeObject> kwargs, final PythonValueSetAcceptor parentAcceptor,
            final Context context) {
        super(decl, name, null, context);
        this.setAcceptor(new PythonValueSetAcceptor() {
            
            public <T> void checkpoint(IGrade<T> grade) {
                RuntimeObject result = getResultByContext(context);
                schedule(CallResolver.callFunction((FunctionObject) result, args, kwargs, parentAcceptor,
                        context));
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
