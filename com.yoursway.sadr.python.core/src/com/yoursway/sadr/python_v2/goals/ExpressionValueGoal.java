package com.yoursway.sadr.python_v2.goals;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.python.parser.ast.expressions.PythonVariableAccessExpression;

import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python.core.typeinferencing.constructs.BinaryC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.IntegerLiteralC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.MethodDeclarationC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.ProcedureCallC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.constructs.StringLiteralC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.VariableReferenceC;
import com.yoursway.sadr.python_v2.goals.internal.CallResolver;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.RuntimeObject;
import com.yoursway.sadr.python_v2.model.builtins.FunctionObject;
import com.yoursway.sadr.python_v2.model.builtins.IntType;
import com.yoursway.sadr.python_v2.model.builtins.StringType;
import com.yoursway.sadr.succeeder.Goal;
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
        Goal resolveNameGoal = new ResolveNameToObjectGoal((VariableReferenceC) expression, acceptor,
                getContext());
        schedule(resolveNameGoal);
    }
    
    private void scheduleProcedureCall() {
        ProcedureCallC callC = (ProcedureCallC) expression;
        List<PythonConstruct> args = callC.getArgs();
        final List<RuntimeObject> actualArguments = new ArrayList<RuntimeObject>(args.size());
        final FunctionObject[] function = new FunctionObject[] { null };
        final Synchronizer syncronizer = new Synchronizer(args.size() + 1) { //arguments and the object being called 
            @Override
            public <T> void allDone(IGrade<T> grade) {
                IGoal callFunction = CallResolver.callFunction(function[0], actualArguments, acceptor,
                        getContext());
                schedule(callFunction);
            }
        };
        schedule(new ResolveNameToObjectGoal(callC, new PythonValueSetAcceptor() {
            
            public <T> void checkpoint(IGrade<T> grade) {
                if (getResultByContext(getContext()) instanceof FunctionObject) {
                    function[0] = (FunctionObject) getResultByContext(getContext());
                    syncronizer.subgoalDone(grade);
                }
            }
        }, getContext()));
        for (int i = 0; i < args.size(); i++) {
            final int pos = i;
            PythonConstruct arg = args.get(i);
            actualArguments.add(null);
            schedule(new ExpressionValueGoal(arg, getContext(), new PythonValueSetAcceptor() {
                public <T> void checkpoint(IGrade<T> grade) {
                    actualArguments.set(pos, getResultByContext(getContext()));
                    syncronizer.subgoalDone(grade);
                }
            }));
        }
    }
}
