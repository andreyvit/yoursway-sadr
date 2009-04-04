package com.yoursway.sadr.python.analysis.lang.constructs.special;

import kilim.pausable;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.analysis.Alias;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstruct;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstructImpl;
import com.yoursway.sadr.python.analysis.lang.unodes.Unode;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public class CallAliasProxyC extends PythonConstructImpl<ASTNode> {
    
    private final Alias alias;
    
    public CallAliasProxyC(Alias alias, PythonConstruct callable) {
        super(alias.getStaticContext(), new DummyAstNode(), null);
        this.alias = alias;
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
        return alias.getUnode();
    }
    
}
