package com.yoursway.sadr.python.constructs;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

import java.util.ArrayList;
import java.util.Collection;
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
import com.yoursway.sadr.python.index.punodes.HeadPunode;
import com.yoursway.sadr.python.index.punodes.Punode;
import com.yoursway.sadr.python.index.unodes.Bnode;
import com.yoursway.sadr.python.index.unodes.Unode;
import com.yoursway.sadr.python.index.unodes.VariableUnode;
import com.yoursway.sadr.python.model.PassedArgumentInfo;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetBuilder;

public class PythonAnalHelpers {
    
    public static Collection<PythonConstruct> chooseAssignmentsFromInnermostScope(Unode unode,
            PythonScope sc, final Map<PythonScope, Collection<PythonConstruct>> assignmentsByScope) {
        VariableUnode variable = unode.leadingVariableUnode();
        if (variable == null)
            throw new IllegalArgumentException(
                    "Complex expressions are awful candidates for alias calculation");
        PythonScope definingScope = sc.findDefiningScope(variable.getName());
        Collection<PythonConstruct> result = new ArrayList<PythonConstruct>();
        for (Map.Entry<PythonScope, Collection<PythonConstruct>> entry : assignmentsByScope.entrySet())
            if (definingScope == entry.getKey().findDefiningScope(variable.getName()))
                result.addAll(entry.getValue());
        return result;
    }
    
    @pausable
    public static void findAssignmentsAndGroupByScopes(PythonStaticContext staticContext, Unode unode,
            final Map<PythonScope, Collection<PythonConstruct>> assignmentsByScope) {
        Analysis.queryIndex(new AssignmentsIndexQuery(unode), new AssignmentsRequestor() {
            public void assignment(PythonConstruct rhs, PythonFileC fileC) {
                PythonStaticContext sc = rhs.staticContext();
                Collection<PythonConstruct> result = assignmentsByScope.get(sc);
                if (result == null) {
                    result = new ArrayList<PythonConstruct>();
                    assignmentsByScope.put(sc, result);
                }
                result.add(rhs);
            }
        });
    }
    
    @pausable
    public static Collection<PythonConstruct> findRenames(Bnode bnode) {
        final Collection<PythonConstruct> result = findConstructsAssignedTo(bnode.getUnode(), bnode
                .getStaticContext());
        
        final Collection<PassedArgumentInfo> infos = new ArrayList<PassedArgumentInfo>();
        Analysis.queryIndex(new PassedArgumentsIndexQuery(bnode.getUnode()), new PassedArgumentsRequestor() {
            public void call(PassedArgumentInfo info, PythonFileC fileC) {
                // FIXME check scope
                infos.add(info);
            }
        });
        for (PassedArgumentInfo info : infos) {
            PythonValueSet callable = Analysis.evaluate(new ExpressionValueGoal(info.getCallable(), bnode
                    .getDynamicContext()));
            List<Bnode> aliases = new ArrayList<Bnode>();
            callable.computeArgumentAliases(info, bnode.getDynamicContext(), aliases);
            for (Bnode alias : aliases)
                result.add(new CallAliasProxyC(alias, info.getCallable()));
        }
        System.out.println("renames(" + bnode + ") = " + Join.join(", ", result));
        for (PassedArgumentInfo info : infos)
            System.out.println(" - " + info);
        return result;
    }
    
    @pausable
    private static Collection<PythonConstruct> findConstructsAssignedTo(Unode unode, PythonStaticContext sc) {
        Map<PythonScope, Collection<PythonConstruct>> assignmentsByScope = Maps.newHashMap();
        for (PythonScope scope : sc.currentScopesIncludingSelf())
            assignmentsByScope.put(scope, new ArrayList<PythonConstruct>());
        findAssignmentsAndGroupByScopes(sc, unode, assignmentsByScope);
        
        return chooseAssignmentsFromInnermostScope(unode, sc, assignmentsByScope);
    }
    
    @pausable
    public static Set<Bnode> computeAliases(Bnode bnode) {
        Set<Bnode> aliases = newHashSet();
        Set<Bnode> newAliases = newHashSet();
        newAliases.add(bnode);
        while (!newAliases.isEmpty()) {
            Set<Bnode> superNewAliases = newHashSet();
            aliases.addAll(newAliases);
            for (Bnode alias : newAliases)
                superNewAliases.addAll(computeAliases_old(alias));
            superNewAliases.removeAll(aliases);
            newAliases = superNewAliases;
            if (!newAliases.isEmpty())
                System.out.println("PythonAnalHelpers.computeAliases()");
        }
        return aliases;
    }
    
    @pausable
    public static Set<Bnode> computeAliases_old(Bnode bnode) {
        return computeAliases_old(bnode.getUnode(), bnode.getStaticContext(), bnode.getDynamicContext());
    }
    
    @pausable
    public static Set<Bnode> computeAliases_old(Unode unode, PythonStaticContext staticContext,
            PythonDynamicContext dc) {
        Set<Bnode> aliases = newHashSet();
        
        computeRenamesForAliasing(new HeadPunode(unode), staticContext, dc, aliases);
        
        for (Punode punode = unode.punodize(); punode != null; punode = punode.punodize())
            computeRenamesForAliasing(punode, staticContext, dc, aliases);
        System.out.println("Aliases of " + unode + ": " + Join.join(", ", aliases));
        return aliases;
    }
    
    @pausable
    private static void computeRenamesForAliasing(Punode punode, PythonStaticContext sc,
            PythonDynamicContext dc, Set<Bnode> aliases) {
        System.out.println("PythonAnalHelpers.computeRenamesForAliasing(" + punode + ")");
        punode.getHead().findRenames(punode, sc, dc, aliases);
    }
    
    @pausable
    public static void computeRenamesForAliasingUsingIndex(Punode punode, PythonStaticContext sc,
            PythonDynamicContext dc, Set<Bnode> aliases) {
        Collection<PythonConstruct> valuesAssignedToPunodeHead = findRenames(new Bnode(punode.getHead(), sc,
                dc));
        addRenamesForConstructs(punode, aliases, valuesAssignedToPunodeHead, dc);
    }
    
    public static void addRenamesForConstructs(Punode punode, Collection<Bnode> aliases,
            Collection<PythonConstruct> valuesAssignedToPunodeHead, PythonDynamicContext dc) {
        for (PythonConstruct assignedValue : valuesAssignedToPunodeHead)
            addRenameForConstruct(punode, aliases, assignedValue, dc);
    }
    
    public static void addRenameForConstruct(Punode punode, Collection<Bnode> aliases,
            PythonConstruct construct, PythonDynamicContext dc) {
        Unode replacementUnode = construct.toUnode();
        if (replacementUnode != null) {
            Unode wrapped = punode.wrap(replacementUnode);
            aliases.add(new Bnode(wrapped, construct.staticContext(), dc));
        }
    }
    
    @pausable
    public static PythonValueSet queryIndexForValuesAssignedTo(Collection<Bnode> unodes) {
        List<PythonValueSet> values = newArrayList();
        for (Bnode alias : unodes)
            values.add(queryIndexForValuesAssignedTo(alias));
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
    public static PythonValueSet queryIndexForValuesAssignedTo(Bnode bnode) {
        if (!bnode.getUnode().isIndexable())
            return PythonValueSet.EMPTY;
        Collection<PythonConstruct> assignments = findConstructsAssignedTo(bnode.getUnode(), bnode
                .getStaticContext());
        final Collection<Goal<PythonValueSet>> goals = new ArrayList<Goal<PythonValueSet>>();
        for (PythonConstruct assignedValue : assignments)
            goals.add(new ExpressionValueGoal(assignedValue, bnode.getDynamicContext()));
        return PythonValueSet.merge(Analysis.evaluate(goals));
    }
    
}
