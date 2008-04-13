package com.yoursway.sadr.ruby.core.runtime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.ruby.core.runtime.requestors.methods.HidingMethodRequestor;
import com.yoursway.sadr.ruby.core.runtime.requestors.methods.MethodRequestor;

public abstract class RubyBasicClass {
    
    private final Collection<RubyMethod> methods = new ArrayList<RubyMethod>();
    
    private final Map<String, RubyMethod> namesToMethods = new HashMap<String, RubyMethod>();
    
    private final Collection<RubyField> fields = new ArrayList<RubyField>();
    
    private final Map<String, RubyField> namesToFields = new HashMap<String, RubyField>();
    
    public void addMethod(RubyMethod method, String name) {
        if (method == null)
            throw new NullPointerException();
        methods.add(method);
        namesToMethods.put(name, method);
    }
    
    public void addField(RubyField field) {
        fields.add(field);
        namesToFields.put(field.name(), field);
    }
    
    public void findMethodsByPrefix(String prefix, Collection<RubyMethod> requestor) {
        for (RubyMethod method : methods)
            if (method.name().toLowerCase().startsWith(prefix.toLowerCase()))
                requestor.add(method);
        RubyBasicClass sup = superclassOfTheSameKind();
        if (sup != null)
            sup.findMethodsByPrefix(prefix, requestor);
    }
    
    public void findMethodWithoutHiding(String name, MethodRequestor requestor) {
        RubyMethod method = findMethod(name);
        if (method != null)
            requestor.accept(method);
        RubyBasicClass sup = superclassOfTheSameKind();
        if (sup != null)
            sup.findMethodWithoutHiding(name, requestor);
    }
    
    public void findMethod(String name, MethodRequestor requestor) {
        findMethodWithoutHiding(name, new HidingMethodRequestor(requestor));
    }
    
    public RubyMethod findMethod(String name) {
        return namesToMethods.get(name);
        // TODO? find in superclass
    }
    
    public abstract RubyBasicClass superclassOfTheSameKind();
    
    @Override
    public abstract String toString();
    
    public RubyField findField(String name) {
        RubyField field = namesToFields.get(name);
        if (field != null)
            return field;
        RubyBasicClass sup = superclassOfTheSameKind();
        if (sup != null)
            return sup.findField(name);
        return null;
    }
    
    public boolean definesAtLeastOneOf(String[] methods) {
        for (String method : methods)
            if (namesToMethods.containsKey(method))
                return true;
        return false;
    }
    
    public RubyField lookupField(String name) {
        RubyField field = findField(name);
        if (field == null)
            field = new RubyField(this, name) {
                
                @Override
                public ASTNode[] getNodesForSelection() {
                    return null;
                }
                
            };
        return field;
    }
    
}
