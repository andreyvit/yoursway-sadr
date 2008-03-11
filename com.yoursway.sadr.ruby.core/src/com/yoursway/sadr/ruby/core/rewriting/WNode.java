package com.yoursway.sadr.ruby.core.rewriting;

import com.yoursway.sadr.ruby.core.rewriting.edits.CompoundEdit;

public abstract class WNode {
    
    WNode() {
    }
    
    public abstract void createEdits(CompoundEdit compound);
    
}
