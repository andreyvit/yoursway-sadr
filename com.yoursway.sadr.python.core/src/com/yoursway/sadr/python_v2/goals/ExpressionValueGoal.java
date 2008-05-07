package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python.core.runtime.contributions.Context;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstructVisitor;
import com.yoursway.sadr.python_v2.goals.visitors.ExpressionAnalyzer;
import com.yoursway.sadr.succeeder.CheckpointToken;
import com.yoursway.sadr.succeeder.IGrade;

public class ExpressionValueGoal extends ContextSensitiveGoal {
    
    private final PythonConstruct expression;
    private final PythonValueSetAcceptor acceptor = new PythonValueSetAcceptor() {
        
        public void checkpoint(IGrade<?> grade) {
            //TODO
        }
        
    };
    
    public ExpressionValueGoal(PythonConstruct expression, Context context) {
        super(context);
        this.expression = expression;
    }
    
    public CheckpointToken flush() {
        return null;
    }
    
    public void preRun() {
        PythonConstructVisitor visitor = new ExpressionAnalyzer(acceptor, getContext());
        expression.traverse(visitor);
        checkpoint(acceptor, Grade.DONE);
    }
    
    public PythonValueSetAcceptor getAcceptor() {
        return acceptor;
    }
}
