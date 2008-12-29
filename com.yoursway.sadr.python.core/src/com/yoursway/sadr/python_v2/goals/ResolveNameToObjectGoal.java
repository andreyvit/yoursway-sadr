package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python_v2.constructs.FieldAccessC;
import com.yoursway.sadr.python_v2.constructs.IfC;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.constructs.PythonDeclaration;
import com.yoursway.sadr.python_v2.constructs.PythonFileC;
import com.yoursway.sadr.python_v2.constructs.PythonVariableAcceptor;
import com.yoursway.sadr.python_v2.constructs.VariableReferenceC;
import com.yoursway.sadr.python_v2.croco.DotFrog;
import com.yoursway.sadr.python_v2.croco.Frog;
import com.yoursway.sadr.python_v2.croco.Index;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.croco.PythonRecord;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.goals.internal.CallResolver;
import com.yoursway.sadr.python_v2.model.PythonArguments;
import com.yoursway.sadr.python_v2.model.builtins.Builtins;
import com.yoursway.sadr.succeeder.Goal;
import com.yoursway.sadr.succeeder.IGrade;

public class ResolveNameToObjectGoal extends Goal {
    
    private final Frog frog;
    private final PythonConstruct from;
    private final Krocodile crocodile;
    protected final PythonVariableAcceptor acceptor;
    
    public ResolveNameToObjectGoal(Frog frog, PythonConstruct from, Krocodile crocodile,
            PythonVariableAcceptor acceptor) {
        if (from == null)
            throw new NullPointerException("from is null");
        if (crocodile == null)
            throw new NullPointerException("croco is null");
        if (acceptor == null)
            throw new NullPointerException("acceptor is null");
        this.frog = frog;
        this.from = from;
        this.crocodile = crocodile;
        this.acceptor = acceptor;
        System.out.println("Created ResolveNameToObjectGoal");
    }
    
    public ResolveNameToObjectGoal(Frog name, PythonFileC from, Krocodile crocodile,
            PythonVariableAcceptor acceptor) {
        this(name, from.getPostChildren().get(from.getPostChildren().size() - 1), crocodile, acceptor);
    }
    
    public ResolveNameToObjectGoal(VariableReferenceC variable, Krocodile crocodile,
            PythonVariableAcceptor acceptor) {
        this(Frog.searchFrog(variable.name()), variable, crocodile, acceptor);
    }
    
    @Override
    public String describe() {
        String scope = (from.scope()).toString();
        return super.describe() + "\nfor name " + this.frog + " in " + scope;
    }
    
    public PythonRecord lookup(Frog searchFrog, PythonConstruct construct) {
        if (searchFrog instanceof DotFrog) {
            
        } else {
            
        }
        return null;
    }
    
    public void find() {
        PythonConstruct construct = from;
        while ((construct = construct.getSyntacticallyPreviousConstruct()) != null) {
            if (construct instanceof PythonDeclaration) {
                final PythonDeclaration declaration = (PythonDeclaration) construct;
                if (declaration.match(frog)) {
                    // means it's simple frog or it's last part of big frog
                    // first, check for simple names.
                    PythonRecord record = Index.lookup(crocodile, declaration);
                    if()
                    declaration.evaluate(crocodile, new PythonValueSetAcceptor(acceptor) {
                        @Override
                        protected <T> void acceptIndividualResult(RuntimeObject result, IGrade<T> grade) {
                            acceptor.addResult(declaration.name(), result);
                        }
                    });
                }
            }
        }
    }
    
    public void preRun() {
        //            crocodile.getMatchingArguments(this.frog, acceptor);
        //        scope = scope.parentScope();
        //        if (scope == null) {
        //            //built-in name is checked
        //            Builtins.instance().findAttributes(frog, acceptor);
        if (!Index.isIndexed(from.parentScope())) {
            schedule(new IndexConstructGoal(from, crocodile, new Acceptor(acceptor) {
                @Override
                public <T> void checkpoint(IGrade<T> grade) {
                    if (grade == Grade.DONE) {
                        find();
                    }
                    super.checkpoint(grade);
                }
            }));
        } else {
            find();
        }
        System.out.println("Completed ResolveNameToObjectGoal");
    }
    
    protected void resolveIf(final IfC ifc, final PythonVariableAcceptor acceptor) {
        schedule(ifc.getCondition().evaluate(crocodile, new PythonValueSetAcceptor(acceptor) {
            @Override
            protected <T> void acceptIndividualResult(RuntimeObject result, IGrade<T> grade) {
                if (null == result)
                    return;
                schedule(CallResolver.callMethod(result, "__nonzero__", new PythonArguments(),
                        new PythonValueSetAcceptor(this) {//TODO incSync here
                            @Override
                            protected <K> void acceptIndividualResult(RuntimeObject result, IGrade<K> grade) {
                                if (Builtins.getTrue().equals(result)) {
                                    schedule(new ResolveNameToObjectGoal(frog, ifc.thenBlock().get(
                                            ifc.thenBlock().size() - 1), crocodile, acceptor));
                                } else if (Builtins.getFalse().equals(result)) {
                                    schedule(new ResolveNameToObjectGoal(frog, ifc.elseBlock().get(
                                            ifc.elseBlock().size() - 1), crocodile, acceptor));
                                } else {
                                    //TODO schedule both?
                                }
                            }
                        }, crocodile, ifc));
            }
        }));
    }
}
