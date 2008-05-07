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
    private final ValueInfoBuilder builder = new ValueInfoBuilder();
    
    public void addResult(RuntimeObject result, Context context) {
        builder.add(result.getType(), result);
        objectToContext.put(context, result);
    }
    
    public RuntimeObject getResultByContext(Context context) {
        return objectToContext.get(context);
    }
    
    public ValueInfo getResult() {
        return builder.build();
    }
    
    public void setResult(ValueInfo result) {
        if (!builder.isEmpty())
            throw new IllegalStateException("You can use setResult only when acceptor is empty");
        builder.add(result);
    }
}
