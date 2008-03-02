package com.yoursway.sadr.core;

public class ValueInfos {
    
    private static final class EmptyValueInfo implements IValueInfo {
        
        public String[] describePossibleTypes() {
            return new String[0];
        }
        
        public String[] describePossibleValues() {
            return new String[0];
        }
        
        public boolean isEmpty() {
            return true;
        }
        
    }
    
    private static final IValueInfo EMPTY = new EmptyValueInfo();
    
    public static final IValueInfo empty() {
        return EMPTY;
    }
    
}
