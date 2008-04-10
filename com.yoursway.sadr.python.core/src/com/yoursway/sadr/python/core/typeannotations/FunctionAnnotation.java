package com.yoursway.sadr.python.core.typeannotations;

import java.util.List;
import java.util.Set;

public class FunctionAnnotation implements Annotation {
    private final Set<TypeAnnotation> returnType;
    private final List<Set<TypeAnnotation>> argumentsTypes;
    
    FunctionAnnotation(List<Set<TypeAnnotation>> argumentsTypes, Set<TypeAnnotation> returnTypeName) {
        this.argumentsTypes = argumentsTypes;
        this.returnType = returnTypeName;
    }
    
    public Set<TypeAnnotation> getReturnType() {
        return returnType;
    }
    
    public List<Set<TypeAnnotation>> getArgumentsTypes() {
        return argumentsTypes;
    }
}
