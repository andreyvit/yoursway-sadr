package com.yoursway.sadr.python.core.typeinferencing.constructs;

import static com.google.common.collect.Lists.newArrayList;

import java.util.Collection;

import org.eclipse.dltk.ast.references.VariableReference;

import com.yoursway.sadr.blocks.foundation.wildcards.StarWildcard;
import com.yoursway.sadr.python.core.typeinferencing.goals.MumblaWumblaThreesome;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

public class VariableReferenceC extends PythonConstructImpl<VariableReference> {
    
    VariableReferenceC(Scope sc, VariableReference node) {
        super(sc, node);
    }
    
    @Override
    public Collection<MumblaWumblaThreesome> mumblaWumbla() {
        return newArrayList(new MumblaWumblaThreesome(null, node.getName(), StarWildcard.INSTANCE));
    }
    
}
