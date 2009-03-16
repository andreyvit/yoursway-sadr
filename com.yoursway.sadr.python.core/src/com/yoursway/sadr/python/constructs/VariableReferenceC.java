package com.yoursway.sadr.python.constructs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import kilim.pausable;

import org.eclipse.dltk.ast.references.VariableReference;

import com.yoursway.sadr.engine.Analysis;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.goals.ExpressionValueGoal;
import com.yoursway.sadr.python.index.AssignmentsIndexQuery;
import com.yoursway.sadr.python.index.AssignmentsRequestor;
import com.yoursway.sadr.python.index.unodes.Unode;
import com.yoursway.sadr.python.index.unodes.VariableUnode;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetBuilder;

public class VariableReferenceC extends PythonConstructImpl<VariableReference> {
    
    VariableReferenceC(PythonStaticContext sc, VariableReference node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
    }
    
    public String name() {
        return node.getName();
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
        return new VariableUnode(name());
    }
    
}
