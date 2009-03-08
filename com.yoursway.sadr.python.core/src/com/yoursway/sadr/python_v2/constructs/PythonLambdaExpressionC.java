package com.yoursway.sadr.python_v2.constructs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.python.parser.ast.PythonArgument;
import org.eclipse.dltk.python.parser.ast.expressions.PythonLambdaExpression;

import com.google.common.collect.Lists;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.model.builtins.values.FunctionObject;

public class PythonLambdaExpressionC extends PythonScopeImpl<PythonLambdaExpression> implements
        CallableDeclaration {
    
    private final PythonConstruct body;
    
    public PythonLambdaExpressionC(Scope sc, PythonLambdaExpression node) {
        super(sc, node);
        body = getPostChildren().get(0);
    }
    
    @Override
    protected void wrapEnclosedChildren() {
        ArrayList<PythonConstruct> constructs = Lists.newArrayList(PythonConstructFactory.wrap(node
                .getBodyExpression(), this));
        setPostChildren(constructs);
    }
    
    @Override
    public PythonValueSet evaluate(Krocodile context) {
        FunctionObject obj = new FunctionObject(this);
        return new PythonValueSet(obj, context);
    }
    
    public PythonValueSet call(Krocodile crocodile) {
        return body.evaluate(crocodile);
    }
    
    public String displayName() {
        return "Lambda";
    }
    
    @Override
    public String name() {
        return "<lambda>";
    }
    
    @SuppressWarnings("unchecked")
    public List<PythonArgument> getArguments() {
        return node.getArguments();
    }
    
    public PythonConstruct getArgInit(String name) {
        return null;
    }
}
