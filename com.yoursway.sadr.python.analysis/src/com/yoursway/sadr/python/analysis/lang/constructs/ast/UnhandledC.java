package com.yoursway.sadr.python.analysis.lang.constructs.ast;

import kilim.pausable;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstructImpl;
import com.yoursway.sadr.python.analysis.lang.constructs.support.VoidConstructException;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public class UnhandledC extends PythonConstructImpl<ASTNode> {
    
    UnhandledC(PythonLexicalContext sc, ASTNode node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
        PythonConstructFactory.wrap(node.getChilds(), sc, this);
    }
    
    @pausable
    public PythonValueSet evaluateValue(PythonDynamicContext dc) {
        throw new VoidConstructException(this);
    }
    
}
