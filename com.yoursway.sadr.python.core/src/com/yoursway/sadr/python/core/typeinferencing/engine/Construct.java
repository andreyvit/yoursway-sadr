package com.yoursway.sadr.python.core.typeinferencing.engine;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.python.core.typeinferencing.constructs.IConstruct;
import com.yoursway.sadr.python.core.typeinferencing.constructs.dtl.PythonFileC;
import com.yoursway.sadr.python.core.typeinferencing.scopes.FileScope;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

public class Construct<S extends Scope, T extends ASTNode> {
    
    private final S scope;
    private final T node;
    
    public Construct(S scope, T node) {
        if (scope == null)
            throw new NullPointerException();
        if (node == null)
            throw new NullPointerException();
        this.scope = scope;
        this.node = node;
    }
    
    public S scope() {
        return scope;
    }
    
    public T node() {
        return node;
    }
    
    public IConstruct asAnotherMyself() {
        FileScope fileScope = scope.fileScope();
        PythonFileC fileC = new PythonFileC(fileScope, fileScope.node());
        return fileC.subconstructFor(node);
    }
    
    @Override
    public String toString() {
        return scope.toString() + "\n" + node.toString();
    }
    
}
