package com.yoursway.sadr.python_v2.goals;

import org.eclipse.dltk.python.parser.ast.expressions.PythonVariableAccessExpression;

import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python.core.runtime.contributions.Context;
import com.yoursway.sadr.python.core.typeinferencing.constructs.AssignmentC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.BinaryC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.IntegerLiteralC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.constructs.StringLiteralC;
import com.yoursway.sadr.python_v2.goals.internal.CallResolver;
import com.yoursway.sadr.python_v2.model.RuntimeObject;
import com.yoursway.sadr.python.core.typeinferencing.constructs.VariableReferenceC;
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
        final PythonValueSetAcceptor subgoalAcceptor = new PythonValueSetAcceptor() {
            public void checkpoint(IGrade<?> grade) {
                acceptor.setResult(getResult());
                ExpressionValueGoal.this.checkpoint(acceptor, Grade.DONE);
            }
        };
        if (expression instanceof IntegerLiteralC) {
            acceptor.addResult(IntType.newIntObject((IntegerLiteralC) expression), getContext());
            checkpoint(acceptor, Grade.DONE);
        } else if (expression instanceof StringLiteralC) {
            acceptor.addResult(StringType.newStringObject((StringLiteralC) expression), getContext());
            checkpoint(acceptor, Grade.DONE);
        } else if (expression instanceof PythonVariableAccessExpression) {
            IGoal goal = new ResolveReference((PythonVariableAccessExpression) expression, subgoalAcceptor);
            schedule(goal);
        } else if (expression instanceof VariableReferenceC) {
            IGoal goal = new ResolveNameGoal((VariableReferenceC) expression, new ResolvedNameAcceptor() {
                
                public void addResult(AssignmentC assignmentC) {
                    PythonConstruct subexpr = assignmentC.rhs();
                    schedule(new ExpressionValueGoal(subexpr, getContext(), subgoalAcceptor));
                }
                
                @SuppressWarnings("unchecked")
                public void checkpoint(IGrade grade) {
                    ExpressionValueGoal.this.checkpoint(acceptor, Grade.DONE);
                }
                
            });
            schedule(goal);
        } else if (expression instanceof BinaryC) {
            BinaryC binExp = (BinaryC)expression;
            BinaryAcceptor subgoalAcceptor = new BinaryAcceptor() {
                public void checkpoint(IGrade<?> grade) {
                    if (!getLeft().isEmpty() && !getRight().isEmpty()) {
                        //TODO evaluate result for each context
                        RuntimeObject receiver = getLeft().get(getContext());
                        RuntimeObject argument = getRight().get(getContext());
                        String name = binExp.node().getKind();
                        CallResolver.callMethod(receiver, name, new Arguments(argument));
                        ExpressionValueGoal.this.checkpoint(acceptor, Grade.DONE);
                    }
                }
            };
            schedule()
            checkpoint(acceptor, Grade.DONE);
        }
    }
}
