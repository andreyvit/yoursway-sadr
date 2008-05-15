package com.yoursway.sadr.python_v2.goals;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo;
import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfoBuilder;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.RuntimeObject;
import com.yoursway.sadr.succeeder.IAcceptor;

public abstract class PythonValueSetAcceptor implements IAcceptor {
    
    private final Map<Context, RuntimeObject> objectToContext = new HashMap<Context, RuntimeObject>();
    private final ValueInfoBuilder builder = new ValueInfoBuilder();
    
    public void addResult(RuntimeObject result, Context context) {
        if (result != null) {
            builder.add(result.getType(), result);
            objectToContext.put(context, result);
        }
    }
    
    public RuntimeObject getResultByContext(Context context) {
        return objectToContext.get(context);
    }
    
    protected void setResults(PythonValueSetAcceptor other) {
        Set<Entry<Context, RuntimeObject>> entries = other.objectToContext.entrySet();
        for (Entry<Context, RuntimeObject> entry : entries) {
            objectToContext.put(entry.getKey(), entry.getValue());
        }
    }
    
    public ValueInfo getResult() {
        return builder.build();
    }
    
    //    public void setResult(ValueInfo result) {
    //        throw new IllegalStateException("Don't use this method");
    //        if (!builder.isEmpty())
    //            throw new IllegalStateException("You can use setResult only when acceptor is empty");
    //        builder.add(result);
    //    }
}
