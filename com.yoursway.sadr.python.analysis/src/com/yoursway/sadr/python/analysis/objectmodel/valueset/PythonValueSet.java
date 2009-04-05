package com.yoursway.sadr.python.analysis.objectmodel.valueset;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import kilim.pausable;

import com.google.common.collect.Sets;
import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo;
import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfoBuilder;
import com.yoursway.sadr.blocks.foundation.values.Value;
import com.yoursway.sadr.core.IValueInfo;
import com.yoursway.sadr.python.analysis.aliasing.AliasConsumer;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.index.data.PassedArgumentInfo;
import com.yoursway.sadr.python.analysis.lang.unodes.Suffix;
import com.yoursway.sadr.python.analysis.objectmodel.types.BooleanType;
import com.yoursway.sadr.python.analysis.objectmodel.values.PythonValue;

public class PythonValueSet implements Iterable<PythonValue>, IValueInfo, PythonValueSetBuilder {
    
    private final ValueInfoBuilder builder = new ValueInfoBuilder(); //FIXME Build value info in getResult().
    
    public static final PythonValueSet EMPTY = new PythonValueSet();
    
    public PythonValueSet() {
    }
    
    public PythonValueSet(PythonValue result) {
        addResult(result);
    }
    
    public PythonValueSet(boolean result, PythonDynamicContext context) {
        addResult(result, context);
    }
    
    public void addResult(PythonValue result) {
        if (null == result)
            throw new IllegalStateException("There should be no null items in results!");
        
        builder.add(result.getType(), result);
    }
    
    public void addResult(boolean result, PythonDynamicContext context) {
        addResult(BooleanType.wrap(result));
    }
    
    public void addResults(PythonValueSet r) {
        for (Value value : r.getResult().containedValues()) {
            addResult(((PythonValue) value));
        }
        
    }
    
    public ValueInfo getResult() {
        return builder.build();
    }
    
    public Iterator<PythonValue> iterator() {
        Collection<Value> values = builder.build().containedValues();
        final Iterator<Value> iterator = values.iterator();
        return new Iterator<PythonValue>() {
            
            public boolean hasNext() {
                return iterator.hasNext();
            }
            
            public PythonValue next() {
                return (PythonValue) iterator.next();
            }
            
            public void remove() {
                //do nothing
            }
        };
    }
    
    public boolean isEmpty() {
        return builder.isEmpty();
    }
    
    @Override
    public String toString() {
        return builder.toString();
    }
    
    public String[] describePossibleTypes() {
        throw new UnsupportedOperationException();
    }
    
    public String[] describePossibleValues() {
        throw new UnsupportedOperationException();
    }
    
    @pausable
    public PythonValueSet call(PythonDynamicContext dc) {
        PythonValueSetBuilder result = PythonValueSet.newBuilder();
        for (PythonValue value : this)
            value.call(dc, result);
        return result.build();
    }
    
    public static PythonValueSetBuilder newBuilder() {
        return new PythonValueSet();
    }
    
    public PythonValueSet build() {
        return this;
    }
    
    public void addAll(List<PythonValueSet> results) {
        for (PythonValueSet v : results)
            addResults(v);
    }
    
    public static PythonValueSet merge(List<PythonValueSet> results) {
        PythonValueSetBuilder builder = newBuilder();
        builder.addAll(results);
        return builder.build();
    }
    
    @pausable
    public PythonValueSet getAttrFromType(String name, PythonLexicalContext sc, PythonDynamicContext dc) {
        PythonValueSetBuilder builder = newBuilder();
        for (PythonValue value : this)
            value.getAttrFromType(name, sc, dc, builder);
        return builder.build();
    }
    
    public void bind(PythonValue self, PythonValueSetBuilder builder) {
        for (PythonValue value : this)
            value.bind(self, builder);
    }
    
    public static PythonValueSet merge(PythonValueSet a, PythonValueSet b) {
        PythonValueSetBuilder builder = newBuilder();
        builder.addResults(a);
        builder.addResults(b);
        return builder.build();
    }
    
    public static PythonValueSet merge(PythonValueSet... sets) {
        PythonValueSetBuilder builder = newBuilder();
        for (PythonValueSet set : sets)
            builder.addResults(set);
        return builder.build();
    }
    
    public boolean canAlias(PythonValueSet that) {
        Set<PythonValue> thisValues = Sets.newHashSet(this);
        Set<PythonValue> thatValues = Sets.newHashSet(that);
        thisValues.retainAll(thatValues);
        return !thisValues.isEmpty();
    }
    
    public Set<Long> obtainIntegerValues() {
        Set<Long> result = new HashSet<Long>();
        for (PythonValue value : this)
            value.obtainIntegerValue(result);
        return result;
    }
    
    public void computeArgumentAliases(PassedArgumentInfo info, PythonDynamicContext dc, Suffix suffix,
            AliasConsumer aliases) {
        for (PythonValue value : this)
            value.computeArgumentAliases(info, dc, suffix, aliases);
    }
    
    @pausable
    public void findRenames(Suffix suffix, PythonLexicalContext sc, PythonDynamicContext dc,
            AliasConsumer aliases) {
        for (PythonValue value : this)
            value.findRenames(suffix, sc, dc, aliases);
    }
    
    public Collection<?> obtainTypedValues(Class<?> klass) {
        if (klass == Long.class)
            return obtainIntegerValues();
        else if (klass == PythonValue.class)
            return builder.build().containedValues();
        else
            throw new IllegalArgumentException("Cannot provide values of type " + klass.getName());
    }
}
