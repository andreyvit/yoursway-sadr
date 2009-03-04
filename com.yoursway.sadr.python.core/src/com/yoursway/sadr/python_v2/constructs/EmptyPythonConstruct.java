/**
 * 
 */
package com.yoursway.sadr.python_v2.constructs;

import java.util.List;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

final class EmptyPythonConstruct implements PythonConstruct {
    private static final String MESSAGE = "Null construct.";
    
    public List<PythonConstruct> getPostChildren() {
        throw new UnsupportedOperationException(MESSAGE);
    }
    
    public List<PythonConstruct> getPreChildren() {
        throw new UnsupportedOperationException(MESSAGE);
    }
    
    public PythonConstruct getSyntacticallyPreviousConstruct() {
        throw new UnsupportedOperationException(MESSAGE);
    }
    
    public Scope scope() {
        throw new UnsupportedOperationException(MESSAGE);
    }
    
    public ASTNode node() {
        throw new UnsupportedOperationException(MESSAGE);
    }
    
    public Scope parentScope() {
        throw new UnsupportedOperationException(MESSAGE);
    }
    
    public PythonValueSet evaluate(Krocodile crocodile) {
        throw new UnsupportedOperationException(MESSAGE);
    }
}