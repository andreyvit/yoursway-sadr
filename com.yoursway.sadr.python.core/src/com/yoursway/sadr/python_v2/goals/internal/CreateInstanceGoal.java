/**
 * 
 */
package com.yoursway.sadr.python_v2.goals.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.yoursway.sadr.python.core.typeinferencing.constructs.ClassDeclarationC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.services.InstanceRegistrar;
import com.yoursway.sadr.python.core.typeinferencing.values.InstanceRegistrarImpl;
import com.yoursway.sadr.python.core.typeinferencing.values.InstanceValue;
import com.yoursway.sadr.python_v2.goals.ContextSensitiveGoal;
import com.yoursway.sadr.python_v2.goals.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.goals.ResultsCollector;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.RuntimeObject;
import com.yoursway.sadr.python_v2.model.builtins.PythonClassType;
import com.yoursway.sadr.succeeder.IGrade;

public final class CreateInstanceGoal extends ContextSensitiveGoal {
    private final PythonValueSetAcceptor acceptor;
    private final ClassDeclarationC classDeclarationC;
    //  private final List<RuntimeObject> args;
    
    static InstanceRegistrar instanceRegistrar = new InstanceRegistrarImpl();
    
    public CreateInstanceGoal(ClassDeclarationC decl, List<RuntimeObject> args,
            HashMap<String, RuntimeObject> kwargs, Context context, PythonValueSetAcceptor acceptor) {
        super(context);
        this.acceptor = acceptor;
        this.classDeclarationC = decl;
        //        this.args = args;
    }
    
    public void preRun() {
        List<PythonConstruct> superClasses = classDeclarationC.getSuperClasses();
        ResultsCollector rc = new ResultsCollector(superClasses.size(), getContext()) {
            @Override
            public <T> void completed(IGrade<T> grade) {
                List<PythonClassType> supers = new ArrayList<PythonClassType>();
                for (RuntimeObject obj : getResults()) {
                    supers.add((PythonClassType) obj);
                }
                PythonClassType receiverType = new PythonUserClassType(classDeclarationC, supers);
                InstanceValue receiver = new InstanceValue(receiverType, instanceRegistrar);
                acceptor.addResult(receiver, getContext());
                updateGrade(acceptor, grade);
                //                schedule(CallResolver.callFunction(receiver, "__init__", args, acceptor, getContext()));
            }
        };
        schedule(rc.addSubgoals(superClasses));
        rc.startCollecting();
    }
    
    @Override
    public String describe() {
        return super.describe() + "\nfor " + classDeclarationC.displayName();
    }
}