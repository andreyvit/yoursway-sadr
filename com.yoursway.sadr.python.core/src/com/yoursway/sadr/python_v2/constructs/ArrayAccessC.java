package com.yoursway.sadr.python_v2.constructs;

import static com.google.common.collect.Iterators.transform;
import static com.google.common.collect.Lists.newArrayList;

import java.util.Collection;
import java.util.Map;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonArrayAccessExpression;

import com.google.common.base.Function;
import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.core.typeinferencing.goals.ExpressionValueInfoGoal;
import com.yoursway.sadr.python.core.typeinferencing.goals.MumblaWumblaThreesome;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfoGoal;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfoUtils;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.goals.ExpressionValueGoal;
import com.yoursway.sadr.python_v2.goals.ResolveNameToObjectGoal;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.goals.acceptors.ResultsCollector;
import com.yoursway.sadr.python_v2.goals.internal.CallResolver;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.PythonArguments;
import com.yoursway.sadr.python_v2.model.RuntimeObject;
import com.yoursway.sadr.python_v2.model.builtins.PythonClassType;
import com.yoursway.sadr.succeeder.IGoal;
import com.yoursway.sadr.succeeder.IGrade;

public class ArrayAccessC extends PythonConstructImpl<PythonArrayAccessExpression> {
    
    private static final Function<MumblaWumblaThreesome, MumblaWumblaThreesome> WRAP_INTO_ARRAY = new Function<MumblaWumblaThreesome, MumblaWumblaThreesome>() {
        
        public MumblaWumblaThreesome apply(MumblaWumblaThreesome from) {
            return from.wrapIntoArray();
        }
        
    };
    private final PythonConstruct array;
    private final PythonConstruct index;
    
    ArrayAccessC(Scope sc, PythonArrayAccessExpression node) {
        super(sc, node);
        array = wrap(node.getArray());
        index = wrap((ASTNode) node.getIndex().getChilds().get(0));
    }
    
    public PythonConstruct array() {
        return array;
    }
    
    @Override
    public IGoal evaluate(final Context context, PythonValueSetAcceptor acceptor) {
        return new ExpressionValueGoal(context, acceptor) {
            public void preRun() {
                ResultsCollector rc = new ResultsCollector(2, context) {
                    @Override
                    public <T> void completed(IGrade<T> grade) {
                        Map<Object, RuntimeObject> results = getResults();
                        RuntimeObject arrayObject = results.get(0);
                        PythonArguments args = new PythonArguments();
                        args.getArgs().add(results.get(1));
                        if (arrayObject.getType() instanceof PythonClassType) {
                            schedule(CallResolver.callMethod(arrayObject, "__getitem__", args, acceptor,
                                    context));
                        }
                    }
                };
                schedule(new ResolveNameToObjectGoal((VariableReferenceC) array, rc.createAcceptor(0),
                        context));
                schedule(index.evaluate(context, rc.createAcceptor(1)));
                rc.startCollecting();
            }
        };
    }
    
    public ContinuationRequestorCalledToken evaluateValue(final PythonDynamicContext dc,
            final InfoKind infoKind, ContinuationScheduler requestor, final ValueInfoContinuation continuation) {
        final Expression array = node.getArray();
        return requestor.schedule(new Continuation() {
            
            private final ValueInfoGoal nameGoal = new ExpressionValueInfoGoal(subconstructFor(array), dc,
                    infoKind);
            
            public Goal[] provideSubgoals() {
                return new Goal[] { nameGoal };
            }
            
            public void done(ContinuationScheduler requestor) {
                continuation.consume(ValueInfoUtils.unwrapArray(nameGoal.result(null)), requestor);
            }
            
        });
    }
    
    @Override
    public Collection<MumblaWumblaThreesome> mumblaWumbla() {
        return newArrayList(transform(array().mumblaWumbla().iterator(), WRAP_INTO_ARRAY));
    }
    
}
