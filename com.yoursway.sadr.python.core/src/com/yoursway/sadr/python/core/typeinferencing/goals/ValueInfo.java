package com.yoursway.sadr.python.core.typeinferencing.goals;

import static com.yoursway.sadr.python.core.typeinferencing.typesets.TypeSetFactory.emptyTypeSet;
import static com.yoursway.sadr.python.core.typeinferencing.valuesets.ValueSetFactory.emptyValueSet;

import java.util.ArrayList;
import java.util.Collection;

import com.yoursway.sadr.engine.Result;
import com.yoursway.sadr.python.core.runtime.RubyBasicClass;
import com.yoursway.sadr.python.core.runtime.RubyMethod;
import com.yoursway.sadr.python.core.runtime.RubyUtils;
import com.yoursway.sadr.python.core.runtime.requestors.methods.MethodRequestor;
import com.yoursway.sadr.python.core.typeinferencing.types.ArrayType;
import com.yoursway.sadr.python.core.typeinferencing.types.ClassType;
import com.yoursway.sadr.python.core.typeinferencing.types.MetaClassType;
import com.yoursway.sadr.python.core.typeinferencing.types.Type;
import com.yoursway.sadr.python.core.typeinferencing.types.UnknownType;
import com.yoursway.sadr.python.core.typeinferencing.typesets.TypeSet;
import com.yoursway.sadr.python.core.typeinferencing.values.ArrayValue;
import com.yoursway.sadr.python.core.typeinferencing.values.Value;
import com.yoursway.sadr.python.core.typeinferencing.valuesets.ValueSet;

public class ValueInfo implements Result {
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((typeSet == null) ? 0 : typeSet.hashCode());
        result = prime * result + ((valueSet == null) ? 0 : valueSet.hashCode());
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
        final ValueInfo other = (ValueInfo) obj;
        if (typeSet == null) {
            if (other.typeSet != null)
                return false;
        } else if (!typeSet.equals(other.typeSet))
            return false;
        if (valueSet == null) {
            if (other.valueSet != null)
                return false;
        } else if (!valueSet.equals(other.valueSet))
            return false;
        return true;
    }
    
    public static ValueInfo emptyValueInfo() {
        return new ValueInfo(emptyTypeSet(), emptyValueSet());
    }
    
    public static ValueInfo createResult(TypeSet typeSet, ValueSet valueSet) {
        return new ValueInfo(typeSet, valueSet);
    }
    
    private final TypeSet typeSet;
    private final ValueSet valueSet;
    
    public ValueInfo(TypeSet typeSet, ValueSet valueSet) {
        this.typeSet = typeSet;
        this.valueSet = valueSet;
    }
    
    public TypeSet getTypeSet() {
        return typeSet;
    }
    
    public ValueSet getValueSet() {
        return valueSet;
    }
    
    public void findMethod(String name, MethodRequestor requestor) {
        typeSet.findMethod(name, requestor);
    }
    
    public ValueInfo getSuper() {
        ValueInfoBuilder builder = new ValueInfoBuilder();
        for (Type type : typeSet.containedTypes()) {
            if (type instanceof ClassType || type instanceof MetaClassType) {
                RubyBasicClass klass = (type instanceof ClassType ? ((ClassType) type).runtimeClass()
                        : ((MetaClassType) type).runtimeMetaClass());
                RubyBasicClass sup = (klass).superclassOfTheSameKind();
                if (sup != null)
                    builder.add(RubyUtils.createType(sup));
            }
        }
        // TODO: handle values
        return builder.build();
    }
    
    public ValueInfo unwrapArray() {
        ValueInfoBuilder builder = new ValueInfoBuilder();
        boolean unknownTypeEncountered = false;
        for (Type type : typeSet.containedTypes())
            if (type instanceof ArrayType) {
                Type component = ((ArrayType) type).component();
                if (component == UnknownType.INSTANCE)
                    unknownTypeEncountered = true;
                else
                    builder.add(component);
            }
        if (builder.isEmpty() && unknownTypeEncountered)
            builder.add(UnknownType.INSTANCE);
        for (Value value : valueSet.containedValues())
            if (value instanceof ArrayValue) {
                ValueSet component = ((ArrayValue) value).combinedComponent();
                builder.add(component);
            }
        return builder.build();
    }
    
    public Collection<RubyBasicClass> possibleClasses() {
        Collection<RubyBasicClass> list = new ArrayList<RubyBasicClass>();
        for (Type type : typeSet.containedTypes()) {
            RubyBasicClass klass = RubyUtils.unwrapType(type);
            if (klass != null)
                list.add(klass);
        }
        return list;
    }
    
    public RubyMethod[] findMethodsByPrefix(String prefix) {
        return typeSet.findMethodsByPrefix(prefix);
    }
    
    public void findMethodsByPrefix(String prefix, MethodRequestor requestor) {
        for (RubyMethod method : typeSet.findMethodsByPrefix(prefix))
            requestor.accept(method);
    }
    
    public boolean isEmpty() {
        return typeSet.isEmpty();
    }
    
    public String[] describePossibleTypes() {
        return typeSet.describePossibleTypes();
    }
    
    public String[] describePossibleValues() {
        return valueSet.describePossibleValues();
    }
    
    public Collection<Value> containedValues() {
        return valueSet.containedValues();
    }
    
}