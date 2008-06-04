package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python_v2.constructs.MethodDeclarationC;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.constructs.VariableReferenceC;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonConstructAcceptor;
import com.yoursway.sadr.python_v2.goals.acceptors.SimpleSynchronizer;
import com.yoursway.sadr.succeeder.Goal;
import com.yoursway.sadr.succeeder.IAcceptor;
import com.yoursway.sadr.succeeder.IGrade;

public class FindPrevConstructGoal extends Goal {
    
    private final class PrevConstructAcceptor extends PythonConstructAcceptor {
        
        private final IAcceptor nextAcceptor;
        
        public PrevConstructAcceptor(IAcceptor nextAcceptor) {
            this.nextAcceptor = nextAcceptor;
        }
        
        @Override
        public <K> void checkpoint(IGrade<K> grade) {
            nextAcceptor.checkpoint(grade);
            acceptor.addResults(getResults());
        }
    }
    
    private final class CallersAcceptor extends PythonConstructAcceptor {
        
        @Override
        public <T> void checkpoint(IGrade<T> grade) {
            SimpleSynchronizer sync = new SimpleSynchronizer() {
                
                @Override
                public <K> void completed(IGrade<K> grade) {
                    updateGrade(acceptor, grade);
                }
                
            };
            for (PythonConstruct construct : getResults())
                schedule(new FindPrevConstructGoal(construct,
                        new PrevConstructAcceptor(sync.createAcceptor())));
            sync.startCollecting();
        }
    }
    
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
        
        PythonConstruct prevConstruct = findPrevConstruct(current);
        System.out.println("PREV: " + current + " --> " + prevConstruct);
        if (prevConstruct == null && current instanceof MethodDeclarationC) {
            schedule(new FindCallersGoal(current.innerScope(), new CallersAcceptor()));
        } else {
            if (prevConstruct != null)
                acceptor.addResult(prevConstruct);
            updateGrade(acceptor, Grade.DONE);
        }
    }
    
    private PythonConstruct findPrevConstruct(PythonConstruct current) {
        return current.getSintacticallyPreviousConstruct();
    }
    
    @Override
    public String describe() {
        return super.describe() + "(" + current + ")";
    }
    
}
