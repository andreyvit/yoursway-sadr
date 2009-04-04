package com.yoursway.sadr.python.analysis.aliasing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import kilim.pausable;

import com.google.common.base.Join;
import com.google.common.collect.Maps;
import com.yoursway.sadr.engine.Analysis;
import com.yoursway.sadr.python.analysis.PythonAnalHelpers;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.context.lexical.scopes.PythonScope;
import com.yoursway.sadr.python.analysis.goals.ExpressionValueGoal;
import com.yoursway.sadr.python.analysis.index.data.PassedArgumentInfo;
import com.yoursway.sadr.python.analysis.index.queries.AssignmentsIndexQuery;
import com.yoursway.sadr.python.analysis.index.queries.AssignmentsRequestor;
import com.yoursway.sadr.python.analysis.index.queries.PassedArgumentsIndexQuery;
import com.yoursway.sadr.python.analysis.index.queries.PassedArgumentsRequestor;
import com.yoursway.sadr.python.analysis.lang.unodes.Bnode;
import com.yoursway.sadr.python.analysis.lang.unodes.Suffix;
import com.yoursway.sadr.python.analysis.lang.unodes.Unode;
import com.yoursway.sadr.python.analysis.lang.unodes.indexable.VariableUnode;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public final class Alias {
    
    private final Unode unode;
    private final PythonDynamicContext dc;
    private final PythonLexicalContext lc;
    
    public Alias(Unode unode, PythonLexicalContext lc, PythonDynamicContext dc) {
        if (unode == null)
            throw new NullPointerException("unode is null");
        if (lc == null)
            throw new NullPointerException("lc is null");
        if (dc == null)
            throw new NullPointerException("dc is null");
        this.unode = unode;
        this.lc = lc;
        this.dc = dc;
    }
    
    @pausable
    public PythonValueSet calculateValue() {
        return unode.calculateValue(lc, dc);
    }
    
    public PythonLexicalContext getStaticContext() {
        return lc;
    }
    
    public PythonDynamicContext getDynamicContext() {
        return dc;
    }
    
    public Unode getUnode() {
        return unode;
    }
    
    @Override
    public String toString() {
        return unode + " @" + lc;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((lc == null) ? 0 : lc.hashCode());
        result = prime * result + ((unode == null) ? 0 : unode.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Alias other = (Alias) obj;
        if (lc == null) {
            if (other.lc != null)
                return false;
        } else if (!lc.equals(other.lc))
            return false;
        if (unode == null) {
            if (other.unode != null)
                return false;
        } else if (!unode.equals(other.unode))
            return false;
        return true;
    }
    
    @pausable
    public void findRenamesUsingIndex(Suffix suffix, AliasConsumer aliases) {
        Collection<Bnode> result = findConstructsAssignedTo();
        PythonAnalHelpers.addRenamesForConstructs(suffix, aliases, result, dc);
        addUsagesInCallsTo(suffix, aliases);
    }
    
    @pausable
    public PythonValueSet queryIndexForValuesAssignedTo() {
        if (!unode.isIndexable())
            return PythonValueSet.EMPTY;
        return PythonAnalHelpers.evaluateConstructs(findConstructsAssignedTo(), getDynamicContext());
    }
    
    @pausable
    private void addUsagesInCallsTo(Suffix suffix, AliasConsumer aliases) {
        final Collection<PassedArgumentInfo> infos = new ArrayList<PassedArgumentInfo>();
        Analysis.queryIndex(new PassedArgumentsIndexQuery(getUnode()), new PassedArgumentsRequestor() {
            public void call(PassedArgumentInfo info) {
                // FIXME check scope
                infos.add(info);
            }
        });
        for (PassedArgumentInfo info : infos) {
            PythonValueSet callable = Analysis.evaluate(new ExpressionValueGoal(info.getCallable(), dc));
            callable.computeArgumentAliases(info, dc, suffix, aliases);
        }
    }
    
    @pausable
    public Collection<Bnode> findConstructsAssignedTo() {
        return chooseAssignmentsFromInnermostScope(findAssignmentsAndGroupByScopes());
    }
    
    @pausable
    private Map<PythonScope, Collection<Bnode>> findAssignmentsAndGroupByScopes() {
        final Map<PythonScope, Collection<Bnode>> assignmentsByScope = Maps.newHashMap();
        AssignmentsRequestor requestor = new AssignmentsRequestor() {
            public void assignment(Bnode rhs) {
                PythonScope sc = rhs.lc().getScope();
                Collection<Bnode> result = assignmentsByScope.get(sc);
                if (result == null) {
                    result = new ArrayList<Bnode>();
                    assignmentsByScope.put(sc, result);
                }
                result.add(rhs);
            }
        };
        Collection<Unode> alternatives = new ArrayList<Unode>();
        unode.addGenericVariationsTo(alternatives, true);
        for (Unode alt : alternatives)
            Analysis.queryIndex(new AssignmentsIndexQuery(alt), requestor);
        return assignmentsByScope;
    }
    
    private Collection<Bnode> chooseAssignmentsFromInnermostScope(
            final Map<PythonScope, Collection<Bnode>> assignmentsByScope) {
        VariableUnode variable = unode.leadingVariableUnode();
        if (variable == null)
            throw new IllegalArgumentException(
                    "Complex expressions are awful candidates for alias calculation");
        PythonScope definingScope = lc.getScope().findDefiningScope(variable.getName());
        Collection<Bnode> result = new ArrayList<Bnode>();
        for (Map.Entry<PythonScope, Collection<Bnode>> entry : assignmentsByScope.entrySet())
            if (definingScope == entry.getKey().findDefiningScope(variable.getName()))
                result.addAll(entry.getValue());
        return result;
    }
    
    @pausable
    private void computeAliasesOnce(AliasConsumer aliases) {
        unode.computeAliases(Suffix.EMPTY, lc, dc, aliases);
    }
    
    @pausable
    public Set<Alias> computeAliases() {
        MultiPassAliasesCollector aliases = new MultiPassAliasesCollector();
        aliases.add(this);
        while (aliases.hasNew())
            for (Alias newAlias : aliases.retrieveNewAliases())
                newAlias.computeAliasesOnce(aliases);
        Set<Alias> result = aliases.asSet();
        System.out.println("Alias.computeAliases(" + unode + ") = " + Join.join(", ", result));
        return result;
    }
}
