package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.constructs.Frog;
import com.yoursway.sadr.python_v2.constructs.IfC;
import com.yoursway.sadr.python_v2.constructs.ImportDeclarationC;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.constructs.PythonFileC;
import com.yoursway.sadr.python_v2.constructs.PythonVariableAcceptor;
import com.yoursway.sadr.python_v2.constructs.VariableReferenceC;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.goals.internal.CallResolver;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.PythonArguments;
import com.yoursway.sadr.python_v2.model.builtins.Builtins;
import com.yoursway.sadr.succeeder.IGoal;
import com.yoursway.sadr.succeeder.IGrade;

public class ResolveNameToObjectGoal extends IterationGoal<PythonVariableAcceptor> {
    
    private final Frog frog;
    private final PythonConstruct from;
    
    public ResolveNameToObjectGoal(Frog name, PythonConstruct from, Context context,
            final PythonVariableAcceptor acceptor) {
        super(acceptor, context);
        this.frog = name;
        this.from = from;
        if (name == null || from == null)
            throw new IllegalArgumentException();
    }
    
    public ResolveNameToObjectGoal(Frog name, PythonFileC from, Context context,
            final PythonVariableAcceptor acceptor) {
        super(acceptor, context);
        if (name == null || from == null)
            throw new IllegalArgumentException();
        this.frog = name;
        this.from = from.getPostChildren().get(from.getPostChildren().size() - 1);
    }
    
    public ResolveNameToObjectGoal(VariableReferenceC variable, Context context,
            final PythonVariableAcceptor acceptor) {
        super(acceptor, context);
        this.frog = new Frog(variable.name());
        this.from = variable;
        if (frog == null || from == null)
            throw new IllegalArgumentException();
    }
    
    private boolean leavingScope(PythonConstruct currentConstruct, PythonConstruct prevConstruct) {
        return currentConstruct == null && prevConstruct != null
                || currentConstruct.parentScope() != prevConstruct.parentScope();
    }
    
    @Override
    public String describe() {
        String scope = (from.innerScope()).toString();
        return super.describe() + "\nfor name " + this.frog + " in " + scope;
    }
    
    @Override
    protected IterationGoal<PythonVariableAcceptor> iteration() {
        PythonConstruct currentConstruct = this.from;
        Scope scope = currentConstruct.parentScope();
        if (currentConstruct instanceof IfC) {
            resolveIf((IfC) currentConstruct);
        }
        boolean foundOrImported = currentConstruct.match(frog);
        
        if (foundOrImported) {
            if (currentConstruct instanceof ImportDeclarationC) {
                ImportDeclarationC moduleImport = (ImportDeclarationC) currentConstruct;
                schedule(new ResolveModuleImportGoal(moduleImport, frog, resultsAcceptor(), getContext()));
            } else {
                IGoal subgoal = currentConstruct.evaluate(getContext(), resultsAcceptor());
                if (subgoal != null) {
                    schedule(subgoal);
                }
            }
            return null;
        }
        
        PythonConstruct prevConstruct = currentConstruct;
        currentConstruct = currentConstruct.getSyntacticallyPreviousConstruct();
        
        if (leavingScope(currentConstruct, prevConstruct)) {
            if (getContext() != null) {
                getContext().getMatchingArguments(this.frog, resultsAcceptor());
            }
            scope = scope.parentScope();
            if (scope == null) {
                //built-in name is checked
                Builtins.instance().findAttributes(frog, resultsAcceptor());
                return null;
            }
            currentConstruct = scope.getPostChildren().get(scope.getPostChildren().size() - 1);
        }
        return new ResolveNameToObjectGoal(frog, currentConstruct, getContext(), resultsAcceptor());
    }
    
    private void resolveIf(final IfC ifc) {
        incSync.increment();
        schedule(ifc.getCondition().evaluate(getContext(), new PythonValueSetAcceptor(getContext()) {
            //TODO incSync here
            
            @Override
            protected <T> void acceptIndividualResult(RuntimeObject result, IGrade<T> grade) {
                if (null == result)
                    return;
                schedule(CallResolver.callMethod(result, "__nonzero__", new PythonArguments(),
                        new PythonValueSetAcceptor(getContext()) {//TODO incSync here
                            private boolean decremented = false;
                            
                            @Override
                            public <K> void checkpoint(IGrade<K> grade) {
                                super.checkpoint(grade);
                                if (!decremented) {
                                    decremented = true;
                                    incSync.decrement();
                                }
                            }
                            
                            @Override
                            protected <K> void acceptIndividualResult(RuntimeObject result, IGrade<K> grade) {
                                if (Builtins.getTrue().equals(result)) {
                                    schedule(new ResolveNameToObjectGoal(frog, ifc.thenBlock().get(
                                            ifc.thenBlock().size() - 1), getContext(), resultsAcceptor()));
                                } else if (Builtins.getFalse().equals(result)) {
                                    schedule(new ResolveNameToObjectGoal(frog, ifc.elseBlock().get(
                                            ifc.elseBlock().size() - 1), getContext(), resultsAcceptor()));
                                } else {
                                    //TODO schedule both
                                }
                            }
                        }, getContext(), ifc));
            }
        }));
    }
}
