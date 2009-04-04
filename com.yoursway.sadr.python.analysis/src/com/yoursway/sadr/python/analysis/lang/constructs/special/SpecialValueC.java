package com.yoursway.sadr.python.analysis.lang.constructs.special;

import kilim.pausable;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstructImpl;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public class SpecialValueC extends PythonConstructImpl<ASTNode> {
    
    private final PythonValueSet value;
    
    public SpecialValueC(PythonLexicalContext sc, PythonValueSet value) {
        super(sc, new DummyAstNode(), null);
        if (value == null)
            throw new NullPointerException("value is null");
        this.value = value;
    }
    
    @pausable
    public PythonValueSet evaluateValue(PythonDynamicContext dc) {
        return value;
    }
    
}
