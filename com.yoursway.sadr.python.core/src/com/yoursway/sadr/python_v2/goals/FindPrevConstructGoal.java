package com.yoursway.sadr.python_v2.goals;

import java.util.List;

import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.constructs.VariableReferenceC;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.succeeder.Goal;

public class FindPrevConstructGoal extends Goal {
    
    private final PythonConstructAcceptor acceptor;
    private final PythonConstruct current;
    
    public FindPrevConstructGoal(PythonConstruct current, PythonConstructAcceptor acceptor) {
        if (acceptor == null)
            throw new NullPointerException("acceptor is null");
        if (current == null)
            throw new NullPointerException("current is null");
        if (current instanceof VariableReferenceC)
            System.out.println("FindPrevConstructGoal.FindPrevConstructGoal()");
        this.current = current;
        this.acceptor = acceptor;
    }
    
    public void preRun() {
        //        System.out.println(((Engine) scheduler()).printGoalStack(this));
        PythonConstruct prevConstruct = findPrevConstruct(current);
        if (prevConstruct == null) {
            schedule(new FindCallersGoal(current.parentScope(), acceptor));
        } else {
            acceptor.addResult(prevConstruct);
            updateGrade(acceptor, Grade.DONE);
        }
    }
    
    private PythonConstruct findPrevConstruct(PythonConstruct current) {
        //FIXME: change to O(1) algorithm
        Scope scope = current.parentScope();
        List<PythonConstruct> children = scope.getEnclosedConstructs();
        PythonConstruct result = null;
        for (PythonConstruct construct : children) {
            if (current.equals(construct)) {
                break;
            }
            if (!(construct instanceof VariableReferenceC))
                result = construct;
        }
        return result;
    }
    
    @Override
    protected String describe() {
        return super.describe() + "(" + current + ")";
    }
    
}
