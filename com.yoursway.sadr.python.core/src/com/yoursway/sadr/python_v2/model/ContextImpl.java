package com.yoursway.sadr.python_v2.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.python.parser.ast.PythonArgument;

public class ContextImpl implements Context {
    
    Map<String, RuntimeObject> args = new HashMap<String, RuntimeObject>();
    
    public ContextImpl(List<ASTNode> formalArguments, List<RuntimeObject> actualArguments) {
        assert formalArguments.size() == actualArguments.size() : "Temporary assertion";
        for (int i = 0; i < formalArguments.size(); i++) {
            ASTNode formalArgument = formalArguments.get(i);
            assert formalArgument instanceof PythonArgument;
            args.put(((PythonArgument) formalArgument).getName(), actualArguments.get(i));
        }
    }
    
    public boolean contains(String name) {
        return args.containsKey(name);
    }
    
    public RuntimeObject getActualArguement(String name) {
        return args.get(name);
    }
    
}
