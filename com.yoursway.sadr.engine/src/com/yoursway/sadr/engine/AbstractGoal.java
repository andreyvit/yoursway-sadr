package com.yoursway.sadr.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public abstract class AbstractGoal implements Goal {
    
    private Collection<Goal> sins;
    
    @Override
    public abstract int hashCode();
    
    @Override
    public abstract boolean equals(Object obj);
    
    @Override
    public abstract String toString();
    
    public void done() {
    }
    
    public boolean cachable() {
        return true;
    }
    
    public boolean isSaint() {
        return sins == null || sins.isEmpty();
    }
    
    public void blame(final Goal sin) {
        if (sins == null)
            sins = new ArrayList<Goal>();
        sins.add(sin);
    }
    
    public void blame(final Collection<Goal> sins_) {
        if (sins_ == null) {
            System.out.println("!!!!!!");
            throw new NullPointerException("sins_");
        }
        if (sins == null)
            sins = new ArrayList<Goal>();
        sins.addAll(sins_);
    }
    
    public Karma karma() {
        if (sins == null)
            return Karma.empty();
        else
            return new Karma(new HashSet<Goal>(sins()));
    }
    
    protected void punish(Sinner acceptor) {
        acceptor.blame(sins());
    }
    
    private Collection<Goal> sins() {
        if (sins == null)
            return Collections.emptyList();
        else
            return sins;
    }
    
    protected Sinner thou() {
        return this;
    }
    
    public boolean hasComplexUnnaturalRelationshipWithRecursion() {
        return false;
    }
    
}
