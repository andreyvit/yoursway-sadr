package com.yoursway.sadr.python_v2.goals;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.python.parser.ast.expressions.PythonVariableAccessExpression;

import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python.core.runtime.contributions.Context;
import com.yoursway.sadr.python.core.typeinferencing.constructs.BinaryC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.IntegerLiteralC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.ProcedureCallC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.constructs.StringLiteralC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.VariableReferenceC;
import com.yoursway.sadr.python_v2.goals.internal.CallResolver;
import com.yoursway.sadr.python_v2.model.RuntimeObject;
import com.yoursway.sadr.python_v2.model.builtins.FunctionObject;
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
    
    @Override
    public CheckpointToken flush() {
        return null;
    }
    
    PythonValueSetAcceptor createValueSetAcceptor() {
        return new PythonValueSetAcceptor() {
            public <T> void checkpoint(IGrade<T> grade) {
                acceptor.setResult(getResult());
                ExpressionValueGoal.this.checkpoint(acceptor, grade);
            }
        };
    }
    
    public void preRun() {
        if (expression instanceof IntegerLiteralC) {
            acceptor.addResult(IntType.newIntObject((IntegerLiteralC) expression), getContext());
            checkpoint(acceptor, Grade.DONE);
        } else if (expression instanceof StringLiteralC) {
            acceptor.addResult(StringType.newStringObject((StringLiteralC) expression), getContext());
            checkpoint(acceptor, Grade.DONE);
        } else if (expression instanceof ProcedureCallC) {
            ProcedureCallC callC = (ProcedureCallC) expression;
            List<PythonConstruct> args = callC.getArgs();
            final List<RuntimeObject> results = new ArrayList<RuntimeObject>(args.size());
            final int collected[] = new int[] { args.size() };
            for (final int i = 0; i < args.size(); i++) {
                PythonConstruct arg = args.get(i);
                results.set(i, null);
                ExpressionValueGoal goal = new ExpressionValueGoal(arg, getContext(),
                        new PythonValueSetAcceptor() {
                            public <T> void checkpoint(IGrade<T> grade) {
                                results.set(i, getResultByContext(getContext()));
                                collected[0]++;
                                if (collected[0] == 0) {
                                    //join this 
                                }
                            }
                        });
                schedule(goal);
            }
            IGoal goal = new ResolveNameGoal((VariableReferenceC) expression,
                    new ResolvedNameDelegatingAcceptor() {
                        @Override
                        protected void onMethodDeclaration(FunctionObject obj) {
                            //join this
                            //call function when joined
                            return CallResolver.callFunction(obj, args, acceptor, getContext());
                            
                            acceptor.addResult(obj, ExpressionValueGoal.this.getContext());
                            ExpressionValueGoal.this.checkpoint(acceptor, Grade.DONE);
                        }
                        
                        @Override
                        protected void onAssignment(PythonConstruct subexpr) {
                            ExpressionValueGoal.this.schedule(new ExpressionValueGoal(subexpr,
                                    ExpressionValueGoal.this.getContext(), createValueSetAcceptor()));
                        }
                        
                    });
            schedule(goal);
            acceptor.addResult(StringType.newStringObject((StringLiteralC) expression), getContext());
            checkpoint(acceptor, Grade.DONE);
        } else if (expression instanceof PythonVariableAccessExpression) {
            IGoal goal = new ResolveReference((PythonVariableAccessExpression) expression,
                    createValueSetAcceptor());
            schedule(goal);
        } else if (expression instanceof VariableReferenceC) {
            IGoal goal = new ResolveNameGoal((VariableReferenceC) expression,
                    new ResolvedNameDelegatingAcceptor() {
                        @Override
                        protected void onMethodDeclaration(FunctionObject obj) {
                            acceptor.addResult(obj, ExpressionValueGoal.this.getContext());
                            ExpressionValueGoal.this.checkpoint(acceptor, Grade.DONE);
                        }
                        
                        @Override
                        protected void onAssignment(PythonConstruct subexpr) {
                            ExpressionValueGoal.this.schedule(new ExpressionValueGoal(subexpr,
                                    ExpressionValueGoal.this.getContext(), createValueSetAcceptor()));
                        }
                        
                    });
            schedule(goal);
        } else if (expression instanceof BinaryC) {
            schedule(new BinaryExpressionGoal((BinaryC) expression, getContext(), acceptor));
        }
    }
}
