package com.yoursway.sadr.python_v2.goals.acceptors;

import static com.google.common.collect.Lists.newArrayList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo;
import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.blocks.foundation.values.Value;

public class DictIterator<Source> implements Iterable<Map<Source, RuntimeObject>>,
        Iterator<Map<Source, RuntimeObject>> {
    private final HashMap<Integer, Iterator<Value>> iterators;
    private final HashMap<Source, RuntimeObject> results;
    private final int size;
    private int count;
    private int position = 0;
    private final HashMap<Integer, ValueInfo> sources;
    private final ArrayList<Source> dimensions;
    
    public DictIterator(Map<Source, PythonValueSet> map) {
        this.results = new HashMap<Source, RuntimeObject>();
        this.iterators = new HashMap<Integer, Iterator<Value>>();
        this.sources = new HashMap<Integer, ValueInfo>();
        this.dimensions = newArrayList(map.keySet());
        this.size = sources.size();
        this.count = 1;
        for (int i = 0; i < size; i++) {
            ValueInfo valueInfo = map.get(dimensions.get(i)).getResult();
            this.sources.put(i, valueInfo);
            this.iterators.put(i, makeIterator(i));
            this.count *= valueInfo.containedValues().size();
        }
    }
    
    public HashMap<Source, RuntimeObject> next() {
        if (!hasNext()) {
            return null;
        }
        position += 1;
        for (int i = size - 1; i >= 0; i--) {
            if (iterators.get(i).hasNext()) {
                Source name = dimensions.get(i);
                results.put(name, (RuntimeObject) iterators.get(i).next());
                break;
            } else {
                Iterator<Value> iterator = makeIterator(i);
                iterators.put(i, iterator);
                results.put(dimensions.get(i), (RuntimeObject) iterator.next());
            }
        }
        return results;
    }
    
    private Iterator<Value> makeIterator(int i) {
        return sources.get(i).containedValues().iterator();
    }
    
    public boolean hasNext() {
        return position < count;
    }
    
    public void remove() {
    }
    
    public Iterator<Map<Source, RuntimeObject>> iterator() {
        return this;
    }
}
