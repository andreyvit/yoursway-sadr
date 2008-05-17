package com.yoursway.sadr.python.core.typeinferencing.constructs;

import org.eclipse.dltk.python.parser.ast.expressions.PythonVariableAccessExpression;

import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.goals.FieldReadF;
import com.yoursway.sadr.python_v2.goals.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.goals.ReadFieldGoal;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.succeeder.IGoal;

public class FieldAccessC extends PythonConstructImpl<PythonVariableAccessExpression> {
    
    //    private static final class WrapIntoFieldAccess implements
    //            Function<MumblaWumblaThreesome, MumblaWumblaThreesome> {
    //        
    //        private final String name;
    //        
    //        public WrapIntoFieldAccess(String name) {
    //            this.name = name;
    //        }
    //        
    //        public MumblaWumblaThreesome apply(MumblaWumblaThreesome from) {
    //            return from.wrapIntoField(name);
    //        }
    //        
    //    };
    
    private final PythonConstruct receiver;
    private final VariableReferenceC variable;
    
    FieldAccessC(Scope sc, PythonVariableAccessExpression node) {
        super(sc, node);
        this.receiver = wrap(node.getReceiver(), sc);
        this.variable = new VariableReferenceC(sc, node.variable());
    }
    
    //    public ContinuationRequestorCalledToken evaluateValue(final PythonDynamicContext dc,
    //            final InfoKind infoKind, ContinuationScheduler requestor, final ValueInfoContinuation continuation) {
    //        return requestor.schedule(new Continuation() {
    //            
    //            private final ValueInfoGoal receiverGoal = new ExpressionValueInfoGoal(receiver(), dc, infoKind);
    //            
    //            public Goal[] provideSubgoals() {
    //                return new Goal[] { receiverGoal };
    //            }
    //            
    //            public void done(ContinuationScheduler requestor) {
    //                ValueInfo receiverInfo = receiverGoal.result(null);
    //                Collection<PythonField> fields = ValueInfoUtils.lookupField(receiverInfo, name());
    //                requestor.schedule(new MergeFieldsValueInfosContinuation(fields, infoKind, continuation,
    //                        staticContext().searchService()));
    //            }
    //            
    //        });
    //    }
    
    public String fieldName() {
        return node.variable().getName();
    }
    
    //    @Override
    //    public Collection<MumblaWumblaThreesome> mumblaWumbla() {
    //        PythonConstruct receiver = receiver();
    //        List<MumblaWumblaThreesome> result = newArrayList(transform(receiver.mumblaWumbla().iterator(),
    //                new WrapIntoFieldAccess(node.getName())));
    //        result.add(new MumblaWumblaThreesome(receiver, name(), StarWildcard.INSTANCE));
    //        return result;
    //    }
    
    @Override
    public IGoal evaluate(final Context context, PythonValueSetAcceptor acceptor) {
        return new ReadFieldGoal(context, receiver(), variable(), acceptor);
    }
    
    public PythonConstruct receiver() {
        return receiver;
    }
    
    public VariableReferenceC variable() {
        return variable;
    }
    
    @Override
    public Frog toFrog() {
        return new FieldReadF(receiver.toFrog(), variable.node().getName());
    }
}
