package com.yoursway.sadr.python.core.runtime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.yoursway.sadr.python.core.runtime.requestors.methods.HidingMethodRequestor;
import com.yoursway.sadr.python.core.runtime.requestors.methods.MethodRequestor;

public abstract class PythonBasicClass {
    
    private final Collection<PythonMethod> methods = new ArrayList<PythonMethod>();
    
    private final Map<String, PythonMethod> namesToMethods = new HashMap<String, PythonMethod>();
    
    private final Collection<PythonField> fields = new ArrayList<PythonField>();
    
    private final Map<String, PythonField> namesToFields = new HashMap<String, PythonField>();
    
    public void addMethod(PythonMethod method, String name) {
        if (method == null)
            throw new NullPointerException();
        methods.add(method);
        namesToMethods.put(name.toLowerCase(), method);
    }
    
    public void addField(PythonField field) {
        fields.add(field);
        namesToFields.put(field.name().toLowerCase(), field);
    }
    
    public void findMethodsByPrefix(String prefix, Collection<PythonMethod> requestor) {
        for (PythonMethod method : methods)
            if (method.name().toLowerCase().startsWith(prefix.toLowerCase()))
                requestor.add(method);
        PythonBasicClass sup = superclassOfTheSameKind();
        if (sup != null)
            sup.findMethodsByPrefix(prefix, requestor);
    }
    
    public void findMethodWithoutHiding(String name, MethodRequestor requestor) {
        PythonMethod method = findMethod(name);
        if (method != null)
            requestor.accept(method);
        PythonBasicClass sup = superclassOfTheSameKind();
        if (sup != null)
            sup.findMethodWithoutHiding(name, requestor);
    }
    
    public void findMethod(String name, MethodRequestor requestor) {
        findMethodWithoutHiding(name, new HidingMethodRequestor(requestor));
    }
    
    public PythonMethod findMethod(String name) {
        return namesToMethods.get(name.toLowerCase());
    }
    
    public abstract PythonBasicClass superclassOfTheSameKind();
    
    @Override
    public abstract String toString();
    
    public PythonField findField(String name) {
        PythonField field = namesToFields.get(name.toLowerCase());
        if (field != null)
            return field;
        PythonBasicClass sup = superclassOfTheSameKind();
        if (sup != null)
            return sup.findField(name);
        return null;
    }
    
    public boolean definesAtLeastOneOf(String[] methods) {
        for (String method : methods)
            if (namesToMethods.containsKey(method.toLowerCase()))
                return true;
        return false;
    }
    
}
