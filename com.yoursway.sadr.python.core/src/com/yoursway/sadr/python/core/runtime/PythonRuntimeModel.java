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

import com.yoursway.sadr.blocks.foundation.RuntimeModel;
import com.yoursway.sadr.blocks.integer_literals.IntegerTypesSupport;
import com.yoursway.sadr.blocks.integer_literals.RuntimeModelWithIntegerTypes;
import com.yoursway.sadr.python.core.runtime.requestors.methods.AnyMethodRequestor;
import com.yoursway.sadr.python.core.typeinferencing.services.VariableLookup;
import com.yoursway.sadr.python.core.typeinferencing.values.InstanceRegistrar;
import com.yoursway.utils.facelets.GemstoneDefinition;
import com.yoursway.utils.facelets.GemstoneImpl;

public class PythonRuntimeModel extends GemstoneImpl<RuntimeModel> implements ClassLookup, VariableLookup,
        ProcedureLookup, RuntimeModel {
    
    private final Collection<PythonMetaType> klasses = new ArrayList<PythonMetaType>();
    
    private final Collection<PythonModule> modules = newArrayList();
    
    private final Map<String, PythonClass> namesToClasses = new HashMap<String, PythonClass>();
    
    private final Map<String, PythonModule> namesToModules = newHashMap();
    
    private final Collection<PythonProcedure> procedures = new ArrayList<PythonProcedure>();
    
    private final Map<String, PythonProcedure> namesToProcedures = new HashMap<String, PythonProcedure>();
    
    private final Map<String, PythonClass[]> methodToClasses = new HashMap<String, PythonClass[]>();
    
    private final StandardTypes standardTypes;
    
    private final InstanceRegistrar instanceRegistrar = new InstanceRegistrar();
    
    private final PythonAnalysisSchema schema;
    
    public PythonRuntimeModel(PythonAnalysisSchema schema, GemstoneDefinition<RuntimeModel> definition) {
        super(definition);
        this.schema = schema;
        standardTypes = new StandardTypesImpl(this, schema);
    }
    
    public PythonMetaType lookupClass(String name) {
        PythonMetaType klass = findClass(name);
        if (klass != null)
            return klass;
        return new PythonClass(this, name);
    }
    
    public PythonMetaType findClass(String name) {
        return namesToClasses.get(name);
    }
    
    public void addClass(PythonMetaType klass) {
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
        Collection<PythonMetaType> result = new ArrayList<PythonMetaType>();
        for (PythonMetaType klass : klasses) {
            AnyMethodRequestor rq = new AnyMethodRequestor();
            klass.findMethod(method, rq);
            if (rq.anythingFound())
                result.add(klass);
        }
        return result.toArray(new PythonClass[result.size()]);
    }
    
    public Set<PythonMetaType> findClassesByMethods(String[] methods) {
        return calculateClassesByMethods(methods);
    }
    
    private Set<PythonMetaType> calculateClassesByMethods(String[] methods) {
        Set<PythonMetaType> klasses = null;
        for (String method : methods) {
            PythonClass[] matching = findClassesWithMethod(method);
            HashSet<PythonMetaType> set = new HashSet<PythonMetaType>(Arrays.asList(matching));
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
    
    private void eliminateExtraSubclasses(Set<PythonMetaType> klasses, String[] methods) {
        for (Iterator<PythonMetaType> iter = klasses.iterator(); iter.hasNext();)
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
    
    public IntegerTypesSupport integerTypesSupport() {
        return schema.integerTypesSupport;
    }
    
    public RuntimeModelWithIntegerTypes runtimeModelWithIntegerTypes() {
        return schema.integerTypesSupport.facelet(this);
    }
    
    public PythonAnalysisSchema schema() {
        return schema;
    }
    
}
