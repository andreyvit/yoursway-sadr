package com.yoursway.sadr.core.propagation;

import com.yoursway.sadr.core.constructs.CfgCursor;
import com.yoursway.sadr.core.constructs.DynamicContext;
import com.yoursway.sadr.core.constructs.IConstruct;
import com.yoursway.sadr.core.constructs.StaticContext;

public class LocationInControlFlow<C extends IConstruct<C, SC, DC, N>, SC extends StaticContext<C, SC, DC, N>, DC extends DynamicContext, N> {
    
    private final LocationInControlFlow<C, SC, DC, N> parent;
    private final CfgCursor<C, SC, DC, N> cursor;
    
    public LocationInControlFlow(LocationInControlFlow<C, SC, DC, N> parent, CfgCursor<C, SC, DC, N> cursor) {
        this.parent = parent;
        this.cursor = cursor;
    }
    
    public LocationInControlFlow<C, SC, DC, N> parent() {
        return parent;
    }
    
    public CfgCursor<C, SC, DC, N> cursor() {
        return cursor;
    }
    
}
