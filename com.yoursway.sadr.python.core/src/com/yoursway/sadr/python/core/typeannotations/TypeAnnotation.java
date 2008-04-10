package com.yoursway.sadr.python.core.typeannotations;

public class TypeAnnotation implements Annotation {
    private final String typeName;
    private final String moduleName;
    
    TypeAnnotation(String typeName, String moduleName) {
        if (typeName == null)
            throw new IllegalArgumentException();
        this.typeName = typeName;
        this.moduleName = moduleName;
    }
    
    public String getName() {
        return typeName;
    }
    
    public String getModuleName() {
        return moduleName;
    }
    
    @Override
    public int hashCode() {
        return typeName.hashCode() ^ ((moduleName != null) ? moduleName.hashCode() : 0);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TypeAnnotation) {
            TypeAnnotation ta = (TypeAnnotation) obj;
            return ta.typeName.equals(this.typeName) && ta.moduleName.equals(this.moduleName);
        } else
            return false;
    }
}
