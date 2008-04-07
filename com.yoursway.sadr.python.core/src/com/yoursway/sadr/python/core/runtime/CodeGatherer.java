package com.yoursway.sadr.python.core.runtime;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;

public interface CodeGatherer {
    
    ContinuationRequestorCalledToken add(PythonConstruct root, ASTNode fakeParent,
            ContinuationScheduler scheduler);
    
}
