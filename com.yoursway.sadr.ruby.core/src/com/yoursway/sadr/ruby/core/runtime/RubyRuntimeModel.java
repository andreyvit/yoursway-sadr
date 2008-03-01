package com.yoursway.sadr.ruby.core.runtime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yoursway.sadr.ruby.core.runtime.requestors.methods.AnyMethodRequestor;
import com.yoursway.sadr.ruby.core.runtime.std.StandardTypes;
import com.yoursway.sadr.ruby.core.runtime.std.StandardTypesImpl;
import com.yoursway.sadr.ruby.core.typeinferencing.services.ClassLookup;
import com.yoursway.sadr.ruby.core.typeinferencing.services.InstanceRegistrar;
import com.yoursway.sadr.ruby.core.typeinferencing.services.ProcedureLookup;
import com.yoursway.sadr.ruby.core.typeinferencing.services.VariableLookup;
import com.yoursway.sadr.ruby.core.typeinferencing.values.InstanceRegistrarImpl;

public class RubyRuntimeModel implements ClassLookup, VariableLookup, ProcedureLookup {
    
    private final Collection<RubyClass> klasses = new ArrayList<RubyClass>();
    
    private final Map<String, RubyClass> lowercaseNamesToClasses = new HashMap<String, RubyClass>();
    
    private final Collection<RubyProcedure> procedures = new ArrayList<RubyProcedure>();
    
    private final Map<String, RubyProcedure> lowercaseNamesToProcedures = new HashMap<String, RubyProcedure>();
    
    private final Collection<RubyGlobalVariable> globalVariables = new ArrayList<RubyGlobalVariable>();
    
    private final Map<String, RubyGlobalVariable> lowercaseNamesToGlobalVariables = new HashMap<String, RubyGlobalVariable>();
    
    private final Collection<RubySimpleType> simpleTypes = new ArrayList<RubySimpleType>();
    
    private final Map<String, RubySimpleType> lowercaseNamesToSimpleTypes = new HashMap<String, RubySimpleType>();
    
    private final Map<String, RubyClass[]> methodToClasses = new HashMap<String, RubyClass[]>();
    
    private final StandardTypes standardTypes = new StandardTypesImpl(this);
    
    private final InstanceRegistrar instanceRegistrar = new InstanceRegistrarImpl();
    
    public RubyRuntimeModel() {
        System.out.println();
    }
    
    public RubyClass lookupClass(String name) {
        RubyClass klass = findClass(name);
        if (klass != null)
            return klass;
        return new RubyClass(this, name);
    }
    
    public RubyClass findClass(String name) {
        return lowercaseNamesToClasses.get(name.toLowerCase());
    }
    
    public RubyGlobalVariable lookupGlobalVariable(String name) {
        RubyGlobalVariable globalVariable = findGlobalVariable(name);
        if (globalVariable != null)
            return globalVariable;
        return new RubyGlobalVariable(this, name);
    }
    
    private RubyGlobalVariable findGlobalVariable(String name) {
        String lowerName = name.toLowerCase();
        RubyGlobalVariable globalVariable = lowercaseNamesToGlobalVariables.get(lowerName);
        return globalVariable;
    }
    
    public void addClass(RubyClass klass) {
        klasses.add(klass);
        lowercaseNamesToClasses.put(klass.name().toLowerCase(), klass);
    }
    
    public void addProcedure(RubyProcedure procedure) {
        procedures.add(procedure);
        lowercaseNamesToProcedures.put(procedure.name().toLowerCase(), procedure);
    }
    
    public void addGlobalVariable(RubyGlobalVariable globalVariable) {
        globalVariables.add(globalVariable);
        lowercaseNamesToGlobalVariables.put(globalVariable.name().toLowerCase(), globalVariable);
    }
    
    public RubyProcedure[] findProceduresMatching(String prefix) {
        List<RubyProcedure> result = new ArrayList<RubyProcedure>();
        for (RubyProcedure procedure : procedures)
            if (procedure.matches(prefix))
                result.add(procedure);
        return result.toArray(new RubyProcedure[result.size()]);
    }
    
    public void addSimpleType(RubySimpleType simpleType) {
        simpleTypes.add(simpleType);
        lowercaseNamesToSimpleTypes.put(simpleType.name().toLowerCase(), simpleType);
    }
    
    public StandardTypes standardTypes() {
        return standardTypes;
    }
    
    public RubyVariable findVariable(String name) {
        return findGlobalVariable(name);
    }
    
    public RubyProcedure findProcedure(String name) {
        return lowercaseNamesToProcedures.get(name.toLowerCase());
    }
    
    public RubyClass[] allClasses() {
        return klasses.toArray(new RubyClass[klasses.size()]);
    }
    
    public RubyClass[] findClassesWithMethod(String method) {
        String lower = method.toLowerCase();
        RubyClass[] result = methodToClasses.get(lower);
        if (result == null) {
            result = calculateClassesWithMethod(method);
            methodToClasses.put(lower, result);
        }
        return result;
    }
    
    private RubyClass[] calculateClassesWithMethod(String method) {
        Collection<RubyClass> result = new ArrayList<RubyClass>();
        for (RubyClass klass : klasses) {
            AnyMethodRequestor rq = new AnyMethodRequestor();
            klass.findMethod(method, rq);
            if (rq.anythingFound())
                result.add(klass);
        }
        return result.toArray(new RubyClass[result.size()]);
    }
    
    public Set<RubyClass> findClassesByMethods(String[] methods) {
        return calculateClassesByMethods(methods);
    }
    
    private Set<RubyClass> calculateClassesByMethods(String[] methods) {
        Set<RubyClass> klasses = null;
        for (String method : methods) {
            RubyClass[] matching = findClassesWithMethod(method);
            HashSet<RubyClass> set = new HashSet<RubyClass>(Arrays.asList(matching));
            if (set.isEmpty())
                continue;
            if (klasses == null)
                klasses = set;
            else
                klasses.retainAll(set);
        }
        if (klasses == null)
            return Collections.emptySet();
        eliminateExtraSubclasses(klasses, methods);
        return klasses;
    }
    
    private void eliminateExtraSubclasses(Set<RubyClass> klasses, String[] methods) {
        for (Iterator<RubyClass> iter = klasses.iterator(); iter.hasNext();)
            if (!iter.next().definesAtLeastOneOf(methods))
                iter.remove();
    }
    
    public InstanceRegistrar instanceRegistrar() {
        return instanceRegistrar;
    }
    
}
