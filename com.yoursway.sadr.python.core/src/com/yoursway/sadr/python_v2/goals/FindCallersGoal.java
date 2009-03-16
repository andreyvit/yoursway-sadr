package com.yoursway.sadr.python_v2.goals;

import java.util.List;

import com.yoursway.sadr.python_v2.constructs.MethodCallC;
import com.yoursway.sadr.python_v2.constructs.ProcedureCallC;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.constructs.PythonScopeImpl;
import com.yoursway.sadr.python_v2.constructs.Scope;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonConstructSet;
import com.yoursway.sadr.succeeder.Goal;

public class FindCallersGoal extends Goal<PythonConstructSet> {
    private final Scope parentScope;
    private final String name;
    private final PythonConstructSet acceptor;
    
    public FindCallersGoal(Scope parentScope, PythonConstructSet acceptor) {
        this.parentScope = parentScope;
        this.acceptor = new PythonConstructSet();
        this.name = parentScope.name();
    }
    
    public void findInScope(Scope scope) {
        List<PythonConstruct> childConstructs = scope.getEnclosedConstructs();
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
            }
            if (construct instanceof PythonScopeImpl<?>) {
                findInScope((Scope) construct);
            }
        }
    }
    
    public PythonConstructSet evaluate() {
        findInScope(this.parentScope.getFileScope());
        return acceptor;
    }
}
