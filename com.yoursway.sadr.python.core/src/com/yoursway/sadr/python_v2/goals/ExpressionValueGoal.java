package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python.core.runtime.contributions.Context;
import com.yoursway.sadr.python.core.typeinferencing.constructs.IntegerLiteralC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.constructs.StringLiteralC;
import com.yoursway.sadr.python_v2.model.builtins.IntType;
import com.yoursway.sadr.python_v2.model.builtins.StringType;
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
        if (expression instanceof IntegerLiteralC)
            acceptor.addResult(IntType.newIntObject((IntegerLiteralC) expression), getContext());
        else if (expression instanceof StringLiteralC)
            acceptor.addResult(StringType.newStringObject((StringLiteralC) expression), getContext());
        checkpoint(acceptor, Grade.DONE);
    }
    
    public PythonValueSetAcceptor getAcceptor() {
        return acceptor;
    }
}
