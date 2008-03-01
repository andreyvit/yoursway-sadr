package com.yoursway.sadr.ruby.core.typeinferencing.typesets;

import java.util.Collection;

import com.yoursway.sadr.ruby.core.typeinferencing.types.Type;
import com.yoursway.sadr.ruby.core.typeinferencing.typesets.internal.MultiTypeSet;
import com.yoursway.sadr.ruby.core.typeinferencing.typesets.internal.SingleTypeSet;
import com.yoursway.sadr.ruby.core.typeinferencing.typesets.internal.ZeroTypeSet;

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
