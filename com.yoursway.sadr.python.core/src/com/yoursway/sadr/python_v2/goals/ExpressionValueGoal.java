package com.yoursway.sadr.python_v2.goals;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.python.parser.ast.expressions.PythonVariableAccessExpression;

import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python.core.runtime.contributions.Context;
import com.yoursway.sadr.python.core.typeinferencing.constructs.BinaryC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.IntegerLiteralC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.MethodDeclarationC;
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
    
    PythonValueSetAcceptor createAcceptor(final PythonValueSetAcceptor resultAcceptor) {
        return new PythonValueSetAcceptor() {
            public <T> void checkpoint(IGrade<T> grade) {
                resultAcceptor.setResult(getResult());
                ExpressionValueGoal.this.checkpoint(resultAcceptor, grade);
            }
        };
    }
    
    public void preRun() {
        if (expression instanceof MethodDeclarationC) {
            checkpoint(acceptor, Grade.DONE);
        } else if (expression instanceof IntegerLiteralC) {
            acceptor.addResult(IntType.newIntObject((IntegerLiteralC) expression), getContext());
            checkpoint(acceptor, Grade.DONE);
        } else if (expression instanceof StringLiteralC) {
            acceptor.addResult(StringType.newStringObject((StringLiteralC) expression), getContext());
            checkpoint(acceptor, Grade.DONE);
        } else if (expression instanceof ProcedureCallC) {
            scheduleProcedureCall();
        } else if (expression instanceof PythonVariableAccessExpression) {
            schedule(new ResolveReference((PythonVariableAccessExpression) expression, acceptor));
        } else if (expression instanceof VariableReferenceC) {
            scheduleVariableReferenceC();
        } else if (expression instanceof BinaryC) {
            schedule(new BinaryExpressionGoal((BinaryC) expression, getContext(), acceptor));
        }
    }
    
    private void scheduleVariableReferenceC() {
        ResolveNameGoal resolveNameGoal = new ResolveNameGoal((VariableReferenceC) expression,
                new ResolvedNameDelegatingAcceptor() {
                    @Override
                    protected void onMethodDeclaration(FunctionObject obj) {
                        acceptor.addResult(obj, ExpressionValueGoal.this.getContext());
                        ExpressionValueGoal.this.checkpoint(acceptor, Grade.DONE);
                    }
                    
                    @Override
                    protected void onAssignment(PythonConstruct subexpr) {
                        ExpressionValueGoal.this.schedule(new ExpressionValueGoal(subexpr,
                                ExpressionValueGoal.this.getContext(), acceptor));
                    }
                    
                });
        schedule(resolveNameGoal);
    }
    
    private void scheduleProcedureCall() {
        ProcedureCallC callC = (ProcedureCallC) expression;
        List<PythonConstruct> args = callC.getArgs();
        final List<RuntimeObject> results = new ArrayList<RuntimeObject>(args.size());
        final FunctionObject[] function = new FunctionObject[] { null };
        final Synchronizer syncronizer = new Synchronizer(args.size() + 1) {
            @Override
            public <T> void allDone(IGrade<T> grade) {
                IGoal callFunction = CallResolver.callFunction(function[0], results, acceptor, getContext());
                schedule(callFunction);
            }
        };
        schedule(new ResolveNameGoal(callC, new ResolvedNameDelegatingAcceptor() {
            @Override
            public <T> void checkpoint(IGrade<T> grade) {
                if (function[0] == null) {
                    throw new IllegalStateException("Function should be found");
                }
            }
            
            @Override
            protected void onMethodDeclaration(FunctionObject obj) {
                function[0] = obj;
                syncronizer.subgoalDone(Grade.DONE);
            }
            
            @Override
            protected void onAssignment(PythonConstruct subexpr) {
                ExpressionValueGoal subGoal = new ExpressionValueGoal(subexpr, ExpressionValueGoal.this
                        .getContext(), new PythonValueSetAcceptor() {
                    public <T> void checkpoint(IGrade<T> grade) {
                        RuntimeObject result = getResultByContext(getContext());
                        //TODO: unsafe type cast
                        function[0] = (FunctionObject) result;
                        syncronizer.subgoalDone(grade);
                    }
                });
                ExpressionValueGoal.this.schedule(subGoal);
            }
        }));
        for (int i = 0; i < args.size(); i++) {
            final int pos = i;
            PythonConstruct arg = args.get(i);
            results.add(null);
            schedule(new ExpressionValueGoal(arg, getContext(), new PythonValueSetAcceptor() {
                public <T> void checkpoint(IGrade<T> grade) {
                    results.set(pos, getResultByContext(getContext()));
                    syncronizer.subgoalDone(grade);
                }
            }));
        }
    }
}
