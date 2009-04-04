package com.yoursway.sadr.python.analysis.lang.constructs.ast;

import kilim.pausable;

import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.python.parser.ast.PythonGlobalStatement;

import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstructImpl;
import com.yoursway.sadr.python.analysis.lang.constructs.support.VoidConstructException;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public class GlobalC extends PythonConstructImpl<PythonGlobalStatement> {
    
    GlobalC(PythonLexicalContext sc, PythonGlobalStatement node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
        PythonConstructFactory.wrap(node.getChilds(), sc, this);
        for (VariableReference var : node.getDeclaredVariables())
            sc.getScope().addGlobalVariable(var.getName());
    }
    
    @pausable
    public PythonValueSet evaluateValue(PythonDynamicContext dc) {
        throw new VoidConstructException(this);
    }
    
}
