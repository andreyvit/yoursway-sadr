package com.yoursway.sadr.core;

public abstract class AbstractSubject implements Subject, Propagator, Contributor {
    
    public void process(Goal goal, ContinuationRequestor requestor) {
        contributeTo(goal, requestor);
        goal.delegatePropagationTo(this, requestor);
    }
    
    protected void contributeTo(Goal goal, ContinuationRequestor requestor) {
        goal.delegateContributionTo(this, requestor);
    }
    
    public void contributeToUnknownGoal(Goal goal, ContinuationRequestor requestor) {
    }
    
    public void propagateToFlow(Goal goal, ContinuationRequestor requestor) {
    }
    
    public void propagateToValue(Goal goal, ContinuationRequestor requestor) {
    }
    
}
