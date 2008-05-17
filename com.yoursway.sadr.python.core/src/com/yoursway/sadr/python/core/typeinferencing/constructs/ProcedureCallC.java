package com.yoursway.sadr.python.core.typeinferencing.constructs;

import static com.yoursway.sadr.python.core.typeinferencing.constructs.Effects.NO_EFFECTS;
import static java.util.Collections.singleton;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.python.parser.ast.expressions.PythonCallExpression;

import com.yoursway.sadr.python.core.runtime.PythonUtils;
import com.yoursway.sadr.python.core.typeinferencing.constructs.requests.EvalRequest;
import com.yoursway.sadr.python.core.typeinferencing.constructs.requests.IndexRequest;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.goals.CallF;

public class ProcedureCallC extends CallC {
    
    ProcedureCallC(Scope sc, PythonCallExpression node) {
        super(sc, node);
    }
    
    public void actOnIndex(IndexRequest request) {
        request.addProcedureCall(node.getName(), this);
    }
    
    public void actOnEval(EvalRequest request) {
        ASTNode args = node.getArgs();
        List<ASTNode> children = PythonUtils.childrenOf(args);
        if (node.getName().equals("eval") && children.size() > 0) {
            request.addEval(this, subconstructFor(children.get(0)));
        }
    }
    
    @Override
    public String toString() {
        return node.getName() + "()";
    }
    
    @Override
    public PythonConstruct getReceiver() {
        return null;
    }
    
    private PythonConstruct function() {
        return wrap(node.getFunction());
    }
    
    @Override
    public Frog toFrog() {
        List<Frog> argFrogs = new ArrayList<Frog>();
        for (PythonConstruct construct : getArgs()) {
            argFrogs.add(construct.toFrog());
        }
        return new CallF(function().toFrog(), argFrogs);
    }
    
    @Override
    public Effects getEffects() {
        return new Effects(NO_EFFECTS, singleton(toFrog()));
    }
    
}
