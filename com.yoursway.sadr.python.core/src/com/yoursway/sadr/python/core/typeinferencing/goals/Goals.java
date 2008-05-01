package com.yoursway.sadr.python.core.typeinferencing.goals;

import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.core.runtime.PythonField;
import com.yoursway.sadr.python.core.runtime.PythonScopedVariable;
import com.yoursway.sadr.python.core.runtime.PythonVariable;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonDynamicContext;
import com.yoursway.sadr.python.core.typeinferencing.constructs.Scope;
import com.yoursway.sadr.python.core.typeinferencing.scopes.DtlArgumentVariable;

public class Goals {
    
    public static ValueInfoGoal createVariableTypeGoal(PythonVariable variable, InfoKind kind,
            PythonDynamicContext dc, Scope megapack) {
        if (variable instanceof PythonScopedVariable)
            return new ScopedVariableValueInfoGoal((PythonScopedVariable) variable, kind, dc);
        if (variable instanceof PythonField)
            return new FieldValueInfoGoal((PythonField) variable, kind, megapack.searchService());
        if (variable instanceof DtlArgumentVariable)
            return new ArgumentVariableValueInfoGoal((DtlArgumentVariable) variable, kind, megapack);
        //        if (variable instanceof ForCounterVariable)
        //            return new ForCounterVariableValueInfoGoal((ForCounterVariable) variable, kind);
        throw new IllegalArgumentException("Unsupported variable type");
    }
    
}
