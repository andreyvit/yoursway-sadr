package com.yoursway.sadr.python.core.typeinferencing.scopes;

import static com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo.emptyValueInfo;

import com.yoursway.sadr.python.core.runtime.RubyArgument;
import com.yoursway.sadr.python.core.runtime.RubyProcedure;
import com.yoursway.sadr.python.core.runtime.RubyVariable;
import com.yoursway.sadr.python.core.typeinferencing.constructs.dtl.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.python.core.typeinferencing.services.VariableLookup;

public class DynamicProcedureScope extends ChildScope implements VariableLookup {
    
    private final DtlArgumentVariable[] argumentVariables;
    
    public DynamicProcedureScope(ProcedureScope parent, ValueInfo[] arguments) {
        super(parent);
        
        RubyProcedure procedure = parent.getProcedure();
        argumentVariables = createArguments(procedure, arguments);
    }
    
    private DtlArgumentVariable[] createArguments(RubyProcedure procedure, ValueInfo[] arguments) {
        RubyArgument[] args = procedure.arguments();
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
    
    public RubyVariable findVariable(String name) {
        for (DtlArgumentVariable var : argumentVariables)
            if (var.name().equalsIgnoreCase(name))
                return var;
        return parent.variableLookup().findVariable(name);
    }
    
    public PythonConstruct createConstruct() {
        return parent.createConstruct();
    }
}
