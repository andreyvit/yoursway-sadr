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
import com.yoursway.sadr.python.index.unodes.Alias;
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
        AssignmentsRequestor requestor = new AssignmentsRequestor() {
            public void assignment(PythonConstruct rhs, PythonFileC fileC) {
                PythonStaticContext sc = rhs.staticContext();
                Collection<PythonConstruct> result = assignmentsByScope.get(sc);
                if (result == null) {
                    result = new ArrayList<PythonConstruct>();
                    assignmentsByScope.put(sc, result);
                }
                result.add(rhs);
            }
        };
        Collection<Unode> alternatives = new ArrayList<Unode>();
        unode.addGenericVariationsTo(alternatives, true);
        for (Unode alt : alternatives)
            Analysis.queryIndex(new AssignmentsIndexQuery(alt), requestor);
    }
    
    @pausable
    public static Collection<PythonConstruct> findRenames(Alias alias) {
        final Collection<PythonConstruct> result = findConstructsAssignedTo(alias.getUnode(), alias
                .getStaticContext());
        
        final Collection<PassedArgumentInfo> infos = new ArrayList<PassedArgumentInfo>();
        Analysis.queryIndex(new PassedArgumentsIndexQuery(alias.getUnode()), new PassedArgumentsRequestor() {
            public void call(PassedArgumentInfo info, PythonFileC fileC) {
                // FIXME check scope
                infos.add(info);
            }
        });
        for (PassedArgumentInfo info : infos) {
            PythonValueSet callable = Analysis.evaluate(new ExpressionValueGoal(info.getCallable(), alias
                    .getDynamicContext()));
            List<Alias> callableAliases = new ArrayList<Alias>();
            callable.computeArgumentAliases(info, alias.getDynamicContext(), callableAliases);
            for (Alias callableAlias : callableAliases)
                result.add(new CallAliasProxyC(callableAlias, info.getCallable()));
        }
        System.out.println("renames(" + alias + ") = " + Join.join(", ", result));
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
    public static Set<Alias> computeAliases(Alias alias) {
        Set<Alias> aliases = newHashSet();
        Set<Alias> newAliases = newHashSet();
        newAliases.add(alias);
        while (!newAliases.isEmpty()) {
            Set<Alias> superNewAliases = newHashSet();
            aliases.addAll(newAliases);
            for (Alias newAlias : newAliases)
                superNewAliases.addAll(computeAliases_old(newAlias));
            superNewAliases.removeAll(aliases);
            newAliases = superNewAliases;
            if (!newAliases.isEmpty())
                System.out.println("PythonAnalHelpers.computeAliases()");
        }
        return aliases;
    }
    
    @pausable
    public static Set<Alias> computeAliases_old(Alias alias) {
        return computeAliases_old(alias.getUnode(), alias.getStaticContext(), alias.getDynamicContext());
    }
    
    @pausable
    public static Set<Alias> computeAliases_old(Unode unode, PythonStaticContext staticContext,
            PythonDynamicContext dc) {
        Set<Alias> aliases = newHashSet();
        
        computeRenamesForAliasing(new HeadPunode(unode), staticContext, dc, aliases);
        
        for (Punode punode = unode.punodize(); punode != null; punode = punode.punodize())
            computeRenamesForAliasing(punode, staticContext, dc, aliases);
        System.out.println("Aliases of " + unode + ": " + Join.join(", ", aliases));
        return aliases;
    }
    
    @pausable
    private static void computeRenamesForAliasing(Punode punode, PythonStaticContext sc,
            PythonDynamicContext dc, Set<Alias> aliases) {
        System.out.println("PythonAnalHelpers.computeRenamesForAliasing(" + punode + ")");
        punode.getHead().findRenames(punode, sc, dc, aliases);
    }
    
    @pausable
    public static void computeRenamesForAliasingUsingIndex(Punode punode, PythonStaticContext sc,
            PythonDynamicContext dc, Set<Alias> aliases) {
        Collection<PythonConstruct> valuesAssignedToPunodeHead = findRenames(new Alias(punode.getHead(), sc,
                dc));
        addRenamesForConstructs(punode, aliases, valuesAssignedToPunodeHead, dc);
    }
    
    public static void addRenamesForConstructs(Punode punode, Collection<Alias> aliases,
            Collection<PythonConstruct> valuesAssignedToPunodeHead, PythonDynamicContext dc) {
        for (PythonConstruct assignedValue : valuesAssignedToPunodeHead)
            addRenameForConstruct(punode, aliases, assignedValue, dc);
    }
    
    public static void addRenameForConstruct(Punode punode, Collection<Alias> aliases,
            PythonConstruct construct, PythonDynamicContext dc) {
        Unode replacementUnode = construct.toUnode();
        if (replacementUnode != null) {
            Unode wrapped = punode.wrap(replacementUnode);
            aliases.add(new Alias(wrapped, construct.staticContext(), dc));
        }
    }
    
    @pausable
    public static PythonValueSet queryIndexForValuesAssignedTo(Collection<Alias> unodes) {
        List<PythonValueSet> values = newArrayList();
        for (Alias alias : unodes) {
            PythonValueSet vs = alias.getUnode().calculateLiteralValue();
            if (vs != null)
                values.add(vs);
            values.add(queryIndexForValuesAssignedTo(alias));
        }
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
    public static PythonValueSet queryIndexForValuesAssignedTo(Alias alias) {
        if (!alias.getUnode().isIndexable())
            return PythonValueSet.EMPTY;
        Collection<PythonConstruct> assignments = findConstructsAssignedTo(alias.getUnode(), alias
                .getStaticContext());
        final Collection<Goal<PythonValueSet>> goals = new ArrayList<Goal<PythonValueSet>>();
        for (PythonConstruct assignedValue : assignments)
            goals.add(new ExpressionValueGoal(assignedValue, alias.getDynamicContext()));
        return PythonValueSet.merge(Analysis.evaluate(goals));
    }
    
}
