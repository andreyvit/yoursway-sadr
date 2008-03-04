package com.yoursway.sadr.python.core.typeinferencing.goals;

import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.core.runtime.PythonField;
import com.yoursway.sadr.python.core.runtime.PythonLocalVariable;
import com.yoursway.sadr.python.core.runtime.PythonVariable;
import com.yoursway.sadr.python.core.typeinferencing.scopes.DtlArgumentVariable;
import com.yoursway.sadr.python.core.typeinferencing.services.ServicesMegapack;

public class Goals {
    
    public static ValueInfoGoal createVariableTypeGoal(PythonVariable variable, InfoKind kind,
            ServicesMegapack megapack) {
        if (variable instanceof PythonLocalVariable)
            return new LocalVariableValueInfoGoal((PythonLocalVariable) variable, kind);
        if (variable instanceof PythonField)
            return new FieldValueInfoGoal((PythonField) variable, kind);
        if (variable instanceof DtlArgumentVariable)
            return new ArgumentVariableValueInfoGoal((DtlArgumentVariable) variable, kind, megapack);
        //        if (variable instanceof ForCounterVariable)
        //            return new ForCounterVariableValueInfoGoal((ForCounterVariable) variable, kind);
        throw new IllegalArgumentException("Unsupported variable type");
    }
    
}
