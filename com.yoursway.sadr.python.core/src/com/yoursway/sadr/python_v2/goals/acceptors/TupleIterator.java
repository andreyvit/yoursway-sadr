package com.yoursway.sadr.python_v2.goals.acceptors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo;
import com.yoursway.sadr.blocks.foundation.values.Value;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;

public class TupleIterator implements Iterator<List<PythonValue>>, Iterable<List<PythonValue>> {
    private final ArrayList<Iterator<Value>> iterators;
    private final ArrayList<PythonValue> results;
    private final int size;
    private int count;
    private int position;
    private final ArrayList<ValueInfo> sources;
    
    public TupleIterator(List<PythonValueSet> list) {
        this.results = new ArrayList<PythonValue>();
        this.iterators = new ArrayList<Iterator<Value>>();
        this.sources = new ArrayList<ValueInfo>();
        this.size = list.size();
        this.count = 1;
        for (int i = 0; i < size; i++) {
            ValueInfo valueInfo = list.get(i).getResult();
            this.sources.add(valueInfo);
            this.iterators.add(makeIterator(i));
            this.count *= valueInfo.containedValues().size();
            if (count != 0) {
                this.results.add(i, (PythonValue) iterators.get(i).next());
            }
        }
    }
    
    public ArrayList<PythonValue> next() {
        if (!hasNext()) {
            return null;
        }
        position += 1;
        if (position == 1)
            return results;
        
        int i = size - 1;
        while (i >= 0) {
            if (iterators.get(i).hasNext()) {
                results.set(i - 1, (PythonValue) iterators.get(i).next());
                break;
            } else {
                Iterator<Value> iterator = makeIterator(i);
                iterators.set(i, iterator);
                results.set(i, (PythonValue) iterator.next());
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
    
    public Iterator<List<PythonValue>> iterator() {
        return this;
    }
}