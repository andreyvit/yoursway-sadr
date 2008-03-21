package com.yoursway.sadr.ruby.core.typeinferencing.services;

import static com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo.emptyValueInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.Query;
import com.yoursway.sadr.engine.SimpleContinuation;
import com.yoursway.sadr.engine.SubgoalRequestor;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.ConstructControlFlowTraverser;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.ConstructVisitor;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.Request;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyConstruct;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.BackwardPropagationEntryPoint;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo;

public class PropagationTrackerImpl implements PropagationTracker {
    
    private final Map<Goal, Query> entryPoints = new HashMap<Goal, Query>();
    
    public void initiateBackwardPropagation(final Goal propagatingGoal,
            final ContinuationRequestor requestor, final ValueInfoContinuation continuation) {
        final Query currentQuery = requestor.currentQuery();
        
        Query query = calculateStartingPoint(currentQuery);
        
        final Collection<Goal> propagatedGoals = new ArrayList<Goal>();
        
        ValueInfoContinuation continuation2 = new ValueInfoContinuation() {
            
            public void consume(ValueInfo result, ContinuationRequestor requestor) {
                for (Goal goal : propagatedGoals)
                    entryPoints.remove(goal);
                currentQuery.goal().addPropagatingGoal(propagatingGoal);
                continuation.consume(result, requestor);
            }
            
        };
        
        //        Scope previousScope = null;
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
        
        continuation2.consume(emptyValueInfo(), requestor);
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
    
    public void traverseEntirely(RubyConstruct rubyConstruct, final Request request,
            ContinuationRequestor requestor, SimpleContinuation continuation) {
        ConstructControlFlowTraverser traverser = new ConstructControlFlowTraverser();
        traverser.traverse(rubyConstruct, requestor, new ConstructVisitor() {
            
            public ConstructVisitor enter(RubyConstruct rubyConstruct) {
                request.accept(rubyConstruct);
                return this;
            }
            
            public void leave() {
            }
            
        }, continuation);
    }
    
}
