package com.yoursway.sadr.python.constructs;

import kilim.pausable;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public class UnhandledC extends PythonConstructImpl<ASTNode> {
    
    UnhandledC(PythonStaticContext sc, ASTNode node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
        PythonConstructFactory.wrap(node.getChilds(), sc, this);
    }
    
    @pausable
    public PythonValueSet evaluateValue(PythonDynamicContext dc, InfoKind infoKind) {
        throw new VoidConstructException(this);
    }
    
}
