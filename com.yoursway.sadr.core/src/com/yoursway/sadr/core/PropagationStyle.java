package com.yoursway.sadr.core;

public enum PropagationStyle {
    
    VALUE {

        public void delegatePropagation(Goal goal, Propagator propagator, ContinuationRequestor requestor) {
            propagator.propagateToValue(goal, requestor);
        }

    },
    
    FLOW {

        public void delegatePropagation(Goal goal, Propagator propagator, ContinuationRequestor requestor) {
            propagator.propagateToFlow(goal, requestor);
        }
        
    },
    
    NONE {

        public void delegatePropagation(Goal goal, Propagator propagator, ContinuationRequestor requestor) {
            // no propagation
        }
        
    };
    
    public abstract void delegatePropagation(Goal goal, Propagator propagator, ContinuationRequestor requestor);

    
}
