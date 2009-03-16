package com.yoursway.sadr.python.constructs;

import kilim.pausable;

import org.eclipse.dltk.ast.references.VariableReference;

import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public class VariableReferenceC extends PythonConstructImpl<VariableReference> {
    
    VariableReferenceC(PythonStaticContext sc, VariableReference node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
    }
    
    public String name() {
        return node.getName();
    }
    
    @pausable
    public PythonValueSet evaluateValue(PythonDynamicContext dc, InfoKind infoKind) {
        throw new UnsupportedOperationException();
    }
    
}
