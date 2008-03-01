package com.yoursway.sadr.ruby.core.typeinferencing.goals;

import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.ruby.core.runtime.RubyField;
import com.yoursway.sadr.ruby.core.runtime.RubyGlobalVariable;
import com.yoursway.sadr.ruby.core.runtime.RubyLocalVariable;
import com.yoursway.sadr.ruby.core.runtime.RubyVariable;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.DtlArgumentVariable;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.ForCounterVariable;
import com.yoursway.sadr.ruby.core.typeinferencing.services.ServicesMegapack;

public class Goals {
    
    public static ValueInfoGoal createVariableTypeGoal(RubyVariable variable, InfoKind kind,
            ServicesMegapack megapack) {
        if (variable instanceof RubyLocalVariable)
            return new LocalVariableValueInfoGoal((RubyLocalVariable) variable, kind);
        if (variable instanceof RubyField)
            return new FieldValueInfoGoal((RubyField) variable, kind);
        if (variable instanceof DtlArgumentVariable)
            return new ArgumentVariableValueInfoGoal((DtlArgumentVariable) variable, kind, megapack);
        if (variable instanceof RubyGlobalVariable)
            return new GlobalVariableValueInfoGoal((RubyGlobalVariable) variable, kind, megapack
                    .searchService());
        if (variable instanceof ForCounterVariable)
            return new ForCounterVariableValueInfoGoal((ForCounterVariable) variable, kind);
        throw new IllegalArgumentException("Unsupported variable type");
    }
    
}
