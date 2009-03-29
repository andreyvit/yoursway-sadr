package com.yoursway.sadr.python.constructs;

import kilim.pausable;

import org.eclipse.dltk.python.parser.ast.expressions.PythonVariableAccessExpression;

import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.index.unodes.AttributeUnode;
import com.yoursway.sadr.python.index.unodes.Unode;
import com.yoursway.sadr.python.model.IndexAffector;
import com.yoursway.sadr.python.model.IndexNameWrappingStrategy;
import com.yoursway.sadr.python.model.IndexRequest;
import com.yoursway.sadr.python.model.PassedReceiverArgumentInfo;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public class FieldAccessC extends PythonConstructImpl<PythonVariableAccessExpression> implements
        IndexAffector {
    
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
        Unode unode = toUnode();
        if (unode == null)
            return PythonValueSet.EMPTY;
        return unode.calculateValue(staticContext(), dc, currentScopes());
    }
    
    @Override
    public Unode toUnode() {
        Unode receiverUnode = receiver.toUnode();
        if (receiverUnode == null)
            return null;
        return new AttributeUnode(receiverUnode, variable.name());
    }
    
    public void actOnIndex(IndexRequest r) {
        Unode unode = receiver.toUnode();
        if (unode != null)
            r.addPassedArgument(unode, new PassedReceiverArgumentInfo(this));
    }
    
    public IndexNameWrappingStrategy createWrappingStrategy() {
        return null;
    }
    
}
