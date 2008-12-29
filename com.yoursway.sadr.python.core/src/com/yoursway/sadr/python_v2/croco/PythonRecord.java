package com.yoursway.sadr.python_v2.croco;

import static com.google.common.collect.Sets.newHashSet;

import java.util.Set;

import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfoBuilder;
import com.yoursway.sadr.blocks.foundation.values.CacheRecord;

public class PythonRecord implements CacheRecord {
    final int id;
    Set<Frog> frogs = newHashSet();
    Set<PythonRecord> conditionallyEqualIds = newHashSet();
    ValueInfoBuilder value = null;
    
    public PythonRecord(int id) {
        this.id = id;
    }
    
    public void addFrog(Frog frog) {
        if (frog == null)
            throw new NullPointerException("frog is null");
        if (frog.id != Frog.UNKNOWN)
            throw new IllegalArgumentException("frog is already assigned!");
        frogs.add(frog);
        frog.setId(id);
    }
}
