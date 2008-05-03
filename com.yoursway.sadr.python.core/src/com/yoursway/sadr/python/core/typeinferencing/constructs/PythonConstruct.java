package com.yoursway.sadr.python.core.typeinferencing.constructs;

import java.util.Collection;

import com.yoursway.sadr.python.core.typeinferencing.goals.MumblaWumblaThreesome;

public interface PythonConstruct {
    
    Collection<MumblaWumblaThreesome> mumblaWumbla();
    
    void traverse(PythonConstructVisitor visitor);
    
}
