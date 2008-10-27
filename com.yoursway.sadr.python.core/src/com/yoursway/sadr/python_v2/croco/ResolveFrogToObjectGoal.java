package com.yoursway.sadr.python_v2.croco;

import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.constructs.Frog;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.succeeder.Goal;

public class ResolveFrogToObjectGoal extends Goal {
    
    private final Frog frog;
    private final PythonConstruct first;
    
    public ResolveFrogToObjectGoal(Krocodile krocodile, Frog frog) {
        if (krocodile == null)
            throw new NullPointerException("krocodile is null");
        if (frog == null)
            throw new NullPointerException("frog is null");
        this.frog = frog;
        this.first = krocodile.construct();
    }
    
    public void preRun() {
        // traverse backward until we match frog
        boolean done = false;
        Scope firstScope = first.innerScope();
        
        Scope scope = firstScope;
        PythonConstruct construct = first;
        while (!done && scope != null) {
            while (construct != null) {
                if (construct.match(frog)) { //TODO: frog.match(construct) here
                    processResultConstruct(construct);
                }
                
                PythonConstruct prev = construct;
                construct = construct.getSyntacticallyPreviousConstruct();
                if (construct instanceof Scope) {//exit from scope
                    Scope sc = (Scope) construct;
                    if (sc == prev.parentScope()) {
                        scope = scope.parentScope();
                        construct = scope.getPostChildren().get(scope.getPostChildren().size() - 1);
                    }
                }
            }
            done = false;
        }
        //FIXME!
        updateGrade(acceptor, Grade.DONE);
    }
    
    protected void processResultConstruct(PythonConstruct construct) {
        Krocodile egg = construct.toEgg();
        
        // if the frog matched an indexed crocodile, return the index
        // if the frog matched a not-yet-indexed crocodile, start computing the index
        // (by traversing backward until we reach another indexed usage, or
        // until we reach a definition, in which case assign a new index)
        
    }
    
}
