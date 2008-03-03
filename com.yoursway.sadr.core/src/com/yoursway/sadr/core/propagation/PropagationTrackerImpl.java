package com.yoursway.sadr.core.propagation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.yoursway.sadr.core.BackwardPropagationEntryPoint;
import com.yoursway.sadr.core.IValueInfo;
import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.core.ValueInfos;
import com.yoursway.sadr.core.constructs.AbstractConstructTraverser;
import com.yoursway.sadr.core.constructs.ConstructControlFlowTraverser;
import com.yoursway.sadr.core.constructs.ConstructStaticStructureTraverser;
import com.yoursway.sadr.core.constructs.DynamicContext;
import com.yoursway.sadr.core.constructs.IConstruct;
import com.yoursway.sadr.core.constructs.Request;
import com.yoursway.sadr.core.constructs.StaticContext;
import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.Query;
import com.yoursway.sadr.engine.SimpleContinuation;
import com.yoursway.sadr.engine.SubgoalRequestor;

public class PropagationTrackerImpl<C extends IConstruct<C, SC, DC, N>, SC extends StaticContext<C, SC, DC, N>, DC extends DynamicContext, N>
        implements PropagationTracker<C, SC, DC, N> {
    
    public static <C extends IConstruct<C, SC, DC, N>, SC extends StaticContext<C, SC, DC, N>, DC extends DynamicContext, N> PropagationTracker<C, SC, DC, N> create() {
        return new PropagationTrackerImpl<C, SC, DC, N>();
    }
    
    private final Map<Goal, Query> entryPoints = new HashMap<Goal, Query>();
    
    public void initiateBackwardPropagation(final Goal propagatingGoal,
            final ContinuationRequestor requestor, final ValueInfoContinuation continuation) {
        final Query currentQuery = requestor.currentQuery();
        
        Query query = calculateStartingPoint(currentQuery);
        
        final Collection<Goal> propagatedGoals = new ArrayList<Goal>();
        
        ValueInfoContinuation continuation2 = new ValueInfoContinuation() {
            
            public void consume(IValueInfo result, ContinuationRequestor requestor) {
                for (Goal goal : propagatedGoals)
                    entryPoints.remove(goal);
                currentQuery.goal().addPropagatingGoal(propagatingGoal);
                continuation.consume(result, requestor);
            }
            
        };
        
        for (; query != null; query = query.parent()) {
            Goal goal = query.goal();
            if (goal instanceof BackwardPropagationEntryPoint) {
                final Query epQuery = query;
                final BackwardPropagationEntryPoint ep = (BackwardPropagationEntryPoint) goal;
                if (ep.backwardPropagation(propagatingGoal, new ContinuationRequestor() {
                    
                    public Query currentQuery() {
                        return requestor.currentQuery();
                    }
                    
                    public void subgoal(Continuation cont) {
                        cont.provideSubgoals(new SubgoalRequestor() {
                            
                            public void subgoal(Goal goal) {
                                propagatedGoals.add(goal);
                                entryPoints.put(goal, epQuery);
                            }
                            
                        });
                        requestor.subgoal(cont);
                    }
                    
                    public void done() {
                        requestor.done();
                    }
                    
                }, continuation2))
                    return;
            }
        }
        
        continuation2.consume(ValueInfos.empty(), requestor);
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
    
    public void traverseEntirely(C construct, final Request<C, SC, DC, N> request,
            ContinuationRequestor requestor, SimpleContinuation continuation) {
        AbstractConstructTraverser<C, SC, DC, N> traverser = new ConstructControlFlowTraverser<C, SC, DC, N>();
        traverser.traverse(construct, requestor, request, continuation);
    }
    
    public void traverseStatically(C construct, final Request<C, SC, DC, N> request,
            ContinuationRequestor requestor, SimpleContinuation continuation) {
        AbstractConstructTraverser<C, SC, DC, N> traverser = new ConstructStaticStructureTraverser<C, SC, DC, N>();
        traverser.traverse(construct, requestor, request, continuation);
    }
    
}
