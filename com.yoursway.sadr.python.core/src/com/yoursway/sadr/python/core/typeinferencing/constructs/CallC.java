package com.yoursway.sadr.python.core.typeinferencing.constructs;

import java.util.Collections;
import java.util.List;

import org.eclipse.dltk.python.parser.ast.expressions.PythonCallExpression;

import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

public abstract class CallC extends PythonConstructImpl<PythonCallExpression> {
    
    private List<PythonConstruct> args;
    
    CallC(Scope sc, PythonCallExpression node) {
        super(sc, node);
        if (node.getArgs() == null) {
            args = Collections.emptyList();
        } else {
            args = PythonConstructFactory.wrap(sc, node.getArgs().getChilds());
        }
    }
    
    public List<PythonConstruct> getArgs() {
        return args;
    }
}
