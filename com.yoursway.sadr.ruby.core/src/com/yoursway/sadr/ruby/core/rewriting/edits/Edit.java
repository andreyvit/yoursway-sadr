package com.yoursway.sadr.ruby.core.rewriting.edits;

public abstract class Edit implements Comparable<Edit> {
    
    public abstract int apply(StringBuilder builder);
    
    abstract int offset();
    
    abstract void fixup(int offset);
    
    public int compareTo(Edit o) {
        return offset() - o.offset();
    }
    
}
