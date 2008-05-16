package com.yoursway.sadr.python_v2.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.python.parser.ast.PythonArgument;

public class ContextImpl implements Context {
    
    Map<String, RuntimeObject> args = new HashMap<String, RuntimeObject>();
    
    public ContextImpl(List<ASTNode> formalArguments, List<RuntimeObject> actualArguments) {
        if (formalArguments.size() != actualArguments.size()) {
            throw new IllegalStateException("Argument number mismatch: " + actualArguments.size()
                    + " given, " + formalArguments.size() + " required");
        }
        for (int i = 0; i < formalArguments.size(); i++) {
            ASTNode formalArgument = formalArguments.get(i);
            if (!(formalArgument instanceof PythonArgument)) {
                throw new IllegalStateException("Wrong argument number " + (i + 1)
                        + ": Expected PythonArgument, got " + formalArgument.getClass().getSimpleName());
            }
            args.put(((PythonArgument) formalArgument).getName(), actualArguments.get(i));
        }
    }
    
    public boolean contains(String name) {
        return args.containsKey(name);
    }
    
    public RuntimeObject getActualArgument(String name) {
        return args.get(name);
    }
    
}
