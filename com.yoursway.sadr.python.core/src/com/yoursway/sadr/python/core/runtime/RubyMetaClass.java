package com.yoursway.sadr.python.core.runtime;

import com.yoursway.sadr.python.core.runtime.std.NewMethod;

public class RubyMetaClass extends RubyBasicClass {
    
    private final RubyClass klass;
    
    public RubyMetaClass(RubyClass klass, RubyRuntimeModel model) {
        this.klass = klass;
        new NewMethod(this, model);
    }
    
    public RubyClass instanceClass() {
        return klass;
    }
    
    @Override
    public String toString() {
        return klass + "!";
    }
    
    @Override
    public RubyBasicClass superclassOfTheSameKind() {
        RubyClass sup = klass.superclass();
        return (sup != null ? sup.metaClass() : null);
    }
    
}
