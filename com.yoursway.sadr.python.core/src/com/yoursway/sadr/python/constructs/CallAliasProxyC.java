package com.yoursway.sadr.python.constructs;

import kilim.pausable;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.index.unodes.Bnode;
import com.yoursway.sadr.python.index.unodes.Unode;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public class CallAliasProxyC extends PythonConstructImpl<ASTNode> {
    
    private final Bnode bnode;
    
    public CallAliasProxyC(Bnode bnode, PythonConstruct callable) {
        super(bnode.getStaticContext(), new DummyAstNode(), null);
        this.bnode = bnode;
    }
    
    @pausable
    public PythonValueSet evaluateValue(PythonDynamicContext dc, InfoKind infoKind) {
        return PythonValueSet.EMPTY;
        // XXX was causing an infinite loop
        //        return bnode.calculateValue(call.createDynamicContext(dc));
    }
    
    @Override
    public Unode toUnode() {
        // FIXME this is a dirty hack because we most definitely lose the scope (and thus will evaluate this unode in a wrong scope)
        return bnode.getUnode();
    }
    
}
