package com.yoursway.sadr.python_v2.croco;

import static com.google.common.collect.Sets.newHashSet;

import java.util.Set;

import com.yoursway.sadr.blocks.foundation.values.CacheRecord;
import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;

public class PythonRecord implements CacheRecord {
    final int id;
    Set<Frog> frogs = newHashSet();
    Set<PythonRecord> conditionallyEqualIds = newHashSet();
    Set<RuntimeObject> values = newHashSet();
    
    public PythonRecord(int id) {
        this.id = id;
    }
    
    public void addFrog(Frog frog) {
        if (frog == null)
            throw new NullPointerException("frog is null");
        frogs.add(frog);
    }
}
