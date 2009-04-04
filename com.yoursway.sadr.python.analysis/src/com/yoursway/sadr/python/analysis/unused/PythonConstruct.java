package com.yoursway.sadr.python.analysis.unused;

import java.util.List;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.core.IValueInfo;
import com.yoursway.sadr.core.constructs.ControlFlowGraph;
import com.yoursway.sadr.core.constructs.IConstruct;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;

/**
 * This class is not used, however is required to make SADR Core happy. (It
 * won't last long, though.)
 */
public class PythonConstruct implements
        IConstruct<PythonConstruct, PythonLexicalContext, PythonDynamicContext, ASTNode> {
    
    public ControlFlowGraph<PythonConstruct, PythonLexicalContext, PythonDynamicContext, ASTNode> calculateEffectiveControlFlowGraph() {
        return null;
    }
    
    public List<PythonConstruct> enclosedConstructs() {
        return null;
    }
    
    public IValueInfo evaluateValue(PythonDynamicContext dc) {
        return null;
    }
    
    public ASTNode node() {
        return null;
    }
    
    public PythonLexicalContext staticContext() {
        return null;
    }
    
    public PythonConstruct subconstructFor(ASTNode node) {
        return null;
    }
    
}
