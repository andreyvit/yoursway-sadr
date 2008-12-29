/**
 * 
 */
package com.yoursway.sadr.python_v2.goals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python_v2.constructs.ClassDeclarationC;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.goals.acceptors.ResultsCollector;
import com.yoursway.sadr.python_v2.model.PythonArguments;
import com.yoursway.sadr.python_v2.model.builtins.FunctionObject;
import com.yoursway.sadr.python_v2.model.builtins.PythonClassType;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;
import com.yoursway.sadr.succeeder.IGrade;

public class CreateInstanceGoal extends ContextSensitiveGoal {
    private final PythonValueSetAcceptor acceptor;
    private final ClassDeclarationC classDeclarationC;
    private final PythonConstruct instanceCreator;
    
    public CreateInstanceGoal(ClassDeclarationC classDecl, PythonConstruct instanceCreator,
            PythonArguments args, Krocodile context, PythonValueSetAcceptor acceptor) {
        super(context);
        this.instanceCreator = instanceCreator;
        if (instanceCreator == null)
            throw new IllegalArgumentException();
        this.acceptor = acceptor;
        this.classDeclarationC = classDecl;
    }
    
    public void preRun() {
        List<PythonConstruct> superClasses = classDeclarationC.getSuperClasses();
        ResultsCollector rc = new ResultsCollector(superClasses.size(), getKrocodile()) {
            @Override
            protected <T> void processResultTuple(Map<Object, RuntimeObject> results, IGrade<T> grade) {
                List<PythonClassType> supers = new ArrayList<PythonClassType>();
                for (RuntimeObject obj : results.values()) {
                    if (obj instanceof FunctionObject) {
                        FunctionObject func = (FunctionObject) obj;
                        ClassDeclarationC decl = (ClassDeclarationC) func.getDecl();
                        PythonClassType superType = new PythonClassType(decl);
                        supers.add(superType);
                    }
                    
                }
                PythonClassType receiverType = new PythonClassType(classDeclarationC, supers);
                PythonObject receiver = new PythonObject(receiverType, instanceCreator);
                acceptor.addResult(receiver, getKrocodile());
            }
            
            @Override
            public <T> void allResultsProcessed(IGrade<T> grade) {
                updateGrade(acceptor, grade);
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