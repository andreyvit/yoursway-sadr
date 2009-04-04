package com.yoursway.sadr.python.analysis.project;

import static com.google.common.collect.Lists.newArrayList;

import java.util.Collection;

public class ProjectUnit {
    
    private final Collection<FileSourceUnit> sourceUnits;
    
    public ProjectUnit(Collection<FileSourceUnit> sourceUnits) {
        this.sourceUnits = newArrayList(sourceUnits);
    }
    
    public Collection<FileSourceUnit> getModules() {
        return sourceUnits;
    }
    
}
