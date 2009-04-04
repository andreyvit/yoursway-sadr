package com.yoursway.sadr.python.analysis.lang.unodes;

public abstract class ChainableSuffix extends Suffix {
    
    protected final Suffix suffix;
    
    public ChainableSuffix(Suffix suffix) {
        if (suffix == null)
            throw new NullPointerException("head is null");
        this.suffix = suffix;
    }
    
    @Override
    public final Unode appendTo(Unode prefix) {
        return suffix.appendTo(appendThisSuffixTo(prefix));
    }
    
    @Override
    final String toStringWithoutStar() {
        return thisSuffixToString() + suffix.toStringWithoutStar();
    }
    
    protected abstract String thisSuffixToString();
    
    protected abstract Unode appendThisSuffixTo(Unode replacementUnode);
    
}
