package com.yoursway.sadr.ruby.core.typeinferencing.typesets.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.yoursway.sadr.ruby.core.runtime.RubyMethod;
import com.yoursway.sadr.ruby.core.runtime.requestors.methods.MethodRequestor;
import com.yoursway.sadr.ruby.core.typeinferencing.types.Type;
import com.yoursway.sadr.ruby.core.typeinferencing.typesets.TypeSet;

public abstract class AbstractTypeSet implements TypeSet {
    
    @Override
    public abstract String toString();
    
    public RubyMethod[] findMethodsByPrefix(String prefix) {
        Collection<RubyMethod> methods = new ArrayList<RubyMethod>();
        for (Type type : containedTypes())
            type.findMethodsByPrefix(prefix, methods);
        return methods.toArray(new RubyMethod[methods.size()]);
    }
    
    public void findMethod(String name, MethodRequestor requestor) {
        for (Type type : containedTypes())
            type.findMethod(name, requestor);
    }
    
    public String[] describePossibleTypes() {
        List<String> result = new ArrayList<String>();
        for (Type type : containedTypes())
            result.add(type.describe());
        Collections.sort(result);
        return result.toArray(new String[result.size()]);
    }
    
}
