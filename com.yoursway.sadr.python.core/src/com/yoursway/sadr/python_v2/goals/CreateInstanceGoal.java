/**
 * 
 */
package com.yoursway.sadr.python_v2.goals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yoursway.sadr.python.core.typeinferencing.services.InstanceRegistrar;
import com.yoursway.sadr.python.core.typeinferencing.values.InstanceRegistrarImpl;
import com.yoursway.sadr.python.core.typeinferencing.values.InstanceValue;
import com.yoursway.sadr.python_v2.constructs.ClassDeclarationC;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.goals.acceptors.ResultsCollector;
import com.yoursway.sadr.python_v2.goals.internal.PythonUserClassType;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.PythonArguments;
import com.yoursway.sadr.python_v2.model.RuntimeObject;
import com.yoursway.sadr.python_v2.model.builtins.FunctionObject;
import com.yoursway.sadr.python_v2.model.builtins.PythonClassType;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;
import com.yoursway.sadr.succeeder.IGrade;

public class CreateInstanceGoal extends ContextSensitiveGoal {
    private final PythonValueSetAcceptor acceptor;
    private final ClassDeclarationC classDeclarationC;
    
    public static InstanceRegistrar instanceRegistrar = new InstanceRegistrarImpl();
    
    public CreateInstanceGoal(ClassDeclarationC decl, PythonArguments args, Context context,
            PythonValueSetAcceptor acceptor) {
        super(context);
        this.acceptor = acceptor;
        this.classDeclarationC = decl;
    }
    
    public void preRun() {
        List<PythonConstruct> superClasses = classDeclarationC.getSuperClasses();
        ResultsCollector rc = new ResultsCollector(superClasses.size(), getContext()) {
            @Override
            protected <T> void processResultTuple(Map<Object, RuntimeObject> results, IGrade<T> grade) {
                List<PythonClassType> supers = new ArrayList<PythonClassType>();
                for (RuntimeObject obj : results.values()) {
                    if (obj instanceof FunctionObject) {
                        FunctionObject func = (FunctionObject) obj;
                        ClassDeclarationC decl = (ClassDeclarationC) func.getDecl();
                        PythonClassType superType = new PythonUserClassType(decl);
                        supers.add(superType);
                    }
                    
                }
                PythonClassType receiverType = new PythonUserClassType(classDeclarationC, supers);
                InstanceValue receiverValue = new InstanceValue(receiverType, instanceRegistrar);
                PythonValue<InstanceValue> receiver = new PythonValue<InstanceValue>(receiverType,
                        receiverValue);
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