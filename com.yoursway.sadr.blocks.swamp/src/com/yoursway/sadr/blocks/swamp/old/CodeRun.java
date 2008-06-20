package com.yoursway.sadr.blocks.swamp.old;

/**
 * Represents an isolated fragment of source code which executes iff the control
 * is passed into it explicitly.
 */
public interface CodeRun {
    
    EffectCursor start();
    
    EffectCursor end();
    
}
