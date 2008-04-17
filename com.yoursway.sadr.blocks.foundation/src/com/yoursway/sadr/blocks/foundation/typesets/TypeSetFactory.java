package com.yoursway.sadr.blocks.foundation.typesets;

import java.util.Collection;

import com.yoursway.sadr.blocks.foundation.types.Type;
import com.yoursway.sadr.blocks.foundation.typesets.internal.MultiTypeSet;
import com.yoursway.sadr.blocks.foundation.typesets.internal.SingleTypeSet;
import com.yoursway.sadr.blocks.foundation.typesets.internal.ZeroTypeSet;

public class TypeSetFactory {
    
    static final ZeroTypeSet EMPTY = new ZeroTypeSet();
    
    public static TypeSet typeSetWith(Collection<Type> types) {
        switch (types.size()) {
        case 0:
            return EMPTY;
        case 1:
            return typeSetWith(types.iterator().next());
        default:
            return new MultiTypeSet(types);
        }
    }
    
    public static SingleTypeSet typeSetWith(Type type) {
        return new SingleTypeSet(type);
    }
    
    public static ZeroTypeSet emptyTypeSet() {
        return EMPTY;
    }
    
}
