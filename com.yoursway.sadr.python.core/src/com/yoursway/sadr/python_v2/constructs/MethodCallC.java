package com.yoursway.sadr.python_v2.constructs;

import static com.yoursway.sadr.python_v2.constructs.Effects.NO_EFFECTS;
import static java.util.Collections.singleton;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.python.parser.ast.expressions.PythonCallExpression;

import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.goals.sideeffects.CallF;
import com.yoursway.sadr.python_v2.goals.sideeffects.FieldReadF;

@Deprecated
public class MethodCallC extends CallC {
    
    private final PythonConstruct receiver;
    
    MethodCallC(Scope sc, PythonCallExpression node) {
        super(sc, node);
        receiver = getPreChildren().get(RECEIVER);
    }
    
    @Override
    public String toString() {
        return node.toString();
    }
    
    @Override
    public PythonConstruct getReceiver() {
        return receiver;
    }
    
    @Override
    public Frog toFrog() {
        List<Frog> argFrogs = new ArrayList<Frog>();
        for (PythonConstruct construct : getArgs()) {
            argFrogs.add(construct.toFrog());
        }
        return new CallF(new FieldReadF(receiver.toFrog(), node.getName()), argFrogs);
    }
    
    @Override
    public Effects getEffects() {
        return new Effects(NO_EFFECTS, singleton(toFrog()));
    }
    
}
