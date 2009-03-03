package com.yoursway.sadr.python_v2.constructs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.python.parser.ast.PythonArgument;
import org.eclipse.dltk.python.parser.ast.expressions.PythonLambdaExpression;

import com.google.common.collect.Lists;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.model.ContextImpl;
import com.yoursway.sadr.python_v2.model.PythonArguments;
import com.yoursway.sadr.python_v2.model.builtins.FunctionObject;

public class PythonLambdaExpressionC extends PythonScopeImpl<PythonLambdaExpression> implements
        PythonCallable {
    
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
    
    public PythonConstruct getExpression() {
        return body;
    }
    
    @Override
    public PythonValueSet evaluate(Krocodile context) {
        FunctionObject obj = new FunctionObject(this);
        return new PythonValueSet(obj, context);
    }
    
    public String displayName() {
        return "Lambda";
    }
    
    @Override
    public String name() {
        return "<lambda>";
    }
    
    public PythonValueSet call(Krocodile crocodile, PythonArguments args) {
        List<PythonArgument> realArgs = node().getArguments();
        ContextImpl context = new ContextImpl(realArgs, args);
        Krocodile actualArguments = new Krocodile(Krocodile.EMPTY, this, context);
        return getExpression().evaluate(actualArguments);
    }
}
