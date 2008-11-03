package com.yoursway.sadr.python_v2.constructs;

import org.eclipse.dltk.python.parser.ast.expressions.PythonVariableAccessExpression;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.croco.DotFrog;
import com.yoursway.sadr.python_v2.croco.Frog;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.ExpressionValueGoal;
import com.yoursway.sadr.python_v2.goals.ResolveModuleImportGoal;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.goals.internal.CallResolver;
import com.yoursway.sadr.python_v2.model.builtins.FunctionObject;
import com.yoursway.sadr.python_v2.model.builtins.ModuleType;
import com.yoursway.sadr.python_v2.model.builtins.ModuleValue;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;
import com.yoursway.sadr.succeeder.IGoal;
import com.yoursway.sadr.succeeder.IGrade;

public class FieldAccessC extends PythonConstructImpl<PythonVariableAccessExpression> {
    
    private final PythonConstruct receiver;
    private final VariableReferenceC variable;
    private final int RECEIVER = 0, VARIABLE = 1;
    
    FieldAccessC(Scope sc, PythonVariableAccessExpression node) {
        super(sc, node);
        this.receiver = getPostChildren().get(RECEIVER);
        this.variable = (VariableReferenceC) getPostChildren().get(VARIABLE);
    }
    
    @Override
    public boolean match(Frog frog) {
        if (frog instanceof DotFrog) {
            DotFrog dotFrog = (DotFrog) frog;
            return dotFrog.match(this.variable) && dotFrog.head().match(receiver);
        }
        return false;
    }
    
    @Override
    public IGoal evaluate(final Krocodile context, final PythonValueSetAcceptor acceptor) {
        return new ExpressionValueGoal(context, acceptor) {
            public void preRun() {
                PythonValueSetAcceptor receiverResolved = new PythonValueSetAcceptor(context) {
                    @Override
                    protected <T> void acceptIndividualResult(RuntimeObject object, IGrade<T> grade) {
                        if (object instanceof FunctionObject) {
                            schedule(CallResolver.findMethod(object, ((FunctionObject) object).name(),
                                    acceptor, context));
                        } else if (object instanceof PythonObject) {
                            if (object.getType() == ModuleType.instance()) {
                                PythonValue<ModuleValue> value = (PythonValue<ModuleValue>) object;
                                schedule(new ResolveModuleImportGoal(value, new Frog(variable.name(),
                                        Frog.SEARCH), new PythonVariableDelegatingAcceptor(acceptor,
                                        context), context));
                            } else {
                                schedule(CallResolver.findMethod(object, variable.name(), acceptor,
                                        getContext()));
                            }
                        }
                    }
                };
                schedule(receiver.evaluate(context, receiverResolved));
            }
        };
    }
    
    public PythonConstruct receiver() {
        return receiver;
    }
    
    public VariableReferenceC variable() {
        return variable;
    }
}
