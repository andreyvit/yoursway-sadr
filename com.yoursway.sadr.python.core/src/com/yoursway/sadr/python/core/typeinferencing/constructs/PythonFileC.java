package com.yoursway.sadr.python.core.typeinferencing.constructs;

import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.ISourceModule;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.core.runtime.PythonModule;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

public class PythonFileC extends PythonConstructImpl<ModuleDeclaration> implements Scope {
    
    public PythonFileC(Scope sc, ISourceModule module, PythonModule module2, ModuleDeclaration node) {
        super(sc, node);
    }
    
    @Override
    protected PythonConstruct wrap(ASTNode node) {
        return PythonConstructFactory.wrapConstruct(node, this);
    }
    
    public ContinuationRequestorCalledToken evaluateValue(PythonDynamicContext dc, InfoKind infoKind,
            ContinuationScheduler requestor, ValueInfoContinuation continuation) {
        throw new UnsupportedOperationException();
    }
    
    public List<PythonConstruct> getEnclosedconstructs() {
        return node.getChilds();
    }
    
    public PythonConstruct parentConstruct() {
        return (PythonConstruct) scope();
    }
    
}
