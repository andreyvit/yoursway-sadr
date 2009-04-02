package com.yoursway.sadr.python.constructs;

import kilim.pausable;

import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.python.parser.ast.PythonGlobalStatement;

import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public class GlobalC extends PythonConstructImpl<PythonGlobalStatement> {
    
    GlobalC(PythonStaticContext sc, PythonGlobalStatement node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
        PythonConstructFactory.wrap(node.getChilds(), sc, this);
        for (VariableReference var : node.getDeclaredVariables())
            sc.addGlobalVariable(var.getName());
    }
    
    @pausable
    public PythonValueSet evaluateValue(PythonDynamicContext dc, InfoKind infoKind) {
        throw new VoidConstructException(this);
    }
    
}
