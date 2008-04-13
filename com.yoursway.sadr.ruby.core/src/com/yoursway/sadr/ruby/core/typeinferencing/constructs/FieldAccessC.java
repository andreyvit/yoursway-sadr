package com.yoursway.sadr.ruby.core.typeinferencing.constructs;

import static com.google.common.collect.Lists.newArrayList;
import static com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo.emptyValueInfo;

import java.util.Collection;

import org.eclipse.dltk.ast.references.VariableReference;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.ruby.core.runtime.RubyField;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.AccessInfo;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.MergeFieldsValueInfosContinuation;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.ruby.core.typeinferencing.keys.wildcards.StarWildcard;

public class FieldAccessC extends RubyConstructImpl<VariableReference> {
    
    public FieldAccessC(RubyStaticContext sc, VariableReference node) {
        super(sc, node);
    }
    
    public ContinuationRequestorCalledToken evaluateValue(RubyDynamicContext dc, InfoKind infoKind,
            ContinuationScheduler requestor, ValueInfoContinuation continuation) {
        
        //! duplicated from SelfC.evaluateValue
        ValueInfo selfType = dc.selfType();
        if (selfType == null)
            selfType = ((RubyDynamicContext) staticContext()).selfType();
        if (selfType == null)
            selfType = emptyValueInfo();
        
        Collection<RubyField> fields = selfType.lookupField(name());
        return requestor.schedule(new MergeFieldsValueInfosContinuation(fields, infoKind, continuation,
                staticContext().searchService()));
    }
    
    public String name() {
        return node.getName();
    }
    
    @Override
    public Collection<AccessInfo> accessInfos() {
        return newArrayList(new AccessInfo(new EmptyConstruct(staticContext()), node.getName(),
                StarWildcard.INSTANCE));
        
        //        RubyConstruct receiver = receiver();
        //        List<AccessInfo> result = newArrayList(transform(receiver.accessInfos().iterator(),
        //                new WrapIntoFieldAccess(node.getName())));
        //        result.add(new AccessInfo(receiver, name(), StarWildcard.INSTANCE));
        //        return result;
    }
    
}
