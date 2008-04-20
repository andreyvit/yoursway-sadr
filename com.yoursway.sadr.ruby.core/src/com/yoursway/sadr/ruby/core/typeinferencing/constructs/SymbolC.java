package com.yoursway.sadr.ruby.core.typeinferencing.constructs;

import static com.google.common.collect.Lists.newArrayList;
import static com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo.emptyValueInfo;

import java.util.Collection;

import org.eclipse.dltk.ast.references.SimpleReference;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.ruby.core.runtime.RubyMetaClass;
import com.yoursway.sadr.ruby.core.runtime.RubyUtils;
import com.yoursway.sadr.ruby.core.runtime.RubyVariable;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.AccessInfo;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.Goals;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfoBuilder;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfoGoal;
import com.yoursway.sadr.ruby.core.typeinferencing.keys.wildcards.StarWildcard;
import com.yoursway.sadr.ruby.core.typeinferencing.types.MetaClassType;
import com.yoursway.sadr.ruby.core.typeinferencing.values.MetaClassValue;

public class SymbolC extends RubyConstructImpl<SimpleReference> {
    
    SymbolC(RubyStaticContext sc, SimpleReference node) {
        super(sc, node);
    }
    
    public ContinuationRequestorCalledToken evaluateValue(RubyDynamicContext dc, InfoKind infoKind,
            ContinuationScheduler requestor, final ValueInfoContinuation continuation) {
        String name = node.getName();
        RubyMetaClass mc = RubyUtils.resolveStaticClassReference(staticContext().classLookup(), node);
        if (mc != null) {
            ValueInfoBuilder builder = new ValueInfoBuilder();
            //            RubySimpleType t = staticContext().builtins().intType();
            builder.add(new MetaClassType(mc), new MetaClassValue(mc));
            return continuation.consume(builder.build(), requestor);
        } else {
            RubyVariable variable = dc.variableLookup().findVariable(name);
            if (variable == null)
                variable = staticContext().variableLookup().lookupVariable(name);
            if (variable == null) {
                return continuation.consume(emptyValueInfo(), requestor);
            } else {
                final ValueInfoGoal varGoal = Goals.createVariableTypeGoal(variable, infoKind, dc,
                        staticContext());
                return requestor.schedule(new Continuation() {
                    
                    public Goal[] provideSubgoals() {
                        return new Goal[] { varGoal };
                    }
                    
                    public void done(ContinuationScheduler requestor) {
                        continuation.consume(varGoal.roughResult(), requestor);
                    }
                    
                });
            }
        }
    }
    
    @Override
    public Collection<AccessInfo> accessInfos() {
        return newArrayList(new AccessInfo(null, node.getName(), StarWildcard.INSTANCE));
    }
    
}
