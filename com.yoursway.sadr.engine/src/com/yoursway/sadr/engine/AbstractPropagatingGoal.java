package com.yoursway.sadr.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public abstract class AbstractPropagatingGoal {
    
    protected Collection<Goal> propagatingGoals;

    public AbstractPropagatingGoal() {
        super();
    }

    public void addPropagatingGoal(final Goal propagatingGoal) {
        if (propagatingGoals == null)
            propagatingGoals = new ArrayList<Goal>();
        propagatingGoals.add(propagatingGoal);
    }

    public void addPropagatingGoals(final Collection<Goal> propagatingGoals_) {
        if (propagatingGoals_ == null) {
            System.out.println("!!!!!!");
            throw new NullPointerException("propagatingGoals_");
        }
        if (propagatingGoals == null)
            propagatingGoals = new ArrayList<Goal>();
        propagatingGoals.addAll(propagatingGoals_);
    }

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object obj);

    public boolean isContextFree() {
        return propagatingGoals == null || propagatingGoals.isEmpty();
    }

    public ContextRelation contextRelation() {
        if (propagatingGoals == null)
            return ContextRelation.empty();
        else
            return new ContextRelation(new HashSet<Goal>(propagatingGoals()));
    }

    protected void expandTo(ContextSensitiveThing acceptor) {
        acceptor.addPropagatingGoals(propagatingGoals());
    }

    private Collection<Goal> propagatingGoals() {
        if (propagatingGoals == null)
            return Collections.emptyList();
        else
            return propagatingGoals;
    }
    
}