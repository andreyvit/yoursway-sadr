package com.yoursway.sadr.ruby.core.rewriting.edits;

import java.util.Collection;

import com.google.common.collect.Sets;

public class CompoundEdit extends Edit {
    
    private final Collection<Edit> children = Sets.newTreeSet();
    
    private int fixup = 0;
    
    public void add(Edit edit) {
        children.add(edit);
    }
    
    @Override
    void fixup(int offset) {
        this.fixup += offset;
    }
    
    @Override
    public int apply(StringBuilder builder) {
        int commonFixup = this.fixup;
        int subseqFixup = 0;
        for (Edit edit : children) {
            edit.fixup(commonFixup + subseqFixup);
            subseqFixup += edit.apply(builder);
        }
        return subseqFixup;
    }
    
    @Override
    int offset() {
        if (children.isEmpty())
            return -1;
        return children.iterator().next().offset();
    }
    
}
