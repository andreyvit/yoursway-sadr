package com.yoursway.sadr.engine;

public class SlotImpl<R extends Result> implements Slot<R> {
    
    private R result;
    
    public SlotImpl() {
        this.result = null;
    }
    
    public SlotImpl(R result) {
        if (result == null)
            throw new NullPointerException("result is null");
        this.result = result;
    }
    
    public void setResult(R result) {
        if (result == null)
            throw new NullPointerException("result is null");
        if (this.result != null)
            throw new IllegalStateException("Can only set result once");
        this.result = result;
    }
    
    public R result() {
        if (result == null)
            throw new IllegalStateException("No result has been stored, cannot retrieve it.");
        return result;
    }
    
}
