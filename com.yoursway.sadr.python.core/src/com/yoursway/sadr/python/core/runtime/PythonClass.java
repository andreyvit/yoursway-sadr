package com.yoursway.sadr.python.core.runtime;

import java.util.ArrayList;
import java.util.Collection;

public class PythonClass extends PythonBasicClass {
    
    private final String name;
    
    private final Collection<PythonClassDefinition> definitions = new ArrayList<PythonClassDefinition>();
    
    private final PythonMetaClass metaClass;
    
    public PythonClass(PythonRuntimeModel model, String name) {
        this.name = name;
        metaClass = new PythonMetaClass(this, model);
        model.addClass(this);
    }
    
    void addClassDefinition(PythonClassDefinition definition) {
        definitions.add(definition);
    }
    
    public String name() {
        return name;
    }
    
    public PythonMetaClass metaClass() {
        return metaClass;
    }
    
    public PythonClass superclass() {
        return calculateSuperclass();
    }
    
    private PythonClass calculateSuperclass() {
        if (definitions.isEmpty())
            return null;
        for (PythonClassDefinition def : definitions) {
            PythonClass superclass = def.superclass();
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
    public PythonBasicClass superclassOfTheSameKind() {
        return superclass();
    }
    
    public boolean isDerivedFrom(PythonClass declaredClass) {
        if (this == declaredClass)
            return true;
        PythonClass sup = superclass();
        return sup != null && sup.isDerivedFrom(declaredClass);
    }
    
    public Collection<PythonClassDefinition> getDefinitions() {
        return definitions;
    }
    
    public boolean isUndefined() {
        return definitions.isEmpty();
    }
    
}
