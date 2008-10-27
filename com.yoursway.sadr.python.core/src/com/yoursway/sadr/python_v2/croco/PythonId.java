package com.yoursway.sadr.python_v2.croco;

import static com.google.common.collect.Sets.newHashSet;

import java.util.Set;

import com.yoursway.sadr.blocks.foundation.values.RuntimeId;
import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python_v2.constructs.Frog;

public class PythonId implements RuntimeId {
    final int id;
    Set<Frog> frogs = newHashSet();
    Set<PythonId> conditionallyEqualIds = newHashSet();
    Set<RuntimeObject> values = newHashSet();
    
    public PythonId(int id) {
        this.id = id;
    }
    
    public void addFrog(Frog frog) {
        if (frog == null)
            throw new NullPointerException("frog is null");
        frogs.add(frog);
    }
}
