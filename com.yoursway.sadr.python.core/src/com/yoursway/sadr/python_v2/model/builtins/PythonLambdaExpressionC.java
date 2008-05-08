package com.yoursway.sadr.python_v2.model.builtins;

import org.eclipse.dltk.python.parser.ast.expressions.PythonLambdaExpression;

import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstructFactory;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstructImpl;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

public class PythonLambdaExpressionC extends PythonConstructImpl<PythonLambdaExpression> {
    
    private final PythonConstruct body;
    
    public PythonLambdaExpressionC(Scope sc, PythonLambdaExpression node) {
        super(sc, node);
        body = PythonConstructFactory.wrapConstruct(node.getBodyExpression(), sc);
    }
    
    public PythonConstruct getExpression() {
        return body;
    }
    
}
