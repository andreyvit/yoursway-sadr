package com.yoursway.sadr.ruby.world;

import java.util.ArrayList;
import java.util.Collection;

public class World implements WorldContributor {
    
    private final Collection<RMethod> methods = new ArrayList<RMethod>();
    
    public void defineMethod(RMethod method) {
        methods.add(method);
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(methods.size()).append(" method(s):\n");
        for (RMethod method : methods)
            builder.append("- ").append(method).append("\n");
        return builder.toString();
    }
    
}
