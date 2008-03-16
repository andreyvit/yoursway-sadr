package com.yoursway.sadr.python.core.runtime;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

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

import com.yoursway.sadr.python.core.runtime.requestors.methods.AnyMethodRequestor;
import com.yoursway.sadr.python.core.runtime.std.StandardTypes;
import com.yoursway.sadr.python.core.runtime.std.StandardTypesImpl;
import com.yoursway.sadr.python.core.typeinferencing.services.ClassLookup;
import com.yoursway.sadr.python.core.typeinferencing.services.InstanceRegistrar;
import com.yoursway.sadr.python.core.typeinferencing.services.ProcedureLookup;
import com.yoursway.sadr.python.core.typeinferencing.services.VariableLookup;
import com.yoursway.sadr.python.core.typeinferencing.values.InstanceRegistrarImpl;

public class PythonRuntimeModel implements ClassLookup, VariableLookup, ProcedureLookup {
    
    private final Collection<PythonClass> klasses = new ArrayList<PythonClass>();
    
    private final Collection<PythonModule> modules = newArrayList();
    
    private final Map<String, PythonClass> namesToClasses = new HashMap<String, PythonClass>();
    
    private final Map<String, PythonModule> namesToModules = newHashMap();
    
    private final Collection<PythonProcedure> procedures = new ArrayList<PythonProcedure>();
    
    private final Map<String, PythonProcedure> namesToProcedures = new HashMap<String, PythonProcedure>();
    
    private final Collection<PythonSimpleType> simpleTypes = new ArrayList<PythonSimpleType>();
    
    private final Map<String, PythonSimpleType> namesToSimpleTypes = new HashMap<String, PythonSimpleType>();
    
    private final Map<String, PythonClass[]> methodToClasses = new HashMap<String, PythonClass[]>();
    
    private final StandardTypes standardTypes = new StandardTypesImpl(this);
    
    private final InstanceRegistrar instanceRegistrar = new InstanceRegistrarImpl();
    
    public PythonRuntimeModel() {
        System.out.println();
    }
    
    public PythonClass lookupClass(String name) {
        PythonClass klass = findClass(name);
        if (klass != null)
            return klass;
        return new PythonClass(this, name);
    }
    
    public PythonClass findClass(String name) {
        return namesToClasses.get(name);
    }
    
    public void addClass(PythonClass klass) {
        klasses.add(klass);
        namesToClasses.put(klass.name(), klass);
    }
    
    public void addProcedure(PythonProcedure procedure) {
        procedures.add(procedure);
        namesToProcedures.put(procedure.name(), procedure);
    }
    
    public PythonProcedure[] findProceduresMatching(String prefix) {
        List<PythonProcedure> result = new ArrayList<PythonProcedure>();
        for (PythonProcedure procedure : procedures)
            if (procedure.matches(prefix))
                result.add(procedure);
        return result.toArray(new PythonProcedure[result.size()]);
    }
    
    public void addSimpleType(PythonSimpleType simpleType) {
        simpleTypes.add(simpleType);
        namesToSimpleTypes.put(simpleType.name(), simpleType);
    }
    
    public StandardTypes standardTypes() {
        return standardTypes;
    }
    
    public PythonVariable findVariable(String name) {
        return null;
    }
    
    public PythonProcedure findProcedure(String name) {
        return namesToProcedures.get(name);
    }
    
    public PythonClass[] allClasses() {
        return klasses.toArray(new PythonClass[klasses.size()]);
    }
    
    public PythonClass[] findClassesWithMethod(String method) {
        PythonClass[] result = methodToClasses.get(method);
        if (result == null) {
            result = calculateClassesWithMethod(method);
            methodToClasses.put(method, result);
        }
        return result;
    }
    
    private PythonClass[] calculateClassesWithMethod(String method) {
        Collection<PythonClass> result = new ArrayList<PythonClass>();
        for (PythonClass klass : klasses) {
            AnyMethodRequestor rq = new AnyMethodRequestor();
            klass.findMethod(method, rq);
            if (rq.anythingFound())
                result.add(klass);
        }
        return result.toArray(new PythonClass[result.size()]);
    }
    
    public Set<PythonClass> findClassesByMethods(String[] methods) {
        return calculateClassesByMethods(methods);
    }
    
    private Set<PythonClass> calculateClassesByMethods(String[] methods) {
        Set<PythonClass> klasses = null;
        for (String method : methods) {
            PythonClass[] matching = findClassesWithMethod(method);
            HashSet<PythonClass> set = new HashSet<PythonClass>(Arrays.asList(matching));
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
    
    private void eliminateExtraSubclasses(Set<PythonClass> klasses, String[] methods) {
        for (Iterator<PythonClass> iter = klasses.iterator(); iter.hasNext();)
            if (!iter.next().definesAtLeastOneOf(methods))
                iter.remove();
    }
    
    public InstanceRegistrar instanceRegistrar() {
        return instanceRegistrar;
    }
    
    public PythonVariable lookupVariable(String name) {
        return null;
    }
    
    public void addModule(PythonModule module) {
        modules.add(module);
        namesToModules.put(module.name(), module);
    }
    
}
