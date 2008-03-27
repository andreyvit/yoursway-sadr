package com.yoursway.sadr.ruby.core.typeinferencing.constructs.requests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.core.constructs.Request;
import com.yoursway.sadr.core.constructs.VisitorRequestor;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyConstruct;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyDynamicContext;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyStaticContext;

public class EvalRequest implements Request<RubyConstruct, RubyStaticContext, RubyDynamicContext, ASTNode> {
    
    public static class EvalInfo {
        
        private final RubyConstruct eval;
        private final RubyConstruct argument;
        
        public EvalInfo(RubyConstruct eval, RubyConstruct argument) {
            this.eval = eval;
            this.argument = argument;
        }
        
        public RubyConstruct getEval() {
            return eval;
        }
        
        public RubyConstruct getArgument() {
            return argument;
        }
        
    }
    
    private final List<EvalInfo> evals;
    
    public EvalRequest() {
        evals = new ArrayList<EvalInfo>();
    }
    
    public void accept(RubyConstruct construct) {
        if (construct instanceof EvalsAffector)
            ((EvalsAffector) construct).actOnEval(this);
    }
    
    public void enter(RubyConstruct construct, ContinuationRequestor requestor,
            VisitorRequestor<RubyConstruct, RubyStaticContext, RubyDynamicContext, ASTNode> continuation) {
        accept(construct);
        continuation.consume(this, requestor);
    }
    
    public void leave() {
    }
    
    public void addEval(RubyConstruct eval, RubyConstruct argument) {
        this.evals.add(new EvalInfo(eval, argument));
    }
    
    public Collection<EvalInfo> evalArgs() {
        return evals;
    }
    
}
