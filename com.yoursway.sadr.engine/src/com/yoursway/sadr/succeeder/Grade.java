package com.yoursway.sadr.succeeder;

enum Grade implements IGrade<Grade> {
    
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
