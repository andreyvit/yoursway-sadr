package com.yoursway.sadr.python.analysis.lang.constructs;

import java.util.List;

import kilim.pausable;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.core.constructs.IConstruct;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonScope;
import com.yoursway.sadr.python.analysis.context.lexical.PythonStaticContext;
import com.yoursway.sadr.python.analysis.lang.constructs.ast.PythonFileC;
import com.yoursway.sadr.python.analysis.lang.unodes.Unode;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public interface PythonConstruct extends
        IConstruct<PythonConstruct, PythonStaticContext, PythonDynamicContext, ASTNode> {
    
    @pausable
    PythonValueSet evaluateValue(PythonDynamicContext dc);
    
    PythonStaticContext staticContext();
    
    PythonFileC fileC();
    
    Unode toUnode();
    
    List<PythonScope> currentScopes();
    
}
