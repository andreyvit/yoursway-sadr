package com.yoursway.sadr.python_v2.goals;

import java.util.ArrayList;
import java.util.List;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.constructs.CallC;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.constructs.PythonDeclaration;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.model.builtins.FunctionObject;
import com.yoursway.sadr.succeeder.Goal;
import com.yoursway.sadr.succeeder.IGoal;
import com.yoursway.sadr.succeeder.IGrade;

public class IndexConstructGoal extends Goal {
    protected final PythonConstruct from;
    protected final Krocodile crocodile;
    protected final Acceptor acceptor;
    
    public IndexConstructGoal(PythonConstruct from, Krocodile croco, Acceptor acceptor) {
        if (from == null)
            throw new NullPointerException("from is null");
        if (croco == null)
            throw new NullPointerException("croco is null");
        if (acceptor == null)
            throw new NullPointerException("acceptor is null");
        this.from = from;
        this.crocodile = croco;
        this.acceptor = acceptor;
        System.out.println("Created IndexConstructGoal");
    }
    
    private List<PythonConstruct> retrieveCallsFrom(PythonConstruct construct) {
        List<PythonConstruct> resList = new ArrayList<PythonConstruct>();
        if (construct instanceof CallC)
            resList.add(construct);
        for (PythonConstruct child : construct.getPreChildren()) {
            resList.addAll(retrieveCallsFrom(child));
        }
        for (PythonConstruct child : construct.getPostChildren()) {
            resList.addAll(retrieveCallsFrom(child));
        }
        return resList;
    }
    
    protected void indexScope() {
        Scope scope = from.parentScope();
        List<PythonConstruct> constructs = scope.getPostChildren();
        for (PythonConstruct c : constructs) {
            if (c instanceof PythonDeclaration) {
                ((PythonDeclaration) c).index(crocodile, new Acceptor(acceptor) {
                });
            } else {
                
            }
            //            if (c instanceof ImportDeclarationC) {
            //ImportDeclarationC moduleImport = (ImportDeclarationC) currentConstruct;
            //i know how to parse all imports but "from x import *" (ImportAllImportC)
            //so forgot 'bout 'em all right now
            //schedule(new ResolveModuleImportGoal(moduleImport, frog, acceptor, crocodile));
            //            } else {
            //                
            //            }
            indexCalls(c);
        }
    }
    
    private void indexCalls(PythonConstruct c) {
        List<PythonConstruct> currentConstructCalls = retrieveCallsFrom(c);
        for (PythonConstruct call : currentConstructCalls) {
            final Krocodile callKroco = new Krocodile(crocodile, call);
            IGoal evaluate = call.evaluate(crocodile, new PythonValueSetAcceptor(acceptor) {
                
                @Override
                protected <T> void acceptIndividualResult(RuntimeObject result, IGrade<T> grade) {
                    if (result instanceof FunctionObject) {
                        FunctionObject functionObject = (FunctionObject) result;
                        schedule(new IndexConstructGoal(functionObject.getDecl(), callKroco, new Acceptor(
                                this) {
                        }));
                    } else {
                        System.out.println("result is " + result + " !");
                    }
                }
                
            });
            schedule(evaluate);
        }
    }
    
    public void preRun() {
        indexScope();
        acceptor.subgoalDone(Grade.DONE);
    }
    
    @Override
    public String describe() {
        return super.describe() + " at " + from;
    }
}
