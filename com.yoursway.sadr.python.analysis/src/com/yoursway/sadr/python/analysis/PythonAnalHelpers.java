package com.yoursway.sadr.python.analysis;

import static com.google.common.collect.Lists.newArrayList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import kilim.pausable;

import com.yoursway.sadr.engine.Analysis;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.python.analysis.aliasing.Alias;
import com.yoursway.sadr.python.analysis.aliasing.AliasConsumer;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.goals.ExpressionValueGoal;
import com.yoursway.sadr.python.analysis.lang.unodes.Bnode;
import com.yoursway.sadr.python.analysis.lang.unodes.Suffix;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSetBuilder;

public class PythonAnalHelpers {
    
    public static void addRenamesForConstructs(Suffix suffix, AliasConsumer aliases,
            Collection<Bnode> valuesAssignedToPunodeHead, PythonDynamicContext dc) {
        for (Bnode assignedValue : valuesAssignedToPunodeHead)
            addRenameForConstruct(suffix, aliases, assignedValue, dc);
    }
    
    public static void addRenameForConstruct(Suffix suffix, AliasConsumer aliases, Bnode bnode,
            PythonDynamicContext dc) {
        aliases.add(bnode.append(suffix).toAlias(dc));
    }
    
    @pausable
    public static PythonValueSet queryIndexForValuesAssignedTo(Collection<Alias> unodes) {
        List<PythonValueSet> values = newArrayList();
        for (Alias alias : unodes) {
            PythonValueSet vs = alias.getUnode().calculateLiteralValue();
            if (vs != null)
                values.add(vs);
            values.add(alias.queryIndexForValuesAssignedTo());
        }
        return PythonValueSet.merge(values);
    }
    
    @pausable
    public static PythonValueSet evaluateConstructs(Collection<Bnode> expressions, PythonDynamicContext dc) {
        PythonValueSetBuilder builder = PythonValueSet.newBuilder();
        evaluateConstructs(expressions, dc, builder);
        return builder.build();
    }
    
    @pausable
    public static void evaluateConstructs(Collection<Bnode> expressions, PythonDynamicContext dc,
            PythonValueSetBuilder builder) {
        Collection<Goal<PythonValueSet>> goals = new ArrayList<Goal<PythonValueSet>>(expressions.size());
        for (Bnode construct : expressions)
            goals.add(new ExpressionValueGoal(construct, dc));
        List<PythonValueSet> results = Analysis.evaluate(goals);
        builder.addAll(results);
    }
    
}
