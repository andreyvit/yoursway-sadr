package com.yoursway.sadr.blocks.foundation;

import com.yoursway.sadr.python.core.runtime.PythonRuntimeModel;

public interface BuildingBlock {
    
    void contributeToSchema(AnalysisSchemaBuilder builder);
    
    void contributeToModel(PythonRuntimeModel model);
    
}
