package com.yoursway.sadr.python.constructs;

import kilim.pausable;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public class SpecialValueC extends PythonConstructImpl<ASTNode> {
    
    private final PythonValueSet value;
    
    public SpecialValueC(PythonStaticContext sc, PythonValueSet value) {
        super(sc, new DummyAstNode(), null);
        if (value == null)
            throw new NullPointerException("value is null");
        this.value = value;
    }
    
    @pausable
    public PythonValueSet evaluateValue(PythonDynamicContext dc, InfoKind infoKind) {
        return value;
    }
    
}
