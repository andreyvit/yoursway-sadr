package com.yoursway.sadr.python_v2.constructs;

import org.eclipse.dltk.python.parser.ast.expressions.PythonVariableAccessExpression;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.croco.DotFrog;
import com.yoursway.sadr.python_v2.croco.Frog;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.ResolveModuleImportGoal;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.goals.internal.CallResolver;
import com.yoursway.sadr.python_v2.model.builtins.FunctionObject;
import com.yoursway.sadr.python_v2.model.builtins.ModuleType;
import com.yoursway.sadr.python_v2.model.builtins.ModuleValue;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;
import com.yoursway.sadr.succeeder.Goal;
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
    
    public boolean match(Frog frog) {
        if (frog instanceof DotFrog) {
            DotFrog dotFrog = (DotFrog) frog;
            return dotFrog.match(this.variable.name());
        }
        return false;
    }
    
    @Override
    public IGoal evaluate(final Krocodile crocodile, final PythonValueSetAcceptor acceptor) {
        return new Goal() {
            public void preRun() {
                PythonValueSetAcceptor receiverResolved = new PythonValueSetAcceptor() {
                    @Override
                    protected <T> void acceptIndividualResult(RuntimeObject object, IGrade<T> grade) {
                        
                        if (object instanceof FunctionObject) {
                            schedule(CallResolver.findMethod(object, ((FunctionObject) object).name(),
                                    acceptor, crocodile));
                        } else if (object instanceof PythonObject) {
                            if (object.getType() == ModuleType.instance()) {
                                PythonValue<ModuleValue> value = (PythonValue<ModuleValue>) object;
                                schedule(new ResolveModuleImportGoal(value, Frog.searchFrog(variable.name()),
                                        new PythonVariableDelegatingAcceptor(acceptor, crocodile), crocodile));
                            } else {
                                schedule(CallResolver
                                        .findMethod(object, variable.name(), acceptor, crocodile));
                            }
                        }
                    }
                };
                schedule(receiver.evaluate(crocodile, receiverResolved));
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
