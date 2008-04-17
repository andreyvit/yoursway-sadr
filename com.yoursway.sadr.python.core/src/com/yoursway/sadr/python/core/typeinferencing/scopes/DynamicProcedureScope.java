package com.yoursway.sadr.python.core.typeinferencing.scopes;

import static com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo.emptyValueInfo;

import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo;
import com.yoursway.sadr.python.core.runtime.PythonCallableArgument;
import com.yoursway.sadr.python.core.runtime.PythonProcedure;
import com.yoursway.sadr.python.core.runtime.PythonVariable;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.services.VariableLookup;

public class DynamicProcedureScope extends ChildScope implements VariableLookup {
    
    private final DtlArgumentVariable[] argumentVariables;
    
    public DynamicProcedureScope(ProcedureScope parent, ValueInfo[] arguments) {
        super(parent);
        
        PythonProcedure procedure = parent.getProcedure();
        argumentVariables = createArguments(procedure, arguments);
    }
    
    private DtlArgumentVariable[] createArguments(PythonProcedure procedure, ValueInfo[] arguments) {
        PythonCallableArgument[] args = procedure.arguments();
        DtlArgumentVariable[] argVars = new DtlArgumentVariable[args.length];
        for (int i = 0; i < args.length; i++)
            argVars[i] = new DtlArgumentVariable(procedure, args[i], (arguments.length > i ? arguments[i]
                    : emptyValueInfo()));
        return argVars;
    }
    
    @Override
    protected String leafToString() {
        return null;
    }
    
    public ValueInfo selfType() {
        return null;
    }
    
    public VariableLookup variableLookup() {
        return this;
    }
    
    public PythonVariable findVariable(String name) {
        for (DtlArgumentVariable var : argumentVariables)
            if (var.name().equalsIgnoreCase(name))
                return var;
        return parent.variableLookup().findVariable(name);
    }
    
    public PythonConstruct createConstruct() {
        return parent.createConstruct();
    }
    
    public PythonVariable lookupVariable(String name) {
        return findVariable(name);
    }
    
}
