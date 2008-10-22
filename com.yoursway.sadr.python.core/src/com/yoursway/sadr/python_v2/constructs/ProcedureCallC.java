package com.yoursway.sadr.python_v2.constructs;

import static com.yoursway.sadr.python_v2.constructs.Effects.NO_EFFECTS;
import static java.util.Collections.singleton;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.python.parser.ast.expressions.PythonCallExpression;

import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.goals.sideeffects.CallF;

public class ProcedureCallC extends CallC {
    
    ProcedureCallC(Scope sc, PythonCallExpression node) {
        super(sc, node);
    }
    
    @Override
    public String toString() {
        return node.getName() + "()";
    }
    
    @Override
    public PythonConstruct getReceiver() {
        return null;
    }
    
    private PythonConstruct function() {
        return getPreChildren().get(RECEIVER);
    }
    
    @Override
    public Frog toFrog() {
        List<Frog> argFrogs = new ArrayList<Frog>();
        for (PythonConstruct construct : getArgs()) {
            argFrogs.add(construct.toFrog());
        }
        return new CallF(function().toFrog(), argFrogs);
    }
    
    @Override
    public Effects getEffects() {
        return new Effects(NO_EFFECTS, singleton(toFrog()));
    }
    
}
