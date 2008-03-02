package com.yoursway.sadr.python.core.typeinferencing.scopes;

import org.eclipse.dltk.ast.declarations.MethodDeclaration;

import com.yoursway.sadr.python.core.runtime.RubyArgument;
import com.yoursway.sadr.python.core.runtime.RubyLocalVariable;
import com.yoursway.sadr.python.core.runtime.RubyProcedure;
import com.yoursway.sadr.python.core.runtime.RubySourceProcedure;
import com.yoursway.sadr.python.core.runtime.RubyVariable;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo;

public class ProcedureScope extends LocalScope {
    
    private final RubySourceProcedure procedure;
    private final DtlArgumentVariable[] argumentVariables;
    
    public ProcedureScope(Scope parent, RubySourceProcedure procedure, MethodDeclaration node) {
        super(parent, node);
        this.procedure = procedure;
        argumentVariables = createArguments(procedure);
    }
    
    private DtlArgumentVariable[] createArguments(RubyProcedure procedure) {
        RubyArgument[] args = procedure.arguments();
        DtlArgumentVariable[] argVars = new DtlArgumentVariable[args.length];
        for (int i = 0; i < args.length; i++)
            argVars[i] = new DtlArgumentVariable(procedure, args[i], (null));
        return argVars;
    }
    
    @Override
    public MethodDeclaration node() {
        return (MethodDeclaration) super.node();
    }
    
    public RubyProcedure getProcedure() {
        return procedure;
    }
    
    @Override
    protected String leafToString() {
        if (procedure == null)
            return "unkProc:" + node().getName();
        else
            return "p:" + procedure;
    }
    
    @Override
    public RubyVariable findOwnVariable(String name) {
        for (DtlArgumentVariable var : argumentVariables)
            if (var.name().equalsIgnoreCase(name))
                return var;
        return procedure.findLocalVariable(name);
    }
    
    @Override
    public RubyVariable lookupVariable(String name) {
        RubyVariable variable = findVariable(name);
        if (variable == null)
            variable = new RubyLocalVariable(procedure, null, procedure.scope(), name);
        return variable;
    }
    
    public ValueInfo selfType() {
        return null;
    }
    
}
