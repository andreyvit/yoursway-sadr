package com.yoursway.sadr.python_v2.constructs;

import static com.google.common.collect.Lists.newArrayList;

import java.util.Collection;

import org.eclipse.dltk.ast.references.VariableReference;

import com.yoursway.sadr.blocks.foundation.wildcards.StarWildcard;
import com.yoursway.sadr.python.core.typeinferencing.goals.MumblaWumblaThreesome;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.goals.ResolveNameToObjectGoal;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.goals.sideeffects.VariableReadF;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.succeeder.IGoal;

public class VariableReferenceC extends PythonConstructImpl<VariableReference> {
    
    VariableReferenceC(Scope sc, VariableReference node) {
        super(sc, node);
    }
    
    @Override
    public Collection<MumblaWumblaThreesome> mumblaWumbla() {
        return newArrayList(new MumblaWumblaThreesome(null, node.getName(), StarWildcard.INSTANCE));
    }
    
    @Override
    public IGoal evaluate(Context context, PythonValueSetAcceptor acceptor) {
        return new ResolveNameToObjectGoal(this, context, acceptor);
    }
    
    @Override
    public Frog toFrog() {
        return new VariableReadF(node.getName());
    }
    
    public String name() {
        return node.getName();
    }
}
