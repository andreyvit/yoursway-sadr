package com.yoursway.sadr.blocks.swamp.old;

import com.yoursway.utils.DebugOutputHelper;

public class LinearExecutionFlow {
    
    private final CodeRun initialRun;

    public LinearExecutionFlow(CodeRun initialRun) {
        if (initialRun == null)
            throw new NullPointerException("initialRun is null");
        this.initialRun = initialRun;
    }
    
    @Override
    public String toString() {
        return DebugOutputHelper.reflectionBasedToString(this, initialRun);
    }
    
}
