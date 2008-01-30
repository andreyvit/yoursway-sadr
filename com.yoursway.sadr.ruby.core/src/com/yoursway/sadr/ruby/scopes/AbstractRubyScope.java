package com.yoursway.sadr.ruby.scopes;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.core.Construct;
import com.yoursway.sadr.core.Scope;
import com.yoursway.sadr.ruby.ConstructFactory;

public abstract class AbstractRubyScope extends Scope {

    public Construct wrap(ASTNode child) {
        return ConstructFactory.I.create(this, child);
    }

}
