package com.yoursway.sadr.python_v2.goals.acceptors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo;
import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.blocks.foundation.values.Value;

public class TupleIterator implements Iterator<List<RuntimeObject>>, Iterable<List<RuntimeObject>> {
    private final ArrayList<Iterator<Value>> iterators;
    private final ArrayList<RuntimeObject> results;
    private final int size;
    private int count;
    private int position;
    private final ArrayList<ValueInfo> sources;
    
    public TupleIterator(List<PythonValueSet> sources) {
        this.results = new ArrayList<RuntimeObject>();
        this.iterators = new ArrayList<Iterator<Value>>();
        this.sources = new ArrayList<ValueInfo>();
        this.size = sources.size();
        this.count = 1;
        for (int i = 0; i < size; i++) {
            ValueInfo valueInfo = sources.get(i).getResult();
            this.sources.add(valueInfo);
            this.iterators.add(makeIterator(i));
            this.count *= valueInfo.containedValues().size();
        }
    }
    
    public ArrayList<RuntimeObject> next() {
        if (!hasNext()) {
            return null;
        }
        position += 1;
        int i = size - 1;
        while (!iterators.get(i).hasNext() && i >= 0) {
            Iterator<Value> iterator = makeIterator(i);
            iterators.set(i, iterator);
            results.set(i, (RuntimeObject) iterator.next());
        }
        if (i > 0) {
            results.set(i - 1, (RuntimeObject) iterators.get(i).next());
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
    
    public Iterator<List<RuntimeObject>> iterator() {
        return this;
    }
}
