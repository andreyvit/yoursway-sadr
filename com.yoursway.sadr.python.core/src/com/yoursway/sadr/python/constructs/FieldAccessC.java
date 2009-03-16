package com.yoursway.sadr.python.constructs;

import static com.yoursway.sadr.python.constructs.PythonAnalHelpers.chooseAssignmentsFromInnermostScope;
import static com.yoursway.sadr.python.constructs.PythonAnalHelpers.findAssignmentsAndGroupByScopes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import kilim.pausable;

import org.eclipse.dltk.python.parser.ast.expressions.PythonVariableAccessExpression;

import com.google.common.collect.Maps;
import com.yoursway.sadr.engine.Analysis;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.goals.ExpressionValueGoal;
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
        final Collection<Goal<PythonValueSet>> goals = new ArrayList<Goal<PythonValueSet>>();
        
        List<PythonScope> scopes = currentScopes();
        
        Map<PythonScope, Collection<PythonConstruct>> assignmentsByScope = Maps.newHashMap();
        for (PythonScope scope : scopes)
            assignmentsByScope.put(scope, new ArrayList<PythonConstruct>());
        findAssignmentsAndGroupByScopes(staticContext(), toUnode(), assignmentsByScope, scopes);
        
        for (PythonConstruct assignedValue : chooseAssignmentsFromInnermostScope(assignmentsByScope, scopes))
            goals.add(new ExpressionValueGoal(assignedValue, dc));
        return PythonValueSet.merge(Analysis.evaluate(goals));
    }
    
    @Override
    public Unode toUnode() {
        Unode receiverUnode = receiver.toUnode();
        if (receiverUnode == null)
            return null;
        return new AttributeUnode(receiverUnode, variable.name());
    }
    
}
