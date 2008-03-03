package com.yoursway.sadr.python.core.typeinferencing.constructs.dtl;

import static com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo.emptyValueInfo;

import org.eclipse.dltk.ast.references.VariableReference;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.core.runtime.RubyMetaClass;
import com.yoursway.sadr.python.core.runtime.RubyUtils;
import com.yoursway.sadr.python.core.runtime.RubyVariable;
import com.yoursway.sadr.python.core.typeinferencing.goals.Goals;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfoBuilder;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfoGoal;
import com.yoursway.sadr.python.core.typeinferencing.services.ServicesMegapack;
import com.yoursway.sadr.python.core.typeinferencing.types.MetaClassType;
import com.yoursway.sadr.python.core.typeinferencing.values.MetaClassValue;

public class VariableReferenceC extends PythonConstructImpl<VariableReference> {
    
    VariableReferenceC(PythonStaticContext sc, VariableReference node) {
        super(sc, node);
    }
    
    public void evaluateValue(PythonDynamicContext dc, InfoKind infoKind, ContinuationRequestor requestor,
            final ValueInfoContinuation continuation) {
        String name = node.getName();
        RubyMetaClass mc = RubyUtils.resolveStaticClassReference(staticContext().classLookup(), node);
        if (mc != null) {
            ValueInfoBuilder builder = new ValueInfoBuilder();
            //            RubySimpleType t = staticContext().builtins().intType();
            builder.add(new MetaClassType(mc), new MetaClassValue(mc));
            continuation.consume(builder.build(), requestor);
        } else {
            RubyVariable variable = dc.variableLookup().findVariable(name);
            if (variable == null)
                variable = staticContext().variableLookup().lookupVariable(name);
            if (variable == null) {
                continuation.consume(emptyValueInfo(), requestor);
            } else {
                final ValueInfoGoal varGoal = Goals.createVariableTypeGoal(variable, infoKind,
                        (ServicesMegapack) staticContext());
                requestor.subgoal(new SingleSubgoalContinuation(varGoal, continuation));
            }
        }
    }
    
}
