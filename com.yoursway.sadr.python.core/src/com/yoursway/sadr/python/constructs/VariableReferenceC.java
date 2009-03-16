package com.yoursway.sadr.python.constructs;

import static com.yoursway.sadr.python.constructs.PythonAnalHelpers.calculateValuesAssignedTo;
import kilim.pausable;

import org.eclipse.dltk.ast.references.VariableReference;

import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.index.unodes.Unode;
import com.yoursway.sadr.python.index.unodes.VariableUnode;
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
        Unode unode = toUnode();
        if (unode == null)
            return PythonValueSet.EMPTY;
        return calculateValuesAssignedTo(unode, staticContext(), dc, currentScopes());
    }
    
    @Override
    public Unode toUnode() {
        return new VariableUnode(name());
    }
    
}
