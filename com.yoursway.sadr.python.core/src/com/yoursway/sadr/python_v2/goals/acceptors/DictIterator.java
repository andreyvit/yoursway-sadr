package com.yoursway.sadr.python_v2.goals.acceptors;

import static com.google.common.collect.Lists.newArrayList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo;
import com.yoursway.sadr.blocks.foundation.values.Value;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;

public class DictIterator<Source> implements Iterable<Map<Source, PythonValue>>,
        Iterator<Map<Source, PythonValue>> {
    private final HashMap<Integer, Iterator<Value>> iterators;
    private final HashMap<Source, PythonValue> results;
    private final int size;
    private int count;
    private int position = 0;
    private final HashMap<Integer, ValueInfo> sources;
    private final ArrayList<Source> dimensions;
    
    public DictIterator(Map<Source, PythonValueSet> map) {
        this.results = new HashMap<Source, PythonValue>();
        this.iterators = new HashMap<Integer, Iterator<Value>>();
        this.sources = new HashMap<Integer, ValueInfo>();
        this.dimensions = newArrayList(map.keySet());
        this.size = map.size();
        this.count = 1;
        for (int i = 0; i < size; i++) {
            Source name = dimensions.get(i);
            ValueInfo valueInfo = map.get(name).getResult();
            this.sources.put(i, valueInfo);
            this.iterators.put(i, makeIterator(i));
            this.count *= valueInfo.containedValues().size();
            if (count != 0) {
                this.results.put(name, (PythonValue) iterators.get(i).next());
            }
        }
    }
    
    public HashMap<Source, PythonValue> next() {
        if (!hasNext()) {
            return null;
        }
        position += 1;
        if (position == 1) {
            return results;
        }
        for (int i = size - 1; i >= 0; i--) {
            Source name = dimensions.get(i);
            if (iterators.get(i).hasNext()) {
                results.put(name, (PythonValue) iterators.get(i).next());
                break;
            } else {
                Iterator<Value> iterator = makeIterator(i);
                iterators.put(i, iterator);
                results.put(name, (PythonValue) iterator.next());
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
    
    public Iterator<Map<Source, PythonValue>> iterator() {
        return this;
    }
}
