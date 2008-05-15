package com.yoursway.sadr.python_v2.goals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.yoursway.sadr.python.core.typeinferencing.constructs.BinaryC;
import com.yoursway.sadr.python_v2.goals.internal.CallResolver;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.RuntimeObject;
import com.yoursway.sadr.succeeder.IGrade;

public class BinaryExpressionGoal extends ContextSensitiveGoal {
    private final PythonValueSetAcceptor acceptor;
    private final BinaryC expression;
    private RuntimeObject right;
    private RuntimeObject left;
    
    public BinaryExpressionGoal(BinaryC expression, Context context, PythonValueSetAcceptor acceptor) {
        super(context);
        this.expression = expression;
        this.acceptor = acceptor;
    }
    
    public void preRun() {
        
        PythonValueSetAcceptor leftSubgoalAcceptor = new PythonValueSetAcceptor() {
            public <T> void checkpoint(IGrade<T> grade) {
                if (getResult().isEmpty()) {
                    throw new IllegalStateException("Left subexpression must return any result.");
                }
                RuntimeObject result = getResultByContext(getContext());
                if (result == null) {
                    throw new IllegalStateException("Left subexpression must return any result.");
                }
                setLeft(result);
            }
        };
        
        PythonValueSetAcceptor rightSubgoalAcceptor = new PythonValueSetAcceptor() {
            public <T> void checkpoint(IGrade<T> grade) {
                if (getResult().isEmpty()) {
                    throw new IllegalStateException("Right subexpression must return any result.");
                }
                RuntimeObject result = getResultByContext(getContext());
                if (result == null) {
                    throw new IllegalStateException("Right subexpression must return any result.");
                }
                setRight(result);
            }
        };
        
        schedule(expression.getLeft().evaluate(getContext(), leftSubgoalAcceptor));
        schedule(expression.getRight().evaluate(getContext(), rightSubgoalAcceptor));
    }
    
    protected void setLeft(RuntimeObject runtimeObject) {
        left = runtimeObject;
        if (left != null && right != null) {
            onBothReady();
        }
    }
    
    protected void setRight(RuntimeObject result) {
        right = result;
        if (left != null && right != null) {
            onBothReady();
        }
    }
    
    private void onBothReady() {
        //TODO evaluate result for each context
        RuntimeObject receiver = left;
        RuntimeObject argument = right;
        String name = expression.getOperationMethodName();
        List<RuntimeObject> actualArgs = new ArrayList<RuntimeObject>(1);
        actualArgs.add(argument);
        HashMap<String, RuntimeObject> kwargs = new HashMap<String, RuntimeObject>();
        schedule(CallResolver.callMethod(receiver, name, actualArgs, kwargs,
                acceptor, getContext()));
    }
    
    @Override
    public String describe() {
        String basic = super.describe();
        return basic + "\nfor expression " + expression.toString();
    }
}
