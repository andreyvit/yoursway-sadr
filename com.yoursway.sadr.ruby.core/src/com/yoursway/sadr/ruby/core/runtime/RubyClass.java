package com.yoursway.sadr.ruby.core.runtime;

import java.util.ArrayList;
import java.util.Collection;

public class RubyClass extends RubyBasicClass {
    
    private final String name;
    
    private final Collection<RubyClassDefinition> definitions = new ArrayList<RubyClassDefinition>();
    
    private final RubyMetaClass metaClass;
    
    public RubyClass(RubyRuntimeModel model, String name) {
        this.name = name;
        metaClass = new RubyMetaClass(this, model);
        model.addClass(this);
    }
    
    void addClassDefinition(RubyClassDefinition definition) {
        definitions.add(definition);
    }
    
    public String name() {
        return name;
    }
    
    public RubyMetaClass metaClass() {
        return metaClass;
    }
    
    public RubyClass superclass() {
        return calculateSuperclass();
    }
    
    private RubyClass calculateSuperclass() {
        if (definitions.isEmpty())
            return null;
        for (RubyClassDefinition def : definitions) {
            RubyClass superclass = def.superclass();
            if (superclass != null)
                return superclass;
        }
        return null;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    @Override
    public RubyBasicClass superclassOfTheSameKind() {
        return superclass();
    }
    
    public boolean isDerivedFrom(RubyClass declaredClass) {
        if (this == declaredClass)
            return true;
        RubyClass sup = superclass();
        return sup != null && sup.isDerivedFrom(declaredClass);
    }
    
    public Collection<RubyClassDefinition> getDefinitions() {
        return definitions;
    }
    
    public boolean isUndefined() {
        return definitions.isEmpty();
    }
    
}
