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
    }
    
    @Override
    public Unode toUnode() {
        return alias.getUnode();
    }
    
}
