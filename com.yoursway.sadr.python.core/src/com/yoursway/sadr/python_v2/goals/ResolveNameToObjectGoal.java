package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.constructs.IfC;
import com.yoursway.sadr.python_v2.constructs.ImportDeclarationC;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.constructs.PythonFileC;
import com.yoursway.sadr.python_v2.constructs.PythonVariableAcceptor;
import com.yoursway.sadr.python_v2.constructs.VariableReferenceC;
import com.yoursway.sadr.python_v2.croco.Frog;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.IncrementableSynchronizer;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.goals.internal.CallResolver;
import com.yoursway.sadr.python_v2.model.PythonArguments;
import com.yoursway.sadr.python_v2.model.builtins.Builtins;
import com.yoursway.sadr.succeeder.Goal;
import com.yoursway.sadr.succeeder.IGoal;
import com.yoursway.sadr.succeeder.IGrade;

public class ResolveNameToObjectGoal extends Goal {
    
    private final Frog frog;
    private PythonConstruct from;
    
    protected IncrementableSynchronizer<PythonVariableAcceptor> incSync;
    protected PythonVariableAcceptor acceptor;
    private final Krocodile crocodile;
    
    protected void init(PythonVariableAcceptor acceptor) {
        this.incSync = new IncrementableSynchronizer<PythonVariableAcceptor>(acceptor) {
            
            @Override
            public <T> void completed(IGrade<T> grade) {
                // TODO Auto-generated method stub
                
            }
            
        };
    }
    
    public ResolveNameToObjectGoal(Frog name, PythonConstruct from, Krocodile crocodile,
            final PythonVariableAcceptor acceptor) {
        init(acceptor);
        this.acceptor = acceptor;
        this.crocodile = crocodile;
        this.frog = name;
        this.from = from;
        if (name == null || from == null)
            throw new IllegalArgumentException();
    }
    
    public ResolveNameToObjectGoal(Frog name, PythonFileC from, Krocodile crocodile,
            final PythonVariableAcceptor acceptor) {
        init(acceptor);
        this.acceptor = acceptor;
        this.crocodile = crocodile;
        if (name == null || from == null)
            throw new IllegalArgumentException();
        this.frog = name;
        this.from = from.getPostChildren().get(from.getPostChildren().size() - 1);
    }
    
    public ResolveNameToObjectGoal(VariableReferenceC variable, Krocodile crocodile,
            final PythonVariableAcceptor acceptor) {
        init(acceptor);
        this.acceptor = acceptor;
        this.crocodile = crocodile;
        this.frog = new Frog(variable.name(), Frog.SEARCH);
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
        String scope = (from.scope()).toString();
        return super.describe() + "\nfor name " + this.frog + " in " + scope;
    }
    
    protected Goal iteration() {
        if (this.from == null)
            throw new NullPointerException("this.from is null");
        PythonConstruct currentConstruct = this.from;
        Scope scope = currentConstruct.parentScope();
        //        if (currentConstruct instanceof IfC) {
        //            resolveIf((IfC) currentConstruct);
        //            break;
        //        }
        boolean foundOrImported = currentConstruct.match(frog);
        
        if (foundOrImported) {
            if (currentConstruct instanceof ImportDeclarationC) {
                ImportDeclarationC moduleImport = (ImportDeclarationC) currentConstruct;
                schedule(new ResolveModuleImportGoal(moduleImport, frog, acceptor, crocodile));
            } else {
                IGoal subgoal = currentConstruct.evaluate(crocodile, acceptor);
                if (subgoal != null) {
                    schedule(subgoal);
                }
            }
        }
        
        PythonConstruct prevConstruct = currentConstruct;
        currentConstruct = currentConstruct.getSyntacticallyPreviousConstruct();
        
        if (leavingScope(currentConstruct, prevConstruct)) {
            if (crocodile != null) {
                crocodile.getMatchingArguments(this.frog, acceptor);
            }
            scope = scope.parentScope();
            if (scope == null) {
                //built-in name is checked
                Builtins.instance().findAttributes(frog, acceptor);
                return null;
            }
            currentConstruct = scope.getPostChildren().get(scope.getPostChildren().size() - 1);
        }
        from = currentConstruct;
        return this;
    }
    
    public void preRun() {
        while (true) {
            Goal iteration = iteration();
            if (null == iteration) {
                updateGrade(this.acceptor, Grade.DONE);
                break;
            }
        }
    }
    
    protected void resolveIf(final IfC ifc) {
        incSync.increment();
        schedule(ifc.getCondition().evaluate(crocodile, new PythonValueSetAcceptor(crocodile) {
            //TODO incSync here
            
            @Override
            protected <T> void acceptIndividualResult(RuntimeObject result, IGrade<T> grade) {
                if (null == result)
                    return;
                schedule(CallResolver.callMethod(result, "__nonzero__", new PythonArguments(),
                        new PythonValueSetAcceptor(crocodile) {//TODO incSync here
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
                                            ifc.thenBlock().size() - 1), crocodile, acceptor));
                                } else if (Builtins.getFalse().equals(result)) {
                                    schedule(new ResolveNameToObjectGoal(frog, ifc.elseBlock().get(
                                            ifc.elseBlock().size() - 1), crocodile, acceptor));
                                } else {
                                    //TODO schedule both
                                }
                            }
                        }, crocodile, ifc));
            }
        }));
    }
}
