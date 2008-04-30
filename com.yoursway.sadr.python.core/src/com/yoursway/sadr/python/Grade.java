package com.yoursway.sadr.python;

import com.yoursway.sadr.succeeder.IGrade;

public enum Grade implements IGrade<Grade> {
    
    INTERMEDIATE {
        
        public boolean isDone() {
            return false;
        }
        
    },
    
    DONE {
        
        public boolean isDone() {
            return true;
        }
        
    };
    
}
