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

import com.google.common.base.Join;
import com.google.common.collect.Maps;
import com.yoursway.sadr.engine.Analysis;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.python.goals.ExpressionValueGoal;
import com.yoursway.sadr.python.index.AssignmentsIndexQuery;
import com.yoursway.sadr.python.index.AssignmentsRequestor;
import com.yoursway.sadr.python.index.PassedArgumentsIndexQuery;
import com.yoursway.sadr.python.index.PassedArgumentsRequestor;
import com.yoursway.sadr.python.index.punodes.Punode;
import com.yoursway.sadr.python.index.unodes.Bnode;
import com.yoursway.sadr.python.index.unodes.Unode;
import com.yoursway.sadr.python.model.PassedArgumentInfo;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetBuilder;

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
        
        Analysis.queryIndex(new AssignmentsIndexQuery(unode), new AssignmentsRequestor() {
            public void assignment(PythonConstruct rhs, PythonFileC fileC) {
                Collection<PythonConstruct> constructs = assignmentsByScope.get(rhs.staticContext());
                if (constructs == null)
                    constructs = outerScopeConstructs;
                constructs.add(rhs);
                
            }
        });
    }
    
    @pausable
    public static Collection<PythonConstruct> findRenames(Unode unode, PythonStaticContext sc,
            List<PythonScope> scopes, final PythonDynamicContext dc) {
        final Collection<PythonConstruct> result = findConstructsAssignedTo(unode, sc, scopes);
        
        final Collection<PassedArgumentInfo> infos = new ArrayList<PassedArgumentInfo>();
        Analysis.queryIndex(new PassedArgumentsIndexQuery(unode), new PassedArgumentsRequestor() {
            public void call(PassedArgumentInfo info, PythonFileC fileC) {
                // FIXME check scope
                infos.add(info);
            }
        });
        for (PassedArgumentInfo info : infos) {
            PythonValueSet callable = Analysis.evaluate(new ExpressionValueGoal(info.getCallable(), dc));
            List<Bnode> unodes = new ArrayList<Bnode>();
            callable.computeArgumentAliases(info, unodes);
            for (Bnode bnode : unodes)
                result.add(new CallAliasProxyC(bnode, info.getCallable()));
        }
        System.out.println("renames(" + unode + ") = " + Join.join(", ", result));
        for (PassedArgumentInfo info : infos)
            System.out.println(" - " + info);
        if (unode.toString().equals("Var(p)"))
            System.out.println("PythonAnalHelpers.findRenames()");
        return result;
    }
    
    @pausable
    private static Collection<PythonConstruct> findConstructsAssignedTo(Unode unode, PythonStaticContext sc,
            List<PythonScope> scopes) {
        Map<PythonScope, Collection<PythonConstruct>> assignmentsByScope = Maps.newHashMap();
        for (PythonScope scope : scopes)
            assignmentsByScope.put(scope, new ArrayList<PythonConstruct>());
        findAssignmentsAndGroupByScopes(sc, unode, assignmentsByScope, scopes);
        
        return chooseAssignmentsFromInnermostScope(assignmentsByScope, scopes);
    }
    
    @pausable
    public static Set<Unode> computeAliases(Unode unode, List<PythonScope> scopes,
            PythonStaticContext staticContext, PythonDynamicContext dc) {
        Set<Unode> aliases = newHashSet();
        aliases.add(unode);
        
        for (Punode punode = unode.punodize(); punode != null; punode = punode.punodize()) {
            Collection<PythonConstruct> valuesAssignedToPunodeHead = findRenames(punode.getHead(),
                    staticContext, scopes, dc);
            for (PythonConstruct assignedValue : valuesAssignedToPunodeHead) {
                Unode replacementUnode = assignedValue.toUnode();
                if (replacementUnode != null) {
                    Unode wrapped = punode.wrap(replacementUnode);
                    aliases.add(wrapped);
                }
            }
        }
        System.out.println("Aliases of " + unode + ": " + Join.join(", ", aliases));
        return aliases;
    }
    
    @pausable
    public static PythonValueSet queryIndexForValuesAssignedTo(Collection<Unode> unodes,
            PythonStaticContext sc, final PythonDynamicContext dc, List<PythonScope> scopes) {
        List<PythonValueSet> values = newArrayList();
        for (Unode alias : unodes)
            values.add(queryIndexForValuesAssignedTo(alias, sc, dc, scopes));
        return PythonValueSet.merge(values);
    }
    
    @pausable
    public static PythonValueSet evaluateConstructs(Collection<PythonConstruct> expressions,
            PythonDynamicContext dc) {
        PythonValueSetBuilder builder = PythonValueSet.newBuilder();
        evaluateConstructs(expressions, dc, builder);
        return builder.build();
    }
    
    @pausable
    public static void evaluateConstructs(Collection<PythonConstruct> expressions, PythonDynamicContext dc,
            PythonValueSetBuilder builder) {
        Collection<Goal<PythonValueSet>> goals = new ArrayList<Goal<PythonValueSet>>(expressions.size());
        for (PythonConstruct construct : expressions)
            goals.add(new ExpressionValueGoal(construct, dc));
        List<PythonValueSet> results = Analysis.evaluate(goals);
        builder.addAll(results);
    }
    
    @pausable
    public static PythonValueSet queryIndexForValuesAssignedTo(Unode unode, PythonStaticContext sc,
            PythonDynamicContext dc, List<PythonScope> scopes) {
        Collection<PythonConstruct> assignments = findConstructsAssignedTo(unode, sc, scopes);
        final Collection<Goal<PythonValueSet>> goals = new ArrayList<Goal<PythonValueSet>>();
        for (PythonConstruct assignedValue : assignments)
            goals.add(new ExpressionValueGoal(assignedValue, dc));
        return PythonValueSet.merge(Analysis.evaluate(goals));
    }
    
}
