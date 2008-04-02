package com.yoursway.sadr.ruby.core.runtime;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyConstruct;

public interface CodeGatherer {
    
    ContinuationRequestorCalledToken add(final RubyConstruct root, ASTNode fakeParent,
            ContinuationScheduler scheduler);
    
}