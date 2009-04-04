package com.yoursway.sadr.python.analysis.lang.constructs.ast;

import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.python.parser.ast.PythonGlobalStatement;

import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstructImpl;

public class GlobalC extends PythonConstructImpl<PythonGlobalStatement> {
    
    GlobalC(PythonLexicalContext sc, PythonGlobalStatement node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
        PythonConstructFactory.wrap(node.getChilds(), sc, this);
        for (VariableReference var : node.getDeclaredVariables())
            sc.getScope().addGlobalVariable(var.getName());
    }
    
}
