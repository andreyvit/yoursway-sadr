package com.yoursway.sadr.python.core.runtime;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.python.core.typeinferencing.constructs.dtl.PythonConstruct;

public interface CodeGatherer {
    
    void add(PythonConstruct root, ASTNode fakeParent);
    
}
