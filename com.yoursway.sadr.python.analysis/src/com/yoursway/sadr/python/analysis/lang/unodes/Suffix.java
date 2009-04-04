package com.yoursway.sadr.python.analysis.lang.unodes;

public abstract class Suffix {
    
    public static final Suffix EMPTY = new EmptySuffix();
    
    public abstract Unode appendTo(Unode prefix);
    
    @Override
    public final String toString() {
        return "*" + toStringWithoutStar();
    }
    
    abstract String toStringWithoutStar();
    
    private static class EmptySuffix extends Suffix {
        
        private EmptySuffix() {
        }
        
        @Override
        String toStringWithoutStar() {
            return "";
        }
        
        @Override
        public Unode appendTo(Unode prefix) {
            return prefix;
        }
        
    }
    
}
