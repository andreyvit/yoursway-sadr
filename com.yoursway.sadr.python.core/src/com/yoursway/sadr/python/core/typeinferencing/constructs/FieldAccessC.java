package com.yoursway.sadr.python.core.typeinferencing.constructs;

import static com.google.common.collect.Iterators.transform;
import static com.google.common.collect.Lists.newArrayList;

import java.util.Collection;
import java.util.List;

import org.eclipse.dltk.python.parser.ast.expressions.PythonVariableAccessExpression;

import com.google.common.base.Function;
import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.engine.SubgoalRequestor;
import com.yoursway.sadr.python.core.runtime.PythonField;
import com.yoursway.sadr.python.core.typeinferencing.goals.ExpressionValueInfoGoal;
import com.yoursway.sadr.python.core.typeinferencing.goals.MergeFieldsValueInfosContinuation;
import com.yoursway.sadr.python.core.typeinferencing.goals.MumblaWumblaThreesome;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfoGoal;
import com.yoursway.sadr.python.core.typeinferencing.keys.wildcards.StarWildcard;

public class FieldAccessC extends PythonConstructImpl<PythonVariableAccessExpression> {
    
    private static final class WrapIntoFieldAccess implements
            Function<MumblaWumblaThreesome, MumblaWumblaThreesome> {
        
        private final String name;
        
        public WrapIntoFieldAccess(String name) {
            this.name = name;
        }
        
        public MumblaWumblaThreesome apply(MumblaWumblaThreesome from) {
            return from.wrapIntoField(name);
        }
        
    };
    
    FieldAccessC(PythonStaticContext sc, PythonVariableAccessExpression node) {
        super(sc, node);
    }
    
    public void evaluateValue(final PythonDynamicContext dc, final InfoKind infoKind,
            ContinuationRequestor requestor, final ValueInfoContinuation continuation) {
        requestor.subgoal(new Continuation() {
            
            private final ValueInfoGoal receiverGoal = new ExpressionValueInfoGoal(receiver(), dc, infoKind);
            
            public void provideSubgoals(SubgoalRequestor requestor) {
                requestor.subgoal(receiverGoal);
            }
            
            public void done(ContinuationRequestor requestor) {
                ValueInfo receiverInfo = receiverGoal.result(null);
                Collection<PythonField> fields = receiverInfo.lookupField(name());
                requestor.subgoal(new MergeFieldsValueInfosContinuation(fields, infoKind, continuation,
                        staticContext().searchService()));
            }
            
        });
    }
    
    public String name() {
        return node.variable().getName();
    }
    
    public PythonConstruct receiver() {
        return wrap(innerContext(), node.getReceiver());
    }
    
    @Override
    public Collection<MumblaWumblaThreesome> mumblaWumbla() {
        PythonConstruct receiver = receiver();
        List<MumblaWumblaThreesome> result = newArrayList(transform(receiver.mumblaWumbla().iterator(),
                new WrapIntoFieldAccess(node.getName())));
        result.add(new MumblaWumblaThreesome(receiver, name(), StarWildcard.INSTANCE));
        return result;
    }
    
}
