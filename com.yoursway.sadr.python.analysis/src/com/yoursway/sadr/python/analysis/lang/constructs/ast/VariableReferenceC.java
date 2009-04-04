package com.yoursway.sadr.python.analysis.lang.constructs.ast;

import org.eclipse.dltk.ast.references.VariableReference;

import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstructImpl;
import com.yoursway.sadr.python.analysis.lang.unodes.Unode;
import com.yoursway.sadr.python.analysis.lang.unodes.indexable.VariableUnode;

public class VariableReferenceC extends PythonConstructImpl<VariableReference> {
    
    VariableReferenceC(PythonLexicalContext sc, VariableReference node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
    }
    
    public String name() {
        return node.getName();
    }
    
    @Override
    public Unode toUnode() {
        return new VariableUnode(name());
    }
    
}
