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
            throw new IllegalStateException("FIXME");
        }
        for (int i = 0; i < formalArguments.size(); i++) {
            ASTNode formalArgument = formalArguments.get(i);
            if (!(formalArgument instanceof PythonArgument)) {
                throw new IllegalStateException("FIXME");
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
