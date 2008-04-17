package com.yoursway.sadr.python.core.typeinferencing.goals;

import java.util.ArrayList;
import java.util.Collection;

import com.yoursway.sadr.blocks.foundation.types.Type;
import com.yoursway.sadr.blocks.foundation.types.UnknownType;
import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo;
import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfoBuilder;
import com.yoursway.sadr.blocks.foundation.values.Value;
import com.yoursway.sadr.engine.ContextSensitiveThing;
import com.yoursway.sadr.python.core.runtime.PythonBasicClass;
import com.yoursway.sadr.python.core.runtime.PythonField;
import com.yoursway.sadr.python.core.runtime.PythonMethod;
import com.yoursway.sadr.python.core.runtime.PythonUtils;
import com.yoursway.sadr.python.core.runtime.requestors.methods.MethodRequestor;
import com.yoursway.sadr.python.core.typeinferencing.types.ArrayType;
import com.yoursway.sadr.python.core.typeinferencing.types.InstanceType;
import com.yoursway.sadr.python.core.typeinferencing.types.MetaClassType;
import com.yoursway.sadr.python.core.typeinferencing.types.TypeUtils;
import com.yoursway.sadr.python.core.typeinferencing.values.ArrayValue;
import com.yoursway.sadr.python.core.typeinferencing.valuesets.ValueSet;

public class ValueInfoUtils {
    
    public static ValueInfo getSuper(ValueInfo valueInfo) {
        ValueInfoBuilder builder = new ValueInfoBuilder();
        for (Type type : valueInfo.getTypeSet().containedTypes()) {
            if (type instanceof InstanceType || type instanceof MetaClassType) {
                PythonBasicClass klass = (type instanceof InstanceType ? ((InstanceType) type).runtimeClass()
                        : ((MetaClassType) type).runtimeMetaClass());
                PythonBasicClass sup = (klass).superclassOfTheSameKind();
                if (sup != null)
                    builder.add(PythonUtils.createType(sup));
            }
        }
        // TODO: handle values
        return builder.build();
    }
    
    public static ValueInfo unwrapArray(ValueInfo valueInfo) {
        ValueInfoBuilder builder = new ValueInfoBuilder();
        boolean unknownTypeEncountered = false;
        for (Type type : valueInfo.getTypeSet().containedTypes())
            if (type instanceof ArrayType) {
                Type component = ((ArrayType) type).component();
                if (component == UnknownType.INSTANCE)
                    unknownTypeEncountered = true;
                else
                    builder.add(component);
            }
        if (builder.isEmpty() && unknownTypeEncountered)
            builder.add(UnknownType.INSTANCE);
        for (Value value : valueInfo.getValueSet().containedValues())
            if (value instanceof ArrayValue) {
                ValueSet component = ((ArrayValue) value).combinedComponent();
                builder.add(component);
            }
        return builder.build();
    }
    
    public static void findMethod(ValueInfo valueInfo, String name, MethodRequestor requestor) {
        TypeUtils.findMethod(valueInfo.getTypeSet(), name, requestor);
    }
    
    public static Collection<PythonBasicClass> possibleClasses(ValueInfo valueInfo) {
        Collection<PythonBasicClass> list = new ArrayList<PythonBasicClass>();
        for (Type type : valueInfo.getTypeSet().containedTypes()) {
            PythonBasicClass klass = PythonUtils.unwrapType(type);
            if (klass != null)
                list.add(klass);
        }
        return list;
    }
    
    public static PythonMethod[] findMethodsByPrefix(ValueInfo valueInfo, String prefix) {
        return TypeUtils.findMethodsByPrefix(valueInfo.getTypeSet(), prefix);
    }
    
    public static void findMethodsByPrefix(ValueInfo valueInfo, String prefix, MethodRequestor requestor) {
        for (PythonMethod method : TypeUtils.findMethodsByPrefix(valueInfo.getTypeSet(), prefix))
            requestor.accept(method);
    }
    
    public static Collection<PythonField> lookupField(ValueInfo valueInfo, String name) {
        Collection<PythonField> list = new ArrayList<PythonField>();
        for (Type type : valueInfo.getTypeSet().containedTypes()) {
            PythonBasicClass klass = PythonUtils.unwrapType(type);
            if (klass != null)
                list.add(klass.lookupField(name));
        }
        return list;
    }
    
    public static void addResultOf(ValueInfoBuilder valueInfoBuilder, ValueInfoGoal goal,
            ContextSensitiveThing thing) {
        valueInfoBuilder.add(goal.result(thing));
    }
    
}
