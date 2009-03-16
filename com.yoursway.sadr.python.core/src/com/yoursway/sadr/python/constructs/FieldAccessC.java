package com.yoursway.sadr.python.constructs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import kilim.pausable;

import org.eclipse.dltk.python.parser.ast.expressions.PythonVariableAccessExpression;

import com.yoursway.sadr.engine.Analysis;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.goals.ExpressionValueGoal;
import com.yoursway.sadr.python.index.AssignmentsIndexQuery;
import com.yoursway.sadr.python.index.AssignmentsRequestor;
import com.yoursway.sadr.python.index.unodes.AttributeUnode;
import com.yoursway.sadr.python.index.unodes.Unode;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetBuilder;

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
        final Collection<Goal<PythonValueSet>> goals = new ArrayList<Goal<PythonValueSet>>();
        staticContext().getIndex().query(new AssignmentsIndexQuery(toUnode()), new AssignmentsRequestor() {
            public void assignment(PythonConstruct rhs, PythonFileC fileC) {
                goals.add(new ExpressionValueGoal(rhs, dc));
            }
        });
        List<PythonValueSet> results = Analysis.evaluate(goals);
        PythonValueSetBuilder builder = PythonValueSet.newBuilder();
        builder.addAll(results);
        return builder.build();
    }
    
    @Override
    public Unode toUnode() {
        Unode receiverUnode = receiver.toUnode();
        if (receiverUnode == null)
            return null;
        return new AttributeUnode(receiverUnode, variable.name());
    }
    
}
