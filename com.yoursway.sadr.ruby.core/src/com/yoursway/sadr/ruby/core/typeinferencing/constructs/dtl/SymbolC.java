package com.yoursway.sadr.ruby.core.typeinferencing.constructs.dtl;

import static com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo.emptyValueInfo;

import org.eclipse.dltk.ast.references.SimpleReference;

import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.engine.SubgoalRequestor;
import com.yoursway.sadr.ruby.core.runtime.RubyMetaClass;
import com.yoursway.sadr.ruby.core.runtime.RubyUtils;
import com.yoursway.sadr.ruby.core.runtime.RubyVariable;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.DynamicContext;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.StaticContext;
import com.yoursway.sadr.ruby.core.typeinferencing.engine.ValueInfoContinuation;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.Goals;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfoBuilder;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfoGoal;
import com.yoursway.sadr.ruby.core.typeinferencing.services.ServicesMegapack;
import com.yoursway.sadr.ruby.core.typeinferencing.types.MetaClassType;
import com.yoursway.sadr.ruby.core.typeinferencing.values.MetaClassValue;

public class SymbolC extends DtlConstruct<SimpleReference> {
    
    SymbolC(StaticContext sc, SimpleReference node) {
        super(sc, node);
    }
    
    public void evaluateValue(DynamicContext dc, InfoKind infoKind, ContinuationRequestor requestor,
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
            if (variable == null) {
                continuation.consume(emptyValueInfo(), requestor);
            } else {
                final ValueInfoGoal varGoal = Goals.createVariableTypeGoal(variable, infoKind,
                        (ServicesMegapack) dc);
                requestor.subgoal(new Continuation() {
                    
                    public void provideSubgoals(SubgoalRequestor requestor) {
                        requestor.subgoal(varGoal);
                    }
                    
                    public void done(ContinuationRequestor requestor) {
                        continuation.consume(varGoal.resultWithoutKarma(), requestor);
                    }
                    
                });
            }
        }
    }
    
}
