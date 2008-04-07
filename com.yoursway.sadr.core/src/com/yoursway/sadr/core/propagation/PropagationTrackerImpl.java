package com.yoursway.sadr.core.propagation;

import static com.google.common.collect.Lists.newArrayList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.yoursway.sadr.core.BackwardPropagationEntryPoint;
import com.yoursway.sadr.core.IValueInfo;
import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.core.ValueInfos;
import com.yoursway.sadr.core.constructs.AbstractConstructTraverser;
import com.yoursway.sadr.core.constructs.BackwardRequest;
import com.yoursway.sadr.core.constructs.CfgCursor;
import com.yoursway.sadr.core.constructs.ConstructControlFlowTraverser;
import com.yoursway.sadr.core.constructs.ConstructStaticStructureTraverser;
import com.yoursway.sadr.core.constructs.ControlFlowGraph;
import com.yoursway.sadr.core.constructs.ControlFlowGraphRequestor;
import com.yoursway.sadr.core.constructs.DynamicContext;
import com.yoursway.sadr.core.constructs.IConstruct;
import com.yoursway.sadr.core.constructs.Request;
import com.yoursway.sadr.core.constructs.StaticContext;
import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.Continuations;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.IterationContinuation;
import com.yoursway.sadr.engine.Query;
import com.yoursway.sadr.engine.SimpleContinuation;
import com.yoursway.sadr.engine.SubgoalRequestor;

public class PropagationTrackerImpl<C extends IConstruct<C, SC, DC, N>, SC extends StaticContext<C, SC, DC, N>, DC extends DynamicContext, N>
        implements PropagationTracker<C, SC, DC, N> {
    
    public static <C extends IConstruct<C, SC, DC, N>, SC extends StaticContext<C, SC, DC, N>, DC extends DynamicContext, N> PropagationTracker<C, SC, DC, N> create() {
        return new PropagationTrackerImpl<C, SC, DC, N>();
    }
    
    private final Map<Goal, Query> entryPoints = new HashMap<Goal, Query>();
    
    public ContinuationRequestorCalledToken initiateBackwardPropagation(final Goal propagatingGoal,
            final ContinuationScheduler requestor, final ValueInfoContinuation continuation) {
        final Query currentQuery = requestor.currentQuery();
        
        Query query = calculateStartingPoint(currentQuery);
        
        final Collection<Goal> propagatedGoals = new ArrayList<Goal>();
        
        ValueInfoContinuation continuation2 = new ValueInfoContinuation() {
            
            public ContinuationRequestorCalledToken consume(IValueInfo result, ContinuationScheduler requestor) {
                for (Goal goal : propagatedGoals)
                    entryPoints.remove(goal);
                currentQuery.goal().addPropagatingGoal(propagatingGoal);
                return continuation.consume(result, requestor);
            }
            
        };
        
        for (; query != null; query = query.parent()) {
            Goal goal = query.goal();
            if (goal instanceof BackwardPropagationEntryPoint) {
                final Query epQuery = query;
                final BackwardPropagationEntryPoint ep = (BackwardPropagationEntryPoint) goal;
                if (ep.backwardPropagation(propagatingGoal, new ContinuationScheduler() {
                    
                    public Query currentQuery() {
                        return requestor.currentQuery();
                    }
                    
                    public ContinuationRequestorCalledToken schedule(Continuation cont) {
                        cont.provideSubgoals(new SubgoalRequestor() {
                            
                            public void subgoal(Goal goal) {
                                propagatedGoals.add(goal);
                                entryPoints.put(goal, epQuery);
                            }
                            
                        });
                        return requestor.schedule(cont);
                    }
                    
                    public ContinuationRequestorCalledToken done() {
                        return requestor.done();
                    }
                    
                    public ContinuationRequestorCalledToken schedule(SimpleContinuation cont) {
                        return requestor.schedule(cont);
                    }
                    
                }, continuation2))
                    return requestor.done();
            }
        }
        
        return continuation2.consume(ValueInfos.empty(), requestor);
    }
    
    private Query calculateStartingPoint(final Query currentQuery) {
        Query query = currentQuery;
        Query entryPointQuery = findPropagationEntryPoint(currentQuery);
        if (entryPointQuery != null)
            query = entryPointQuery.parent();
        return query;
    }
    
    private Query findPropagationEntryPoint(final Query currentQuery) {
        for (Query query = currentQuery; query != null; query = query.parent()) {
            Goal goal = query.goal();
            Query ep = entryPoints.get(goal);
            if (ep != null)
                return ep;
        }
        return null;
    }
    
    @SuppressWarnings("unchecked")
    private ConstructBoundGoal<C, SC, DC, N> findPreviousConstructBoundGoal(final Query currentQuery) {
        for (Query query = currentQuery; query != null; query = query.parent()) {
            Goal goal = query.goal();
            if (goal instanceof ConstructBoundGoal)
                return (ConstructBoundGoal<C, SC, DC, N>) goal;
        }
        return null;
    }
    
    public ContinuationRequestorCalledToken traverseEntirely(C construct,
            final Request<C, SC, DC, N> request, ContinuationScheduler requestor,
            SimpleContinuation continuation) {
        AbstractConstructTraverser<C, SC, DC, N> traverser = new ConstructControlFlowTraverser<C, SC, DC, N>();
        return traverser.traverse(construct, requestor, request, continuation);
    }
    
    public ContinuationRequestorCalledToken traverseStatically(C construct,
            final Request<C, SC, DC, N> request, ContinuationScheduler requestor,
            SimpleContinuation continuation) {
        AbstractConstructTraverser<C, SC, DC, N> traverser = new ConstructStaticStructureTraverser<C, SC, DC, N>();
        return traverser.traverse(construct, requestor, request, continuation);
    }
    
    public ContinuationRequestorCalledToken traverseBackwardByControlFlowFromLastConstructBoundGoalConstruct(
            BackwardRequest<C, SC, DC, N> request, ContinuationScheduler requestor,
            SimpleContinuation continuation) {
        Query query = requestor.currentQuery();
        ConstructBoundGoal<C, SC, DC, N> cbg = findPreviousConstructBoundGoal(query);
        C construct = cbg.construct();
        return traverseBackwardByControlFlowFromLastConstructBoundGoalConstruct_(construct, request,
                requestor, continuation);
    }
    
    private ContinuationRequestorCalledToken traverseBackwardByControlFlowFromLastConstructBoundGoalConstruct_(
            final C construct, final BackwardRequest<C, SC, DC, N> request, ContinuationScheduler requestor,
            final SimpleContinuation continuation) {
        final C parent = construct.parent();
        if (parent == null) {
            return continuation.run(requestor);
        }
        return parent.calculateEffectiveControlFlowGraph(requestor,
                new ControlFlowGraphRequestor<C, SC, DC, N>() {
                    
                    public ContinuationRequestorCalledToken process(
                            ControlFlowGraph<C, SC, DC, N> effectiveGraph, ContinuationScheduler requestor) {
                        CfgCursor<C, SC, DC, N> cursor = effectiveGraph.cursorAt(construct);
                        if (cursor == null)
                            return traverseBackwardByControlFlowFromLastConstructBoundGoalConstruct_(parent,
                                    request, requestor, continuation);
                        else
                            return traverseBackwardByControlFlowFromLastConstructBoundGoalConstruct__(parent,
                                    cursor.previous(), request, requestor, continuation);
                    }
                    
                });
    }
    
    protected ContinuationRequestorCalledToken traverseBackwardByControlFlowFromLastConstructBoundGoalConstruct__(
            final C parent, final CfgCursor<C, SC, DC, N> cursor,
            final BackwardRequest<C, SC, DC, N> request, ContinuationScheduler requestor,
            final SimpleContinuation continuation) {
        if (cursor.isBof()) {
            return traverseBackwardByControlFlowFromLastConstructBoundGoalConstruct_(parent, request,
                    requestor, continuation);
        } else {
            C construct = cursor.current();
            return construct.calculateEffectiveControlFlowGraph(requestor,
                    new ControlFlowGraphRequestor<C, SC, DC, N>() {
                        
                        public ContinuationRequestorCalledToken process(
                                ControlFlowGraph<C, SC, DC, N> effectiveGraph, ContinuationScheduler requestor) {
                            return Continuations.iterate(effectiveGraph.getNodes().iterator(),
                                    new IterationContinuation<C>() {
                                        
                                        public ContinuationRequestorCalledToken iteration(C value,
                                                ContinuationScheduler requestor,
                                                SimpleContinuation continuation) {
                                            request.visit(value);
                                            return continuation.run(requestor);
                                        }
                                        
                                    }, requestor, new SimpleContinuation() {
                                        
                                        public ContinuationRequestorCalledToken run(
                                                ContinuationScheduler requestor) {
                                            return traverseBackwardByControlFlowFromLastConstructBoundGoalConstruct___(
                                                    parent, cursor, request, requestor, continuation);
                                        }
                                        
                                    });
                        }
                        
                    });
        }
    }
    
    protected ContinuationRequestorCalledToken traverseBackwardByControlFlowFromLastConstructBoundGoalConstruct___(
            C parent, CfgCursor<C, SC, DC, N> cursor, BackwardRequest<C, SC, DC, N> request,
            ContinuationScheduler requestor, SimpleContinuation continuation) {
        C construct = cursor.current();
        request.visit(construct);
        if (request.done())
            return continuation.run(requestor);
        else
            return traverseBackwardByControlFlowFromLastConstructBoundGoalConstruct__(parent, cursor
                    .previous(), request, requestor, continuation);
    }
    
    public Collection<C> callStack(DC dc, ContinuationScheduler requestor) {
        Query query = requestor.currentQuery();
        ConstructBoundGoal<C, SC, DC, N> cbg = findPreviousConstructBoundGoal(query);
        Collection<C> result = newArrayList();
        for (C construct = cbg.construct(); construct != null; construct = construct.parent())
            result.add(construct);
        return result;
    }
    
}
