package com.yoursway.sadr.python_v2.goals;

import org.eclipse.dltk.python.parser.ast.expressions.PythonVariableAccessExpression;

import com.yoursway.sadr.python.core.runtime.contributions.Context;
import com.yoursway.sadr.python.core.typeinferencing.constructs.BinaryC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.IntegerLiteralC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.constructs.StringLiteralC;
import com.yoursway.sadr.python_v2.model.builtins.IntType;
import com.yoursway.sadr.python_v2.model.builtins.StringType;
import com.yoursway.sadr.succeeder.CheckpointToken;
import com.yoursway.sadr.succeeder.IGoal;
import com.yoursway.sadr.succeeder.IGrade;

public class ExpressionValueGoal extends ContextSensitiveGoal {
    
    private final PythonConstruct expression;
    private final PythonValueSetAcceptor acceptor;
    
    public ExpressionValueGoal(PythonConstruct expression, Context context, PythonValueSetAcceptor acceptor) {
        super(context);
        this.expression = expression;
        this.acceptor = acceptor;
    }
    
    public CheckpointToken flush() {
        return null;
    }
    
    public void preRun() {
        PythonValueSetAcceptor subgoalAcceptor = new PythonValueSetAcceptor() {
            public void checkpoint(IGrade<?> grade) {
                acceptor.setResult(getResult());
            }
        };
        if (expression instanceof IntegerLiteralC) {
            acceptor.addResult(IntType.newIntObject((IntegerLiteralC) expression), getContext());
        } else if (expression instanceof StringLiteralC) {
            acceptor.addResult(StringType.newStringObject((StringLiteralC) expression), getContext());
        } else if (expression instanceof PythonVariableAccessExpression) {
            //TODO for Bur
            IGoal goal = new ResolveReference((PythonVariableAccessExpression) expression, subgoalAcceptor);
            schedule(goal);
        } else if (expression instanceof BinaryC) {
            
        }
    }
}
