package com.yoursway.sadr.python.core.typeinferencing.constructs.requests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.core.constructs.Request;
import com.yoursway.sadr.core.constructs.VisitorRequestor;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonDynamicContext;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonStaticContext;

public class EvalRequest implements
        Request<PythonConstruct, PythonStaticContext, PythonDynamicContext, ASTNode> {
    
    public static class EvalInfo {
        
        private final PythonConstruct eval;
        private final PythonConstruct argument;
        
        public EvalInfo(PythonConstruct eval, PythonConstruct argument) {
            this.eval = eval;
            this.argument = argument;
        }
        
        public PythonConstruct getEval() {
            return eval;
        }
        
        public PythonConstruct getArgument() {
            return argument;
        }
        
    }
    
    private final List<EvalInfo> evals;
    
    public EvalRequest() {
        evals = new ArrayList<EvalInfo>();
    }
    
    public void accept(PythonConstruct construct) {
        if (construct instanceof EvalsAffector)
            ((EvalsAffector) construct).actOnEval(this);
    }
    
    public void enter(PythonConstruct construct, ContinuationRequestor requestor,
            VisitorRequestor<PythonConstruct, PythonStaticContext, PythonDynamicContext, ASTNode> continuation) {
        accept(construct);
        continuation.consume(this, requestor);
    }
    
    public void leave() {
    }
    
    public void addEval(PythonConstruct eval, PythonConstruct argument) {
        this.evals.add(new EvalInfo(eval, argument));
    }
    
    public Collection<EvalInfo> evalArgs() {
        return evals;
    }
    
}
