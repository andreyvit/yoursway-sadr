package com.yoursway.sadr.python.analysis.context.dynamic;

import com.yoursway.sadr.core.constructs.DynamicContext;
import com.yoursway.sadr.python.analysis.context.dynamic.arguments.ActualArguments;

public abstract class PythonDynamicContext implements DynamicContext {
    
    public static final PythonDynamicContext EMPTY = new PythonDynamicContext() {
        
        @Override
        public String toString() {
            return "<E>";
        }
        
        @Override
        public Arguments argumentsOfTopCall() {
            return ActualArguments.EMPTY;
        }
        
        @Override
        public PythonDynamicContext unwind() {
            return this;
        }
        
        @Override
        public int hashCode() {
            return 42;
        };
        
        @Override
        public boolean equals(Object obj) {
            return obj == this;
        }
        
        @Override
        public int callStackSize() {
            return 0;
        };
        
    };
    
    @Override
    public abstract String toString();
    
    @Override
    public abstract boolean equals(Object obj);
    
    @Override
    public abstract int hashCode();
    
    public abstract Arguments argumentsOfTopCall();
    
    public abstract PythonDynamicContext unwind();
    
    public abstract int callStackSize();
    
}
