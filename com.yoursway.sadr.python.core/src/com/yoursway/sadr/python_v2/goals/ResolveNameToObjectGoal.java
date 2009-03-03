package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.constructs.PythonDeclaration;
import com.yoursway.sadr.python_v2.constructs.PythonFileC;
import com.yoursway.sadr.python_v2.constructs.VariableReferenceC;
import com.yoursway.sadr.python_v2.croco.Frog;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.model.builtins.Builtins;
import com.yoursway.sadr.succeeder.Goal;

public class ResolveNameToObjectGoal extends Goal<PythonValueSet> {
    
    private final Frog frog;
    private final PythonConstruct from;
    private final Krocodile crocodile;
    protected final PythonValueSet builder;
    
    public ResolveNameToObjectGoal(Frog frog, PythonConstruct from, Krocodile crocodile) {
        if (from == null)
            throw new NullPointerException("from is null");
        if (crocodile == null)
            throw new NullPointerException("croco is null");
        this.frog = frog;
        this.from = from;
        this.crocodile = crocodile;
        this.builder = new PythonValueSet();
        System.out.println("Created ResolveNameToObjectGoal");
    }
    
    public ResolveNameToObjectGoal(Frog name, PythonFileC from, Krocodile crocodile) {
        this(name, from.getPostChildren().get(from.getPostChildren().size() - 1), crocodile);
    }
    
    public ResolveNameToObjectGoal(VariableReferenceC variable, Krocodile crocodile) {
        this(Frog.searchFrog(variable.name()), variable, crocodile);
    }
    
    @Override
    public String describe() {
        String scope = (from.scope()).toString();
        return super.describe() + "\nfor name " + this.frog + " in " + scope;
    }
    
    public PythonValueSet evaluate() {
        PythonConstruct construct = from;
        while ((construct = construct.getSyntacticallyPreviousConstruct()) != null) {
            if (construct instanceof PythonDeclaration) {
                final PythonDeclaration declaration = (PythonDeclaration) construct;
                if (declaration.match(frog)) {
                    // means it's simple frog or it's last part of big frog
                    // first, check for simple names.
                    
                    // PythonRecord record = Index.lookup(crocodile, declaration);
                    return declaration.evaluate(crocodile);
                }
            }
        }
        RuntimeObject builtin = Builtins.instance().getScopedAttribute(frog.accessor());
        if (builtin != null) {
            return new PythonValueSet(builtin, crocodile);
        }
        return PythonValueSet.EMPTY;
    }
}
