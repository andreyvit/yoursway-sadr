package com.yoursway.sadr.python.analysis;

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
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonScope;
import com.yoursway.sadr.python.analysis.context.lexical.PythonStaticContext;
import com.yoursway.sadr.python.analysis.goals.ExpressionValueGoal;
import com.yoursway.sadr.python.analysis.index.data.PassedArgumentInfo;
import com.yoursway.sadr.python.analysis.index.queries.AssignmentsIndexQuery;
import com.yoursway.sadr.python.analysis.index.queries.AssignmentsRequestor;
import com.yoursway.sadr.python.analysis.index.queries.PassedArgumentsIndexQuery;
import com.yoursway.sadr.python.analysis.index.queries.PassedArgumentsRequestor;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstruct;
import com.yoursway.sadr.python.analysis.lang.constructs.ast.PythonFileC;
import com.yoursway.sadr.python.analysis.lang.constructs.special.CallAliasProxyC;
import com.yoursway.sadr.python.analysis.lang.unodes.Unode;
import com.yoursway.sadr.python.analysis.lang.unodes.indexable.VariableUnode;
import com.yoursway.sadr.python.analysis.lang.unodes.punodes.HeadPunode;
import com.yoursway.sadr.python.analysis.lang.unodes.punodes.Punode;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public final class Alias {
    
    private final Unode unode;
    private final PythonScope sc;
    private final PythonDynamicContext dc;
    
    public Alias(Unode unode, PythonScope sc, PythonDynamicContext dc) {
        if (unode == null)
            throw new NullPointerException("unode is null");
        if (sc == null)
            throw new NullPointerException("sc is null");
        if (dc == null)
            throw new NullPointerException("dc is null");
        this.unode = unode;
        this.sc = sc;
        this.dc = dc;
    }
    
    @pausable
    public PythonValueSet calculateValue(PythonDynamicContext dc) {
        return unode.calculateValue(sc.scopeContext(), dc);
    }
    
    public PythonStaticContext getStaticContext() {
        return sc.scopeContext();
    }
    
    public PythonDynamicContext getDynamicContext() {
        return dc;
    }
    
    public Unode getUnode() {
        return unode;
    }
    
    @Override
    public String toString() {
        return unode + " @" + sc;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((sc == null) ? 0 : sc.hashCode());
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
        if (sc == null) {
            if (other.sc != null)
                return false;
        } else if (!sc.equals(other.sc))
            return false;
        if (unode == null) {
            if (other.unode != null)
                return false;
        } else if (!unode.equals(other.unode))
            return false;
        return true;
    }
    
    @pausable
    public Collection<PythonConstruct> findRenamesUsingIndex() {
        Collection<PythonConstruct> result = findConstructsAssignedTo();
        addUsagesInCallsTo(result);
        System.out.println("Alias.findRenamesUsingIndex(" + unode + ") = " + Join.join(", ", result));
        return result;
    }
    
    @pausable
    public PythonValueSet queryIndexForValuesAssignedTo() {
        if (!unode.isIndexable())
            return PythonValueSet.EMPTY;
        return PythonAnalHelpers.evaluateConstructs(findConstructsAssignedTo(), getDynamicContext());
    }
    
    @pausable
    private void addUsagesInCallsTo(Collection<PythonConstruct> result) {
        final Collection<PassedArgumentInfo> infos = new ArrayList<PassedArgumentInfo>();
        Analysis.queryIndex(new PassedArgumentsIndexQuery(getUnode()), new PassedArgumentsRequestor() {
            public void call(PassedArgumentInfo info, PythonFileC fileC) {
                // FIXME check scope
                infos.add(info);
            }
        });
        for (PassedArgumentInfo info : infos) {
            PythonValueSet callable = Analysis.evaluate(new ExpressionValueGoal(info.getCallable(), dc));
            List<Alias> callableAliases = new ArrayList<Alias>();
            callable.computeArgumentAliases(info, dc, callableAliases);
            for (Alias callableAlias : callableAliases)
                result.add(new CallAliasProxyC(callableAlias, info.getCallable()));
        }
    }
    
    @pausable
    public Collection<PythonConstruct> findConstructsAssignedTo() {
        return chooseAssignmentsFromInnermostScope(findAssignmentsAndGroupByScopes());
    }
    
    @pausable
    private Map<PythonScope, Collection<PythonConstruct>> findAssignmentsAndGroupByScopes() {
        final Map<PythonScope, Collection<PythonConstruct>> assignmentsByScope = Maps.newHashMap();
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
        return assignmentsByScope;
    }
    
    private Collection<PythonConstruct> chooseAssignmentsFromInnermostScope(
            final Map<PythonScope, Collection<PythonConstruct>> assignmentsByScope) {
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
    private Set<Alias> computeAliasesOnce() {
        Set<Alias> aliases = newHashSet();
        unode.findRenames(new HeadPunode(unode), sc.scopeContext(), dc, aliases);
        
        for (Punode punode = unode.punodize(); punode != null; punode = punode.punodize()) {
            punode.getHead().findRenames(punode, sc.scopeContext(), dc, aliases);
        }
        return aliases;
    }
    
    @pausable
    public Set<Alias> computeAliases() {
        Set<Alias> aliases = newHashSet();
        Set<Alias> newAliases = newHashSet();
        newAliases.add(this);
        while (!newAliases.isEmpty()) {
            Set<Alias> superNewAliases = newHashSet();
            aliases.addAll(newAliases);
            for (Alias newAlias : newAliases)
                superNewAliases.addAll(newAlias.computeAliasesOnce());
            superNewAliases.removeAll(aliases);
            newAliases = superNewAliases;
        }
        System.out.println("Alias.computeAliases(" + unode + ") = " + Join.join(", ", aliases));
        return aliases;
    }
}
