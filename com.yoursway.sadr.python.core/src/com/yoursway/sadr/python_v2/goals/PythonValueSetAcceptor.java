package com.yoursway.sadr.python_v2.goals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.yoursway.sadr.python.core.runtime.contributions.Context;
import com.yoursway.sadr.python_v2.model.RuntimeObject;
import com.yoursway.sadr.python_v2.model.builtins.PythonClass;
import com.yoursway.sadr.succeeder.IAcceptor;

public abstract class PythonValueSetAcceptor implements IAcceptor {
    
    private final Map<Context, RuntimeObject> objectToContext = new HashMap<Context, RuntimeObject>();
    private final Set<RuntimeObject> results = new HashSet<RuntimeObject>();
    private final Set<PythonClass> resultTypes = new HashSet<PythonClass>();
    
    public void addResult(RuntimeObject result, Context context) {
        results.add(result);
        resultTypes.add((PythonClass) result.getType());
        objectToContext.put(context, result);
    }
    
    public RuntimeObject getResultByContext(Context context) {
        return objectToContext.get(context);
    }
    
    public Set<PythonClass> getResultTypes() {
        return resultTypes;
    }
}
