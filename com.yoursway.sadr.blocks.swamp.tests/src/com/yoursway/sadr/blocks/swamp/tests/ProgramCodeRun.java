/**
 * 
 */
package com.yoursway.sadr.blocks.swamp.tests;

import com.yoursway.sadr.blocks.swamp.old.CodeRun;
import com.yoursway.sadr.blocks.swamp.old.EffectCursor;

public class ProgramCodeRun implements CodeRun {
    
    private final Program program;
    private final int startIndex;
    private final int endIndex;

    public ProgramCodeRun(Program program, int startIndex, int endIndex) {
        if (program == null)
            throw new NullPointerException("program is null");
        this.program = program;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public EffectCursor start() {
        return null;
    }
    
    @Override
    public EffectCursor end() {
        return null;
    }
    
}