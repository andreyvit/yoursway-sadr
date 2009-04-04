package com.yoursway.sadr.python.analysis.unused.typeannotations;

public class TypeAnnotation implements Annotation {
    private final String typeName;
    private final String moduleName;
    
    public TypeAnnotation(String typeName, String moduleName) {
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
            return ta.typeName.equals(this.typeName)
                    && (!(this.moduleName != null && ta.moduleName != null) || ta.moduleName
                            .equals(this.moduleName));
        }
        return false;
    }
}
