package com.yoursway.sadr.python.core.typeinferencing.goals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.expressions.CallExpression;

import com.yoursway.sadr.engine.AbstractGoal;
import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.engine.Result;
import com.yoursway.sadr.engine.Sinner;
import com.yoursway.sadr.engine.SubgoalRequestor;
import com.yoursway.sadr.python.core.ast.visitor.RubyAstVisitor;
import com.yoursway.sadr.python.core.runtime.Callable;
import com.yoursway.sadr.python.core.runtime.RubyBasicClass;
import com.yoursway.sadr.python.core.runtime.RubyMethod;
import com.yoursway.sadr.python.core.runtime.RubyUtils;
import com.yoursway.sadr.python.core.typeinferencing.engine.Construct;
import com.yoursway.sadr.python.core.typeinferencing.scopes.FileScope;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python.core.typeinferencing.services.CallsRequestor;
import com.yoursway.sadr.python.core.typeinferencing.services.SearchService;

public class MethodCallersGoal extends AbstractGoal {
    
    private final Callable callable;
    
    private CallersInfo callers;
    
    private final SearchService searchService;
    
    public MethodCallersGoal(Callable callable, SearchService searchService) {
        this.searchService = searchService;
        if (callable instanceof RubyMethod) {
            RubyMethod method = (RubyMethod) callable;
            if (method.name().equals("init"))
                callable = method.runtimeClass().metaClass().findMethod("new");
        }
        this.callable = callable;
    }
    
    public void copyAnswerFrom(Goal goal) {
        copyAnswerFrom(((MethodCallersGoal) goal).result(thou()));
    }
    
    public void copyAnswerFrom(Result result) {
        callers = (CallersInfo) result;
        done();
    }
    
    @Override
    public void done() {
        if (callers == null)
            callers = new CallersInfo(createArray(0));
    }
    
    public void causesRecursion() {
        done();
    }
    
    public CallersInfo result(Sinner victim) {
        punish(victim);
        return resultWithoutKarma();
    }
    
    @SuppressWarnings("unchecked")
    public CallersInfo resultWithoutKarma() {
        if (callers == null)
            throw new IllegalStateException("MethodCallersGoal.result() before done()");
        return callers;
    }
    
    public void evaluate(ContinuationRequestor requestor) {
        final List<Construct<Scope, CallExpression>> possibleCallers = new ArrayList<Construct<Scope, CallExpression>>();
        boolean isMethod = (callable instanceof RubyMethod);
        //        for (FileScope fileScope : searchService.searchForEverything()) {
        //            PossibleCallsVisitor visitor = new PossibleCallsVisitor(callable.name(), isMethod);
        //            new RubyAstTraverser().traverse(fileScope.node(), visitor);
        //            for (RubyProcedureCall node : visitor.possibleCalls()) {
        //                Scope scope = RubyUtils.restoreScope(fileScope, node);
        //                possibleCallers.add(new Construct<Scope, RubyProcedureCall>(scope, node));
        //            }
        //        }
        String name = callable.name();
        CallsRequestor cr = new CallsRequestor() {
            
            public void call(CallExpression call, FileScope fileScope) {
                Scope scope = RubyUtils.restoreScope(fileScope, call);
                possibleCallers.add(new Construct<Scope, CallExpression>(scope, call));
            }
            
        };
        if (callable instanceof RubyMethod)
            searchService.findMethodCalls(name, cr);
        else
            searchService.findProcedureCalls(name, cr);
        Construct<Scope, CallExpression>[] array = possibleCallers
                .toArray(createArray(possibleCallers.size()));
        if (!isMethod) {
            this.callers = new CallersInfo(array);
            requestor.done();
        } else
            requestor.subgoal(new MethodCallersRefiner(array));
    }
    
    class MethodCallersRefiner implements Continuation {
        
        private final ExpressionValueInfoGoal[] goals;
        private final Construct<Scope, CallExpression>[] constructs;
        
        public MethodCallersRefiner(Construct<Scope, CallExpression>[] constructs) {
            this.constructs = constructs;
            goals = new ExpressionValueInfoGoal[constructs.length];
            for (int i = 0; i < constructs.length; i++)
                goals[i] = new ExpressionValueInfoGoal(constructs[i].scope(), constructs[i].node()
                        .getReceiver(), InfoKind.TYPE);
        }
        
        public void provideSubgoals(SubgoalRequestor requestor) {
            for (Goal goal : goals)
                requestor.subgoal(goal);
        }
        
        public void done(ContinuationRequestor requestor) {
            callers = new CallersInfo(collectSuitableCallers((RubyMethod) callable));
            requestor.done();
        }
        
        private Construct<Scope, CallExpression>[] collectSuitableCallers(RubyMethod method) {
            List<Construct<Scope, CallExpression>> realCallers = new ArrayList<Construct<Scope, CallExpression>>();
            for (int i = 0; i < constructs.length; i++)
                for (RubyBasicClass klass : goals[i].result(thou()).possibleClasses())
                    if (method.canBeCalledFrom(klass))
                        realCallers.add(constructs[i]);
            return realCallers.toArray(createArray(realCallers.size()));
        }
        
    }
    
    class PossibleCallsVisitor extends RubyAstVisitor<ASTNode> {
        
        private final Collection<CallExpression> possibleCalls = new ArrayList<CallExpression>();
        
        private final String name;
        
        private final boolean needReceiver;
        
        public PossibleCallsVisitor(String name, boolean needReceiver) {
            super(null);
            this.name = name;
            this.needReceiver = needReceiver;
        }
        
        public CallExpression[] possibleCalls() {
            return possibleCalls.toArray(new CallExpression[possibleCalls.size()]);
        }
        
        @Override
        protected RubyAstVisitor<?> enterCallExpression(CallExpression node) {
            if (name.equalsIgnoreCase(node.getName()) && (needReceiver == (node.getReceiver() != null)))
                possibleCalls.add(node);
            return this;
        }
        
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((callable == null) ? 0 : callable.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final MethodCallersGoal other = (MethodCallersGoal) obj;
        if (callable == null) {
            if (other.callable != null)
                return false;
        } else if (!callable.equals(other.callable))
            return false;
        return true;
    }
    
    @Override
    public String toString() {
        return "MethodCallersGoal <" + callable + ">";
    }
    
    @SuppressWarnings("unchecked")
    private Construct<Scope, CallExpression>[] createArray(int size) {
        return new Construct[size];
    }
    
    public int debugSlot() {
        return 6;
    }
    
    public Goal cloneGoal() {
        return new MethodCallersGoal(callable, searchService);
    }
    
}
