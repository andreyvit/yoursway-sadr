package com.yoursway.sadr.python.core.runtime;

import com.yoursway.sadr.blocks.foundation.AnalysisSchema;
import com.yoursway.sadr.blocks.foundation.AnalysisSchemaBuilder;
import com.yoursway.sadr.blocks.integer_literals.IntegerTypesConfig;
import com.yoursway.sadr.blocks.integer_literals.IntegerTypesSupport;
import com.yoursway.sadr.blocks.integer_literals.IntegerTypesSupportImpl;
import com.yoursway.sadr.blocks.simple_types.SimpleTypesSupport;
import com.yoursway.sadr.blocks.simple_types.SimpleTypesSupportImpl;

public class PythonAnalysisSchema {
    
    private final AnalysisSchema schema;
    
    public final SimpleTypesSupport simpleTypesSupport;
    
    public final IntegerTypesSupport integerTypesSupport;
    
    public PythonAnalysisSchema() {
        AnalysisSchemaBuilder builder = new AnalysisSchemaBuilder();
        simpleTypesSupport = SimpleTypesSupportImpl.create(builder);
        integerTypesSupport = IntegerTypesSupportImpl.create(builder, new IntegerTypesConfig("int", "long"),
                simpleTypesSupport);
        schema = builder.build();
    }
    
    public PythonRuntimeModel newRuntimeModel() {
        return new PythonRuntimeModel(this, schema.forRuntimeModel());
    }
    
}
