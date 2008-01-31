package com.yoursway.sadr.core;

public enum PropagationStyle {
    
    VALUE {
        
        @Override
        public void delegatePropagation(Goal goal, Propagator propagator, ContinuationRequestor requestor) {
            propagator.propagateToValue(goal, requestor);
        }
        
    },
    
    FLOW {
        
        @Override
        public void delegatePropagation(Goal goal, Propagator propagator, ContinuationRequestor requestor) {
            propagator.propagateToFlow(goal, requestor);
        }
        
    },
    
    NONE {
        
        @Override
        public void delegatePropagation(Goal goal, Propagator propagator, ContinuationRequestor requestor) {
            // no propagation
        }
        
    };
    
    public abstract void delegatePropagation(Goal goal, Propagator propagator, ContinuationRequestor requestor);
    
}
