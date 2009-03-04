package com.yoursway.sadr.python_v2.goals;

import java.util.List;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.constructs.IfC;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.constructs.PythonDeclaration;
import com.yoursway.sadr.python_v2.constructs.PythonFileC;
import com.yoursway.sadr.python_v2.constructs.VariableReferenceC;
import com.yoursway.sadr.python_v2.croco.Frog;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.model.ContextImpl;
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
        System.out.println("Resolving name '" + frog + "'");
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
        PythonValueSet valueSet = findInScope(from, from.scope());
        if (valueSet != null) {
            return valueSet;
        }
        RuntimeObject builtin = Builtins.instance().getScopedAttribute(frog.accessor());
        if (builtin != null) {
            return new PythonValueSet(builtin, crocodile);
        }
        return PythonValueSet.EMPTY;
    }
    
    private PythonValueSet findInScope(PythonConstruct construct, Scope scope) {
        while ((construct = construct.getSyntacticallyPreviousConstruct()) != null) {
            if (construct instanceof PythonDeclaration) {
                final PythonDeclaration declaration = (PythonDeclaration) construct;
                if (declaration.match(frog)) {
                    // means it's simple frog or it's last part of big frog
                    // first, check for simple names.
                    
                    // PythonRecord record = Index.lookup(crocodile, declaration);
                    return declaration.evaluate(crocodile);
                }
            } else if (construct instanceof IfC) {
                return resolveIf((IfC) construct);
            }
        }
        ContextImpl context = crocodile.getContext(from.scope());
        if (context != null) {
            RuntimeObject argument = context.getActualArgument(frog.accessor());
            if (argument != null)
                return new PythonValueSet(argument, crocodile);
        }
        Scope parentScope = scope.parentScope();
        if (parentScope != null) {
            List<PythonConstruct> children = parentScope.getPostChildren();
            return findInScope(children.get(children.size() - 1), parentScope);
        }
        return null;
    }
    
    protected PythonValueSet resolveIf(final IfC ifc) {
        PythonValueSet results = new PythonValueSet();
        for (RuntimeObject choice : ifc.evaluate(crocodile)) {
            List<PythonConstruct> branch = ifc.getBranch(choice);
            PythonConstruct last = branch.get(branch.size() - 1);
            ResolveNameToObjectGoal resolve = new ResolveNameToObjectGoal(frog, last, crocodile);
            results.addResults(resolve.evaluate());
        }
        return results;
    }
}
