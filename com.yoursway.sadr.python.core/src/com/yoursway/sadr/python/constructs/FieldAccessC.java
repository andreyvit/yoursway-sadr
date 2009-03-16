package com.yoursway.sadr.python.constructs;

import static com.yoursway.sadr.python.constructs.PythonAnalHelpers.calculateValuesAssignedTo;
import static com.yoursway.sadr.python.constructs.PythonAnalHelpers.computeAliases;

import java.util.List;
import java.util.Set;

import kilim.pausable;

import org.eclipse.dltk.python.parser.ast.expressions.PythonVariableAccessExpression;

import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.index.unodes.AttributeUnode;
import com.yoursway.sadr.python.index.unodes.Unode;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public class FieldAccessC extends PythonConstructImpl<PythonVariableAccessExpression> {
    
    private final PythonConstruct receiver;
    private final VariableReferenceC variable;
    
    FieldAccessC(PythonStaticContext sc, PythonVariableAccessExpression node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
        this.receiver = wrap(node.getReceiver(), sc);
        this.variable = (VariableReferenceC) wrap(node.variable(), sc);
    }
    
    public PythonConstruct receiver() {
        return receiver;
    }
    
    public VariableReferenceC variable() {
        return variable;
    }
    
    @pausable
    public PythonValueSet evaluateValue(final PythonDynamicContext dc, InfoKind infoKind) {
        List<PythonScope> scopes = currentScopes();
        Unode unode = toUnode();
        if (unode == null)
            return PythonValueSet.EMPTY;
        Set<Unode> aliases = computeAliases(unode, scopes, staticContext());
        return calculateValuesAssignedTo(aliases, staticContext(), dc, scopes);
    }
    
    @Override
    public Unode toUnode() {
        Unode receiverUnode = receiver.toUnode();
        if (receiverUnode == null)
            return null;
        return new AttributeUnode(receiverUnode, variable.name());
    }
    
}
