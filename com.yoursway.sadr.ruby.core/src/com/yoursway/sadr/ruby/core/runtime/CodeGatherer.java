package com.yoursway.sadr.ruby.core.runtime;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyConstruct;

public interface CodeGatherer {
    
    void add(RubyConstruct root, ASTNode fakeParent);
    
}