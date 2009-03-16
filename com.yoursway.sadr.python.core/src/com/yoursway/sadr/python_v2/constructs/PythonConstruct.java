package com.yoursway.sadr.python_v2.constructs;

import java.util.List;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public interface PythonConstruct {
    
    ASTNode node();
    
    PythonValueSet evaluate(Krocodile crocodile);
    
    Scope parentScope();
    
    Scope scope();
    
    List<PythonConstruct> getPostChildren();
    
    List<PythonConstruct> getPreChildren();
    
    PythonConstruct getSyntacticallyPreviousConstruct();
}
