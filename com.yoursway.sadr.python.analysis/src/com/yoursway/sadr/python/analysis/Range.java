package com.yoursway.sadr.python.analysis;

public final class Range {
    
    private final int start;
    private final int end;
    
    public Range(int start, int end) {
        this.start = start;
        this.end = end;
    }
    
    public boolean contains(int offset) {
        return offset >= start && offset < end;
    }
    
    @Override
    public String toString() {
        return start + ".." + end;
    }
    
    @Override
    public int hashCode() {
        return (start + 31) * 31 + end;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        Range that = (Range) obj;
        return this.start == that.start && this.end == that.end;
    }
    
}
