package com.yoursway.sadr.ruby.core.runtime;

public abstract class RubyField extends RubyVariable {
    
    private final RubyBasicClass klass;
    private final String name;
    
    public RubyField(RubyBasicClass klass, String name) {
        this.klass = klass;
        this.name = name;
        klass.addField(this);
    }
    
    public String name() {
        return name;
    }
    
    public RubyBasicClass container() {
        return klass;
    }
    
    @Override
    public String toString() {
        if (klass instanceof RubyMetaClass)
            return klass + ".@@" + name;
        else
            return klass + ".@" + name;
    }
    
}
