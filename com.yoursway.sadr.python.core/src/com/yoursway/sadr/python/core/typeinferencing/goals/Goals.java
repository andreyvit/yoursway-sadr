package com.yoursway.sadr.python.core.typeinferencing.goals;

import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.core.runtime.RubyField;
import com.yoursway.sadr.python.core.runtime.RubyLocalVariable;
import com.yoursway.sadr.python.core.runtime.RubyVariable;
import com.yoursway.sadr.python.core.typeinferencing.scopes.DtlArgumentVariable;
import com.yoursway.sadr.python.core.typeinferencing.services.ServicesMegapack;

public class Goals {
    
    public static ValueInfoGoal createVariableTypeGoal(RubyVariable variable, InfoKind kind,
            ServicesMegapack megapack) {
        if (variable instanceof RubyLocalVariable)
            return new LocalVariableValueInfoGoal((RubyLocalVariable) variable, kind);
        if (variable instanceof RubyField)
            return new FieldValueInfoGoal((RubyField) variable, kind);
        if (variable instanceof DtlArgumentVariable)
            return new ArgumentVariableValueInfoGoal((DtlArgumentVariable) variable, kind, megapack);
        //        if (variable instanceof ForCounterVariable)
        //            return new ForCounterVariableValueInfoGoal((ForCounterVariable) variable, kind);
        throw new IllegalArgumentException("Unsupported variable type");
    }
    
}
