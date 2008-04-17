package com.yoursway.sadr.python.core.typeinferencing.types;

import java.util.ArrayList;
import java.util.Collection;

import com.yoursway.sadr.blocks.foundation.types.Type;
import com.yoursway.sadr.blocks.foundation.typesets.TypeSet;
import com.yoursway.sadr.python.core.runtime.PythonMethod;
import com.yoursway.sadr.python.core.runtime.requestors.methods.MethodRequestor;

public class TypeUtils {
    
    public static void findMethodsByPrefix(Type type, String prefix, Collection<PythonMethod> methods) {
        if (type instanceof InstanceType)
            ((InstanceType) type).runtimeClass().findMethodsByPrefix(prefix, methods);
        else if (type instanceof MetaClassType)
            ((MetaClassType) type).runtimeMetaClass().findMethodsByPrefix(prefix, methods);
    }
    
    public static void findMethod(Type type, String name, MethodRequestor requestor) {
        if (type instanceof InstanceType)
            ((InstanceType) type).runtimeClass().findMethod(name, requestor);
        else if (type instanceof MetaClassType)
            ((MetaClassType) type).runtimeMetaClass().findMethod(name, requestor);
    }
    
    public static PythonMethod[] findMethodsByPrefix(TypeSet typeSet, String prefix) {
        Collection<PythonMethod> methods = new ArrayList<PythonMethod>();
        for (Type type : typeSet.containedTypes())
            TypeUtils.findMethodsByPrefix(type, prefix, methods);
        return methods.toArray(new PythonMethod[methods.size()]);
    }
    
    public static void findMethod(TypeSet typeSet, String name, MethodRequestor requestor) {
        for (Type type : typeSet.containedTypes())
            TypeUtils.findMethod(type, name, requestor);
    }
    
}
