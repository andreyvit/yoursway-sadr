package com.yoursway.sadr.blocks.foundation.typesets.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.yoursway.sadr.blocks.foundation.types.Type;
import com.yoursway.sadr.blocks.foundation.typesets.TypeSet;

public abstract class AbstractTypeSet implements TypeSet {
    
    @Override
    public abstract String toString();
    
    public String[] describePossibleTypes() {
        List<String> result = new ArrayList<String>();
        for (Type type : containedTypes())
            result.add(type.describe());
        Collections.sort(result);
        return result.toArray(new String[result.size()]);
    }
    
}
