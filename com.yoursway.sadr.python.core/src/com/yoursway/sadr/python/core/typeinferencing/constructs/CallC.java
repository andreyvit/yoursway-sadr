package com.yoursway.sadr.python.core.typeinferencing.constructs;

import static com.yoursway.sadr.python.core.runtime.PythonUtils.childrenOf;

import java.util.Collections;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.python.parser.ast.expressions.PythonCallExpression;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.yoursway.sadr.python.core.typeinferencing.constructs.requests.CallsAffector;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

public abstract class CallC extends PythonConstructImpl<PythonCallExpression> implements CallsAffector {
    
    CallC(Scope sc, PythonCallExpression node) {
        super(sc, node);
    }
    
    public List<PythonConstruct> arguments() {
        if (node.getArgs() == null) {
            return Collections.emptyList();
        }
        return Lists.transform(childrenOf(node.getArgs()), new Function<ASTNode, PythonConstruct>() {
            
            public PythonConstruct apply(ASTNode from) {
                return wrap(innerContext(), from);
            }
            
        });
    }
    
    public PythonConstruct receiver() {
        ASTNode receiver = node.getReceiver();
        if (receiver == null)
            return null;
        return wrap(parentScope(), receiver);
    }
    
}
