package com.yoursway.sadr.python.core.typeinferencing.constructs;

import java.util.Collection;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.core.constructs.IConstruct;
import com.yoursway.sadr.python.core.typeinferencing.goals.MumblaWumblaThreesome;

public interface PythonConstruct extends
        IConstruct<PythonConstruct, PythonStaticContext, PythonDynamicContext, ASTNode> {
    
    Collection<MumblaWumblaThreesome> mumblaWumbla();
    
}
