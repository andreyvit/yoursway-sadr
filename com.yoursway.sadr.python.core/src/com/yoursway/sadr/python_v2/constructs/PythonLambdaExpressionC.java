package com.yoursway.sadr.python_v2.constructs;

import java.util.ArrayList;

import org.eclipse.dltk.python.parser.ast.expressions.PythonLambdaExpression;

import com.google.common.collect.Lists;
import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.goals.ExpressionValueGoal;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.builtins.FunctionObject;
import com.yoursway.sadr.succeeder.IGoal;

public class PythonLambdaExpressionC extends PythonScopeImpl<PythonLambdaExpression> {
    
    private final PythonConstruct body;
    
    public PythonLambdaExpressionC(Scope sc, PythonLambdaExpression node) {
        super(sc, node);
        body = getChildConstructs().get(0);
    }
    
    @Override
    protected void wrapEnclosedChildren() {
        ArrayList<PythonConstruct> constructs = Lists.newArrayList(wrap(node.getBodyExpression(), this));
        setChildConstructs(constructs);
    }
    
    public PythonConstruct getExpression() {
        return body;
    }
    
    @Override
    public IGoal evaluate(Context context, PythonValueSetAcceptor acceptor) {
        return new ExpressionValueGoal(context, acceptor) {
            public void preRun() {
                FunctionObject obj = new FunctionObject(PythonLambdaExpressionC.this);
                acceptor.addResult(obj, getContext());
                updateGrade(acceptor, Grade.DONE);
            }
        };
    }
    
    public String displayName() {
        return "Lambda";
    }
    
    @Override
    public String name() {
        return "<lambda>";
    }
}
