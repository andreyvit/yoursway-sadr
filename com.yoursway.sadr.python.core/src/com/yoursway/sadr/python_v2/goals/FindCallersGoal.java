package com.yoursway.sadr.python_v2.goals;

import static com.yoursway.sadr.python.Grade.DONE;

import java.util.List;

import com.yoursway.sadr.python.core.typeinferencing.constructs.MethodCallC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.ProcedureCallC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonScopeImpl;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.succeeder.Goal;

public class FindCallersGoal extends Goal {
    private final Scope parentScope;
    private final String name;
    private final PythonConstructAcceptor acceptor;
    
    public FindCallersGoal(Scope parentScope, PythonConstructAcceptor acceptor) {
        this.parentScope = parentScope;
        this.acceptor = acceptor;
        this.name = parentScope.name();
    }
    
    public void findInScope(Scope scope) {
        List<PythonConstruct> childConstructs = scope.getChildConstructs();
        for (PythonConstruct construct : childConstructs) {
            if (construct instanceof MethodCallC) {
                MethodCallC callC = (MethodCallC) construct;
                if (this.name.equals(callC.node().getName())) {
                    acceptor.addResult(callC);
                }
            } else if (construct instanceof ProcedureCallC) {
                ProcedureCallC callC = (ProcedureCallC) construct;
                if (this.name.equals(callC.node().getName())) {
                    acceptor.addResult(callC);
                }
            } else if (construct instanceof PythonScopeImpl<?>) {
                findInScope((Scope) construct);
            }
        }
    }
    
    public void preRun() {
        System.out.println(scheduler().getGoalStack(this));
        findInScope(this.parentScope.getFileScope());
        updateGrade(acceptor, DONE);
    }
}
