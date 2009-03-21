package com.yoursway.sadr.python.constructs;

import java.util.List;

import kilim.pausable;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.core.constructs.IConstruct;
import com.yoursway.sadr.python.index.unodes.Unode;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public interface PythonConstruct extends
        IConstruct<PythonConstruct, PythonStaticContext, PythonDynamicContext, ASTNode> {
    
    @pausable
    PythonValueSet evaluateValue(PythonDynamicContext dc, com.yoursway.sadr.engine.InfoKind infoKind);
    
    PythonStaticContext staticContext();
    
    PythonFileC fileC();
    
    Unode toUnode();
    
    List<PythonScope> currentScopes();
    
}
