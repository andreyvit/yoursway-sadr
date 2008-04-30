package com.yoursway.sadr.python.core.typeinferencing.scopes;

import org.eclipse.dltk.ast.declarations.MethodDeclaration;

import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo;
import com.yoursway.sadr.python.core.runtime.PythonCallableArgument;
import com.yoursway.sadr.python.core.runtime.PythonScopedVariable;
import com.yoursway.sadr.python.core.runtime.PythonProcedure;
import com.yoursway.sadr.python.core.runtime.PythonSourceProcedure;
import com.yoursway.sadr.python.core.runtime.PythonVariable;

public class ProcedureScope extends LocalScope {
    
    private final PythonSourceProcedure procedure;
    private final DtlArgumentVariable[] argumentVariables;
    
    public ProcedureScope(Scope parent, PythonSourceProcedure procedure, MethodDeclaration node) {
        super(parent, node);
        this.procedure = procedure;
        argumentVariables = createArguments(procedure);
    }
    
    private DtlArgumentVariable[] createArguments(PythonProcedure procedure) {
        PythonCallableArgument[] args = procedure.arguments();
        DtlArgumentVariable[] argVars = new DtlArgumentVariable[args.length];
        for (int i = 0; i < args.length; i++)
            argVars[i] = new DtlArgumentVariable(procedure, args[i], (null));
        return argVars;
    }
    
    @Override
    public MethodDeclaration node() {
        return (MethodDeclaration) super.node();
    }
    
    public PythonProcedure getProcedure() {
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
    public PythonVariable findOwnVariable(String name) {
        for (DtlArgumentVariable var : argumentVariables)
            if (var.name().equalsIgnoreCase(name))
                return var;
        return procedure.findLocalVariable(name);
    }
    
    @Override
    public PythonVariable lookupVariable(String name) {
        PythonVariable variable = findVariable(name);
        if (variable == null)
            variable = new PythonScopedVariable(procedure, null, procedure.parentScope(), name);
        return variable;
    }
    
    public ValueInfo selfType() {
        return null;
    }
    
}
