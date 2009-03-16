package com.yoursway.sadr.python.constructs;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import kilim.pausable;

import com.google.common.collect.Maps;
import com.yoursway.sadr.engine.Analysis;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.python.goals.ExpressionValueGoal;
import com.yoursway.sadr.python.index.AssignmentsIndexQuery;
import com.yoursway.sadr.python.index.AssignmentsRequestor;
import com.yoursway.sadr.python.index.punodes.Punode;
import com.yoursway.sadr.python.index.unodes.Unode;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public class PythonAnalHelpers {
    
    public static Collection<PythonConstruct> chooseAssignmentsFromInnermostScope(
            final Map<PythonScope, Collection<PythonConstruct>> assignmentsByScope,
            final List<PythonScope> scopes) {
        for (PythonScope scope : scopes) {
            Collection<PythonConstruct> assignedValues = assignmentsByScope.get(scope);
            if (!assignedValues.isEmpty())
                return assignedValues;
        }
        return Collections.emptyList();
    }
    
    @pausable
    public static void findAssignmentsAndGroupByScopes(PythonStaticContext staticContext, Unode unode,
            final Map<PythonScope, Collection<PythonConstruct>> assignmentsByScope,
            final List<PythonScope> scopes) {
        PythonScope outerScope = scopes.get(scopes.size() - 1);
        final Collection<PythonConstruct> outerScopeConstructs = assignmentsByScope.get(outerScope);
        
        staticContext.getIndex().query(new AssignmentsIndexQuery(unode), new AssignmentsRequestor() {
            public void assignment(PythonConstruct rhs, PythonFileC fileC) {
                Collection<PythonConstruct> constructs = assignmentsByScope.get(rhs.staticContext());
                if (constructs == null)
                    constructs = outerScopeConstructs;
                constructs.add(rhs);
                
            }
        });
    }
    
    @pausable
    public static PythonValueSet calculateValuesAssignedTo(Unode unode, PythonStaticContext sc,
            final PythonDynamicContext dc, List<PythonScope> scopes) {
        Collection<PythonConstruct> assignments = findConstructsAssignedTo(unode, sc, scopes);
        final Collection<Goal<PythonValueSet>> goals = new ArrayList<Goal<PythonValueSet>>();
        for (PythonConstruct assignedValue : assignments)
            goals.add(new ExpressionValueGoal(assignedValue, dc));
        return PythonValueSet.merge(Analysis.evaluate(goals));
    }
    
    @pausable
    public static Collection<PythonConstruct> findConstructsAssignedTo(Unode unode, PythonStaticContext sc,
            List<PythonScope> scopes) {
        Map<PythonScope, Collection<PythonConstruct>> assignmentsByScope = Maps.newHashMap();
        for (PythonScope scope : scopes)
            assignmentsByScope.put(scope, new ArrayList<PythonConstruct>());
        findAssignmentsAndGroupByScopes(sc, unode, assignmentsByScope, scopes);
        
        return chooseAssignmentsFromInnermostScope(assignmentsByScope, scopes);
    }
    
    @pausable
    public static Set<Unode> computeAliases(Unode unode, List<PythonScope> scopes,
            PythonStaticContext staticContext) {
        Set<Unode> aliases = newHashSet();
        aliases.add(unode);
        
        for (Punode punode = unode.punodize(); punode != null; punode = punode.punodize()) {
            Collection<PythonConstruct> valuesAssignedToPunodeHead = findConstructsAssignedTo(punode
                    .getHead(), staticContext, scopes);
            for (PythonConstruct assignedValue : valuesAssignedToPunodeHead) {
                Unode replacementUnode = assignedValue.toUnode();
                if (replacementUnode != null) {
                    Unode wrapped = punode.wrap(replacementUnode);
                    aliases.add(wrapped);
                }
            }
        }
        return aliases;
    }
    
    @pausable
    public static PythonValueSet calculateValuesAssignedTo(Collection<Unode> unodes, PythonStaticContext sc,
            final PythonDynamicContext dc, List<PythonScope> scopes) {
        List<PythonValueSet> values = newArrayList();
        for (Unode alias : unodes)
            values.add(calculateValuesAssignedTo(alias, sc, dc, scopes));
        PythonValueSet result = PythonValueSet.merge(values);
        return result;
    }
    
}
