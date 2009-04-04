package com.yoursway.sadr.python.analysis.lang.constructs.support;


public enum Comparison {
    
    EQUALS {
        
        @Override
        public boolean matches(int ordering) {
            return ordering == 0;
        }
        
    },
    
    NOT_EQUALS {
        
        @Override
        public boolean matches(int ordering) {
            return ordering != 0;
        }
        
    },
    
    LESS {
        
        @Override
        public boolean matches(int ordering) {
            return ordering < 0;
        }
        
    },
    
    LESS_OR_EQUALS {
        
        @Override
        public boolean matches(int ordering) {
            return ordering <= 0;
        }
        
    },
    
    GREATER {
        
        @Override
        public boolean matches(int ordering) {
            return ordering > 0;
        }
        
    },
    
    GREATER_OR_EQUALS {
        
        @Override
        public boolean matches(int ordering) {
            return ordering >= 0;
        }
        
    };
    
    public abstract boolean matches(int ordering);
    
}
