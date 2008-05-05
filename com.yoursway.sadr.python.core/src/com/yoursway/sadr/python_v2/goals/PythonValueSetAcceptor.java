package com.yoursway.sadr.python_v2.goals;

import java.util.HashMap;
import java.util.Map;

import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo;
import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfoBuilder;
import com.yoursway.sadr.python.core.runtime.contributions.Context;
import com.yoursway.sadr.python_v2.model.RuntimeObject;
import com.yoursway.sadr.succeeder.IAcceptor;

public abstract class PythonValueSetAcceptor implements IAcceptor {
    
    private final Map<Context, RuntimeObject> objectToContext = new HashMap<Context, RuntimeObject>();
    private final ValueInfoBuilder results = new ValueInfoBuilder();
    
    public void addResult(RuntimeObject result, Context context) {
        results.addValue(result);
        results.addType(result.getType());
        objectToContext.put(context, result);
    }
    
    public RuntimeObject getResultByContext(Context context) {
        return objectToContext.get(context);
    }
    
    public ValueInfo getResult() {
        return results.build();
    }
}
