package com.yoursway.sadr.python.analysis.lang.unodes;

import com.yoursway.sadr.python.analysis.aliasing.Alias;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;

public final class Bnode {
    
    private final Unode unode;
    private final PythonLexicalContext lc;
    private final int hashCode;
    
    public Bnode(Unode unode, PythonLexicalContext lc) {
        if (unode == null)
            throw new NullPointerException("unode is null");
        if (lc == null)
            throw new NullPointerException("lc");
        this.unode = unode;
        this.lc = lc;
        this.hashCode = (lc.hashCode() + 31) * 31 + unode.hashCode();
    }
    
    public PythonLexicalContext lc() {
        return lc;
    }
    
    public Alias toAlias(PythonDynamicContext dc) {
        return new Alias(unode, lc, dc);
    }
    
    public Bnode append(Suffix suffix) {
        return new Bnode(suffix.appendTo(unode), lc);
    }
    
    @Override
    public String toString() {
        return unode + " @" + lc;
    }
    
    @Override
    public int hashCode() {
        return hashCode;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        Bnode that = (Bnode) obj;
        return this.lc.equals(that.lc) && this.unode.equals(that.unode);
    }
    
}
