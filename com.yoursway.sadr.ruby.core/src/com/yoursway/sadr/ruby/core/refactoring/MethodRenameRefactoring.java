package com.yoursway.sadr.ruby.core.refactoring;

import org.eclipse.dltk.ast.expressions.CallExpression;

import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.SimpleContinuation;
import com.yoursway.sadr.engine.SubgoalRequestor;
import com.yoursway.sadr.ruby.core.rewriting.RewritingSession;
import com.yoursway.sadr.ruby.core.rewriting.WCall;
import com.yoursway.sadr.ruby.core.rewriting.WMethodDeclaration;
import com.yoursway.sadr.ruby.core.runtime.Callable;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.dtl.CallC;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.CallersInfo;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.MethodCallersGoal;

public class MethodRenameRefactoring implements SimpleContinuation {
    
    private final Callable method;
    private final String newName;
    private final SimpleContinuation continuation;
    private final RewritingSession session;
    
    public MethodRenameRefactoring(RewritingSession session, Callable method, String newName,
            SimpleContinuation continuation) {
        this.session = session;
        this.method = method;
        this.newName = newName;
        this.continuation = continuation;
    }
    
    public void run(ContinuationRequestor requestor) {
        final MethodCallersGoal goal = new MethodCallersGoal(method, method.construct().staticContext()
                .searchService());
        requestor.subgoal(new Continuation() {
            
            public void provideSubgoals(SubgoalRequestor requestor) {
                requestor.subgoal(goal);
            }
            
            public void done(ContinuationRequestor requestor) {
                CallersInfo info = goal.result(null);
                executeWithInfo(info, requestor);
            }
            
        });
    }
    
    void executeWithInfo(CallersInfo info, ContinuationRequestor requestor) {
        renameMethod();
        for (CallC caller : info.callers())
            renameCall(caller.node());
        continuation.run(requestor);
    }
    
    private void renameMethod() {
        WMethodDeclaration wmethod = (WMethodDeclaration) session.map(method.construct().node());
        wmethod.setName(newName);
    }
    
    private void renameCall(CallExpression node) {
        WCall wcall = (WCall) session.map(node);
        wcall.setName(newName);
    }
    
}
