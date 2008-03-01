package com.yoursway.sadr.ruby.core.typeinferencing.scopes;

import org.eclipse.dltk.ast.declarations.MethodDeclaration;

import com.yoursway.sadr.ruby.core.runtime.RubyArgument;
import com.yoursway.sadr.ruby.core.runtime.RubyProcedure;
import com.yoursway.sadr.ruby.core.runtime.RubySourceProcedure;
import com.yoursway.sadr.ruby.core.runtime.RubyVariable;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo;

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
    
    public ValueInfo selfType() {
        return null;
    }
    
}
